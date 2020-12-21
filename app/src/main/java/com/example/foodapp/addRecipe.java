package com.example.foodapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.UUID;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class addRecipe extends AppCompatActivity
{
    // Views decleration
    Button sendRecipe;
    Button addPhoto;
    EditText recipeName;
    EditText ingridientsList;
    EditText howToDescription;
    String generatedFilePath;


    // DB decleration

    FirebaseDatabase mDatabase;
    DatabaseReference dbRecipeRef;
    private FirebaseAuth mAuth;

    // 21.12 addition
    private Button choose, uplode;
    private ImageView imageView9;

    private Uri filePath;

    private final int PICK_IMAGE_REQUEST = 71;


    FirebaseStorage mDatabase2;
    StorageReference dbRecipeRef2;


    private void chooseImage()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    // Override onActivityResult method
    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data)
    {

        super.onActivityResult(requestCode,
                resultCode,
                data);

        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and
        // resultCode is RESULT_OK
        // then set image in the image view
        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            // Get the Uri of data
            filePath = data.getData();
            try {

                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                getContentResolver(),
                                filePath);
                imageView9.setImageBitmap(bitmap);
            }

            catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }

    // UploadImage method
    private void uploadImage() {
        if (filePath != null) {
            // Code for showing progressDialog while uploading
            ProgressDialog progressDialog
                    = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            // Defining the child of storageReference
            String uid = UUID.randomUUID().toString();
            dbRecipeRef2 = mDatabase2.getReference().child("Image/" + uid);

            // adding listeners on upload
            // or failure of image
            dbRecipeRef2.putFile(filePath).addOnSuccessListener(
                    new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(

                                /////////////!!!!!!--------------------->my baby is a liveeeeeeee!!!!

                                UploadTask.TaskSnapshot taskSnapshot) {
                            //Uri  downloadUrl = filePath;
                            Task<Uri> downloadUrl = taskSnapshot.getStorage().getDownloadUrl();
                            //Bitmap bitmap = BitmapFactory.decodeFile(downloadUrl.)
                            //String generatedFilePath = downloadUrl.getPath().toString();
                           // String generatedFilePath = downloadUrl.getResult().toString();
                            generatedFilePath = downloadUrl.getResult().toString();



                            //System.out.println("## Stored path is "+generatedFilePath);

                            /////////////////////////////-------------->

                            // Image uploaded successfully
                            // Dismiss dialog
                            progressDialog.dismiss();
                            Toast
                                    .makeText(addRecipe.this,
                                            "Image Uploaded!!",
                                            Toast.LENGTH_SHORT)
                                    .show();
                        }



                    })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            // Error, Image not uploaded
                            progressDialog.dismiss();
                            Toast
                                    .makeText(addRecipe.this,
                                            "Failed " + e.getMessage(),
                                            Toast.LENGTH_SHORT)
                                    .show();
                        }
                    })
                    .addOnProgressListener(
                            new OnProgressListener<UploadTask.TaskSnapshot>() {

                                // Progress Listener for loading
                                // percentage on the dialog box
                                @Override
                                public void onProgress(
                                        UploadTask.TaskSnapshot taskSnapshot) {
                                    double progress
                                            = (100.0
                                            * taskSnapshot.getBytesTransferred()
                                            / taskSnapshot.getTotalByteCount());
                                    progressDialog.setMessage(
                                            "Uploaded "
                                                    + (int) progress + "%");
                                }
                            });

           ////try
            //try
            mDatabase2.getReference().child("Image/" + uid).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri)
                {
                    String a = uri.toString();



                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                }
            });

            /////
        }}


    //end addition 21.12


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        // Init buttons and EditText

        sendRecipe = (Button)findViewById(R.id.send_recipe_button);
       // addPhoto = (Button)findViewById(R.id.add_photo_button);
        recipeName = (EditText)findViewById(R.id.recipeName_TextView);
        ingridientsList = (EditText)findViewById(R.id.ingredient_list_text);
        howToDescription = (EditText)findViewById(R.id.wrkProgress_text);
        //Rphoto = (EditText)findViewById(R.id.)


        // Init DB references
        mDatabase = FirebaseDatabase.getInstance();
        dbRecipeRef = mDatabase.getReference().child("RecpieDetiels");
        // Initing FirebaseAuth instance
        mAuth = FirebaseAuth.getInstance();


        //additon 21.12 add photo
        ///Initialize Views
        addPhoto = (Button) findViewById(R.id.add_photo_button);
        //uplode = (Button) findViewById(R.id.uplode);
        imageView9 = (ImageView) findViewById(R.id.imageView9);

        // Init DB references
        //mDatabase = FirebaseDatabase.getInstance();
        mDatabase2 = FirebaseStorage.getInstance();
        //dbRecipeRef = mDatabase.getReference().child("Image/"+ UUID.randomUUID().toString());
        // Initing FirebaseAuth instance
        //mAuth = FirebaseAuth.getInstance();


        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });


       /* uplode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });*/

        //end addition 21.12

        // Clicking sendRecipe
        sendRecipe.setOnClickListener(new View.OnClickListener()
        {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onClick(View v)
        {
            // Parts of recipe
            String rName = recipeName.getText().toString().toLowerCase().trim(); // Should I use trim?
            String rIngridients = ingridientsList.getText().toString().toLowerCase().trim();
            String howTo = howToDescription.getText().toString().trim();;

            //21.12 addition uplode photo
            uploadImage();
            //image url
            //String rImage = ""; //TODO: get link to image in 55
            String rImage;
            if (generatedFilePath!=null)
            {
                 rImage = generatedFilePath;
            }
            else
                 rImage = "";
            //end of additon 21.12

            // 1) Check if any field is empty and show toast ,  else put them inside a string;

            if(TextUtils.isEmpty(rName))
            {
                recipeName.setError("Recipe name is Required!");
                return;
            }
            if(TextUtils.isEmpty(rIngridients))
            {
                ingridientsList.setError("Recipe must have Ingredients");
                return;
            }

            if(TextUtils.isEmpty(howTo))
            {
                howToDescription.setError("Please tell us how to make the recipe");
                return;
            }
            // 2) Create a Recipe Object

            // 2.a) count different ingredients by counting the num of commas -1
            int commas = 0;
            for(int i = 0; i < rIngridients.length(); i++)
            {
                if(rIngridients.charAt(i) == ',') commas++;
            }
            // 2.b ) Establish current Date
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDateTime now = LocalDateTime.now();
            String currentDate = now.toString();
            // 2.c ) User id is given by DBfirebase and I update in when putting the recipe in DB
            String id = "";
            // 2.d) Extract and put user email in the host string
            FirebaseUser currentUser = mAuth.getCurrentUser(); // getting user Info from Authentication system
            String host = currentUser.getEmail().toLowerCase(); // host is now our email.
            // 2.e)
            String measures = "How do I get the measures?";
            // 3) Store the recipe in DB







            //!!!!!!!-> 19.12 ->liora added anoter tab for use.
            //Recipe newRecipe = new Recipe( 0 , currentDate ,host,howTo ,id ,measures , commas+1 ,rIngridients , rName ); // Creating the new recipe
            Recipe newRecipe = new Recipe( 0 , currentDate ,host,howTo ,id ,measures , commas+1 ,rIngridients , rName , rImage); // Creating the new recipe
            newRecipe.setId(dbRecipeRef.push().getKey());
            dbRecipeRef.child(newRecipe.getId()).setValue(newRecipe);
            // 4) Toast and move to main page.
            Toast.makeText(addRecipe.this, "Thank you ! your recipe is added ! and will be visible when aproved by admin. ", Toast.LENGTH_SHORT).show();
            //startActivity(new Intent(getApplicationContext(), MainUserActivity.class)); // This is the proper path!
            finish();


        }


        //add pic for firebase





    });

        // End of click on Add recipe
    addPhoto.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {



        }
    });

    // End of add photo


    }




}