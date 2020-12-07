package com.example.foodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class register_login extends AppCompatActivity {

    /**
     * Indian things
     *
     * // creating instance of the current user
     *
     * FirebaseUser user = fAuth.getCurrentUser();
     *
     * //document the user
     * DocumentReference df = fStore.collection("Users").document(user.getUid());
     * // Store the data in <Key,Vakue> map .
     *
     * Map<String,Object> userInfo = new HashMap<>();
     *
     * // Specify if the user is admin
     *
     *
     * userInfo.put("isUser" , "1"); // Based on this field we gonna determinate users lvl
     *
     * df,set(userInfo);
     */

    // Creating widget objects for class useage
    private Button log_in_button;
    private Button sign_in_button;
    private Button enter_anon_button;
    private EditText enter_password;
    private EditText mEmail;
    private TextView output_to_user;
    private ProgressBar progressBar;

    // Firebase authentication instance
    private FirebaseAuth mAuth;

    // For using Firestore to authenticate admin users from others

    private FirebaseFirestore fStore;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_login);


        // Connectiong the Java objects to our XML objects

        log_in_button = (Button)findViewById(R.id.log_in_button);
        sign_in_button = (Button)findViewById(R.id.sing_in_button);
        enter_anon_button = (Button)findViewById(R.id.enter_as_anon_button);
        output_to_user = (TextView)findViewById(R.id.output_to_user);
        enter_password = (EditText)findViewById(R.id.enter_pass);
        mEmail = (EditText)findViewById(R.id.enter_user_name_plain_text);
        progressBar = findViewById(R.id.my_progressBar);

        // Initing FirebaseAuth instance
        mAuth = FirebaseAuth.getInstance();

        // Init FirebaseFirestore
        fStore = FirebaseFirestore.getInstance();



        /** The indian dude wants to go to the main activity if the user is allready logged in

        if(mAuth.getCurrentUser() != null)
        {
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }
        */



        sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String email = mEmail.getText().toString().trim();
                String password = enter_password.getText().toString().trim();

                // Validate the email and pass data

                if(TextUtils.isEmpty(email))
                {
                   mEmail.setError("Email is Required.");
                   return;
                }
                if(TextUtils.isEmpty(password))
                {
                    enter_password.setError("Password is Required.");
                    return;
                }

                // Password must be at least 6 chars long
                if(password.length() <= 6 )
                {
                    enter_password.setError("Password must be a least 6 characters");
                    return;
                }

                // If it all passed that means our data from the user is valid

                progressBar.setVisibility(View.VISIBLE);

                // Sign in the user

                final Task<AuthResult> user_created = mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful()) {
                            Toast.makeText(register_login.this, "User Created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainUserActivity.class));
                        } else {
                            Toast.makeText(register_login.this, "Error !" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });


            }
        });

        log_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password = enter_password.getText().toString().trim();

                // Validate the email and pass data

                if(TextUtils.isEmpty(email))
                {
                    mEmail.setError("Email is Required.");
                    return;
                }
                if(TextUtils.isEmpty(password))
                {
                    enter_password.setError("Password is Required.");
                    return;
                }

                // Password must be at least 6 chars long
                if(password.length() <= 6 )
                {
                    enter_password.setError("Password must be a least 6 characters");
                    return;
                }

                // If it all passed that means our data from the user is valid

                progressBar.setVisibility(View.VISIBLE);

                // Authenticate the user

                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful())
                            {
                            Toast.makeText(register_login.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainUserActivity.class));
                            }
                        else
                            {
                            Toast.makeText(register_login.this, "Error !" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }

                    }
                });

            }
        });




    }



}