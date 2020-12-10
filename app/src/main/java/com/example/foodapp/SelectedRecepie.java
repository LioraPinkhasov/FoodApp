package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class SelectedRecepie extends AppCompatActivity {



    Recipe choosen_recipe ;
    TextView r_header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_recepie);

        // Assuming I got Recipe recipe
/*
        Intent i = getIntent();
        choosen_recipe = (Recipe) i.getSerializableExtra("choosenRecipe");

        r_header = (TextView)findViewById(R.id.headline_textview);

        r_header.setText(choosen_recipe.toString());

*/
    }

    private void getIncomingIntent(){
        
        if(getIntent().hasExtra("image_url") && getIntent().hasExtra("image_name")){
            String imageUrl = getIntent().getStringExtra("image_url");
            String imageName = getIntent().getStringExtra("image_name");
            setImage(imageUrl, imageName);
        }
    }

    private void setImage(String imageUrl, String imageName) {

       TextView name = findViewById(R.id.image_description);
       name.setText(imageName);

       ImageView image = findViewById(R.id.image);
       Glide.with(this).asBitmap().load(imageUrl).into(image);
    }
}