package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class AccommodationRoom extends RealmObject{
    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
        return;
    }
    @SerializedName("quantity")
    @Expose
    private int quantity=1;
    @SerializedName("accommodation_service_id")
    @Expose
    private Integer accommodationServiceId;
    @SerializedName("district_name")
    @Expose
    private String districtName;
    @SerializedName("district_id")
    @Expose
    private Integer districtId;
    @SerializedName("provider_id")
    @Expose
    private Integer providerId;
    @SerializedName("provider_name")
    @Expose
    private String providerName;
    @SerializedName("accommodation_room_id")
    @Expose
    private Integer accommodationRoomId;
    @SerializedName("accommodation_room_type")
    @Expose
    private String accommodationRoomType;
    @SerializedName("accommodation_room_max_occupancy")
    @Expose
    private String accommodationRoomMaxOccupancy;
    @SerializedName("accommodation_room_price")
    @Expose
    private String accommodationRoomPrice;
    @SerializedName("accommodation_category_id")
    @Expose
    private Integer accommodationCategoryId;
    @SerializedName("accommodation_category_name")
    @Expose
    private String accommodationCategoryName;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        return;
    }

    public String getDistrictName() {
        return districtName;
    }

    public AccommodationRoom setDistrictName(String districtName) {
        this.districtName = districtName;
        return this;
    }

    public Integer getDistrictId() {
        return districtId;
    }

    public AccommodationRoom setDistrictId(Integer districtId) {
        this.districtId = districtId;
        return this;
    }

    public Integer getAccommodationServiceId() {
        return accommodationServiceId;
    }

    public AccommodationRoom setAccommodationServiceId(Integer accommodationServiceId) {
        this.accommodationServiceId = accommodationServiceId;
        return this;
    }

    public Integer getProviderId() {
        return providerId;
    }

    public AccommodationRoom setProviderId(Integer providerId) {
        this.providerId = providerId;
        return this;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public Integer getAccommodationRoomId() {
        return accommodationRoomId;
    }

    public void setAccommodationRoomId(Integer accommodationRoomId) {
        this.accommodationRoomId = accommodationRoomId;
    }

    public String getAccommodationRoomType() {
        return accommodationRoomType;
    }

    public void setAccommodationRoomType(String accommodationRoomType) {
        this.accommodationRoomType = accommodationRoomType;
    }

    public String getAccommodationRoomMaxOccupancy() {
        return accommodationRoomMaxOccupancy;
    }

    public void setAccommodationRoomMaxOccupancy(String accommodationRoomMaxOccupancy) {
        this.accommodationRoomMaxOccupancy = accommodationRoomMaxOccupancy;
    }

    public String getAccommodationRoomPrice() {
        return accommodationRoomPrice;
    }

    public void setAccommodationRoomPrice(String accommodationRoomPrice) {
        this.accommodationRoomPrice = accommodationRoomPrice;
    }

    public Integer getAccommodationCategoryId() {
        return accommodationCategoryId;
    }

    public void setAccommodationCategoryId(Integer accommodationCategoryId) {
        this.accommodationCategoryId = accommodationCategoryId;
    }

    public String getAccommodationCategoryName() {
        return accommodationCategoryName;
    }

    public void setAccommodationCategoryName(String accommodationCategoryName) {
        this.accommodationCategoryName = accommodationCategoryName;
    }

}