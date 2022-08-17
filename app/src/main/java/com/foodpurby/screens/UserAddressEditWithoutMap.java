package com.foodpurby.screens;

import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.foodpurby.R;
import com.foodpurby.adapters.AreaAdapter;
import com.foodpurby.adapters.CityAdapter;
import com.foodpurby.events.AddressEvent;
import com.foodpurby.events.DummyEvent;
import com.foodpurby.model.DAOAddress;
import com.foodpurby.model.DAOAddressTypeList;
import com.foodpurby.model.DAOArea;
import com.foodpurby.model.DAOCity;
import com.foodpurby.model.DAOGetAddress;
import com.foodpurby.ondbstorage.DBActionsAppSettings;
import com.foodpurby.ondbstorage.DBActionsUserAddress;
import com.foodpurby.ondbstorage.UserAddress;
import com.foodpurby.util.CustomProgressDialog;
import com.foodpurby.util.GPSTracker;
import com.foodpurby.utillities.AppSharedValues;
import com.foodpurby.utillities.UserDetails;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.foodpurby.R.id.etStreet;

public class UserAddressEditWithoutMap extends AppCompatActivity implements OnMapReadyCallback {

    private CustomProgressDialog mCustomProgressDialog;
    private EventBus bus = EventBus.getDefault();

    private RelativeLayout mSnackbar;
    String mBitmapToString;
    LinearLayout mButtonBar;
    LinearLayout llMap;
    LinearLayout llAddrDetails;
    Toolbar mToolbar;
    Button btnSave;
    CityAdapter cityAdapter;
    AreaAdapter areaAdapter;
    LinearLayout llSearch;
    EditText etSearch;
    ListView lvCities;
    TextView tvWait;
    ListView lvArea;


    EditText tvCityName;
    EditText tvAreaName;
    EditText etCompany;
    EditText etFlatNo;
    EditText etStreetName;
    EditText etDeliveryInstructions;

    private EditText etBuildingName;
    private EditText etLandmark;

