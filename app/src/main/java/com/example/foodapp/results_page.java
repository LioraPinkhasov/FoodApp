package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;

public class results_page extends AppCompatActivity implements RecyclerViewAdapter_forRecipes.ItemClickListener{

    RecyclerViewAdapter_forRecipes adapter;
    List<Recipe> recived_list;
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
        recived_list = (List<Recipe>) i.getSerializableExtra("LIST");
        String[] received_Recipes = new String[recived_list.size()];
        int j=0;
        for (Recipe r: recived_list){
            String temp = "";
            temp = temp + r.getRecipeName() +" ";
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
//        string a = view.
//        Toast.makeText(results_page.this, "clicked", Toast.LENGTH_SHORT).show();
        Recipe choosen_recipe = recived_list.get(position);

        // Passing the choosen recipe  as serilizable list to SelectedRecipe activity
        Intent myIntent2 = new Intent(getApplicationContext(), selected_recipe2.class); // Creating the intent
        myIntent2.putExtra("choosenRecipe" , (Serializable) choosen_recipe); // Putting the Recipe there
        startActivity(myIntent2); // Start new activity with the given intent
        finish(); // End this activity


        //do something once a certain recipe was clicked from the recyclerView
    }
}