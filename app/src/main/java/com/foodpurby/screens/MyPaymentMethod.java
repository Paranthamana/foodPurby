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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ipay.Ipay;
import com.ipay.IpayPayment;
import com.sloop.fonts.FontsManager;
import com.foodpurby.R;
import com.foodpurby.baseclass.MyActivity;
import com.foodpurby.events.AddRemoveToCartEvent;
import com.foodpurby.model.DAOOnlineResponse;
import com.foodpurby.model.DAOPaymentCOD;
import com.foodpurby.model.DAOPaymentOnline;
import com.foodpurby.model.DAOPaymentWallet;
import com.foodpurby.ondbstorage.DBActionsRestaurantBranch;
import com.foodpurby.ondbstorage.RestaurantBranch;
import com.foodpurby.util.Common;
import com.foodpurby.util.CustomProgressDialog;
import com.foodpurby.utillities.AppConfig;
import com.foodpurby.utillities.AppSharedValues;
import com.foodpurby.utillities.CartDeliveryDetails;
import com.foodpurby.utillities.SessionManager;
import com.foodpurby.utillities.UserDetails;

import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MyPaymentMethod extends AppCompatActivity {

    private EventBus bus = EventBus.getDefault();
    private CustomProgressDialog mCustomProgressDialog;

    private ListView lvAddress;
    Toolbar mToolbar;
    TextView mToolHeading;

    ImageView imgCart;
    TextView tvCart;

    RadioGroup rgOptions;
    RadioButton rbOnline, rbWallet;
    RadioButton rbCOD;

    Button btnGo;
    FrameLayout mShowCart;
    String MerchantKey = "T5dABreBtF";
    String MerchantCode = "M05437";

    public static String resultTitle = "";
    public static String resultCode = "";
    public static String resultInfo = "";
    public static String resultExtra = "";

    public static String TransId;
    public static String RefNo;
    public static String Amount;
    public static String Remark;
    public static String AuthCode;
    public static String ErrDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_my_payment);

       // FontsManager.initFormAssets(this, "Lato-Light.ttf");
       // FontsManager.changeFonts(this);

        mCustomProgressDialog = CustomProgressDialog.getInstance();

        mToolbar = (Toolbar) findViewById(R.id.tb_header);
        mToolbar.setNavigationIcon(R.drawable.back);
        mToolHeading = (TextView) findViewById(R.id.tool_title);
        mToolHeading.setText(R.string.txt_payment_methods);

        setSupportActionBar(mToolbar);

        btnGo = (Button) findViewById(R.id.btnGo);
        imgCart = (ImageView) findViewById(R.id.imgCart);
        imgCart.setVisibility(View.GONE);
        tvCart = (TextView) findViewById(R.id.tvCart);
        tvCart.setText(Common.getPriceWithCurrencyCode(String.valueOf(AppSharedValues.getGrandTotalPrice())));
        tvCart.setTypeface(Typeface.DEFAULT);

        mShowCart = (FrameLayout) findViewById(R.id.flCart);
        mShowCart.setVisibility(View.VISIBLE);

        TextView tvCart = (TextView) mToolbar.findViewById(R.id.tvCart);
        tvCart.setText(Common.getPriceWithCurrencyCode(AppConfig.decimalFormat.format(AppSharedValues.getGrandTotalPrice())));
        tvCart.setTypeface(Typeface.DEFAULT);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        rgOptions = (RadioGroup) findViewById(R.id.rgOptions);
        rbOnline = (RadioButton) findViewById(R.id.rbOnline);
        rbWallet = (RadioButton) findViewById(R.id.rbWallet);
        rbCOD = (RadioButton) findViewById(R.id.rbCOD);

        //rbOnline.setText(getString(R.string.prefix_payment_online) + AppSharedValues.getOnlinePaymentGatewayName() + ")");
        RestaurantBranch restaurantBranch = DBActionsRestaurantBranch.get(AppSharedValues.getSelectedRestaurantBranchKey());
        if (restaurantBranch != null) {
            /*rbCOD.setVisibility(View.VISIBLE);
            rbOnline.setVisibility(View.VISIBLE);
            rbWallet.setVisibility(View.VISIBLE);*/
            if (restaurantBranch.getRestaurantPaymentTypes().equals(0)) {
                rbCOD.setVisibility(View.VISIBLE);
                rbOnline.setVisibility(View.GONE);
            } else if (restaurantBranch.getRestaurantPaymentTypes().equals(1)) {
                rbCOD.setVisibility(View.GONE);
                rbOnline.setVisibility(View.VISIBLE);
            } else if (restaurantBranch.getRestaurantPaymentTypes().equals(2)) {
                rbOnline.setVisibility(View.VISIBLE);
                rbCOD.setVisibility(View.VISIBLE);
            }

            if (Double.parseDouble(SessionManager.getInstance().getcreditpoint(MyPaymentMethod.this)) > AppSharedValues.getGrandTotalPrice()) {
                rbWallet.setVisibility(View.VISIBLE);
            } else {
                rbWallet.setVisibility(View.GONE);
            }
            rbOnline.setVisibility(View.GONE);
        }

        CartDeliveryDetails.setCartPaymentMethod(CartDeliveryDetails.PaymentMethod.Online);
        rgOptions.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.rbOnline) {
                    MyApplication.getInstance().trackEvent("Checkbox", "Click", "Online Payment");
                    CartDeliveryDetails.setCartPaymentMethod(CartDeliveryDetails.PaymentMethod.Online);
                } else if (checkedId == R.id.rbCOD) {
                    MyApplication.getInstance().trackEvent("Checkbox", "Click", "COD Payment");
                    CartDeliveryDetails.setCartPaymentMethod(CartDeliveryDetails.PaymentMethod.COD);
                } else if (checkedId == R.id.rbWallet) {
                    MyApplication.getInstance().trackEvent("Checkbox", "Click", "COD Payment");
                    CartDeliveryDetails.setCartPaymentMethod(CartDeliveryDetails.PaymentMethod.WALLET);
                }

            }

        });


        if (btnGo != null) {

            btnGo.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            int id = rgOptions.getCheckedRadioButtonId();
                            if (id == -1) {
                                Toast.makeText(MyPaymentMethod.this, R.string.toast_select_payment_method, Toast.LENGTH_SHORT).show();
                            } else if (id == R.id.rbOnline) {
                                MyApplication.getInstance().trackEvent("Button", "Click", "Go Online");
                                if (!mCustomProgressDialog.isShowing()) {
                                    mCustomProgressDialog.ChageMessage(MyPaymentMethod.this, "Please wait...", "Please wait...");
                                    mCustomProgressDialog.show(MyPaymentMethod.this);
                                }


                                String mSelectedDate = "";
                                String mSelectedTime = "";

                                if (SessionManager.getInstance().getDateTimeSelected(MyPaymentMethod.this).equals("ASAP")) {
                                    mSelectedDate = "";
                                    mSelectedTime = "";
                                } else {
                                    String arr[] = SessionManager.getInstance().getDateTimeSelected(MyPaymentMethod.this).split(" ");
                                    mSelectedDate = arr[0];
                                    mSelectedTime = arr[1];
                                }


                                DAOPaymentOnline.getInstance().Callresponse("tokenkey", AppSharedValues.getLanguage(), CartDeliveryDetails.getCartSelectedAddressKey(), UserDetails.getCustomerKey(),
                                        AppSharedValues.getSelectedRestaurantBranchKey(),
                                        Integer.parseInt(SessionManager.getInstance().getDeliveryMethod(MyPaymentMethod.this)), 1,
                                        SessionManager.getInstance().getDeviceToken(MyPaymentMethod.this), "1", mSelectedDate, mSelectedTime, new Callback<DAOPaymentOnline.OnlinePaymentResponse>() {

                                            @Override
                                            public void success(DAOPaymentOnline.OnlinePaymentResponse onlinePaymentResponse, Response response) {

                                                if (onlinePaymentResponse.getHttpcode().equals("200")) {
                                                    CartDeliveryDetails.setCartOrderKey(onlinePaymentResponse.getData().getPayment().getOkey());

                                                    SessionManager.getInstance().setCurrentOrderKey(MyPaymentMethod.this, onlinePaymentResponse.getData().getPayment().getOkey());


                                                    IpayPayment payment = new IpayPayment();
                                                    MerchantKey = onlinePaymentResponse.getData().getPayment().getMerchantKey();
                                                    MerchantCode = onlinePaymentResponse.getData().getPayment().getMerchanCode();

                                                    payment.setMerchantCode(onlinePaymentResponse.getData().getPayment().getMerchanCode());
                                                    payment.setMerchantKey(onlinePaymentResponse.getData().getPayment().getMerchantKey());
                                                    payment.setPaymentId("");
                                                    payment.setCurrency("MYR");
                                                    payment.setLang("UTF-8");
                                                    payment.setRemark(onlinePaymentResponse.getData().getPayment().getRemark());
                                                    payment.setRefNo(onlinePaymentResponse.getData().getPayment().getRefNo());
                                                    payment.setAmount(onlinePaymentResponse.getData().getPayment().getAmount());
                                                    payment.setProdDesc(onlinePaymentResponse.getData().getPayment().getProdDesc());
                                                    payment.setUserName(onlinePaymentResponse.getData().getPayment().getUserName());
                                                    payment.setUserEmail(onlinePaymentResponse.getData().getPayment().getUserEmail());
                                                    payment.setUserContact(onlinePaymentResponse.getData().getPayment().getUserContact());
                                                    payment.setCountry("MY");
                                                    payment.setBackendPostURL(onlinePaymentResponse.getData().getPayment().getBackendURL());
                                                    Intent checkoutIntent = Ipay.getInstance().checkout(payment, MyPaymentMethod.this, new PaymentResultDelegate());
                                                    startActivityForResult(checkoutIntent, 1);


                                                    if (mCustomProgressDialog.isShowing()) {
                                                        mCustomProgressDialog.dismiss();
                                                    }
                                                } else {
                                                    if (mCustomProgressDialog.isShowing()) {
                                                        mCustomProgressDialog.dismiss();
                                                    }
                                                    Toast.makeText(MyPaymentMethod.this, onlinePaymentResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            }

                                            @Override
                                            public void failure(RetrofitError error) {
                                                Toast.makeText(MyPaymentMethod.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                                                if (mCustomProgressDialog.isShowing()) {
                                                    mCustomProgressDialog.dismiss();
                                                }
                                            }
                                        }
                                );
                            } else if (id == R.id.rbCOD) {
                                MyApplication.getInstance().trackEvent("Button", "Click", "Go COD");
                                if (!mCustomProgressDialog.isShowing()) {
                                    mCustomProgressDialog.ChageMessage(MyPaymentMethod.this, "Please wait...", "Please wait...");
                                    mCustomProgressDialog.show(MyPaymentMethod.this);
                                }

                                /*if (!AppSharedValues.getCouponCode().isEmpty() && AppSharedValues.getCouponCode() != null) {
                                    mCustomProgressDialog.dismiss();
                                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(MyPaymentMethod.this);
                                    mBuilder.setTitle(getString(R.string.txt_warning)).setMessage(getString(R.string.txt_sorry_voucher) + " " + AppSharedValues.getCouponCode() + " " + getString(R.string.txt_sufix_voucher));
                                    mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                                    mBuilder.show();

                                    //CartDeliveryDetails.getCartSelectedAddressKey(),
                                } else*/
                                {

                                    String mSelectedDate = "";
                                    String mSelectedTime = "";

                                    if (SessionManager.getInstance().getDateTimeSelected(MyPaymentMethod.this).equals("ASAP")) {
                                        mSelectedDate = "";
                                        mSelectedTime = "";
                                    } else {
                                        String arr[] = SessionManager.getInstance().getDateTimeSelected(MyPaymentMethod.this).split(" ");
                                        mSelectedDate = arr[0];
                                        mSelectedTime = arr[1];
                                    }


                                    DAOPaymentCOD.getInstance().Callresponse(
                                            AppSharedValues.getLanguage(),
                                            CartDeliveryDetails.getCartSelectedAddressKey(),
                                            UserDetails.getCustomerKey(),
                                            AppSharedValues.getSelectedRestaurantBranchKey(),
//                                            CartDeliveryDetails.getCartDeliveryMethod().getValue(),
                                            Integer.parseInt(SessionManager.getInstance().getDeliveryMethod(MyPaymentMethod.this)),
                                            2,
                                            SessionManager.getInstance().getDeviceToken(MyPaymentMethod.this),
                                            "1",
                                            mSelectedDate,
                                            mSelectedTime,
                                            "" + SessionManager.getInstance().getselectedmember(MyPaymentMethod.this),
                                            new Callback<DAOPaymentCOD.CODPaymentResponse>() {

                                                @Override
                                                public void success(DAOPaymentCOD.CODPaymentResponse codPaymentResponse, Response response) {
                                                    if (mCustomProgressDialog.isShowing()) {
                                                        mCustomProgressDialog.dismiss();
                                                    }
                                                    if (codPaymentResponse.getHttpcode().equalsIgnoreCase("200")) {
                                                        //arul 15.9
                                                        //CartDeliveryDetails.setCartSelectedAddressKey("");

                                                        CartDeliveryDetails.setCartOrderKey(codPaymentResponse.getData().getPayment().getOkey());
                                                        MyActivity.DisplayPaymentOrderConfirmed(MyPaymentMethod.this, codPaymentResponse.getData().getPayment().getOkey(), codPaymentResponse.getData().getPayment().getOrder_total(), "1", "");
                                                    } else {
                                                        if (mCustomProgressDialog.isShowing()) {
                                                            mCustomProgressDialog.dismiss();
                                                        }
                                                        Toast.makeText(MyPaymentMethod.this, codPaymentResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                                    }

                                                }

                                                @Override
                                                public void failure(RetrofitError error) {
                                                    error.printStackTrace();
                                                    Toast.makeText(MyPaymentMethod.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                                                    if (mCustomProgressDialog.isShowing()) {
                                                        mCustomProgressDialog.dismiss();
                                                    }
                                                }
                                            }
                                    );
                                }


                            } else if (id == R.id.rbWallet) {
                                MyApplication.getInstance().trackEvent("Button", "Click", "Go WAllet");
                                if (!mCustomProgressDialog.isShowing()) {
                                    mCustomProgressDialog.ChageMessage(MyPaymentMethod.this, "Please wait...", "Please wait...");
                                    mCustomProgressDialog.show(MyPaymentMethod.this);
                                }

                                /*if (!AppSharedValues.getCouponCode().isEmpty() && AppSharedValues.getCouponCode() != null) {
                                    mCustomProgressDialog.dismiss();
                                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(MyPaymentMethod.this);
                                    mBuilder.setTitle(getString(R.string.txt_warning)).setMessage(getString(R.string.txt_sorry_voucher) + " " + AppSharedValues.getCouponCode() + " " + getString(R.string.txt_sufix_voucher));
                                    mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                                    mBuilder.show();

                                    //CartDeliveryDetails.getCartSelectedAddressKey(),
                                } else*/
                                {


                                    String mSelectedDate = "";
                                    String mSelectedTime = "";

                                    if (SessionManager.getInstance().getDateTimeSelected(MyPaymentMethod.this).equals("ASAP")) {
                                        mSelectedDate = "";
                                        mSelectedTime = "";
                                    } else {
                                        String arr[] = SessionManager.getInstance().getDateTimeSelected(MyPaymentMethod.this).split(" ");
                                        mSelectedDate = arr[0];
                                        mSelectedTime = arr[1];
                                    }

                                    DAOPaymentWallet.getInstance().Callresponse(AppSharedValues.getLanguage(),
                                            CartDeliveryDetails.getCartSelectedAddressKey(),
                                            UserDetails.getCustomerKey(),
                                            AppSharedValues.getSelectedRestaurantBranchKey(),
//                                            CartDeliveryDetails.getCartDeliveryMethod().getValue(),
                                            Integer.parseInt(SessionManager.getInstance().getDeliveryMethod(MyPaymentMethod.this)),
                                            3,
                                            SessionManager.getInstance().getDeviceToken(MyPaymentMethod.this),
                                            "1",
                                            mSelectedDate,
                                            mSelectedTime,
                                            "" + SessionManager.getInstance().getselectedmember(MyPaymentMethod.this), new Callback<DAOPaymentWallet.CODPaymentResponse>() {
                                                @Override
                                                public void success(DAOPaymentWallet.CODPaymentResponse codPaymentResponse, Response response) {
                                                    if (mCustomProgressDialog.isShowing()) {
                                                        mCustomProgressDialog.dismiss();
                                                    }
                                                    if (codPaymentResponse.getHttpcode().equalsIgnoreCase("200")) {
                                                        //arul 15.9
                                                        //CartDeliveryDetails.setCartSelectedAddressKey("");

                                                        CartDeliveryDetails.setCartOrderKey(codPaymentResponse.getData().getOrder_key());
                                                        SessionManager.getInstance().setcreditpoint(getApplicationContext(), codPaymentResponse.getData().getWallet_points());

                                                        MyActivity.DisplayPaymentOrderConfirmed(MyPaymentMethod.this,
                                                                codPaymentResponse.getData().getOrder_key(),
                                                                codPaymentResponse.getData().getOrder_total() + "",
                                                                "1",
                                                                "");
                                                    } else {
                                                        if (mCustomProgressDialog.isShowing()) {
                                                            mCustomProgressDialog.dismiss();
                                                        }
                                                        Toast.makeText(MyPaymentMethod.this, codPaymentResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                }

                                                @Override
                                                public void failure(RetrofitError error) {
                                                    error.printStackTrace();
                                                    Toast.makeText(MyPaymentMethod.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                                                    if (mCustomProgressDialog.isShowing()) {
                                                        mCustomProgressDialog.dismiss();
                                                    }
                                                }
                                            });


                                }


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
        if (id == android.R.id.home) {
            finish();
            overridePendingTransition(R.anim.anim_left_to_right, R.anim.anim_right_to_left);
        }
        return super.onOptionsItemSelected(item);
    }

    public void onEvent(final AddRemoveToCartEvent ev) {
        runOnUiThread(new Runnable() {
            public void run() {

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();


        if (resultTitle.equals("SUCCESS") || resultTitle.equals("CANCELED") || resultTitle.equals("FAILURE")) {
            Toast.makeText(MyPaymentMethod.this, "" + resultInfo, Toast.LENGTH_SHORT).show();
            final CustomProgressDialog mCustomProgressDialog = new CustomProgressDialog();
            mCustomProgressDialog.show(MyPaymentMethod.this);

            DAOOnlineResponse.getInstance().Callresponse("en", "ewerwerwerr", MerchantCode, "", RefNo, Amount, "MYR", Remark, TransId, AuthCode, resultCode, ErrDesc, "", "", UserDetails.getCustomerKey(), new Callback<DAOOnlineResponse.Tabaoco_credit_Response>() {
                @Override
                public void success(DAOOnlineResponse.Tabaoco_credit_Response tabaoco_credit_response, Response response) {


                    mCustomProgressDialog.dismiss();

                    if (tabaoco_credit_response.getHttpcode().equals("200")) {

                        if (resultTitle.equals("SUCCESS")) {
                            MyActivity.DisplayPaymentOrderConfirmed(MyPaymentMethod.this, tabaoco_credit_response.getData().getOrder_key(), tabaoco_credit_response.getData().getOrder_total(), "1", "");
                        } else {
                            MyActivity.DisplayPaymentOrderConfirmed(MyPaymentMethod.this, "key", "value", "2", resultInfo);
                        }


                    } else {
                        Toast.makeText(MyPaymentMethod.this, tabaoco_credit_response.getMessage(), Toast.LENGTH_SHORT).show();
                        MyActivity.DisplayPaymentOrderConfirmed(MyPaymentMethod.this, "key", "value", "2", resultInfo);
                    }
                    resultTitle = "";
                }

                @Override
                public void failure(RetrofitError error) {
                    Toast.makeText(MyPaymentMethod.this, error.toString(), Toast.LENGTH_SHORT).show();
                    mCustomProgressDialog.dismiss();
                }
            });


        }

    }

}