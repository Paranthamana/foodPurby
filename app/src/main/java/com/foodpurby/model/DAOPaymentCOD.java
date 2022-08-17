package com.foodpurby.model;


import com.foodpurby.util.URLClass;

import java.util.HashMap;
import java.util.Map;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;
import retrofit.http.Query;

public class DAOPaymentCOD {

    private static DAOPaymentCOD ourInstance = new DAOPaymentCOD();

    public static DAOPaymentCOD getInstance() {
        return ourInstance;
    }

    private DAOPaymentCOD() {
    }

    public void Callresponse(String language,
                             String addressKey,
                             String userKey,
                             String restaurantKey,
                             int deliveryOrPickup,
                             int onlneOrCOD,
                             String deviceId,
                             String deviceType,
                             String later_date,
                             String later_time,
                             String member_count,
                             Callback<CODPaymentResponse> mCallback) {

        DAOOrderHelper obj = new DAOOrderHelper();

        MyLabelLists mGetapi = URLClass.getInstance().getApiBuilder().create(MyLabelLists.class);
        mGetapi.MyListView(language,
                addressKey,
                userKey,
                restaurantKey,
                deliveryOrPickup,
                onlneOrCOD,
                deviceId,
                deviceType,
                later_date,
                later_time,
                member_count,
                obj.getCartProducts(),
                mCallback);
    }

    public interface MyLabelLists {

        //@FormUrlEncoded
        @POST("/payment.html?key=$2y$13$O8eus5IZeW9tkpEB3.sahuVVsmZemaXTyIxqDi3ROO2uEYvRxVC72")
        public void MyListView(@Query("language") String language,
                               @Query("akey") String akey,
                               @Query("ckey") String ckey,
                               @Query("shop_key") String shop_key,
                               @Query("d_opt") int d_opt,
                               @Query("p_opt") int p_opt,
                               @Query("dev") String dev,
                               @Query("dev_t") String dev_t,
                               @Query("later_date") String later_date,
                               @Query("later_time") String later_time,
                               @Query("member_count") String member_count,
                               @Body DAOOrderHelper.RequestValues mRequestValues,
                               Callback<CODPaymentResponse> codPaymentResponse);
    }




    public class CODPaymentResponse {

        private String httpcode;
        private String status;
        private String message;
        private Data data;
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

        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

    }


    public class Payment {

        private String otp;
        private String okey;
        private String ckey;
        private String type;

        public String getOrder_total() {
            return order_total;
        }

        public void setOrder_total(String order_total) {
            this.order_total = order_total;
        }

        private String order_total;
        private String cancel_url;
        private String redirect_url;
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        public String getOtp() {
            return otp;
        }

        public void setOtp(String otp) {
            this.otp = otp;
        }

        public String getOkey() {
            return okey;
        }

        public void setOkey(String okey) {
            this.okey = okey;
        }

        public String getCkey() {
            return ckey;
        }

        public void setCkey(String ckey) {
            this.ckey = ckey;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getCancel_url() {
            return cancel_url;
        }

        public void setCancel_url(String cancel_url) {
            this.cancel_url = cancel_url;
        }

        public String getRedirect_url() {
            return redirect_url;
        }

        public void setRedirect_url(String redirect_url) {
            this.redirect_url = redirect_url;
        }

        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

    }

    public class Data {

        private Payment payment;
        private Integer totalitems;
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        public Payment getPayment() {
            return payment;
        }

        public void setPayment(Payment payment) {
            this.payment = payment;
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


}