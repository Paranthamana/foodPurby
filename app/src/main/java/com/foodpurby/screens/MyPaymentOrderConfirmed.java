package com.foodpurby.screens;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.foodpurby.R;
import com.foodpurby.baseclass.MyActivity;
import com.foodpurby.events.AddRemoveToCartEvent;
import com.foodpurby.ondbstorage.DBActionsCart;
import com.foodpurby.util.Common;
import com.foodpurby.util.CustomProgressDialog;
import com.foodpurby.utillities.AppConfig;
import com.foodpurby.utillities.AppSharedValues;

import de.greenrobot.event.EventBus;

public class MyPaymentOrderConfirmed extends AppCompatActivity {

    private EventBus bus = EventBus.getDefault();
    private CustomProgressDialog mCustomProgressDialog;

    private ListView lvAddress;
    Toolbar mToolbar;
    TextView mToolHeading;

    ImageView imgCart;

    TextView tvInvoiceNumber;
    TextView tvInvoiceAmount;


    Button btnOPTCode;
    EditText etOPTCode;
    TextView tvMobileNo, tvResend;
    TextView messages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_my_payment_order_confirmed);
        mCustomProgressDialog = CustomProgressDialog.getInstance();

        mToolbar = (Toolbar) findViewById(R.id.tb_header);
//        mToolbar.setNavigationIcon(R.drawable.ic_back);
        mToolHeading = (TextView) findViewById(R.id.tool_title);
        mToolHeading.setText(R.string.txt_success);

        messages = (TextView) findViewById(R.id.messages);

        setSupportActionBar(mToolbar);
        imgCart = (ImageView) findViewById(R.id.imgCart);
        imgCart.setVisibility(View.GONE);

        String orderKey = "";
        String orderValue = "";
        String status = "";
        String errormessage = "";

        if (getIntent().getExtras() != null && getIntent().getExtras().getString("orderKey") != null &&
                getIntent().getExtras().get("orderValue") != null) {
            orderKey = getIntent().getExtras().getString("orderKey");
            orderValue = getIntent().getExtras().getString("orderValue");
            status = getIntent().getExtras().getString("status");
            errormessage = getIntent().getExtras().getString("errormessage");
        }


        TextView tvCart = (TextView) mToolbar.findViewById(R.id.tvCart);
        tvCart.setText(Common.getPriceWithCurrencyCode(AppConfig.decimalFormat.format(AppSharedValues.getGrandTotalPrice())));
        tvCart.setTypeface(Typeface.DEFAULT);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                finish();
            }
        });

        btnOPTCode = (Button) findViewById(R.id.btnOPTCode);
        tvInvoiceNumber = (TextView) findViewById(R.id.tvInvoiceNumber);
        tvInvoiceAmount = (TextView) findViewById(R.id.tvInvoiceAmount);
        tvMobileNo = (TextView) findViewById(R.id.tvMobileNo);

        if (status.equals("2")) {
            tvInvoiceNumber.setText("Failure");
            tvInvoiceAmount.setText(errormessage);
            mToolHeading.setText("Failure");
            messages.setVisibility(View.GONE);
            tvMobileNo.setVisibility(View.GONE);

        } else {
            messages.setVisibility(View.VISIBLE);
            tvMobileNo.setVisibility(View.VISIBLE);
            tvInvoiceNumber.setText(getString(R.string.txt_invoice_num) + orderKey);
            tvInvoiceAmount.setText(getString(R.string.txt_totals) + Common.getPriceWithCurrencyCode(orderValue.toString()));
            tvInvoiceAmount.setTypeface(Typeface.DEFAULT);
        }

        btnOPTCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                UserDetails.clearAll();
                MyApplication.getInstance().trackEvent("Button", "Click", "OPTCode");
                MyActivity.DisplayHome(MyPaymentOrderConfirmed.this);
                DBActionsCart.emptyCart(AppSharedValues.getSelectedRestaurantBranchKey());
                finish();
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
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }

    @Override
    public void onBackPressed() {

    }
}