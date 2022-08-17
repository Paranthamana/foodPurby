package com.foodpurby.model;


import com.foodpurby.util.URLClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;

/**
 * Created by tech on 11/2/2015.
 */
public class DAORestaurantNames {

    private static DAORestaurantNames ourInstance = new DAORestaurantNames();

    public static DAORestaurantNames getInstance() {
        return ourInstance;
    }

    private DAORestaurantNames() {
    }

    public void Callresponse(String token, Callback<RestaurantApi> mCallback) {
        RestaurantlabelLists mGetapi = URLClass.getInstance().getApiBuilder().create(RestaurantlabelLists.class);
        mGetapi.RestaurantNameListView(token, mCallback);
    }

    public interface RestaurantlabelLists {
        @GET("/restuarant")
        public void RestaurantNameListView(@Header("request") String token, Callback<RestaurantApi> response);
    }


    public class RestaurantApi {
        private List<Restaurant> restaurants = new ArrayList<Restaurant>();
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        /**
         * @return The restaurants
         */
        public List<Restaurant> getRestaurants() {
            return restaurants;
        }

        /**
         * @param restaurants The restaurants
         */
        public void setRestaurants(List<Restaurant> restaurants) {
            this.restaurants = restaurants;
        }

        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

    }

    public class Restaurant {

        private Integer areaid;
        private Integer activecount;
        private String cusinekey;
        private List<Tokens> token = new ArrayList<Tokens>();
        private List<Restaurants> restaurant = new ArrayList<Restaurants>();
        private String status_code;
        private String status_messages;
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        /**
         * @return The areaid
         */
        public Integer getAreaid() {
            return areaid;
        }

        /**
         * @param areaid The areaid
         */
        public void setAreaid(Integer areaid) {
            this.areaid = areaid;
        }

        /**
         * @return The activecount
         */
        public Integer getActivecount() {
            return activecount;
        }

        /**
         * @param activecount The activecount
         */
        public void setActivecount(Integer activecount) {
            this.activecount = activecount;
        }

        /**
         * @return The cusinekey
         */
        public String getCusinekey() {
            return cusinekey;
        }

        /**
         * @param cusinekey The cusinekey
         */
        public void setCusinekey(String cusinekey) {
            this.cusinekey = cusinekey;
        }

        /**
         * @return The token
         */
        public List<Tokens> getToken() {
            return token;
        }

        /**
         * @param token The token
         */
        public void setToken(List<Tokens> token) {
            this.token = token;
        }

        /**
         * @return The restaurant
         */
        public List<Restaurants> getRestaurant() {
            return restaurant;
        }

        /**
         * @param restaurant The restaurant
         */
        public void setRestaurant(List<Restaurants> restaurant) {
            this.restaurant = restaurant;
        }

        /**
         * @return The status_code
         */
        public String getStatus_code() {
            return status_code;
        }

        /**
         * @param status_code The status_code
         */
        public void setStatus_code(String status_code) {
            this.status_code = status_code;
        }

        /**
         * @return The status_messages
         */
        public String getStatus_messages() {
            return status_messages;
        }

        /**
         * @param status_messages The status_messages
         */
        public void setStatus_messages(String status_messages) {
            this.status_messages = status_messages;
        }

        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }


        public class Restaurants {
            private String restaurant_key;
            private Integer restaurantid;
            private String id;
            private String name;
            private String locality;
            private Integer rating;
            private Integer on_time_stat;
            private Integer cost_rating;
            private Integer cost_for_two;
            private String logo_url;
            private Boolean online_payment;
            private String payment_modes;
            private Boolean status;
            private String phone_no;
            private String version;
            private Integer service_tax;
            private Double vat;
            private Integer packaging_charges;
            private String has_offers;
            private String cuisine_names;
            private Integer min_delivery_amount;
            private Integer delivery_time;
            private Integer delivery_charges;
            private String absolute_position;
            private String is_open;
            private String closing_at;
            private String has_recommendations;
            private Map<String, Object> additionalProperties = new HashMap<String, Object>();

            /**
             * @return The restaurantid
             */
            public Integer getRestaurantid() {
                return restaurantid;
            }

            /**
             * @param restaurantid The restaurantid
             */
            public void setRestaurantid(Integer restaurantid) {
                this.restaurantid = restaurantid;
            }

            /**
             * @return The id
             */
            public String getId() {
                return id;
            }

            /**
             * @param id The id
             */
            public void setId(String id) {
                this.id = id;
            }

            /**
             * @return The name
             */
            public String getName() {
                return name;
            }

            /**
             * @param name The name
             */
            public void setName(String name) {
                this.name = name;
            }


            public String getRestaurant_key() {
                return restaurant_key;
            }

            public void setRestaurant_key(String restaurant_key) {
                this.restaurant_key = restaurant_key;
            }

            /**
             * @return The locality
             */
            public String getLocality() {
                return locality;
            }

            /**
             * @param locality The locality
             */
            public void setLocality(String locality) {
                this.locality = locality;
            }

            /**
             * @return The rating
             */
            public Integer getRating() {
                return rating;
            }

            /**
             * @param rating The rating
             */
            public void setRating(Integer rating) {
                this.rating = rating;
            }

            /**
             * @return The on_time_stat
             */
            public Integer getOn_time_stat() {
                return on_time_stat;
            }

            /**
             * @param on_time_stat The on_time_stat
             */
            public void setOn_time_stat(Integer on_time_stat) {
                this.on_time_stat = on_time_stat;
            }

