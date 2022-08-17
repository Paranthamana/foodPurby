package com.foodpurby.model;

import com.foodpurby.util.URLClass;
import com.foodpurby.utillities.AppSharedValues;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;

/**
 * Created by android1 on 12/31/2015.
 */
public class DAOShops {

    private static DAOShops ourInstance = new DAOShops();

    public static DAOShops getInstance() {
        return ourInstance;
    }

    private DAOShops() {
    }

    public void Callresponse(String token, String area, String city, String category, String customerKey, Callback<Restaurant> mCallback) {
        RestaurantlabelLists mGetapi = URLClass.getInstance().getApiBuilder().create(RestaurantlabelLists.class);
        mGetapi.RestaurantNameListView(token, area, city, category, AppSharedValues.getLanguage(), customerKey, "ewrewe342eWREW", mCallback);
    }

    public interface RestaurantlabelLists {
        @GET("/shop_list.html")
        public void RestaurantNameListView(@Header("request") String token,
                                           @Query("area") String area,
                                           @Query("city") String city,
                                           @Query("type") String type,
                                           @Query("languge") String languge,
                                           @Query("customer") String customer,
                                           @Query("key") String key,
                                           Callback<Restaurant> response);
    }

    public class Data {

        private List<Shop_list> shop_list = new ArrayList<Shop_list>();
        private List<String> cuisine_list = new ArrayList<String>();

        public List<String> getBanner_list_image() {
            return banner_list_image;
        }

        public void setBanner_list_image(List<String> banner_list_image) {
            this.banner_list_image = banner_list_image;
        }

        private List<String> banner_list_image = new ArrayList<>();
        private Integer category_id;
        private Integer totalitems;

        /**
         * @return The shop_list
         */
        public List<Shop_list> getShop_list() {
            return shop_list;
        }

        /**
         * @param shop_list The shop_list
         */
        public void setShop_list(List<Shop_list> shop_list) {
            this.shop_list = shop_list;
        }

        /**
         * @return The cuisine_list
         */
        public List<String> getCuisine_list() {
            return cuisine_list;
        }

        /**
         * @param cuisine_list The cuisine_list
         */
        public void setCuisine_list(List<String> cuisine_list) {
            this.cuisine_list = cuisine_list;
        }


        /**
         * @return The category_id
         */
        public Integer getCategory_id() {
            return category_id;
        }

        /**
         * @param category_id The category_id
         */
        public void setCategory_id(Integer category_id) {
            this.category_id = category_id;
        }

        /**
         * @return The totalitems
         */
        public Integer getTotalitems() {
            return totalitems;
        }

        /**
         * @param totalitems The totalitems
         */
        public void setTotalitems(Integer totalitems) {
            this.totalitems = totalitems;
        }

    }

    public class Restaurant {

        private Integer httpcode;
        private String status;
        private String message;
        private Data data;
        private String responsetime;

        /**
         * @return The httpcode
         */
        public Integer getHttpcode() {
            return httpcode;
        }

        /**
         * @param httpcode The httpcode
         */
        public void setHttpcode(Integer httpcode) {
            this.httpcode = httpcode;
        }

        /**
         * @return The status
         */
        public String getStatus() {
            return status;
        }

        /**
         * @param status The status
         */
        public void setStatus(String status) {
            this.status = status;
        }

        /**
         * @return The message
         */
        public String getMessage() {
            return message;
        }

        /**
         * @param message The message
         */
        public void setMessage(String message) {
            this.message = message;
        }

        /**
         * @return The data
         */
        public Data getData() {
            return data;
        }

        /**
         * @param data The data
         */
        public void setData(Data data) {
            this.data = data;
        }

        /**
         * @return The responsetime
         */
        public String getResponsetime() {
            return responsetime;
        }

        /**
         * @param responsetime The responsetime
         */
        public void setResponsetime(String responsetime) {
            this.responsetime = responsetime;
        }

    }

    public class Shop_list {

        private String shop_name;
        private String shop_address;
        private String shop_key;
        private String shop_logo;
        private Integer pickup_time;
        private Integer delivery_in;
        private Integer min_order_value;
        private Integer preorder_available;
        private Integer online_payment_available;
        private Integer restaurant_type;
        private Integer total_rating;
        private Double avg_rating;
        private Integer popularity;
        private String shop_status;
        private String cuisines;
        private Integer is_favourite;
        private Integer favourite_count;
        private Integer distance_in_km;
        private Integer delivery_fee;
        private Integer pure_vegetarian;
        private String shop_opening_time;
        private String voucher;
        private String offer_text;
        private String offer_code;
        private Double offer_min_order_value;
        private String currency_symbol;


        /**
         * @return The shop_name
         */
        public String getShop_name() {
            return shop_name;
        }

        /**
         * @param shop_name The shop_name
         */
        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        /**
         * @return The shop_address
         */
        public String getShop_address() {
            return shop_address;
        }

        /**
         * @param shop_address The shop_address
         */
        public void setShop_address(String shop_address) {
            this.shop_address = shop_address;
        }

        /**
         * @return The shop_key
         */
        public String getShop_key() {
            return shop_key;
        }

        /**
         * @param shop_key The shop_key
         */
        public void setShop_key(String shop_key) {
            this.shop_key = shop_key;
        }

        /**
         * @return The shop_logo
         */
        public String getShop_logo() {
            return shop_logo;
        }

        /**
         * @param shop_logo The shop_logo
         */
        public void setShop_logo(String shop_logo) {
            this.shop_logo = shop_logo;
        }

        /**
         * @return The pickup_time
         */
        public Integer getPickup_time() {
            return pickup_time;
        }

