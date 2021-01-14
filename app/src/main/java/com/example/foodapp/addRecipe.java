package com.example.foodapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class addRecipe extends AppCompatActivity {
    // Views decleration
    Button sendRecipe;
    Button addPhoto;
    EditText recipeName;
//    EditText ingridientsList;
    EditText howToDescription;
    String generatedFilePath;
    Button takePhoto;
    String[] storagePermissions;
    String[] cameraPermissions;

    public ArrayAdapter<String> adaptIng;
    private MultiAutoCompleteTextView ingData;
    private Query ingredientQuery;

    private static final int REQUST_IMAGE_CAPTURE = 101;


    // DB decleration

    FirebaseDatabase mDatabase;
    DatabaseReference dbRecipeRef;
    private FirebaseAuth mAuth;

    // 21.12 addition
    private Button choose, uplode;
    private ImageView imageView9;

    public Uri image_uri;

    private final int PICK_IMAGE_REQUEST = 71;
    private static final int PICK_IMAGE_FROM_CAMERA_REQUEST = 400;
    private static final int STORAGE_REQUEST = 200;


    FirebaseStorage mDatabase2;
    StorageReference dbRecipeRef2;


    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
        // var for url
        // send to upload image
//        uploadImage();
    }

    // Override onActivityResult method
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and
        // resultCode is RESULT_OK
        // then set image in the image view
        if (resultCode == RESULT_OK && data != null) {
            if(requestCode == PICK_IMAGE_REQUEST)
            // Get the Uri of data
            if(requestCode == PICK_IMAGE_REQUEST) { //case of pick from gallery. This test and extra step is not needed for camera.
                if(data.getData() != null){
                    image_uri = data.getData();
                }
                else{
                    return; //got picture form gallery, but it has an error - data.getData() = null
                }
            }
            try {
                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), image_uri);
                imageView9.setImageBitmap(bitmap); //shows the picture on the activity add recipe
                uploadImage();
            } catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
       /* if(requestCode == PICK_IMAGE_FROM_CAMERA_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) // Roie v1 for take photo
        {
           // Get capture image
           Bitmap captureImage = (Bitmap) data.getExtras().get("data");
           //Set Capture Iamge
            imageView9.setImageBitmap(captureImage);
            image_uri = data.getData();
            uploadImage();
            
        } */
    }

    // UploadImage method
    private void uploadImage() {
        Log.d(null, "line 125");
        if (image_uri != null) {
            Log.d(null, "line 127");
            // Code for showing progressDialog while uploading
            ProgressDialog progressDialog
                    = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            Log.d(null, "line 133");
            // Defining the child of storageReference
            String uid = UUID.randomUUID().toString();
            dbRecipeRef2 = mDatabase2.getReference().child("Image/" + uid);
            Log.d(null, "line 137");

            // adding listeners on upload
            // or failure of image
//            dbRecipeRef2.putFile(filePath);


//            dbRecipeRef2.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                                                                    @Override
//                                                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                                                                        /////////////!!!!!!--------------------->my baby is a liveeeeeeee!!!!
//
//                                                                        //Uri  downloadUrl = filePath;
//                                                                        Task<Uri> downloadUrl = taskSnapshot.getStorage().getDownloadUrl();
//                                                                        //Bitmap bitmap = BitmapFactory.decodeFile(downloadUrl.)
//                                                                        //String generatedFilePath = downloadUrl.getPath().toString();
//                                                                        // String generatedFilePath = downloadUrl.getResult().toString();
//                                                                        generatedFilePath = downloadUrl.getResult().toString(); //TODO - bug here! Looks like we are trying to get the URI before we are done uploading/processing the images in the DB
//
//
//                                                                        //System.out.println("## Stored path is "+generatedFilePath);
//
//                                                                        /////////////////////////////-------------->
//
//                                                                        // Image uploaded successfully
//                                                                        // Dismiss dialog
//                                                                        progressDialog.dismiss();
//                                                                        Toast
//                                                                                .makeText(addRecipe.this,
//                                                                                        "Image Uploaded!!",
//                                                                                        Toast.LENGTH_SHORT)
//                                                                                .show();
//                                                                    }
//
//
//                                                                }

            //Guide used -  https://firebase.google.com/docs/storage/android/upload-files#get_a_download_url

            UploadTask uploadTask = dbRecipeRef2.putFile(image_uri);

            uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                // Progress Listener for loading
                // percentage on the dialog box
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (
                            100.0
                            *
                            taskSnapshot.getBytesTransferred()
                            /
                            taskSnapshot.getTotalByteCount());

                    progressDialog.setMessage(
                            "Uploaded "
                                    + (int) progress + "%");
                }});

            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return dbRecipeRef2.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        if (downloadUri == null)
                            return;
                        else {
                            generatedFilePath = downloadUri.getPath();
                            progressDialog.dismiss();
                        }

                    }
                }
            });

