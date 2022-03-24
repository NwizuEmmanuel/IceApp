/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package new_intern;

import alerts.GenErrorAlert;
import alerts.SuccessAlert;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import database.DatabaseActions;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author Star fruit
 */
public class New_internController implements Initializable {

    @FXML
    private JFXTextField fullNameInput;
    @FXML
    private JFXComboBox<String> courseInput;
    @FXML
    private JFXTextField amountInput;
    @FXML
    private JFXTextField telephoneInput;
    @FXML
    private JFXDatePicker regdateInput;

    /**
     * Initializes the controller class.
     */
    private void clearParsmeters() {
        telephoneInput.clear();
        amountInput.clear();
        regdateInput.setValue(null);
        courseInput.setValue(null);
        fullNameInput.clear();
    }

    @FXML
    private void registerBtn() {
        DatabaseActions da = new DatabaseActions();
        try {
            da.registerNewIntern(fullNameInput.getText(), telephoneInput.getText(), courseInput.getValue(), amountInput.getText(), regdateInput.getValue().toString());
            SuccessAlert.showAlert("Success");
            clearParsmeters();
        } catch (SQLException e) {
            GenErrorAlert.showAlert(e.getMessage() + "\nError in inputs\nInvalid parameters\nTelephone no. must be 11.\n"
                    + "Amount must be >= 0");
        } catch (Exception e) {
            GenErrorAlert.showAlert(e.getMessage() + "\nError in inputs\nInvalid parameters\nTelephone no. must be 11.\n"
                    + "Amount must be >= 0");
        }
    }

    private void addCourses() throws SQLException {
        DatabaseActions da = new DatabaseActions();
        da.loadcourses(courseInput);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            addCourses();
        } catch (SQLException ex) {
            Logger.getLogger(New_internController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
