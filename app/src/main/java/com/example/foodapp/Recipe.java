package com.example.foodapp;

public class Recipe
{
    private String id ;
    private String recipeName ;
    private String numOfProducts ;
    private String products ;
    private String measures ;
    private String howTo ;

    public Recipe(String id , String recipeName ,String numOfProducts , String products , String measures , String howTo )
    {
        this.id = id;
        this.recipeName = recipeName;
        this.numOfProducts = numOfProducts;
        this.products = products;
        this.measures = measures;
        this.howTo = howTo;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
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
        return "This recipe name is : " + recipeName + " and its id is : " + id + " " ;
    }
}
