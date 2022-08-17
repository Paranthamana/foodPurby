package com.foodpurby;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.foodpurby.model.DAOContactdetailsupload;
import com.foodpurby.model.DAOContactus;
import com.foodpurby.util.Common;
import com.foodpurby.util.CustomProgressDialog;
import com.foodpurby.utillities.SessionManager;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.foodpurby.screens.UserSignin.isValidEmail;


public class ContactUSFragment extends Fragment implements OnMapReadyCallback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    View view;


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
//    private MapFragment mapFragment;

    public ContactUSFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ContactUSFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ContactUSFragment newInstance(String param1, String param2) {
        ContactUSFragment fragment = new ContactUSFragment();
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
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }

        view = inflater.inflate(R.layout.fragment_contact_us, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.FCU_map);
        mapFragment.getMapAsync(this);
        FCU_names = (EditText) view.findViewById(R.id.FCU_name);
        FCU_emails = (EditText) view.findViewById(R.id.FCU_email);
        FCU_mobilenxumbers = (EditText) view.findViewById(R.id.FCU_mobilenumber);
        FCU_subjects = (EditText) view.findViewById(R.id.FCU_subject);
        FCU_messages = (EditText) view.findViewById(R.id.FCU_message);

        contactname = (TextView) view.findViewById(R.id.FUC_contactname);
        location = (TextView) view.findViewById(R.id.FUC_location);
        contactnumber = (TextView) view.findViewById(R.id.FUC_contactnumber);
        emailaddress = (TextView) view.findViewById(R.id.FUC_emauiladdress);
        submits = (TextView) view.findViewById(R.id.FUC_submit);

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

        if (submits != null) {
            submits.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!Common.IsInternetConnected(getActivity())) {
                        Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                    } else if (FCU_names.getText().toString().isEmpty()) {
                        Toast.makeText(getActivity(), "First name cannot empty", Toast.LENGTH_SHORT).show();
                    } else if (FCU_emails.getText().toString().isEmpty()) {
                        Toast.makeText(getActivity(), "Email id empty", Toast.LENGTH_LONG).show();
                    } else if (!isValidEmail(FCU_emails.getText().toString())) {
                        Toast.makeText(getActivity(), "Enter vaild Emailid", Toast.LENGTH_LONG).show();
                    } else if (FCU_mobilenxumbers.getText().toString().isEmpty()) {
                        Toast.makeText(getActivity(), "Please enter mobilenumber", Toast.LENGTH_SHORT).show();
                    } else if (FCU_mobilenxumbers.getText().toString().length() < 6 || FCU_mobilenxumbers.getText().toString().length() > 13) {
                        Toast.makeText(getActivity(), "Please enter vaild mobilenumber", Toast.LENGTH_SHORT).show();
                    } else if (FCU_subjects.getText().toString().isEmpty()) {
                        Toast.makeText(getActivity(), "Please enter subject", Toast.LENGTH_SHORT).show();
                    } else if (FCU_messages.getText().toString().isEmpty()) {
                        Toast.makeText(getActivity(), "Please enter message", Toast.LENGTH_SHORT).show();
                    } else {
                        CustomProgressDialog.getInstance().show(getActivity());
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
                                        CustomProgressDialog.getInstance().dismiss();
                                    }

                                    @Override
                                    public void failure(RetrofitError error) {
                                        error.printStackTrace();
                                        CustomProgressDialog.getInstance().dismiss();
                                    }
                                });
                    }
                }
            });

        }


        return view;
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

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_search);
        item.setVisible(false);
        super.onPrepareOptionsMenu(menu);

    }
}
