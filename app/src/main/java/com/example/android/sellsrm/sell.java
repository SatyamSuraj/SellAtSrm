package com.example.android.sellsrm;

import android.*;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.io.IOException;
import java.util.UUID;

import static android.R.attr.bitmap;
import static android.R.attr.data;
import static android.R.attr.name;
import static android.R.attr.path;

public class sell extends AppCompatActivity {

    private EditText mBookName, mBookDescription, mBookPrice;
    private static final int STORAGE_PERMISSION_CODE = 7232 ;
    RadioGroup radioGroup;
    RadioButton r1, r2, r3, r4;
    ImageButton mImageOne;
    Button submit;
    private Uri filepath;
    String userEmail;
    private Bitmap bitmap;
    private static final int Gallery_Request_One = 1;
    private static final String UPLOAD_URl = "https://geeksofsrm.000webhostapp.com/srmsell/upload.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);

        Intent i = getIntent();
        userEmail = i.getStringExtra("email");

        requestStoragePermission();
        submit = (Button) findViewById(R.id.submitDetails);

        radioGroup = (RadioGroup)findViewById(R.id.rg);

        r1 = (RadioButton) findViewById(R.id.first);
        r2 = (RadioButton) findViewById(R.id.second);
        r3 = (RadioButton) findViewById(R.id.third);
        r4 = (RadioButton) findViewById(R.id.fourth);

        mBookName = (EditText) findViewById(R.id.bookName);
        mBookDescription = (EditText) findViewById(R.id.bookDescription);
        mBookPrice = (EditText) findViewById(R.id.bookPrice);


        mImageOne = (ImageButton) findViewById(R.id.selectImageOne);


        mImageOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, Gallery_Request_One);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadData();
            }
        });

    }

    private void requestStoragePermission(){

        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();

            }else{
                Toast.makeText(this, "Permission not Granted ", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Gallery_Request_One && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filepath = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filepath);
                mImageOne.setImageBitmap(bitmap);
            } catch (IOException exc) {

                Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }
    }

    private String getPath(Uri uri) {
        String path = null;
        try {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);

            cursor.moveToFirst();
            String documnent_Id = cursor.getString(0);

            documnent_Id = documnent_Id.substring(documnent_Id.lastIndexOf(":") + 1);
            cursor.close();

            cursor = getContentResolver().query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, MediaStore.Images.Media._ID + "= ?", new String[]{documnent_Id}, null
            );

            cursor.moveToFirst();
            path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            cursor.close();
            return path;
        } catch (Exception e) {

            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();

        }
        return path;

    }

    public void uploadData() {

        if (mBookName.getText().toString().trim().isEmpty() || mBookDescription.getText().toString().trim().isEmpty() || mBookPrice.getText().toString().trim().isEmpty()){
            Toast.makeText(this, "Fill all the details", Toast.LENGTH_SHORT).show();
        }else if ( Integer.parseInt(mBookPrice.getText().toString()) >1000){
            Toast.makeText(this, "Please provide price between 10 - 1000", Toast.LENGTH_SHORT).show();
        }
        else{

        String BName = mBookName.getText().toString();
        String BDesp = mBookDescription.getText().toString();
        String Bprice = mBookPrice.getText().toString();
        int flag = 0 ;
        switch (radioGroup.getCheckedRadioButtonId()){
            case R.id.first:
                flag = 1;
                break;
            case R.id.second:
                flag = 2;
                break;
            case R.id.third:
                flag=3;
                break;
            case R.id.fourth:
                flag = 4;
                break;
        }


        String path = getPath(filepath);

        Intent i = getIntent();
            userEmail = i.getStringExtra("email");

        try {
            String uploadid = UUID.randomUUID().toString();

            new MultipartUploadRequest(this, uploadid, UPLOAD_URl)
                    .addFileToUpload(path, "image")
                    .addParameter("BookName", BName)
                    .addParameter("BookDesp", BDesp)
                    .addParameter("Price", Bprice)
                    .addParameter("email",userEmail)
                    .addParameter("year", String.valueOf(flag))
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(2)
                    .startUpload();



            Toast.makeText(this, "Upload SuccessFul", Toast.LENGTH_SHORT).show();
            mBookName.setText("");
            mBookDescription.setText("");
            mBookPrice.setText("");
            mImageOne.setImageResource(R.mipmap.add_btn);

            Intent j = new Intent(sell.this,homepageActivity.class);
            j.putExtra("email",userEmail);
            startActivity(j);
        } catch (Exception e) {

        }
    }
    }
}
