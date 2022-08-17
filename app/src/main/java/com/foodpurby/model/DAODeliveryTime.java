package com.foodpurby.model;


import com.foodpurby.util.URLClass;
import com.foodpurby.utillities.AppSharedValues;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;

/**
 * Created by android1 on 1/4/2016.
 */
public class DAODeliveryTime {

    private static DAODeliveryTime ourInstance = new DAODeliveryTime();

    public static DAODeliveryTime getInstance() {
        return ourInstance;
    }

    private DAODeliveryTime() {
    }

    public void Callresponse(String token, String shopKey, String date, String deliveryType, Callback<DeliveryTime> mCallback) {
        DeliveryTimeLists mGetapi = URLClass.getInstance().getApiBuilder().create(DeliveryTimeLists.class);
        mGetapi.AddressListView(token, shopKey, date, deliveryType, AppSharedValues.getLanguage(), "$2y$13$O8eus5IZeW9tkpEB3.sahuVVsmZemaXTyIxqDi3ROO2uEYvRxVC72", mCallback);
    }

    public interface DeliveryTimeLists {
        @GET("/delivery_time_settings.html")
        public void AddressListView(@Header("request") String token,
                                    @Query("shop_key") String shopKey,
                                    @Query("delivery_date") String date,
                                    @Query("delivery_option") String deliveryType,
                                    @Query("languge") String languge,
                                    @Query("key") String key,
                                    Callback<DeliveryTime> response);
    }


    public class Data {

        private Delivery_pickup_details_time delivery_pickup_details_time;

        /**
         *
         * @return
         * The delivery_pickup_details_time
         */
        public Delivery_pickup_details_time getDelivery_pickup_details_time() {
            return delivery_pickup_details_time;
        }

        /**
         *
         * @param delivery_pickup_details_time
         * The delivery_pickup_details_time
         */
        public void setDelivery_pickup_details_time(Delivery_pickup_details_time delivery_pickup_details_time) {
            this.delivery_pickup_details_time = delivery_pickup_details_time;
        }

    }

    public class DeliveryTime {

        private String httpcode;
        private String status;
        private String message;
        private Data data;
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


    public class Delivery_pickup_details_time {

        private List<String> timings = new ArrayList<String>();

        /**
         *
         * @return
         * The timings
         */
        public List<String> getTimings() {
            return timings;
        }

        /**
         *
         * @param timings
         * The timings
         */
        public void setTimings(List<String> timings) {
            this.timings = timings;
        }

    }

}
