/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package userDefinder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import model.AccommodationRoom;
import model.Itenerary;
import model.Route;
import model.TransportProvider;

/**
 * Created by Olivine on 6/7/2017.
 */

public class TailormadeSync {
    @SerializedName("customer_id")
    @Expose
    private String customerId;
    @SerializedName("tailormade_id")
    @Expose
    private int tailormade_id;
    @SerializedName("deapartDistrictId")
    @Expose
    private int deapartDistrictId;
    @SerializedName("destinationDistrictId")
    @Expose
    private int destinationDistrictId;
    @SerializedName("departDistrictName")
    @Expose
    private String departDistrictName;
    @SerializedName("destinationDistrictName")
    @Expose
    private String destinationDistrictName;
    @SerializedName("departDate")
    @Expose
    private String departDate;
    @SerializedName("returnDate")
    @Expose
    private String returnDate;
    @SerializedName("numberOFDays")
    @Expose
    private int numberOFDays;
    @SerializedName("numberOFTourists")
    @Expose
    private  int numberOFTourists;
    @SerializedName("routes")
    @Expose
    private List<Route> transportProviders=new ArrayList<>();
    @SerializedName("accommodationProviders")
    @Expose
    private List<AccommodationRoom> accommodationProviders=new ArrayList<>();
    @SerializedName("iteneraries")
    @Expose
    private List<Itenerary> iteneraries=new ArrayList<>();

    public String getCustomerId() {
        return customerId;
    }

    public TailormadeSync setCustomerId(String customerId) {
        this.customerId = customerId;
        return this;
    }

    public List<Route> getTransportProviders() {
        return transportProviders;
    }

    public TailormadeSync setTransportProviders(List<Route> transportProviders) {
        this.transportProviders = transportProviders;
        return this;
    }

    public List<AccommodationRoom> getAccommodationProviders() {
        return accommodationProviders;
    }

    public TailormadeSync setAccommodationProviders(List<AccommodationRoom> accommodationProviders) {
        this.accommodationProviders = accommodationProviders;
        return this;
    }

    public List<Itenerary> getIteneraries() {
        return iteneraries;
    }

    public TailormadeSync setIteneraries(List<Itenerary> iteneraries) {
        this.iteneraries = iteneraries;
        return this;
    }

    public TailormadeSync setTailormade_id(int tailormade_id) {
        this.tailormade_id = tailormade_id;
        return this;
    }

    public TailormadeSync setDeapartDistrictId(int deapartDistrictId) {
        this.deapartDistrictId = deapartDistrictId;
        return this;
    }

    public TailormadeSync setDestinationDistrictId(int destinationDistrictId) {
        this.destinationDistrictId = destinationDistrictId;
        return this;
    }

    public TailormadeSync setDepartDistrictName(String departDistrictName) {
        this.departDistrictName = departDistrictName;
        return this;
    }

    public TailormadeSync setDestinationDistrictName(String destinationDistrictName) {
        this.destinationDistrictName = destinationDistrictName;
        return this;
    }

    public TailormadeSync setDepartDate(String departDate) {
        this.departDate = departDate;
        return this;
    }

    public TailormadeSync setReturnDate(String returnDate) {
        this.returnDate = returnDate;
        return this;
    }

    public TailormadeSync setNumberOFDays(int numberOFDays) {
        this.numberOFDays = numberOFDays;
        return this;
    }

    public TailormadeSync setNumberOFTourists(int numberOFTourists) {
        this.numberOFTourists = numberOFTourists;
        return this;
    }



    public int getTailormade_id() {
        return tailormade_id;
    }

    public int getDeapartDistrictId() {
        return deapartDistrictId;
    }

    public int getDestinationDistrictId() {
        return destinationDistrictId;
    }

    public String getDepartDistrictName() {
        return departDistrictName;
    }

    public String getDestinationDistrictName() {
        return destinationDistrictName;
    }

    public String getDepartDate() {
        return departDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public int getNumberOFDays() {
        return numberOFDays;
    }

    public int getNumberOFTourists() {
        return numberOFTourists;
    }


}
