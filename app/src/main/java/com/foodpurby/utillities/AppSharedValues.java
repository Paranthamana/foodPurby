package com.foodpurby.utillities;


import com.foodpurby.model.DAOArea;
import com.foodpurby.model.DAOCMSList;
import com.foodpurby.model.DAOCity;
import com.foodpurby.model.DAORestaurantBranchItems;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by android1 on 12/17/2015.
 */
public class AppSharedValues {

    private static boolean IsShowOpenRestaurent = false;
    private static boolean IsShowVoucheraccept = false;
    private static boolean IsShowNewRestaurent = false;

    public static void setCategory(String _category) {
        category = _category;
    }

    public static String getCategory() {
        return category;
    }

    public static String getOrderNotes() {
        return orderNotes;
    }

    public static void setOrderNotes(String orderNotes) {
        AppSharedValues.orderNotes = orderNotes;
    }

    public enum DeliveryMethod {
        None,
        Collect,
        Delivery,
        Dinein,
        DeliveryToMyTable;
    }

    public enum ShopStatus {
        Open,
        Closed,
    }

    private static String category = "100";

    //arul
    public static String LoginStatus = "0";
    public static String SignupStatus = "0";
    public static String CategorySelection = "0";


    private Integer RestaurantStartTime = 540;
    private Integer RestaurantEndTime = 1415;
    private static String ClientKey = "";
    private static DAOArea.Area_list Area;
    private static DAOCity.City_list City;

    //private static String UserKey = "";
    public static String getCouponCode() {
        return CouponCode;
    }

    public static void setCouponCode(String couponCode) {
        CouponCode = couponCode;
    }

    private static String RestaurantCurrentTable = "";
    private static String CouponCode = "";
    private static String DistanceMetrics = "";
    private static String Language = "en";
    private static String RestaurantSearchKey = "";
    private static boolean IsShowFavouriteResturantOnly = false;
    private static boolean IsShowOnlinePayment = false;
    private static boolean IsShowPreOrder = false;
    private static List<DAOCMSList.Cms_list> CMSList;
    private static boolean VibrateWhileAddingToCart = true;
    public static Map<String, String> FilterSelectedCuisines = new HashMap<String, String>();
    private static boolean IsPureVeg = false;
    private static Double MinOrderDeliveryPrice = 0D;
    private static Double minOrderPrice = 0D;
    private static Double DeliveryCharge = 0D;
    private static DeliveryMethod CartDeliveryMethod = DeliveryMethod.Delivery;
    private static String CartSelectedAddressKey = "";
    private static boolean RTLEnabled = false;
    private static boolean ShowPriceWithSpace = false;
    private static boolean ShowCurrencyCodeInRight = false;
    private static String SelectedRestaurantBranchCurrencyCode;
    private static DAORestaurantBranchItems.RestaurantBranchItems SelectedRestaurantItemsFromServer;
    private static String SelectedRestaurantBranchKey;
    private static String SelectedRestaurantBranchName;
    private static ShopStatus SelectedRestaurantBranchStatus = ShopStatus.Closed;
    private static Double GrandTotalPrice = 0D;
    private static boolean DeliveryAvailability_COD = false;
    private static boolean DeliveryAvailability_ONLINE = false;
    private static boolean DeliveryType_Collect = false;
    private static boolean DeliveryType_Delivery = false;
    private static String DeliveryDateSelected;
    private static String SelectedRestaurantBranchOfferText = "";
    private static String SelectedRestaurantBranchOfferCode = "";
    private static Double SelectedRestaurantBranchOfferMinOrderValue = 0D;


    private static String SelectedRestaurantType = "";
    private static String orderNotes;

    public static String getCerfiticateImage() {
        return CerfiticateImage;
    }

    public static void setCerfiticateImage(String cerfiticateImage) {
        CerfiticateImage = cerfiticateImage;
    }

    private static String DeciveId;
    private static String CerfiticateImage;
    public static boolean isIntegrediantChecked = false;
    public static boolean isSortChoosed = false;

