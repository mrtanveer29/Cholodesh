/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.olivine.cholodesh;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

import adapters.PackageItineraryAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import callbacks.ProvideCallback;
import model.PackageItinerary;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static constants.Travel.INTENT_PACKAGE_ID;

public class PackageItinerayActivity extends AppCompatActivity {
    private ProvideCallback provideCallback;
    @BindView(R.id.packageItinerary) RecyclerView packageItinerary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_itineray);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        int packageId=getIntent().getIntExtra(INTENT_PACKAGE_ID,0);
        provideCallback=new ProvideCallback(this);
        provideCallback.getPackageItineraries(packageId).enqueue(new Callback<PackageItinerary[]>() {
            @Override
            public void onResponse(Call<PackageItinerary[]> call, Response<PackageItinerary[]> response) {

                PackageItineraryAdapter packageItineraryAdapter=new PackageItineraryAdapter(PackageItinerayActivity.this,response.body());
                packageItinerary.setAdapter(packageItineraryAdapter);
            }

            @Override
            public void onFailure(Call<PackageItinerary[]> call, Throwable t) {
                Toast.makeText(PackageItinerayActivity.this, "Network Error!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);

    }
}
