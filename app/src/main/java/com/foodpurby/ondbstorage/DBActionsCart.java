package com.foodpurby.ondbstorage;


import com.foodpurby.utillities.AppConfig;

import java.util.List;
import java.util.UUID;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by android1 on 12/9/2015.
 */
public class DBActionsCart {

    public static void deleteAll(String restaurantBranchKey) {
        List<CartProducts> cartProducts = SQLConfig.cartProductsDao.queryBuilder().where(CartProductsDao.Properties.RestaurantBranchKey.eq(restaurantBranchKey)).list();
        for (CartProducts cartProduct : cartProducts) {
            SQLConfig.cartProductsDao.delete(cartProduct);
        }

        List<CartProductsIngredients> cartProductsIngredients = SQLConfig.cartProductsIngredientsDao.queryBuilder().where(CartProductsIngredientsDao.Properties.RestaurantBranchKey.eq(restaurantBranchKey)).list();
        for (CartProductsIngredients cartProductsIngredient : cartProductsIngredients) {
            SQLConfig.cartProductsIngredientsDao.delete(cartProductsIngredient);
        }

    }

    public static long getTotalItems(String restaurantBranchKey) {
        long cartProductCount = 0L;
        List<CartProducts> cartProducts = SQLConfig.cartProductsDao.queryBuilder().where(CartProductsDao.Properties.RestaurantBranchKey.eq(restaurantBranchKey)).list();
        for (CartProducts cartProduct : cartProducts) {
            cartProductCount = cartProductCount + (cartProduct.getTotalQuantity() == null ? 0 : cartProduct.getTotalQuantity());
        }
        return cartProductCount;
    }

    public static Double getTotalProductPrice(CartProducts cartProduct) {
        Double totalPrice = 0D;
        totalPrice = cartProduct.getTotalQuantity() * cartProduct.getPrice();
        totalPrice = Double.valueOf(AppConfig.decimalFormat.format(totalPrice));

        QueryBuilder<CartProductsIngredients> qb = SQLConfig.cartProductsIngredientsDao.queryBuilder();
        List<CartProductsIngredients> cartProductsIngredients = qb.where(CartProductsIngredientsDao.Properties.CartProductKey.eq(cartProduct.getCartProductKey())).list();
        for (CartProductsIngredients cartProductsIngredient : cartProductsIngredients) {
            totalPrice = totalPrice + (cartProduct.getTotalQuantity() * getTotalProductIngredientPrice(cartProductsIngredient));
        }

        return totalPrice;
    }

    public static Double getTotalProductIngredientPrice(CartProductsIngredients cartProductsIngredients) {
        Double totalPrice = 0D;
        totalPrice = cartProductsIngredients.getPrice();
        totalPrice = Double.valueOf(AppConfig.decimalFormat.format(totalPrice));
        return totalPrice;
    }

    public static Double getTotalCartPrice(String restaurantBranchKey) {
        Double totalPrice = 0D;
        List<CartProducts> cartProducts = SQLConfig.cartProductsDao.queryBuilder().where(CartProductsDao.Properties.RestaurantBranchKey.eq(restaurantBranchKey)).list();
        for (CartProducts cartProduct : cartProducts) {
            totalPrice = totalPrice + getTotalProductPrice(cartProduct);
        }
        totalPrice = Double.valueOf(AppConfig.decimalFormat.format(totalPrice));


        totalPrice = Double.valueOf(AppConfig.decimalFormat.format(totalPrice));
        return totalPrice;
    }

    public static Double getTotalCartPriceIncludingTax(String restaurantBranchKey) {
        Double totalPrice = 0D;
        List<CartProducts> cartProducts = SQLConfig.cartProductsDao.queryBuilder().where(CartProductsDao.Properties.RestaurantBranchKey.eq(restaurantBranchKey)).list();
        for (CartProducts cartProduct : cartProducts) {
            totalPrice = totalPrice + getTotalProductPrice(cartProduct);
        }
        totalPrice = Double.valueOf(AppConfig.decimalFormat.format(totalPrice));


        totalPrice = Double.valueOf(AppConfig.decimalFormat.format(totalPrice)) + 100.0;
        return totalPrice;
    }


