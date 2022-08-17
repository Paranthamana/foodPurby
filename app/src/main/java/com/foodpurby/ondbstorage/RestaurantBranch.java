package com.foodpurby.ondbstorage;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "RESTAURANT_BRANCH".
 */
public class RestaurantBranch {

    private Long id;
    private String restaurantKey;
    private String restaurantBranchKey;
    private String restaurantBranchGroupKey;
    private String restaurantBranchName;
    private String restaurantImageUrl;
    private Double minOrderPrice;
    private double minOrderDeliveryPrice;
    private String restaurantAddress;
    private String restaurantCuisines;
    private String restaurantArea;
    private String restaurantCity;
    private Double restaurantAverageRating;
    private String restaurantStatus;
    private Boolean restaurantFavouriteStatus;
    private Double restaurantDistance;
    private Integer restaurantDeliveryInMin;
    private Integer restaurantPaymentTypes;
    private String restaurantOpeningTime;
    private String restaurantOfferText;
    private String restaurantOfferCode;
    private Double restaurantOfferMinOrderValue;
    private Boolean restaurantPureVegetarianStatus;
    private Double restaurantLatitude;
    private Double restaurantLongitude;
    private Boolean activeStatus;

    public RestaurantBranch() {
    }

    public RestaurantBranch(Long id) {
        this.id = id;
    }

