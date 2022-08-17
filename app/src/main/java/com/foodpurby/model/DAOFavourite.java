package com.foodpurby.model;


import com.foodpurby.util.URLClassUser;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by android1 on 1/4/2016.
 */
public class DAOFavourite {

    private static DAOFavourite ourInstance = new DAOFavourite();

    public static DAOFavourite getInstance() {
        return ourInstance;
    }

    private DAOFavourite() {
    }

    public void Callresponse(String customerKey, String language, String restaurantBranchKey, Callback<UserFavourite> mCallback) {
        UserFavouriteLists mGetapi = URLClassUser.getInstance().getApiBuilder().create(UserFavouriteLists.class);
        mGetapi.MyListView(customerKey, language, restaurantBranchKey, mCallback);
    }

    public interface UserFavouriteLists {

        @FormUrlEncoded
        @POST("/favourite.html")
        public void MyListView(
                @Field("customer_key") String customerKey,
                @Field("languge") String languge,
                @Field("shop_key") String restaurantBranchKey,
                Callback<UserFavourite> response);
    }

    public class Data {

        private String favourite_count;
        private Integer is_favourite;

        /**
         * @return The favourite_count
         */
        public String getFavourite_count() {
            return favourite_count;
        }

        /**
         * @param favourite_count The favourite_count
         */
        public void setFavourite_count(String favourite_count) {
            this.favourite_count = favourite_count;
        }

        /**
         * @return The is_favourite
         */
        public Integer getIs_favourite() {
            return is_favourite;
        }

        /**
         * @param is_favourite The is_favourite
         */
        public void setIs_favourite(Integer is_favourite) {
            this.is_favourite = is_favourite;
        }

    }

    public class UserFavourite {

        private String httpcode;
        private String status;
        private String message;
        private Data data;

        /**
         * @return The httpcode
         */
        public String getHttpcode() {
            return httpcode;
        }

        /**
         * @param httpcode The httpcode
         */
        public void setHttpcode(String httpcode) {
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

    }
}