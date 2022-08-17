package com.foodpurby.model;

import com.foodpurby.util.URLClassUser;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import retrofit.http.Query;


/**
 * Created by Developer on 26-May-17.
 */

public class DAORestFavApi {
    private static DAORestFavApi ourInstance = new DAORestFavApi();

    public static DAORestFavApi getInstance() {
        return ourInstance;
    }

    private DAORestFavApi() {
    }

    public void Callresponse(String customerKey, String language, Callback<DAORestFavApi.FavList> mCallback) {
        DAORestFavApi.UserFavouriteResLists mGetapi = URLClassUser.getInstance().getApiBuilder().create(UserFavouriteResLists.class);
        mGetapi.MyListView(customerKey, language, mCallback);
    }

    public interface UserFavouriteResLists {
        @FormUrlEncoded
        @POST("/favouritelist.html?")
        public void MyListView(
                @Query("language") String language,
                @Field("customer_key") String customerKey,
                Callback<DAORestFavApi.FavList> response);
    }

    public class FavList {

        private String httpcode;
        private String status;
        private String message;
        private List<Datum> data = null;

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

        public List<Datum> getData() {
            return data;
        }

        public void setData(List<Datum> data) {
            this.data = data;
        }

    }

    public class Datum {

        private String favourite_count;
        private String vendor_key;
        private String vendor_name;
        private String vendor_slug;
        private String vendor_logo_path;
        private String cuisines;
        private String total_rating;
        private String avg_rating;

        public String getFavourite_count() {
            return favourite_count;
        }

        public void setFavourite_count(String favourite_count) {
            this.favourite_count = favourite_count;
        }

        public String getVendor_key() {
            return vendor_key;
        }

        public void setVendor_key(String vendor_key) {
            this.vendor_key = vendor_key;
        }

        public String getVendor_name() {
            return vendor_name;
        }

        public void setVendor_name(String vendor_name) {
            this.vendor_name = vendor_name;
        }

        public String getVendor_slug() {
            return vendor_slug;
        }

        public void setVendor_slug(String vendor_slug) {
            this.vendor_slug = vendor_slug;
        }

        public String getVendor_logo_path() {
            return vendor_logo_path;
        }

        public void setVendor_logo_path(String vendor_logo_path) {
            this.vendor_logo_path = vendor_logo_path;
        }

        public String getCuisines() {
            return cuisines;
        }

        public void setCuisines(String cuisines) {
            this.cuisines = cuisines;
        }

        public String getTotal_rating() {
            return total_rating;
        }

        public void setTotal_rating(String total_rating) {
            this.total_rating = total_rating;
        }

        public String getAvg_rating() {
            return avg_rating;
        }

        public void setAvg_rating(String avg_rating) {
            this.avg_rating = avg_rating;
        }

    }
}
