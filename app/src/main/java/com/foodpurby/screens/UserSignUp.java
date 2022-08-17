package com.foodpurby.screens;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.foodpurby.util.CustomProgressDialog;
import com.foodpurby.utillities.AppSharedValues;
import com.foodpurby.utillities.SessionManager;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.sloop.fonts.FontsManager;

import org.json.JSONObject;

import java.util.Arrays;

import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.foodpurby.screens.UserSignin.isValidEmail;

public class UserSignUp extends Activity implements View.OnClickListener {
    private EventBus bus = EventBus.getDefault();
    private CustomProgressDialog mCustomProgressDialog;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    EditText etFirstName;
    EditText etLastName;
    EditText etMobileNumber;

    EditText etEmailId;
    EditText etPassword;
    Button btnSignup, mFBSignUp;

    LoginButton mfbSignUpButton;

    TextView tvCancel;
    TextView mToolHeading;

    CallbackManager callbackManager;
    AccessTokenTracker accessTokenTracker;
    String[] recourseList, resourceList;

    EditText vCountryText;

    private String blockCharacterSet = "~#^|$%&*! ";
    ListView vCountryList;
    EditText vSearchCountry;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    CountryListAdapter mCountryListAdapter;
    private String PreviousScreen = "";
    private String get_first, get_last, get_mobile, get_email, get_password;

