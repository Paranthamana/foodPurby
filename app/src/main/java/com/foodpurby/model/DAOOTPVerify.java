package com.foodpurby.model;


import com.foodpurby.util.URLClassUser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by android1 on 12/31/2015.
 */
public class DAOOTPVerify {

    private static DAOOTPVerify ourInstance = new DAOOTPVerify();

    public static DAOOTPVerify getInstance() {
        return ourInstance;
    }

    private DAOOTPVerify() {
    }

    public void Callresponse(String otpCode, String orderKey, Callback<ConfirmOrder> mCallback) {
        MyLists mGetapi = URLClassUser.getInstance().getApiBuilder().create(MyLists.class);
        mGetapi.MyListView(otpCode, orderKey, mCallback);
    }

    public interface MyLists {
        @FormUrlEncoded
        @POST("/otp_verfication.html")
        public void MyListView(@Field("otp_verfication") String motpCode,
                               @Field("tmp_customer_key") String mCustomerkey,
                               Callback<ConfirmOrder> response);
    }

    public class ConfirmOrder {

        private String httpcode;
        private String status;
        private String message;
        private Data data;
        private Errors errors;
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

        public Errors getErrors() {
            return errors;
        }

        public void setErrors(Errors errors) {
            this.errors = errors;
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

    public class Errors {

        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

    }

    public class Data {

        private String customer_type;
        private String customer_key;
        private String customer_email;
        private String customer_name;
        private String customer_last_name;
        private String customer_mobile;
        private String access_token;
        private List<Object> delivery_details = null;
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        public String getCustomer_type() {
            return customer_type;
        }

        public void setCustomer_type(String customer_type) {
            this.customer_type = customer_type;
        }

        public String getCustomer_key() {
            return customer_key;
        }

        public void setCustomer_key(String customer_key) {
            this.customer_key = customer_key;
        }

        public String getCustomer_email() {
            return customer_email;
        }

        public void setCustomer_email(String customer_email) {
            this.customer_email = customer_email;
        }

        public String getCustomer_name() {
            return customer_name;
        }

        public void setCustomer_name(String customer_name) {
            this.customer_name = customer_name;
        }

        public String getCustomer_last_name() {
            return customer_last_name;
        }

        public void setCustomer_last_name(String customer_last_name) {
            this.customer_last_name = customer_last_name;
        }

        public String getCustomer_mobile() {
            return customer_mobile;
        }

        public void setCustomer_mobile(String customer_mobile) {
            this.customer_mobile = customer_mobile;
        }

        public String getAccess_token() {
            return access_token;
        }

        public void setAccess_token(String access_token) {
            this.access_token = access_token;
        }

        public List<Object> getDelivery_details() {
            return delivery_details;
        }

        public void setDelivery_details(List<Object> delivery_details) {
            this.delivery_details = delivery_details;
        }

        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

    }
}