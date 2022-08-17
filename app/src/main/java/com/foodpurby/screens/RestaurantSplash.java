package com.foodpurby.screens;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.foodpurby.R;
import com.foodpurby.baseclass.BaseActivity;
import com.foodpurby.baseclass.MyActivity;
import com.foodpurby.model.DAOArea;
import com.foodpurby.model.DAOCMSList;
import com.foodpurby.model.DAOCity;
import com.foodpurby.model.DAOCurrentLocation;
import com.foodpurby.util.Common;
import com.foodpurby.util.CustomProgressDialog;
import com.foodpurby.util.GPSTracker;
import com.foodpurby.utillities.AppSharedValues;
import com.foodpurby.utillities.SessionManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.foodpurby.util.Common.byte2HexFormatted;


public class RestaurantSplash extends BaseActivity {
    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    private final int SPLASH_DISPLAY_LENGTH = 4000;
    private FrameLayout mainFrame;
    private Common uCommon;
    public GPSTracker mGpsTracker;
    public static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private GoogleCloudMessaging mGoogleCloudMessaging = null;
    private String regid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        generateHashKey();
        //getCertificateSHA1Fingerprint();

        /*OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();*/


        mGpsTracker = new GPSTracker(RestaurantSplash.this);
        registerReceiver(broadcastReceiver, new IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION));

        DAOCMSList.getInstance().Callresponse(new Callback<DAOCMSList.ResponseCMSList>() {
            @Override
            public void success(DAOCMSList.ResponseCMSList cmsList, Response response) {
                if (cmsList != null) {
                    if (cmsList.getData() != null && cmsList.getData().getCms_list() != null) {
                        AppSharedValues.setCMSList(cmsList.getData().getCms_list());
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });

        try {
            Picasso.Builder builder = new Picasso.Builder(this);
            builder.downloader(new OkHttpDownloader(this, Integer.MAX_VALUE));
            Picasso built = builder.build();
            built.setIndicatorsEnabled(false);
            built.setLoggingEnabled(true);
            Picasso.setSingletonInstance(built);
        } catch (Exception e) {
            e.printStackTrace();
        }


        //Background splash zoom effect coding section
        mainFrame = ((FrameLayout) findViewById(R.id.AS_FL_back_frame));


        Animation zoomout = AnimationUtils.loadAnimation(this, R.anim.zoom_out);

        uCommon = new Common(RestaurantSplash.this);


        final String mCurrentLoaction = mGpsTracker.getLocality(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    String[] PERMISSIONS = {

                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_NETWORK_STATE,
                            Manifest.permission.INTERNET,
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            //  Manifest.permission.GET_ACCOUNTS,
                            Manifest.permission_group.LOCATION,
                            // Manifest.permission.WRITE_EXTERNAL_STORAGE
                    };
                    if (!hasPermissions(RestaurantSplash.this, PERMISSIONS)) {
                        ActivityCompat.requestPermissions(RestaurantSplash.this, PERMISSIONS, PERMISSION_REQUEST_CODE);
                    } else {
                        MainFunction();
                    }
                } else {
                    MainFunction();
                }

                //MainFunction();
            }


        }, SPLASH_DISPLAY_LENGTH);


        if (checkPlayServices()) {

            mGoogleCloudMessaging = GoogleCloudMessaging.getInstance(RestaurantSplash.this);
            regid = getRegistrationId(RestaurantSplash.this);
            System.out.println("Res Reister ID: " + regid);
            if (regid.isEmpty()) {
                registerInBackground();
            } else {
                Log.d("No Vaild", "No valid Google Play Services APK found.");
            }
        }

    }

    private void MainFunction() {
        if (mGpsTracker.getIsGPSTrackingEnabled() == true) {
            ApiCallValue();

        } else {
            showSettingsAlert();

        }


    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().matches("android.location.PROVIDERS_CHANGED")) {
                mGpsTracker = new GPSTracker(RestaurantSplash.this);
                if (mGpsTracker.getIsGPSTrackingEnabled() == true) {
                    ApiCallValue();
                }
            }
        }
    };


    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_page, menu);
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
        unregisterReceiver(broadcastReceiver);
    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(RestaurantSplash.this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, RestaurantSplash.this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {

                finish();
            }
            return false;
        }
        return true;
    }


    private String getRegistrationId(Context context) {
        final SharedPreferences prefs = getGCMPreferences(context);
        String registrationId = prefs.getString(PROPERTY_REG_ID, "");
        if (registrationId.isEmpty()) {
            return "";
        }
        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {

            return "";
        }
        return registrationId;
    }

    private SharedPreferences getGCMPreferences(Context context) {
        return getSharedPreferences(RestaurantSplash.class.getSimpleName(),
                Context.MODE_PRIVATE);
    }


    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    private void registerInBackground() {
        new AsyncTask() {

            @Override
            protected Object doInBackground(Object[] params) {
                String msg = "";
                try {
                    if (mGoogleCloudMessaging == null) {
                        mGoogleCloudMessaging = GoogleCloudMessaging.getInstance(RestaurantSplash.this);
                    }
                    regid = mGoogleCloudMessaging.register("995700170132");
                    Log.d("GCM", "Current Device's Registration ID is: " + msg);
                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object result) {
                super.onPostExecute(result);
            }
        }.execute(null, null, null);
    }

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(RestaurantSplash.this);

        alertDialog.setTitle(Html.fromHtml("<font color='#000000'>" + getString(R.string.title_enable_location) + "</font>"));
        alertDialog.setMessage(Html.fromHtml("<font color='#000000'>" + getString(R.string.msg_gps_not_enable) + "</font>"));
        alertDialog.setPositiveButton(getString(R.string.title_enable_location),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                        dialog.dismiss();
                    }
                });
        // on pressing cancel button
        alertDialog.setNegativeButton(getString(R.string.dialog_cancel),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        ApiCallValue();
                    }
                });
        // Showing Alert Message
        try {
            alertDialog.show();
        } catch (Exception e) {

        }

    }

    public void ApiCallValue() {
        Double lat = 0.0;
        Double lon = 0.0;

        if (mGpsTracker.getIsGPSTrackingEnabled()) {
            lat = mGpsTracker.getLatitude();
            lon = mGpsTracker.getLongitude();
        }
        final CustomProgressDialog mCustomProgressDialog = new CustomProgressDialog();
        mCustomProgressDialog.show(RestaurantSplash.this);
        DAOCurrentLocation.getInstance().Callresponse("", AppSharedValues.getLanguage(), lat, lon, SessionManager.getInstance().getDeviceToken(RestaurantSplash.this), 1, new Callback<DAOCurrentLocation.CurrentLocation>() {
            @Override
            public void success(DAOCurrentLocation.CurrentLocation currentLocation, Response response) {
                if (currentLocation.getStatus().equalsIgnoreCase("success")) {
                    mCustomProgressDialog.dismiss();
                    if (currentLocation.getData() != null) {
                        DAOCity.City_list city = new DAOCity().new City_list();
                        city.setCity_key(currentLocation.getData().getGet_location().getCity_key());
                        city.setCity_name(currentLocation.getData().getGet_location().getCity_name());
                        DAOArea.Area_list area = new DAOArea().new Area_list();
                        area.setArea_key(currentLocation.getData().getGet_location().getArea_key());
                        area.setArea_name(currentLocation.getData().getGet_location().getArea_name());
                        AppSharedValues.setCity(city);
                        AppSharedValues.setArea(area);
                        if (AppSharedValues.getArea() != null && AppSharedValues.getCity() != null) {
                            mCustomProgressDialog.dismiss();
                            MyActivity.DisplayGetStartActivity(RestaurantSplash.this);
                            RestaurantSplash.this.finish();
                        }
                    }
                } else {
                    mCustomProgressDialog.dismiss();
                    MyActivity.DisplayGetStartActivity(RestaurantSplash.this);
                    finish();
                }

            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
                mCustomProgressDialog.dismiss();
                Toast.makeText(RestaurantSplash.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void generateHashKey() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.foodpurby", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getCertificateSHA1Fingerprint() {
        PackageManager pm = RestaurantSplash.this.getPackageManager();
        String packageName = RestaurantSplash.this.getPackageName();
        int flags = PackageManager.GET_SIGNATURES;
        PackageInfo packageInfo = null;
        try {
            packageInfo = pm.getPackageInfo(packageName, flags);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        Signature[] signatures = packageInfo.signatures;
        byte[] cert = signatures[0].toByteArray();
        InputStream input = new ByteArrayInputStream(cert);
        CertificateFactory cf = null;
        try {
            cf = CertificateFactory.getInstance("X509");
        } catch (CertificateException e) {
            e.printStackTrace();
        }
        X509Certificate c = null;
        try {
            c = (X509Certificate) cf.generateCertificate(input);
        } catch (CertificateException e) {
            e.printStackTrace();
        }
        String hexString = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(c.getEncoded());
            hexString = byte2HexFormatted(publicKey);
        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (CertificateEncodingException e) {
            e.printStackTrace();
        }
        return hexString;
    }


    public boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }

        }
        return true;
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    MainFunction();
                }
                break;
        }
    }
}
