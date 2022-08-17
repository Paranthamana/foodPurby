package com.foodpurby.screens;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.foodpurby.R;
import com.foodpurby.events.AddRemoveToCartEvent;
import com.foodpurby.model.DAOUserPassword;
import com.foodpurby.util.CustomProgressDialog;
import com.foodpurby.utillities.AppSharedValues;
import com.foodpurby.utillities.UserDetails;

import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by android1 on 12/29/2015.
 */
public class UserPassword extends Activity {

    private EventBus bus = EventBus.getDefault();
    private CustomProgressDialog mCustomProgressDialog;

    Toolbar mToolbar;
    TextView mToolHeading;


    EditText etCurrentPassword;
    EditText etNewPassword;
    EditText etConfirmPassword;

    Button btnUpdatePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_user_password);
        mCustomProgressDialog = CustomProgressDialog.getInstance();

        btnUpdatePassword = (Button) findViewById(R.id.btnUpdatePassword);

        etCurrentPassword = (EditText) findViewById(R.id.etCurrentPassword);
        etNewPassword = (EditText) findViewById(R.id.etNewPassword);
        etConfirmPassword = (EditText) findViewById(R.id.etConfirmPassword);

        mToolbar = (Toolbar) findViewById(R.id.tb_header);
        mToolbar.setNavigationIcon(R.drawable.back);
        mToolHeading = (TextView) findViewById(R.id.tool_title);
        mToolHeading.setText(getString(R.string.txt_update_password));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        FrameLayout flCart = (FrameLayout) findViewById(R.id.flCart);
        flCart.setVisibility(View.GONE);

        btnUpdatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mCustomProgressDialog.isShowing()) {
                    mCustomProgressDialog.ChageMessage(UserPassword.this, "Please wait...", "Please wait...");
                    mCustomProgressDialog.show(UserPassword.this);
                }
                MyApplication.getInstance().trackEvent("Button", "Click", "Update Password");
                if (etNewPassword.getText().toString().isEmpty()) {
                    etNewPassword.setError("New password is empty");
                    etNewPassword.requestFocus();
                    if (mCustomProgressDialog.isShowing()) {
                        mCustomProgressDialog.dismiss();
                    }
                } else if (etConfirmPassword.getText().toString().isEmpty()) {
                    etConfirmPassword.setError("Confirm password is empty");
                    etConfirmPassword.requestFocus();
                    if (mCustomProgressDialog.isShowing()) {
                        mCustomProgressDialog.dismiss();
                    }
                } else if (etNewPassword.getText().toString().equals(etConfirmPassword.getText().toString())) {
                    DAOUserPassword.getInstance().Callresponse("",
                            UserDetails.getCustomerKey(),
                            etCurrentPassword.getText().toString().trim(),
                            etNewPassword.getText().toString().trim(),
                            AppSharedValues.getLanguage(),
                            new Callback<DAOUserPassword.UserPassword>() {

                                @Override
                                public void success(DAOUserPassword.UserPassword userPassword, Response response) {

                                    if (userPassword.getHttpcode().equals("200")) {
                                        Toast.makeText(UserPassword.this, userPassword.getMessage(), Toast.LENGTH_LONG).show();
                                        finish();
                                    } else {
                                        Toast.makeText(UserPassword.this, userPassword.getError().getCustomer_password(), Toast.LENGTH_LONG).show();
                                    }
                                    if (mCustomProgressDialog.isShowing()) {
                                        mCustomProgressDialog.dismiss();
                                    }
                                }

                                @Override
                                public void failure(RetrofitError error) {
                                    String ss = "";
                                    Toast.makeText(UserPassword.this, error.getMessage(), Toast.LENGTH_LONG).show();
                                    if (mCustomProgressDialog.isShowing()) {
                                        mCustomProgressDialog.dismiss();
                                    }
                                }
                            });
                } else {
                    Toast.makeText(UserPassword.this, R.string.toast_new_password_Confirm_password, Toast.LENGTH_SHORT).show();
                    if (mCustomProgressDialog.isShowing()) {
                        mCustomProgressDialog.dismiss();
                    }
                }


            }
        });

        notifyChanges();
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
