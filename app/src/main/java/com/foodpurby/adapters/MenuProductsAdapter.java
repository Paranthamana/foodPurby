package com.foodpurby.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.foodpurby.ondbstorage.DBActionsProducts;
import com.foodpurby.ondbstorage.Products;
import com.foodpurby.R;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by android1 on 12/9/2015.
 */
public class MenuProductsAdapter extends BaseAdapter implements BaseAdapterInterface {
    private LayoutInflater inflater;
    private Context context;
    private List<Products> products;

    private EventBus bus;
    public MenuProductsAdapter(Context context, EventBus bus)
    {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;

        this.bus = bus;
        this.products = DBActionsProducts.getProducts();
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
        row = inflater.inflate(R.layout.lv_menu_products_item,
                parent, false);

        Button btnItem = (Button) row.findViewById(R.id.btnItem);
        btnItem.setText(products.get(position).getProductName());
        btnItem.setTag(products.get(position));

        return row;
    }

    @Override
    public void notifyChanges() {

    }
}
