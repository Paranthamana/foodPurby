package com.foodpurby.model;

import com.foodpurby.util.URLClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by android1 on 1/4/2016.
 */
public class DAOVendorOutlet {

    private static DAOVendorOutlet ourInstance = new DAOVendorOutlet();

    public static DAOVendorOutlet getInstance() {
        return ourInstance;
    }

    private DAOVendorOutlet() {
    }

    public void VendorOutletResponse(String ShopKey, Callback<Outlets> mCallback) {
        VendorOutlet mVendorOutlet = URLClass.getInstance().getApiBuilder().create(VendorOutlet.class);
        mVendorOutlet.View(ShopKey,mCallback);
    }

    public interface VendorOutlet {

        @GET("/outletlisting.html?key=$2y$13$O8eus5IZeW9tkpEB3.sahuVVsmZemaXTyIxqDi3ROO2uEYvRxVC72")
        public void View(
                @Query ("shop_key") String shopkey,
                Callback<Outlets> response);
    }

    public class Outlets {

        private String httpcode;
        private String status;
        private String message;
        private Data data;
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

        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

    }

    public class Data {

        private List<Vendor_outlet> vendor_outlets = new ArrayList<Vendor_outlet>();
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        /**
         *
         * @return
         * The vendor_outlets
         */
        public List<Vendor_outlet> getVendor_outlets() {
            return vendor_outlets;
        }

        /**
         *
         * @param vendor_outlets
         * The vendor_outlets
         */
        public void setVendor_outlets(List<Vendor_outlet> vendor_outlets) {
            this.vendor_outlets = vendor_outlets;
        }

        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

    }

    public class Vendor_outlet {

        private String outlet_id;
        private String vendor_id;
        private String outlet_key;
        private String outlet_name;
        private String outlet_contact_address;
        private String outlet_contact_name;
        private String outlet_conatct_number;
        private String outlet_emergency_contact_number;
        private String outlet_contact_email;
        private String outlet_city;
        private String outlet_area;
        private String outlet_latitude;
        private String outlet_longitude;
        private String outlet_status;
        private String created_by;
        private String modified_by;
        private String created_datetime;
        private String modified_datetime;
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        /**
         *
         * @return
         * The outlet_id
         */
        public String getOutlet_id() {
            return outlet_id;
        }

        /**
         *
         * @param outlet_id
         * The outlet_id
         */
        public void setOutlet_id(String outlet_id) {
            this.outlet_id = outlet_id;
        }

        /**
         *
         * @return
         * The vendor_id
         */
        public String getVendor_id() {
            return vendor_id;
        }

        /**
         *
         * @param vendor_id
         * The vendor_id
         */
        public void setVendor_id(String vendor_id) {
            this.vendor_id = vendor_id;
        }

        /**
         *
         * @return
         * The outlet_key
         */
        public String getOutlet_key() {
            return outlet_key;
        }

        /**
         *
         * @param outlet_key
         * The outlet_key
         */
        public void setOutlet_key(String outlet_key) {
            this.outlet_key = outlet_key;
        }

        /**
         *
         * @return
         * The outlet_name
         */
        public String getOutlet_name() {
            return outlet_name;
        }

        /**
         *
         * @param outlet_name
         * The outlet_name
         */
        public void setOutlet_name(String outlet_name) {
            this.outlet_name = outlet_name;
        }

        /**
         *
         * @return
         * The outlet_contact_address
         */
        public String getOutlet_contact_address() {
            return outlet_contact_address;
        }

        /**
         *
         * @param outlet_contact_address
         * The outlet_contact_address
         */
        public void setOutlet_contact_address(String outlet_contact_address) {
            this.outlet_contact_address = outlet_contact_address;
        }

        /**
         *
         * @return
         * The outlet_contact_name
         */
        public String getOutlet_contact_name() {
            return outlet_contact_name;
        }

        /**
         *
         * @param outlet_contact_name
         * The outlet_contact_name
         */
        public void setOutlet_contact_name(String outlet_contact_name) {
            this.outlet_contact_name = outlet_contact_name;
        }

