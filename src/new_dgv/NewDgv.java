package new_dgv;

import alerts.DisplayError;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import constants.GlobalVariables;
import database.DatabaseActions;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import preferences.Prefs;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class NewDgv implements Initializable {

    @FXML
    private JFXTextField dgNameInput;

    @FXML
    private JFXDatePicker dateInput;

    @FXML
    private void doneBtnListener() {
        Prefs.putDataManager(dgNameInput.getText(), dgNameInput.getText());
        GlobalVariables.dgvCreationDate = dateInput.getValue().toString();
        try {
            DatabaseActions.recordGroupValue();
        } catch (SQLException e) {
            DisplayError.showErrorAlert("Can't create DGV or already exist.");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
