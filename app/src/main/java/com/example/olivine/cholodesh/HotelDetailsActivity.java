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
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import adapters.InclusionAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import callbacks.ProvideCallback;
import constants.Travel;
import helpers.BaseURL;
import model.AccommodationProvider;
import model.HotelDetails;
import model.HotelGallery;
import model.HotelInclusion;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static adapters.HotelListAdapter.SERVICE_ID;

public class HotelDetailsActivity extends AppCompatActivity {
    List<AccommodationProvider> accommodationProviders=new ArrayList<>();
    private static final int ROOM_REQUEST = 120;
    private static final int NEXT_MENU =2 ;
    private static final int PREVIOUS_MENU =1 ;
    private int currentServiceId;
    private int currentSerialNo=0;
    @BindView(R.id.facilitiesTitle) TextView facilitiesTitle;
    @BindView(R.id.mDemoSlider) SliderLayout mDemoSlider;
    @BindView(R.id.inclusions) RecyclerView inclusions;
    @BindView(R.id.providerName) TextView providerName;

    @BindView(R.id.hotelAddress) TextView hotelAddress;
    @BindView(R.id.hotelEmail) TextView hotelEmail;
    @BindView(R.id.hotelHotLine) TextView hotelHotLine;
    @BindView(R.id.hotelDetails) TextView hotelDetailsView;
    @BindView(R.id.hotelRating) RatingBar hotelRating;

    private ProvideCallback provideCallback;
    private SharedPreferences sharedPreferences;


    @OnClick(R.id.viewRooms)
    public void viewRooms(View view){
        int serviceId=getIntent().getIntExtra(SERVICE_ID,0);
        Intent intent=new Intent(HotelDetailsActivity.this,HotelRoomListActivity.class);
        intent.putExtra(SERVICE_ID,serviceId);
        startActivity(intent);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        sharedPreferences=getSharedPreferences(getString(R.string.preference_file_key),MODE_PRIVATE);
        currentServiceId=getIntent().getIntExtra(SERVICE_ID,0);
        provideCallback=new ProvideCallback(this);
        loadHotelDetailsInfo(currentServiceId);
        getAllProviders(currentServiceId);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem previous=menu.add(Menu.NONE,PREVIOUS_MENU,Menu.NONE,"<");
        previous.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        previous.setIcon(R.drawable.ic_chevron_left);
        MenuItem next=menu.add(Menu.NONE,NEXT_MENU,Menu.NONE,">");
        next.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        next.setIcon(R.drawable.ic_chevron_right);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case PREVIOUS_MENU:
                if(currentSerialNo>0){
                    currentSerialNo--;
                    currentServiceId=accommodationProviders.get(currentSerialNo).getAccommodationServiceId();
                    loadHotelDetailsInfo(currentServiceId);
                }
                break;
            case NEXT_MENU:
                if(currentSerialNo<accommodationProviders.size()-1){
                    currentSerialNo++;
                    currentServiceId=accommodationProviders.get(currentSerialNo).getAccommodationServiceId();
                    loadHotelDetailsInfo(currentServiceId);
                }
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadHotelDetailsInfo(int serviceId){
        provideCallback.getHotelGalley(serviceId).enqueue(new Callback<HotelGallery[]>() {
            @Override
            public void onResponse(Call<HotelGallery[]> call, Response<HotelGallery[]> response) {
                if(response.body()!=null){
                    loadGalleryImage(response.body());
                }
            }

            @Override
            public void onFailure(Call<HotelGallery[]> call, Throwable t) {
                Toast.makeText(HotelDetailsActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
            }
        });
        provideCallback.getHotelDetails(serviceId).enqueue(new Callback<HotelDetails>() {
            @Override
            public void onResponse(Call<HotelDetails> call, Response<HotelDetails> response) {
                if(response.body()!=null){
                    loadHotelDetails(response.body());
                }
            }

            @Override
            public void onFailure(Call<HotelDetails> call, Throwable t) {

            }
        });
        provideCallback.getHotelInclusions(serviceId).enqueue(new Callback<HotelInclusion[]>() {
            @Override
            public void onResponse(Call<HotelInclusion[]> call, Response<HotelInclusion[]> response) {
                if(response.body().length==0){
                    facilitiesTitle.setText("No Facilities");
                }
                InclusionAdapter inclusionAdapter=new InclusionAdapter(getApplicationContext(),response.body());
                inclusions.setAdapter(inclusionAdapter);
            }

            @Override
            public void onFailure(Call<HotelInclusion[]> call, Throwable t) {

            }
        });


    }
    private void getAllProviders(int serviceId){
        String districtId = sharedPreferences.getInt(Travel.TO_LOCATION, 26) + "";
        provideCallback.getDestinationWiseAccommodationList(Integer.parseInt(districtId)).enqueue(new Callback<AccommodationProvider[]>() {
            @Override
            public void onResponse(Call<AccommodationProvider[]> call, Response<AccommodationProvider[]> response) {
                accommodationProviders= Arrays.asList(response.body());
                // Geting Current list position
                for (int i=0;i<accommodationProviders.size();i++){
                    AccommodationProvider tmp_provoder=accommodationProviders.get(i);
                    if(tmp_provoder.getAccommodationServiceId()==currentServiceId){
                        currentSerialNo=i;
                        break;
                    }
                }
            }

            @Override
            public void onFailure(Call<AccommodationProvider[]> call, Throwable t) {

            }
        });
    }


    private void loadGalleryImage(HotelGallery [] hotelgalleries){
        mDemoSlider.removeAllSliders();
        for(HotelGallery hotelgallery : hotelgalleries){
            String name=hotelgallery.getProviderName();
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(BaseURL.HOTEL_IMAGE_BASE_URL+hotelgallery.getAccommodationGalleryImage())
                    .setScaleType(BaseSliderView.ScaleType.Fit);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);

            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.RotateDown);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
    }

    private void loadHotelDetails(HotelDetails hotelDetails){
        getSupportActionBar().setTitle(hotelDetails.getProviderName());
        providerName.setText(hotelDetails.getProviderName());
        hotelAddress.setText(hotelDetails.getAccommodationServiceAddress());
       hotelEmail.setText(hotelDetails.getAccommodationServiceEmail());
          hotelHotLine.setText(hotelDetails.getAccommodationServiceHotline());
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            hotelDetailsView.setText(Html.fromHtml(hotelDetails.getAccommodationServiceDetails(),Html.FROM_HTML_MODE_LEGACY));
        } else {
            hotelDetailsView.setText(Html.fromHtml(hotelDetails.getAccommodationServiceDetails()));
        }

        hotelRating.setRating(2);

    }
}