            /**
             * @return The cost_rating
             */
            public Integer getCost_rating() {
                return cost_rating;
            }

            /**
             * @param cost_rating The cost_rating
             */
            public void setCost_rating(Integer cost_rating) {
                this.cost_rating = cost_rating;
            }

            /**
             * @return The cost_for_two
             */
            public Integer getCost_for_two() {
                return cost_for_two;
            }

            /**
             * @param cost_for_two The cost_for_two
             */
            public void setCost_for_two(Integer cost_for_two) {
                this.cost_for_two = cost_for_two;
            }

            /**
             * @return The logo_url
             */
            public String getLogo_url() {
                return logo_url;
            }

            /**
             * @param logo_url The logo_url
             */
            public void setLogo_url(String logo_url) {
                this.logo_url = logo_url;
            }

            /**
             * @return The online_payment
             */
            public Boolean getOnline_payment() {
                return online_payment;
            }

            /**
             * @param online_payment The online_payment
             */
            public void setOnline_payment(Boolean online_payment) {
                this.online_payment = online_payment;
            }

            /**
             * @return The payment_modes
             */
            public String getPayment_modes() {
                return payment_modes;
            }

            /**
             * @param payment_modes The payment_modes
             */
            public void setPayment_modes(String payment_modes) {
                this.payment_modes = payment_modes;
            }

            /**
             * @return The status
             */
            public Boolean getStatus() {
                return status;
            }

            /**
             * @param status The status
             */
            public void setStatus(Boolean status) {
                this.status = status;
            }

            /**
             * @return The phone_no
             */
            public String getPhone_no() {
                return phone_no;
            }

            /**
             * @param phone_no The phone_no
             */
            public void setPhone_no(String phone_no) {
                this.phone_no = phone_no;
            }

            /**
             * @return The version
             */
            public String getVersion() {
                return version;
            }

            /**
             * @param version The version
             */
            public void setVersion(String version) {
                this.version = version;
            }

            /**
             * @return The service_tax
             */
            public Integer getService_tax() {
                return service_tax;
            }

            /**
             * @param service_tax The service_tax
             */
            public void setService_tax(Integer service_tax) {
                this.service_tax = service_tax;
            }

            /**
             * @return The vat
             */
            public Double getVat() {
                return vat;
            }

            /**
             * @param vat The vat
             */
            public void setVat(Double vat) {
                this.vat = vat;
            }

            /**
             * @return The packaging_charges
             */
            public Integer getPackaging_charges() {
                return packaging_charges;
            }

            /**
             * @param packaging_charges The packaging_charges
             */
            public void setPackaging_charges(Integer packaging_charges) {
                this.packaging_charges = packaging_charges;
            }

            /**
             * @return The has_offers
             */
            public String getHas_offers() {
                return has_offers;
            }

            /**
             * @param has_offers The has_offers
             */
            public void setHas_offers(String has_offers) {
                this.has_offers = has_offers;
            }

            /**
             * @return The cuisine_names
             */
            public String getCuisine_names() {
                return cuisine_names;
            }

            /**
             * @param cuisine_names The cuisine_names
             */
            public void setCuisine_names(String cuisine_names) {
                this.cuisine_names = cuisine_names;
            }

            /**
             * @return The min_delivery_amount
             */
            public Integer getMin_delivery_amount() {
                return min_delivery_amount;
            }

            /**
             * @param min_delivery_amount The min_delivery_amount
             */
            public void setMin_delivery_amount(Integer min_delivery_amount) {
                this.min_delivery_amount = min_delivery_amount;
            }

            /**
             * @return The delivery_time
             */
            public Integer getDelivery_time() {
                return delivery_time;
            }

            /**
             * @param delivery_time The delivery_time
             */
            public void setDelivery_time(Integer delivery_time) {
                this.delivery_time = delivery_time;
            }

            /**
             * @return The delivery_charges
             */
            public Integer getDelivery_charges() {
                return delivery_charges;
            }

            /**
             * @param delivery_charges The delivery_charges
             */
            public void setDelivery_charges(Integer delivery_charges) {
                this.delivery_charges = delivery_charges;
            }

            /**
             * @return The absolute_position
             */
            public String getAbsolute_position() {
                return absolute_position;
            }

            /**
             * @param absolute_position The absolute_position
             */
            public void setAbsolute_position(String absolute_position) {
                this.absolute_position = absolute_position;
            }

            /**
             * @return The is_open
             */
            public String getIs_open() {
                return is_open;
            }

            /**
             * @param is_open The is_open
             */
            public void setIs_open(String is_open) {
                this.is_open = is_open;
            }

            /**
             * @return The closing_at
             */
            public String getClosing_at() {
                return closing_at;
            }

            /**
             * @param closing_at The closing_at
             */
            public void setClosing_at(String closing_at) {
                this.closing_at = closing_at;
            }

            /**
             * @return The has_recommendations
             */
            public String getHas_recommendations() {
                return has_recommendations;
            }

            /**
             * @param has_recommendations The has_recommendations
             */
            public void setHas_recommendations(String has_recommendations) {
                this.has_recommendations = has_recommendations;
            }

            public Map<String, Object> getAdditionalProperties() {
                return this.additionalProperties;
            }

            public void setAdditionalProperty(String name, Object value) {
                this.additionalProperties.put(name, value);
            }
        }
    }
}