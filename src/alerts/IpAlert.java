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
public class IpAlert {
    public void showIpAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Error");
        alert.setContentText("Ip assigned successfully");
        alert.showAndWait();
    };
}
