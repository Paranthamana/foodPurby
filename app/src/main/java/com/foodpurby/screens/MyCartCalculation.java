package com.foodpurby.screens;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.foodpurby.R;
import com.foodpurby.adapters.MyRestaurantDeliveryDates;
import com.foodpurby.adapters.MyRestaurantDeliveryTimes;
import com.foodpurby.baseclass.MyActivity;
import com.foodpurby.events.AddRemoveToCartEvent;
import com.foodpurby.events.SigninEvent;
import com.foodpurby.model.DAOUserOrderCalculateCart;
import com.foodpurby.ondbstorage.DBActionsCart;
import com.foodpurby.util.Common;
import com.foodpurby.util.CustomProgressDialog;
import com.foodpurby.utillities.AppConfig;
import com.foodpurby.utillities.AppSharedValues;
import com.foodpurby.utillities.CartDeliveryDetails;
import com.foodpurby.utillities.SessionManager;
import com.foodpurby.utillities.UserDetails;
import com.sloop.fonts.FontsManager;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.foodpurby.R.id.DMC_collect_close;
import static com.foodpurby.R.id.rbDinein;

public class MyCartCalculation extends AppCompatActivity implements DialogInterface.OnCancelListener {

    private CustomProgressDialog mCustomProgressDialog;
    public static final String TIMEPICKER_TAG = "timepicker";
    private EventBus bus = EventBus.getDefault();

    Double couponCodeValue = 0D;
    Double packageValue = 0D;
    Double vatValue = 0D;
    Double serviceTaxValue = 0D;

    AlertDialog.Builder mDialog;
    AlertDialog mAlertDilaog;

    Toolbar mToolbar;
    TextView tvCart, tvCouponValue;
    RadioGroup rgOptions;
    TableRow trDeliveryCharges, trCouponValue;
    Button btnPay;

    TextView tvItemTotal;
    TextView tvBilltotal;

    TextView tvPackageCharges;
    TextView tvDeliveryCharges;
    TextView tvDeliveryChargesExtra;

    TextView tvServiceTaxText;
    TextView tvServiceTax;
    TextView tvVatText;
    TextView tvVat;

    TextView tvGrandTotal;
    TextView mToolHeading;
    RadioButton rbCollect;
    RadioButton rbDeliver;
    RadioButton rbTable;
    TextView tvCollectDateTime, tvDeliverDateTime;
    TextView tvTableText;
    ImageView imgCart;
    FrameLayout mShowCart;
    TextView tvCouponText, tvCoupon;

