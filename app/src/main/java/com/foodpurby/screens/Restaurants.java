package com.foodpurby.screens;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sloop.fonts.FontsManager;
import com.foodpurby.ondbstorage.DBActionsCategory;
import com.foodpurby.ondbstorage.DBActionsGroup;
import com.foodpurby.ondbstorage.DBActionsProducts;
import com.foodpurby.ondbstorage.RestaurantBranch;
import com.foodpurby.HomeActivity;
import com.foodpurby.R;
import com.foodpurby.adapters.RestaurantNamesAdapter;
import com.foodpurby.events.FilterIconVisibilityEvent;
import com.foodpurby.events.RestaurantNamesEvent;
import com.foodpurby.events.RestaurantsCountEvent;
import com.foodpurby.events.RestaurantsLoadedEvent;
import com.foodpurby.events.RestaurantsRefreshEvent;
import com.foodpurby.events.RestaurantsSearchEvent;
import com.foodpurby.model.DAORestaurantBranchItems;
import com.foodpurby.model.DAORestaurantNames;
import com.foodpurby.ondbstorage.DBActionsCuisine;
import com.foodpurby.ondbstorage.DBActionsIngredients;
import com.foodpurby.ondbstorage.DBActionsIngredientsCategory;
import com.foodpurby.ondbstorage.DBActionsRestaurantBranch;
import com.foodpurby.util.Common;
import com.foodpurby.util.CustomProgressDialog;
import com.foodpurby.utillities.AppSharedValues;
import com.foodpurby.utillities.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Restaurants#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Restaurants extends Fragment {

    private EventBus bus = EventBus.getDefault();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Common uCommon;
    private int pageNo = 1, mPreLast, scrollX, scrollY;
    private ListView mRestaurantNameLists;
    private LinearLayout llChangeFilter;
    private Button btnChangeFilter;
    private LinearLayout llCityArea;
    private TextView tvAreaCityName, tvErrorMsg;
    private OnFragmentInteractionListener mListener;
    private CustomProgressDialog mCustomProgressDialog;
    JSONObject mJsonObject;
    ArrayList<List<DAORestaurantNames.Restaurant.Restaurants>> mBulkRestaurantDetails = new ArrayList<List<DAORestaurantNames.Restaurant.Restaurants>>();
    List<DAORestaurantNames.Restaurant.Restaurants> mRestaurantNames = new ArrayList<DAORestaurantNames.Restaurant.Restaurants>();
    private RestaurantNamesAdapter aRestaurantNamesAdapter;
    int mPosition;

    private Boolean isFinished = false;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Restaurants.
     */
    // TODO: Rename and change types and number of parameters
    public static Restaurants newInstance(String param1, String param2) {
        Restaurants fragment = new Restaurants();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public Restaurants() {
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
        View vRestaurantHome = inflater.inflate(R.layout.fragment_restaurant_home, container, false);

      //  FontsManager.initFormAssets(getActivity(), "Lato-Light.ttf");
      //  FontsManager.changeFonts(getActivity());

        HomeActivity.flFilter.setVisibility(View.VISIBLE);


        mRestaurantNameLists = (ListView) vRestaurantHome.findViewById(R.id.ARH_LV_restaurants_name_lists);
        llChangeFilter = (LinearLayout) vRestaurantHome.findViewById(R.id.llChangeFilter);
        btnChangeFilter = (Button) vRestaurantHome.findViewById(R.id.btnChangeLocation);
        llCityArea = (LinearLayout) vRestaurantHome.findViewById(R.id.llCityArea);
        tvAreaCityName = (TextView) vRestaurantHome.findViewById(R.id.tvAreaCityName);
        tvErrorMsg = (TextView) vRestaurantHome.findViewById(R.id.tvErrorMsg);
        mRestaurantNameLists.setDivider(null);
        uCommon = new Common(getActivity());
        mCustomProgressDialog = CustomProgressDialog.getInstance();
        mCustomProgressDialog.show(getActivity());
        mJsonObject = new JSONObject();

        if (AppSharedValues.getCategory().equalsIgnoreCase("0")) {
            btnChangeFilter.setText(getResources().getString(R.string.txt_find_nearby) + "  shops");
            //tvErrorMsg.setText("No Shops found , please try again sometime");
        } else if (AppSharedValues.getCategory().equalsIgnoreCase("1")) {
            btnChangeFilter.setText(getResources().getString(R.string.txt_find_nearby) + "  Restaurants");
            tvErrorMsg.setText("No Restaurants found , please try again sometime");
        } else {
            btnChangeFilter.setText(getResources().getString(R.string.txt_find_nearby) + "  Supermarkets");
            tvErrorMsg.setText("No Supermarkets found , please try again sometime");
        }

        if (uCommon.isInternetConnected(getActivity())) {
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
            startActivity(new Intent(getActivity(), OfflineDataSerivces.class));
            mCustomProgressDialog.dismiss();
        }


        llCityArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.container, new CityAreaFragment()).commit();
                //MyActivity.DisplayCityArea(getActivity());
            }
        });

        btnChangeFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.container, new CityAreaFragment()).commit();
            }
        });


        setAreaCity();


        bus.post(new FilterIconVisibilityEvent(View.VISIBLE, FilterIconVisibilityEvent.PageTitleType.Home));
        return vRestaurantHome;
    }


    private void setAreaCity() {
        String areaName = AppSharedValues.getArea() == null ? "Choose Area here" : AppSharedValues.getArea().getArea_name() + ", ";
        String cityName = AppSharedValues.getCity() == null ? "" : AppSharedValues.getCity().getCity_name();
        tvAreaCityName.setText(areaName + cityName);
    }

    private void GenerateRestaurantNameListAdapter() {
        try {
            //Common.GetRestaurants(getActivity(), mCustomProgressDialog, bus);

            aRestaurantNamesAdapter = new RestaurantNamesAdapter(getActivity(), bus);
            mRestaurantNameLists.setAdapter(aRestaurantNamesAdapter);
            aRestaurantNamesAdapter.notifyChanges();

            mRestaurantNameLists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    mPosition = position;
                    //bus.post(new FilterIconVisibilityEvent(View.GONE, FilterIconVisibilityEvent.PageTitleType.Menu));
                    Map<String, String> clearFilter = new HashMap<String, String>();
                    AppSharedValues.setFilterSelectedCuisines(clearFilter);

                    try {
                        if (!mCustomProgressDialog.isShowing()) {
                            mCustomProgressDialog.ChageMessage(getActivity(), getString(R.string.dialog_please_wait), getString(R.string.dialog_please_wait));
                            mCustomProgressDialog.show(getActivity());
                        }

                        RestaurantNamesAdapter.ViewHolder viewHolder = ((RestaurantNamesAdapter.ViewHolder) view.getTag());
                        final String mRestaurantKey = ((RestaurantBranch) viewHolder.mRestaurantName.getTag()).getRestaurantKey();

                        DAORestaurantBranchItems.getInstance().Callresponse("",
                                mRestaurantKey, AppSharedValues.getCategory(),
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
                                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
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
                            SessionManager.getInstance().setLatitute(getActivity(), String.valueOf(restaurantDetail.getLatitude()));
                            SessionManager.getInstance().setLongtitue(getActivity(), String.valueOf(restaurantDetail.getLongitude()));
                            SessionManager.getInstance().setAddress(getActivity(),restaurantDetail.getLocation());
                            AppSharedValues.setSelectedRestaurantBranchKey(restaurantDetail.getShop_key());
                            AppSharedValues.setSelectedRestaurantBranchName(restaurantDetail.getShop_name());

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

                                    DBActionsProducts.add(restaurantDetail.getShop_key(), category.getCategory_key(),
                                            menuItem.getItem_key(), menuItem.getItem_name(), menuItem.getItem_description(), menuItem.getItem_image(), category.getCategory_key(),
                                            Double.valueOf(menuItem.getPrice()), Double.valueOf(menuItem.getPrice()), Double.valueOf(menuItem.getPrice()),
                                            0D, //Integer.valueOf(menuItem.getItem_type()
                                            0);

                                    List<DAORestaurantBranchItems.Ingredient> ingredients = menuItem.getIngredients();
                                    if (ingredients != null) {
                                        for (DAORestaurantBranchItems.Ingredient ingredient : ingredients) {
                                            DBActionsIngredientsCategory.add(restaurantDetail.getShop_key(), menuItem.getItem_key(),
                                                    String.valueOf(ingredient.getIngredients_type_id()), ingredient.getIngredients_type_name(),
                                                    ingredient.getMinimum(), ingredient.getMaximum(), ingredient.getRequired(), 0D);

                                            for (DAORestaurantBranchItems.Ingredients_list ingredientList : ingredient.getIngredients_list()) {

                                                DBActionsIngredients.add(restaurantDetail.getShop_key(), menuItem.getItem_key(), String.valueOf(ingredientList.getItem_ingredients_list_id()),
                                                        ingredientList.getIngredient_name(), String.valueOf(ingredient.getIngredients_type_id()),
                                                        String.valueOf(ingredient.getIngredients_type_id()), Double.valueOf(ingredientList.getPrice()));
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

                Intent mIntent = new Intent(getActivity(), RestaurantMenuActivity.class);
                mIntent.putExtra("Position", mPosition);
                startActivity(mIntent);
                AppSharedValues.setSelectedRestaurantBranchKey(mRestaurantKey);


            } else {
                Toast.makeText(getActivity(), R.string.toast_no_item_to_show, Toast.LENGTH_SHORT).show();
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


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(int position) {
        if (mListener != null) {
            mListener.onFragmentInteraction(position);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        EventBus.getDefault().register(this);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        EventBus.getDefault().unregister(this);
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(int position);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    public void onEvent(final RestaurantsLoadedEvent ev) {
        getActivity().runOnUiThread(new Runnable() {
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

    public void onEvent(final RestaurantsSearchEvent ev) {
        getActivity().runOnUiThread(new Runnable() {
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
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                setAreaCity();
//                MyActivity.DisplayHomePage(getActivity());
            }
        });
    }

    int count = 0;

    public void onEvent(final RestaurantsCountEvent ev) {
        getActivity().runOnUiThread(new Runnable() {
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
    public void onResume() {
        super.onResume();
        if (aRestaurantNamesAdapter != null) {
            aRestaurantNamesAdapter.notifyChanges();
        }
    }
}