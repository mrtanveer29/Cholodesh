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
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.olivine.cholodesh.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import model.AccommodationProvider;
import model.AccommodationRoom;
import model.ListWrapper;

/**
 * Created by Tanveer on 5/27/2017.
 */

public class AccommodationRoomListAdapter extends RecyclerView.Adapter<AccommodationRoomViewholder> {

    private Context mContext;
    private List<AccommodationRoom> accommodationRooms;

    public AccommodationRoomListAdapter(Context mContext, List<AccommodationRoom> accommodationRooms) {
        this.mContext = mContext;
        this.accommodationRooms = accommodationRooms;
    }
    @Override
    public AccommodationRoomViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(mContext).inflate(R.layout.layout_hotel_room_list,parent,false);
        AccommodationRoomViewholder accommodationViewholder=new AccommodationRoomViewholder(itemView);
        return accommodationViewholder;
    }

    @Override
    public void onBindViewHolder(AccommodationRoomViewholder holder, int position) {
        final AccommodationRoom accommodationRoom=accommodationRooms.get(position);
        holder.accommodationName.setText(accommodationRoom.getProviderName());
        holder.occupancy.setText(accommodationRoom.getAccommodationRoomMaxOccupancy()+"");
        holder.roomType.setText(accommodationRoom.getAccommodationCategoryName());
        holder.roomPrice.setText(accommodationRoom.getAccommodationRoomPrice()+"৳");
        holder.bedType.setText("Single Bed");

        holder.roomListParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleSelecion(view,accommodationRoom);
            }
        });

    }

    @Override
    public int getItemCount() {
        return accommodationRooms.size();
    }
    private void toggleSelecion(View view,AccommodationRoom accommodationRoom){
        if(accommodationRoom.isSelected()){
            view.setBackgroundColor(Color.parseColor("#ffffff"));
            accommodationRoom.setSelected(false);
        }else{
            view.setBackgroundColor(Color.parseColor("#90c468"));
            accommodationRoom.setSelected(true);
        }

    }
}
class AccommodationRoomViewholder extends RecyclerView.ViewHolder{
    LinearLayout roomListParent;
    TextView occupancy;
    TextView accommodationName; //// STOPSHIP: 6/2/2017  
    TextView roomType; //s
    TextView bedType;
    TextView roomPrice;

    public AccommodationRoomViewholder(View itemView) {
        super(itemView);
        roomListParent= (LinearLayout) itemView.findViewById(R.id.roomListParent);
        occupancy= (TextView) itemView.findViewById(R.id.occupancy);
        roomType= (TextView) itemView.findViewById(R.id.roomType);
        accommodationName= (TextView) itemView.findViewById(R.id.accommodationName);
        bedType= (TextView) itemView.findViewById(R.id.bedType);
        roomPrice= (TextView) itemView.findViewById(R.id.roomPrice);
    }

}