    private String UserAddressKey = "";
    private LinearLayout llHome;
    private LinearLayout llWork;
    private LinearLayout llOther;
    private LinearLayout llOtherDetails;
    private TextView mToolHeading;
    private GPSTracker mGPSTracker;
    private GoogleApiClient client;
    private GoogleMap mMap;
    private TextView vUpdateButton;
    private LatLng mCurrentLatLon;
    private double mFinalLatitude;
    private double mFinalLongitude;
    private DAOGetAddress.ResponseGetAddressApi mResponse;
    private List<DAOAddressTypeList.Address_list> addresstypes;
    private List<DAOArea.Area_list> areaListsFromAPI = new ArrayList<>();
    private List<DAOCity.City_list> cityListsFromAPI = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_map);
        EventBus.getDefault().register(this);
        mCustomProgressDialog = CustomProgressDialog.getInstance();
        vUpdateButton = (TextView) findViewById(R.id.AM_update);
        if (getIntent().getExtras() != null && getIntent().getExtras().getString("addresskey") != null && !getIntent().getExtras().getString("addresskey").trim().isEmpty()) {
            UserAddressKey = getIntent().getExtras().getString("addresskey").trim();
        }
        //Utils.isLocationEnabled(MarkAddressActivity.this);

        mToolbar = (Toolbar) findViewById(R.id.tb_header);
        if (mToolbar != null) {
            mToolbar.setNavigationIcon(R.drawable.dummy_menu);
            mToolbar.setNavigationIcon(ContextCompat.getDrawable(
                    getApplicationContext(), R.drawable.back));
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    finish();
                }
            });

            mToolHeading = (TextView) findViewById(R.id.tool_title);
            mToolHeading.setText(getString(R.string.txt_update_address));
        }


        mGPSTracker = new GPSTracker(UserAddressEditWithoutMap.this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        final CustomProgressDialog mCustomProgressDialog = new CustomProgressDialog();
        mCustomProgressDialog.show(UserAddressEditWithoutMap.this);
        DAOGetAddress.getInstance().Callresponse("", UserAddressKey, new Callback<DAOGetAddress.ResponseGetAddressApi>() {
            @Override
            public void success(DAOGetAddress.ResponseGetAddressApi responseGetAddressApi, Response response) {
                mCustomProgressDialog.dismiss();
                if (responseGetAddressApi.getHttpcode().equals("200")) {
                    mResponse = responseGetAddressApi;
                    if (!responseGetAddressApi.getData().getList().getCustomer_latitude().equals("") && !responseGetAddressApi.getData().getList().getCustomer_longitude().equals("")) {
                        mFinalLatitude = Double.parseDouble(responseGetAddressApi.getData().getList().getCustomer_latitude());
                        mFinalLongitude = Double.parseDouble(responseGetAddressApi.getData().getList().getCustomer_longitude());
//                        responseGetAddressApi.getData().getList().getAddress_key();
                    }

                    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                            .findFragmentById(R.id.map);
                    mapFragment.getMapAsync(UserAddressEditWithoutMap.this);
                    // ATTENTION: This was auto-generated to implement the App Indexing API.
                    // See https://g.co/AppIndexing/AndroidStudio for more information.
                    client = new GoogleApiClient.Builder(UserAddressEditWithoutMap.this).addApi(AppIndex.API).build();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                mCustomProgressDialog.dismiss();
                error.printStackTrace();
            }
        });


        vUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomProgressDialog.getInstance().show(UserAddressEditWithoutMap.this);
                vUpdateButton.setClickable(false);
                DAOAddressTypeList.getInstance().AddTypeCallresponse("",
                        UserDetails.getCustomerKey(),
                        AppSharedValues.getLanguage(),
                        new Callback<DAOAddressTypeList.AddressType>() {
                            @Override
                            public void success(DAOAddressTypeList.AddressType addressType, Response response) {
                                CustomProgressDialog.getInstance().dismiss();
                                if (addressType.getHttpcode().equals("200")) {
                                    addresstypes = addressType.getData().getAddress_list();
                                    callAddAddressDialog();
                                } else {
                                    Toast.makeText(UserAddressEditWithoutMap.this, addressType.getMessage(), Toast.LENGTH_SHORT).show();

                                }
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                CustomProgressDialog.getInstance().dismiss();
                                error.printStackTrace();
                                Toast.makeText(UserAddressEditWithoutMap.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });


              /*  final CustomProgressDialog mCustomProgressDialog = new CustomProgressDialog();
                mCustomProgressDialog.show(UserAddressEditWithoutMap.this);
                DAOCurrentLocation.getInstance().Callresponse("", AppSharedValues.getLanguage(), mFinalLatitude, mFinalLongitude, AppSharedValues.getDeciveId(), 1, new Callback<DAOCurrentLocation.CurrentLocation>() {
                    @Override
                    public void success(DAOCurrentLocation.CurrentLocation currentLocation, Response response) {
                        mCustomProgressDialog.dismiss();
                        if (currentLocation.getStatus().equalsIgnoreCase("success")) {
                            if (currentLocation.getData() != null) {

                            }
                        } else {
                            Toast.makeText(UserAddressEditWithoutMap.this, currentLocation.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        error.printStackTrace();
                        mCustomProgressDialog.dismiss();
                    }
                });*/

            }
        });


    }

    private void callAddAddressDialog() {

        final Dialog mUpdateAddresstDialog = new Dialog(UserAddressEditWithoutMap.this);
        mUpdateAddresstDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mUpdateAddresstDialog.setCancelable(false);
//        mUpdateAddresstDialog.setContentView(R.layout.activity_map_mark_address_edit_without_map);
//        mUpdateAddresstDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


        mUpdateAddresstDialog.setContentView(R.layout.activity_map_mark_address_without_map);
        mUpdateAddresstDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        tvCityName = (EditText) mUpdateAddresstDialog.findViewById(R.id.tvCityName);
        tvAreaName = (EditText) mUpdateAddresstDialog.findViewById(R.id.tvAreaName);
//        tvAddressType = (TextView) mUpdateAddresstDialog.findViewById(R.id.tvAddressType);

        final Spinner spinner = (Spinner) mUpdateAddresstDialog.findViewById(R.id.AM_spinner);

        CustomAdapter customAdapter = new CustomAdapter(UserAddressEditWithoutMap.this, addresstypes);
//        ArrayAdapter<DAOAddressTypeList.Address_list> adapter = new ArrayAdapter<DAOAddressTypeList.Address_list>(getApplicationContext(), android.R.layout.simple_spinner_item, addresstypes);
//        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(customAdapter);
        etFlatNo = (EditText) mUpdateAddresstDialog.findViewById(R.id.etFlatNo);
        etCompany = (EditText) mUpdateAddresstDialog.findViewById(R.id.etcompanyName);
        etStreetName = (EditText) mUpdateAddresstDialog.findViewById(etStreet);
        etLandmark = (EditText) mUpdateAddresstDialog.findViewById(R.id.etLandmark);
        etDeliveryInstructions = (EditText) mUpdateAddresstDialog.findViewById(R.id.etDeliveryInstructions);

        mButtonBar = (LinearLayout) mUpdateAddresstDialog.findViewById(R.id.button_bar);
        btnSave = (Button) mUpdateAddresstDialog.findViewById(R.id.btnSave);
        etBuildingName = (EditText) mUpdateAddresstDialog.findViewById(R.id.etBuildingName);
        etLandmark = (EditText) mUpdateAddresstDialog.findViewById(R.id.etLandmark);
        TextView tvCancel = (TextView) mUpdateAddresstDialog.findViewById(R.id.tvCancelss);

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUpdateAddresstDialog.dismiss();
                vUpdateButton.setClickable(true);
            }
        });

        llMap = (LinearLayout) mUpdateAddresstDialog.findViewById(R.id.llMap);
        llAddrDetails = (LinearLayout) mUpdateAddresstDialog.findViewById(R.id.llAddrDetails);

        llHome = (LinearLayout) mUpdateAddresstDialog.findViewById(R.id.llHome);
        llOther = (LinearLayout) mUpdateAddresstDialog.findViewById(R.id.llOther);
        llWork = (LinearLayout) mUpdateAddresstDialog.findViewById(R.id.llWork);

        llHome.setOnClickListener(addressTypeListener);
        llOther.setOnClickListener(addressTypeListener);
        llWork.setOnClickListener(addressTypeListener);

        llOtherDetails = (LinearLayout) mUpdateAddresstDialog.findViewById(R.id.llOtherDetails);

