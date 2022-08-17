package com.foodpurby.screens;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sloop.fonts.FontsManager;
import com.foodpurby.R;
import com.foodpurby.adapters.RestaurantTimingAdapter;
import com.foodpurby.model.DAORestaurantDetails;
import com.foodpurby.ondbstorage.DBActionsRestaurantBranch;
import com.foodpurby.ondbstorage.RestaurantBranch;
import com.foodpurby.util.Common;
import com.foodpurby.util.CustomProgressDialog;
import com.foodpurby.utillities.AppSharedValues;
import com.foodpurby.utillities.LoadImages;
import com.foodpurby.utillities.SessionManager;
import com.foodpurby.utillities.WrapContentDraweeView;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.foodpurby.R.id.tvCuisine;


public class InfoFragment extends Fragment implements OnMapReadyCallback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private GoogleMap mMap;
    private OnFragmentInteractionListener mListener;
    private CustomProgressDialog mCustomProgressDialog;
    View vInfoRoot;
    ListView lvTiming;
    TextView tvRestaurantStatus;
    TextView tvOnline;
    TextView tvCOD;
    TextView tvMinOrder;
    TextView tvContactNo;
    private RestaurantTimingAdapter aRestaurantTimingAdapter;
    //    List<DAORestaurantDetails.Delivery_time_list> timing = new ArrayList<>();
    int mPosition;
    TextView tvRestaurantName;
    TextView tvAddress;
    TextView tvDistance;
    TextView tvDeliversIn;
    TextView tvRestaurantCuisines;
    ImageView ivRestaurantLogo;
    SimpleDraweeView ivCertificateImage;
    List<RestaurantBranch> restaurant;
    TextView tvDeliveryFee;

    private TextView tvAddresse;
    private TextView tvCuisines;
    private MapView mMapView;
    private GoogleMap mGoogleMap;
    private Context mcontext;
    private GoogleMap maps;
    private MapFragment mapFragment;
    private Double latitude;
    private Double longtitude;
    private List<DAORestaurantDetails.Delivery_time_list> timing;
    private static View view;

    public InfoFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public InfoFragment(int position) {
        mPosition = position;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InfoFragment newInstance(String param1, String param2) {
        InfoFragment fragment = new InfoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (vInfoRoot != null) {
            ViewGroup parent = (ViewGroup) vInfoRoot.getParent();
            if (parent != null)
                parent.removeView(vInfoRoot);
        }
        try {
//            vInfoRoot = inflater.inflate(R.layout.map, container, false);
            // Inflate the layout for this fragment
            vInfoRoot = inflater.inflate(R.layout.fragment_info, container, false);
          //  FontsManager.initFormAssets(getActivity(), "Lato-Light.ttf");
          //  FontsManager.changeFonts(vInfoRoot);

            restaurant = DBActionsRestaurantBranch.get();
            mCustomProgressDialog = CustomProgressDialog.getInstance();

            initViews(vInfoRoot);


            if (!mCustomProgressDialog.isShowing()) {
                mCustomProgressDialog.ChageMessage(getActivity(), getString(R.string.dialog_please_wait), getString(R.string.dialog_please_wait));
                mCustomProgressDialog.show(getActivity());
            }

            mapFragment = (MapFragment) getActivity().getFragmentManager()
                    .findFragmentById(R.id.info_map);
            mapFragment.getMapAsync(this);

            DAORestaurantDetails.getInstance().Callresponse("",
                    AppSharedValues.getSelectedRestaurantBranchKey(),
                    AppSharedValues.getLanguage(), "asdf",
                    new Callback<DAORestaurantDetails.RestaurantDetails>() {

                        @Override
                        public void success(DAORestaurantDetails.RestaurantDetails restaurantDetails, Response response) {
                            if (restaurantDetails.getHttpcode().equals(200)) {

                            /*if (restaurant.get(mPosition).getRestaurantLatitude() != null && restaurant.get(mPosition).getRestaurantLongitude() != null) {
                                mMap.addMarker(new MarkerOptions().position(new LatLng(restaurant.get(mPosition).getRestaurantLatitude(), restaurant.get(mPosition).getRestaurantLongitude())).title(restaurant.get(mPosition).getRestaurantAddress()));
                                LatLng TutorialsPoint = new LatLng(restaurant.get(mPosition).getRestaurantLatitude(), restaurant.get(mPosition).getRestaurantLongitude());
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(TutorialsPoint));
                            }*/
                                tvMinOrder.setText(Common.getPriceWithCurrencyCode(restaurantDetails.getData().getShop_info().get(0).getMin_order_value().toString()));
                                tvMinOrder.setTypeface(Typeface.DEFAULT);
                                tvContactNo.setText(restaurantDetails.getData().getShop_info().get(0).getShop_phone_number());
                                tvDeliversIn.setText(restaurant.get(mPosition).getRestaurantDeliveryInMin() + "" + getResources().getString(R.string.txt_min));
                                tvCuisines.setText(restaurantDetails.getData().getShop_info().get(0).getCuisines());
                                tvRestaurantName.setText(restaurantDetails.getData().getShop_info().get(0).getShop_name());
                                tvAddresse.setText(restaurantDetails.getData().getShop_info().get(0).getShop_address());
                                tvDeliveryFee.setText(String.valueOf(Common.getPriceWithCurrencyCode(String.valueOf(restaurant.get(mPosition).getMinOrderDeliveryPrice()))));
                                tvDeliveryFee.setTypeface(Typeface.DEFAULT);
                                tvDistance.setText(String.valueOf(restaurant.get(mPosition).getRestaurantDistance() + " " + AppSharedValues.getDistanceMetrics()));
                                //tvTotalRating.setText(String.valueOf(AppConfig.decimalFormat.format(restaurantDetails.getData().getShop_info().get(0).getAvg_rating())));
                                LoadImages.show(getActivity(), ivRestaurantLogo, restaurantDetails.getData().getShop_info().get(0).getShop_logo());


                                if (restaurantDetails.getData().getShop_info().get(0).getOnline_payment_available().equals(0)) {
                                    tvCOD.setVisibility(View.VISIBLE);
                                    tvOnline.setVisibility(View.GONE);
                                } else if (restaurantDetails.getData().getShop_info().get(0).getOnline_payment_available().equals(1)) {
                                    tvCOD.setVisibility(View.GONE);
                                    tvOnline.setVisibility(View.VISIBLE);
                                } else if (restaurantDetails.getData().getShop_info().get(0).getOnline_payment_available().equals(2)) {
                                    tvOnline.setVisibility(View.VISIBLE);
                                    tvCOD.setVisibility(View.VISIBLE);
                                }
                                if (AppSharedValues.getCerfiticateImage() != null) {
                                    if (AppSharedValues.getCerfiticateImage().equals("")) {
                                        ivCertificateImage.setVisibility(View.GONE);
                                    } else {
                                        //LoadImages.show(getActivity(), ivCertificateImage, AppSharedValues.getCerfiticateImage());
                                        ivCertificateImage.setVisibility(View.VISIBLE);
                                    }
                                }

                                timing = restaurantDetails.getData().getShop_info().get(0).getDelivery_time_list();

                                aRestaurantTimingAdapter.setData(timing);
                                aRestaurantTimingAdapter.notifyChanges();
                                Common.setListViewHeightBasedOnChildren(lvTiming);

                                if (mCustomProgressDialog.isShowing()) {
                                    mCustomProgressDialog.dismiss();
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

            aRestaurantTimingAdapter = new RestaurantTimingAdapter(getActivity());
            lvTiming.setAdapter(aRestaurantTimingAdapter);
            lvTiming.setSelection(0);
            setListViewHeightBasedOnChildren(lvTiming);

            if (AppSharedValues.getCerfiticateImage() == null || AppSharedValues.getCerfiticateImage().equals("")) {
                ivCertificateImage.setVisibility(View.GONE);
            } else {
                ivCertificateImage.setVisibility(View.VISIBLE);
            }
            ivCertificateImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final Dialog dialog = new Dialog(getActivity());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.dialog_img);

                    WrapContentDraweeView text = (WrapContentDraweeView) dialog.findViewById(R.id.ivRestaurantLogo);
                    Uri uri = Uri.parse(AppSharedValues.getCerfiticateImage());
                    text.setImageURI(uri);

                    dialog.show();


                }
            });
        } catch (InflateException e) {
        /* map is already there, just return view as it is */
        }
        return vInfoRoot;

//        return vInfoRoot;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        // Add a marker in Sydney and move the camera

        LatLng TutorialsPoint = new LatLng(Double.parseDouble(SessionManager.getInstance().getLatitute(getActivity())),
                Double.parseDouble(SessionManager.getInstance().getLongtitue(getActivity())));
        mMap.addMarker(new
                MarkerOptions().position(TutorialsPoint).title(SessionManager.getInstance().getAddress(getActivity())));


        mMap.moveCamera(CameraUpdateFactory.newLatLng(TutorialsPoint));

        //LatLng origin = new LatLng(lat, lng);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(TutorialsPoint)
                .zoom(18)                   // Sets the zoom
                .tilt(40)
                .build();                   // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0) {
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, LinearLayout.LayoutParams.WRAP_CONTENT));
            }

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    private void initViews(View vInfoRoot) {
        lvTiming = (ListView) vInfoRoot.findViewById(R.id.lvTiming);
        tvRestaurantStatus = (TextView) vInfoRoot.findViewById(R.id.tvRestaurantStatus);
        tvOnline = (TextView) vInfoRoot.findViewById(R.id.tvOnline);
        tvCOD = (TextView) vInfoRoot.findViewById(R.id.tvCOD);
        ivCertificateImage = (SimpleDraweeView) vInfoRoot.findViewById(R.id.ivcertificateImage);
        ivRestaurantLogo = (ImageView) vInfoRoot.findViewById(R.id.ivRestaurantLogo);
        tvRestaurantName = (TextView) vInfoRoot.findViewById(R.id.tvRestaurantName);
        tvAddress = (TextView) vInfoRoot.findViewById(R.id.tvAddress);
        tvAddresse = (TextView) vInfoRoot.findViewById(R.id.tv_Addresses);
        tvAddresse.setTypeface(tvAddresse.getTypeface(), Typeface.BOLD);
        tvRestaurantCuisines = (TextView) vInfoRoot.findViewById(R.id.tvRestaurantCuisines);
        tvCuisines = (TextView) vInfoRoot.findViewById(tvCuisine);
        //tvTotalRating = (TextView) vInfoRoot.findViewById(R.id.tvTotalRating);
        tvDistance = (TextView) vInfoRoot.findViewById(R.id.tvDistance);
        tvDeliversIn = (TextView) vInfoRoot.findViewById(R.id.tvDeliversIn);
        tvDeliveryFee = (TextView) vInfoRoot.findViewById(R.id.tvRestaurant_min_cost);
        tvMinOrder = (TextView) vInfoRoot.findViewById(R.id.tvMinOrder);
        tvContactNo = (TextView) vInfoRoot.findViewById(R.id.tvPhoneNo);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(int uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
       /* if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }*/
        mcontext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(int uri);
    }
}
