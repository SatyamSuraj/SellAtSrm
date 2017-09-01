package com.example.android.sellsrm;

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
import android.widget.TextView;
import android.widget.Toast;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.io.IOException;
import java.util.UUID;

import static android.R.attr.bitmap;
import static android.R.attr.path;

public class update_profile extends AppCompatActivity {

    private EditText mname,mcontact,musername;
    private ImageButton userDp;
    private TextView memail;
    private Button submit;
    private static final int Gallery_Request_One = 1;
    private static final int STORAGE_PERMISSION_CODE = 7232 ;
    private Uri filepath;
    private Bitmap bitmap;
    String userEmail;
    private static final String UPLOAD_URL = "https://geeksofsrm.000webhostapp.com/srmsell/update_profile.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        requestStoragePermission();

        mname = (EditText)findViewById(R.id.UserName);
        memail = (TextView) findViewById(R.id.UserEmail);
        mcontact = (EditText)findViewById(R.id.UserContact);
        musername = (EditText)findViewById(R.id.UserUsername);

        userDp = (ImageButton)findViewById(R.id.UserDp);

        submit = (Button)findViewById(R.id.submitProfile);

        Intent i = getIntent();
        userEmail = i.getStringExtra("email");
        memail.setText(userEmail);

        userDp.setOnClickListener(new View.OnClickListener() {
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
                userDp.setImageBitmap(bitmap);
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

    private void uploadData() {

        if (mname.getText().toString().isEmpty() || mcontact.getText().toString().isEmpty() || musername.getText().toString().isEmpty()){
            Toast.makeText(this, "Fill all the details", Toast.LENGTH_SHORT).show();
        }else{

        String UName = mname.getText().toString();
        String UEmail = userEmail;
        String UContact = mcontact.getText().toString();
        String UUsernname = musername.getText().toString();


        String path = getPath(filepath);

        try {
            String uploadid = UUID.randomUUID().toString();

            new MultipartUploadRequest(this, uploadid, UPLOAD_URL)
                    .addFileToUpload(path, "image")
                    .addParameter("name", UName)
                    .addParameter("email", UEmail)
                    .addParameter("phoneNo", UContact)
                    .addParameter("nickname", UUsernname)
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(2)
                    .startUpload();

            Toast.makeText(this, "Upload SuccessFul", Toast.LENGTH_SHORT).show();
            mname.setText("");
            memail.setText("");
            mcontact.setText("");
            musername.setText("");

            Intent i = new Intent(update_profile.this,personalInfo_disp.class);
            i.putExtra("email",UEmail);
            startActivity(i);
        } catch (Exception e) {

        }

    }
    }
}
