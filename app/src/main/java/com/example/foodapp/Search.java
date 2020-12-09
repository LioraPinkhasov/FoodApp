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
    private List<Recipe> recipesWithMatchSize;
    private Query query;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //  Making the IngridientList filled with all the Ingredients from the DB
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
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        };



        ingredientList = new ArrayList<>();
        query = FirebaseDatabase.getInstance().getReference("Products").orderByChild("C"); // A query that get all the ingredient names
        query.addListenerForSingleValueEvent(valueEventListener); // Here we push the ingredient names into the ingredientList
        // Connecting views from XML to our Objects
        sByingredient = (Button) findViewById(R.id.by_ing_buttn) ;
        needToFind = (MultiAutoCompleteTextView)findViewById(R.id.AutoCompIngList);
        //
        String[] tmpArr = new String[ingredientList.size()];
        int index = 0;
        for(Ingredient ingredient : ingredientList) {
            tmpArr[index] = ingredient.getC();
        }

        ArrayAdapter<String> ingredientAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tmpArr);
        needToFind.setAdapter(ingredientAdapter);
        needToFind.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());








        needToFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });






    }





    //  Making the IngridientList filled with all the Ingredients from the DB
    ValueEventListener valueEventListener2 = new ValueEventListener() {// Insert the query results into the ingredientList
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            recipesWithMatchSize.clear();
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Recipe recipe = snapshot.getValue(Recipe.class);
                    recipesWithMatchSize.add(recipe);
                }
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error)
        {

        }
    };

    // The Search Function by Ingridients

    /**
     *
     * @param userInputIng a String list
     * @return
     */
    public List<Recipe> searchByIng(ArrayList<String> userInputIng)
    {
        // 1) How many ingredients?
        int size = userInputIng.size();
        String strSize = String.valueOf(size);

        // 2) Querry all the recipes of size "size".

        query = FirebaseDatabase.getInstance().getReference("Recipe").orderByChild("numOfProducts").equalTo(strSize); // A query that get all the ingredient names
        query.addListenerForSingleValueEvent(valueEventListener2); // Here we push the ingredient names into the ingredientList

        //Now we can use recipesWithMatchSize as we please



        // 3) Extract the Recipe Id of all recipes with a perfect Ingridient match.
        // First option is that we intersect them from  recipesWithMatchSize

        // Intersection algorithem
        List<Recipe> recipeMatched = new ArrayList<Recipe>();
        for(Recipe recipe : recipesWithMatchSize){
            String[] ingForRecipe = recipe.splitIngredients();
            boolean notMatch = false;
            for(int index = 0; index < size && !notMatch; index++){
                boolean ingFound = false;
                for(int innerIndex = 0; innerIndex < ingForRecipe.length && !ingFound; innerIndex++){

                    if(userInputIng.get(index).equals(ingForRecipe[innerIndex])){
                        ingFound = true;
                    }

                    if(!ingFound && innerIndex+1 ==  ingForRecipe.length){
                        notMatch = true;

                    }
                }
            }
            if(!notMatch){
                recipeMatched.add(recipe);
            }
        }




        return recipeMatched;
    }


}

