package com.foodpurby.ondbstorage;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "CART_PRODUCTS".
 */
public class CartProducts {

    private Long id;
    /** Not-null value. */
    private String cartProductKey;
    /** Not-null value. */
    private String productKey;
    /** Not-null value. */
    private String restaurantBranchKey;
    /** Not-null value. */
    private String restaurantBranchGroupKey;
    private String categoryKey;
    /** Not-null value. */
    private String cuisineKey;
    private String productName;
    private String productDesc;
    private String productImgUrl;
    private Double price;
    private Double price_min;
    private Double price_max;
    private Double tax_amount;
    private Boolean tax_included;
    private Double tax_value;
    private Boolean active;
    private Boolean in_stock;
    private Boolean orders_accepted;
    private Integer preparation_time;
    private Integer popularity;
    private Integer sortingNumber;
    private Boolean activeStatus;
    private Integer totalQuantity;

    public CartProducts() {
    }

    public CartProducts(Long id) {
        this.id = id;
    }

    public CartProducts(Long id, String cartProductKey, String productKey, String restaurantBranchKey, String restaurantBranchGroupKey, String categoryKey, String cuisineKey, String productName, String productDesc, String productImgUrl, Double price, Double price_min, Double price_max, Double tax_amount, Boolean tax_included, Double tax_value, Boolean active, Boolean in_stock, Boolean orders_accepted, Integer preparation_time, Integer popularity, Integer sortingNumber, Boolean activeStatus, Integer totalQuantity) {
        this.id = id;
        this.cartProductKey = cartProductKey;
        this.productKey = productKey;
        this.restaurantBranchKey = restaurantBranchKey;
        this.restaurantBranchGroupKey = restaurantBranchGroupKey;
        this.categoryKey = categoryKey;
        this.cuisineKey = cuisineKey;
        this.productName = productName;
        this.productDesc = productDesc;
        this.productImgUrl = productImgUrl;
        this.price = price;
        this.price_min = price_min;
        this.price_max = price_max;
        this.tax_amount = tax_amount;
        this.tax_included = tax_included;
        this.tax_value = tax_value;
        this.active = active;
        this.in_stock = in_stock;
        this.orders_accepted = orders_accepted;
        this.preparation_time = preparation_time;
        this.popularity = popularity;
        this.sortingNumber = sortingNumber;
        this.activeStatus = activeStatus;
        this.totalQuantity = totalQuantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /** Not-null value. */
    public String getCartProductKey() {
        return cartProductKey;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setCartProductKey(String cartProductKey) {
        this.cartProductKey = cartProductKey;
    }

    /** Not-null value. */
    public String getProductKey() {
        return productKey;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setProductKey(String productKey) {
        this.productKey = productKey;
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
    public String getRestaurantBranchGroupKey() {
        return restaurantBranchGroupKey;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setRestaurantBranchGroupKey(String restaurantBranchGroupKey) {
        this.restaurantBranchGroupKey = restaurantBranchGroupKey;
    }

    public String getCategoryKey() {
        return categoryKey;
    }

    public void setCategoryKey(String categoryKey) {
        this.categoryKey = categoryKey;
    }

    /** Not-null value. */
    public String getCuisineKey() {
        return cuisineKey;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setCuisineKey(String cuisineKey) {
        this.cuisineKey = cuisineKey;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getProductImgUrl() {
        return productImgUrl;
    }

    public void setProductImgUrl(String productImgUrl) {
        this.productImgUrl = productImgUrl;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getPrice_min() {
        return price_min;
    }

    public void setPrice_min(Double price_min) {
        this.price_min = price_min;
    }

    public Double getPrice_max() {
        return price_max;
    }

    public void setPrice_max(Double price_max) {
        this.price_max = price_max;
    }

    public Double getTax_amount() {
        return tax_amount;
    }

    public void setTax_amount(Double tax_amount) {
        this.tax_amount = tax_amount;
    }

    public Boolean getTax_included() {
        return tax_included;
    }

    public void setTax_included(Boolean tax_included) {
        this.tax_included = tax_included;
    }

    public Double getTax_value() {
        return tax_value;
    }

    public void setTax_value(Double tax_value) {
        this.tax_value = tax_value;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getIn_stock() {
        return in_stock;
    }

    public void setIn_stock(Boolean in_stock) {
        this.in_stock = in_stock;
    }

    public Boolean getOrders_accepted() {
        return orders_accepted;
    }

    public void setOrders_accepted(Boolean orders_accepted) {
        this.orders_accepted = orders_accepted;
    }

    public Integer getPreparation_time() {
        return preparation_time;
    }

    public void setPreparation_time(Integer preparation_time) {
        this.preparation_time = preparation_time;
    }

    public Integer getPopularity() {
        return popularity;
    }

    public void setPopularity(Integer popularity) {
        this.popularity = popularity;
    }

    public Integer getSortingNumber() {
        return sortingNumber;
    }

    public void setSortingNumber(Integer sortingNumber) {
        this.sortingNumber = sortingNumber;
    }

    public Boolean getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(Boolean activeStatus) {
        this.activeStatus = activeStatus;
    }

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

}
