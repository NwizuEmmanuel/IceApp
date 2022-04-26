/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.sql.Date;

/**
 *
 * @author Star fruit
 */
public class InternModel {

    int id;
    String fullName;
    Date regDate;
    String course;
    String telephone;
    String amount;

    public InternModel(int id, String fullName, Date regDate, String course, String telephone, String amount) {
        this.id = id;
        this.fullName = fullName;
        this.regDate = regDate;
        this.course = course;
        this.telephone = telephone;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public Date getRegDate() {
        return regDate;
    }

    public String getCourse() {
        return course;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getAmount() {
        return amount;
    }
}
