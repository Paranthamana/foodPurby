package com.foodpurby.screens;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.foodpurby.R;
import com.foodpurby.constants.ConstantsClass;
import com.foodpurby.events.DummyEvent;
import com.foodpurby.model.DAOSignup;
import com.foodpurby.util.Common;
import com.foodpurby.util.CustomProgressDialog;
import com.foodpurby.utillities.AppSharedValues;
import com.foodpurby.utillities.SessionManager;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class FBSignUpAct extends AppCompatActivity {

    EditText mEmailSignUp, mMobileNumberSignUp;
    Button mFBSignUpButton;
    LoginButton mFBButton;
    private CustomProgressDialog mCustomProgressDialog;
    CallbackManager callbackManager;
    AccessTokenTracker accessTokenTracker;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";
    private static final String ARG_PARAM5 = "param5";
    private static final String ARG_PARAM6 = "params6";
    private static final String ARG_PARAM7 = "params7";


    // TODO: Rename and change types of parameters
    private String mEmail;
    private String mFirstName;
    private String mLastName;
    private String mBirthday;
    private String mFBId;
    private String mAccessToken;
    private String mImageUrl;

    EditText etFirstName, etLastName;

    CountryListAdapter mCountryListAdapter;

    String[] recourseList, resourceList;

    EditText vCountryText, etPassword;

    TextInputLayout tilPassword;

    ListView vCountryList;
    EditText vSearchCountry;

    public FBSignUpAct() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCustomProgressDialog = CustomProgressDialog.getInstance();
        EventBus.getDefault().register(this);
        setContentView(R.layout.fragment_fbsign_up);

        etPassword = (EditText) findViewById(R.id.etPassword);
        etPassword.setVisibility(View.GONE);

        tilPassword = (TextInputLayout) findViewById(R.id.tilPassword);
        tilPassword.setVisibility(View.GONE);

        Intent fbData = getIntent();

        if (fbData != null) {
            mEmail = fbData.getStringExtra("email");
            mFirstName = fbData.getStringExtra("firstName");
            mLastName = fbData.getStringExtra("lastName");
            mBirthday = fbData.getStringExtra("birthday");
            mFBId = fbData.getStringExtra("id");
            mAccessToken = fbData.getStringExtra("token");
            mImageUrl = fbData.getStringExtra("imageUrl");
        }


        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etLastName = (EditText) findViewById(R.id.etLastName);

        etFirstName.setText(mFirstName);
        etLastName.setText(mLastName);


        initiateWidgets();


        if (mEmail != null) {
            mEmailSignUp.setText(mEmail);
            // mEmailSignUp.setEnabled(false);
        } else {
            //Do nothing
        }
        mFBSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Common.IsInternetConnected(FBSignUpAct.this)) {
                    if (TextUtils.isEmpty(etFirstName.getText().toString().trim())) {
                        Toast.makeText(FBSignUpAct.this, ConstantsClass.toast_firstname_cannot_be_empty, Toast.LENGTH_SHORT).show();
                    } else if (TextUtils.isEmpty(etLastName.getText().toString().trim())) {
                        Toast.makeText(FBSignUpAct.this, ConstantsClass.toast_lastname_cannot_be_empty, Toast.LENGTH_SHORT).show();
                    } else if (TextUtils.isEmpty(mEmailSignUp.getText().toString().trim())) {
                        Toast.makeText(FBSignUpAct.this, ConstantsClass.toast_email_cannot_be_blank, Toast.LENGTH_SHORT).show();
                    } else if (!Common.isValidEmail(mEmailSignUp.getText().toString().trim())) {
                        Toast.makeText(FBSignUpAct.this, ConstantsClass.toast_email_is_not_valid_emailid, Toast.LENGTH_SHORT).show();
                    } else if (TextUtils.isEmpty(mMobileNumberSignUp.getText().toString().trim())) {
                        Toast.makeText(FBSignUpAct.this, ConstantsClass.toast_mobilenumber_cannot_be_empty, Toast.LENGTH_SHORT).show();
                    } else if (mMobileNumberSignUp.getText().toString().trim().length() < 6) {
                        Toast.makeText(FBSignUpAct.this, ConstantsClass.toast_mobilenumber_should_be_atleast_6character, Toast.LENGTH_SHORT).show();
                    } else if (mMobileNumberSignUp.getText().toString().trim().length() > 16) {
                        Toast.makeText(FBSignUpAct.this, ConstantsClass.toast_mobilenumber_should_be_maximum_16character, Toast.LENGTH_SHORT).show();
                    } else {
                        MyApplication.getInstance().trackEvent("Button", "Click", "Fb Login");
                        signInToTheApp(mEmailSignUp.getText().toString(), mFirstName, mLastName, mBirthday, mFBId, mAccessToken, mMobileNumberSignUp.getText().toString());
                    }
                } else {
                    Toast.makeText(FBSignUpAct.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });


        recourseList = this.getResources().getStringArray(R.array.country_code);
        resourceList = this.getResources().getStringArray(R.array.country_code);

        vCountryText = (EditText) findViewById(R.id.AS_et_countrycode);

        vCountryText.setText("+60");
        vCountryText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recourseList = getResources().getStringArray(R.array.country_code);
                final Dialog dialog = new Dialog(FBSignUpAct.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.dialog_country_code);


                vCountryList = (ListView) dialog.findViewById(R.id.DCC_country_list);
                vSearchCountry = (EditText) dialog.findViewById(R.id.DCC_et_search);


                mCountryListAdapter = new CountryListAdapter(FBSignUpAct.this, recourseList, dialog);
                vCountryList.setAdapter(mCountryListAdapter);
                dialog.show();

                vSearchCountry.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                        if (s.length() > 0) {
                            List<String> tempList = new ArrayList<String>();
                            for (int countryCount = 0; countryCount < resourceList.length; countryCount++) {
                                String[] split = resourceList[countryCount].split(",");
                                if (split[2].toLowerCase().startsWith(s.toString())) {
                                    tempList.add(resourceList[countryCount]);
                                }
                            }
                            recourseList = new String[tempList.size()];
                            for (int rcount = 0; rcount < tempList.size(); rcount++) {
                                recourseList[rcount] = tempList.get(rcount);
                            }
                            mCountryListAdapter = new CountryListAdapter(FBSignUpAct.this, recourseList, dialog);
                            vCountryList.setAdapter(mCountryListAdapter);
                        } else {
                            recourseList = resourceList;
                            mCountryListAdapter = new CountryListAdapter(FBSignUpAct.this, recourseList, dialog);
                            vCountryList.setAdapter(mCountryListAdapter);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }
        });


    }


    private void initiateWidgets() {
        mFBSignUpButton = (Button) findViewById(R.id.btnSignup_fb);
        mEmailSignUp = (EditText) findViewById(R.id.eEmailId_fb);
        mMobileNumberSignUp = (EditText) findViewById(R.id.etMobileNumber_fb);
        mFBButton = (LoginButton) findViewById(R.id.login_button_sign_up);
    }

    private void signInToTheApp(final String email, final String firstName, final String lastName, final String birthday, final String id, String token, String mobileNumber) {

        if (!mCustomProgressDialog.isShowing()) {
            mCustomProgressDialog.ChageMessage(FBSignUpAct.this, "Please wait...", "Please wait...");
            mCustomProgressDialog.show(FBSignUpAct.this);
        }

        String deviceId = SessionManager.getInstance().getDeviceToken(FBSignUpAct.this);
        //ConstantsClass.getUniqueID(FBSignUpAct.this);

        DAOSignup.getInstance().Callresponse("",
                firstName,
                lastName,
                vCountryText.getText().toString().trim() + "" + mobileNumber,//Mobile number empty for now
                email,
                "",//Password empty when login from FB
                ConstantsClass.CUSTOMER_TYPE_FACEBOOK, //If it is 2 means trying to login from facebook
                id,
                token.toString(),
                AppSharedValues.getLanguage(),
                deviceId,
                ConstantsClass.DEVICE_TYPE, // If it  is 1 means Android
                new Callback<DAOSignup.Signup>() {

                    @Override
                    public void success(DAOSignup.Signup signup, Response response) {
//                        System.out.println("respo:::" + response.getBody().toString());

                        if (signup.getHttpcode().equals("200")) {

                            Toast.makeText(FBSignUpAct.this, R.string.toast_signup_success, Toast.LENGTH_SHORT).show();

                            saveDataToLocalFromFbLogin(signup);

                            if (mCustomProgressDialog.isShowing()) {
                                mCustomProgressDialog.dismiss();
                            }

                            finish();


                        } else if (signup.getHttpcode().equals("406")) {
//                            if (mCustomProgressDialog.isShowing()) {
//                                mCustomProgressDialog.dismiss();
//                            }
                            if (mCustomProgressDialog.isShowing()) {
                                mCustomProgressDialog.dismiss();
                            }
                           /* if (signup.getError().getCustomer_name() != null && !signup.getError().getCustomer_email().isEmpty()) {
                                Toast.makeText(FBSignUpAct.this, signup.getError().getCustomer_name(), Toast.LENGTH_LONG).show();
                            } else if (signup.getError().getCustomer_last_name() != null && !signup.getError().getCustomer_email().isEmpty()) {
                                Toast.makeText(FBSignUpAct.this, signup.getError().getCustomer_last_name(), Toast.LENGTH_LONG).show();
                            } else if (signup.getError().getCustomer_email() != null && !signup.getError().getCustomer_email().isEmpty()) {
                                Toast.makeText(FBSignUpAct.this, signup.getError().getCustomer_email(), Toast.LENGTH_LONG).show();
                            } else if (signup.getError().getCustomer_mobile() != null && !signup.getError().getCustomer_mobile().isEmpty()) {
                                Toast.makeText(FBSignUpAct.this, signup.getError().getCustomer_mobile(), Toast.LENGTH_LONG).show();
                            }*/
                        } else {
//                            if (mCustomProgressDialog.isShowing()) {
//                                mCustomProgressDialog.dismiss();
//                            }
                            if (mCustomProgressDialog.isShowing()) {
                                mCustomProgressDialog.dismiss();
                            }
                            Toast.makeText(FBSignUpAct.this, signup.getStatus(), Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        String ss = "";
                        if (mCustomProgressDialog.isShowing()) {
                            mCustomProgressDialog.dismiss();
                        }

                        Toast.makeText(FBSignUpAct.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });


    }

    private EventBus bus = EventBus.getDefault();

    private void saveDataToLocalFromFbLogin(DAOSignup.Signup signin) {
        {
            if (signin.getHttpcode().equals("200")) {
                /*UserDetails.setCustomerKey(signin.getData().getCustomer_key());
                UserDetails.setCustomerEmail(signin.getData().getCustomer_email());
                UserDetails.setCustomerName(signin.getData().getCustomer_name());
                UserDetails.setCustomerLastName(signin.getData().getCustomer_last_name());
                UserDetails.setCustomerMobile(signin.getData().getCustomer_mobile());
                UserDetails.setAccessToken(signin.getData().getAccess_token());
                UserDetails.setImageUrl(mImageUrl);

                DBActionsUser.add(
                        UserDetails.getCustomerKey(), UserDetails.getCustomerKey(),
                        UserDetails.getCustomerEmail(),
                        UserDetails.getCustomerName(),
                        UserDetails.getCustomerLastName(),
                        UserDetails.getCustomerMobile(),
                        UserDetails.getAccessToken());

                Toast.makeText(FBSignUpAct.this, R.string.toast_login_success, Toast.LENGTH_SHORT).show();

                bus.post(new AddressEvent());
                bus.post(new MainMenuRefreshEvent());
                bus.post(new SigninEvent());*/

                Intent in = new Intent(FBSignUpAct.this, OTPPAge.class);
                in.putExtra("number", vCountryText.getText().toString().trim() + "" + mMobileNumberSignUp.getText().toString().trim() + "&" + mEmailSignUp.getText().toString());
                in.putExtra("tempckey", signin.getTmp_customer_key());
                in.putExtra("type", "activity");
                startActivity(in);


            } else {
//                if (mCustomProgressDialog.isShowing()) {
//                    mCustomProgressDialog.dismiss();
//                }
                Toast.makeText(FBSignUpAct.this, signin.getStatus(), Toast.LENGTH_LONG).show();
            }
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void onEvent(final DummyEvent ev) {
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    private class CountryListAdapter extends BaseAdapter {
        public String[] mCountryList;
        Dialog mDialog;
        Context mContext;

        public CountryListAdapter(Context context, String[] mCountryName, Dialog dialog) {
            this.mContext = context;
            this.mCountryList = mCountryName;
            this.mDialog = dialog;
        }

        @Override
        public int getCount() {
            return mCountryList.length;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder mHolder;
            if (convertView == null) {
                LayoutInflater mLayoutInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                convertView = mLayoutInflater.inflate(R.layout.adapter_counrty_code, null);
                mHolder = new ViewHolder();
                mHolder.vCountryName = (TextView) convertView.findViewById(R.id.ACC_country_name);
                mHolder.vCountryId = (TextView) convertView.findViewById(R.id.ACC_country_id);
                convertView.setTag(mHolder);
            } else {
                mHolder = (ViewHolder) convertView.getTag();
            }

            final String[] strArray = mCountryList[position].split(",");
            mHolder.vCountryName.setText(strArray[2].trim());
            mHolder.vCountryId.setText("+" + strArray[1].trim());

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vCountryText.setText("+" + (strArray[1]).trim());
                    mDialog.dismiss();
                }
            });

            return convertView;
        }

        private class ViewHolder {
            TextView vCountryName, vCountryId;
        }

    }

}
