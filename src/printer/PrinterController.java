package printer;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXListView;
import database.DatabaseActions;
import jasperReporter.InvoicePrinter;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import models.DGVModel;
import models.DummyOther2;
import models.Other2Model;
import net.sf.jasperreports.engine.JRException;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.ResourceBundle;

public class PrinterController implements Initializable {

    @FXML
    private TextField textField;

    @FXML
    private TableView<Other2Model> tableView;

    @FXML
    private TableColumn<Other2Model, String> desCol;

    @FXML
    private TableColumn<Other2Model, String> amountCol;

    @FXML
    private TableColumn<Other2Model, Integer> quantityCol;

    @FXML
    private TableColumn<Other2Model, String> dateCol;

    @FXML
    private TableColumn<Other2Model, String> dueDateCol;

    @FXML
    private TableColumn<Other2Model, String> vatCol;
    @FXML
    private TableColumn<Other2Model, String> customerCol;
    @FXML
    private TableColumn<Other2Model, String> addressCol;

    @FXML
    private TableView<DummyOther2> tableView1;

    @FXML
    private TableColumn<DummyOther2, String> desCol1;

    @FXML
    private TableColumn<DummyOther2, String> amountCol1;

    @FXML
    private TableColumn<DummyOther2, Integer> quantityCol1;

    @FXML
    private TableColumn<DummyOther2, String> dateCol1;

    @FXML
    private TableColumn<DummyOther2, String> dueDateCol1;
    @FXML
    private TableColumn<DummyOther2, String> customerCol1;
    @FXML
    private TableColumn<DummyOther2, String> addressCol1;
    @FXML
    private JFXListView<String> listView;

    @FXML
    private JFXButton btn2;

    @FXML
    private JFXButton btn1;

    @FXML
    private TableColumn actionCol;
    @FXML
    private TableColumn actionCol1;

    @FXML
    private JFXCheckBox selectAll1;

    @FXML
    private JFXCheckBox selectAll2;

    boolean isSelectAllChecked1 = false;
    boolean isSelectAllChecked2 = false;

    @FXML
    private void selectAll1Power() throws SQLException {
        isSelectAllChecked1 = selectAll1.isSelected();
        DatabaseActions.other2VatDataGenerator(customer, tableView);
    }

    @FXML
    private void selectAll2Power() throws SQLException {
        isSelectAllChecked2 = selectAll2.isSelected();
        DatabaseActions.Other2CommonDataGenerator(customer, tableView1);
    }

