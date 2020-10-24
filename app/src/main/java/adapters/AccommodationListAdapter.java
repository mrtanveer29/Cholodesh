/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.olivine.cholodesh.AccommodationRoomlistActivity;
import com.example.olivine.cholodesh.R;

import java.util.ArrayList;
import java.util.List;

import listeners.AccommodationListener;
import model.AccommodationProvider;

/**
 * Created by Olivine on 5/27/2017.
 */

public class AccommodationListAdapter extends RecyclerView.Adapter<AccommodationListViewholder> {
    private Context mContext;
    private List<AccommodationProvider> accommodationProviders;
    private AccommodationListener accommodationListener;

    public AccommodationListAdapter setAccommodationListener(AccommodationListener accommodationListener) {
        this.accommodationListener = accommodationListener;
        return this;
    }

    public AccommodationListAdapter(Context mContext, List<AccommodationProvider> accommodationProviders) {
        this.mContext = mContext;
        this.accommodationProviders = accommodationProviders;
    }

    @Override
    public AccommodationListViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(mContext).inflate(R.layout.layout_accommodation_list,parent,false);
        AccommodationListViewholder accommodationViewholder=new AccommodationListViewholder(itemView);
        return accommodationViewholder;
    }

    @Override
    public void onBindViewHolder(AccommodationListViewholder holder, int position) {
        final AccommodationProvider accommodationProvider=accommodationProviders.get(position);
        holder.hotelNameTextView.setText(accommodationProvider.getProviderName());
        holder.accommodationCategory.setText(accommodationProvider.getAccommodationTypeName());
        holder.hotelAddress.setText(accommodationProvider.getAccommodationServiceAddress());
        holder.parentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accommodationListener.accommodationActivityResult(accommodationProvider.getAccommodationServiceId());
            }
        });

    }

    @Override
    public int getItemCount() {
        return accommodationProviders.size();
    }
}
class AccommodationListViewholder extends RecyclerView.ViewHolder{
    CardView parentView;
    TextView hotelNameTextView;
    TextView accommodationCategory;
    TextView hotelAddress;
    RatingBar hotelRating;

    public AccommodationListViewholder(View itemView) {
        super(itemView);
        parentView= (CardView) itemView.findViewById(R.id.parentView);
        hotelNameTextView= (TextView) itemView.findViewById(R.id.hotelNameTextView);
        accommodationCategory= (TextView) itemView.findViewById(R.id.accommodationCategory);
        hotelAddress= (TextView) itemView.findViewById(R.id.hotelAddress);
        hotelRating= (RatingBar) itemView.findViewById(R.id.hotelRating);
    }

}
