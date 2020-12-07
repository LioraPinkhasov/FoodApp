package com.example.foodapp;

import android.view.View;
import android.widget.MultiAutoCompleteTextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import java.util.List;
import java.util.ArrayList;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class Search extends AppCompatActivity {


    /**
     * **********Roies codes tests*************
     * by keren: class to use DataSnapshot , Post
     *
     * // dataSnapshot is an onject that returns from an event
     *
     * for(DataSnapshot data : dataSnapshot.getChildren())
     * {
     *     Post p = data.getValue(Post.class);
     *     posts.add(p);
     * }
     *allPostAdapter = new AllPostAdapter(AllPostActivity.this,0,0,posts);
     *Iv.setAdapter(allPostAdapter);
     */

    private MultiAutoCompleteTextView needToFind;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("server/saving-data/fireblog/posts");
        needToFind = (MultiAutoCompleteTextView) findViewById(R.id.MultiAutoCompleteTextView);

       // ref.addValueEventListener(new ValueEventListener(){
   //         @Override
     //       public void onClick(View v) {

      //      }
      //  });
    }
}