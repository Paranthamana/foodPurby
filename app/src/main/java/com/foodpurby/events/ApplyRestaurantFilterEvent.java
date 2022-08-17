package com.foodpurby.events;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by android1 on 1/22/2016.
 */
public class ApplyRestaurantFilterEvent {

    Boolean isPureVeg;
    public Map<String, String> selectedCuisines = new HashMap<String, String>();
    public ApplyRestaurantFilterEvent(Boolean isPureVeg, Map<String, String> selectedCuisines) {
        this.isPureVeg = isPureVeg;
        this.selectedCuisines = selectedCuisines;
    }
}
