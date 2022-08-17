package com.foodpurby.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.foodpurby.R;
import com.foodpurby.baseclass.MyActivity;
import com.foodpurby.events.RestaurantNamesEvent;
import com.foodpurby.model.DAORestaurantBranchItems;
import com.foodpurby.model.DAOUserOrder;
import com.foodpurby.ondbstorage.DBActionsCategory;
import com.foodpurby.ondbstorage.DBActionsCuisine;
import com.foodpurby.ondbstorage.DBActionsGroup;
import com.foodpurby.ondbstorage.DBActionsIngredients;
import com.foodpurby.ondbstorage.DBActionsIngredientsCategory;
import com.foodpurby.ondbstorage.DBActionsProducts;
import com.foodpurby.ondbstorage.DBActionsRestaurantBranch;
import com.foodpurby.screens.RestaurantMenuActivity;
import com.foodpurby.util.Common;
import com.foodpurby.util.CustomProgressDialog;
import com.foodpurby.utillities.AppSharedValues;
import com.foodpurby.utillities.LoadImages;
import com.foodpurby.utillities.SessionManager;
import com.sloop.fonts.FontsManager;

import java.util.List;

import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by android1 on 12/29/2015.
 */
public class UserOrderAdapter extends BaseAdapter implements BaseAdapterInterface {

    List<DAOUserOrder.Data> userOrders;
    private String restaurantBranchKey;
    private LayoutInflater inflater;
    private Context context;
    private EventBus bus;
    private CustomProgressDialog mCustomProgressDialog;

    public UserOrderAdapter(Context context, EventBus bus, String restaurantBranchKey, List<DAOUserOrder.Data> userOrders) {
        this.context = context;
        this.bus = bus;
        this.restaurantBranchKey = restaurantBranchKey;
        this.userOrders = userOrders;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mCustomProgressDialog = CustomProgressDialog.getInstance();
       // FontsManager.initFormAssets(this.context, "Lato-Light.ttf");
    }

    @Override
    public int getCount() {
        return this.userOrders.size();
    }

    @Override
    public Object getItem(int position) {
        return this.userOrders.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = new ViewHolder();
        final DAOUserOrder.Data userOrder = ((DAOUserOrder.Data) getItem(position));

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.lv_user_order, parent, false);
          //  FontsManager.changeFonts(convertView);