        /**
         * @param pickup_time The pickup_time
         */
        public void setPickup_time(Integer pickup_time) {
            this.pickup_time = pickup_time;
        }

        /**
         * @return The delivery_in
         */
        public Integer getDelivery_in() {
            return delivery_in;
        }

        /**
         * @param delivery_in The delivery_in
         */
        public void setDelivery_in(Integer delivery_in) {
            this.delivery_in = delivery_in;
        }

        /**
         * @return The min_order_value
         */
        public Integer getMin_order_value() {
            return min_order_value;
        }

        /**
         * @param min_order_value The min_order_value
         */
        public void setMin_order_value(Integer min_order_value) {
            this.min_order_value = min_order_value;
        }

        /**
         * @return The preorder_available
         */
        public Integer getPreorder_available() {
            return preorder_available;
        }

        /**
         * @param preorder_available The preorder_available
         */
        public void setPreorder_available(Integer preorder_available) {
            this.preorder_available = preorder_available;
        }

        /**
         * @return The online_payment_available
         */
        public Integer getOnline_payment_available() {
            return online_payment_available;
        }

        /**
         * @param online_payment_available The online_payment_available
         */
        public void setOnline_payment_available(Integer online_payment_available) {
            this.online_payment_available = online_payment_available;
        }

        /**
         * @return The restaurant_type
         */
        public Integer getRestaurant_type() {
            return restaurant_type;
        }

        /**
         * @param restaurant_type The restaurant_type
         */
        public void setRestaurant_type(Integer restaurant_type) {
            this.restaurant_type = restaurant_type;
        }

        /**
         * @return The total_rating
         */
        public Integer getTotal_rating() {
            return total_rating;
        }

        /**
         * @param total_rating The total_rating
         */
        public void setTotal_rating(Integer total_rating) {
            this.total_rating = total_rating;
        }

        /**
         * @return The avg_rating
         */
        public Double getAvg_rating() {
            return avg_rating;
        }

        /**
         * @param avg_rating The avg_rating
         */
        public void setAvg_rating(Double avg_rating) {
            this.avg_rating = avg_rating;
        }

        /**
         * @return The popularity
         */
        public Integer getPopularity() {
            return popularity;
        }

        /**
         * @param popularity The popularity
         */
        public void setPopularity(Integer popularity) {
            this.popularity = popularity;
        }

        /**
         * @return The shop_status
         */
        public String getShop_status() {
            return shop_status;
        }

        /**
         * @param shop_status The shop_status
         */
        public void setShop_status(String shop_status) {
            this.shop_status = shop_status;
        }

        /**
         * @return The cuisines
         */
        public String getCuisines() {
            return cuisines;
        }

        /**
         * @param cuisines The cuisines
         */
        public void setCuisines(String cuisines) {
            this.cuisines = cuisines;
        }

        /**
         * @return The is_favourite
         */
        public Integer getIs_favourite() {
            return is_favourite;
        }

        /**
         * @param is_favourite The is_favourite
         */
        public void setIs_favourite(Integer is_favourite) {
            this.is_favourite = is_favourite;
        }

        /**
         * @return The favourite_count
         */
        public Integer getFavourite_count() {
            return favourite_count;
        }

        /**
         * @param favourite_count The favourite_count
         */
        public void setFavourite_count(Integer favourite_count) {
            this.favourite_count = favourite_count;
        }

        /**
         * @return The distance_in_km
         */
        public Integer getDistance_in_km() {
            return distance_in_km;
        }

        /**
         * @param distance_in_km The distance_in_km
         */
        public void setDistance_in_km(Integer distance_in_km) {
            this.distance_in_km = distance_in_km;
        }

        /**
         * @return The delivery_fee
         */
        public Integer getDelivery_fee() {
            return delivery_fee;
        }

        /**
         * @param delivery_fee The delivery_fee
         */
        public void setDelivery_fee(Integer delivery_fee) {
            this.delivery_fee = delivery_fee;
        }

        /**
         * @return The delivery_fee
         */
        public Integer getPure_vegetarian() {
            return pure_vegetarian;
        }

        /**
         * @param pure_vegetarian The pure_vegetarian
         */
        public void setPure_vegetarian(Integer pure_vegetarian) {
            this.pure_vegetarian = pure_vegetarian;
        }

        /**
         * @return The shop_opening_time
         */
        public String getShop_opening_time() {
            return shop_opening_time;
        }

        /**
         * @param shop_opening_time The shop_opening_time
         */
        public void setShop_opening_time(String shop_opening_time) {
            this.shop_opening_time = shop_opening_time;
        }

        /**
         * @return The voucher
         */
        public String getVoucher() {
            return voucher;
        }

        /**
         * @param voucher The voucher
         */
        public void setVoucher(String voucher) {
            this.voucher = voucher;
        }


        /**
         * @return The shop_opening_time
         */
        public String getOffer_text() {
            return offer_text;
        }

        /**
         * @param offer_text The offer_text
         */
        public void setOffer_text(String offer_text) {
            this.offer_text = offer_text;
        }

        /**
         * @return The shop_opening_time
         */
        public String getOffer_code() {
            return offer_code;
        }

        /**
         * @param offer_code The offer_code
         */
        public void setOffer_code(String offer_code) {
            this.offer_code = offer_code;
        }

        public Double getOffer_min_order_value() {
            return offer_min_order_value;
        }

        public void setOffer_min_order_value(Double offer_min_order_value) {
            this.offer_min_order_value = offer_min_order_value;
        }

        public String getCurrency_symbol() {
            return currency_symbol;
        }

        public void setCurrency_symbol(String currency_symbol) {
            this.currency_symbol = currency_symbol;
        }
    }
}
