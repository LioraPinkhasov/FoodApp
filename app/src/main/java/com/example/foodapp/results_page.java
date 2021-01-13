package com.example.foodapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class results_page extends AppCompatActivity implements ItemClickListener{


    Button sortBy;
    int nextSortByIsByLikes = -1;

    RecyclerViewAdapter_forRecipes adapter;
    List<Recipe> recived_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_page);

        sortBy = (Button) findViewById(R.id.button_SortBy);
        sortBy.setText("Sort By Date");

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
//        String[] received_Recipes = new String[recived_list.size()];
//        int j=0;
//        for (Recipe r: recived_list){
//            String temp = "";
//            temp = temp + r.getRecipeName() +" ";
//            temp = temp + r.getCreate_time();
//            //TODO add the likes/rating
//            received_Recipes[j] = new String(temp);
//            j++;
//        }

        Collections.sort(recived_list, new RecipeLikeComparator()); //default order is by likes
        RecyclerView _recyclerView = findViewById(R.id.myRecyclerView);
        _recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new RecyclerViewAdapter_forRecipes(this, recived_list);
        adapter.setClickListener(this);
        _recyclerView.setAdapter(adapter);

        //divider between each object in the recyclerView.
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(_recyclerView.getContext(),
                 LinearLayoutManager.VERTICAL);
        _recyclerView.addItemDecoration(dividerItemDecoration);

        sortBy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nextSortByIsByLikes == 1) {
                    Collections.sort(recived_list, new RecipeLikeComparator());
                    nextSortByIsByLikes = -1;
                    sortBy.setText("Sort By Date");
                }
                else {
                    Collections.sort(recived_list, new RecipeDateComparator());
                    nextSortByIsByLikes = 1;
                    sortBy.setText("Sort By Likes");
                }
                adapter.notifyDataSetChanged();
            }
        });



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
//        finish(); // End this activity


        //do something once a certain recipe was clicked from the recyclerView
    }

    class RecipeDateComparator implements Comparator<Recipe>{

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public int compare(Recipe r1, Recipe r2) {
            //get the create time of both recipes
            //convert them to LocalDateTime objects, with the relevant formatting
            //compare them, return 1 if 1 is bigger, else -1
            String r1_time = r1.getCreate_time();
            String r2_time = r2.getCreate_time();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            LocalDateTime r1_dateTime = LocalDateTime.parse(r1_time, dtf);
            LocalDateTime r2_dateTime = LocalDateTime.parse(r2_time, dtf);
            if (r1_dateTime.isBefore(r2_dateTime)) return 1;
            else return -1;
        }
    }

    class RecipeLikeComparator implements Comparator<Recipe>{

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public int compare(Recipe r1, Recipe r2) {
            //get ratings of both recipes, compare them and return 1 if r1 is grater, else return -1
            int r1_likes = r1.getRating();
            int r2_likes = r2.getRating();

            if (r1_likes > r2_likes) return 1;
            else return -1;
        }
    }
}