package com.foodpurby.screens;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sloop.fonts.FontsManager;
import com.foodpurby.R;
import com.foodpurby.adapters.FavRestaurantListAdapter;
import com.foodpurby.model.DAORestFavApi;
import com.foodpurby.util.CustomProgressDialog;
import com.foodpurby.utillities.AppSharedValues;
import com.foodpurby.utillities.UserDetails;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class RestaurantFavList extends AppCompatActivity {
    ListView favlist;
    TextView noRest;
    Toolbar mToolbar;
    private ConnectivityManager cm;
    private NetworkInfo netInfo;
    TextView mToolHeading;
    Context mContext;
    private CustomProgressDialog mCustomProgressDialog;
    private FavRestaurantListAdapter mpFavadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fav_rest_list);
        //FontsManager.initFormAssets(this, "Lato-Light.ttf");
       // FontsManager.changeFonts(this);
        mContext = this;
        mCustomProgressDialog = CustomProgressDialog.getInstance();
        favlist = (ListView) findViewById(R.id.lv_fav_list);
        noRest = (TextView) findViewById(R.id.txt_no_list);
        mToolbar = (Toolbar) findViewById(R.id.tb_header);
        mToolbar.setNavigationIcon(R.drawable.back);
        mToolHeading = (TextView) findViewById(R.id.tool_title);
        mToolHeading.setText(getString(R.string.txt_my_fav_rest));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        netInfo = cm.getActiveNetworkInfo();
        getFavList();
    }

    private void getFavList() {
        cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        netInfo = cm.getActiveNetworkInfo();
        if (netInfo == null) {
            Toast.makeText(RestaurantFavList.this, "Please Enable Internet Connection", Toast.LENGTH_LONG).show();
        } else {
            if (!UserDetails.getCustomerKey().isEmpty()) {
                if (!mCustomProgressDialog.isShowing()) {
                    mCustomProgressDialog.ChageMessage(RestaurantFavList.this, "Please wait...", "Please wait...");
                    mCustomProgressDialog.show(RestaurantFavList.this);
                }
                DAORestFavApi.getInstance().Callresponse(AppSharedValues.getLanguage(), UserDetails.getCustomerKey(), new Callback<DAORestFavApi.FavList>() {
                    @Override
                    public void success(DAORestFavApi.FavList favList, Response response) {
                        if (mCustomProgressDialog.isShowing()) {
                            mCustomProgressDialog.dismiss();
                        }
                        if (favList.getHttpcode().equalsIgnoreCase("200")) {
                            int count = favList.getData().size();
                            if (count > 0) {
                                mpFavadapter = new FavRestaurantListAdapter(mContext, favList.getData());
                                favlist.setAdapter(mpFavadapter);
                            }
                        } else {
                            noRest.setVisibility(View.VISIBLE);
                            Toast.makeText(mContext, favList.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        if (mCustomProgressDialog.isShowing()) {
                            mCustomProgressDialog.dismiss();
                        }
                        Toast.makeText(mContext, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                if (mCustomProgressDialog.isShowing()) {
                    mCustomProgressDialog.dismiss();
                }
                Toast.makeText(mContext, "Please login to continue.", Toast.LENGTH_SHORT).show();
            }

        }

    }
}
