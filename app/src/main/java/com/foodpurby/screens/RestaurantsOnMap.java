package com.foodpurby.screens;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
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
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.foodpurby.adapters.RestaurantNamesMapAdapter;
import com.foodpurby.ondbstorage.DBActionsCategory;
import com.foodpurby.ondbstorage.DBActionsGroup;
import com.foodpurby.ondbstorage.DBActionsProducts;
import com.foodpurby.ondbstorage.DBActionsRestaurantBranch;
import com.foodpurby.ondbstorage.RestaurantBranch;
import com.foodpurby.util.CustomProgressDialog;
import com.foodpurby.events.DummyEvent;
import com.foodpurby.events.RestaurantNamesEvent;
import com.foodpurby.model.DAORestaurantBranchItems;
import com.foodpurby.ondbstorage.DBActionsCuisine;
import com.foodpurby.ondbstorage.DBActionsIngredients;
import com.foodpurby.ondbstorage.DBActionsIngredientsCategory;
import com.foodpurby.utillities.AppSharedValues;
import com.foodpurby.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class RestaurantsOnMap extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, GoogleMap.OnMarkerDragListener,
        LocationListener, GoogleMap.OnCameraChangeListener {

    private CustomProgressDialog mCustomProgressDialog;
    final Random r = new Random();
    private EventBus bus = EventBus.getDefault();
    private RestaurantNamesMapAdapter aRestaurantNamesMapAdapter;
    ListView mRestaurantNameLists;
    ArrayList<Marker> mMarkers = new ArrayList<>();

    private GoogleMap mGoogleMap;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private Toolbar mToolbar;

    private SupportMapFragment fragment;
    TextView tvFavourite;
    TextView tvFrequent;
    TextView tvSortBy;
    ImageButton btnShowHide;
    SlidingUpPanelLayout mLayout;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Restaurants.
     */
    // TODO: Rename and change types and number of parameters
    public static RestaurantsOnMap newInstance(String param1, String param2) {
        RestaurantsOnMap fragment = new RestaurantsOnMap();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public RestaurantsOnMap() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vRestaurantHome = inflater.inflate(R.layout.activity_main_map_fragment, container, false);
        mCustomProgressDialog = new CustomProgressDialog();

        FragmentManager fragmentManager = getFragmentManager();



        fragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(
                R.id.map_fragment);
        fragment.getMapAsync(this);

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(LocationServices.API).addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build();


        RadioButton radioPopular = (RadioButton) vRestaurantHome.findViewById(R.id.radioPopular);
        radioPopular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
            }
        });
        mLayout = (SlidingUpPanelLayout) vRestaurantHome.findViewById(R.id.sliding_layout);
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

        TextView t = (TextView) vRestaurantHome.findViewById(R.id.name);
        t.setText("hello");



        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int height = dm.heightPixels;

        LinearLayout dragView = (LinearLayout) vRestaurantHome.findViewById(R.id.dragView);
        dragView.getLayoutParams().height = (height / 2);

        mLayout.setOverlayed(true);

        tvSortBy = (TextView) vRestaurantHome.findViewById(R.id.tvSortBy);


        tvFavourite = (TextView) vRestaurantHome.findViewById(R.id.tvFavourite);
        tvFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(!v.isSelected());
                AppSharedValues.setIsShowFavouriteResturantOnly(v.isSelected());
                if(v.isSelected()) {
                    ((TextView)v).setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_list_fav_selected, 0, 0);
                }
                else {
                    ((TextView)v).setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_list_fav_normal, 0, 0);
                }

                addMarkersToMap();
                aRestaurantNamesMapAdapter.notifyChanges();
            }
        });

        tvFrequent = (TextView) vRestaurantHome.findViewById(R.id.tvFrequent);
        tvFrequent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(!v.isSelected());
                AppSharedValues.setIsShowFavouriteResturantOnly(v.isSelected());
                if(v.isSelected()) {
                    ((TextView)v).setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_green, 0, 0);
                }
                else {
                    ((TextView)v).setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_red, 0, 0);
                }

                addMarkersToMap();
                aRestaurantNamesMapAdapter.notifyChanges();
            }
        });

        btnShowHide = (ImageButton) vRestaurantHome.findViewById(R.id.btnShowHide);
        btnShowHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToggleArrow(v);
            }
        });

        if(AppSharedValues.isShowFavouriteResturantOnly()) {
            tvFavourite.setSelected(AppSharedValues.isPureVeg());
            tvFavourite.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_list_fav_selected, 0, 0);
        }
        else {
            tvFavourite.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_list_fav_normal, 0, 0);
        }







        mRestaurantNameLists = (ListView) vRestaurantHome.findViewById(R.id.mRestaurantNameLists);
        aRestaurantNamesMapAdapter = new RestaurantNamesMapAdapter(getActivity(), bus, mCustomProgressDialog, fragmentManager);
        mRestaurantNameLists.setAdapter(aRestaurantNamesMapAdapter);
        mRestaurantNameLists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                try {
                    if (!mCustomProgressDialog.isShowing()) {
                        mCustomProgressDialog.ChageMessage(getActivity(), getString(R.string.dialog_please_wait), getString(R.string.dialog_please_wait));
                        mCustomProgressDialog.show(getActivity());
                    }

                    RestaurantNamesMapAdapter.ViewHolder viewHolder = ((RestaurantNamesMapAdapter.ViewHolder) view.getTag());
                    final String mRestaurantKey = ((RestaurantBranch) viewHolder.tvRestaurantName.getTag()).getRestaurantKey();


                    DAORestaurantBranchItems.getInstance().Callresponse("", mRestaurantKey,AppSharedValues.getCategory(), new Callback<DAORestaurantBranchItems.RestaurantBranchItems>() {
                        @Override
                        public void success(DAORestaurantBranchItems.RestaurantBranchItems restaurantBranchItems, Response response) {


                            int count = 0;
                            if (restaurantBranchItems.getHttpcode().equals(200)) {


                                DBActionsCuisine.deleteAll();
                                DBActionsGroup.deleteAll();
                                DBActionsCategory.deleteAll();
                                DBActionsIngredientsCategory.deleteAll();
                                DBActionsIngredients.deleteAll();

                                List<DAORestaurantBranchItems.Shop_detail> restaurantDetails = restaurantBranchItems.getData().getShop_detail();
                                for (DAORestaurantBranchItems.Shop_detail restaurantDetail : restaurantDetails) {

                                    if (restaurantDetail != null) {
//                                        Integer deliveryFee = restaurantDetail.getDelivery_fee() == null ? 0 : restaurantDetail.getDelivery_fee();
                                        Integer minOrderValue = restaurantDetail.getMin_order_value() == null ? 0 : restaurantDetail.getMin_order_value();

                                        AppSharedValues.setSelectedRestaurantBranchOfferText(restaurantDetail.getOffer_text());
                                        AppSharedValues.setSelectedRestaurantBranchOfferCode(restaurantDetail.getOffer_code());
                                        AppSharedValues.setSelectedRestaurantBranchOfferMinOrderValue(Double.valueOf(restaurantDetail.getOffer_min_order_value()));

                                        AppSharedValues.setSelectedRestaurantBranchKey(restaurantDetail.getShop_key());
                                        AppSharedValues.setSelectedRestaurantBranchName(restaurantDetail.getShop_name());

                                        if (restaurantDetail.getShop_status().trim().equalsIgnoreCase("closed")) {
                                            AppSharedValues.setSelectedRestaurantBranchStatus(AppSharedValues.ShopStatus.Closed);
                                        } else {
                                            AppSharedValues.setSelectedRestaurantBranchStatus(AppSharedValues.ShopStatus.Open);
                                        }
                                        //AppSharedValues.setSelectedRestaurantBranchStatus(AppSharedValues.ShopStatus.Closed);
//                                        AppSharedValues.setDeliveryCharge(Double.valueOf(deliveryFee));
                                        AppSharedValues.setMinOrderDeliveryPrice(Double.valueOf(minOrderValue));
                                        bus.post(new RestaurantNamesEvent());

                                        AppSharedValues.setDeliveryAvailability_ONLINE(false);
                                        if (restaurantDetail.getOnline_payment_available().equals(1)) {
                                            AppSharedValues.setDeliveryAvailability_ONLINE(true);
                                        }

                                        List<DAORestaurantBranchItems.Category> categories = restaurantDetail.getCategories();
                                        for (DAORestaurantBranchItems.Category category : categories) {

                                            count++;

                                            DBActionsCuisine.add(restaurantDetail.getShop_key(), restaurantDetail.getShop_key(), category.getCategory_key(), category.getCategory_name());
                                            DBActionsGroup.add(restaurantDetail.getShop_key(), restaurantDetail.getShop_key(), "Grp1", "Food");
                                            DBActionsCategory.add(restaurantDetail.getShop_key(), restaurantDetail.getShop_key(), category.getCategory_key(), category.getCategory_name());

                                            for (DAORestaurantBranchItems.Menu_item menuItem : category.getMenu_items()) {

                                                DBActionsProducts.add(restaurantDetail.getShop_key(), category.getCategory_key(),
                                                        menuItem.getItem_key(), menuItem.getItem_name(), menuItem.getItem_description(), menuItem.getItem_image(), category.getCategory_key(),
                                                        Double.valueOf(menuItem.getPrice()), Double.valueOf(menuItem.getPrice()), Double.valueOf(menuItem.getPrice()),
                                                        0D, Integer.valueOf(menuItem.getItem_type()));

                                                List<DAORestaurantBranchItems.Ingredient> ingredients = menuItem.getIngredients();
                                                if (ingredients != null) {
                                                    for (DAORestaurantBranchItems.Ingredient ingredient : ingredients) {
                                                        DBActionsIngredientsCategory.add(restaurantDetail.getShop_key(), menuItem.getItem_key(),
                                                                String.valueOf(ingredient.getIngredients_type_id()), ingredient.getIngredients_type_name(),
                                                                ingredient.getMinimum(), ingredient.getMaximum(), ingredient.getRequired(), 0D);

                                                        for (DAORestaurantBranchItems.Ingredients_list ingredientList : ingredient.getIngredients_list()) {

                                                            DBActionsIngredients.add(restaurantDetail.getShop_key(), menuItem.getItem_key(), String.valueOf(ingredientList.getItem_ingredients_list_id()),
                                                                    ingredientList.getIngredient_name(), String.valueOf(ingredient.getIngredients_type_id()),
                                                                    String.valueOf(ingredient.getIngredients_type_id()), Double.valueOf(ingredientList.getPrice()));
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }

                                if (count > 0) {
                                    if (mCustomProgressDialog.isShowing()) {
                                        mCustomProgressDialog.dismiss();
                                    }
                                    Bundle bundle = new Bundle();
                                    bundle.putString("restaurantKey", mRestaurantKey);

                                    //MainMenuFragment mainMenuFragment = new MainMenuFragment();
                                    //mainMenuFragment.setArguments(bundle);

                                    MenuItemFragment menuItemFragment = new MenuItemFragment();
                                    menuItemFragment.setArguments(bundle);

                                    AppSharedValues.setSelectedRestaurantBranchKey(mRestaurantKey);

                                    FragmentManager fragmentManager = getFragmentManager();
                                    fragmentManager.beginTransaction()
                                            .replace(R.id.container, menuItemFragment)
                                            .commit();
                                } else {
                                    if (mCustomProgressDialog.isShowing()) {
                                        mCustomProgressDialog.dismiss();
                                    }
                                    Toast.makeText(getActivity(), getString(R.string.toast_no_item_to_show), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                            if (mCustomProgressDialog.isShowing()) {
                                mCustomProgressDialog.dismiss();
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    if (mCustomProgressDialog.isShowing()) {
                        mCustomProgressDialog.dismiss();
                    }
                }
            }
        });

        return vRestaurantHome;
    }

    private void ToggleArrow(View v) {
        v.setSelected(!v.isSelected());

        if(v.isSelected()) {
            mLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);

            Resources res = getResources();
            int resourceId = res.getIdentifier("arrow_down", "drawable", getActivity().getPackageName());
            btnShowHide.setImageResource(resourceId);

        }
        else {
            mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);

            Resources res = getResources();
            int resourceId = res.getIdentifier("arrow_up", "drawable", getActivity().getPackageName() );
            btnShowHide.setImageResource(resourceId);
            //btnShowHide.setImageDrawable(getDrawable(R.drawable.arrow_up));
        }

    }
    private void addMarkersToMap() {

        try {
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

            mMarkers = new ArrayList<>();
            mGoogleMap.clear();
            for (RestaurantBranch restaurantBranch : restaurants) {
                LatLng ll = new LatLng(restaurantBranch.getRestaurantLatitude() + (r.nextInt(10) * 0.08), restaurantBranch.getRestaurantLongitude() + (r.nextInt(10) * 0.08));

                if (!restaurantBranch.getRestaurantFavouriteStatus()) {
                    Marker marker = mGoogleMap.addMarker(new MarkerOptions().position(ll).title(restaurantBranch.getRestaurantBranchName())
                            .snippet("Test").icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_restaurant)));
                    dropPinEffect(marker);
                    mMarkers.add(marker);
                } else {

                    BitmapDescriptor bitmapMarker = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);

                    MarkerOptions markerOptions = new MarkerOptions().position(ll).title(restaurantBranch.getRestaurantBranchName())
                            .snippet("Test").icon(bitmapMarker);

                    Marker marker = mGoogleMap.addMarker(markerOptions);
                    dropPinEffect(marker);
                    mMarkers.add(marker);
                }

                count++;

                if (count > 20) {
                    break;
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void dropPinEffect(final Marker marker) {

        try {
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
        catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {

        try {
            //TextView locationTv = (TextView) findViewById(R.id.latlongLocation);

            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            LatLng latLng = new LatLng(latitude, longitude);
            mGoogleMap.addMarker(new MarkerOptions().position(latLng));
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
            //locationTv.setText("Latitude:" + latitude + ", Longitude:" + longitude);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
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


        try {
            // TODO Auto-generated method stub
            this.mGoogleMap = googleMap;

            if(checkPlayServices()) {
                this.mGoogleMap.setMyLocationEnabled(true);
            }

            // Get the button view
            mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
            mGoogleMap.getUiSettings().setRotateGesturesEnabled(true);


            mGoogleMap.setMyLocationEnabled(true);
            LocationManager locationManager = (LocationManager) getActivity().getSystemService(getActivity().LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            String bestProvider = locationManager.getBestProvider(criteria, true);
            Location location = locationManager.getLastKnownLocation(bestProvider);
            if (location != null) {
                onLocationChanged(location);
            }
            locationManager.requestLocationUpdates(bestProvider, 20000, 0, this);




            if (location != null) {
                try {
                    mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                            new LatLng(location.getLatitude(), location.getLongitude()), 13));

                    CameraPosition cameraPosition = new CameraPosition.Builder()
                            .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
                            .zoom(10)                   // Sets the zoom
                                    //.bearing(90)                // Sets the orientation of the camera to east
                            .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                            .build();                   // Creates a CameraPosition from the builder
                    mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                    addMarkersToMap();

                    if (mMarkers.size() > 0) {
                        LatLngBounds.Builder builder = new LatLngBounds.Builder();
                        for (Marker marker : mMarkers) {
                            builder.include(marker.getPosition());
                        }
                        LatLngBounds bounds = builder.build();
                        int padding = 0;
                        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
                        googleMap.animateCamera(cu);
                    }
                } catch (Exception e) {
                    e.printStackTrace();

                    addMarkersToMap();
                }
            } else {

                try {
                    addMarkersToMap();

                    if (mMarkers.size() > 0) {
                        LatLngBounds.Builder builder = new LatLngBounds.Builder();
                        for (Marker marker : mMarkers) {
                            builder.include(marker.getPosition());
                        }
                        LatLngBounds bounds = builder.build();
                        int padding = 0;
                        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
                        googleMap.animateCamera(cu);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    addMarkersToMap();
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }




    protected boolean checkPlayServices() {
        final int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity());
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                Dialog dialog = GooglePlayServicesUtil.getErrorDialog(resultCode, getActivity(), 1);
                if (dialog != null) {
                    dialog.show();
                    dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        public void onDismiss(DialogInterface dialog) {
                            if (ConnectionResult.SERVICE_INVALID == resultCode) getActivity().finish();
                        }
                    });
                    return false;
                }
            }

            Toast.makeText(getActivity(), R.string.toast_this_device_not_supported, Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    @Override
    public void onConnected(Bundle bundle) {

        try {
            // TODO Auto-generated method stub
            mLocationRequest = LocationRequest.create();
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            mLocationRequest.setInterval(1000);
            //LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
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

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        EventBus.getDefault().unregister(this);
    }

    public void onEvent(final DummyEvent ev) {
    }
}