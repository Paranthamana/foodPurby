package com.foodpurby.screens;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.foodpurby.adapters.AddressAdapter;
import com.foodpurby.adapters.RestaurantFilterCuisineAdapter;
import com.foodpurby.adapters.RestaurantReviewAdapter;
import com.foodpurby.adapters.RestaurantTimingAdapter;
import com.foodpurby.events.RestaurantsLoadedEvent;
import com.foodpurby.model.DAORestaurantDetails;
import com.foodpurby.ondbstorage.DBActionsCuisineFilter;
import com.foodpurby.util.CustomProgressDialog;
import com.foodpurby.utillities.UserDetails;
import com.foodpurby.R;
import com.foodpurby.events.DummyEvent;
import com.foodpurby.ondbstorage.CuisineFilter;
import com.foodpurby.utillities.AppSharedValues;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;

import static com.foodpurby.R.id.tvNewRestaurant;
import static com.foodpurby.R.id.tvOpenRestaurant;
import static com.foodpurby.R.id.tvVoucharAccept;

public class RestaurantFilter extends AppCompatActivity {
    private CustomProgressDialog mCustomProgressDialog;
    private RestaurantFilterCuisineAdapter restaurantFilterCuisineAdapter;
    private EventBus bus = EventBus.getDefault();

    AlertDialog.Builder mDialog;
    AlertDialog mAlertDilaog;

    private ListView lvAddress;
    private AddressAdapter mpAdapter;
    Toolbar mToolbar;
    TextView mToolHeading, mTvSelectAllIntgrediants;
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

    ImageView ivRestaurantLogo;
    RatingBar rbRating;
    Button btnAddNewReview, btnSubmitReview;

    ImageView ivOnline;
    ImageView ivCOD;

    List<DAORestaurantDetails.Delivery_time_list> timing = new ArrayList<>();
    List<DAORestaurantDetails.Review> review = new ArrayList<>();
    private RestaurantTimingAdapter aRestaurantTimingAdapter;
    private RestaurantReviewAdapter aRestaurantReviewAdapter;

