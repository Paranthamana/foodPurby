package com.foodpurby.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.foodpurby.R;
import com.foodpurby.model.LatLong;


import java.util.ArrayList;

/**
 * Created by tech on 12/7/2015.
 */
public class MapPopUpRestaurants extends BaseAdapter {
    Context mContext;
    ArrayList<LatLong> mLatLongLists;
    LayoutInflater mInflater;

    public MapPopUpRestaurants(Context mContext, ArrayList<LatLong> mLatLongLists) {
        this.mContext = mContext;
        this.mLatLongLists = mLatLongLists;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return mLatLongLists.size();
    }

    @Override
    public LatLong getItem(int position) {
        return mLatLongLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = mInflater.inflate(R.layout.adapter_add_menu_lists, null);
        ViewHolder mHolder = new ViewHolder();
        mHolder.mMenuNameLists = (TextView) convertView.findViewById(R.id.AAML_TV_menu_lists);
        mHolder.mMenuNameLists.setText(mLatLongLists.get(position).getmLocationName());

        return convertView;
    }

    class ViewHolder {
        TextView mMenuNameLists;
    }
}
