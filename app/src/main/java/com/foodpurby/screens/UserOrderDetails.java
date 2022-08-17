package com.foodpurby.screens;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.sloop.fonts.FontsManager;
import com.foodpurby.R;
import com.foodpurby.adapters.UserOrderDetailsAdapter;
import com.foodpurby.events.DummyEvent;
import com.foodpurby.model.DAOUserOrderDetails;
import com.foodpurby.util.Common;
import com.foodpurby.util.CustomProgressDialog;
import com.foodpurby.utillities.AppConfig;
import com.foodpurby.utillities.AppSharedValues;
import com.foodpurby.utillities.LoadImages;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class UserOrderDetails extends AppCompatActivity {
    private CustomProgressDialog mCustomProgressDialog;
    AlertDialog.Builder mDialog;
    AlertDialog mAlertDilaog;

    private EventBus bus = EventBus.getDefault();
    private UserOrderDetailsAdapter uodAdapter;
    private ListView lvAddress;
    Toolbar mToolbar;
    TextView mToolHeading;

    TextView tvCouponValue;
    TableRow trCouponValue;

    TextView tvRestaurantName;
    TextView tvAddress;
    TextView tvRestaurantCuisines;

    TextView tvBilltotal, tvPackageCharges, tvDeliveryCharges, tvDeliveryChargesExtra;
    TextView tvVATText, tvServiceTaxText;
    TextView tvVAT, tvServiceTax;
    TextView tvGrandTotal;

    TextView tvDate, tvStatus, tvPaymentStatus;

    ImageView ivRestaurantLogo;
    private ListView lvDetails;

    String orderKey = "";

    Button btnLiveTracking;

    String custmoerlat, customerlon, vendorlat, vendorlon;

    String order_id="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_order_details);

        //FontsManager.initFormAssets(this, "Lato-Light.ttf");
       // FontsManager.changeFonts(this);
        EventBus.getDefault().register(this);
        mCustomProgressDialog = CustomProgressDialog.getInstance();

        if (getIntent().getExtras() != null && getIntent().getExtras().getString("orderKey") != null && !getIntent().getExtras().getString("orderKey").isEmpty()) {
            orderKey = getIntent().getExtras().getString("orderKey");
        }
        mToolbar = (Toolbar) findViewById(R.id.tb_header);
        mToolbar.setNavigationIcon(R.drawable.back);
        mToolHeading = (TextView) findViewById(R.id.tool_title);
        mToolHeading.setText(R.string.txt_order_details);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        FrameLayout mShowCart = (FrameLayout) findViewById(R.id.flCart);
        mShowCart.setVisibility(View.GONE);


        tvDate = (TextView) findViewById(R.id.tvDate);
        tvStatus = (TextView) findViewById(R.id.tvStatus);
        tvPaymentStatus = (TextView) findViewById(R.id.tvPaymentStatus);

        tvCouponValue = (TextView) findViewById(R.id.tvCouponValue);
        trCouponValue = (TableRow) findViewById(R.id.trCouponValue);

        tvBilltotal = (TextView) findViewById(R.id.tvBilltotal);
        tvDeliveryCharges = (TextView) findViewById(R.id.tvDeliveryCharges);
        tvPackageCharges = (TextView) findViewById(R.id.tvPackageCharges);
        tvDeliveryChargesExtra = (TextView) findViewById(R.id.tvDeliveryChargesExtra);
        tvServiceTax = (TextView) findViewById(R.id.tvServiceTax);
        tvVAT = (TextView) findViewById(R.id.tvVat);

        tvVATText = (TextView) findViewById(R.id.tvVatText);
        tvServiceTaxText = (TextView) findViewById(R.id.tvServiceTaxText);

        tvGrandTotal = (TextView) findViewById(R.id.tvGrandTotal);

        lvDetails = (ListView) findViewById(R.id.lvDetails);

        List<DAOUserOrderDetails.Item> products = new ArrayList<>();
        uodAdapter = new UserOrderDetailsAdapter(UserOrderDetails.this, bus, products);
        lvDetails.setAdapter(uodAdapter);

        tvRestaurantName = (TextView) findViewById(R.id.tvRestaurantName);
        tvAddress = (TextView) findViewById(R.id.tvAddress);
        tvRestaurantCuisines = (TextView) findViewById(R.id.tvRestaurantCuisines);

        btnLiveTracking = (Button) findViewById(R.id.btnLiveTracking);
        btnLiveTracking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent in = new Intent(UserOrderDetails.this, LiveOrderTracking.class);
                in.putExtra("custmoerlat",custmoerlat);
                in.putExtra("customerlon",customerlon);
                in.putExtra("vendorlat",vendorlat);
                in.putExtra("vendorlon",vendorlon);
                in.putExtra("orderKey",order_id);
                startActivity(in);


            }
        });


        ivRestaurantLogo = (ImageView) findViewById(R.id.ivRestaurantLogo);

        if (!mCustomProgressDialog.isShowing()) {
            mCustomProgressDialog.ChageMessage(UserOrderDetails.this, getString(R.string.dialog_please_wait), getString(R.string.dialog_please_wait));
            mCustomProgressDialog.show(UserOrderDetails.this);
        }

        DAOUserOrderDetails.getInstance().Callresponse("", orderKey, AppSharedValues.getLanguage(), new Callback<DAOUserOrderDetails.UserOrderDetails>() {

            @Override
            public void success(DAOUserOrderDetails.UserOrderDetails userOrderDetails, Response response) {
                if (userOrderDetails.getHttpcode().equals(200)) {

                    if (userOrderDetails.getData().get(0) != null) {
                        uodAdapter = new UserOrderDetailsAdapter(UserOrderDetails.this, bus, userOrderDetails.getData().get(0).getItems());
                        lvDetails.setAdapter(uodAdapter);
                        uodAdapter.notifyChanges();

                        Common.setListViewHeightBasedOnChildren(lvDetails);

                        LoadImages.show(UserOrderDetails.this, ivRestaurantLogo, userOrderDetails.getData().get(0).getShop_logo());

                        if (userOrderDetails.getData().get(0).getMap_tracking().equals("0")) {
                            btnLiveTracking.setVisibility(View.GONE);
                        } else {
                            btnLiveTracking.setVisibility(View.VISIBLE);
                        }

                        order_id=userOrderDetails.getData().get(0).getOrder_id();

                        custmoerlat = userOrderDetails.getData().get(0).getCustomer_latitude();
                        customerlon = userOrderDetails.getData().get(0).getCustomer_longitude();
                        vendorlat = userOrderDetails.getData().get(0).getShop_latitude();
                        vendorlon = userOrderDetails.getData().get(0).getShop_longitude();


                        tvRestaurantName.setText(userOrderDetails.getData().get(0).getShop_name());
                        tvAddress.setText("");
                        tvRestaurantCuisines.setText("");


                        if (userOrderDetails.getData().get(0).getPromocode_amount() > 0) {
                            tvCouponValue.setText("(" + Common.getPriceWithCurrencyCode(AppConfig.decimalFormat.format(userOrderDetails.getData().get(0).getPromocode_amount())) + ")");
                            trCouponValue.setVisibility(View.VISIBLE);
                            tvCouponValue.setTypeface(Typeface.DEFAULT);
                        }

                        tvBilltotal.setText(Common.getPriceWithCurrencyCode(AppConfig.decimalFormat.format(userOrderDetails.getData().get(0).getSub_total())));
                        tvBilltotal.setTypeface(Typeface.DEFAULT);

                        tvDeliveryCharges.setText(Common.getPriceWithCurrencyCode(AppConfig.decimalFormat.format(userOrderDetails.getData().get(0).getDelivery_fee())));
                        tvDeliveryCharges.setTypeface(Typeface.DEFAULT);
                        tvDeliveryChargesExtra.setText("");

                        tvServiceTaxText.setText(getString(R.string.txt_service_tax) + "(" + userOrderDetails.getData().get(0).getService_tax_percentage().toString() + "%)");
                        tvServiceTax.setText(Common.getPriceWithCurrencyCode(AppConfig.decimalFormat.format(userOrderDetails.getData().get(0).getService_tax()).toString()));
                        tvServiceTax.setTypeface(Typeface.DEFAULT);

                        tvVATText.setText(getString(R.string.txt_vat) + "(" + userOrderDetails.getData().get(0).getVat_percentage().toString() + "%)");
                        tvVAT.setText(Common.getPriceWithCurrencyCode(AppConfig.decimalFormat.format(userOrderDetails.getData().get(0).getVat()).toString()));
                        tvVAT.setTypeface(Typeface.DEFAULT);

                        tvPackageCharges.setText(Common.getPriceWithCurrencyCode(userOrderDetails.getData().get(0).getPackage_price().toString()));
                        tvPackageCharges.setTypeface(Typeface.DEFAULT);

                        tvGrandTotal.setText(Common.getPriceWithCurrencyCode(userOrderDetails.getData().get(0).getOrder_total().toString()));
                        tvGrandTotal.setTypeface(Typeface.DEFAULT);

                        tvDate.setText(userOrderDetails.getData().get(0).getOrder_datetime().toString());
                        tvStatus.setText(userOrderDetails.getData().get(0).getDelivery_status().toString());
                        tvPaymentStatus.setText(userOrderDetails.getData().get(0).getPayment_status().toString());
                    }
                }

                if (mCustomProgressDialog.isShowing()) {
                    mCustomProgressDialog.dismiss();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(UserOrderDetails.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                if (mCustomProgressDialog.isShowing()) {
                    mCustomProgressDialog.dismiss();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_restaurant_profile, menu);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void onEvent(final DummyEvent ev) {
        runOnUiThread(new Runnable() {
            public void run() {

            }
        });
    }
}