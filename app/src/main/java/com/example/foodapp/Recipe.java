package com.example.foodapp;

import java.io.Serializable;

public class Recipe implements Serializable
{
    private String host;
    private String recipeName ;
    private String numOfProducts ;
    private String products ;
    private String measures ;
    private String approved ;
    private String create_time ;
    private String howTo ;

    public Recipe(String host , String recipeName ,String numOfProducts , String products , String measures , String approved , String create_time , String howTo )
    {
        this.host = host;
        this.recipeName = recipeName;
        this.numOfProducts = numOfProducts;
        this.products = products;
        this.measures = measures;
        this.approved = "0"; // Never approved without admin
        this.create_time = create_time;
        this.howTo = howTo;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getHost() {
        return host;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setNumOfProducts(String numOfProducts) {
        this.numOfProducts = numOfProducts;
    }

    public String getNumOfProducts() {
        return numOfProducts;
    }

    public void setProducts(String products) {
        this.products = products;
    }

    public String getProducts() {
        return products;
    }

    public void setMeasures(String measures) {
        this.measures = measures;
    }

    public String getApproved() {
        return approved;
    }

    public void setApproved(String approved) {
        this.approved = approved;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getMeasures() {
        return measures;
    }

    public void setHowTo(String howTo) {
        this.howTo = howTo;
    }

    public String getHowTo() {
        return howTo;
    }

    public String toString()
    {
        return "This recipe name is : " + recipeName + " by a : " + host + " " ;
    }

    public String[] splitIngredients(){
        return this.products.split(",");
    }

    /**
     * We can add a method theat return split ingridients with mesaurments
     */

}
