package com.foodpurby.model;


import com.foodpurby.util.URLClass;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by android1 on 1/8/2016.
 */
public class DAORestaurantNewReview {

    private static DAORestaurantNewReview ourInstance = new DAORestaurantNewReview();

    public static DAORestaurantNewReview getInstance() {
        return ourInstance;
    }

    private DAORestaurantNewReview() {
    }

    public void Callresponse(String token,String language, String restaurantKey, String userKey, Integer rating,
                             String reviewTitle, String reviewDesc,
                             Callback<RestaurantReview> mCallback) {
        RestaurantlabelLists mGetapi = URLClass.getInstance().getApiBuilder().create(RestaurantlabelLists.class);
        mGetapi.RestaurantNameListView(token,language, restaurantKey, userKey, rating,
                reviewTitle, reviewDesc, mCallback);
    }

    public interface RestaurantlabelLists {
        @FormUrlEncoded
        @POST("/writereview.html?key=$2y$13$O8eus5IZeW9tkpEB3.sahuVVsmZemaXTyIxqDi3ROO2uEYvRxVC72")
        public void RestaurantNameListView(@Header("token") String token,
                                           @Query("language") String language,
                                           @Field("shop") String restaurantKey,
                                           @Field("customer_key") String userKey,
                                           @Field("rating") Integer rating,
                                           @Field("review_title") String reviewTitle,
                                           @Field("review_text") String reviewDesc,
                                          /* @Field("review_text") String reviewDesc,*/
                                           Callback<RestaurantReview> response);
    }

    public class RestaurantReview {

        private String httpcode;
        private String status;
        private String message;
        private List<Object> data = new ArrayList<Object>();
        private String responsetime;

        /**
         *
         * @return
         * The httpcode
         */
        public String getHttpcode() {
            return httpcode;
        }

        /**
         *
         * @param httpcode
         * The httpcode
         */
        public void setHttpcode(String httpcode) {
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
        public List<Object> getData() {
            return data;
        }

        /**
         *
         * @param data
         * The data
         */
        public void setData(List<Object> data) {
            this.data = data;
        }

        /**
         *
         * @return
         * The responsetime
         */
        public String getResponsetime() {
            return responsetime;
        }

        /**
         *
         * @param responsetime
         * The responsetime
         */
        public void setResponsetime(String responsetime) {
            this.responsetime = responsetime;
        }

    }
}