    public RestaurantBranch(Long id, String restaurantKey, String restaurantBranchKey, String restaurantBranchGroupKey, String restaurantBranchName, String restaurantImageUrl, Double minOrderPrice, double minOrderDeliveryPrice, String restaurantAddress, String restaurantCuisines, String restaurantArea, String restaurantCity, Double restaurantAverageRating, String restaurantStatus, Boolean restaurantFavouriteStatus, Double restaurantDistance, Integer restaurantDeliveryInMin, Integer restaurantPaymentTypes, String restaurantOpeningTime, String restaurantOfferText, String restaurantOfferCode, Double restaurantOfferMinOrderValue, Boolean restaurantPureVegetarianStatus, Double restaurantLatitude, Double restaurantLongitude, Boolean activeStatus) {
        this.id = id;
        this.restaurantKey = restaurantKey;
        this.restaurantBranchKey = restaurantBranchKey;
        this.restaurantBranchGroupKey = restaurantBranchGroupKey;
        this.restaurantBranchName = restaurantBranchName;
        this.restaurantImageUrl = restaurantImageUrl;
        this.minOrderPrice = minOrderPrice;
        this.minOrderDeliveryPrice = minOrderDeliveryPrice;
        this.restaurantAddress = restaurantAddress;
        this.restaurantCuisines = restaurantCuisines;
        this.restaurantArea = restaurantArea;
        this.restaurantCity = restaurantCity;
        this.restaurantAverageRating = restaurantAverageRating;
        this.restaurantStatus = restaurantStatus;
        this.restaurantFavouriteStatus = restaurantFavouriteStatus;
        this.restaurantDistance = restaurantDistance;
        this.restaurantDeliveryInMin = restaurantDeliveryInMin;
        this.restaurantPaymentTypes = restaurantPaymentTypes;
        this.restaurantOpeningTime = restaurantOpeningTime;
        this.restaurantOfferText = restaurantOfferText;
        this.restaurantOfferCode = restaurantOfferCode;
        this.restaurantOfferMinOrderValue = restaurantOfferMinOrderValue;
        this.restaurantPureVegetarianStatus = restaurantPureVegetarianStatus;
        this.restaurantLatitude = restaurantLatitude;
        this.restaurantLongitude = restaurantLongitude;
        this.activeStatus = activeStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRestaurantKey() {
        return restaurantKey;
    }

    public void setRestaurantKey(String restaurantKey) {
        this.restaurantKey = restaurantKey;
    }

    public String getRestaurantBranchKey() {
        return restaurantBranchKey;
    }

    public void setRestaurantBranchKey(String restaurantBranchKey) {
        this.restaurantBranchKey = restaurantBranchKey;
    }

    public String getRestaurantBranchGroupKey() {
        return restaurantBranchGroupKey;
    }

    public void setRestaurantBranchGroupKey(String restaurantBranchGroupKey) {
        this.restaurantBranchGroupKey = restaurantBranchGroupKey;
    }

    public String getRestaurantBranchName() {
        return restaurantBranchName;
    }

    public void setRestaurantBranchName(String restaurantBranchName) {
        this.restaurantBranchName = restaurantBranchName;
    }

    public String getRestaurantImageUrl() {
        return restaurantImageUrl;
    }

    public void setRestaurantImageUrl(String restaurantImageUrl) {
        this.restaurantImageUrl = restaurantImageUrl;
    }

    public Double getMinOrderPrice() {
        return minOrderPrice;
    }

    public void setMinOrderPrice(Double minOrderPrice) {
        this.minOrderPrice = minOrderPrice;
    }

    public double getMinOrderDeliveryPrice() {
        return minOrderDeliveryPrice;
    }

    public void setMinOrderDeliveryPrice(double minOrderDeliveryPrice) {
        this.minOrderDeliveryPrice = minOrderDeliveryPrice;
    }

    public String getRestaurantAddress() {
        return restaurantAddress;
    }

    public void setRestaurantAddress(String restaurantAddress) {
        this.restaurantAddress = restaurantAddress;
    }

    public String getRestaurantCuisines() {
        return restaurantCuisines;
    }

    public void setRestaurantCuisines(String restaurantCuisines) {
        this.restaurantCuisines = restaurantCuisines;
    }

    public String getRestaurantArea() {
        return restaurantArea;
    }

    public void setRestaurantArea(String restaurantArea) {
        this.restaurantArea = restaurantArea;
    }

    public String getRestaurantCity() {
        return restaurantCity;
    }

    public void setRestaurantCity(String restaurantCity) {
        this.restaurantCity = restaurantCity;
    }

    public Double getRestaurantAverageRating() {
        return restaurantAverageRating;
    }

    public void setRestaurantAverageRating(Double restaurantAverageRating) {
        this.restaurantAverageRating = restaurantAverageRating;
    }

    public String getRestaurantStatus() {
        return restaurantStatus;
    }

    public void setRestaurantStatus(String restaurantStatus) {
        this.restaurantStatus = restaurantStatus;
    }

    public Boolean getRestaurantFavouriteStatus() {
        return restaurantFavouriteStatus;
    }

    public void setRestaurantFavouriteStatus(Boolean restaurantFavouriteStatus) {
        this.restaurantFavouriteStatus = restaurantFavouriteStatus;
    }

    public Double getRestaurantDistance() {
        return restaurantDistance;
    }

    public void setRestaurantDistance(Double restaurantDistance) {
        this.restaurantDistance = restaurantDistance;
    }

    public Integer getRestaurantDeliveryInMin() {
        return restaurantDeliveryInMin;
    }

    public void setRestaurantDeliveryInMin(Integer restaurantDeliveryInMin) {
        this.restaurantDeliveryInMin = restaurantDeliveryInMin;
    }

    public Integer getRestaurantPaymentTypes() {
        return restaurantPaymentTypes;
    }

    public void setRestaurantPaymentTypes(Integer restaurantPaymentTypes) {
        this.restaurantPaymentTypes = restaurantPaymentTypes;
    }

    public String getRestaurantOpeningTime() {
        return restaurantOpeningTime;
    }

    public void setRestaurantOpeningTime(String restaurantOpeningTime) {
        this.restaurantOpeningTime = restaurantOpeningTime;
    }

    public String getRestaurantOfferText() {
        return restaurantOfferText;
    }

    public void setRestaurantOfferText(String restaurantOfferText) {
        this.restaurantOfferText = restaurantOfferText;
    }

    public String getRestaurantOfferCode() {
        return restaurantOfferCode;
    }

    public void setRestaurantOfferCode(String restaurantOfferCode) {
        this.restaurantOfferCode = restaurantOfferCode;
    }

    public Double getRestaurantOfferMinOrderValue() {
        return restaurantOfferMinOrderValue;
    }

    public void setRestaurantOfferMinOrderValue(Double restaurantOfferMinOrderValue) {
        this.restaurantOfferMinOrderValue = restaurantOfferMinOrderValue;
    }

    public Boolean getRestaurantPureVegetarianStatus() {
        return restaurantPureVegetarianStatus;
    }

    public void setRestaurantPureVegetarianStatus(Boolean restaurantPureVegetarianStatus) {
        this.restaurantPureVegetarianStatus = restaurantPureVegetarianStatus;
    }

    public Double getRestaurantLatitude() {
        return restaurantLatitude;
    }

    public void setRestaurantLatitude(Double restaurantLatitude) {
        this.restaurantLatitude = restaurantLatitude;
    }

    public Double getRestaurantLongitude() {
        return restaurantLongitude;
    }

    public void setRestaurantLongitude(Double restaurantLongitude) {
        this.restaurantLongitude = restaurantLongitude;
    }

    public Boolean getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(Boolean activeStatus) {
        this.activeStatus = activeStatus;
    }

}
