package com.foodpurby.screens;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.foodpurby.R;
import com.foodpurby.adapters.AreaAdapter;
import com.foodpurby.adapters.CategoryListAdapter;
import com.foodpurby.adapters.CityAdapter;
import com.foodpurby.adapters.GridCategoryAdapter;
import com.foodpurby.customview.CustomButton;
import com.foodpurby.customview.CustomTextView;
import com.foodpurby.events.FilterIconVisibilityEvent;
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
import com.foodpurby.utillities.AppSharedValues;
import com.foodpurby.utillities.ScrollableGridView;
import com.sloop.fonts.FontsManager;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CityAreaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CityAreaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CityAreaFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    View vRootView;
    private EventBus bus = EventBus.getDefault();
    private List<DAOCity.City_list> cityListsFromAPI = new ArrayList<>();
    private List<DAOArea.Area_list> areaListsFromAPI = new ArrayList<>();
    private ListView lvAddress;
    Toolbar mToolbar;
    TextView mToolHeading;
    ImageView mImgLogo;
    CustomTextView tvCityName;
    CustomTextView tvAreaName;
    CustomButton btnShowRestaurants, btnAllRestaurants;
    ScrollableGridView vCategoryGridView;
    CityAdapter cityAdapter;
    ListView lvCities;
    TextView tvWait;
    CustomTextView tvMyLocation;
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
    Boolean status = null;

    public CityAreaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CityAreaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CityAreaFragment newInstance(String param1, String param2) {
        CityAreaFragment fragment = new CityAreaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_home_page, menu);
        MenuItem item_search = menu.findItem(R.id.action_search);
        item_search.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
        item_search.setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vRootView = inflater.inflate(R.layout.activity_city_area, container, false);
        initViews(vRootView);
        bus.post(new FilterIconVisibilityEvent(View.VISIBLE, FilterIconVisibilityEvent.PageTitleType.category));
        return vRootView;
    }

    private void initViews(View vRootView) {

        mGpsTracker = new GPSTracker(getActivity());
        uCommon = new Common(getActivity());
      //  FontsManager.initFormAssets(getActivity(), "Lato-Light.ttf");
      //  FontsManager.changeFonts(getActivity());

        mCustomProgressDialog = CustomProgressDialog.getInstance();

        vCategoryGridView = (ScrollableGridView) vRootView.findViewById(R.id.gridCategory);
        btnShowRestaurants = (CustomButton) vRootView.findViewById(R.id.btnShowRestaurants);
        btnAllRestaurants = (CustomButton) vRootView.findViewById(R.id.btnAllRestaurants);
        tvCityName = (CustomTextView) vRootView.findViewById(R.id.tvCityName);
        tvAreaName = (CustomTextView) vRootView.findViewById(R.id.tvAreaName);
        tvCategory = (TextView) vRootView.findViewById(R.id.ACA_category);
        tvMyLocation = (CustomTextView) vRootView.findViewById(R.id.tvMyLocation);

        mCategoryNameArray = new ArrayList<String>();
        mCategoryIdArray = new ArrayList<String>();
        //showSelectedCategory();

        if (!mCustomProgressDialog.isShowing()) {
            mCustomProgressDialog.ChageMessage(getActivity(), getString(R.string.dialog_please_wait), getString(R.string.dialog_please_wait));
            mCustomProgressDialog.show(getActivity());
        }

        DAOCategory.getInstance().Callresponse(new Callback<DAOCategory.ResponseCategory>() {
            @Override
            public void success(DAOCategory.ResponseCategory responseCategory, Response response) {
                if (responseCategory.getHttpcode() == 200) {
                    mCategoryList = responseCategory.getData();
                    tvCategory.setTag(mCategoryList.get(0).getCategory_id());
                    if (mCategoryList.size() > 0) {
                        mGridCategoryAdapter = new GridCategoryAdapter(getActivity(), bus, mCategoryList, imageId);
                        vCategoryGridView.setAdapter(mGridCategoryAdapter);
                        for (int count = 0; count < mCategoryList.size(); count++) {
                            mCategoryName = mCategoryList.get(count).getCategory_name();
                            mCategoryId = mCategoryList.get(count).getCategory_id();
                        }
                    }
                    mCustomProgressDialog.dismiss();
                } else {
                    mCustomProgressDialog.dismiss();
                    Toast.makeText(getActivity(), responseCategory.getMessage(), Toast.LENGTH_SHORT).show();
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
                    if (uCommon.isInternetConnected(getActivity())) {
                        mGpsTracker = new GPSTracker(getActivity());
                        if (mGpsTracker.isGPSEnabled) {
                            if (mGpsTracker.getLatitude() != 0.0 || mGpsTracker.getLongitude() != 0.0) {
                                final CustomProgressDialog mCustomProgressDialog = new CustomProgressDialog();
                                mCustomProgressDialog.show(getActivity());
                                DAOCurrentLocation.getInstance().Callresponse("", AppSharedValues.getLanguage(), mGpsTracker.getLatitude(), mGpsTracker.getLongitude(), AppSharedValues.getDeciveId(), 1, new Callback<DAOCurrentLocation.CurrentLocation>() {
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


                                                tvCityName.setTag(city);
                                                tvCityName.setText(city.getCity_name());
                                                tvAreaName.setTag(area);
                                                tvAreaName.setText(area.getArea_name());


                                                if (AppSharedValues.getArea() != null && AppSharedValues.getCity() != null) {
                                                    status = Common.GetRestaurants(getActivity(), mCustomProgressDialog, bus);

                                                    Intent ins = new Intent(getActivity(), RestaurantActivity.class);
                                                    startActivity(ins);

                                                    //getActivity ().getSupportFragmentManager ().beginTransaction ().replace (R.id.container, new Restaurants ()).commit ();
                                                } else {
                                                    getFragmentManager().beginTransaction().replace(R.id.container, new CityAreaFragment()).commit();
                                                }


                                            }
                                        } else {
                                            mCustomProgressDialog.dismiss();
                                            Toast.makeText(getActivity(), currentLocation.getMessage(), Toast.LENGTH_SHORT).show();
                                            // MyActivity.DisplayBaseActivity(CityArea.this);
                                            //finish();
                                        }
                                    }

                                    @Override
                                    public void failure(RetrofitError error) {
                                        error.printStackTrace();
                                        mCustomProgressDialog.dismiss();
                                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                Toast.makeText(getActivity(), "Try after few seconds", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                            alertDialog.setTitle(Html.fromHtml("<font color='#000000'>" + getString(R.string.title_enable_location) + "</font>"));
                            alertDialog.setMessage(Html.fromHtml("<font color='#000000'>" + getString(R.string.msg_gps_not_enable) + "</font>"));
                            alertDialog.setPositiveButton(getString(R.string.title_enable_location),
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                            startActivity(intent);
                                            dialog.dismiss();
                                        }
                                    });
                            // on pressing cancel button
                            alertDialog.setNegativeButton(getString(R.string.dialog_cancel),
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            // Showing Alert Message
                            try {
                                alertDialog.show();
                            } catch (Exception e) {

                            }
                        }
                    } else {
                        Toast.makeText(getActivity(), "No internet Connetion", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        btnAllRestaurants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnAllRestaurants.setSelected(true);
                vCategoryGridView.setSelector(R.color.transparent);
                mGridCategoryAdapter.notifyDataSetChanged();
                selectAllCategory = 1;
                tvCategory.setTag("0");
                tvCategory.setText(getResources().getString(R.string.txt_allshops));
                btnShowRestaurants.setText(getResources().getString(R.string.txt_show_restaurants));

                tvCategory.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_category, 0, 0, 0);

                GradientDrawable gd = new GradientDrawable();
                gd.setColor(getResources().getColor(R.color.toolbar_color)); // Changes this drawbale to use a single color instead of a gradient
                gd.setCornerRadius(5);
                gd.setStroke(3, getResources().getColor(R.color.white));
                btnAllRestaurants.setBackgroundDrawable(gd);

                AppSharedValues.setCategory("100");
                vCategoryGridView.invalidateViews();

            }
        });


        AppSharedValues.setCategory("1");
        selectAllCategory = 1;


        vCategoryGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {
                    tvCategory.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_category_food, 0, 0, 0);
                    btnShowRestaurants.setText(getResources().getString(R.string.txt_show_restaurants) + " for Restaurants");
                    AppSharedValues.setCategory("1");
                } else {
                    tvCategory.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_category_groceries, 0, 0, 0);
                    btnShowRestaurants.setText(getResources().getString(R.string.txt_show_restaurants) + " for Supermarket");
                    AppSharedValues.setCategory("4");
                }

                view.setBackgroundDrawable(getResources().getDrawable(R.drawable.category_selector));

//                vCategoryGridView.setSelector(R.drawable.category_selector);
                tvCategory.setTag(mCategoryList.get(position).getCategory_id());
                tvCategory.setText(mCategoryList.get(position).getCategory_name());
                selectAllCategory = 1;
                vCategoryGridView.invalidateViews();

                btnAllRestaurants.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_red_button));

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
                cityAdapter = new CityAdapter(getActivity(), bus, cityLists);
                AlertDialog.Builder mDialog = new AlertDialog.Builder(getActivity(), R.style.MyAlertDialogStyle);
                final View vDialog = getActivity().getLayoutInflater().inflate(R.layout.dialog_city_area, null);
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
                            cityAdapter = new CityAdapter(getActivity(), bus, cityListsFromAPI);
                            lvCities.setAdapter(cityAdapter);
                            cityAdapter.notifyChanges();

                            tvWait.setVisibility(View.GONE);
                            lvCities.setVisibility(View.VISIBLE);
                            llSearch.setVisibility(View.VISIBLE);
                        } else {
                            Toast.makeText(getActivity(), city.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
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

                if (tvCityName.getTag() != null && tvAreaName.getTag() != null && selectAllCategory == 1 && tvCategory.getTag() != null) {
                    DBActionsRestaurantBranch.deleteAll();
                    DAOCity.City_list city = (DAOCity.City_list) tvCityName.getTag();
                    DAOArea.Area_list area = (DAOArea.Area_list) tvAreaName.getTag();
                    DBActionsAppSettings.set(city.getCity_key(), city.getCity_name(), area.getArea_key(), area.getArea_name());
                    AppSharedValues.setArea(area);
                    AppSharedValues.setCity(city);
                    AppSharedValues.setCategory(tvCategory.getTag().toString());
//                    bus.post(new RestaurantsRefreshEvent());
                    MyApplication.getInstance().trackEvent("Button", "Click", "Show Restaurant");
                    //MyActivity.DisplayBaseActivity(getActivity());
                    if (AppSharedValues.getArea() != null && AppSharedValues.getCity() != null) {
                        status = Common.GetRestaurants(getActivity(), mCustomProgressDialog, bus);

                        Intent ins = new Intent(getActivity(), RestaurantActivity.class);
                        startActivity(ins);

//                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new Restaurants()).commit();

                    } else {
                        getFragmentManager().beginTransaction().replace(R.id.container, new CityAreaFragment()).commit();
                    }
                } else if (tvCityName.getTag() == null) {
                    Toast.makeText(getActivity(), R.string.txt_select_city_and_area, Toast.LENGTH_SHORT).show();
                } else if (tvAreaName.getTag() == null) {
                    Toast.makeText(getActivity(), R.string.toast_select_area, Toast.LENGTH_SHORT).show();
                } else if (selectAllCategory == 0) {
                    Toast.makeText(getActivity(), R.string.toast_select_category, Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void OpenAreaDialog() {
        if (tvCityName.getTag() != null) {
            String cityKey = ((DAOCity.City_list) tvCityName.getTag()).getCity_key();

            AlertDialog.Builder mDialog = new AlertDialog.Builder(getActivity(), R.style.MyAlertDialogStyle);
            final View vDialog = getActivity().getLayoutInflater().inflate(R.layout.dialog_city_area, null);
            mDialog.setView(vDialog);

            List<DAOArea.Area_list> areaLists = new ArrayList<>();
            areaAdapter = new AreaAdapter(getActivity(), bus, areaLists);

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
                                areaAdapter = new AreaAdapter(getActivity(), bus, areaListsFromAPI);
                                lvArea.setAdapter(areaAdapter);
                                areaAdapter.notifyChanges();

                                tvWait.setVisibility(View.GONE);
                                lvArea.setVisibility(View.VISIBLE);
                                llSearch.setVisibility(View.VISIBLE);
                            } else {
                                Toast.makeText(getActivity(), area.getMessage(), Toast.LENGTH_SHORT).show();

                                if (mAlertDilaog.isShowing()) {
                                    mAlertDilaog.dismiss();
                                }
                            }
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                            if (mAlertDilaog.isShowing()) {
                                mAlertDilaog.dismiss();
                            }
                        }
                    });

        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
