package adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.olivine.cholodesh.R;

import java.util.ArrayList;

import model.LocalTour;

/**
 * Created by Olivine on 5/9/2017.
 */

public class LocalTourAdapter extends RecyclerView.Adapter<LocalTourListViewHolder>{
    Context mContext;
    private LocalTour []tripList;

    public LocalTourAdapter(Context mContext, LocalTour []tripList) {
        this.mContext = mContext;
        this.tripList = tripList;
    }

    public LocalTour[] getTripList() {
        return tripList;
    }

    @Override
    public LocalTourListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layout= LayoutInflater.from(mContext).inflate(R.layout.layout_local_tour_list,parent,false);
        LocalTourListViewHolder holder=new LocalTourListViewHolder(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(LocalTourListViewHolder holder, int position) {
        LocalTour localTour=tripList[position];
        holder.textLocation.setText(localTour.getDestinationNearbyPlaceName());
        holder.textTourType.setText(localTour.getLocalTourDuration());
        holder.tourCost.setText(localTour.getLocalTourPerPersonCost()+"৳");
        holder.textReservationType.setText(localTour.getReservationTypeName());
        holder.localTransport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return tripList.length;
    }
}
 class LocalTourListViewHolder extends RecyclerView.ViewHolder{
     ImageView localTransport;
     TextView textLocation;
     TextView textTourType;
     TextView textReservationType;
     TextView tourCost;
    public LocalTourListViewHolder(View itemView) {
        super(itemView);
        localTransport= (ImageView) itemView.findViewById(R.id.localTransport);
        textLocation= (TextView) itemView.findViewById(R.id.textLocation);
        textTourType= (TextView) itemView.findViewById(R.id.textTourType);
        tourCost= (TextView) itemView.findViewById(R.id.tourCost);
        textReservationType= (TextView) itemView.findViewById(R.id.textReservationType);

    }
}