    public static boolean isSortChoosed() {
        return isSortChoosed;
    }

    public static void setIsSortChoosed(boolean isSortChoosed) {
        AppSharedValues.isSortChoosed = isSortChoosed;
    }

    public static boolean isIntegrediantChecked() {
        return isIntegrediantChecked;
    }

    public static void setIsIntegrediantChecked(boolean isIntegrediantChecked_) {
        isIntegrediantChecked = isIntegrediantChecked_;
    }

    public static String getDeciveId() {
        return DeciveId;
    }

    public static void setDeciveId(String deciveId) {
        DeciveId = deciveId;
    }

    public static Map<String, List<String>> getDeliveryDateTimes() {
        return DeliveryDateTimes;
    }

    public static void setDeliveryDateTimes(Map<String, List<String>> deliveryDateTimes) {
        DeliveryDateTimes = deliveryDateTimes;
    }

    public static void putDeliveryDateTimes(String deliveryDate, List<String> deliveryTimes) {
        DeliveryDateTimes.put(deliveryDate, deliveryTimes);
    }

    private static Map<String, List<String>> DeliveryDateTimes = new HashMap<String, List<String>>();

    private static String OnlinePaymentGatewayName;

    public static String getOnlinePaymentGatewayName() {
        return OnlinePaymentGatewayName;
    }

    public static void setOnlinePaymentGatewayName(String onlinePaymentGatewayName) {
        OnlinePaymentGatewayName = onlinePaymentGatewayName;
    }

    public static String getSelectedRestaurantBranchName() {
        return SelectedRestaurantBranchName;
    }

    public static void setSelectedRestaurantBranchName(String selectedRestaurantBranchName) {
        SelectedRestaurantBranchName = selectedRestaurantBranchName;
    }

    public static void setSelectedRestaurantBranchKey(String selectedRestaurantBranchKey) {
        SelectedRestaurantBranchKey = selectedRestaurantBranchKey;
    }

    public static String getSelectedRestaurantBranchCurrencyCode() {
        return SelectedRestaurantBranchCurrencyCode;
    }

    public static void setSelectedRestaurantBranchCurrencyCode(String selectedRestaurantBranchCurrencyCode) {
        SelectedRestaurantBranchCurrencyCode = selectedRestaurantBranchCurrencyCode;
    }

    public static void setShowCurrencyCodeInRight(boolean showCurrencyCodeInRight) {
        ShowCurrencyCodeInRight = showCurrencyCodeInRight;
    }

    public static boolean isShowPriceWithSpace() {
        return ShowPriceWithSpace;
    }

    public static void setShowPriceWithSpace(boolean showPriceWithSpace) {
        ShowPriceWithSpace = showPriceWithSpace;
    }

    public static boolean isShowCurrencyCodeInRight() {
        return ShowCurrencyCodeInRight;
    }

    public static boolean isRTLEnabled() {
        return RTLEnabled;
    }

    public static void setRTLEnabled(boolean RTLEnabled) {
        AppSharedValues.RTLEnabled = RTLEnabled;
    }

    public static DeliveryMethod getCartDeliveryMethod() {
        return CartDeliveryMethod;
    }

    public static void setCartDeliveryMethod(DeliveryMethod cartDeliveryMethod) {
        CartDeliveryMethod = cartDeliveryMethod;
    }

    public Integer getRestaurantStartTime() {
        return RestaurantStartTime;
    }

    public void setRestaurantStartTime(Integer restaurantStartTime) {
        RestaurantStartTime = restaurantStartTime;
    }

    public Integer getRestaurantEndTime() {
        return RestaurantEndTime;
    }

    public void setRestaurantEndTime(Integer restaurantEndTime) {
        RestaurantEndTime = restaurantEndTime;
    }

    public static Double getDeliveryCharge() {
        return DeliveryCharge;
    }

    public static void setDeliveryCharge(Double deliveryCharge) {
        DeliveryCharge = deliveryCharge;
    }

    public static Double getMinOrderDeliveryPrice() {
        return MinOrderDeliveryPrice;
    }

