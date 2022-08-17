package com.foodpurby.screens;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sloop.fonts.FontsManager;
import com.foodpurby.R;
import com.foodpurby.adapters.CityAdapter;
import com.foodpurby.adapters.DrawerMenuAdapter;
import com.foodpurby.baseclass.MyActivity;
import com.foodpurby.model.DAOCity;
import com.foodpurby.model.DAOSubscribeNow;
import com.foodpurby.model.DrawerMenuItems;
import com.foodpurby.util.Common;
import com.foodpurby.util.CustomProgressDialog;
import com.foodpurby.utillities.AppSharedValues;
import com.foodpurby.utillities.UserDetails;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MoreActivity extends AppCompatActivity {
    ArrayList<DrawerMenuItems> mDrawerMenuLists = new ArrayList<>();
    private ListView mDrawerListView;
    DrawerMenuAdapter mDrawerMenuAdapter;
    Toolbar mToolbar;
    TextView mToolHeading;
    TextView tvCity;
    EditText tvEmail;
    Button vSubscribe;
    private List<DAOCity.City_list> cityListsFromAPI = new ArrayList<>();
    CityAdapter cityAdapter;
    LinearLayout llSearch;
    EditText etSearch;
    ListView lvCities;
    TextView tvWait;
    private EventBus bus = EventBus.getDefault();
    private CustomProgressDialog mCustomProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);

     //   FontsManager.initFormAssets(this, "Lato-Light.ttf");
     //   FontsManager.changeFonts(this);

        mCustomProgressDialog = CustomProgressDialog.getInstance();
        final Common mCommon = new Common(MoreActivity.this);

        mDrawerListView = (ListView) findViewById(R.id.AM_LV_menu_items);

        mToolbar = (Toolbar) findViewById(R.id.tb_header);
        mToolHeading = (TextView) findViewById(R.id.tool_title);
        mToolbar.setNavigationIcon(R.drawable.back);
        mToolHeading.setText(getResources().getString(R.string.txt_more));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvCity = (TextView) findViewById(R.id.tvCity);
        tvEmail = (EditText) findViewById(R.id.tvEmail);
        vSubscribe = (Button) findViewById(R.id.btnSubscribe);

        vSubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (tvCity.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(MoreActivity.this, "Please select city", Toast.LENGTH_SHORT).show();
                } else if (tvEmail.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(MoreActivity.this, "Please enter Email", Toast.LENGTH_SHORT).show();
                } else {

                    if (mCommon.isInternetConnected(MoreActivity.this)) {

                        if (!mCustomProgressDialog.isShowing()) {
                            mCustomProgressDialog.ChageMessage(MoreActivity.this, "Please wait...", "Please wait...");
                            mCustomProgressDialog.show(MoreActivity.this);

                            DAOSubscribeNow.getInstance().Callresponse(tvEmail.getText().toString().trim(), tvCity.getText().toString().trim(),
                                    new Callback<DAOSubscribeNow.SubscribeNow>() {
                                        @Override
                                        public void success(DAOSubscribeNow.SubscribeNow subscribeNow, Response response) {

                                            if (subscribeNow.getHttpcode() == 200) {
                                                mCustomProgressDialog.dismiss();
                                                Toast.makeText(MoreActivity.this, subscribeNow.getMessage(), Toast.LENGTH_SHORT).show();
                                                tvCity.setText("");
                                                tvEmail.setText("");
                                            } else {
                                                mCustomProgressDialog.dismiss();
                                                Toast.makeText(MoreActivity.this, subscribeNow.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void failure(RetrofitError error) {
                                            mCustomProgressDialog.dismiss();
                                            Toast.makeText(MoreActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    } else {
                        Toast.makeText(MoreActivity.this, "Please enable internet connection", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        tvCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                List<DAOCity.City_list> cityLists = new ArrayList<DAOCity.City_list>();
                cityAdapter = new CityAdapter(MoreActivity.this, bus, cityLists);
                AlertDialog.Builder mDialog = new AlertDialog.Builder(MoreActivity.this, R.style.MyAlertDialogStyle);
                final View vDialog = getLayoutInflater().inflate(R.layout.dialog_city_area, null);
                mDialog.setView(vDialog);
                final AlertDialog mAlertDilaog = mDialog.create();
                llSearch = (LinearLayout) vDialog.findViewById(R.id.llSearch);
                etSearch = (EditText) vDialog.findViewById(R.id.etSearch);

                etSearch.addTextChangedListener(new TextWatcher() {

                    public void afterTextChanged(Editable s) {
                    }

                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                        try {

                            List<DAOCity.City_list> cityListsSearch = new ArrayList<>();

                            for (DAOCity.City_list city : cityListsFromAPI) {
                                if (city.getCity_name().toLowerCase().contains(etSearch.getText().toString().trim())) {
                                    cityListsSearch.add(city);
                                }
                            }

                            cityAdapter.SearchKeyword(cityListsSearch);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

                lvCities = (ListView) vDialog.findViewById(R.id.lvCitiesArea);
                tvWait = (TextView) vDialog.findViewById(R.id.tvWait);
                lvCities.setAdapter(cityAdapter);
                lvCities.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                        CityAdapter.ViewHolder viewHolder = (CityAdapter.ViewHolder) view.getTag();
                        DAOCity.City_list city = (DAOCity.City_list) viewHolder.tvName.getTag();
                        tvCity.setTag(city);
                        tvCity.setText(city.getCity_name());

                        if (mAlertDilaog.isShowing()) {
                            mAlertDilaog.dismiss();
                        }
                    }
                });

                mAlertDilaog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                mAlertDilaog.show();

                DAOCity.getInstance().Callresponse(AppSharedValues.getLanguage(), new Callback<DAOCity.City>() {
                    @Override
                    public void success(DAOCity.City city, Response response) {

                        if (city.getHttpcode().equals("200")) {

                            cityListsFromAPI = city.getData().getCity_list();
                            cityAdapter = new CityAdapter(MoreActivity.this, bus, cityListsFromAPI);
                            lvCities.setAdapter(cityAdapter);
                            cityAdapter.notifyChanges();

                            tvWait.setVisibility(View.GONE);
                            lvCities.setVisibility(View.VISIBLE);
                            llSearch.setVisibility(View.VISIBLE);
                        } else {
                            Toast.makeText(MoreActivity.this, city.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(MoreActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        if (mAlertDilaog.isShowing()) {
                            mAlertDilaog.dismiss();
                        }
                    }
                });
            }
        });

        getMenuItems();
        setAdapter();

    }

    private void setAdapter() {
        mDrawerMenuAdapter = new DrawerMenuAdapter(MoreActivity.this, mDrawerMenuLists);
        mDrawerListView.setAdapter(mDrawerMenuAdapter);

        mDrawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction mFragmentTransaction = fragmentManager.beginTransaction();

                if (position == 0) {
                    if (!UserDetails.getCustomerKey().isEmpty()) {
                        MyActivity.DisplayProfile(getApplicationContext());
                    } else {
                        MyActivity.DisplayUserSignInActivity(getApplicationContext());
                    }
                } /*else if (position == 1) {

                    if (!UserDetails.getCustomerKey().isEmpty()) {
                        MyActivity.DisplayOrder(getApplicationContext());
                    } else {
                        MyActivity.DisplayUserSignInActivity(getApplicationContext());
                    }
                }*/ /*else if (position == 2) {

                    if (!UserDetails.getCustomerKey().isEmpty()) {
                        MyActivity.DisplayMyAddress(getApplicationContext());
                    } else {
                        MyActivity.DisplayUserSignInActivity(getApplicationContext());
                    }
                }*/ /*else if (position == 2) {
                    if (!UserDetails.getCustomerKey().isEmpty()) {
                        MyActivity.DisplayTabaocoCredit(getApplicationContext());
                    } else {
                        MyActivity.DisplayUserSignInActivity(getApplicationContext());
                    }
                }*/ else if (position == 1) {
                    if (!UserDetails.getCustomerKey().isEmpty()) {
                        MyActivity.DisplayRestaurantFavList(getApplicationContext());
                    } else {
                        MyActivity.DisplayUserSignInActivity(getApplicationContext());
                    }
                }

            }
        });
    }

    private void getMenuItems() {

        mDrawerMenuLists = new ArrayList<>();
        DrawerMenuItems mDrawerMenuItems;
        mDrawerMenuItems = new DrawerMenuItems();
        mDrawerMenuItems.setmTitle(getResources().getString(R.string.txt_my_profile));
        mDrawerMenuItems.setmIcon(R.drawable.ic_menu_account);
        mDrawerMenuLists.add(mDrawerMenuItems);

        /*mDrawerMenuItems = new DrawerMenuItems();
        mDrawerMenuItems.setmTitle(getResources().getString(R.string.txt_orders));
        mDrawerMenuItems.setmIcon(R.drawable.ic_menu_order);
        mDrawerMenuLists.add(mDrawerMenuItems);*/

        /*mDrawerMenuItems = new DrawerMenuItems();
        mDrawerMenuItems.setmTitle(getResources().getString(R.string.txt_myaddress));
        mDrawerMenuItems.setmIcon(R.drawable.ic_menu_account);
        mDrawerMenuLists.add(mDrawerMenuItems);*/


        /*mDrawerMenuItems = new DrawerMenuItems();
        mDrawerMenuItems.setmTitle(getResources().getString(R.string.txt_tabaoco_credit));
        mDrawerMenuItems.setmIcon(R.drawable.ic_credit);
        mDrawerMenuLists.add(mDrawerMenuItems);
*/
        mDrawerMenuItems = new DrawerMenuItems();
        mDrawerMenuItems.setmTitle(getResources().getString(R.string.txt_my_fav_rest));
        mDrawerMenuItems.setmIcon(R.drawable.ic_heart);
        mDrawerMenuLists.add(mDrawerMenuItems);
    }
}
