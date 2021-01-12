package com.example.foodapp;

import android.content.Intent;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;



public class Search extends AppCompatActivity {

    // Init needed views
    private Button sByingredient;
    private Button byAuthor;
    private Button byRecipe;

    
    private List<Recipe> recipesWithMatchSize;
    private List<Recipe> AuthorNames;
    private List<Recipe> RecipeNames;
    public ArrayAdapter<String> adaptIng;
    public ArrayAdapter<String> adaptRecipe;
    public Query query;
    private MultiAutoCompleteTextView ingData;
    private AutoCompleteTextView authorOrRecipeNames;
    private Query ingredientQuery;
    private Query RecipeQuery;
    private Query AuthorQuery;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        adaptIng = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        ingredientQuery = FirebaseDatabase.getInstance().getReference("Products");
        ingredientQuery.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot data : snapshot.getChildren()) {
                        Products ingred = data.getValue(Products.class);
                        adaptIng.add(ingred.getName());
                    }
                }
                //below code is for preventing duplicates. BUT -
                //A. This creates a bug where the autocomplete is empty for some reason (when debugging it doesn't. Not sure why)
                //B. It is un-needed. The ingredients list is not supposed to have duplicates in the first place.
//                HashSet hs1 = new HashSet();
//                for(int i = 0 ; i < adaptIng.getCount(); i++) {
//                    hs1.add(adaptIng.getItem(i));
//                }
//                adaptIng.clear();
//                adaptIng.addAll(hs1);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        ingData = (MultiAutoCompleteTextView) findViewById(R.id.multiAutoCompleteTextView); // This is the field were ingredient input is comming from
        ingData.setThreshold(1);
        ingData.setAdapter(adaptIng);
        ingData.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());



        adaptRecipe = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        RecipeQuery = FirebaseDatabase.getInstance().getReference("RecipeDetails").orderByChild("approved").equalTo(1);
        RecipeQuery.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot data : snapshot.getChildren()) {
                        Recipe ingred = data.getValue(Recipe.class);
                        adaptRecipe.add(ingred.getRecipeName());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        AuthorQuery = FirebaseDatabase.getInstance().getReference("RecipeDetails").orderByChild("approved").equalTo(1);
        AuthorQuery.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot data : snapshot.getChildren()) {
                        Recipe ingred = data.getValue(Recipe.class);
                        adaptRecipe.add(ingred.getHost());
                    }
                }
                HashSet hs2 = new HashSet();
                int c = adaptRecipe.getCount();
                for(int i = 0 ; i < adaptRecipe.getCount(); i++) {
                    hs2.add(adaptRecipe.getItem(i));
                }
                adaptRecipe.clear();
                adaptRecipe.addAll(hs2);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });




        final String[] tmpVar = new String[1];
        authorOrRecipeNames = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        authorOrRecipeNames.setThreshold(1);
        authorOrRecipeNames.setAdapter(adaptRecipe);
        authorOrRecipeNames.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tmpVar[0] = adaptRecipe.getItem(position);
            }
        });


        authorOrRecipeNames.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // Connecting the XML to our Objects
        sByingredient = (Button) findViewById(R.id.by_ing_buttn);
        byAuthor = (Button) findViewById(R.id.by_author_bttn);
        byRecipe = (Button) findViewById(R.id.by_recipe_bttn);
        recipesWithMatchSize = new ArrayList<>();
        AuthorNames = new ArrayList<>();
        RecipeNames = new ArrayList<>();
        // Creating Intent to pass forward to resualt page.


        sByingredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 1) Cast the Input into an  ArrayList<String> userInputIng

                String usrInput = ingData.getText().toString(); // This is the string from input

                usrInput = usrInput.replace(" ", ""); // Cutting off all the spaces for easier work.
                String[] splittedToArrayInput = usrInput.split(","); // Cut the string into an array of ingridients
                ArrayList<String> userInputIng = new ArrayList<>(); // Init the list
                for (int i = 0; i < splittedToArrayInput.length; i++) {
                    userInputIng.add(splittedToArrayInput[i]); // Fill the list
                }

                // 2) Pass userInputIng to  The Search Function by Ingridients searchByIng and store it in matchedRecipeList

                int size = userInputIng.size();
                String strSize = String.valueOf(size);

                query = FirebaseDatabase.getInstance().getReference("RecipeDetails").orderByChild("numOfProducts").equalTo(size);
                query.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot DS) {
                        recipesWithMatchSize.clear();
                        if (DS.exists()) {
                            for (DataSnapshot snapshot : DS.getChildren()) {
                                Recipe recipe = snapshot.getValue(Recipe.class);
                                recipesWithMatchSize.add(recipe);
                            }
                        }

                        List<Recipe> matchedRcipes = new ArrayList<Recipe>();
                        for (Recipe recipe : recipesWithMatchSize) {
                            String[] ingForRecipe = recipe.splitIngredients();
                            boolean notMatch = false;
                            for (int index = 0; index < size && !notMatch; index++) {
                                boolean ingFound = false;
                                for (int innerIndex = 0; innerIndex < ingForRecipe.length && !ingFound; innerIndex++) {

                                    if (userInputIng.get(index).equals(ingForRecipe[innerIndex])) {
                                        ingFound = true;
                                    }

                                    if (!ingFound && innerIndex + 1 == ingForRecipe.length) {
                                        notMatch = true;

                                    }
                                }
                            }
                            if (!notMatch) {
                                matchedRcipes.add(recipe);
                            }
                        }

                        // Passing the matchedRecipes  as serilizable list to result_page activity
                        Intent myIntent = new Intent(getApplicationContext(), results_page.class); // Creating the intent
                        myIntent.putExtra("LIST", (Serializable) matchedRcipes); // Putting the list there
                        startActivity(myIntent); // Start new activity with the given intent
//                        finish(); // End this activity


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.d(null, "---- !!!!!!onCancelled!!!!!! ----");


                    }
                });


            }//end onClick
        });


        byAuthor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 1) Cast the Input into an  ArrayList<String> userInputIng

                String usrInput = authorOrRecipeNames.getText().toString(); // This is the string from input
                usrInput = usrInput.replace(" ", ""); // Cutting off all the spaces for easier work
                usrInput = usrInput.toLowerCase();
