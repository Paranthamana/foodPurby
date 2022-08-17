package com.foodpurby.screens;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sloop.fonts.FontsManager;
import com.foodpurby.adapters.UserOrderAdapter;
import com.foodpurby.events.AddRemoveToCartEvent;
import com.foodpurby.model.DAOUserOrder;
import com.foodpurby.util.CustomProgressDialog;
import com.foodpurby.utillities.UserDetails;
import com.foodpurby.R;
import com.foodpurby.utillities.AppSharedValues;

import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class UserOrder extends AppCompatActivity {

    private EventBus bus = EventBus.getDefault();
    private CustomProgressDialog mCustomProgressDialog;

    private ListView lvUserOrder;
    private UserOrderAdapter mpAdapter;
    Toolbar mToolbar;
    TextView mToolHeading;
    TextView tvCart, tvNoOrder;
    ImageView imgCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_user_order);

      //  FontsManager.initFormAssets(this, "Lato-Light.ttf");
      //  FontsManager.changeFonts(this);
        mCustomProgressDialog = CustomProgressDialog.getInstance();

        mToolbar = (Toolbar) findViewById(R.id.tb_header);
        mToolbar.setNavigationIcon(R.drawable.back);
        mToolHeading = (TextView) findViewById(R.id.tool_title);
        mToolHeading.setText(R.string.txt_my_orders);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                MyActivity.DisplayHomePage(UserOrder.this);
                finish();
            }
        });
        tvNoOrder = (TextView) findViewById(R.id.tvNoOrder);
        tvNoOrder.setVisibility(View.GONE);
        imgCart = (ImageView) findViewById(R.id.imgCart);
        imgCart.setVisibility(View.GONE);
        tvCart = (TextView) findViewById(R.id.tvCart);
        tvCart.setVisibility(View.GONE);

        lvUserOrder = (ListView) findViewById(R.id.lvUserOrder);

        notifyChanges();

        LoadUserOrders();
        lvUserOrder.setEmptyView(tvNoOrder);
    }

    private void LoadUserOrders() {
        if (!UserDetails.getCustomerKey().isEmpty()) {

            if (!mCustomProgressDialog.isShowing()) {
                mCustomProgressDialog.show(UserOrder.this);
            }

            DAOUserOrder.getInstance().Callresponse("", UserDetails.getCustomerKey(), AppSharedValues.getLanguage(), new Callback<DAOUserOrder.UserOrder>() {
                @Override
                public void success(DAOUserOrder.UserOrder restaurant, Response response) {
                    if (restaurant.getHttpcode().equals(200)) {
                        mpAdapter = new UserOrderAdapter(UserOrder.this, bus, AppSharedValues.getSelectedRestaurantBranchKey(), restaurant.getData());
                        lvUserOrder.setAdapter(mpAdapter);
                        mpAdapter.notifyChanges();

                        if (mCustomProgressDialog.isShowing()) {
                            mCustomProgressDialog.dismiss();
                        }

                        //bus.post(new RestaurantsLoadedEvent());
                    } else {
                        Toast.makeText(UserOrder.this, restaurant.getMessage(), Toast.LENGTH_SHORT).show();
                        if (mCustomProgressDialog.isShowing()) {
                            mCustomProgressDialog.dismiss();
                        }
                        //bus.post(new RestaurantsLoadedEvent());
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    String ss = "";
                    Toast.makeText(UserOrder.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    if (mCustomProgressDialog.isShowing()) {
                        mCustomProgressDialog.dismiss();
                    }
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_cart, menu);
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
                notifyChanges();
            }
        });
    }

    private void notifyChanges() {
        //btnAddress.setText("Pay order total " + Common.getPriceWithCurrencyCode(DBActionsCart.getTotalCartPriceIncludingTax(AppSharedValues.getSelectedRestaurantBranchKey()).toString()));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
