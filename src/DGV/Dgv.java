package DGV;

import alerts.DisplayError;
import alerts.SuccessAlert;
import constants.GlobalVariables;
import database.DatabaseActions;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import keys.PrefKeys;
import models.DGVModel;
import preferences.Prefs;

import javafx.scene.text.Text;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class Dgv implements Initializable {

    @FXML
    private TableView<DGVModel> tableView;

    @FXML
    private TableColumn<DGVModel, String> Col_1;

    @FXML
    private TableColumn<DGVModel, String> Col_2;

    @FXML
    private TableColumn actionsCol;

    @FXML
    private Text text;

    Prefs prefs = new Prefs();
    Preferences preferences = Preferences.userRoot().node(prefs.getClass().getName());

    private void setData(){
        try {
            DatabaseActions.loadDGV(tableView);
        } catch (SQLException e) {
            DisplayError.showErrorAlert("Can't load data.");
        }
        Col_1.setCellValueFactory(new PropertyValueFactory<>("DGV"));
        Col_2.setCellValueFactory(new PropertyValueFactory<>("CreationDate"));
        actionsCol.setCellFactory(actionCellFactory);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        text.setText(preferences.get(PrefKeys.dgvKey,"")+" in use");
        setData();
    }

    Callback<TableColumn<DGVModel, String>, TableCell<DGVModel, String>> actionCellFactory = (param) -> {

        // setting a cell to accept button

        return new TableCell<DGVModel, String>() {

            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    HBox box = new HBox();
                    box.setSpacing(10);
                    Button button = new Button("use");
                    button.setOnAction(e->{
                        DGVModel dgvModel = tableView.getItems().get(getIndex());
                        Prefs.putDataManager(dgvModel.getDGV());
                        text.setText(dgvModel.getDGV()+"in use");
                        SuccessAlert.showAlert("DGV in use now.");
                    });
                    Button button1 = new Button("delete");
                    button1.setOnAction(e->{
                        DGVModel dgvModel = tableView.getItems().get(getIndex());
                        String sql1 = "delete from "+DatabaseActions.tableNameForDGV+" where dgv='"+dgvModel.getDGV()+"' ";
                        String sql2 = "delete from "+DatabaseActions.tableNameForIntern+" where data_group_value='"+dgvModel.getDGV()+"' ";
                        String sql3 = "delete from "+DatabaseActions.tableNameForOthers+" where data_group_value='"+dgvModel.getDGV()+"' ";
                        try {
                            PreparedStatement ps1 = DatabaseActions.connectToDB().prepareStatement(sql1);
                            PreparedStatement ps2 = DatabaseActions.connectToDB().prepareStatement(sql2);
                            PreparedStatement ps3 = DatabaseActions.connectToDB().prepareStatement(sql3);

                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setContentText("Do you want to delete this?");
                            alert.setTitle("Confirmation");
                            alert.setHeaderText("Confirmation dialogue");
                            ButtonType noButton = new ButtonType("No");
                            ButtonType yesButton = new ButtonType("Yes");
                            alert.getButtonTypes().setAll(yesButton,noButton);
                            Optional<ButtonType> result = alert.showAndWait();
                            if(result.isPresent()){
                                if(result.get() == yesButton){
                                    ps1.execute();
                                    ps2.execute();
                                    ps3.execute();
                                    setData();
                                }else {
                                    alert.close();
                                }
                            }
                        } catch (SQLException ex) {
                            DisplayError.showErrorAlert(GlobalVariables.privilegeMsg);
                        }
                    });
                    box.getChildren().addAll(button,button1);
                    setGraphic(box);
                }
                setText(null);
            }
        };
    };
}
