package com.foodpurby.api;

import com.foodpurby.model.DAOCredit;

import java.util.HashMap;
import java.util.Map;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by Tech on 18-04-2017.
 */

public interface TabaocoCreditApi {

    @FormUrlEncoded
    @POST("/api/v3/user/wallet_points.html")
    public void SubscribeNowcredit(@Query("customer_key") String customer_key,
                                   @Field("walletpoints") String walletpoints,
                                   Callback<DAOCredit.Tabaoco_credit_Response> response);



    public class Tabaoco_credit_Response {

        private String httpcode;
        private String status;
        private String message;
        private Object data;
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

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }

        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

    }

}
