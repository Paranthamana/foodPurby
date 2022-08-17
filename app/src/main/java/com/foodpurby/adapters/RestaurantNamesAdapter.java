package com.foodpurby.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.sloop.fonts.FontsManager;
import com.foodpurby.R;
import com.foodpurby.baseclass.MyActivity;
import com.foodpurby.model.DAOFavourite;
import com.foodpurby.ondbstorage.DBActionsRestaurantBranch;
import com.foodpurby.ondbstorage.RestaurantBranch;
import com.foodpurby.screens.MyApplication;
import com.foodpurby.util.Common;
import com.foodpurby.utillities.AppSharedValues;
import com.foodpurby.utillities.LoadImages;
import com.foodpurby.utillities.UserDetails;

import java.util.List;

import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by tech on 11/2/2015.
 */
public class RestaurantNamesAdapter extends BaseAdapter implements BaseAdapterInterface {
    Context mContext;
    List<RestaurantBranch> restaurant;
    LayoutInflater mInflater;
    EventBus bus;
    String searchKey;
    TranslateAnimation animation;
    int size;

    public RestaurantNamesAdapter(Context mContext, EventBus bus) {
        this.mContext = mContext;
        this.bus = bus;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.restaurant = DBActionsRestaurantBranch.get();

       // FontsManager.initFormAssets(this.mContext, "Lato-Light.ttf");
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
            convertView = mInflater.inflate(R.layout.lv_restaurant_names, null);
            //FontsManager.changeFonts(convertView);

            mHolder.rbRating = (RatingBar) convertView.findViewById(R.id.rbRating);
            mHolder.ivInfo = (ImageView) convertView.findViewById(R.id.img_info);
            mHolder.ivOnline = (ImageView) convertView.findViewById(R.id.ivOnline);
            mHolder.ivCOD = (ImageView) convertView.findViewById(R.id.ivCOD);
            mHolder.tvOfferPercent = (TextView) convertView.findViewById(R.id.tvOfferPercent);
            mHolder.mOpeningTime = (TextView) convertView.findViewById(R.id.tvOpeningTime);
            mHolder.mDeliversIn = (TextView) convertView.findViewById(R.id.tvDeliversIn);
            mHolder.mFavouriteStatus = (ImageView) convertView.findViewById(R.id.ivFavouriteStatus);
            mHolder.llClosed = (LinearLayout) convertView.findViewById(R.id.llClosed);
            mHolder.llOpened = (LinearLayout) convertView.findViewById(R.id.llOpened);
            mHolder.mRestaurantLocates = (TextView) convertView.findViewById(R.id.tvRestaurantLocates);
            mHolder.mRestaurantCuisines = (TextView) convertView.findViewById(R.id.tvRestaurantCuisines);
            mHolder.mRestaurantIcon = (SimpleDraweeView) convertView.findViewById(R.id.RVRNL_IV_rest_image);
            mHolder.mRestaurantName = (TextView) convertView.findViewById(R.id.RVRNL_TV_restaurant_name);
            mHolder.mRestaurantDistance = (TextView) convertView.findViewById(R.id.RVRNL_TV_distance);
            mHolder.mRestaurantMinCost = (TextView) convertView.findViewById(R.id.RVRNL_TV_restaurant_min_cost);
            mHolder.mRestaurantOrderTime = (TextView) convertView.findViewById(R.id.RVRNL_TV_restaurant_order_time);
            mHolder.mRestaurantOrderDeliveryOnTime = (TextView) convertView.findViewById(R.id.RVRNL_TV_restaurant_order_delivery_on_time);
            mHolder.mAvgRating = (TextView) convertView.findViewById(R.id.tvAvgRating);
            mHolder.tvStatus = (TextView) convertView.findViewById(R.id.tvStatus);
            mHolder.tvoffr = (TextView) convertView.findViewById(R.id.offertext);
            mHolder.halallogo = (ImageView) convertView.findViewById(R.id.ivhalal);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }

        if (restaurant.get(position).getRestaurantStatus() != null && restaurant.get(position).getRestaurantStatus().equalsIgnoreCase("open")) {
            mHolder.llClosed.setVisibility(View.GONE);
            mHolder.llOpened.setVisibility(View.VISIBLE);
        } else {
            String openingTime = restaurant.get(position).getRestaurantOpeningTime() == null ? "" : restaurant.get(position).getRestaurantOpeningTime();

            mHolder.mOpeningTime.setText(openingTime);
            mHolder.llClosed.setVisibility(View.VISIBLE);
            mHolder.llOpened.setVisibility(View.GONE);
        }

