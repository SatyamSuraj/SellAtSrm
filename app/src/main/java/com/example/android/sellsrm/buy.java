package com.example.android.sellsrm;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class buy extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Button homep;
    private String userEmail ;
    private EditText editTextSearch;
    private RecyclerView.Adapter adapter;
    private List<ListItem> listItems;
    MyAdapter adap;
    private ArrayList<String> names;
    private static final String URL_DATA = "https://geeksofsrm.000webhostapp.com/srmsell/fetchApi2.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);

        names = new ArrayList<>();

        editTextSearch = (EditText)findViewById(R.id.editTextSearch);



        Intent i = getIntent();
        userEmail = i.getStringExtra("email");


        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listItems = new ArrayList<>();
        loadRecyclerViewData();
    }
    private void loadRecyclerViewData(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Data...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        progressDialog.dismiss();
                        try {
                            //JSONObject jsonObject = new JSONObject(s);
                            JSONArray array = new JSONArray(s);

                            for (int i=0; i<array.length(); i++){
                                JSONObject o = array.getJSONObject(i);
                                ListItem item = new ListItem(
                                        o.getString("BookName"),
                                        o.getString("BookDesp"),
                                        o.getInt("Price"),
                                        o.getInt("year"),
                                        o.getString("url")
                                );
                                names.add(o.getString("BookName"));
                                listItems.add(item);
                            }
                            adapter = new MyAdapter(listItems,getApplicationContext());
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(buy.this, ""+error, Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


}
