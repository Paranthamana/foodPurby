package com.foodpurby;

import android.annotation.TargetApi;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sloop.fonts.FontsManager;
import com.foodpurby.baseclass.MyActivity;
import com.foodpurby.events.AddressEvent;
import com.foodpurby.events.FilterIconVisibilityEvent;
import com.foodpurby.events.RestaurantNamesEvent;
import com.foodpurby.events.RestaurantsRefreshEvent;
import com.foodpurby.events.RestaurantsSearchEvent;
import com.foodpurby.events.UpdateTitleEvent;
import com.foodpurby.map.RestaurantMapFragment;
import com.foodpurby.model.DAOSignout;
import com.foodpurby.screens.CityAreaFragment;
import com.foodpurby.screens.InviteAndEarn;
import com.foodpurby.screens.MoreActivity;
import com.foodpurby.screens.NavigationDrawerFragment;
import com.foodpurby.screens.Restaurants;
import com.foodpurby.screens.UserSignin;
import com.foodpurby.util.Common;
import com.foodpurby.util.CustomProgressDialog;
import com.foodpurby.utillities.AppSharedValues;
import com.foodpurby.utillities.LanguageCode;
import com.foodpurby.utillities.UserDetails;

import java.util.HashMap;
import java.util.Map;

import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class HomeActivity extends AppCompatActivity implements RestaurantMapFragment.OnFragmentInteractionListener, Restaurants.OnFragmentInteractionListener, NavigationDrawerFragment.NavigationDrawerCallbacks, UserSignin.OnFragmentInteractionListener {

    private CustomProgressDialog mCustomProgressDialog;
    private EventBus bus = EventBus.getDefault();
    private Screens SelectedScreen = Screens.RESTAURANT_HOME;
    SearchView searchView = null;

    boolean doubleBackToExitPressedOnce = false;


    enum Screens {
        RESTAURANT_HOME(0),
        InviteAndEarn(8),
        ORDER(2),
        PROFILE(8),
        SIGNIN(6),
        SETTINGS(7),
        HELP(4),
        CONTACTUS(5),
        OFFERS(3),
        MORE(1);

        //FOOD(1),
        //GROCERIES(2);

        private int _value;

        Screens(int Value) {
            this._value = Value;
        }

        public int getValue() {
            return _value;
        }

        public static Screens fromInt(int i) {
            for (Screens b : Screens.values()) {
                if (b.getValue() == i) {
                    return b;
                }
            }
            return null;
        }
    }

    ImageView imgCart;
    Toolbar mToolbar;
    TextView tvCart;
    TextView mToolbarTitle;
    private DrawerLayout mDrawerLayout;
    private CharSequence mTitle;
    private NavigationDrawerFragment mNavigationDrawerFragment;
    ActionBarDrawerToggle drawerToggle;
    FrameLayout mShowCart;
    Animation shake;
    Vibrator vibrator;
    public static FrameLayout flFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


       // FontsManager.initFormAssets(this, "Lato-Light.ttf");
      //  FontsManager.changeFonts(this);

        mCustomProgressDialog = CustomProgressDialog.getInstance();
        EventBus.getDefault().register(this);
        shake = AnimationUtils.loadAnimation(this, R.anim.shakeanim);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        AppSharedValues.setClientKey("7dbd7874-d3c6-46ad-9f25-e79bef761613");
        AppSharedValues.setRestaurantCurrentTable("TABLE 001");
        AppSharedValues.setSelectedRestaurantBranchKey("1b7ff608-ef32-4c2a-bff1-4823dd404cdf");
        AppSharedValues.setSelectedRestaurantBranchCurrencyCode(HomeActivity.this.getResources().getString(R.string.Rs));
        AppSharedValues.setShowCurrencyCodeInRight(false);
        AppSharedValues.setShowPriceWithSpace(true);
        AppSharedValues.setRTLEnabled(false);
        AppSharedValues.setDeliveryCharge(10D);
        AppSharedValues.setGrandTotalPrice(0D);
        AppSharedValues.setCartDeliveryMethod(AppSharedValues.DeliveryMethod.Delivery);
        AppSharedValues.setDeliveryType_Collect(true);
        AppSharedValues.setDeliveryType_Delivery(true);
        //        AppSharedValues.setLanguage("en");
        AppSharedValues.setDistanceMetrics("Km");
        AppSharedValues.setDeliveryAvailability_COD(true);
        AppSharedValues.setDeliveryAvailability_ONLINE(true);
        AppSharedValues.setOnlinePaymentGatewayName("PayUMoney");
        Boolean status = null;
        if (AppSharedValues.getArea() != null && AppSharedValues.getCity() != null) {
            status = Common.GetRestaurants(HomeActivity.this, mCustomProgressDialog, bus);
        }

        String languageCode = getSelectedLanguageFromSharedPreference();
        AppSharedValues.setLanguage(languageCode);

        mToolbar = (Toolbar) findViewById(R.id.tb_header);
        mToolbarTitle = (TextView) mToolbar.findViewById(R.id.tool_title);
        mToolbar.setNavigationIcon(R.drawable.ic_menu);
        setSupportActionBar(mToolbar);


        imgCart = (ImageView) findViewById(R.id.imgCart);
        tvCart = (TextView) findViewById(R.id.tvCart);
        mShowCart = (FrameLayout) findViewById(R.id.flCart);
        mShowCart.setVisibility(View.GONE);
        mShowCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Common.IsShopOpened(HomeActivity.this, getLayoutInflater())) {
                    MyActivity.DisplayCart(HomeActivity.this);
                    overridePendingTransition(R.anim.anim_left_to_right, R.anim.anim_right_to_left);
                }
            }
        });
        flFilter = (FrameLayout) findViewById(R.id.flFilter);
        flFilter.setVisibility(View.INVISIBLE);
        /*flFilter.setVisibility(View.GONE);
        if(mToolbarTitle.getText().toString().equalsIgnoreCase("home")) {
            flFilter.setVisibility(View.VISIBLE);
        }*/
        flFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyActivity.DisplayRestaurantFilter(HomeActivity.this);
            }
        });
        mToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        //flCart.setVisibility(View.GONE);
        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = setupDrawerToggle();

        // Tie DrawerLayout events to the ActionBarToggle
        mDrawerLayout.setDrawerListener(drawerToggle);

        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, mDrawerLayout);
        handleIntent(getIntent());
