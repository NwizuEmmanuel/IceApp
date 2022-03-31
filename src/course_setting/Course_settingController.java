/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package course_setting;

import alerts.DisplayError;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import constants.GlobalVariables;
import database.DatabaseActions;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import models.CoursePriceModel;
import models.InternModel;

/**
 * FXML Controller class
 *
 * @author Star fruit
 */
public class Course_settingController implements Initializable {

    @FXML
    private JFXTextField courseInput;
    @FXML
    private JFXTextField priceInput;
    @FXML
    private TableView<CoursePriceModel> tableview;
    @FXML
    private TableColumn<CoursePriceModel, String> courseCol;
    @FXML
    private TableColumn<CoursePriceModel, String> priceCol;
    @FXML
    private TableColumn actionCol;

    /**
     * Initializes the controller class.
     */
    
    private void showDataOntable() throws SQLException{
        DatabaseActions da = new DatabaseActions();
        da.loadCoursePriceData(tableview);
        courseCol.setCellValueFactory(new PropertyValueFactory<>("course"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        actionCol.setCellFactory(coursePriceFac);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            showDataOntable();
        } catch (SQLException ex) {
            DisplayError.showErrorAlert(ex.getMessage());
        }
    }    

    @FXML
    private void addListener() throws SQLException {
        DatabaseActions da = new DatabaseActions();
        if(priceInput.getText().matches("[0-9]+")){
            try {
                da.coursePriceInput(courseInput.getText(), priceInput.getText());
            } catch (SQLException ex) {
                DisplayError.showErrorAlert(GlobalVariables.privilegeMsg);
            }
        }else {
            DisplayError.showErrorAlert("Only numbers is allowed.");
        }
        showDataOntable();
        courseInput.clear();
        priceInput.clear();
    }
    
    Callback<TableColumn<InternModel, String>, TableCell<InternModel, String>> coursePriceFac = (param) -> {

        // setting a cell to accept button
        final TableCell<InternModel, String> cell = new TableCell<InternModel, String>() {

            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    final JFXButton button = new JFXButton("delete");
                    button.setGraphic(new ImageView(new Image("/assets/icons8_waste_32px.png")));
                    button.setStyle("-fx-background-color: #E9E9E9;");
                    button.setOnAction(e -> {
                        CoursePriceModel im = tableview.getItems().get(getIndex());
                        String query = "delete from course_price where course='" + im.getCourse()+ "'";
                        try {
                            PreparedStatement ps = DatabaseActions.connectToDB().prepareStatement(query);
                            ps.execute();
                            showDataOntable();
                        } catch (SQLException ex) {
                            DisplayError.showErrorAlert(GlobalVariables.privilegeMsg);
                        }
                    });
                    setGraphic(button);
                }
                setText(null);
            }
        };

        return cell;
    };
    
}
