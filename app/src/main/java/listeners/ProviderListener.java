package listeners;

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
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Olivine on 5/13/2017.
 */

public interface ProviderListener {
    @GET("provider/routeWiseTransportType/{route_from}/{route_to}")
    Call<TransportProvider[]> getTransportProviders(@Path("route_from") int route_to,@Path("route_to") int route_from);

    @GET("provider/routeAccommodationList/{location}")
    Call<AccommodationProvider[]> getDestinationWiseAccommodationList(@Path("location") int locationId);
    @GET("provider/accommodationRoom/{provider_id}")
    Call<AccommodationRoom[]> getAccommodationRooms(@Path("provider_id") int providerId);

    @GET("district/food/{districtId}")
    Call<Food[]> getFoodRestaurentList(@Path("districtId") String id);

    @GET("provider/allPackages")
    Call<Package[]> getAllPackages();

    @GET("provider/accommodationGallery/{service_id}")
    Call<HotelGallery[]> getHotelGalley(@Path("service_id") int id);

    @GET("provider/accommodationDetails/{service_id}")
    Call<HotelDetails> getHotelDetails(@Path("service_id") int id);

    @GET("provider/accommodationFacilities/{service_id}")
    Call<HotelInclusion[]> getHotelInclusions(@Path("service_id") int id);

    @GET("provider/packageDetails/{package_id}")
    Call<PackageDetails> getPackageDetails(@Path("package_id") int id);

    @GET("provider/packageGallery/{package_id}")
    Call<PackageGallery[]> getPackageImages(@Path("package_id") int id);

    @GET("provider/packageInclusion/{package_id}")
    Call<PackageInclusion[]> getPackageInclusions(@Path("package_id") int id);

    @GET("provider/packageItinerary/{package_id}")
    Call<PackageItinerary[]> getPackageItineraries(@Path("package_id") int id);
}
