package com.example.android.sellsrm;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity implements View.OnClickListener {

    private Button ButtonSignIn;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignUp;
    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();



        ButtonSignIn = (Button) findViewById(R.id.register);
        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.password);
        textViewSignUp = (TextView) findViewById(R.id.viewsignup);

        ButtonSignIn.setOnClickListener(this);
        textViewSignUp.setOnClickListener(this);
    }

    private void UserLogin() {

        final String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            //email is empty
            Toast.makeText(login.this, "Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            //password is empty
            Toast.makeText(login.this, "Please enter a password", Toast.LENGTH_SHORT).show();
            return;
        }


        //if ()

        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            //start profile activity


                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                            if (user.isEmailVerified()) {

                                Intent i = new Intent(getApplicationContext(), homepageActivity.class);
                                i.putExtra("email", email);
                                startActivity(i);
                            } else {
                                Toast.makeText(login.this, "Please Verify Your Email :-)", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(login.this, "Unsuccessful Login", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }

    @Override
    public void onClick(View view) {

        if (view == ButtonSignIn) {
            UserLogin();
        }

        if (view == textViewSignUp) {
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
    }
}


