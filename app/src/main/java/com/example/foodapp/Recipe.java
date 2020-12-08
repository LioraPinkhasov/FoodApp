package com.example.foodapp;

public class Recipe
{
    RecipeHeader r_name;
    RecipeDetails r_details;

    public Recipe(String id)
    {

        // if such ID exists
        r_name = new RecipeHeader(id);
        r_details = new RecipeDetails(id);


    }
}
