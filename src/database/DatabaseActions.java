/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import alerts.DisplayError;
import com.jfoenix.controls.JFXComboBox;
import constants.GlobalVariables;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import keys.PrefKeys;
import models.CoursePriceModel;
import models.DGVModel;
import models.InternModel;
import models.OtherModel;
import preferences.Prefs;
import java.io.IOException;
import java.sql.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.prefs.Preferences;

public class DatabaseActions {

    static Prefs prefClass = new Prefs();
    static Preferences pref = Preferences.userRoot().node(prefClass.getClass().getName());
    static final String ipAddress = pref.get(PrefKeys.ipKey, "");
    static final String databaseName = "icehub";
    static final String DB_URL = "jdbc:mysql://" + ipAddress + ":3306/"  +databaseName+ "";
    static Preferences prefs = Preferences.userRoot().node(prefClass.getClass().getName());
    public static String tableNameForIntern = "intern_data";
    public static String tableNameForOthers = "other_data";
    public static String tableNameForCoursePrice ="course_price";
    public static String tableNameForDGV = "dgv"; // dgv = data value group

    static public Connection connectToDB() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DB_URL, pref.get(PrefKeys.userKey, ""), pref.get(PrefKeys.passKey, ""));
        } catch (SQLException ex) {
            DisplayError.showErrorAlert("Error, can't connect");
        }
        return connection;
    }

    public void loginAction(String username, String password, Pane pane) throws IOException {
        prefClass.writerLoginInfo(username, password);
        loadPage(pane);
    }

    private void loadPage(Pane pane) throws IOException {
        try {
            DriverManager.getConnection(DB_URL, pref.get(PrefKeys.userKey, ""), pref.get(PrefKeys.passKey, ""));
            FXMLLoader lLoader = new FXMLLoader(getClass().getResource("/mainpage/mainpage.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(lLoader.load());
            stage.setTitle("Icehub");
            stage.setScene(scene);
            stage.setOnCloseRequest(e -> {
                prefs.remove(PrefKeys.userKey);
                prefs.remove(PrefKeys.passKey);
            });
            stage.setMaximized(true);
            stage.show();
            pane.getScene().getWindow().hide();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            DisplayError.showErrorAlert("Can't connect to database.\nInvalid username or password");
        }
    }

    public void registerNewIntern(String fullname, String telephone, String course, String amount, String regdate) throws SQLException {
        final String registerInternQuery = "insert into "+tableNameForIntern+"(fullname,telephone,course,amount,regdate,data_group_value)values(?,?,?,?,?,?)";
        PreparedStatement ps = connectToDB().prepareStatement(registerInternQuery);
        ps.setString(1, fullname);
        ps.setString(2, telephone);
        ps.setString(3, course);
        ps.setString(4, amount);
        ps.setDate(5, Date.valueOf(regdate));
        ps.setString(6,pref.get(PrefKeys.dgvKey, ""));
        ps.executeUpdate();
    }

    public void addRecord(String item, String amount, String cashflow, String transacDate) throws SQLException {
        final String addRecordQuery = "insert into "+tableNameForOthers+"(item,amount,cashflow,transac_date,data_group_value)values(?,?,?,?,?)";
        PreparedStatement ps = connectToDB().prepareStatement(addRecordQuery);
        ps.setString(1, item);
        ps.setString(2, amount);
        ps.setString(3, cashflow);
        ps.setDate(4, Date.valueOf(transacDate));
        ps.setString(5,pref.get(PrefKeys.dgvKey,""));
        ps.executeUpdate();
    }

    static public void recordGroupValue() throws SQLException {
        final String sql = "insert into "+tableNameForDGV+"(dgv, creation_date)value(?,?)";
        PreparedStatement ps = connectToDB().prepareStatement(sql);
        ps.setString(1, pref.get(PrefKeys.dgvKey,""));
        ps.setString(2, GlobalVariables.dgvCreationDate);
        ps.execute();
    }

    public void loadOthersData(TableView<OtherModel> tableView) throws SQLException {
        ObservableList<OtherModel> data = FXCollections.observableArrayList();
        final String showOthersDataQuery = "select * from "+tableNameForOthers+" where data_group_value = '"+pref.get(PrefKeys.dgvKey,"")+"' ";
        Statement statement = connectToDB().createStatement();
        ResultSet rs = statement.executeQuery(showOthersDataQuery);
        NumberFormat nf = NumberFormat.getInstance();
        while (rs.next()) {
            data.add(new OtherModel(rs.getString(1), nf.format(Double.parseDouble(rs.getString(2))), rs.getString(3), rs.getString(4)));
        }
        tableView.setItems(data);
    }

    public void loadInternsData(TableView<InternModel> tableView) throws SQLException {
        ObservableList<InternModel> data = FXCollections.observableArrayList();
        final String showInternDataQuery = "select * from "+tableNameForIntern+" where data_group_value = '"+pref.get(PrefKeys.dgvKey,"")+"' ";
        Statement statement = connectToDB().createStatement();
        ResultSet rs = statement.executeQuery(showInternDataQuery);
        NumberFormat nf = NumberFormat.getInstance();
        while (rs.next()) {
            data.add(new InternModel(rs.getString("fullname"), rs.getDate("regdate"), rs.getString("course"), rs.getString("telephone"), nf.format(Double.parseDouble(rs.getString("amount")))));
        }
        tableView.setItems(data);
    }

    static public void loadDGV(TableView<DGVModel> tableView) throws SQLException {
        ObservableList<DGVModel> data = FXCollections.observableArrayList();
        final String sql = "select * from "+tableNameForDGV+"";
        Statement statement = connectToDB().createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()){
            data.add(new DGVModel(resultSet.getString("dgv"),resultSet.getString("creation_date")));
        }
        tableView.setItems(data);
    }

    static public void DGVSearchQuery(String month, String year, TableView<DGVModel> tableView) throws SQLException {
        String sql = "select * from "+tableNameForDGV+" where creation_date like '%"+month+"%' and creation_date like '%"+year+"%' ";
        tableView.getItems().clear();
        ObservableList<DGVModel> data = FXCollections.observableArrayList();
        Statement statement = connectToDB().createStatement();
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()){
            data.add(new DGVModel(rs.getString("dgv"), rs.getString("creation_date")));
        }
        tableView.setItems(data);
    }

    public void internUpdater(String colValue, String colValue1, String value, String value1) throws SQLException {
        String query = "update "+tableNameForIntern+" set " + colValue + "='" + value + "' where " + colValue1 + "='" + value1 + "'";
        PreparedStatement ps = connectToDB().prepareStatement(query);
        ps.executeUpdate();
    }

    public void internSearchQuery(String txt, TableView<InternModel> tableView) throws SQLException {
        tableView.getItems().clear();
        String query = "select * from "+tableNameForIntern+" where fullname like '%" + txt + "%' or course like '%" + txt + "%' or telephone like '%" + txt + "%' or regdate like '%"+txt+"%'";
        ObservableList<InternModel> data = FXCollections.observableArrayList();
        Statement statement = connectToDB().createStatement();
        ResultSet rs = statement.executeQuery(query);
        NumberFormat nf = NumberFormat.getInstance();
        while (rs.next()) {
            data.add(new InternModel(rs.getString("fullname"), rs.getDate("regdate"), rs.getString("course"), rs.getString("telephone"), nf.format(Double.parseDouble(rs.getString("amount")))));
        }
        tableView.setItems(data);
    }

    public void othersSearchQuery(String txt, TableView<OtherModel> tableView) throws SQLException {
        tableView.getItems().clear();
        String query = "select * from "+tableNameForOthers+" where item like '%" + txt + "%' ";
        ObservableList<OtherModel> data = FXCollections.observableArrayList();
        Statement statement = connectToDB().createStatement();
        ResultSet rs = statement.executeQuery(query);
        NumberFormat nf = NumberFormat.getInstance();
        while (rs.next()) {
            data.add(new OtherModel(rs.getString(1), nf.format(Double.parseDouble(rs.getString(2))), rs.getString(3), rs.getString(4)));
        }
        tableView.setItems(data);
    }

    public void otherCashflowSorter(String txt, TableView<OtherModel> tableView) throws SQLException {
        tableView.getItems().clear();
        String query = "select * from "+tableNameForOthers+" where cashflow='" + txt + "' ";
        ObservableList<OtherModel> data = FXCollections.observableArrayList();
        Statement statement = connectToDB().createStatement();
        ResultSet rs = statement.executeQuery(query);
        NumberFormat nf = NumberFormat.getInstance();
        while (rs.next()) {
            data.add(new OtherModel(rs.getString(1), nf.format(Double.parseDouble(rs.getString(2))), rs.getString(3), rs.getString(4)));
        }
        tableView.setItems(data);
    }

    public void loadCoursePriceData(TableView<CoursePriceModel> tableView) throws SQLException {
        String query = "select * from "+ tableNameForCoursePrice +"";
        ObservableList<CoursePriceModel> data = FXCollections.observableArrayList();
        Statement statement = connectToDB().createStatement();
        ResultSet rs = statement.executeQuery(query);
        NumberFormat nf = NumberFormat.getInstance();
        while (rs.next()) {
            data.add(new CoursePriceModel(rs.getString(1), nf.format(Integer.parseInt(rs.getString(2)))));
        }
        tableView.setItems(data);
    }

    public void coursePriceInput(String course, String price) throws SQLException {
        String query = "insert into "+ tableNameForCoursePrice +"(course, price)value(?,?)";
        PreparedStatement ps = connectToDB().prepareStatement(query);
        ps.setString(1, course);
        ps.setString(2, price);
        ps.execute();
    }

    public void loadcourses(JFXComboBox<String> comboBox) throws SQLException {
        ArrayList<String> courses = new ArrayList<>();
        String query = "select course from "+ tableNameForCoursePrice +"";
        Statement statement = connectToDB().createStatement();
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
            courses.add(rs.getString(1));
        }
        ObservableList<String> data = FXCollections.observableArrayList(courses);
        comboBox.getItems().addAll(data);
    }
    
    public void createInitTables() throws SQLException{
        String query2 = "create table if not exists "+tableNameForIntern+"(id int auto_increment not null, fullname text not null, telephone varchar(11) not null, course text not null, amount text not null, regdate date not null" +
                ",data_group_value text not null, primary key (id))";
        String query3 = "create table if not exists "+tableNameForOthers+"(item text not null, amount text not null, cashflow text not null, transac_date date not null,data_group_value text not null)";
        String query4 = "create table if not exists "+ tableNameForCoursePrice +"(course text, price text, unique key(course))";
        String query5 = "create table if not exists "+tableNameForDGV+" (dgv text not null, creation_date text not null, unique key(dgv))";
        PreparedStatement ps2 = connectToDB().prepareStatement(query2);
        PreparedStatement ps3 = connectToDB().prepareStatement(query3);
        PreparedStatement ps4 = connectToDB().prepareStatement(query4);
        PreparedStatement ps5 = connectToDB().prepareStatement(query5);
        ps2.execute();
        ps3.execute();
        ps4.execute();
        ps5.execute();
   }
}
