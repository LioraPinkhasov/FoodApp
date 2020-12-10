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
    public interface OnGetDataListener {
        //this is for callbacks
        void onSuccess(DataSnapshot dataSnapshot);
        void onStart();
        void onFailure();
    }

    public void readData(Query ref, final OnGetDataListener listener) {
        listener.onStart();

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listener.onSuccess(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onFailure();
            }
        });
    }


    // Init needed views
    private Button sByingredient;
    private List<Recipe> recipesWithMatchSize;
    public Query query;
    private EditText ingData;
    private boolean got_data = false;
    CountDownLatch done = new CountDownLatch(1);



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
                        Intent myIntent = new Intent(getApplicationContext(), results_page.class); // Creating the intent
                        myIntent.putExtra("LIST" , (Serializable) matchedRcipes); // Putting the list there
                        startActivity(myIntent); // Start new activity with the given intent
                        finish(); // End this activity




                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.d(null, "---- !!!!!!onCancelled!!!!!! ----");
                        got_data = true;
                        done.countDown();

                    }
                });

//                List<Recipe> matchedRcipes = searchByIng(userInputIng);
                // 3) Pass an List of recipes to peller in the result_page by intent

                /**
                 * Please note that serialization can cause performance issues: it takes time, and a lot of objects will be allocated (and thus, have to be garbage collected).
                 */

                // Passing the matchedRecipes  as serilizable list to result_page activity
//                Intent myIntent = new Intent(getApplicationContext(), results_page.class); // Creating the intent
//                myIntent.putExtra("LIST" , (Serializable) matchedRcipes); // Putting the list there
//                startActivity(myIntent); // Start new activity with the given intent
//                finish(); // End this activity






            }//end onClick
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
                    //Toast.makeText(Search.this, "125", Toast.LENGTH_SHORT).show();
                    Log.d(null, "---- line 127 ----");

                }
                //Toast.makeText(Search.this, "128", Toast.LENGTH_SHORT).show();
                Log.d(null, "---- line 131 ----");

            }

        }

        @Override
        public void onCancelled(@NonNull DatabaseError error)
        {

        }
    };



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
        Log.d(null, "---- line 158 ----");
        query = FirebaseDatabase.getInstance().getReference("RecpieDetiels").orderByChild("numOfProducts").equalTo(size); // A query that get all the ingredient names
//        query = FirebaseDatabase.getInstance().getReference("RecpieDetiels").orderByChild("numOfProducts"); // A query that get all the ingredient names
//        FirebaseDatabase.getInstance().getReference("RecpieDetiels").orderByChild("numOfProducts").equalTo(size); // A query that get all the ingredient names
//        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("RecpieDetiels");


        query.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot DS) {
                recipesWithMatchSize.clear();
                Log.d(null, "---- line 172 ----");
                if (DS.exists()) {
                    for (DataSnapshot snapshot : DS.getChildren()) {
                        Recipe recipe = snapshot.getValue(Recipe.class);

                        recipesWithMatchSize.add(recipe);

                        //Toast.makeText(Search.this, "125", Toast.LENGTH_SHORT).show();
                        Log.d(null, "---- line 178 ----");

                    }
                    //Toast.makeText(Search.this, "128", Toast.LENGTH_SHORT).show();
                    Log.d(null, "---- line 182 ----");

                }
                got_data = true;
                done.countDown();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(null, "---- !!!!!!onCancelled!!!!!! ----");
                got_data = true;
                done.countDown();

            }
        });



        FirebaseDatabase mDatabase;
        DatabaseReference dbRef;
        mDatabase = FirebaseDatabase.getInstance();
        dbRef = mDatabase.getReference("RecpieDetiels");
//        dbRef.addListenerForSingleValueEvent();



        //Toast.makeText(Search.this, "149", Toast.LENGTH_SHORT).show();
        Log.d(null, "---- line 161 ----");

//        query.addListenerForSingleValueEvent(valueEventListener2); // Here we push the ingredient names into the recipesWithMatchSize.
//        query = FirebaseDatabase.getInstance().getReference("RecpieDetiels").orderByChild("numOfProducts").equalTo(size); //aa
        //Toast.makeText(Search.this, "153", Toast.LENGTH_SHORT).show();
        //Now we can use recipesWithMatchSize as we please
