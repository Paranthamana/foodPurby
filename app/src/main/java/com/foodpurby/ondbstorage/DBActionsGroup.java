package com.foodpurby.ondbstorage;

import java.util.List;

/**
 * Created by android1 on 12/24/2015.
 */
public class DBActionsGroup {

    public static Boolean deleteAll() {
        SQLConfig.groupDao.deleteAll();
        return true;
    }

    public static Boolean add(String restaurantBranchKey, String restaurantKey, String groupKey, String groupName) {

        Group group = SQLConfig.groupDao.queryBuilder().where(GroupDao.Properties.GroupKey.eq(groupKey)).limit(1).unique();
        if(group != null) {
            group.setRestaurantBranchKey(restaurantBranchKey);
            group.setRestaurantKey(restaurantKey);
            group.setGroupKey(groupKey);
            group.setGroupName(groupName);
            group.setSortingNumber("1");
            SQLConfig.groupDao.update(group);
        }
        else {
            Group groupIs = new Group();
            groupIs.setRestaurantBranchKey(restaurantBranchKey);
            groupIs.setRestaurantKey(restaurantKey);
            groupIs.setGroupKey(groupKey);
            groupIs.setGroupName(groupName);
            groupIs.setSortingNumber("1");
            SQLConfig.groupDao.insert(groupIs);
        }

        return true;
    }

    public static List<Group> getGroups() {
        return SQLConfig.groupDao.queryBuilder().list();
    }
}
