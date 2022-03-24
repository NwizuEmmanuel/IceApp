/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package iceapp;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
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
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author Star fruit
 */
public class MainController implements Initializable {

    @FXML
    public Pane loginPane;

    @FXML
    private JFXButton cancel_btn;

    @FXML
    private JFXButton authenticate_btn;
    @FXML
    private JFXTextField userField;
    @FXML
    private JFXPasswordField passField;

    @FXML
    private void cancelBtnListener() {
        System.exit(0);
    }

    @FXML
    private void ipAssigner() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ipm/ipm.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void authenticateBtnListener() throws IOException, SQLException {
        DatabaseActions da = new DatabaseActions();
        da.loginAction(userField.getText(), passField.getText(), loginPane);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
