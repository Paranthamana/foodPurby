package com.foodpurby.screens;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.foodpurby.adapters.AddressAdapter;
import com.foodpurby.adapters.RestaurantReviewAdapterMore;
import com.foodpurby.model.DAORestaurantDetails;
import com.foodpurby.model.DAORestaurantNewReview;
import com.foodpurby.model.DAORestaurantReviews;
import com.foodpurby.util.CustomProgressDialog;
import com.foodpurby.utillities.AppSharedValues;
import com.foodpurby.utillities.UserDetails;
import com.foodpurby.R;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class RestaurantProfileReview extends AppCompatActivity {
    private CustomProgressDialog mCustomProgressDialog;
    AlertDialog.Builder mDialog;
    AlertDialog mAlertDilaog;

    private ListView lvAddress;
    private AddressAdapter mpAdapter;
    Toolbar mToolbar;
    TextView mToolHeading;
    TextView btnAddNew;

    TextView tvRestaurantName, tvCustomMessage;
    TextView tvAddress;

    ListView lvReviews;

    RatingBar rbRating;
    Button btnAddNewReview, btnSubmitReview;
    TextView tvCancel;

    List<DAORestaurantDetails.Delivery_time_list> timing = new ArrayList<>();
    List<DAORestaurantReviews.More_review> review = new ArrayList<>();
    private RestaurantReviewAdapterMore aRestaurantReviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_profile_review);
        mCustomProgressDialog = CustomProgressDialog.getInstance();

        mToolbar = (Toolbar) findViewById(R.id.tb_header);
        mToolbar.setNavigationIcon(R.drawable.back);
        mToolHeading = (TextView) findViewById(R.id.tool_title);
        mToolHeading.setText(R.string.txt_ratings);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        FrameLayout mShowCart = (FrameLayout) findViewById(R.id.flCart);
        mShowCart.setVisibility(View.GONE);

        tvRestaurantName = (TextView) findViewById(R.id.tvRestaurantName);
        tvAddress = (TextView) findViewById(R.id.tvAddress);

        btnAddNewReview = (Button) findViewById(R.id.btnAddNewReview);

        lvReviews = (ListView) findViewById(R.id.lvReviews);


        aRestaurantReviewAdapter = new RestaurantReviewAdapterMore(RestaurantProfileReview.this);
        lvReviews.setAdapter(aRestaurantReviewAdapter);

        if (UserDetails.getCustomerKey().isEmpty()) {
            btnAddNewReview.setVisibility(View.GONE);
        }

        btnAddNewReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.getInstance().trackEvent("Button", "Click", "Add New Reviews");
                mDialog = new AlertDialog.Builder(RestaurantProfileReview.this, R.style.MyAlertDialogStyle);
                final View vDialog = getLayoutInflater().inflate(R.layout.dialog_restaurant_review, null);
                mDialog.setView(vDialog);
                mAlertDilaog = mDialog.create();
                mAlertDilaog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                mAlertDilaog.show();
                tvCustomMessage = (TextView) vDialog.findViewById(R.id.tvCustomMessage);
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


                tvCancel = (TextView) vDialog.findViewById(R.id.tvCancel);
                tvCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mAlertDilaog != null && mAlertDilaog.isShowing()) {
                            mAlertDilaog.dismiss();
                        }
                    }
                });

                btnSubmitReview = (Button) vDialog.findViewById(R.id.btnSubmitReview);

                rbRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                        if(Math.round(rating) == 1 ) {
                            tvCustomMessage.setText(getString(R.string.txt_hated_it));
                        }
                        else if(Math.round(rating) == 2) {
                            tvCustomMessage.setText(getString(R.string.txt_disliked_it));
                        }
                        else if(Math.round(rating) == 3) {
                            tvCustomMessage.setText(getString(R.string.txt_its_ok));
                        }
                        else if(Math.round(rating) == 4) {
                            tvCustomMessage.setText(getString(R.string.txt_liked_it));
                        }
                        else if(Math.round(rating) == 5) {
                            tvCustomMessage.setText(getString(R.string.txt_loved_it));
                        }
                    }
                });
                btnSubmitReview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyApplication.getInstance().trackEvent("Button", "Click", "Review Submit");
                        if(Math.round(rbRating.getRating()) > 0) {
                        if (!mCustomProgressDialog.isShowing()) {
                            mCustomProgressDialog.ChageMessage(RestaurantProfileReview.this, getString(R.string.dialog_please_wait), getString(R.string.dialog_please_wait));
                            mCustomProgressDialog.show(RestaurantProfileReview.this);
                        }

                        DAORestaurantNewReview.getInstance().Callresponse("","en", AppSharedValues.getSelectedRestaurantBranchKey(),
                                UserDetails.getCustomerKey(), Integer.valueOf(String.valueOf(Math.round(rbRating.getRating()))), "", "", new Callback<DAORestaurantNewReview.RestaurantReview>() {

                                    @Override
                                    public void success(DAORestaurantNewReview.RestaurantReview restaurantReview, Response response) {
                                        if (restaurantReview.getHttpcode().equals("200") || restaurantReview.getHttpcode().equals(200)) {
                                            if (mCustomProgressDialog.isShowing()) {
                                                mCustomProgressDialog.dismiss();
                                            }

                                            LoadReviews();

                                            if (mAlertDilaog.isShowing()) {
                                                mAlertDilaog.dismiss();
                                            }

                                            Toast.makeText(RestaurantProfileReview.this, restaurantReview.getMessage(), Toast.LENGTH_SHORT).show();


                                        } else {
                                            Toast.makeText(RestaurantProfileReview.this, restaurantReview.getMessage(), Toast.LENGTH_SHORT).show();
                                            if (mCustomProgressDialog.isShowing()) {
                                                mCustomProgressDialog.dismiss();
                                            }
                                        }
                                    }

                                    @Override
                                    public void failure(RetrofitError error) {
                                        Toast.makeText(RestaurantProfileReview.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                                        if (mCustomProgressDialog.isShowing()) {
                                            mCustomProgressDialog.dismiss();
                                        }
                                    }
                                });
                        }
                        else {
                            Toast.makeText(RestaurantProfileReview.this, getString(R.string.toast_please_give_rating), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


        LoadReviews();
    }

    private void LoadReviews() {
        if (!mCustomProgressDialog.isShowing()) {
            mCustomProgressDialog.ChageMessage(RestaurantProfileReview.this, getString(R.string.dialog_please_wait), getString(R.string.dialog_please_wait));
            mCustomProgressDialog.show(RestaurantProfileReview.this);
        }
        DAORestaurantReviews.getInstance().Callresponse("", AppSharedValues.getSelectedRestaurantBranchKey(), "1", "asdf", AppSharedValues.getLanguage(), new Callback<DAORestaurantReviews.RestaurantReviews>() {

            @Override
            public void success(DAORestaurantReviews.RestaurantReviews restaurantReviews, Response response) {
                if (restaurantReviews.getHttpcode().equals(200)) {

                    review = restaurantReviews.getData().getMore_reviews();
                    aRestaurantReviewAdapter.setData(review);
                    aRestaurantReviewAdapter.notifyChanges();

                    setListViewHeightBasedOnChildren(lvReviews);
                    if (mCustomProgressDialog.isShowing()) {
                        mCustomProgressDialog.dismiss();
                    }
                } else {
                    Toast.makeText(RestaurantProfileReview.this, restaurantReviews.getMessage(), Toast.LENGTH_SHORT).show();
                    if (mCustomProgressDialog.isShowing()) {
                        mCustomProgressDialog.dismiss();
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                String ss = "";
                Toast.makeText(RestaurantProfileReview.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = listView.getPaddingTop() + listView.getPaddingBottom();
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            if (listItem instanceof ViewGroup) {
                listItem.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            }
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}