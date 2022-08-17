package com.foodpurby.screens;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.foodpurby.util.Common;
import com.foodpurby.util.CustomProgressDialog;
import com.foodpurby.utillities.AppSharedValues;
import com.foodpurby.utillities.SessionManager;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.login.widget.LoginButton;

import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.foodpurby.R.id.etFirstName;
import static com.foodpurby.R.id.etLastName;
import static com.facebook.FacebookSdk.getApplicationContext;

public class FBSignUp extends Fragment {

    EditText mEmailSignUp, mMobileNumberSignUp, mFirstNameSignUp, mLastNameSignUp;
    Button mFBSignUpButton;
    LoginButton mFBButton;
    TextView cancel;
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
    private String mPhoneNo;
    private String mEmail;
    private String mFirstName;
    private String mLastName;
    private String mBirthday;
    private String mFBId;
    private String mAccessToken;
    private String mImageUrl;
    String[] recourseList, resourceList;
    EditText vCountryText, etPassword;
    CountryListAdapter mCountryListAdapter;
    TextInputLayout tilPassword;

    ListView vCountryList;
    EditText vSearchCountry;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param email
     * @param firstName
     * @param lastName
     * @param birthday  Parameter 1.
     * @param id        Parameter 2.
     * @param token     @return A new instance of fragment FBSignUp.
     */
    // TODO: Rename and change types and number of parameters
    public static FBSignUp newInstance(String email, String firstName, String lastName, String birthday, String id, String token, String imageUrl) {
        FBSignUp fragment = new FBSignUp();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, email);
        args.putString(ARG_PARAM2, firstName);
        args.putString(ARG_PARAM3, lastName);
        args.putString(ARG_PARAM4, birthday);
        args.putString(ARG_PARAM5, id);
        args.putString(ARG_PARAM6, token);
        args.putString(ARG_PARAM7, imageUrl);
        fragment.setArguments(args);
        return fragment;
    }

    public FBSignUp() {
        // Required empty public constructor
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_home_page, menu);
        MenuItem item_search = menu.findItem(R.id.action_search);
        item_search.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
        item_search.setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mEmail = getArguments().getString(ARG_PARAM1);
            mFirstName = getArguments().getString(ARG_PARAM2);
            mLastName = getArguments().getString(ARG_PARAM3);
            mBirthday = getArguments().getString(ARG_PARAM4);
            mFBId = getArguments().getString(ARG_PARAM5);
            mAccessToken = getArguments().getString(ARG_PARAM6);
            mImageUrl = getArguments().getString(ARG_PARAM7);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getApplicationContext());
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_fbsign_up, container, false);

        initiateWidgets(v);

        etPassword = (EditText) v.findViewById(R.id.etPassword);
        etPassword.setVisibility(View.GONE);

        tilPassword = (TextInputLayout) v.findViewById(R.id.tilPassword);
        tilPassword.setVisibility(View.GONE);

        if (mFirstName != null) {
            mFirstNameSignUp.setText(mFirstName);
        }
        if (mLastName != null) {
            mLastNameSignUp.setText(mLastName);
        }
        if (mEmail != null) {
            mEmailSignUp.setText(mEmail);
            // mEmailSignUp.setEnabled(true);
        }

        mFBSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Common.IsInternetConnected(getActivity())) {
                    if (TextUtils.isEmpty(mFirstNameSignUp.getText().toString().trim())) {
                        Toast.makeText(getActivity(), ConstantsClass.toast_firstname_cannot_be_empty, Toast.LENGTH_SHORT).show();
                    } else if (TextUtils.isEmpty(mLastNameSignUp.getText().toString().trim())) {
                        Toast.makeText(getActivity(), ConstantsClass.toast_lastname_cannot_be_empty, Toast.LENGTH_SHORT).show();
                    } else if (TextUtils.isEmpty(mEmailSignUp.getText().toString().trim())) {
                        Toast.makeText(getActivity(), ConstantsClass.toast_email_cannot_be_blank, Toast.LENGTH_SHORT).show();
                    } else if (!Common.isValidEmail(mEmailSignUp.getText().toString().trim())) {
                        Toast.makeText(getActivity(), ConstantsClass.toast_email_is_not_valid_emailid, Toast.LENGTH_SHORT).show();
                    } else if (TextUtils.isEmpty(mMobileNumberSignUp.getText().toString().trim())) {
                        Toast.makeText(getActivity(), ConstantsClass.toast_mobilenumber_cannot_be_empty, Toast.LENGTH_SHORT).show();
                    } else if (mMobileNumberSignUp.getText().toString().trim().length() < 6) {
                        Toast.makeText(getActivity(), ConstantsClass.toast_mobilenumber_should_be_atleast_6character, Toast.LENGTH_SHORT).show();
                    } else if (mMobileNumberSignUp.getText().toString().trim().length() > 16) {
                        Toast.makeText(getActivity(), ConstantsClass.toast_mobilenumber_should_be_maximum_16character, Toast.LENGTH_SHORT).show();
                    } else {
                        MyApplication.getInstance().trackEvent("Button", "Click", "Fb Login");
                        signInToTheApp(mEmailSignUp.getText().toString(), mFirstName, mLastName, mBirthday, mFBId, mAccessToken, mMobileNumberSignUp.getText().toString());
                    }
                } else {
                    Toast.makeText(getActivity(), "No Internet Connnection", Toast.LENGTH_SHORT).show();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        recourseList = this.getResources().getStringArray(R.array.country_code);
        resourceList = this.getResources().getStringArray(R.array.country_code);

        vCountryText = (EditText) v.findViewById(R.id.AS_et_countrycode);

        vCountryText.setText("+91");
      /*  vCountryText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recourseList = getResources().getStringArray(R.array.country_code);
                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.dialog_country_code);


                vCountryList = (ListView) dialog.findViewById(R.id.DCC_country_list);
                vSearchCountry = (EditText) dialog.findViewById(R.id.DCC_et_search);


                mCountryListAdapter = new CountryListAdapter(getActivity(), recourseList, dialog);
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
                            mCountryListAdapter = new CountryListAdapter(getActivity(), recourseList, dialog);
                            vCountryList.setAdapter(mCountryListAdapter);
                        } else {
                            recourseList = resourceList;
                            mCountryListAdapter = new CountryListAdapter(getActivity(), recourseList, dialog);
                            vCountryList.setAdapter(mCountryListAdapter);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }
        });*/


        return v;
    }

    private void initiateWidgets(View v) {
        mFBSignUpButton = (Button) v.findViewById(R.id.btnSignup_fb);
        mEmailSignUp = (EditText) v.findViewById(R.id.eEmailId_fb);
        mFirstNameSignUp = (EditText) v.findViewById(etFirstName);
        mLastNameSignUp = (EditText) v.findViewById(etLastName);
        mMobileNumberSignUp = (EditText) v.findViewById(R.id.etMobileNumber_fb);
        mFBButton = (LoginButton) v.findViewById(R.id.login_button_sign_up);
        cancel = (TextView) v.findViewById(R.id.tvCancel);
    }

    private void signInToTheApp(final String email, final String firstName, final String lastName, final String birthday, final String id, String token, String mobileNumber) {

        if (!mCustomProgressDialog.isShowing()) {
            mCustomProgressDialog.ChageMessage(getActivity(), "Please wait...", "Please wait...");
            mCustomProgressDialog.show(getActivity());
        }

        String deviceId = SessionManager.getInstance().getDeviceToken(getActivity());
        //ConstantsClass.getUniqueID(getActivity());

        DAOSignup.getInstance().Callresponse("",
                firstName,
                lastName,
                mobileNumber,//Mobile number empty for now
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
                        System.out.println("respo:::" + response.getBody().toString());

                        if (signup.getHttpcode().equals("200")) {

                            Toast.makeText(getActivity(), R.string.toast_signup_success, Toast.LENGTH_SHORT).show();

                            saveDataToLocalFromFbLogin(signup);

                            if (mCustomProgressDialog.isShowing()) {
                                mCustomProgressDialog.dismiss();
                            }

                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            fragmentManager.beginTransaction().replace(R.id.container, Restaurants.newInstance("", "")).commit();


                        } else if (signup.getHttpcode().equals("406")) {
//                            if (mCustomProgressDialog.isShowing()) {
//                                mCustomProgressDialog.dismiss();
//                            }
                            if (mCustomProgressDialog.isShowing()) {
                                mCustomProgressDialog.dismiss();
                            }

                            /*if (signup.getError().getCustomer_name() != null && !signup.getError().getCustomer_email().isEmpty()) {
                                Toast.makeText(getActivity(), signup.getError().getCustomer_name(), Toast.LENGTH_LONG).show();
                            } else if (signup.getError().getCustomer_last_name() != null && !signup.getError().getCustomer_email().isEmpty()) {
                                Toast.makeText(getActivity(), signup.getError().getCustomer_last_name(), Toast.LENGTH_LONG).show();
                            } else if (signup.getError().getCustomer_email() != null && !signup.getError().getCustomer_email().isEmpty()) {
                                Toast.makeText(getActivity(), signup.getError().getCustomer_email(), Toast.LENGTH_LONG).show();
                            } else if (signup.getError().getCustomer_mobile() != null && !signup.getError().getCustomer_mobile().isEmpty()) {
                                Toast.makeText(getActivity(), signup.getError().getCustomer_mobile(), Toast.LENGTH_LONG).show();
                            }*/
                        } else {
//                            if (mCustomProgressDialog.isShowing()) {
//                                mCustomProgressDialog.dismiss();
//                            }
                            if (mCustomProgressDialog.isShowing()) {
                                mCustomProgressDialog.dismiss();
                            }
                            Toast.makeText(getActivity(), signup.getStatus(), Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        String ss = "";
                        if (mCustomProgressDialog.isShowing()) {
                            mCustomProgressDialog.dismiss();
                        }

                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });


    }

    private void saveDataToLocalFromFbLogin(DAOSignup.Signup signin) {
        {
            if (signin.getHttpcode().equals("200")) {
               /* UserDetails.setCustomerKey(signin.getData().getCustomer_key());
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

                Toast.makeText(getActivity(), R.string.toast_login_success, Toast.LENGTH_SHORT).show();
*/

                Intent in = new Intent(getActivity(), OTPPAge.class);
                in.putExtra("number", vCountryText.getText().toString().trim() + "" + mMobileNumberSignUp.getText().toString().trim() + "&" + mEmailSignUp.getText().toString());
                in.putExtra("tempckey", signin.getTmp_customer_key());
                in.putExtra("type", "fragment");
                startActivity(in);


            } else {
//                if (mCustomProgressDialog.isShowing()) {
//                    mCustomProgressDialog.dismiss();
//                }
                Toast.makeText(getActivity(), signin.getStatus(), Toast.LENGTH_LONG).show();
            }
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    public void onEvent(final DummyEvent ev) {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        EventBus.getDefault().register(this);
        mCustomProgressDialog = CustomProgressDialog.getInstance();
    }

    @Override
    public void onDetach() {
        super.onDetach();
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
