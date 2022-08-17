package com.foodpurby.model;


import com.foodpurby.util.URLClassUser;

import java.util.HashMap;
import java.util.Map;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Header;
import retrofit.http.POST;

/**
 * Created by android1 on 1/4/2016.
 */
public class DAOSignup {

    private static DAOSignup ourInstance = new DAOSignup();

    public static DAOSignup getInstance() {
        return ourInstance;
    }

    private DAOSignup() {
    }

    public void Callresponse(String token,
                             String firstName, String lastName, String mobileNumber,
                             String eMail, String password, String customerType, String fbUserId,
                             String fbUserAccessToken, String language, String device_id, String device_type,
                             Callback<Signup> mCallback) {
        SigninLists mGetapi = URLClassUser.getInstance().getApiBuilder().create(SigninLists.class);
        mGetapi.SigninListView(token, firstName, lastName, mobileNumber, eMail, password, customerType,
                fbUserId, fbUserAccessToken, language, device_id, device_type, mCallback);
    }

    public interface SigninLists {

        @FormUrlEncoded
        @POST("/signup.html")
        public void SigninListView(@Header("request") String token,
                                   @Field("customer_name") String firstName,
                                   @Field("customer_last_name") String lastName,
                                   @Field("customer_mobile") String mobileNumber,
                                   @Field("customer_email") String eMail,
                                   @Field("customer_password") String password,
                                   @Field("customer_type") String customerType,
                                   @Field("fb_user_id") String fbUserId,
                                   @Field("fb_user_access_token") String fbUserAccessToken,
                                   @Field("language") String language,
                                   @Field("device_id") String device_id,
                                   @Field("device_type") String device_type,
                                   Callback<Signup> response);
    }


    public class Signup {

        private String httpcode;
        private String status;

        public String getTmp_customer_key() {
            return tmp_customer_key;
        }

        public void setTmp_customer_key(String tmp_customer_key) {
            this.tmp_customer_key = tmp_customer_key;
        }

        private String tmp_customer_key;
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


        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

    }
}