/*
        if (!status) {
            MyActivity.DisplayCityArea(HomeActivity.this);
        }
*/

    }


    @Override
    public void onFragmentInteraction(int position) {

    }

    protected void setFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction mFragmentTransaction = fragmentManager.beginTransaction();
        Screens t = Screens.fromInt(position);
        SelectedScreen = t;
        switch (t) {
            case RESTAURANT_HOME:
                Map<String, String> clearFilter = new HashMap<String, String>();
                AppSharedValues.setFilterSelectedCuisines(clearFilter);
                //                mToolbarTitle.setText("Home");
                mFragmentTransaction.replace(R.id.container, new CityAreaFragment()).commit();
                break;


            case MORE:
                Intent mIntent = new Intent(HomeActivity.this, MoreActivity.class);
                startActivity(mIntent);
                break;

            case CONTACTUS:
//                Intent intent = new Intent(HomeActivity.this, MainMap.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);

                mToolbarTitle.setText("Contact Us");
                searchView.setVisibility(View.GONE);

//                mFragmentTransaction.replace(R.id.container, new ContactUs()).addToBackStack("contactus").commit();


                getSupportFragmentManager().beginTransaction().replace(R.id.container, new ContactUSFragment()).commit();


//                mFragmentTransaction
//                        .replace(R.id.container, new ContactUs())
//                        .commit();
                break;
            case OFFERS:
                if (!UserDetails.getCustomerKey().isEmpty()) {
                    MyActivity.DisplayTabaocoCredit(getApplicationContext());
                } else {
                    MyActivity.DisplayUserSignInActivity(getApplicationContext());
                }
                break;

            case InviteAndEarn:
                mFragmentTransaction.replace(R.id.container, new InviteAndEarn()).addToBackStack("earn").commit();
                break;

            case PROFILE:
                if (!UserDetails.getCustomerKey().isEmpty()) {
                    MyActivity.DisplayProfile(getApplicationContext());
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString("goto", "profile");

                    UserSignin userSignin = new UserSignin();
                    userSignin.setArguments(bundle);

                    fragmentManager.beginTransaction().replace(R.id.container, userSignin).addToBackStack("profile").commit();
                }
                break;
            case SIGNIN:
                if (!UserDetails.getCustomerKey().isEmpty()) {
                    userSignOut();
                    UserDetails.clearAll();
                    MyActivity.DisplayHomePage(HomeActivity.this);
                    finish();
                } else {
                    fragmentManager.beginTransaction().replace(R.id.container, new UserSignin()).addToBackStack("signin").commit();
                }
                break;
            case SETTINGS:

                //if (!UserDetails.getCustomerKey().isEmpty()) {
                MyActivity.DisplaySettings(getApplicationContext());


                break;
            case ORDER:
                if (!UserDetails.getCustomerKey().isEmpty()) {
                    MyActivity.DisplayOrder(getApplicationContext());
                } else {
                    MyActivity.DisplayUserSignInActivity(getApplicationContext());
                }
                break;
            case HELP:
                if (tvCart != null) {
                    mShowCart.setVisibility(View.GONE);
                }
                MyActivity.DisplayHelp(getApplicationContext());
                break;
        }
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, mDrawerLayout, ((Toolbar) findViewById(R.id.tb_header)),
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        if (id == android.R.id.home) {
            if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                mDrawerLayout.closeDrawers();
            } else {
                mDrawerLayout.openDrawer(Gravity.LEFT);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { // Inflate the menu;
        this.getMenuInflater().inflate(R.menu.main, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        if (searchItem != null) {
            searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
            MenuItemCompat.collapseActionView(searchItem);
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        }

        if (searchView != null) {
            // Detect SearchView icon clicks
            searchView.setOnSearchClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mToolbarTitle.setVisibility(View.GONE);
                    bus.post(new FilterIconVisibilityEvent(View.GONE, FilterIconVisibilityEvent.PageTitleType.None));
                    bus.post(new RestaurantsSearchEvent());
                }
            });

            // Detect SearchView close
            searchView.setOnCloseListener(new SearchView.OnCloseListener() {
                @Override
                public boolean onClose() {
                    AppSharedValues.setRestaurantSearchKey("");
                    bus.post(new FilterIconVisibilityEvent(View.VISIBLE, FilterIconVisibilityEvent.PageTitleType.Home));
                    bus.post(new RestaurantsSearchEvent());

                    mToolbarTitle.setVisibility(View.VISIBLE);
                    return false;
                }
            });

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    if (!query.equals("")) {

                    }
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    //bus.post(new SearchContact(newText));
                    AppSharedValues.setRestaurantSearchKey(newText);
                    bus.post(new RestaurantsSearchEvent());
                    return true;
                }
            });
        }

        super.onPrepareOptionsMenu(menu);

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        try {
            MenuItem searchViewMenuItem = menu.findItem(R.id.action_search);
            searchView = (SearchView) MenuItemCompat.getActionView(searchViewMenuItem);
            int searchImgId = R.id.search_button; // I used the explicit layout ID of searchview's ImageView
            ImageView v = (ImageView) searchView.findViewById(searchImgId);
            v.setImageResource(R.drawable.ic_search);
            int searchImgIdClose = R.id.search_close_btn; // I used the explicit layout ID of searchview's ImageView
            ImageView vClose = (ImageView) searchView.findViewById(searchImgIdClose);
            vClose.setImageResource(R.drawable.ic_close);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //        super.onPrepareOptionsMenu(menu);
        return true;
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void setSlide(float moveFactor) {
        findViewById(R.id.frame_container).setTranslationX(moveFactor);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

        if (mCustomProgressDialog != null) {
            mCustomProgressDialog.dismiss();
        }
    }


    public void onEvent(final AddressEvent ev) {
        runOnUiThread(new Runnable() {
            public void run() {
                Common.GetAddress(HomeActivity.this, mCustomProgressDialog, bus);
            }
        });
    }

    public void onEvent(final RestaurantNamesEvent ev) {
        runOnUiThread(new Runnable() {
            public void run() {
                mToolbarTitle.setText(AppSharedValues.getSelectedRestaurantBranchName());
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

    public void onEvent(final RestaurantsRefreshEvent ev) {
        runOnUiThread(new Runnable() {
            public void run() {
                Common.GetRestaurants(HomeActivity.this, mCustomProgressDialog, bus);
            }
        });
    }

    public void onEvent(final FilterIconVisibilityEvent ev) {
        runOnUiThread(new Runnable() {
            public void run() {
                if (ev.getPageTitleType() == FilterIconVisibilityEvent.PageTitleType.Home) {
                    mToolbarTitle.setVisibility(View.VISIBLE);
                    //   flFilter.setVisibility(View.VISIBLE);

                    /*if (AppSharedValues.getCategory().equalsIgnoreCase("1")) {
                        mToolbarTitle.setText(getString(R.string.txt_restaurants));

                    } else if (AppSharedValues.getCategory().equalsIgnoreCase("4")) {
                        mToolbarTitle.setText(getString(R.string.txt_supermarket));
                    } else {*/
                    mToolbarTitle.setText(getString(R.string.txt_home));
                  /*  }*/

                    mShowCart.setVisibility(View.GONE);
                    //flFilter.setVisibility(View.VISIBLE);
                    if (searchView != null) {
                        searchView.setVisibility(View.VISIBLE);
                    }
                    invalidateOptionsMenu();

                    AppSharedValues.setRestaurantSearchKey("");
                    //bus.post(new RestaurantsSearchEvent());

                } else if (ev.getPageTitleType() == FilterIconVisibilityEvent.PageTitleType.MenuItems) {
                    mToolbarTitle.setText(AppSharedValues.getSelectedRestaurantBranchName());
                    mToolbarTitle.setVisibility(View.VISIBLE);
                    mShowCart.setVisibility(View.GONE);
                    flFilter.setVisibility(View.GONE);
                    if (searchView != null) {
                        searchView.setVisibility(View.GONE);
                    }
                } else if (ev.getPageTitleType() == FilterIconVisibilityEvent.PageTitleType.category) {
                    mToolbarTitle.setText("Home");
                    mToolbarTitle.setVisibility(View.VISIBLE);
                    mShowCart.setVisibility(View.GONE);
                    flFilter.setVisibility(View.GONE);
                    //searchView.setVisibility(View.GONE);

                } else if (ev.getPageTitleType() == FilterIconVisibilityEvent.PageTitleType.Menu) {
                    mToolbarTitle.setText(AppSharedValues.getSelectedRestaurantBranchName());
                    mToolbarTitle.setVisibility(View.VISIBLE);
                    mShowCart.setVisibility(View.GONE);
                    flFilter.setVisibility(View.GONE);
                    if (searchView != null) {
                        searchView.setVisibility(View.GONE);
                    }
                } else if (ev.getPageTitleType() == FilterIconVisibilityEvent.PageTitleType.Signin) {
                    mToolbarTitle.setText(getString(R.string.txt_sign_in));
                    mShowCart.setVisibility(View.GONE);
                    flFilter.setVisibility(View.GONE);
                    if (searchView != null) {
                        searchView.setVisibility(View.GONE);
                    }
                } else if (ev.getPageTitleType() == FilterIconVisibilityEvent.PageTitleType.InviteAndEarn) {
                    mToolbarTitle.setText(getString(R.string.txt_invite_and_earn));
                    mShowCart.setVisibility(View.GONE);
                    flFilter.setVisibility(View.GONE);
                    if (searchView != null) {
                        searchView.setVisibility(View.GONE);
                    }
                } else if (ev.getPageTitleType() == FilterIconVisibilityEvent.PageTitleType.None) {
                    mShowCart.setVisibility(View.GONE);
                    flFilter.setVisibility(View.GONE);
                }


            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        // .... other stuff in my onResume ....
        this.doubleBackToExitPressedOnce = false;
        mToolbarTitle.setText(getString(R.string.txt_home));
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        FragmentManager manager = getSupportFragmentManager();
        int count = manager.getBackStackEntryCount();
        // Toast.makeText(this, "count:"+count, Toast.LENGTH_SHORT).show();

        if (count == 1) {

            super.onBackPressed();
        }

        if (count == 0) {

            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Press click BACK again to exit", Toast.LENGTH_SHORT).show();
        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);
        fragment.onActivityResult(requestCode, resultCode, data);
    }


    private void userSignOut() {
        if (!mCustomProgressDialog.isShowing()) {
            mCustomProgressDialog.ChageMessage(HomeActivity.this, getString(R.string.dialog_please_wait), getString(R.string.dialog_please_wait));
            mCustomProgressDialog.show(HomeActivity.this);
        }
        DAOSignout.getInstance().Callresponse("", UserDetails.getCustomerEmail(), new Callback<DAOSignout.Signout>() {

            @Override
            public void success(DAOSignout.Signout signout, Response response) {
                if (mCustomProgressDialog.isShowing()) {
                    mCustomProgressDialog.dismiss();
                }

                if (signout.getHttpcode().equals("200")) {
                    UserDetails.clearAll();
                    //DBActionsUser.delete();
                    //DBActionsCart.deleteAll();

                } else {
                    Toast.makeText(HomeActivity.this, signout.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void failure(RetrofitError error) {
                if (mCustomProgressDialog.isShowing()) {
                    mCustomProgressDialog.dismiss();
                }
                Toast.makeText(HomeActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public String getSelectedLanguageFromSharedPreference() {
        SharedPreferences prefs = getSharedPreferences(LanguageCode.MyPREFERENCES, MODE_PRIVATE);
        String selectedLanguageFromSharedPreference = prefs.getString(LanguageCode.LANGUAGE_CODE, null);
        if (selectedLanguageFromSharedPreference == null) {
            selectedLanguageFromSharedPreference = LanguageCode.ENGLISH;
        }
        return selectedLanguageFromSharedPreference;
    }

}

