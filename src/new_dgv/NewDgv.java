package new_dgv;

import alerts.DisplayError;
import alerts.SuccessAlert;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import constants.GlobalVariables;
import database.DatabaseActions;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import preferences.Prefs;

import java.net.URL;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class NewDgv implements Initializable {

    @FXML
    private JFXTextField dgNameInput;

    @FXML
    private JFXDatePicker dateInput;

    @FXML
    private VBox root;

    @FXML
    private void doneBtnListener() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd MMMM, yyyy");
        if(!dgNameInput.getText().isEmpty() || dateInput.getValue() != null){
            Prefs.putDataManager(dgNameInput.getText());
            GlobalVariables.dgvCreationDate = dateTimeFormatter.format(dateInput.getValue());
            try {
                DatabaseActions.recordGroupValue();
                SuccessAlert.showAlert("Well created!");
                root.getScene().getWindow().hide();
            } catch (SQLException e) {
                DisplayError.showErrorAlert("Can't create DGV or already exist.");
            }
        }else {
            DisplayError.showErrorAlert("Empty parameters not allowed.");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
