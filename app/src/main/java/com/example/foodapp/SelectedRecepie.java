package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class SelectedRecepie extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_recepie);
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