package com.foodpurby.modeltab;



import com.foodpurby.util.URLClassTab;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;

/**
 * Created by tech on 11/2/2015.
 */
public class DAORestaurantNames {

    private static DAORestaurantNames ourInstance = new DAORestaurantNames();

    public static DAORestaurantNames getInstance() {
        return ourInstance;
    }

    private DAORestaurantNames() {
    }

    public void Callresponse(String token, String clientKey, Callback<RestaurantApi> mCallback) {
        RestaurantlabelLists mGetapi = URLClassTab.getInstance().getApiBuilder().create(RestaurantlabelLists.class);
        mGetapi.RestaurantNameListView(token, clientKey, mCallback);
    }

    public interface RestaurantlabelLists {
        @GET("/Restaurantbranch_client")
        public void RestaurantNameListView(@Header("request") String token,
                                           @Query("client_key") String clientKey,
                                           Callback<RestaurantApi> response);
    }

    public class RestaurantApi {

        private List<Restaurantbranch> restaurantbranch = new ArrayList<Restaurantbranch>();

        /**
         *
         * @return
         * The restaurantbranch
         */
        public List<Restaurantbranch> getRestaurantbranch() {
            return restaurantbranch;
        }

        /**
         *
         * @param restaurantbranch
         * The restaurantbranch
         */
        public void setRestaurantbranch(List<Restaurantbranch> restaurantbranch) {
            this.restaurantbranch = restaurantbranch;
        }

    }

    public class Restaurantbranch {

        private List<Token> token = new ArrayList<Token>();
        private List<Restaurantbranch_> restaurantbranch = new ArrayList<Restaurantbranch_>();

        /**
         *
         * @return
         * The token
         */
        public List<Token> getToken() {
            return token;
        }

        /**
         *
         * @param token
         * The token
         */
        public void setToken(List<Token> token) {
            this.token = token;
        }

        /**
         *
         * @return
         * The restaurantbranch
         */
        public List<Restaurantbranch_> getRestaurantbranch() {
            return restaurantbranch;
        }

        /**
         *
         * @param restaurantbranch
         * The restaurantbranch
         */
        public void setRestaurantbranch(List<Restaurantbranch_> restaurantbranch) {
            this.restaurantbranch = restaurantbranch;
        }

    }

    public class Restaurantbranch_ {

        private String restaurantbranchkey;
        private String restaurantbranchname;
        private String restaurantbranchcontactname;
        private String restaurantbranchemailID;
        private String restaurantbranchcontactphoneno;
        private String restaurantbranchaddress1;
        private String restaurantbranchaddress2;
        private String locality;
        private Integer reviewCount;
        private Double rating;
        private Integer ontimestatus;
        private Double costrating;
        private Double costfortwo;
        private Boolean onlinepaymentavailablestatus;
        private Double vatPercent;
        private Boolean vatEnabledStatus;
        private Boolean vatIncludedInProductPrice;
        private Double servicetaxpercent;
        private Boolean servicetaxenabledstatus;
        private Boolean servicetaxvisiblestatus;
        private Boolean checkoutcommentenabled;
        private Double packagingcharges;
        private Double minorderamount;
        private Integer mindeliverytime;
        private Integer minimumpickuptime;
        private Double deliverycharges;
        private Boolean deliveryenabledStatus;

        /**
         *
         * @return
         * The restaurantbranchkey
         */
        public String getRestaurantbranchkey() {
            return restaurantbranchkey;
        }

        /**
         *
         * @param restaurantbranchkey
         * The restaurantbranchkey
         */
        public void setRestaurantbranchkey(String restaurantbranchkey) {
            this.restaurantbranchkey = restaurantbranchkey;
        }

        /**
         *
         * @return
         * The restaurantbranchname
         */
        public String getRestaurantbranchname() {
            return restaurantbranchname;
        }

        /**
         *
         * @param restaurantbranchname
         * The restaurantbranchname
         */
        public void setRestaurantbranchname(String restaurantbranchname) {
            this.restaurantbranchname = restaurantbranchname;
        }

        /**
         *
         * @return
         * The restaurantbranchcontactname
         */
        public String getRestaurantbranchcontactname() {
            return restaurantbranchcontactname;
        }

        /**
         *
         * @param restaurantbranchcontactname
         * The restaurantbranchcontactname
         */
        public void setRestaurantbranchcontactname(String restaurantbranchcontactname) {
            this.restaurantbranchcontactname = restaurantbranchcontactname;
        }

        /**
         *
         * @return
         * The restaurantbranchemailID
         */
        public String getRestaurantbranchemailID() {
            return restaurantbranchemailID;
        }

        /**
         *
         * @param restaurantbranchemailID
         * The restaurantbranchemailID
         */
        public void setRestaurantbranchemailID(String restaurantbranchemailID) {
            this.restaurantbranchemailID = restaurantbranchemailID;
        }

        /**
         *
         * @return
         * The restaurantbranchcontactphoneno
         */
        public String getRestaurantbranchcontactphoneno() {
            return restaurantbranchcontactphoneno;
        }

