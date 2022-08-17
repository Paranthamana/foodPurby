package com.foodpurby.model;

import com.foodpurby.util.URLClassUser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;

/**
 * Created by android1 on 1/4/2016.
 */
public class DAOAddressTypeList {

    private static DAOAddressTypeList ourInstance = new DAOAddressTypeList();

    public static DAOAddressTypeList getInstance() {
        return ourInstance;
    }

    private DAOAddressTypeList() {
    }

    public void AddTypeCallresponse(String mShopKey, String customerKey, String language, Callback<AddressType> mCallback) {
        AddressTypeLists mGetapi = URLClassUser.getInstance().getApiBuilder().create(AddressTypeLists.class);
        mGetapi.AddressTypeApi(mShopKey, customerKey, language,mCallback);
    }

    public interface AddressTypeLists {
        @GET("/addresstype_list.html?key=$2y$13$O8eus5IZeW9tkpEB3.sahuVVsmZemaXTyIxqDi3ROO2uEYvRxVC72")
        public void AddressTypeApi(@Header("shop_key") String mShopKey,
                                    @Query("customer_key") String customerKey,
                                    @Query("language") String language,
                                    Callback<AddressType> response);
    }

    /*public class AddressType {
        private String httpcode;
        private String status;
        private String message;
        private Data data;
        private String responsetime;
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        *//**
         *
         * @return
         * The httpcode
         *//*
        public String getHttpcode() {
            return httpcode;
        }

        *//**
         *
         * @param httpcode
         * The httpcode
         *//*
        public void setHttpcode(String httpcode) {
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

        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }
    }

    public class Data {

        private List<Address_list> address_list = new ArrayList<Address_list>();
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        *//**
         *
         * @return
         * The address_list
         *//*
        public List<Address_list> getAddress_list() {
            return address_list;
        }

        *//**
         *
         * @param address_list
         * The address_list
         *//*
        public void setAddress_list(List<Address_list> address_list) {
            this.address_list = address_list;
        }

        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

    }

    public class Address_list {

        private String type_id;
        private String type_name;
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        *//**
         *
         * @return
         * The type_id
         *//*
        public String getType_id() {
            return type_id;
        }

        *//**
         *
         * @param type_id
         * The type_id
         *//*
        public void setType_id(String type_id) {
            this.type_id = type_id;
        }

        *//**
         *
         * @return
         * The type_name
         *//*
        public String getType_name() {
            return type_name;
        }

        *//**
         *
//         * @param type_name
         * The type_name
         *//*
        public void setType_name(String type_name) {
            this.type_name = type_name;
        }

        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

    }*/


    public class AddressType {

        private String httpcode;
        private String status;
        private String message;
        private Data data;
        private String responsetime;
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        public String getHttpcode() {
            return httpcode;
        }

        public void setHttpcode(String httpcode) {
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

    public class Address_list {

        private String type_id;
        private String type_name;
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        public String getType_id() {
            return type_id;
        }

        public void setType_id(String type_id) {
            this.type_id = type_id;
        }

        public String getType_name() {
            return type_name;
        }

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

        private List<Address_list> address_list = null;
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        public List<Address_list> getAddress_list() {
            return address_list;
        }

        public void setAddress_list(List<Address_list> address_list) {
            this.address_list = address_list;
        }

        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

    }


}
