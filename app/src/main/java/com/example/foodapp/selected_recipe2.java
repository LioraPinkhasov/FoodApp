package com.example.foodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class selected_recipe2 extends AppCompatActivity {

    Recipe choosen_recipe ;
    TextView r_name;
    TextView r_host;
    TextView r_ing;
    TextView r_how_to;
    ImageView r_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_recipe2);


        // Assuming I got Recipe recipe

        Intent i = getIntent();
        choosen_recipe = (Recipe) i.getSerializableExtra("choosenRecipe");

        // Show the recipe header
        r_name = (TextView)findViewById(R.id.recipe_header_view);
        r_host = (TextView)findViewById(R.id.textView_RecipeHost);

//       String recipe_name = choosen_recipe.getRecipeName();
//       String recipe_host = choosen_recipe.getHost();

        r_name.setText(choosen_recipe.getRecipeName());
        r_host.setText(choosen_recipe.getHost());



//        String header2 = recipe_name;
//
//        ///!!!!----19.12 -- liora: added line separator
//        header2 = header2.replace(",", System.getProperty("line.separator"));
//        r_name.setText(header2);


        //clicking the recipe host name displays all of his recipes
        r_host.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                List<Recipe> matchedRcipes = new ArrayList<Recipe>();
                Query AuthorQuery = FirebaseDatabase.getInstance().getReference("RecipeDetails").orderByChild("host");
                AuthorQuery.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            //for each recipe you get from the DB, check if it is from the same author, and if so, display it.
                            for (DataSnapshot data : snapshot.getChildren()) {
                                Recipe temp_recipe = data.getValue(Recipe.class);
                                if (temp_recipe.getHost().equals(choosen_recipe.getHost())){ //if current recipe has same author as currently displayed recipe
                                    matchedRcipes.add(temp_recipe);
                                }
                            }
                            // Passing the matchedRecipes  as serializable list to result_page activity
                            Intent myIntent = new Intent(getApplicationContext(), results_page.class); // Creating the intent
                            myIntent.putExtra("LIST", (Serializable) matchedRcipes); // Putting the list there
                            startActivity(myIntent); // Start new activity with the given intent
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }

                });
            }

        });

        // Show Ingredients

        r_ing = (TextView)findViewById(R.id.ing_textView);
        String ingredients = choosen_recipe.getProducts();
        String ingredients3 = ingredients;

        ///!!!!----19.12 -- liora: added line separator
        ingredients3 = ingredients3.replace(",", System.getProperty("line.separator"));
        r_ing.setText(ingredients3);

        // Show how to

        r_how_to = (TextView)findViewById(R.id.how_to_view);
        String how_to = choosen_recipe.getHowTo();

        ///!!!!----19.12 -- liora: added line separator
        String Howto = how_to;
        Howto = Howto.replace(",", System.getProperty("line.separator"));


        r_how_to.setText(Howto);

        //added image
        r_image = (ImageView)findViewById(R.id.dislike_buttn);
        String imageurl = choosen_recipe.getRimage();
        //String imageURi = choosen_recipe.getRimage();
        Toast.makeText(selected_recipe2.this, "hi", Toast.LENGTH_SHORT).show();

        if (imageurl!=null)
        {
            Glide.with(this).load(imageurl).into(r_image);

        }
        //Glide.with(this).load("https://firebasestorage.googleapis.com/v0/b/fooding-90639.appspot.com/o/Image%2F18f2abc0-b81b-4b62-9ad6-e0cffce6a89c?alt=media&token=e09122c5-9fab-4a69-ba62-dfa17cda9e29").into(r_image);

        Uri img;
        //r_image.setImageURI(img);







    }
}