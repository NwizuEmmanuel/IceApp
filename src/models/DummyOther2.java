package models;

public class DummyOther2 {
    int id;
    String customer;
    String address;
    String telephone;
    String description;
    String amount;
    String date;
    String dueDate;
    int quantity;

    public DummyOther2(int id, String customer, String address, String telephone, String description, String amount, String date, String dueDate, int quantity) {
        this.id = id;
        this.customer = customer;
        this.address = address;
        this.telephone = telephone;
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.dueDate = dueDate;
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

    public int getQuantity() {
        return quantity;
    }
}
