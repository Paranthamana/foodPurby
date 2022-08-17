package com.foodpurby.screens;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.foodpurby.events.DummyEvent;
import com.foodpurby.util.CustomProgressDialog;
import com.foodpurby.R;

import de.greenrobot.event.EventBus;

/**
 * Created by android1 on 12/29/2015.
 */
public class HelpBrowser extends Activity {

    private EventBus bus = EventBus.getDefault();
    private CustomProgressDialog mProgressDialog;
    Toolbar mToolbar;
    TextView mToolHeading;
    WebView wvHelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_browser);

        EventBus.getDefault().register(this);
        mProgressDialog = CustomProgressDialog.getInstance();
        mToolbar = (Toolbar) findViewById(R.id.tb_header);

        mToolbar.setNavigationIcon(R.drawable.back);
        mToolHeading = (TextView) findViewById(R.id.tool_title);
        mToolHeading.setText(R.string.txt_help);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        wvHelp = (WebView) findViewById(R.id.wvHelp);
        wvHelp.getSettings().setJavaScriptEnabled(true);
        wvHelp.setWebViewClient(new MyWebViewClient());

        if (getIntent().getExtras() != null && getIntent().getExtras().getString("url") != null) {
            wvHelp.loadUrl(getIntent().getExtras().getString("url"));
        }
        if (getIntent().getExtras() != null && getIntent().getExtras().getString("pagetitle") != null) {
            mToolHeading.setText(getIntent().getExtras().getString("pagetitle"));
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

    public void onEvent(final DummyEvent ev) {
        runOnUiThread(new Runnable() {
            public void run() {

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
        mProgressDialog.dismiss();
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            mProgressDialog.dismiss();
            super.onPageFinished(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            mProgressDialog.show(HelpBrowser.this);
            super.onPageStarted(view, url, favicon);
        }
    }
}
