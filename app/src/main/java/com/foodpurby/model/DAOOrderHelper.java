package com.foodpurby.model;


import com.foodpurby.ondbstorage.CartProducts;
import com.foodpurby.ondbstorage.CartProductsIngredients;
import com.foodpurby.ondbstorage.DBActionsCart;
import com.foodpurby.utillities.AppSharedValues;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by android1 on 1/21/2016.
 */
public class DAOOrderHelper {

    public RequestValues getCartProducts() {
        Cart_items mCart_items = new Cart_items();
        mCart_items.setShop_key(AppSharedValues.getSelectedRestaurantBranchKey());
        mCart_items.setCity_key(AppSharedValues.getCity().getCity_key());
        mCart_items.setArea_key(AppSharedValues.getArea().getArea_key());
        mCart_items.setOrder_message(AppSharedValues.getOrderNotes());
        mCart_items.setPromocode(AppSharedValues.getCouponCode());

        ArrayList<Item> mItems = new ArrayList<Item>();
        List<CartProducts> cartProducts = DBActionsCart.getItems(AppSharedValues.getSelectedRestaurantBranchKey());
        for (CartProducts cartProduct : cartProducts) {

            Item mItem = new Item();
            mItem.setItem_key(cartProduct.getProductKey());
            mItem.setQuantity(cartProduct.getTotalQuantity());

            List<CartProductsIngredients> cartProductsIngredients = DBActionsCart.getProductIngredientsAll(cartProduct.getCartProductKey());
            if (cartProductsIngredients.size() > 0) {
                mItem.setIs_ingredients(1);
                ArrayList<Integer> mIngredientID = new ArrayList<Integer>();

                for (int count = 0; count < cartProductsIngredients.size(); count++) {

                    try {
                        mIngredientID.add(Integer.parseInt(cartProductsIngredients.get(count).getIngredientsKey().trim()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                mItem.setIngredients(mIngredientID);
            }
            mItems.add(mItem);
        }
        mCart_items.setItems(mItems);

        RequestValues mRequestValues = new RequestValues();
        mRequestValues.setCart_items(mCart_items);

        return mRequestValues;
    }

    public class RequestValues {

        private Cart_items cart_items;


        public Cart_items getCart_items() {
            return cart_items;
        }

        /**
         * @param cart_items The cart_items
         */
        public void setCart_items(Cart_items cart_items) {
            this.cart_items = cart_items;
        }


    }

    public class Cart_items {

        private String shop_key;
        private String city_key;
        private String area_key;
        private String order_message;
        private String promocode;
        private ArrayList<Item> items = new ArrayList<Item>();

        public Cart_items() {
        }

        public String getOrder_message() {
            return order_message;
        }

        public void setOrder_message(String order_message) {
            this.order_message = order_message;
        }

        /**
         * @return The shop_key
         */
        public String getShop_key() {
            return shop_key;
        }

        /**
         * @param shop_key The shop_key
         */
        public void setShop_key(String shop_key) {
            this.shop_key = shop_key;
        }

        public String getPromocode() {
            return promocode;
        }

        public void setPromocode(String promocode) {
            if (promocode != "") {
                this.promocode = promocode;

            }
        }

        /**
         * @return The city_key
         */
        public String getCity_key() {
            return city_key;
        }

        /**
         * @param city_key The city_key
         */
        public void setCity_key(String city_key) {
            this.city_key = city_key;
        }

        /**
         * @return The area_key
         */
        public String getArea_key() {
            return area_key;
        }

        /**
         * @param area_key The area_key
         */
        public void setArea_key(String area_key) {
            this.area_key = area_key;
        }

        public ArrayList<Item> getItems() {
            return items;
        }

        public void setItems(ArrayList<Item> items) {
            this.items = items;
        }


    }

    public class Item {

        private String item_key;
        private Integer quantity;
        private Integer is_ingredients;
        private ArrayList<Integer> ingredients = new ArrayList<Integer>();

        /**
         * @return The item_key
         */
        public String getItem_key() {
            return item_key;
        }

        /**
         * @param item_key The item_key
         */
        public void setItem_key(String item_key) {
            this.item_key = item_key;
        }

        /**
         * @return The quantity
         */
        public Integer getQuantity() {
            return quantity;
        }

        /**
         * @param quantity The quantity
         */
        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

        /**
         * @return The is_ingredients
         */
        public Integer getIs_ingredients() {
            return is_ingredients;
        }

        /**
         * @param is_ingredients The is_ingredients
         */
        public void setIs_ingredients(Integer is_ingredients) {
            this.is_ingredients = is_ingredients;
        }

        /**
         * @return The ingredients
         */
        public ArrayList<Integer> getIngredients() {
            return ingredients;
        }

        /**
         * @param ingredients The ingredients
         */
        public void setIngredients(ArrayList<Integer> ingredients) {
            this.ingredients = ingredients;
        }


    }
}
