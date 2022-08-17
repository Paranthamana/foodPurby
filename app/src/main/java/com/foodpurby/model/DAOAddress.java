package com.foodpurby.model;

import com.google.gson.annotations.SerializedName;
import com.foodpurby.util.URLClassUser;
import com.foodpurby.utillities.AppSharedValues;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by android1 on 1/4/2016.
 */
public class DAOAddress {

    private static DAOAddress ourInstance = new DAOAddress();

    public static DAOAddress getInstance() {
        return ourInstance;
    }

    private DAOAddress() {
    }



    public void AddCallresponse(String addressTypeId, String city, String location, String flatNo,
                                String apartment, String company, String postalCode, String landmark,
                                String alternateContact, String deliverInstruction, String customerKey,
                                String lat, String lon, Callback<Address> mCallback) {
        AddAddress mGetapi = URLClassUser.getInstance().getApiBuilder().create(AddAddress.class);
        mGetapi.Add(addressTypeId, city, location, flatNo, apartment,
                company, postalCode, landmark, alternateContact, deliverInstruction, AppSharedValues.getLanguage(),
                customerKey, lat, lon,
                mCallback);
    }

    public void UpdateCallresponse(String addressTypeId, String addressKey, String city, String location, String flatNo,
                                   String apartment, String company, String postalCode, String landmark,
                                   String alternateContact, String deliverInstruction, String customerKey,
                                   String lat, String lon, Callback<Address> mCallback) {
        UpdateAddress mGetapi = URLClassUser.getInstance().getApiBuilder().create(UpdateAddress.class);
        mGetapi.Update(addressTypeId, addressKey, city, location, flatNo, apartment,
                company, postalCode, landmark, alternateContact, deliverInstruction, AppSharedValues.getLanguage(),
                customerKey, lat, lon,
                mCallback);
    }
    public void Callresponse(String token, String customerKey, String languge, String key, String shopKey, Callback<Address> mCallback) {
        AddressLists mGetapi = URLClassUser.getInstance().getApiBuilder().create(AddressLists.class);
        mGetapi.AddressListView(token, customerKey, languge, key, shopKey, mCallback);
    }

    public interface AddressLists {
        @GET("/address_book_list.html")
        public void AddressListView(@Header("request") String token,
                                    @Query("customer_key") String customerKey,
                                    @Query("languge") String languge,
                                    @Query("key") String key,
                                    @Query("shop_key") String shopKey,
                                    Callback<Address> response);
    }

    public interface AddAddress {
        @FormUrlEncoded
        @POST("/add_address_book.html")
        public void Add(
                @Field("address_type_id") String addressTypeId,
                @Field("city") String cityKey,
                @Field("location") String areaKey,
                @Field("flat_no") String flatNo,
                @Field("apartment") String apartment,
                @Field("company") String company,
                @Field("street_name") String postalCode,
                @Field("landmark") String landmark,
                @Field("alternate_contact") String alternateContact,
                @Field("deliver_instruction") String deliverInstruction,
                @Field("language") String language,
                @Field("customer_key") String userKey,
                @Field("customer_latitude") String latitude,
                @Field("customer_longitude") String longitude,
                Callback<Address> response);
    }

    public interface UpdateAddress {
        @FormUrlEncoded
        @POST("/update_address_book.html")
        public void Update(
                @Field("address_type_id") String addressTypeId,
                @Field("address_key") String address_key,
                @Field("city") String cityKey,
                @Field("location") String areaKey,
                @Field("flat_no") String flatNo,
                @Field("apartment") String apartment,
                @Field("company") String company,
                @Field("street_name") String postalCode,
                @Field("landmark") String landmark,
                @Field("alternate_contact") String alternateContact,
                @Field("deliver_instruction") String deliverInstruction,
                @Field("language") String language,
                @Field("customer_key") String userKey,
                @Field("customer_latitude") String latitude,
                @Field("customer_longitude") String longitude,
                Callback<Address> response);
    }


    public class Address {

        private String httpcode;
        private String status;
        private String message;
        private Data data;
        private String responsetime;

        /**
         * @return The httpcode
         */
        public String getHttpcode() {
            return httpcode;
        }

