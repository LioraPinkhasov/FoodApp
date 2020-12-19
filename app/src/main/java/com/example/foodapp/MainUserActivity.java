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
    private Button button_Admin_Options;

    boolean isAdmin = false;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user);

        //Make admin button invisible by default
        button_Admin_Options = (Button)findViewById(R.id.button_Admin_Options);
        button_Admin_Options.setVisibility(View.INVISIBLE);

        // Get intent if admin or not. If admin logged in - make admin button visible
        Intent i = getIntent();
        isAdmin = i.getBooleanExtra("isAdmin",false);
        if(isAdmin) {button_Admin_Options.setVisibility(View.VISIBLE);}

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

    public void AdminOp(View view)
    {
        startActivity(new Intent(getApplicationContext(),MainAdminActivity.class)); // Send the user to the rigister/adnim main
//        finish();

    }

    public void Recpieperm(View view)
    {
        startActivity(new Intent(getApplicationContext(),Recpie_Admin_permmision.class)); // Send the user to the rigister/adnim main
//        finish();

    }

    public void uplodephoto(View view)
    {
        startActivity(new Intent(getApplicationContext(),PhotoUplode.class)); // Send the user to the rigister/adnim main
//        finish();

    }



}
