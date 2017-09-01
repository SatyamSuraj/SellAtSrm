package com.example.android.sellsrm;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.R.attr.name;

public class personalInfo_disp extends AppCompatActivity {

    private ImageView imageView;
    private Button editProf,deleteProf,homep,product;
    private TextView dName,dEmail,dMobile,dUsername;
    private String userEmail,APIresp;
    private Context context = this ;
    private String URL_DATA = "https://geeksofsrm.000webhostapp.com/srmsell/personal_fetch.php"+"?user_email=";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info_disp);

        Intent j = getIntent();
        userEmail = j.getStringExtra("email");

        product = (Button)findViewById(R.id.product);
        product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(personalInfo_disp.this,products_uploaded.class);
                i.putExtra("email",userEmail);
                startActivity(i);
            }
        });

        homep = (Button)findViewById(R.id.homepage);
        editProf = (Button)findViewById(R.id.editProfile);
        deleteProf = (Button)findViewById(R.id.deleteProfile);
        imageView = (ImageView)findViewById(R.id.image);
        dName = (TextView)findViewById(R.id.bookName);
        dEmail = (TextView)findViewById(R.id.bookDescription);
        dMobile = (TextView)findViewById(R.id.bookPrice);
        dUsername = (TextView)findViewById(R.id.year);

        homep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent j = getIntent();
                userEmail = j.getStringExtra("email");


                Intent i = new Intent(personalInfo_disp.this,homepageActivity.class);
                i.putExtra("email",userEmail);
                startActivity(i);
            }
        });

        editProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (APIresp.equals("[]")){

                    Intent i = new Intent(personalInfo_disp.this, personalInfo_submit.class);
                    i.putExtra("email", userEmail);
                    startActivity(i);

                }else {

                    Intent i = new Intent(personalInfo_disp.this, update_profile.class);
                    i.putExtra("email", userEmail);
                    startActivity(i);
                }
            }
        });

        deleteProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        loadPersonalInfo();


    }

    private void loadPersonalInfo(){



        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();

        Intent i = getIntent();
        userEmail = i.getStringExtra("email");
        URL_DATA = URL_DATA+userEmail;

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            progressDialog.dismiss();
                            JSONArray array = new JSONArray(response);

                            APIresp = response;

                                JSONObject o = array.getJSONObject(0);
                                dName.setText("Name : "+o.getString("name"));
                                dMobile.setText("Contact : "+o.getString("phoneNo"));
                                dEmail.setText("Email : "+o.getString("email"));
                                dUsername.setText("Username : "+o.getString("nickname"));

                                Picasso.with(context)
                                        .load(o.getString("dpurl"))
                                        .into(imageView);



                        } catch (JSONException e) {
                            Toast.makeText(personalInfo_disp.this, ""+e, Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }


                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(personalInfo_disp.this, ""+error, Toast.LENGTH_SHORT).show();
                    }
                }
        );

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
