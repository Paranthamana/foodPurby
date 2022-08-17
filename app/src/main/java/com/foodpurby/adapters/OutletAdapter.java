package com.foodpurby.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sloop.fonts.FontsManager;
import com.foodpurby.baseclass.MyActivity;
import com.foodpurby.model.DAOVendorOutlet;
import com.foodpurby.utillities.CartDeliveryDetails;
import com.foodpurby.R;

import java.util.ArrayList;
import java.util.List;


public class OutletAdapter extends BaseAdapter implements BaseAdapterInterface {

	List<DAOVendorOutlet.Vendor_outlet> addresses = new ArrayList<> ();
	private LayoutInflater inflater;
	private Context context;

	public OutletAdapter ( Context context, ArrayList<DAOVendorOutlet.Vendor_outlet> addresses1 ) {
		this.inflater = (LayoutInflater) context.getSystemService (Context.LAYOUT_INFLATER_SERVICE);
		this.context = context;

		this.addresses = addresses1;
	//	FontsManager.initFormAssets (this.context, "Lato-Light.ttf");
	}

	@Override
	public int getCount () {
		return this.addresses.size ();
	}

	@Override
	public Object getItem ( int position ) {
		return this.addresses.get (position);
	}

	@Override
	public long getItemId ( int position ) {
		return position;
	}

	@Override
	public View getView ( final int position, View convertView, ViewGroup parent ) {
		final ViewHolder mHolder;
		if ( convertView == null ) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService (Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate (R.layout.lv_outlet_item, parent, false);
		//	FontsManager.changeFonts (convertView);
			mHolder = new ViewHolder ();

			mHolder.tvAddressAnnotation = (TextView) convertView.findViewById (R.id.tvAddressAnnotation);
			mHolder.tvFullAddress = (TextView) convertView.findViewById (R.id.tvFullAddress);
			mHolder.tvContactNumber = (TextView) convertView.findViewById (R.id.tvContactNumber);
			mHolder.tvContactEmail = (TextView) convertView.findViewById (R.id.tvContactEmail);

			convertView.setTag (mHolder);
		}
		else {
			mHolder = (ViewHolder) convertView.getTag ();
		}

		mHolder.tvAddressAnnotation.setText (addresses.get (position).getOutlet_name ());
		mHolder.tvFullAddress.setText (addresses.get (position).getOutlet_contact_address ().trim () + "\n" + addresses.get (position).getOutlet_area ().trim () + "\n" + addresses.get (position).getOutlet_city ().trim ());
		mHolder.tvContactNumber.setText ("Contact Number :"+addresses.get (position).getOutlet_conatct_number ());
		mHolder.tvContactEmail.setText ("Contact E-Mail :"+addresses.get (position).getOutlet_contact_email ());

		convertView.setOnClickListener (new View.OnClickListener () {
			@Override
			public void onClick ( View v ) {


				CartDeliveryDetails.setCartSelectedAddressKey (addresses.get (position).getOutlet_key ());
				MyActivity.DisplayMyPaymentMethod (context, addresses.get (position).getOutlet_key ());

			}
		});

		return convertView;
	}

	@Override
	public void notifyChanges () {
		// this.addresses = DBActionsUserAddress.getAddresses();
		notifyDataSetChanged ();
	}

	public class ViewHolder {
		public ImageView ivAddrType;
		public TextView tvAddressAnnotation,tvContactNumber,tvContactEmail;
		public TextView tvFullAddress;
	}
}
