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
 * Created by Tech on 10-05-2017.
 */

public class DAOContactdetailsupload {

    private static DAOContactdetailsupload ourInstance = new DAOContactdetailsupload();

    public static DAOContactdetailsupload getInstance() {
        return ourInstance;
    }

    private DAOContactdetailsupload() {
    }

    public void Callresponse(String name, String email, String mobilenumber, String subject, String message, Callback<DAOContactdetailsupload.ContactdetailsResponse> mCallback) {
        DAOContactdetailsupload.Mycontact mGetapi = URLClassUser.getInstance().getApiBuilder().create(DAOContactdetailsupload.Mycontact.class);
        mGetapi.MyListView(name,
                email,
                mobilenumber,
                subject,
                message,
                mCallback);
    }

    public interface Mycontact {
        @FormUrlEncoded
        @POST("/contact_us.html?")
        public void MyListView(@Field("contact_name") String language,
                               @Field("contact_email") String firstName,
                               @Field("contact_phone") String lastName,
                               @Field("subject") String mobileNumber,
                               @Field("message") String eMail,
                               Callback<DAOContactdetailsupload.ContactdetailsResponse> response);
    }

    public class ContactdetailsResponse {

        private Integer httpcode;
        private String status;
        private String message;
        private List<Object> data = null;
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

        public List<Object> getData() {
            return data;
        }

        public void setData(List<Object> data) {
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
