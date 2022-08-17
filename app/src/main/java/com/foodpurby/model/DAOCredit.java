package com.foodpurby.model;

import com.google.gson.annotations.SerializedName;
import com.foodpurby.util.URLClassWallet;

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

public class DAOCredit {
    private static DAOCredit ourInstance = new DAOCredit();

    public static DAOCredit getInstance() {
        return ourInstance;
    }

    public DAOCredit() {
    }

    public void Callresponse(String Language, String customer_key, String walletpoint, Callback<Tabaoco_credit_Response> mCallback1) {
        DAOCredit.SubscribeNowInterface mGetcreditapi = URLClassWallet.getInstance().getApiBuilder().create(DAOCredit.SubscribeNowInterface.class);
        mGetcreditapi.SubscribeNowcredit(Language, customer_key, walletpoint, mCallback1);
    }

    public interface SubscribeNowInterface {
        @FormUrlEncoded
        @POST("/wallet_points.html?")
        public void SubscribeNowcredit(@Query("Language") String Language,
                                       @Field("customer_key") String customer_key,
                                       @Field("walletpoints") String walletpoints,
                                       Callback<Tabaoco_credit_Response> response);
    }

    public class Tabaoco_credit_Response {

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

    public class Data {

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


}