//              query = FirebaseDatabase.getInstance().getReference("RecipeDetails").orderByChild("approved").equalTo(1).orderByChild("host");
                query = FirebaseDatabase.getInstance().getReference("RecipeDetails").orderByChild("approved").equalTo(1);

                String finalUsrInput = usrInput;
                query.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot DS) {
                        AuthorNames.clear();
                        if (DS.exists()) {
                            for (DataSnapshot snapshot : DS.getChildren()) {
                                Recipe names = snapshot.getValue(Recipe.class);
                                AuthorNames.add(names);
                            }
                        }


                        for(int i = 0 ; i < AuthorNames.size(); i++){
                            if(!AuthorNames.get(i).getHost().equals(finalUsrInput)){
                                AuthorNames.remove(i);
                            }
                        }

                        // Passing the matchedRecipes  as serilizable list to result_page activity
                        // Intent myIntent = new Intent(getApplicationContext(), ResultsPageUser.class); // Creating the intent
                        Intent myIntent = new Intent(getApplicationContext(), results_page.class); // Creating the intent
                        myIntent.putExtra("LIST", (Serializable) AuthorNames); // Putting the list there
                        startActivity(myIntent); // Start new activity with the given intent
                        finish(); // End this activity


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.d(null, "---- !!!!!!onCancelled!!!!!! ----");


                    }
                });


            }//end onClick
        });


        byRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 1) Cast the Input into an  ArrayList<String> userInputIng

                String usrInput = authorOrRecipeNames.getText().toString(); // This is the string from input
                usrInput = usrInput.replace(" ", ""); // Cutting off all the spaces for easier work
                usrInput = usrInput.toLowerCase();
                query = FirebaseDatabase.getInstance().getReference("RecipeDetails").orderByChild("recipeName").startAt(usrInput);
                String finalUsrInput = usrInput;
                query.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot DS) {
                        RecipeNames.clear();
                        if (DS.exists()) {
                            for (DataSnapshot snapshot : DS.getChildren()) {
                                Recipe recipe = snapshot.getValue(Recipe.class);
                                RecipeNames.add(recipe);
                            }
                        }


                        for(int i = 0 ; i < RecipeNames.size(); i++) {
                            if (!RecipeNames.get(i).getHost().equals(finalUsrInput)) {
                                RecipeNames.remove(i);
                            }
                        }

                        // Passing the matchedRecipes  as serilizable list to result_page activity
                        Intent myIntent = new Intent(getApplicationContext(), results_page.class); // Creating the intent
                        myIntent.putExtra("LIST", (Serializable) RecipeNames); // Putting the list there
                        startActivity(myIntent); // Start new activity with the given intent
                        finish(); // End this activity


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.d(null, "---- !!!!!!onCancelled!!!!!! ----");


                    }
                });


            }//end onClick
        });
    }
}