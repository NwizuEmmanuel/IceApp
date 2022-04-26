/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package mainpage;

import alerts.DisplayError;
import com.jfoenix.controls.JFXButton;
import constants.GlobalVariables;
import database.DatabaseActions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import keys.PrefKeys;
import models.InternModel;
import models.Other2Model;
import models.OtherModel;
import preferences.Prefs;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

/**
 * FXML Controller class
 *
 * @author Star fruit
 */
public class MainpageController implements Initializable {

    @FXML
    private BorderPane dashboard_child;// child_1
    @FXML
    private BorderPane intern_child;// child_2
    @FXML
    private BorderPane others_child;// child_3
    @FXML
    private Circle circle_1;
    @FXML
    private Circle circle_2;
    @FXML
    private Circle circle_3;
    @FXML
    private BorderPane dashboard_page;
    @FXML
    private BorderPane intern_page;
    @FXML
    private BorderPane others_page;
    @FXML
    private BorderPane help_child; // child_4
    @FXML
    private TableView<OtherModel> othersTable;
    @FXML
    private TableColumn<OtherModel, String> othersItemCol;
    @FXML
    private TableColumn<OtherModel, String> othersAmountCol;
    @FXML
    private TableColumn<OtherModel, String> othersTransDateCol;
    @FXML
    private TableView<InternModel> internTable;
    @FXML
    private TableColumn<InternModel, String> internNameCol;
    @FXML
    private TableColumn<InternModel, String> internTelephoneCol;
    @FXML
    private TableColumn<InternModel, String> internCourseCol;
    @FXML
    private TableColumn<InternModel, String> internRegDateCol;
    @FXML
    private TableColumn<InternModel, String> othersActionCol;
    @FXML
    private TableColumn<InternModel, String> internAmountCol;
    @FXML
    private TextField otherSearchBar;
    @FXML
    private TextField internSearchBar;
    @FXML
    private PieChart cashFlowPie;
    @FXML
    private Text expenseText;
    @FXML
    private Text incomeText;
    @FXML
    private Text balanceText;
    @FXML
    private VBox dashboardPane;
    @FXML
    private BorderPane report_child;
    @FXML
    private JFXButton newBtn;
    @FXML
    private JFXButton courseSettingBtn;
    @FXML
    private AnchorPane root;
    @FXML
    private TableColumn<Other2Model, String>customerCol;
    @FXML
    private TableColumn<Other2Model, String>addressCol;
    @FXML
    private TableColumn<Other2Model, String>telephoneCol;
    @FXML
    private TableColumn<Other2Model, String>descriptionCol;
    @FXML
    private TableColumn<Other2Model, String>amountCol;
    @FXML
    private TableColumn<Other2Model, String>dateCol;
    @FXML
    private TableColumn<Other2Model, String>dueDateCol;
    @FXML
    private TableColumn<Other2Model, String>vatCol;
    @FXML
    private TableColumn<Other2Model, String>quantityCol;
    @FXML
    private TableView<Other2Model> other2TableView;
    @FXML
    private Tab incomeTab;
    @FXML
    private Tab expenseTab;
    @FXML
    private TabPane tabPane;

    static NumberFormat numberFormat = NumberFormat.getInstance();

    @FXML
    private void clearTextIntern() {
        internSearchBar.clear();
    }

    @FXML
    private void clearTextOther() {
        otherSearchBar.clear();
    }

    static Prefs prefsClass = new Prefs();
    static Preferences preferences = Preferences.userRoot().node(prefsClass.getClass().getName());

    String sqlRestrict = "alter table "+DatabaseActions.dummyTable+" modify dummy_text text not null";

    private void hideCircleIndicators() {
        circle_1.setVisible(false);
        circle_2.setVisible(false);
        circle_3.setVisible(false);
    }

