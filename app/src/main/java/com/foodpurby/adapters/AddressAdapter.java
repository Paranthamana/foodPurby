package com.foodpurby.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.foodpurby.util.CustomProgressDialog;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.sloop.fonts.FontsManager;
import com.foodpurby.R;
import com.foodpurby.baseclass.MyActivity;
import com.foodpurby.model.AvailableAddressApi;
import com.foodpurby.model.DAOAddressdelete;
import com.foodpurby.ondbstorage.DBActionsUserAddress;
import com.foodpurby.ondbstorage.UserAddress;
import com.foodpurby.utillities.AppSharedValues;
import com.foodpurby.utillities.CartDeliveryDetails;
import com.foodpurby.utillities.UserDetails;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by android1 on 12/29/2015.
 */
public class AddressAdapter extends BaseAdapter implements BaseAdapterInterface {

    private final String sample;
    List<UserAddress> addresses = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;

    public AddressAdapter(Context context, String s) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        this.sample = s;
        if (!UserDetails.getCustomerKey().isEmpty()) {
            this.addresses = DBActionsUserAddress.getAddresses();
        }
       // FontsManager.initFormAssets(this.context, "Lato-Light.ttf");
    }

    @Override
    public int getCount() {
        return this.addresses.size();
    }

    @Override
    public Object getItem(int position) {
        return this.addresses.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        row = inflater.inflate(R.layout.lv_address_item, parent, false);
      //  FontsManager.changeFonts(row);

        final UserAddress userAddress = ((UserAddress) getItem(position));

        ViewHolder viewHolder = new ViewHolder();
        viewHolder.ivAddrType = (ImageView) row.findViewById(R.id.ivAddrType);
        viewHolder.tvAddressAnnotation = (TextView) row.findViewById(R.id.tvAddressAnnotation);
        viewHolder.tvFullAddress_delete = (TextView) row.findViewById(R.id.tvFullAddress_delete);

        viewHolder.tvFullAddress = (TextView) row.findViewById(R.id.tvFullAddress);

        viewHolder.tvFullAddress_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(context, R.style.AlertDialogCustom);
                builder1.setTitle(R.string.delete_address);
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                CustomProgressDialog.getInstance().show(context);
                                DAOAddressdelete.getInstance().Callresponse("en",
                                        "$2y$13$O8eus5IZeW9tkpEB3.sahuVVsmZemaXTyIxqDi3ROO2uEYvRxVC72",
                                        addresses.get(position).getUserAddressKey(),
                                        new Callback<DAOAddressdelete.Addressdelete>() {
                                            @Override
                                            public void success(DAOAddressdelete.Addressdelete addressdelete, Response response) {
                                                CustomProgressDialog.getInstance().dismiss();
                                                if (addressdelete.getHttpcode().equals("200")) {
                                                    addresses.remove(position);
                                                    DBActionsUserAddress.deleteSingleItem(userAddress.getUserAddressKey());
//                                                    Toast.makeText(context, deleteAddressApiResponse.getMessage(), Toast.LENGTH_LONG).show();

                                                    Toast.makeText(context, addressdelete.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                                    notifyDataSetChanged();


                                                }
                                            }

                                            @Override

                                            public void failure(RetrofitError error) {
                                                error.printStackTrace();
                                                CustomProgressDialog.getInstance().dismiss();

                                            }
                                        });


                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();


            }
        });
        viewHolder.tvAddressAnnotation.setTag(userAddress);


        String fullAddress = "";
        fullAddress = fullAddress.replaceFirst("^,", "").trim() + (userAddress.getFlatNo().trim().isEmpty() == true ? "" : ",\n" + userAddress.getFlatNo().trim());
        fullAddress = fullAddress.replaceFirst("^,", "").trim() + (userAddress.getLocationName().trim().isEmpty() == true ? "" : ",\n" + userAddress.getLocationName().trim());

        //fullAddress = fullAddress.replaceFirst("^,", "").trim() + (userAddress.getAreaName().trim().isEmpty() == true ? "" : ",\n" + userAddress.getAreaName().trim());
        //fullAddress = fullAddress.replaceFirst("^,", "").trim() + (userAddress.getCityName().trim().isEmpty() == true ? "" : ",\n" + userAddress.getCityName().trim());
        fullAddress = fullAddress.replaceFirst("^,", "").trim() + (userAddress.getLandmark().trim().isEmpty() == true ? "" : ",\n " + userAddress.getLandmark().trim());
        //fullAddress = fullAddress.replaceFirst("^,","").trim() + (userAddress.getPostalCode().trim().isEmpty() == true ? "" : " - " + userAddress.getPostalCode());

        viewHolder.tvFullAddress.setText(fullAddress.replaceFirst("^,", "").trim().replaceFirst("^,", "").trim());

        viewHolder.tvAddressAnnotation.setText(userAddress.getCompanyName());
        row.setTag(viewHolder);

        if (sample.equals("1")) {

        } else {
            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AvailableAddressApi.getInstance().Callresponse(AppSharedValues.getSelectedRestaurantBranchKey(), UserDetails.getCustomerKey(), new Callback<AvailableAddressApi.ResponseAvailableAddressApi>() {
                        @Override
                        public void success(AvailableAddressApi.ResponseAvailableAddressApi responseAvailableAddressApi, Response response) {
                            if (responseAvailableAddressApi.getHttpcode().equals("200")) {
                                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                                for (int count = 0; count < responseAvailableAddressApi.getData().getVendor_detail().getCoordinates().size(); count++) {

                                    String CurrentString = responseAvailableAddressApi.getData().getVendor_detail().getCoordinates().get(count);

                                    String[] separated = CurrentString.split(",");

                                    System.out.println("lat :" + separated[0] + "," + separated[1]);
                                    builder.include(new LatLng(Double.valueOf(separated[0]), Double.valueOf(separated[1])));
                                }


//                                builder.include(new LatLng(11.021759, 76.959840));
//                                builder.include(new LatLng(11.015166, 76.960312));
//                                builder.include(new LatLng(11.015440, 76.951193));


                                LatLngBounds bound = builder.build();
                                System.out.println("latcecc :" + userAddress.getCityKey() + "," + userAddress.getAreaKey());

                                if (bound.contains(new LatLng(Double.valueOf(userAddress.getCityKey()), Double.valueOf(userAddress.getAreaKey())))) {
                                    CartDeliveryDetails.setCartSelectedAddressKey(userAddress.getUserAddressKey());
                                    MyActivity.DisplayMyPaymentMethod(context, userAddress.getUserAddressKey());
                                } else {
                                    Toast.makeText(context, "Out of Delivery", Toast.LENGTH_SHORT).show();
                                }

                            }
                        }

                        @Override
                        public void failure(RetrofitError error) {

                        }
                    });

                   /* if (userAddress.getAnnotation().equals("0")) {
                        Toast.makeText(context, "Out of delivery", Toast.LENGTH_SHORT).show();
                    } else if (userAddress.getAnnotation().equals("1")) {
                        CartDeliveryDetails.setCartSelectedAddressKey(userAddress.getUserAddressKey());
                        MyActivity.DisplayMyPaymentMethod(context, userAddress.getUserAddressKey());
                    }*/

                }
            });
        }
        return row;
    }

    @Override
    public void notifyChanges() {
        this.addresses = DBActionsUserAddress.getAddresses();
        notifyDataSetChanged();
    }

    public class ViewHolder {

        public ImageView ivAddrType;
        public TextView tvAddressAnnotation;
        public TextView tvFullAddress_delete;
        public TextView tvFullAddress;
    }
}
