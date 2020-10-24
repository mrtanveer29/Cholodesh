package com.example.olivine.cholodesh;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import adapters.RouteListAdapter;
import adapters.TripListAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import callbacks.RouteCallback;
import constants.Travel;
import io.realm.Realm;
import listeners.TransportCostListener;
import model.Route;
import model.TransportProvider;
import model.Trip;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import userDefinder.TailorMade;

public class CustomeTrip extends AppCompatActivity implements TransportCostListener{
    private static final int MENU_ITEM_NEXT = 1;
    private static int perPersonTranspotCost =0;
    private static int totalTranspotCost =0;
    private ArrayList<TransportProvider> transportProviders=new ArrayList<>();
    private int from_district_id=0;
    private int to_district_id=0;

    private Route [] routes;
    // Data store
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Realm realm;
    // Views
    @BindView(R.id.tripList) RecyclerView tripList;
    @BindView(R.id.itineraryPlaceholderText) TextView itineraryPlaceholderText;
    @BindView(R.id.transportCostTotal) TextView transportCostTotal;
    @BindView(R.id.GrandTotal) TextView GrandTotal;
    @BindView(R.id.txtPersons) TextView txtPersons;


    //----------------------------------------------
    private RouteCallback routeCallback;
    private TripListAdapter tripListAdapter;
    private ArrayList<Trip> tripArrayList=new ArrayList<>();
    private int person=1;
    private TailorMade tailorMade;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custome_trip);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_clear_24);
        // Data storage init
        sharedPreferences=getSharedPreferences(getString(R.string.preference_file_key),MODE_PRIVATE);
        editor=sharedPreferences.edit();
        Realm.init(this);
        realm=Realm.getDefaultInstance();
        // initialize callbacks
        routeCallback=new RouteCallback(this);

        int current_tailormade_id=sharedPreferences.getInt(Travel.CURRENT_TAILORMADE_ID,0);
        tailorMade=realm.where(TailorMade.class).equalTo("tailormade_id",current_tailormade_id).findFirst();
        List<Route> realm_route=tailorMade.routes;
        if(realm_route!=null && realm_route.size()!=0 ){
            from_district_id=sharedPreferences.getInt(Travel.DEPART_LOCATION,0);
            to_district_id=sharedPreferences.getInt(Travel.TO_LOCATION,0);
            realm_route=realm.copyFromRealm(realm_route);
            Toast.makeText(this, "Not null", Toast.LENGTH_SHORT).show();
//            if(realm_route.get(0).getStartDistrictId()==from_district_id && realm_route.get(realm_route.size()-1).getEndDistrictId()==to_district_id){
                // Add all previous transport provider in selected list
                for (int i=0; i<realm_route.size();i++){
                    TransportProvider temp_provider=realm_route.get(i).getTransportProvider();
                    if (temp_provider!=null){
                        transportProviders.add(temp_provider);
                    }

                }
                routes=realm_route.toArray(new Route[realm_route.size()]);
                RouteListAdapter routeListAdapter=new RouteListAdapter(CustomeTrip.this,routes);
                routeListAdapter.setTransportCostListener(CustomeTrip.this);
                tripList.setAdapter(routeListAdapter);
                return;
//            }
        }
        Toast.makeText(this, "NEW LIST", Toast.LENGTH_LONG).show();
        searchRoute();

//        ------------------------------------------

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem menuItem=menu.add(Menu.NONE,MENU_ITEM_NEXT, Menu.NONE,"Next");
        menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case MENU_ITEM_NEXT:

                realm.beginTransaction();
                tailorMade.transportCost=totalTranspotCost;
                tailorMade.routes.clear();
                tailorMade.routes.addAll(Arrays.asList(routes));
                realm.commitTransaction();
//                .........................................................
                Intent intent=new Intent(CustomeTrip.this,AccommodationActivity.class);
                startActivity(intent);
                // migration done
        }
        return super.onOptionsItemSelected(item);
    }
    private void savetailorMade(TailorMade tailorMade){

    }

    public void searchRoute() {

// ;

        from_district_id=sharedPreferences.getInt(Travel.DEPART_LOCATION,0);
        to_district_id=sharedPreferences.getInt(Travel.TO_LOCATION,0);
        int person=sharedPreferences.getInt(Travel.NUMBER_OF_TOUIST,0);
        txtPersons.setText("Estimated cost ("+person+" Peson)");
        createRouteList(from_district_id,to_district_id);
//        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show();
    }


    private void createRouteList(int location_id,int destination_id){
        routeCallback.getRoutes(location_id,destination_id).enqueue(new Callback<Route[]>() {
            @Override
            public void onResponse(Call<Route[]> call, Response<Route[]> response) {
                routes=response.body();
                RouteListAdapter routeListAdapter=new RouteListAdapter(CustomeTrip.this,routes);
                routeListAdapter.setTransportCostListener(CustomeTrip.this);
                tripList.setAdapter(routeListAdapter);
                perPersonTranspotCost =0;
                transportProviders.clear();
                if(response.body()==null||response.body().length==0){
                    itineraryPlaceholderText.setVisibility(View.VISIBLE);
                }else{
                    itineraryPlaceholderText.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<Route[]> call, Throwable t) {
                Toast.makeText(CustomeTrip.this, "Could not get Route Information", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void addTransportCost(TransportProvider selectedTransportProvider){
        for(TransportProvider tmpProvider:transportProviders){
            if(tmpProvider.getRouteId()==selectedTransportProvider.getRouteId()){
                if(tmpProvider.getTransportInfoId()==selectedTransportProvider.getTransportInfoId()){
                    Toast.makeText(this, "Already Selected", Toast.LENGTH_SHORT).show();
                    return;
                }
                transportProviders.remove(tmpProvider);
                perPersonTranspotCost -=Integer.parseInt(tmpProvider.getTransportInfoPrice());
                totalTranspotCost -=Integer.parseInt(tmpProvider.getTransportInfoPrice())*person;
                break;
            }

        }
         person = sharedPreferences.getInt(Travel.NUMBER_OF_TOUIST, 1);
        transportProviders.add(selectedTransportProvider);
        perPersonTranspotCost +=Integer.parseInt(selectedTransportProvider.getTransportInfoPrice());
        totalTranspotCost +=Integer.parseInt(selectedTransportProvider.getTransportInfoPrice())*person;
        transportCostTotal.setText(perPersonTranspotCost +" ৳");
        GrandTotal.setText(totalTranspotCost+" ৳");

    }


}


