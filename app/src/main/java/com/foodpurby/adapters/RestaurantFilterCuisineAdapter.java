package com.foodpurby.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.foodpurby.R;
import com.foodpurby.ondbstorage.CuisineFilter;
import com.foodpurby.ondbstorage.DBActionsCuisineFilter;
import com.foodpurby.utillities.AppSharedValues;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by tech on 11/2/2015.
 */
public class RestaurantFilterCuisineAdapter extends BaseAdapter implements BaseAdapterInterface {

    //    public Map<String, String> selectedCuisines = new HashMap<String, String>();
    Calendar cal = Calendar.getInstance();
    SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);

    String previousDay = "";
    Context mContext;
    List<CuisineFilter> cuisineFilter = new ArrayList<>();
    LayoutInflater mInflater;

    public RestaurantFilterCuisineAdapter(Context mContext) {
        this.mContext = mContext;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.cuisineFilter = DBActionsCuisineFilter.getCuisines();
        AppSharedValues.setFilterSelectedCuisines(new HashMap<String, String>(AppSharedValues.getFilterSelectedCuisines()));

    }

    @Override
    public int getCount() {
        return cuisineFilter.size();
    }

    @Override
    public Object getItem(int position) {
        return cuisineFilter.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder mHolder = new ViewHolder();
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.lv_restaurant_cuisine, null);

            mHolder.tvCuisineName = (TextView) convertView.findViewById(R.id.tvCuisineName);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }

        CuisineFilter cuisineFilter = (CuisineFilter) getItem(position);
        mHolder.tvCuisineName.setText(cuisineFilter.getCuisineName());
        convertView.setTag(mHolder);

        ChangeColor(mHolder.tvCuisineName);

        if (AppSharedValues.isIntegrediantChecked() || mHolder.tvCuisineName.isSelected()) {
            mHolder.tvCuisineName.setTextColor(mContext.getResources().getColor(R.color.red));

        } else {
            mHolder.tvCuisineName.setTextColor(mContext.getResources().getColor(R.color.black));

        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewHolder vh = (ViewHolder) v.getTag();
                vh.tvCuisineName.setSelected(!vh.tvCuisineName.isSelected());

                if (vh.tvCuisineName.isSelected()) {
                    if (!AppSharedValues.getFilterSelectedCuisines().containsKey(vh.tvCuisineName.getText())) {
                        AppSharedValues.FilterSelectedCuisines.put(vh.tvCuisineName.getText().toString(), vh.tvCuisineName.getText().toString());
                    }

                } else {
                    if (AppSharedValues.getFilterSelectedCuisines().containsKey(vh.tvCuisineName.getText())) {
                        AppSharedValues.FilterSelectedCuisines.remove(vh.tvCuisineName.getText().toString());
                    }
                }
                ChangeColor(vh.tvCuisineName);
            }
        });

        return convertView;
    }

    public void ChangeColor(TextView tvCuisineName) {
        if (AppSharedValues.getFilterSelectedCuisines().containsKey(tvCuisineName.getText())) {
            tvCuisineName.setTextColor(mContext.getResources().getColor(R.color.red));
            tvCuisineName.setSelected(true);
        } else {
            tvCuisineName.setSelected(false);
            tvCuisineName.setTextColor(mContext.getResources().getColor(R.color.black));
        }
    }

    @Override
    public void notifyChanges() {
        this.cuisineFilter = DBActionsCuisineFilter.getCuisines();
        this.notifyDataSetChanged();
    }

    public class ViewHolder {
        public TextView tvCuisineName;
    }
}
