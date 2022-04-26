/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package new_intern;

import alerts.GenErrorAlert;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import database.DatabaseActions;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.event.AncestorEvent;

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
    @FXML
    private AnchorPane root;

    /**
     * Initializes the controller class.
     */
    private void clearParameters() {
        telephoneInput.clear();
        amountInput.clear();
        regdateInput.setValue(null);
        courseInput.setValue(null);
        fullNameInput.clear();
    }

    @FXML
    private void addCourse() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/course_setting/course_setting.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.setTitle("Add course");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNIFIED);
        stage.setOnCloseRequest(e->{
            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/new_intern/new_intern.fxml"));
            try {
                Parent parent = loader1.load();
                root.getScene().setRoot(parent);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        stage.show();
    }

    @FXML
    private void registerBtn() throws SQLException {
        DatabaseActions da = new DatabaseActions();
        if(fullNameInput.getText().matches("[a-zA-Z]+") && telephoneInput.getText().matches("[0-9]{11}")&&
        courseInput.getValue()!=null && amountInput.getText().matches("[0-9]+") && regdateInput.getValue()!=null){
            da.registerNewIntern(fullNameInput.getText(), telephoneInput.getText(), courseInput.getValue(), amountInput.getText(), regdateInput.getValue().toString());
            clearParameters();
        }else {
            GenErrorAlert.showAlert("Error in inputs\nInvalid parameters\nTelephone no. must be 11.\n"
                    + "Amount must be >= 0");
        }
    }

    private void addCourses() throws SQLException {
        DatabaseActions da = new DatabaseActions();
        da.loadCourses(courseInput);
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
