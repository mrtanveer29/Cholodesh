/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.example.olivine.cholodesh.AccommodationProviderActivity;
import com.example.olivine.cholodesh.R;

import java.util.ArrayList;

import listeners.AccommodationListener;
import model.AccommodationProvider;
import model.AccommodationRoom;

import static com.example.olivine.cholodesh.AccommodationActivity.ACTION_DECREASE_ROOM;
import static com.example.olivine.cholodesh.AccommodationActivity.ACTION_DELETE_ROOM;
import static com.example.olivine.cholodesh.AccommodationActivity.ACTION_INCRESE_ROOM;

/**
 * Created by Olivine on 5/27/2017.
 */

public class RouteAccommodationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TITLEVIEW=0;
    private static final int ACCOMMODATIONVIEW=1;

    private int currentDestination=0;
    private int lastListposition;
    private Activity mContext;
    private  ArrayList<AccommodationRoom> accommodationRooms;
    private ArrayList<Integer> itemCount=new ArrayList<>();
    private AccommodationListener accommodationListener;

    public RouteAccommodationAdapter setAccommodationListener(AccommodationListener accommodationListener) {
        this.accommodationListener = accommodationListener;
        return this;
    }

    public RouteAccommodationAdapter(Activity mContext, ArrayList<AccommodationRoom> accommodationRooms) {
        this.mContext = mContext;
        this.accommodationRooms = accommodationRooms;

        for(int i=0;i<accommodationRooms.size();i++){
            int tmp_id= accommodationRooms.get(i).getDistrictId();
            if(tmp_id!=currentDestination){
                itemCount.add(TITLEVIEW);
                currentDestination=tmp_id;
            }else{
                itemCount.add(ACCOMMODATIONVIEW);
            }

        }
    }


    @Override
    public int getItemViewType(int position) {

        switch (itemCount.get(position)){
            case TITLEVIEW:
                return TITLEVIEW;
            case ACCOMMODATIONVIEW:
                return  ACCOMMODATIONVIEW;
        }
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case TITLEVIEW:
                View titleView=LayoutInflater.from(mContext).inflate(R.layout.layout_global_list_title,parent,false);
                return new GlobaltitleViewholder(titleView);
            case ACCOMMODATIONVIEW:
                View itemView=LayoutInflater.from(mContext).inflate(R.layout.layout_selected_room,parent,false);
                return new SelectedAccommodationViewHolder(itemView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        switch (getItemViewType(position)){
            case TITLEVIEW:
                GlobaltitleViewholder globaltitleViewholder= (GlobaltitleViewholder) holder;
                globaltitleViewholder.routeTitle.setText(accommodationRooms.get(position).getDistrictName());
                globaltitleViewholder.addHotel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(mContext,AccommodationProviderActivity.class);
                        intent.putExtra("district_id",accommodationRooms.get(position).getDistrictId());
                        (mContext).startActivityForResult(intent,accommodationRooms.get(position).getDistrictId());
                    }
                });
                break;
            case ACCOMMODATIONVIEW:
                final AccommodationRoom tmp_provider=accommodationRooms.get(position);
                final SelectedAccommodationViewHolder selectedAccommodationViewHolder= (SelectedAccommodationViewHolder) holder;
                selectedAccommodationViewHolder.accommodationName.setText(tmp_provider.getProviderName());
                selectedAccommodationViewHolder.occupency.setText(tmp_provider.getAccommodationRoomMaxOccupancy()+"");
                selectedAccommodationViewHolder.accommodationName.setText(tmp_provider.getProviderName());

                if(tmp_provider.getQuantity()==1){
                    selectedAccommodationViewHolder.roomType.setText(tmp_provider.getAccommodationCategoryName());
                }
                selectedAccommodationViewHolder.roomType.setText(tmp_provider.getQuantity()+" "+tmp_provider.getAccommodationCategoryName());
                selectedAccommodationViewHolder.roomPrice.setText(tmp_provider.getQuantity()*Integer.parseInt(tmp_provider.getAccommodationRoomPrice())+"৳");


                selectedAccommodationViewHolder.bedType.setText("Single " + "bed");
                selectedAccommodationViewHolder.increaseRoom.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tmp_provider.setQuantity(tmp_provider.getQuantity()+1);
                        selectedAccommodationViewHolder.roomType.setText(tmp_provider.getQuantity()+" "+tmp_provider.getAccommodationCategoryName());
                        selectedAccommodationViewHolder.roomPrice.setText(tmp_provider.getQuantity()*Integer.parseInt(tmp_provider.getAccommodationRoomPrice())+"৳");
                        accommodationListener.calculateAccommodationCost(tmp_provider,ACTION_INCRESE_ROOM);
                    }
                });
                selectedAccommodationViewHolder.decreaseRoom.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(tmp_provider.getQuantity()>1){
                            tmp_provider.setQuantity(tmp_provider.getQuantity()-1);
                            selectedAccommodationViewHolder.roomType.setText(tmp_provider.getQuantity()+" "+tmp_provider.getAccommodationCategoryName());
                            selectedAccommodationViewHolder.roomPrice.setText(tmp_provider.getQuantity()*Integer.parseInt(tmp_provider.getAccommodationRoomPrice())+"৳");
                            accommodationListener.calculateAccommodationCost(tmp_provider,ACTION_DECREASE_ROOM);
                        }
                    }
                });
                selectedAccommodationViewHolder.deleteAccomodation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int position=accommodationRooms.indexOf(tmp_provider);
                        itemCount.remove(position);
                        accommodationRooms.remove(tmp_provider);
                        notifyDataSetChanged();
                        accommodationListener.calculateAccommodationCost(tmp_provider,ACTION_DELETE_ROOM);
                    }
                });
                break;
        }
    }

    @Override
    public int getItemCount() {
        return accommodationRooms.size();
    }
}
class GlobaltitleViewholder extends RecyclerView.ViewHolder{
    TextView routeTitle;
    Button addHotel;
    public GlobaltitleViewholder(View itemView) {
        super(itemView);
        routeTitle= (TextView) itemView.findViewById(R.id.routeTitle);
        addHotel= (Button) itemView.findViewById(R.id.addHotel);
    }
}

class SelectedAccommodationViewHolder extends RecyclerView.ViewHolder{
    LinearLayout roomListParent;
    TextView decreaseRoom;
    TextView increaseRoom;
    TextView deleteAccomodation;
    TextView occupency;
    TextView accommodationName;
    TextView roomType;
    TextView bedType;
    TextView roomPrice;
    SwipeLayout swipeLayout;

    public SelectedAccommodationViewHolder(View itemView) {
        super(itemView);
        //Swipe
        swipeLayout= (SwipeLayout) itemView.findViewById(R.id.swipeLayout);
        decreaseRoom= (TextView) itemView.findViewById(R.id.decreaseRoom);
        increaseRoom= (TextView) itemView.findViewById(R.id.increaseRoom);
        deleteAccomodation= (TextView) itemView.findViewById(R.id.deleteAccomodation);
        roomListParent= (LinearLayout) itemView.findViewById(R.id.roomListParent);
        occupency= (TextView) itemView.findViewById(R.id.availableRooms);
        roomType= (TextView) itemView.findViewById(R.id.roomTypenQuantity);
        accommodationName= (TextView) itemView.findViewById(R.id.accommodationName);
        bedType= (TextView) itemView.findViewById(R.id.occupency);
        roomPrice= (TextView) itemView.findViewById(R.id.roomPrice);

    }

}
