package com.foodpurby.screens;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.sloop.fonts.FontsManager;
import com.foodpurby.adapters.CustomPagerAdapter;
import com.foodpurby.animation.CircleAnimationUtil;
import com.foodpurby.events.UpdateTitleEvent;
import com.foodpurby.ondbstorage.DBActionsCart;
import com.foodpurby.ondbstorage.DBActionsRestaurantBranch;
import com.foodpurby.ondbstorage.RestaurantBranch;
import com.foodpurby.R;
import com.foodpurby.baseclass.MyActivity;
import com.foodpurby.events.AddRemoveToCartEvent;
import com.foodpurby.events.FilterIconVisibilityEvent;
import com.foodpurby.events.RestaurantNamesEvent;
import com.foodpurby.ondbstorage.CartProducts;
import com.foodpurby.util.Common;
import com.foodpurby.utillities.AppSharedValues;

import java.util.List;

import de.greenrobot.event.EventBus;

public class RestaurantItemActivity extends AppCompatActivity {

    private int[] ICONS = {R.drawable.footer_profile,
            R.drawable.footer_profile, R.drawable.footer_map,
            R.drawable.footer_timeline};
    Toolbar mToolbar;
    TextView mToolHeading;
    ViewPager viewPager;
    private EventBus bus = EventBus.getDefault();
    int position;
    List<RestaurantBranch> restaurant;
    ImageView imgCart;
    TextView tvCart;
    FrameLayout mShowCart;
    Animation shake;
    Vibrator vibrator;
    private int mPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_item);
        EventBus.getDefault().register(this);
      //  FontsManager.initFormAssets(this, "Lato-Light.ttf");
      //  FontsManager.changeFonts(this);
        restaurant = DBActionsRestaurantBranch.get();

        Intent in = getIntent();
        if (in != null) {
            position = in.getIntExtra("Position", 0);
            mPosition= in.getIntExtra("Position11", 0);
        }

        shake = AnimationUtils.loadAnimation(this, R.anim.shakeanim);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        viewPager = (ViewPager) findViewById(R.id.pager);

        viewPager.setCurrentItem(position);
        CustomPagerAdapter customPagerAdapter = new CustomPagerAdapter(getSupportFragmentManager(), ICONS, "Grp1");
        viewPager.setAdapter(customPagerAdapter);
        PagerSlidingTabStrip tabsStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());
        viewPager.setPageMargin(pageMargin);
        tabsStrip.setViewPager(viewPager);

        mToolbar = (Toolbar) findViewById(R.id.tb_header);
        mToolHeading = (TextView) findViewById(R.id.tool_title);
        mToolbar.setNavigationIcon(R.drawable.back);
        mToolHeading.setText(restaurant.get(position).getRestaurantBranchName());
        setSupportActionBar(mToolbar);

        imgCart = (ImageView) findViewById(R.id.imgCart);
        tvCart = (TextView) findViewById(R.id.tvCart);
        mShowCart = (FrameLayout) findViewById(R.id.flCart);
        mShowCart.setVisibility(View.VISIBLE);
        mShowCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Common.IsShopOpened(RestaurantItemActivity.this, getLayoutInflater())) {
                    MyActivity.DisplayCart(RestaurantItemActivity.this);
                    overridePendingTransition(R.anim.anim_left_to_right, R.anim.anim_right_to_left);
                }
            }
        });

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        bus.post(new AddRemoveToCartEvent(true, null));
        //bus.post(new FilterIconVisibilityEvent(View.GONE, FilterIconVisibilityEvent.PageTitleType.MenuItems));
    }

    @Override
    public void onResume() {
        super.onResume();
        viewPager.postDelayed(new Runnable() {

            @Override
            public void run() {
                viewPager.setCurrentItem(mPosition);
            }
        }, 100);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void onEvent(final AddRemoveToCartEvent ev) {
        runOnUiThread(new Runnable() {
            public void run() {
                mShowCart.setVisibility(View.VISIBLE);
                //tvCart.setText(String.valueOf(DBActionsCart.getTotalItems(AppSharedValues.getSelectedRestaurantBranchKey())));
                List<CartProducts> products = DBActionsCart.getItems(AppSharedValues.getSelectedRestaurantBranchKey());
                tvCart.setText(String.valueOf(products.size()));

                if (ev.getVibrate()) {
                    imgCart.startAnimation(shake);

                    if (AppSharedValues.isVibrateWhileAddingToCart()) {
                        vibrator.vibrate(200);
                    }
                }

                if (ev.getView() != null) {
                    new CircleAnimationUtil().attachActivity(RestaurantItemActivity.this).
                            setTargetView(ev.getView()).setDestView(imgCart).startAnimation();
                }
            }
        });
    }

    public void onEvent(final RestaurantNamesEvent ev) {
        runOnUiThread(new Runnable() {
            public void run() {
                mToolHeading.setText(AppSharedValues.getSelectedRestaurantBranchName());
            }
        });
    }

    public void onEvent(final UpdateTitleEvent ev) {
        runOnUiThread(new Runnable() {
            public void run() {
                //mToolbarTitle.setText(AppSharedValues.getSelectedRestaurantBranchName());
            }
        });
    }

    public void onEvent(final FilterIconVisibilityEvent ev) {
        runOnUiThread(new Runnable() {
            public void run() {

                if (ev.getPageTitleType() == FilterIconVisibilityEvent.PageTitleType.MenuItems) {
                    mToolHeading.setText(AppSharedValues.getSelectedRestaurantBranchName());
                    mToolHeading.setVisibility(View.VISIBLE);
                    mShowCart.setVisibility(View.VISIBLE);

                } else if (ev.getPageTitleType() == FilterIconVisibilityEvent.PageTitleType.Menu) {
                    mToolHeading.setText(AppSharedValues.getSelectedRestaurantBranchName());
                    mToolHeading.setVisibility(View.VISIBLE);
                    mShowCart.setVisibility(View.GONE);

                } else if (ev.getPageTitleType() == FilterIconVisibilityEvent.PageTitleType.Signin) {
                    mToolHeading.setText(getString(R.string.txt_sign_in));
                    mShowCart.setVisibility(View.GONE);

                } else if (ev.getPageTitleType() == FilterIconVisibilityEvent.PageTitleType.InviteAndEarn) {
                    mToolHeading.setText(getString(R.string.txt_invite_and_earn));
                    mShowCart.setVisibility(View.GONE);

                } else if (ev.getPageTitleType() == FilterIconVisibilityEvent.PageTitleType.None) {
                    mShowCart.setVisibility(View.GONE);
                }

            }
        });



    }




}
