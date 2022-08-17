package com.foodpurby.screens;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.GoogleMap;
import com.foodpurby.util.GPSTracker;
import com.foodpurby.R;
import com.foodpurby.ondbstorage.DBActionsUserAddress;
import com.foodpurby.ondbstorage.UserAddress;

public class UserAddressEdit extends Activity {
	private GoogleMap mGoogleMap;
	private LocationRequest mLocationRequest;
	private Location mLastLocation;
	static Double lat,lon;
	GPSTracker mGpsTracker;
	private RelativeLayout mSnackbar;
	String mBitmapToString;
	LinearLayout mButtonBar;
	LinearLayout llMap;
	LinearLayout llAddrDetails;
	Toolbar mToolbar;
	Button btnSave;

	private TextView tvAddress;
	private EditText etBuildingName;
	private EditText etLandmark;

	private String UserAddressKey = "";
	private LinearLayout llHome;
	private LinearLayout llWork;
	private LinearLayout llOther;
	private LinearLayout llOtherDetails;
	private TextView mToolHeading;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map_mark_address_edit);

		if(getIntent().getExtras() != null && getIntent().getExtras().getString("addresskey") != null && !getIntent().getExtras().getString("addresskey").trim().isEmpty()) {
			UserAddressKey = getIntent().getExtras().getString("addresskey").trim();
		}
		//Utils.isLocationEnabled(MarkAddressActivity.this);

		mToolbar = (Toolbar) findViewById(R.id.tb_header);
		if (mToolbar != null) {
			mToolbar.setNavigationIcon(R.drawable.dummy_menu);
			mToolbar.setNavigationIcon(ContextCompat.getDrawable(
					getApplicationContext(), R.drawable.back));
			mToolbar.setNavigationOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					finish();
				}
			});

			mToolHeading = (TextView) findViewById(R.id.tool_title);
			mToolHeading.setText(R.string.txt_update_address);
		}

		mButtonBar = (LinearLayout) findViewById(R.id.button_bar);
		btnSave = (Button) findViewById(R.id.btnSave);
		etBuildingName = (EditText) findViewById(R.id.etBuildingName);
		tvAddress = (TextView) findViewById(R.id.tvAddress);

		etLandmark = (EditText) findViewById(R.id.etLandmark);

		tvAddress.setText(" ");
		etBuildingName.setText(" ");
		etLandmark.setText(" ");

		llMap = (LinearLayout) findViewById(R.id.llMap);
		llAddrDetails = (LinearLayout) findViewById(R.id.llAddrDetails);

		llHome = (LinearLayout) findViewById(R.id.llHome);
		llOther = (LinearLayout) findViewById(R.id.llOther);
		llWork = (LinearLayout) findViewById(R.id.llWork);

		llHome.setOnClickListener(addressTypeListener);
		llOther.setOnClickListener(addressTypeListener);
		llWork.setOnClickListener(addressTypeListener);

		llOtherDetails = (LinearLayout) findViewById(R.id.llOtherDetails);


		if(!UserAddressKey.isEmpty()) {

			UserAddress userAddress = DBActionsUserAddress.getAddress(UserAddressKey);
			if(userAddress != null) {

				String fullAddress = userAddress.getCompanyName();
				fullAddress = fullAddress.replaceFirst("^,","").trim() + (userAddress.getFlatNo().trim().isEmpty() == true ? "" : ", " + userAddress.getFlatNo());
				fullAddress = fullAddress.replaceFirst("^,","").trim() + (userAddress.getLandmark().trim().isEmpty() == true ? "" : ", " + userAddress.getLandmark());
				fullAddress = fullAddress.replaceFirst("^,","").trim() + (userAddress.getAreaName().trim().isEmpty() == true ? "" : ", " + userAddress.getAreaName());
				fullAddress = fullAddress.replaceFirst("^,","").trim() + (userAddress.getCityName().trim().isEmpty() == true ? "" : ", " + userAddress.getCityName());
				fullAddress = fullAddress.replaceFirst("^,","").trim() + (userAddress.getPostalCode().trim().isEmpty() == true ? "" : " - " + userAddress.getPostalCode());

				tvAddress.setText(fullAddress.replaceFirst("^,","").trim().replaceFirst("^,","").trim());

				etBuildingName.setText(userAddress.getLandmark());
				etLandmark.setText(userAddress.getLandmark());
			}
		}

		btnSave.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				MyApplication.getInstance().trackEvent("Button", "Click", "Save Address");
				Toast.makeText(UserAddressEdit.this, R.string.toast_functionality_is_not_yet_completed, Toast.LENGTH_LONG).show();
			}
		});
	}

	View.OnClickListener addressTypeListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			if(view.getId() == R.id.llHome) {
				llOtherDetails.setVisibility(View.GONE);
			}
			else if(view.getId() == R.id.llWork) {
				llOtherDetails.setVisibility(View.GONE);
			}
			else if(view.getId() == R.id.llOther) {
				llOtherDetails.setVisibility(View.VISIBLE);
			}
		}
	};
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		this.mLastLocation = null;
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
}
