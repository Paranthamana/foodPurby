package com.foodpurby.model;


import com.google.gson.annotations.SerializedName;
import com.foodpurby.util.URLClass;

import java.util.HashMap;
import java.util.Map;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;
import retrofit.http.Query;

public class DAOPaymentOnline {

    private static DAOPaymentOnline ourInstance = new DAOPaymentOnline();

    public static DAOPaymentOnline getInstance() {
        return ourInstance;
    }

    private DAOPaymentOnline() {
    }

    public void Callresponse(String key, String language, String addressKey, String userKey, String restaurantKey,
                             int deliveryOrPickup, int onlneOrCOD, String deviceId, String deviceType,
                             String later_date, String later_time,
                             Callback<OnlinePaymentResponse> mCallback) {


        DAOOrderHelper obj = new DAOOrderHelper();

        MyLabelLists mGetapi = URLClass.getInstance().getApiBuilder().create(MyLabelLists.class);
        mGetapi.MyListView(key, language, addressKey, userKey, restaurantKey, deliveryOrPickup, onlneOrCOD, deviceId, deviceType, later_date, later_time, obj.getCartProducts(), mCallback);
    }

    public interface MyLabelLists {

        //@FormUrlEncoded
        @POST("/payment.html")
        public void MyListView(@Query("key") String key, @Query("language") String language,
                                @Query("akey") String akey, @Query("ckey") String ckey,
                                @Query("shop_key") String shop_key,
                                @Query("d_opt") int d_opt, @Query("p_opt") int p_opt,
                                @Query("dev") String dev, @Query("dev_t") String dev_t,
                                @Query("later_date") String later_date, @Query("later_time") String later_time,
                               @Body DAOOrderHelper.RequestValues mRequestValues,
                                Callback<OnlinePaymentResponse> onlinePaymentResponse);
    }


    public class OnlinePaymentResponse {

        private String httpcode;
        private String status;
        private String message;
        private Data data;

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

    }


    public class Payment {

        private String otp;
        private String okey;
        private String ckey;
        private String type;
        private String form_id;
        @SerializedName("MerchanCode")
        private String merchanCode;
        @SerializedName("MerchantKey")
        private String merchantKey;
        @SerializedName("PaymentId")
        private String paymentId;
        @SerializedName("RefNo")
        private String refNo;
        @SerializedName("Amount")
        private String amount;
        @SerializedName("Currency")
        private String currency;
        @SerializedName("ProdDesc")
        private String prodDesc;
        @SerializedName("UserName")
        private String userName;
        @SerializedName("UserEmail")
        private String userEmail;
        @SerializedName("UserContact")
        private String userContact;
        @SerializedName("Remark")
        private String remark;
        @SerializedName("Signature")
        private String signature;
        @SerializedName("ResponseURL")
        private String responseURL;
        @SerializedName("BackendURL")
        private String backendURL;

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

        public String getForm_id() {
            return form_id;
        }

        public void setForm_id(String form_id) {
            this.form_id = form_id;
        }

        public String getMerchanCode() {
            return merchanCode;
        }

        public void setMerchanCode(String merchanCode) {
            this.merchanCode = merchanCode;
        }

        public String getMerchantKey() {
            return merchantKey;
        }

        public void setMerchantKey(String merchantKey) {
            this.merchantKey = merchantKey;
        }

        public String getPaymentId() {
            return paymentId;
        }

        public void setPaymentId(String paymentId) {
            this.paymentId = paymentId;
        }

        public String getRefNo() {
            return refNo;
        }

        public void setRefNo(String refNo) {
            this.refNo = refNo;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getProdDesc() {
            return prodDesc;
        }

        public void setProdDesc(String prodDesc) {
            this.prodDesc = prodDesc;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserEmail() {
            return userEmail;
        }

        public void setUserEmail(String userEmail) {
            this.userEmail = userEmail;
        }

        public String getUserContact() {
            return userContact;
        }

        public void setUserContact(String userContact) {
            this.userContact = userContact;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }

        public String getResponseURL() {
            return responseURL;
        }

        public void setResponseURL(String responseURL) {
            this.responseURL = responseURL;
        }

        public String getBackendURL() {
            return backendURL;
        }

        public void setBackendURL(String backendURL) {
            this.backendURL = backendURL;
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