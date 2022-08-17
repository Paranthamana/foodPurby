package com.foodpurby.map;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.foodpurby.model.DAOAddress;
import com.foodpurby.util.CustomProgressDialog;
import com.foodpurby.util.GPSTracker;
import com.foodpurby.utillities.UserDetails;
import com.foodpurby.R;

import java.util.List;
import java.util.Locale;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MarkAddressActivity extends Activity implements
        OnMapReadyCallback, ConnectionCallbacks, OnConnectionFailedListener, GoogleMap.OnMarkerDragListener,
        LocationListener, GoogleMap.OnCameraChangeListener {

    private CustomProgressDialog mCustomProgressDialog;
    private GoogleMap mGoogleMap;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private Location mLastLocation;
    private Button mCancelButton;
    static Double lat, lon;
    GPSTracker mGpsTracker;
    private Button mShareButton, btnSave;
    private RelativeLayout mSnackbar;
    MapFragment fragment;
    String mBitmapToString;
    ImageView mImgMarker;
    LinearLayout mButtonBar;
    LinearLayout llMap;
    LinearLayout llAddrDetails;
    Toolbar mToolbar;

    EditText etAppartmentName;
    EditText etLandmark;
    EditText etDeliveryInstructions;

    private LinearLayout llHome;
    private LinearLayout llWork;
    private LinearLayout llOther;
    private LinearLayout llOtherDetails;
    private TextView tvAddress;
    Address address = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_mark_address);
        mCustomProgressDialog = CustomProgressDialog.getInstance();

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
        }

        fragment = (MapFragment) getFragmentManager().findFragmentById(
                R.id.map_fragment);
        fragment.getMapAsync(this);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API).addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build();
        mCancelButton = (Button) findViewById(R.id.cancel_button);

        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
        mShareButton = (Button) findViewById(R.id.share_button);
        btnSave = (Button) findViewById(R.id.btnSave);

        tvAddress = (TextView) findViewById(R.id.tvAddress);
        mImgMarker = (ImageView) findViewById(R.id.imgMarker);
        mButtonBar = (LinearLayout) findViewById(R.id.button_bar);

        llMap = (LinearLayout) findViewById(R.id.llMap);
        llAddrDetails = (LinearLayout) findViewById(R.id.llAddrDetails);

        etAppartmentName = (EditText) findViewById(R.id.etAppartmentName);
        etLandmark = (EditText) findViewById(R.id.etLandmark);
        etDeliveryInstructions = (EditText) findViewById(R.id.etDeliveryInstructions);

        llHome = (LinearLayout) findViewById(R.id.llHome);
        llOther = (LinearLayout) findViewById(R.id.llOther);
        llWork = (LinearLayout) findViewById(R.id.llWork);

        llHome.setOnClickListener(addressTypeListener);
        llOther.setOnClickListener(addressTypeListener);
        llWork.setOnClickListener(addressTypeListener);

        llOtherDetails = (LinearLayout) findViewById(R.id.llOtherDetails);

        mShareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mLastLocation != null) {

                    llMap.setVisibility(View.GONE);
                    llAddrDetails.setVisibility(View.VISIBLE);

                    LatLng latLng = mGoogleMap.getCameraPosition().target;
                    double lati = latLng.latitude;
                    double longi = latLng.longitude;

                    try {
                        Geocoder mGeocoder = new Geocoder(MarkAddressActivity.this, Locale.getDefault());
                        List<Address> mFullAddress = mGeocoder.getFromLocation(lati, longi, 1);
                        if (mFullAddress.size() > 0) {
                            address = mFullAddress.get(0);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();

                    }

                    if (address != null) {
                        String addressLines = "";
                        for (int Count = 0; Count < address.getMaxAddressLineIndex(); Count++) {
                            if (addressLines.trim().isEmpty()) {
                                addressLines = address.getAddressLine(Count);
                            } else {
                                addressLines = addressLines + ", " + address.getAddressLine(Count);
                            }
                        }

						/*addressIs = address.getFeatureName() + ", " + addressLines + ", " + address.getSubAdminArea() + ", " +
                                address.getAdminArea() + ", " + address.getCountryName() + " - " + address.getPostalCode();*/

                        tvAddress.setText(addressLines);
                    }
                }
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!mCustomProgressDialog.isShowing()) {
                    mCustomProgressDialog.show(MarkAddressActivity.this);
                }

                DAOAddress.getInstance().AddCallresponse("",
                        "cityKey", "areaKey", address.getFeatureName(), "", "",
                        address.getPostalCode(), etLandmark.getText().toString(), "", etDeliveryInstructions.getText().toString(),
                        UserDetails.getCustomerKey(), "" , "", new Callback<DAOAddress.Address>() {

                            @Override
                            public void success(DAOAddress.Address address, Response response) {
                                if (address.getHttpcode().equals("200")) {
                                    Toast.makeText(MarkAddressActivity.this, address.getMessage(), Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(MarkAddressActivity.this, address.getMessage(), Toast.LENGTH_SHORT).show();
                                }

                                if (mCustomProgressDialog.isShowing()) {
                                    mCustomProgressDialog.dismiss();
                                }
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                Toast.makeText(MarkAddressActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                                if (mCustomProgressDialog.isShowing()) {
                                    mCustomProgressDialog.dismiss();
                                }
                            }
                        }

                );
            }
        });


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
        this.mLastLocation = null;

        mShareButton.setEnabled(false);
        mShareButton.setTextColor(0x8a000000);
        mShareButton.setText("Locating");
        mGoogleApiClient.connect();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        mGoogleApiClient.disconnect();
        super.onPause();
    }


    @Override
    public void onMarkerDrag(Marker marker) {

    }


    @Override
    public void onMarkerDragEnd(Marker marker) {
    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // TODO Auto-generated method stub
        this.mGoogleMap = googleMap;
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
        this.mGoogleMap.setMyLocationEnabled(true);

    }

    @Override
    public void onConnected(Bundle bundle) {
        // TODO Auto-generated method stub
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(1000);
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
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

    }


    public void dropPinEffect(final Marker marker) {
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        final long duration = 1500;

        final Interpolator interpolator = new BounceInterpolator();

        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = Math.max(
                        1 - interpolator.getInterpolation((float) elapsed
                                / duration), 0);
                marker.setAnchor(0.5f, 1.0f + 14 * t);

                if (t > 0.0) {
                    // Post again 15ms later.
                    handler.postDelayed(this, 15);
                } else {
                    marker.showInfoWindow();

                }
            }
        });
    }

    private void setMarkerBounce(final Marker marker) {
        final Handler handler = new Handler();
        final long startTime = SystemClock.uptimeMillis();
        final long duration = 2000;
        final Interpolator interpolator = new BounceInterpolator();
        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - startTime;
                float t = Math.max(1 - interpolator.getInterpolation((float) elapsed / duration), 0);
                marker.setAnchor(0.5f, 1.0f + t);

                if (t > 0.0) {
                    handler.postDelayed(this, 16);
                } else {
                    setMarkerBounce(marker);
                }
            }
        });
    }

    @Override
    public void onConnectionSuspended(int arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onConnectionFailed(ConnectionResult arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onLocationChanged(Location location) {
        // TODO Auto-generated method stub
        if (this.mLastLocation == null) {

            mImgMarker.setPadding(0, 0, 0, mButtonBar.getHeight() + mImgMarker.getHeight());

            this.mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(location.getLatitude(), location.getLongitude()), 15));
            this.mShareButton.setEnabled(true);
            this.mShareButton.setTextColor(0xde000000);
            this.mShareButton.setText("Mark my address");
        }

        this.mLastLocation = location;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private boolean isLocationEnabledKitkat() {
        try {
            int locationMode = Settings.Secure.getInt(getContentResolver(),
                    Settings.Secure.LOCATION_MODE);
            return locationMode != Settings.Secure.LOCATION_MODE_OFF;
        } catch (Settings.SettingNotFoundException e) {
            return false;
        }
    }

    @SuppressWarnings("deprecation")
    private boolean isLocationEnabledLegacy() {
        String locationProviders = Settings.Secure.getString(
                getContentResolver(),
                Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        return !TextUtils.isEmpty(locationProviders);
    }

    private boolean isLocationEnabled() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return isLocationEnabledKitkat();
        } else {
            return isLocationEnabledLegacy();
        }
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        if (cameraPosition != null) {
            CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        }
    }
}