        /**
         *
         * @return
         * The outlet_conatct_number
         */
        public String getOutlet_conatct_number() {
            return outlet_conatct_number;
        }

        /**
         *
         * @param outlet_conatct_number
         * The outlet_conatct_number
         */
        public void setOutlet_conatct_number(String outlet_conatct_number) {
            this.outlet_conatct_number = outlet_conatct_number;
        }

        /**
         *
         * @return
         * The outlet_emergency_contact_number
         */
        public String getOutlet_emergency_contact_number() {
            return outlet_emergency_contact_number;
        }

        /**
         *
         * @param outlet_emergency_contact_number
         * The outlet_emergency_contact_number
         */
        public void setOutlet_emergency_contact_number(String outlet_emergency_contact_number) {
            this.outlet_emergency_contact_number = outlet_emergency_contact_number;
        }

        /**
         *
         * @return
         * The outlet_contact_email
         */
        public String getOutlet_contact_email() {
            return outlet_contact_email;
        }

        /**
         *
         * @param outlet_contact_email
         * The outlet_contact_email
         */
        public void setOutlet_contact_email(String outlet_contact_email) {
            this.outlet_contact_email = outlet_contact_email;
        }

        /**
         *
         * @return
         * The outlet_city
         */
        public String getOutlet_city() {
            return outlet_city;
        }

        /**
         *
         * @param outlet_city
         * The outlet_city
         */
        public void setOutlet_city(String outlet_city) {
            this.outlet_city = outlet_city;
        }

        /**
         *
         * @return
         * The outlet_area
         */
        public String getOutlet_area() {
            return outlet_area;
        }

        /**
         *
         * @param outlet_area
         * The outlet_area
         */
        public void setOutlet_area(String outlet_area) {
            this.outlet_area = outlet_area;
        }

        /**
         *
         * @return
         * The outlet_latitude
         */
        public String getOutlet_latitude() {
            return outlet_latitude;
        }

        /**
         *
         * @param outlet_latitude
         * The outlet_latitude
         */
        public void setOutlet_latitude(String outlet_latitude) {
            this.outlet_latitude = outlet_latitude;
        }

        /**
         *
         * @return
         * The outlet_longitude
         */
        public String getOutlet_longitude() {
            return outlet_longitude;
        }

        /**
         *
         * @param outlet_longitude
         * The outlet_longitude
         */
        public void setOutlet_longitude(String outlet_longitude) {
            this.outlet_longitude = outlet_longitude;
        }

        /**
         *
         * @return
         * The outlet_status
         */
        public String getOutlet_status() {
            return outlet_status;
        }

        /**
         *
         * @param outlet_status
         * The outlet_status
         */
        public void setOutlet_status(String outlet_status) {
            this.outlet_status = outlet_status;
        }

        /**
         *
         * @return
         * The created_by
         */
        public String getCreated_by() {
            return created_by;
        }

        /**
         *
         * @param created_by
         * The created_by
         */
        public void setCreated_by(String created_by) {
            this.created_by = created_by;
        }

        /**
         *
         * @return
         * The modified_by
         */
        public String getModified_by() {
            return modified_by;
        }

        /**
         *
         * @param modified_by
         * The modified_by
         */
        public void setModified_by(String modified_by) {
            this.modified_by = modified_by;
        }

        /**
         *
         * @return
         * The created_datetime
         */
        public String getCreated_datetime() {
            return created_datetime;
        }

        /**
         *
         * @param created_datetime
         * The created_datetime
         */
        public void setCreated_datetime(String created_datetime) {
            this.created_datetime = created_datetime;
        }

        /**
         *
         * @return
         * The modified_datetime
         */
        public String getModified_datetime() {
            return modified_datetime;
        }

        /**
         *
         * @param modified_datetime
         * The modified_datetime
         */
        public void setModified_datetime(String modified_datetime) {
            this.modified_datetime = modified_datetime;
        }

        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

    }
}
