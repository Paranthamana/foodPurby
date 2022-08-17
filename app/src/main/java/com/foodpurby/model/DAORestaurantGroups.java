package com.foodpurby.model;


import com.foodpurby.util.URLClass;
import com.foodpurby.utillities.AppSharedValues;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;

/**
 * Created by android1 on 12/28/2015.
 */
public class DAORestaurantGroups {
    private static DAORestaurantGroups ourInstance = new DAORestaurantGroups();

    public static DAORestaurantGroups getInstance() {
        return ourInstance;
    }

    private DAORestaurantGroups() {

    }

    public void Callresponse(String token, Callback<RestaurantGroupsApi> mCallback) {
        RestaurantGroupsLists mGetapi = URLClass.getInstance().getApiBuilder().create(RestaurantGroupsLists.class);
        mGetapi.RestaurantGroupsListView(token, "1", AppSharedValues.getSelectedRestaurantBranchKey(), mCallback);
    }

    public interface RestaurantGroupsLists {
        @GET("/restaurantgroup")
        public void RestaurantGroupsListView(@Header("request") String token, @Query("pageno") String pageNo, @Query("restaurantbranch_key") String restaurantbranch_key, Callback<RestaurantGroupsApi> response);
    }


    public class RestaurantGroupsApi {
        private List<Restaurantgroup> groups = new ArrayList<Restaurantgroup>();
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        /**
         * @return The restaurants
         */
        public List<Restaurantgroup> getGroups() {
            return groups;
        }

        /**
         * @param groups The groups
         */
        public void setGroups(List<Restaurantgroup> groups) {
            this.groups = groups;
        }

        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

    }

    public class Restaurantgroup {

        private String restaurantkey;
        private String restaurantbranchkey;
        private String groupkey;
        private String groupname;
        private String sortingnumber;
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        /**
         *
         * @return
         * The restaurantkey
         */
        public String getRestaurantkey() {
            return restaurantkey;
        }

        /**
         *
         * @param restaurantkey
         * The restaurantkey
         */
        public void setRestaurantkey(String restaurantkey) {
            this.restaurantkey = restaurantkey;
        }

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
         * The groupkey
         */
        public String getGroupkey() {
            return groupkey;
        }

        /**
         *
         * @param groupkey
         * The groupkey
         */
        public void setGroupkey(String groupkey) {
            this.groupkey = groupkey;
        }

        /**
         *
         * @return
         * The groupname
         */
        public String getGroupname() {
            return groupname;
        }

        /**
         *
         * @param groupname
         * The groupname
         */
        public void setGroupname(String groupname) {
            this.groupname = groupname;
        }

        /**
         *
         * @return
         * The sortingnumber
         */
        public String getSortingnumber() {
            return sortingnumber;
        }

        /**
         *
         * @param sortingnumber
         * The sortingnumber
         */
        public void setSortingnumber(String sortingnumber) {
            this.sortingnumber = sortingnumber;
        }

        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

    }

}