    public static void setMinOrderDeliveryPrice(Double minOrderDeliveryPrice) {
        MinOrderDeliveryPrice = minOrderDeliveryPrice;
    }

    public static Double getMinOrderPrice() {
        return minOrderPrice;
    }

    public static void setMinOrderPrice(Double minOrderPrice) {
        AppSharedValues.minOrderPrice = minOrderPrice;
    }

    public static Double getGrandTotalPrice() {
        return GrandTotalPrice;
    }

    public static void setGrandTotalPrice(Double grandTotalPrice) {
        GrandTotalPrice = grandTotalPrice;
    }

    public static String getSelectedRestaurantBranchKey() {
        return SelectedRestaurantBranchKey;
    }

    public static String getCartSelectedAddressKey() {
        return CartSelectedAddressKey;
    }

    public static void setCartSelectedAddressKey(String cartSelectedAddressKey) {
        CartSelectedAddressKey = cartSelectedAddressKey;
    }


    public static boolean isDeliveryAvailability_COD() {
        return DeliveryAvailability_COD;
    }

    public static void setDeliveryAvailability_COD(boolean deliveryAvailability_COD) {
        DeliveryAvailability_COD = deliveryAvailability_COD;
    }

    public static boolean isDeliveryAvailability_ONLINE() {
        return DeliveryAvailability_ONLINE;
    }

    public static void setDeliveryAvailability_ONLINE(boolean deliveryAvailability_ONLINE) {
        DeliveryAvailability_ONLINE = deliveryAvailability_ONLINE;
    }

    public static boolean isDeliveryType_Collect() {
        return DeliveryType_Collect;
    }

    public static void setDeliveryType_Collect(boolean deliveryType_Collect) {
        DeliveryType_Collect = deliveryType_Collect;
    }

    public static boolean isDeliveryType_Delivery() {
        return DeliveryType_Delivery;
    }

    public static void setDeliveryType_Delivery(boolean deliveryType_Delivery) {
        DeliveryType_Delivery = deliveryType_Delivery;
    }

    public static String getDeliveryDateSelected() {
        return DeliveryDateSelected;
    }

    public static void setDeliveryDateSelected(String deliveryDateSelected) {
        DeliveryDateSelected = deliveryDateSelected;
    }

    public static String getDistanceMetrics() {
        return DistanceMetrics;
    }

    public static void setDistanceMetrics(String distanceMetrics) {
        DistanceMetrics = distanceMetrics;
    }

    public static ShopStatus getSelectedRestaurantBranchStatus() {
        return SelectedRestaurantBranchStatus;
    }

    public static void setSelectedRestaurantBranchStatus(ShopStatus selectedRestaurantBranchStatus) {
        SelectedRestaurantBranchStatus = selectedRestaurantBranchStatus;
    }

    public static DAOCity.City_list getCity() {
        return City;
    }

    public static void setCity(DAOCity.City_list city) {
        City = city;
    }

    public static DAOArea.Area_list getArea() {
        return Area;
    }

    public static void setArea(DAOArea.Area_list area) {
        Area = area;
    }

    public static Map<String, String> getFilterSelectedCuisines() {
        return FilterSelectedCuisines;
    }

    public static void setFilterSelectedCuisines(Map<String, String> filterSelectedCuisines) {
        FilterSelectedCuisines = filterSelectedCuisines;
    }

    public static boolean isPureVeg() {
        return IsPureVeg;
    }

    public static void setIsPureVeg(boolean isPureVeg) {
        IsPureVeg = isPureVeg;
    }

    public static boolean isShowFavouriteResturantOnly() {
        return IsShowFavouriteResturantOnly;
    }

    public static void setIsShowFavouriteResturantOnly(boolean isShowFavouriteResturantOnly) {
        IsShowFavouriteResturantOnly = isShowFavouriteResturantOnly;
    }

    public static boolean isShowOnlinePayment() {
        return IsShowOnlinePayment;
    }

    public static void setIsShowOnlinePayment(boolean isShowOnlinePayment) {
        IsShowOnlinePayment = isShowOnlinePayment;
    }

