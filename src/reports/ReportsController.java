package reports;

import alerts.DisplayError;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import database.DatabaseActions;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import models.DGVModel;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ReportsController implements Initializable {

    @FXML
    private JFXComboBox<String> month;

    @FXML
    private JFXTextField year;

    @FXML
    private TableView<DGVModel> tableView;

    @FXML
    private TableColumn<DGVModel, String> Col_1;

    @FXML
    private TableColumn<DGVModel, String> Col_2;

    @FXML
    private TableColumn<DGVModel, String> actionsCol;

    private void loadData() {
        try {
            DatabaseActions.loadDGV(tableView);
        } catch (SQLException e) {
            DisplayError.showErrorAlert("Can't display data.");
        }
        Col_1.setCellValueFactory(new PropertyValueFactory<>("DGV"));
        Col_2.setCellValueFactory(new PropertyValueFactory<>("CreationDate"));
        actionsCol.setCellFactory(actionCellFactory);
    }

    @FXML
    private void reload(){
        loadData();
    }

    @FXML
    private void searchBtn() throws SQLException {
        DatabaseActions.DGVSearchQuery(month.getValue(),year.getText(),tableView);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadData();
        month.getItems().addAll(
                "January",
                "February",
                "March",
                "April",
                "May",
                "June",
                "July",
                "August",
                "September",
                "October",
                "November",
                "December"
        );
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
                    Button button = new Button("Get report");
                    button.setOnAction(e->{
                        DGVModel dgvModel = tableView.getItems().get(getIndex());
                        DirectoryChooser directoryChooser = new DirectoryChooser();
                        File file = directoryChooser.showDialog(new Stage());
                        if(file != null){
                            try {
                                ReportCreator.createReport(dgvModel.getDGV(), file);
                            } catch (SQLException ex) {
                                DisplayError.showErrorAlert("Error can't get report.");
                            }
                        }
                    });
                    setGraphic(button);
                }
                setText(null);
            }
        };
    };
}
