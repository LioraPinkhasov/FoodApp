package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import java.util.List;
import java.util.ArrayList;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainUserActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference dbRootRef ;

    TextView thedata;
    Button showData;
    List<Ingredient> ingredientList;

    // Holders for queries
   // private ArtistsAdapter adapter;
   // private List<Artist> artistList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user);

        thedata = (TextView)findViewById(R.id.showDataText);
        showData = (Button)findViewById(R.id.showDataButton);
        ingredientList = new ArrayList<Ingredient>();


        /// Trying to fetch info from DB

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
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        Query q1 = FirebaseDatabase.getInstance().getReference("Products").orderByChild("C").equalTo("Baking Soda");
        q1.addListenerForSingleValueEvent(valueEventListener);


        // Creating instances of:

        //database = FirebaseDatabase.getInstance();
        //dbRootRef = database.getReference();


        // First toy example

      /*  showData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {


            /*  // Toy example 2
                dbRootRef = FirebaseDatabase.getInstance().getReference().child("Products").child("1");
                dbRootRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot)
                    {
                        String myInfo = snapshot.child("A").getValue().toString(); // Supposed to retrive B: ?
                        thedata.setText(myInfo);
                       // Toast.makeText(MainUserActivity.this, myInfo, Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                // Toy example 2



            }
        });*/



        // Still in create
        String ing1 = "Baking Soda";
        showData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                thedata.setText(ingredientList.get(0).toString());
            }
        });
    }


    public void addRecipe(View view)
    {
        startActivity(new Intent(getApplicationContext(),addRecipe.class)); // Send the user to the rigister/login activity
        finish();

    }

    public void logout(View view)
    {
        FirebaseAuth.getInstance().signOut(); // Logout of a user
        startActivity(new Intent(getApplicationContext(),register_login.class)); // Send the user to the rigister/login activity
        finish();
    }





        public void onCancelled(DatabaseError databaseError)
        {
            Toast.makeText(MainUserActivity.this, databaseError.toString(), Toast.LENGTH_SHORT).show();

        }
    };


/**
    for(
    DataSnapshot data : dataSnapshot.getChildren())
            * {
     *     Post p = data.getValue(Post.class);
     *     posts.add(p);
     * }
     *allPostAdapter = new AllPostAdapter(AllPostActivity.this,0,0,posts);
     *Iv.setAdapter(allPostAdapter
     */

