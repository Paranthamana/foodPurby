package com.foodpurby.screens;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.foodpurby.R;
import com.foodpurby.baseclass.MyActivity;
import com.foodpurby.events.AddressEvent;
import com.foodpurby.events.DummyEvent;
import com.foodpurby.events.MainMenuRefreshEvent;
import com.foodpurby.events.SigninEvent;
import com.foodpurby.model.DAOOTPVerify;
import com.foodpurby.model.DAOUserResendOtp;
import com.foodpurby.ondbstorage.DBActionsUser;
import com.foodpurby.util.CustomProgressDialog;
import com.foodpurby.utillities.AppSharedValues;
import com.foodpurby.utillities.UserDetails;

import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class OTPPAge extends AppCompatActivity {


    TextView tvMobileNo;
    EditText etOPTCode;
    Intent intent;
    private EventBus bus = EventBus.getDefault();
    Button btnOPTCode;
    CustomProgressDialog mCustomProgressDialog;
    private TextView tvResends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otppage);

        intent = getIntent();
        EventBus.getDefault().register(this);

        tvMobileNo = (TextView) findViewById(R.id.tvMobileNo);
        etOPTCode = (EditText) findViewById(R.id.etOPTCode);
        btnOPTCode = (Button) findViewById(R.id.btnOPTCode);
        tvResends = (TextView) findViewById(R.id.tvResend);
        tvMobileNo.setText(intent.getStringExtra("number"));

        etOPTCode.setText("");

        mCustomProgressDialog = CustomProgressDialog.getInstance();
        tvResends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyApplication.getInstance().trackEvent("Button", "Click", "Resend");
                if (!mCustomProgressDialog.isShowing()) {
                    mCustomProgressDialog.show(OTPPAge.this);
                }
                DAOUserResendOtp.getInstance().Callresponse(intent.getStringExtra("tempckey"),

                        new Callback<DAOUserResendOtp.ResendOtp>() {
                            @Override
                            public void success(DAOUserResendOtp.ResendOtp confirmOrder, Response response) {
                                if (mCustomProgressDialog.isShowing()) {
                                    mCustomProgressDialog.dismiss();
                                }
                                if (confirmOrder.getHttpcode().equals(200) || confirmOrder.getHttpcode().equals("200")) {
                                    Toast.makeText(OTPPAge.this, confirmOrder.getMessage(), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(OTPPAge.this, confirmOrder.getMessage(), Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void failure(RetrofitError error) {
                                error.printStackTrace();
                                Toast.makeText(OTPPAge.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                                if (mCustomProgressDialog.isShowing()) {
                                    mCustomProgressDialog.dismiss();
                                }
                            }
                        });
            }
        });
        btnOPTCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //CustomProgressDialog.getInstance().show(OTPPAge.this);

                if (mCustomProgressDialog != null && !mCustomProgressDialog.isShowing()) {
                    mCustomProgressDialog.ChageMessage(OTPPAge.this, getString(R.string.dialog_please_wait), getString(R.string.dialog_please_wait));
                    mCustomProgressDialog.show(OTPPAge.this);
                }

                DAOOTPVerify.getInstance().Callresponse(etOPTCode.getText().toString().trim(), intent.getStringExtra("tempckey"), new Callback<DAOOTPVerify.ConfirmOrder>() {
                    @Override
                    public void success(DAOOTPVerify.ConfirmOrder confirmOrder, Response response) {

                        if (mCustomProgressDialog.isShowing()) {
                            mCustomProgressDialog.dismiss();
                        }

                        if (confirmOrder.getHttpcode().equals("200")) {
                            if (confirmOrder.getData().getCustomer_type().equals("1")) {
                                UserDetails.setCustomerKey(confirmOrder.getData().getCustomer_key());
                                UserDetails.setCustomerEmail(confirmOrder.getData().getCustomer_email());
                                UserDetails.setCustomerName(confirmOrder.getData().getCustomer_name());
                                UserDetails.setCustomerLastName(confirmOrder.getData().getCustomer_last_name());
                                UserDetails.setCustomerMobile(confirmOrder.getData().getCustomer_mobile());
                                UserDetails.setAccessToken(confirmOrder.getData().getAccess_token());

                                DBActionsUser.add(
                                        UserDetails.getCustomerKey(), UserDetails.getCustomerKey(),
                                        UserDetails.getCustomerEmail(),
                                        UserDetails.getCustomerName(),
                                        UserDetails.getCustomerLastName(),
                                        UserDetails.getCustomerMobile(),
                                        UserDetails.getAccessToken());

                                //  Toast.makeText(OTPPAge.this, R.string.txt_singnup_sucess, Toast.LENGTH_SHORT).show();

                                Toast.makeText(OTPPAge.this, confirmOrder.getMessage(), Toast.LENGTH_SHORT).show();

                                if (AppSharedValues.SignupStatus.equalsIgnoreCase("1")) {
                                    bus.post(new SigninEvent());
                                }
                                finish();

                            } else if (confirmOrder.getData().getCustomer_type().equals("2")) {
                                UserDetails.setCustomerKey(confirmOrder.getData().getCustomer_key());
                                UserDetails.setCustomerEmail(confirmOrder.getData().getCustomer_email());
                                UserDetails.setCustomerName(confirmOrder.getData().getCustomer_name());
                                UserDetails.setCustomerLastName(confirmOrder.getData().getCustomer_last_name());
                                UserDetails.setCustomerMobile(confirmOrder.getData().getCustomer_mobile());
                                UserDetails.setAccessToken(confirmOrder.getData().getAccess_token());
                                // UserDetails.setImageUrl(mImageUrl);

                                DBActionsUser.add(
                                        UserDetails.getCustomerKey(), UserDetails.getCustomerKey(),
                                        UserDetails.getCustomerEmail(),
                                        UserDetails.getCustomerName(),
                                        UserDetails.getCustomerLastName(),
                                        UserDetails.getCustomerMobile(),
                                        UserDetails.getAccessToken());

                                Toast.makeText(OTPPAge.this, R.string.toast_login_success, Toast.LENGTH_SHORT).show();

                                bus.post(new AddressEvent());
                                bus.post(new MainMenuRefreshEvent());
                                bus.post(new SigninEvent());

                                finish();

                                if (getIntent().getStringExtra("type").equals("activity")) {
                                    MyActivity.DisplayHomePage(OTPPAge.this);
                                } else {
                                    finish();
                                }
                            }
                        } else {
                            Toast.makeText(OTPPAge.this, confirmOrder.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        if (mCustomProgressDialog.isShowing()) {
                            mCustomProgressDialog.dismiss();
                        }
                    }
                });

            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void onEvent(final DummyEvent ev) {
    }
}
