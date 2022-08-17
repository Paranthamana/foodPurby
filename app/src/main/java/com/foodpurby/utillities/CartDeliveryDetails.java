package com.foodpurby.utillities;

import java.util.Date;

/**
 * Created by android1 on 12/31/2015.
 */
public class CartDeliveryDetails {

    public enum DeliveryMethod {
        None(0),
        Collect(1),
        Deliver(1),
        Dinein(3),
        DeliveryToMyTable(4);

        private final int value;

        private DeliveryMethod(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public enum PaymentMethod {
        None(0),
        Online(1),
        COD(2),
        WALLET(3);

        private final int value;

        private PaymentMethod(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    private static PaymentMethod CartPaymentMethod = PaymentMethod.None;
    private static DeliveryMethod CartDeliveryMethod = DeliveryMethod.None;
    private static Date CartDeliveryDateTime = new Date();
    private static String CartDeliveryTable = "";
    private static String CartSelectedAddressKey = "";
    private static String CartOrderKey = "";

    public static String getCartSelectedAddressKey() {
        return CartSelectedAddressKey;
    }

    public static void setCartSelectedAddressKey(String cartSelectedAddressKey) {
        CartSelectedAddressKey = cartSelectedAddressKey;
    }

    public static String getCartDeliveryTable() {
        return CartDeliveryTable;
    }

    public static void setCartDeliveryTable(String cartDeliveryTable) {
        CartDeliveryTable = cartDeliveryTable;
    }

    public static DeliveryMethod getCartDeliveryMethod() {
        return CartDeliveryMethod;
    }

    public static void setCartDeliveryMethod(DeliveryMethod cartDeliveryMethod) {
        CartDeliveryMethod = cartDeliveryMethod;
    }

    public static Date getCartDeliveryDateTime() {
        return CartDeliveryDateTime;
    }

    public static void setCartDeliveryDateTime(Date cartDeliveryDateTime) {
        CartDeliveryDateTime = cartDeliveryDateTime;
    }

    public static String getCartOrderKey() {
        return CartOrderKey;
    }

    public static void setCartOrderKey(String cartOrderKey) {
        CartOrderKey = cartOrderKey;
    }

    public static PaymentMethod getCartPaymentMethod() {
        return CartPaymentMethod;
    }

    public static void setCartPaymentMethod(PaymentMethod cartPaymentMethod) {
        CartPaymentMethod = cartPaymentMethod;
    }

}
