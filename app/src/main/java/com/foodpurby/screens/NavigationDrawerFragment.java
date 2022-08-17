package com.foodpurby.screens;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.SwitchCompat;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.sloop.fonts.FontsManager;
import com.squareup.picasso.Picasso;
import com.foodpurby.adapters.DrawerMenuAdapter;
import com.foodpurby.events.MainMenuRefreshEvent;
import com.foodpurby.events.SigninEvent;
import com.foodpurby.utillities.UserDetails;
import com.foodpurby.HomeActivity;
import com.foodpurby.R;
import com.foodpurby.model.DrawerMenuItems;
import com.foodpurby.utillities.LanguageCode;

import java.util.ArrayList;
import java.util.Locale;

import de.greenrobot.event.EventBus;


/**
 * Fragment used for managing interactions for and presentation of a navigation drawer.
 * See the <a href="https://developer.android.com/design/patterns/navigation-drawer.html#Interaction">
 * design guidelines</a> for a complete explanation of the behaviors implemented here.
 */
public class NavigationDrawerFragment extends Fragment {

    private EventBus bus = EventBus.getDefault();
    /**
     * Remember the position of the selected item.
     */
    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";

    /**
     * Per the design guidelines, you should show the drawer on launch until the user manually
     * expands it. This shared preference tracks this.
     */
    private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";

    /**
     * A pointer to the current callbacks instance (the Activity).
     */
    private NavigationDrawerCallbacks mCallbacks;

    /**
     * Helper component that ties the action bar to the navigation drawer.
     */
    private ActionBarDrawerToggle mDrawerToggle;

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerListView;
    private View mFragmentContainerView;
    ArrayList<DrawerMenuItems> mDrawerMenuLists = new ArrayList<>();
    DrawerMenuAdapter mDrawerMenuAdapter;
    private int mCurrentSelectedPosition = 0;
    private boolean mFromSavedInstanceState;
    private boolean mUserLearnedDrawer;
    private TextView tvName;
    private TextView tvEmail;
    private TextView tvMobileNo;
    private ImageView mProfilePicture;
    private SwitchCompat mLanguageChangeSwitchCompat;

    public NavigationDrawerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Read in the flag indicating whether or not the user has demonstrated awareness of the
        // drawer. See PREF_USER_LEARNED_DRAWER for details.

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mUserLearnedDrawer = sp.getBoolean(PREF_USER_LEARNED_DRAWER, false);

        if (savedInstanceState != null) {
            mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
            mFromSavedInstanceState = true;
        }

