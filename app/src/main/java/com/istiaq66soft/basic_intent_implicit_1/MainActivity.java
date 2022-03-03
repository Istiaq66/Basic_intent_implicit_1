package com.istiaq66soft.basic_intent_implicit_1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    Button call, camera, contact, browser, gallery, dialpad, assignment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.editText123);
        call = (Button) findViewById(R.id.b_call);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (isPermissionGranted()) {
                    call_action();
                }

            }
        });
        camera = (Button) findViewById(R.id.b_camera);
        browser = (Button) findViewById(R.id.b_browser);
        browser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setAction(Intent.ACTION_VIEW);
                String url = editText.getText().toString();
                i.setData(Uri.parse("http://" + url));
                startActivity(Intent.createChooser(i, "Title"));
            }
        });
        gallery = (Button) findViewById(R.id.b_gallery);
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setAction(Intent.ACTION_VIEW);
                i.setData(Uri.parse("content://media/external/images/media/"));
                startActivity(i);
            }
        });
        dialpad = (Button) findViewById(R.id.b_dialpad);
        dialpad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setAction(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel:" + editText.getText()));
                startActivity(i);
            }
        });
        contact = (Button) findViewById(R.id.b_contact);
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setAction(Intent.ACTION_VIEW);
                i.setData(Uri.parse("content//contacts/people"));
                startActivity(i);
            }
        });
        assignment = (Button) findViewById(R.id.b_assignment);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivity(i);
            }
        });
    }

    public void call_action() {
        String phnum = editText.getText().toString();
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + phnum));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(callIntent);
    }


    public boolean isPermissionGranted() {

        if (Build.VERSION.SDK_INT >= 23) {
            if(checkSelfPermission(Manifest.permission.CALL_PHONE)==PackageManager.PERMISSION_GRANTED){

                Log.v("Tag", "Permission is granted");
                return true;
            }
            else {
                Log.v("Tag", "Permssion is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                return false;

            }


            } else{
            Log.v("Tag", "Permission is granted");
            return true;
        }


    }
        @Override
        public void onRequestPermissionsResult(int requestCode,
        String permissions[], int[] grantResults) {
            switch (requestCode) {

                case 1: {

                    if (grantResults.length > 0
                            && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(getApplicationContext(), "Permission granted", Toast.LENGTH_SHORT).show();
                        call_action();
                    } else {
                        Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                    }
                    return;
                }

                // other 'case' lines to check for other
                // permissions this app might request
            }
        }
}