    LinearLayout llFavourite;
    ListView lvCuisines;
    Button btnApplyFilter;
    List<CuisineFilter> cuisineFilter = new ArrayList<>();
    TextView tvPopularity, tvRating, tvMinOrder, tvOpen, tvClose, tvVegetarian, tvFavourite, tvDeliveryFee, tvFastestDelivery;
    TextView tvOnlinePayment, tvPreorder, tvOpenRestaurants, tvVoucharAccepts, tvNewRestaurants;
    private RadioGroup tv_radiogroup;
    private RadioButton tv_purevegetarian;
    private RadioButton tv_nonvegetarian;
    private RadioButton tv_halal;
    private RadioButton tv_nonhalal;
    private RadioButton tv_parkfree;
    private RadioButton radioButton;
    private TextView vReset;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_filter);


        mCustomProgressDialog = CustomProgressDialog.getInstance();
        EventBus.getDefault().register(this);

        mToolbar = (Toolbar) findViewById(R.id.tb_header);
        mToolbar.setNavigationIcon(R.drawable.back);
        mToolHeading = (TextView) findViewById(R.id.tool_title);

        mToolHeading.setText(R.string.txt_filters);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        FrameLayout mShowCart = (FrameLayout) findViewById(R.id.flCart);
        mShowCart.setVisibility(View.GONE);

        FrameLayout flFilterReset = (FrameLayout) findViewById(R.id.flFilterReset);
        flFilterReset.setVisibility(View.VISIBLE);

        llFavourite = (LinearLayout) findViewById(R.id.llFavourite);
        lvCuisines = (ListView) findViewById(R.id.lvCuisines);
        btnApplyFilter = (Button) findViewById(R.id.btnApplyFilter);

        tvPopularity = (TextView) findViewById(R.id.tvPopularity);
        tvRating = (TextView) findViewById(R.id.tvRating);
        tvMinOrder = (TextView) findViewById(R.id.tvMinOrder);
        tvOpen = (TextView) findViewById(R.id.tvOpen);
        tvClose = (TextView) findViewById(R.id.tvClose);
        tvVegetarian = (TextView) findViewById(R.id.tvVegetarian);
        tvFavourite = (TextView) findViewById(R.id.tvFavourite);
        tvDeliveryFee = (TextView) findViewById(R.id.tvDeliveryFee);
        tvFastestDelivery = (TextView) findViewById(R.id.tvFastestDelivery);
        tvOnlinePayment = (TextView) findViewById(R.id.tvOnlinePayment);
        tvVoucharAccepts = (TextView) findViewById(tvVoucharAccept);
        tvNewRestaurants = (TextView) findViewById(tvNewRestaurant);
        tvOpenRestaurants = (TextView) findViewById(tvOpenRestaurant);
        tvPreorder = (TextView) findViewById(R.id.tvPreorder);

        tv_radiogroup = (RadioGroup) findViewById(R.id.tv_radiogroup);
        tv_purevegetarian = (RadioButton) findViewById(R.id.tv_purevegetarian);
        tv_nonvegetarian = (RadioButton) findViewById(R.id.tv_nonvegetarian);
        tv_halal = (RadioButton) findViewById(R.id.tv_halal);
        tv_nonhalal = (RadioButton) findViewById(R.id.tv_nonhalal);
        tv_parkfree = (RadioButton) findViewById(R.id.tv_parkfree);

        vReset = (TextView) findViewById(R.id.ARF_reset);


        View mFilterHeader = getLayoutInflater().inflate(R.layout.layout_filter_header, null);
        lvCuisines.addHeaderView(mFilterHeader);
        mTvSelectAllIntgrediants = (TextView) mFilterHeader.findViewById(R.id.tv_select_all);
        restaurantFilterCuisineAdapter = new RestaurantFilterCuisineAdapter(RestaurantFilter.this);
        lvCuisines.setAdapter(restaurantFilterCuisineAdapter);
        restaurantFilterCuisineAdapter.notifyChanges();


        if (!AppSharedValues.getSelectedRestaurantType().isEmpty()) {
            if (AppSharedValues.getSelectedRestaurantType().equals("0")) {
                tv_purevegetarian.setChecked(true);
                tv_nonvegetarian.setChecked(false);
                tv_halal.setChecked(false);
                tv_nonhalal.setChecked(false);
                tv_parkfree.setChecked(false);

            } else if (AppSharedValues.getSelectedRestaurantType().equals("1")) {
                tv_purevegetarian.setChecked(false);
                tv_nonvegetarian.setChecked(true);
                tv_halal.setChecked(false);
                tv_nonhalal.setChecked(false);
                tv_parkfree.setChecked(false);
            } else if (AppSharedValues.getSelectedRestaurantType().equals("2")) {
                tv_purevegetarian.setChecked(false);
                tv_nonvegetarian.setChecked(false);
                tv_halal.setChecked(true);
                tv_nonhalal.setChecked(false);
                tv_parkfree.setChecked(false);
            } else if (AppSharedValues.getSelectedRestaurantType().equals("3")) {
                tv_purevegetarian.setChecked(false);
                tv_nonvegetarian.setChecked(false);
                tv_halal.setChecked(false);
                tv_nonhalal.setChecked(true);
                tv_parkfree.setChecked(false);
            } else if (AppSharedValues.getSelectedRestaurantType().equals("4")) {
                tv_purevegetarian.setChecked(true);
                tv_nonvegetarian.setChecked(false);
                tv_halal.setChecked(false);
                tv_nonhalal.setChecked(false);
                tv_parkfree.setChecked(true);
            }

        }

        mTvSelectAllIntgrediants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(!v.isSelected());
                if (v.isSelected()) {
                    mTvSelectAllIntgrediants.setText(getString(R.string.txt_un_select));
                    mTvSelectAllIntgrediants.setTextColor(getResources().getColor(R.color.red));
                    AppSharedValues.setIsIntegrediantChecked(true);
                    cuisineFilter = DBActionsCuisineFilter.getCuisines();
                    for (int i = 0; i < cuisineFilter.size(); i++) {
                        AppSharedValues.FilterSelectedCuisines.put(cuisineFilter.get(i).getCuisineName(), cuisineFilter.get(i).getCuisineName());
                    }
                    restaurantFilterCuisineAdapter.notifyChanges();
                } else {
                    AppSharedValues.setIsIntegrediantChecked(false);
                    AppSharedValues.getFilterSelectedCuisines().clear();
                    mTvSelectAllIntgrediants.setText(getString(R.string.txt_select_all));
                    mTvSelectAllIntgrediants.setTextColor(getResources().getColor(R.color.black));
                    restaurantFilterCuisineAdapter.notifyChanges();
                }
            }
        });


        btnApplyFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.getInstance().trackEvent("Button", "Click", "Filter");
                AppSharedValues.setIsShowFavouriteResturantOnly(tvFavourite.isSelected());
                AppSharedValues.setIsPureVeg(tvVegetarian.isSelected());
                AppSharedValues.setIsShowOnlinePayment(tvOnlinePayment.isSelected());
                AppSharedValues.setIsShowPreOrder(tvPreorder.isSelected());
                AppSharedValues.setIsShowOpenRestaurent(tvOpenRestaurants.isSelected());
                AppSharedValues.setIsShowVoucheraccept(tvVoucharAccepts.isSelected());
                AppSharedValues.setIsShowNewRestaurent(tvNewRestaurants.isSelected());

                if (tvPopularity.isSelected()) {
                    AppSharedValues.setIsSortChoosed(tvPopularity.isSelected());
//                    bus.post(new RestaurantNamesEvent());
                } else if (tvRating.isSelected()) {
                    AppSharedValues.setIsSortChoosed(tvRating.isSelected());
                } else if (tvMinOrder.isSelected()) {
                    AppSharedValues.setIsSortChoosed(tvMinOrder.isSelected());
                } else if (tvDeliveryFee.isSelected()) {
                    AppSharedValues.setIsSortChoosed(tvDeliveryFee.isSelected());
                } else if (tvFastestDelivery.isSelected()) {
                    AppSharedValues.setIsSortChoosed(tvFastestDelivery.isSelected());
                } else if (tvOpen.isSelected()) {
                    AppSharedValues.setIsSortChoosed(tvOpen.isSelected());
                } else {
                    AppSharedValues.setIsSortChoosed(tvClose.isSelected());
                }


                int selectedId = tv_radiogroup.getCheckedRadioButtonId();
                radioButton = (RadioButton) findViewById(selectedId);

                if (tv_purevegetarian.isChecked())
                    AppSharedValues.setSelectedRestaurantType("0");
                if (tv_nonvegetarian.isChecked())
                    AppSharedValues.setSelectedRestaurantType("1");
                if (tv_halal.isChecked())
                    AppSharedValues.setSelectedRestaurantType("2");
                if (tv_nonhalal.isChecked())
                    AppSharedValues.setSelectedRestaurantType("3");
                if (tv_parkfree.isChecked())
                    AppSharedValues.setSelectedRestaurantType("4");

                               /* if (selectedId == R.id.tv_purevegetarian) {
                                    AppSharedValues.setSelectedRestaurantType("0");
                                } else if (selectedId == R.id.tv_nonvegetarian) {
                                    AppSharedValues.setSelectedRestaurantType("1");
                                } else if (selectedId == R.id.tv_halal) {
                                    AppSharedValues.setSelectedRestaurantType("2");
                                } else if (selectedId == R.id.tv_nonhalal) {
                                    AppSharedValues.setSelectedRestaurantType("3");
                                } else if (selectedId == R.id.tv_parkfree) {
                                    AppSharedValues.setSelectedRestaurantType("4");
                                }*/


