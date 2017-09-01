package com.example.android.sellsrm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.android.sellsrm.chat.ui.activities.SplashActivity;

import com.example.android.sellsrm.chat.ui.activities.SplashActivity;
import com.squareup.picasso.Picasso;

public class product extends AppCompatActivity {

    private TextView bname,bdesp,bprice,byear;
    private ImageView bimage;
    private Button buybtn,backbtn;
    private String Bname,Bdesp,Bprice,Byear,Bimg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        buybtn = (Button)findViewById(R.id.buyButton);
        backbtn = (Button)findViewById(R.id.backButton);
        bname = (TextView)findViewById(R.id.bName);
        bdesp = (TextView)findViewById(R.id.bDescription);
        bprice = (TextView)findViewById(R.id.bPrice);
        byear = (TextView)findViewById(R.id.yr);
        bimage = (ImageView)findViewById(R.id.bookImg);

        buybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(product.this,SplashActivity.class));
                //Toast.makeText(product.this, "Under Construction", Toast.LENGTH_SHORT).show();
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(product.this,homepageActivity.class);
                startActivity(i);
            }
        });


        Intent i = getIntent();
        Bname = i.getStringExtra("bname");
        Bdesp = i.getStringExtra("bdesp");
        Bprice = i.getStringExtra("bprice");
        Byear = i.getStringExtra("byear");
        Bimg = i.getStringExtra("burl");

        bname.setText(Bname);
        bdesp.setText("Description: "+Bdesp);
        bprice.setText("Price: "+Bprice);
        byear.setText("Year: "+Byear);

        Picasso.with(product.this)
                .load(Bimg)
                .into(bimage);


    }
}