    public static boolean isShowPreOrder() {
        return IsShowPreOrder;
    }

    public static void setIsShowPreOrder(boolean isShowPreOrder) {
        IsShowPreOrder = isShowPreOrder;
    }

    public static boolean isShowOpenRestaurent() {
        return IsShowOpenRestaurent;
    }

    public static void setIsShowOpenRestaurent(boolean isShowOpenRestaurent) {
        IsShowOpenRestaurent = isShowOpenRestaurent;
    }

    public static boolean isShowVoucheraccept() {
        return IsShowVoucheraccept;
    }

    public static void setIsShowVoucheraccept(boolean isShowVoucheraccept) {
        IsShowVoucheraccept = isShowVoucheraccept;
    }

    public static boolean isShowNewRestaurent() {
        return IsShowNewRestaurent;
    }

    public static void setIsShowNewRestaurent(boolean isShowNewRestaurent) {
        IsShowNewRestaurent = isShowNewRestaurent;
    }

    public static String getLanguage() {
        return Language;
    }

    public static void setLanguage(String language) {
        Language = language;
    }

    public static String getSelectedRestaurantType() {
        return SelectedRestaurantType;
    }

    public static void setSelectedRestaurantType(String selectedRestaurantBranchOfferCode) {
        SelectedRestaurantType = selectedRestaurantBranchOfferCode;
    }


    public static String getSelectedRestaurantBranchOfferCode() {
        return SelectedRestaurantBranchOfferCode;
    }

    public static void setSelectedRestaurantBranchOfferCode(String selectedRestaurantBranchOfferCode) {
        SelectedRestaurantBranchOfferCode = selectedRestaurantBranchOfferCode;
    }

    public static String getSelectedRestaurantBranchOfferText() {
        return SelectedRestaurantBranchOfferText;
    }

    public static void setSelectedRestaurantBranchOfferText(String selectedRestaurantBranchOfferText) {
        SelectedRestaurantBranchOfferText = selectedRestaurantBranchOfferText;
    }

    public static Double getSelectedRestaurantBranchOfferMinOrderValue() {
        return SelectedRestaurantBranchOfferMinOrderValue;
    }

    public static void setSelectedRestaurantBranchOfferMinOrderValue(Double selectedRestaurantBranchOfferMinOrderValue) {
        SelectedRestaurantBranchOfferMinOrderValue = selectedRestaurantBranchOfferMinOrderValue;
    }

    public static String getRestaurantSearchKey() {
        return RestaurantSearchKey;
    }

    public static void setRestaurantSearchKey(String restaurantSearchKey) {
        RestaurantSearchKey = restaurantSearchKey;
    }

    public static String getRestaurantCurrentTable() {
        return RestaurantCurrentTable;
    }

    public static void setRestaurantCurrentTable(String restaurantCurrentTable) {
        RestaurantCurrentTable = restaurantCurrentTable;
    }

    public static boolean isVibrateWhileAddingToCart() {
        return VibrateWhileAddingToCart;
    }

    public static void setVibrateWhileAddingToCart(boolean vibrateWhileAddingToCart) {
        VibrateWhileAddingToCart = vibrateWhileAddingToCart;
    }

    public static List<DAOCMSList.Cms_list> getCMSList() {
        return CMSList;
    }

    public static void setCMSList(List<DAOCMSList.Cms_list> CMSList) {
        AppSharedValues.CMSList = CMSList;
    }

    public static String getClientKey() {
        return ClientKey;
    }

    public static void setClientKey(String clientKey) {
        ClientKey = clientKey;
    }

    public static DAORestaurantBranchItems.RestaurantBranchItems getSelectedRestaurantItemsFromServer() {
        return SelectedRestaurantItemsFromServer;
    }

    public static void setSelectedRestaurantItemsFromServer(DAORestaurantBranchItems.RestaurantBranchItems selectedRestaurantItemsFromServer) {
        SelectedRestaurantItemsFromServer = selectedRestaurantItemsFromServer;
    }
}
