package model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Food {
    @SerializedName("district_id")
    @Expose
    private Integer districtId;
    @SerializedName("district_name")
    @Expose
    private String districtName;
    @SerializedName("destination_id")
    @Expose
    private Integer destinationId;


    @SerializedName("destination_name")
    @Expose
    private String destinationName;
    @SerializedName("food_category_name")
    @Expose
    private String foodCategoryName;
    @SerializedName("destination_food_service_id")
    @Expose
    private Integer destinationFoodServiceId;
    @SerializedName("destination_food_service_destination_id")
    @Expose
    private Integer destinationFoodServiceDestinationId;
    @SerializedName("destination_food_service_food_category_id")
    @Expose
    private String destinationFoodServiceFoodCategoryId;
    @SerializedName("destination_food_service_provider_name")
    @Expose
    private String destinationFoodServiceProviderName;
    @SerializedName("destination_food_service_provider_email")
    @Expose
    private String destinationFoodServiceProviderEmail;
    @SerializedName("destination_food_service_provider_phone")
    @Expose
    private String destinationFoodServiceProviderPhone;
    @SerializedName("destination_food_service_provider_address")
    @Expose
    private String destinationFoodServiceProviderAddress;
    @SerializedName("destination_food_service_provider_details")
    @Expose
    private String destinationFoodServiceProviderDetails;
    @SerializedName("destination_food_service_provider_image")
    @Expose
    private String destinationFoodServiceProviderImage;
    @SerializedName("destination_food_service_provider_price_range")
    @Expose
    private String destinationFoodServiceProviderPriceRange;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Integer getDistrictId() {
        return districtId;
    }

    public Food setDistrictId(Integer districtId) {
        this.districtId = districtId;
        return this;
    }

    public String getDistrictName() {
        return districtName;
    }

    public Food setDistrictName(String districtName) {
        this.districtName = districtName;
        return this;
    }

    public Integer getDestinationId() {
        return destinationId;
    }

    public Food setDestinationId(Integer destinationId) {
        this.destinationId = destinationId;
        return this;
    }

    public String getFoodCategoryName() {
        return foodCategoryName;
    }

    public Food setFoodCategoryName(String foodCategoryName) {
        this.foodCategoryName = foodCategoryName;
        return this;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public Integer getDestinationFoodServiceId() {
        return destinationFoodServiceId;
    }

    public void setDestinationFoodServiceId(Integer destinationFoodServiceId) {
        this.destinationFoodServiceId = destinationFoodServiceId;
    }

    public Integer getDestinationFoodServiceDestinationId() {
        return destinationFoodServiceDestinationId;
    }

    public void setDestinationFoodServiceDestinationId(Integer destinationFoodServiceDestinationId) {
        this.destinationFoodServiceDestinationId = destinationFoodServiceDestinationId;
    }

    public String getDestinationFoodServiceFoodCategoryId() {
        return destinationFoodServiceFoodCategoryId;
    }

    public void setDestinationFoodServiceFoodCategoryId(String destinationFoodServiceFoodCategoryId) {
        this.destinationFoodServiceFoodCategoryId = destinationFoodServiceFoodCategoryId;
    }

    public String getDestinationFoodServiceProviderName() {
        return destinationFoodServiceProviderName;
    }

    public void setDestinationFoodServiceProviderName(String destinationFoodServiceProviderName) {
        this.destinationFoodServiceProviderName = destinationFoodServiceProviderName;
    }

    public String getDestinationFoodServiceProviderEmail() {
        return destinationFoodServiceProviderEmail;
    }

    public void setDestinationFoodServiceProviderEmail(String destinationFoodServiceProviderEmail) {
        this.destinationFoodServiceProviderEmail = destinationFoodServiceProviderEmail;
    }

    public String getDestinationFoodServiceProviderPhone() {
        return destinationFoodServiceProviderPhone;
    }

    public void setDestinationFoodServiceProviderPhone(String destinationFoodServiceProviderPhone) {
        this.destinationFoodServiceProviderPhone = destinationFoodServiceProviderPhone;
    }

    public String getDestinationFoodServiceProviderAddress() {
        return destinationFoodServiceProviderAddress;
    }

    public void setDestinationFoodServiceProviderAddress(String destinationFoodServiceProviderAddress) {
        this.destinationFoodServiceProviderAddress = destinationFoodServiceProviderAddress;
    }

    public String getDestinationFoodServiceProviderDetails() {
        return destinationFoodServiceProviderDetails;
    }

    public void setDestinationFoodServiceProviderDetails(String destinationFoodServiceProviderDetails) {
        this.destinationFoodServiceProviderDetails = destinationFoodServiceProviderDetails;
    }

    public String getDestinationFoodServiceProviderImage() {
        return destinationFoodServiceProviderImage;
    }

    public void setDestinationFoodServiceProviderImage(String destinationFoodServiceProviderImage) {
        this.destinationFoodServiceProviderImage = destinationFoodServiceProviderImage;
    }

    public String getDestinationFoodServiceProviderPriceRange() {
        return destinationFoodServiceProviderPriceRange;
    }

    public void setDestinationFoodServiceProviderPriceRange(String destinationFoodServiceProviderPriceRange) {
        this.destinationFoodServiceProviderPriceRange = destinationFoodServiceProviderPriceRange;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}