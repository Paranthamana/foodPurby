package com.foodpurby.screens;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.foodpurby.R;
import com.foodpurby.events.AddRemoveToCartEvent;
import com.foodpurby.events.SigninEvent;
import com.foodpurby.events.UserProfileUpdatedEvent;
import com.foodpurby.model.DAOUserProfile;
import com.foodpurby.model.DAOUserProfileEdit;
import com.foodpurby.util.CustomProgressDialog;
import com.foodpurby.utillities.AppSharedValues;
import com.foodpurby.utillities.UserDetails;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by android1 on 12/29/2015.
 */
public class UserProfileEdit extends Activity {

    private EventBus bus = EventBus.getDefault();
    private CustomProgressDialog mCustomProgressDialog;

    Toolbar mToolbar;
    TextView mToolHeading;

    EditText etFirstName;
    EditText etLastName;
    TextView etMobileNo;
    TextView tvEmail;
    EditText etDateOfBirth;
    RadioButton male, female;
    RadioGroup vGender;
    String genderId;
    private DatePickerDialog fromDatePickerDialog;
    Button btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_profile_edit);
        mCustomProgressDialog = CustomProgressDialog.getInstance();

        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etLastName = (EditText) findViewById(R.id.etLastName);
        tvEmail = (TextView) findViewById(R.id.tvEmail);
        etMobileNo = (TextView) findViewById(R.id.etMobileNo);
        etDateOfBirth = (EditText) findViewById(R.id.etDateOfBirth);
        vGender = (RadioGroup) findViewById(R.id.radioGender);
        male = (RadioButton) findViewById(R.id.radioMale);
        female = (RadioButton) findViewById(R.id.radioFemale);
        male.setTag("1");
        female.setTag("2");
        genderId = "1";


        mToolbar = (Toolbar) findViewById(R.id.tb_header);
        mToolbar.setNavigationIcon(R.drawable.back);
        mToolHeading = (TextView) findViewById(R.id.tool_title);
        mToolHeading.setText(getString(R.string.txt_edit_profile));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        FrameLayout flCart = (FrameLayout) findViewById(R.id.flCart);
        flCart.setVisibility(View.GONE);

        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        etDateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setDateTimeField();
            }


        });

        vGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                genderId = (String) radioButton.getTag();
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (genderId == null) {
                    Toast.makeText(UserProfileEdit.this, "Please select gender", Toast.LENGTH_SHORT).show();
                } else {
                    MyApplication.getInstance().trackEvent("Button", "Click", "Profile Update");
                    if (mCustomProgressDialog != null && !mCustomProgressDialog.isShowing()) {
                        mCustomProgressDialog.ChageMessage(UserProfileEdit.this, "Please wait...", "Please wait...");
                        mCustomProgressDialog.show(UserProfileEdit.this);
                    }

                    DAOUserProfileEdit.getInstance().Callresponse("",
                            UserDetails.getCustomerKey(),
                            etFirstName.getText().toString(),
                            etLastName.getText().toString(),
                            etMobileNo.getText().toString(),
                            AppSharedValues.getLanguage(),
                            etDateOfBirth.getText().toString().trim(),
                            genderId,
                            new Callback<DAOUserProfileEdit.UserProfile>() {

                                @Override
                                public void success(DAOUserProfileEdit.UserProfile userProfile, Response response) {

                                    if (userProfile.getHttpcode().equals("200")) {
                                        Toast.makeText(UserProfileEdit.this, userProfile.getMessage(), Toast.LENGTH_LONG).show();
                                        bus.post(new UserProfileUpdatedEvent());
                                        bus.post(new SigninEvent());
                                        if (mCustomProgressDialog.isShowing()) {
                                            mCustomProgressDialog.dismiss();
                                        }
                                        finish();
                                    } else {
                                        if (userProfile.getError() != null && userProfile.getError().getCustomer_name() != null) {
                                            Toast.makeText(UserProfileEdit.this, userProfile.getError().getCustomer_name(), Toast.LENGTH_LONG).show();
                                        } else if (userProfile.getError() != null && userProfile.getError().getCustomer_last_name() != null) {
                                            Toast.makeText(UserProfileEdit.this, userProfile.getError().getCustomer_last_name(), Toast.LENGTH_LONG).show();
                                        } else if (userProfile.getError() != null && userProfile.getError().getCustomer_mobile() != null) {
                                            Toast.makeText(UserProfileEdit.this, userProfile.getError().getCustomer_mobile(), Toast.LENGTH_LONG).show();
                                        } else if (userProfile.getError() != null && userProfile.getError().getCustomer_email() != null) {
                                            Toast.makeText(UserProfileEdit.this, userProfile.getError().getCustomer_email(), Toast.LENGTH_LONG).show();
                                        } else {
                                            Toast.makeText(UserProfileEdit.this, userProfile.getMessage(), Toast.LENGTH_LONG).show();
                                        }

                                        if (mCustomProgressDialog.isShowing()) {
                                            mCustomProgressDialog.dismiss();
                                        }
                                    }

                                }

                                @Override
                                public void failure(RetrofitError error) {
                                    String ss = "";
                                    Toast.makeText(UserProfileEdit.this, error.getMessage(), Toast.LENGTH_LONG).show();
                                    if (mCustomProgressDialog.isShowing()) {
                                        mCustomProgressDialog.dismiss();
                                    }
                                }
                            });
                }
            }
        });


        if (!UserDetails.getCustomerKey().trim().isEmpty()) {

            if (mCustomProgressDialog != null && !mCustomProgressDialog.isShowing()) {
                mCustomProgressDialog.ChageMessage(UserProfileEdit.this, getString(R.string.dialog_please_wait), getString(R.string.dialog_please_wait));
                mCustomProgressDialog.show(UserProfileEdit.this);
            }

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
                        etFirstName.setText(profile.getData().getProfile().getCustomer_name());
                        etLastName.setText(profile.getData().getProfile().getCustomer_last_name());
                        tvEmail.setText(profile.getData().getProfile().getCustomer_email());
                        etMobileNo.setText(profile.getData().getProfile().getCustomer_mobile());
                        etDateOfBirth.setText(profile.getData().getProfile().getCustomer_dob());
                        if (profile.getData().getProfile().getCustomer_gender().equalsIgnoreCase("1")) {
                            male.setChecked(true);
                            female.setChecked(false);
                        } else {
                            female.setChecked(true);
                            male.setChecked(false);
                        }
                    }

                    if (mCustomProgressDialog.isShowing()) {
                        mCustomProgressDialog.dismiss();
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    String ss = "";
                    Toast.makeText(UserProfileEdit.this, error.getMessage(), Toast.LENGTH_LONG).show();

                    if (mCustomProgressDialog.isShowing()) {
                        mCustomProgressDialog.dismiss();
                    }
                }
            });
        } else {
            Toast.makeText(UserProfileEdit.this, getString(R.string.txt_edit_profile), Toast.LENGTH_LONG).show();
        }
        etFirstName.requestFocus();


    }

    private void setDateTimeField() {

        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, R.style.MyThemeOverlay, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
                etDateOfBirth.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        fromDatePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        fromDatePickerDialog.show();

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
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