    private void addDataToTableView1() {
        actionCol.setCellFactory(actionCellFactory);
        vatCol.setCellValueFactory(new PropertyValueFactory<>("vat"));
        dueDateCol.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        desCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        customerCol.setCellValueFactory(new PropertyValueFactory<>("customer"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
    }

    private void addDataToTableView2() {
        dueDateCol1.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        dateCol1.setCellValueFactory(new PropertyValueFactory<>("date"));
        amountCol1.setCellValueFactory(new PropertyValueFactory<>("amount"));
        quantityCol1.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        desCol1.setCellValueFactory(new PropertyValueFactory<>("description"));
        customerCol1.setCellValueFactory(new PropertyValueFactory<>("customer"));
        addressCol1.setCellValueFactory(new PropertyValueFactory<>("address"));
        actionCol1.setCellFactory(actionCellFactory1);
    }

    @FXML
    private void clearText() {
        textField.clear();
    }

    String customer = null;

    @FXML
    private void searchAction() throws SQLException {
        String sql = "select customer from " + DatabaseActions.tableNameForOthers2 + " where customer like '%" + textField.getText() + "%' group by customer";
        Statement statement = DatabaseActions.connectToDB().createStatement();
        ResultSet rs = statement.executeQuery(sql);
        listView.getItems().clear();
        while (rs.next()) {
            listView.getItems().addAll(rs.getString("customer"));
        }
    }

    @FXML
    private void printer1() throws JRException {
        InvoicePrinter.printInvoice(customer, 1);
    }

    @FXML
    private void printer2() throws JRException {
        InvoicePrinter.printInvoice(customer, 2);
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        addDataToTableView1();
        addDataToTableView2();
        btn1.setTooltip(new Tooltip("Print"));
        btn2.setTooltip(new Tooltip("Print"));

        listView.setOnMouseClicked(e -> {
            customer = listView.getSelectionModel().getSelectedItem();
            try {
                DatabaseActions.other2VatDataGenerator(customer, tableView);
                DatabaseActions.Other2CommonDataGenerator(customer, tableView1);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
    }

    Callback<TableColumn<DGVModel, String>, TableCell<Other2Model, String>> actionCellFactory = (param) -> {

        // setting a cell to accept button

        return new TableCell<Other2Model, String>() {

            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    Other2Model model = tableView.getItems().get(getIndex());
                    CheckBox checkBox = new CheckBox();
                    if (isSelectAllChecked1) {
                        checkBox.setSelected(true);
                        selectPowerForVat(model);
                    } else {
                        checkBox.setSelected(false);
                        deSelectPowerForVat(model);
                    }
                    checkBox.setOnAction(e -> {
                        if (checkBox.isSelected()) {
                            selectPowerForVat(model);
                        } else {
                            selectAll1.setSelected(false);
                            deSelectPowerForVat(model);
                        }
                    });
                    setGraphic(checkBox);
                }
                setText(null);
            }
        };
    };

    Callback<TableColumn<DGVModel, String>, TableCell<DummyOther2, String>> actionCellFactory1 = (param) -> {

        // setting a cell to accept button

        return new TableCell<DummyOther2, String>() {

            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    DummyOther2 model = tableView1.getItems().get(getIndex());
                    CheckBox checkBox = new CheckBox();
                    if (isSelectAllChecked2) {
                        checkBox.setSelected(true);
                        selectPowerForNonVat(model);
                    } else {
                        checkBox.setSelected(false);
                        deSelectPowerForNonVat(model);
                    }
                    checkBox.setOnAction(e -> {
                        if (checkBox.isSelected()) {
                            selectPowerForNonVat(model);
                        } else {
                            selectAll2.setSelected(false);
                            deSelectPowerForNonVat(model);
                        }
                    });
                    setGraphic(checkBox);
                }
                setText(null);
            }
        };
    };

    private void selectPowerForVat(Other2Model model) {
        String sql = "insert into " + DatabaseActions.printerTable + "(customer,address,telephone,description," +
                "amount,quantity,tran_date,vat,due_date,total_vat,id)values(?,?,?,?,?,?,?,?,?,?,?)";
        String sql2 = "select amount from " + DatabaseActions.tableNameForOthers2 + " where id=" +
                +model.getId() + "";
        NumberFormat nf = NumberFormat.getInstance();
        String amount1 = null;
        try {
            Statement statement = DatabaseActions.connectToDB().createStatement();
            ResultSet resultSet = statement.executeQuery(sql2);
            while (resultSet.next()) {
                amount1 = String.valueOf(resultSet.getInt("amount"));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        try {
            PreparedStatement preparedStatement = DatabaseActions.connectToDB().prepareStatement(sql);
            preparedStatement.setString(1, model.getCustomer());
            preparedStatement.setString(2, model.getAddress());
            preparedStatement.setString(3, model.getTelephone());
            preparedStatement.setString(4, model.getDescription());
            double amount3 = Double.parseDouble(model.getAmount());
            preparedStatement.setString(5, nf.format(amount3));
            preparedStatement.setInt(6, model.getQuantity());
            preparedStatement.setString(7, model.getDate());
            double vat = Double.parseDouble(model.getVat());
            preparedStatement.setString(8, nf.format(vat));
            preparedStatement.setString(9, model.getDueDate());
            preparedStatement.setString(10, amount1);
            preparedStatement.setInt(11, model.getId());
            preparedStatement.execute();
            DatabaseActions.dummyPrinterTable1(customer);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void selectPowerForNonVat(DummyOther2 model) {
        String sql = "insert into " + DatabaseActions.printerTable2 + "(customer,address,telephone,description," +
                "amount,quantity,tran_date,due_date,total_amount,id)values(?,?,?,?,?,?,?,?,?,?)";
        String sql2 = "select amount from " + DatabaseActions.tableNameForOthers2 + " where id=" +
                +model.getId() + "";
        NumberFormat nf = NumberFormat.getInstance();
        String amount1 = null;
        try {
            Statement statement = DatabaseActions.connectToDB().createStatement();
            ResultSet resultSet = statement.executeQuery(sql2);
            while (resultSet.next()) {
                amount1 = String.valueOf(resultSet.getInt("amount"));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        try {
            PreparedStatement preparedStatement = DatabaseActions.connectToDB().prepareStatement(sql);
            preparedStatement.setString(1, model.getCustomer());
            preparedStatement.setString(2, model.getAddress());
            preparedStatement.setString(3, model.getTelephone());
            preparedStatement.setString(4, model.getDescription());
            double amount3 = Double.parseDouble(model.getAmount());
            preparedStatement.setString(5, nf.format(amount3));
            preparedStatement.setInt(6, model.getQuantity());
            preparedStatement.setString(7, model.getDate());
            preparedStatement.setString(8, model.getDueDate());
            preparedStatement.setString(9, amount1);
            preparedStatement.setInt(10, model.getId());
            preparedStatement.execute();
            DatabaseActions.dummyPrinterTable2(customer);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void deSelectPowerForVat(Other2Model model) {
        String sql = "delete from " + DatabaseActions.printerTable + " where id=" + model.getId() + "";
        try {
            PreparedStatement ps = DatabaseActions.connectToDB().prepareStatement(sql);
            ps.execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private void deSelectPowerForNonVat(DummyOther2 model) {
        String sql = "delete from " + DatabaseActions.printerTable + " where id=" + model.getId() + "";
        try {
            PreparedStatement ps = DatabaseActions.connectToDB().prepareStatement(sql);
            ps.execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
