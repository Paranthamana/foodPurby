package com.foodpurby.demo;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sloop.fonts.FontsManager;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.foodpurby.adapters.RestaurantNamesMapAdapter;
import com.foodpurby.events.DummyEvent;
import com.foodpurby.ondbstorage.DBActionsRestaurantBranch;
import com.foodpurby.ondbstorage.RestaurantBranch;
import com.foodpurby.util.CustomProgressDialog;
import com.foodpurby.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import de.greenrobot.event.EventBus;

public class MainMap extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, GoogleMap.OnMarkerDragListener,
        LocationListener, GoogleMap.OnCameraChangeListener {

    final Random r = new Random();
    private EventBus bus = EventBus.getDefault();
    private RestaurantNamesMapAdapter aRestaurantNamesMapAdapter;
    ListView mRestaurantNameLists;
    private CustomProgressDialog mCustomProgressDialog;

    private GoogleMap mGoogleMap;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private Toolbar mToolbar;

    private MapFragment fragment;
    ImageButton ibFavourite;
    ImageButton btnShowHide;
    SlidingUpPanelLayout mLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_map);

      //  FontsManager.initFormAssets(this, "Lato-Light.ttf");
      //  FontsManager.changeFonts(this);
        //getSupportActionBar().hide();
        EventBus.getDefault().register(this);
        mCustomProgressDialog = new CustomProgressDialog();

        mToolbar = (Toolbar) findViewById(R.id.tb_header);
        mToolbar.setNavigationIcon(R.drawable.back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //mToolbar.getBackground().setAlpha(0);

        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        mRestaurantNameLists = (ListView) findViewById(R.id.mRestaurantNameLists);
        aRestaurantNamesMapAdapter = new RestaurantNamesMapAdapter(MainMap.this, bus, mCustomProgressDialog, fragmentManager);
        mRestaurantNameLists.setAdapter(aRestaurantNamesMapAdapter);


        fragment = (MapFragment) getFragmentManager().findFragmentById(
                R.id.map_fragment);
        fragment.getMapAsync(this);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API).addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build();




        List<String> your_array_list = Arrays.asList(
                "This",
                "Is",
                "An",
                "Example",
                "ListView",
                "That",
                "You",
                "Can",
                "Scroll",
                ".",
                "It",
                "Shows",
                "How",
                "Any",
                "Scrollable",
                "View",
                "Can",
                "Be",
                "Included",
                "As",
                "A",
                "Child",
                "Of",
                "SlidingUpPanelLayout"
        );

        // This is the array adapter, it takes the context of the activity as a
        // first parameter, the type of list view as a second parameter and your
        // array as a third parameter.
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                your_array_list );

        //lv.setAdapter(arrayAdapter);

        RadioButton radioPopular = (RadioButton) findViewById(R.id.radioPopular);
        radioPopular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
            }
        });
        mLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        mLayout.setTouchEnabled(false);
        mLayout.setPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                //Log.i(TAG, "onPanelSlide, offset " + slideOffset);
            }

            @Override
            public void onPanelExpanded(View panel) {
                //Log.i(TAG, "onPanelExpanded");

            }

            @Override
            public void onPanelCollapsed(View panel) {
                //Log.i(TAG, "onPanelCollapsed");

            }

            @Override
            public void onPanelAnchored(View panel) {
                //Log.i(TAG, "onPanelAnchored");
            }

            @Override
            public void onPanelHidden(View panel) {
                //Log.i(TAG, "onPanelHidden");
            }
        });

        TextView t = (TextView) findViewById(R.id.name);
        t.setText("hello");


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int height = dm.heightPixels;

        LinearLayout dragView = (LinearLayout) findViewById(R.id.dragView);
        dragView.getLayoutParams().height = (height / 2);

        mLayout.setOverlayed(true);

        ibFavourite = (ImageButton) findViewById(R.id.ibFavourite);
        ibFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(!v.isSelected());

                if(v.isSelected()) {
                    ((ImageButton)v).setImageResource(R.drawable.ic_list_fav_selected);
                }
                else {
                    ((ImageButton)v).setImageResource(R.drawable.ic_list_fav_normal);
                }
            }
        });

        btnShowHide = (ImageButton) findViewById(R.id.btnShowHide);
        btnShowHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToggleArrow(v);
            }
        });
    }

    private void ToggleArrow(View v) {
        v.setSelected(!v.isSelected());

        if(v.isSelected()) {
            mLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);

            Resources res = getResources();
            int resourceId = res.getIdentifier("arrow_down", "drawable", getPackageName());
            btnShowHide.setImageResource(resourceId);

        }
        else {
            mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);

            Resources res = getResources();
            int resourceId = res.getIdentifier("arrow_up", "drawable", getPackageName() );
            btnShowHide.setImageResource(resourceId);
            //btnShowHide.setImageDrawable(getDrawable(R.drawable.arrow_up));
        }

    }
    private void addMarkersToMap() {

        mGoogleMap.clear();

        Bitmap.Config conf = Bitmap.Config.ARGB_8888;
        Bitmap bmp = Bitmap.createBitmap(80, 80, conf);
        Canvas canvas1 = new Canvas(bmp);

        // paint defines the text color,
        // stroke width, size
        Paint color = new Paint();
        color.setTextSize(35);
        color.setColor(Color.BLACK);

        //modify canvas
        canvas1.drawBitmap(BitmapFactory.decodeResource(getResources(),
                R.drawable.pin_restaurant), 0, 0, color);
        //canvas1.drawText("User Name!", 30, 40, color);

        List<RestaurantBranch> restaurants = DBActionsRestaurantBranch.get();

        Integer count = 0;

        ArrayList<Marker> mMarkers = new ArrayList<>();
        mGoogleMap.clear();
        for (RestaurantBranch restaurantBranch : restaurants) {
            LatLng ll = new LatLng(restaurantBranch.getRestaurantLatitude() + (r.nextInt(10) * 0.06), restaurantBranch.getRestaurantLongitude() + (r.nextInt(10) * 0.06));

            if(!restaurantBranch.getRestaurantFavouriteStatus()) {
                Marker marker = mGoogleMap.addMarker(new MarkerOptions().position(ll).title(restaurantBranch.getRestaurantBranchName())
                        .snippet("Test").icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_restaurant)));
                dropPinEffect(marker);
                mMarkers.add(marker);
            }
            else {

                BitmapDescriptor bitmapMarker = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);
                Marker marker = mGoogleMap.addMarker(new MarkerOptions().position(ll).title(restaurantBranch.getRestaurantBranchName())
                        .snippet("Test").icon(bitmapMarker));
                dropPinEffect(marker);
                mMarkers.add(marker);
            }

            count++;

            if(count > 20)
            {
                break;
            }
        }
    }

    private void dropPinEffect(final Marker marker) {
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.demo, menu);
        MenuItem item = menu.findItem(R.id.action_toggle);
        if (mLayout != null) {
            if (mLayout.getPanelState() == SlidingUpPanelLayout.PanelState.HIDDEN) {
                item.setTitle("Show");
            } else {
                item.setTitle("Hide");
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_toggle: {
                if (mLayout != null) {
                    if (mLayout.getPanelState() != SlidingUpPanelLayout.PanelState.HIDDEN) {
                        //mLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
                        item.setTitle("Show");
                        mLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
                    } else {
                        mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                        item.setTitle("Hide");
                    }
                }
                return true;
            }
            case R.id.action_anchor: {
                if (mLayout != null) {
                    if (mLayout.getAnchorPoint() == 1.0f) {
                        mLayout.setAnchorPoint(0.4f);
                        mLayout.setPanelState(SlidingUpPanelLayout.PanelState.ANCHORED);
                        item.setTitle("Disable");
                    } else {
                        mLayout.setAnchorPoint(1.0f);
                        mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                        item.setTitle("Enable");
                    }
                }
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {

        //TextView locationTv = (TextView) findViewById(R.id.latlongLocation);

        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        LatLng latLng = new LatLng(latitude, longitude);
        mGoogleMap.addMarker(new MarkerOptions().position(latLng));
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        //locationTv.setText("Latitude:" + latitude + ", Longitude:" + longitude);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // TODO Auto-generated method stub
        this.mGoogleMap = googleMap;
        this.mGoogleMap.setMyLocationEnabled(true);

        // Get the button view
        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
        mGoogleMap.getUiSettings().setRotateGesturesEnabled(true);


        mGoogleMap.setMyLocationEnabled(true);
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, true);
        Location location = locationManager.getLastKnownLocation(bestProvider);
        if (location != null) {
            onLocationChanged(location);
        }
        locationManager.requestLocationUpdates(bestProvider, 20000, 0, this);

        addMarkersToMap();






        if (location != null)
        {
            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(location.getLatitude(), location.getLongitude()), 13));

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
                    .zoom(10)                   // Sets the zoom
                    //.bearing(90)                // Sets the orientation of the camera to east
                    .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                    .build();                   // Creates a CameraPosition from the builder
            mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        // TODO Auto-generated method stub
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(1000);
        //LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {

    }

    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        mGoogleApiClient.disconnect();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void onEvent(final DummyEvent ev) {
    }
}
