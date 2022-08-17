package com.foodpurby.baseclass;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;


import com.facebook.FacebookSdk;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GcmPubSub;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.foodpurby.model.DAOCity;
import com.foodpurby.ondbstorage.SQLConfig;
import com.foodpurby.ondbstorage.User;
import com.foodpurby.model.DAOArea;
import com.foodpurby.ondbstorage.AppSettings;
import com.foodpurby.ondbstorage.DBActionsAppSettings;
import com.foodpurby.ondbstorage.DBActionsUser;
import com.foodpurby.utillities.AppSharedValues;
import com.foodpurby.utillities.UserDetails;

import java.io.IOException;

/**
 * Created by android1 on 11/13/2015.
 */
public abstract class BaseActivity extends AppCompatActivity {
    GCMRegisterAsync gcm;

    @Override
    protected void onStart() {
        super.onStart();

        new SQLConfig(BaseActivity.this);
        FacebookSdk.sdkInitialize(BaseActivity.this);



        gcm = new GCMRegisterAsync(BaseActivity.this, null);
        gcm.execute();

//        AppSharedValues.setDeciveId("19d1bc6c-3365-4f4c-82d1-f83038e90a3b");
        AppSettings cityArea = DBActionsAppSettings.get();



        if (cityArea != null &&
                cityArea.getCityKey() != null && cityArea.getAreaKey() != null) {

            DAOCity.City_list city = new DAOCity().new City_list();
            city.setCity_key(cityArea.getCityKey());
            city.setCity_name(cityArea.getCityName());

            DAOArea.Area_list area = new DAOArea().new Area_list();
            area.setArea_key(cityArea.getAreaKey());
            area.setArea_name(cityArea.getAreaName());

            AppSharedValues.setCity(city);
            AppSharedValues.setArea(area);

            AppSharedValues.setVibrateWhileAddingToCart(cityArea.getVibrateOnCartChange());
        } else {
            AppSharedValues.setVibrateWhileAddingToCart(true);
        }

        User user = DBActionsUser.get();
        if (user != null) {
            try {
                UserDetails.setCustomerKey(user.getUserKey());
                UserDetails.setCustomerEmail(user.getUserEMail());
                UserDetails.setCustomerName(user.getUserFirstName());
                UserDetails.setCustomerLastName(user.getUserLastName());
                UserDetails.setCustomerMobile(user.getUserMobile());
                UserDetails.setAccessToken(user.getUserToken());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class GCMRegisterAsync extends AsyncTask<String, Void, String> {
        Context context;
        Activity activity;

        public GCMRegisterAsync(Context context, Activity activity) {
            this.context = context;
            this.activity = activity;
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... urls) {
            try {
                checkGooglePlayServices();
                // repeated calls to this method will return the same token
                // a new registration is needed if the app is updated or backup & restore happens
                final String token;
                try {
                    token = InstanceID.getInstance(context).getToken("995700170132", GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
                    AppSharedValues.setDeciveId(token);




                    //mDataStorage.saveGCMToken(context, token);
                    // then uploads the token to your server
                    // or, if the client wants to subscribe to certain topics, use this method
                    GcmPubSub.getInstance(context).subscribe(token, "/xmpp/messages", null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
                }

            return "string";
        }

        private boolean checkGooglePlayServices() {
            try {
                int result = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
                if (result == ConnectionResult.SUCCESS) {
                    return true;
                } else {
                    Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(result, activity, 8964,
                            new DialogInterface.OnCancelListener() {
                                @Override
                                public void onCancel(DialogInterface dialog) {
                                    finish();
                                }
                            });
                    if (errorDialog != null) {
                        // the problem can be fixed
                        // e.g. the user needs to enable or download the latest version
                        errorDialog.show();
                    } else {
                        // for some reason, the problem can't be fixed
                        // you should provide some notice to the user
                    }
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }


        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
        }


    }
}


