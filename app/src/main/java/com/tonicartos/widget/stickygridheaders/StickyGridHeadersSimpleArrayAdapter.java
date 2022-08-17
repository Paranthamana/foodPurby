/*
 Copyright 2013 Tonic Artos

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */

package com.tonicartos.widget.stickygridheaders;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.foodpurby.R;
import com.foodpurby.dialogs.IngredientsSelection;
import com.foodpurby.events.AddRemoveToCartEvent;
import com.foodpurby.ondbstorage.Category;
import com.foodpurby.ondbstorage.DBActionsCart;
import com.foodpurby.ondbstorage.DBActionsCategory;
import com.foodpurby.ondbstorage.DBActionsIngredients;
import com.foodpurby.ondbstorage.DBActionsProducts;
import com.foodpurby.ondbstorage.Products;
import com.foodpurby.screens.MyApplication;
import com.foodpurby.util.Common;
import com.foodpurby.utillities.AppSharedValues;
import com.foodpurby.utillities.LoadImages;
import com.facebook.drawee.view.SimpleDraweeView;
import com.sloop.fonts.FontsManager;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * @param <T>
 * @author Tonic Artos
 */
public class StickyGridHeadersSimpleArrayAdapter<T> extends BaseAdapter implements
        StickyGridHeadersSimpleAdapter {
    protected static final String TAG = StickyGridHeadersSimpleArrayAdapter.class.getSimpleName();

    private int mHeaderResId;

    private LayoutInflater mInflater;

    private int mItemResId;

    private Context context;

    private List<Products> mProducts = new ArrayList<>();

    private EventBus bus;

    public StickyGridHeadersSimpleArrayAdapter(Context context, int headerResId,
                                               int itemResId, String cuisineKey, EventBus bus) {
        this.context = context;
        this.bus = bus;
        init(context, headerResId, itemResId, cuisineKey);
      //  FontsManager.initFormAssets(this.context, "Lato-Light.ttf");

    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public int getCount() {
        return mProducts.size();
    }

    @Override
    public long getHeaderId(int position) {
        Products item = getItem(position);
        //CharSequence value = item.getCategoryKey();

        return item.getCategoryKey().charAt(2);
    }

    @Override
    @SuppressWarnings("unchecked")
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(mHeaderResId, parent, false);
         //   FontsManager.changeFonts(convertView);
            holder = new HeaderViewHolder();
            holder.textView = (TextView) convertView.findViewById(android.R.id.text1);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }

        Products item = getItem(position);
        Category category = DBActionsCategory.getCategory(item.getCategoryKey());
        // set header text as first char in string
        holder.textView.setText(category.getCategoryName());

        return convertView;
    }

    @Override
    public Products getItem(int position) {
        return mProducts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    @SuppressWarnings("unchecked")
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;


        if (convertView == null) {
            convertView = mInflater.inflate(mItemResId, parent, false);
         //   FontsManager.changeFonts(convertView);
            holder = new ViewHolder();

            holder.btnItemAdd = (ImageButton) convertView.findViewById(R.id.btnItemAdd);

            holder.llItem = (LinearLayout) convertView.findViewById(R.id.llItem);
            holder.tvItemDesc = (TextView) convertView.findViewById(R.id.tvItemDesc);
            holder.tvItem = (TextView) convertView.findViewById(R.id.tvItem);
            holder.imgItem = (SimpleDraweeView) convertView.findViewById(R.id.imgItem);
            holder.imgItemType = (SimpleDraweeView) convertView.findViewById(R.id.imgItemTypes);
            holder.tvItemPrice = (TextView) convertView.findViewById(R.id.tvItemPrice);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Products products = getItem(position);
        holder.tvItem.setText(products.getProductName());
//        Uri uri = Uri.parse(String.valueOf(getItem(position)));
        if (products.getProductDesc() != null) {
            if (products.getProductDesc().trim().isEmpty()) {
                //holder.tvItemDesc.setVisibility(View.INVISIBLE);
            } else {
                //holder.tvItemDesc.setVisibility(View.VISIBLE);
                holder.tvItemDesc.setText(products.getProductDesc());
            }
        }

        holder.tvItem.setTag(getItem(position));


        holder.tvItemPrice.setText(Common.getPriceWithCurrencyCode(getItem(position).getPrice().toString()));
        holder.tvItemPrice.setTypeface(Typeface.DEFAULT);
        Uri uri = Uri.parse(getItem(position).getProductImgUrl());

        if (holder.imgItem != null) {

            /*Uri imageUri;
            imageUri = Uri.parse(getItem(position).getProductImgUrl());
            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setUri(imageUri)
                    .setAutoPlayAnimations(true)

                    .build();
            //Set the DraweeView controller, and you should be good to go.
            holder.imgItem.setController(controller);
*/

            LoadImages.show(context, holder.imgItem, getItem(position).getProductImgUrl());
            holder.imgItem.setTag(getItem(position));

        }

        if (holder.imgItemType != null) {
  /*          LoadImages.show(context, holder.imgItemType, getItem(position).getProductImgUrl());


            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setUri(uri)
                    .setAutoPlayAnimations(true)
                    .build();
            //Set the DraweeView controller, and you should be good to go.
            holder.imgItemType.setController(controller);
            holder.imgItemType.setTag(getItem(position));*/

            Common.showFresco1(context,holder.imgItemType,getItem(position).getProductImgUrl());

        }

        holder.llItem.setTag(getItem(position));
        holder.llItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.getInstance().trackEvent("LinearLayout", "Click", "Items");
                if (Common.IsShopOpened(context, mInflater)) {
                    ViewHolder myView = (ViewHolder) v.getTag();
                    Products product = (Products) myView.tvItem.getTag();
                    int ingredientsCount = DBActionsIngredients.getIngredients(product).size();
                    if (ingredientsCount <= 0) {

                        if (Common.CanIncreaseItemCount(context, mInflater, AppSharedValues.getSelectedRestaurantBranchKey(), product.getProductKey(), null)) {
                            DBActionsCart.add(AppSharedValues.getSelectedRestaurantBranchKey(), product.getProductKey(), null, 1);
                            bus.post(new AddRemoveToCartEvent(true, v));
                        }
                    } else {
                        IngredientsSelection sec = new IngredientsSelection();
                        sec.ShowDialog(context, mInflater, v, bus, product);
                    }
                }
            }
        });

        holder.btnItemAdd.setTag(getItem(position));
        holder.btnItemAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.getInstance().trackEvent("ImageButton", "Click", "Add New items");
                if (Common.IsShopOpened(context, mInflater)) {

                    Products product = (Products) v.getTag();
                    int ingredientsCount = DBActionsIngredients.getIngredients(product).size();
                    if (ingredientsCount <= 0) {

                        if (Common.CanIncreaseItemCount(context, mInflater, AppSharedValues.getSelectedRestaurantBranchKey(), product.getProductKey(), null)) {
                            DBActionsCart.add(AppSharedValues.getSelectedRestaurantBranchKey(), product.getProductKey(), null, 1);
                            bus.post(new AddRemoveToCartEvent(true, null));
                        }
                    } else {
                        IngredientsSelection sec = new IngredientsSelection();
                        sec.ShowDialog(context, mInflater, v, bus, product);
                    }
                }
            }
        });

        convertView.setTag(holder);
        return convertView;
    }

    private void init(Context context, int headerResId, int itemResId, String cuisineKey) {

        this.mHeaderResId = headerResId;
        this.mItemResId = itemResId;
        this.mInflater = LayoutInflater.from(context);

        for (Products product : DBActionsProducts.getProducts(AppSharedValues.getSelectedRestaurantBranchKey(), cuisineKey)) {
            this.mProducts.add(product);
        }
    }

    protected class HeaderViewHolder {
        public TextView textView;
    }

    protected class ViewHolder {

        public LinearLayout llItem;
        public TextView tvItem, tvItemDesc;
        public SimpleDraweeView imgItem;
        public SimpleDraweeView imgItemType;
        public TextView tvItemPrice;
        public ImageButton btnItemAdd;

    }
}