        /**
         *
         * @param restaurantbranchcontactphoneno
         * The restaurantbranchcontactphoneno
         */
        public void setRestaurantbranchcontactphoneno(String restaurantbranchcontactphoneno) {
            this.restaurantbranchcontactphoneno = restaurantbranchcontactphoneno;
        }

        /**
         *
         * @return
         * The restaurantbranchaddress1
         */
        public String getRestaurantbranchaddress1() {
            return restaurantbranchaddress1;
        }

        /**
         *
         * @param restaurantbranchaddress1
         * The restaurantbranchaddress1
         */
        public void setRestaurantbranchaddress1(String restaurantbranchaddress1) {
            this.restaurantbranchaddress1 = restaurantbranchaddress1;
        }

        /**
         *
         * @return
         * The restaurantbranchaddress2
         */
        public String getRestaurantbranchaddress2() {
            return restaurantbranchaddress2;
        }

        /**
         *
         * @param restaurantbranchaddress2
         * The restaurantbranchaddress2
         */
        public void setRestaurantbranchaddress2(String restaurantbranchaddress2) {
            this.restaurantbranchaddress2 = restaurantbranchaddress2;
        }

        /**
         *
         * @return
         * The locality
         */
        public String getLocality() {
            return locality;
        }

        /**
         *
         * @param locality
         * The locality
         */
        public void setLocality(String locality) {
            this.locality = locality;
        }

        /**
         *
         * @return
         * The reviewCount
         */
        public Integer getReviewCount() {
            return reviewCount;
        }

        /**
         *
         * @param reviewCount
         * The reviewCount
         */
        public void setReviewCount(Integer reviewCount) {
            this.reviewCount = reviewCount;
        }

        /**
         *
         * @return
         * The rating
         */
        public Double getRating() {
            return rating;
        }

        /**
         *
         * @param rating
         * The rating
         */
        public void setRating(Double rating) {
            this.rating = rating;
        }

        /**
         *
         * @return
         * The ontimestatus
         */
        public Integer getOntimestatus() {
            return ontimestatus;
        }

        /**
         *
         * @param ontimestatus
         * The ontimestatus
         */
        public void setOntimestatus(Integer ontimestatus) {
            this.ontimestatus = ontimestatus;
        }

        /**
         *
         * @return
         * The costrating
         */
        public Double getCostrating() {
            return costrating;
        }

        /**
         *
         * @param costrating
         * The costrating
         */
        public void setCostrating(Double costrating) {
            this.costrating = costrating;
        }

        /**
         *
         * @return
         * The costfortwo
         */
        public Double getCostfortwo() {
            return costfortwo;
        }

        /**
         *
         * @param costfortwo
         * The costfortwo
         */
        public void setCostfortwo(Double costfortwo) {
            this.costfortwo = costfortwo;
        }

        /**
         *
         * @return
         * The onlinepaymentavailablestatus
         */
        public Boolean getOnlinepaymentavailablestatus() {
            return onlinepaymentavailablestatus;
        }

        /**
         *
         * @param onlinepaymentavailablestatus
         * The onlinepaymentavailablestatus
         */
        public void setOnlinepaymentavailablestatus(Boolean onlinepaymentavailablestatus) {
            this.onlinepaymentavailablestatus = onlinepaymentavailablestatus;
        }

        /**
         *
         * @return
         * The vatPercent
         */
        public Double getVatPercent() {
            return vatPercent;
        }

        /**
         *
         * @param vatPercent
         * The vatPercent
         */
        public void setVatPercent(Double vatPercent) {
            this.vatPercent = vatPercent;
        }

        /**
         *
         * @return
         * The vatEnabledStatus
         */
        public Boolean getVatEnabledStatus() {
            return vatEnabledStatus;
        }

        /**
         *
         * @param vatEnabledStatus
         * The vatEnabledStatus
         */
        public void setVatEnabledStatus(Boolean vatEnabledStatus) {
            this.vatEnabledStatus = vatEnabledStatus;
        }

        /**
         *
         * @return
         * The vatIncludedInProductPrice
         */
        public Boolean getVatIncludedInProductPrice() {
            return vatIncludedInProductPrice;
        }

        /**
         *
         * @param vatIncludedInProductPrice
         * The vatIncludedInProductPrice
         */
        public void setVatIncludedInProductPrice(Boolean vatIncludedInProductPrice) {
            this.vatIncludedInProductPrice = vatIncludedInProductPrice;
        }

        /**
         *
         * @return
         * The servicetaxpercent
         */
        public Double getServicetaxpercent() {
            return servicetaxpercent;
        }

        /**
         *
         * @param servicetaxpercent
         * The servicetaxpercent
         */
        public void setServicetaxpercent(Double servicetaxpercent) {
            this.servicetaxpercent = servicetaxpercent;
        }

        /**
         *
         * @return
         * The servicetaxenabledstatus
         */
        public Boolean getServicetaxenabledstatus() {
            return servicetaxenabledstatus;
        }

