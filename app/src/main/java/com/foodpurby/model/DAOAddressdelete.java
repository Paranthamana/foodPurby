package com.foodpurby.model;

import com.foodpurby.util.URLClassUser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Tech on 18-05-2017.
 */

public class DAOAddressdelete {
    private static DAOAddressdelete ourInstance = new DAOAddressdelete();

    public static DAOAddressdelete getInstance() {
        return ourInstance;
    }

    private DAOAddressdelete() {
    }

    public void Callresponse(String s, String s1, String s2, Callback<Addressdelete> mCallback) {
        DAOAddressdelete.MyLists mGetapi = URLClassUser.getInstance().getApiBuilder().create(DAOAddressdelete.MyLists.class);
        mGetapi.MyListView(s, s1, s2, mCallback);
    }

    public interface MyLists {
        @GET("/delete_address_book.html?")
        public void MyListView(@Query("language") String language,
                               @Query("key") String key,
                               @Query("address_key") String address_key,
                               Callback<Addressdelete> response);
    }

    public class Addressdelete {

        private String httpcode;
        private String status;
        private String message;
        private List<Object> data = null;
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

        public List<Object> getData() {
            return data;
        }

        public void setData(List<Object> data) {
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


}
