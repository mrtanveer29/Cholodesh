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

import java.util.ArrayList;
import java.util.List;

import adapters.TailormadeListAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;
import userDefinder.TailorMade;
import userDefinder.TailormadeSync;

public class TailormadeListActivity extends AppCompatActivity {
    @BindView(R.id.tailormadeList) RecyclerView tailormadeList;

    // Adapters
    TailormadeListAdapter tailormadeListAdapter;
    // data storage
    SharedPreferences sharedPreferences;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tailormade_list);
        ButterKnife.bind(this);

        // Data storage init
        sharedPreferences=getSharedPreferences(getString(R.string.preference_file_key),MODE_PRIVATE);
        Realm.init(this);
        realm=Realm.getDefaultInstance();
        // Get Data from database
        RealmResults<TailorMade> realm_tailormade=realm.where(TailorMade.class).findAll();

        List<TailorMade> tailormadesToShow=realm.copyFromRealm(realm_tailormade);
        // Adapter init
        tailormadeListAdapter=new TailormadeListAdapter(this,tailormadesToShow);
        tailormadeList.setAdapter(tailormadeListAdapter);


    }
}
