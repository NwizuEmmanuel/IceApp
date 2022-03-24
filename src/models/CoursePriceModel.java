/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author Star fruit
 */
public class CoursePriceModel {
    String course;
    String price;

    public CoursePriceModel(String course, String price) {
        this.course = course;
        this.price = price;
    }

    public String getCourse() {
        return course;
    }

    public String getPrice() {
        return price;
    }
    
}
