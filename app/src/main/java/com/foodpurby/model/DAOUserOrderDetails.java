package com.foodpurby.model;


import com.foodpurby.util.URLClassUser;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Header;
import retrofit.http.POST;

/**
 * Created by android1 on 1/4/2016.
 */
public class DAOUserOrderDetails {

    private static DAOUserOrderDetails ourInstance = new DAOUserOrderDetails();

    public static DAOUserOrderDetails getInstance() {
        return ourInstance;
    }

    private DAOUserOrderDetails() {
    }

    public void Callresponse(String token, String orderKey, String language, Callback<UserOrderDetails> mCallback) {
        MyLists mGetapi = URLClassUser.getInstance().getApiBuilder().create(MyLists.class);
        mGetapi.MyListView(token, orderKey, language, mCallback);
    }

    public interface MyLists {

        @FormUrlEncoded
        @POST("/get_order.html")
        public void MyListView(@Header("request") String token,
                               @Field("okey") String orderKey,
                               @Field("language") String language,
                               Callback<UserOrderDetails> response);
    }


    public class Data {

        private String shop_name;
        private String shop_logo;
        private String order_datetime;
        private Double order_total;
        private Double service_tax_percentage;
        private Double service_tax;
        private Double vat;
        private Double vat_percentage;
        private Double sub_total;
        private String payment_status;
        private String delivery_type;
        private Double delivery_fee;
        private String delivery_status;
        private Double package_price;
        private List<Item> items = new ArrayList<Item>();
        private String order_key;
        private Double promocode_amount;
        private String promocode;
        private Integer delivery_timing;
        private String pickup_date;
        private String pickup_time;
        private Integer delivery_minutes;
        private Double total_ingredient_price;

        private String customer_latitude;
        private String customer_longitude;
        private String shop_latitude;
        private String shop_longitude;

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        private String order_id;

        public String getCustomer_latitude() {
            return customer_latitude;
        }

        public void setCustomer_latitude(String customer_latitude) {
            this.customer_latitude = customer_latitude;
        }

        public String getCustomer_longitude() {
            return customer_longitude;
        }

        public void setCustomer_longitude(String customer_longitude) {
            this.customer_longitude = customer_longitude;
        }

        public String getShop_latitude() {
            return shop_latitude;
        }

        public void setShop_latitude(String shop_latitude) {
            this.shop_latitude = shop_latitude;
        }

        public String getShop_longitude() {
            return shop_longitude;
        }

        public void setShop_longitude(String shop_longitude) {
            this.shop_longitude = shop_longitude;
        }

        public String getMap_tracking() {
            return map_tracking;
        }

        public void setMap_tracking(String map_tracking) {
            this.map_tracking = map_tracking;
        }

        private String map_tracking;

        /**
         *
         * @return
         * The order_key
         */
        public String getOrder_key() {
            return order_key;
        }

        /**
         *
         * @param order_key
         * The shop_name
         */
        public void setOrder_key(String order_key) {
            this.order_key = order_key;
        }

        /**
         *
         * @return
         * The shop_name
         */
        public String getShop_name() {
            return shop_name;
        }

        /**
         *
         * @param shop_name
         * The shop_name
         */
        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        /**
         *
         * @return
         * The shop_logo
         */
        public String getShop_logo() {
            return shop_logo;
        }

        /**
         *
         * @param shop_logo
         * The shop_logo
         */
        public void setShop_logo(String shop_logo) {
            this.shop_logo = shop_logo;
        }

        /**
         *
         * @return
         * The order_datetime
         */
        public String getOrder_datetime() {
            return order_datetime;
        }

        /**
         *
         * @param order_datetime
         * The order_datetime
         */
        public void setOrder_datetime(String order_datetime) {
            this.order_datetime = order_datetime;
        }

        /**
         *
         * @return
         * The order_total
         */
        public Double getOrder_total() {
            return order_total;
        }

        /**
         *
         * @param order_total
         * The order_total
         */
        public void setOrder_total(Double order_total) {
            this.order_total = order_total;
        }

        /**
         *
         * @return
         * The service_tax_percentage
         */
        public Double getService_tax_percentage() {
            return service_tax_percentage;
        }

