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
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import adapters.AccommodationListAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import callbacks.ProvideCallback;
import constants.Travel;
import listeners.AccommodationListener;
import model.AccommodationProvider;
import model.AccommodationRoom;
import model.ListWrapper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccommodationProviderActivity extends AppCompatActivity implements AccommodationListener {

    private ProvideCallback provideCallback;
    private List<AccommodationProvider> accommodationProviders=new ArrayList<>();
    @BindView(R.id.acccommodationProvierList) RecyclerView acccommodationProvierList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accommodation_provider);
        ButterKnife.bind(this);
        //TODO init callback
        provideCallback=new ProvideCallback(this);
        //--------------------------------------------------
        int district_id=getIntent().getIntExtra("district_id",0);
        loadAccommodation(district_id);
    }





    private void loadAccommodation(int id){
        provideCallback.getDestinationWiseAccommodationList(id).enqueue(new Callback<AccommodationProvider[]>() {
            @Override
            public void onResponse(Call<AccommodationProvider[]> call, Response<AccommodationProvider[]> response) {
                accommodationProviders= Arrays.asList(response.body());
                AccommodationListAdapter accommodationListAdapter=new AccommodationListAdapter(AccommodationProviderActivity.this,accommodationProviders);
                accommodationListAdapter.setAccommodationListener(AccommodationProviderActivity.this);
                acccommodationProvierList.setAdapter(accommodationListAdapter);
            }

            @Override
            public void onFailure(Call<AccommodationProvider[]> call, Throwable t) {
                Toast.makeText(AccommodationProviderActivity.this, "No accommocation found", Toast.LENGTH_SHORT).show();
            }
        });

        //TODO TEST


    }

    @Override
    public void calculateAccommodationCost(AccommodationRoom accommodationProvider, int action) {

    }

    @Override
    public void accommodationActivityResult(int requestCode) {
        Intent roomIntent=new Intent(AccommodationProviderActivity.this, AccommodationRoomlistActivity.class);
        roomIntent.putExtra(Travel.PROVIDER_KEY,requestCode);
        startActivityForResult(roomIntent,requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode!=200){
            return;
        }
        String json=data.getExtras().getString(Travel.KEY_ROOM_LIST,null);
        Intent intent=new Intent();
        Bundle bundle=new Bundle();
        bundle.putString(Travel.KEY_ROOM_LIST,json);
        intent.putExtras(bundle);
        setResult(200,intent);
        finish();
    }
}
