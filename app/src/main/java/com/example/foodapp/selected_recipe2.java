package com.example.foodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    ImageButton like_button,dislike_button;
    Button edit_approve_recipe_button;
    private FirebaseAuth mAuth;
    //private List<Auser> matchedAdminUsers;
    public ArrayAdapter<Admin> adaptAdmin;
    public Query query;
    
    

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_recipe2);
    
        // Buttons
    
        like_button = (ImageButton)findViewById(R.id.like_buttn);
        dislike_button = (ImageButton)findViewById(R.id.dislike_button);
        
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
        
        // Setting edit approve and make it Invisiable
        edit_approve_recipe_button = (Button)findViewById(R.id.edit_approve_button);
        edit_approve_recipe_button.setVisibility(View.INVISIBLE);
        
        // This section gets the current user for rating system
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser(); // getting user Info from Authentication system
        String currUser = currentUser.getEmail().toLowerCase(); // host is now our email.
        adaptAdmin = new ArrayAdapter<Admin>(this, android.R.layout.simple_list_item_1);
       
    
    
        // Editting / Approve recipe
        // 1) check if admin and show the button;
    
    
        query = FirebaseDatabase.getInstance().getReference("Admins");
        query.addListenerForSingleValueEvent(new ValueEventListener()
        {
        
            @Override
            public void onDataChange(@NonNull DataSnapshot DS)
            {
    
                adaptAdmin.clear();
                if (DS.exists()) {
                    for (DataSnapshot snapshot : DS.getChildren()) {
                        Admin admin = snapshot.getValue(Admin.class);
                        adaptAdmin.add(admin);
                    }
                }
            
            }
        
        
            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                Log.d(null, "---- !!!!!!onCancelled!!!!!! ----");
            }
        
        
        });
    
        if(!(adaptAdmin.isEmpty()))
        {
            edit_approve_recipe_button.setVisibility(View.VISIBLE);
        }
        
        
        
        //2) go to approve_recipes when clicked;
        edit_approve_recipe_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Passing the choosen recipe  as serilizable list to SelectedRecipe activity
                Intent myIntent2 = new Intent(getApplicationContext(), Recpie_Admin_permmision.class); // Creating the intent
                myIntent2.putExtra("adminChoosenRecipe" , (Serializable) choosen_recipe); // Putting the Recipe there
                startActivity(myIntent2); // Start new activity with the given intent
            
            }
        });
    
        ////////////////////////////
    
       
        


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

        /**
         * This section is the like/dislike buttons ,  pressing on them ,  this choosen recipe is rated in the DB.
         */
        like_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
               // FirebaseUser currentUser = mAuth.getCurrentUser(); // getting user Info from Authentication system
               // String host = currentUser.getEmail().toLowerCase(); // host is now our email.
                if(choosen_recipe.addRating(currUser ,'+'))
                {
                    Toast.makeText(selected_recipe2.this, "Thanks for liking this recipe!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(selected_recipe2.this, "Your rating was not added", Toast.LENGTH_SHORT).show();
                }

            }
        });

        dislike_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //FirebaseUser currentUser = mAuth.getCurrentUser(); // getting user Info from Authentication system
               // String host = currentUser.getEmail().toLowerCase(); // host is now our email.
                if(choosen_recipe.addRating(currUser ,'-'))
                {
                    Toast.makeText(selected_recipe2.this, "Wasn't the recipe any good?", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(selected_recipe2.this, "Your rating was not added", Toast.LENGTH_SHORT).show();
                }
                
            }
        });







    }
    /** This method is depricated
    public boolean isAdmin(String user)
    {
        
        ////////////////////////////
        boolean isAdmin = false;
        query = FirebaseDatabase.getInstance().getReference("Admins").orderByChild("email").equalTo(user);
        query.addListenerForSingleValueEvent(new ValueEventListener()
        {
        
            @Override
            public void onDataChange(@NonNull DataSnapshot DS) {
                
                matchedAdminUsers.clear();
                if (DS.exists()) {
                    for (DataSnapshot snapshot : DS.getChildren()) {
                        Auser admin = snapshot.getValue(Auser.class);
                        matchedAdminUsers.add(admin) ;
                    }
                }
                 isAdmin = !(matchedAdminUsers.isEmpty());
                
               
            }
        
            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                Log.d(null, "---- !!!!!!onCancelled!!!!!! ----");
            }
            
            
        });
    
        return isAdmin;
        ////////////////////////////
       
    }*/
    
    
        
}