package com.foodpurby.model;

import com.foodpurby.util.URLClassUser;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by Developer on 01-Jun-17.
 */

public class DAOUserResendOtp {
    private static DAOUserResendOtp ourInstance = new DAOUserResendOtp();

    public static DAOUserResendOtp getInstance() {
        return ourInstance;
    }

    private DAOUserResendOtp() {
    }

    public void Callresponse(String mCustomerkey, Callback<ResendOtp> mCallback) {
        DAOUserResendOtp.MyLists mGetapi = URLClassUser.getInstance().getApiBuilder().create(DAOUserResendOtp.MyLists.class);
        mGetapi.MyListView(mCustomerkey, mCallback);
    }

    public interface MyLists {
        @FormUrlEncoded
        @POST("/resend_otp_verfication.html?env=dev")
        public void MyListView(@Field("tmp_customer_key") String mCustomerkey,
                               Callback<ResendOtp> response);
    }

    public class ResendOtp {

        private Integer httpcode;
        private String message;
        private Integer otp;

        public Integer getHttpcode() {
            return httpcode;
        }

        public void setHttpcode(Integer httpcode) {
            this.httpcode = httpcode;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Integer getOtp() {
            return otp;
        }

        public void setOtp(Integer otp) {
            this.otp = otp;
        }
    }
}
