package com.foodpurby.ondbstorage;

import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by android1 on 12/18/2015.
 */
public class DBActionsIngredientsCategory {

    public static Boolean deleteAll() {
        SQLConfig.ingredientsCategoryDao.deleteAll();
        return true;
    }

    public static Boolean add(String restaurantBranchKey, String productKey,
                                 String ingredientsCategoryKey, String ingredientsCategoryName,
                                 Integer minSelection, Integer maxSelection, Integer requiredSelection, Double price) {

        QueryBuilder<IngredientsCategory> qb = SQLConfig.ingredientsCategoryDao.queryBuilder();
        IngredientsCategory ingredientsCategory = qb.where(qb.and(IngredientsCategoryDao.Properties.IngredientsCategoryKey.eq(ingredientsCategoryKey), IngredientsCategoryDao.Properties.ProductKey.eq(productKey))).limit(1).unique();


        if(ingredientsCategory != null) {

            ingredientsCategory.setIngredientsCategoryKey(ingredientsCategoryKey);
            ingredientsCategory.setRestaurantBranchKey(restaurantBranchKey);
            ingredientsCategory.setProductKey(productKey);
            ingredientsCategory.setIngredientsCategoryName(ingredientsCategoryName);
            ingredientsCategory.setMinSelection(minSelection);
            ingredientsCategory.setMaxSelection(maxSelection);
            ingredientsCategory.setRequiredSelection(requiredSelection);
            ingredientsCategory.setMandatoryStatus(true);
            ingredientsCategory.setMultiSelectStatus(true);
            ingredientsCategory.setPrice(price);
            ingredientsCategory.setSortingNumber(0);
            ingredientsCategory.setActiveStatus(true);

            SQLConfig.ingredientsCategoryDao.update(ingredientsCategory);
        }
        else {
            IngredientsCategory ingredientsCategoryIs = new IngredientsCategory();
            ingredientsCategoryIs.setIngredientsCategoryKey(ingredientsCategoryKey);
            ingredientsCategoryIs.setRestaurantBranchKey(restaurantBranchKey);
            ingredientsCategoryIs.setProductKey(productKey);
            ingredientsCategoryIs.setIngredientsCategoryName(ingredientsCategoryName);
            ingredientsCategoryIs.setMinSelection(minSelection);
            ingredientsCategoryIs.setMaxSelection(maxSelection);
            ingredientsCategoryIs.setMandatoryStatus(true);
            ingredientsCategoryIs.setMultiSelectStatus(true);
            ingredientsCategoryIs.setPrice(price);
            ingredientsCategoryIs.setSortingNumber(0);
            ingredientsCategoryIs.setActiveStatus(true);

            SQLConfig.ingredientsCategoryDao.insert(ingredientsCategoryIs);
        }

        return true;
    }

    public static List<IngredientsCategory> getIngredientsCategory(Products products) {
        QueryBuilder<IngredientsCategory> qb = SQLConfig.ingredientsCategoryDao.queryBuilder();
        List<IngredientsCategory> ingredientsCategories = qb.where(qb.and(
                        IngredientsCategoryDao.Properties.ProductKey.eq(products.getProductKey()),
                        IngredientsCategoryDao.Properties.RestaurantBranchKey.eq(products.getRestaurantBranchKey()))
                ).list();

        return ingredientsCategories;
    }
}
