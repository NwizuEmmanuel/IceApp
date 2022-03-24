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
    String Item;
    String amount;
    String cashflow;
    String transDate;

    public OtherModel(String Item, String amount, String cashflow, String transDate) {
        this.Item = Item;
        this.amount = amount;
        this.cashflow = cashflow;
        this.transDate = transDate;
    }

    public String getItem() {
        return Item;
    }

    public String getAmount() {
        return amount;
    }

    public String getCashflow() {
        return cashflow;
    }

    public String getTransDate() {
        return transDate;
    }
    
    
}
