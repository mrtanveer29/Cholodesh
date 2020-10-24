package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Package extends RealmObject {

    @SerializedName("provider_id")
    @Expose
    private Integer providerId;
    @SerializedName("provider_name")
    @Expose
    private String providerName;
    @SerializedName("destination_id")
    @Expose
    private Integer destinationId;
    @SerializedName("destination_name")
    @Expose
    private String destinationName;
    @SerializedName("package_name")
    @Expose
    private String packageName;
    @SerializedName("package_id")
    @Expose
    private Integer packageId;
    @SerializedName("package_day")
    @Expose
    private String packageDay;
    @SerializedName("package_night")
    @Expose
    private String packageNight;
    @SerializedName("package_location_from")
    @Expose
    private String packageLocationFrom;
    @SerializedName("package_location_to")
    @Expose
    private String packageLocationTo;
    @SerializedName("package_price")
    @Expose
    private String packagePrice;
    @SerializedName("package_location_route")
    @Expose
    private String packageLocationRoute;
    @SerializedName("package_discount")
    @Expose
    private String packageDiscount;
    @SerializedName("package_gallery_image")
    @Expose
    private String packageGalleryImage;

    public Integer getProviderId() {
        return providerId;
    }

    public void setProviderId(Integer providerId) {
        this.providerId = providerId;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public Integer getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(Integer destinationId) {
        this.destinationId = destinationId;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Integer getPackageId() {
        return packageId;
    }

    public void setPackageId(Integer packageId) {
        this.packageId = packageId;
    }

    public String getPackageDay() {
        return packageDay;
    }

    public void setPackageDay(String packageDay) {
        this.packageDay = packageDay;
    }

    public String getPackageNight() {
        return packageNight;
    }

    public void setPackageNight(String packageNight) {
        this.packageNight = packageNight;
    }

    public String getPackageLocationFrom() {
        return packageLocationFrom;
    }

    public void setPackageLocationFrom(String packageLocationFrom) {
        this.packageLocationFrom = packageLocationFrom;
    }

    public String getPackageLocationTo() {
        return packageLocationTo;
    }

    public void setPackageLocationTo(String packageLocationTo) {
        this.packageLocationTo = packageLocationTo;
    }

    public String getPackagePrice() {
        return packagePrice;
    }

    public void setPackagePrice(String packagePrice) {
        this.packagePrice = packagePrice;
    }

    public String getPackageLocationRoute() {
        return packageLocationRoute;
    }

    public void setPackageLocationRoute(String packageLocationRoute) {
        this.packageLocationRoute = packageLocationRoute;
    }

    public String getPackageDiscount() {
        return packageDiscount;
    }

    public void setPackageDiscount(String packageDiscount) {
        this.packageDiscount = packageDiscount;
    }

    public String getPackageGalleryImage() {
        return packageGalleryImage;
    }

    public void setPackageGalleryImage(String packageGalleryImage) {
        this.packageGalleryImage = packageGalleryImage;
    }

}