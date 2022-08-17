package com.foodpurby.ondbstorage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by android1 on 12/9/2015.
 */
public class SQLConfig {
    public static DaoMaster.DevOpenHelper helper;
    public static SQLiteDatabase db;
    public static DaoMaster daoMaster;
    public static DaoSession daoSession;

    public static CuisineFilterDao cuisineFilterDao;
    public static RestaurantBranchDao restaurantBranchDao;
    public static GroupDao groupDao;
    public static CuisineDao cuisineDao;
    public static CategoryDao categoryDao;
    public static ProductsDao productsDao;
    public static IngredientsCategoryDao ingredientsCategoryDao;
    public static IngredientsDao ingredientsDao;

    public static CartProductsDao cartProductsDao;
    public static CartProductsIngredientsDao cartProductsIngredientsDao;

    public static UserAddressDao userAddressDao;
    public static UserDao userDao;
    public static AppSettingsDao appSettingsDao;

    public SQLConfig(Context context) {

        try {
            helper = new DaoMaster.DevOpenHelper(context, "ontabee", null);
            db = helper.getWritableDatabase();
            daoMaster = new DaoMaster(db);
            daoSession = daoMaster.newSession();

            cuisineFilterDao = daoSession.getCuisineFilterDao();
            restaurantBranchDao = daoSession.getRestaurantBranchDao();
            groupDao = daoSession.getGroupDao();
            cuisineDao = daoSession.getCuisineDao();
            categoryDao = daoSession.getCategoryDao();
            productsDao = daoSession.getProductsDao();
            ingredientsCategoryDao = daoSession.getIngredientsCategoryDao();
            ingredientsDao = daoSession.getIngredientsDao();

            cartProductsDao = daoSession.getCartProductsDao();
            cartProductsIngredientsDao = daoSession.getCartProductsIngredientsDao();

            userAddressDao = daoSession.getUserAddressDao();
            userDao = daoSession.getUserDao();
            appSettingsDao = daoSession.getAppSettingsDao();
        }
        catch (Exception e) {
            e.printStackTrace();
            //Crittercism.logHandledException(e);
        }
    }
}
