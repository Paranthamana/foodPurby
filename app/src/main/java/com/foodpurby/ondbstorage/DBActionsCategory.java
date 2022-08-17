package com.foodpurby.ondbstorage;

import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by android1 on 12/9/2015.
 */
public class DBActionsCategory {

    public static Boolean deleteAll() {
        SQLConfig.categoryDao.deleteAll();
        return true;
    }

    public static Boolean add(String restaurantKey, String restaurantBranchKey, String categoryKey, String categoryName) {

        Category category = SQLConfig.categoryDao.queryBuilder().where(CategoryDao.Properties.CategoryKey.eq(categoryKey)).limit(1).unique();
        if(category != null) {
            category.setRestaurantKey(restaurantKey);
            category.setRestaurantBranchKey(restaurantBranchKey);
            category.setParentCategoryKey("");
            category.setCategoryKey(categoryKey);
            category.setCategoryName(categoryName);
            category.setSortingNumber(1);
            category.setActiveStatus(true);
            SQLConfig.categoryDao.update(category);
        }
        else {
            Category categoryIs = new Category();
            categoryIs.setRestaurantKey(restaurantKey);
            categoryIs.setRestaurantBranchKey(restaurantBranchKey);
            categoryIs.setParentCategoryKey("");
            categoryIs.setCategoryKey(categoryKey);
            categoryIs.setCategoryName(categoryName);
            categoryIs.setSortingNumber(1);
            categoryIs.setActiveStatus(true);
            SQLConfig.categoryDao.insert(categoryIs);
        }
        return true;
    }

    public static List<Category> getCategories() {
        Category category = new Category();
        return SQLConfig.categoryDao.queryBuilder().list();
    }

    public static Category getCategory(String categoryKey) {
        Category category = new Category();
        QueryBuilder<Category> qb = SQLConfig.categoryDao.queryBuilder();
        return qb.where(CategoryDao.Properties.CategoryKey.eq(categoryKey)).limit(1).unique();
    }
}
