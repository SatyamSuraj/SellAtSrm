package com.example.android.sellsrm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class requestRecevied extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_recevied);

        Intent i = getIntent();
        String email = i.getStringExtra("email");
    }
}
