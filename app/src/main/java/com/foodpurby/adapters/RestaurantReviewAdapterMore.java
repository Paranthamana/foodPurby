package com.foodpurby.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.foodpurby.model.DAORestaurantReviews;
import com.foodpurby.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tech on 11/2/2015.
 */
public class RestaurantReviewAdapterMore extends BaseAdapter implements BaseAdapterInterface {
    Context mContext;
    List<DAORestaurantReviews.More_review> review = new ArrayList<>();
    LayoutInflater mInflater;

    public RestaurantReviewAdapterMore(Context mContext) {
        this.mContext = mContext;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return review.size();
    }

    @Override
    public Object getItem(int position) {
        return review.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder mHolder = new ViewHolder();
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.lv_restaurant_review, null);

            mHolder.tvDate = (TextView) convertView.findViewById(R.id.tvDate);
            mHolder.tvName = (TextView) convertView.findViewById(R.id.tvName);
            mHolder.tvReview = (TextView) convertView.findViewById(R.id.tvReview);
            mHolder.rbRating = (RatingBar) convertView.findViewById(R.id.rbRating);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }

        DAORestaurantReviews.More_review dReview = (DAORestaurantReviews.More_review)getItem(position);
        mHolder.tvDate.setText(dReview.getCreated_date());
        mHolder.tvName.setText(dReview.getFirstname() + " " + dReview.getLastname());
        mHolder.tvReview.setText(dReview.getReview_text());
        mHolder.rbRating.setRating(dReview.getRating());

        convertView.setTag(mHolder);
        convertView.setEnabled(false);
        return convertView;
    }

    public void setData(List<DAORestaurantReviews.More_review> timing) {
        this.review = timing;
    }

    @Override
    public void notifyChanges() {
        this.notifyDataSetChanged();
    }

    public class ViewHolder {
        public TextView tvDate;
        public TextView tvName;
        public TextView tvReview;
        public RatingBar rbRating;
    }
}
