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
    public String rimage;
    public int rating;
    public String rators; // String of users who rates this recipe;


    /**
     * Constructor for "Add recipe" creates a new recipe to store in DB
     * @param approved
     * @param create_time
     * @param host
     * @param howTo
     * @param id
     * @param measures
     * @param numOfProducts
     * @param products
     * @param recipeName
     * @param Image
     */
    public Recipe( int approved ,String create_time ,String host ,  String howTo , String id,  String measures ,int numOfProducts , String products , String recipeName , String  Image)
    //public Recipe( int approved ,String create_time ,String host ,  String howTo , String id,  String measures ,int numOfProducts , String products , String recipeName)
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
        this.rimage = Image;
        this.rating = 0;
        this.rators = "";




    }

    /**
     *  Constructor for showing Recipes or storing them from DB
     * @param approved
     * @param create_time
     * @param host
     * @param howTo
     * @param id
     * @param measures
     * @param numOfProducts
     * @param products
     * @param recipeName
     * @param Image
     * @param rating
     * @param rators
     */
    public Recipe( int approved ,String create_time ,String host ,  String howTo , String id,  String measures ,int numOfProducts , String products, int rating , String rators, String recipeName , String  Image )
    //public Recipe( int approved ,String create_time ,String host ,  String howTo , String id,  String measures ,int numOfProducts , String products , String recipeName)
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
        this.rimage = Image;
        this.rating = rating;
        this.rators = rators;
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

    //image
    public void setRimage(String rimage) {
        this.rimage = rimage;
    }

    public String getRimage() {
        return rimage;
    }

    public int getRating() {
        return rating;
    }

    public String getRators() {
        return rators;
    }

    /**
     * To prevent multyple rating from the same user , this method check the validity of the rating ,  and updates the recipe accordingly.
     * @param userName the user who is rating this recipe
     * @param rating the rating must be '-' or '+' only!!!!
     * @return true iff the recipe was properly rated by a new user and updated. and fals if no changes were made to the recipe.
     */
    public boolean addRating(String userName, char rating) {

        // 1) check valid rating
        if(rating != '-' &&  rating != '+')
            return false;
        // 2) check if userName allready rated this recipe
        if(this.rators.contains(userName)) // if this user allready rated this recipe
            return false;    // do nothing and return false
        else {
            this.rators = this.rators + userName + rating; // add this users vote to the rators names list ,  and change the rating accordingly

            // 3) Updating rating
            if(rating == '+')
                this.rating++;
            else // Assuming the other option is '-';
                this.rating--;
            return true;
        }
    }

    //19.12 -- liora: delete the info and by , and return just the name recpie and the host
    // i put " " after the "," because i dont want the two labels begin in the same colm
    public String toString()
    {
        //return "This recipe name is : " + recipeName + " by : " + host + " " ;
        return (recipeName + ", " + host );
    }

    public String[] splitIngredients(){

        this.products = this.products.replace(" " , "");
        return this.products.split(",");
    }



    /**
     * We can add a method theat return split ingridients with mesaurments
     */


}
