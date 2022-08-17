package com.foodpurby.ondbstorage;

import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by android1 on 12/17/2015.
 */
public class DBActionsIngredients {

    public static Boolean deleteAll() {
        SQLConfig.ingredientsDao.deleteAll();
        return true;
    }

    public static List<Ingredients> getIngredients(Products products) {
        QueryBuilder<Ingredients> qb = SQLConfig.ingredientsDao.queryBuilder();
        List<Ingredients> ingredients = qb.where(qb.and(
                        IngredientsDao.Properties.ProductKey.eq(products.getProductKey()),
                        IngredientsDao.Properties.RestaurantBranchKey.eq(products.getRestaurantBranchKey())
                )
        ).list();

        return ingredients;
    }

    public static List<Ingredients> getIngredients(Products products, String ingredientsCategoryKey) {
        QueryBuilder<Ingredients> qb = SQLConfig.ingredientsDao.queryBuilder();
        List<Ingredients> ingredients = qb.where(qb.and(
                        IngredientsDao.Properties.ProductKey.eq(products.getProductKey()),
                        IngredientsDao.Properties.RestaurantBranchKey.eq(products.getRestaurantBranchKey()),
                        IngredientsDao.Properties.IngredientsCategoryKey.eq(ingredientsCategoryKey)
                )
        ).list();

        return ingredients;
    }

    public static Boolean add(String restaurantBranchKey, String productKey, String ingredientsKey,
                              String ingredientsName, String ingredientsCategoryKey,
                              String ingredientsTypeKey, Double price) {


        QueryBuilder<Ingredients> qb = SQLConfig.ingredientsDao.queryBuilder();
        Ingredients ingredients = qb.where(qb.and(IngredientsDao.Properties.IngredientsKey.eq(ingredientsKey), IngredientsDao.Properties.ProductKey.eq(productKey))).limit(1).unique();
        if (ingredients != null) {
            ingredients.setIngredientsKey(ingredientsKey);
            ingredients.setRestaurantBranchKey(restaurantBranchKey);
            ingredients.setProductKey(productKey);
            ingredients.setIngredientsTypeKey(ingredientsTypeKey);
            ingredients.setIngredientsCategoryKey(ingredientsCategoryKey);
            ingredients.setIngredientsName(ingredientsName);
            ingredients.setPrice(price);
            ingredients.setSortingNumber("0");
            ingredients.setActiveStatus(true);

            SQLConfig.ingredientsDao.update(ingredients);
        } else {
            Ingredients ingredientsIs = new Ingredients();
            ingredientsIs.setIngredientsKey(ingredientsKey);
            ingredientsIs.setRestaurantBranchKey(restaurantBranchKey);
            ingredientsIs.setProductKey(productKey);
            ingredientsIs.setIngredientsTypeKey(ingredientsTypeKey);
            ingredientsIs.setIngredientsCategoryKey(ingredientsCategoryKey);
            ingredientsIs.setIngredientsName(ingredientsName);
            ingredientsIs.setPrice(price);
            ingredientsIs.setSortingNumber("0");
            ingredientsIs.setActiveStatus(true);
            SQLConfig.ingredientsDao.insert(ingredientsIs);
        }
        return true;
    }

}