//        tvCityName.setText(currentLocation.getData().getGet_location().getCity_name());
//        tvAreaName.setText(currentLocation.getData().getGet_location().getArea_name());
        if (!UserAddressKey.isEmpty()) {

            UserAddress userAddress = DBActionsUserAddress.getAddress(UserAddressKey);
            if (userAddress != null) {

//                tvAddressType.setText(SessionManager.getInstance().getAddressType(this));
//                tvAddressType.setTag(SessionManager.getInstance().getAddressTypeId(this));
                tvCityName.setText(mResponse.getData().getList().getCity());
//                tvCityName.setTag(userAddress.getCityKey());
                tvAreaName.setText(mResponse.getData().getList().getLocation());
//                tvAreaName.setTag(userAddress.getAreaKey());

                etFlatNo.setText(mResponse.getData().getList().getApartment());
                etCompany.setText(mResponse.getData().getList().getCompany());
                spinner.setSelection(getSelectedPosition());
                //etStreetName.setText(mResponse.getData().getList().getApartment());
                etLandmark.setText(mResponse.getData().getList().getLandmark());
                etDeliveryInstructions.setText("");

//                etLandmark.setText(userAddress.getLandmark());

                //btnSave.setTag(userAddress.getUserAddressKey());
            }
        }
        tvCityName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //tvCity.setTag(null);
                //tvCityName.setText("");
                List<DAOCity.City_list> cityLists = new ArrayList<DAOCity.City_list>();
                cityAdapter = new CityAdapter(UserAddressEditWithoutMap.this, bus, cityLists);
                AlertDialog.Builder mDialog = new AlertDialog.Builder(UserAddressEditWithoutMap.this, R.style.MyAlertDialogStyle);
                final View vDialog = UserAddressEditWithoutMap.this.getLayoutInflater().inflate(R.layout.dialog_city_area, null);
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
                            cityAdapter = new CityAdapter(UserAddressEditWithoutMap.this, bus, cityListsFromAPI);
                            lvCities.setAdapter(cityAdapter);
                            cityAdapter.notifyChanges();

                            tvWait.setVisibility(View.GONE);
                            lvCities.setVisibility(View.VISIBLE);
                            llSearch.setVisibility(View.VISIBLE);
                        } else {
                            Toast.makeText(UserAddressEditWithoutMap.this, city.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(UserAddressEditWithoutMap.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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

        String mBuildings = "", mAddress = "", mLandmark = "", mPostal = "";
        String mBuilding = "", mCountry = "", mState = "", mCity = "", mArea = "";
        if (mFinalLatitude == 0.0 || mFinalLongitude == 0.0) {
            mBuilding = "";
            mAddress = "";
            mLandmark = "";
            mCity = "";
            mPostal = "";
            mState = "";
        } else {
            try {
                Geocoder geocoder;
                List<Address> addresses;
                geocoder = new Geocoder(getBaseContext(), Locale.getDefault());
                addresses = geocoder.getFromLocation(mFinalLatitude, mFinalLongitude, 1);

                if (addresses.size() != 0) {
                    if (addresses.get(0).getAdminArea() != null) {
                        mState = addresses.get(0).getAdminArea();
                    }
                    if (addresses.get(0).getAddressLine(0) != null) {
                        mBuilding = addresses.get(0).getAddressLine(0);
                        etStreetName.setText(mBuilding);
                    }
                    if (addresses.get(0).getAddressLine(1) != null) {
                        mAddress = addresses.get(0).getAddressLine(1);
//                        etStreetName.setText(mAddress);
                    }
                    if (addresses.get(0).getLocality() != null) {
                        mCity = addresses.get(0).getLocality();
                        //tvCityName.setText(mCity);
                    }
                    if (addresses.get(0).getLocality() != null) {
                        mArea = addresses.get(0).getSubLocality();
//                        tvAreaName.setText(mArea);
                    }
                    if (addresses.get(0).getPostalCode() != null) {
                        mPostal = addresses.get(0).getPostalCode();
                    }

                    if (addresses.get(0).getAddressLine(0) != null) {
                        mBuilding = addresses.get(0).getAddressLine(0);
                        etStreetName.setText(mBuilding + "," + mAddress + "," + mCity);
                    }
//                    mMainBuilding = mState + "," + mBuilding;

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.getInstance().trackEvent("Button", "Click", "Save Address");
                if (tvCityName.getText().toString() != null && !tvCityName.getText().toString().isEmpty() && tvAreaName.getText().toString() != null && !tvAreaName.getText().toString().isEmpty()) {

                   /* if (etLocationName.getText().toString().trim().isEmpty()) {
                        Toast.makeText(UserAddressEditWithoutMap.this, R.string.toast_please_enter_the_location, Toast.LENGTH_SHORT).show();
                        etLocationName.requestFocus();
                    } else*/
                    if (etStreetName.getText().toString() != null && etStreetName.getText().toString().trim().isEmpty()) {
                        Toast.makeText(UserAddressEditWithoutMap.this, "Please enter the Address1", Toast.LENGTH_SHORT).show();
                    } else {
                        if (!mCustomProgressDialog.isShowing()) {
                            mCustomProgressDialog.show(UserAddressEditWithoutMap.this);
                        }

//                        String cityKey = tvCityName.getTag().toString();
//                        String areaKey = tvAreaName.getTag().toString();


                        DAOAddress.getInstance().UpdateCallresponse("" + addresstypes.get(spinner.getSelectedItemPosition()).getType_id(), UserAddressKey,
                                tvCityName.getText().toString(), tvAreaName.getText().toString(), etStreetName.getText().toString(), etFlatNo.getText().toString(), etCompany.getText().toString(),
                                "", etLandmark.getText().toString(), "", etDeliveryInstructions.getText().toString(),
                                UserDetails.getCustomerKey(), mFinalLatitude + "", mFinalLongitude + "", new Callback<DAOAddress.Address>() {

                                    @Override
                                    public void success(DAOAddress.Address address, Response response) {
                                        if (address.getHttpcode().equals("200")) {
                                            bus.post(new AddressEvent());
                                            Toast.makeText(UserAddressEditWithoutMap.this, address.getMessage(), Toast.LENGTH_SHORT).show();
                                            finish();
                                        } else {
                                            Toast.makeText(UserAddressEditWithoutMap.this, address.getMessage(), Toast.LENGTH_SHORT).show();
                                        }

                                        if (mCustomProgressDialog.isShowing()) {
                                            mCustomProgressDialog.dismiss();
                                        }
                                    }

                                    @Override
                                    public void failure(RetrofitError error) {
                                        Toast.makeText(UserAddressEditWithoutMap.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                                        if (mCustomProgressDialog.isShowing()) {
                                            mCustomProgressDialog.dismiss();
                                        }
                                    }
                                }

                        );
                    }
                } else if (tvCityName.getText().toString() == null || tvCityName.getText().toString().isEmpty()) {
                    Toast.makeText(UserAddressEditWithoutMap.this, "Please select State to continue.", Toast.LENGTH_SHORT).show();
                } else if (tvAreaName.getText().toString() == null || tvAreaName.getText().toString().isEmpty()) {
                    Toast.makeText(UserAddressEditWithoutMap.this, "Please select Location to continue.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mUpdateAddresstDialog.show();
    }

    private String getSelectedTypeId(int selectedItemPosition) {
        for (int count = 0; count < addresstypes.size(); count++) {
            if (count == selectedItemPosition) {
                return addresstypes.get(count).getType_id();
            }
        }

        return "0";
    }

    private int getSelectedPosition() {
        for (int count = 0; count < addresstypes.size(); count++) {
            if (addresstypes.get(count).getType_id().equals(mResponse.getData().getList().getAddress_type_id())) {
                return count;
            }

        }
        return 0;
    }

    View.OnClickListener addressTypeListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.llHome) {
                llOtherDetails.setVisibility(View.GONE);
            } else if (view.getId() == R.id.llWork) {
                llOtherDetails.setVisibility(View.GONE);
            } else if (view.getId() == R.id.llOther) {
                llOtherDetails.setVisibility(View.VISIBLE);
            }
        }
    };

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }

    public void onEvent(final DummyEvent ev) {
        runOnUiThread(new Runnable() {
            public void run() {

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        mCurrentLatLon = new LatLng(Double.parseDouble(mResponse.getData().getList().getCustomer_latitude()), Double.parseDouble(mResponse.getData().getList().getCustomer_longitude()));
        mFinalLatitude = Double.parseDouble(mResponse.getData().getList().getCustomer_latitude());
        mFinalLongitude = Double.parseDouble(mResponse.getData().getList().getCustomer_longitude());
        if (ActivityCompat.checkSelfPermission(UserAddressEditWithoutMap.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(UserAddressEditWithoutMap.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mCurrentLatLon, 16));


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }


        mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                mFinalLatitude = cameraPosition.target.latitude;
                mFinalLongitude = cameraPosition.target.longitude;
                mCurrentLatLon = new LatLng(mFinalLatitude, mFinalLongitude);

                Geocoder geocoder;
                List<Address> addresses;
                geocoder = new Geocoder(getBaseContext(), Locale.getDefault());
                try {
                    addresses = geocoder.getFromLocation(mFinalLatitude, mFinalLongitude, 1);
                    if (addresses.size() == 0) {
                        mCurrentLatLon = new LatLng(mGPSTracker.getLatitude(), mGPSTracker.getLongitude());
                        mFinalLatitude = mGPSTracker.getLatitude();
                        mFinalLongitude = mGPSTracker.getLongitude();
                        if (ActivityCompat.checkSelfPermission(UserAddressEditWithoutMap.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(UserAddressEditWithoutMap.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        mMap.setMyLocationEnabled(true);
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mCurrentLatLon, 16));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });
    }

    private void OpenAreaDialog() {
        if (tvCityName.getTag() != null) {
            String cityKey = ((DAOCity.City_list) tvCityName.getTag()).getCity_key();
            AlertDialog.Builder mDialog = new AlertDialog.Builder(UserAddressEditWithoutMap.this, R.style.MyAlertDialogStyle);
            final View vDialog = getLayoutInflater().inflate(R.layout.dialog_city_area, null);
            mDialog.setView(vDialog);

            List<DAOArea.Area_list> areaLists = new ArrayList<>();
            areaAdapter = new AreaAdapter(UserAddressEditWithoutMap.this, bus, areaLists);

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
                                areaAdapter = new AreaAdapter(UserAddressEditWithoutMap.this, bus, areaListsFromAPI);
                                lvArea.setAdapter(areaAdapter);
                                areaAdapter.notifyChanges();

                                tvWait.setVisibility(View.GONE);
                                lvArea.setVisibility(View.VISIBLE);
                                llSearch.setVisibility(View.VISIBLE);
                            } else {
                                Toast.makeText(UserAddressEditWithoutMap.this, area.getMessage(), Toast.LENGTH_SHORT).show();

                                if (mAlertDilaog.isShowing()) {
                                    mAlertDilaog.dismiss();
                                }
                            }
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            Toast.makeText(UserAddressEditWithoutMap.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                            if (mAlertDilaog.isShowing()) {
                                mAlertDilaog.dismiss();
                            }
                        }
                    });

        }
    }

    private class CustomAdapter extends BaseAdapter {
        private final UserAddressEditWithoutMap activity;
        private final List<DAOAddressTypeList.Address_list> list;
        private LayoutInflater infl;

        public CustomAdapter(UserAddressEditWithoutMap markAddressWithoutMapActivity, List<DAOAddressTypeList.Address_list> addresstypes) {
            this.activity = markAddressWithoutMapActivity;
            this.list = addresstypes;
            infl = (LayoutInflater) activity.getSystemService(LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            convertView = infl.inflate(R.layout.simple_spinner, null);
            TextView textview = (TextView) convertView.findViewById(R.id.SS_spinner);
            textview.setText(list.get(position).getType_name());
            return convertView;
        }
    }

}
