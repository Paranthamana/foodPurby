package com.foodpurby.screens;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.foodpurby.R;
import com.foodpurby.adapters.AddressAdapter;
import com.foodpurby.baseclass.MyActivity;
import com.foodpurby.events.AddRemoveToCartEvent;
import com.foodpurby.events.SigninEvent;
import com.foodpurby.events.UserProfileUpdatedEvent;
import com.foodpurby.model.DAOSignout;
import com.foodpurby.model.DAOUserProfile;
import com.foodpurby.ondbstorage.UserAddress;
import com.foodpurby.util.Common;
import com.foodpurby.util.CustomProgressDialog;
import com.foodpurby.utillities.UserDetails;
import com.sloop.fonts.FontsManager;

import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by android1 on 12/29/2015.
 */
public class UserProfile extends Activity {

    private EventBus bus = EventBus.getDefault();

    private ListView lvAddress;
    private AddressAdapter mpAddressAdapter;
    Toolbar mToolbar;
    TextView mToolHeading;
    TextView btnAddNew;
    TextView btnChangePassword;

    Button btnEditProfile;
    TextView tvFirstName;
    TextView tvLastName;
    TextView tvEmail;
    TextView tvMobileNo;
    Button btnLogout;
    private CustomProgressDialog mCustomProgressDialog;
    EditText etDummy;
    TextView tvGender_txt, tvDob_txt;
    TextView etDateOfBirth;
    RadioButton male, female;
    RadioGroup vGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_profile);

     //   FontsManager.initFormAssets(this, "Lato-Light.ttf");
      //  FontsManager.changeFonts(this);

        btnLogout = (Button) findViewById(R.id.btnLogout);

        etDummy = (EditText) findViewById(R.id.etDummy);
        etDummy.requestFocus();

        btnEditProfile = (Button) findViewById(R.id.btnEditProfile);
        mCustomProgressDialog = CustomProgressDialog.getInstance();
        tvFirstName = (TextView) findViewById(R.id.tvFirstName);
        tvLastName = (TextView) findViewById(R.id.tvLastName);
        tvEmail = (TextView) findViewById(R.id.tvEmail);
        tvMobileNo = (TextView) findViewById(R.id.tvMobileNo);
        etDateOfBirth = (TextView) findViewById(R.id.etDateOfBirth);
        tvGender_txt = (TextView) findViewById(R.id.gender_txt);
        tvDob_txt = (TextView) findViewById(R.id.dob_txt);
        vGender = (RadioGroup) findViewById(R.id.radioGender);
        male = (RadioButton) findViewById(R.id.radioMale);
        female = (RadioButton) findViewById(R.id.radioFemale);

        mToolbar = (Toolbar) findViewById(R.id.tb_header);
        mToolbar.setNavigationIcon(R.drawable.back);
        mToolHeading = (TextView) findViewById(R.id.tool_title);
        mToolHeading.setText(getString(R.string.txt_my_profile));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                MyActivity.DisplayHomePage(UserProfile.this);
                finish();
            }
        });

        FrameLayout flCart = (FrameLayout) findViewById(R.id.flCart);
        flCart.setVisibility(View.GONE);
        btnChangePassword = (TextView) findViewById(R.id.btnChangePassword);
        View mHeaderView = getLayoutInflater().inflate(R.layout.profile_header, null);
       // FontsManager.changeFonts(mHeaderView);
        LinearLayout vLinear = (LinearLayout) mHeaderView.findViewById(R.id.PH_ll);
        vLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnAddNew = (TextView) mHeaderView.findViewById(R.id.btnAddNew);
        lvAddress = (ListView) findViewById(R.id.lvAddress);
        lvAddress.addHeaderView(mHeaderView);
        mpAddressAdapter = new AddressAdapter(UserProfile.this, "1");
        lvAddress.setAdapter(mpAddressAdapter);
        lvAddress.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }

                // Handle ListView touch events.
                v.onTouchEvent(event);
                return true;
            }
        });

        Common.GetAddress(UserProfile.this, mCustomProgressDialog, bus);

        mpAddressAdapter.notifyChanges();


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.getInstance().trackEvent("Button", "Click", "Logout");
                DAOSignout.getInstance().Callresponse("",
                        UserDetails.getCustomerEmail(),
                        new Callback<DAOSignout.Signout>() {

                            @Override
                            public void success(DAOSignout.Signout signout, Response response) {

                                if (signout.getHttpcode().equals("200")) {
                                    UserDetails.clearAll();
                                    //DBActionsUser.delete();
                                    //DBActionsCart.deleteAll();
                                    finish();
                                } else {
                                    Toast.makeText(UserProfile.this, signout.getMessage(), Toast.LENGTH_LONG).show();
                                }

                            }

                            @Override
                            public void failure(RetrofitError error) {
                                Toast.makeText(UserProfile.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                                MyApplication.getInstance().trackException(error);
                            }
                        });
            }
        });

        lvAddress.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MyApplication.getInstance().trackEvent("Listview", "Click", "Address list item click");

                AddressAdapter.ViewHolder addr = (AddressAdapter.ViewHolder) view.getTag();
                String userAddressKey = ((UserAddress) addr.tvAddressAnnotation.getTag()).getUserAddressKey();
                MyActivity.DisplayAddressEdit(UserProfile.this, userAddressKey);


            }
        });

        notifyChanges();

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.getInstance().trackEvent("Button", "Click", "Change Password");
                MyActivity.DisplayUpdatePassword(UserProfile.this);
            }
        });

        btnAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.getInstance().trackEvent("Button", "Click", "AddNew");
                MyActivity.DisplayNewAddress(UserProfile.this);
            }
        });


        tvFirstName.setText(UserDetails.getCustomerName());
        tvLastName.setText(UserDetails.getCustomerLastName());
        tvEmail.setText(UserDetails.getCustomerEmail());
        tvMobileNo.setText(UserDetails.getCustomerMobile());
        etDateOfBirth.setText(UserDetails.getCustomer_dob());

        if (UserDetails.getCustomer_gender().equalsIgnoreCase("1")) {
            male.setChecked(true);
            female.setChecked(false);
            male.setEnabled(false);
            female.setEnabled(false);
        } else {
            female.setChecked(true);
            male.setChecked(false);
            male.setEnabled(false);
            female.setEnabled(false);
        }

        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.getInstance().trackEvent("Button", "Click", "Edit Profile");
                MyActivity.DisplayUserProfileEdit(UserProfile.this);
            }
        });

        GetUserProfile();
    }

    private void GetUserProfile() {
        if (!UserDetails.getCustomerKey().trim().isEmpty()) {
            DAOUserProfile.getInstance().Callresponse("", UserDetails.getCustomerKey(), new Callback<DAOUserProfile.Profile>() {

                @Override
                public void success(DAOUserProfile.Profile profile, Response response) {

                    if (profile.getHttpcode().equals("200")) {

                        UserDetails.setCustomerName(profile.getData().getProfile().getCustomer_name());
                        UserDetails.setCustomerLastName(profile.getData().getProfile().getCustomer_last_name());
                        UserDetails.setCustomerEmail(profile.getData().getProfile().getCustomer_email());
                        UserDetails.setCustomerMobile(profile.getData().getProfile().getCustomer_mobile());
                        UserDetails.setCustomer_dob(profile.getData().getProfile().getCustomer_dob());
                        UserDetails.setCustomer_gender(profile.getData().getProfile().getCustomer_gender());

                        tvFirstName.setText(profile.getData().getProfile().getCustomer_name());
                        tvLastName.setText(profile.getData().getProfile().getCustomer_last_name());
                        tvEmail.setText(profile.getData().getProfile().getCustomer_email());
                        tvMobileNo.setText(profile.getData().getProfile().getCustomer_mobile());
                        etDateOfBirth.setText(UserDetails.getCustomer_dob());

                        if (UserDetails.getCustomer_gender().equalsIgnoreCase("1")) {
                            male.setChecked(true);
                            female.setChecked(false);
                        } else {
                            female.setChecked(true);
                            male.setChecked(false);
                        }
                        bus.post(new SigninEvent());
                    }

                }

                @Override
                public void failure(RetrofitError error) {
                    String ss = "";
                    Toast.makeText(UserProfile.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    MyApplication.getInstance().trackException(error);
                }
            });
        } else {
            Toast.makeText(UserProfile.this, R.string.toast_please_signin, Toast.LENGTH_LONG).show();
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

        return super.onOptionsItemSelected(item);
    }

    public void onEvent(final UserProfileUpdatedEvent ev) {
        runOnUiThread(new Runnable() {
            public void run() {
                GetUserProfile();
            }
        });
    }

    public void onEvent(final AddRemoveToCartEvent ev) {
        runOnUiThread(new Runnable() {
            public void run() {
                notifyChanges();
            }
        });
    }

    public void onEvent(final SigninEvent ev) {
        runOnUiThread(new Runnable() {
            public void run() {
                mpAddressAdapter.notifyChanges();
            }
        });
    }

    private void notifyChanges() {
        //btnAddress.setText("Pay order total " + Common.getPriceWithCurrencyCode(DBActionsCart.getTotalCartPriceIncludingTax(AppSharedValues.getSelectedRestaurantBranchKey()).toString()));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
