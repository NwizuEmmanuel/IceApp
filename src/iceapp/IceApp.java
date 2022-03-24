/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML.java to edit this template
 */
package iceapp;

import database.DatabaseActions;

import java.util.Objects;
import java.util.prefs.Preferences;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("main.fxml")));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Ice network");
        stage.setOnCloseRequest(e -> {
            prefs.remove(PrefKeys.userKey);
            prefs.remove(PrefKeys.passKey);
        });
        stage.setResizable(false);
        stage.setMaximized(false);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
