package com.foodpurby.ondbstorage;

import java.util.List;

/**
 * Created by android1 on 12/29/2015.
 */
public class DBActionsUserAddress {

    public static void deleteAll() {
        SQLConfig.userAddressDao.deleteAll();
    }

    public static UserAddress getAddress(String userAddressKey) {
        return SQLConfig.userAddressDao.queryBuilder().where(UserAddressDao.Properties.UserAddressKey.eq(userAddressKey)).limit(1).unique();
    }

    public static void deleteSingleItem(String userAddressKey) {

        SQLConfig.userAddressDao.delete(getAddress(userAddressKey));

    }


    public static List<UserAddress> getAddresses() {
        return SQLConfig.userAddressDao.queryBuilder().list();
    }

    public static Boolean add(String userAddressKey, String companyName, String flatNo, String locationName,
                              String postalCode, String landmark, String deliveryInstrutions,
                              String cityKey,String cityName, String areaKey,String areaName, String annotation, String city) {

        UserAddress userAddress = SQLConfig.userAddressDao.queryBuilder().where(UserAddressDao.Properties.UserAddressKey.eq(userAddressKey)).limit(1).unique();
        if(userAddress != null) {

            userAddress.setUserAddressKey(userAddressKey);
            userAddress.setDefaultStatus(true);

            userAddress.setCompanyName(companyName);
            userAddress.setFlatNo(flatNo);
            userAddress.setLocationName(locationName);
            userAddress.setPostalCode(postalCode);
            userAddress.setLandmark(landmark);
            userAddress.setDeliveryInstrutions(deliveryInstrutions);
            userAddress.setCityKey(cityKey);
            userAddress.setCityName(cityName);
            userAddress.setAreaKey(areaKey);
            userAddress.setAreaName(areaName);
            userAddress.setAnnotation(annotation);
            userAddress.setDeliveryValid(true);
            userAddress.setDeleteStatus(false);
            userAddress.setActiveStatus(true);

            SQLConfig.userAddressDao.update(userAddress);

        }
        else {

            UserAddress userAddressIs = new UserAddress();
            userAddressIs.setUserAddressKey(userAddressKey);
            userAddressIs.setDefaultStatus(true);




            userAddressIs.setCompanyName(companyName);
            userAddressIs.setFlatNo(flatNo);
            userAddressIs.setLocationName(locationName);
            userAddressIs.setPostalCode(postalCode);
            userAddressIs.setLandmark(landmark);
            userAddressIs.setDeliveryInstrutions(deliveryInstrutions);
            userAddressIs.setCityKey(cityKey);
            userAddressIs.setCityName(cityName);
            userAddressIs.setAreaKey(areaKey);
            userAddressIs.setAreaName(areaName);
            userAddressIs.setAnnotation(annotation);

            userAddressIs.setDeliveryValid(true);
            userAddressIs.setDeleteStatus(false);
            userAddressIs.setActiveStatus(true);
            SQLConfig.userAddressDao.insert(userAddressIs);
        }

        return true;
    }
}
