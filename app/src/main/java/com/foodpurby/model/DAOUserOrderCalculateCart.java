package com.foodpurby.model;


import com.foodpurby.util.URLClass;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by android1 on 1/21/2016.
 */
public class DAOUserOrderCalculateCart {
    private static DAOUserOrderCalculateCart ourInstance = new DAOUserOrderCalculateCart();

    public static DAOUserOrderCalculateCart getInstance() {
        return ourInstance;
    }

    private DAOUserOrderCalculateCart() {
    }

    public void Callresponse(String token, String userKey, String key, String language, Callback<CalculateCart> mCallback) {

        DAOOrderHelper obj = new DAOOrderHelper();

        MyLists mGetapi = URLClass.getInstance().getApiBuilder().create(MyLists.class);
        mGetapi.MyListView(token, userKey, key, language, obj.getCartProducts(), mCallback);
    }

    public interface MyLists {

        @POST("/calculate_basket.html")
        public void MyListView(@Header("request") String token,
                               @Query("ckey") String userKey,
                               @Query("key") String key,
                               @Query("language") String language,
                               @Body DAOOrderHelper.RequestValues mRequestValues,
                               Callback<CalculateCart> response);
    }


    public class CalculateCart {

        private Integer httpcode;
        private String status;
        private String message;
        private Data data;
        private Double wallet_points;
        private Integer member_count;
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

        public Double getWallet_points() {
            return wallet_points;
        }

        public void setWallet_points(Double wallet_points) {
            this.wallet_points = wallet_points;
        }

        public Integer getMember_count() {
            return member_count;
        }

        public void setMember_count(Integer member_count) {
            this.member_count = member_count;
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

    public class Data {

        private Delivery_details delivery_details;
        private Shop shop;
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        public Delivery_details getDelivery_details() {
            return delivery_details;
        }

        public void setDelivery_details(Delivery_details delivery_details) {
            this.delivery_details = delivery_details;
        }

        public Shop getShop() {
            return shop;
        }

        public void setShop(Shop shop) {
            this.shop = shop;
        }

        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

    }

    public class Delivery_details {

        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
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
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

    }

    public class Item {

        private String item_key;
        private String item_name;
        private String description;

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }

        public Double getItem_package_price() {
            return item_package_price;
        }

        public void setItem_package_price(Double item_package_price) {
            this.item_package_price = item_package_price;
        }

        public Double getItem_total_price() {
            return item_total_price;
        }

        public void setItem_total_price(Double item_total_price) {
            this.item_total_price = item_total_price;
        }

        private Double price;
        private Double item_package_price;
        private Integer quantity;
        private Integer is_ingredients;
        private List<Integer> ingredient_items = null;
        private List<Ingredient> ingredient = null;
        private Double item_total_price;
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        public String getItem_key() {
            return item_key;
        }

        public void setItem_key(String item_key) {
            this.item_key = item_key;
        }

        public String getItem_name() {
            return item_name;
        }

        public void setItem_name(String item_name) {
            this.item_name = item_name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }


        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

        public Integer getIs_ingredients() {
            return is_ingredients;
        }

        public void setIs_ingredients(Integer is_ingredients) {
            this.is_ingredients = is_ingredients;
        }

        public List<Integer> getIngredient_items() {
            return ingredient_items;
        }

        public void setIngredient_items(List<Integer> ingredient_items) {
            this.ingredient_items = ingredient_items;
        }

        public List<Ingredient> getIngredient() {
            return ingredient;
        }

        public void setIngredient(List<Ingredient> ingredient) {
            this.ingredient = ingredient;
        }


        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

    }

    public class Shop {

        private String shop_key;
        private String vendor_name;
        private double vendor_delivery_charge;
        private String shop_logo;
        private Integer pickup_time;
        private Integer delivery_time;
        private Integer min_order_value;
        private double vat_percentage;
        private Integer online_payment_available;
        private Double vendor_service_tax;
        private Integer self_pickup;
        private Double preorder_available;
        private String promocode;
        private double promocode_amount;
        private List<Item> items = null;
        private Double subtotal;
        private Double vat;
        private Double vat_price;
        private double delivery_fee;

