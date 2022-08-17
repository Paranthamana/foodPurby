package com.foodpurby.model;


import com.foodpurby.util.URLClass;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;

/**
 * Created by android1 on 1/8/2016.
 */
public class DAORestaurantReviews {

    private static DAORestaurantReviews ourInstance = new DAORestaurantReviews();

    public static DAORestaurantReviews getInstance() {
        return ourInstance;
    }

    private DAORestaurantReviews() {
    }

    public void Callresponse(String token, String restaurantKey, String page, String key,
                             String language,
                             Callback<RestaurantReviews> mCallback) {
        RestaurantlabelLists mGetapi = URLClass.getInstance().getApiBuilder().create(RestaurantlabelLists.class);
        mGetapi.RestaurantNameListView(token, restaurantKey, page, key,
                language, mCallback);
    }

    public interface RestaurantlabelLists {

        @GET("/morereviews.html")
        public void RestaurantNameListView(@Header("request") String token,
                                           @Query("shop") String restaurantKey,
                                           @Query("page") String page,
                                           @Query("key") String key,
                                           @Query("language") String language,
                                           Callback<RestaurantReviews> response);
    }

    public class Data {

        private List<More_review> more_reviews = new ArrayList<More_review>();
        private Integer totalitems;

        /**
         *
         * @return
         * The more_reviews
         */
        public List<More_review> getMore_reviews() {
            return more_reviews;
        }

        /**
         *
         * @param more_reviews
         * The more_reviews
         */
        public void setMore_reviews(List<More_review> more_reviews) {
            this.more_reviews = more_reviews;
        }

        /**
         *
         * @return
         * The totalitems
         */
        public Integer getTotalitems() {
            return totalitems;
        }

        /**
         *
         * @param totalitems
         * The totalitems
         */
        public void setTotalitems(Integer totalitems) {
            this.totalitems = totalitems;
        }

    }

    public class More_review {

        private Integer rating;
        private String review_title;
        private String review_text;
        private String created_date;
        private String firstname;
        private String lastname;

        /**
         *
         * @return
         * The rating
         */
        public Integer getRating() {
            return rating;
        }

        /**
         *
         * @param rating
         * The rating
         */
        public void setRating(Integer rating) {
            this.rating = rating;
        }

        /**
         *
         * @return
         * The review_title
         */
        public String getReview_title() {
            return review_title;
        }

        /**
         *
         * @param review_title
         * The review_title
         */
        public void setReview_title(String review_title) {
            this.review_title = review_title;
        }

        /**
         *
         * @return
         * The review_text
         */
        public String getReview_text() {
            return review_text;
        }

        /**
         *
         * @param review_text
         * The review_text
         */
        public void setReview_text(String review_text) {
            this.review_text = review_text;
        }

        /**
         *
         * @return
         * The created_date
         */
        public String getCreated_date() {
            return created_date;
        }

        /**
         *
         * @param created_date
         * The created_date
         */
        public void setCreated_date(String created_date) {
            this.created_date = created_date;
        }

        /**
         *
         * @return
         * The firstname
         */
        public String getFirstname() {
            return firstname;
        }

        /**
         *
         * @param firstname
         * The firstname
         */
        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        /**
         *
         * @return
         * The lastname
         */
        public String getLastname() {
            return lastname;
        }

        /**
         *
         * @param lastname
         * The lastname
         */
        public void setLastname(String lastname) {
            this.lastname = lastname;
        }

    }

    public class RestaurantReviews {

        private Integer httpcode;
        private String status;
        private String message;
        private Data data;
        private String responsetime;

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
        public Data getData() {
            return data;
        }

        /**
         *
         * @param data
         * The data
         */
        public void setData(Data data) {
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
