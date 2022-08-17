package com.foodpurby.ondbstorage;

import java.util.List;

/**
 * Created by android1 on 12/9/2015.
 */
public class DBActionsCuisineFilter {

    public static Boolean add(String cuisineKey, String cuisineName) {

        CuisineFilter cuisineFilter = SQLConfig.cuisineFilterDao.queryBuilder().where(CuisineFilterDao.Properties.CuisineKey.eq(cuisineKey)).limit(1).unique();
        if(cuisineFilter != null) {
            cuisineFilter.setCuisineKey(cuisineKey);
            cuisineFilter.setCuisineName(cuisineName);
            SQLConfig.cuisineFilterDao.update(cuisineFilter);
        }
        else {
            CuisineFilter cuisineFilterIs = new CuisineFilter();
            cuisineFilterIs.setCuisineKey(cuisineKey);
            cuisineFilterIs.setCuisineName(cuisineName);
            SQLConfig.cuisineFilterDao.insert(cuisineFilterIs);
        }
        return true;
    }

    public static Boolean deleteAll() {
        SQLConfig.cuisineFilterDao.deleteAll();
        return true;
    }

    public static List<CuisineFilter> getCuisines() {
        Cuisine cuisine = new Cuisine();
        return SQLConfig.cuisineFilterDao.queryBuilder().list();
    }

}