    public static Boolean add(String restaurantBranchKey, String productKey, List<Ingredients> ingredients, Integer count) {

        if (!productKey.isEmpty() && count > 0) {
            boolean isUpdated = false;
            QueryBuilder<CartProducts> qb = SQLConfig.cartProductsDao.queryBuilder();
            List<CartProducts> cartProducts = qb.where(qb.and(CartProductsDao.Properties.ProductKey.eq(productKey), CartProductsDao.Properties.RestaurantBranchKey.eq(restaurantBranchKey))).list();
            for (CartProducts cartProductIs : cartProducts) {
                String cartProductKeyFromDB = cartProductIs.getCartProductKey();

                if(ingredients == null) {
                    if (cartProductIs != null) {
                        int total = cartProductIs.getTotalQuantity() == null ? 0 : cartProductIs.getTotalQuantity();
                        cartProductIs.setTotalQuantity(total + count);
                        SQLConfig.cartProductsDao.update(cartProductIs);

                        isUpdated = true;
                        break;
                    }
                }
                else if(ingredients != null) {

                    QueryBuilder<CartProductsIngredients> qbI = SQLConfig.cartProductsIngredientsDao.queryBuilder();
                    List<CartProductsIngredients> cartProductsIngredientsCount = qbI.where(
                            qbI.and(
                                    CartProductsIngredientsDao.Properties.CartProductKey.eq(cartProductKeyFromDB),
                                    CartProductsIngredientsDao.Properties.ProductKey.eq(productKey)
                            )
                    ).list();


                    if (cartProductIs != null && cartProductsIngredientsCount != null) {

                        Integer matchedIngredients = 0;
                        for(Ingredients ingredientDB : ingredients) {
                            for(CartProductsIngredients ingredientSelected : cartProductsIngredientsCount) {
                                if(ingredientSelected.getIngredientsKey().equalsIgnoreCase(ingredientDB.getIngredientsKey()))
                                {
                                    matchedIngredients++;
                                    break;
                                }
                            }
                        }

                        if(matchedIngredients == ingredients.size() && cartProductsIngredientsCount.size() == ingredients.size()) {
                            int total = cartProductIs.getTotalQuantity() == null ? 0 : cartProductIs.getTotalQuantity();
                            cartProductIs.setTotalQuantity(total + count);
                            SQLConfig.cartProductsDao.update(cartProductIs);

                            isUpdated = true;
                            break;
                        }
                    }
                }
            }

            if (!isUpdated) {

                Products products = SQLConfig.productsDao.queryBuilder().where(ProductsDao.Properties.ProductKey.eq(productKey)).limit(1).unique();
                if(products != null) {
                    String cartProductKey = UUID.randomUUID().toString();
                    CartProducts cartProductNew = new CartProducts();
                    cartProductNew.setCartProductKey(cartProductKey);
                    cartProductNew.setProductKey(productKey);
                    cartProductNew.setRestaurantBranchKey(products.getRestaurantBranchKey());
                    cartProductNew.setCategoryKey(products.getCategoryKey());
                    cartProductNew.setCuisineKey(products.getCuisineKey());
                    cartProductNew.setProductName(products.getProductName());
                    cartProductNew.setProductDesc(products.getProductName());
                    cartProductNew.setProductImgUrl(products.getProductImgUrl());
                    cartProductNew.setPrice(products.getPrice());
                    cartProductNew.setPrice_min(products.getPrice_min());
                    cartProductNew.setPrice_max(products.getPrice_max());
                    cartProductNew.setTax_amount(products.getTax_amount());
                    cartProductNew.setTax_included(products.getTax_included());
                    cartProductNew.setTax_value(products.getTax_value());
                    cartProductNew.setActive(products.getActive());
                    cartProductNew.setIn_stock(products.getIn_stock());
                    cartProductNew.setOrders_accepted(products.getOrders_accepted());
                    cartProductNew.setPreparation_time(products.getPreparation_time());
                    cartProductNew.setPopularity(products.getPopularity());
                    cartProductNew.setSortingNumber(products.getSortingNumber());
                    cartProductNew.setActiveStatus(products.getActiveStatus());
                    cartProductNew.setTotalQuantity(count);

                    cartProductNew.setRestaurantBranchGroupKey("");

                    QueryBuilder<CartProducts> qbCount = SQLConfig.cartProductsDao.queryBuilder();
                    Integer cartProductsTotal = qbCount.where(CartProductsDao.Properties.RestaurantBranchKey.eq(restaurantBranchKey)).list().size() + 1;
                    cartProductNew.setSortingNumber(cartProductsTotal);

                    SQLConfig.cartProductsDao.insert(cartProductNew);

                    if (ingredients != null) {
                        for (Ingredients ingredient : ingredients) {
                            CartProductsIngredients cartProductsIngredient = new CartProductsIngredients();
                            cartProductsIngredient.setCartProductKey(cartProductKey);
                            cartProductsIngredient.setIngredientsKey(ingredient.getIngredientsKey());
                            cartProductsIngredient.setRestaurantBranchKey(ingredient.getRestaurantBranchKey());
                            cartProductsIngredient.setProductKey(productKey);
                            cartProductsIngredient.setIngredientsTypeKey(ingredient.getIngredientsTypeKey());
                            cartProductsIngredient.setIngredientsName(ingredient.getIngredientsName());
                            cartProductsIngredient.setPrice(ingredient.getPrice());
                            cartProductsIngredient.setSortingNumber("");
                            cartProductsIngredient.setActiveStatus(ingredient.getActiveStatus());

                            SQLConfig.cartProductsIngredientsDao.insert(cartProductsIngredient);
                        }
                    }
                }
            }

            return true;
        } else {
            return false;
        }
    }