//            dbRecipeRef2.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//
//
//                @Override
//                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                            /////////////!!!!!!--------------------->my baby is a liveeeeeeee!!!!
//
//                            //Uri  downloadUrl = filePath;
//                            Task<Uri> downloadUrl = taskSnapshot.getStorage().getDownloadUrl();
//                            //Bitmap bitmap = BitmapFactory.decodeFile(downloadUrl.)
//                            //String generatedFilePath = downloadUrl.getPath().toString();
//                            // String generatedFilePath = downloadUrl.getResult().toString();
//                            generatedFilePath = downloadUrl.getResult().toString(); //TODO - bug here! Looks like we are trying to get the URI before we are done uploading/processing the images in the DB
//
//
//                            //System.out.println("## Stored path is "+generatedFilePath);
//
//                            /////////////////////////////-------------->
//
//                            // Image uploaded successfully
//                            // Dismiss dialog
//                            progressDialog.dismiss();
//                            Toast
//                                    .makeText(addRecipe.this,
//                                            "Image Uploaded!!",
//                                            Toast.LENGTH_SHORT)
//                                    .show();
//                        }


//                    })
//
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Log.d(null, "line 173");
//                            e.printStackTrace();
//                            // Error, Image not uploaded
//                            progressDialog.dismiss();
//                            Toast
//                                    .makeText(addRecipe.this,
//                                            "Failed " + e.getMessage(),
//                                            Toast.LENGTH_SHORT)
//                                    .show();
//                        }
//                    })
//                    .addOnProgressListener(
//                            new OnProgressListener<UploadTask.TaskSnapshot>() {
//
//                                // Progress Listener for loading
//                                // percentage on the dialog box
//                                @Override
//                                public void onProgress(
//                                        UploadTask.TaskSnapshot taskSnapshot) {
//                                    double progress
//                                            = (100.0
//                                            * taskSnapshot.getBytesTransferred()
//                                            / taskSnapshot.getTotalByteCount());
//                                    progressDialog.setMessage(
//                                            "Uploaded "
//                                                    + (int) progress + "%");
//                                }
//                            });

            ////try
            //try
//            mDatabase2.getReference().child("Image/" + uid).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                @Override
//                public void onSuccess(Uri uri) {
//                    String a = uri.toString();
//                    Log.d(null, "line 206");
//
//
//
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception exception) {
//
//                    Log.d(null, "line 214");
//                    exception.printStackTrace();
//
//                    // Handle any errors
//                }
//            });

            /////
        }
        Log.d(null, "line 222");
    }


    //end addition 21.12

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        permissionsInit();



        // Init buttons and EditText

        takePhoto = (Button) findViewById(R.id.take_photo_bttn);
        sendRecipe = (Button) findViewById(R.id.send_recipe_button);
        // addPhoto = (Button)findViewById(R.id.add_photo_button);
        recipeName = (EditText) findViewById(R.id.recipeName_TextView);
