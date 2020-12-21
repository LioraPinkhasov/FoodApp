package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class selected_recipe2 extends AppCompatActivity {

    Recipe choosen_recipe ;
    TextView r_header;
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
        r_header = (TextView)findViewById(R.id.recipe_header_view);
        String header = choosen_recipe.toString();
        String header2 = header;

        ///!!!!----19.12 -- liora: added line separator
        header2 = header2.replace(",", System.getProperty("line.separator"));
        r_header.setText(header2);

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
        r_image = (ImageView)findViewById(R.id.imageView8);
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