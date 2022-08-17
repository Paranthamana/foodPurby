package com.foodpurby.model;


import com.foodpurby.util.URLClass;
import com.foodpurby.utillities.AppSharedValues;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;

/**
 * Created by android1 on 12/31/2015.
 */
public class DAORestaurantBranchItems {

    private static DAORestaurantBranchItems ourInstance = new DAORestaurantBranchItems();

    public static DAORestaurantBranchItems getInstance() {
        return ourInstance;
    }

    private DAORestaurantBranchItems() {
    }

    public void Callresponse(String token, String shop,String cat, Callback<RestaurantBranchItems> mCallback) {
        RestaurantlabelLists mGetapi = URLClass.getInstance().getApiBuilder().create(RestaurantlabelLists.class);
        mGetapi.RestaurantNameListView(token, shop,AppSharedValues.getLanguage(), "$2y$13$O8eus5IZeW9tkpEB3.sahuVVsmZemaXTyIxqDi3ROO2uEYvRxVC72",cat, mCallback);
    }

    public interface RestaurantlabelLists {
        @GET("/shop_detail.html")
        public void RestaurantNameListView(@Header("request") String token,
                                           @Query("shop") String shop,
                                           @Query("language") String languge,
                                           @Query("key") String key,
                                           @Query("cat") String cat,
                                           Callback<RestaurantBranchItems> response);
    }



    public class RestaurantBranchItems {

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

    public class Category {

        private String category_name;
        private String category_key;
        private List<Menu_item> menu_items = null;
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        public String getCategory_name() {
            return category_name;
        }

        public void setCategory_name(String category_name) {
            this.category_name = category_name;
        }

        public String getCategory_key() {
            return category_key;
        }

        public void setCategory_key(String category_key) {
            this.category_key = category_key;
        }

        public List<Menu_item> getMenu_items() {
            return menu_items;
        }

        public void setMenu_items(List<Menu_item> menu_items) {
            this.menu_items = menu_items;
        }

        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

    }

    public class Data {

        private List<Shop_detail> shop_detail = null;
        private Integer totalitems;
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        public List<Shop_detail> getShop_detail() {
            return shop_detail;
        }

        public void setShop_detail(List<Shop_detail> shop_detail) {
            this.shop_detail = shop_detail;
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

    public class Ingredient {

        private String ingredients_type_name;
        private Integer ingredients_type_id;
        private Integer item_ingredients_id;
        private Integer minimum;
        private Integer maximum;
        private Integer required;
        private List<Ingredients_list> ingredients_list = null;
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        public String getIngredients_type_name() {
            return ingredients_type_name;
        }

        public void setIngredients_type_name(String ingredients_type_name) {
            this.ingredients_type_name = ingredients_type_name;
        }

        public Integer getIngredients_type_id() {
            return ingredients_type_id;
        }

        public void setIngredients_type_id(Integer ingredients_type_id) {
            this.ingredients_type_id = ingredients_type_id;
        }

        public Integer getItem_ingredients_id() {
            return item_ingredients_id;
        }

        public void setItem_ingredients_id(Integer item_ingredients_id) {
            this.item_ingredients_id = item_ingredients_id;
        }

        public Integer getMinimum() {
            return minimum;
        }

        public void setMinimum(Integer minimum) {
            this.minimum = minimum;
        }

        public Integer getMaximum() {
            return maximum;
        }

        public void setMaximum(Integer maximum) {
            this.maximum = maximum;
        }

        public Integer getRequired() {
            return required;
        }

        public void setRequired(Integer required) {
            this.required = required;
        }

        public List<Ingredients_list> getIngredients_list() {
            return ingredients_list;
        }

        public void setIngredients_list(List<Ingredients_list> ingredients_list) {
            this.ingredients_list = ingredients_list;
        }

        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

    }

    public class Ingredients_list {

        private String ingredient_name;

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }

        private Double price;
        private Integer item_ingredients_list_id;
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        public String getIngredient_name() {
            return ingredient_name;
        }

        public void setIngredient_name(String ingredient_name) {
            this.ingredient_name = ingredient_name;
        }



        public Integer getItem_ingredients_list_id() {
            return item_ingredients_list_id;
        }

        public void setItem_ingredients_list_id(Integer item_ingredients_list_id) {
            this.item_ingredients_list_id = item_ingredients_list_id;
        }

        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

    }

    public class Menu_item {

        private String item_key;
        private String item_name;
        private Integer item_id;
        private String item_description;

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }

        private Double price;
        private Integer is_ingredient;
        private String item_image;
        private Integer item_type;
        private List<Ingredient> ingredients = null;
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