//        ingridientsList = (EditText) findViewById(R.id.ingredient_list_text);
        howToDescription = (EditText) findViewById(R.id.wrkProgress_text);
        //Rphoto = (EditText)findViewById(R.id.)


        // Init DB references
        mDatabase = FirebaseDatabase.getInstance();
        dbRecipeRef = mDatabase.getReference().child("RecipeDetails");
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


        adaptIng = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

        ingData = (MultiAutoCompleteTextView) findViewById(R.id.multiAutoCompleteTextView_addRecipe); // This is the field were ingredient input is comming from
        ingData.setThreshold(1);
        ingData.setAdapter(adaptIng);
        ingData.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        ingredientQuery = FirebaseDatabase.getInstance().getReference("Products");
        ingredientQuery.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot data : snapshot.getChildren()) {
                        Products ingred = data.getValue(Products.class);
                        adaptIng.add(ingred.getName());
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




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
        
        
        //*******Take Photo******\\
        
        //1) Request for camera permissions
        if(ContextCompat.checkSelfPermission(addRecipe.this,Manifest.permission.CAMERA ) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(addRecipe.this,
                    new String[]{
                            Manifest.permission.CAMERA
                    },PICK_IMAGE_FROM_CAMERA_REQUEST);
        }

        if(ContextCompat.checkSelfPermission(addRecipe.this,Manifest.permission.WRITE_EXTERNAL_STORAGE ) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(addRecipe.this,
                    new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    },STORAGE_REQUEST);
        }



        //2) take photo button
        takePhoto.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {


                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, "temp pic");
                values.put(MediaStore.Images.Media.DESCRIPTION, "temp description");

                image_uri = getApplicationContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);


                // Open camera
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
                startActivityForResult(cameraIntent, PICK_IMAGE_FROM_CAMERA_REQUEST);
            }
        });

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        String rName = recipeName.getText().toString().toLowerCase().trim(); // Should I use trim?

                        String rIngridients = ingData.getText().toString(); // This is the string from input
                        rIngridients = rIngridients.replace(" ", ""); // Cutting off all the spaces for easier work.
                        String[] splittedToArrayInput = rIngridients.split(","); // Cut the string into an array of ingridients
                        ArrayList<String> userInputIng = new ArrayList<>(); // Init the list
                        for (int i = 0; i < splittedToArrayInput.length; i++) {
                            userInputIng.add(splittedToArrayInput[i]); // Fill the list
                        }

//                String rIngridients = ingridientsList.getText().toString().toLowerCase().trim();



                        String howTo = howToDescription.getText().toString().trim();
                        ;

                        //21.12 addition uplode photo
                        //uploadImage();
                        //image url
                        //String rImage = ""; //TODO: get link to image in 55
                        String rImage;
                        if (generatedFilePath != null) {
                            rImage = generatedFilePath;
                        } else
                            rImage = "";
                        //end of additon 21.12

                        // 1) Check if any field is empty and show toast ,  else put them inside a string;

                        if (TextUtils.isEmpty(rName)) {
                            recipeName.setError("Recipe name is Required!");
                            return;
                        }
                        if (TextUtils.isEmpty(rIngridients)) {
                            ingData.setError("Recipe must have Ingredients");
                            return;
                        }

                        if (TextUtils.isEmpty(howTo)) {
                            howToDescription.setError("Please tell us how to make the recipe");
                            return;
                        }
                        // 2) Create a Recipe Object

                        // 2.a) count different ingredients by counting the num of commas -1
                        int commas = 0;
                        for (int i = 0; i < rIngridients.length(); i++) {
                            if (rIngridients.charAt(i) == ',') commas++;
                        }
                        // 2.b ) Establish current Date
                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                        LocalDateTime now = LocalDateTime.now();
                        String currentDate = now.format(dtf);
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
                        Recipe newRecipe = new Recipe(0, currentDate, host, howTo, id, measures, commas, rIngridients, rName, rImage); // Creating the new recipe
                        newRecipe.setId(dbRecipeRef.push().getKey());
                        dbRecipeRef.child(newRecipe.getId()).setValue(newRecipe);
                        // 4) Toast and move to main page.
                        Toast.makeText(addRecipe.this, "Thank you ! your recipe is added ! and will be visible when aproved by admin. ", Toast.LENGTH_SHORT).show();
                        //startActivity(new Intent(getApplicationContext(), MainUserActivity.class)); // This is the proper path!
                        finish();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };



        sendRecipe.setOnClickListener(new View.OnClickListener() {
//            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                // Parts of recipe

                AlertDialog.Builder builder = new AlertDialog.Builder(addRecipe.this);
                builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();




            }


            //add pic for firebase


        });
        // End of add photo


    }

    private boolean checkStoragePermission() {
        boolean ans = ContextCompat.checkSelfPermission(addRecipe.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return ans;
    }

    private void permissionsInit() {
        cameraPermissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
    }


}