package com.foodpurby.screens;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.foodpurby.adapters.MenuProductsAdapter;
import com.foodpurby.events.AddRemoveToCartEvent;
import com.foodpurby.ondbstorage.Cuisine;
import com.foodpurby.ondbstorage.DBActionsCart;
import com.foodpurby.ondbstorage.DBActionsCuisine;
import com.foodpurby.baseclass.MyActivity;
import com.foodpurby.utillities.AppSharedValues;
import com.foodpurby.R;

import java.util.List;

import de.greenrobot.event.EventBus;

public class MenuProducts extends AppCompatActivity {

    private EventBus bus = EventBus.getDefault();
    private MenuProductsAdapter mpAdapter;
    private TextView tvTotalItemsInCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);

        setContentView(R.layout.activity_menu_products);

        tvTotalItemsInCart = (TextView) findViewById(R.id.tvTotalItemsInCart);
        ListView lstProducts = (ListView) findViewById(R.id.lstProducts);
        Button btnShowCart = (Button) findViewById(R.id.btnShowCart);

        btnShowCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.getInstance().trackEvent("Button", "Click", "Show Cart");
                final List<Cuisine> cuisines = DBActionsCuisine.getCuisines();
                MyActivity.DisplayCart(MenuProducts.this);

            }
        });

        mpAdapter = new MenuProductsAdapter(MenuProducts.this, bus);
        lstProducts.setAdapter(mpAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menu_products, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onEvent(final AddRemoveToCartEvent ev) {
        runOnUiThread(new Runnable() {
            public void run() {
                tvTotalItemsInCart.setText(String.valueOf(DBActionsCart.getTotalItems(AppSharedValues.getSelectedRestaurantBranchKey())));
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
