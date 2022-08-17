package com.foodpurby.utillities;


import com.foodpurby.ondbstorage.DBActionsUser;

/**
 * Created by android1 on 1/7/2016.
 */
public class UserDetails {

    private static String CustomerKey = "";
    private static String CustomerEmail = "";
    private static String CustomerName = "";
    private static String CustomerLastName = "";
    private static String CustomerMobile = "";
    private static String AccessToken = "";
    private static String imageUrl = "";
    private static String customer_dob = "";
    private static String customer_gender = "";

    public static String getImageUrl() {
        return imageUrl;
    }

    public static void setImageUrl(String imageUrl) {
        UserDetails.imageUrl = imageUrl;
    }


    public static void clearAll() {
        setCustomerKey("");
        setCustomerEmail("");
        setCustomerName("");
        setCustomerLastName("");
        setCustomerMobile("");
        setAccessToken("");

        UserDetails.setCustomerKey("");

        setImageUrl("");

        DBActionsUser.delete();
    }

    public static String getCustomerKey() {
        return CustomerKey;
    }

    public static void setCustomerKey(String customerKey) {
        //UserDetails.setCustomerKey(customerKey);
        CustomerKey = customerKey;
    }

    public static String getCustomerEmail() {
        return CustomerEmail;
    }

    public static void setCustomerEmail(String customerEmail) {
        CustomerEmail = customerEmail;
    }

    public static String getCustomerName() {
        return CustomerName;
    }

    public static void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public static String getCustomerLastName() {
        return CustomerLastName;
    }

    public static void setCustomerLastName(String customerLastName) {
        CustomerLastName = customerLastName;
    }

    public static String getCustomerMobile() {
        return CustomerMobile;
    }

    public static void setCustomerMobile(String customerMobile) {
        CustomerMobile = customerMobile;
    }

    public static String getAccessToken() {
        return AccessToken;
    }

    public static void setAccessToken(String accessToken) {
        AccessToken = accessToken;
    }


    public static String getCustomer_dob() {
        return customer_dob;
    }

    public static void setCustomer_dob(String customer_dob) {
        UserDetails.customer_dob = customer_dob;
    }

    public static String getCustomer_gender() {
        return customer_gender;
    }

    public static void setCustomer_gender(String customer_gender) {
        UserDetails.customer_gender = customer_gender;
    }
}