    public UserSignUp() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.fragment_signup);

        //FontsManager.initFormAssets(this, "Lato-Light.ttf");
       // FontsManager.changeFonts(this);

        EventBus.getDefault().register(this);
        mCustomProgressDialog = CustomProgressDialog.getInstance();

        Toolbar mToolbar = (Toolbar) findViewById(R.id.tb_header);
        mToolbar.setNavigationIcon(R.drawable.back);
        mToolHeading = (TextView) findViewById(R.id.tool_title);
        mToolHeading.setText(getString(R.string.txt_sign_up));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initiateWidgets();

        mFBSignUp.setOnClickListener(this);

        setupFBSignUp();

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etLastName = (EditText) findViewById(R.id.etLastName);
        etMobileNumber = (EditText) findViewById(R.id.etMobileNumber);
        etEmailId = (EditText) findViewById(R.id.etEmailId);
        etPassword = (EditText) findViewById(R.id.etPassword);

        etFirstName.setFilters(new InputFilter[]{
                filter
        });

        etLastName.setFilters(new InputFilter[]{
                filter
        });
        recourseList = this.getResources().getStringArray(R.array.country_code);
        resourceList = this.getResources().getStringArray(R.array.country_code);

        vCountryText = (EditText) findViewById(R.id.AS_et_countrycode);

        vCountryText.setText("+91");
      /*  vCountryText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recourseList = getResources().getStringArray(R.array.country_code);
                final Dialog dialog = new Dialog(UserSignUp.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.dialog_country_code);


                vCountryList = (ListView) dialog.findViewById(R.id.DCC_country_list);
                vSearchCountry = (EditText) dialog.findViewById(R.id.DCC_et_search);


                mCountryListAdapter = new CountryListAdapter(UserSignUp.this, recourseList, dialog);
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
                            mCountryListAdapter = new CountryListAdapter(UserSignUp.this, recourseList, dialog);
                            vCountryList.setAdapter(mCountryListAdapter);
                        } else {
                            recourseList = resourceList;
                            mCountryListAdapter = new CountryListAdapter(UserSignUp.this, recourseList, dialog);
                            vCountryList.setAdapter(mCountryListAdapter);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }
        });*/


        btnSignup.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             get_first = etFirstName.getText().toString();
                                             get_last = etLastName.getText().toString();
                                             get_mobile = etMobileNumber.getText().toString();
                                             get_email = etEmailId.getText().toString();
                                             get_password = etPassword.getText().toString();
                                             /*if (get_first.equals("") && get_last.equals("") && get_mobile.equals("") && get_email.equals("") && get_password.equals("")) {
                                                 Toast.makeText(UserSignUp.this, "Please enter the field", Toast.LENGTH_SHORT).show();
                                             } else*/
                                             if (get_first.isEmpty()) {
                                                 Toast.makeText(UserSignUp.this, "Please enter firstname", Toast.LENGTH_SHORT).show();
                                             } else if (get_last.isEmpty()) {
                                                 Toast.makeText(UserSignUp.this, "Please enter lastname", Toast.LENGTH_SHORT).show();
                                             } else if (get_email.isEmpty()) {
                                                 Toast.makeText(UserSignUp.this, "Email id empty", Toast.LENGTH_LONG).show();
                                             } else if (!isValidEmail(get_email)) {
                                                 Toast.makeText(UserSignUp.this, "Enter valid Email id", Toast.LENGTH_LONG).show();
                                             } else if (get_mobile.isEmpty()) {
                                                 Toast.makeText(UserSignUp.this, "Please enter mobile number", Toast.LENGTH_SHORT).show();
                                             } else if (get_mobile.length() < 6 || get_mobile.length() > 13) {
                                                 Toast.makeText(UserSignUp.this, "Please enter valid mobile number", Toast.LENGTH_SHORT).show();
                                             } else if (get_password.isEmpty()) {
                                                 Toast.makeText(UserSignUp.this, "Enter the password", Toast.LENGTH_LONG).show();
                                             } else if (get_password.length() < 6 || get_password.length() > 17) {
                                                 Toast.makeText(UserSignUp.this, "Enter valid password", Toast.LENGTH_LONG).show();
                                             } else {
                                                 MyApplication.getInstance().trackEvent("Button", "Click", "SignUp");
                                                 normalSignUp();
                                             }

                                         }
                                     }

        );
    }

    private InputFilter filter = new InputFilter() {

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

            if (source != null && blockCharacterSet.contains(("" + source))) {
                return "";
            }
            return null;
        }
    };

    private void normalSignUp() {


        if (mCustomProgressDialog != null && !mCustomProgressDialog.isShowing()) {
            mCustomProgressDialog.ChageMessage(UserSignUp.this, getString(R.string.dialog_please_wait), getString(R.string.dialog_please_wait));
            mCustomProgressDialog.show(UserSignUp.this);
        }
        String deviceId = SessionManager.getInstance().getDeviceToken(UserSignUp.this);//ConstantsClass.getUniqueID(UserSignUp.this);

        DAOSignup.getInstance().Callresponse("",
                etFirstName.getText().toString(),
                etLastName.getText().toString(),
                vCountryText.getText().toString().trim() + "" + etMobileNumber.getText().toString(),
                etEmailId.getText().toString(),
                etPassword.getText().toString(),
                "1",
                "",
                "",
                AppSharedValues.getLanguage(),
                deviceId,
                ConstantsClass.DEVICE_TYPE, // If it 1 is  means Android
                new Callback<DAOSignup.Signup>() {

                    @Override
                    public void success(DAOSignup.Signup signup, Response response) {

                        if (signup.getHttpcode().equals("200")) {
                            /*UserDetails.setCustomerKey(signup.getData().getCustomer_key());
                            UserDetails.setCustomerEmail(signup.getData().getCustomer_email());
                            UserDetails.setCustomerName(signup.getData().getCustomer_name());
                            UserDetails.setCustomerLastName(signup.getData().getCustomer_last_name());
                            UserDetails.setCustomerMobile(signup.getData().getCustomer_mobile());
                            UserDetails.setAccessToken(signup.getData().getAccess_token());

                            DBActionsUser.add(
                                    UserDetails.getCustomerKey(), UserDetails.getCustomerKey(),
                                    UserDetails.getCustomerEmail(),
                                    UserDetails.getCustomerName(),
                                    UserDetails.getCustomerLastName(),
                                    UserDetails.getCustomerMobile(),
                                    UserDetails.getAccessToken());

                            Toast.makeText(UserSignUp.this, R.string.txt_singnup_sucess, Toast.LENGTH_SHORT).show();

                            if (AppSharedValues.SignupStatus.equalsIgnoreCase("1")) {
                                bus.post(new SigninEvent());
                            }
                            */

                            if (mCustomProgressDialog.isShowing()) {
                                mCustomProgressDialog.dismiss();
                            }

                            Intent in = new Intent(UserSignUp.this, OTPPAge.class);
                            in.putExtra("number", vCountryText.getText().toString().trim() + "" + etMobileNumber.getText().toString() + "&" + etEmailId.getText().toString());
                            in.putExtra("tempckey", signup.getTmp_customer_key());
                            startActivity(in);
                            finish();
                        } else if (signup.getHttpcode().equals("406")) {
                            if (mCustomProgressDialog.isShowing()) {
                                mCustomProgressDialog.dismiss();
                            }

                           /* if (signup.getError().getCustomer_name() != null && !signup.getError().getCustomer_email().isEmpty()) {
                                Toast.makeText(UserSignUp.this, signup.getError().getCustomer_name(), Toast.LENGTH_LONG).show();
                            } else if (signup.getError().getCustomer_last_name() != null && !signup.getError().getCustomer_email().isEmpty()) {
                                Toast.makeText(UserSignUp.this, signup.getError().getCustomer_last_name(), Toast.LENGTH_LONG).show();
                            } else if (signup.getError().getCustomer_email() != null && !signup.getError().getCustomer_email().isEmpty()) {
                                Toast.makeText(UserSignUp.this, signup.getError().getCustomer_email(), Toast.LENGTH_LONG).show();
                            } else if (signup.getError().getCustomer_mobile() != null && !signup.getError().getCustomer_mobile().isEmpty()) {
                                Toast.makeText(UserSignUp.this, signup.getError().getCustomer_mobile(), Toast.LENGTH_LONG).show();
                            }*/
                        } else {
                            if (mCustomProgressDialog.isShowing()) {
                                mCustomProgressDialog.dismiss();
                            }
                            Toast.makeText(UserSignUp.this, signup.getStatus(), Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        String ss = "";
                        if (mCustomProgressDialog.isShowing()) {
                            mCustomProgressDialog.dismiss();
                        }

                        Toast.makeText(UserSignUp.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

    }

    private void initiateWidgets() {
        btnSignup = (Button) findViewById(R.id.btnSignup);
        tvCancel = (TextView) findViewById(R.id.tvCancel);
        mfbSignUpButton = (LoginButton) findViewById(R.id.login_button_sign_up);
        mFBSignUp = (Button) findViewById(R.id.facebook_sign_up);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    public void onEvent(final DummyEvent ev) {
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.facebook_sign_up) {
            mfbSignUpButton.performClick();
        }
    }


    private void setupFBSignUp() {
        mfbSignUpButton.setReadPermissions(Arrays.asList("public_profile, email"));
        callbackManager = CallbackManager.Factory.create();

        mfbSignUpButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                Toast.makeText(UserSignUp.this, getString(R.string.toast_sucess).toUpperCase(), Toast.LENGTH_LONG).show();

                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {

                                Log.d("FB response", object + "*");
                                try {
                                    String email = "";//= object.getString("email");
                                    String birthday = "";//= object.getString("birthday");
                                    String id = "";//= object.getString("id");
                                    String firstName = "";//= object.getString("name");
                                    String lastName = "";
//                                    mFBDetails.setText(name + "\n" + email + "\n" + birthday + "\n" + id);


                                    String imageUrl = "https://graph.facebook.com/" + id + "/picture?type=large";

                                    try {
                                        email = object.getString("email") == null ? "" : object.getString("email");
                                        Log.e("email", email + "**");

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                    try {
                                        firstName = object.getString("first_name") == null ? "" : object.getString("first_name");
                                        lastName = object.getString("last_name") == null ? "" : object.getString("last_name");
                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                    }

                                    try {
                                        birthday = object.getString("birthday") == null ? "" : object.getString("birthday");
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                    try {
                                        id = object.getString("id") == null ? "" : object.getString("id");
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    AccessToken token = AccessToken.getCurrentAccessToken();
                                    if (token != null) {
                                        Log.d("token", token.toString() + "*");
//                                        Toast.makeText(getActivity(), token.toString(), Toast.LENGTH_LONG).show();
                                    }


                                    signInToTheApp(email, firstName, lastName, birthday, id, token);

//                                    Picasso.with(getActivity()).load(imageUrl).into(mFBImage);

//                                    setFBDataToSharedPreference(id, email, birthday, name, imageUrl, token);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        });


                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, first_name, last_name, email, gender, birthday");
                request.setParameters(parameters);
                request.executeAsync();


/**
 * AccessTokenTracker to manage logout
 */
                accessTokenTracker = new AccessTokenTracker() {
                    @Override
                    protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken,
                                                               AccessToken currentAccessToken) {

                    }
                };

            }

            @Override
            public void onCancel() {
                Toast.makeText(UserSignUp.this, getString(R.string.toast_user_cancel).toUpperCase(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(UserSignUp.this, getString(R.string.toast_error).toUpperCase(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void signInToTheApp(String email, String firstName, String lastName, String birthday, String id, AccessToken token) {

        String deviceId = SessionManager.getInstance().getDeviceToken(UserSignUp.this);


        DAOSignup.getInstance().Callresponse("",
                firstName,
                lastName,
                "",//Mobile number empty for now
                email,
                "",//Password empty for now
                ConstantsClass.CUSTOMER_TYPE_FACEBOOK, //If it is 2 means trying to login from facebook
                id,
                token.toString(),
                AppSharedValues.getLanguage(),
                deviceId,
                ConstantsClass.DEVICE_TYPE, // If it  is 1 means Android
                new Callback<DAOSignup.Signup>() {

                    @Override
                    public void success(DAOSignup.Signup signup, Response response) {
                        System.out.println("respo:::" + response.getBody().toString());

                        if (signup.getHttpcode().equals("200")) {

                            Toast.makeText(UserSignUp.this, getString(R.string.toast_signup_success), Toast.LENGTH_SHORT).show();

                            if (mCustomProgressDialog.isShowing()) {
                                mCustomProgressDialog.dismiss();
                            }


                            finish();


                        } else if (signup.getHttpcode().equals("406")) {
                            if (mCustomProgressDialog.isShowing()) {
                                mCustomProgressDialog.dismiss();
                            }

                            /*if (signup.getError().getCustomer_name() != null && !signup.getError().getCustomer_email().isEmpty()) {
                                Toast.makeText(UserSignUp.this, signup.getError().getCustomer_name(), Toast.LENGTH_LONG).show();
                            } else if (signup.getError().getCustomer_last_name() != null && !signup.getError().getCustomer_email().isEmpty()) {
                                Toast.makeText(UserSignUp.this, signup.getError().getCustomer_last_name(), Toast.LENGTH_LONG).show();
                            } else if (signup.getError().getCustomer_email() != null && !signup.getError().getCustomer_email().isEmpty()) {
                                Toast.makeText(UserSignUp.this, signup.getError().getCustomer_email(), Toast.LENGTH_LONG).show();
                            } else if (signup.getError().getCustomer_mobile() != null && !signup.getError().getCustomer_mobile().isEmpty()) {
                                Toast.makeText(UserSignUp.this, signup.getError().getCustomer_mobile(), Toast.LENGTH_LONG).show();
                            }*/
                        } else {
                            if (mCustomProgressDialog.isShowing()) {
                                mCustomProgressDialog.dismiss();
                            }
                            Toast.makeText(UserSignUp.this, signup.getStatus(), Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        String ss = "";
                        if (mCustomProgressDialog.isShowing()) {
                            mCustomProgressDialog.dismiss();
                        }

                        Toast.makeText(UserSignUp.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

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