        /**
         *
         * @param servicetaxenabledstatus
         * The servicetaxenabledstatus
         */
        public void setServicetaxenabledstatus(Boolean servicetaxenabledstatus) {
            this.servicetaxenabledstatus = servicetaxenabledstatus;
        }

        /**
         *
         * @return
         * The servicetaxvisiblestatus
         */
        public Boolean getServicetaxvisiblestatus() {
            return servicetaxvisiblestatus;
        }

        /**
         *
         * @param servicetaxvisiblestatus
         * The servicetaxvisiblestatus
         */
        public void setServicetaxvisiblestatus(Boolean servicetaxvisiblestatus) {
            this.servicetaxvisiblestatus = servicetaxvisiblestatus;
        }

        /**
         *
         * @return
         * The checkoutcommentenabled
         */
        public Boolean getCheckoutcommentenabled() {
            return checkoutcommentenabled;
        }

        /**
         *
         * @param checkoutcommentenabled
         * The checkoutcommentenabled
         */
        public void setCheckoutcommentenabled(Boolean checkoutcommentenabled) {
            this.checkoutcommentenabled = checkoutcommentenabled;
        }

        /**
         *
         * @return
         * The packagingcharges
         */
        public Double getPackagingcharges() {
            return packagingcharges;
        }

        /**
         *
         * @param packagingcharges
         * The packagingcharges
         */
        public void setPackagingcharges(Double packagingcharges) {
            this.packagingcharges = packagingcharges;
        }

        /**
         *
         * @return
         * The minorderamount
         */
        public Double getMinorderamount() {
            return minorderamount;
        }

        /**
         *
         * @param minorderamount
         * The minorderamount
         */
        public void setMinorderamount(Double minorderamount) {
            this.minorderamount = minorderamount;
        }

        /**
         *
         * @return
         * The mindeliverytime
         */
        public Integer getMindeliverytime() {
            return mindeliverytime;
        }

        /**
         *
         * @param mindeliverytime
         * The mindeliverytime
         */
        public void setMindeliverytime(Integer mindeliverytime) {
            this.mindeliverytime = mindeliverytime;
        }

        /**
         *
         * @return
         * The minimumpickuptime
         */
        public Integer getMinimumpickuptime() {
            return minimumpickuptime;
        }

        /**
         *
         * @param minimumpickuptime
         * The minimumpickuptime
         */
        public void setMinimumpickuptime(Integer minimumpickuptime) {
            this.minimumpickuptime = minimumpickuptime;
        }

        /**
         *
         * @return
         * The deliverycharges
         */
        public Double getDeliverycharges() {
            return deliverycharges;
        }

        /**
         *
         * @param deliverycharges
         * The deliverycharges
         */
        public void setDeliverycharges(Double deliverycharges) {
            this.deliverycharges = deliverycharges;
        }

        /**
         *
         * @return
         * The deliveryenabledStatus
         */
        public Boolean getDeliveryenabledStatus() {
            return deliveryenabledStatus;
        }

        /**
         *
         * @param deliveryenabledStatus
         * The deliveryenabledStatus
         */
        public void setDeliveryenabledStatus(Boolean deliveryenabledStatus) {
            this.deliveryenabledStatus = deliveryenabledStatus;
        }

    }

    public class Token {

        private Integer start_index;
        private String more;
        private Integer valid_until;
        private Integer page_num;
        private Integer page_size;
        private String status_code;
        private String status_messages;

        /**
         *
         * @return
         * The start_index
         */
        public Integer getStart_index() {
            return start_index;
        }

        /**
         *
         * @param start_index
         * The start_index
         */
        public void setStart_index(Integer start_index) {
            this.start_index = start_index;
        }

        /**
         *
         * @return
         * The more
         */
        public String getMore() {
            return more;
        }

        /**
         *
         * @param more
         * The more
         */
        public void setMore(String more) {
            this.more = more;
        }

        /**
         *
         * @return
         * The valid_until
         */
        public Integer getValid_until() {
            return valid_until;
        }

        /**
         *
         * @param valid_until
         * The valid_until
         */
        public void setValid_until(Integer valid_until) {
            this.valid_until = valid_until;
        }

        /**
         *
         * @return
         * The page_num
         */
        public Integer getPage_num() {
            return page_num;
        }

        /**
         *
         * @param page_num
         * The page_num
         */
        public void setPage_num(Integer page_num) {
            this.page_num = page_num;
        }

        /**
         *
         * @return
         * The page_size
         */
        public Integer getPage_size() {
            return page_size;
        }

        /**
         *
         * @param page_size
         * The page_size
         */
        public void setPage_size(Integer page_size) {
            this.page_size = page_size;
        }

        /**
         *
         * @return
         * The status_code
         */
        public String getStatus_code() {
            return status_code;
        }

        /**
         *
         * @param status_code
         * The status_code
         */
        public void setStatus_code(String status_code) {
            this.status_code = status_code;
        }

        /**
         *
         * @return
         * The status_messages
         */
        public String getStatus_messages() {
            return status_messages;
        }

        /**
         *
         * @param status_messages
         * The status_messages
         */
        public void setStatus_messages(String status_messages) {
            this.status_messages = status_messages;
        }

}
        }