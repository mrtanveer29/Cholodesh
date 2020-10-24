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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import adapters.FoodListAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import callbacks.ProvideCallback;
import constants.Travel;
import model.Food;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodListActivity extends AppCompatActivity {
    private static final int FINISH_MENU = 1;
    @BindView(R.id.foodList) RecyclerView foodList;
    // DataStrorage
    SharedPreferences sharedPreferences;
    // Callback
    ProvideCallback provideCallback;
    // Adapter
    FoodListAdapter foodListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);
        ButterKnife.bind(this);
        initialization();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuItem finishItem=menu.add(Menu.NONE,FINISH_MENU,Menu.NONE,"Back");
        finishItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case FINISH_MENU:
                setResult(200);
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initialization(){
        sharedPreferences=getSharedPreferences(getString(R.string.preference_file_key),MODE_PRIVATE);
        String districtId=sharedPreferences.getInt(Travel.TO_LOCATION,26)+"";
        provideCallback=new ProvideCallback(this);

        provideCallback.getFoodRestairents(districtId).enqueue(new Callback<Food[]>() {
            @Override
            public void onResponse(Call<Food[]> call, Response<Food[]> response) {
                Food [] asdasdasd=response.body();
                Log.e("Food",call.request().url().toString());
                foodListAdapter=new FoodListAdapter(getApplicationContext(),response.body());
                foodList.setAdapter(foodListAdapter);
            }

            @Override
            public void onFailure(Call<Food[]> call, Throwable t) {
                Toast.makeText(FoodListActivity.this, "Network Error!", Toast.LENGTH_SHORT).show();

            }
        });

    }
}
