/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.olivine.cholodesh;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.stepstone.stepper.StepperLayout;

import adapters.StepperAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import fragments.RouteSelectionFragment;
import listeners.FragmentInteractionListener;

public class TailorMadeActivity extends AppCompatActivity implements FragmentInteractionListener {
    @BindView(R.id.stepperLayout) StepperLayout stepperLayout;
    private RouteSelectionFragment routeSelectionFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tailor_made);
        ButterKnife.bind(this);
        routeSelectionFragment=new RouteSelectionFragment();
        StepperAdapter stepperAdapter=new StepperAdapter(getSupportFragmentManager(),this);
        stepperLayout.setAdapter(stepperAdapter);
    }


}
