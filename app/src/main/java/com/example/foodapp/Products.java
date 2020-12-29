package com.example.foodapp;


import java.util.List;

/**
 * This class represents a ingridient named as product in the DB
 */
public class Products {
    public String measures, name;


    public Products(){}


    public Products(String measures, String name) {
        this.measures = measures;
        this.name = name;

    }


    public String getMeasures() {
        return measures;
    }

    public String getName() {
        return name;
    }

    public void setMeasures(String measures) {
        this.measures = measures;
    }

    public void getName(String name) {
        this.name = name;
    }


    public String toString() {
        return "product name: " + name + ", measures unit for this product is: " + measures;
    }

}