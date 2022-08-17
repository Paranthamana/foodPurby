package com.foodpurby.baseclass;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.foodpurby.HomeActivity;
import com.foodpurby.screens.GetStartActivity;
import com.foodpurby.screens.MenuProducts;
import com.foodpurby.screens.MyAddress;
import com.foodpurby.screens.MyCart;
import com.foodpurby.screens.MyCartCalculation;
import com.foodpurby.screens.MyPaymentCOD;
import com.foodpurby.screens.MyPaymentOnline_PayU;
import com.foodpurby.screens.MyPaymentOrderConfirmed;
import com.foodpurby.screens.RestaurantFavList;
import com.foodpurby.screens.RestaurantFilter;
import com.foodpurby.screens.RestaurantProfileReview;
import com.foodpurby.screens.TabaocoCreditActivity;
import com.foodpurby.screens.UserAddressEditWithoutMap;
import com.foodpurby.screens.UserOrderDetails;
import com.foodpurby.screens.UserPassword;
import com.foodpurby.screens.UserProfile;
import com.foodpurby.screens.UserProfileEdit;
import com.foodpurby.screens.UserSignUp;
import com.foodpurby.screens.UserSigninAct;
import com.foodpurby.screens.UserSigninForgotPassword;
import com.foodpurby.map.MarkAddressWithoutMapActivity;
import com.foodpurby.screens.CityArea;
import com.foodpurby.screens.Help;
import com.foodpurby.screens.HelpBrowser;
import com.foodpurby.screens.MyPaymentMethod;
import com.foodpurby.screens.RestaurantProfile;
import com.foodpurby.screens.Settings;
import com.foodpurby.screens.UserOrder;


/**
 * Created by android1 on 12/9/2015.
 */
public class MyActivity {



    public static void DisplayGetStartActivity(Context context) {
        Intent intent = new Intent(context, GetStartActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void DisplayBaseActivity(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void DisplayPaymentOrderConfirmed(Context context, String orderKey, String orderValue, String status, String errormessage) {
        Intent intent = new Intent(context, MyPaymentOrderConfirmed.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("orderKey", orderKey);
        intent.putExtra("orderValue", orderValue);
        intent.putExtra("status", status);
        intent.putExtra("errormessage", errormessage);
        context.startActivity(intent);
    }

    public static void DisplayPaymentCOD(Context context) {
        Intent intent = new Intent(context, MyPaymentCOD.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void DisplayPaymentOnline(Context context, Bundle bundle) {
        Intent intent = new Intent(context, MyPaymentOnline_PayU.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public static void DisplayCityArea(Context context) {
        Intent intent = new Intent(context, CityArea.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void DisplayUserProfileEdit(Context context) {
        Intent intent = new Intent(context, UserProfileEdit.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void DisplayUserSignInActivity(Context context) {
        Intent intent = new Intent(context, UserSigninAct.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void DisplayUserSigninForgotPassword(Context context) {
        Intent intent = new Intent(context, UserSigninForgotPassword.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

    }

    public static void DisplayUserSignup(Context context) {
        Intent intent = new Intent(context, UserSignUp.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void DisplayAddressEdit(Context context, String userAddressKey) {
        Intent intent = new Intent(context, UserAddressEditWithoutMap.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("addresskey", userAddressKey);
        context.startActivity(intent);
    }

    public static void DisplaySettings(Context context) {
        Intent intent = new Intent(context, Settings.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void DisplayOrder(Context context) {
        Intent intent = new Intent(context, UserOrder.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void DisplayOrderDetails(Context context, String orderKey) {
        Intent intent = new Intent(context, UserOrderDetails.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("orderKey", orderKey);
        context.startActivity(intent);
    }

    public static void DisplayProfile(Context context) {
        Intent intent = new Intent(context, UserProfile.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void DisplayUpdatePassword(Context context) {
        Intent intent = new Intent(context, UserPassword.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void DisplayNewAddress(Context context) {
        Intent intent = new Intent(context, MarkAddressWithoutMapActivity.class);
        intent.putExtra("status", "add");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void DisplayCart(Context context) {
        Intent intent = new Intent(context, MyCart.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void DisplayMyPay(Context context) {
        Intent intent = new Intent(context, MyCartCalculation.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


    public static void DisplayMyAddress(Context context) {
        Intent intent = new Intent(context, MyAddress.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void DisplayTabaocoCredit(Context context) {
        Intent intent = new Intent(context, TabaocoCreditActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void DisplayRestaurantFavList(Context context) {
        Intent intent = new Intent(context, RestaurantFavList.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void DisplayMyPaymentMethod(Context context, String userAddressKey) {
        Intent intent = new Intent(context, MyPaymentMethod.class);
        intent.putExtra("userAddressKey", userAddressKey);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void DisplayRestaurantFilter(Context context) {
        Intent intent = new Intent(context, RestaurantFilter.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void DisplayRestaurantProfile(Context context) {
        Intent intent = new Intent(context, RestaurantProfile.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void DisplayRestaurantProfileReviews(Context context) {
        Intent intent = new Intent(context, RestaurantProfileReview.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void DisplayProducts(Context context, String restaurantBranchKey, String cuisineKey) {
        Intent intent = new Intent(context, MenuProducts.class);
        intent.putExtra("1b7ff608-ef32-4c2a-bff1-4823dd404cdf", restaurantBranchKey);
        intent.putExtra("cuisineKey", cuisineKey);
        context.startActivity(intent);
    }

    public static void DisplayHome(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    public static void DisplayHomePage(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

    }

    public static void DisplayHelp(Context context) {
        Intent intent = new Intent(context, Help.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void DisplayHelpBrowser(Context context, String url, String pageTitle) {
        Intent intent = new Intent(context, HelpBrowser.class);
        intent.putExtra("url", url);
        intent.putExtra("pagetitle", pageTitle);
        context.startActivity(intent);
    }
}
