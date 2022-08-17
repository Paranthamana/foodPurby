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
public class DAODeliveryDate {

    private static DAODeliveryDate ourInstance = new DAODeliveryDate();

    public static DAODeliveryDate getInstance() {
        return ourInstance;
    }

    private DAODeliveryDate() {
    }

    public void Callresponse(String token, String shopKey, Callback<DeliveryDate> mCallback) {
        DeliveryDateLists mGetapi = URLClass.getInstance().getApiBuilder().create(DeliveryDateLists.class);
        mGetapi.AddressListView(token, shopKey, AppSharedValues.getLanguage(), "$2y$13$O8eus5IZeW9tkpEB3.sahuVVsmZemaXTyIxqDi3ROO2uEYvRxVC72", mCallback);
    }

    public interface DeliveryDateLists {
        @GET("/delivery_date_settings.html")
        public void AddressListView(@Header("request") String token,
                                    @Query("shop_key") String shopKey,
                                    @Query("languge") String languge,
                                    @Query("key") String key,
                                    Callback<DeliveryDate> response);
    }

    public class Data {

        private Delivery_pickup_details_date delivery_pickup_details_date;

        /**
         *
         * @return
         * The delivery_pickup_details_date
         */
        public Delivery_pickup_details_date getDelivery_pickup_details_date() {
            return delivery_pickup_details_date;
        }

        /**
         *
         * @param delivery_pickup_details_date
         * The delivery_pickup_details_date
         */
        public void setDelivery_pickup_details_date(Delivery_pickup_details_date delivery_pickup_details_date) {
            this.delivery_pickup_details_date = delivery_pickup_details_date;
        }

    }

    public class DeliveryDate {

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

    public class Delivery_pickup_details_date {

        private List<String> dates = new ArrayList<String>();

        /**
         *
         * @return
         * The dates
         */
        public List<String> getDates() {
            return dates;
        }

        /**
         *
         * @param dates
         * The dates
         */
        public void setDates(List<String> dates) {
            this.dates = dates;
        }

    }
}
