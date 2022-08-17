package com.foodpurby.screens;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.foodpurby.adapters.MenuItemAdapter;
import com.foodpurby.model.MenuItem;
import com.foodpurby.adapters.CategoryAdapter;
import com.foodpurby.R;

import java.util.ArrayList;


public class MenuCategory extends Fragment {
    CategoryAdapter aCategoryAdapter;
    ArrayList<MenuItem> mPutMenuItems = new ArrayList<MenuItem>();
    private ListView mMenusListItems;
    private TextView mCostPrice, mCartItems, mItemCounts;
    private LinearLayout mShowCosts;
    private ImageButton mAddQuantitySet, mRemoveQuantitySet;
    MenuItemAdapter aMenuItemAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View vCategoryMenus = inflater.inflate(R.layout.fragment_menu_items, container, false);
        mMenusListItems = (ListView) vCategoryMenus.findViewById(R.id.FMI_LV_menu_items);
        mCostPrice = (TextView) vCategoryMenus.findViewById(R.id.FMI_TV_cost_price);
        mCartItems = (TextView) vCategoryMenus.findViewById(R.id.FMI_TV_cart_entry);
        mShowCosts = (LinearLayout) vCategoryMenus.findViewById(R.id.FMI_LL_show_prices);

        getMenuItems();
        aMenuItemAdapter = new MenuItemAdapter(getActivity(), mPutMenuItems);
        mMenusListItems.setAdapter(aMenuItemAdapter);
        mMenusListItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                MyApplication.getInstance().trackEvent("Listview", "Click", "Menu lists");
                Toast.makeText(getActivity(), getResources().getString(R.string.toast_item_clicked_on) + position, Toast.LENGTH_LONG).show();
                LayoutInflater mInflater = getActivity().getLayoutInflater();
                view = mInflater.inflate(R.layout.row_menus_list_items, null);
                mAddQuantitySet = (ImageButton) view.findViewById(R.id.RMLI_IB_item_quantity);
                mRemoveQuantitySet = (ImageButton) view.findViewById(R.id.RMLI_IB_reduce_item_quantity);
                mItemCounts = (TextView) view.findViewById(R.id.RMLI_TV_item_count);
                mAddQuantitySet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyApplication.getInstance().trackEvent("ImageButton", "Click", "Add Quantity");
                        mRemoveQuantitySet.setVisibility(View.VISIBLE);
                        mItemCounts.setVisibility(View.VISIBLE);
                        mCartItems.setVisibility(View.VISIBLE);

                        String mGetCountValues = mItemCounts.getText().toString();
                        System.out.println("Res count values: " + mGetCountValues);
                        Integer count = Integer.parseInt(mGetCountValues) + 1;
                        System.out.println("Res Add count values: " + count);
                        String retVal = count.toString();
                        System.out.println("Res Ret count values: " + retVal);
                        mItemCounts.setText(retVal);
                        mCartItems.setText(retVal);
                        mShowCosts.setVisibility(View.VISIBLE);
                        if (mPutMenuItems.get(position).getmItemCost() != null) {
                            int mTotalCost = Integer.parseInt(mPutMenuItems.get(position).getmItemCost());
                            mCostPrice.setText("" + Integer.parseInt(retVal) * mTotalCost);
                        }

                    }
                });
                mRemoveQuantitySet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyApplication.getInstance().trackEvent("ImageButton", "Click", "Remove Quantity");
                        String mGetCountValues = mItemCounts.getText().toString();
                        Integer count = Integer.parseInt(mGetCountValues) - 1;

                        if (count <= -1) {
                            mRemoveQuantitySet.setVisibility(View.GONE);
                            mItemCounts.setVisibility(View.GONE);
                            mShowCosts.setVisibility(View.GONE);
                            mCartItems.setVisibility(View.GONE);
                        }

                        if (count > -1) {
                            String retVal = count.toString();
                            int mOrignalCost = Integer.parseInt(mPutMenuItems.get(position).getmItemCost());
                            int mTotalCost = Integer.parseInt(retVal) * mOrignalCost;
                            System.out.println("Res Total: " + mTotalCost);
                            mItemCounts.setText(retVal);
                            mCartItems.setText(retVal);
                            mCostPrice.setText("" + mTotalCost);
                        }
                    }

                });

            }
        });


        return vCategoryMenus;
    }

    private ArrayList<MenuItem> getMenuItems() {
        mPutMenuItems.clear();
        MenuItem mMenuItem;
        for (int i = 0; i < 10; i++) {
            mMenuItem = new MenuItem();
            mMenuItem.setmItemName("Black Forest Fresh Cream Cake");
            mMenuItem.setmItemCategoryNames("Fresh Cream Cakes");
            mMenuItem.setmItemCost("280");
            mPutMenuItems.add(mMenuItem);
        }

        return mPutMenuItems;
    }

}
