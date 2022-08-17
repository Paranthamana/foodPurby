package com.foodpurby.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.foodpurby.R;
import com.foodpurby.events.AddRemoveToCartEvent;
import com.foodpurby.ondbstorage.CartProducts;
import com.foodpurby.ondbstorage.DBActionsCart;
import com.foodpurby.screens.MyApplication;
import com.foodpurby.util.Common;
import com.foodpurby.utillities.LoadImages;
import com.sloop.fonts.FontsManager;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by android1 on 12/9/2015.
 */
public class MyCartAdapter extends BaseAdapter implements BaseAdapterInterface {
    private LayoutInflater inflater;
    private Context context;
    private List<CartProducts> products;
    private String restaurantBranchKey;
    private EventBus bus;
    Double total = 0.0;

    public MyCartAdapter(Context context, EventBus bus, String restaurantBranchKey) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        this.bus = bus;
        this.restaurantBranchKey = restaurantBranchKey;
        products = DBActionsCart.getItems(this.restaurantBranchKey);
      //  FontsManager.initFormAssets(this.context, "Lato-Light.ttf");
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        View row = convertView;
        row = inflater.inflate(R.layout.lv_menu_cart_item, parent, false);
      //  FontsManager.changeFonts(row);

        ImageButton btnUp = (ImageButton) row.findViewById(R.id.btnUp);
        ImageButton btnDown = (ImageButton) row.findViewById(R.id.btnDown);
        ImageView ivRestaurantLogo = (ImageView) row.findViewById(R.id.ivRestaurantLogo);

        TextView tvProductName = (TextView) row.findViewById(R.id.tvProductName);
        TextView tvProductIngredients = (TextView) row.findViewById(R.id.tvProductIngredients);
        TextView tvProductIngredientsText = (TextView) row.findViewById(R.id.tvProductIngredientsText);
        final TextView tvTotalQuantity = (TextView) row.findViewById(R.id.tvTotalQuantity);
        TextView tvTotalPrice = (TextView) row.findViewById(R.id.tvTotalPrice);
        ImageButton btnItemAdd = (ImageButton) row.findViewById(R.id.btnItemAdd);
        ImageButton btnItemRemove = (ImageButton) row.findViewById(R.id.btnItemRemove);
        ImageButton tvClear = (ImageButton) row.findViewById(R.id.tvClear);

        tvProductName.setText(products.get(position).getProductName());
        tvTotalQuantity.setText(products.get(position).getTotalQuantity().toString());
        tvTotalPrice.setText(Common.getPriceWithCurrencyCode(round(DBActionsCart.getTotalProductPrice(products.get(position)), 2) + ""));
        tvTotalPrice.setTypeface(Typeface.DEFAULT);
//        tvTotalPrice.setText(Common.getPriceWithCurrencyCode(Float.parseFloat()));
        LoadImages.show(context, ivRestaurantLogo, products.get(position).getProductImgUrl());

        //AppConfig.MYCART.setTotalValue(DBActionsCart.getTotalProductPrice(products.get(position)));

        btnItemAdd.setTag(products.get(position));
        btnItemAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Common.CanIncreaseItemCount(context, inflater, Integer.valueOf(tvTotalQuantity.getText().toString()))) {
                    CartProducts cartProducts = (CartProducts) v.getTag();
                    DBActionsCart.addFromCart(cartProducts.getCartProductKey(), 1);
                    bus.post(new AddRemoveToCartEvent(true, null));
                }
            }
        });

        btnItemRemove.setTag(products.get(position));
        btnItemRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartProducts cartProducts = (CartProducts) v.getTag();
                DBActionsCart.remove(cartProducts.getCartProductKey(), 1);
                bus.post(new AddRemoveToCartEvent(true, null));
            }
        });

        tvClear.setTag(products.get(position));
        tvClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                AlertDialog.Builder mDialog = new AlertDialog.Builder(context, R.style.MyAlertDialogStyle);
                final View vDialog = inflater.inflate(R.layout.dialog_my_cart_clear_items, null);
                TextView vTitle = (TextView) vDialog.findViewById(R.id.tvTitle);
                vTitle.setText(context.getResources().getString(R.string.txt_want_to_clear_item));
                Button btnCancel = (Button) vDialog.findViewById(R.id.btnCancel);
                Button btnClearOrder = (Button) vDialog.findViewById(R.id.btnClearOrder);
                mDialog.setView(vDialog);
                final AlertDialog mAlertDilaog = mDialog.create();
                mAlertDilaog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                mAlertDilaog.show();

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mAlertDilaog != null && mAlertDilaog.isShowing()) {
                            mAlertDilaog.dismiss();
                        }
                        MyApplication.getInstance().trackEvent("Button", "Click", "Cancel");
                    }
                });

                btnClearOrder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mAlertDilaog != null && mAlertDilaog.isShowing()) {
                            mAlertDilaog.dismiss();
                        }

                        CartProducts cartProducts = (CartProducts) view.getTag();
                        DBActionsCart.remove(cartProducts.getCartProductKey(), 0);
                        bus.post(new AddRemoveToCartEvent(true, null));
                    }
                });

            }
        });

        if (products.get(position).getSortingNumber() > 1) {
            btnUp.setVisibility(View.VISIBLE);
            btnUp.setTag(products.get(position));
            btnUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    CartProducts cartProducts = (CartProducts) v.getTag();
                    DBActionsCart.reOrder(cartProducts.getRestaurantBranchKey(), cartProducts.getSortingNumber(), cartProducts.getSortingNumber() - 1);
                    bus.post(new AddRemoveToCartEvent(true, null));
                }
            });
        } else {
            btnUp.setVisibility(View.INVISIBLE);
        }

        if (products.get(position).getSortingNumber() < products.size()) {
            btnDown.setVisibility(View.VISIBLE);
            btnDown.setTag(products.get(position));
            btnDown.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    CartProducts cartProducts = (CartProducts) v.getTag();
                    DBActionsCart.reOrder(cartProducts.getRestaurantBranchKey(), cartProducts.getSortingNumber(), cartProducts.getSortingNumber() + 1);
                    bus.post(new AddRemoveToCartEvent(true, null));
                }
            });
        } else {
            btnDown.setVisibility(View.INVISIBLE);
        }

        String ing = DBActionsCart.getProductIngredients(products.get(position).getCartProductKey());
        //tvProductIngredients.setText(ing);
        if (ing.trim().isEmpty()) {
            tvProductIngredients.setVisibility(View.GONE);
            tvProductIngredientsText.setVisibility(View.GONE);
        } else {
            tvProductIngredients.setText(ing);
        }
        return row;
    }

    @Override
    public void notifyChanges() {
        products = DBActionsCart.getItems(restaurantBranchKey);
        this.notifyDataSetChanged();
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
