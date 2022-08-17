package com.foodpurby.screens;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.foodpurby.R;
import com.foodpurby.model.DAOContactdetailsupload;
import com.foodpurby.model.DAOContactus;
import com.foodpurby.utillities.SessionManager;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.foodpurby.R.id.FCU_email;
import static com.foodpurby.R.id.FCU_message;
import static com.foodpurby.R.id.FCU_mobilenumber;
import static com.foodpurby.R.id.FCU_subject;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ContactUs.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ContactUs#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactUs extends Fragment implements OnMapReadyCallback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private TextView contactname;
    private TextView location;
    private TextView contactnumber;
    private TextView emailaddress;
    private GoogleMap mMap;
    private TextView submits;
    private EditText FCU_names;
    private EditText FCU_emails;
    private EditText FCU_mobilenxumbers;
    private EditText FCU_subjects;
    private EditText FCU_messages;
    private MapFragment mapFragment;


    public ContactUs() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ContactUs.
     */
    // TODO: Rename and change types and number of parameters
    public static ContactUs newInstance(String param1, String param2) {
        ContactUs fragment = new ContactUs();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_contact_us, container, false);
        mapFragment = (MapFragment) getActivity().getFragmentManager()
                .findFragmentById(R.id.FCU_map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        return view;

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        FCU_names = (EditText) getView().findViewById(R.id.FCU_name);
        FCU_emails = (EditText) getView().findViewById(FCU_email);
        FCU_mobilenxumbers = (EditText) getView().findViewById(FCU_mobilenumber);
        FCU_subjects = (EditText) getView().findViewById(FCU_subject);
        FCU_messages = (EditText) getView().findViewById(FCU_message);

        contactname = (TextView) getView().findViewById(R.id.FUC_contactname);
        location = (TextView) getView().findViewById(R.id.FUC_location);
        contactnumber = (TextView) getView().findViewById(R.id.FUC_contactnumber);
        emailaddress = (TextView) getView().findViewById(R.id.FUC_emauiladdress);
        submits = (TextView) getView().findViewById(R.id.FUC_submit);

        DAOContactus.getInstance().Callresponse(new Callback<DAOContactus.ContactResponse>() {
            @Override
            public void success(DAOContactus.ContactResponse contactResponse, Response response) {
                if (contactResponse.getHttpcode().equals("200")) {
                    contactname.setText(contactResponse.getData().getSite_contact_name());
                    location.setText(contactResponse.getData().getSite_locatoin());
                    contactnumber.setText(contactResponse.getData().getSite_contact_number());
                    emailaddress.setText(contactResponse.getData().getSite_contact_email());

                    SessionManager.getInstance().setlat(getActivity(), String.valueOf(contactResponse.getData().getSite_latitude()));
                    SessionManager.getInstance().setlong(getActivity(), String.valueOf(contactResponse.getData().getSite_longitude()));
                }


            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();

            }
        });


        submits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DAOContactdetailsupload.getInstance().Callresponse(FCU_names.getText().toString(),
                        FCU_emails.getText().toString(),
                        FCU_mobilenxumbers.getText().toString(),
                        FCU_subjects.getText().toString(),
                        FCU_subjects.getText().toString(), new Callback<DAOContactdetailsupload.ContactdetailsResponse>() {
                            @Override
                            public void success(DAOContactdetailsupload.ContactdetailsResponse contactdetailsResponse, Response response) {
                                if (contactdetailsResponse.getHttpcode() == 200) {
                                    Toast.makeText(getActivity(), contactdetailsResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                    FCU_names.getText().clear();
                                    FCU_emails.getText().clear();
                                    FCU_messages.getText().clear();
                                    FCU_mobilenxumbers.getText().clear();
                                    FCU_subjects.getText().clear();
                                }
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                error.printStackTrace();

                            }
                        });

            }
        });


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;


        // Add a marker in Sydney and move the camera

        LatLng TutorialsPoint = new LatLng(Double.parseDouble(SessionManager.getInstance().getlat(getActivity())),
                Double.parseDouble(SessionManager.getInstance().getlong(getActivity())));
        mMap.addMarker(new MarkerOptions().position(TutorialsPoint).title(SessionManager.getInstance().getAddress(getActivity())));


        mMap.moveCamera(CameraUpdateFactory.newLatLng(TutorialsPoint));

        //LatLng origin = new LatLng(lat, lng);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(TutorialsPoint)
                .zoom(18)                   // Sets the zoom
                .tilt(40)
                .build();                   // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void onDestroyView() {
        super.onDestroyView();
        FragmentManager fm = getActivity().getSupportFragmentManager();
        Fragment fragment = (fm.findFragmentById(R.id.FCU_map));
        FragmentTransaction ft = fm.beginTransaction();
        ft.remove(fragment);
        ft.commit();
    }

  /*  public void onDestroyView()
    {
        super.onDestroyView();
        Fragment fragment = (getFragmentManager().findFragmentById(R.id.FCU_map));
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.remove(fragment);
        ft.commit();
    }*/
}
