package com.foodpurby.screens;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.foodpurby.model.DAOOfferApi;
import com.foodpurby.util.CustomProgressDialog;
import com.foodpurby.R;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class OfferFragment extends Fragment {

    ImageView vOfferImage;
    Context mContext;
    private CustomProgressDialog mCustomProgressDialog;

    public OfferFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_home_page, menu);
        MenuItem item_search = menu.findItem(R.id.action_search);
        item_search.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
        item_search.setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }
    public static OfferFragment newInstance(String param1, String param2) {
        OfferFragment fragment = new OfferFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vOfferFragment;
        vOfferFragment = inflater.inflate(R.layout.fragment_offer, container, false);
        initView(vOfferFragment);

        mCustomProgressDialog = CustomProgressDialog.getInstance();
        mCustomProgressDialog.show(mContext);
        DAOOfferApi.getInstance().Callresponse(new Callback<DAOOfferApi.OfferResponse>() {
            @Override
            public void success(DAOOfferApi.OfferResponse offerResponse, Response response) {
                mCustomProgressDialog.dismiss();
                if (offerResponse.getHttpcode().equals("200")) {
                    Picasso.with(getActivity()).load(offerResponse.getData().getImage()).into(vOfferImage);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                mCustomProgressDialog.dismiss();
                error.printStackTrace();
            }
        });

        return vOfferFragment;
    }

    private void initView(View vOfferFragment) {
        vOfferImage = (ImageView) vOfferFragment.findViewById(R.id.FO_offer_image);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }
}
