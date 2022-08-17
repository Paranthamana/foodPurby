package com.foodpurby.screens;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import com.sloop.fonts.FontsManager;
import com.foodpurby.R;
import com.foodpurby.adapters.ViewPagerAdapter;
import com.foodpurby.baseclass.MyActivity;
import com.foodpurby.utillities.AppSharedValues;
import com.foodpurby.utillities.UserDetails;
import com.viewpagerindicator.CirclePageIndicator;

public class GetStartActivity extends Activity {
    ViewPager viewPager;
    PagerAdapter adapter;
//    CirclePageIndicator mIndicator;
    int[] flag = new int[]{R.drawable.splash};
    Button vLogin, vGetStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_start);

     //   FontsManager.initFormAssets(this, "Lato-Light.ttf");
      //  FontsManager.changeFonts(this);

        viewPager = (ViewPager) findViewById(R.id.AG_view_pager_banner);
//        mIndicator = (CirclePageIndicator) findViewById(R.id.AG_indicator);
        vLogin = (Button) findViewById(R.id.AG_btnSignin);
        vGetStarted = (Button) findViewById(R.id.AG_btnGetStart);

        // Pass results to ViewPagerAdapter Class
        adapter = new ViewPagerAdapter(GetStartActivity.this, flag);
        // Binds the Adapter to the ViewPager
        viewPager.setAdapter(adapter);
        // ViewPager Indicator
//        mIndicator.setViewPager(viewPager);


        if (UserDetails.getCustomerKey().isEmpty()) {
            vLogin.setVisibility(View.VISIBLE);
        } else {
            vLogin.setVisibility(View.GONE);
        }


        vLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppSharedValues.LoginStatus = "1";
                MyActivity.DisplayUserSignInActivity(GetStartActivity.this);
                GetStartActivity.this.finish();
            }
        });

        vGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyActivity.DisplayHome(GetStartActivity.this);
                GetStartActivity.this.finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (UserDetails.getCustomerKey().isEmpty()) {
            vLogin.setVisibility(View.VISIBLE);
        } else {
            vLogin.setVisibility(View.GONE);
        }
    }
}