    public static Integer getItemCartCount(String restaurantBranchKey, String productKey, List<Ingredients> ingredients) {

        Integer total = 0;
        if (!productKey.isEmpty()) {

            boolean isUpdated = false;

            QueryBuilder<CartProducts> qb = SQLConfig.cartProductsDao.queryBuilder();
            List<CartProducts> cartProducts = qb.where(qb.and(CartProductsDao.Properties.ProductKey.eq(productKey), CartProductsDao.Properties.RestaurantBranchKey.eq(restaurantBranchKey))).list();
            for (CartProducts cartProductIs : cartProducts) {
                String cartProductKeyFromDB = cartProductIs.getCartProductKey();

                if(ingredients == null) {
                    if (cartProductIs != null) {
                        total = cartProductIs.getTotalQuantity() == null ? 0 : cartProductIs.getTotalQuantity();
                        isUpdated = true;
                        break;
                    }
                }
                else if(ingredients != null) {

                    QueryBuilder<CartProductsIngredients> qbI = SQLConfig.cartProductsIngredientsDao.queryBuilder();
                    List<CartProductsIngredients> cartProductsIngredientsCount = qbI.where(
                            qbI.and(
                                    CartProductsIngredientsDao.Properties.CartProductKey.eq(cartProductKeyFromDB),
                                    CartProductsIngredientsDao.Properties.ProductKey.eq(productKey)
                            )
                    ).list();


                    if (cartProductIs != null && cartProductsIngredientsCount != null) {

                        Integer matchedIngredients = 0;
                        for(Ingredients ingredientDB : ingredients) {
                            for(CartProductsIngredients ingredientSelected : cartProductsIngredientsCount) {
                                if(ingredientSelected.getIngredientsKey().equalsIgnoreCase(ingredientDB.getIngredientsKey()))
                                {
                                    matchedIngredients++;
                                    break;
                                }
                            }
                        }

                        if(matchedIngredients == ingredients.size() && cartProductsIngredientsCount.size() == ingredients.size()) {
                            total = cartProductIs.getTotalQuantity() == null ? 0 : cartProductIs.getTotalQuantity();
                            isUpdated = true;
                            break;
                        }
                    }
                }
            }

            return total;
        } else {
            return total;
        }
    }

    public static Boolean reOrder(String restaurantBranchKey, Integer oldSortingOrder, Integer newSortingOrder) {
        if (!restaurantBranchKey.isEmpty()) {

            QueryBuilder<CartProducts> qb = SQLConfig.cartProductsDao.queryBuilder();
            CartProducts cartProductOld = qb.where(qb.and(CartProductsDao.Properties.RestaurantBranchKey.eq(restaurantBranchKey), CartProductsDao.Properties.SortingNumber.eq(newSortingOrder))).limit(1).unique();

            qb = SQLConfig.cartProductsDao.queryBuilder();
            CartProducts cartProductNew = qb.where(qb.and(CartProductsDao.Properties.RestaurantBranchKey.eq(restaurantBranchKey), CartProductsDao.Properties.SortingNumber.eq(oldSortingOrder))).limit(1).unique();

            if (cartProductOld != null) {
                cartProductOld.setSortingNumber(oldSortingOrder);
                SQLConfig.cartProductsDao.update(cartProductOld);
            }
            if (cartProductNew != null) {
                cartProductNew.setSortingNumber(newSortingOrder);
                SQLConfig.cartProductsDao.update(cartProductNew);
            }

            return true;
        } else {
            return false;
        }
    }

