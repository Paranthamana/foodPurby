package com.foodpurby.model;


import com.google.gson.annotations.SerializedName;
import com.foodpurby.util.URLClassDefault;
import com.foodpurby.utillities.AppSharedValues;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by android1 on 1/4/2016.
 */
public class DAOCMSList {

    private static DAOCMSList ourInstance = new DAOCMSList();

    public static DAOCMSList getInstance() {
        return ourInstance;
    }

    private DAOCMSList() {
    }

    public void Callresponse(Callback<ResponseCMSList> mCallback) {
        MyLists mGetapi = URLClassDefault.getInstance().getApiBuilder().create(MyLists.class);
        mGetapi.MyListView(AppSharedValues.getLanguage(), mCallback);
    }

    public interface MyLists {
        @GET("/cms_list.html")
        public void MyListView(@Query("language") String languge,
                               Callback<ResponseCMSList> response);
    }

    public class ResponseCMSList {

        private Integer httpcode;
        private String status;
        private String message;
        private Data data;
        private String responsetime;
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        /**
         * @return The httpcode
         */
        public Integer getHttpcode() {
            return httpcode;
        }

        /**
         * @param httpcode The httpcode
         */
        public void setHttpcode(Integer httpcode) {
            this.httpcode = httpcode;
        }

        /**
         * @return The status
         */
        public String getStatus() {
            return status;
        }

        /**
         * @param status The status
         */
        public void setStatus(String status) {
            this.status = status;
        }

        /**
         * @return The message
         */
        public String getMessage() {
            return message;
        }

        /**
         * @param message The message
         */
        public void setMessage(String message) {
            this.message = message;
        }

        /**
         * @return The data
         */
        public Data getData() {
            return data;
        }

        /**
         * @param data The data
         */
        public void setData(Data data) {
            this.data = data;
        }

        /**
         * @return The responsetime
         */
        public String getResponsetime() {
            return responsetime;
        }

        /**
         * @param responsetime The responsetime
         */
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
        @SerializedName("cms_list")
        private List<Cms_list> cms_list = new ArrayList<Cms_list>();
        @SerializedName("cms_page")
        private Integer totalitems;
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        /**
         * @return The cms_list
         */
        public List<Cms_list> getCms_list() {
            return cms_list;
        }

        /**
         * @param cms_list The cms-list
         */
        public void setCms_list(List<Cms_list> cms_list) {
            this.cms_list = cms_list;
        }

        /**
         * @return The totalitems
         */
        public Integer getTotalitems() {
            return totalitems;
        }

        /**
         * @param totalitems The totalitems
         */
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

    public class Cms_list {

        private String page_url;
        private String page_name;
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        /**
         * @return The page_url
         */
        public String getPage_url() {
            return page_url;
        }

        /**
         * @param page_url The page_url
         */
        public void setPage_url(String page_url) {
            this.page_url = page_url;
        }

        /**
         * @return The page_name
         */
        public String getPage_name() {
            return page_name;
        }

        /**
         * @param page_name The page_name
         */
        public void setPage_name(String page_name) {
            this.page_name = page_name;
        }

        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

    }
}
