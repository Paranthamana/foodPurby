package com.foodpurby.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.foodpurby.ondbstorage.DBActionsCategory;
import com.foodpurby.ondbstorage.DBActionsGroup;
import com.foodpurby.ondbstorage.DBActionsProducts;
import com.foodpurby.ondbstorage.RestaurantBranch;
import com.foodpurby.screens.MenuItemFragment;
import com.foodpurby.events.RestaurantNamesEvent;
import com.foodpurby.events.RestaurantsCountEvent;
import com.foodpurby.model.DAORestaurantBranchItems;
import com.foodpurby.ondbstorage.DBActionsCuisine;
import com.foodpurby.ondbstorage.DBActionsIngredients;
import com.foodpurby.ondbstorage.DBActionsIngredientsCategory;
import com.foodpurby.ondbstorage.DBActionsRestaurantBranch;
import com.foodpurby.util.CustomProgressDialog;
import com.foodpurby.utillities.AppSharedValues;
import com.foodpurby.R;

import java.util.List;

import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by tech on 11/2/2015.
 */
public class RestaurantNamesMapAdapter extends BaseAdapter implements BaseAdapterInterface {
    Context mContext;
    List<RestaurantBranch> restaurant;
    LayoutInflater mInflater;
    EventBus bus;
    String searchKey;
    CustomProgressDialog mCustomProgressDialog;
    FragmentManager fragmentManager;

    public RestaurantNamesMapAdapter(Context mContext, EventBus bus, CustomProgressDialog mCustomProgressDialog, FragmentManager fragmentManager) {
        this.mContext = mContext;
        this.bus = bus;
        this.mCustomProgressDialog = mCustomProgressDialog;
        this.fragmentManager = fragmentManager;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        this.restaurant = DBActionsRestaurantBranch.get();
    }

    @Override
    public int getCount() {
        return restaurant.size();
    }

    @Override
    public Object getItem(int position) {
        return restaurant.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder mHolder = new ViewHolder();
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.lv_restaurant_names_map, null);

