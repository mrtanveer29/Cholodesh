package com.example.olivine.cholodesh;

import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;


import java.util.Calendar;

import adapters.PackageAdapter;
import adapters.RouteListAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import callbacks.ProvideCallback;
import helpers.RetrofitInitializer;
import model.Package;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeActivity extends AppCompatActivity {

    private static final int SEARCH_MENU=0;
    private Retrofit retrofit;
    int hitCount=0;
    long time=0;


    // adapter initialization
    private RouteListAdapter routeListAdapter;

    // Componets initialization

    @BindView(R.id.packages) GridView packages;
    private ProvideCallback provideCallback;
    @BindView(R.id.placeHolderImage)
    ImageView placeHolderImage;

    // menu item componets
    @OnClick(R.id.menu_accommodation)
    public void goToAccommodationActivity(View view){
        Intent intent=new Intent(HomeActivity.this,HotelListActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.myTrip)
    public void goToTailormadeList(View view){
        Intent intent=new Intent(HomeActivity.this,TailormadeListActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.menu_tailorMade)
    public void goToTailorMade(View view){
        Intent tailorMadeIntent=new Intent(this,TripPlannerActivity.class);
        startActivity(tailorMadeIntent);
        getSupportActionBar().setTitle("Promotions");
        overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
    }
    @OnClick(R.id.bookings)
    public void gotoBookingList(View view){
        Intent intent=new Intent(this,BookingActivity.class);
        startActivity(intent);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Cholo Desh");
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        // api init
        retrofit= RetrofitInitializer.initNetwork(this);
        //-------------------------------------------------

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       MenuItem item= menu.add(Menu.NONE,SEARCH_MENU,Menu.NONE,"Search");
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch (id){
            case SEARCH_MENU :
                Intent intent=new Intent(HomeActivity.this,CustomeTrip.class);

                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        long currentTime=System.currentTimeMillis();
        if(currentTime-time<=3000){
            hitCount++;
        }
        else{
            Toast.makeText(this, "Press Back again to close", Toast.LENGTH_SHORT).show();
            hitCount=0;
        }

        if(hitCount==1){
            Toast.makeText(this, "Exit", Toast.LENGTH_SHORT).show();
        }
        time=currentTime;
    }
}