    public static Boolean addFromCart(String cartProductKey, Integer count) {
        if (!cartProductKey.isEmpty() && count > 0) {

            CartProducts cartProduct = SQLConfig.cartProductsDao.queryBuilder().where(CartProductsDao.Properties.CartProductKey.eq(cartProductKey)).limit(1).unique();
            if (cartProduct != null) {
                int total = cartProduct.getTotalQuantity() == null ? 0 : cartProduct.getTotalQuantity();
                cartProduct.setTotalQuantity(total + count);
                SQLConfig.cartProductsDao.update(cartProduct);
            }

            return true;
        } else {
            return false;
        }
    }

    public static Boolean remove(String cartProductKey, Integer count) {
        if (!cartProductKey.isEmpty() && count > 0) {

            CartProducts cartProduct = SQLConfig.cartProductsDao.queryBuilder().where(CartProductsDao.Properties.CartProductKey.eq(cartProductKey)).limit(1).unique();
            if (cartProduct != null) {
                int total = cartProduct.getTotalQuantity() == null ? 0 : cartProduct.getTotalQuantity() - 1;
                if (total <= 0) {

                    //Delete product ingredients
                    QueryBuilder<CartProductsIngredients> qb = SQLConfig.cartProductsIngredientsDao.queryBuilder();
                    List<CartProductsIngredients> cartProductsIngredients = qb.where(CartProductsIngredientsDao.Properties.CartProductKey.eq(cartProductKey)).list();
                    for (CartProductsIngredients cartProductsIngredient : cartProductsIngredients) {
                        SQLConfig.cartProductsIngredientsDao.delete(cartProductsIngredient);
                    }

                    SQLConfig.cartProductsDao.delete(cartProduct);

                } else {
                    cartProduct.setTotalQuantity(total);
                    SQLConfig.cartProductsDao.update(cartProduct);
                }
            }

            return true;
        } else if(count == 0) {

            CartProducts cartProduct = SQLConfig.cartProductsDao.queryBuilder().where(CartProductsDao.Properties.CartProductKey.eq(cartProductKey)).limit(1).unique();
            //Delete product ingredients
            QueryBuilder<CartProductsIngredients> qb = SQLConfig.cartProductsIngredientsDao.queryBuilder();
            List<CartProductsIngredients> cartProductsIngredients = qb.where(CartProductsIngredientsDao.Properties.CartProductKey.eq(cartProductKey)).list();
            for (CartProductsIngredients cartProductsIngredient : cartProductsIngredients) {
                SQLConfig.cartProductsIngredientsDao.delete(cartProductsIngredient);
            }

            SQLConfig.cartProductsDao.delete(cartProduct);

            return true;
        }
        else {
            return false;
        }
    }

    public static String getProductIngredients(String cartProductKey) {
        String ingredientsName = "";
        List<CartProductsIngredients> cartProductsIngredients = SQLConfig.cartProductsIngredientsDao.queryBuilder().where(CartProductsIngredientsDao.Properties.CartProductKey.eq(cartProductKey)).list();
        for (CartProductsIngredients cartProductsIngredient : cartProductsIngredients) {
            if (ingredientsName.trim().isEmpty()) {
                ingredientsName = cartProductsIngredient.getIngredientsName();
            } else {
                ingredientsName = ingredientsName + ", " + cartProductsIngredient.getIngredientsName();
            }

        }
        return ingredientsName;
    }

    public static List<CartProductsIngredients> getProductIngredientsAll(String cartProductKey) {
        return SQLConfig.cartProductsIngredientsDao.queryBuilder().where(CartProductsIngredientsDao.Properties.CartProductKey.eq(cartProductKey)).list();

    }

    public static List<CartProducts> getItems(String restaurantBranchKey) {
        List<CartProducts> cartProducts = SQLConfig.cartProductsDao.queryBuilder().where(CartProductsDao.Properties.RestaurantBranchKey.eq(restaurantBranchKey)).orderAsc(CartProductsDao.Properties.SortingNumber).list();
        return cartProducts;
    }

    public static void emptyCart(String restaurantBranchKey) {
        List<CartProducts> cartProducts = SQLConfig.cartProductsDao.queryBuilder().where(CartProductsDao.Properties.RestaurantBranchKey.eq(restaurantBranchKey)).list();
        for(CartProducts cartProduct : cartProducts) {
            SQLConfig.cartProductsDao.delete(cartProduct);
        }

        List<CartProductsIngredients> cartProductsIngredients = SQLConfig.cartProductsIngredientsDao.queryBuilder().where(CartProductsIngredientsDao.Properties.RestaurantBranchKey.eq(restaurantBranchKey)).list();
        for(CartProductsIngredients cartProductsIngredient : cartProductsIngredients) {
            SQLConfig.cartProductsIngredientsDao.delete(cartProductsIngredient);
        }
    }
}