package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class addRecipe extends AppCompatActivity
{

    Button sendRecipe;
    Button addPhoto;
    EditText recipeName;
    EditText ingridientsList;
    EditText howToDescription;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        // Init buttons and EditText

        sendRecipe = (Button)findViewById(R.id.send_recipe_button);
        addPhoto = (Button)findViewById(R.id.add_photo_button);
        recipeName = (EditText)findViewById(R.id.recipeName_TextView);
        ingridientsList = (EditText)findViewById(R.id.ingredient_list_text);
        howToDescription = (EditText)findViewById(R.id.wrkProgress_text);

        // Clicking sendRecipe
        sendRecipe.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v)
        {
            // Parts of recipe
            String rName = recipeName.getText().toString().trim(); // Should I use trim?
            String rIngridients = ingridientsList.getText().toString().trim();
            String howTo = howToDescription.getText().toString().trim();;

            // 1) Check if any field is empty and show toast ,  else put them inside a string;

            if(TextUtils.isEmpty(rName))
            {
                recipeName.setError("Recipe name is Required!");
                return;
            }
            if(TextUtils.isEmpty(rIngridients))
            {
                ingridientsList.setError("Recipe must have Ingredients");
                return;
            }

            if(TextUtils.isEmpty(howTo))
            {
                howToDescription.setError("Please tell us how to make the recipe");
                return;
            }
            // 2) Create a Recipe Object

            // 2.a) count different ingredients by counting the num of commas -1
            int commas = 0;
            for(int i = 0; i < rIngridients.length(); i++)
            {
                if(rIngridients.charAt(i) == ',') commas++;
            }
            // 2.b ) Establish current Date
            String currentDate = "00/00/00";
            // 2.c ) How to make an ID?
            String id = "What is id used for?";
            // 2.d) Extract and put user email in the host string
            String host = "how to get the email of a user?";
            // 2.e)
            String measures = "How do I get the measures?";

            Recipe newRecipe = new Recipe( 0 , currentDate ,host,howTo ,id ,measures , commas+1 ,rIngridients , rName );


            // 3) Store the recipe in DB

        }

    });
    }




}