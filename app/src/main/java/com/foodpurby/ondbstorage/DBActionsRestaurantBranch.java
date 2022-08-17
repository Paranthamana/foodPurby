package com.foodpurby.ondbstorage;


import com.foodpurby.utillities.AppSharedValues;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by android1 on 12/28/2015.
 */
public class DBActionsRestaurantBranch {

    public static Boolean add(String restaurantBranchName, String restaurantBranchKey, String restaurantKey,
                              Double minOrderPrice, Double minOrderDeliveryPrice, String imageUrl,
                              String restaurantAddress, String restaurantCuisines, String area, String city,
                              Double avgRating, String restaurantStatus, Integer favouriteStatus, Double distanceInKm,
                              Integer deliversInMin, Integer paymentTypes, String openingTime, Boolean isPureVegetarian,
                              Integer restaurant_type, String offerText, String offerCode, Double offerMinOrderValue,
                              Double latitude, Double longitude
    ) {

        RestaurantBranch restaurantBranch = SQLConfig.restaurantBranchDao.queryBuilder().where(RestaurantBranchDao.Properties.RestaurantBranchKey.eq(restaurantBranchKey)).limit(1).unique();
        if (restaurantBranch != null) {
            restaurantBranch.setRestaurantBranchKey(restaurantBranchKey);
            restaurantBranch.setRestaurantBranchName(restaurantBranchName);
            restaurantBranch.setRestaurantKey(restaurantKey);
            restaurantBranch.setMinOrderPrice(minOrderPrice);
            restaurantBranch.setMinOrderDeliveryPrice(minOrderDeliveryPrice);
            restaurantBranch.setRestaurantImageUrl(imageUrl);
            restaurantBranch.setRestaurantAddress(restaurantAddress);
            restaurantBranch.setRestaurantCuisines(restaurantCuisines);

            restaurantBranch.setRestaurantArea(area);
            restaurantBranch.setRestaurantCity(city);
            restaurantBranch.setRestaurantAverageRating(avgRating);
            restaurantBranch.setRestaurantStatus(restaurantStatus);
            restaurantBranch.setRestaurantDistance(distanceInKm);
            restaurantBranch.setRestaurantDeliveryInMin(deliversInMin);
            restaurantBranch.setRestaurantPaymentTypes(paymentTypes);
            restaurantBranch.setRestaurantOpeningTime(openingTime);

            restaurantBranch.setRestaurantPureVegetarianStatus(isPureVegetarian);
            restaurantBranch.setRestaurantOfferText(offerText.trim());
            restaurantBranch.setRestaurantOfferCode(offerCode.trim());
            restaurantBranch.setRestaurantOfferMinOrderValue(offerMinOrderValue);

            restaurantBranch.setRestaurantLatitude(latitude);
            restaurantBranch.setRestaurantLongitude(longitude);

            if (favouriteStatus.equals(1)) {
                restaurantBranch.setRestaurantFavouriteStatus(true);
            } else {
                restaurantBranch.setRestaurantFavouriteStatus(false);
            }
            SQLConfig.restaurantBranchDao.update(restaurantBranch);
        } else {
            RestaurantBranch restaurantBranchIs = new RestaurantBranch();
            restaurantBranchIs.setRestaurantBranchKey(restaurantBranchKey);
            restaurantBranchIs.setRestaurantBranchName(restaurantBranchName);
            restaurantBranchIs.setRestaurantKey(restaurantKey);
            restaurantBranchIs.setMinOrderPrice(minOrderPrice);
            restaurantBranchIs.setMinOrderDeliveryPrice(minOrderDeliveryPrice);
            restaurantBranchIs.setRestaurantImageUrl(imageUrl);
            restaurantBranchIs.setRestaurantAddress(restaurantAddress);
            restaurantBranchIs.setRestaurantCuisines(restaurantCuisines);
            restaurantBranchIs.setRestaurantArea(area);
            restaurantBranchIs.setRestaurantCity(city);
            restaurantBranchIs.setRestaurantAverageRating(avgRating);
            restaurantBranchIs.setRestaurantStatus(restaurantStatus);
            restaurantBranchIs.setRestaurantDistance(distanceInKm);
            restaurantBranchIs.setRestaurantDeliveryInMin(deliversInMin);
            restaurantBranchIs.setRestaurantPaymentTypes(paymentTypes);
            restaurantBranchIs.setRestaurantOpeningTime(openingTime);
            restaurantBranchIs.setRestaurantPureVegetarianStatus(isPureVegetarian);
            restaurantBranchIs.setRestaurantOfferText(offerText.trim());
            restaurantBranchIs.setRestaurantOfferCode(offerCode.trim());
            restaurantBranchIs.setRestaurantOfferMinOrderValue(offerMinOrderValue);

            restaurantBranchIs.setRestaurantLatitude(latitude);
            restaurantBranchIs.setRestaurantLongitude(longitude);

            if (favouriteStatus.equals(1)) {
                restaurantBranchIs.setRestaurantFavouriteStatus(true);
            } else {
                restaurantBranchIs.setRestaurantFavouriteStatus(false);
            }
            SQLConfig.restaurantBranchDao.insert(restaurantBranchIs);
        }

        return true;
    }

