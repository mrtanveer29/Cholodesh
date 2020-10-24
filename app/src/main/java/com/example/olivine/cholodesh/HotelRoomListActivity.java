/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.olivine.cholodesh;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import adapters.AccommodationRoomListAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import callbacks.BookingCallback;
import callbacks.ProvideCallback;
import constants.Travel;
import io.realm.Realm;
import io.realm.RealmList;
import model.AccommodationRoom;
import model.HotelBooking;
import model.ListWrapper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import userDefinder.Booking;

import static adapters.HotelListAdapter.SERVICE_ID;

public class HotelRoomListActivity extends AppCompatActivity {

    private List<AccommodationRoom> accommodationRooms;
    private static final int BOOK_MENU = 1;
    ProvideCallback provideCallback;
    BookingCallback bookingCallback;
    // Data strorage
    SharedPreferences sharedPreferences;
    Realm realm;
    //View
    @BindView(R.id.hotelRooms) RecyclerView hotelRooms;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem menuItem=menu.add(Menu.NONE,BOOK_MENU,Menu.NONE,"Confirm Room");
        menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final AlertDialog.Builder roomConfirmDialog=new AlertDialog.Builder(this);
        roomConfirmDialog.setTitle("Confirmation");
        roomConfirmDialog.setMessage("Do You want to confirm Booking ?");
        roomConfirmDialog.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        roomConfirmDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String customerEmail=sharedPreferences.getString(Travel.USER_EMAIL,null);
                if(customerEmail==null){
                    Toast.makeText(HotelRoomListActivity.this, "Email Null", Toast.LENGTH_SHORT).show();
                    Intent loginIntent=new Intent(HotelRoomListActivity.this,LoginActivity.class);
                    startActivityForResult(loginIntent,110);
                    return;
                }
                reserveRoom();
            }
        });
        roomConfirmDialog.show();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==200){
            reserveRoom();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_room);
        ButterKnife.bind(this);
        //Storage init
        Realm.init(this);
        realm=Realm.getDefaultInstance();
        sharedPreferences=getSharedPreferences(getString(R.string.preference_file_key),MODE_PRIVATE);
        int serviceId= getIntent().getIntExtra(SERVICE_ID,0);
        provideCallback=new ProvideCallback(this);
        bookingCallback=new BookingCallback(this);
        provideCallback.getAccommodationRoom(serviceId).enqueue(new Callback<AccommodationRoom[]>() {
            @Override
            public void onResponse(Call<AccommodationRoom[]> call, Response<AccommodationRoom[]> response) {
                if(response.body()==null)return;
                accommodationRooms= Arrays.asList(response.body());
                AccommodationRoomListAdapter accommodationRoomListAdapter=new AccommodationRoomListAdapter(HotelRoomListActivity.this,accommodationRooms);
                hotelRooms.setAdapter(accommodationRoomListAdapter);
            }

            @Override
            public void onFailure(Call<AccommodationRoom[]> call, Throwable t) {
                Toast.makeText(HotelRoomListActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void reserveRoom(){
        final List<AccommodationRoom> selectedAccommodationRooms=new ArrayList<AccommodationRoom>();
        for (AccommodationRoom selected_room:accommodationRooms){
            if (selected_room.isSelected()){
                selectedAccommodationRooms.add(selected_room);
            }
        }
        String customerEmail=sharedPreferences.getString(Travel.USER_EMAIL,null);
        final HotelBooking rooms=new HotelBooking();
        rooms.setCustomerEmail(customerEmail);
        rooms.setAccommodationRoomList(selectedAccommodationRooms);
        bookingCallback.confirmBooking(rooms).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                realm.beginTransaction();
                Booking booking=new Booking();
                booking.tokenNO=response.body();

                Log.e("Token",call.request().url().toString());
                Log.e("Token",response.body());

                booking.accommodationRooms=new RealmList<>();
                booking.accommodationRooms.addAll(selectedAccommodationRooms);

                realm.copyToRealm(booking);
                realm.commitTransaction();

                Intent bookinListintent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(bookinListintent);


            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(HotelRoomListActivity.this, "Network Error !", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
