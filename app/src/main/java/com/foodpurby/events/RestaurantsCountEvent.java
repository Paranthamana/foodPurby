package com.foodpurby.events;

/**
 * Created by android1 on 1/22/2016.
 */
public class RestaurantsCountEvent {
    public static int getTotalRestaurants() {
        return totalRestaurants;
    }

    public void setTotalRestaurants(int totalRestaurants) {
        this.totalRestaurants = totalRestaurants;
    }

    private static int totalRestaurants = 0;
    public RestaurantsCountEvent(int totalRestaurants) {
        this.totalRestaurants = totalRestaurants;
    }
}
