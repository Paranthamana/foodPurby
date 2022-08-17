package com.foodpurby.api;

import com.foodpurby.util.URLClassUser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by android1 on 1/4/2016.
 */
public class TransactionApi {

    private static TransactionApi ourInstance = new TransactionApi();

    public static TransactionApi getInstance() {
        return ourInstance;
    }

    private TransactionApi() {
    }

    public void Callresponse(String language, String ckey, Callback<ResponseTransaction> mCallback) {
        TransactionApis mGetapi = URLClassUser.getInstance().getApiBuilder().create(TransactionApis.class);
        mGetapi.Transaction(language, ckey, mCallback);
    }

    public interface TransactionApis {
        @FormUrlEncoded
        @POST("/mytranscation.html?")
        public void Transaction(
                @Query("language") String language,
                @Field("ckey") String ckey,
                Callback<ResponseTransaction> response);
    }

    public class ResponseTransaction {

        private Integer httpcode;
        private String status;
        private String message;
        private List<Datum> data = null;
        private String responsetime;
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        public Integer getHttpcode() {
            return httpcode;
        }

        public void setHttpcode(Integer httpcode) {
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

        public List<Datum> getData() {
            return data;
        }

        public void setData(List<Datum> data) {
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


    public class Datum {

        private String date_formatted;
        private String transaction_key;
        private Integer wallet_amount;
        private String first_name;
        private String last_name;
        private String email;
        private String mobile_number;
        private String tracking_id;
        private String bank_ref_no;
        private String message;
        private String transaction_status;
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        public String getDate_formatted() {
            return date_formatted;
        }

        public void setDate_formatted(String date_formatted) {
            this.date_formatted = date_formatted;
        }

        public String getTransaction_key() {
            return transaction_key;
        }

        public void setTransaction_key(String transaction_key) {
            this.transaction_key = transaction_key;
        }

        public Integer getWallet_amount() {
            return wallet_amount;
        }

        public void setWallet_amount(Integer wallet_amount) {
            this.wallet_amount = wallet_amount;
        }

        public String getFirst_name() {
            return first_name;
        }

        public void setFirst_name(String first_name) {
            this.first_name = first_name;
        }

        public String getLast_name() {
            return last_name;
        }

        public void setLast_name(String last_name) {
            this.last_name = last_name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getMobile_number() {
            return mobile_number;
        }

        public void setMobile_number(String mobile_number) {
            this.mobile_number = mobile_number;
        }

        public String getTracking_id() {
            return tracking_id;
        }

        public void setTracking_id(String tracking_id) {
            this.tracking_id = tracking_id;
        }

        public String getBank_ref_no() {
            return bank_ref_no;
        }

        public void setBank_ref_no(String bank_ref_no) {
            this.bank_ref_no = bank_ref_no;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getTransaction_status() {
            return transaction_status;
        }

        public void setTransaction_status(String transaction_status) {
            this.transaction_status = transaction_status;
        }

        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

    }
}
