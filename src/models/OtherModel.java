/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author Star fruit
 */
public class OtherModel {
    int id;
    String Item;
    String amount;
    String transDate;

    public OtherModel(int id, String item, String amount, String transDate) {
        this.id = id;
        Item = item;
        this.amount = amount;
        this.transDate = transDate;
    }

    public int getId() {
        return id;
    }

    public String getItem() {
        return Item;
    }

    public String getAmount() {
        return amount;
    }

    public String getTransDate() {
        return transDate;
    }
}
