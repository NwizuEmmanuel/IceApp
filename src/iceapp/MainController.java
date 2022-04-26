/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package iceapp;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import database.DatabaseActions;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import keys.PrefKeys;
import preferences.Prefs;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

/**
 *
 * @author Star fruit
 */
public class MainController implements Initializable {

    @FXML
    private VBox loginPane;
    @FXML
    private JFXTextField userField;
    @FXML
    private JFXPasswordField passField;
    @FXML
    private JFXCheckBox checkBox;

    @FXML
    private void cancelBtnListener() {
        System.exit(0);
    }

    Prefs p = new Prefs();
    Preferences preferences = Preferences.userRoot().node(p.getClass().getName());

    @FXML
    private void ipAssigner() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ipm/ipm.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void authenticateBtnListener() throws IOException {
        DatabaseActions da = new DatabaseActions();
        da.loginAction(userField.getText(), passField.getText(), loginPane);
    }

    @FXML
    private void checkBoxListener(){
        if(checkBox.isSelected()){
            Prefs.putStartUp("yes");
        }else {
            Prefs.removeStartUp("no");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        checkBox.setSelected(preferences.get(PrefKeys.startUpKey, "").equals("yes"));
    }

    public VBox getLoginPane(){
        return loginPane;
    }
}
