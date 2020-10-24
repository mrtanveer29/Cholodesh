/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package callbacks;

import android.app.Activity;

import helpers.RetrofitInitializer;
import listeners.ProviderListener;
import model.AccommodationProvider;
import model.AccommodationRoom;
import model.Food;
import model.HotelDetails;
import model.HotelGallery;
import model.HotelInclusion;
import model.Package;
import model.PackageDetails;
import model.PackageGallery;
import model.PackageInclusion;
import model.PackageItinerary;
import model.TransportProvider;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Created by Olivine on 5/13/2017.
 */


public class ProvideCallback {
    private Retrofit retrofit;
    private ProviderListener providerListener;

    private TransportProvider [] transportProviders;

    public ProvideCallback(Activity mContext) {
        this.retrofit = RetrofitInitializer.initNetwork(mContext);
        providerListener=retrofit.create(ProviderListener.class);

    }
    public Call<TransportProvider[]> getTransportProviders(int route_id,int transport_type_id){
        return  providerListener.getTransportProviders(route_id,transport_type_id);
    }
    public Call<AccommodationProvider[]> getDestinationWiseAccommodationList(int locationId){
        return  providerListener.getDestinationWiseAccommodationList(locationId);
    }
    public Call<AccommodationRoom[]> getAccommodationRoom(int providerId){
        return  providerListener.getAccommodationRooms(providerId);
    }
    public Call<Food[]> getFoodRestairents(String districtId){
        return  providerListener.getFoodRestaurentList(districtId);
    }
    public Call<Package[]> getAllPackages(){
        return  providerListener.getAllPackages();
    }

    public Call<HotelGallery[]> getHotelGalley( int serviceId){
        return  providerListener.getHotelGalley(serviceId);
    }
    public Call<HotelDetails> getHotelDetails(int serviceId){
        return  providerListener.getHotelDetails(serviceId);
    }
    public Call<HotelInclusion[]> getHotelInclusions(int serviceId){
        return  providerListener.getHotelInclusions(serviceId);
    }
    public Call<PackageDetails> getPackageDetails(int packageId){
        return  providerListener.getPackageDetails(packageId);
    }

    public Call<PackageGallery[]> getPackageImages(int packageId){
        return  providerListener.getPackageImages(packageId);
    }

    public Call<PackageInclusion[]> getPackageInclusions(int packageId){
        return  providerListener.getPackageInclusions(packageId);
    }

    public Call<PackageItinerary[]> getPackageItineraries(int packageId){
        return  providerListener.getPackageItineraries(packageId);
    }

}

