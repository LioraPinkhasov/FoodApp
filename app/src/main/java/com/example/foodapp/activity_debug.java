package com.example.foodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class activity_debug extends AppCompatActivity {

    private Button button_ACTIVITY_MAIN_USER;
    private Button button_ACTIVITY_REGISTER_LOGIN;
    private Button button_ACTIVITY_SEARCH;
    private Button button_ACTIVITY_RESULTS_PAGE;
    private Button button_ACTIVITY_ADD_RECIPE;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug);

        button_ACTIVITY_MAIN_USER = (Button) findViewById(R.id.button_ACTIVITY_MAIN_USER);
        button_ACTIVITY_REGISTER_LOGIN = (Button) findViewById(R.id.button_ACTIVITY_REGISTER_LOGIN);
        button_ACTIVITY_SEARCH = (Button) findViewById(R.id.button_ACTIVITY_SEARCH);
        button_ACTIVITY_RESULTS_PAGE = (Button) findViewById(R.id.button_ACTIVITY_RESULTS_PAGE);
        //button_ACTIVITY_PHOTO_UPLODE = (Button)findViewById(R.id.button_ACTIVITY_PHOTO_UPLODE);
        button_ACTIVITY_ADD_RECIPE = (Button) findViewById(R.id.button_ACTIVITY_ADD_RECIPE);

        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();

        mAuth.signInWithEmailAndPassword("admin@admin.com", "adminadmin").addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(activity_debug.this, "Logged in as admin@admin.com", Toast.LENGTH_SHORT).show();
                }
            }
        });

        button_ACTIVITY_MAIN_USER.setOnClickListener(new View.OnClickListener() {
            //login as admin@admin.com
            //send to main user activity, with flag of admin=true
            @Override
            public void onClick(View v) {
                Intent myLoginIntent = new Intent(getApplicationContext(), MainUserActivity.class); // For testing
                myLoginIntent.putExtra("isAdmin", true); // Putting the list there
                startActivity(myLoginIntent); // Start new activity with the given intent

            }
        });

        button_ACTIVITY_REGISTER_LOGIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), register_login.class));
            }
        });

        button_ACTIVITY_SEARCH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Search.class));
            }
        });

       /* button_ACTIVITY_PHOTO_UPLODE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                startActivity(new Intent(getApplicationContext(), PhotoUplode.class));
            }
        });*/

        button_ACTIVITY_ADD_RECIPE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), addRecipe.class));
            }
        });

        button_ACTIVITY_RESULTS_PAGE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //imititaing the behaviour of process to call search results
//                public Recipe( int approved ,String create_time ,String host ,  String howTo , String id,  String measures ,int numOfProducts , String products , String recipeName    )
                Recipe r1 = new Recipe(1, "123", "host_1", "justDoIt1", "1", "1", 1, "sugar", "recipie1", "");
                Recipe r2 = new Recipe(1, "234", "host_2", "justDoIt2", "2", "2", 1, "spice", "recipie2", "");
                Recipe r3 = new Recipe(1, "345", "host_3", "justDoIt3", "3", "3", 1, "everything_nice", "recipie3", "");

                ArrayList<String> userInputIng = new ArrayList<>(); // Init the list
                List<Recipe> matchedRcipes = new ArrayList<Recipe>();
                matchedRcipes.add(r1);
                matchedRcipes.add(r2);
                matchedRcipes.add(r3);
//                Search s = new Search();
//                List<Recipe> matchedRcipes = s.searchByIng(userInputIng);
                Intent myIntent = new Intent(getApplicationContext(), results_page.class); // Creating the intent
                myIntent.putExtra("LIST", (Serializable) matchedRcipes); // Putting the list there
                startActivity(myIntent); // Start new activity with the given intent
                finish(); // End this activity
            }
        });


    }

    public void uplodephoto(View V) {
        startActivity(new Intent(getApplicationContext(), PhotoUplode.class)); // Send the user to the rigister/adnim main
//        finish();

    }
}
