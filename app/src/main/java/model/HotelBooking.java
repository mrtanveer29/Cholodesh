/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Olivine on 6/14/2017.
 */

public class HotelBooking {
    @SerializedName("customer_email")
    @Expose
    private String customerEmail;

    @SerializedName("booking_list")
    @Expose
    List<AccommodationRoom> accommodationRoomList;

    public String getCustomerEmail() {
        return customerEmail;
    }

    public HotelBooking setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
        return this;
    }

    public List<AccommodationRoom> getAccommodationRoomList() {
        return accommodationRoomList;
    }

    public HotelBooking setAccommodationRoomList(List<AccommodationRoom> accommodationRoomList) {
        this.accommodationRoomList = accommodationRoomList;
        return this;
    }
}
