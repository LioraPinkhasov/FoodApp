package com.example.foodapp;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class RecipeDetails
{


    // Memebrs as defined in FbDb

    // A : id
    public  String id;

    // B : the products used in the recipe

    public List<Ingredient> ingredients;

    // C : Units to use for each ingredient in B

    public List<String> units;

    ///// Time members are not yet implemented

    // Default constructor
    public RecipeDetails() {}

    // More ditailed constructor
    public RecipeDetails( String id , List<Ingredient> ingredients , List<String> units )
    {
        this.id = id;
        this.ingredients = ingredients;
        this.units = units;
    }


    // This next part is a constructor by id ,  where it will find the proper recipe from out DB and fill the details according to it.

    public RecipeDetails (String id)
    // Need constructor to build from DB
    {


    }


}