//                bus.post(new FilterIconVisibilityEvent(View.VISIBLE, FilterIconVisibilityEvent.PageTitleType.Home));
                AppSharedValues.setFilterSelectedCuisines(AppSharedValues.getFilterSelectedCuisines());
                bus.post(new RestaurantsLoadedEvent());
                finish();
            }
        });

        if (!UserDetails.getCustomerKey().trim().isEmpty()) {
            llFavourite.setVisibility(View.GONE);
        }


        vReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.getInstance().trackEvent("FrameLayout", "Click", "Reset");
                tvPopularity.setSelected(false);
                tvPopularity.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);
                tvRating.setSelected(false);
                tvRating.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);
                tvMinOrder.setSelected(false);
                tvMinOrder.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);
                tvDeliveryFee.setSelected(false);
                tvDeliveryFee.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);
                tvFastestDelivery.setSelected(false);
                tvFastestDelivery.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);

                tvPreorder.setSelected(false);
                tvPreorder.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);

                tvOnlinePayment.setSelected(false);
                tvOnlinePayment.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);

                tvOpenRestaurants.setSelected(false);
                tvOpenRestaurants.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);


                tvVoucharAccepts.setSelected(false);
                tvVoucharAccepts.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);

                tvNewRestaurants.setSelected(false);
                tvNewRestaurants.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);


                tvOpen.setSelected(false);
                tvOpen.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);
                tvClose.setSelected(false);
                tvClose.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);
                mTvSelectAllIntgrediants.setText(getString(R.string.txt_select_all));
                AppSharedValues.getFilterSelectedCuisines().clear();
                AppSharedValues.setIsIntegrediantChecked(false);
                restaurantFilterCuisineAdapter.notifyChanges();
                tvVegetarian.setSelected(false);
                tvVegetarian.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);
                mTvSelectAllIntgrediants.setTextColor(getResources().getColor(R.color.black));
                tvFavourite.setSelected(false);
                tvFavourite.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_list_fav_normal, 0, 0);
                Map<String, String> selectedCuisinesReset = new HashMap<String, String>();
                AppSharedValues.setIsShowFavouriteResturantOnly(false);
                AppSharedValues.setIsPureVeg(false);
                AppSharedValues.setFilterSelectedCuisines(selectedCuisinesReset);
                AppSharedValues.setIsSortChoosed(false);

                restaurantFilterCuisineAdapter = new RestaurantFilterCuisineAdapter(RestaurantFilter.this);
                lvCuisines.setAdapter(restaurantFilterCuisineAdapter);
                restaurantFilterCuisineAdapter.notifyChanges();

                tv_purevegetarian.setChecked(false);
                tv_nonvegetarian.setChecked(false);
                tv_halal.setChecked(false);
                tv_nonhalal.setChecked(false);
                tv_parkfree.setChecked(false);

                AppSharedValues.setSelectedRestaurantType("");
                bus.post(new RestaurantsLoadedEvent());
            }
        });
        flFilterReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.getInstance().trackEvent("FrameLayout", "Click", "Reset");
                tvPopularity.setSelected(false);
                tvPopularity.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);
                tvRating.setSelected(false);
                tvRating.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);
                tvMinOrder.setSelected(false);
                tvMinOrder.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);
                tvDeliveryFee.setSelected(false);
                tvDeliveryFee.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);
                tvFastestDelivery.setSelected(false);
                tvFastestDelivery.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);

                tvPreorder.setSelected(false);
                tvPreorder.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);

                tvOnlinePayment.setSelected(false);
                tvOnlinePayment.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);

                tvOpenRestaurants.setSelected(false);
                tvOpenRestaurants.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);


                tvVoucharAccepts.setSelected(false);
                tvVoucharAccepts.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);

                tvNewRestaurants.setSelected(false);
                tvNewRestaurants.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);


                tvOpen.setSelected(false);
                tvOpen.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);
                tvClose.setSelected(false);
                tvClose.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);
                mTvSelectAllIntgrediants.setText(getString(R.string.txt_select_all));
                AppSharedValues.getFilterSelectedCuisines().clear();
                AppSharedValues.setIsIntegrediantChecked(false);
                restaurantFilterCuisineAdapter.notifyChanges();
                tvVegetarian.setSelected(false);
                tvVegetarian.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);
                mTvSelectAllIntgrediants.setTextColor(getResources().getColor(R.color.black));
                tvFavourite.setSelected(false);
                tvFavourite.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_list_fav_normal, 0, 0);
                Map<String, String> selectedCuisinesReset = new HashMap<String, String>();
                AppSharedValues.setIsShowFavouriteResturantOnly(false);
                AppSharedValues.setIsPureVeg(false);
                AppSharedValues.setFilterSelectedCuisines(selectedCuisinesReset);
                AppSharedValues.setIsSortChoosed(false);
                AppSharedValues.setSelectedRestaurantType("");
                restaurantFilterCuisineAdapter = new RestaurantFilterCuisineAdapter(RestaurantFilter.this);
                lvCuisines.setAdapter(restaurantFilterCuisineAdapter);
                restaurantFilterCuisineAdapter.notifyChanges();

                bus.post(new RestaurantsLoadedEvent());
            }
        });

        tvPopularity.setOnClickListener(btnClick);
        tvRating.setOnClickListener(btnClick);
        tvMinOrder.setOnClickListener(btnClick);
        tvDeliveryFee.setOnClickListener(btnClick);
        tvFastestDelivery.setOnClickListener(btnClick);

        tvOpen.setOnClickListener(btnClick);
        tvClose.setOnClickListener(btnClick);
        tvVegetarian.setOnClickListener(btnClick);
        tvFavourite.setOnClickListener(btnClick);

        tvPreorder.setOnClickListener(btnClick);
        tvOpenRestaurants.setOnClickListener(btnClick);
        tvVoucharAccepts.setOnClickListener(btnClick);
        tvNewRestaurants.setOnClickListener(btnClick);
        tvOnlinePayment.setOnClickListener(btnClick);

        if (AppSharedValues.isPureVeg()) {
            tvVegetarian.setSelected(AppSharedValues.isPureVeg());
            tvVegetarian.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_on, 0, 0);
        } else {
            tvVegetarian.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);
        }

        if (AppSharedValues.isShowFavouriteResturantOnly()) {
            tvFavourite.setSelected(AppSharedValues.isPureVeg());
            tvFavourite.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_list_fav_selected, 0, 0);
        } else {
            tvFavourite.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_list_fav_normal, 0, 0);
        }

        if (AppSharedValues.isSortChoosed()) {
            tvPopularity.setSelected(AppSharedValues.isSortChoosed());
            tvPopularity.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_on, 0, 0);
        } else {
            tvPopularity.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);
        }

        if (AppSharedValues.isShowFavouriteResturantOnly()) {
            tvRating.setSelected(AppSharedValues.isSortChoosed());
            tvRating.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_on, 0, 0);
        } else {
            tvRating.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);
        }
        if (AppSharedValues.isShowFavouriteResturantOnly()) {
            tvMinOrder.setSelected(AppSharedValues.isSortChoosed());
            tvMinOrder.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_on, 0, 0);
        } else {
            tvMinOrder.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);
        }
        if (AppSharedValues.isShowFavouriteResturantOnly()) {
            tvDeliveryFee.setSelected(AppSharedValues.isSortChoosed());
            tvDeliveryFee.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_on, 0, 0);
        } else {
            tvDeliveryFee.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);
        }
        if (AppSharedValues.isShowFavouriteResturantOnly()) {
            tvFastestDelivery.setSelected(AppSharedValues.isSortChoosed());
            tvFastestDelivery.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_on, 0, 0);
        } else {
            tvFastestDelivery.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);
        }
    }


    View.OnClickListener btnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            v.setSelected(!v.isSelected());
            MyApplication.getInstance().trackEvent("Button", "Click", "Favourite");
            if (v.getId() == R.id.tvFavourite) {
                if (v.isSelected()) {
                    ((TextView) v).setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_list_fav_selected, 0, 0);
                } else {
                    ((TextView) v).setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_list_fav_normal, 0, 0);
                }
            } else {
                if (v.isSelected()) {
                    ((TextView) v).setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_on, 0, 0);
                } else {
                    ((TextView) v).setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);
                }
            }


            if (v.getId() == R.id.tvPopularity) {
                tvRating.setSelected(false);
                tvMinOrder.setSelected(false);
                tvDeliveryFee.setSelected(false);
                tvFastestDelivery.setSelected(false);
                tvOpen.setSelected(false);
                tvClose.setSelected(false);

                tvRating.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);
                tvMinOrder.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);
                tvDeliveryFee.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);
                tvFastestDelivery.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);

                tvOpen.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);
                tvClose.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);

                tvPreorder.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);
                tvOnlinePayment.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);
                tvOpenRestaurants.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);
                tvVoucharAccepts.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);
                tvNewRestaurants.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);

            } else if (v.getId() == R.id.tvRating) {
                tvPopularity.setSelected(false);
                tvMinOrder.setSelected(false);
                tvDeliveryFee.setSelected(false);
                tvFastestDelivery.setSelected(false);
                tvOpen.setSelected(false);
                tvClose.setSelected(false);

                tvPopularity.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);
                tvMinOrder.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);
                tvDeliveryFee.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);
                tvFastestDelivery.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);

                tvOpen.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);
                tvClose.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);
                tvPreorder.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);
                tvOnlinePayment.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);
                tvOpenRestaurants.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);
                tvVoucharAccepts.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);
                tvNewRestaurants.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);

            } else if (v.getId() == R.id.tvMinOrder) {
                tvPopularity.setSelected(false);
                tvRating.setSelected(false);
                tvDeliveryFee.setSelected(false);
                tvFastestDelivery.setSelected(false);

                tvOpen.setSelected(false);
                tvClose.setSelected(false);

                tvPopularity.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);
                tvRating.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);
                tvDeliveryFee.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);
                tvFastestDelivery.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);

                tvOpen.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);
                tvClose.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);
                tvPreorder.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);
                tvOnlinePayment.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);
                tvOpenRestaurants.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);
                tvVoucharAccepts.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);
                tvNewRestaurants.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);

            } else if (v.getId() == R.id.tvDeliveryFee) {
                tvPopularity.setSelected(false);
                tvRating.setSelected(false);
                tvMinOrder.setSelected(false);
                tvFastestDelivery.setSelected(false);

                tvOpen.setSelected(false);
                tvClose.setSelected(false);

                tvPopularity.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);
                tvRating.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);
                tvFastestDelivery.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);
                tvMinOrder.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);

                tvOpen.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);
                tvClose.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);
                tvPreorder.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);
                tvOnlinePayment.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);
                tvOpenRestaurants.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);
                tvVoucharAccepts.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);
                tvNewRestaurants.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);

            } else if (v.getId() == R.id.tvFastestDelivery) {
                tvPopularity.setSelected(false);
                tvRating.setSelected(false);
                tvDeliveryFee.setSelected(false);
                tvMinOrder.setSelected(false);

                tvOpen.setSelected(false);
                tvClose.setSelected(false);

                tvPopularity.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);
                tvRating.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);
                tvMinOrder.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);
                tvDeliveryFee.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);

                tvOpen.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);
                tvClose.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);
                tvOpenRestaurants.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);
                tvVoucharAccepts.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);
                tvNewRestaurants.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);

            } else if (v.getId() == R.id.tvPreorder) {

                tvOnlinePayment.setSelected(false);
                tvOpenRestaurants.setSelected(false);
                tvVoucharAccepts.setSelected(false);
                tvNewRestaurants.setSelected(false);


                tvOnlinePayment.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);
                tvOpenRestaurants.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);
                tvVoucharAccepts.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);
                tvNewRestaurants.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);

            } else if (v.getId() == R.id.tvOnlinePayment) {

                tvPreorder.setSelected(false);
                tvOpenRestaurants.setSelected(false);
                tvVoucharAccepts.setSelected(false);
                tvNewRestaurants.setSelected(false);


                tvPreorder.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);
                tvOpenRestaurants.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);
                tvVoucharAccepts.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);
                tvNewRestaurants.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);

            } else if (v.getId() == R.id.tvOpenRestaurant) {

                tvPreorder.setSelected(false);
                tvOnlinePayment.setSelected(false);
                tvVoucharAccepts.setSelected(false);
                tvNewRestaurants.setSelected(false);


                tvPreorder.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);
                tvOnlinePayment.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);
                tvVoucharAccepts.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);
                tvNewRestaurants.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);

            } else if (v.getId() == R.id.tvVoucharAccept) {

                tvPreorder.setSelected(false);
                tvOnlinePayment.setSelected(false);
                tvOpenRestaurants.setSelected(false);
                tvNewRestaurants.setSelected(false);


                tvPreorder.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);
                tvOnlinePayment.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);
                tvOpenRestaurants.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);
                tvNewRestaurants.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);

            } else if (v.getId() == R.id.tvNewRestaurant) {

                tvPreorder.setSelected(false);
                tvOnlinePayment.setSelected(false);
                tvVoucharAccepts.setSelected(false);
                tvOpenRestaurants.setSelected(false);


                tvPreorder.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);
                tvOnlinePayment.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);
                tvVoucharAccepts.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);
                tvOpenRestaurants.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);

            } else if (v.getId() == R.id.tvOpen) {
                tvPopularity.setSelected(false);
                tvRating.setSelected(false);
                tvMinOrder.setSelected(false);
                tvClose.setSelected(false);

                tvPopularity.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);
                tvRating.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);
                tvMinOrder.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);
                tvClose.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);
            } else if (v.getId() == R.id.tvClose) {
                tvPopularity.setSelected(false);
                tvRating.setSelected(false);
                tvMinOrder.setSelected(false);
                tvOpen.setSelected(false);

                tvPopularity.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);
                tvRating.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);
                tvMinOrder.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);
                tvOpen.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sort_by_off, 0, 0);
            }
        }
    };

    /*@Override
    protected void onResume() {
        super.onResume();
        restaurantFilterCuisineAdapter = new RestaurantFilterCuisineAdapter(RestaurantFilter.this);
        lvCuisines.setAdapter(restaurantFilterCuisineAdapter);
        restaurantFilterCuisineAdapter.notifyChanges();
    }*/

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void onEvent(final DummyEvent ev) {
    }
}