        public Integer getItem_id() {
            return item_id;
        }

        public void setItem_id(Integer item_id) {
            this.item_id = item_id;
        }

        public String getItem_description() {
            return item_description;
        }

        public void setItem_description(String item_description) {
            this.item_description = item_description;
        }



        public Integer getIs_ingredient() {
            return is_ingredient;
        }

        public void setIs_ingredient(Integer is_ingredient) {
            this.is_ingredient = is_ingredient;
        }

        public String getItem_image() {
            return item_image;
        }

        public void setItem_image(String item_image) {
            this.item_image = item_image;
        }

        public Integer getItem_type() {
            return item_type;
        }

        public void setItem_type(Integer item_type) {
            this.item_type = item_type;
        }

        public List<Ingredient> getIngredients() {
            return ingredients;
        }

        public void setIngredients(List<Ingredient> ingredients) {
            this.ingredients = ingredients;
        }

        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

    }

    public class Shop_detail {

        private String shop_name;

        public String getContact_number() {
            return contact_number;
        }

        public void setContact_number(String contact_number) {
            this.contact_number = contact_number;
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

        public String getCertificateimage() {
            return certificateimage;
        }

        public void setCertificateimage(String certificateimage) {
            this.certificateimage = certificateimage;
        }

        public Integer getPickup_time() {
            return pickup_time;
        }

        public void setPickup_time(Integer pickup_time) {
            this.pickup_time = pickup_time;
        }

        public String getShop_logo() {
            return shop_logo;
        }

        public void setShop_logo(String shop_logo) {
            this.shop_logo = shop_logo;
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

        public Double getLatitude() {
            return latitude;
        }

        public void setLatitude(Double latitude) {
            this.latitude = latitude;
        }

        public Double getLongitude() {
            return longitude;
        }

        public void setLongitude(Double longitude) {
            this.longitude = longitude;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getCity_name() {
            return city_name;
        }

        public void setCity_name(String city_name) {
            this.city_name = city_name;
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

        public Double getVendor_service_tax() {
            return vendor_service_tax;
        }

        public void setVendor_service_tax(Double vendor_service_tax) {
            this.vendor_service_tax = vendor_service_tax;
        }

        public Double getVat_percentage() {
            return vat_percentage;
        }

        public void setVat_percentage(Double vat_percentage) {
            this.vat_percentage = vat_percentage;
        }

        public String getCuisines() {
            return cuisines;
        }

        public void setCuisines(String cuisines) {
            this.cuisines = cuisines;
        }

        public String getOffer_text() {
            return offer_text;
        }

        public void setOffer_text(String offer_text) {
            this.offer_text = offer_text;
        }

        public String getVoucher() {
            return voucher;
        }

        public void setVoucher(String voucher) {
            this.voucher = voucher;
        }

        public String getOffer_code() {
            return offer_code;
        }

        public void setOffer_code(String offer_code) {
            this.offer_code = offer_code;
        }

        public Integer getOffer_min_order_value() {
            return offer_min_order_value;
        }

        public void setOffer_min_order_value(Integer offer_min_order_value) {
            this.offer_min_order_value = offer_min_order_value;
        }

        public String getVoucher_text() {
            return voucher_text;
        }

        public void setVoucher_text(String voucher_text) {
            this.voucher_text = voucher_text;
        }

        public List<Category> getCategories() {
            return categories;
        }

        public void setCategories(List<Category> categories) {
            this.categories = categories;
        }

        public String getShop_status() {
            return shop_status;
        }

        public void setShop_status(String shop_status) {
            this.shop_status = shop_status;
        }

        public String getShop_opening_time() {
            return shop_opening_time;
        }

        public void setShop_opening_time(String shop_opening_time) {
            this.shop_opening_time = shop_opening_time;
        }

        private String contact_number;
        private String shop_address;
        private String shop_key;
        private String certificateimage;
        private Integer pickup_time;
        private String shop_logo;
        private Integer delivery_in;
        private Integer min_order_value;
        private Double latitude;
        private Double longitude;
        private String location;
        private String city_name;
        private Integer preorder_available;
        private Integer online_payment_available;
        private Integer pure_vegetarian;
        private Double avg_rating;
        private Integer total_rating;
        private Double vendor_service_tax;
        private Double vat_percentage;
        private String cuisines;
        private String offer_text;
        private String voucher;
        private String offer_code;
        private Integer offer_min_order_value;
        private String voucher_text;
        private List<Category> categories = null;
        private String shop_status;
        private String shop_opening_time;
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        public String getShop_name() {
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }




        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

    }
}