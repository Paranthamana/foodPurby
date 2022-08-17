package com.foodpurby.screens;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sloop.fonts.FontsManager;
import com.foodpurby.HomeActivity;
import com.foodpurby.adapters.AreaAdapter;
import com.foodpurby.adapters.CategoryListAdapter;
import com.foodpurby.adapters.CityAdapter;
import com.foodpurby.adapters.GridCategoryAdapter;
import com.foodpurby.baseclass.MyActivity;
import com.foodpurby.customview.CustomButton;
import com.foodpurby.events.AddRemoveToCartEvent;
import com.foodpurby.model.DAOArea;
import com.foodpurby.model.DAOCategory;
import com.foodpurby.model.DAOCity;
import com.foodpurby.model.DAOCurrentLocation;
import com.foodpurby.ondbstorage.AppSettings;
import com.foodpurby.ondbstorage.DBActionsAppSettings;
import com.foodpurby.ondbstorage.DBActionsRestaurantBranch;
import com.foodpurby.util.Common;
import com.foodpurby.util.CustomProgressDialog;
import com.foodpurby.util.GPSTracker;
import com.foodpurby.utillities.ScrollableGridView;
import com.foodpurby.utillities.SessionManager;
import com.foodpurby.R;
import com.foodpurby.utillities.AppSharedValues;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by android1 on 12/29/2015.
 */
public class CityArea extends AppCompatActivity {

