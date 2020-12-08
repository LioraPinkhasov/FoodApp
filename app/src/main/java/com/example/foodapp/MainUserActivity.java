package com.example.foodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainUserActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference dbRootRef ;

    TextView thedata;
    Button showData;

    // Holders for queries
   // private ArtistsAdapter adapter;
   // private List<Artist> artistList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user);

        thedata = (TextView)findViewById(R.id.showDataText);
        showData = (Button)findViewById(R.id.showDataButton);



        /// Trying to fetch info from DB



        // Creating instances of:

        //database = FirebaseDatabase.getInstance();
        //dbRootRef = database.getReference();


        /**
        // First toy example

        showData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {


              // Toy example 2
                dbRootRef = FirebaseDatabase.getInstance().getReference().child("Products").child("1");
                dbRootRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot)
                    {
                        String myInfo = snapshot.child("A").getValue().toString(); // Supposed to retrive B: ?
                        thedata.setText(myInfo);
                       // Toast.makeText(MainUserActivity.this, myInfo, Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                // Toy example 2

                Query query1 = FirebaseDatabase.getInstance().getReference("Products").orderByChild()


            }
        });
        */
        // Still in create
        String ing1 = "Baking Soda";
        showData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Query q1 = FirebaseDatabase.getInstance().getReference("Products").orderByChild("C").equalTo("Baking Soda");
                thedata.setText(q1.toString());
               q1.addListenerForSingleValueEvent(valueEventListener);

            }
        });
    }


    public void addRecipe(View view)
    {
        startActivity(new Intent(getApplicationContext(),addRecipe.class)); // Send the user to the rigister/login activity
        finish();

    }

    public void logout(View view)
    {
        FirebaseAuth.getInstance().signOut(); // Logout of a user
        startActivity(new Intent(getApplicationContext(),register_login.class)); // Send the user to the rigister/login activity
        finish();
    }




        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };


/**
    for(
    DataSnapshot data : dataSnapshot.getChildren())
            * {
     *     Post p = data.getValue(Post.class);
     *     posts.add(p);
     * }
     *allPostAdapter = new AllPostAdapter(AllPostActivity.this,0,0,posts);
     *Iv.setAdapter(allPostAdapter
     */

}