package com.foodpurby.screens;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.foodpurby.R;
import com.foodpurby.adapters.AddressAdapter;
import com.foodpurby.adapters.OutletAdapter;
import com.foodpurby.baseclass.MyActivity;
import com.foodpurby.events.AddRemoveToCartEvent;
import com.foodpurby.events.SigninEvent;
import com.foodpurby.model.DAOVendorOutlet;
import com.foodpurby.util.Common;
import com.foodpurby.util.CustomProgressDialog;
import com.foodpurby.utillities.AppConfig;
import com.foodpurby.utillities.AppSharedValues;
import com.foodpurby.utillities.CartDeliveryDetails;
import com.foodpurby.utillities.UserDetails;
import com.sloop.fonts.FontsManager;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MyAddress extends AppCompatActivity {

    private EventBus bus = EventBus.getDefault();
    private CustomProgressDialog mCustomProgressDialog;

    private ListView lvAddress;
    private AddressAdapter mpAdapter;
    Toolbar mToolbar;
    TextView mToolHeading;
    TextView btnAddNew;
    TextView tvCart;
    ImageView imgCart;
    ArrayList<DAOVendorOutlet.Vendor_outlet> mVendorList;
    OutletAdapter mOutletAdapter;
    TextView tvAddressName;


    LinearLayout llMyAddress, llNewUser, llCollect;
    Common mCommon;

    TextView btnSignin;
    FrameLayout mShowCart;
    Button btnPaymentMethod;
    EditText vFirstName, vLastName, vEmail, vMobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_my_address);

      //  FontsManager.initFormAssets(this, "Lato-Light.ttf");
      //  FontsManager.changeFonts(this);

        mCustomProgressDialog = CustomProgressDialog.getInstance();
        mCommon = new Common(MyAddress.this);

        llMyAddress = (LinearLayout) findViewById(R.id.llMyAddress);
        llNewUser = (LinearLayout) findViewById(R.id.llNewUser);
        llCollect = (LinearLayout) findViewById(R.id.ll_collect);
        btnPaymentMethod = (Button) findViewById(R.id.btnPaymentMethod);
        vFirstName = (EditText) findViewById(R.id.et_firstname);
        vLastName = (EditText) findViewById(R.id.et_lastname);
        vEmail = (EditText) findViewById(R.id.et_email);
        vMobile = (EditText) findViewById(R.id.et_mobile);

        mToolbar = (Toolbar) findViewById(R.id.tb_header);
        mToolbar.setNavigationIcon(R.drawable.back);
        mToolHeading = (TextView) findViewById(R.id.tool_title);
        mToolHeading.setText(R.string.txt_delivery_address);
        tvAddressName = (TextView) findViewById(R.id.addressbook);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mShowCart = (FrameLayout) findViewById(R.id.flCart);
        mShowCart.setVisibility(View.VISIBLE);

        imgCart = (ImageView) findViewById(R.id.imgCart);
        imgCart.setVisibility(View.GONE);
        tvCart = (TextView) findViewById(R.id.tvCart);
        //tvCart.setVisibility(View.GONE);
        tvCart.setText(Common.getPriceWithCurrencyCode(AppConfig.decimalFormat.format(AppSharedValues.getGrandTotalPrice())));
        tvCart.setTypeface(Typeface.DEFAULT);

        btnAddNew = (TextView) findViewById(R.id.btnAddNew);
        lvAddress = (ListView) findViewById(R.id.lvAddress);

        btnSignin = (TextView) findViewById(R.id.btnSignin);

        if (!UserDetails.getCustomerKey().isEmpty()) {
            llNewUser.setVisibility(View.GONE);

            if (CartDeliveryDetails.getCartDeliveryMethod() == CartDeliveryDetails.DeliveryMethod.Collect) {

            /*    tvAddressName.setText("Choose Outlet");
                btnAddNew.setVisibility(View.INVISIBLE);
                llCollect.setVisibility(View.GONE);
                llMyAddress.setVisibility(View.VISIBLE);
                getVendorOutlets();
                mToolHeading.setText(R.string.txt_select_outlet);*/
                /*tvAddressName.setText("Delivery Address");
                llCollect.setVisibility(View.GONE);
                llMyAddress.setVisibility(View.VISIBLE);
                mpAdapter = new AddressAdapter(MyAddress.this, "0");
                lvAddress.setAdapter(mpAdapter);
                mToolHeading.setText(R.string.txt_delivery_address);*/
                Intent intent = new Intent(MyAddress.this, MyPaymentMethod.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                MyAddress.this.startActivity(intent);

            } else if (CartDeliveryDetails.getCartDeliveryMethod() == CartDeliveryDetails.DeliveryMethod.Deliver) {
                tvAddressName.setText("Delivery Address");
                llCollect.setVisibility(View.GONE);
                llMyAddress.setVisibility(View.VISIBLE);
                mpAdapter = new AddressAdapter(MyAddress.this, "0");
                lvAddress.setAdapter(mpAdapter);
                mToolHeading.setText(R.string.txt_delivery_address);
            } else if (CartDeliveryDetails.getCartDeliveryMethod() == CartDeliveryDetails.DeliveryMethod.Dinein) {
                tvAddressName.setText("Delivery Address");
                llCollect.setVisibility(View.GONE);
                llMyAddress.setVisibility(View.VISIBLE);
                mpAdapter = new AddressAdapter(MyAddress.this, "0");
                lvAddress.setAdapter(mpAdapter);
                mToolHeading.setText(R.string.txt_delivery_address);
            }
        } else {
            llCollect.setVisibility(View.GONE);
            llMyAddress.setVisibility(View.GONE);
            llNewUser.setVisibility(View.VISIBLE);
        }

        if (CartDeliveryDetails.getCartDeliveryMethod() == CartDeliveryDetails.DeliveryMethod.Collect) {
            mToolHeading.setText(R.string.txt_select_outlet);

        } else if (CartDeliveryDetails.getCartDeliveryMethod() == CartDeliveryDetails.DeliveryMethod.Deliver) {
            mToolHeading.setText(R.string.txt_billing_and_delivery_address);
        }


        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppSharedValues.SignupStatus = "1";
                MyApplication.getInstance().trackEvent("Button", "Click", "Sigin");
                MyActivity.DisplayUserSignInActivity(MyAddress.this);
            }
        });

        btnPaymentMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyActivity.DisplayMyPaymentMethod(MyAddress.this, "");

                //MyActivity.DisplayMyPaymentMethod(MyAddress.this, "");
            }
        });

        notifyChanges();

        btnAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.getInstance().trackEvent("Button", "Click", "Add New");
                MyActivity.DisplayNewAddress(MyAddress.this);
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

    private void notifyChanges() {
        //btnAddress.setText("Pay order total " + Common.getPriceWithCurrencyCode(DBActionsCart.getTotalCartPriceIncludingTax(AppSharedValues.getSelectedRestaurantBranchKey()).toString()));
    }

    @Override
    protected void onResume() {
        super.onResume();

        vFirstName.setText(UserDetails.getCustomerName());
        vLastName.setText(UserDetails.getCustomerLastName());
        vEmail.setText(UserDetails.getCustomerEmail());
        vMobile.setText(UserDetails.getCustomerMobile());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void onEvent(final SigninEvent ev) {
        runOnUiThread(new Runnable() {
            public void run() {

                if (!UserDetails.getCustomerKey().isEmpty()) {
                    llNewUser.setVisibility(View.GONE);

                    if (CartDeliveryDetails.getCartDeliveryMethod() == CartDeliveryDetails.DeliveryMethod.Collect) {

                        /*tvAddressName.setText("Choose Outlet");
                        btnAddNew.setVisibility(View.INVISIBLE);
                        llCollect.setVisibility(View.GONE);
                        llMyAddress.setVisibility(View.VISIBLE);
                        getVendorOutlets();
                        mToolHeading.setText(R.string.txt_select_outlet);*/
                        tvAddressName.setText("Delivery Address");
                        llCollect.setVisibility(View.GONE);
                        llMyAddress.setVisibility(View.VISIBLE);
                        mpAdapter = new AddressAdapter(MyAddress.this, "0");
                        lvAddress.setAdapter(mpAdapter);
                        mToolHeading.setText(R.string.txt_delivery_address);

                    } else if (CartDeliveryDetails.getCartDeliveryMethod() == CartDeliveryDetails.DeliveryMethod.Deliver) {
                        tvAddressName.setText("Delivery Address");
                        llCollect.setVisibility(View.GONE);
                        llMyAddress.setVisibility(View.VISIBLE);
                        mpAdapter = new AddressAdapter(MyAddress.this, "0");
                        lvAddress.setAdapter(mpAdapter);
                        mToolHeading.setText(R.string.txt_delivery_address);
                    }

                } else if (CartDeliveryDetails.getCartDeliveryMethod() == CartDeliveryDetails.DeliveryMethod.Dinein) {


                    tvAddressName.setText("Delivery Address");
                    llCollect.setVisibility(View.GONE);
                    llMyAddress.setVisibility(View.VISIBLE);
                    mpAdapter = new AddressAdapter(MyAddress.this, "0");
                    lvAddress.setAdapter(mpAdapter);

                    mToolHeading.setText(R.string.txt_delivery_address);
                } else {
                    llCollect.setVisibility(View.GONE);
                    llMyAddress.setVisibility(View.GONE);
                    llNewUser.setVisibility(View.VISIBLE);
                }


            }
        });


    }

    private void getVendorOutlets() {

        if (!mCustomProgressDialog.isShowing()) {
            mCustomProgressDialog.ChageMessage(MyAddress.this, "Please wait...", "Please wait...");
            mCustomProgressDialog.show(MyAddress.this);
        }

        DAOVendorOutlet.getInstance().VendorOutletResponse(AppSharedValues.getSelectedRestaurantBranchKey(), new Callback<DAOVendorOutlet.Outlets>() {
            @Override
            public void success(DAOVendorOutlet.Outlets outlets, Response response) {
                mCustomProgressDialog.dismiss();

                if (outlets.getHttpcode().equals("200")) {
                    mVendorList = new ArrayList<DAOVendorOutlet.Vendor_outlet>();
                    for (int count = 0; count < outlets.getData().getVendor_outlets().size(); count++) {
                        mVendorList.add(outlets.getData().getVendor_outlets().get(count));
                    }
                    mOutletAdapter = new OutletAdapter(MyAddress.this, mVendorList);
                    lvAddress.setAdapter(mOutletAdapter);
                }

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }
}
