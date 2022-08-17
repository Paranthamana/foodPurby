package com.foodpurby.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.sloop.fonts.FontsManager;
import com.foodpurby.R;
import com.foodpurby.model.DAORestFavApi;
import com.foodpurby.utillities.LoadImages;

import java.util.List;

/**
 * Created by Developer on 26-May-17.
 */

public class FavRestaurantListAdapter extends BaseAdapter {
    Context mContext;
    LayoutInflater mInflater;
    List<DAORestFavApi.Datum> restaurant;

    public FavRestaurantListAdapter(Context mContext, List<DAORestFavApi.Datum> data) {
        this.mContext = mContext;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.restaurant = data;

      //  FontsManager.initFormAssets(this.mContext, "Lato-Light.ttf");
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
        FavRestaurantListAdapter.ViewHolder mHolder = new FavRestaurantListAdapter.ViewHolder();
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.activity_restaurant_fav_list, null);
          //  FontsManager.changeFonts(convertView);
            mHolder.rbRating = (RatingBar) convertView.findViewById(R.id.rbRating);
            mHolder.mFavouriteStatus = (ImageView) convertView.findViewById(R.id.ivFavouriteStatus);
            mHolder.mRestaurantCuisines = (TextView) convertView.findViewById(R.id.tvRestaurantCuisines);
            mHolder.mRestaurantIcon = (SimpleDraweeView) convertView.findViewById(R.id.RVRNL_IV_rest_image);
            mHolder.mRestaurantName = (TextView) convertView.findViewById(R.id.RVRNL_TV_restaurant_name);
            mHolder.mRestaurantDistance = (TextView) convertView.findViewById(R.id.RVRNL_TV_distance);
            mHolder.mAvgRating = (TextView) convertView.findViewById(R.id.tvAvgRating);
        } else {
            mHolder = (FavRestaurantListAdapter.ViewHolder) convertView.getTag();
        }
        String str = String.valueOf(restaurant.get(position).getAvg_rating());
        float rating = Float.parseFloat(str);
        mHolder.rbRating.setRating(rating);
        mHolder.mAvgRating.setText(restaurant.get(position).getAvg_rating().toString());
        mHolder.mRestaurantName.setText(restaurant.get(position).getVendor_name());
        mHolder.mRestaurantCuisines.setText(restaurant.get(position).getCuisines());
        mHolder.mRestaurantDistance.setText(restaurant.get(position).getFavourite_count());
        LoadImages.show(mContext, mHolder.mRestaurantIcon, restaurant.get(position).getVendor_logo_path());
        convertView.setTag(mHolder);
        return convertView;

    }

    public class ViewHolder {
        public RatingBar rbRating;
        public ImageView mFavouriteStatus;
        public SimpleDraweeView mRestaurantIcon;
        public TextView mRestaurantName, mRestaurantCuisines, mRestaurantDistance;
        public TextView mAvgRating;
    }
}
