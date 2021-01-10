package com.example.foodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class MainAdminActivity extends AppCompatActivity
{
    public Query query;
    Button approve_rec;
    public ArrayAdapter<String> adaptRecipe;
    private List<Recipe> unaprovedRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);
        approve_rec = (Button)findViewById(R.id.button_Admin_Options2);

        approve_rec.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
    
                //1) Query for unaprroved recipes
    
                query = FirebaseDatabase.getInstance().getReference("RecipeDetails").orderByChild("approved").equalTo(0);
                query.addListenerForSingleValueEvent(new ValueEventListener()
                {
    
                    @Override
                    public void onDataChange(@NonNull DataSnapshot DS)
                    {
                        unaprovedRecipes.clear();
                        if (DS.exists())
                        {
                            for (DataSnapshot snapshot : DS.getChildren())
                            {
                                Recipe recipe = snapshot.getValue(Recipe.class);
                                unaprovedRecipes.add(recipe);
                            }
                        }
    
                        List<Recipe> matchedRcipes = new ArrayList<Recipe>();
                        for (Recipe recipe : unaprovedRecipes)
                        {
                            matchedRcipes.add(recipe);
                        }
    
                        // 2) Send to resualt page;
                        // Passing the matchedRecipes  as serilizable list to result_page activity
                        Intent myIntent = new Intent(getApplicationContext(), results_page.class); // Creating the intent
                        myIntent.putExtra("LIST", (Serializable) matchedRcipes); // Putting the list there
                        startActivity(myIntent); // Start new activity with the given intent
//                        finish(); // End this activity
    
                    }
    
                    @Override
                    public void onCancelled(@NonNull DatabaseError error)
                    {
                        Toast.makeText(MainAdminActivity.this, "something went wrong ,  querry failed!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        })
    ;}
}