    Boolean isChecked = false;
    private TextView tvDineinDateTime;
    private ListView DMC_membercount;
    private Integer members;
    private ImageView DMC_collect_closes;
    private Dialog customdialog;
    private RadioButton rbDineins;
    private Button btnDateASAPs;
    private LinearLayout DMC_layout;
    private RelativeLayout DMC_layout2;
    private Integer selfPick = 0;
    TextInputLayout tilComments;
    EditText etComments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_my_cart_calculation);

        //  FontsManager.initFormAssets(this, "Lato-Light.ttf");
        // FontsManager.changeFonts(this);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        mCustomProgressDialog = CustomProgressDialog.getInstance();

        AppSharedValues.setCouponCode("");
        AppSharedValues.setCartDeliveryMethod(AppSharedValues.DeliveryMethod.None);
        CartDeliveryDetails.setCartDeliveryMethod(CartDeliveryDetails.DeliveryMethod.None);

        LinearLayout llOfferStickyView = (LinearLayout) findViewById(R.id.llOfferStickyView);
        TextView tvOffer = (TextView) findViewById(R.id.tvOffer);
        if (!AppSharedValues.getSelectedRestaurantBranchOfferCode().trim().isEmpty()) {
            llOfferStickyView.setVisibility(View.VISIBLE);
            tvOffer.setText(AppSharedValues.getSelectedRestaurantBranchOfferText() + getString(R.string.txt_on_orders_above) + Common.getPriceWithCurrencyCode(AppConfig.decimalFormat.format(AppSharedValues.getSelectedRestaurantBranchOfferMinOrderValue())) + ". Use Code " + AppSharedValues.getSelectedRestaurantBranchOfferCode());
            tvOffer.setTypeface(Typeface.DEFAULT);
        } else {
            llOfferStickyView.setVisibility(View.GONE);
        }

        mToolbar = (Toolbar) findViewById(R.id.tb_header);
        mToolHeading = (TextView) findViewById(R.id.tool_title);
        mToolbar.setNavigationIcon(R.drawable.back);
        mToolHeading.setText(R.string.txt_select_delivery_method);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnPay = (Button) findViewById(R.id.btnPay);
        btnPay.setTag("success");


        trCouponValue = (TableRow) findViewById(R.id.trCouponValue);
        tvCouponValue = (TextView) findViewById(R.id.tvCouponValue);

        trDeliveryCharges = (TableRow) findViewById(R.id.trDeliveryCharges);
        tvItemTotal = (TextView) findViewById(R.id.tvItemTotal);
        tvBilltotal = (TextView) findViewById(R.id.tvBilltotal);
        tvPackageCharges = (TextView) findViewById(R.id.tvPackageCharges);
        tvDeliveryCharges = (TextView) findViewById(R.id.tvDeliveryCharges);
        tvDeliveryChargesExtra = (TextView) findViewById(R.id.tvDeliveryChargesExtra);
        tvGrandTotal = (TextView) findViewById(R.id.tvGrandTotal);
        etComments = (EditText) findViewById(R.id.etComments);
        tilComments = (TextInputLayout) findViewById(R.id.tilComments);
        tilComments.setHint("Order Notes");
        mShowCart = (FrameLayout) findViewById(R.id.flCart);
        mShowCart.setVisibility(View.VISIBLE);

        imgCart = (ImageView) findViewById(R.id.imgCart);
        imgCart.setVisibility(View.GONE);
        tvCart = (TextView) findViewById(R.id.tvCart);
        //tvCart.setVisibility(View.GONE);
        tvCart.setText(Common.getPriceWithCurrencyCode(String.valueOf(AppSharedValues.getGrandTotalPrice())));
        tvCart.setTypeface(Typeface.DEFAULT);


        tvServiceTaxText = (TextView) findViewById(R.id.tvServiceTaxText);
        tvServiceTax = (TextView) findViewById(R.id.tvServiceTax);
        tvVatText = (TextView) findViewById(R.id.tvVatText);
        tvVat = (TextView) findViewById(R.id.tvVat);

        rgOptions = (RadioGroup) findViewById(R.id.rgOptions);
        rbCollect = (RadioButton) findViewById(R.id.rbCollect);
        rbDeliver = (RadioButton) findViewById(R.id.rbDeliver);
        rbDineins = (RadioButton) findViewById(rbDinein);
        rbTable = (RadioButton) findViewById(R.id.rbTable);

        tvCouponText = (TextView) findViewById(R.id.tvCouponText);
        tvCoupon = (TextView) findViewById(R.id.tvCoupon);

        tvCollectDateTime = (TextView) findViewById(R.id.tvCollectDateTime);
        tvCollectDateTime.setText("");

        tvDeliverDateTime = (TextView) findViewById(R.id.tvDeliverDateTime);
        tvDeliverDateTime.setText("");

        tvDineinDateTime = (TextView) findViewById(R.id.tvDineinDateTime);
        tvDineinDateTime.setText("");

        tvTableText = (TextView) findViewById(R.id.tvTableText);
        tvTableText.setText("");


        if (!UserDetails.getCustomerKey().isEmpty()) {
            BindCouponOnClick();
        } else {
            tvCouponText.setTextColor(getResources().getColor(R.color.toolbar_color));
            tvCouponText.setText(R.string.txt_pls_login_to_apply_coupon);
            tvCouponText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyActivity.DisplayUserSignInActivity(MyCartCalculation.this);
                }
            });
        }

        rgOptions.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (!isChecked) {
                    rbButtonClick(checkedId);
                } else {
                    isChecked = false;
                }
            }

        });

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Common.IsInternetConnected(MyCartCalculation.this)) {
                    String orderNote = etComments.getText().toString();
                    if (!orderNote.isEmpty()) {
                        AppSharedValues.setOrderNotes(orderNote);
                    } else {
                        AppSharedValues.setOrderNotes("");
                    }
                    MyApplication.getInstance().trackEvent("Button", "Click", "Pay");
                    if (btnPay.getTag().toString().equalsIgnoreCase("success")) {
                        if (AppSharedValues.getCartDeliveryMethod() == AppSharedValues.DeliveryMethod.Collect) {
                            //MyActivity.DisplayMyPaymentMethod(MyCartCalculation.this, "");
                            if (!tvCollectDateTime.getText().toString().isEmpty()) {
//                                MyActivity.DisplayMyAddress(MyCartCalculation.this);
                                MyActivity.DisplayMyPaymentMethod(MyCartCalculation.this, "");
                                SessionManager.getInstance().setDateTimeSelected(MyCartCalculation.this, tvCollectDateTime.getText().toString());

                            /*Intent in=new Intent (getApplicationContext (),OutletActivity.class);
                            startActivity (in);*/
                            } else {
                                Toast.makeText(MyCartCalculation.this, R.string.txt_pls_select_delivery_method, Toast.LENGTH_SHORT).show();
                            }

                        } else if (AppSharedValues.getCartDeliveryMethod() == AppSharedValues.DeliveryMethod.Delivery) {
                            if (!tvDeliverDateTime.getText().toString().isEmpty() || !tvDineinDateTime.getText().toString().isEmpty()) {
                                MyActivity.DisplayMyAddress(MyCartCalculation.this);


                                SessionManager.getInstance().setDateTimeSelected(MyCartCalculation.this, tvDeliverDateTime.getText().toString());

                            } else {
                                Toast.makeText(MyCartCalculation.this, R.string.txt_pls_select_delivery_method, Toast.LENGTH_SHORT).show();
                            }

                        } else if (AppSharedValues.getCartDeliveryMethod() == AppSharedValues.DeliveryMethod.Dinein) {
                            if (!tvDineinDateTime.getText().toString().isEmpty()) {
                                MyActivity.DisplayMyAddress(MyCartCalculation.this);
                                SessionManager.getInstance().setDateTimeSelected(MyCartCalculation.this, tvDineinDateTime.getText().toString());
                            } else {
//                            MyActivity.DisplayMyAddress(MyCartCalculation.this);
                                Toast.makeText(MyCartCalculation.this, R.string.txt_pls_select_delivery_method, Toast.LENGTH_SHORT).show();
                            }

                        } else if (AppSharedValues.getCartDeliveryMethod() == AppSharedValues.DeliveryMethod.DeliveryToMyTable) {
                            Toast.makeText(MyCartCalculation.this, R.string.toast_to_my_table, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MyCartCalculation.this, R.string.txt_pls_select_delivery_method, Toast.LENGTH_SHORT).show();

                        }
                    } else {
                        Toast.makeText(MyCartCalculation.this, R.string.toast_error_while_calculating_cart, Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(MyCartCalculation.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

        notifyChanges();

        GetDetails();
        /*if (selfPick == 1) {
            rbDineins.setVisibility(View.VISIBLE);
        } else {
            rbDineins.setVisibility(View.GONE);
        }*/

    }

    private void rbButtonClick(int checkedId) {
        rbCollect.setText(R.string.txt_collect);
        if (checkedId == R.id.rbCollect) {
            MyApplication.getInstance().trackEvent("checkbox", "Click", "Collect");
            tvCollectDateTime.setText("");
            tvDeliverDateTime.setText("");
            tvTableText.setText("");
            CollectDeliver(tvCollectDateTime, AppSharedValues.DeliveryMethod.Collect);
            trDeliveryCharges.setVisibility(View.GONE);
            btnPay.setText(R.string.txt_payment_methods);
//            btnPay.setText(R.string.txt_select_billing_address);
            btnPay.setTextColor(getResources().getColor(R.color.white));
            //btnPay.setBackgroundColor(getResources().getColor(R.color.toolbar_color));
            AppSharedValues.setCartDeliveryMethod(AppSharedValues.DeliveryMethod.Collect);
            CartDeliveryDetails.setCartDeliveryMethod(CartDeliveryDetails.DeliveryMethod.Collect);
            SessionManager.getInstance().setDeliveryMethod(MyCartCalculation.this, "2");
            CalcualteCost();
        } else if (checkedId == R.id.rbDeliver) {
            MyApplication.getInstance().trackEvent("Checkbox", "Click", "Deliver");
            tvCollectDateTime.setText("");
            tvDeliverDateTime.setText("");
            tvTableText.setText("");
            rbDineins.setChecked(false);
            tvDineinDateTime.setText("");
            CollectDeliver(tvDeliverDateTime, AppSharedValues.DeliveryMethod.Delivery);
            trDeliveryCharges.setVisibility(View.VISIBLE);
            btnPay.setText(R.string.txt_select_delivery_address);
            btnPay.setTextColor(getResources().getColor(R.color.white));
            //btnPay.setBackgroundColor(getResources().getColor(R.color.toolbar_color));
            AppSharedValues.setCartDeliveryMethod(AppSharedValues.DeliveryMethod.Delivery);
            CartDeliveryDetails.setCartDeliveryMethod(CartDeliveryDetails.DeliveryMethod.Deliver);
            SessionManager.getInstance().setDeliveryMethod(MyCartCalculation.this, "1");
            CalcualteCost();
        } else if (checkedId == rbDinein) {
            SessionManager.getInstance().setDeliveryMethod(MyCartCalculation.this, "3");
            //CollectDeliver1();
            SessionManager.getInstance().setselectedmember(MyCartCalculation.this, 0);

            MyApplication.getInstance().trackEvent("Checkbox", "Click", "Dinein");
            tvCollectDateTime.setText("");
            tvDeliverDateTime.setText("");
            tvDineinDateTime.setText("");
            tvTableText.setText("");
            CollectDeliver(tvDineinDateTime, AppSharedValues.DeliveryMethod.Dinein);
            trDeliveryCharges.setVisibility(View.VISIBLE);
            btnDateASAPs.setVisibility(View.GONE);
            DMC_layout.setVisibility(View.GONE);
            DMC_layout2.setVisibility(View.GONE);
            btnPay.setText(R.string.txt_select_delivery_address);
            btnPay.setTextColor(getResources().getColor(R.color.white));
            //btnPay.setBackgroundColor(getResources().getColor(R.color.toolbar_color));
            AppSharedValues.setCartDeliveryMethod(AppSharedValues.DeliveryMethod.Dinein);
            CartDeliveryDetails.setCartDeliveryMethod(CartDeliveryDetails.DeliveryMethod.Dinein);
            CalcualteCost();
            //customdialog.dismiss();

        } else if (checkedId == R.id.rbTable) {
            MyApplication.getInstance().trackEvent("Checkbox", "Click", "Table");
            tvCollectDateTime.setText("");
            tvDeliverDateTime.setText("");
            tvTableText.setText("");
            mDialog = new AlertDialog.Builder(MyCartCalculation.this, R.style.MyAlertDialogStyle);
            final View vDialog = getLayoutInflater().inflate(R.layout.dialog_mytable, null);
            //  FontsManager.changeFonts(vDialog);

            mDialog.setView(vDialog);
            mAlertDilaog = mDialog.create();
            mAlertDilaog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            mAlertDilaog.show();
            mAlertDilaog.setOnKeyListener(new Dialog.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface arg0, int keyCode, KeyEvent event) {
                    // TODO Auto-generated method stub
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        rbCollect.setSelected(false);
                        rbDeliver.setSelected(false);
                        rbTable.setSelected(false);
                    }
                    return true;
                }
            });
            final Button btnAddTable = (Button) vDialog.findViewById(R.id.btnAddTable);
            final EditText etTableNo = (EditText) vDialog.findViewById(R.id.etTableNo);
            etTableNo.addTextChangedListener(new TextWatcher() {

                public void afterTextChanged(Editable s) {
                }

                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (etTableNo.getText().toString().trim().length() > 0) {
                        btnAddTable.setBackgroundColor(getResources().getColor(R.color.dark_green));
                        btnAddTable.setText(getString(R.string.txt_table) + etTableNo.getText().toString());
                    } else {
                        btnAddTable.setBackgroundColor(getResources().getColor(R.color.red));
                        btnAddTable.setText(R.string.txt_cancel);
                    }
                }
            });

            btnAddTable.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        tvTableText.setText(etTableNo.getText().toString().trim());
                        if (mAlertDilaog.isShowing()) {
                            mAlertDilaog.dismiss();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            btnPay.setText(R.string.txt_deliver_to_my_table);
            btnPay.setTextColor(getResources().getColor(R.color.white));
            btnPay.setBackgroundColor(getResources().getColor(R.color.toolbar_color));

            AppSharedValues.setCartDeliveryMethod(AppSharedValues.DeliveryMethod.Dinein);
            CartDeliveryDetails.setCartDeliveryMethod(CartDeliveryDetails.DeliveryMethod.Dinein);
            trDeliveryCharges.setVisibility(View.GONE);
            SessionManager.getInstance().setDeliveryMethod(MyCartCalculation.this, "3");
            CalcualteCost();
        }

    }

    private void GetDetails() {
        if (!mCustomProgressDialog.isShowing()) {
            mCustomProgressDialog.ChageMessage(MyCartCalculation.this, "Please wait...", "Please wait...");
            mCustomProgressDialog.show(MyCartCalculation.this);
        }

        DAOUserOrderCalculateCart.getInstance().Callresponse(
                "",
                UserDetails.getCustomerKey(),
                "",
                AppSharedValues.getLanguage(),
                new Callback<DAOUserOrderCalculateCart.CalculateCart>() {

                    @Override
                    public void success(DAOUserOrderCalculateCart.CalculateCart calculateCart, Response response) {

                        if (calculateCart.getHttpcode().equals("200") || calculateCart.getHttpcode() == 200) {

                            //AppSharedValues.setCouponCode("");
                            vatValue = calculateCart.getData().getShop().getVat_price();
                            serviceTaxValue = calculateCart.getData().getShop().getService_tax_amount();
                            packageValue = calculateCart.getData().getShop().getPackage_price();
                            couponCodeValue = calculateCart.getData().getShop().getPromocode_amount();
                            selfPick = calculateCart.getData().getShop().getSelf_pickup();
                            /*if (selfPick == 1) {
                                rbDineins.setVisibility(View.VISIBLE);
                            } else {
                                rbDineins.setVisibility(View.GONE);
                            }*/
                            SessionManager.getInstance().setmember(MyCartCalculation.this, calculateCart.getMember_count());


                            if (couponCodeValue > 0) {
                                trCouponValue.setVisibility(View.VISIBLE);
                                tvCouponValue.setText("(" + Common.getPriceWithCurrencyCode(AppConfig.decimalFormat.format(couponCodeValue)) + ")");
                                tvCouponValue.setTypeface(Typeface.DEFAULT);
                            } else {
                                trCouponValue.setVisibility(View.GONE);
                                tvCouponValue.setText("0");
                            }

                            tvServiceTaxText.setText(getString(R.string.prefix_service_tax) + calculateCart.getData().getShop().getService_tax() + "" + "%)");
                            tvServiceTax.setText(Common.getPriceWithCurrencyCode(AppConfig.decimalFormat.format(calculateCart.getData().getShop().getService_tax_amount()).toString()));
                            tvServiceTax.setTypeface(Typeface.DEFAULT);

                            tvVatText.setText(getString(R.string.prefix_vat) + calculateCart.getData().getShop().getVat_percentage() + "" + "%)");
                            tvVat.setText(Common.getPriceWithCurrencyCode(AppConfig.decimalFormat.format(calculateCart.getData().getShop().getVat_price()).toString()));
                            tvVat.setTypeface(Typeface.DEFAULT);

                            tvPackageCharges.setText(Common.getPriceWithCurrencyCode(calculateCart.getData().getShop().getPackage_price() + ""));
                            tvPackageCharges.setTypeface(Typeface.DEFAULT);

                            AppSharedValues.setDeliveryCharge(calculateCart.getData().getShop().getDelivery_fee());
                            AppSharedValues.setGrandTotalPrice(0D);

                        } else {
                            tvCoupon.setText("");
                            trCouponValue.setVisibility(View.GONE);
                            tvCouponValue.setText("0");
                            Toast.makeText(MyCartCalculation.this, calculateCart.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        btnPay.setTag("success");

                        if (mCustomProgressDialog.isShowing()) {
                            mCustomProgressDialog.dismiss();
                        }
                        CalcualteCost();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        String ss = "";
                        Toast.makeText(MyCartCalculation.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                        AppSharedValues.setGrandTotalPrice(0D);
                        btnPay.setTag("error");
                        if (mCustomProgressDialog.isShowing()) {
                            mCustomProgressDialog.dismiss();
                        }

                        CalcualteCost();
                    }
                });
    }

    private void ApplyCoupon() {

        mDialog = new AlertDialog.Builder(MyCartCalculation.this/*R.style.MyAlertDialogStyle*/);
        final View vDialog = getLayoutInflater().inflate(R.layout.dialog_coupon, null);

        //FontsManager.initFormAssets(MyCartCalculation.this, Common.appFon);
        // FontsManager.changeFonts(vDialog);

        mDialog.setView(vDialog);
        mAlertDilaog = mDialog.create();
        mAlertDilaog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mAlertDilaog.show();
        /*mAlertDilaog.setOnKeyListener(new Dialog.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface arg0, int keyCode, KeyEvent event) {
                // TODO Auto-generated method stub
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    rbCollect.setSelected(false);
                    rbDeliver.setSelected(false);
                    rbTable.setSelected(false);
                }
                return true;
            }
        });*/

        final Button btnApplyCoupon = (Button) vDialog.findViewById(R.id.btnApplyCoupon);
        final EditText etCouponCode = (EditText) vDialog.findViewById(R.id.etCouponCode);
        ImageButton mCloseImageButton = (ImageButton) vDialog.findViewById(R.id.close_button);

        mCloseImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlertDilaog.dismiss();
            }
        });

        etCouponCode.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etCouponCode.getText().toString().trim().length() > 0) {
                    //btnApplyCoupon.setBackgroundColor(getResources().getColor(R.color.dark_green));
                    btnApplyCoupon.setText(getString(R.string.prefix_apply));
                } else {
                    //btnApplyCoupon.setBackgroundColor(getResources().getColor(R.color.red));
                    btnApplyCoupon.setText(R.string.prefix_no_coupon_code);
                }
            }
        });

        btnApplyCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (Common.IsInternetConnected(MyCartCalculation.this)) {
                        if (!etCouponCode.getText().toString().isEmpty()) {
                            if (mAlertDilaog.isShowing()) {
                                mAlertDilaog.dismiss();
                            }
                            MyApplication.getInstance().trackEvent("Button", "Click", "Apply Coupon");

                            if (!mCustomProgressDialog.isShowing()) {
                                mCustomProgressDialog.ChageMessage(MyCartCalculation.this, "Please wait...", "Please wait...");
                                mCustomProgressDialog.show(MyCartCalculation.this);
                            }

                            tvCoupon.setText(etCouponCode.getText().toString().trim());
                            AppSharedValues.setCouponCode(etCouponCode.getText().toString().trim());
                            GetDetails();

                            if (mCustomProgressDialog.isShowing()) {
                                mCustomProgressDialog.dismiss();
                            }
                        } else {
                            Toast.makeText(MyCartCalculation.this, "promocode cannot be empty", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MyCartCalculation.this, "No Intenet Connetion", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void CollectDeliver(TextView dateTimeTextView, AppSharedValues.DeliveryMethod cartDeliveryMethod) {
        mDialog = new AlertDialog.Builder(MyCartCalculation.this, R.style.MyAlertDialogStyle);
        final View vDialog = getLayoutInflater().inflate(R.layout.dialog_my_collect, null);
        //   FontsManager.changeFonts(vDialog);

        mDialog.setView(vDialog);

        final LinearLayout llDateList = (LinearLayout) vDialog.findViewById(R.id.llDateList);
        final LinearLayout llTime = (LinearLayout) vDialog.findViewById(R.id.llTime);
        final LinearLayout llTimeListOther = (LinearLayout) vDialog.findViewById(R.id.llTimeListOther);
        final TextView tvSelectedDate = (TextView) vDialog.findViewById(R.id.tvSelectedDate);
        final TextView tvHelpText = (TextView) vDialog.findViewById(R.id.tvHelpText);
        ImageView ivClose = (ImageView) vDialog.findViewById(R.id.iv_collect_close);
        if (cartDeliveryMethod == AppSharedValues.DeliveryMethod.Delivery) {
            tvHelpText.setText(R.string.txt_when_want_to_deliver);
        } else if (cartDeliveryMethod == AppSharedValues.DeliveryMethod.Collect) {
            tvHelpText.setText(R.string.txt_when_want_to_pick);
        }

        mAlertDilaog = mDialog.create();
        mAlertDilaog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mAlertDilaog.show();
        mAlertDilaog.setCanceledOnTouchOutside(false);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlertDilaog.dismiss();
                rbCollect.setSelected(false);
                rbDeliver.setSelected(false);
                isChecked = true;
                rgOptions.clearCheck();

            }
        });
        mAlertDilaog.setOnKeyListener(new Dialog.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface arg0, int keyCode, KeyEvent event) {
                // TODO Auto-generated method stub
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    rbCollect.setSelected(false);
                    rbDeliver.setSelected(false);
                    rbTable.setSelected(false);
                }
                return true;
            }
        });


        final ListView lvTimes = (ListView) vDialog.findViewById(R.id.lvTimes);
        MyRestaurantDeliveryTimes myRestaurantDeliveryTimes = new MyRestaurantDeliveryTimes(MyCartCalculation.this, bus, AppSharedValues.getSelectedRestaurantBranchKey(), mAlertDilaog, dateTimeTextView);
        lvTimes.setAdapter(myRestaurantDeliveryTimes);

        final ListView lvDates = (ListView) vDialog.findViewById(R.id.lvDates);
        MyRestaurantDeliveryDates myRestaurantDeliveryDates = new MyRestaurantDeliveryDates(MyCartCalculation.this, bus, AppSharedValues.getSelectedRestaurantBranchKey(), llDateList, llTime, llTimeListOther, myRestaurantDeliveryTimes, tvSelectedDate);
        lvDates.setAdapter(myRestaurantDeliveryDates);
        myRestaurantDeliveryDates.notifyChanges();


        Button btnOther = (Button) vDialog.findViewById(R.id.btnOther);
        Button btnBack = (Button) vDialog.findViewById(R.id.btnBack);
        //Button btnConfirm = (Button) vDialog.findViewById(R.id.btnConfirm);

        btnOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.getInstance().trackEvent("Button", "Click", "Others");
                llDateList.setVisibility(View.GONE);
                llTime.setVisibility(View.GONE);
                llTimeListOther.setVisibility(View.VISIBLE);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llDateList.setVisibility(View.VISIBLE);
                llTimeListOther.setVisibility(View.GONE);
                llTime.setVisibility(View.GONE);
                MyApplication.getInstance().trackEvent("Button", "Click", "Back");
            }
        });


        btnDateASAPs = (Button) vDialog.findViewById(R.id.btnDateASAP);
        DMC_layout = (LinearLayout) vDialog.findViewById(R.id.DMC_layout);
        DMC_layout2 = (RelativeLayout) vDialog.findViewById(R.id.DMC_layout2);
        btnDateASAPs.setOnClickListener(dateOnclick);

        Button btnTime1 = (Button) vDialog.findViewById(R.id.btnTime1);
        Button btnTime2 = (Button) vDialog.findViewById(R.id.btnTime2);
        Button btnTime3 = (Button) vDialog.findViewById(R.id.btnTime3);
        Button btnTime4 = (Button) vDialog.findViewById(R.id.btnTime4);

        btnTime1.setTag("3:00");
        btnTime2.setTag("4:00");
        btnTime3.setTag("5:00");
        btnTime4.setTag("6:00");

        btnTime1.setOnClickListener(timeOnclick);
        btnTime2.setOnClickListener(timeOnclick);
        btnTime3.setOnClickListener(timeOnclick);
        btnTime4.setOnClickListener(timeOnclick);


    }

    private void CollectDeliver1() {


        customdialog = new Dialog(MyCartCalculation.this);
        customdialog.setContentView(R.layout.dialog_member_count);
        ArrayList<String> arrayList = new ArrayList<>();
        DMC_membercount = (ListView) customdialog.findViewById(R.id.DMC_membercount);
        DMC_collect_closes = (ImageView) customdialog.findViewById(DMC_collect_close);
        for (int count = 1; count <= SessionManager.getInstance().getmember(MyCartCalculation.this); count++) {

            arrayList.add("" + count);
        }
        customdialog.show();
        rbDeliver.setChecked(false);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MyCartCalculation.this, R.layout.dialog_list_text, R.id.DLT_list_count, arrayList);
        DMC_membercount.setAdapter(arrayAdapter);

        DMC_membercount.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                SessionManager.getInstance().setselectedmember(MyCartCalculation.this, position + 1);

                MyApplication.getInstance().trackEvent("Checkbox", "Click", "Dinein");
                tvCollectDateTime.setText("");
                tvDeliverDateTime.setText("");
                tvDineinDateTime.setText("");
                tvTableText.setText("");
                CollectDeliver(tvDineinDateTime, AppSharedValues.DeliveryMethod.Dinein);
                trDeliveryCharges.setVisibility(View.VISIBLE);
                btnDateASAPs.setVisibility(View.GONE);
                DMC_layout.setVisibility(View.GONE);
                DMC_layout2.setVisibility(View.GONE);
                btnPay.setText(R.string.txt_select_delivery_address);
                btnPay.setTextColor(getResources().getColor(R.color.white));
                //btnPay.setBackgroundColor(getResources().getColor(R.color.toolbar_color));
                AppSharedValues.setCartDeliveryMethod(AppSharedValues.DeliveryMethod.Dinein);
                CartDeliveryDetails.setCartDeliveryMethod(CartDeliveryDetails.DeliveryMethod.Dinein);
                CalcualteCost();
                customdialog.dismiss();

            }
        });


        /*DMC_membercount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(MyCartCalculation.this, ""+position, Toast.LENGTH_SHORT).show();

               *//* SessionManager.getInstance().setselectedmember(MyCartCalculation.this, position);

                MyApplication.getInstance().trackEvent("Checkbox", "Click", "Dinein");
                tvCollectDateTime.setText("");
                tvDeliverDateTime.setText("");
                tvDineinDateTime.setText("");
                tvTableText.setText("");
                CollectDeliver(tvDineinDateTime, AppSharedValues.DeliveryMethod.Dinein);
                trDeliveryCharges.setVisibility(View.VISIBLE);
                bty.setText(R.string.txt_select_dinein_address);
                btnPay.setTextColor(getResources().getColor(R.color.white));
                //btnPay.setBackgroundColor(getResources().getColor(R.color.toolbar_color));
                AppSharedValues.setCartDeliveryMethod(AppSharedValues.DeliveryMethod.Dinein);
                CartDeliveryDetails.setCartDeliveryMethod(CartDeliveryDetails.DeliveryMethod.Dinein);
                CalcualteCost();
                customdialog.dismiss();*//*

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

        DMC_collect_closes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customdialog.dismiss();
                rbDineins.setChecked(false);
            }
        });

        customdialog.show();

    }

    private View.OnClickListener dateOnclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                if (CartDeliveryDetails.getCartDeliveryMethod() == CartDeliveryDetails.DeliveryMethod.Deliver) {
                    tvDeliverDateTime.setText(R.string.txt_asap);
                    tvCollectDateTime.setText("");
                } else if (CartDeliveryDetails.getCartDeliveryMethod() == CartDeliveryDetails.DeliveryMethod.Collect) {
                    tvDeliverDateTime.setText("");
                    tvCollectDateTime.setText(R.string.txt_asap);
                }

                if (mAlertDilaog.isShowing()) {
                    mAlertDilaog.dismiss();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private View.OnClickListener timeOnclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                rbCollect.setText(getString(R.string.prefix_collect) + ((Button) v).getText());
                if (mAlertDilaog.isShowing()) {
                    mAlertDilaog.dismiss();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

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
        //btnPay.setText("Pay order total " + Common.getPriceWithCurrencyCode(DBActionsCart.getTotalCartPriceIncludingTax(AppSharedValues.getSelectedRestaurantBranchKey()).toString()));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void CalcualteCost() {

        Double billTotal = Double.valueOf(AppConfig.decimalFormat.format(DBActionsCart.getTotalCartPrice(AppSharedValues.getSelectedRestaurantBranchKey())));
        Double grandTotal = billTotal;
        Double deliveryCharge = 0D;

        tvDeliveryChargesExtra.setVisibility(View.GONE);
        tvDeliveryCharges.setVisibility(View.GONE);
       /* if (AppSharedValues.getCartDeliveryMethod() == AppSharedValues.DeliveryMethod.None) {
            deliveryCharge = 0D;
            grandTotal = Double.valueOf(billTotal) +
                    Double.valueOf(AppConfig.decimalFormat.format(vatValue)) +
                    Double.valueOf(AppConfig.decimalFormat.format(serviceTaxValue)) +
                    Double.valueOf(AppConfig.decimalFormat.format(packageValue)) -
                    Double.valueOf(AppConfig.decimalFormat.format(couponCodeValue));

        } else if (AppSharedValues.getCartDeliveryMethod() == AppSharedValues.DeliveryMethod.Collect) {
            deliveryCharge = 0D;

            grandTotal = Double.valueOf(billTotal) +
                    Double.valueOf(AppConfig.decimalFormat.format(vatValue)) +
                    Double.valueOf(AppConfig.decimalFormat.format(serviceTaxValue)) +
                    Double.valueOf(AppConfig.decimalFormat.format(packageValue)) -
                    Double.valueOf(AppConfig.decimalFormat.format(couponCodeValue));

        } else if (AppSharedValues.getCartDeliveryMethod() == AppSharedValues.DeliveryMethod.Deliver) {
            tvDeliveryCharges.setVisibility(View.VISIBLE);
            deliveryCharge = AppSharedValues.getDeliveryCharge();

            grandTotal = Double.valueOf(billTotal) + AppSharedValues.getDeliveryCharge() +
                    Double.valueOf(AppConfig.decimalFormat.format(vatValue)) +
                    Double.valueOf(AppConfig.decimalFormat.format(serviceTaxValue)) +
                    Double.valueOf(AppConfig.decimalFormat.format(packageValue)) -
                    Double.valueOf(AppConfig.decimalFormat.format(couponCodeValue));

            if (AppSharedValues.getMinOrderDeliveryPrice() > 0) {
                if (billTotal < AppSharedValues.getMinOrderPrice()) {
                    tvDeliveryChargesExtra.setVisibility(View.VISIBLE);
                    tvDeliveryChargesExtra.setText(AppSharedValues.getMinOrderDeliveryPrice().toString());
                    grandTotal = Double.valueOf(grandTotal) + AppSharedValues.getMinOrderDeliveryPrice();
                }
            }
        }
        else if (AppSharedValues.getCartDeliveryMethod() == AppSharedValues.DeliveryMethod.Dinein) {
            tvDeliveryCharges.setVisibility(View.VISIBLE);
            deliveryCharge = 0D;

            grandTotal = Double.valueOf(billTotal) + AppSharedValues.getDeliveryCharge() +
                    Double.valueOf(AppConfig.decimalFormat.format(vatValue)) +
                    Double.valueOf(AppConfig.decimalFormat.format(serviceTaxValue)) +
                    Double.valueOf(AppConfig.decimalFormat.format(packageValue)) -
                    Double.valueOf(AppConfig.decimalFormat.format(couponCodeValue));

            if (AppSharedValues.getMinOrderDeliveryPrice() > 0) {
                if (billTotal < AppSharedValues.getMinOrderPrice()) {
                    tvDeliveryChargesExtra.setVisibility(View.VISIBLE);
                    tvDeliveryChargesExtra.setText(AppSharedValues.getMinOrderDeliveryPrice().toString());
                    grandTotal = Double.valueOf(grandTotal) + AppSharedValues.getMinOrderDeliveryPrice();
                }
            }
        }
        else if (AppSharedValues.getCartDeliveryMethod() == AppSharedValues.DeliveryMethod.DeliveryToMyTable) {
            deliveryCharge = 0D;
        }*/


        if (SessionManager.getInstance().getDeliveryMethod(MyCartCalculation.this).equals("0")) {
            deliveryCharge = 0D;

            if (Double.valueOf(billTotal) <= Double.valueOf(AppConfig.decimalFormat.format(couponCodeValue))) {
                grandTotal = 0 +
                        Double.valueOf(AppConfig.decimalFormat.format(vatValue)) +
                        Double.valueOf(AppConfig.decimalFormat.format(serviceTaxValue)) +
                        Double.valueOf(AppConfig.decimalFormat.format(packageValue));
            } else {
                grandTotal = Double.valueOf(billTotal) +
                        Double.valueOf(AppConfig.decimalFormat.format(vatValue)) +
                        Double.valueOf(AppConfig.decimalFormat.format(serviceTaxValue)) +
                        Double.valueOf(AppConfig.decimalFormat.format(packageValue)) -
                        Double.valueOf(AppConfig.decimalFormat.format(couponCodeValue));
            }
        } else if (SessionManager.getInstance().getDeliveryMethod(MyCartCalculation.this).equals("2")) {
            deliveryCharge = 0D;

            if (Double.valueOf(billTotal) <= Double.valueOf(AppConfig.decimalFormat.format(couponCodeValue))) {
                grandTotal = 0 +
                        Double.valueOf(AppConfig.decimalFormat.format(vatValue)) +
                        Double.valueOf(AppConfig.decimalFormat.format(serviceTaxValue)) +
                        Double.valueOf(AppConfig.decimalFormat.format(packageValue));
            } else {
                grandTotal = Double.valueOf(billTotal) +
                        Double.valueOf(AppConfig.decimalFormat.format(vatValue)) +
                        Double.valueOf(AppConfig.decimalFormat.format(serviceTaxValue)) +
                        Double.valueOf(AppConfig.decimalFormat.format(packageValue)) -
                        Double.valueOf(AppConfig.decimalFormat.format(couponCodeValue));
            }

        } else if (SessionManager.getInstance().getDeliveryMethod(MyCartCalculation.this).equals("1")) {
            tvDeliveryCharges.setVisibility(View.VISIBLE);
            deliveryCharge = AppSharedValues.getDeliveryCharge();

            if (Double.valueOf(billTotal) <= Double.valueOf(AppConfig.decimalFormat.format(couponCodeValue))) {

                grandTotal = 0 + AppSharedValues.getDeliveryCharge() +
                        Double.valueOf(AppConfig.decimalFormat.format(vatValue)) +
                        Double.valueOf(AppConfig.decimalFormat.format(serviceTaxValue)) +
                        Double.valueOf(AppConfig.decimalFormat.format(packageValue));
            } else {
                grandTotal = Double.valueOf(billTotal) + AppSharedValues.getDeliveryCharge() +
                        Double.valueOf(AppConfig.decimalFormat.format(vatValue)) +
                        Double.valueOf(AppConfig.decimalFormat.format(serviceTaxValue)) +
                        Double.valueOf(AppConfig.decimalFormat.format(packageValue)) -
                        Double.valueOf(AppConfig.decimalFormat.format(couponCodeValue));
            }

            if (AppSharedValues.getMinOrderDeliveryPrice() > 0) {
                if (billTotal < AppSharedValues.getMinOrderPrice()) {
                    tvDeliveryChargesExtra.setVisibility(View.VISIBLE);
                    tvDeliveryChargesExtra.setText(AppSharedValues.getMinOrderDeliveryPrice().toString());
                    grandTotal = Double.valueOf(grandTotal) + AppSharedValues.getMinOrderDeliveryPrice();
                }
            }
        } else if (SessionManager.getInstance().getDeliveryMethod(MyCartCalculation.this).equals("3")) {
            tvDeliveryCharges.setVisibility(View.VISIBLE);
            deliveryCharge = 0D;

            if (Double.valueOf(billTotal) <= Double.valueOf(AppConfig.decimalFormat.format(couponCodeValue))) {
                grandTotal = 0 + 0 +
                        Double.valueOf(AppConfig.decimalFormat.format(vatValue)) +
                        Double.valueOf(AppConfig.decimalFormat.format(serviceTaxValue)) +
                        Double.valueOf(AppConfig.decimalFormat.format(packageValue));
            } else {
                grandTotal = Double.valueOf(billTotal) + 0 +
                        Double.valueOf(AppConfig.decimalFormat.format(vatValue)) +
                        Double.valueOf(AppConfig.decimalFormat.format(serviceTaxValue)) +
                        Double.valueOf(AppConfig.decimalFormat.format(packageValue)) -
                        Double.valueOf(AppConfig.decimalFormat.format(couponCodeValue));
            }
            if (AppSharedValues.getMinOrderDeliveryPrice() > 0) {
                if (billTotal < AppSharedValues.getMinOrderPrice()) {
                    tvDeliveryChargesExtra.setVisibility(View.GONE);
                    tvDeliveryChargesExtra.setText(AppSharedValues.getMinOrderDeliveryPrice().toString());
                    grandTotal = Double.valueOf(grandTotal) + AppSharedValues.getMinOrderDeliveryPrice();
                }
            }
        } else if (AppSharedValues.getCartDeliveryMethod() == AppSharedValues.DeliveryMethod.DeliveryToMyTable) {
            deliveryCharge = 0D;
        }


        AppSharedValues.setGrandTotalPrice(grandTotal);

        tvItemTotal.setText(String.valueOf(DBActionsCart.getTotalItems(AppSharedValues.getSelectedRestaurantBranchKey())));
        tvBilltotal.setText(Common.getPriceWithCurrencyCode(AppConfig.decimalFormat.format(billTotal)));
        tvBilltotal.setTypeface(Typeface.DEFAULT);
        tvDeliveryCharges.setText(Common.getPriceWithCurrencyCode(AppConfig.decimalFormat.format(deliveryCharge)));
        tvDeliveryCharges.setTypeface(Typeface.DEFAULT);

        tvGrandTotal.setText(Common.getPriceWithCurrencyCode(AppConfig.decimalFormat.format(AppSharedValues.getGrandTotalPrice())));
        tvGrandTotal.setTypeface(Typeface.DEFAULT);
        tvCart.setText(Common.getPriceWithCurrencyCode(AppConfig.decimalFormat.format(AppSharedValues.getGrandTotalPrice())));
        tvCart.setTypeface(Typeface.DEFAULT);
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        rbCollect.setSelected(false);
        rbDeliver.setSelected(false);
        rbTable.setSelected(false);
    }

    private void BindCouponOnClick() {
        tvCouponText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.getInstance().trackEvent("TextView", "Click", "Coupon");
                ApplyCoupon();
            }
        });
        tvCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.getInstance().trackEvent("TextView", "Click", "Coupon");
                ApplyCoupon();
            }
        });
    }

    public void onEvent(final SigninEvent ev) {
        runOnUiThread(new Runnable() {
            public void run() {

                tvCouponText.setText(R.string.txt_apply_coupon);
                tvCouponText.setTextColor(getResources().getColor(R.color.gray));
                BindCouponOnClick();
            }
        });
    }
}
