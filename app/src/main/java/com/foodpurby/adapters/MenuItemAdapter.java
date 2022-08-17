package com.foodpurby.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.foodpurby.model.MenuItem;
import com.foodpurby.R;

import java.util.ArrayList;

/**
 * Created by tech on 11/16/2015.
 */
public class MenuItemAdapter extends BaseAdapter {
    Context context;
    ArrayList<MenuItem> mPutMenuItems;
    LayoutInflater mInflater;
    //    int count = 0;
    MenuItem mMenuItem = new MenuItem();

    public MenuItemAdapter(Context context, ArrayList<MenuItem> mPutMenuItems) {
        this.context = context;
        this.mPutMenuItems = mPutMenuItems;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mPutMenuItems.size();
    }

    @Override
    public MenuItem getItem(int position) {
        return mPutMenuItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder mHolder;


        if (convertView == null){
            convertView = mInflater.inflate(R.layout.row_menus_list_items, null);

            mHolder = new ViewHolder();
            mHolder.mItemNames = (TextView) convertView.findViewById(R.id.RMLI_TV_item_names);
            mHolder.mCategoryItemaNames = (TextView) convertView.findViewById(R.id.RMLI_TV_item_categories);
            mHolder.mItemCosts = (TextView) convertView.findViewById(R.id.RMLI_TV_item_costs);
            mHolder.mAddQuantitySet = (ImageButton) convertView.findViewById(R.id.RMLI_IB_item_quantity);
            mHolder.mRemoveQuantitySet = (ImageButton) convertView.findViewById(R.id.RMLI_IB_reduce_item_quantity);
            mHolder.mItemCounts = (TextView) convertView.findViewById(R.id.RMLI_TV_item_count);
            mHolder.mShowCosts = (LinearLayout) convertView.findViewById(R.id.RMLI_LL_show_prices);
            mHolder.mCostPrice = (TextView) convertView.findViewById(R.id.RQP_TV_cost_price);
            mHolder.mCartItems = (TextView) convertView.findViewById(R.id.RMLI_TV_cart_entry);

            convertView.setTag(mHolder);

        }else{
            mHolder = (ViewHolder) convertView.getTag();
        }
        mHolder.mItemNames.setText(mPutMenuItems.get(position).getmItemName());
        mHolder.mCategoryItemaNames.setText(mPutMenuItems.get(position).getmItemCategoryNames());
        mHolder.mItemCosts.setText(mPutMenuItems.get(position).getmItemCost());



        return convertView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    class ViewHolder {
        TextView mItemNames, mCategoryItemaNames, mItemCosts, mItemCounts, mCostPrice, mCartItems;
        ImageButton mAddQuantitySet, mRemoveQuantitySet;
        LinearLayout mShowCosts;
    }
}
