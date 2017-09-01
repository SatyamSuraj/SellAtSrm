package com.example.android.sellsrm;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.firebase.auth.FirebaseAuth;

public class homepageActivity extends AppCompatActivity {

    private TextView email, phone, name, address;
    private ImageView imageView;

    private Button mLogout;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private Button mBuyButton, mSellButton, mProfile,mRequest;
    String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        mProfile = (Button) findViewById(R.id.profileDisp);

        mAuth = FirebaseAuth.getInstance();
        Intent i = getIntent();
        userEmail = i.getStringExtra("email");

       // if (i.getStringExtra("email").equals("null")) {
         //   mAuth.signOut();
        //}


        mRequest = (Button)findViewById(R.id.requestButton);
        mRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(homepageActivity.this, requestRecevied.class);
                i.putExtra("email", userEmail);
                startActivity(i);
            }
        });


        mProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(homepageActivity.this, personalInfo_disp.class);
                i.putExtra("email", userEmail);
                startActivity(i);
            }
        });

        mBuyButton = (Button) findViewById(R.id.buyButton);
        mSellButton = (Button) findViewById(R.id.sellButton);


        mBuyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(homepageActivity.this, buy.class);
                i.putExtra("email", userEmail);
                startActivity(i);
            }
        });

        mSellButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(homepageActivity.this, sell.class);
                i.putExtra("email", userEmail);
                startActivity(i);
            }
        });


        mLogout = (Button) findViewById(R.id.logout);
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (firebaseAuth.getCurrentUser() == null) {
                    startActivity(new Intent(homepageActivity.this, MainActivity.class));
                    Toast.makeText(homepageActivity.this, "Successfull Logout", Toast.LENGTH_SHORT).show();
                }

            }
        };


        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);
    }
}
