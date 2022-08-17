package com.foodpurby.screens;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sloop.fonts.FontsManager;
import com.foodpurby.model.DAORestaurantNewReview;
import com.foodpurby.util.CustomProgressDialog;
import com.foodpurby.R;
import com.foodpurby.adapters.RestaurantReviewAdapter;
import com.foodpurby.baseclass.MyActivity;
import com.foodpurby.model.DAORestaurantDetails;
import com.foodpurby.util.Common;
import com.foodpurby.utillities.AppSharedValues;
import com.foodpurby.utillities.UserDetails;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.foodpurby.R.id.tvReviewDesc;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RatingsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RatingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RatingsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    int mPosition;

    View vRatingRoot;
    private CustomProgressDialog mCustomProgressDialog;
    AlertDialog.Builder mDialog;
    AlertDialog mAlertDilaog;
    RatingBar rbRating;
    TextView tvCancel, tvCustomMessage;
    ListView lvReviews;
    TextView tvMoreReviews;
    TextView tvNoReviews;
    Button btnAddNewReview, btnSubmitReview;
    List<DAORestaurantDetails.Review> review = new ArrayList<>();
    private RestaurantReviewAdapter aRestaurantReviewAdapter;

    private OnFragmentInteractionListener mListener;
    private EditText tvReviewDescs;

    public RatingsFragment() {
        // Required empty public constructor
    }


    @SuppressLint("ValidFragment")
    public RatingsFragment(int position) {

        mPosition = position;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RatingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RatingsFragment newInstance(String param1, String param2) {
        RatingsFragment fragment = new RatingsFragment();
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
        vRatingRoot = inflater.inflate(R.layout.fragment_ratings, container, false);
      //  FontsManager.initFormAssets(getActivity(), "Lato-Light.ttf");
      //  FontsManager.changeFonts(vRatingRoot);

        mCustomProgressDialog = CustomProgressDialog.getInstance();
        initViews(vRatingRoot);

        if (!mCustomProgressDialog.isShowing()) {
            mCustomProgressDialog.ChageMessage(getActivity(), getString(R.string.dialog_please_wait), getString(R.string.dialog_please_wait));
            mCustomProgressDialog.show(getActivity());
        }

        DAORestaurantDetails.getInstance().Callresponse("", AppSharedValues.getSelectedRestaurantBranchKey(), AppSharedValues.getLanguage(), "asdf", new Callback<DAORestaurantDetails.RestaurantDetails>() {

            @Override
            public void success(DAORestaurantDetails.RestaurantDetails restaurantDetails, Response response) {
                if (restaurantDetails.getHttpcode().equals(200)) {
                    review = restaurantDetails.getData().getShop_info().get(0).getReviews();

                    if (review.size() > 0) {
                        tvMoreReviews.setVisibility(View.VISIBLE);
                        tvNoReviews.setVisibility(View.GONE);
                    } else {
                        tvMoreReviews.setVisibility(View.GONE);
                        tvNoReviews.setVisibility(View.VISIBLE);
                    }

                    aRestaurantReviewAdapter.setData(review);
                    //  Toast.makeText(getActivity(), "REvew:"+review, Toast.LENGTH_LONG).show();
                    aRestaurantReviewAdapter.notifyChanges();
                    Common.setListViewHeightBasedOnChildren(lvReviews);

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

        aRestaurantReviewAdapter = new RestaurantReviewAdapter(getActivity());
        lvReviews.setAdapter(aRestaurantReviewAdapter);
          /*Toast.makeText(getActivity(), "REvew:"+lvReviews, Toast.LENGTH_LONG).show();*/
        lvReviews.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }

                // Handle ListView touch events.
                v.onTouchEvent(event);
                return true;
            }
        });

        lvReviews.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                return (event.getAction() == MotionEvent.ACTION_MOVE);
            }
        });

        tvMoreReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.getInstance().trackEvent("TextView", "Click", "More Reviews");
                MyActivity.DisplayRestaurantProfileReviews(getActivity());
            }
        });

        btnAddNewReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.getInstance().trackEvent("Button", "Click", "Add new Reviews");
                mDialog = new AlertDialog.Builder(getActivity(), R.style.MyAlertDialogStyle);
                final View vDialog = getActivity().getLayoutInflater().inflate(R.layout.dialog_restaurant_review, null);
                mDialog.setView(vDialog);
                mAlertDilaog = mDialog.create();
                mAlertDilaog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                mAlertDilaog.show();

                mAlertDilaog.setOnKeyListener(new Dialog.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface arg0, int keyCode, KeyEvent event) {
                        // TODO Auto-generated method stub
                        if (keyCode == KeyEvent.KEYCODE_BACK) {

                        }
                        return true;
                    }
                });

                rbRating = (RatingBar) vDialog.findViewById(R.id.rbRating);
                tvCustomMessage = (TextView) vDialog.findViewById(R.id.tvCustomMessage);
                tvReviewDescs = (EditText) vDialog.findViewById(tvReviewDesc);
                rbRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                        if (Math.round(rating) == 1) {
                            tvCustomMessage.setText(R.string.txt_hated_it);
                        } else if (Math.round(rating) == 2) {
                            tvCustomMessage.setText(R.string.txt_disliked_it);
                        } else if (Math.round(rating) == 3) {
                            tvCustomMessage.setText(R.string.txt_its_ok);
                        } else if (Math.round(rating) == 4) {
                            tvCustomMessage.setText(R.string.txt_liked_it);
                        } else if (Math.round(rating) == 5) {
                            tvCustomMessage.setText(R.string.txt_loved_it);
                        }
                    }
                });

                tvCancel = (TextView) vDialog.findViewById(R.id.tvCancel);
                tvCancel.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        if (mAlertDilaog.isShowing()) {
                            mAlertDilaog.dismiss();
                        }
                    }
                });

                btnSubmitReview = (Button) vDialog.findViewById(R.id.btnSubmitReview);
                btnSubmitReview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        MyApplication.getInstance().trackEvent("Button", "Click", "Submit Reviews");
                        if (Math.round(rbRating.getRating()) > 0) {


                            if (!mCustomProgressDialog.isShowing()) {
                                mCustomProgressDialog.ChageMessage(getActivity(), "Please wait...", "Please wait...");
                                mCustomProgressDialog.show(getActivity());
                            }

                            DAORestaurantNewReview.getInstance().Callresponse("",
                                    "en",
                                    AppSharedValues.getSelectedRestaurantBranchKey(),
                                    UserDetails.getCustomerKey(),
                                    Integer.valueOf(String.valueOf(Math.round(rbRating.getRating()))),
                                    "",
                                    tvReviewDescs.getText().toString(),
                                    new Callback<DAORestaurantNewReview.RestaurantReview>() {

                                        @Override
                                        public void success(DAORestaurantNewReview.RestaurantReview restaurantReview, Response response) {

                                            if (restaurantReview.getHttpcode().equals("200") || restaurantReview.getHttpcode().equals(200)) {
                                                if (mCustomProgressDialog.isShowing()) {
                                                    mCustomProgressDialog.dismiss();
                                                }

                                                if (mAlertDilaog.isShowing()) {
                                                    mAlertDilaog.dismiss();
                                                }

                                                Toast.makeText(getActivity(), restaurantReview.getMessage(), Toast.LENGTH_SHORT).show();
                                                //  Toast.makeText(getActivity(),"review :"+ rbRating.getRating(), Toast.LENGTH_LONG).show();
                                                // Toast.makeText(getActivity(),"Rospones :"+ restaurantReview.getResponsetime(), Toast.LENGTH_LONG).show();


                                            } else {
                                                Toast.makeText(getActivity(), restaurantReview.getMessage(), Toast.LENGTH_SHORT).show();
                                                if (mCustomProgressDialog.isShowing()) {
                                                    mCustomProgressDialog.dismiss();
                                                }

                                                if (mAlertDilaog.isShowing()) {
                                                    mAlertDilaog.dismiss();
                                                }
                                            }
                                        }

                                        @Override
                                        public void failure(RetrofitError error) {
                                            Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                                            if (mCustomProgressDialog.isShowing()) {
                                                mCustomProgressDialog.dismiss();
                                            }

                                            if (mAlertDilaog.isShowing()) {
                                                mAlertDilaog.dismiss();
                                            }
                                        }
                                    });
                        } else {
                            Toast.makeText(getActivity(), R.string.toast_please_give_rating, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        return vRatingRoot;
    }

    private void initViews(View vRatingRoot) {
        lvReviews = (ListView) vRatingRoot.findViewById(R.id.lvReviews);
        tvMoreReviews = (TextView) vRatingRoot.findViewById(R.id.tvMoreReviews);
        tvNoReviews = (TextView) vRatingRoot.findViewById(R.id.tvNoReviews);
        btnAddNewReview = (Button) vRatingRoot.findViewById(R.id.btnAddNewReview);

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
        void onFragmentInteraction(int uri);
    }
}
