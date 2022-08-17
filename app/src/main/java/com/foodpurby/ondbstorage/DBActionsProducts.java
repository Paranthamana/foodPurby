package com.foodpurby.ondbstorage;

import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by android1 on 12/9/2015.
 */
public class DBActionsProducts {

    public static Boolean add(String restaurantBranchKey, String cuisineKey,
                                 String productKey, String productName, String productDesc, String productImgUrl, String categoryKey,
                                 Double price, Double priceMin, Double priceMax,
                                 Double taxAmount, Integer productType) {

        Products products = SQLConfig.productsDao.queryBuilder().where(ProductsDao.Properties.ProductKey.eq(productKey)).limit(1).unique();
        if(products != null) {
            products.setProductKey(productKey);
            products.setRestaurantBranchKey(restaurantBranchKey);
            products.setCategoryKey(categoryKey);
            products.setCuisineKey(cuisineKey);
            products.setProductName(productName);
            products.setProductDesc(productDesc);
            products.setProductImgUrl(productImgUrl);
            products.setPrice(price);
            products.setPrice_min(priceMin);
            products.setPrice_max(priceMax);
            products.setTax_amount(taxAmount);
            products.setTax_included(false);
            products.setTax_value(1.0);
            products.setActive(true);
            products.setIn_stock(true);
            products.setOrders_accepted(true);
            products.setPreparation_time(30);
            products.setPopularity(30);
            products.setSortingNumber(1);
            products.setProductType(productType);
            products.setActiveStatus(true);
            SQLConfig.productsDao.update(products);
        }
        else {
            Products productsIs = new Products();
            productsIs.setProductKey(productKey);
            productsIs.setRestaurantBranchKey(restaurantBranchKey);
            productsIs.setCategoryKey(categoryKey);
            productsIs.setCuisineKey(cuisineKey);
            productsIs.setProductName(productName);
            productsIs.setProductDesc(productDesc);
            productsIs.setProductImgUrl(productImgUrl);
            productsIs.setPrice(price);
            productsIs.setPrice_min(priceMin);
            productsIs.setPrice_max(priceMax);
            productsIs.setTax_amount(taxAmount);
            productsIs.setTax_included(false);
            productsIs.setTax_value(1.0);
            productsIs.setActive(true);
            productsIs.setIn_stock(true);
            productsIs.setOrders_accepted(true);
            productsIs.setPreparation_time(30);
            productsIs.setPopularity(30);
            productsIs.setSortingNumber(1);
            productsIs.setProductType(productType);
            productsIs.setActiveStatus(true);
            SQLConfig.productsDao.insert(productsIs);
        }
        return true;
    }

    public static List<Products> getProducts() {
        Products products = new Products();
        return SQLConfig.productsDao.queryBuilder().list();
    }

    public static List<Products> getProducts(String restaurantBranchKey, String cuisineKey) {
        Products products = new Products();
        QueryBuilder<Products> qb = SQLConfig.productsDao.queryBuilder();
        return qb.where(qb.and(ProductsDao.Properties.CuisineKey.eq(cuisineKey), ProductsDao.Properties.RestaurantBranchKey.eq(restaurantBranchKey))).list();
    }
}
