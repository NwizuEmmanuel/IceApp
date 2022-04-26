package models;

public class Other2Model {
    int id;
    String customer;
    String address;
    String telephone;
    String description;
    String amount;
    String date;
    String dueDate;
    String vat;
    int quantity;

    public Other2Model(int id, String customer, String address, String telephone, String description, String amount, String date, String dueDate, String vat, int quantity) {
        this.id = id;
        this.customer = customer;
        this.address = address;
        this.telephone = telephone;
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.dueDate = dueDate;
        this.vat = vat;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public String getCustomer() {
        return customer;
    }

    public String getAddress() {
        return address;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getDescription() {
        return description;
    }

    public String getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getVat() {
        return vat;
    }

    public int getQuantity() {
        return quantity;
    }
}
