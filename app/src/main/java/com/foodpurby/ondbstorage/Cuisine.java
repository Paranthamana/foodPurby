package com.foodpurby.ondbstorage;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "CUISINE".
 */
public class Cuisine {

    private Long id;
    /** Not-null value. */
    private String restaurantKey;
    /** Not-null value. */
    private String restaurantBranchKey;
    /** Not-null value. */
    private String groupKey;
    /** Not-null value. */
    private String cuisineKey;
    private String cuisineName;
    private String sortingNumber;
    private Boolean activeStatus;

    public Cuisine() {
    }

    public Cuisine(Long id) {
        this.id = id;
    }

    public Cuisine(Long id, String restaurantKey, String restaurantBranchKey, String groupKey, String cuisineKey, String cuisineName, String sortingNumber, Boolean activeStatus) {
        this.id = id;
        this.restaurantKey = restaurantKey;
        this.restaurantBranchKey = restaurantBranchKey;
        this.groupKey = groupKey;
        this.cuisineKey = cuisineKey;
        this.cuisineName = cuisineName;
        this.sortingNumber = sortingNumber;
        this.activeStatus = activeStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /** Not-null value. */
    public String getRestaurantKey() {
        return restaurantKey;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setRestaurantKey(String restaurantKey) {
        this.restaurantKey = restaurantKey;
    }

    /** Not-null value. */
    public String getRestaurantBranchKey() {
        return restaurantBranchKey;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setRestaurantBranchKey(String restaurantBranchKey) {
        this.restaurantBranchKey = restaurantBranchKey;
    }

    /** Not-null value. */
    public String getGroupKey() {
        return groupKey;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    /** Not-null value. */
    public String getCuisineKey() {
        return cuisineKey;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setCuisineKey(String cuisineKey) {
        this.cuisineKey = cuisineKey;
    }

    public String getCuisineName() {
        return cuisineName;
    }

    public void setCuisineName(String cuisineName) {
        this.cuisineName = cuisineName;
    }

    public String getSortingNumber() {
        return sortingNumber;
    }

    public void setSortingNumber(String sortingNumber) {
        this.sortingNumber = sortingNumber;
    }

    public Boolean getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(Boolean activeStatus) {
        this.activeStatus = activeStatus;
    }

}
