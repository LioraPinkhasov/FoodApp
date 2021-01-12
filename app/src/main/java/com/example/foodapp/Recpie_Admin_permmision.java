/**
 * This activity lets the Admin to edit and approve recipes
 */


package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


public class Recpie_Admin_permmision extends AppCompatActivity
{
    
    Recipe choosen_recipe;
    //******* Declerations ******\\
    //Buttons
    Button aprrove_recipe,delete_recipe;
    //Uneditable text Views.
    TextView textDate , textHostName;
    //Editable text Views
    EditText howTo , ingList ,recipeName;
    // DataBase
    FirebaseDatabase db;
    DatabaseReference dbRefToRecipe;
    DatabaseReference dbRefToProducts;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recpie__admin_permmision);
    
        //  1) Getting the recipe
        Intent i = getIntent();
        choosen_recipe = (Recipe) i.getSerializableExtra("adminChoosenRecipe");
        
        // 2) init Buttons and textViews
        
        aprrove_recipe = (Button)findViewById(R.id.approve_button) ;
        delete_recipe = (Button)findViewById(R.id.delete_recipe_buttn) ;
        textDate = (TextView)findViewById(R.id.date_text) ;
        textHostName= (TextView)findViewById(R.id.host_name_text) ;
        howTo = (EditText)findViewById(R.id.edit_text_howTo) ;
        ingList= (EditText)findViewById(R.id.edit_text_inng) ;
        recipeName= (EditText)findViewById(R.id.edit_text_recipeName) ;
        
        // 3) Set texts from the Recipe to the appropriate fields
         // Uneditable fields
        textDate.setText(choosen_recipe.getCreate_time());
        textHostName.setText(choosen_recipe.getHost());
        
         //Editable fields
        howTo.setText(choosen_recipe.getHowTo());
        ingList.setText(choosen_recipe.getProducts());
        recipeName.setText(choosen_recipe.getRecipeName());
        
        // Init dataBase
        db.getInstance();
        dbRefToRecipe = db.getReference().child("RecipeDetails").child(choosen_recipe.getId());
        dbRefToProducts = db.getReference().child("Products");
        
        delete_recipe.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Delete this recipe from DB
                dbRefToRecipe.removeValue();
            }
        });
        
        aprrove_recipe.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Update all fields
                    // Create new Strings for updates
                String updt_howTo = howTo.getText().toString().trim();
                String updt_ings = ingList.getText().toString().trim();
                String updt_recipeName = recipeName.getText().toString().trim();
                
                    // Updating in the DB;
                Map<String,Object> childUpdate = new HashMap<>();
                childUpdate.put("howTo",updt_howTo);
                childUpdate.put("products",updt_ings);
                childUpdate.put("recipeName",updt_recipeName);
                childUpdate.put("approved",1); // Approve status
                dbRefToRecipe.updateChildren(childUpdate);
                
                
                // Add all ingridients into the Products DB
                
                
        
            }
        });
    
    
    }
}