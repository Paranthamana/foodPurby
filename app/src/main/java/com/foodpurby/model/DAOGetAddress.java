package com.foodpurby.model;

import com.foodpurby.util.URLClassUser;
import com.foodpurby.utillities.AppSharedValues;

import java.util.HashMap;
import java.util.Map;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;

/**
 * Created by android1 on 1/4/2016.
 */
public class DAOGetAddress {

    private static DAOGetAddress ourInstance = new DAOGetAddress();

    public static DAOGetAddress getInstance() {
        return ourInstance;
    }

    private DAOGetAddress() {
    }

    public void Callresponse(String token, String customerKey, Callback<ResponseGetAddressApi> mCallback) {
        AddressLists mGetapi = URLClassUser.getInstance().getApiBuilder().create(AddressLists.class);
        mGetapi.AddressListView(token, customerKey, AppSharedValues.getLanguage(), "$2y$13$O8eus5IZeW9tkpEB3.sahuVVsmZemaXTyIxqDi3ROO2uEYvRxVC72", mCallback);
    }


    public interface AddressLists {
        @GET("/edit_address_book.html")
        public void AddressListView(@Header("request") String token,
                                    @Query("address_key") String customerKey,
                                    @Query("languge") String languge,
                                    @Query("key") String key,
                                    Callback<ResponseGetAddressApi> response);
    }

    public class ResponseGetAddressApi {

        private String httpcode;
        private String status;
        private String message;
        private Data data;
        private String responsetime;
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        /**
         *
         * @return
         * The httpcode
         */
        public String getHttpcode() {
            return httpcode;
        }

        /**
         *
         * @param httpcode
         * The httpcode
         */
        public void setHttpcode(String httpcode) {
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
        public Data getData() {
            return data;
        }

        /**
         *
         * @param data
         * The data
         */
        public void setData(Data data) {
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

        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

    }

    public class List {

        private String address_key;
        private String customer_key;
        private String city;
        private String location;
        private String company;
        private String flat_no;
        private String apartment;
        private String street_name;
        private String landmark;
        private String customer_latitude;
        private String customer_longitude;
        private String city_name;
        private String area_name;
        private String address_type_id;
        private String type_name;
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        /**
         *
         * @return
         * The address_key
         */
        public String getAddress_key() {
            return address_key;
        }

        /**
         *
         * @param address_key
         * The address_key
         */
        public void setAddress_key(String address_key) {
            this.address_key = address_key;
        }

        /**
         *
         * @return
         * The customer_key
         */
        public String getCustomer_key() {
            return customer_key;
        }

        /**
         *
         * @param customer_key
         * The customer_key
         */
        public void setCustomer_key(String customer_key) {
            this.customer_key = customer_key;
        }

        /**
         *
         * @return
         * The city
         */
        public String getCity() {
            return city;
        }

        /**
         *
         * @param city
         * The city
         */
        public void setCity(String city) {
            this.city = city;
        }

        /**
         *
         * @return
         * The location
         */
        public String getLocation() {
            return location;
        }

        /**
         *
         * @param location
         * The location
         */
        public void setLocation(String location) {
            this.location = location;
        }

        /**
         *
         * @return
         * The company
         */
        public String getCompany() {
            return company;
        }

        /**
         *
         * @param company
         * The company
         */
        public void setCompany(String company) {
            this.company = company;
        }

        /**
         *
         * @return
         * The flat_no
         */
        public String getFlat_no() {
            return flat_no;
        }

        /**
         *
         * @param flat_no
         * The flat_no
         */
        public void setFlat_no(String flat_no) {
            this.flat_no = flat_no;
        }

        /**
         *
         * @return
         * The apartment
         */
        public String getApartment() {
            return apartment;
        }

        /**
         *
         * @param apartment
         * The apartment
         */
        public void setApartment(String apartment) {
            this.apartment = apartment;
        }

        /**
         *
         * @return
         * The street_name
         */
        public String getStreet_name() {
            return street_name;
        }

        /**
         *
         * @param street_name
         * The street_name
         */
        public void setStreet_name(String street_name) {
            this.street_name = street_name;
        }

        /**
         *
         * @return
         * The landmark
         */
        public String getLandmark() {
            return landmark;
        }

        /**
         *
         * @param landmark
         * The landmark
         */
        public void setLandmark(String landmark) {
            this.landmark = landmark;
        }

        /**
         *
         * @return
         * The customer_latitude
         */
        public String getCustomer_latitude() {
            return customer_latitude;
        }

        /**
         *
         * @param customer_latitude
         * The customer_latitude
         */
        public void setCustomer_latitude(String customer_latitude) {
            this.customer_latitude = customer_latitude;
        }

        /**
         *
         * @return
         * The customer_longitude
         */
        public String getCustomer_longitude() {
            return customer_longitude;
        }

        /**
         *
         * @param customer_longitude
         * The customer_longitude
         */
        public void setCustomer_longitude(String customer_longitude) {
            this.customer_longitude = customer_longitude;
        }

        /**
         *
         * @return
         * The city_name
         */
        public String getCity_name() {
            return city_name;
        }

        /**
         *
         * @param city_name
         * The city_name
         */
        public void setCity_name(String city_name) {
            this.city_name = city_name;
        }

        /**
         *
         * @return
         * The area_name
         */
        public String getArea_name() {
            return area_name;
        }

        /**
         *
         * @param area_name
         * The area_name
         */
        public void setArea_name(String area_name) {
            this.area_name = area_name;
        }

        /**
         *
         * @return
         * The address_type_id
         */
        public String getAddress_type_id() {
            return address_type_id;
        }

        /**
         *
         * @param address_type_id
         * The address_type_id
         */
        public void setAddress_type_id(String address_type_id) {
            this.address_type_id = address_type_id;
        }

        /**
         *
         * @return
         * The type_name
         */
        public String getType_name() {
            return type_name;
        }

        /**
         *
         * @param type_name
         * The type_name
         */
        public void setType_name(String type_name) {
            this.type_name = type_name;
        }

        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

    }
    public class Data {

        private List list;
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        /**
         *
         * @return
         * The list
         */
        public List getList() {
            return list;
        }

        /**
         *
         * @param list
         * The list
         */
        public void setList(List list) {
            this.list = list;
        }

        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

    }
}
