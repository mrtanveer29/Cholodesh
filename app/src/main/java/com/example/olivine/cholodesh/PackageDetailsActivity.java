/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.olivine.cholodesh;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.transition.Slide;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.function.BiFunction;

import adapters.InclusionAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import callbacks.ProvideCallback;
import constants.Travel;
import helpers.BaseURL;
import io.realm.Realm;
import model.HotelGallery;
import model.PackageDetails;
import model.PackageGallery;
import model.PackageInclusion;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import userDefinder.PackageReservation;

public class PackageDetailsActivity extends AppCompatActivity {
    // API Init
    PackageDetails tourPackage;
    Realm realm;
    ProvideCallback provideCallback;
    @BindView(R.id.PackageName) TextView PackageName;
    @BindView(R.id.tourShortDetails) TextView tourShortDetails;
    @BindView(R.id.dayNight) TextView dayNight;
    @BindView(R.id.tourCost) TextView tourCost;
    @BindView(R.id.providerName) TextView providerName;
    @BindView(R.id.providerAddress) TextView providerAddress;
    @BindView(R.id.providerEmail) TextView providerEmail;
    @BindView(R.id.providerHotLine) TextView providerHotLine;
    @BindView(R.id.tourActivities) TextView tourActivities;
    @BindView(R.id.packageOverview) TextView packageOverview;
    @BindView(R.id.mDemoSlider) SliderLayout mDemoSlider;
    @BindView(R.id.inclusions) RecyclerView inclusions;

    @OnClick(R.id.viewRooms)
    public void viewDetailsPlan(View view){
        loadItineraries(6);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_details);
        ButterKnife.bind(this);
        // Api init
        provideCallback=new ProvideCallback(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Realm.init(
                this
        );
        realm=Realm.getDefaultInstance();
        int packageId=getIntent().getIntExtra(Travel.PACKAGE_KEY,0);

        loadPackageDetails(packageId);
        loadGallery(packageId);
        loadInclusion(packageId);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });
    }
    private void registerUser(){
        String url=BaseURL.BASE_URL+"provider/packageBooking";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(PackageDetailsActivity.this, response, Toast.LENGTH_SHORT).show();
               if(response.equals("false")){
                   Toast.makeText(PackageDetailsActivity.this, response, Toast.LENGTH_SHORT).show();
               }else{
                   realm.beginTransaction();
                   PackageReservation par=new PackageReservation();
                   par.token=response;
                   par.packages=tourPackage;
                   realm.copyToRealm(par);
                   realm.commitTransaction();
               }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PackageDetailsActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                String email="01551718411";
                Map<String,String> params = new HashMap<String, String>();
                params.put("package_id",tourPackage.getPackageId()+"");
                params.put("customer_email","01551817411");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void loadItineraries(int packageId) {
        Intent packageIteneraryIntent=new Intent(PackageDetailsActivity.this,PackageItinerayActivity.class);
        packageIteneraryIntent.putExtra(Travel.INTENT_PACKAGE_ID,packageId);
        startActivity(packageIteneraryIntent);
    }

    private void loadInclusion(int packageId) {
        provideCallback.getPackageInclusions(packageId).enqueue(new Callback<PackageInclusion[]>() {
            @Override
            public void onResponse(Call<PackageInclusion[]> call, Response<PackageInclusion[]> response) {
                InclusionAdapter inclusionAdapter=new InclusionAdapter(getApplicationContext(),response.body());
                inclusions.setAdapter(inclusionAdapter);
            }

            @Override
            public void onFailure(Call<PackageInclusion[]> call, Throwable t) {

            }
        });
    }

    private void loadPackageDetails(int packageid){
        provideCallback.getPackageDetails(packageid).enqueue(new Callback<PackageDetails>() {
            @Override
            public void onResponse(Call<PackageDetails> call, Response<PackageDetails> response) {
                PackageDetails packageDetails=response.body();
                if(packageDetails!=null){
                    tourPackage=packageDetails;
                    PackageName.setText(packageDetails.getPackageName());
                    tourCost.setText("At "+packageDetails.getPackagePrice()+" ৳");
                    providerName.setText(packageDetails.getProviderName());
                    providerAddress.setText("Address: "+packageDetails.getProviderAddress());
                    providerEmail.setText("Email: "+packageDetails.getProviderEmail());
                    providerHotLine.setText("Horline: "+packageDetails.getProviderHotline());
                    tourActivities.setText(Html.fromHtml(packageDetails.getPackageActivities()));
                    packageOverview.setText(Html.fromHtml(packageDetails.getPackageOverview()));
                    dayNight.setText(packageDetails.getPackageDay()+" Day "+packageDetails.getPackageNight()+" Night");
                    tourShortDetails.setText(packageDetails.getPackageLocationFrom()+" - "+packageDetails.getPackageLocationTo());
                }
            }

            @Override
            public void onFailure(Call<PackageDetails> call, Throwable t) {
                Toast.makeText(PackageDetailsActivity.this, "Network Error!", Toast.LENGTH_SHORT).show();
                Log.e("Hotel Error",call.request().url().toString());
            }
        });
    }
    private void loadGallery(int packageId){
        provideCallback.getPackageImages(packageId).enqueue(new Callback<PackageGallery[]>() {
            @Override
            public void onResponse(Call<PackageGallery[]> call, Response<PackageGallery[]> response) {
                PackageGallery[] packageGalleries=response.body();
                if(packageGalleries==null){
                    return;
                }
                for(PackageGallery packageGallery : packageGalleries){
                    String name=packageGallery.getPackageName();
                    TextSliderView textSliderView = new TextSliderView(PackageDetailsActivity.this);
                    // initialize a SliderLayout
                    textSliderView
                            .description(name)
                            .image(BaseURL.PACKAGE_IMAGE_BASE_URL+packageGallery.getPackageGalleryImage())
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

            @Override
            public void onFailure(Call<PackageGallery[]> call, Throwable t) {
                Toast.makeText(PackageDetailsActivity.this, "Network Error!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
