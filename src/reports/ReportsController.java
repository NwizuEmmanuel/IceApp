package reports;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDatePicker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ReportsController implements Initializable {

    @FXML
    private JFXDatePicker dateInput;

    @FXML
    private JFXButton button;

    @FXML
    private JFXCheckBox internCB;

    @FXML
    private JFXCheckBox otherCB;

    @FXML
    private void buttonAction() throws SQLException {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File file_dir = directoryChooser.showDialog(new Stage());
        if (file_dir != null) {
            if(internCB.isSelected()){
                ReportCreator.createInternReport(file_dir, dateInput.getValue());
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dateInput.setValue(LocalDate.now());
    }
}
