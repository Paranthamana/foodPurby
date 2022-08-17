package com.foodpurby.model;

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

public class DAOCreditUpdate {
    private static DAOCreditUpdate ourInstance = new DAOCreditUpdate();

    public static DAOCreditUpdate getInstance() {
        return ourInstance;
    }

    public DAOCreditUpdate() {
    }

    public void Callresponse(String Language,
                             String mKey,
                             String mMerchantCode,
                             String mPaymentId,
                             String mRefNo,
                             String mAmount,
                             String mCurrency,
                             String mRemark,
                             String mTransId,
                             String mAuthCode,
                             String mStatus,
                             String mErrDesc,
                             String mSignature,
                             String mCreditCardName,
                             String mCustomerKey, Callback<Tabaoco_credit_Response> mCallback1) {
        DAOCreditUpdate.SubscribeNowInterface mGetcreditapi = URLClassWallet.getInstance().getApiBuilder().create(DAOCreditUpdate.SubscribeNowInterface.class);
        mGetcreditapi.SubscribeNowcredit(Language, mKey, mMerchantCode, mPaymentId, mRefNo, mAmount, mCurrency, mRemark, mTransId, mAuthCode, mStatus, mErrDesc, mSignature, mCreditCardName, mCustomerKey, mCallback1);
    }

    public interface SubscribeNowInterface {
        @FormUrlEncoded
        @POST("/ipay88_wallet_response.html?")
        public void SubscribeNowcredit(@Query("Language") String Language,
                                       @Query("key") String mKey,
                                       @Field("MerchantCode") String mMerchantCode,
                                       @Field("PaymentId") String mPaymentId,
                                       @Field("RefNo") String mRefNo,
                                       @Field("Amount") String mAmount,
                                       @Field("Currency") String mCurrency,
                                       @Field("Remark") String mRemark,
                                       @Field("TransId") String mTransId,
                                       @Field("AuthCode") String mAuthCode,
                                       @Field("Status") String mStatus,
                                       @Field("ErrDesc") String mErrDesc,
                                       @Field("Signature") String mSignature,
                                       @Field("CCName") String mCreditCardName,
                                       @Field("customer_key") String mCustomerKey,
                                       Callback<Tabaoco_credit_Response> response);
    }

    public class Tabaoco_credit_Response {

        private String httpcode;
        private String status;
        private String message;
        private String data;
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

        public String getData() {
            return data;
        }

        public void setData(String data) {
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
