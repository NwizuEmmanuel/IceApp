/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ipm;

import alerts.IpAlert;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import preferences.Prefs;

/**
 * FXML Controller class
 *
 * @author Star fruit
 */
public class IpmController implements Initializable {

    @FXML
    private TextField ipTextfield;
    @FXML
    private Button assignBtn;
    @FXML
    private Text invalidIndicator;

    /**
     * Initializes the controller class.
     */
    @FXML
    private void assignBtnListener() {
        if (!ipTextfield.getText().isEmpty()) {
            Prefs prefs = new Prefs();
            prefs.writeIpAddress(ipTextfield.getText());
            IpAlert ia = new IpAlert();
            ia.showIpAlert();
        }else{
            invalidIndicator.setVisible(true);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ipTextfield.setOnKeyReleased(e->{
            invalidIndicator.setVisible(false);
        });
    }

}
