package com.foodpurby.screens;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.foodpurby.R;
import com.foodpurby.model.DAORestaurantDetails;
import com.foodpurby.ondbstorage.Cuisine;
import com.foodpurby.ondbstorage.DBActionsCuisine;
import com.foodpurby.ondbstorage.DBActionsRestaurantBranch;
import com.foodpurby.ondbstorage.RestaurantBranch;
import com.foodpurby.util.Common;
import com.foodpurby.util.CustomProgressDialog;
import com.foodpurby.utillities.AppSharedValues;
import com.foodpurby.utillities.LoadImages;
import com.foodpurby.utillities.WrapContentDraweeView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.sloop.fonts.FontsManager;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MenuFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    View vMenuRoot;
    private CustomProgressDialog mCustomProgressDialog;
    TextView tvRestaurantName;
    ListView vCuisine;
    TextView tvAddress;
    TextView tvTotalRating;
    TextView tvMinOrder;
    TextView tvContactNo;
    TextView tvDistance;
    TextView tvDeliversIn;
    TextView tvRestaurantCuisines;
    TextView tvDeliveryFee;
    SimpleDraweeView ivRestaurantLogo, ivCertificateImage;
    List<Cuisine> cuisines;
    ArrayList<String> mCuisineNameList = new ArrayList<>();
    List<RestaurantBranch> restaurant;
    int mPosition;

    private OnFragmentInteractionListener mListener;

    private SimpleDraweeView roundimage;

    public MenuFragment() {
        // Required empty public constructor
    }


    public MenuFragment(int position) {
        mPosition = position;
    }


    // TODO: Rename and change types and number of parameters
    public static MenuFragment newInstance(String mParamGroup) {
        MenuFragment fragment = new MenuFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, mParamGroup);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vMenuRoot = inflater.inflate(R.layout.fragment_menu, container, false);
        //FontsManager.initFormAssets(getActivity(), "Lato-Light.ttf");
       // FontsManager.changeFonts(vMenuRoot);

        restaurant = DBActionsRestaurantBranch.get();
        mCustomProgressDialog = CustomProgressDialog.getInstance();

        initViews(vMenuRoot);

        if (!mCustomProgressDialog.isShowing()) {
            mCustomProgressDialog.ChageMessage(getActivity(), getString(R.string.dialog_please_wait), getString(R.string.dialog_please_wait));
            mCustomProgressDialog.show(getActivity());
        }

        DAORestaurantDetails.getInstance().Callresponse("", AppSharedValues.getSelectedRestaurantBranchKey(), AppSharedValues.getLanguage(), "asdf", new Callback<DAORestaurantDetails.RestaurantDetails>() {

            @Override
            public void success(DAORestaurantDetails.RestaurantDetails restaurantDetails, Response response) {
                if (restaurantDetails.getHttpcode().equals(200)) {

                    tvMinOrder.setText(Common.getPriceWithCurrencyCode(restaurantDetails.getData().getShop_info().get(0).getMin_order_value().toString()));
                    tvMinOrder.setTypeface(Typeface.DEFAULT);
                    tvContactNo.setText(restaurantDetails.getData().getShop_info().get(0).getShop_phone_number());
                    tvDeliversIn.setText(restaurant.get(mPosition).getRestaurantDeliveryInMin() + "" + getResources().getString(R.string.txt_min));
                    tvRestaurantCuisines.setText(restaurantDetails.getData().getShop_info().get(0).getCuisines());

                    tvRestaurantName.setText(restaurantDetails.getData().getShop_info().get(0).getShop_name());
                    tvAddress.setText(restaurantDetails.getData().getShop_info().get(0).getShop_address());
                    tvDeliveryFee.setText(String.valueOf(Common.getPriceWithCurrencyCode(String.valueOf(restaurant.get(mPosition).getMinOrderDeliveryPrice()))));
                    tvDeliveryFee.setTypeface(Typeface.DEFAULT);
                    tvDistance.setText(String.valueOf(restaurant.get(mPosition).getRestaurantDistance() + " " + AppSharedValues.getDistanceMetrics()));

//                    Uri imageUri;
//                    imageUri = Uri.parse(restaurantDetails.getData().getShop_info().get(0).getShop_logo());
//                    DraweeController controller = Fresco.newDraweeControllerBuilder()
//                            .setUri(imageUri)
//                            .setAutoPlayAnimations(true)
//                            .build();
//                    //Set the DraweeView controller, and you should be good to go.
//                    ivRestaurantLogo.setController(controller);

//                    Common.showFresco1(getActivity(), ivRestaurantLogo, restaurantDetails.getData().getShop_info().get(0).getShop_logo());

                    LoadImages.show(getActivity(), ivRestaurantLogo, restaurantDetails.getData().getShop_info().get(0).getShop_logo());

                    if (AppSharedValues.getCerfiticateImage() == null || AppSharedValues.getCerfiticateImage().equals("")) {
                        ivCertificateImage.setVisibility(View.GONE);
                    } else {
                        ivCertificateImage.setVisibility(View.VISIBLE);
                    }

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
        if (AppSharedValues.getCerfiticateImage() != null) {
            if (AppSharedValues.getCerfiticateImage().equals("")) {
                ivCertificateImage.setVisibility(View.GONE);
            } else {
                //LoadImages.show(getActivity(), ivCertificateImage, AppSharedValues.getCerfiticateImage());
                ivCertificateImage.setVisibility(View.VISIBLE);
            }
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

        return vMenuRoot;
    }

    private void initViews(View vMenuRoot) {

        vCuisine = (ListView) vMenuRoot.findViewById(R.id.cuisineList);
        ivRestaurantLogo = (SimpleDraweeView) vMenuRoot.findViewById(R.id.ivRestaurantLogo);
        roundimage = (SimpleDraweeView) vMenuRoot.findViewById(R.id.roundimage);
        ivCertificateImage = (SimpleDraweeView) vMenuRoot.findViewById(R.id.ivcertificateImage);

        tvRestaurantName = (TextView) vMenuRoot.findViewById(R.id.tvRestaurantName);
        tvAddress = (TextView) vMenuRoot.findViewById(R.id.tvAddress);
        tvRestaurantCuisines = (TextView) vMenuRoot.findViewById(R.id.tvRestaurantCuisines);
        //tvTotalRating = (TextView) vMenuRoot.findViewById(R.id.tvTotalRating);
        tvDistance = (TextView) vMenuRoot.findViewById(R.id.tvDistance);
        tvDeliveryFee = (TextView) vMenuRoot.findViewById(R.id.tvRestaurant_min_cost);
        tvDeliversIn = (TextView) vMenuRoot.findViewById(R.id.tvDeliversIn);
        tvMinOrder = (TextView) vMenuRoot.findViewById(R.id.tvMinOrder);
        tvContactNo = (TextView) vMenuRoot.findViewById(R.id.tvPhoneNo);
        addCuisines();
    }

    private void addCuisines() {
        mCuisineNameList.clear();
        cuisines = DBActionsCuisine.getCuisines("Grp1");
        for (Cuisine cuisine : cuisines) {
            this.mCuisineNameList.add(cuisine.getCuisineName());
        }

        View view = getActivity().getLayoutInflater().inflate(R.layout.cuisine_names, null);
      //  FontsManager.changeFonts(view);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.cuisine_names, mCuisineNameList);
        vCuisine.setAdapter(adapter);


        vCuisine.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                try {

                    Intent mIntent = new Intent(getActivity(), RestaurantItemActivity.class);
                    mIntent.putExtra("Position", mPosition);
                    mIntent.putExtra("Position11", position);
                    startActivity(mIntent);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(int position) {
        if (mListener != null) {
            mListener.onFragmentInteraction(position);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(int position);
    }
}
