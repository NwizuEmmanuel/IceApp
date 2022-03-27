/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package table_scenes;

import com.jfoenix.controls.JFXComboBox;
import database.DatabaseActions;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import preferences.Prefs;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;

/**
 * FXML Controller class
 *
 * @author Star fruit
 */
public class Table_sceneController implements Initializable {

    @FXML
    private JFXComboBox<String> courseInput;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        database.DatabaseActions da = new DatabaseActions();
        try {
            da.loadcourses(courseInput);
        } catch (SQLException ex) {
            Logger.getLogger(Table_sceneController.class.getName()).log(Level.SEVERE, null, ex);
        }
        courseInput.setOnAction(e->{
            Prefs prefClass = new Prefs();
            Preferences pref = Preferences.userRoot().node(prefClass.getClass().getName());
            prefClass.writeTableValue(courseInput.getValue());
        });
    }    
    
}
