package com.foodpurby.screens;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.sloop.fonts.FontsManager;
import com.foodpurby.R;
import com.foodpurby.util.DirectionsJSONParser;
import com.foodpurby.util.GPSTracker;
import com.foodpurby.utillities.AppConfig;
import com.foodpurby.utillities.MoveTaxi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import static com.foodpurby.R.id.map;

public class LiveOrderTracking extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private GoogleApiClient client;
    private GPSTracker mGPSTracker;
    private LatLng mCurrentLatLon;
    Animation slide_down, slide_up;
    Toolbar mToolbar;
    TextView mToolHeading;
    ArrayList<MoveTaxi> TaxiList = new ArrayList<MoveTaxi>();

    PolylineOptions lineOptions = null;
    String custmoerlat,customerlon,vendorlat,vendorlon;
    TextView eta;
    String orderKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_order_tracking);
        //FontsManager.initFormAssets(LiveOrderTracking.this, "Lato-Light.ttf");
       // FontsManager.changeFonts(this);

        mToolbar = (Toolbar) findViewById(R.id.tb_header);
        if (mToolbar != null) {
            mToolbar.setNavigationIcon(ContextCompat.getDrawable(
                    getApplicationContext(), R.drawable.back));
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            mToolHeading = (TextView) findViewById(R.id.tool_title);
                mToolHeading.setText("Live Tracking");

        }

        eta= (TextView) findViewById(R.id.eta);

        orderKey=getIntent().getStringExtra("orderKey");

        custmoerlat=getIntent().getStringExtra("custmoerlat");
        customerlon=getIntent().getStringExtra("customerlon");
        vendorlat=getIntent().getStringExtra("vendorlat");
        vendorlon=getIntent().getStringExtra("vendorlon");

        mGPSTracker = new GPSTracker(LiveOrderTracking.this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(map);
        mapFragment.getMapAsync(this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();


        ConnectSocket();

    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mCurrentLatLon = new LatLng(mGPSTracker.getLatitude(), mGPSTracker.getLongitude());
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mCurrentLatLon, 10));
        mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(final CameraPosition cameraPosition) {

            }
        });

        MarkerOptions mMarkerOptions1 = new MarkerOptions().position(new LatLng(Double.parseDouble(custmoerlat), Double.parseDouble(customerlon))).icon(BitmapDescriptorFactory.fromResource(R.drawable.mapmarker1));
        Marker mMarker1 = mMap.addMarker(mMarkerOptions1);

        MarkerOptions mMarkerOptions2 = new MarkerOptions().position(new LatLng(Double.parseDouble(vendorlat), Double.parseDouble(vendorlon))).icon(BitmapDescriptorFactory.fromResource(R.drawable.mapmarker2));
        Marker mMarker2 = mMap.addMarker(mMarkerOptions2);

        LatLng origin = new LatLng(Double.parseDouble(custmoerlat), Double.parseDouble(customerlon));
        LatLng dest = new LatLng(Double.parseDouble(vendorlat), Double.parseDouble(vendorlon));

        if (origin.latitude != 0.0 && origin.longitude != 0.0 && dest.latitude != 0.0 && dest.longitude != 0.0) {
            // Getting URL to the Google Directions API
            String url = getDirectionsUrl(origin, dest);

            DownloadTask downloadTask = new DownloadTask();
            // Start downloading json data from Google Directions API
            downloadTask.execute(url);
        }



        mMap.getUiSettings().setRotateGesturesEnabled(false);
    }



    private void ConnectSocket() {
        try {
            final JSONObject objInit = new JSONObject();
            objInit.put("order_id", orderKey);

            if (AppConfig.socket == null) {
                AppConfig.socket = IO.socket(AppConfig.SocketPath);
            } else {
                System.out.println("Socket is already connected");
            }

            AppConfig.socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {

                @Override
                public void call(Object... args) {
                    AppConfig.socket.emit("deliveryboy_location", objInit);
                }

            }).on("authenticated", new Emitter.Listener() {

                @Override
                public void call(Object... args) {
                    String taxiDetails = "asdf";
                }

            }).on("event", new Emitter.Listener() {

                @Override
                public void call(Object... args) {
                }

            }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {

                @Override
                public void call(Object... args) {
                }

            }).on("deliveryboy_location", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    final String taxiDetails = args[0].toString();

                    try {
                        LiveOrderTracking.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    JSONObject Job_CarDetails = new JSONObject(taxiDetails);
                                    String deliveryboy_key = Job_CarDetails.getString("deliveryboy_key");
                                    String delivery_boy_name = Job_CarDetails.getString("delivery_boy_name");
                                    String delivery_boy_email = Job_CarDetails.getString("delivery_boy_email");
                                    String mobile_number = Job_CarDetails.getString("mobile_number");
                                    String latitude = Job_CarDetails.getString("latitude");
                                    String longitude = Job_CarDetails.getString("longitude");

                                    if(TaxiList.size()==0) {
                                        MarkerOptions mMarkerOptions = new MarkerOptions().position(new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude))).icon(BitmapDescriptorFactory.fromResource(R.drawable.carmap));
                                        Marker mMarker = mMap.addMarker(mMarkerOptions);
                                        MoveTaxi mMoveTaxi = new MoveTaxi();
                                        mMoveTaxi.setDeliveryboy_key(deliveryboy_key);
                                        mMoveTaxi.setDelivery_boy_name(delivery_boy_name);
                                        mMoveTaxi.setDelivery_boy_email(delivery_boy_email);
                                        mMoveTaxi.setMobile_number(mobile_number);
                                        mMoveTaxi.setLatitude(latitude);
                                        mMoveTaxi.setLongitude(longitude);
                                        mMoveTaxi.setMarker(mMarker);
                                        TaxiList.add(mMoveTaxi);
                                    }else
                                    {

                                        LatLng mToPosition = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
                                        animateMarker(TaxiList.get(0).getMarker(), mToPosition, true);
                                    }


                                    showDistanceTime(Double.parseDouble(latitude),Double.parseDouble(longitude),Double.parseDouble(custmoerlat),Double.parseDouble(customerlon));




                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    } catch (Exception e) {

                    }




                }
            });
            if (!AppConfig.socket.connected()) {
                AppConfig.socket.connect();
            }



        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void animateMarker(final Marker marker, final LatLng toPosition, final boolean hideMarker) {
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        Projection proj = mMap.getProjection();
        Point startPoint = proj.toScreenLocation(marker.getPosition());
        final LatLng startLatLng = proj.fromScreenLocation(startPoint);
        final long duration = 5000;

        final Interpolator interpolator = new LinearInterpolator();

        handler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    long elapsed = SystemClock.uptimeMillis() - start;
                    float t = interpolator.getInterpolation((float) elapsed / duration);
                    if (toPosition != null) {
                        double lng = t * toPosition.longitude + (1 - t) * startLatLng.longitude;
                        double lat = t * toPosition.latitude + (1 - t) * startLatLng.latitude;

                        Location prevLoc = new Location("service Provider");
                        prevLoc.setLatitude(startLatLng.latitude);
                        prevLoc.setLongitude(startLatLng.longitude);

                        Location newLoc = new Location("service Provider");
                        newLoc.setLatitude(toPosition.latitude);
                        newLoc.setLongitude(toPosition.longitude);

                        float bearing = prevLoc.bearingTo(newLoc);
                        marker.setPosition(new LatLng(lat, lng));
                        marker.setRotation(bearing + 90);
                        //moveToPostion(lat, lng);
                        if (t < 1.0) {
                            // Post again 16ms later.
                            handler.postDelayed(this, 16);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private double showDistanceTime(double mDistanceLatitude, double mDistanceLongitude, double mTaxiLatitude, double mTaxiLongitude) {
        int Radius = 6371;// radius of earth in Km
        double lat1 = mDistanceLatitude;
        double lat2 = mTaxiLatitude;
        double lon1 = mDistanceLongitude;
        double lon2 = mTaxiLongitude;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec = Integer.valueOf(newFormat.format(km));
        double meter = valueResult % 1000;
        int meterInDec = Integer.valueOf(newFormat.format(meter));
        Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec + " Meter   " + meterInDec);

        eta.setText("ETA : "+(int)round((valueResult*2),2));

        return Radius * c;
    }

    private String getDirectionsUrl(LatLng origin, LatLng dest) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;

        return url;
    }

    /**
     * A method to download json data from url
     */
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    // Fetches data from url passed
    private class DownloadTask extends AsyncTask<String, Void, String> {

        // Downloading data in non-ui thread
        @Override
        protected String doInBackground(String... url) {
            // For storing data from web service
            String data = "";

            try {
                // Fetching the data from web service
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        // Executes in UI thread, after the execution of
        // doInBackground()
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);

        }
    }

    /**
     * A class to parse the Google Places in JSON format
     */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                // Starts parsing data
                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points = null;
            MarkerOptions markerOptions = new MarkerOptions();
            String distance = "";
            String duration = "";
            if (result.size() < 1) {
                Toast.makeText(LiveOrderTracking.this, "No Points", Toast.LENGTH_SHORT).show();
                return;
            }
            // Traversing through all the routes
            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    if (j == 0) { // Get duration from the list
                        distance = (String) point.get("distance");
                        continue;
                    } else if (j == 1) { // Get duration from the list
                        duration = (String) point.get("duration");
                        continue;
                    }

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(15);
                lineOptions.color(Color.BLUE);
            }

            /*vDistanceValue.setText(distance);
            vRemainingTimeValue.setText(duration);*/
            // Drawing polyline in the Google Map for the i-th route
            mMap.addPolyline(lineOptions);
        }
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }


}
