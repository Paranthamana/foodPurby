package com.foodpurby.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sloop.fonts.FontsManager;
import com.foodpurby.model.DAORestaurantDetails;
import com.foodpurby.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by tech on 11/2/2015.
 */
public class RestaurantTimingAdapter extends BaseAdapter implements BaseAdapterInterface {

    Calendar cal = Calendar.getInstance();
    SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);

    String previousDay = "";
    Context mContext;
    List<DAORestaurantDetails.Delivery_time_list> timing = new ArrayList<>();
    LayoutInflater mInflater;

    public RestaurantTimingAdapter(Context mContext) {
        this.mContext = mContext;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //FontsManager.initFormAssets(this.mContext, "Lato-Light.ttf");

    }

    @Override
    public int getCount() {
        return timing.size();
    }

    @Override
    public Object getItem(int position) {
        return timing.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder mHolder = new ViewHolder();
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.lv_restaurant_timing, null);
           // FontsManager.changeFonts(convertView);

            mHolder.tvDay = (TextView) convertView.findViewById(R.id.tvDay);
            mHolder.tvTime = (TextView) convertView.findViewById(R.id.tvTime);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }


        DAORestaurantDetails.Delivery_time_list dTime = (DAORestaurantDetails.Delivery_time_list)getItem(position);

        mHolder.tvDay.setText(" ");
        if(!previousDay.equalsIgnoreCase(dTime.getTimeslot_day())) {
            mHolder.tvDay.setText(dTime.getTimeslot_day());
        }
        mHolder.tvTime.setText(dTime.getTimeslot_start_time() + " - " + dTime.getTimeslot_end_time());

        if(dTime.getTimeslot_day().equalsIgnoreCase(dayFormat.format(cal.getTime()))) {
            //mHolder.tvDay.setTypeface(null, Typeface.BOLD);
            //mHolder.tvTime.setTypeface(null, Typeface.BOLD);

            mHolder.tvDay.setTextColor(mContext.getResources().getColor(R.color.toolbar_color));
            mHolder.tvTime.setTextColor(mContext.getResources().getColor(R.color.toolbar_color));
        }
        convertView.setTag(mHolder);
        previousDay = dTime.getTimeslot_day();

        return convertView;
    }

    public void setData(List<DAORestaurantDetails.Delivery_time_list> timing) {
        this.timing = timing;
    }

    @Override
    public void notifyChanges() {
        this.notifyDataSetChanged();
    }

    public class ViewHolder {
        public TextView tvTime;
        public TextView tvDay;
    }
}