        mHolder.tvOfferPercent.setText(restaurant.get(position).getRestaurantOfferText());
        String value = restaurant.get(position).getRestaurantArea();
        if (value.equals("2")) {
            mHolder.halallogo.setVisibility(View.VISIBLE);
        } else {
            mHolder.halallogo.setVisibility(View.GONE);
        }
        String offer = restaurant.get(position).getRestaurantOfferText();
        if (offer != null && !offer.isEmpty()) {
            mHolder.tvoffr.setVisibility(View.VISIBLE);
            mHolder.tvoffr.setText(offer);
        }
        if (!UserDetails.getCustomerKey().trim().isEmpty()) {
            //mHolder.mFavouriteStatus.setVisibility(View.VISIBLE);
            if (restaurant.get(position).getRestaurantFavouriteStatus() != null && restaurant.get(position).getRestaurantFavouriteStatus()) {
                mHolder.mFavouriteStatus.setImageResource(R.drawable.ic_list_fav_selected);
                mHolder.mFavouriteStatus.setTag(restaurant.get(position));
            } else {
                mHolder.mFavouriteStatus.setImageResource(R.drawable.ic_list_fav_normal);
                mHolder.mFavouriteStatus.setTag(restaurant.get(position));
            }

            mHolder.ivInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyActivity.DisplayRestaurantProfile(mContext);
                }
            });

            final ViewHolder finalMHolder = mHolder;
            mHolder.mFavouriteStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    MyApplication.getInstance().trackEvent("Imagview", "Click", "Favourites");
                    if (!UserDetails.getCustomerKey().isEmpty()) {
                        final RestaurantBranch restaurantBranch = (RestaurantBranch) v.getTag();
                        DAOFavourite.getInstance().Callresponse(UserDetails.getCustomerKey(), AppSharedValues.getLanguage(), restaurantBranch.getRestaurantBranchKey(), new Callback<DAOFavourite.UserFavourite>() {
                            @Override
                            public void success(DAOFavourite.UserFavourite userFavourite, Response response) {
                                if (userFavourite.getHttpcode().equalsIgnoreCase("200")) {

                                    if (userFavourite.getData().getIs_favourite().equals(1)) {
                                        ((ImageView) v).setImageResource(R.drawable.ic_list_fav_selected);
                                        ((ImageView) v).setTag(restaurantBranch);
                                    } else {
                                        ((ImageView) v).setImageResource(R.drawable.ic_list_fav_normal);
                                        ((ImageView) v).setTag(restaurantBranch);
                                    }
                                    finalMHolder.mRestaurantDistance.setText(userFavourite.getData().getFavourite_count());
                                    DBActionsRestaurantBranch.updateFavouriteStatus(restaurantBranch.getRestaurantBranchKey(), userFavourite.getData().getIs_favourite());
                                    restaurant = DBActionsRestaurantBranch.get();
                                    Toast.makeText(mContext, userFavourite.getMessage(), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(mContext, userFavourite.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                Toast.makeText(mContext, error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Toast.makeText(mContext, "Please login to continue.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        /*if(restaurant.get(position).getRestaurantPaymentTypes().equals(0)) {
            mHolder.ivCOD.setVisibility(View.VISIBLE);
            mHolder.ivOnline.setVisibility(View.GONE);
        }
        else if(restaurant.get(position).getRestaurantPaymentTypes().equals(1)) {
            mHolder.ivCOD.setVisibility(View.GONE);
            mHolder.ivOnline.setVisibility(View.VISIBLE);
        }
        else if(restaurant.get(position).getRestaurantPaymentTypes().equals(2)) {
            mHolder.ivOnline.setVisibility(View.VISIBLE);
            mHolder.ivCOD.setVisibility(View.VISIBLE);
        }*/

        String str = String.valueOf(restaurant.get(position).getRestaurantAverageRating());
        float rating = Float.parseFloat(str);
        mHolder.rbRating.setRating(rating);
        mHolder.tvStatus.setText(restaurant.get(position).getRestaurantStatus());
        mHolder.mAvgRating.setText(restaurant.get(position).getRestaurantAverageRating().toString());
        int a = (restaurant.get(position).getRestaurantDistance().intValue());
        mHolder.mRestaurantDistance.setText(String.valueOf(a));
        mHolder.mRestaurantName.setTag(restaurant.get(position));
        mHolder.mRestaurantName.setText(restaurant.get(position).getRestaurantBranchName());
        //mHolder.mRestaurantVat.setText("" + restaurant.get(position).getVat());
        mHolder.mRestaurantMinCost.setText(Common.getPriceWithCurrencyCode(String.valueOf(restaurant.get(position).getMinOrderDeliveryPrice())));
        mHolder.mRestaurantMinCost.setTypeface(Typeface.DEFAULT);

        mHolder.mDeliversIn.setText(restaurant.get(position).getRestaurantDeliveryInMin().toString() + "" + mContext.getResources().getString(R.string.txt_min));
        mHolder.mRestaurantCuisines.setText(restaurant.get(position).getRestaurantCuisines());
        mHolder.mRestaurantLocates.setText(restaurant.get(position).getRestaurantAddress());

        LoadImages.show(mContext, mHolder.mRestaurantIcon, restaurant.get(position).getRestaurantImageUrl());
        //mHolder.mRestaurantOrderTime.setText("" + restaurant.get(position).getDelivery_time());

        convertView.setTag(mHolder);
        return convertView;
    }

    @Override
    public void notifyChanges() {
        this.restaurant = DBActionsRestaurantBranch.get();
        this.notifyDataSetChanged();
    }

    public class ViewHolder {
        public RatingBar rbRating;
        public ImageView ivOnline, ivInfo;
        public ImageView ivCOD;
        public ImageView mFavouriteStatus;
        public LinearLayout llClosed, llOpened;
        public SimpleDraweeView mRestaurantIcon;
        public TextView mOpeningTime, mDeliversIn, tvOfferPercent, tvStatus, tvoffr;
        public TextView mRestaurantName, mRestaurantCuisines, mRestaurantLocates, mRestaurantDistance;
        public TextView mRestaurantMinCost, mRestaurantOrderTime, mRestaurantOrderDeliveryOnTime, mAvgRating;
        public ImageView halallogo;
    }
}
