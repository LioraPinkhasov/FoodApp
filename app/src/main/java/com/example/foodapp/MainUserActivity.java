package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class MainUserActivity extends AppCompatActivity {
boolean isAdmin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user);

        // Get intent if admin or not
        Intent i = getIntent();
        isAdmin = i.getBooleanExtra("isAdmin",false);


        if(isAdmin)
        {


        }

    }

    public void addRecipe(View view)
    {
        startActivity(new Intent(getApplicationContext(),addRecipe.class)); // Send the user to the rigister/login activity
//        finish();

    }

    public void logout(View view)
    {
        FirebaseAuth.getInstance().signOut(); // Logout of a user
        startActivity(new Intent(getApplicationContext(),register_login.class)); // Send the user to the rigister/login activity
        finish();
    }

    public void goToSearch(View view)
    {
        startActivity(new Intent(getApplicationContext(),Search.class)); // Send the user to the rigister/login activity
//        finish();

    }


}