            mHolder.tvRestaurantName = (TextView) convertView.findViewById(R.id.tvRestaurantName);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }


        mHolder.tvRestaurantName.setText(restaurant.get(position).getRestaurantBranchName().toString());
        mHolder.tvRestaurantName.setTag(restaurant.get(position));
        mHolder.tvRestaurantName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try {
                    if (!mCustomProgressDialog.isShowing()) {
                        mCustomProgressDialog.ChageMessage(mContext, "Please wait...", "Please wait...");
                        mCustomProgressDialog.show(mContext);
                    }

                    final String mRestaurantKey = ((RestaurantBranch) v.getTag()).getRestaurantKey();


                    DAORestaurantBranchItems.getInstance().Callresponse("", mRestaurantKey,AppSharedValues.getCategory(), new Callback<DAORestaurantBranchItems.RestaurantBranchItems>() {
                        @Override
                        public void success(DAORestaurantBranchItems.RestaurantBranchItems restaurantBranchItems, Response response) {


                            int count = 0;
                            if (restaurantBranchItems.getHttpcode().equals(200)) {


                                DBActionsCuisine.deleteAll();
                                DBActionsGroup.deleteAll();
                                DBActionsCategory.deleteAll();
                                DBActionsIngredientsCategory.deleteAll();
                                DBActionsIngredients.deleteAll();

                                List<DAORestaurantBranchItems.Shop_detail> restaurantDetails = restaurantBranchItems.getData().getShop_detail();
                                for (DAORestaurantBranchItems.Shop_detail restaurantDetail : restaurantDetails) {

                                    if (restaurantDetail != null) {
//                                        Integer deliveryFee = restaurantDetail.getDelivery_fee() == null ? 0 : restaurantDetail.getDelivery_fee();
                                        Integer minOrderValue = restaurantDetail.getMin_order_value() == null ? 0 : restaurantDetail.getMin_order_value();

                                        AppSharedValues.setSelectedRestaurantBranchOfferText(restaurantDetail.getOffer_text());
                                        AppSharedValues.setSelectedRestaurantBranchOfferCode(restaurantDetail.getOffer_code());
                                        AppSharedValues.setSelectedRestaurantBranchOfferMinOrderValue(Double.valueOf(restaurantDetail.getOffer_min_order_value()));

                                        AppSharedValues.setSelectedRestaurantBranchKey(restaurantDetail.getShop_key());
                                        AppSharedValues.setSelectedRestaurantBranchName(restaurantDetail.getShop_name());

                                        if (restaurantDetail.getShop_status().trim().equalsIgnoreCase("closed")) {
                                            AppSharedValues.setSelectedRestaurantBranchStatus(AppSharedValues.ShopStatus.Closed);
                                        } else {
                                            AppSharedValues.setSelectedRestaurantBranchStatus(AppSharedValues.ShopStatus.Open);
                                        }

                                        //AppSharedValues.setSelectedRestaurantBranchStatus(AppSharedValues.ShopStatus.Closed);

//                                        AppSharedValues.setDeliveryCharge(Double.valueOf(deliveryFee));
                                        AppSharedValues.setMinOrderDeliveryPrice(Double.valueOf(minOrderValue));
                                        bus.post(new RestaurantNamesEvent());

                                        AppSharedValues.setDeliveryAvailability_ONLINE(false);
                                        if (restaurantDetail.getOnline_payment_available().equals(1)) {
                                            AppSharedValues.setDeliveryAvailability_ONLINE(true);
                                        }

                                        List<DAORestaurantBranchItems.Category> categories = restaurantDetail.getCategories();
                                        for (DAORestaurantBranchItems.Category category : categories) {

                                            count++;

                                            DBActionsCuisine.add(restaurantDetail.getShop_key(), restaurantDetail.getShop_key(), category.getCategory_key(), category.getCategory_name());
                                            DBActionsGroup.add(restaurantDetail.getShop_key(), restaurantDetail.getShop_key(), "Grp1", "Food");
                                            DBActionsCategory.add(restaurantDetail.getShop_key(), restaurantDetail.getShop_key(), category.getCategory_key(), category.getCategory_name());

                                            for (DAORestaurantBranchItems.Menu_item menuItem : category.getMenu_items()) {

                                                DBActionsProducts.add(restaurantDetail.getShop_key(), category.getCategory_key(),
                                                        menuItem.getItem_key(), menuItem.getItem_name(), menuItem.getItem_description(), menuItem.getItem_image(), category.getCategory_key(),
                                                        Double.valueOf(menuItem.getPrice()), Double.valueOf(menuItem.getPrice()), Double.valueOf(menuItem.getPrice()),
                                                        0D, Integer.valueOf(menuItem.getItem_type()));

                                               /* List<DAORestaurantBranchItems.Menu_item> ingredients = menuItem.getIs_ingredient();
                                                if (ingredients != null) {
                                                    for (DAORestaurantBranchItems.Menu_item ingredient : ingredients) {
                                                        DBActionsIngredientsCategory.add(restaurantDetail.getShop_key(), menuItem.getItem_key(),
                                                                String.valueOf(ingredient.getIngredients_type_id()), ingredient.getIngredients_type_name(),
                                                                ingredient.(), ingredient.getMaximum(), ingredient.getRequired(), 0D);

                                                        for (DAORestaurantBranchItems.Ingredients_list ingredientList : ingredient.getIngredients_list()) {

                                                            DBActionsIngredients.add(restaurantDetail.getShop_key(), menuItem.getItem_key(), String.valueOf(ingredientList.getItem_ingredients_list_id()),
                                                                    ingredientList.getIngredient_name(), String.valueOf(ingredient.getIngredients_type_id()),
                                                                    String.valueOf(ingredient.getIngredients_type_id()), Double.valueOf(ingredientList.getPrice()));
                                                        }
                                                    }
                                                }*/
                                            }
                                        }
                                    }
                                }

                                if (count > 0) {
                                    if (mCustomProgressDialog.isShowing()) {
                                        mCustomProgressDialog.dismiss();
                                    }
                                    Bundle bundle = new Bundle();
                                    bundle.putString("restaurantKey", mRestaurantKey);

                                    //MainMenuFragment mainMenuFragment = new MainMenuFragment();
                                    //mainMenuFragment.setArguments(bundle);

                                    MenuItemFragment menuItemFragment = new MenuItemFragment();
                                    menuItemFragment.setArguments(bundle);

                                    AppSharedValues.setSelectedRestaurantBranchKey(mRestaurantKey);

                                    fragmentManager.beginTransaction()
                                            .replace(R.id.container, menuItemFragment)
                                            .commit();
                                } else {
                                    if (mCustomProgressDialog.isShowing()) {
                                        mCustomProgressDialog.dismiss();
                                    }
                                    Toast.makeText(mContext, "No items to show", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            Toast.makeText(mContext, error.getMessage(), Toast.LENGTH_SHORT).show();
                            if (mCustomProgressDialog.isShowing()) {
                                mCustomProgressDialog.dismiss();
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    if (mCustomProgressDialog.isShowing()) {
                        mCustomProgressDialog.dismiss();
                    }
                }
            }
        });

        convertView.setTag(mHolder);
        return convertView;
    }

    @Override
    public void notifyChanges() {
        this.restaurant = DBActionsRestaurantBranch.get();
        bus.post(new RestaurantsCountEvent(this.restaurant.size()));
        this.notifyDataSetChanged();
    }

    public class ViewHolder {
        public TextView tvRestaurantName;
    }
}
