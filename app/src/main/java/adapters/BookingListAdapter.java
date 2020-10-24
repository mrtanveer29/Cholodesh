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

import userDefinder.Booking;
import userDefinder.PackageReservation;

/**
 * Created by Olivine on 6/14/2017.
 */

public class BookingListAdapter extends RecyclerView.Adapter<BookinglistViewholder> {
    private Context mContext;
    private List<Object> bookings;

    public BookingListAdapter(Context mContext, List<Object> bookings) {
        this.mContext = mContext;
        this.bookings = bookings;
    }

    @Override
    public BookinglistViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_booking_list,parent,false);
        BookinglistViewholder bookinglistViewholder=new BookinglistViewholder(view);

        return bookinglistViewholder;
    }

    @Override
    public void onBindViewHolder(BookinglistViewholder holder, int position) {

            Object booking=bookings.get(position);
            if(booking instanceof Booking) {
                Booking hotelBooking = (Booking) booking;
                holder.hotelName.setText(hotelBooking.accommodationRooms.get(0).getProviderName());
                holder.bookingToken.setText(hotelBooking.tokenNO);
            }
            if(booking instanceof PackageReservation){
                PackageReservation packageReservation= (PackageReservation) booking;
                holder.packageName.setText("By "+packageReservation.packages.getProviderName());
                holder.hotelName.setText(packageReservation.packages.getPackageName());
                holder.bookingToken.setText(packageReservation.token);

            }

    }

    @Override
    public int getItemCount() {
        return bookings.size();
    }
}
class BookinglistViewholder extends RecyclerView.ViewHolder{
    public BookinglistViewholder(View itemView) {
        super(itemView);
        hotelName= (TextView) itemView.findViewById(R.id.hotelName);
        bookingToken= (TextView) itemView.findViewById(R.id.bookingToken);
        packageName= (TextView) itemView.findViewById(R.id.packageName);
    }
    TextView hotelName;
    TextView bookingToken;
    TextView packageName;

}
