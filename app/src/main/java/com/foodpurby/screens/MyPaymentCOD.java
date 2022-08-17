package com.foodpurby.screens;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sloop.fonts.FontsManager;
import com.foodpurby.R;
import com.foodpurby.baseclass.MyActivity;
import com.foodpurby.events.AddRemoveToCartEvent;
import com.foodpurby.model.DAOUserOrderConfirmation;
import com.foodpurby.model.DAOUserOrderResendOTP;
import com.foodpurby.ondbstorage.DBActionsCart;
import com.foodpurby.util.Common;
import com.foodpurby.util.CustomProgressDialog;
import com.foodpurby.utillities.AppConfig;
import com.foodpurby.utillities.AppSharedValues;
import com.foodpurby.utillities.CartDeliveryDetails;
import com.foodpurby.utillities.SessionManager;
import com.foodpurby.utillities.UserDetails;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MyPaymentCOD extends AppCompatActivity {

    private EventBus bus = EventBus.getDefault();
    private CustomProgressDialog mCustomProgressDialog;

    private ListView lvAddress;
    Toolbar mToolbar;
    TextView mToolHeading;

    ImageView imgCart;
    TextView tvCart;

    Button btnOPTCode;
    EditText etOPTCode;
    TextView tvMobileNo, tvResend;
    public BroadcastReceiver smsReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_my_payment_cod);
        mCustomProgressDialog = CustomProgressDialog.getInstance();


      //  FontsManager.initFormAssets(MyPaymentCOD.this, "Lato-Light.ttf");
      //  FontsManager.changeFonts(this);


        smsReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // Retrieves a map of extended data from the intent.
                final Bundle bundle = intent.getExtras();

                try {

                    if (bundle != null) {

                        final Object[] pdusObj = (Object[]) bundle.get("pdus");

                        for (int i = 0; i < pdusObj.length; i++) {

                            SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                            String phoneNumber = currentMessage.getDisplayOriginatingAddress();

                            String senderNum = phoneNumber;
                            String message = currentMessage.getDisplayMessageBody();

                            Log.i("SmsReceiver", "senderNum: " + senderNum + "; message: " + message);


                            String regex = "\\byour OTP is\\b (\\d{6})";
                            Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
                            Matcher matcher = pattern.matcher(message);
                            while (matcher.find()) {
                                etOPTCode.setText(matcher.group(1).trim());
                                Verify(matcher.group(1).trim());
                                break;
                            }

                        } // end for loop
                    } // bundle is null

                } catch (Exception e) {
                    Log.e("SmsReceiver", "Exception smsReceiver" +e);

                }
            }
        };

        mToolbar = (Toolbar) findViewById(R.id.tb_header);
        mToolbar.setNavigationIcon(R.drawable.back);
        mToolHeading = (TextView) findViewById(R.id.tool_title);
        mToolHeading.setText(R.string.txt_verify_your_number);

        setSupportActionBar(mToolbar);
        imgCart = (ImageView) findViewById(R.id.imgCart);
        imgCart.setVisibility(View.GONE);
        tvCart = (TextView) findViewById(R.id.tvCart);
        tvCart.setText(Common.getPriceWithCurrencyCode(AppConfig.decimalFormat.format(AppSharedValues.getGrandTotalPrice())));
        tvCart.setTypeface(Typeface.DEFAULT);

        TextView tvCart = (TextView) mToolbar.findViewById(R.id.tvCart);
        tvCart.setText(Common.getPriceWithCurrencyCode(AppConfig.decimalFormat.format(AppSharedValues.getGrandTotalPrice())));
        tvCart.setTypeface(Typeface.DEFAULT);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnOPTCode = (Button) findViewById(R.id.btnOPTCode);
        etOPTCode = (EditText) findViewById(R.id.etOPTCode);
        tvMobileNo = (TextView) findViewById(R.id.tvMobileNo);
        tvResend = (TextView) findViewById(R.id.tvResend);

        tvMobileNo.setText(UserDetails.getCustomerMobile());

        btnOPTCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.getInstance().trackEvent("Button", "Click", "OTP Code");
                Verify(etOPTCode.getText().toString().trim());
            }
        });

        tvResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.getInstance().trackEvent("Button", "Click", "Resend");
                etOPTCode.setText("");
                if(!mCustomProgressDialog.isShowing()) {
                    mCustomProgressDialog.show(MyPaymentCOD.this);
                }
                DAOUserOrderResendOTP.getInstance().Callresponse("token",
                        CartDeliveryDetails.getCartOrderKey(),
                        "$2y$13$O8eus5IZeW9tkpEB3.sahuVVsmZemaXTyIxqDi3ROO2uEYvRxVC72",
                        new Callback<DAOUserOrderResendOTP.ConfirmOrder>() {
                            @Override
                            public void success(DAOUserOrderResendOTP.ConfirmOrder confirmOrder, Response response) {

                                if (confirmOrder.getHttpcode().equals(200) || confirmOrder.getHttpcode().equals("200")) {
                                    Toast.makeText(MyPaymentCOD.this, confirmOrder.getMessage(), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(MyPaymentCOD.this, confirmOrder.getMessage(), Toast.LENGTH_SHORT).show();
                                }

                                if(mCustomProgressDialog.isShowing()) {
                                    mCustomProgressDialog.dismiss();
                                }
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                error.printStackTrace();
                                Toast.makeText(MyPaymentCOD.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                                if(mCustomProgressDialog.isShowing()) {
                                    mCustomProgressDialog.dismiss();
                                }
                            }
                        });


            }
        });
    }
    private void Verify(String otpCode) {
        if (!otpCode.trim().isEmpty()) {
            if(!mCustomProgressDialog.isShowing()) {
                mCustomProgressDialog.ChageMessage(MyPaymentCOD.this, "Please wait...", "Please wait...");
                mCustomProgressDialog.show(MyPaymentCOD.this);
            }
            DAOUserOrderConfirmation.getInstance().Callresponse("token",
                    otpCode.trim(),
                    SessionManager.getInstance().getCurrentOrderKey(MyPaymentCOD.this),
                    "$2y$13$O8eus5IZeW9tkpEB3.sahuVVsmZemaXTyIxqDi3ROO2uEYvRxVC72",
                    new Callback<DAOUserOrderConfirmation.ConfirmOrder>() {
                        @Override
                        public void success(DAOUserOrderConfirmation.ConfirmOrder confirmOrder, Response response) {

                            if (confirmOrder.getHttpcode().equals(200) || confirmOrder.getHttpcode().equals("200")) {

                                DBActionsCart.deleteAll(AppSharedValues.getSelectedRestaurantBranchKey());

                                MyActivity.DisplayPaymentOrderConfirmed(MyPaymentCOD.this, confirmOrder.getData().getOrder_key(), confirmOrder.getData().getOrder_total(),"1","");

                                Toast.makeText(MyPaymentCOD.this, confirmOrder.getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MyPaymentCOD.this, confirmOrder.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            if(mCustomProgressDialog.isShowing()) {
                                mCustomProgressDialog.dismiss();
                            }
                        }

                        
                        @Override
                        public void failure(RetrofitError error) {
                            error.printStackTrace();
                            Toast.makeText(MyPaymentCOD.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                            if(mCustomProgressDialog.isShowing()) {
                                mCustomProgressDialog.dismiss();
                            }
                        }
                    });
        } else {
            Toast.makeText(MyPaymentCOD.this, R.string.toast_enter_verification_code, Toast.LENGTH_SHORT).show();
            etOPTCode.requestFocus();
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
        // TODO Auto-generated method stub
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(smsReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        unregisterReceiver(smsReceiver);
    }
}