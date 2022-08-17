package com.foodpurby.screens;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.foodpurby.R;
import com.foodpurby.adapters.AddressAdapter;
import com.foodpurby.adapters.RestaurantReviewAdapter;
import com.foodpurby.adapters.RestaurantTimingAdapter;
import com.foodpurby.baseclass.MyActivity;
import com.foodpurby.model.DAORestaurantDetails;
import com.foodpurby.model.DAORestaurantNewReview;
import com.foodpurby.util.Common;
import com.foodpurby.util.CustomProgressDialog;
import com.foodpurby.utillities.AppConfig;
import com.foodpurby.utillities.AppSharedValues;
import com.foodpurby.utillities.LoadImages;
import com.foodpurby.utillities.UserDetails;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class RestaurantProfile extends AppCompatActivity {
    private CustomProgressDialog mCustomProgressDialog;
    AlertDialog.Builder mDialog;
    AlertDialog mAlertDilaog;

    private ListView lvAddress;
    private AddressAdapter mpAdapter;
    Toolbar mToolbar;
    TextView mToolHeading;
    TextView btnAddNew;

    TextView tvOnline;
    TextView tvCOD;

    TextView tvRestaurantName;
    TextView tvAddress;
    TextView tvTotalRating;
    TextView tvDistance;
    TextView tvDeliversIn;
    TextView tvRestaurantCuisines;

    ListView lvTiming;
    ListView lvReviews;
    TextView tvMoreReviews;
    TextView tvNoReviews;
    TextView tvRestaurantStatus;
    TextView tvMinOrder;

    ImageView ivRestaurantLogo;
    RatingBar rbRating;
    TextView tvCancel, tvCustomMessage;
    Button btnAddNewReview, btnSubmitReview;

    ImageView ivOnline;
    ImageView ivCOD;

    List<DAORestaurantDetails.Delivery_time_list> timing = new ArrayList<>();
    List<DAORestaurantDetails.Review> review = new ArrayList<>();
    private RestaurantTimingAdapter aRestaurantTimingAdapter;
    private RestaurantReviewAdapter aRestaurantReviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_profile);
        mCustomProgressDialog = CustomProgressDialog.getInstance();

        mToolbar = (Toolbar) findViewById(R.id.tb_header);
        mToolbar.setNavigationIcon(R.drawable.back);
        mToolHeading = (TextView) findViewById(R.id.tool_title);
        mToolHeading.setText(R.string.txt_getting_details);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        FrameLayout mShowCart = (FrameLayout) findViewById(R.id.flCart);
        mShowCart.setVisibility(View.GONE);

        tvOnline = (TextView) findViewById(R.id.tvOnline);
        tvCOD = (TextView) findViewById(R.id.tvCOD);

        tvRestaurantName = (TextView) findViewById(R.id.tvRestaurantName);
        tvAddress = (TextView) findViewById(R.id.tvAddress);
        tvTotalRating = (TextView) findViewById(R.id.tvTotalRating);
        tvDistance = (TextView) findViewById(R.id.tvDistance);
        tvMinOrder = (TextView) findViewById(R.id.tvMinOrder);
        tvDeliversIn = (TextView) findViewById(R.id.tvDeliversIn);

        ivOnline = (ImageView) findViewById(R.id.ivOnline);
        ivCOD = (ImageView) findViewById(R.id.ivCOD);

        tvRestaurantCuisines = (TextView) findViewById(R.id.tvRestaurantCuisines);

        tvMoreReviews = (TextView) findViewById(R.id.tvMoreReviews);
        tvNoReviews = (TextView) findViewById(R.id.tvNoReviews);
        btnAddNewReview = (Button) findViewById(R.id.btnAddNewReview);


        lvTiming = (ListView) findViewById(R.id.lvTiming);
        lvReviews = (ListView) findViewById(R.id.lvReviews);
        ivRestaurantLogo = (ImageView) findViewById(R.id.ivRestaurantLogo);
        tvRestaurantStatus = (TextView) findViewById(R.id.tvRestaurantStatus);

        aRestaurantTimingAdapter = new RestaurantTimingAdapter(RestaurantProfile.this);
        lvTiming.setAdapter(aRestaurantTimingAdapter);
        lvTiming.setOnTouchListener(new View.OnTouchListener() {
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
        aRestaurantReviewAdapter = new RestaurantReviewAdapter(RestaurantProfile.this);
        lvReviews.setAdapter(aRestaurantReviewAdapter);
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

        lvTiming.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                return (event.getAction() == MotionEvent.ACTION_MOVE);
            }
        });
        lvReviews.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                return (event.getAction() == MotionEvent.ACTION_MOVE);
            }
        });

        if (UserDetails.getCustomerKey().isEmpty()) {
            btnAddNewReview.setVisibility(View.GONE);
        }

        btnAddNewReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.getInstance().trackEvent("Button", "Click", "Add new Reviews");
                mDialog = new AlertDialog.Builder(RestaurantProfile.this, R.style.MyAlertDialogStyle);
                final View vDialog = getLayoutInflater().inflate(R.layout.dialog_restaurant_review, null);
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
                rbRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                        if(Math.round(rating) == 1 ) {
                            tvCustomMessage.setText(R.string.txt_hated_it);
                        }
                        else if(Math.round(rating) == 2) {
                            tvCustomMessage.setText(R.string.txt_disliked_it);
                        }
                        else if(Math.round(rating) == 3) {
                            tvCustomMessage.setText(R.string.txt_its_ok);
                        }
                        else if(Math.round(rating) == 4) {
                            tvCustomMessage.setText(R.string.txt_liked_it);
                        }
                        else if(Math.round(rating) == 5) {
                            tvCustomMessage.setText(R.string.txt_loved_it);
                        }
                    }
                });

                tvCancel = (TextView) vDialog.findViewById(R.id.tvCancel);
                tvCancel.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        if(mAlertDilaog.isShowing()) {
                            mAlertDilaog.dismiss();
                        }
                    }
                });

                btnSubmitReview = (Button) vDialog.findViewById(R.id.btnSubmitReview);
                btnSubmitReview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        MyApplication.getInstance().trackEvent("Button", "Click", "Submit Reviews");
                        if(Math.round(rbRating.getRating()) > 0) {


                            if (!mCustomProgressDialog.isShowing()) {
                                mCustomProgressDialog.ChageMessage(RestaurantProfile.this, "Please wait...", "Please wait...");
                                mCustomProgressDialog.show(RestaurantProfile.this);
                            }

                            DAORestaurantNewReview.getInstance().Callresponse("","en", AppSharedValues.getSelectedRestaurantBranchKey(),
                                    UserDetails.getCustomerKey(), Integer.valueOf(String.valueOf(Math.round(rbRating.getRating()))), "", "", new Callback<DAORestaurantNewReview.RestaurantReview>() {

                                        @Override
                                        public void success(DAORestaurantNewReview.RestaurantReview restaurantReview, Response response) {

                                            if (restaurantReview.getHttpcode().equals("200") || restaurantReview.getHttpcode().equals(200)) {
                                                if (mCustomProgressDialog.isShowing()) {
                                                    mCustomProgressDialog.dismiss();
                                                }

                                                if (mAlertDilaog.isShowing()) {
                                                    mAlertDilaog.dismiss();
                                                }

                                                Toast.makeText(RestaurantProfile.this, restaurantReview.getMessage(), Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(RestaurantProfile.this, restaurantReview.getMessage(), Toast.LENGTH_SHORT).show();
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
                                            Toast.makeText(RestaurantProfile.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                                            if (mCustomProgressDialog.isShowing()) {
                                                mCustomProgressDialog.dismiss();
                                            }

                                            if (mAlertDilaog.isShowing()) {
                                                mAlertDilaog.dismiss();
                                            }
                                        }
                                    });
                        }
                        else {
                            Toast.makeText(RestaurantProfile.this, R.string.toast_please_give_rating, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        tvMoreReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.getInstance().trackEvent("TextView", "Click", "More Reviews");
                MyActivity.DisplayRestaurantProfileReviews(RestaurantProfile.this);
            }
        });


        if(!mCustomProgressDialog.isShowing()) {
            mCustomProgressDialog.ChageMessage(RestaurantProfile.this, getString(R.string.dialog_please_wait), getString(R.string.dialog_please_wait));
            mCustomProgressDialog.show(RestaurantProfile.this);
        }

        DAORestaurantDetails.getInstance().Callresponse("", AppSharedValues.getSelectedRestaurantBranchKey(), AppSharedValues.getLanguage(), "asdf", new Callback<DAORestaurantDetails.RestaurantDetails>() {

            @Override
            public void success(DAORestaurantDetails.RestaurantDetails restaurantDetails, Response response) {
                if (restaurantDetails.getHttpcode().equals(200)) {


                    tvMinOrder.setText(Common.getPriceWithCurrencyCode(restaurantDetails.getData().getShop_info().get(0).getMin_order_value().toString()));
                    tvMinOrder.setTypeface(Typeface.DEFAULT);
                    tvDeliversIn.setText(restaurantDetails.getData().getShop_info().get(0).getDelivery_in().toString());
                    tvRestaurantCuisines.setText(restaurantDetails.getData().getShop_info().get(0).getCuisines());


                    tvRestaurantName.setText(restaurantDetails.getData().getShop_info().get(0).getShop_name());
                    tvAddress.setText(restaurantDetails.getData().getShop_info().get(0).getShop_address());

                    if(restaurantDetails.getData().getShop_info().get(0).getOnline_payment_available().equals(0)) {
                        ivCOD.setVisibility(View.GONE);
                        ivOnline.setVisibility(View.GONE);
                    }
                    else if(restaurantDetails.getData().getShop_info().get(0).getOnline_payment_available().equals(1)) {
                        ivCOD.setVisibility(View.GONE);
                        ivOnline.setVisibility(View.GONE);
                    }
                    else if(restaurantDetails.getData().getShop_info().get(0).getOnline_payment_available().equals(2)) {
                        ivOnline.setVisibility(View.GONE);
                        ivCOD.setVisibility(View.GONE);
                    }

                    tvDistance.setText(" - ");
                    tvTotalRating.setText(String.valueOf(AppConfig.decimalFormat.format(restaurantDetails.getData().getShop_info().get(0).getAvg_rating())));
                    if(restaurantDetails.getData().getShop_info().get(0).getOnline_payment_available().equals(0)) {
                        tvCOD.setVisibility(View.VISIBLE);
                        tvOnline.setVisibility(View.GONE);
                    }
                    else if(restaurantDetails.getData().getShop_info().get(0).getOnline_payment_available().equals(1)) {
                        tvCOD.setVisibility(View.VISIBLE);
                        tvOnline.setVisibility(View.GONE);
                    }
                    else if(restaurantDetails.getData().getShop_info().get(0).getOnline_payment_available().equals(2)) {
                        tvOnline.setVisibility(View.GONE);
                        tvCOD.setVisibility(View.VISIBLE);
                    }

                    LoadImages.show(RestaurantProfile.this, ivRestaurantLogo, restaurantDetails.getData().getShop_info().get(0).getShop_logo());
                    mToolHeading.setText(restaurantDetails.getData().getShop_info().get(0).getShop_name());

                    timing = restaurantDetails.getData().getShop_info().get(0).getDelivery_time_list();
                    review = restaurantDetails.getData().getShop_info().get(0).getReviews();

                    if(review.size() > 0) {
                        tvMoreReviews.setVisibility(View.VISIBLE);
                        tvNoReviews.setVisibility(View.GONE);
                    }
                    else {
                        tvMoreReviews.setVisibility(View.GONE);
                        tvNoReviews.setVisibility(View.VISIBLE);
                    }

                    tvRestaurantStatus.setVisibility(View.GONE);



                    aRestaurantTimingAdapter.setData(timing);
                    aRestaurantTimingAdapter.notifyChanges();

                    aRestaurantReviewAdapter.setData(review);
                    aRestaurantReviewAdapter.notifyChanges();

                    Common.setListViewHeightBasedOnChildren(lvTiming);
                    Common.setListViewHeightBasedOnChildren(lvReviews);

                    if (mCustomProgressDialog.isShowing()) {
                        mCustomProgressDialog.dismiss();
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(RestaurantProfile.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                if (mCustomProgressDialog.isShowing()) {
                    mCustomProgressDialog.dismiss();
                }
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_restaurant_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}