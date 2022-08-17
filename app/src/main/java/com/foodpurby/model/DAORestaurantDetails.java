
package com.foodpurby.model;


import com.foodpurby.util.URLClass;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;

/**
 * Created by tech on 11/2/2015.
 */
public class DAORestaurantDetails {

    private static DAORestaurantDetails ourInstance = new DAORestaurantDetails();

    public static DAORestaurantDetails getInstance() {
        return ourInstance;
    }

    public DAORestaurantDetails() {
    }

    public void Callresponse(String token, String restaurantKey, String language, String key, Callback<RestaurantDetails> mCallback) {
        RestaurantlabelLists mGetapi = URLClass.getInstance().getApiBuilder().create(RestaurantlabelLists.class);
        mGetapi.RestaurantNameListView(token, restaurantKey, language, key, mCallback);
    }

    public interface RestaurantlabelLists {
        @GET("/shop_info.html")
        public void RestaurantNameListView(@Header("request") String token,
                                           @Query("shop") String restaurantKey,
                                           @Query("language") String language,
                                           @Query("key") String key,
                                           Callback<RestaurantDetails> response);
    }

    /*public class Data {

        private List<Shop_info> shop_info = new ArrayList<Shop_info>();
        private Integer totalitems;

        *//**
         *
         * @return
         * The shop_info
         *//*
        public List<Shop_info> getShop_info() {
            return shop_info;
        }

        *//**
         *
         * @param shop_info
         * The shop_info
         *//*
        public void setShop_info(List<Shop_info> shop_info) {
            this.shop_info = shop_info;
        }

        *//**
         *
         * @return
         * The totalitems
         *//*
        public Integer getTotalitems() {
            return totalitems;
        }

        *//**
         *
         * @param totalitems
         * The totalitems
         *//*
        public void setTotalitems(Integer totalitems) {
            this.totalitems = totalitems;
        }

    }


    public class Delivery_time_list {

        private String timeslot_day;
        private String timeslot_start_time;
        private String timeslot_end_time;

        *//**
         *
         * @return
         * The timeslot_day
         *//*
        public String getTimeslot_day() {
            return timeslot_day;
        }

        *//**
         *
         * @param timeslot_day
         * The timeslot_day
         *//*
        public void setTimeslot_day(String timeslot_day) {
            this.timeslot_day = timeslot_day;
        }

        *//**
         *
         * @return
         * The timeslot_start_time
         *//*
        public String getTimeslot_start_time() {
            return timeslot_start_time;
        }

        *//**
         *
         * @param timeslot_start_time
         * The timeslot_start_time
         *//*
        public void setTimeslot_start_time(String timeslot_start_time) {
            this.timeslot_start_time = timeslot_start_time;
        }

        *//**
         *
         * @return
         * The timeslot_end_time
         *//*
        public String getTimeslot_end_time() {
            return timeslot_end_time;
        }

        *//**
         *
         * @param timeslot_end_time
         * The timeslot_end_time
         *//*
        public void setTimeslot_end_time(String timeslot_end_time) {
            this.timeslot_end_time = timeslot_end_time;
        }

    }


    public class RestaurantDetails {

        private Integer httpcode;
        private String status;
        private String message;
        private Data data;
        private String responsetime;

        *//**
         *
         * @return
         * The httpcode
         *//*
        public Integer getHttpcode() {
            return httpcode;
        }

        *//**
         *
         * @param httpcode
         * The httpcode
         *//*
        public void setHttpcode(Integer httpcode) {
            this.httpcode = httpcode;
        }

        *//**
         *
         * @return
         * The status
         *//*
        public String getStatus() {
            return status;
        }

        *//**
         *
         * @param status
         * The status
         *//*
        public void setStatus(String status) {
            this.status = status;
        }

        *//**
         *
         * @return
         * The message
         *//*
        public String getMessage() {
            return message;
        }

        *//**
         *
         * @param message
         * The message
         *//*
        public void setMessage(String message) {
            this.message = message;
        }

        *//**
         *
         * @return
         * The data
         *//*
        public Data getData() {
            return data;
        }

        *//**
         *
         * @param data
         * The data
         *//*
        public void setData(Data data) {
            this.data = data;
        }

        *//**
         *
         * @return
         * The responsetime
         *//*
        public String getResponsetime() {
            return responsetime;
        }

        *//**
         *
         * @param responsetime
         * The responsetime
         *//*
        public void setResponsetime(String responsetime) {
            this.responsetime = responsetime;
        }

    }


    public class Review {

        private Integer rating;
        private String review_title;
        private String review_text;
        private String created_date;
        private String firstname;
        private String lastname;

        *//**
         *
         * @return
         * The rating
         *//*
        public Integer getRating() {
            return rating;
        }

        *//**
         *
         * @param rating
         * The rating
         *//*
        public void setRating(Integer rating) {
            this.rating = rating;
        }

        *//**
         *
         * @return
         * The review_title
         *//*
        public String getReview_title() {
            return review_title;
        }

        *//**
         *
         * @param review_title
         * The review_title
         *//*
        public void setReview_title(String review_title) {
            this.review_title = review_title;
        }

        *//**
         *
         * @return
         * The review_text
         *//*
        public String getReview_text() {
            return review_text;
        }

        *//**
         *
         * @param review_text
         * The review_text
         *//*
        public void setReview_text(String review_text) {
            this.review_text = review_text;
        }

        *//**
         *
         * @return
         * The created_date
         *//*
        public String getCreated_date() {
            return created_date;
        }

        *//**
         *
         * @param created_date
         * The created_date
         *//*
        public void setCreated_date(String created_date) {
            this.created_date = created_date;
        }

        *//**
         *
         * @return
         * The firstname
         *//*
        public String getFirstname() {
            return firstname;
        }

        *//**
         *
         * @param firstname
         * The firstname
         *//*
        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        *//**
         *
         * @return
         * The lastname
         *//*
        public String getLastname() {
            return lastname;
        }

        *//**
         *
         * @param lastname
         * The lastname
         *//*
        public void setLastname(String lastname) {
            this.lastname = lastname;
        }

    }


    public class Shop_info {

        private String shop_name;
        private String shop_address;
        @SerializedName("shop_phone_number")
        private String shop_phone_no;
        private String shop_key;
        private String shop_logo;
        private Integer pickup_time;
        private Integer delivery_in;
        private Integer min_order_value;
        private Integer preorder_available;
        private Integer online_payment_available;
        private Integer pure_vegetarian;
        private Double avg_rating;
        private Integer total_rating;
        private String cuisines;
        private List<Review> reviews = new ArrayList<Review>();
        private List<Delivery_time_list> delivery_time_list = new ArrayList<Delivery_time_list>();

        *//**
         *
         * @return
         * The shop_name
         *//*
        public String getShop_name() {
            return shop_name;
        }

        *//**
         *
         * @param shop_name
         * The shop_name
         *//*
        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        *//**
         *
         * @return
         * The shop_address
         *//*
        public String getShop_address() {
            return shop_address;
        }

        *//**
         *
         * @param shop_address
         * The shop_address
         *//*
        public void setShop_address(String shop_address) {
            this.shop_address = shop_address;
        }

        public String getShop_phone_no() {
            return shop_phone_no;
        }

        public void setShop_phone_no(String shop_phone_no) {
            this.shop_phone_no = shop_phone_no;
        }

        *//**
         *
         * @return
         * The shop_key
         *//*
        public String getShop_key() {
            return shop_key;
        }

        *//**
         *
         * @param shop_key
         * The shop_key
         *//*
        public void setShop_key(String shop_key) {
            this.shop_key = shop_key;
        }

        *//**
         *
         * @return
         * The shop_logo
         *//*
        public String getShop_logo() {
            return shop_logo;
        }

        *//**
         *
         * @param shop_logo
         * The shop_logo
         *//*
        public void setShop_logo(String shop_logo) {
            this.shop_logo = shop_logo;
        }

        *//**
         *
         * @return
         * The pickup_time
         *//*
        public Integer getPickup_time() {
            return pickup_time;
        }

        *//**
         *
         * @param pickup_time
         * The pickup_time
         *//*
        public void setPickup_time(Integer pickup_time) {
            this.pickup_time = pickup_time;
        }

        *//**
         *
         * @return
         * The delivery_in
         *//*
        public Integer getDelivery_in() {
            return delivery_in;
        }

        *//**
         *
         * @param delivery_in
         * The delivery_in
         *//*
        public void setDelivery_in(Integer delivery_in) {
            this.delivery_in = delivery_in;
        }

        *//**
         *
         * @return
         * The min_order_value
         *//*
        public Integer getMin_order_value() {
            return min_order_value;
        }

        *//**
         *
         * @param min_order_value
         * The min_order_value
         *//*
        public void setMin_order_value(Integer min_order_value) {
            this.min_order_value = min_order_value;
        }

        *//**
         *
         * @return
         * The preorder_available
         *//*
        public Integer getPreorder_available() {
            return preorder_available;
        }

        *//**
         *
         * @param preorder_available
         * The preorder_available
         *//*
        public void setPreorder_available(Integer preorder_available) {
            this.preorder_available = preorder_available;
        }

        *//**
         *
         * @return
         * The online_payment_available
         *//*
        public Integer getOnline_payment_available() {
            return online_payment_available;
        }

        *//**
         *
         * @param online_payment_available
         * The online_payment_available
         *//*
        public void setOnline_payment_available(Integer online_payment_available) {
            this.online_payment_available = online_payment_available;
        }

        *//**
         *
         * @return
         * The pure_vegetarian
         *//*
        public Integer getPure_vegetarian() {
            return pure_vegetarian;
        }

        *//**
         *
         * @param pure_vegetarian
         * The pure_vegetarian
         *//*
        public void setPure_vegetarian(Integer pure_vegetarian) {
            this.pure_vegetarian = pure_vegetarian;
        }

        *//**
         *
         * @return
         * The avg_rating
         *//*
        public Double getAvg_rating() {
            return avg_rating;
        }

        *//**
         *
         * @param avg_rating
         * The avg_rating
         *//*
        public void setAvg_rating(Double avg_rating) {
            this.avg_rating = avg_rating;
        }

        *//**
         *
         * @return
         * The total_rating
         *//*
        public Integer getTotal_rating() {
            return total_rating;
        }

        *//**
         *
         * @param total_rating
         * The total_rating
         *//*
        public void setTotal_rating(Integer total_rating) {
            this.total_rating = total_rating;
        }

        *//**
         *
         * @return
         * The cuisines
         *//*
        public String getCuisines() {
            return cuisines;
        }

        *//**
         *
         * @param cuisines
         * The cuisines
         *//*
        public void setCuisines(String cuisines) {
            this.cuisines = cuisines;
        }

        *//**
         *
         * @return
         * The reviews
         *//*
        public List<Review> getReviews() {
            return reviews;
        }

        *//**
         *
         * @param reviews
         * The reviews
         *//*
        public void setReviews(List<Review> reviews) {
            this.reviews = reviews;
        }

        *//**
         *
         * @return
         * The delivery_time_list
         *//*
        public List<Delivery_time_list> getDelivery_time_list() {
            return delivery_time_list;
        }

        *//**
         *
//         * @param delivery_time_list
         * The delivery_time_list
         *//*
        public void setDelivery_time_list(List<Delivery_time_list> delivery_time_list) {
            this.delivery_time_list = delivery_time_list;
        }

    }*/


