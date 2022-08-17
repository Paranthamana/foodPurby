package com.foodpurby.model;

import com.foodpurby.api.ContactApi;
import com.foodpurby.util.URLClassDefault;
import com.foodpurby.utillities.AppSharedValues;

import java.util.HashMap;
import java.util.Map;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Tech on 10-05-2017.
 */

public class DAOContactus {

    private static DAOContactus ourInstance = new DAOContactus();

    public static DAOContactus getInstance() {
        return ourInstance;
    }

    private DAOContactus() {
    }

    public void Callresponse(Callback<DAOContactus.ContactResponse> mCallback) {
        DAOContactus.MyLists mGetapi = URLClassDefault.getInstance().getApiBuilder().create(DAOContactus.MyLists.class);
        mGetapi.MyListView(AppSharedValues.getLanguage(), mCallback);
    }

    public interface MyLists {
        @GET("/get_contact_data.html?key=$2y$13$O8eus5IZeW9tkpEB3.sahuVVsmZemaXTyIxqDi3ROO2uEYvRxVC72")
        public void MyListView(@Query("language") String language,
                               Callback<ContactResponse> response);
    }

    public class ContactResponse {

        private String httpcode;
        private String status;
        private String message;
        private ContactApi.Data data;
        private String responsetime;
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

        public ContactApi.Data getData() {
            return data;
        }

        public void setData(ContactApi.Data data) {
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


    public class Data {

        private String site_contact_name;
        private String site_locatoin;
        private String site_contact_number;
        private String site_contact_email;
        private Double site_latitude;
        private Double site_longitude;
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        public String getSite_contact_name() {
            return site_contact_name;
        }

        public void setSite_contact_name(String site_contact_name) {
            this.site_contact_name = site_contact_name;
        }

        public String getSite_locatoin() {
            return site_locatoin;
        }

        public void setSite_locatoin(String site_locatoin) {
            this.site_locatoin = site_locatoin;
        }

        public String getSite_contact_number() {
            return site_contact_number;
        }

        public void setSite_contact_number(String site_contact_number) {
            this.site_contact_number = site_contact_number;
        }

        public String getSite_contact_email() {
            return site_contact_email;
        }

        public void setSite_contact_email(String site_contact_email) {
            this.site_contact_email = site_contact_email;
        }

        public Double getSite_latitude() {
            return site_latitude;
        }

        public void setSite_latitude(Double site_latitude) {
            this.site_latitude = site_latitude;
        }

        public Double getSite_longitude() {
            return site_longitude;
        }

        public void setSite_longitude(Double site_longitude) {
            this.site_longitude = site_longitude;
        }

        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

    }


}
