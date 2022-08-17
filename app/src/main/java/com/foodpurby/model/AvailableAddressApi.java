package com.foodpurby.model;


import com.foodpurby.util.URLClassDefault;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by android1 on 1/4/2016.
 */
public class AvailableAddressApi {

    private static AvailableAddressApi ourInstance = new AvailableAddressApi();

    public static AvailableAddressApi getInstance() {
        return ourInstance;
    }

    private AvailableAddressApi() {
    }

    public void Callresponse(String vendor_key, String customer_key, Callback<ResponseAvailableAddressApi> mCallback) {
        AvailableAddressInterface mGetapi = URLClassDefault.getInstance().getApiBuilder().create(AvailableAddressInterface.class);
        mGetapi.AvailableAddress(vendor_key, customer_key, mCallback);
    }

    public interface AvailableAddressInterface {
        @GET("/available_address.html")
        public void AvailableAddress(@Query("vendor_key") String vendor_key,
                                     @Query("customer_key") String customer_key,
                                     Callback<ResponseAvailableAddressApi> response);
    }

    public class ResponseAvailableAddressApi {

        private String httpcode;
        private String status;
        private String message;
        private Data data;
        private String responsetime;
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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

        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

    }

    public class Vendor_detail {

        private List<String> coordinates = null;
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        /**
         * @return The coordinates
         */
        public List<String> getCoordinates() {
            return coordinates;
        }

        /**
         * @param coordinates The coordinates
         */
        public void setCoordinates(List<String> coordinates) {
            this.coordinates = coordinates;
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
        private Vendor_detail vendor_detail;
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        /**
         * @return The address_list
         */
        public List<Address_list> getAddress_list() {
            return address_list;
        }

        /**
         * @param address_list The address_list
         */
        public void setAddress_list(List<Address_list> address_list) {
            this.address_list = address_list;
        }

        /**
         * @return The vendor_detail
         */
        public Vendor_detail getVendor_detail() {
            return vendor_detail;
        }

        /**
         * @param vendor_detail The vendor_detail
         */
        public void setVendor_detail(Vendor_detail vendor_detail) {
            this.vendor_detail = vendor_detail;
        }

        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

    }

    public class Address_list {

        private String address_key;
        private String customer_latitude;
        private String customer_longitude;
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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
         * @return The customer_latitude
         */
        public String getCustomer_latitude() {
            return customer_latitude;
        }

        /**
         * @param customer_latitude The customer_latitude
         */
        public void setCustomer_latitude(String customer_latitude) {
            this.customer_latitude = customer_latitude;
        }

        /**
         * @return The customer_longitude
         */
        public String getCustomer_longitude() {
            return customer_longitude;
        }

        /**
         * @param customer_longitude The customer_longitude
         */
        public void setCustomer_longitude(String customer_longitude) {
            this.customer_longitude = customer_longitude;
        }

        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

    }
}
