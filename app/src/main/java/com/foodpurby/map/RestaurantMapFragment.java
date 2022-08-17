package com.foodpurby.map;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.foodpurby.util.GPSTracker;
import com.foodpurby.R;
import com.foodpurby.adapters.MapPopUpRestaurants;
import com.foodpurby.model.LatLong;
import com.google.android.gms.common.ConnectionResult;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RestaurantMapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RestaurantMapFragment extends Fragment implements OnMapReadyCallback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private GoogleMap googleMap;
    private OnFragmentInteractionListener mListener;
    private GPSTracker mGpsTracker;
    ArrayList<LatLong> mLatLongLists = new ArrayList<LatLong>();
    LatLong mLatLong;
    MarkerOptions marker;
    MapPopUpRestaurants aMapPopUpRestaurants;
    private ListView mRestaurantLists;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RestaurantMapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RestaurantMapFragment newInstance(String param1, String param2) {
        RestaurantMapFragment fragment = new RestaurantMapFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public RestaurantMapFragment() {
        // Required empty public constructor
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mMapLayout = inflater.inflate(R.layout.fragment_restaurant_map, container, false);
        mRestaurantLists = (ListView) mMapLayout.findViewById(R.id.FSLMF_LV_show_restaurant_lists);


        if (!isGooglePlayServicesAvailable()) {
            getActivity().finish();
        }



       /*googleMap =*/ ((SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map)).getMapAsync(this);

        // googleMap.clear();

        if (isGooglePlayServicesIsInstalled(getActivity())) {
          //  googleMap = supportMapFragment.getMap();

          //  googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
           // googleMap.getUiSettings().setCompassEnabled(false);
           // googleMap.getUiSettings().setZoomControlsEnabled(false);
        } else {

            Toast.makeText(getActivity(), "Map", Toast.LENGTH_SHORT).show();
        }


        try {
            intializeMap();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mMapLayout;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(int position) {
        if (mListener != null) {
            mListener.onFragmentInteraction(position);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(int position);
    }


    private boolean isGooglePlayServicesIsInstalled(Context userLocation) {
        // TODO Auto-generated method stub
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(userLocation);
        if (resultCode == ConnectionResult.SUCCESS)
            return true;
        else
            return false;
    }

    private void intializeMap() {
        if (googleMap == null) {

            mGpsTracker = new GPSTracker(getActivity());
            if (mGpsTracker.getIsGPSTrackingEnabled()) {
                mLatLong = new LatLong();
                mLatLong.setmLatitutde(mGpsTracker.getLatitude());
                mLatLong.setmLongitude(mGpsTracker.getLongitude());
                mLatLong.setmLocationName("Coimbatore");
                mLatLongLists.add(mLatLong);

                mLatLong = new LatLong();
                mLatLong.setmLatitutde(56.130366);
                mLatLong.setmLongitude(-106.346771);
                mLatLong.setmLocationName("Canada");
                mLatLongLists.add(mLatLong);

                mLatLong = new LatLong();
                mLatLong.setmLatitutde(37.09024);
                mLatLong.setmLongitude(-95.712891);
                mLatLong.setmLocationName("United states");
                mLatLongLists.add(mLatLong);

                mLatLong = new LatLong();
                mLatLong.setmLatitutde(-25.274398);
                mLatLong.setmLongitude(133.775136);
                mLatLong.setmLocationName("Australia");
                mLatLongLists.add(mLatLong);

                mLatLong = new LatLong();
                mLatLong.setmLatitutde(35.86166);
                mLatLong.setmLongitude(104.195397);
                mLatLong.setmLocationName("China");
                mLatLongLists.add(mLatLong);


                mLatLong = new LatLong();
                mLatLong.setmLatitutde(-38.416097);
                mLatLong.setmLongitude(-63.616672);
                mLatLong.setmLocationName("Argentina");
                mLatLongLists.add(mLatLong);




                googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(37.7750, 122.4183))
                        .title("San Francisco").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

                for (int mMorePoints = 0; mMorePoints < mLatLongLists.size(); mMorePoints++) {
                    marker = new MarkerOptions().position(new LatLng(mLatLongLists.get(mMorePoints).getmLatitutde(), mLatLongLists.get(mMorePoints).getmLongitude())).title(mLatLongLists.get(mMorePoints).getmLocationName());
                    marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                    googleMap.addMarker(marker);


                    aMapPopUpRestaurants = new MapPopUpRestaurants(getActivity(), mLatLongLists);
                    mRestaurantLists.setAdapter(aMapPopUpRestaurants);
                    aMapPopUpRestaurants.notifyDataSetChanged();
                }


            } else {

            }

            // check if map is created successfully or not
            if (googleMap == null) {
                Toast.makeText(getActivity(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }
        }

    }


    private boolean isGooglePlayServicesAvailable() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity());
        if (ConnectionResult.SUCCESS == status) {
            return true;
        } else {
            GooglePlayServicesUtil.getErrorDialog(status, getActivity(), 0).show();
            return false;
        }
    }

}
