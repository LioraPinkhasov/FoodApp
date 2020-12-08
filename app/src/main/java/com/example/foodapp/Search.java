package com.example.foodapp;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class Search extends AppCompatActivity {

    private MultiAutoCompleteTextView needToFind;
    private Button sByingredient;
    private List<Ingredient> ingredientList;
    private Query query;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        ValueEventListener valueEventListener = new ValueEventListener() {// Insert the query results into the ingredientList
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ingredientList.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Ingredient ingredient = snapshot.getValue(Ingredient.class);
                        ingredientList.add(ingredient);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        ingredientList = new ArrayList<>();
        query = FirebaseDatabase.getInstance().getReference("Products").orderByChild("C"); // A query that get all the ingredient names
        query.addListenerForSingleValueEvent(valueEventListener); // Here we push the ingredient names into the ingredientList
        sByingredient = (Button) findViewById(R.id.by_ing_buttn) ;
        needToFind = (MultiAutoCompleteTextView)findViewById(R.id.insert_data_space);
        ArrayAdapter<Ingredient> ingredientAdapter = new ArrayAdapter<Ingredient>(this, android.R.layout.simple_list_item_1, ingredientList);
        needToFind.setAdapter(ingredientAdapter);




        needToFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });






    }


}


