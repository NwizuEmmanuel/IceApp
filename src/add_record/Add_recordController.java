/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package add_record;

import alerts.DisplayError;
import alerts.SuccessAlert;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import database.DatabaseActions;
import jasperReporter.InvoicePrinter;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.text.Text;
import net.sf.jasperreports.engine.JRException;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import static java.lang.String.valueOf;

/**
 * FXML Controller class
 *
 * @author Star fruit
 */
public class Add_recordController implements Initializable {

    @FXML
    private JFXTextField itemInput;
    @FXML
    private JFXDatePicker tranDateInput;
    @FXML
    private JFXTextField amountInput;
    @FXML
    private JFXTextField customerF;
    @FXML
    private JFXTextField addressF;
    @FXML
    private JFXTextField telephoneF;
    @FXML
    private JFXTextField descriptionF;
    @FXML
    private JFXTextField amountF;
    @FXML
    private JFXDatePicker dateF;
    @FXML
    private Text vatText;
    @FXML
    private JFXCheckBox vatChecker;
    @FXML
    private JFXDatePicker dueDateF;
    @FXML
    private Spinner<Integer> quantityF;

    static public String pubVat = null;

    @FXML
    private void addBtn() throws SQLException {
        if(itemInput.getText().matches("[0-9A-Za-z -]+") && amountInput.getText().matches("[0-9]+")
        && tranDateInput.getValue()!=null){
            database.DatabaseActions da = new DatabaseActions();
            da.addRecord(itemInput.getText(), amountInput.getText(), tranDateInput.getValue().toString());
            SuccessAlert.showAlert("Successfully done!");
            clearPara();
        }else {
            DisplayError.showErrorAlert("Invalid parameters.");
        }
    }

    @FXML
    private void recordAction() throws JRException, SQLException, IOException {
        if(!customerF.getText().isEmpty() && !addressF.getText().isEmpty()
        && telephoneF.getText().matches("[0-9]{11}") && !descriptionF.getText().isEmpty()
        && amountF.getText().matches("[0-9]+") && dateF.getValue() != null
        && dueDateF.getValue() != null
         && quantityF.getValue() !=null){
            DatabaseActions.addOther2Data(customerF.getText(),addressF.getText(),telephoneF.getText(),
                    descriptionF.getText(),amountF.getText(),dateF.getValue().toString(),vatChecker.isSelected(),dueDateF.getValue().toString(),
                    quantityF.getValue());
            SuccessAlert.showAlert("Successfully recorded!");
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Do you want to print invoice now?");
            alert.setHeaderText(null);
            alert.setTitle("Confirm");
            ButtonType yesBtn = new ButtonType("Yes");
            ButtonType noBtn = new ButtonType("No");
            alert.getButtonTypes().setAll(yesBtn,noBtn);
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == yesBtn){
                if (vatChecker.isSelected()){
                    InvoicePrinter.printInvoice(customerF.getText(),1);
                }else if(!vatChecker.isSelected()){
                    InvoicePrinter.printInvoice(customerF.getText(),2);
                }
            }else if(result.isPresent() && result.get() == noBtn){
                alert.close();
            }
            clearPara2();
        }else {
            DisplayError.showErrorAlert("Invalid parameters.");
        }
    }
    
    private void clearPara(){
        itemInput.clear();
        amountInput.clear();
        tranDateInput.setValue(null);
    }

    private void clearPara2(){
        customerF.clear();
        addressF.clear();
        telephoneF.clear();
        descriptionF.clear();
        amountF.clear();
        dateF.setValue(null);
        dueDateF.setValue(null);
        vatText.setText("0");
        vatChecker.setSelected(false);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,50);
        valueFactory.setValue(1);
        quantityF.setValueFactory(valueFactory);
    }

    @FXML
    private void vatCalculator(){
        if(!amountF.getText().isEmpty()){
            double vatConst = 0.075;
            double total = Double.parseDouble(amountF.getText());
            double vat = (vatConst * total) + total;
            String mainVat = valueOf(vat);
            vatText.setText(mainVat);
            vatText.setVisible(true);
            pubVat = mainVat;
        }else{
            DisplayError.showErrorAlert("Total amount not specified.");
        }
    }

    @FXML
    private void vatInclude(){
        if(!amountF.getText().isEmpty()){
            double vatConst = 0.075;
            double total = Double.parseDouble(amountF.getText());
            double vat = (vatConst * total) + total;
            pubVat = valueOf(vat);
        }else{
            DisplayError.showErrorAlert("Total amount not specified.");
        }
    }
    
}