    private EventBus bus = EventBus.getDefault();
    private List<DAOCity.City_list> cityListsFromAPI = new ArrayList<>();
    private List<DAOArea.Area_list> areaListsFromAPI = new ArrayList<>();
    private ListView lvAddress;
    Toolbar mToolbar;
    TextView mToolHeading;
    ImageView mImgLogo;
    TextView tvCityName;
    TextView tvAreaName;
    CustomButton btnShowRestaurants, btnAllRestaurants;
    ScrollableGridView vCategoryGridView;
    CityAdapter cityAdapter;
    ListView lvCities;
    TextView tvWait;
    TextView tvMyLocation;
    AreaAdapter areaAdapter;
    ListView lvArea;
    LinearLayout llSearch;
    EditText etSearch;
    Spinner vCategorySpinner;
    List<DAOCategory.Data> mCategoryList;
    String mCategoryName, mCategoryId;
    private ArrayList<String> mCategoryNameArray, mCategoryIdArray;
    private TextView tvCategory;
    private CategoryListAdapter mCategoryListAdapter;
    private GridCategoryAdapter mGridCategoryAdapter;
    int selectAllCategory = 0;
    private CustomProgressDialog mCustomProgressDialog;
    int[] imageId = {R.drawable.ic_food, R.drawable.ic_grocery};
    GPSTracker mGpsTracker;
    private Common uCommon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_city_area);

        mGpsTracker = new GPSTracker(CityArea.this);
        uCommon = new Common(this);
    //   FontsManager.initFormAssets(this, "Lato-Light.ttf");
     //   FontsManager.changeFonts(this);

        mCustomProgressDialog = CustomProgressDialog.getInstance();
        mToolbar = (Toolbar) findViewById(R.id.tb_header);
        mToolbar.setNavigationIcon(R.drawable.back);
        mImgLogo = (ImageView) findViewById(R.id.imgLogo);
        mImgLogo.setVisibility(View.GONE);
        mToolHeading = (TextView) findViewById(R.id.tool_title);
        mToolHeading.setText("Category");
        mToolHeading.setVisibility(View.VISIBLE);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });

        vCategoryGridView = (ScrollableGridView) findViewById(R.id.gridCategory);
        btnShowRestaurants = (CustomButton) findViewById(R.id.btnShowRestaurants);
        btnAllRestaurants = (CustomButton) findViewById(R.id.btnAllRestaurants);
        tvCityName = (TextView) findViewById(R.id.tvCityName);
        tvAreaName = (TextView) findViewById(R.id.tvAreaName);
        tvCategory = (TextView) findViewById(R.id.ACA_category);
        tvMyLocation = (TextView) findViewById(R.id.tvMyLocation);

        mCategoryNameArray = new ArrayList<String>();
        mCategoryIdArray = new ArrayList<String>();
        //showSelectedCategory();

        if (!mCustomProgressDialog.isShowing()) {
            mCustomProgressDialog.ChageMessage(CityArea.this, getString(R.string.dialog_please_wait), getString(R.string.dialog_please_wait));
            mCustomProgressDialog.show(CityArea.this);
        }

        DAOCategory.getInstance().Callresponse(new Callback<DAOCategory.ResponseCategory>() {
            @Override
            public void success(DAOCategory.ResponseCategory responseCategory, Response response) {
                if (responseCategory.getHttpcode() == 200) {
                    mCategoryList = responseCategory.getData();
                    if (mCategoryList.size() > 0) {
                        mGridCategoryAdapter = new GridCategoryAdapter(CityArea.this, bus, mCategoryList, imageId);
                        vCategoryGridView.setAdapter(mGridCategoryAdapter);
                        for (int count = 0; count < mCategoryList.size(); count++) {
                            mCategoryName = mCategoryList.get(count).getCategory_name();
                            mCategoryId = mCategoryList.get(count).getCategory_id();
                        }
                    }
                    mCustomProgressDialog.dismiss();
                } else {
                    mCustomProgressDialog.dismiss();
                    Toast.makeText(CityArea.this, responseCategory.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                mCustomProgressDialog.dismiss();
                error.printStackTrace();
            }
        });

        tvMyLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DBActionsRestaurantBranch.deleteAll();

                try {
                    if (uCommon.isInternetConnected(CityArea.this)) {
                        if (mGpsTracker.isGPSEnabled) {

                            final CustomProgressDialog mCustomProgressDialog = new CustomProgressDialog();
                            mCustomProgressDialog.show(CityArea.this);
                            DAOCurrentLocation.getInstance().Callresponse(SessionManager.getInstance().getAddresskey(CityArea.this), AppSharedValues.getLanguage(), mGpsTracker.getLatitude(), mGpsTracker.getLongitude(), SessionManager.getInstance().getDeviceToken(CityArea.this), 1, new Callback<DAOCurrentLocation.CurrentLocation>() {
                                @Override
                                public void success(DAOCurrentLocation.CurrentLocation currentLocation, Response response) {

                                    if (currentLocation.getStatus().equalsIgnoreCase("success")) {
                                        mCustomProgressDialog.dismiss();
                                        if (currentLocation.getData() != null) {

                                            DAOCity.City_list city = new DAOCity().new City_list();
                                            city.setCity_key(currentLocation.getData().getGet_location().getCity_key());
                                            city.setCity_name(currentLocation.getData().getGet_location().getCity_name());
                                            DAOArea.Area_list area = new DAOArea().new Area_list();
                                            area.setArea_key(currentLocation.getData().getGet_location().getArea_key());
                                            area.setArea_name(currentLocation.getData().getGet_location().getArea_name());
                                            AppSharedValues.setCity(city);
                                            AppSharedValues.setArea(area);

                                            if (AppSharedValues.getArea() != null && AppSharedValues.getCity() != null) {
                                                mCustomProgressDialog.dismiss();
                                                MyActivity.DisplayBaseActivity(CityArea.this);
                                                finish();
                                            }
                                        }
                                    } else {
                                        mCustomProgressDialog.dismiss();
                                        Toast.makeText(CityArea.this, currentLocation.getMessage(), Toast.LENGTH_SHORT).show();
                                        // MyActivity.DisplayBaseActivity(CityArea.this);
                                        //finish();
                                    }
                                }

                                @Override
                                public void failure(RetrofitError error) {
                                    error.printStackTrace();
                                    mCustomProgressDialog.dismiss();
                                    Toast.makeText(CityArea.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        btnAllRestaurants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vCategoryGridView.setSelector(R.color.transparent);
                mGridCategoryAdapter.notifyDataSetChanged();
                selectAllCategory = 1;
                tvCategory.setTag("0");
                tvCategory.setText("All shops");
            }
        });

        vCategoryGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //vCategoryGridView.getChildAt(position).findViewById(R.id.category_grid_image).getResources().getDrawable(R.drawable.category_selector);

                if (position == 0) {
                    btnShowRestaurants.setText(getResources().getString(R.string.txt_show_restaurants) + " for Restaurants");
                } else {
                    btnShowRestaurants.setText(getResources().getString(R.string.txt_show_restaurants) + " for groceries");
                }

                vCategoryGridView.setSelector(R.drawable.category_selector);
                tvCategory.setTag(mCategoryList.get(position).getCategory_id());
                System.out.println("Res mCategoryList id:" + mCategoryList.get(position).getCategory_id());
                tvCategory.setText(mCategoryList.get(position).getCategory_name());
                selectAllCategory = 1;
            }
        });


        AppSettings cityArea = DBActionsAppSettings.get();
        if (cityArea != null && !cityArea.getCityName().trim().isEmpty()) {
            tvCityName.setText(cityArea.getCityName());
            tvAreaName.setText(cityArea.getAreaName());

            DAOCity.City_list city = new DAOCity().new City_list();
            city.setCity_key(cityArea.getCityKey());
            city.setCity_name(cityArea.getCityName());

            DAOArea.Area_list area = new DAOArea().new Area_list();
            area.setArea_key(cityArea.getAreaKey());
            area.setArea_name(cityArea.getAreaName());

            tvCityName.setTag(city);
            tvAreaName.setTag(area);

        }

        tvCityName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //tvCity.setTag(null);
                //tvCityName.setText("");
                List<DAOCity.City_list> cityLists = new ArrayList<DAOCity.City_list>();
                cityAdapter = new CityAdapter(CityArea.this, bus, cityLists);
                AlertDialog.Builder mDialog = new AlertDialog.Builder(CityArea.this, R.style.MyAlertDialogStyle);
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
                        tvCityName.setTag(city);
                        tvCityName.setText(city.getCity_name());

                        tvAreaName.setText("");
                        tvAreaName.setTag(null);

                        if (mAlertDilaog.isShowing()) {
                            mAlertDilaog.dismiss();
                        }

                        OpenAreaDialog();
                    }
                });

                mAlertDilaog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                mAlertDilaog.show();

                DAOCity.getInstance().Callresponse(AppSharedValues.getLanguage(), new Callback<DAOCity.City>() {
                    @Override
                    public void success(DAOCity.City city, Response response) {

                        if (city.getHttpcode().equals("200")) {

                            cityListsFromAPI = city.getData().getCity_list();
                            cityAdapter = new CityAdapter(CityArea.this, bus, cityListsFromAPI);
                            lvCities.setAdapter(cityAdapter);
                            cityAdapter.notifyChanges();

                            tvWait.setVisibility(View.GONE);
                            lvCities.setVisibility(View.VISIBLE);
                            llSearch.setVisibility(View.VISIBLE);
                        } else {
                            Toast.makeText(CityArea.this, city.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(CityArea.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        if (mAlertDilaog.isShowing()) {
                            mAlertDilaog.dismiss();
                        }
                    }
                });
            }
        });

        tvAreaName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenAreaDialog();
            }
        });

        btnShowRestaurants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvCityName.getTag() != null && tvAreaName.getTag() != null && selectAllCategory == 1) {
                    DBActionsRestaurantBranch.deleteAll();
                    DAOCity.City_list city = (DAOCity.City_list) tvCityName.getTag();
                    DAOArea.Area_list area = (DAOArea.Area_list) tvAreaName.getTag();
                    DBActionsAppSettings.set(city.getCity_key(), city.getCity_name(), area.getArea_key(), area.getArea_name());
                    AppSharedValues.setArea(area);
                    AppSharedValues.setCity(city);
                    AppSharedValues.setCategory(tvCategory.getTag().toString());
//                    bus.post(new RestaurantsRefreshEvent());
                    MyApplication.getInstance().trackEvent("Button", "Click", "Show Restaurant");
                    MyActivity.DisplayBaseActivity(CityArea.this);
                    finish();
                } else if (tvCityName.getTag() == null) {
                    Toast.makeText(CityArea.this, R.string.txt_select_city_and_area, Toast.LENGTH_SHORT).show();
                } else if (tvAreaName.getTag() == null) {
                    Toast.makeText(CityArea.this, R.string.toast_select_area, Toast.LENGTH_SHORT).show();
                } else if (selectAllCategory == 0) {
                    Toast.makeText(CityArea.this, R.string.toast_select_category, Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void OpenAreaDialog() {
        if (tvCityName.getTag() != null) {
            String cityKey = ((DAOCity.City_list) tvCityName.getTag()).getCity_key();

            AlertDialog.Builder mDialog = new AlertDialog.Builder(CityArea.this, R.style.MyAlertDialogStyle);
            final View vDialog = getLayoutInflater().inflate(R.layout.dialog_city_area, null);
            mDialog.setView(vDialog);

            List<DAOArea.Area_list> areaLists = new ArrayList<>();
            areaAdapter = new AreaAdapter(CityArea.this, bus, areaLists);

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

                        List<DAOArea.Area_list> areaListsSearch = new ArrayList<>();

                        for (DAOArea.Area_list area : areaListsFromAPI) {
                            if (area.getArea_name().toLowerCase().contains(etSearch.getText().toString().trim())) {
                                areaListsSearch.add(area);
                            }
                        }

                        areaAdapter.SearchKeyword(areaListsSearch);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            lvArea = (ListView) vDialog.findViewById(R.id.lvCitiesArea);
            tvWait = (TextView) vDialog.findViewById(R.id.tvWait);
            lvArea.setAdapter(areaAdapter);

            lvArea.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    AreaAdapter.ViewHolder viewHolder = (AreaAdapter.ViewHolder) view.getTag();
                    DAOArea.Area_list area = (DAOArea.Area_list) viewHolder.tvName.getTag();
                    tvAreaName.setTag(area);
                    tvAreaName.setText(area.getArea_name());

                    DAOCity.City_list city = (DAOCity.City_list) tvCityName.getTag();
                    DBActionsAppSettings.set(city.getCity_key(), city.getCity_name(), area.getArea_key(), area.getArea_name());
                    if (mAlertDilaog.isShowing()) {
                        mAlertDilaog.dismiss();
                    }
                }
            });

            mAlertDilaog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            mAlertDilaog.show();

            DAOArea.getInstance().Callresponse(
                    "test",
                    AppSharedValues.getLanguage(),
                    cityKey,
                    new Callback<DAOArea.Area>() {
                        @Override
                        public void success(DAOArea.Area area, Response response) {

                            if (area.getHttpcode().equals("200")) {

                                areaListsFromAPI = area.getData().getArea_list();
                                areaAdapter = new AreaAdapter(CityArea.this, bus, areaListsFromAPI);
                                lvArea.setAdapter(areaAdapter);
                                areaAdapter.notifyChanges();

                                tvWait.setVisibility(View.GONE);
                                lvArea.setVisibility(View.VISIBLE);
                                llSearch.setVisibility(View.VISIBLE);
                            } else {
                                Toast.makeText(CityArea.this, area.getMessage(), Toast.LENGTH_SHORT).show();

                                if (mAlertDilaog.isShowing()) {
                                    mAlertDilaog.dismiss();
                                }
                            }
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            Toast.makeText(CityArea.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                            if (mAlertDilaog.isShowing()) {
                                mAlertDilaog.dismiss();
                            }
                        }
                    });

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
        if (id == android.R.id.home) {
            finish();
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

    private void notifyChanges() {
        //btnAddress.setText("Pay order total " + Common.getPriceWithCurrencyCode(DBActionsCart.getTotalCartPriceIncludingTax(AppSharedValues.getSelectedRestaurantBranchKey()).toString()));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onBackPressed() {
        if (AppSharedValues.getArea() == null && AppSharedValues.getCity() == null) {
            super.onBackPressed();
        } else {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        }

    }

}
