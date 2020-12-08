package com.example.foodapp;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.MultiAutoCompleteTextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

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

        //query =


    }


}


