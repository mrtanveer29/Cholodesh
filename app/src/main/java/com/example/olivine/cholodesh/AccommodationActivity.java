/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.olivine.cholodesh;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import adapters.RouteAccommodationAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import callbacks.RouteCallback;
import constants.Travel;
import io.realm.Realm;
import listeners.AccommodationListener;
import model.AccommodationProvider;
import model.AccommodationRoom;
import model.ListWrapper;
import model.Route;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import userDefinder.TailorMade;

public class AccommodationActivity extends AppCompatActivity implements AccommodationListener {
    public static final int ACTION_MULTIPLE_ROOM_ADDITION=0;
    public static final int ACTION_DELETE_ROOM=1;
    public static final int ACTION_INCRESE_ROOM=2;
    public static final int ACTION_DECREASE_ROOM=3;
    private RouteCallback routeCallback;
    private ArrayList<AccommodationRoom> accommodationRooms;
    private static int totalAccommodationCost=0;
    // TODO Data storages declear
    SharedPreferences sharedPreferences;
    Realm realm;
    // TODO init view
    @BindView(R.id.accommodations) RecyclerView accommodations;
    @BindView(R.id.accommodationCostTotal) TextView accommodationCostTotal;
    private TailorMade tailorMade;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accommodation);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //TODO init callback
        routeCallback=new RouteCallback(this);
        accommodationRooms=new ArrayList<>();

//        ...............................................
        //TODO Data storage init
        sharedPreferences=getSharedPreferences(getString(R.string.preference_file_key),MODE_PRIVATE);
        Realm.init(this);
        realm=Realm.getDefaultInstance();
        // ------------------------------------------------
        int tailormade_id=sharedPreferences.getInt(Travel.CURRENT_TAILORMADE_ID,0);
        tailorMade=realm.where(TailorMade.class).equalTo("tailormade_id",tailormade_id).findFirst();
        ArrayList<AccommodationRoom> accomodationRoomsFromRealm= (ArrayList<AccommodationRoom>) realm.copyFromRealm(tailorMade.accommodationRooms);
        // Load with prevoius data if has any
        if(accomodationRoomsFromRealm.size()>0 && accomodationRoomsFromRealm!=null){
            RouteAccommodationAdapter routeAccommodationAdapter=new RouteAccommodationAdapter(AccommodationActivity.this,accomodationRoomsFromRealm);
            routeAccommodationAdapter.setAccommodationListener(AccommodationActivity.this);
            accommodations.setAdapter(routeAccommodationAdapter);
            accommodationRooms=accomodationRoomsFromRealm;
            totalAccommodationCost=getTotalAccommodationCost();
            accommodationCostTotal.setText(String.valueOf(totalAccommodationCost)+" ৳");
        }else{
//            viewAccommodationPlaces(1,26);
            viewAccommodationPlaces(sharedPreferences.getInt(Travel.DEPART_LOCATION,0),sharedPreferences.getInt(Travel.TO_LOCATION,0));
        }



    }
    public void viewAccommodationPlaces(int location,int destination){
        routeCallback.getRoutes(location,destination).enqueue(new Callback<Route[]>() {
            @Override
            public void onResponse(Call<Route[]> call, Response<Route[]> response) {
                for(Route route:response.body()){
                    AccommodationRoom accommodationRoom=new AccommodationRoom();
                    accommodationRoom.setDistrictId(route.getEndDistrictId());
                    accommodationRoom.setDistrictName(route.getEndDistrictName());
                    accommodationRooms.add(accommodationRoom);
                }
                RouteAccommodationAdapter routeAccommodationAdapter=new RouteAccommodationAdapter(AccommodationActivity.this,accommodationRooms);
                routeAccommodationAdapter.setAccommodationListener(AccommodationActivity.this);
                accommodations.setAdapter(routeAccommodationAdapter);
            }

            @Override
            public void onFailure(Call<Route[]> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem menuItem=menu.add(Menu.NONE,1,Menu.NONE,"Next");
        menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        realm.beginTransaction();
        tailorMade.accommodationRooms.clear();
        tailorMade.accommodationCost=totalAccommodationCost;
        for(AccommodationRoom selected_rooms:accommodationRooms){
            tailorMade.accommodationRooms.add(selected_rooms);
        }
        realm.commitTransaction();

        Intent intent=new Intent(AccommodationActivity.this,ItineraryPlanner.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

           if(resultCode==200){
               String json=data.getExtras().getString(Travel.KEY_ROOM_LIST,"");
               ListWrapper listWrapper=new Gson().fromJson(json,ListWrapper.class);
               if(listWrapper.getAccommodationRooms().size()!=0){
                   ArrayList<AccommodationRoom> selecedProvidersRoom=listWrapper.getAccommodationRooms();
                   int idTobeMatched=requestCode;
                   boolean isdesticationMatched=false;
                   int counter;
                   for(counter=0;counter<accommodationRooms.size();counter++){
                       AccommodationRoom tmp_provider_room=accommodationRooms.get(counter);

                       if(tmp_provider_room.getDistrictId()==idTobeMatched){
                           accommodationRooms.addAll(counter+1,selecedProvidersRoom);
                           break;

                       }
                   }
                   for(int i=counter+1;i<accommodationRooms.size();i++){
                       AccommodationRoom tmp_provider_room=accommodationRooms.get(i);
                       for(int j=i+1;j<accommodationRooms.size();j++){
                           AccommodationRoom providerRoomTobeMatched=accommodationRooms.get(j);
                           boolean providerMatched=providerRoomTobeMatched.getProviderId()==tmp_provider_room.getProviderId();
                           boolean roomMatched=tmp_provider_room.getAccommodationRoomId()==providerRoomTobeMatched.getAccommodationRoomId();
                           if(providerMatched&&roomMatched){
                               tmp_provider_room.setQuantity(tmp_provider_room.getQuantity()+providerRoomTobeMatched.getQuantity());
                               accommodationRooms.remove(j);
                           }
                       }
                   }

                   accommodationCostTotal.setText(getTotalAccommodationCost()+" ৳");
                   RouteAccommodationAdapter routeAccommodationAdapter=new RouteAccommodationAdapter(AccommodationActivity.this,accommodationRooms);
                   routeAccommodationAdapter.setAccommodationListener(AccommodationActivity.this);
                   accommodations.setAdapter(routeAccommodationAdapter);
               }

           }

    }
                private int getTotalAccommodationCost(){

                for (AccommodationRoom ar:accommodationRooms){
                    if(ar.getProviderId()==null){
                        continue;
                    }
                    totalAccommodationCost+=Integer.parseInt(ar.getAccommodationRoomPrice())*ar.getQuantity();
                }
                return totalAccommodationCost;

    }

    @Override
    public void calculateAccommodationCost(AccommodationRoom accommodationRoom, int action) {
        switch (action){
            case ACTION_INCRESE_ROOM:
                totalAccommodationCost+=Integer.parseInt(accommodationRoom.getAccommodationRoomPrice());
                break;
            case ACTION_DECREASE_ROOM:
                totalAccommodationCost-=Integer.parseInt(accommodationRoom.getAccommodationRoomPrice());
                break;
            case ACTION_DELETE_ROOM:
                int accommodationCost=Integer.parseInt(accommodationRoom.getAccommodationRoomPrice());
                int roomQuantity=accommodationRoom.getQuantity();
                totalAccommodationCost-=accommodationCost*roomQuantity;
                break;
        }
        accommodationCostTotal.setText(totalAccommodationCost+" ৳");

    }


    @Override
    public void accommodationActivityResult(int resultCode) {

    }


}
