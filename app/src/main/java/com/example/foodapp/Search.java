package com.example.foodapp;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class Search extends AppCompatActivity
{





    // Init needed views
    private Button sByingredient;
    private List<Recipe> recipesWithMatchSize;
    public Query query;
    private EditText ingData;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

    // Connecting the XML to our Objects
        sByingredient = (Button) findViewById(R.id.by_ing_buttn) ;
        ingData = (EditText)findViewById(R.id.ingData_input); // This is the field were ingredient input is comming from
        recipesWithMatchSize = new ArrayList<>();
        // Creating Intent to pass forward to resualt page.





        sByingredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 1) Cast the Input into an  ArrayList<String> userInputIng

                String usrInput = ingData.getText().toString(); // This is the string from input
                
                usrInput = usrInput.replace(" " , ""); // Cutting off all the spaces for easier work.
                String[] splittedToArrayInput = usrInput.split(","); // Cut the string into an array of ingridients
                ArrayList<String> userInputIng = new ArrayList<>() ; // Init the list
                for( int i = 0 ; i < splittedToArrayInput.length ; i++)
                {
                    userInputIng.add(splittedToArrayInput[i]); // Fill the list
                }

                // 2) Pass userInputIng to  The Search Function by Ingridients searchByIng and store it in matchedRecipeList

                int size = userInputIng.size();
                String strSize = String.valueOf(size);

                query = FirebaseDatabase.getInstance().getReference("RecpieDetiels").orderByChild("numOfProducts").equalTo(size);
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
                                matchedRcipes.add(recipe);
                            }
                        }

                        // Passing the matchedRecipes  as serilizable list to result_page activity
                        // 3) Pass an List of recipes to peller in the result_page by intent
                         //* Please note that serialization can cause performance issues: it takes time, and a lot of objects will be allocated (and thus, have to be garbage collected).

                                Intent myIntent = new Intent(getApplicationContext(), results_page.class); // Creating the intent
                        myIntent.putExtra("LIST" , (Serializable) matchedRcipes); // Putting the list there
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
