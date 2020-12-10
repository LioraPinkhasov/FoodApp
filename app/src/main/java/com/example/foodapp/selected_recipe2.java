package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class selected_recipe2 extends AppCompatActivity {

    Recipe choosen_recipe ;
    TextView r_header;
    TextView r_ing;
    TextView r_how_to;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_recipe2);


        // Assuming I got Recipe recipe

        Intent i = getIntent();
        choosen_recipe = (Recipe) i.getSerializableExtra("choosenRecipe");

        // Show the recipe header
        r_header = (TextView)findViewById(R.id.recipe_header_view);
        String header = choosen_recipe.toString();
        r_header.setText(header);

        // Show Ingredients

        r_ing = (TextView)findViewById(R.id.ing_textView);
        String ingredients = choosen_recipe.getProducts();
        r_ing.setText(ingredients);

        // Show how to

        r_how_to = (TextView)findViewById(R.id.how_to_view);
        String how_to = choosen_recipe.getHowTo();

        r_how_to.setText(how_to);






    }
}