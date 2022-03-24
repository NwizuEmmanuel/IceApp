package new_dgv;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import constants.GlobalVariables;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import preferences.Prefs;

import java.net.URL;
import java.util.ResourceBundle;

public class NewDgv implements Initializable {

    @FXML
    private JFXTextField dgNameInput;

    @FXML
    private JFXDatePicker dateInput;

    @FXML
    private void doneBtnListener(){
        Prefs.putDataManager(dgNameInput.getText(), dgNameInput.getText());
        GlobalVariables.dgvCreationDate = dateInput.getValue().toString();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
