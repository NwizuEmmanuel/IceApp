package DGV;

import alerts.DisplayError;
import alerts.SuccessAlert;
import com.jfoenix.controls.JFXButton;
import database.DatabaseActions;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import models.DGVModel;
import models.InternModel;
import models.OtherModel;
import preferences.Prefs;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Dgv implements Initializable {

    @FXML
    private TableView<DGVModel> tableView;

    @FXML
    private TableColumn<DGVModel, String> internCol;

    @FXML
    private TableColumn<DGVModel, String> otherCol;

    @FXML
    private TableColumn actionsCol;

    private void setData(){
        try {
            DatabaseActions.loadDGV(tableView);
        } catch (SQLException e) {
            DisplayError.showErrorAlert("Can't load data.");
        }
        internCol.setCellValueFactory(new PropertyValueFactory<>("internValue"));
        otherCol.setCellValueFactory(new PropertyValueFactory<>("otherValue"));
        actionsCol.setCellFactory(actionCellFactory);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
                    Button button = new Button("use");
                    button.setOnAction(e->{
                        DGVModel dgvModel = tableView.getItems().get(getIndex());
                        Prefs.putDataManager(dgvModel.getInternValue(), dgvModel.getOtherValue());
                        SuccessAlert.showAlert("DGV in use now.");
                    });
                    setGraphic(button);
                }
                setText(null);
            }
        };
    };
}
