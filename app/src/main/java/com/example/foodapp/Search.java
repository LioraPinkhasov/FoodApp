/*

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

    private MultiAutoCompleteTextView needToFind;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("server/saving-data/fireblog/posts");
        needToFind = (MultiAutoCompleteTextView) findViewById(R.id.MultiAutoCompleteTextView);

        ref.addValueEventListener(new ValueEventListener(){
            @Override
            public void onClick(View v) {

            }
        });
    }
}


 */