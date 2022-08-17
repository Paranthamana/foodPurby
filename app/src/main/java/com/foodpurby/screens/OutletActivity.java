package com.foodpurby.screens;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;

import com.sloop.fonts.FontsManager;
import com.foodpurby.adapters.OutletAdapter;
import com.foodpurby.util.CustomProgressDialog;
import com.foodpurby.R;
import com.foodpurby.model.DAOVendorOutlet;
import com.foodpurby.utillities.AppSharedValues;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class OutletActivity extends AppCompatActivity {

	private CustomProgressDialog mCustomProgressDialog;
	Toolbar mToolbar;
	TextView mToolHeading;
	ArrayList<DAOVendorOutlet.Vendor_outlet> mVendorList;
	OutletAdapter mOutletAdapter;
	ListView vListViewoutlets;
	@Override
	protected void onCreate ( Bundle savedInstanceState ) {
		super.onCreate (savedInstanceState);
		setContentView (R.layout.activity_outlet);

		//FontsManager.initFormAssets (this, "Lato-Light.ttf");
		//FontsManager.changeFonts (this);
		vListViewoutlets= (ListView) findViewById (R.id.vList);


		this.getWindow().setSoftInputMode (WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		mCustomProgressDialog = CustomProgressDialog.getInstance();

		mToolbar = (Toolbar) findViewById(R.id.tb_header);
		mToolHeading = (TextView) findViewById(R.id.tool_title);
		mToolbar.setNavigationIcon (R.drawable.back);
		mToolHeading.setText (R.string.txt_select_outlet);
		mToolbar.setNavigationOnClickListener (new View.OnClickListener () {

			@Override
			public void onClick ( View v ) {
				finish ();
			}
		});
		getVendorOutlets();




	}

	private void getVendorOutlets() {

		if (!mCustomProgressDialog.isShowing()) {
			mCustomProgressDialog.ChageMessage(OutletActivity.this, "Please wait...", "Please wait...");
			mCustomProgressDialog.show(OutletActivity.this);
		}

			DAOVendorOutlet.getInstance ().VendorOutletResponse(AppSharedValues.getSelectedRestaurantBranchKey (), new Callback<DAOVendorOutlet.Outlets> () {
			@Override
			public void success ( DAOVendorOutlet.Outlets outlets, Response response ) {
				mCustomProgressDialog.dismiss ();

				if(outlets.getHttpcode ().equals ("200"))
				{
					mVendorList=new ArrayList<DAOVendorOutlet.Vendor_outlet> ();
					for(int count=0;count<outlets.getData ().getVendor_outlets ().size ();count++)
					{
						mVendorList.add (outlets.getData ().getVendor_outlets ().get (count));
					}
					mOutletAdapter=new OutletAdapter (OutletActivity.this,mVendorList);
					vListViewoutlets.setAdapter (mOutletAdapter);
				}




			}

			@Override
			public void failure ( RetrofitError error ) {

			}
		});

	}
}
