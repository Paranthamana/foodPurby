package com.foodpurby.model;

import com.foodpurby.util.URLClassCategory;
import com.foodpurby.utillities.AppSharedValues;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by android1 on 4/6/2016.
 */
public class DAOCategory {
    private static DAOCategory ourInstance = new DAOCategory();

    public static DAOCategory getInstance() {
        return ourInstance;
    }

    private DAOCategory() {
    }

    public void Callresponse(Callback<ResponseCategory> mCallback) {
        MyLists mGetapi = URLClassCategory.getInstance().getApiBuilder().create(MyLists.class);
        mGetapi.MyListView(AppSharedValues.getLanguage(), mCallback);
    }

    public interface MyLists {
        @GET("/category_list.html?key=$2y$13$O8eus5IZeW9tkpEB3.sahuVVsmZemaXTyIxqDi3ROO2uEYvRxVC72")
        public void MyListView(@Query("language") String language,
                               Callback<ResponseCategory> response);
    }

    public class ResponseCategory {
        private Integer httpcode;
        private String status;
        private String message;
        private List<Data> data = new ArrayList<Data>();
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        /**
         *
         * @return
         * The httpcode
         */
        public Integer getHttpcode() {
            return httpcode;
        }

        /**
         *
         * @param httpcode
         * The httpcode
         */
        public void setHttpcode(Integer httpcode) {
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
        public List<Data> getData() {
            return data;
        }

        /**
         *
         * @param data
         * The data
         */
        public void setData(List<Data> data) {
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

        private String category_id;
        private String category_key;
        private String category_name;
        private String category_image;
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        /**
         *
         * @return
         * The category_id
         */
        public String getCategory_id() {
            return category_id;
        }

        /**
         *
         * @param category_id
         * The category_id
         */
        public void setCategory_id(String category_id) {
            this.category_id = category_id;
        }

        /**
         *
         * @return
         * The category_key
         */
        public String getCategory_key() {
            return category_key;
        }

        /**
         *
         * @param category_key
         * The category_key
         */
        public void setCategory_key(String category_key) {
            this.category_key = category_key;
        }

        /**
         *
         * @return
         * The category_name
         */
        public String getCategory_name() {
            return category_name;
        }

        /**
         *
         * @param category_name
         * The category_name
         */
        public void setCategory_name(String category_name) {
            this.category_name = category_name;
        }

        /**
         *
         * @return
         * The category_image
         */
        public String getCategory_image() {
            return category_image;
        }

        /**
         *
         * @param category_image
         * The category_image
         */
        public void setCategory_image(String category_image) {
            this.category_image = category_image;
        }

        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

    }

    public class Category {

        private String category_id;
        private String category_name;
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        /**
         * @return The category_id
         */
        public String getCategory_id() {
            return category_id;
        }

        /**
         * @param category_id The category_id
         */
        public void setCategory_id(String category_id) {
            this.category_id = category_id;
        }

        /**
         * @return The category_name
         */
        public String getCategory_name() {
            return category_name;
        }

        /**
         * @param category_name The category_name
         */
        public void setCategory_name(String category_name) {
            this.category_name = category_name;
        }

        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

    }
}
