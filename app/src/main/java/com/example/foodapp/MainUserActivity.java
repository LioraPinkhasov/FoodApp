package com.example.foodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MainUserActivity extends AppCompatActivity {
    private Button button_Admin_Options;
    private FirebaseAuth mAuth;
    private boolean isAdmin = false;
    Button addRecipe_button;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user);
        
        addRecipe_button = (Button)findViewById(R.id.add_recipe);
        addRecipe_button.setVisibility(View.INVISIBLE);
        //Make admin button invisible by default
        button_Admin_Options = (Button)findViewById(R.id.button_Admin_Options);
        button_Admin_Options.setVisibility(View.INVISIBLE);

        // Get intent if admin or not. If admin logged in - make admin button visible
        Intent i = getIntent();
        isAdmin = i.getBooleanExtra("isAdmin",false);
        if(isAdmin) {button_Admin_Options.setVisibility(View.VISIBLE);}
        
        // Check anon user , and set UI accordingly
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if(!user.isAnonymous())
        {
           addRecipe_button.setVisibility(View.VISIBLE);
        }
        

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.basic_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.item1:
                startActivity(new Intent(getApplicationContext(), Contact_us.class));
                return true;

            case R.id.item2:
                startActivity(new Intent(getApplicationContext(), register_login.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
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