//        com.firebase.client.Firebase

//        readData(query, new OnGetDataListener(){
//
//            @Override
//            public void onSuccess(DataSnapshot DS) {
//                recipesWithMatchSize.clear();
//                for (DataSnapshot snapshot : DS.getChildren()) {
//                    Recipe recipe = snapshot.getValue(Recipe.class);
//
//                    recipesWithMatchSize.add(recipe);
//
////                    Toast.makeText(Search.this, "125", Toast.LENGTH_SHORT).show();
//                    Log.d(null, "---- readData ----");
//                }
//            }
//
//            @Override
//            public void onStart() {
//                //when starting
//                Log.d("ONSTART", "Started");
//            }
//
//            @Override
//            public void onFailure() {
//                Log.d("onFailure", "Failed");
//            }
//
//        });

        query.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Log.d(null, "---- onChildAdded ----");
                recipesWithMatchSize.clear();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    Recipe recipe = snap.getValue(Recipe.class);
                    recipesWithMatchSize.add(recipe);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Recipe temp = new Recipe(0, "1", "1", "1", "1", "1", 1, "1", "1");

        DatabaseReference.CompletionListener completionListener = new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference)
            {
                    if (databaseError != null)
                    {
                        Toast.makeText(Search.this,databaseError.getMessage(),Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(Search.this, "Saved!!", Toast.LENGTH_LONG).show();
                    }
            }
        };

        DatabaseReference dbRootRef = mDatabase.getReference();
        temp.setId(dbRootRef.push().getKey());
        dbRootRef.child("RecpieDetiels").child(temp.getId()).setValue(temp, completionListener);





//        FirebaseDatabase.getInstance().getReference("RecpieDetiels").

//        query.addListenerForSingleValueEvent(new ValueEventListener() {
//
//            @Override
//            public void onDataChange(@NonNull DataSnapshot DS) {
//                recipesWithMatchSize.clear();
//                Log.d(null, "---- line 172 ----");
//                if (DS.exists()) {
//                    for (DataSnapshot snapshot : DS.getChildren()) {
//                        Recipe recipe = snapshot.getValue(Recipe.class);
//
//                        recipesWithMatchSize.add(recipe);
//
//                        //Toast.makeText(Search.this, "125", Toast.LENGTH_SHORT).show();
//                        Log.d(null, "---- line 178 ----");
//
//                    }
//                    //Toast.makeText(Search.this, "128", Toast.LENGTH_SHORT).show();
//                    Log.d(null, "---- line 182 ----");
//
//                }
//                got_data = true;
//                done.countDown();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.d(null, "---- !!!!!!onCancelled!!!!!! ----");
//                got_data = true;
//                done.countDown();
//
//            }
//        });
//        query.keepSynced(true);








        // 3) Extract the Recipe Id of all recipes with a perfect Ingridient match.
        // First option is that we intersect them from  recipesWithMatchSize


//        while(!got_data) {
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        };
//        try {
//            done.await(); //it will wait till the response is received from firebase.
//        } catch (InterruptedException e){
//            e.printStackTrace();
//        }

        // Intersection algorithem
        List<Recipe> recipeMatched = new ArrayList<Recipe>();
        Log.d(null, "---- line 205 ----");
        for(Recipe recipe : recipesWithMatchSize){
            Log.d(null, "---- line 207 ----");
            String[] ingForRecipe = recipe.splitIngredients();
            boolean notMatch = false;
            for(int index = 0; index < size && !notMatch; index++){
                boolean ingFound = false;
                for(int innerIndex = 0; innerIndex < ingForRecipe.length && !ingFound; innerIndex++){

                    if(userInputIng.get(index).equals(ingForRecipe[innerIndex])){
                        ingFound = true;
                        Log.d(null, "---- line 216 ----");
                    }

                    if(!ingFound && innerIndex+1 ==  ingForRecipe.length){
                        Log.d(null, "---- line 220 ----");
                        notMatch = true;

                    }
                }
            }
            if(!notMatch){
                recipeMatched.add(recipe);
            }
        }



        Log.d(null, "---- line 233 ----");
        return recipeMatched;
    }


}
