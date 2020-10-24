/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.olivine.cholodesh;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.nbsp.materialfilepicker.MaterialFilePicker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import callbacks.AuthCallback;
import droidninja.filepicker.FilePickerBuilder;
import model.Auth;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity {
    @BindView(R.id.emailTextView) EditText emailTextView;
    @BindView(R.id.phoneTextView) EditText phoneTextView;
    @BindView(R.id.passwordTextView) EditText passwordTextView;
    @BindView(R.id.imageLogo)
    ImageView imageLogo;

    private AuthCallback authCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().hide();
        ButterKnife.bind(this);

        authCallback=new AuthCallback(this);

    }
    @OnClick(R.id.gotoLogin)
    public void gotoLogin(){
        Intent intent=new Intent(this,LoginActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.btnSignUp)
    public  void SignUp(View viw){
        String emailText=emailTextView.getText().toString();
        String phoneText=phoneTextView.getText().toString();
        String passwordText=passwordTextView.getText().toString();
        if(!validate()){
            return;
        }
        Auth auth=new Auth();
        auth.setCustomerEmail(emailText)
                .setPassword(passwordText)
                .setCustomerPhone(phoneText);
        authCallback.register(auth).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Toast.makeText(RegistrationActivity.this, response.body(), Toast.LENGTH_SHORT).show();
                if(response.body().equalsIgnoreCase("success")){
                    Toast.makeText(RegistrationActivity.this, "YES", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(RegistrationActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private boolean validate(){
        boolean validate=true;

        String emailText=emailTextView.getText().toString();
        String phoneText=phoneTextView.getText().toString();
        String passwordText=passwordTextView.getText().toString();
        if(phoneText.length()==0){
            phoneTextView.requestFocus();
            Toast.makeText(this, "Phone is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(passwordText.length()==0){
            phoneTextView.requestFocus();
            Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show();
            return false;
        }

        return validate;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Toast.makeText(this, "Clicked and resolved", Toast.LENGTH_SHORT).show();
        Uri uri = data.getData();

        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            String path=uri.getPath();
            Log.e("PATH",path);
            // Log.d(TAG, String.valueOf(bitmap));
            imageLogo.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
