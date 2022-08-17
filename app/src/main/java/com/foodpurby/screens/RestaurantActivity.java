package com.foodpurby.screens;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.foodpurby.R;
import com.foodpurby.adapters.RestaurantNamesAdapter;
import com.foodpurby.adapters.ViewPagerAdapter;
import com.foodpurby.baseclass.MyActivity;
import com.foodpurby.events.FilterIconVisibilityEvent;
import com.foodpurby.events.RestaurantNamesEvent;
import com.foodpurby.events.RestaurantsCountEvent;
import com.foodpurby.events.RestaurantsLoadedEvent;
import com.foodpurby.events.RestaurantsRefreshEvent;
import com.foodpurby.events.RestaurantsSearchEvent;
import com.foodpurby.model.DAORestaurantBranchItems;
import com.foodpurby.model.DAORestaurantNames;
import com.foodpurby.model.DAOShops;
import com.foodpurby.ondbstorage.DBActionsCategory;
import com.foodpurby.ondbstorage.DBActionsCuisine;
import com.foodpurby.ondbstorage.DBActionsGroup;
import com.foodpurby.ondbstorage.DBActionsIngredients;
import com.foodpurby.ondbstorage.DBActionsIngredientsCategory;
import com.foodpurby.ondbstorage.DBActionsProducts;
import com.foodpurby.ondbstorage.DBActionsRestaurantBranch;
import com.foodpurby.ondbstorage.RestaurantBranch;
import com.foodpurby.util.Common;
import com.foodpurby.util.CustomProgressDialog;
import com.foodpurby.utillities.AppConfig;
import com.foodpurby.utillities.AppSharedValues;
import com.foodpurby.utillities.SessionManager;
import com.sloop.fonts.FontsManager;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class RestaurantActivity extends AppCompatActivity implements View.OnClickListener {


    private EventBus bus = EventBus.getDefault();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    SearchView searchView = null;
    private Common uCommon;
    private int pageNo = 1, mPreLast, scrollX, scrollY;
    private ListView mRestaurantNameLists;
    private LinearLayout llChangeFilter;
    private Button btnChangeFilter;
    private LinearLayout llCityArea;
    private TextView tvAreaCityName, tvErrorMsg;
    private CustomProgressDialog mCustomProgressDialog;
    JSONObject mJsonObject;
    ArrayList<List<DAORestaurantNames.Restaurant.Restaurants>> mBulkRestaurantDetails = new ArrayList<List<DAORestaurantNames.Restaurant.Restaurants>>();
    List<DAORestaurantNames.Restaurant.Restaurants> mRestaurantNames = new ArrayList<DAORestaurantNames.Restaurant.Restaurants>();
    private RestaurantNamesAdapter aRestaurantNamesAdapter;
    int mPosition;
    Toolbar mToolbar;
    TextView mToolbarTitle;
    private String[] stockArr;
    FrameLayout flFilter;

    private Boolean isFinished = false;
    private FrameLayout framelayout;
    private ViewPager viewPager;
    private CirclePageIndicator mIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_home);

      //  FontsManager.initFormAssets(RestaurantActivity.this, "Lato-Light.ttf");
      //  FontsManager.changeFonts(RestaurantActivity.this);

        mToolbar = (Toolbar) findViewById(R.id.tb_header);
        setSupportActionBar(mToolbar);

        mToolbarTitle = (TextView) mToolbar.findViewById(R.id.tool_title);


        if (AppSharedValues.getCategory().equalsIgnoreCase("1")) {
            mToolbarTitle.setText("Restaurants");
        } else if (AppSharedValues.getCategory().equalsIgnoreCase("4")) {
            mToolbarTitle.setText("Super markets");
        } else {
            mToolbarTitle.setText("Home");
        }

        mToolbar.setNavigationIcon(R.drawable.back);
        //   setSupportActionBar(mToolbar);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mRestaurantNameLists = (ListView) findViewById(R.id.ARH_LV_restaurants_name_lists);
        llChangeFilter = (LinearLayout) findViewById(R.id.llChangeFilter);
        btnChangeFilter = (Button) findViewById(R.id.btnChangeLocation);
        llCityArea = (LinearLayout) findViewById(R.id.llCityArea);
        tvAreaCityName = (TextView) findViewById(R.id.tvAreaCityName);
        tvErrorMsg = (TextView) findViewById(R.id.tvErrorMsg);
        framelayout = (FrameLayout) findViewById(R.id.AG_framelayout_home);
        mRestaurantNameLists.setDivider(null);
        uCommon = new Common(RestaurantActivity.this);
        mCustomProgressDialog = CustomProgressDialog.getInstance();
        mCustomProgressDialog.show(RestaurantActivity.this);
        mJsonObject = new JSONObject();

        if (AppSharedValues.getCategory().equalsIgnoreCase("0")) {
            btnChangeFilter.setText(getResources().getString(R.string.txt_find_nearby) + "  shops");
            //  tvErrorMsg.setText("No Shops found , please try again sometime");
        } else if (AppSharedValues.getCategory().equalsIgnoreCase("1")) {
            btnChangeFilter.setText(getResources().getString(R.string.txt_find_nearby) + "  Restaurants");
            tvErrorMsg.setText("No Restaurants found , please try again sometime");
        } else {
            btnChangeFilter.setText(getResources().getString(R.string.txt_find_nearby) + "  Supermarkets");
            tvErrorMsg.setText("No Supermarkets found , please try again sometime");
        }

        if (uCommon.isInternetConnected(RestaurantActivity.this)) {
            try {
                mJsonObject.put("pageno", pageNo).toString();
                System.out.println("Res Json: " + mJsonObject.toString());
                GenerateRestaurantNameListAdapter();
                mCustomProgressDialog.dismiss();

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            startActivity(new Intent(RestaurantActivity.this, OfflineDataSerivces.class));
            mCustomProgressDialog.dismiss();
        }

        llCityArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getFragmentManager().beginTransaction().replace(R.id.container,new CityAreaFragment()).commit();
                finish();
                //MyActivity.DisplayCityArea(RestaurantActivity.this);
            }
        });
        btnChangeFilter.setOnClickListener(this);
        flFilter = (FrameLayout) findViewById(R.id.flFilter);
        flFilter.setVisibility(View.VISIBLE);


        flFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyActivity.DisplayRestaurantFilter(RestaurantActivity.this);
            }
        });

        setAreaCity();

        bus.post(new FilterIconVisibilityEvent(View.VISIBLE, FilterIconVisibilityEvent.PageTitleType.Home));


    }


    private void setAreaCity() {
        String areaName = AppSharedValues.getArea() == null ? "Choose Area here" : AppSharedValues.getArea().getArea_name() + ", ";
        String cityName = AppSharedValues.getCity() == null ? "" : AppSharedValues.getCity().getCity_name();
        tvAreaCityName.setText(areaName + cityName);
    }

    private void GenerateRestaurantNameListAdapter() {
        try {
//            Common.GetRestaurants(RestaurantActivity.this, mCustomProgressDialog, bus);


            aRestaurantNamesAdapter = new RestaurantNamesAdapter(RestaurantActivity.this, bus);
            mRestaurantNameLists.setAdapter(aRestaurantNamesAdapter);
            aRestaurantNamesAdapter.notifyChanges();

            mRestaurantNameLists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    mPosition = position;
//                    bus.post(new FilterIconVisibilityEvent(View.GONE, FilterIconVisibilityEvent.PageTitleType.Menu));
                    Map<String, String> clearFilter = new HashMap<String, String>();
                    AppSharedValues.setFilterSelectedCuisines(clearFilter);

                    try {
                        if (!mCustomProgressDialog.isShowing()) {
                            mCustomProgressDialog.ChageMessage(RestaurantActivity.this, getString(R.string.dialog_please_wait), getString(R.string.dialog_please_wait));
                            mCustomProgressDialog.show(RestaurantActivity.this);
                        }

                        RestaurantNamesAdapter.ViewHolder viewHolder = ((RestaurantNamesAdapter.ViewHolder) view.getTag());
                        final String mRestaurantKey = ((RestaurantBranch) viewHolder.mRestaurantName.getTag()).getRestaurantKey();

                        DAORestaurantBranchItems.getInstance().Callresponse("", mRestaurantKey,
                                AppSharedValues.getCategory(),
                                new Callback<DAORestaurantBranchItems.RestaurantBranchItems>() {
                                    @Override
                                    public void success(DAORestaurantBranchItems.RestaurantBranchItems restaurantBranchItems, Response response) {

                                /*if (mCustomProgressDialog.isShowing()) {
                                    mCustomProgressDialog.dismiss();
                                }*/
                                        int count = 1;
                                        if (restaurantBranchItems.getHttpcode().equals(200)) {
                                            AppSharedValues.setSelectedRestaurantItemsFromServer(restaurantBranchItems);

                                            AsyncTaskRunner runner = new AsyncTaskRunner();
                                            runner.execute(mRestaurantKey);
                                            bus.post(new RestaurantNamesEvent());


                                        }
                                    }

                                    @Override
                                    public void failure(RetrofitError error) {
                                        error.printStackTrace();
                                        Toast.makeText(RestaurantActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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

            mRestaurantNameLists.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {

                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    int lastItem = firstVisibleItem + visibleItemCount;
                    if (pageNo != 0) {
                        if (mPreLast != lastItem) {
                            mPreLast = lastItem;

                            if (lastItem == totalItemCount) {
                                int pagenation = pageNo + 1;

                                try {
                                    mJsonObject.put("pageno", pagenation);
                                    scrollX = mRestaurantNameLists.getScrollX();
                                    scrollY = mRestaurantNameLists.getScrollY();
                                    //GenerateRestaurantNameListAdapterApi(mJsonObject.toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Do something after 100ms


                    String mStrREsponse1 = SessionManager.getInstance().getBannerImage(RestaurantActivity.this);

                    if(!mStrREsponse1.equals("")) {
                        viewPager = (ViewPager) findViewById(R.id.AG_view_pager_banner1);
                        mIndicator = (CirclePageIndicator) findViewById(R.id.AG_indicator1);

                        DAOShops.Restaurant CommonLanguagefiles = AppConfig.StringToObject(mStrREsponse1);
                        List<String> mImages = new ArrayList<>();

                        if (CommonLanguagefiles.getData() != null) {
                            if (CommonLanguagefiles.getData().getBanner_list_image().size() > 0)
                                for (int count = 0; count < CommonLanguagefiles.getData().getBanner_list_image().size(); count++) {

                                    mImages.add(CommonLanguagefiles.getData().getBanner_list_image().get(count));

                                }
                        }
                  /*  if (CommonLanguagefiles.getData().getBanner_list_image().size()!= 0) {
                        framelayout.setVisibility(View.VISIBLE);


                    }
                    else {
                        framelayout.setVisibility(View.GONE);
                    }*/


                        stockArr = new String[mImages.size()];
                        stockArr = mImages.toArray(stockArr);


                        final ViewPagerAdapter adapter = new ViewPagerAdapter(RestaurantActivity.this, stockArr);
                        // Binds the Adapter to the ViewPager
                        viewPager.setAdapter(adapter);
                        // ViewPager Indicator
                        mIndicator.setViewPager(viewPager);


                        final int[] currentPage = new int[1];
                        final Handler handler = new Handler();

                        final Runnable update = new Runnable() {
                            public void run() {
                                if (currentPage[0] == stockArr.length) {
                                    currentPage[0] = 0;
                                }
                                viewPager.setCurrentItem(currentPage[0]++, true);
                            }
                        };


                        new Timer().schedule(new TimerTask() {

                            @Override
                            public void run() {
                                handler.post(update);
                            }
                        }, 2000, 3000);
                    }

                }
            }, 1000);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnChangeLocation) {
            finish();
        }
    }

    private class AsyncTaskRunner extends AsyncTask<String, String, String> {
        Integer count = 0;
        private String resp;
        String mRestaurantKey = "";

        @Override
        protected String doInBackground(String... params) {

            try {

                mRestaurantKey = params[0];
                if (AppSharedValues.getSelectedRestaurantItemsFromServer() != null) {

                    DBActionsCuisine.deleteAll();
                    DBActionsGroup.deleteAll();
                    DBActionsCategory.deleteAll();
                    DBActionsIngredientsCategory.deleteAll();
                    DBActionsIngredients.deleteAll();

                    List<DAORestaurantBranchItems.Shop_detail> restaurantDetails = AppSharedValues.getSelectedRestaurantItemsFromServer().getData().getShop_detail();
                    for (DAORestaurantBranchItems.Shop_detail restaurantDetail : restaurantDetails) {

                        if (restaurantDetail != null) {
//                            Integer deliveryFee = restaurantDetail.getDelivery_fee() == null ? 0 : restaurantDetail.getDelivery_fee();
                            Integer minOrderValue = restaurantDetail.getMin_order_value() == null ? 0 : restaurantDetail.getMin_order_value();

                            AppSharedValues.setSelectedRestaurantBranchOfferText(restaurantDetail.getOffer_text());
                            AppSharedValues.setSelectedRestaurantBranchOfferCode(restaurantDetail.getOffer_code());
                            AppSharedValues.setSelectedRestaurantBranchOfferMinOrderValue(Double.valueOf(restaurantDetail.getOffer_min_order_value()));
                            SessionManager.getInstance().setLatitute(RestaurantActivity.this, String.valueOf(restaurantDetail.getLatitude()));
                            SessionManager.getInstance().setLongtitue(RestaurantActivity.this, String.valueOf(restaurantDetail.getLongitude()));
                            SessionManager.getInstance().setAddress(RestaurantActivity.this,restaurantDetail.getLocation());
                            AppSharedValues.setSelectedRestaurantBranchKey(restaurantDetail.getShop_key());
                            AppSharedValues.setSelectedRestaurantBranchName(restaurantDetail.getShop_name());

                            AppSharedValues.setCerfiticateImage(restaurantDetail.getCertificateimage());

                            if (restaurantDetail.getShop_status().trim().equalsIgnoreCase("closed")) {

                                AppSharedValues.setSelectedRestaurantBranchStatus(AppSharedValues.ShopStatus.Closed);
                            } else {
                                AppSharedValues.setSelectedRestaurantBranchStatus(AppSharedValues.ShopStatus.Open);
                            }

                            //AppSharedValues.setSelectedRestaurantBranchStatus(AppSharedValues.ShopStatus.Closed);

//                            AppSharedValues.setDeliveryCharge(Double.valueOf(deliveryFee));
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

                                    DBActionsProducts.add(restaurantDetail.getShop_key(), category.getCategory_key(), menuItem.getItem_key(), menuItem.getItem_name(), menuItem.getItem_description(), menuItem.getItem_image(), category.getCategory_key(), Double.valueOf(menuItem.getPrice()), Double.valueOf(menuItem.getPrice()), Double.valueOf(menuItem.getPrice()), 0D, //Integer.valueOf(menuItem.getItem_type()
                                            0);

                                    List<DAORestaurantBranchItems.Ingredient> ingredients = menuItem.getIngredients();
                                    if (ingredients != null) {
                                        for (DAORestaurantBranchItems.Ingredient ingredient : ingredients) {
                                            DBActionsIngredientsCategory.add(restaurantDetail.getShop_key(), menuItem.getItem_key(), String.valueOf(ingredient.getIngredients_type_id()), ingredient.getIngredients_type_name(), ingredient.getMinimum(), ingredient.getMaximum(), ingredient.getRequired(), 0D);

                                            for (DAORestaurantBranchItems.Ingredients_list ingredientList : ingredient.getIngredients_list()) {

                                                DBActionsIngredients.add(restaurantDetail.getShop_key(), menuItem.getItem_key(), String.valueOf(ingredientList.getItem_ingredients_list_id()), ingredientList.getIngredient_name(), String.valueOf(ingredient.getIngredients_type_id()), String.valueOf(ingredient.getIngredients_type_id()), Double.valueOf(ingredientList.getPrice()));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return resp;
        }

        @Override
        protected void onPostExecute(String result) {

            if (mCustomProgressDialog.isShowing()) {
                mCustomProgressDialog.dismiss();
            }

            if (count > 0) {

                Bundle bundle = new Bundle();
                bundle.putString("restaurantKey", mRestaurantKey);

                Intent mIntent = new Intent(RestaurantActivity.this, RestaurantMenuActivity.class);
                mIntent.putExtra("Position", mPosition);
                startActivity(mIntent);
                AppSharedValues.setSelectedRestaurantBranchKey(mRestaurantKey);

            } else {
                Toast.makeText(RestaurantActivity.this, R.string.toast_no_item_to_show, Toast.LENGTH_SHORT).show();
            }

        }


        @Override
        protected void onPreExecute() {
            // Things to be done before execution of long running operation. For
            // example showing ProgessDialog
        }


        @Override
        protected void onProgressUpdate(String... text) {
        }
    }


    public void onEvent(final RestaurantsLoadedEvent ev) {
        RestaurantActivity.this.runOnUiThread(new Runnable() {
            public void run() {

                if (DBActionsRestaurantBranch.get().size() == 0) {
                    llChangeFilter.setVisibility(View.VISIBLE);
                    framelayout.setVisibility(View.GONE);

                } else {
                    llChangeFilter.setVisibility(View.INVISIBLE);
                    framelayout.setVisibility(View.VISIBLE);
                }

                aRestaurantNamesAdapter.notifyChanges();
            }
        });
    }

    public void onEvent(final RestaurantsSearchEvent ev) {
        RestaurantActivity.this.runOnUiThread(new Runnable() {
            public void run() {

                if (DBActionsRestaurantBranch.get().size() == 0) {
                    llChangeFilter.setVisibility(View.VISIBLE);
                } else {
                    llChangeFilter.setVisibility(View.INVISIBLE);
                }

                aRestaurantNamesAdapter.notifyChanges();
            }
        });
    }

    public void onEvent(final RestaurantsRefreshEvent ev) {
        RestaurantActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                setAreaCity();
                //                MyActivity.DisplayHomePage(getActivity());
            }
        });
    }

    int count = 0;

    public void onEvent(final RestaurantsCountEvent ev) {
        RestaurantActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                /*if (!mCustomProgressDialog.isShowing() && !isFinished) {
                    mCustomProgressDialog.ChageMessage(getActivity(), "Please wait...", "Please wait...");
                    mCustomProgressDialog.show(getActivity());
                }*/

                if (ev.getTotalRestaurants() <= 0) {
                    if (count >= 2) {
                        mRestaurantNameLists.setVisibility(View.GONE);
                        //llChangeFilter.setVisibility(View.VISIBLE);
                    } else {
                        //do nothing
                    }
                    count = count + 1;
                } else {
                    mRestaurantNameLists.setVisibility(View.VISIBLE);
                    //llChangeFilter.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (aRestaurantNamesAdapter != null) {
            aRestaurantNamesAdapter.notifyChanges();
        }
        EventBus.getDefault().register(this);

       /* RestaurantActivity.this.runOnUiThread(new Runnable() {
            public void run() {

                if (DBActionsRestaurantBranch.get().size() == 0) {
                    // llChangeFilter.setVisibility(View.VISIBLE);
                } else {
                    llChangeFilter.setVisibility(View.INVISIBLE);
                }

                aRestaurantNamesAdapter.notifyChanges();
            }
        });*/
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { // Inflate the menu;
        this.getMenuInflater().inflate(R.menu.main, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        if (searchItem != null) {
            searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
            MenuItemCompat.collapseActionView(searchItem);
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        }

        if (searchView != null) {
            // Detect SearchView icon clicks
            searchView.setOnSearchClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //mToolbarTitle.setVisibility(View.GONE);
                    bus.post(new FilterIconVisibilityEvent(View.GONE, FilterIconVisibilityEvent.PageTitleType.None));
                    bus.post(new RestaurantsSearchEvent());
                }
            });

            // Detect SearchView close
            searchView.setOnCloseListener(new SearchView.OnCloseListener() {
                @Override
                public boolean onClose() {
                    AppSharedValues.setRestaurantSearchKey("");
                    bus.post(new FilterIconVisibilityEvent(View.VISIBLE, FilterIconVisibilityEvent.PageTitleType.Home));
                    bus.post(new RestaurantsSearchEvent());

                    //mToolbarTitle.setVisibility(View.VISIBLE);
                    return false;
                }
            });

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    if (!query.equals("")) {

                    }
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    //bus.post(new SearchContact(newText));
                    AppSharedValues.setRestaurantSearchKey(newText);
                    bus.post(new RestaurantsSearchEvent());
                    return true;
                }
            });
        }

        super.onPrepareOptionsMenu(menu);

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        try {
            MenuItem searchViewMenuItem = menu.findItem(R.id.action_search);
            searchView = (SearchView) MenuItemCompat.getActionView(searchViewMenuItem);
            int searchImgId = R.id.search_button; // I used the explicit layout ID of searchview's ImageView
            ImageView v = (ImageView) searchView.findViewById(searchImgId);
            v.setImageResource(R.drawable.ic_search);
            int searchImgIdClose = R.id.search_close_btn; // I used the explicit layout ID of searchview's ImageView
            ImageView vClose = (ImageView) searchView.findViewById(searchImgIdClose);
            vClose.setImageResource(R.drawable.ic_close);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
        }
    }


}