            viewHolder.tvOrderNo = (TextView) convertView.findViewById(R.id.tvOrderNo);
            viewHolder.tvTotalPrice = (TextView) convertView.findViewById(R.id.tvTotalPrice);
            viewHolder.ivLogo = (ImageView) convertView.findViewById(R.id.ivLogo);
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.tvName);
            viewHolder.tvDate = (TextView) convertView.findViewById(R.id.tvDate);
            viewHolder.tReoder = (Button) convertView.findViewById(R.id.reorder);
            viewHolder.tvStatus = (TextView) convertView.findViewById(R.id.tvStatus);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvOrderNo.setText(userOrder.getOrder_key());
        viewHolder.tvTotalPrice.setText(Common.getPriceWithCurrencyCode(userOrder.getOrder_total().toString()));
        viewHolder.tvTotalPrice.setTypeface(Typeface.DEFAULT);
        viewHolder.tvName.setText(userOrder.getShop_name());
        viewHolder.tvDate.setText(userOrder.getOrder_datetime());
        viewHolder.tvStatus.setText(userOrder.getPayment_status());

        LoadImages.show(context, viewHolder.ivLogo, userOrder.getShop_logo());

        viewHolder.tvName.setTag(userOrder);

        convertView.setTag(viewHolder);
        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.tReoder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DBActionsRestaurantBranch.get(userOrder.getShop_key()) != null) {
                    final DAOUserOrder.Data userOrder = (DAOUserOrder.Data) finalViewHolder.tvName.getTag();
                    try {
                        if (!mCustomProgressDialog.isShowing()) {
                            mCustomProgressDialog.ChageMessage(context, context.getString(R.string.dialog_please_wait), context.getString(R.string.dialog_please_wait));
                            mCustomProgressDialog.show(context);
                        }


                        DAORestaurantBranchItems.getInstance().Callresponse("",
                                userOrder.getShop_key(), AppSharedValues.getCategory(),
                                new Callback<DAORestaurantBranchItems.RestaurantBranchItems>() {
                                    @Override
                                    public void success(DAORestaurantBranchItems.RestaurantBranchItems restaurantBranchItems, Response response) {

                                /*if (mCustomProgressDialog.isShowing()) {
                                    mCustomProgressDialog.dismiss();
                                }*/
                                        int count = 1;
                                        if (restaurantBranchItems.getHttpcode().equals(200)) {
                                            AppSharedValues.setSelectedRestaurantItemsFromServer(restaurantBranchItems);

                                            AsyncTaskRunner runner = new AsyncTaskRunner();
                                            runner.execute(userOrder.getShop_key());
                                            bus.post(new RestaurantNamesEvent());


                                        }
                                    }

                                    @Override
                                    public void failure(RetrofitError error) {
                                        error.printStackTrace();
                                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                                        if (mCustomProgressDialog.isShowing()) {
                                            mCustomProgressDialog.dismiss();
                                        }
                                    }
                                });
                    } catch (Exception e) {
                        e.printStackTrace();
                    /*if (mCustomProgressDialog.isShowing()) {
                        mCustomProgressDialog.dismiss();
                    }*/
                    }
                } else {
                    Toast.makeText(context, "Restaurant not available try after sometime", Toast.LENGTH_SHORT).show();
                }
            }
        });
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewHolder viewHolder = (ViewHolder) v.getTag();
                final DAOUserOrder.Data userOrder = (DAOUserOrder.Data) viewHolder.tvName.getTag();
                MyActivity.DisplayOrderDetails(context, userOrder.getOrder_key());
            }
        });
        return convertView;
    }

    @Override
    public void notifyChanges() {
        //this.addresses = DBActionsUserAddress.getAddresses();
        notifyDataSetChanged();
    }

    public class ViewHolder {
        public ImageView ivLogo;
        public TextView tvName, tvTotalPrice, tvOrderNo;
        public TextView tvDate;
        public Button tReoder;
        public TextView tvStatus;
    }


    private class AsyncTaskRunner extends AsyncTask<String, String, String> {
        Integer count = 0;
        private String resp;
        String mRestaurantKey = "";

        @Override
        protected String doInBackground(String... params) {

            try {

                mRestaurantKey = params[0];
                if (AppSharedValues.getSelectedRestaurantItemsFromServer() != null) {

                    DBActionsCuisine.deleteAll();
                    DBActionsGroup.deleteAll();
                    DBActionsCategory.deleteAll();
                    DBActionsIngredientsCategory.deleteAll();
                    DBActionsIngredients.deleteAll();

                    List<DAORestaurantBranchItems.Shop_detail> restaurantDetails = AppSharedValues.getSelectedRestaurantItemsFromServer().getData().getShop_detail();
                    for (DAORestaurantBranchItems.Shop_detail restaurantDetail : restaurantDetails) {

                        if (restaurantDetail != null) {
//                            Integer deliveryFee = restaurantDetail.getDelivery_fee() == null ? 0 : restaurantDetail.getDelivery_fee();
                            Integer minOrderValue = restaurantDetail.getMin_order_value() == null ? 0 : restaurantDetail.getMin_order_value();

                            AppSharedValues.setSelectedRestaurantBranchOfferText(restaurantDetail.getOffer_text());
                            AppSharedValues.setSelectedRestaurantBranchOfferCode(restaurantDetail.getOffer_code());
                            AppSharedValues.setSelectedRestaurantBranchOfferMinOrderValue(Double.valueOf(restaurantDetail.getOffer_min_order_value()));
                            SessionManager.getInstance().setLatitute(context, String.valueOf(restaurantDetail.getLatitude()));
                            SessionManager.getInstance().setLongtitue(context, String.valueOf(restaurantDetail.getLongitude()));
                            SessionManager.getInstance().setAddress(context, restaurantDetail.getLocation());
                            AppSharedValues.setSelectedRestaurantBranchKey(restaurantDetail.getShop_key());
                            AppSharedValues.setSelectedRestaurantBranchName(restaurantDetail.getShop_name());

                            if (restaurantDetail.getShop_status().trim().equalsIgnoreCase("closed")) {
                                AppSharedValues.setSelectedRestaurantBranchStatus(AppSharedValues.ShopStatus.Closed);
                            } else {
                                AppSharedValues.setSelectedRestaurantBranchStatus(AppSharedValues.ShopStatus.Open);
                            }

                            //AppSharedValues.setSelectedRestaurantBranchStatus(AppSharedValues.ShopStatus.Closed);

//                            AppSharedValues.setDeliveryCharge(Double.valueOf(deliveryFee));
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
                                            0D, //Integer.valueOf(menuItem.getItem_type()
                                            0);

                                    List<DAORestaurantBranchItems.Ingredient> ingredients = menuItem.getIngredients();
                                    if (ingredients != null) {
                                        for (DAORestaurantBranchItems.Ingredient ingredient : ingredients) {
                                            DBActionsIngredientsCategory.add(restaurantDetail.getShop_key(), menuItem.getItem_key(),
                                                    String.valueOf(ingredient.getIngredients_type_id()), ingredient.getIngredients_type_name(),
                                                    ingredient.getMinimum(), ingredient.getMaximum(), ingredient.getRequired(), 0D);

                                            for (DAORestaurantBranchItems.Ingredients_list ingredientList : ingredient.getIngredients_list()) {

                                                DBActionsIngredients.add(restaurantDetail.getShop_key(), menuItem.getItem_key(), String.valueOf(ingredientList.getItem_ingredients_list_id()),
                                                        ingredientList.getIngredient_name(), String.valueOf(ingredient.getIngredients_type_id()),
                                                        String.valueOf(ingredient.getIngredients_type_id()), Double.valueOf(ingredientList.getPrice()));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return resp;
        }

        @Override
        protected void onPostExecute(String result) {

            if (mCustomProgressDialog.isShowing()) {
                mCustomProgressDialog.dismiss();
            }

            if (count > 0) {

                Bundle bundle = new Bundle();
                bundle.putString("restaurantKey", mRestaurantKey);

                Intent mIntent = new Intent(context, RestaurantMenuActivity.class);
                mIntent.putExtra("Position", 0);
                context.startActivity(mIntent);
                AppSharedValues.setSelectedRestaurantBranchKey(mRestaurantKey);


            } else {
                Toast.makeText(context, R.string.toast_no_item_to_show, Toast.LENGTH_SHORT).show();
            }

        }


        @Override
        protected void onPreExecute() {
            // Things to be done before execution of long running operation. For
            // example showing ProgessDialog
        }


        @Override
        protected void onProgressUpdate(String... text) {
        }
    }
}