/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.olivine.cholodesh.R;

import java.util.ArrayList;
import java.util.List;

import userDefinder.TailorMade;

/**
 * Created by Olivine on 6/7/2017.
 */

public class TailormadeListAdapter extends RecyclerView.Adapter<TailorMadeViewholder> {
    private Context mContext;
    private List<TailorMade> tailorMades;

    public TailormadeListAdapter(Context mContext, List<TailorMade> tailorMades) {
        this.mContext = mContext;
        this.tailorMades = tailorMades;
    }

    @Override
    public TailorMadeViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(mContext).inflate(R.layout.layout_tailormade_list,parent,false);
        TailorMadeViewholder tailorMadeViewholder=new TailorMadeViewholder(view);
        return tailorMadeViewholder;
    }

    @Override
    public void onBindViewHolder(TailorMadeViewholder holder, int position) {
        TailorMade tailorMade=tailorMades.get(position);
        holder.txtTourTitle.setText(tailorMade.getNumberOFDays()+" Days in "+tailorMade.destinationDistrictName);
        holder.txtTourFrom.setText("From "+tailorMade.departDistrictName);
        holder.txtCost.setText(tailorMade.transportCost+tailorMade.accommodationCost+tailorMade.itineraryCost+"৳");
        holder.txtTourDate.setText("at "+tailorMade.departDate);

    }

    @Override
    public int getItemCount() {
        return tailorMades.size();
    }
}
class TailorMadeViewholder extends RecyclerView.ViewHolder{
    TextView txtTourTitle;
    TextView txtTourFrom;
    TextView txtTourDate;
    TextView txtCost;

    public TailorMadeViewholder(View itemView) {
        super(itemView);
        txtTourTitle= (TextView) itemView.findViewById(R.id.txtTourTitle);
        txtTourFrom= (TextView) itemView.findViewById(R.id.txtTourFrom);
        txtTourDate= (TextView) itemView.findViewById(R.id.txtTourDate);
        txtCost= (TextView) itemView.findViewById(R.id.txtCost);
    }
}
