package com.foodpurby.ondbstorage;

import java.util.List;

/**
 * Created by android1 on 12/9/2015.
 */
public class DBActionsCuisine {

    public static Boolean add(String restaurantBranchKey, String restaurantKey, String cuisineKey, String cuisineName) {

        Cuisine cuisine = SQLConfig.cuisineDao.queryBuilder().where(CuisineDao.Properties.CuisineKey.eq(cuisineKey)).limit(1).unique();
        if(cuisine != null) {
            cuisine.setCuisineKey(cuisineKey);
            cuisine.setRestaurantBranchKey(restaurantBranchKey);
            cuisine.setRestaurantKey(restaurantKey);
            cuisine.setCuisineName(cuisineName);
            cuisine.setGroupKey("Grp1");
            SQLConfig.cuisineDao.update(cuisine);
        }
        else {
            Cuisine cuisineIs = new Cuisine();
            cuisineIs.setCuisineKey(cuisineKey);
            cuisineIs.setRestaurantBranchKey(restaurantBranchKey);
            cuisineIs.setRestaurantKey(restaurantKey);
            cuisineIs.setCuisineName(cuisineName);
            cuisineIs.setGroupKey("Grp1");
            SQLConfig.cuisineDao.insert(cuisineIs);
        }
        return true;
    }

    public static Boolean deleteAll() {
        SQLConfig.cuisineDao.deleteAll();
        return true;
    }

    public static List<Cuisine> getCuisines() {
        Cuisine cuisine = new Cuisine();
        return SQLConfig.cuisineDao.queryBuilder().list();
    }

    public static List<Cuisine> getCuisines(String groupKey) {
        Cuisine cuisine = new Cuisine();
        return SQLConfig.cuisineDao.queryBuilder().where(CuisineDao.Properties.GroupKey.eq(groupKey)).list();
    }

}
