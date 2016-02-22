package com.task.satu.rabbitsfarmer;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.parse.ParseUser;
import com.parse.ui.ParseLoginBuilder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {
    ParseUser currentUser = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    @Override
    protected void onStart() {
        super.onStart();
        currentUser = ParseUser.getCurrentUser();
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.task.satu.rabbitsfarmer",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

        if (currentUser != null) {
            Toast.makeText(getApplicationContext(),currentUser.getUsername(),Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(),RabbitManagerActivity.class);
            startActivity(intent);
        } else {
            loginIn();
        }
    }

    private void loginIn(){
        ParseLoginBuilder loginBuilder = new ParseLoginBuilder(
                MainActivity.this);
        startActivityForResult(loginBuilder.build(), 0);
    }
}
