package com.foodpurby.screens;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.foodpurby.R;
import com.foodpurby.adapters.MenuPagerAdapter;
import com.foodpurby.ondbstorage.DBActionsRestaurantBranch;
import com.foodpurby.ondbstorage.RestaurantBranch;
import com.foodpurby.utillities.AppSharedValues;
import com.sloop.fonts.FontsManager;

import java.util.List;

import de.greenrobot.event.EventBus;

public class RestaurantMenuActivity extends AppCompatActivity
        implements MenuFragment.OnFragmentInteractionListener, RatingsFragment.OnFragmentInteractionListener,
        InfoFragment.OnFragmentInteractionListener {

    private int mGroupPosition;
    private int[] ICONS = {R.drawable.footer_profile,
            R.drawable.footer_profile, R.drawable.footer_map,
            R.drawable.footer_timeline};

    Toolbar mToolbar;
    TextView mToolHeading;
    ViewPager viewPager;
    private EventBus bus = EventBus.getDefault();
    List<RestaurantBranch> restaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurent_menu);

     //   FontsManager.initFormAssets(this, "Lato-Light.ttf");
     //   FontsManager.changeFonts(this);
        restaurant = DBActionsRestaurantBranch.get();
        Intent in = getIntent();
        if (in != null) {
            mGroupPosition = in.getIntExtra("Position", 0);
        }

        mToolbar = (Toolbar) findViewById(R.id.tb_header);
        mToolHeading = (TextView) findViewById(R.id.tool_title);
        mToolbar.setNavigationIcon(R.drawable.back);
        mToolHeading.setText(AppSharedValues.getSelectedRestaurantBranchName());
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        viewPager = (ViewPager) findViewById(R.id.menu_pager);
        MenuPagerAdapter customPagerAdapter = new MenuPagerAdapter(getSupportFragmentManager(), ICONS, mGroupPosition);
        viewPager.setAdapter(customPagerAdapter);
        PagerSlidingTabStrip tabsStrip = (PagerSlidingTabStrip) findViewById(R.id.menu_tabs);
        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());
        viewPager.setPageMargin(pageMargin);
        tabsStrip.setViewPager(viewPager);
    }

    @Override
    public void onResume() {
        super.onResume();
        //bus.post(new FilterIconVisibilityEvent(View.GONE, FilterIconVisibilityEvent.PageTitleType.Menu));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onFragmentInteraction(int position) {

    }
}