    public static void updateFavouriteStatus(String restaurantBranchKey, Integer favouriteStatus) {

        RestaurantBranch restaurantBranch = SQLConfig.restaurantBranchDao.queryBuilder().where(RestaurantBranchDao.Properties.RestaurantBranchKey.eq(restaurantBranchKey)).limit(1).unique();
        if (restaurantBranch != null) {
            if (favouriteStatus.equals(1)) {
                restaurantBranch.setRestaurantFavouriteStatus(true);
            } else {
                restaurantBranch.setRestaurantFavouriteStatus(false);
            }
            SQLConfig.restaurantBranchDao.update(restaurantBranch);
        }
    }

    public static List<RestaurantBranch> get() {

        List<RestaurantBranch> restaurantBranchSelected = new ArrayList<>();
        List<RestaurantBranch> restaurantBranchs = SQLConfig.restaurantBranchDao.queryBuilder().list();
        if (restaurantBranchs != null) {
            for (RestaurantBranch restaurantBranch : restaurantBranchs) {

                if (!AppSharedValues.getRestaurantSearchKey().isEmpty()) {
                    if (!restaurantBranch.getRestaurantBranchName().toLowerCase().trim().contains(AppSharedValues.getRestaurantSearchKey().trim().toLowerCase())) {
                        continue;
                    }
                }


                if (!AppSharedValues.getSelectedRestaurantType().isEmpty()) {
                    if (!restaurantBranch.getRestaurantOfferText().toLowerCase().trim().contains(AppSharedValues.getSelectedRestaurantType().trim().toLowerCase())) {
                        continue;
                    }
                }

                if (AppSharedValues.isShowFavouriteResturantOnly()) {
                    if (!restaurantBranch.getRestaurantFavouriteStatus()) {
                        continue;
                    }
                }
                if (AppSharedValues.isPureVeg()) {
                    if (restaurantBranch.getRestaurantPureVegetarianStatus()) {
                        List<String> cuisines = Arrays.asList(restaurantBranch.getRestaurantCuisines().split("\\s*,\\s*"));

                        if (AppSharedValues.getFilterSelectedCuisines().size() > 0) {
                            for (String cuisine : cuisines) {
                                if (AppSharedValues.getFilterSelectedCuisines() != null && AppSharedValues.getFilterSelectedCuisines().containsKey(cuisine)) {
                                    restaurantBranchSelected.add(restaurantBranch);
                                    break;
                                }
                            }
                        } else {
                            restaurantBranchSelected.add(restaurantBranch);
                        }
                    }
                } else if (AppSharedValues.getFilterSelectedCuisines() != null && AppSharedValues.getFilterSelectedCuisines().size() > 0) {
                    List<String> cuisines = Arrays.asList(restaurantBranch.getRestaurantCuisines().split("\\s*,\\s*"));
                    for (String cuisine : cuisines) {
                        if (AppSharedValues.getFilterSelectedCuisines() != null && AppSharedValues.getFilterSelectedCuisines().containsKey(cuisine)) {
                            restaurantBranchSelected.add(restaurantBranch);
                            break;
                        }
                    }
                } else {
                    restaurantBranchSelected.add(restaurantBranch);
                }
            }
        }
        return restaurantBranchSelected;
    }

    public static RestaurantBranch get(String restaurantBranchKey) {

        RestaurantBranch restaurantBranch = SQLConfig.restaurantBranchDao.queryBuilder().where(RestaurantBranchDao.Properties.RestaurantBranchKey.eq(restaurantBranchKey)).limit(1).unique();
        return restaurantBranch;
    }

    public static Boolean deleteAll() {
        SQLConfig.restaurantBranchDao.deleteAll();

        return true;
    }
}