        /**
         * @param httpcode The httpcode
         */
        public void setHttpcode(String httpcode) {
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

    public class Address_list {

        private String address_key;
        private String customer_key;
        @SerializedName("type_name")
        private String addressType;
        @SerializedName("address_type_id")
        private String addressTypeId;
        private String city;
        private String city_key;
        private String location;
        private String area_key;
        private String area;
        private String company;
        private String flat_no;
        private String apartment;
        private String location_name;
        private String postal_code;
        private String landmark;
        private String alternate_contact;
        private String deliver_instruction;
        private String is_delivery;

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

        private String customer_latitude;
        private String customer_longitude;

        /**
         * @return The address_key
         */
        public String getAddress_key() {
            return address_key;
        }

        /**
         * @param address_key The address_key
         */
        public void setAddress_key(String address_key) {
            this.address_key = address_key;
        }

        /**
         * @return The customer_key
         */
        public String getCustomer_key() {
            return customer_key;
        }

        /**
         * @param customer_key The customer_key
         */
        public void setCustomer_key(String customer_key) {
            this.customer_key = customer_key;
        }

        public String getAddressType() {
            return addressType;
        }

        public void setAddressType(String addressType) {
            this.addressType = addressType;
        }

        public String getAddressTypeId() {
            return addressTypeId;
        }

        public void setAddressTypeId(String addressTypeId) {
            this.addressTypeId = addressTypeId;
        }

        /**
         * @return The city
         */
        public String getCity() {
            return city;
        }

        /**
         * @param city The city
         */
        public void setCity(String city) {
            this.city = city;
        }

        /**
         * @return The city_key
         */
        public String getCity_key() {
            return city_key;
        }

        /**
         * @param city_key The city_key
         */
        public void setCity_key(String city_key) {
            this.city_key = city_key;
        }

        /**
         * @return The location
         */
        public String getLocation() {
            return location;
        }

        /**
         * @param location The location
         */
        public void setLocation(String location) {
            this.location = location;
        }

        /**
         * @return The area_key
         */
        public String getArea_key() {
            return area_key;
        }

        /**
         * @param area_key The area_key
         */
        public void setArea_key(String area_key) {
            this.area_key = area_key;
        }

        /**
         * @return The area
         */
        public String getArea() {
            return area;
        }

        /**
         * @param area The area
         */
        public void setArea(String area) {
            this.area = area;
        }

        /**
         * @return The company
         */
        public String getCompany() {
            return company;
        }

        /**
         * @param company The company
         */
        public void setCompany(String company) {
            this.company = company;
        }

        /**
         * @return The flat_no
         */
        public String getFlat_no() {
            return flat_no;
        }

        /**
         * @param flat_no The flat_no
         */
        public void setFlat_no(String flat_no) {
            this.flat_no = flat_no;
        }

        /**
         * @return The apartment
         */
        public String getApartment() {
            return apartment;
        }

        /**
         * @param apartment The apartment
         */
        public void setApartment(String apartment) {
            this.apartment = apartment;
        }

        /**
         * @return The location_name
         */
        public String getLocation_name() {
            return location_name;
        }

        /**
         * @param location_name The location_name
         */
        public void setLocation_name(String location_name) {
            this.location_name = location_name;
        }

        /**
         * @return The postal_code
         */
        public String getPostal_code() {
            return postal_code;
        }

        /**
         * @param postal_code The postal_code
         */
        public void setPostal_code(String postal_code) {
            this.postal_code = postal_code;
        }

        /**
         * @return The landmark
         */
        public String getLandmark() {
            return landmark;
        }

        /**
         * @param landmark The landmark
         */
        public void setLandmark(String landmark) {
            this.landmark = landmark;
        }

        /**
         * @return The alternate_contact
         */
        public String getAlternate_contact() {
            return alternate_contact;
        }

        /**
         * @param alternate_contact The alternate_contact
         */
        public void setAlternate_contact(String alternate_contact) {
            this.alternate_contact = alternate_contact;
        }

        /**
         * @return The deliver_instruction
         */
        public String getDeliver_instruction() {
            return deliver_instruction;
        }

        /**
         * @param deliver_instruction The deliver_instruction
         */
        public void setDeliver_instruction(String deliver_instruction) {
            this.deliver_instruction = deliver_instruction;
        }

        /**
         * @return The is_delivery
         */
        public String getIs_delivery() {
            return is_delivery;
        }

        /**
         * @param is_delivery The is_delivery
         */
        public void setIs_delivery(String is_delivery) {
            this.is_delivery = is_delivery;
        }

    }

    public class Data {

        private List<Address_list> address_list = new ArrayList<Address_list>();

        /**
         * @return The address_list
         */
        public List<Address_list> getAddress_list() {
            return address_list;
        }

        /**
         * @param address_list The address-list
         */
        public void setAddress_list(List<Address_list> address_list) {
            this.address_list = address_list;
        }

    }
}