        /**
         *
         * @param service_tax_percentage
         * The service_tax_percentage
         */
        public void setService_tax_percentage(Double service_tax_percentage) {
            this.service_tax_percentage = service_tax_percentage;
        }

        /**
         *
         * @return
         * The service_tax
         */
        public Double getService_tax() {
            return service_tax;
        }

        /**
         *
         * @param service_tax
         * The service_tax
         */
        public void setService_tax(Double service_tax) {
            this.service_tax = service_tax;
        }

        /**
         *
         * @return
         * The vat
         */
        public Double getVat() {
            return vat;
        }

        /**
         *
         * @param vat
         * The vat
         */
        public void setVat(Double vat) {
            this.vat = vat;
        }

        /**
         *
         * @return
         * The vat_percentage
         */
        public Double getVat_percentage() {
            return vat_percentage;
        }

        /**
         *
         * @param vat_percentage
         * The vat_percentage
         */
        public void setVat_percentage(Double vat_percentage) {
            this.vat_percentage = vat_percentage;
        }

        /**
         *
         * @return
         * The sub_total
         */
        public Double getSub_total() {
            return sub_total;
        }

        /**
         *
         * @param sub_total
         * The sub_total
         */
        public void setSub_total(Double sub_total) {
            this.sub_total = sub_total;
        }

        /**
         *
         * @return
         * The payment_status
         */
        public String getPayment_status() {
            return payment_status;
        }

        /**
         *
         * @param payment_status
         * The payment_status
         */
        public void setPayment_status(String payment_status) {
            this.payment_status = payment_status;
        }

        /**
         *
         * @return
         * The delivery_type
         */
        public String getDelivery_type() {
            return delivery_type;
        }

        /**
         *
         * @param delivery_type
         * The delivery_type
         */
        public void setDelivery_type(String delivery_type) {
            this.delivery_type = delivery_type;
        }

        /**
         *
         * @return
         * The delivery_fee
         */
        public Double getDelivery_fee() {
            return delivery_fee;
        }

        /**
         *
         * @param delivery_fee
         * The delivery_fee
         */
        public void setDelivery_fee(Double delivery_fee) {
            this.delivery_fee = delivery_fee;
        }

        /**
         *
         * @return
         * The delivery_status
         */
        public String getDelivery_status() {
            return delivery_status;
        }

        /**
         *
         * @param delivery_status
         * The delivery_status
         */
        public void setDelivery_status(String delivery_status) {
            this.delivery_status = delivery_status;
        }

        /**
         *
         * @return
         * The package_price
         */
        public Double getPackage_price() {
            return package_price;
        }

        /**
         *
         * @param package_price
         * The package_price
         */
        public void setPackage_price(Double package_price) {
            this.package_price = package_price;
        }

        /**
         *
         * @return
         * The items
         */
        public List<Item> getItems() {
            return items;
        }

        /**
         *
         * @param items
         * The items
         */
        public void setItems(List<Item> items) {
            this.items = items;
        }

        /**
         *
         * @return
         * The promocode_amount
         */
        public Double  getPromocode_amount() {
            return promocode_amount;
        }

        /**
         *
         * @param promocode_amount
         * The promocode_amount
         */
        public void setPromocode_amount(Double promocode_amount) {
            this.promocode_amount = promocode_amount;
        }

        /**
         *
         * @return
         * The promocode
         */
        public String getPromocode() {
            return promocode;
        }

        /**
         *
         * @param promocode
         * The promocode
         */
        public void setPromocode(String promocode) {
            this.promocode = promocode;
        }

        /**
         *
         * @return
         * The delivery_timing
         */
        public Integer getDelivery_timing() {
            return delivery_timing;
        }

        /**
         *
         * @param delivery_timing
         * The delivery_timing
         */
        public void setDelivery_timing(Integer delivery_timing) {
            this.delivery_timing = delivery_timing;
        }

        /**
         *
         * @return
         * The pickup_date
         */
        public String getPickup_date() {
            return pickup_date;
        }

