package com.foodpurby.model;


import com.foodpurby.util.URLClassUser;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by android1 on 1/18/2016.
 */
public class DAOUserCheckoutInfo {
    private static DAOUserCheckoutInfo ourInstance = new DAOUserCheckoutInfo();

    public static DAOUserCheckoutInfo getInstance() {
        return ourInstance;
    }

    private DAOUserCheckoutInfo() {
    }

    public void Callresponse(String userKey, String addressKey, String userFirstName,
                             String userLastName, String userEmail, String userMobileNo, String userType,
                             String fbUserId, String fbUserTokenKey, String city, String location, String company,
                             String flatNo, String apartment, String postalCode, String langmark,
                             String alternateContact, String deliveryInstructions, Callback<CheckoutAddressInfo> mCallback) {
        MyLists mGetapi = URLClassUser.getInstance().getApiBuilder().create(MyLists.class);
        mGetapi.MyListView(userKey, addressKey, userFirstName,
                            userLastName, userEmail, userMobileNo, userType,
                            fbUserId, fbUserTokenKey, city, location, company,
                            flatNo, apartment, postalCode, langmark,
                            alternateContact, deliveryInstructions, mCallback);
    }

    public interface MyLists {

        @FormUrlEncoded
        @POST("/customerinfo.html")
        public void MyListView(@Field("customer_key") String userKey,
                               @Field("address_key") String addressKey,
                               @Field("customer_name") String userFirstName,
                               @Field("customer_last_name") String userLastName,
                               @Field("customer_email") String userEmail,
                               @Field("customer_mobile") String userMobileNo,
                               @Field("customer_type") String userType,
                               @Field("fb_user_id") String fbUserId,
                               @Field("fb_user_access_token") String fbUserTokenKey,
                               @Field("city") String city,
                               @Field("location") String location,
                               @Field("company") String company,
                               @Field("flat_no") String flatNo,
                               @Field("apartment") String apartment,
                               @Field("postal_code") String postalCode,
                               @Field("landmark") String langmark,
                               @Field("alternate_contact") String alternateContact,
                               @Field("deliver_instruction") String deliveryInstructions,
                               Callback<CheckoutAddressInfo> response);
    }

    public class CheckoutAddressInfo {

        private Integer httpcode;
        private String status;
        private String message;
        private Data data;
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

    }

    public class Data {

        private String customer_key;
        private String address_key;

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

    }
}