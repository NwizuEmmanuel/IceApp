/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package add_record;

import alerts.GenErrorAlert;
import alerts.SuccessAlert;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import constants.CashflowConstants;
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
public class Add_recordController implements Initializable {

    @FXML
    private JFXTextField itemInput;
    @FXML
    private JFXComboBox<String> cashflowInput;
    @FXML
    private JFXDatePicker tranDateInput;
    @FXML
    private JFXTextField amountInput;
    
    @FXML
    private void addBtn(){
        try {
            database.DatabaseActions da = new DatabaseActions();
            da.addRecord(itemInput.getText(), amountInput.getText(), cashflowInput.getValue(), tranDateInput.getValue().toString());
            SuccessAlert.showAlert("Successfully done!");
            clearPara();
        } catch (SQLException ex) {
            GenErrorAlert.showAlert("Error\n"+ex.getMessage());
        }catch(Exception e){
            GenErrorAlert.showAlert("Error\n"+e.getMessage());
        }
    }
    
    private void clearPara(){
        itemInput.clear();
        amountInput.clear();
        tranDateInput.setValue(null);
        cashflowInput.setValue(null);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cashflowInput.getItems().addAll(CashflowConstants.const_income, CashflowConstants.const_expense);
    }    
    
}