        /**
         *
         * @param pickup_date
         * The pickup_date
         */
        public void setPickup_date(String pickup_date) {
            this.pickup_date = pickup_date;
        }

        /**
         *
         * @return
         * The pickup_time
         */
        public String getPickup_time() {
            return pickup_time;
        }

        /**
         *
         * @param pickup_time
         * The pickup_time
         */
        public void setPickup_time(String pickup_time) {
            this.pickup_time = pickup_time;
        }

        /**
         *
         * @return
         * The delivery_minutes
         */
        public Integer getDelivery_minutes() {
            return delivery_minutes;
        }

        /**
         *
         * @param delivery_minutes
         * The delivery_minutes
         */
        public void setDelivery_minutes(Integer delivery_minutes) {
            this.delivery_minutes = delivery_minutes;
        }


        /**
         *
         * @return
         * The total_ingredient_price
         */
        public Double getTotal_ingredient_price() {
            return total_ingredient_price;
        }

        /**
         *
         * @param total_ingredient_price
         * The total_ingredient_price
         */
        public void setTotal_ingredient_price(Double total_ingredient_price) {
            this.total_ingredient_price = total_ingredient_price;
        }
    }

    public class Ingredient {

        private String name;

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }

        private Double price;

        /**
         *
         * @return
         * The name
         */
        public String getName() {
            return name;
        }

        /**
         *
         * @param name
         * The name
         */
        public void setName(String name) {
            this.name = name;
        }



    }

    public class Item {

        private String name;

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }

        public Double getTotal_price() {
            return total_price;
        }

        public void setTotal_price(Double total_price) {
            this.total_price = total_price;
        }

        private Double price;
        private Integer quantity;
        private Double total_price;
        private Integer is_ingredients;
        private List<Ingredient> ingredients = new ArrayList<Ingredient>();

        /**
         *
         * @return
         * The name
         */
        public String getName() {
            return name;
        }

        /**
         *
         * @param name
         * The name
         */
        public void setName(String name) {
            this.name = name;
        }



        /**
         *
         * @return
         * The quantity
         */
        public Integer getQuantity() {
            return quantity;
        }

        /**
         *
         * @param quantity
         * The quantity
         */
        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }


        /**
         *
         * @return
         * The is_ingredients
         */
        public Integer getIs_ingredients() {
            return is_ingredients;
        }

        /**
         *
         * @param is_ingredients
         * The is_ingredients
         */
        public void setIs_ingredients(Integer is_ingredients) {
            this.is_ingredients = is_ingredients;
        }

        /**
         *
         * @return
         * The ingredients
         */
        public List<Ingredient> getIngredients() {
            return ingredients;
        }

        /**
         *
         * @param ingredients
         * The ingredients
         */
        public void setIngredients(List<Ingredient> ingredients) {
            this.ingredients = ingredients;
        }

    }

    public class UserOrderDetails {

        private Integer httpcode;
        private String status;
        private String message;
        private List<Data> data = new ArrayList<Data>();
        private String responsetime;

        /**
         *
         * @return
         * The httpcode
         */
        public Integer getHttpcode() {
            return httpcode;
        }

        /**
         *
         * @param httpcode
         * The httpcode
         */
        public void setHttpcode(Integer httpcode) {
            this.httpcode = httpcode;
        }

        /**
         *
         * @return
         * The status
         */
        public String getStatus() {
            return status;
        }

        /**
         *
         * @param status
         * The status
         */
        public void setStatus(String status) {
            this.status = status;
        }

        /**
         *
         * @return
         * The message
         */
        public String getMessage() {
            return message;
        }

        /**
         *
         * @param message
         * The message
         */
        public void setMessage(String message) {
            this.message = message;
        }

        /**
         *
         * @return
         * The data
         */
        public List<Data> getData() {
            return data;
        }

        /**
         *
         * @param data
         * The data
         */
        public void setData(List<Data> data) {
            this.data = data;
        }

        /**
         *
         * @return
         * The responsetime
         */
        public String getResponsetime() {
            return responsetime;
        }

        /**
         *
         * @param responsetime
         * The responsetime
         */
        public void setResponsetime(String responsetime) {
            this.responsetime = responsetime;
        }

    }
}