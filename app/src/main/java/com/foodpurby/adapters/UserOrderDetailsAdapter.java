package com.foodpurby.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.foodpurby.R;
import com.foodpurby.model.DAOUserOrderDetails;
import com.foodpurby.util.Common;
import com.foodpurby.utillities.AppConfig;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by android1 on 12/9/2015.
 */
public class UserOrderDetailsAdapter extends BaseAdapter implements BaseAdapterInterface {
    private LayoutInflater inflater;
    private Context context;
    private List<DAOUserOrderDetails.Item> products;

    private EventBus bus;

    public UserOrderDetailsAdapter(Context context, EventBus bus, List<DAOUserOrderDetails.Item> products) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;

        this.bus = bus;

        this.products = products;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        row = inflater.inflate(R.layout.lv_user_order_details_item,
                parent, false);


        TextView tvProductName = (TextView) row.findViewById(R.id.tvProductName);
        TextView tvProductIngredients = (TextView) row.findViewById(R.id.tvProductIngredients);
        TextView tvProductIngredientsText = (TextView) row.findViewById(R.id.tvProductIngredientsText);
        final TextView tvTotalQuantity = (TextView) row.findViewById(R.id.tvTotalQuantity);
        TextView tvTotalPrice = (TextView) row.findViewById(R.id.tvTotalPrice);

        tvProductName.setText(products.get(position).getName());
        tvTotalQuantity.setText(products.get(position).getQuantity().toString());
        tvTotalPrice.setText(Common.getPriceWithCurrencyCode(AppConfig.decimalFormat.format(products.get(position).getTotal_price())));
        tvTotalPrice.setTypeface(Typeface.DEFAULT);
        String ingredients = "";
        if(products.get(position).getIngredients() != null) {
            for (DAOUserOrderDetails.Ingredient ingredient : products.get(position).getIngredients()) {
                if (ingredients.isEmpty()) {
                    ingredients = ingredient.getName();
                } else {
                    ingredients = ingredients + ", " + ingredient.getName();
                }
            }
        }
        tvProductIngredients.setText(ingredients);
        if (ingredients.trim().isEmpty()) {
            tvProductIngredientsText.setVisibility(View.GONE);
        }

        return row;
    }

    @Override
    public void notifyChanges() {
        this.notifyDataSetChanged();
    }
}