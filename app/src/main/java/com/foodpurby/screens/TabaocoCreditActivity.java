package com.foodpurby.screens;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.foodpurby.R;
import com.foodpurby.model.DAOCredit;
import com.foodpurby.model.DAOCreditUpdate;
import com.foodpurby.util.Common;
import com.foodpurby.util.CustomProgressDialog;
import com.foodpurby.utillities.SessionManager;
import com.foodpurby.utillities.UserDetails;
import com.ipay.Ipay;
import com.ipay.IpayPayment;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Tech on 18-04-2017.
 */

public class TabaocoCreditActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private TextView mToolHeading;
    private TextView pointtext;
    private TextView submitbtn;
    RadioGroup rgAmt;
    private EditText tabaocopointamt;
    private Common mCommon;
    private CustomProgressDialog mCustomProgressDialog;
    private String date;

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

    String MerchantKey = "T5dABreBtF";
    String MerchantCode = "M05437";
    private TextView walletHistory;
    private TextView htmllinks;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabaoco_credit);

        mToolbar = (Toolbar) findViewById(R.id.tb_header);
        mToolHeading = (TextView) findViewById(R.id.tool_title);
        mToolbar.setNavigationIcon(R.drawable.back);
        mToolHeading.setText(getResources().getString(R.string.txt_tabao_credit));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        pointtext = (TextView) findViewById(R.id.tabaoco_points);
        submitbtn = (TextView) findViewById(R.id.tabaoco_submit);
        walletHistory = (TextView) findViewById(R.id.wallet_list);
        htmllinks = (TextView) findViewById(R.id.htmllink);
        rgAmt = (RadioGroup) findViewById(R.id.rg_amt);
        tabaocopointamt = (EditText) findViewById(R.id.tabaoco_point_values);

        walletHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(TabaocoCreditActivity.this, TransactionActivity.class);
                startActivity(mIntent);
            }
        });

        pointtext.setText(getString(R.string.points) + " " + "" + SessionManager.getInstance().getcreditpoint(TabaocoCreditActivity.this));
        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!tabaocopointamt.getText().toString().isEmpty()) {
                    DAOCredit.getInstance().Callresponse("en", UserDetails.getCustomerKey(), tabaocopointamt.getText().toString(), new Callback<DAOCredit.Tabaoco_credit_Response>() {
                        @Override
                        public void success(DAOCredit.Tabaoco_credit_Response tabaoco_credit_response, Response response) {

                            if (tabaoco_credit_response.getHttpcode().equals("200")) {

                                IpayPayment payment = new IpayPayment();
                                MerchantKey = tabaoco_credit_response.getData().getMerchantKey();
                                MerchantCode = tabaoco_credit_response.getData().getMerchanCode();

                                payment.setMerchantCode(tabaoco_credit_response.getData().getMerchanCode());
                                payment.setMerchantKey(tabaoco_credit_response.getData().getMerchantKey());
                                payment.setPaymentId("");
                                payment.setCurrency("MYR");
                                payment.setLang("UTF-8");
                                payment.setRemark(tabaoco_credit_response.getData().getRemark());
                                payment.setRefNo(tabaoco_credit_response.getData().getRefNo());
                                payment.setAmount(tabaoco_credit_response.getData().getAmount());
                                payment.setProdDesc(tabaoco_credit_response.getData().getProdDesc());
                                payment.setUserName(tabaoco_credit_response.getData().getUserName());
                                payment.setUserEmail(tabaoco_credit_response.getData().getUserEmail());
                                payment.setUserContact(tabaoco_credit_response.getData().getUserContact());
                                payment.setCountry("MY");
                                payment.setBackendPostURL(tabaoco_credit_response.getData().getBackendURL());
                                Intent checkoutIntent = Ipay.getInstance().checkout(payment, TabaocoCreditActivity.this, new ResultDelegate());
                                startActivityForResult(checkoutIntent, 1);
                            } else {
                                Toast.makeText(TabaocoCreditActivity.this, "Something went wrong.", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void failure(RetrofitError error) {

                        }
                    });

                } else {
                    Toast.makeText(TabaocoCreditActivity.this, "Enter Wallet Amount", Toast.LENGTH_SHORT).show();
                }

            }


        });
        /*submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selecdId = rgAmt.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) findViewById(selecdId);
                if (selecdId != -1) {
                    DAOCredit.getInstance().Callresponse("en", UserDetails.getCustomerKey(), radioButton.getTag().toString(), new Callback<DAOCredit.Tabaoco_credit_Response>() {
                        @Override
                        public void success(DAOCredit.Tabaoco_credit_Response tabaoco_credit_response, Response response) {

                            if (tabaoco_credit_response.getHttpcode().equals("200")) {

                                IpayPayment payment = new IpayPayment();
                                MerchantKey = tabaoco_credit_response.getData().getMerchantKey();
                                MerchantCode = tabaoco_credit_response.getData().getMerchanCode();

                                payment.setMerchantCode(tabaoco_credit_response.getData().getMerchanCode());
                                payment.setMerchantKey(tabaoco_credit_response.getData().getMerchantKey());
                                payment.setPaymentId("");
                                payment.setCurrency("MYR");
                                payment.setLang("UTF-8");
                                payment.setRemark(tabaoco_credit_response.getData().getRemark());
                                payment.setRefNo(tabaoco_credit_response.getData().getRefNo());
                                payment.setAmount(tabaoco_credit_response.getData().getAmount());
                                payment.setProdDesc(tabaoco_credit_response.getData().getProdDesc());
                                payment.setUserName(tabaoco_credit_response.getData().getUserName());
                                payment.setUserEmail(tabaoco_credit_response.getData().getUserEmail());
                                payment.setUserContact(tabaoco_credit_response.getData().getUserContact());
                                payment.setCountry("MY");
                                payment.setBackendPostURL(tabaoco_credit_response.getData().getBackendURL());
                                Intent checkoutIntent = Ipay.getInstance().checkout(payment, TabaocoCreditActivity.this, new ResultDelegate());
                                startActivityForResult(checkoutIntent, 1);
                            } else {
                                Toast.makeText(TabaocoCreditActivity.this, "Something went wrong.", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void failure(RetrofitError error) {

                        }
                    });

                } else {
                    Toast.makeText(TabaocoCreditActivity.this, "Nothing selected from Reload Option.", Toast.LENGTH_SHORT).show();
                }

            }


        });*/
    }

    @Override
    protected void onResume() {
        super.onResume();


        if (resultTitle.equals("SUCCESS") || resultTitle.equals("CANCELED") || resultTitle.equals("FAILURE")) {
            Toast.makeText(TabaocoCreditActivity.this, "" + resultInfo, Toast.LENGTH_SHORT).show();
            final CustomProgressDialog mCustomProgressDialog = new CustomProgressDialog();
            mCustomProgressDialog.show(TabaocoCreditActivity.this);
            DAOCreditUpdate.getInstance().Callresponse("en", "ewerwerwerr", MerchantCode, "", RefNo, Amount, "MYR", Remark, TransId, AuthCode, resultCode, ErrDesc, "", "", UserDetails.getCustomerKey(), new Callback<DAOCreditUpdate.Tabaoco_credit_Response>() {
                @Override
                public void success(DAOCreditUpdate.Tabaoco_credit_Response tabaoco_credit_response, Response response) {
                    mCustomProgressDialog.dismiss();
                    resultTitle = "";
                    if (tabaoco_credit_response.getHttpcode().equals("200")) {
                        Toast.makeText(TabaocoCreditActivity.this, tabaoco_credit_response.getMessage(), Toast.LENGTH_SHORT).show();
                        SessionManager.getInstance().setcreditpoint(TabaocoCreditActivity.this, tabaoco_credit_response.getData());
                    } else {
                        Toast.makeText(TabaocoCreditActivity.this, tabaoco_credit_response.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    pointtext.setText(getString(R.string.points) + SessionManager.getInstance().getcreditpoint(TabaocoCreditActivity.this));

                }

                @Override
                public void failure(RetrofitError error) {
                    resultTitle = "";
                    mCustomProgressDialog.dismiss();
                }
            });


        }

    }
}
