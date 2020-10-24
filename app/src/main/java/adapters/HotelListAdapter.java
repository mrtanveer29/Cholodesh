/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.olivine.cholodesh.HotelDetailsActivity;
import com.example.olivine.cholodesh.R;

import model.AccommodationProvider;

/**
 * Created by Olivine on 6/11/2017.
 */

public class HotelListAdapter extends RecyclerView.Adapter<HotelViewholder> {
    public static final String SERVICE_ID="service_id";
    private Context mContext;
    private AccommodationProvider [] accommodationProviders;

    public HotelListAdapter(Context mContext, AccommodationProvider[] accommodationProviders) {
        this.mContext = mContext;
        this.accommodationProviders = accommodationProviders;
    }

    @Override
    public HotelViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_accommodation_list,parent,false);
        HotelViewholder viewholder=new HotelViewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(HotelViewholder holder, int position) {
        final AccommodationProvider accommodationProvider=accommodationProviders[position];
        holder.hotelNameTextView.setText(accommodationProvider.getProviderName());
        holder.accommodationCategory.setText(accommodationProvider.getAccommodationTypeName());
        holder.hotelAddress.setText(accommodationProvider.getAccommodationServiceAddress());
        holder.parentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent detailsIntent=new Intent(mContext, HotelDetailsActivity.class);
                detailsIntent.putExtra(SERVICE_ID,accommodationProvider.getAccommodationServiceId());
                detailsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(detailsIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return accommodationProviders.length;
    }
}
class HotelViewholder extends RecyclerView.ViewHolder{
    CardView parentView;
    TextView hotelNameTextView;
    TextView accommodationCategory;
    TextView hotelAddress;
    RatingBar hotelRating;

    public HotelViewholder(View itemView) {
        super(itemView);
        parentView = (CardView) itemView.findViewById(R.id.parentView);
        hotelNameTextView = (TextView) itemView.findViewById(R.id.hotelNameTextView);
        accommodationCategory = (TextView) itemView.findViewById(R.id.accommodationCategory);
        hotelAddress = (TextView) itemView.findViewById(R.id.hotelAddress);
        hotelRating = (RatingBar) itemView.findViewById(R.id.hotelRating);
    }
}
