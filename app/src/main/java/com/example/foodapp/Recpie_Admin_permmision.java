/**
 * This activity lets the Admin to edit and approve recipes
 */


package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.example.foodapp.notifications.*;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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

    private APIService apiService;
    
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
        db = FirebaseDatabase.getInstance();
        String recipeID = choosen_recipe.getId();
        dbRefToRecipe = db.getReference().child("RecipeDetails").child(recipeID);
        dbRefToProducts = db.getReference().child("Products");

        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);
        
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
                updt_ings = updt_ings.replace(" ", "");
                String updt_recipeName = recipeName.getText().toString().trim();
                
                    // Updating in the DB;
                Map<String,Object> recipeUpdate = new HashMap<>();
                recipeUpdate.put("howTo",updt_howTo);
                recipeUpdate.put("products",updt_ings);
                recipeUpdate.put("recipeName",updt_recipeName);
                recipeUpdate.put("approved",1); // Approve status
                dbRefToRecipe.updateChildren(recipeUpdate);
                
                
                // Add all ingridients into the Products DB
                     //1) Extract all ingridients into an arrayList;
                            //1).a Checking how many products should be after update
                
                String[] splittedToArrayIngs = updt_ings.split(","); // Cut the string into an array of ingridients
                ArrayList<String> userInputIng = new ArrayList<>(); // Init the list
                for (int i = 0; i < splittedToArrayIngs.length; i++)
                {
                    userInputIng.add(splittedToArrayIngs[i]); // Fill the list
                }


                
                    
                     //2) Create HashMap of Products
                     //3) Update all products in dbRefProductds


                String message = "Your recipe, named \"" + updt_recipeName + "\" has been approved!"; //construct the notification message
                String usertoken = choosen_recipe.getUuid(); //get the user's UUID

                Data data = new Data("Fooding", message);
                NotificationSender sender = new NotificationSender(data, usertoken);
                apiService.sendNotifcation(sender).enqueue(new Callback<MyResponse>() {
                    @Override
                    public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                        Log.d(null, "onResponse when sending notification. response.code() ==" + response.code() + " and response.body().success == " + response.body().success);
                        if (response.code() == 200) {
                            Log.d(null, "responsecode 200 and " + response.errorBody().toString());
                            if (response.body().success != 1) {
                                Toast.makeText(Recpie_Admin_permmision.this, "Failed to send notification", Toast.LENGTH_LONG);
                            }
                            else{
                                Toast.makeText(Recpie_Admin_permmision.this, "Sent notification successfully", Toast.LENGTH_LONG);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<MyResponse> call, Throwable t) {
                        Log.d(null, "onFailure when sending notification");
                        t.printStackTrace();
                        Toast.makeText(Recpie_Admin_permmision.this, "ON FAILURE of sending notification", Toast.LENGTH_LONG);
                    }
                });


                finish();
            }
        });

    
    }
}