package com.foodpurby.map;

import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.foodpurby.R;
import com.foodpurby.adapters.AddressTypeAdapter;
import com.foodpurby.adapters.AreaAdapter;
import com.foodpurby.adapters.CityAdapter;
import com.foodpurby.events.AddressEvent;
import com.foodpurby.model.DAOAddress;
import com.foodpurby.model.DAOAddressTypeList;
import com.foodpurby.model.DAOArea;
import com.foodpurby.model.DAOCity;
import com.foodpurby.ondbstorage.AppSettings;
import com.foodpurby.ondbstorage.DBActionsAppSettings;
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
import com.sloop.fonts.FontsManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MarkAddressWithoutMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private CustomProgressDialog mCustomProgressDialog;
    private EventBus bus = EventBus.getDefault();

    private AddressType SelectedAddressType = AddressType.None;
    private GoogleMap mMap;
    private GoogleApiClient client;
    private GPSTracker mGPSTracker;
    private LatLng mCurrentLatLon;
    private double mFinalLatitude;
    private double mFinalLongitude;
    private TextView vSaveButton;
    private String mStatus;
    private List<DAOAddress.Address_list> addresstype;
    private Spinner spinner;
    private List<DAOAddressTypeList.Address_list> addresstypes;


    private enum AddressType {
        None,
        Home,
        Work,
        Other
    }

    private List<DAOCity.City_list> cityListsFromAPI = new ArrayList<>();
    private List<DAOArea.Area_list> areaListsFromAPI = new ArrayList<>();
    private List<DAOAddressTypeList.Address_list> addressListApi = new ArrayList<>();


    private Button btnSave;
    private RelativeLayout mSnackbar;

    String mBitmapToString;
    ImageView mImgMarker;
    LinearLayout mButtonBar;
    LinearLayout llMap;
    LinearLayout llAddrDetails;
    Toolbar mToolbar;


    ImageView ivHome;
    ImageView ivWork;
    ImageView ivOther;


    EditText tvCityName;
    EditText tvAreaName;

    EditText etCompany;
    EditText etFlatNo;
    EditText etLocationName;
    EditText etStreet;
    EditText etLandmark;
    EditText etDeliveryInstructions;

    AreaAdapter areaAdapter;
    AddressTypeAdapter mAddressTypeAdapter;
    ListView lvArea;
    LinearLayout llSearch;
    EditText etSearch;
    CityAdapter cityAdapter;
    ListView lvAddressType;
    ListView lvCities;
    TextView tvWait;

    private LinearLayout llHome;
    private LinearLayout llWork;
    private LinearLayout llOther;
    private LinearLayout llOtherDetails;
    private TextView tvAddress;
    Address address = null;
    TextView mToolHeading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        mStatus = getIntent().getStringExtra("status");
     //   FontsManager.initFormAssets(MarkAddressWithoutMapActivity.this, "Lato-Light.ttf");
     //   FontsManager.changeFonts(this);
        initView();
        mCustomProgressDialog = CustomProgressDialog.getInstance();
        mToolbar = (Toolbar) findViewById(R.id.tb_header);
        if (mToolbar != null) {
            mToolbar.setNavigationIcon(ContextCompat.getDrawable(
                    getApplicationContext(), R.drawable.back));
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            mToolHeading = (TextView) findViewById(R.id.tool_title);
            if (mStatus.equals("add")) {
                mToolHeading.setText(R.string.txt_add_address);
            } else {
                mToolHeading.setText("Edit Address");
            }

        }
        vSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomProgressDialog.getInstance().show(MarkAddressWithoutMapActivity.this);
                DAOAddressTypeList.getInstance().AddTypeCallresponse("",
                        UserDetails.getCustomerKey(),
                        AppSharedValues.getLanguage(),
                        new Callback<DAOAddressTypeList.AddressType>() {
                            @Override
                            public void success(DAOAddressTypeList.AddressType addressType, Response response) {
                                CustomProgressDialog.getInstance().dismiss();
                                if (addressType.getHttpcode().equals("200")) {
                                    if (addressType.getData().getAddress_list() != null) {
                                        addresstypes = addressType.getData().getAddress_list();
                                        callAddAddressDialog();
                                    }
                                } else {
                                    Toast.makeText(MarkAddressWithoutMapActivity.this, addressType.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                error.printStackTrace();
                                Toast.makeText(MarkAddressWithoutMapActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                                CustomProgressDialog.getInstance().dismiss();
                            }
                        });
            }
        });


        mGPSTracker = new GPSTracker(MarkAddressWithoutMapActivity.this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();


    }

    private void callAddAddressDialog() {
        final Dialog mAddAddresstDialog = new Dialog(MarkAddressWithoutMapActivity.this);
        mAddAddresstDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mAddAddresstDialog.setCancelable(true);

        LayoutInflater li = LayoutInflater.from(this);
        final View myView = li.inflate(R.layout.activity_map_mark_address_without_map, null);

        mAddAddresstDialog.setContentView(myView);
        mAddAddresstDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


     //   FontsManager.initFormAssets(MarkAddressWithoutMapActivity.this, "Lato-Light.ttf");
     //   FontsManager.changeFonts(myView);


//        tvAddressType = (TextView) mAddAddresstDialog.findViewById(R.id.tvAddressType);
        tvCityName = (EditText) mAddAddresstDialog.findViewById(R.id.tvCityName);
        tvAreaName = (EditText) mAddAddresstDialog.findViewById(R.id.tvAreaName);
        etStreet = (EditText) mAddAddresstDialog.findViewById(R.id.etStreet);
        etCompany = (EditText) mAddAddresstDialog.findViewById(R.id.etcompanyName);
        etLocationName = (EditText) mAddAddresstDialog.findViewById(R.id.etLocationName);
        etFlatNo = (EditText) mAddAddresstDialog.findViewById(R.id.etFlatNo);
        etLandmark = (EditText) mAddAddresstDialog.findViewById(R.id.etLandmark);
        etDeliveryInstructions = (EditText) mAddAddresstDialog.findViewById(R.id.etDeliveryInstructions);
        btnSave = (Button) mAddAddresstDialog.findViewById(R.id.btnSave);
        TextView tvCancel = (TextView) mAddAddresstDialog.findViewById(R.id.tvCancelss);

        spinner = (Spinner) mAddAddresstDialog.findViewById(R.id.AM_spinner);


        CustomAdapter customAdapter = new CustomAdapter(MarkAddressWithoutMapActivity.this, addresstypes);
//        ArrayAdapter<DAOAddressTypeList.Address_list> adapter = new ArrayAdapter<DAOAddressTypeList.Address_list>(getApplicationContext(), android.R.layout.simple_spinner_item, addresstypes);
//        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(customAdapter);


        if (mStatus.equals("add")) {
            btnSave.setText("Save Address");
        } else {
            btnSave.setText("Update Address");
        }
//        tvCityName.setText(currentLocation.getData().getGet_location().getCity_name());
//        tvAreaName.setText(currentLocation.getData().getGet_location().getArea_name());
//        tvCityName.setTag(currentLocation.getData().getGet_location().getCity_key());
//        tvAreaName.setTag(currentLocation.getData().getGet_location().getArea_key());

        final LatLng center = mMap.getCameraPosition().target;

        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... args) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        String mBuildings = "", mAddress = "", mLandmark = "", mPostal = "";
                        String mBuilding = "", mCountry = "", mState = "", mCity = "", mArea = "";
                        if (center.latitude == 0.0 || center.longitude == 0.0) {
                            mBuilding = "";
                            mAddress = "";
                            mLandmark = "";
                            mCity = "";
                            mPostal = "";
                            mState = "";
                            mArea = "";
                        } else {
                            try {
                                Geocoder geocoder;
                                List<Address> addresses;
                                geocoder = new Geocoder(getBaseContext(), Locale.getDefault());
                                addresses = geocoder.getFromLocation(center.latitude, center.longitude, 1);

                                if (addresses.size() != 0) {
                                    if (addresses.get(0).getAdminArea() != null) {
                                        mState = addresses.get(0).getAdminArea();
                                    }
                                    if (addresses.get(0).getAddressLine(1) != null) {
                                        mAddress = addresses.get(0).getAddressLine(1);
//                        etFlatNo.setText(mAddress);
                                    }
                                    if (addresses.get(0).getLocality() != null) {
                                        mCity = addresses.get(0).getLocality();
                                        //tvCityName.setText(mCity);
                                    }
                                    if (addresses.get(0).getAdminArea() != null) {
                                        mArea = addresses.get(0).getAdminArea();
                                        //etLandmark.setText(mArea);
                                    }
                                    if (addresses.get(0).getPostalCode() != null) {
                                        mPostal = addresses.get(0).getPostalCode();
                                    }

//                    mMainBuilding = mState + "," + mBuilding;


                                    if (addresses.get(0).getSubLocality() != null) {
                                        //tvAreaName.setText(addresses.get(0).getSubLocality());
                                    }
                                    if (addresses.get(0).getAddressLine(0) != null) {
                                        mBuilding = addresses.get(0).getAddressLine(0);
                                        etStreet.setText(mBuilding + "," + mAddress + "," + mCity + "," + mArea);
                                    }
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                });
                return null;
            }

            @Override
            protected void onPostExecute(String url) {

            }
        }.execute();

        tvCityName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //tvCity.setTag(null);
                //tvCityName.setText("");
                List<DAOCity.City_list> cityLists = new ArrayList<DAOCity.City_list>();
                cityAdapter = new CityAdapter(MarkAddressWithoutMapActivity.this, bus, cityLists);
                AlertDialog.Builder mDialog = new AlertDialog.Builder(MarkAddressWithoutMapActivity.this, R.style.MyAlertDialogStyle);
                final View vDialog = MarkAddressWithoutMapActivity.this.getLayoutInflater().inflate(R.layout.dialog_city_area, null);
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
                            cityAdapter = new CityAdapter(MarkAddressWithoutMapActivity.this, bus, cityListsFromAPI);
                            lvCities.setAdapter(cityAdapter);
                            cityAdapter.notifyChanges();

                            tvWait.setVisibility(View.GONE);
                            lvCities.setVisibility(View.VISIBLE);
                            llSearch.setVisibility(View.VISIBLE);
                        } else {
                            Toast.makeText(MarkAddressWithoutMapActivity.this, city.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(MarkAddressWithoutMapActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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


        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAddAddresstDialog.dismiss();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*if (tvCityName.getText().toString() != null && !tvCityName.getText().toString().isEmpty() && tvAreaName.getText().toString() != null && !tvAreaName.getText().toString().isEmpty()) {
                  *//*  if (etCompany.getText().toString().trim().isEmpty()) {
                        Toast.makeText(MarkAddressWithoutMapActivity.this, "Please enter the estate name.", Toast.LENGTH_SHORT).show();
                        etCompany.requestFocus();
                    } else *//*
                    if (etFlatNo.getText().toString() != null && etFlatNo.getText().toString().trim().isEmpty()) {
                        Toast.makeText(MarkAddressWithoutMapActivity.this, "Please enter the Address1 name.", Toast.LENGTH_SHORT).show();
                        etFlatNo.requestFocus();
                    } else {

                    }
                } else if (tvCityName.getText().toString() == null || tvCityName.getText().toString().isEmpty()) {
                    Toast.makeText(MarkAddressWithoutMapActivity.this, "Please select State to continue.", Toast.LENGTH_SHORT).show();
                } else if (tvAreaName.getText().toString() == null || tvAreaName.getText().toString().isEmpty()) {
                    Toast.makeText(MarkAddressWithoutMapActivity.this, "Please select Location to continue.", Toast.LENGTH_SHORT).show();
                }*//* else if (tvAddressType.getTag() == null) {
                    Toast.makeText(MarkAddressWithoutMapActivity.this, "Please select Address type to continue.", Toast.LENGTH_SHORT).show();
                }*/
                if (spinner.getSelectedItemPosition() != -1) {


                    if ((addresstypes.size() >= spinner.getSelectedItemPosition() &&
                            addresstypes.get(spinner.getSelectedItemPosition()).getType_id() != null)) {
                        if (!mCustomProgressDialog.isShowing()) {
                            mCustomProgressDialog.show(MarkAddressWithoutMapActivity.this);
                        }

//                        DAOAddressTypeList.Address_list addressList = (DAOAddressTypeList.Address_list) tvAddressType.getTag();
//                        DAOCity.City_list city = (DAOCity.City_list) tvCityName.getTag();
//                        DAOArea.Area_list area = (DAOArea.Area_list) tvAreaName.getTag();

                        LatLng center = mMap.getCameraPosition().target;

                        DAOAddress.getInstance().AddCallresponse("" + addresstypes.get(spinner.getSelectedItemPosition()).getType_id(),
                                tvCityName.getText().toString(), tvAreaName.getText().toString(), etStreet.getText().toString(), etFlatNo.getText().toString(),
                                etCompany.getText().toString(), "", etLandmark.getText().toString(), "", etDeliveryInstructions.getText().toString(),
                                UserDetails.getCustomerKey(), center.latitude + "", center.longitude + "", new Callback<DAOAddress.Address>() {

                                    @Override
                                    public void success(DAOAddress.Address address, Response response) {
                                        if (address.getHttpcode().equals("200")) {
                                            bus.post(new AddressEvent());

                                            addresstype = address.getData().getAddress_list();
                                            mAddAddresstDialog.dismiss();
                                            finish();
                                            Toast.makeText(MarkAddressWithoutMapActivity.this, address.getMessage(), Toast.LENGTH_SHORT).show();

                                        } else {
                                            Toast.makeText(MarkAddressWithoutMapActivity.this, address.getMessage(), Toast.LENGTH_SHORT).show();
                                        }

                                        if (mCustomProgressDialog.isShowing()) {
                                            mCustomProgressDialog.dismiss();
                                        }
                                    }

                                    @Override
                                    public void failure(RetrofitError error) {
                                        Toast.makeText(MarkAddressWithoutMapActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                                        if (mCustomProgressDialog.isShowing()) {
                                            mCustomProgressDialog.dismiss();
                                        }
                                    }
                                }

                        );
                    } else {
                        Toast.makeText(MarkAddressWithoutMapActivity.this, "Select Address Type", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MarkAddressWithoutMapActivity.this, "Select Address Type", Toast.LENGTH_SHORT).show();
                }
            }/**/
        });


        AppSettings cityArea = DBActionsAppSettings.get();

        DBActionsAppSettings.get();


        mAddAddresstDialog.show();

    }

    private void OpenAddressTypeDialog() {
        AlertDialog.Builder mDialog = new AlertDialog.Builder(MarkAddressWithoutMapActivity.this, R.style.MyAlertDialogStyle);
        final View vDialog = getLayoutInflater().inflate(R.layout.dialog_city_area, null);
        mDialog.setView(vDialog);

        List<DAOAddressTypeList.Address_list> addressTypeLists = new ArrayList<>();
        mAddressTypeAdapter = new AddressTypeAdapter(MarkAddressWithoutMapActivity.this, bus, addressTypeLists);

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

                    List<DAOAddressTypeList.Address_list> addressTypeSearch = new ArrayList<>();

                    for (DAOAddressTypeList.Address_list type : addressListApi) {
                        if (type.getType_name().toLowerCase().contains(etSearch.getText().toString().trim())) {
                            addressTypeSearch.add(type);
                        }
                    }

                    mAddressTypeAdapter.SearchKeyword(addressTypeSearch);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        lvAddressType = (ListView) vDialog.findViewById(R.id.lvCitiesArea);
        tvWait = (TextView) vDialog.findViewById(R.id.tvWait);
        lvAddressType.setAdapter(mAddressTypeAdapter);
        lvAddressType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AddressTypeAdapter.ViewHolder viewHolder = (AddressTypeAdapter.ViewHolder) view.getTag();
                DAOAddressTypeList.Address_list addressType = (DAOAddressTypeList.Address_list) viewHolder.tvName.getTag();
//                tvAddressType.setTag(addressType);
//                tvAddressType.setText(addressType.getType_name());
                if (mAlertDilaog.isShowing()) {
                    mAlertDilaog.dismiss();
                }
            }
        });

        mAlertDilaog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mAlertDilaog.show();

        DAOAddressTypeList.getInstance().AddTypeCallresponse("",
                UserDetails.getCustomerKey(),
                AppSharedValues.getLanguage(),
                new Callback<DAOAddressTypeList.AddressType>() {
                    @Override
                    public void success(DAOAddressTypeList.AddressType addressType, Response response) {

                        if (addressType.getHttpcode().equals("200")) {

                            addresstypes = addressType.getData().getAddress_list();
                            mAddressTypeAdapter = new AddressTypeAdapter(MarkAddressWithoutMapActivity.this, bus, addressListApi);
                            lvAddressType.setAdapter(mAddressTypeAdapter);
                            mAddressTypeAdapter.notifyChanges();

                            tvWait.setVisibility(View.GONE);
                            lvAddressType.setVisibility(View.VISIBLE);
                            llSearch.setVisibility(View.VISIBLE);
                        } else {
                            Toast.makeText(MarkAddressWithoutMapActivity.this, addressType.getMessage(), Toast.LENGTH_SHORT).show();
                            if (mAlertDilaog.isShowing()) {
                                mAlertDilaog.dismiss();
                            }
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        error.printStackTrace();
                        Toast.makeText(MarkAddressWithoutMapActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        if (mAlertDilaog.isShowing()) {
                            mAlertDilaog.dismiss();
                        }
                    }
                });

    }

    private void initView() {
        vSaveButton = (TextView) findViewById(R.id.AM_saveBtn);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mCurrentLatLon = new LatLng(mGPSTracker.getLatitude(), mGPSTracker.getLongitude());
        mFinalLatitude = mGPSTracker.getLatitude();
        mFinalLongitude = mGPSTracker.getLongitude();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
        mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(final CameraPosition cameraPosition) {


                new AsyncTask<String, String, String>() {
                    @Override
                    protected String doInBackground(String... args) {
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {


                            }
                        });
                        return null;
                    }

                    @Override
                    protected void onPostExecute(String url) {

                    }
                }.execute();


            }
        });
    }

    private void OpenAreaDialog() {
        if (tvCityName.getTag() != null) {
            String cityKey = ((DAOCity.City_list) tvCityName.getTag()).getCity_key();
            AlertDialog.Builder mDialog = new AlertDialog.Builder(MarkAddressWithoutMapActivity.this, R.style.MyAlertDialogStyle);
            final View vDialog = getLayoutInflater().inflate(R.layout.dialog_city_area, null);
            mDialog.setView(vDialog);

            List<DAOArea.Area_list> areaLists = new ArrayList<>();
            areaAdapter = new AreaAdapter(MarkAddressWithoutMapActivity.this, bus, areaLists);

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
                                areaAdapter = new AreaAdapter(MarkAddressWithoutMapActivity.this, bus, areaListsFromAPI);
                                lvArea.setAdapter(areaAdapter);
                                areaAdapter.notifyChanges();

                                tvWait.setVisibility(View.GONE);
                                lvArea.setVisibility(View.VISIBLE);
                                llSearch.setVisibility(View.VISIBLE);
                            } else {
                                Toast.makeText(MarkAddressWithoutMapActivity.this, area.getMessage(), Toast.LENGTH_SHORT).show();

                                if (mAlertDilaog.isShowing()) {
                                    mAlertDilaog.dismiss();
                                }
                            }
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            Toast.makeText(MarkAddressWithoutMapActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                            if (mAlertDilaog.isShowing()) {
                                mAlertDilaog.dismiss();
                            }
                        }
                    });

        }
    }


    private class CustomAdapter extends BaseAdapter {
        private final MarkAddressWithoutMapActivity activity;
        private final List<DAOAddressTypeList.Address_list> list;
        private final LayoutInflater infl;

        public CustomAdapter(MarkAddressWithoutMapActivity markAddressWithoutMapActivity, List<DAOAddressTypeList.Address_list> addresstypes) {
            this.activity = markAddressWithoutMapActivity;

            this.list = addresstypes != null ? addresstypes : new ArrayList<DAOAddressTypeList.Address_list>();
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
