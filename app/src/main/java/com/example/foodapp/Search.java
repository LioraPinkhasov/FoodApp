package com.example.foodapp;

import android.view.View;
import android.widget.ArrayAdapter;
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
    private List<Ingredient> ingredientList;
    private Query query;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ingredientList = new ArrayList<>();
        needToFind = (MultiAutoCompleteTextView)findViewById(R.id.insert_data_space);
        ArrayAdapter<Ingredient> ingredientAdapter = new ArrayAdapter<Ingredient>(this, android.R.layout.simple_list_item_1,ingredientList);
        needToFind.setAdapter(ingredientAdapter);

        ValueEventListener valueEventListener = new ValueEventListener() {
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

        //query =


    }


}