    public class Data {

        private List<Shop_info> shop_info = null;
        private Integer totalitems;
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        public List<Shop_info> getShop_info() {
            return shop_info;
        }

        public void setShop_info(List<Shop_info> shop_info) {
            this.shop_info = shop_info;
        }

        public Integer getTotalitems() {
            return totalitems;
        }

        public void setTotalitems(Integer totalitems) {
            this.totalitems = totalitems;
        }

        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

    }


    public class Delivery_time_list {

        private String timeslot_day;
        private String timeslot_start_time;
        private String timeslot_end_time;
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        public String getTimeslot_day() {
            return timeslot_day;
        }

        public void setTimeslot_day(String timeslot_day) {
            this.timeslot_day = timeslot_day;
        }

        public String getTimeslot_start_time() {
            return timeslot_start_time;
        }

        public void setTimeslot_start_time(String timeslot_start_time) {
            this.timeslot_start_time = timeslot_start_time;
        }

        public String getTimeslot_end_time() {
            return timeslot_end_time;
        }

        public void setTimeslot_end_time(String timeslot_end_time) {
            this.timeslot_end_time = timeslot_end_time;
        }

        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

    }

    public class RestaurantDetails {

        private Integer httpcode;
        private String status;
        private String message;
        private Data data;
        private String responsetime;
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        public Integer getHttpcode() {
            return httpcode;
        }

