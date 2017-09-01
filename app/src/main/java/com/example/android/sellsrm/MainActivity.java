package com.example.android.sellsrm;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import static android.R.attr.start;
import static android.os.Build.VERSION_CODES.M;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private SignInButton GinButton;
    private static final int RC_SIGN_IN = 1;
    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "Main_Activity";


    private Button registerButton;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignin;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        firebaseAuth = FirebaseAuth.getInstance();


        //if the objects getcurrentuser method is not null
        //means user is already logged in


        progressDialog = new ProgressDialog(this);

        registerButton = (Button) findViewById(R.id.register);
        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.password);
        textViewSignin = (TextView) findViewById(R.id.viewsignin);

        registerButton.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);



        mAuth = FirebaseAuth.getInstance();
       // GinButton = (SignInButton) findViewById(R.id.googleSignin);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if(firebaseAuth.getCurrentUser() != null)
                {

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    String test = user.getEmail();
                    Intent i = new Intent(MainActivity.this,homepageActivity.class);
                    i.putExtra("email",test);
                   // Toast.makeText(MainActivity.this, "Registration Successfull" + "\n Please verify your email", Toast.LENGTH_SHORT).show();


                }
            }
        };


        // Configure Google Sign In



    }

    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);
    }

    private void registerUser(){

        final String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            //email is empty
            Toast.makeText(MainActivity.this, "Please enter a email", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            //password is empty
            Toast.makeText(MainActivity.this, "Please enter a password", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Registering User...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            //finish();

                            FirebaseUser user  = FirebaseAuth.getInstance().getCurrentUser();
                            user.sendEmailVerification()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                // Log.d(TAG, "Email sent.");
                                            }
                                        }
                                    });
                                //Intent i = new Intent(getApplicationContext(), homepageActivity.class);
                                //i.putExtra("email",email);
                                //startActivity(i);

                            Toast.makeText(MainActivity.this, "Registration Successfull" + "\n Please verify your email\n Verification link is sent to your emailId", Toast.LENGTH_SHORT).show();

                            //we can directly start our activity here
                        }

                        else
                        {

                            Toast.makeText(MainActivity.this, "Registration Unsucessfull", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    @Override
    public void onClick(View view) {

        if (view == registerButton){
            registerUser();

        }

        if (view == textViewSignin){
            //will open sign in or login activity

            startActivity(new Intent(this, login.class));
        }

    }
}



