package com.example.foodapp;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RecipeHeader
{
    // Members

    // A : recipe id; is the same as the ID in RecipeDetails
    public String id;

    // B : Hebrew name not implemented
    // C : English name
    public String name;

    // Default constructor
    public RecipeHeader() {}
    // Explicit constructor
    public RecipeHeader(String id , String name )
    {

    }

    /////////////// This constructor contacts the DB and pass the RecipeHeader info to create an instance of RecipeHeader \\\\\\\\\\\\\

    // Connect to FBDB
   FirebaseDatabase database = FirebaseDatabase.getInstance() ;
    DatabaseReference dbRHRef = database.getReference() ;



    // Init connection



    public RecipeHeader(String id)
    {

    }



}