        // Select either the default item (0) or the last selected item.
        selectItem(mCurrentSelectedPosition);
    }


    public static NavigationDrawerFragment newInstance() {
        NavigationDrawerFragment mNavigationDrawerFragment = new NavigationDrawerFragment();
        return mNavigationDrawerFragment;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Indicate that this fragment would like to influence the set of actions in the action bar.
//        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View menuListItems = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);


      //  FontsManager.initFormAssets(getActivity(), "Lato-Light.ttf");
      //  FontsManager.changeFonts(menuListItems);

        initiateWidgets(menuListItems);
        setUserDetails();
        setProfilePictures();
        getMenuItems();
        setAdapter();
        setLanguage();

        mDrawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItem(position);
            }
        });

        mLanguageChangeSwitchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                showAlert();
            }
        });
        mLanguageChangeSwitchCompat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlert();
            }
        });

        return menuListItems;
    }

    private void setLanguage() {
        String existingLanguage = getSelectedLanguageFromSharedPreference();
        if (existingLanguage.equals(LanguageCode.ENGLISH)) {
            mLanguageChangeSwitchCompat.setChecked(false);
            mLanguageChangeSwitchCompat.setText(getString(R.string.txt_english));
        } else if (existingLanguage.equals(LanguageCode.ARABIC)) {
            mLanguageChangeSwitchCompat.setChecked(true);
            mLanguageChangeSwitchCompat.setText(getString(R.string.txt_arabic));
        }
    }

    private void setAdapter() {
        mDrawerMenuAdapter = new DrawerMenuAdapter(getActivity(), mDrawerMenuLists);
        mDrawerListView.setAdapter(mDrawerMenuAdapter);
        mDrawerListView.setItemChecked(mCurrentSelectedPosition, true);
    }

    private void setProfilePictures() {
        String imageUrl = UserDetails.getImageUrl();
        if (imageUrl != null && !imageUrl.equals("")) {
            Picasso.with(getActivity())
                    .load(imageUrl)
                    .into(mProfilePicture);
        }
    }

    private void setUserDetails() {
        if (!UserDetails.getCustomerName().isEmpty()) {
            tvName.setText(UserDetails.getCustomerName());
            tvEmail.setText(UserDetails.getCustomerEmail());
            tvMobileNo.setText(UserDetails.getCustomerMobile());
        } else {
            tvName.setText("");
            tvEmail.setText(getResources().getString(R.string.txt_guest));
            tvMobileNo.setText("");
        }
    }

    private void initiateWidgets(View menuListItems) {
        tvName = (TextView) menuListItems.findViewById(R.id.tvName);
        tvEmail = (TextView) menuListItems.findViewById(R.id.tvEmail);
        tvMobileNo = (TextView) menuListItems.findViewById(R.id.tvMobileNo);
        mProfilePicture = (ImageView) menuListItems.findViewById(R.id.profile_picture);
        mLanguageChangeSwitchCompat = (SwitchCompat) menuListItems.findViewById(R.id.language_changer);
        mDrawerListView = (ListView) menuListItems.findViewById(R.id.FND_LV_menu_items);
    }

    private void getMenuItems() {

        mDrawerMenuLists = new ArrayList<>();
        DrawerMenuItems mDrawerMenuItems;
        mDrawerMenuItems = new DrawerMenuItems();
        mDrawerMenuItems.setmTitle(getResources().getString(R.string.txt_home));
        mDrawerMenuItems.setmIcon(R.drawable.ic_menu_home);
        mDrawerMenuLists.add(mDrawerMenuItems);


        mDrawerMenuItems = new DrawerMenuItems();
        mDrawerMenuItems.setmTitle(getResources().getString(R.string.txt_myprofile));
        mDrawerMenuItems.setmIcon(R.drawable.contact);
        mDrawerMenuLists.add(mDrawerMenuItems);
        mDrawerMenuItems = new DrawerMenuItems();
        mDrawerMenuItems.setmTitle(getResources().getString(R.string.txt_orders));
        mDrawerMenuItems.setmIcon(R.drawable.ic_menu_order);
        mDrawerMenuLists.add(mDrawerMenuItems);

        mDrawerMenuItems = new DrawerMenuItems();
        mDrawerMenuItems.setmTitle(getResources().getString(R.string.txt_tabao_credit));
        mDrawerMenuItems.setmIcon(R.drawable.ic_credit);
        mDrawerMenuLists.add(mDrawerMenuItems);

        mDrawerMenuItems = new DrawerMenuItems();
        mDrawerMenuItems.setmTitle(getResources().getString(R.string.txt_help));
        mDrawerMenuItems.setmIcon(R.drawable.ic_menu_help);
        mDrawerMenuLists.add(mDrawerMenuItems);

        mDrawerMenuItems = new DrawerMenuItems();
        mDrawerMenuItems.setmTitle(getResources().getString(R.string.txt_contact));
        mDrawerMenuItems.setmIcon(R.drawable.contact1);
        mDrawerMenuLists.add(mDrawerMenuItems);


        if (UserDetails.getCustomerKey().trim().isEmpty()) {
            mDrawerMenuItems = new DrawerMenuItems();
            mDrawerMenuItems.setmTitle(getResources().getString(R.string.txt_sign_in));
            mDrawerMenuItems.setmIcon(R.drawable.ic_menu_login);
            mDrawerMenuLists.add(mDrawerMenuItems);

        } else if (!UserDetails.getCustomerKey().trim().isEmpty()) {

            mDrawerMenuItems = new DrawerMenuItems();
            mDrawerMenuItems.setmTitle(getResources().getString(R.string.txt_sign_out));
            mDrawerMenuItems.setmIcon(R.drawable.ic_menu_logout);
            mDrawerMenuLists.add(mDrawerMenuItems);
        }


    }


    /**
     * Users of this fragment must call this method to set up the navigation drawer interactions.
     *
     * @param fragmentId   The android:id of this fragment in its activity's layout.
     * @param drawerLayout The DrawerLayout containing this fragment's UI.
     */
    public void setUp(int fragmentId, DrawerLayout drawerLayout) {
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;

        // set a custom shadow that overlays the main content when the drawer opens
        // mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener

//        ActionBar actionBar = getActionBar();

        mDrawerToggle = new ActionBarDrawerToggle(getActivity(),                    /* host Activity */
                mDrawerLayout,                    /* DrawerLayout object */
                android.R.drawable.ic_menu_slideshow,             /* nav drawer image to replace 'Up' caret */
                R.string.navigation_drawer_open,  /* "open drawer" description for accessibility */
                R.string.navigation_drawer_close  /* "close drawer" description for accessibility */
        ) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (!isAdded()) {
                    return;
                }

                getActivity().supportInvalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!isAdded()) {
                    return;
                }

                if (!mUserLearnedDrawer) {
                    // The user manually opened the drawer; store this flag to prevent auto-showing
                    // the navigation drawer automatically in the future.
                    mUserLearnedDrawer = true;
                    SharedPreferences sp = PreferenceManager
                            .getDefaultSharedPreferences(getActivity());
                    sp.edit().putBoolean(PREF_USER_LEARNED_DRAWER, true).apply();

                }

                getActivity().supportInvalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                float moveFactor = mDrawerListView.getWidth() * slideOffset;
                ((HomeActivity) getActivity()).setSlide(moveFactor);
            }
        };

        // If the user hasn't 'learned' about the drawer, open it to introduce them to the drawer,
        // per the navigation drawer design guidelines.
        if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
        }

        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    private void selectItem(int position) {
        mCurrentSelectedPosition = position;
        if (mDrawerListView != null) {
            mDrawerListView.setItemChecked(position, true);
        }
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(mFragmentContainerView);
        }
        if (mCallbacks != null) {
            mCallbacks.onNavigationDrawerItemSelected(position);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        EventBus.getDefault().register(this);
        try {
            mCallbacks = (NavigationDrawerCallbacks) activity;
        } catch (ClassCastException e) {

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Forward the new configuration the drawer toggle component.
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // If the drawer is open, show the global app actions in the action bar. See also
        // showGlobalContextActionBar, which controls the top-left area of the action bar.

//        inflater.inflate(R.menu.main, menu);
//        showGlobalContextActionBar();
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * Callbacks interfaces that all activities using this fragment must implement.
     */
    public static interface NavigationDrawerCallbacks {
        /**
         * Called when an item in the navigation drawer is selected.
         */
        void onNavigationDrawerItemSelected(int position);

    }

    @Override
    public void onDestroy() {
        getActivity().finish();
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    public int getmCurrentSelectedPosition() {
        return mCurrentSelectedPosition;
    }

    public void setmCurrentSelectedPosition(int mCurrentSelectedPosition) {
        this.mCurrentSelectedPosition = mCurrentSelectedPosition;
    }

    public void onEvent(final MainMenuRefreshEvent ev) {
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                getMenuItems();
                mDrawerMenuAdapter = new DrawerMenuAdapter(getActivity(), mDrawerMenuLists);
                mDrawerListView.setAdapter(mDrawerMenuAdapter);
                mDrawerMenuAdapter.notifyChanges();
            }
        });
    }

    public void onEvent(final SigninEvent ev) {
        getActivity().runOnUiThread(new Runnable() {
            public void run() {

                if (!UserDetails.getCustomerName().isEmpty()) {
                    tvName.setText(UserDetails.getCustomerName());
                    tvEmail.setText(UserDetails.getCustomerEmail());
                    tvMobileNo.setText(UserDetails.getCustomerMobile());
                } else {
                    tvName.setText("");
                    tvEmail.setText(getResources().getString(R.string.txt_guest));
                    tvMobileNo.setText("");

                }

            }
        });
    }

    public void showAlert() {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.AlertDialogCustom));
        mBuilder.setMessage(getResources().getString(R.string.txt_change_language));
        mBuilder.setCancelable(false);
        mBuilder.setPositiveButton(getResources().getString(R.string.tct_ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (mLanguageChangeSwitchCompat.isChecked()) {
                    changeLanguageTo(LanguageCode.ARABIC);

                } else {
                    changeLanguageTo(LanguageCode.ENGLISH);
                }

            }
        })
                .setNegativeButton(getResources().getString(R.string.txt_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        mLanguageChangeSwitchCompat.setChecked(false);
                        dialog.dismiss();
                    }
                });

        AlertDialog mDialog = mBuilder.create();
        mDialog.show();

    }

    private void changeLanguageTo(String language) {
        Resources res = getActivity().getResources();
        // Change locale settings in the app.
        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        conf.locale = new Locale(language);
        res.updateConfiguration(conf, dm);
        setSelectedLanguage();
    }

    private void setSelectedLanguage() {
        if (!mLanguageChangeSwitchCompat.isChecked()) {
            System.out.println(LanguageCode.ENGLISH);
            setLanguageInSharedPreference(LanguageCode.ENGLISH);
        } else {
            System.out.println(LanguageCode.ARABIC);
            setLanguageInSharedPreference(LanguageCode.ARABIC);
        }
    }

    private void setLanguageInSharedPreference(String _languageCode) {
        SharedPreferences sharedpreferences = getActivity().getSharedPreferences(LanguageCode.MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(LanguageCode.LANGUAGE_CODE, _languageCode);
        editor.commit();

        startActivity(new Intent(getActivity(), HomeActivity.class));
        getActivity().finish();
//        editor.apply();
    }

    public String getSelectedLanguageFromSharedPreference() {
        SharedPreferences prefs = getActivity().getSharedPreferences(LanguageCode.MyPREFERENCES, getActivity().MODE_PRIVATE);
        String selectedLanguageFromSharedPreference = prefs.getString(LanguageCode.LANGUAGE_CODE, null);
        if (selectedLanguageFromSharedPreference == null) {
            selectedLanguageFromSharedPreference = LanguageCode.ENGLISH;
            mLanguageChangeSwitchCompat.setText(getString(R.string.txt_english));
        } else {

        }
        return selectedLanguageFromSharedPreference;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();

    }

}
