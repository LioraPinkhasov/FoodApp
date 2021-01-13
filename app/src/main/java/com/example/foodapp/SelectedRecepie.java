package com.example.foodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.basic_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.item1:
                startActivity(new Intent(getApplicationContext(), Contact_us.class));
                return true;

            case R.id.item2:
                startActivity(new Intent(getApplicationContext(), register_login.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}