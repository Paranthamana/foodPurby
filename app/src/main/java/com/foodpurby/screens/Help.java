package com.foodpurby.screens;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.sloop.fonts.FontsManager;
import com.foodpurby.adapters.HelpAdapter;
import com.foodpurby.events.DummyEvent;
import com.foodpurby.util.Common;
import com.foodpurby.util.CustomProgressDialog;
import com.foodpurby.utillities.AppConfig;
import com.foodpurby.BuildConfig;
import com.foodpurby.R;
import com.foodpurby.utillities.AppSharedValues;

import java.util.Calendar;

import de.greenrobot.event.EventBus;

/**
 * Created by android1 on 12/29/2015.
 */
public class Help extends Activity {

    private EventBus bus = EventBus.getDefault();
    private CustomProgressDialog mCustomProgressDialog;
    HelpAdapter helpAdapter;
    Toolbar mToolbar;
    TextView mToolHeading;

    ListView lvHelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

      //  FontsManager.initFormAssets(this, "Lato-Light.ttf");
      //  FontsManager.changeFonts(this);

        EventBus.getDefault().register(this);
        mCustomProgressDialog = CustomProgressDialog.getInstance();
        mToolbar = (Toolbar) findViewById(R.id.tb_header);
        lvHelp = (ListView) findViewById(R.id.lvHelp);

        mToolbar.setNavigationIcon(R.drawable.back);
        mToolHeading = (TextView) findViewById(R.id.tool_title);
        mToolHeading.setText(R.string.txt_help);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (AppSharedValues.getCMSList() != null) {
            helpAdapter = new HelpAdapter(Help.this, bus);
            lvHelp.setAdapter(helpAdapter);
        }

        Calendar cal = Calendar.getInstance();
        String poweredBy = getString(R.string.about_powered_by, AppConfig.AboutUrl, AppConfig.AboutCompanyName);
        String copyright = getString(R.string.about_copyright, cal.get(Calendar.YEAR), AppConfig.CopyrightCompanyName);
        String poweredByLink = getString(R.string.about_powered_by_link, AppConfig.AboutUrl);
        String version = getString(R.string.about_version, BuildConfig.VERSION_NAME);

        ((TextView) findViewById(R.id.tvPoweredBy)).setText(poweredBy);
        ((TextView) findViewById(R.id.tvPoweredByLink)).setText(poweredByLink);
        ((TextView) findViewById(R.id.tvCopyright)).setText(copyright);
        ((TextView) findViewById(R.id.tvVersion)).setText(version);

        Common.setListViewHeightBasedOnChildren(lvHelp);
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
        mCustomProgressDialog.dismiss();
    }
}