    @FXML
    private void newInternBtnListener() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/new_intern/new_intern.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(loader.load());
        stage.setResizable(false);
        stage.setTitle("Registrar");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.getIcons().add(new Image(Objects.requireNonNull(MainpageController.class.getResourceAsStream("/assets/Ice Logo.png"))));
        stage.setScene(scene);
        stage.showAndWait();
        showDataOnInternTable();
    }

    @FXML
    private void addRecordBtnListener() throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/add_record/add_record.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(loader.load());
        stage.setResizable(false);
        stage.setTitle("Registrar");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.getIcons().add(new Image(Objects.requireNonNull(MainpageController.class.getResourceAsStream("/assets/Ice Logo.png"))));
        stage.setScene(scene);
        stage.setOnCloseRequest(e->{
//            String sql = "delete from "+DatabaseActions.printerTable+" where id>=0";
//            try {
//                PreparedStatement preparedStatement = DatabaseActions.connectToDB().prepareStatement(sql);
//                preparedStatement.execute();
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//            }
        });
        stage.showAndWait();
        refresh();
    }

    @FXML
    private void onHover_child_1() {
        dashboard_child.setStyle("-fx-background-color: white");
    }

    @FXML
    private void onHoverExit_child_1() {
        dashboard_child.setStyle("-fx-background-color: #E9E9E9");
    }

    @FXML
    private void onPress_child_1() {
        dashboard_child.setStyle("-fx-background-color: #A6A6A6;");
        hideCircleIndicators();
        circle_1.setVisible(true);
        dashboard_page.toFront();
        loadCashFlowPie();
    }

    @FXML
    private void onHover_child_2() {
        intern_child.setStyle("-fx-background-color: white");
    }

    @FXML
    private void onHoverExit_child_2() {
        intern_child.setStyle("-fx-background-color: #E9E9E9");
    }

    @FXML
    private void onPress_child_2() {
        intern_child.setStyle("-fx-background-color: #A6A6A6;");
        hideCircleIndicators();
        circle_2.setVisible(true);
        intern_page.toFront();
    }

    @FXML
    private void onHover_child_3() {
        others_child.setStyle("-fx-background-color: white");
    }

    @FXML
    private void onHoverExit_child_3() {
        others_child.setStyle("-fx-background-color: #E9E9E9");
    }

    @FXML
    private void onPress_child_3() {
        others_child.setStyle("-fx-background-color: #A6A6A6;");
        hideCircleIndicators();
        circle_3.setVisible(true);
        others_page.toFront();
    }

    @FXML
    private void onHover_child_5() {
        help_child.setStyle("-fx-background-color: white");
    }

    @FXML
    private void onHoverExit_child_5() {
        help_child.setStyle("-fx-background-color: #E9E9E9");
    }

    @FXML
    private void onPress_child_5() throws IOException, URISyntaxException {
        help_child.setStyle("-fx-background-color: #A6A6A6;");
        Desktop desk = Desktop.getDesktop();

        // now we enter our URL that we want to open in our
        // default browser
        desk.browse(new URI("https://nwizuemmanuel200.medium.com/ice-management-app-ice-ma-93f2436265d4"));
    }
    @FXML
    private void onHover_child_4() {
        report_child.setStyle("-fx-background-color: white");
    }

    @FXML
    private void onHoverExit_child_4() {
        report_child.setStyle("-fx-background-color: #E9E9E9");
    }

    @FXML
    private void onPress_child_4() throws IOException {
        try{
            PreparedStatement ps = DatabaseActions.connectToDB().prepareStatement(sqlRestrict);
            ps.execute();
            report_child.setStyle("-fx-background-color: #A6A6A6;");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/reports/reports.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.getIcons().add(new Image(Objects.requireNonNull(MainpageController.class.getResourceAsStream("/assets/Ice Logo.png"))));
            stage.setTitle("Reports");
            stage.setResizable(false);
            stage.show();
        }catch (SQLException e){
            DisplayError.showErrorAlert(GlobalVariables.privilegeMsg2);
        }
    }

    private void showFirstPage() {
        if(dashboard_page.isVisible()){
            circle_1.setVisible(true);
            dashboard_page.toFront();
        }else {
            dashboard_page.setVisible(false);
            dashboard_page.setManaged(false);
            dashboard_child.setVisible(false);
            dashboard_child.setManaged(false);
            intern_page.toFront();
            hideCircleIndicators();
            circle_2.setVisible(true);
        }
    }

    @FXML
    private void internSearchQuery() throws SQLException {
        DatabaseActions da = new DatabaseActions();
        da.internSearchQuery(internSearchBar.getText(), internTable);
    }

    @FXML
    private void othersSearchQuery() throws SQLException {
        if(tabPane.getSelectionModel().getSelectedItem().getText().equals(incomeTab.getText())){
            DatabaseActions.other2SearchQuery(otherSearchBar.getText(),other2TableView);
        }else if(tabPane.getSelectionModel().getSelectedItem().getText().equals(expenseTab.getText())){
            DatabaseActions da = new DatabaseActions();
            da.othersSearchQuery(otherSearchBar.getText(), othersTable);
        }
    }

    @FXML
    private void newDGV()throws IOException {
        try{
                PreparedStatement ps = DatabaseActions.connectToDB().prepareStatement(sqlRestrict);
                ps.execute();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/new_dgv/new_dgv.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Create new sheet or Data group value");
                stage.setScene(new Scene(loader.load()));
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.getIcons().add(new Image(Objects.requireNonNull(MainpageController.class.getResourceAsStream("/assets/Ice Logo.png"))));
                stage.setResizable(false);
                stage.showAndWait();
            try {
                refresh();
                FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/mainpage/mainpage.fxml"));
                Parent parent = loader1.load();
                root.getScene().setRoot(parent);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }catch (SQLException ex){
            DisplayError.showErrorAlert(GlobalVariables.privilegeMsg2);
        }
    }

    @FXML
    private void viewDGV() throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/DGV/dgv.fxml"));
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.getIcons().add(new Image(Objects.requireNonNull(MainpageController.class.getResourceAsStream("/assets/Ice Logo.png"))));
        stage.setScene(new Scene(loader.load()));
        stage.showAndWait();
        refresh();
        FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/mainpage/mainpage.fxml"));
        Parent parent = loader1.load();
        root.getScene().setRoot(parent);
    }

    @FXML
    private void signOut() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Are you sure to sign out?");
        alert.setHeaderText(null);
        alert.setTitle("Confirm");
        ButtonType yesBtn = new ButtonType("Yes");
        ButtonType noBtn = new ButtonType("No");
        alert.getButtonTypes().setAll(yesBtn,noBtn);
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == yesBtn){
            preferences.remove(PrefKeys.userKey);
            preferences.remove(PrefKeys.passKey);
            root.getScene().getWindow().hide();
            root.getScene().getWindow().hide();
            GlobalVariables.isAdmin = false;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/iceapp/main.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();
        }else {
            alert.close();
        }
    }

    private void checkDGV(){
        String sql = "select count(dgv) from "+DatabaseActions.tableNameForDGV+"";
        int num = 0;
        try {
            Statement statement = DatabaseActions.connectToDB().createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                num = resultSet.getInt("count(dgv)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if((preferences.get(PrefKeys.dgvKey,"").isEmpty() || num == 0) && GlobalVariables.isAdmin){
            intern_child.setVisible(false);
            others_child.setVisible(false);
            intern_child.setManaged(false);
            others_child.setManaged(false);
            intern_page.setVisible(false);
            intern_page.setManaged(false);
            others_page.setVisible(false);
            others_page.setManaged(false);
        }
    }

    private void adminChecker2(){
        newBtn.setVisible(false);
        newBtn.setManaged(false);
        String sql = "alter table "+DatabaseActions.dummyTable+" modify dummy_text text not null";
        try {
            PreparedStatement ps = DatabaseActions.connectToDB().prepareStatement(sql);
            ps.execute();
            newBtn.setVisible(true);
            newBtn.setManaged(true);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        DatabaseActions da = new DatabaseActions();
        da.createInitTables();

        adminChecker2();
        DatabaseActions.adminChecker();
        restrictions();
        showFirstPage();
        displayOther2Data();
        showDataOnOthersTable();
        showDataOnInternTable();
        loadCashFlowPie();
        checkDGV();
    }

    private void displayOther2Data(){
        try {
            DatabaseActions.generateOther2Data(other2TableView);
            customerCol.setCellValueFactory(new PropertyValueFactory<>("customer"));
            customerCol.setCellFactory(TextFieldTableCell.forTableColumn());

            addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
            addressCol.setCellFactory(TextFieldTableCell.forTableColumn());

            telephoneCol.setCellValueFactory(new PropertyValueFactory<>("telephone"));
            telephoneCol.setCellFactory(TextFieldTableCell.forTableColumn());

            descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
            descriptionCol.setCellFactory(TextFieldTableCell.forTableColumn());

            amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
            amountCol.setCellFactory(TextFieldTableCell.forTableColumn());

            dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
            dueDateCol.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
            vatCol.setCellValueFactory(new PropertyValueFactory<>("vat"));
            quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showDataOnOthersTable() {
        try {
            database.DatabaseActions da = new DatabaseActions();
            da.loadOthersData(othersTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        othersItemCol.setCellValueFactory(new PropertyValueFactory<>("Item"));
        othersAmountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
        othersTransDateCol.setCellValueFactory(new PropertyValueFactory<>("transDate"));
        othersActionCol.setCellFactory(othersCellFactory);
    }

    private void showDataOnInternTable() {
        try {
            database.DatabaseActions da = new DatabaseActions();
            da.loadInternsData(internTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        internNameCol.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        internNameCol.setCellFactory(TextFieldTableCell.forTableColumn());

        internTelephoneCol.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        internTelephoneCol.setCellFactory(TextFieldTableCell.forTableColumn());

        internCourseCol.setCellValueFactory(new PropertyValueFactory<>("course"));
        internCourseCol.setCellFactory(TextFieldTableCell.forTableColumn());

        internAmountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
        internAmountCol.setCellFactory(TextFieldTableCell.forTableColumn());

        internRegDateCol.setCellValueFactory(new PropertyValueFactory<>("regDate"));
    }

    @FXML
    private void nameOnEditCommit(CellEditEvent<InternModel, String> event) {
        Object newValue = event.getNewValue();
        InternModel im = internTable.getSelectionModel().getSelectedItem();
        DatabaseActions da = new DatabaseActions();
        try {
            if (newValue.toString().matches("[a-zA-Z ]+")) {
                if(newValue.toString().equals("delete")){
                    String sql = "delete from "+DatabaseActions.tableNameForIntern+" where id="+im.getId()+" ";
                    PreparedStatement ps = DatabaseActions.connectToDB().prepareStatement(sql);
                    ps.execute();
                }else {
                    da.internUpdater("fullname", "id", newValue.toString(), String.valueOf(im.getId()));
                }
            } else {
                DisplayError.showErrorAlert("Only letters and white spaces");
            }
        } catch (SQLException e) {
            DisplayError.showErrorAlert(GlobalVariables.privilegeMsg);
        }
        showDataOnInternTable();
    }

    @FXML
    private void amountOnEditCommit(CellEditEvent<InternModel, String> event) throws SQLException {
        Object newValue = event.getNewValue();
        InternModel im = internTable.getSelectionModel().getSelectedItem();
        DatabaseActions da = new DatabaseActions();
        String query = "select amount from " +DatabaseActions.tableNameForIntern+ " where id=" + im.getId() + "";
        Statement statement = DatabaseActions.connectToDB().createStatement();
        ResultSet rs = statement.executeQuery(query);
        double initialAmount = 0;
        while (rs.next()) {
            initialAmount = Double.parseDouble(rs.getString("amount"));
        }
        if (newValue.toString().matches("[0-9]+")) {
            try {
                double result = initialAmount + Double.parseDouble(newValue.toString());
                da.internUpdater("amount", "id", String.valueOf(result), String.valueOf(im.getId()));
            } catch (NumberFormatException | SQLException e) {
                DisplayError.showErrorAlert(GlobalVariables.privilegeMsg);
            }
        } else if (newValue.toString().contains("clear")) {
            try {
                da.internUpdater("amount", "id", "0", String.valueOf(im.getId()));
            } catch (SQLException ex) {
                DisplayError.showErrorAlert(GlobalVariables.privilegeMsg);
            }
        } else {
            DisplayError.showErrorAlert("Invalid parameter.");
        }
        showDataOnInternTable();
    }

    @FXML
    private void telephoneOnEditCommit(CellEditEvent<InternModel, String> event) {
        Object newValue = event.getNewValue();
        InternModel im = internTable.getSelectionModel().getSelectedItem();
        DatabaseActions da = new DatabaseActions();
        try {
            if (newValue.toString().length() == 11) {
                da.internUpdater("telephone", "id", newValue.toString(), String.valueOf(im.getId()));
            } else {
                DisplayError.showErrorAlert("Characters must be <= 11");
            }
        } catch (SQLException ex) {
            DisplayError.showErrorAlert(GlobalVariables.privilegeMsg);
        }
        showDataOnInternTable();
    }

    @FXML
    private void other2_customerEditCommit(CellEditEvent<Other2Model, String> event) throws SQLException {
        Object newValue = event.getNewValue();
        Other2Model other2Model = other2TableView.getSelectionModel().getSelectedItem();
        if(newValue.toString().equals("delete")){
            String sql = "delete from "+DatabaseActions.tableNameForOthers2+" where id="+other2Model.getId()+" ";
            PreparedStatement ps = DatabaseActions.connectToDB().prepareStatement(sql);
            ps.execute();
        }else {
            String query = "update "+DatabaseActions.tableNameForOthers2+" set customer='"+newValue+"' where id="+other2Model.getId()+"";
            PreparedStatement ps = DatabaseActions.connectToDB().prepareStatement(query);
            ps.executeUpdate();
        }
        displayOther2Data();
    }

    @FXML
    private void other2_addressEditCommit(CellEditEvent<Other2Model, String> event) throws SQLException {
        Object newValue = event.getNewValue();
        Other2Model other2Model = other2TableView.getSelectionModel().getSelectedItem();
        String query = "update "+DatabaseActions.tableNameForOthers2+" set address='"+newValue+"' where id="+other2Model.getId()+"";
        PreparedStatement ps = DatabaseActions.connectToDB().prepareStatement(query);
        ps.executeUpdate();
        displayOther2Data();
    }

    @FXML
    private void other2_telephoneEditCommit(CellEditEvent<Other2Model, String> event) throws SQLException {
        Object newValue = event.getNewValue();
        Other2Model other2Model = other2TableView.getSelectionModel().getSelectedItem();
        if(newValue.toString().matches("[0-9]{11}")){
            String query = "update "+DatabaseActions.tableNameForOthers2+" set telephone='"+newValue+"' where id="+other2Model.getId()+"";
            PreparedStatement ps = DatabaseActions.connectToDB().prepareStatement(query);
            ps.executeUpdate();
        }else {
            DisplayError.showErrorAlert("Telephone number format invalid");
        }
        displayOther2Data();
    }

    @FXML
    private void other2_descEditCommit(CellEditEvent<Other2Model, String> event) throws SQLException {
        Object newValue = event.getNewValue();
        Other2Model other2Model = other2TableView.getSelectionModel().getSelectedItem();
        String query = "update "+DatabaseActions.tableNameForOthers2+" set description='"+newValue+"' where id="+other2Model.getId()+"";
        PreparedStatement ps = DatabaseActions.connectToDB().prepareStatement(query);
        ps.executeUpdate();
        displayOther2Data();
    }

    @FXML
    private void other2_quantityEditCommit(CellEditEvent<Other2Model, String> event) throws SQLException {
        Object newValue = event.getNewValue();
        Other2Model other2Model = other2TableView.getSelectionModel().getSelectedItem();
        String query = "update "+DatabaseActions.tableNameForOthers2+" set description='"+newValue+"' where id="+other2Model.getId()+"";
        PreparedStatement ps = DatabaseActions.connectToDB().prepareStatement(query);
        ps.executeUpdate();
        displayOther2Data();
    }

    @FXML
    private void other2_amountOnEditCommit(CellEditEvent<Other2Model, String> event) throws SQLException {
        Object newValue = event.getNewValue();
        Other2Model im = other2TableView.getSelectionModel().getSelectedItem();
        String query = "select amount from " +DatabaseActions.tableNameForOthers2+ " where id=" + im.getId() + "";
        Statement statement = DatabaseActions.connectToDB().createStatement();
        ResultSet rs = statement.executeQuery(query);
        int initialAmount = 0;
        while (rs.next()) {
            initialAmount = Integer.parseInt(rs.getString("amount"));
        }
        if (newValue.toString().matches("[0-9]+")) {
            try {
                int result = initialAmount + Integer.parseInt(newValue.toString());
                String query2 = "update "+DatabaseActions.tableNameForOthers2+" set amount='"+ result +"' where id="+im.getId()+"";
                PreparedStatement ps = DatabaseActions.connectToDB().prepareStatement(query2);
                ps.executeUpdate();
            } catch (NumberFormatException | SQLException e) {
                DisplayError.showErrorAlert(GlobalVariables.privilegeMsg);
            }
        } else if (newValue.toString().contains("clear")) {
            try {
                String query3 = "update "+DatabaseActions.tableNameForOthers2+" set amount='0' where id="+im.getId()+"";
                PreparedStatement ps1 = DatabaseActions.connectToDB().prepareStatement(query3);
                ps1.executeUpdate();
            } catch (SQLException ex) {
                DisplayError.showErrorAlert(GlobalVariables.privilegeMsg);
            }
        } else {
            DisplayError.showErrorAlert("Invalid parameter.");
        }
        displayOther2Data();
    }

    private void showTableScene() throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/table_scenes/table_scene.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(loader.load());
        stage.initStyle(StageStyle.UNIFIED);
        stage.setScene(scene);
        stage.getIcons().add(new Image(Objects.requireNonNull(MainpageController.class.getResourceAsStream("/assets/Ice Logo.png"))));
        stage.showAndWait();
        showDataOnInternTable();
    }

    @FXML
    private void courseOnEditCommit(CellEditEvent<InternModel, String> event) throws IOException {
        Object newValue = event.getNewValue();
        Prefs prefClass = new Prefs();
        Preferences pref = Preferences.userRoot().node(prefClass.getClass().getName());
        if (newValue.equals("show")) {
            InternModel im = internTable.getSelectionModel().getSelectedItem();
            DatabaseActions da = new DatabaseActions();
            try {
                showTableScene();
                da.internUpdater("course", "id", pref.get(PrefKeys.tableKey, ""), String.valueOf(im.getId()));
            } catch (SQLException ex) {
                DisplayError.showErrorAlert(GlobalVariables.privilegeMsg);
            }
        } else {
            DisplayError.showErrorAlert("Invalid input");
        }
        showDataOnInternTable();
    }

    Callback<TableColumn<InternModel, String>, TableCell<InternModel, String>> othersCellFactory = (param) -> {

        // setting a cell to accept button

        return new TableCell<InternModel, String>() {

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
                        OtherModel im = othersTable.getItems().get(getIndex());
                        String query = "delete from " + DatabaseActions.tableNameForOthers + " where id=" + im.getId() + "";
                        try {
                            PreparedStatement ps = DatabaseActions.connectToDB().prepareStatement(query);
                            ps.execute();
                            showDataOnOthersTable();
                        } catch (SQLException ex) {
                            DisplayError.showErrorAlert(GlobalVariables.privilegeMsg);
                        }
                    });
                    setGraphic(button);
                }
                setText(null);
            }
        };
    };

    @FXML
    private void courseSettingAction() throws IOException {
        try{
            PreparedStatement ps = DatabaseActions.connectToDB().prepareStatement(sqlRestrict);
            ps.execute();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/course_setting/course_setting.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(loader.load());
            stage.setTitle("Course setting");
            stage.getIcons().add(new Image(Objects.requireNonNull(MainpageController.class.getResourceAsStream("/assets/Ice Logo.png"))));
            stage.setScene(scene);
            stage.show();
        }catch (SQLException e){
            DisplayError.showErrorAlert(GlobalVariables.privilegeMsg2);
        }
    }

    private void loadCashFlowPie() {
        try {
            Statement statement = DatabaseActions.connectToDB().createStatement();
            String commandForIncome = "SELECT sum(amount) FROM " + DatabaseActions.tableNameForOthers2 +" "
                    +"where data_group_value ='"+preferences.get(PrefKeys.dgvKey,"")+"' ";
            String commandForExpense = "SELECT sum(amount) FROM " + DatabaseActions.tableNameForOthers + " " +
                    "where data_group_value ='"+preferences.get(PrefKeys.dgvKey,"")+"' ";
            String commandForInternFees= "select sum(amount) from "+DatabaseActions.tableNameForIntern+" where " +
                    "data_group_value ='"+preferences.get(PrefKeys.dgvKey,"")+"' ";

            int incomeCount = 0;
            int expenseCount = 0;
            int internFeeCount = 0;
            int totalIncome;
            int balance;

            ResultSet rsIncome;
            ResultSet rsExpense;
            ResultSet rsInternFee;

            rsIncome = statement.executeQuery(commandForIncome);
            while (rsIncome.next()) {
                incomeCount = rsIncome.getInt("sum(amount)");
            }
            rsExpense = statement.executeQuery(commandForExpense);
            while (rsExpense.next()) {
                expenseCount = rsExpense.getInt("sum(amount)");
            }
            rsInternFee = statement.executeQuery(commandForInternFees);
            while (rsInternFee.next()){
                internFeeCount = rsInternFee.getInt("sum(amount)");
            }
            totalIncome = incomeCount + internFeeCount;
            if(totalIncome > expenseCount){
                balance = totalIncome - expenseCount;
            }else {
                balance = expenseCount - totalIncome;
            }
            balanceText.setText("Balance: ₦" + numberFormat.format(balance));
            incomeText.setText("Income: ₦" + numberFormat.format(totalIncome));
            expenseText.setText("Expense: ₦" + numberFormat.format(expenseCount));
            if(totalIncome <=0 || expenseCount <=0){
                dashboardPane.toFront();
            }else {
                dashboardPane.toBack();
            }

            ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList(
                    new PieChart.Data("Income", totalIncome), new PieChart.Data("Expense", expenseCount));
            cashFlowPie.setData(pieData);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void refresh() throws SQLException {
        showDataOnInternTable();
        showDataOnOthersTable();
        displayOther2Data();
        loadCashFlowPie();
    }

    private void restrictions(){
        if(!GlobalVariables.isAdmin){
            report_child.setVisible(false);
            report_child.setManaged(false);
            newBtn.setVisible(false);
            newBtn.setManaged(false);
            internTable.setEditable(false);
            othersTable.setEditable(false);
            other2TableView.setEditable(false);
            othersActionCol.setVisible(false);
            courseSettingBtn.setVisible(false);
            courseSettingBtn.setManaged(false);
            dashboard_page.setVisible(false);
            dashboard_page.setManaged(false);
            dashboard_child.setVisible(false);
            dashboard_child.setManaged(false);
        }
    }
}
