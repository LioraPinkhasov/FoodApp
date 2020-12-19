package com.example.foodapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class addRecipe extends AppCompatActivity
{
    // Views decleration
    Button sendRecipe;
    Button addPhoto;
    EditText recipeName;
    EditText ingridientsList;
    EditText howToDescription;


    // DB decleration

    FirebaseDatabase mDatabase;
    DatabaseReference dbRecipeRef;
    private FirebaseAuth mAuth;





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

        // Init DB references
        mDatabase = FirebaseDatabase.getInstance();
        dbRecipeRef = mDatabase.getReference().child("RecpieDetiels");
        // Initing FirebaseAuth instance
        mAuth = FirebaseAuth.getInstance();

        // Clicking sendRecipe
        sendRecipe.setOnClickListener(new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onClick(View v)
        {
            // Parts of recipe
            String rName = recipeName.getText().toString().toLowerCase().trim(); // Should I use trim?
            String rIngridients = ingridientsList.getText().toString().toLowerCase().trim();
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
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDateTime now = LocalDateTime.now();
            String currentDate = now.toString();
            // 2.c ) User id is given by DBfirebase and I update in when putting the recipe in DB
            String id = "";
            // 2.d) Extract and put user email in the host string
            FirebaseUser currentUser = mAuth.getCurrentUser(); // getting user Info from Authentication system
            String host = currentUser.getEmail(); // host is now our email.
            // 2.e)
            String measures = "How do I get the measures?";
            // 3) Store the recipe in DB
            Recipe newRecipe = new Recipe( 0 , currentDate ,host,howTo ,id ,measures , commas+1 ,rIngridients , rName ); // Creating the new recipe
            newRecipe.setId(dbRecipeRef.push().getKey());
            dbRecipeRef.child(newRecipe.getId()).setValue(newRecipe);
            // 4) Toast and move to main page.
            Toast.makeText(addRecipe.this, "Thank you ! your recipe is added ! and will be visible when aproved by admin. ", Toast.LENGTH_SHORT).show();
            //startActivity(new Intent(getApplicationContext(), MainUserActivity.class)); // This is the proper path!
            finish();


        }


        //add pic for firebase





    });
    }




}