        public Double getVendor_service_tax() {
            return vendor_service_tax;
        }

        public void setVendor_service_tax(Double vendor_service_tax) {
            this.vendor_service_tax = vendor_service_tax;
        }

        public Double getPreorder_available() {
            return preorder_available;
        }

        public void setPreorder_available(Double preorder_available) {
            this.preorder_available = preorder_available;
        }

        public Double getSubtotal() {
            return subtotal;
        }

        public void setSubtotal(Double subtotal) {
            this.subtotal = subtotal;
        }

        public Double getVat() {
            return vat;
        }

        public void setVat(Double vat) {
            this.vat = vat;
        }


        public String getVendor_name() {
            return vendor_name;
        }

        public void setVendor_name(String vendor_name) {
            this.vendor_name = vendor_name;
        }

        public double getVendor_delivery_charge() {
            return vendor_delivery_charge;
        }

        public void setVendor_delivery_charge(double vendor_delivery_charge) {
            this.vendor_delivery_charge = vendor_delivery_charge;
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

        public Integer getDelivery_time() {
            return delivery_time;
        }

        public void setDelivery_time(Integer delivery_time) {
            this.delivery_time = delivery_time;
        }

        public Integer getMin_order_value() {
            return min_order_value;
        }

        public void setMin_order_value(Integer min_order_value) {
            this.min_order_value = min_order_value;
        }

        public double getVat_percentage() {
            return vat_percentage;
        }

        public void setVat_percentage(double vat_percentage) {
            this.vat_percentage = vat_percentage;
        }

        public Integer getOnline_payment_available() {
            return online_payment_available;
        }

        public void setOnline_payment_available(Integer online_payment_available) {
            this.online_payment_available = online_payment_available;
        }


        public String getPromocode() {
            return promocode;
        }

        public void setPromocode(String promocode) {
            this.promocode = promocode;
        }

        public double getPromocode_amount() {
            return promocode_amount;
        }

        public void setPromocode_amount(double promocode_amount) {
            this.promocode_amount = promocode_amount;
        }

        public List<Item> getItems() {
            return items;
        }

        public void setItems(List<Item> items) {
            this.items = items;
        }


        public Double getVat_price() {
            return vat_price;
        }

        public void setVat_price(Double vat_price) {
            this.vat_price = vat_price;
        }

        public double getDelivery_fee() {
            return delivery_fee;
        }

        public void setDelivery_fee(double delivery_fee) {
            this.delivery_fee = delivery_fee;
        }

        public double getPickup_fee() {
            return pickup_fee;
        }

        public void setPickup_fee(double pickup_fee) {
            this.pickup_fee = pickup_fee;
        }

        public double getService_tax() {
            return service_tax;
        }

        public void setService_tax(double service_tax) {
            this.service_tax = service_tax;
        }

        public double getService_tax_amount() {
            return service_tax_amount;
        }

        public void setService_tax_amount(double service_tax_amount) {
            this.service_tax_amount = service_tax_amount;
        }

        public double getPackage_price() {
            return package_price;
        }

        public void setPackage_price(double package_price) {
            this.package_price = package_price;
        }

        public Double getTotal() {
            return total;
        }

        public void setTotal(Double total) {
            this.total = total;
        }

        public Double getGrand_total() {
            return grand_total;
        }

        public void setGrand_total(Double grand_total) {
            this.grand_total = grand_total;
        }

        private double pickup_fee;
        private double service_tax;
        private double service_tax_amount;
        private double package_price;
        private Double total;
        private Double grand_total;
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        public String getShop_key() {
            return shop_key;
        }

        public void setShop_key(String shop_key) {
            this.shop_key = shop_key;
        }


        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

        public Integer getSelf_pickup() {
            return self_pickup;
        }

        public void setSelf_pickup(Integer self_pickup) {
            this.self_pickup = self_pickup;
        }
    }

}