/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package alerts;

import javafx.scene.control.Alert;

/**
 *
 * @author Star fruit
 */
public class SuccessAlert {
    static public void showAlert(String msg){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Success");
        alert.setContentText(msg);
        alert.showAndWait();
    };
}
