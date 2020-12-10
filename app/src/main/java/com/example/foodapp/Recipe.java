package com.example.foodapp;

import java.io.Serializable;

public class Recipe implements Serializable
{
    public int approved ;
    public String create_time ;
    public String host;
    public String howTo ;
    public String id;
    public String measures ;
    public int numOfProducts ;
    public String products ;
    public String recipeName ;

    public Recipe( int approved ,String create_time ,String host ,  String howTo , String id,  String measures ,int numOfProducts , String products , String recipeName    )
    {
        this.numOfProducts = numOfProducts;
        this.recipeName = recipeName;
        this.approved = approved; // Never approved without admin
        this.measures = measures;
        this.create_time = create_time;
        this.howTo = howTo;
        this.host = host;
        this.id = id;
        this.products = products;

    }

    public Recipe ()
    {
       // int a=0;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
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

    public void setNumOfProducts(int numOfProducts) {
        this.numOfProducts = numOfProducts;
    }

    public int getNumOfProducts() {
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

    public int getApproved() {
        return approved;
    }

    public void setApproved(int approved) {
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
        return "This recipe name is : " + recipeName + " by : " + host + " " ;
    }

    public String[] splitIngredients(){

        this.products = this.products.replace(" " , "");
        return this.products.split(",");
    }

    /**
     * We can add a method theat return split ingridients with mesaurments
     */


}
