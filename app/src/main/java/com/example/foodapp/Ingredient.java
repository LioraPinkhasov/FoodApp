package com.example.foodapp;

/**
 * This class represents a ingridient named as product in the DB
 */
public class Ingredient {
    public String uid, engName, units;


    public Ingredient(String uid, String engName, String units) {
        this.uid = uid;
        this.engName = engName;
        this.units = units;
    }


    public String toString() {
        return "ID: " + uid + ", english name: " +engName+ ", measure units: " + units ;
    }

}