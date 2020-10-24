/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.olivine.cholodesh;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

import adapters.FoodListAdapter;
import adapters.HotelListAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import callbacks.ProvideCallback;
import constants.Travel;
import model.AccommodationProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HotelListActivity extends AppCompatActivity {
    @BindView(R.id.hotelListView) RecyclerView hotelListView;
    private HotelListAdapter hotelListAdapter;

    // DataStrorage
    SharedPreferences sharedPreferences;
    // Callback
    ProvideCallback provideCallback;
    // Adapter
    FoodListAdapter foodListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_list);
        ButterKnife.bind(this);
        initialization();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    private void initialization() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        sharedPreferences = getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
        String districtId = sharedPreferences.getInt(Travel.TO_LOCATION, 26) + "";
        provideCallback = new ProvideCallback(this);
        provideCallback.getDestinationWiseAccommodationList(Integer.parseInt(districtId)).enqueue(new Callback<AccommodationProvider[]>() {
              @Override
              public void onResponse(Call<AccommodationProvider[]> call, Response<AccommodationProvider[]> response) {
                  hotelListAdapter=new HotelListAdapter(getApplicationContext(),response.body());
                  hotelListView.setAdapter(hotelListAdapter);
              }

              @Override
              public void onFailure(Call<AccommodationProvider[]> call, Throwable t) {
                  Toast.makeText(HotelListActivity.this, "network Error", Toast.LENGTH_SHORT).show();
              }
          }
        );
    }
}
