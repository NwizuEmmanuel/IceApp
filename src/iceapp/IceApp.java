/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML.java to edit this template
 */
package iceapp;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;
import java.util.prefs.Preferences;

import alerts.DisplayError;
import database.DatabaseActions;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import keys.PrefKeys;
import preferences.Prefs;

/**
 *
 * @author Star fruit
 */
public class IceApp extends Application {

    static Prefs prefClass = new Prefs();
    Preferences prefs = Preferences.userRoot().node(prefClass.getClass().getName());

//    ice database name is icehub
    @Override
    public void start(Stage stage) throws Exception {
        startUpInitCheck();
        checkStartUp();
        startUpGuide();
        if(prefs.get(PrefKeys.userKey, "").isEmpty() || prefs.get(PrefKeys.passKey, "").isEmpty() || prefs.get(PrefKeys.ipKey,"").isEmpty()){
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("main.fxml")));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Ice Network");
            stage.getIcons().add(new Image(Objects.requireNonNull(IceApp.class.getResourceAsStream("/assets/Ice Logo.png"))));
            stage.setResizable(false);
            stage.setMaximized(false);
            stage.show();
        }else {
            try {
            	Class.forName("com.mysql.jdbc.Driver");
                DriverManager.getConnection(DatabaseActions.DB_URL, prefs.get(PrefKeys.userKey, ""), prefs.get(PrefKeys.passKey, ""));
                FXMLLoader lLoader = new FXMLLoader(getClass().getResource("/mainpage/mainpage.fxml"));
                Scene scene = new Scene(lLoader.load());
                stage.setTitle("ICE MS");
                stage.setScene(scene);
                stage.getIcons().add(new Image(Objects.requireNonNull(DatabaseActions.class.getResourceAsStream("/assets/Ice Logo.png"))));
                stage.setMaximized(true);
                stage.show();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                DisplayError.showErrorAlert("Can't connect to database.\nInvalid username or password");
            }
        }
    }

    private void startUpGuide() throws URISyntaxException, IOException {
        if(prefs.get(PrefKeys.startUpKey, "").equals("yes")){
            Desktop desk = Desktop.getDesktop();
            desk.browse(new URI("https://nwizuemmanuel200.medium.com/ice-management-app-ice-ma-93f2436265d4"));
        }
    }

    private void checkStartUp(){
        if(prefs.get(PrefKeys.startUpKey, "").equals("yes")){
            Prefs.putStartUp("yes");
        }else {
            Prefs.removeStartUp("no");
        }
    }

    private void startUpInitCheck(){
        if(prefs.get(PrefKeys.startUpKey, "").isEmpty()){
            Prefs.putStartUp("yes");
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
