package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.List;

public class results_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_page);

        /**
         * By this link , this is how peller gets my List from the intent
         * https://stackoverflow.com/questions/12092612/pass-list-of-objects-from-one-activity-to-other-activity-in-android/12092942 by Ruzin
         */
        Intent i = getIntent();
        List<Recipe> list = (List<Recipe>) i.getSerializableExtra("LIST");

    }
}