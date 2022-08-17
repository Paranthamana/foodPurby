package com.foodpurby.screens;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sloop.fonts.FontsManager;
import com.foodpurby.util.CustomProgressDialog;
import com.foodpurby.adapters.AddressAdapter;
import com.foodpurby.events.AddRemoveToCartEvent;
import com.foodpurby.model.DAOSigninForgotPassword;
import com.foodpurby.util.Common;
import com.foodpurby.R;

import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by android1 on 12/29/2015.
 */
public class UserSigninForgotPassword extends Activity {

    private EventBus bus = EventBus.getDefault();
    private CustomProgressDialog mCustomProgressDialog;

    private ListView lvAddress;
    private AddressAdapter mpAdapter;
    Toolbar mToolbar;
    TextView mToolHeading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

      //  FontsManager.initFormAssets(this, "Lato-Light.ttf");
      //  FontsManager.changeFonts(this);

        EventBus.getDefault().register(this);
        mCustomProgressDialog = CustomProgressDialog.getInstance();
        mToolbar = (Toolbar) findViewById(R.id.tb_header);
        mToolHeading = (TextView) findViewById(R.id.tool_title);
        mToolbar.setNavigationIcon(R.drawable.back);
        mToolHeading.setText(R.string.txt_forgotten_password);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button btnForgot = (Button) findViewById(R.id.btnForgot);
        final EditText etEmailId = (EditText) findViewById(R.id.etEmailId);

        TextView tvCancel = (TextView) findViewById(R.id.tvCancel);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!mCustomProgressDialog.isShowing()) {
                    mCustomProgressDialog.ChageMessage(UserSigninForgotPassword.this, getString(R.string.dialog_please_wait), getString(R.string.dialog_please_wait));
                    mCustomProgressDialog.show(UserSigninForgotPassword.this);
                }
                MyApplication.getInstance().trackEvent("Button", "Click", "Forgot");
                DAOSigninForgotPassword.getInstance().Callresponse("",
                        etEmailId.getText().toString(),
                        new Callback<DAOSigninForgotPassword.ForgotPassword>() {

                            @Override
                            public void success(DAOSigninForgotPassword.ForgotPassword forgotPassword, Response response) {

                                if (forgotPassword.getHttpcode().equals("200") || forgotPassword.getHttpcode().equals(200)) {
                                    Common.GetAddress(UserSigninForgotPassword.this, mCustomProgressDialog, bus);
                                    Toast.makeText(UserSigninForgotPassword.this, forgotPassword.getMessage(), Toast.LENGTH_LONG).show();
                                    finish();
                                }
                                else {
                                    if(forgotPassword.getError().getCustomer_email() != null) {
                                        Toast.makeText(UserSigninForgotPassword.this, forgotPassword.getError().getCustomer_email(), Toast.LENGTH_LONG).show();
                                    }
                                    else {
                                        Toast.makeText(UserSigninForgotPassword.this, forgotPassword.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }

                                if (mCustomProgressDialog.isShowing()) {
                                    mCustomProgressDialog.dismiss();
                                }

                            }

                            @Override
                            public void failure(RetrofitError error) {
                                String ss = "";
                                Toast.makeText(UserSigninForgotPassword.this, error.getMessage(), Toast.LENGTH_LONG).show();
                                if (mCustomProgressDialog.isShowing()) {
                                    mCustomProgressDialog.dismiss();
                                }

                            }
                        });
            }
        });
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

    private  void notifyChanges() {
        //btnAddress.setText("Pay order total " + Common.getPriceWithCurrencyCode(DBActionsCart.getTotalCartPriceIncludingTax(AppSharedValues.getSelectedRestaurantBranchKey()).toString()));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        mCustomProgressDialog.dismiss();
    }
}
