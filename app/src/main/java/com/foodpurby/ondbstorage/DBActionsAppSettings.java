package com.foodpurby.ondbstorage;

/**
 * Created by android1 on 12/9/2015.
 */
public class DBActionsAppSettings {

    public static void deleteAll() {
        SQLConfig.appSettingsDao.deleteAll();
    }

    public static AppSettings get() {
        if (SQLConfig.appSettingsDao.queryBuilder().count() > 0) {
            return SQLConfig.appSettingsDao.queryBuilder().limit(1).uniqueOrThrow();
        } else {
            return null;
        }
    }

    public static void set(String cityKey, String cityName, String areaKey, String areaName) {
        SQLConfig.appSettingsDao.deleteAll();

        AppSettings appSettings = SQLConfig.appSettingsDao.queryBuilder().limit(1).unique();
        if (appSettings != null) {
            appSettings.setCityKey(cityKey);
            appSettings.setCityName(cityName);
            appSettings.setAreaKey(areaKey);
            appSettings.setAreaName(areaName);
            SQLConfig.appSettingsDao.update(appSettings);
        } else {
            AppSettings appSettingsIs = new AppSettings();
            appSettingsIs.setCityKey(cityKey);
            appSettingsIs.setCityName(cityName);
            appSettingsIs.setAreaKey(areaKey);
            appSettingsIs.setAreaName(areaName);
            appSettingsIs.setVibrateOnCartChange(true);
            SQLConfig.appSettingsDao.insert(appSettingsIs);
        }
    }




    public static void setVibration(Boolean vibrationState) {
        SQLConfig.appSettingsDao.deleteAll();

        AppSettings appSettings = SQLConfig.appSettingsDao.queryBuilder().limit(1).unique();
        if (appSettings != null) {
            appSettings.setVibrateOnCartChange(vibrationState);
            SQLConfig.appSettingsDao.update(appSettings);
        } else {
            AppSettings appSettingsIs = new AppSettings();
            appSettingsIs.setVibrateOnCartChange(vibrationState);
            appSettingsIs.setCityKey("");
            appSettingsIs.setCityName("");
            appSettingsIs.setAreaKey("");
            appSettingsIs.setAreaName("");
            SQLConfig.appSettingsDao.insert(appSettingsIs);
        }
    }
}