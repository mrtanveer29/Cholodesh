/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.olivine.cholodesh;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import adapters.PackageAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import callbacks.ProvideCallback;
import model.Package;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PackageActivity extends AppCompatActivity {
    @BindView(R.id.packages) GridView packages;
    // Callback
    ProvideCallback provideCallback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package);
        ButterKnife.bind(this);

        // Callback init
        provideCallback=new ProvideCallback(this);
        provideCallback.getAllPackages().enqueue(new Callback<Package[]>() {
            @Override
            public void onResponse(Call<Package[]> call, Response<Package[]> response) {
                PackageAdapter packageAdapter=new PackageAdapter(PackageActivity.this,response.body());
                packages.setAdapter(packageAdapter);
            }

            @Override
            public void onFailure(Call<Package[]> call, Throwable t) {
                Toast.makeText(PackageActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
            }
        });




    }
}
