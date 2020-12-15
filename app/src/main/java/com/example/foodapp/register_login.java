package com.example.foodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.os.Debug;
import android.text.TextUtils;
import android.util.Log;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class register_login extends AppCompatActivity
{


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


    private List<Auser> matchedAdminUsers;
    private Button log_in_button;
    private Button sign_in_button;
    private Button enter_anon_button;
    private EditText enter_password;
    private EditText mEmail;
    private TextView output_to_user;
    private ProgressBar progressBar;
    public Query query;
    private Button debug_button;
    boolean debug_mode_bool = false;

    // Firebase authentication instance
    private FirebaseAuth mAuth;

    // For using Firestore to authenticate admin users from others

    FirebaseDatabase mDatabase ;
    DatabaseReference admin_data_ref;


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

        //show debug button if in debug mode
        debug_button = findViewById(R.id.debug_button);
        debug_button.setVisibility(View.GONE);
//        boolean isDebuggable = (0 != (getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE));

//        debug_mode_bool = (0 != (getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE));
        if(Debug.isDebuggerConnected()){
            debug_button.setVisibility(View.VISIBLE);
            Toast.makeText(register_login.this, "DEBUG MODE", Toast.LENGTH_SHORT).show();
        }

        // Initing FirebaseAuth instance
        mAuth = FirebaseAuth.getInstance();

        // Init mDatabase and the reference
       // mDatabase.getInstance();
      // admin_data_ref.getRef("admins");



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
                            startActivity(new Intent(getApplicationContext(), MainUserActivity.class)); // This is the proper path!
                           // startActivity(new Intent(getApplicationContext(),Search.class)); // for running,  new path
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

                                boolean admin = false;


                                /**
                                 *
                                 *
                                 *
                                 */

                                query = FirebaseDatabase.getInstance().getReference("Admins").orderByChild("email").equalTo(email);
                                query.addListenerForSingleValueEvent(new ValueEventListener()
                                {

                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot DS) {
                                        matchedAdminUsers.clear();
                                        if (DS.exists()) {
                                            for (DataSnapshot snapshot : DS.getChildren()) {
                                                Auser admin = snapshot.getValue(Auser.class);
                                                matchedAdminUsers.add(admin);
                                            }
                                        }
                                        // checking if matchedAdminUser list is empty ,  if it is then the email don't match admin privileges

                                        // 3) Pass an List of recipes to peller in the result_page by intent
                                        //* Please note that serialization can cause performance issues: it takes time, and a lot of objects will be allocated (and thus, have to be garbage collected).




                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        Log.d(null, "---- !!!!!!onCancelled!!!!!! ----");
                                    }
                                });

                                /**
                                 *
                                 */
                                boolean isAdmin = matchedAdminUsers.isEmpty();
                                if(isAdmin)
                                {
                                    Toast.makeText(register_login.this, "Admin Logged in Successfully", Toast.LENGTH_SHORT).show();
                                    Intent myAdminIntent = new Intent(getApplicationContext(), MainAdminActivity.class); // For testing
                                    myAdminIntent.putExtra("isAdmin" , isAdmin ); // Putting the list there
                                    startActivity(myAdminIntent); // Start new activity with the given intent

                                }
                                else { // Regular user
                                    Toast.makeText(register_login.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), MainUserActivity.class));
                                }
                            }
                        else
                            {
                            Toast.makeText(register_login.this, "Error !" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }

                    }
                });

            }
        });

        debug_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), activity_debug.class));
            }
        });


    }





}