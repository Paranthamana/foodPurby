package com.foodpurby.screens;

import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sloop.fonts.FontsManager;
import com.foodpurby.adapters.MyCartAdapter;
import com.foodpurby.model.DAODeliveryDate;
import com.foodpurby.ondbstorage.DBActionsCart;
import com.foodpurby.ondbstorage.DBActionsRestaurantBranch;
import com.foodpurby.ondbstorage.RestaurantBranch;
import com.foodpurby.util.Common;
import com.foodpurby.util.CustomProgressDialog;
import com.foodpurby.baseclass.MyActivity;
import com.foodpurby.events.AddRemoveToCartEvent;
import com.foodpurby.model.DAODeliveryTime;
import com.foodpurby.utillities.AppConfig;
import com.foodpurby.utillities.AppSharedValues;
import com.foodpurby.R;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MyCart extends AppCompatActivity {
    private CustomProgressDialog mCustomProgressDialog;

    //private String minOrderText = "This restaurant charges #charge# for orders below #below#. Your current subtotal is #subtotal#";
    private String minOrderText = "Minimum cart order value is #below# but your current subtotal is #subtotal#";

    private EventBus bus = EventBus.getDefault();
    private ListView lvCartItems;
    private MyCartAdapter mpAdapter;
    private TextView tvTotalCartPrice;

    Button btnPay;
    Button btnEmpty;
    TextView tvMinOrder, tvSubTotal;
    RestaurantBranch restaurantBranch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_my_cart);

        AppConfig.MYCART = this;
    //    FontsManager.initFormAssets(this, "Lato-Light.ttf");
    //    FontsManager.changeFonts(this);
        mCustomProgressDialog = CustomProgressDialog.getInstance();

        restaurantBranch = DBActionsRestaurantBranch.get(AppSharedValues.getSelectedRestaurantBranchKey());
        AppSharedValues.setMinOrderDeliveryPrice(restaurantBranch.getMinOrderDeliveryPrice());
        AppSharedValues.setMinOrderPrice(restaurantBranch.getMinOrderPrice());

        Common.PopulateRestaurantDetails(MyCart.this, "My Cart", Common.Price.CartPrice);

        tvMinOrder = (TextView) findViewById(R.id.tvMinOrder);
        tvSubTotal = (TextView) findViewById(R.id.tvSubtotal);

        tvTotalCartPrice = (TextView) findViewById(R.id.tvTotalCartPrice);
        lvCartItems = (ListView) findViewById(R.id.lvCartItems);
        mpAdapter = new MyCartAdapter(MyCart.this, bus, AppSharedValues.getSelectedRestaurantBranchKey());
        lvCartItems.setAdapter(mpAdapter);

        btnEmpty = (Button) findViewById(R.id.btnEmpty);
        btnEmpty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.getInstance().trackEvent("Button", "Click", "Empty");
                AlertDialog.Builder mDialog = new AlertDialog.Builder(MyCart.this, R.style.MyAlertDialogStyle);
                final View vDialog = getLayoutInflater().inflate(R.layout.dialog_my_cart_clear_items, null);
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
                        MyApplication.getInstance().trackEvent("Button", "Click", "Clear");
                        DBActionsCart.emptyCart(AppSharedValues.getSelectedRestaurantBranchKey());
                        bus.post(new AddRemoveToCartEvent(true, null));
                    }
                });
            }
        });

        btnPay = (Button) findViewById(R.id.btnPay);
        btnPay.setTag("pay");
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.getInstance().trackEvent("Button", "Click", "Pay");
                if (v.getTag().equals("payminimum")) {
                    Toast.makeText(MyCart.this, getString(R.string.toast_minimum_cart_order_value) + Common.getPriceWithCurrencyCode(AppSharedValues.getMinOrderPrice().toString()), Toast.LENGTH_SHORT).show();
                } else if (v.getTag().equals("pay")) {

                    if (!mCustomProgressDialog.isShowing()) {
                        mCustomProgressDialog.ChageMessage(MyCart.this, "Please wait...", "Please wait...");
                        mCustomProgressDialog.show(MyCart.this);
                    }

                    DAODeliveryDate.getInstance().Callresponse("", AppSharedValues.getSelectedRestaurantBranchKey(), new Callback<DAODeliveryDate.DeliveryDate>() {

                        @Override
                        public void success(DAODeliveryDate.DeliveryDate deliveryDate, Response response) {

                            if (deliveryDate.getHttpcode().equals("200")) {

                                List<String> deliveryTimes = new ArrayList<String>();
                                for (String dates : deliveryDate.getData().getDelivery_pickup_details_date().getDates()) {
                                    getDeliveryTimes(dates);
                                    AppSharedValues.putDeliveryDateTimes(dates, deliveryTimes);
                                }

                                Common.GetAddress(MyCart.this, mCustomProgressDialog, bus);
                                if (mCustomProgressDialog.isShowing()) {
                                    mCustomProgressDialog.dismiss();
                                }

                                MyActivity.DisplayMyPay(MyCart.this);
                            } else {
                                Toast.makeText(MyCart.this, deliveryDate.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void failure(RetrofitError error) {
                            String ss = "";
                            Toast.makeText(MyCart.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                            if (mCustomProgressDialog.isShowing()) {
                                mCustomProgressDialog.dismiss();
                            }
                        }
                    });
                } else {
                    finish();
                }
            }
        });

        notifyChanges();
    }


    private void getDeliveryTimes(final String deliveryDate) {
        DAODeliveryTime.getInstance().Callresponse("", AppSharedValues.getSelectedRestaurantBranchKey(), deliveryDate, "1", new Callback<DAODeliveryTime.DeliveryTime>() {

            @Override
            public void success(DAODeliveryTime.DeliveryTime deliveryTime, Response response) {

                if (deliveryTime.getHttpcode().equals("200")) {


                    if (deliveryTime.getStatus().equalsIgnoreCase("success")) {
                        AppSharedValues.putDeliveryDateTimes(deliveryDate,  deliveryTime.getData().getDelivery_pickup_details_time().getTimings());
                    }

                }
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(MyCart.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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

    private Boolean notifyChanges() {
        Common.PopulateRestaurantDetailsCartPrice(MyCart.this, Common.Price.CartPrice);
        mpAdapter.notifyChanges();

        Double totalPrice = DBActionsCart.getTotalCartPrice(AppSharedValues.getSelectedRestaurantBranchKey());

        tvMinOrder.setText(minOrderText.replace("#charge#", Common.getPriceWithCurrencyCode(String.valueOf(AppSharedValues.getMinOrderDeliveryPrice())))
                .replace("#below#", Common.getPriceWithCurrencyCode(String.valueOf(AppSharedValues.getMinOrderPrice())))
                .replace("#subtotal#", Common.getPriceWithCurrencyCode(DBActionsCart.getTotalCartPrice(AppSharedValues.getSelectedRestaurantBranchKey()).toString())));
        tvMinOrder.setTypeface(Typeface.DEFAULT);


        btnPay.getBackground().setAlpha(255);
        //btnPay.setEnabled(true);
        if (totalPrice.equals(0D)) {
            finish();
            return true;


        } else if (totalPrice < AppSharedValues.getMinOrderPrice()) {

            if (AppSharedValues.getMinOrderDeliveryPrice() > 0) {
                tvMinOrder.setVisibility(View.VISIBLE);
            }

            //btnPay.getBackground().setAlpha(128);
            //btnPay.setEnabled(false);
            btnPay.setText(R.string.txt_lets_pay);
            btnPay.setTag("payminimum");
            btnEmpty.setVisibility(View.VISIBLE);
            return false;
        } else {

            //btnPay.setEnabled(true);
            tvMinOrder.setVisibility(View.GONE);
            btnPay.setText(R.string.txt_lets_pay);
            btnPay.setTag("pay");
            btnEmpty.setVisibility(View.VISIBLE);

            return true;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void setTotalValue(Double total) {
        tvSubTotal.setText(" :  " + String.valueOf(total));
    }
}
