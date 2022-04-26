/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import add_record.Add_recordController;
import alerts.DisplayError;
import alerts.SuccessAlert;
import com.jfoenix.controls.JFXComboBox;
import constants.GlobalVariables;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import keys.PrefKeys;
import models.*;
import preferences.Prefs;
import java.io.IOException;
import java.sql.*;
import java.text.NumberFormat;

import java.util.ArrayList;
import java.util.Objects;
import java.util.prefs.Preferences;

public class DatabaseActions {

    static Prefs prefClass = new Prefs();
    static Preferences pref = Preferences.userRoot().node(prefClass.getClass().getName());
    static final String ipAddress = pref.get(PrefKeys.ipKey, "");
    static final String databaseName = "icehub";
    public static final String DB_URL = "jdbc:mysql://" + ipAddress + ":3306/"  +databaseName+ "";
    public static String tableNameForIntern = "intern_data";
    public static String tableNameForOthers = "other_data";
    public static String tableNameForCoursePrice ="course_price";
    public static String tableNameForOthers2 = "other_data2";
    public static String tableNameForDGV = "dgv"; // dgv = data value group
    public static String dummyTable = "dummy_table";
    public static String printerTable = "printer_table";

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
            stage.setTitle("ICE MS");
            stage.setScene(scene);
            stage.setOnCloseRequest(e-> pref.getInt(PrefKeys.mainPageStateKey,0));
            stage.getIcons().add(new Image(Objects.requireNonNull(DatabaseActions.class.getResourceAsStream("/assets/Ice Logo.png"))));
            stage.setMaximized(true);
            stage.show();
            pane.getScene().getWindow().hide();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            DisplayError.showErrorAlert("Can't connect to database.\nInvalid username or password");
        }
    }

    public void registerNewIntern(String fullname, String telephone, String course, String amount, String regdate) throws SQLException {
        if(telephone.matches("[0-9]{11}") || amount.matches("[0-9]+")){
            final String registerInternQuery = "insert into "+tableNameForIntern+"(fullname,telephone,course,amount,regdate,data_group_value)values(?,?,?,?,?,?)";
            PreparedStatement ps = connectToDB().prepareStatement(registerInternQuery);
            ps.setString(1, fullname);
            ps.setString(2, telephone);
            ps.setString(3, course);
            ps.setString(4, amount);
            ps.setDate(5, Date.valueOf(regdate));
            ps.setString(6,pref.get(PrefKeys.dgvKey, ""));
            ps.executeUpdate();
            SuccessAlert.showAlert("Success");
        }else {
            DisplayError.showErrorAlert("Parameter invalid in telephone or amount");
        }
    }

    public void addRecord(String item, String amount, String transacDate) throws SQLException {
        final String addRecordQuery = "insert into "+tableNameForOthers+"(item,amount,transac_date,data_group_value)values(?,?,?,?)";
        PreparedStatement ps = connectToDB().prepareStatement(addRecordQuery);
        ps.setString(1, item);
        ps.setString(2, amount);
        ps.setDate(3, Date.valueOf(transacDate));
        ps.setString(4,pref.get(PrefKeys.dgvKey,""));
        ps.executeUpdate();
    }
    
    public static void addOther2Data(String customer,String address,String telephone,String desc,String amount,String date,boolean isIncluded,String dueDate,
                                     int quantity) throws SQLException {
        NumberFormat nf = NumberFormat.getInstance();
        Double total_amount = Double.parseDouble(amount) * quantity;
        if(isIncluded){
            String sql = "insert into "+tableNameForOthers2+"(customer,address,telephone,description,amount,tran_date,data_group_value,vat,due_date,quantity)values(?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = connectToDB().prepareStatement(sql);
            ps.setString(1,customer);
            ps.setString(2,address);
            ps.setString(3,telephone);
            ps.setString(4,desc);
            ps.setString(5,nf.format(total_amount));
            ps.setDate(6, Date.valueOf(date));
            ps.setString(7,pref.get(PrefKeys.dgvKey,""));
            double totalVat = Double.parseDouble(Add_recordController.pubVat) * quantity;
            ps.setString(8, nf.format(totalVat));
            ps.setString(9,dueDate);
            ps.setInt(10,quantity);
            ps.executeUpdate();

            String sql1 = "insert into "+printerTable+"(customer,address,telephone,description,amount,tran_date,data_group_value,vat,due_date,quantity)values(?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps1 = connectToDB().prepareStatement(sql1);
            ps1.setString(1,customer);
            ps1.setString(2,address);
            ps1.setString(3,telephone);
            ps1.setString(4,desc);
            ps1.setString(5,String.valueOf(total_amount));
            ps1.setDate(6, Date.valueOf(date));
            ps1.setString(7,pref.get(PrefKeys.dgvKey,""));
            double totalVat1 = Double.parseDouble(Add_recordController.pubVat) * quantity;
            ps1.setString(8, nf.format(totalVat1));
            ps1.setString(9,dueDate);
            ps1.setInt(10,quantity);
            ps1.executeUpdate();
        }else {
            String sql = "insert into "+tableNameForOthers2+"(customer,address,telephone,description,amount,tran_date,data_group_value,due_date,quantity)values(?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = connectToDB().prepareStatement(sql);
            ps.setString(1,customer);
            ps.setString(2,address);
            ps.setString(3,telephone);
            ps.setString(4,desc);
            ps.setString(5,nf.format(total_amount));
            ps.setDate(6, Date.valueOf(date));
            ps.setString(7,pref.get(PrefKeys.dgvKey,""));
            ps.setString(8,dueDate);
            ps.setInt(9,quantity);
            ps.executeUpdate();

            String sql1 = "insert into "+printerTable+"(customer,address,telephone,description,amount,tran_date,data_group_value,due_date,quantity)values(?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps1 = connectToDB().prepareStatement(sql1);
            ps1.setString(1,customer);
            ps1.setString(2,address);
            ps1.setString(3,telephone);
            ps1.setString(4,desc);
            ps1.setString(5,nf.format(total_amount));
            ps1.setDate(6, Date.valueOf(date));
            ps1.setString(7,pref.get(PrefKeys.dgvKey,""));
            ps1.setString(8,dueDate);
            ps1.setInt(9,quantity);
            ps1.executeUpdate();
        }
        printerTableExtra();
    }
    static public void printerTableExtra() throws SQLException {
        String total_amount = "";
        String total_vat = "";
        String sql1 = "select sum(vat) from "+DatabaseActions.printerTable+"";
        String sql2 = "select sum(amount) from "+DatabaseActions.printerTable+"";
        Statement statement1 = DatabaseActions.connectToDB().createStatement();
        Statement statement2 = DatabaseActions.connectToDB().createStatement();
        ResultSet resultSet1 = statement1.executeQuery(sql1);
        while(resultSet1.next()){
            total_vat=resultSet1.getString("vat");
        }
        resultSet1.close();
        ResultSet resultSet2 = statement2.executeQuery(sql2);
        while(resultSet2.next()){
            total_amount = resultSet2.getString("amount");
        }
        resultSet2.close();
        String query1 = "update "+printerTable+" set total_vat='"+total_vat+"',total_amount='"+total_amount+"' where 1";
        PreparedStatement ps = DatabaseActions.connectToDB().prepareStatement(query1);
        ps.execute();
    }

    public static void generateOther2Data(TableView<Other2Model> tableView) throws SQLException {
        ObservableList<Other2Model> data = FXCollections.observableArrayList();
        String sqlQuery = "select * from "+tableNameForOthers2+"";
        Statement statement = connectToDB().createStatement();
        ResultSet resultSet = statement.executeQuery(sqlQuery);
        while (resultSet.next()){
            data.add(new Other2Model(resultSet.getInt("id"),resultSet.getString("customer"),resultSet.getString("address"),
                    resultSet.getString("telephone"),resultSet.getString("description"),
                    resultSet.getString("amount"),resultSet.getString("tran_date"),resultSet.getString("due_date"),resultSet.getString("vat"),
                    resultSet.getInt("quantity")));
        }
        tableView.setItems(data);
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
            data.add(new OtherModel(rs.getInt("id"),rs.getString("item"), nf.format(Double.parseDouble(rs.getString("amount"))), rs.getString("transac_date")));
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
            data.add(new InternModel(rs.getInt("id"),rs.getString("fullname"), rs.getDate("regdate"), rs.getString("course"), rs.getString("telephone"), nf.format(Double.parseDouble(rs.getString("amount")))));
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
        String query = "update "+tableNameForIntern+" set " + colValue + "='" + value + "' where " + colValue1 + "=" + value1 + "";
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
            data.add(new InternModel(rs.getInt("id"),rs.getString("fullname"), rs.getDate("regdate"), rs.getString("course"), rs.getString("telephone"), nf.format(Double.parseDouble(rs.getString("amount")))));
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
            data.add(new OtherModel(rs.getInt("id"),rs.getString("item"), nf.format(Double.parseDouble(rs.getString("amount"))), rs.getString("transac_date")));
        }
        tableView.setItems(data);
    }

    public static void other2SearchQuery(String txt, TableView<Other2Model> tableView) throws SQLException {
        tableView.getItems().clear();
        String sql = "select * from "+tableNameForOthers2+" where customer like '%"+txt+"%'";
        ObservableList<Other2Model> data = FXCollections.observableArrayList();
        Statement statement = connectToDB().createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()){
            data.add(new Other2Model(resultSet.getInt("id"),resultSet.getString("customer"),resultSet.getString("address"),
                    resultSet.getString("telephone"),resultSet.getString("description"),
                    resultSet.getString("amount"),resultSet.getString("tran_date"),
                    resultSet.getString("due_date"),resultSet.getString("vat"),
                    resultSet.getInt("quantity")));
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

    public void loadCourses(JFXComboBox<String> comboBox) throws SQLException {
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
    
    public void createInitTables() {
        String query2 = "create table if not exists "+tableNameForIntern+"(id int auto_increment not null, fullname text not null, telephone varchar(11) not null, course text not null, amount text not null, regdate date not null" +
                ",data_group_value text not null, primary key (id))";
        String query3 = "create table if not exists "+tableNameForOthers+"(id int auto_increment,item text not null, amount text not null, transac_date date not null,data_group_value text not null, primary key(id))";
        String query4 = "create table if not exists "+ tableNameForCoursePrice +"(course text, price text, unique key(course))";
        String query5 = "create table if not exists "+tableNameForDGV+" (dgv text not null, creation_date text not null, unique key(dgv))";
        String query6 = "create table if not exists "+dummyTable+" (dummy_text text null)";
        String query7 = "create table if not exists "+tableNameForOthers2+" (" +
                "id int auto_increment,"+
                "customer text not null," +
                "address text not null," +
                "telephone varchar(11) not null," +
                "description text not null," +
                "amount text not null," +
                "quantity int not null,"+
                "tran_date date not null," +
                "vat text null,"+
                "due_date text null,"+
                "data_group_value text not null," +
                "primary key(id))";
        String query8 = "create table if not exists "+printerTable+" (" +
                "id int auto_increment,"+
                "customer text not null," +
                "address text not null," +
                "telephone varchar(11) not null," +
                "description text not null," +
                "amount text not null," +
                "quantity int not null,"+
                "tran_date date not null," +
                "vat text null,"+
                "due_date text null,"+
                "total_vat text not null default '0',"+
                "total_amount text not null default '0',"+
                "data_group_value text not null," +
                "primary key(id))";
        try {
            PreparedStatement ps2 = connectToDB().prepareStatement(query2);
            PreparedStatement ps3 = connectToDB().prepareStatement(query3);
            PreparedStatement ps4 = connectToDB().prepareStatement(query4);
            PreparedStatement ps5 = connectToDB().prepareStatement(query5);
            PreparedStatement ps6 = connectToDB().prepareStatement(query6);
            PreparedStatement ps7 = connectToDB().prepareStatement(query7);
            PreparedStatement ps8 = connectToDB().prepareStatement(query8);
            ps2.execute();
            ps3.execute();
            ps4.execute();
            ps5.execute();
            ps6.execute();
            ps7.execute();
            ps8.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
   }

   static public void adminChecker() {
       try {
           String sqlRestrict = "alter table "+DatabaseActions.dummyTable+" modify dummy_text text not null";
           PreparedStatement ps = DatabaseActions.connectToDB().prepareStatement(sqlRestrict);
           ps.execute();
           GlobalVariables.isAdmin = true;
       } catch (SQLException ex) {
           System.out.println("Admin: No");
       }
   }
}
