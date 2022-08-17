package com.foodpurby.ondbstorage;

/**
 * Created by android1 on 12/29/2015.
 */
public class DBActionsUser {

    public static User get() {
        return SQLConfig.userDao.queryBuilder().limit(1).unique();
    }

    public static void delete() {
        SQLConfig.userDao.deleteAll();
    }

    public static Boolean add(String customerKey, String userKey, String userEMail, String userFirstName, String userLastName, String userMobile, String userToken) {

        User user = SQLConfig.userDao.queryBuilder().where(UserDao.Properties.UserKey.eq(userKey)).limit(1).unique();
        if(user != null) {

            user.setUserKey(userKey.trim());
            user.setUserEMail(userEMail.trim());
            user.setUserFirstName(userFirstName.trim());
            user.setUserLastName(userLastName.trim());
            user.setUserMobile(userMobile.trim());
            user.setUserToken(userToken.trim());

            SQLConfig.userDao.update(user);
        }
        else {

            User userIs = new User();
            userIs.setUserKey(userKey.trim());
            userIs.setUserEMail(userEMail.trim());
            userIs.setUserFirstName(userFirstName.trim());
            userIs.setUserLastName(userLastName.trim());
            userIs.setUserMobile(userMobile.trim());
            userIs.setUserToken(userToken.trim());

            SQLConfig.userDao.insert(userIs);
        }

        return true;
    }
}
