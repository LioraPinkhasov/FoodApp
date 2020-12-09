package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.List;

public class results_page extends AppCompatActivity implements RecyclerViewAdapter_forRecipes.ItemClickListener{

    RecyclerViewAdapter_forRecipes adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        //passing arrays using bundle in android
//        // https://stackoverflow.com/questions/5299532/passing-arrays-using-bundle-in-android
//
//        //in activity A -
//        //      String[] abc;
//        //
//        //      Bundle bundle =new Bundle();
//        //      bundle.putStringArray("some string",abcd);
//
//
//        // in Activity B
//
//        //      String abcd[]=bundle.getStringArray("some string");
//        //
//        //
//        //"some string" should be same in both case.
//
//        String[] received_Recipes =savedInstanceState.getStringArray("some string");

        /**
         * By this link , this is how peller gets my List from the intent
         * https://stackoverflow.com/questions/12092612/pass-list-of-objects-from-one-activity-to-other-activity-in-android/12092942 by Ruzin
         */
        Intent i = getIntent();
        List<Recipe> list = (List<Recipe>) i.getSerializableExtra("LIST");

        String[] received_Recipes = new String[list.size()];
        int j=0;
        for (Recipe r: list){
            String temp = "";
            temp = temp + r.getRecipeName();
            temp = temp + r.getCreate_time();
            //TODO add the likes/rating
            received_Recipes[j] = new String(temp);
            j++;
        }

        setContentView(R.layout.activity_results_page);

        RecyclerView _recyclerView = findViewById(R.id.myRecyclerView);
        _recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new RecyclerViewAdapter_forRecipes(this, received_Recipes);
        adapter.setClickListener(this);
        _recyclerView.setAdapter(adapter);






    }

    @Override
    public void onItemClick(View view, int position) {
        //do something once a certain recipe was clicked from the recyclerView
    }
}