        public void setHttpcode(Integer httpcode) {
            this.httpcode = httpcode;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }

        public String getResponsetime() {
            return responsetime;
        }

        public void setResponsetime(String responsetime) {
            this.responsetime = responsetime;
        }

        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

    }

    public class Review {

        private Integer rating;
        private String review_title;
        private String review_text;
        private String created_date;
        private String firstname;
        private String lastname;
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        public Integer getRating() {
            return rating;
        }

        public void setRating(Integer rating) {
            this.rating = rating;
        }

        public String getReview_title() {
            return review_title;
        }

        public void setReview_title(String review_title) {
            this.review_title = review_title;
        }

        public String getReview_text() {
            return review_text;
        }

        public void setReview_text(String review_text) {
            this.review_text = review_text;
        }

        public String getCreated_date() {
            return created_date;
        }

        public void setCreated_date(String created_date) {
            this.created_date = created_date;
        }

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getLastname() {
            return lastname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }

        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

    }

    public class Shop_info {

        private String shop_name;
        private String shop_phone_number;
        private String shop_address;
        private String shop_key;
        private String shop_logo;
        private Integer pickup_time;
        private Integer delivery_in;
        private Integer min_order_value;
        private Integer preorder_available;
        private Integer online_payment_available;
        private Integer pure_vegetarian;
        private Double avg_rating;
        private Integer total_rating;
        private String cuisines;
        private List<Review> reviews = null;
        private List<Delivery_time_list> delivery_time_list = null;
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        public String getShop_name() {
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        public String getShop_phone_number() {
            return shop_phone_number;
        }

        public void setShop_phone_number(String shop_phone_number) {
            this.shop_phone_number = shop_phone_number;
        }

        public String getShop_address() {
            return shop_address;
        }

        public void setShop_address(String shop_address) {
            this.shop_address = shop_address;
        }

        public String getShop_key() {
            return shop_key;
        }

        public void setShop_key(String shop_key) {
            this.shop_key = shop_key;
        }

        public String getShop_logo() {
            return shop_logo;
        }

        public void setShop_logo(String shop_logo) {
            this.shop_logo = shop_logo;
        }

        public Integer getPickup_time() {
            return pickup_time;
        }

        public void setPickup_time(Integer pickup_time) {
            this.pickup_time = pickup_time;
        }

        public Integer getDelivery_in() {
            return delivery_in;
        }

        public void setDelivery_in(Integer delivery_in) {
            this.delivery_in = delivery_in;
        }

        public Integer getMin_order_value() {
            return min_order_value;
        }

        public void setMin_order_value(Integer min_order_value) {
            this.min_order_value = min_order_value;
        }

        public Integer getPreorder_available() {
            return preorder_available;
        }

        public void setPreorder_available(Integer preorder_available) {
            this.preorder_available = preorder_available;
        }

        public Integer getOnline_payment_available() {
            return online_payment_available;
        }

        public void setOnline_payment_available(Integer online_payment_available) {
            this.online_payment_available = online_payment_available;
        }

        public Integer getPure_vegetarian() {
            return pure_vegetarian;
        }

        public void setPure_vegetarian(Integer pure_vegetarian) {
            this.pure_vegetarian = pure_vegetarian;
        }

        public Double getAvg_rating() {
            return avg_rating;
        }

        public void setAvg_rating(Double avg_rating) {
            this.avg_rating = avg_rating;
        }

        public Integer getTotal_rating() {
            return total_rating;
        }

        public void setTotal_rating(Integer total_rating) {
            this.total_rating = total_rating;
        }

        public String getCuisines() {
            return cuisines;
        }

        public void setCuisines(String cuisines) {
            this.cuisines = cuisines;
        }

        public List<Review> getReviews() {
            return reviews;
        }

        public void setReviews(List<Review> reviews) {
            this.reviews = reviews;
        }

        public List<Delivery_time_list> getDelivery_time_list() {
            return delivery_time_list;
        }

        public void setDelivery_time_list(List<Delivery_time_list> delivery_time_list) {
            this.delivery_time_list = delivery_time_list;
        }

        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

    }
}