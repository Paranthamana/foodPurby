package com.foodpurby.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sloop.fonts.FontsManager;
import com.foodpurby.R;
import com.foodpurby.model.DrawerMenuItems;

import java.util.ArrayList;


/*import biznetcircles.technoduce.com.biznet.R;
import biznetcircles.technoduce.com.biznet.model.DrawerMenuItems;*/

/**
 * Created by tech on 8/6/2015.
 */
public class DrawerMenuAdapter extends BaseAdapter implements  BaseAdapterInterface {

    Context context;
    ArrayList<DrawerMenuItems> mDrawerMenuLists;
    LayoutInflater mInflater;

    public DrawerMenuAdapter(Context context, ArrayList<DrawerMenuItems> mDrawerMenuLists) {

        this.context = context;
        this.mDrawerMenuLists = mDrawerMenuLists;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

      //  FontsManager.initFormAssets(this.context, "Lato-Light.ttf");
    }

    @Override
    public int getCount() {
        return mDrawerMenuLists.size();
    }

    @Override
    public DrawerMenuItems getItem(int position) {
        return mDrawerMenuLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rootview = mInflater.inflate(R.layout.row_drawer_menu_view, null);
      //  FontsManager.changeFonts(rootview);

        ViewHolder mHolder = new ViewHolder();
        mHolder.mMenuIcons = (ImageView) rootview.findViewById(R.id.RDMV_IV_menu_icons);
        mHolder.mMenuValues = (TextView) rootview.findViewById(R.id.RDMV_TV_menu_items);

        mHolder.mMenuIcons.setImageResource(mDrawerMenuLists.get(position).getmIcon());
        mHolder.mMenuValues.setText(mDrawerMenuLists.get(position).getmTitle());

        return rootview;
    }

    @Override
    public void notifyChanges() {
        notifyDataSetChanged();
    }

    class ViewHolder {
        ImageView mMenuIcons;
        TextView mMenuValues;
    }
}
