package com.foodpurby.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.foodpurby.R;
import com.foodpurby.events.RestaurantsLoadedEvent;
import com.foodpurby.events.SigninEvent;
import com.foodpurby.model.DAOAddress;
import com.foodpurby.model.DAOShops;
import com.foodpurby.ondbstorage.DBActionsCart;
import com.foodpurby.ondbstorage.DBActionsCuisineFilter;
import com.foodpurby.ondbstorage.DBActionsRestaurantBranch;
import com.foodpurby.ondbstorage.DBActionsUserAddress;
import com.foodpurby.ondbstorage.Ingredients;
import com.foodpurby.utillities.AppConfig;
import com.foodpurby.utillities.AppSharedValues;
import com.foodpurby.utillities.SessionManager;
import com.foodpurby.utillities.UserDetails;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by tech on 11/2/2015.
 */
public class Common {
    public static String appFon = "Lato-Light.ttf";
    FragmentActivity mContext;

    public enum Price {
        None,
        CartPrice,
        TotalPrice;
    }

    public Common(FragmentActivity mContext) {
        this.mContext = mContext;
    }

    public static void PopulateRestaurantDetails(final Context context, final String title, final Price price) {
        try {
            View rootView = ((Activity) context).getWindow().getDecorView().findViewById(android.R.id.content);

            Toolbar mToolbar = (Toolbar) rootView.findViewById(R.id.tb_header);
            mToolbar.setNavigationIcon(R.drawable.back);
            TextView mToolHeading = (TextView) rootView.findViewById(R.id.tool_title);
            mToolHeading.setText(AppSharedValues.getSelectedRestaurantBranchName());
            mToolHeading.setText(title);
            FrameLayout mShowCart = (FrameLayout) rootView.findViewById(R.id.flCart);
            mShowCart.setVisibility(View.VISIBLE);

            ImageView imgCart = (ImageView) rootView.findViewById(R.id.imgCart);
            imgCart.setVisibility(View.GONE);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    ((Activity) context).finish();
                }
            });

            TextView tvCart = (TextView) rootView.findViewById(R.id.tvCart);

            if (price == Price.None) {
                tvCart.setText("0");
            } else if (price == Price.CartPrice) {
                tvCart.setText(Common.getPriceWithCurrencyCode(DBActionsCart.getTotalCartPrice(AppSharedValues.getSelectedRestaurantBranchKey()).toString()));
                tvCart.setTypeface(Typeface.DEFAULT);
            } else if (price == Price.CartPrice) {
                tvCart.setText(Common.getPriceWithCurrencyCode(String.valueOf(AppSharedValues.getGrandTotalPrice())));
                tvCart.setTypeface(Typeface.DEFAULT);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void PopulateRestaurantDetailsCartPrice(final Context context, final Price price) {
        try {
            View rootView = ((Activity) context).getWindow().getDecorView().findViewById(android.R.id.content);

            TextView tvCart = (TextView) rootView.findViewById(R.id.tvCart);

            if (price == Price.None) {
                tvCart.setText("0");
            } else if (price == Price.CartPrice) {
                tvCart.setText(Common.getPriceWithCurrencyCode(DBActionsCart.getTotalCartPrice(AppSharedValues.getSelectedRestaurantBranchKey()).toString()));
                tvCart.setTypeface(Typeface.DEFAULT);
            } else if (price == Price.CartPrice) {
                tvCart.setText(Common.getPriceWithCurrencyCode(String.valueOf(AppSharedValues.getGrandTotalPrice())));
                tvCart.setTypeface(Typeface.DEFAULT);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static boolean IsInternetConnected(Context mContext) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static Boolean GetRestaurants(final Context context, final CustomProgressDialog mCustomProgressDialog, final EventBus bus) {

        if (!mCustomProgressDialog.isShowing()) {
            mCustomProgressDialog.show(context);
            mCustomProgressDialog.ChageMessage(context, context.getString(R.string.dialog_please_wait), context.getString(R.string.dialog_please_wait));
        }

        String areaKey = AppSharedValues.getArea() == null ? "" : AppSharedValues.getArea().getArea_key();
        String cityKey = AppSharedValues.getCity() == null ? "" : AppSharedValues.getCity().getCity_key();

        if (!areaKey.isEmpty() && !cityKey.isEmpty()) {

            try {
                DAOShops.getInstance().Callresponse("", areaKey, cityKey, AppSharedValues.getCategory(), UserDetails.getCustomerKey(), new Callback<DAOShops.Restaurant>() {

                    @Override
                    public void success(DAOShops.Restaurant restaurant, Response response) {
                        if (restaurant.getHttpcode().equals(200)) {

                            String mStrReponse = AppConfig.ObjectToString(restaurant);
                            SessionManager.getInstance().setBannerImage(context, mStrReponse);

                            if (mCustomProgressDialog.isShowing()) {
                                mCustomProgressDialog.dismiss();
                            }

                            DBActionsRestaurantBranch.deleteAll();
                            for (DAOShops.Shop_list rest : restaurant.getData().getShop_list()) {

                                Boolean isPureVegetarian = false;
                                if (rest.getRestaurant_type() != null && rest.getRestaurant_type().equals(0)) {
                                    isPureVegetarian = true;
                                }


                                DBActionsRestaurantBranch.add(rest.getShop_name(), rest.getShop_key(), rest.getShop_key(),
                                        Double.valueOf(rest.getMin_order_value()), Double.valueOf(rest.getDelivery_fee()), rest.getShop_logo(),
                                        rest.getShop_address(), rest.getCuisines(), String.valueOf(rest.getRestaurant_type()), "City", rest.getAvg_rating(), rest.getShop_status(),
                                        rest.getIs_favourite(), Double.valueOf(rest.getFavourite_count()),
                                        rest.getDelivery_in(), rest.getOnline_payment_available(), rest.getShop_opening_time(),
                                        isPureVegetarian,
                                        rest.getRestaurant_type(), rest.getOffer_text(), rest.getOffer_code(), rest.getOffer_min_order_value(),
                                        11.0183D, 76.9725
                                );
                            }

                            DBActionsCuisineFilter.deleteAll();
                            for (String cuisnineName : restaurant.getData().getCuisine_list()) {
                                DBActionsCuisineFilter.add(cuisnineName.trim(), cuisnineName.trim());
                            }

                            bus.post(new RestaurantsLoadedEvent());
                        } else {
                            // Toast.makeText(context, restaurant.getMessage(), Toast.LENGTH_SHORT).show();
                            if (mCustomProgressDialog.isShowing()) {
                                mCustomProgressDialog.dismiss();
                            }
                            bus.post(new RestaurantsLoadedEvent());
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        String ss = "";
                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                        if (mCustomProgressDialog.isShowing()) {
                            mCustomProgressDialog.dismiss();
                        }
                    }
                });
            } catch (Exception ex) {
                Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
                if (mCustomProgressDialog.isShowing()) {
                    mCustomProgressDialog.dismiss();
                }
            }

            return true;
        } else {

            Toast.makeText(context, R.string.toast_please_select_your_city, Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public static Boolean CanIncreaseItemCount(Context context, LayoutInflater inflater, String restaurantBranchKey, String productKey, List<Ingredients> ingredients) {

        Integer quantity = DBActionsCart.getItemCartCount(restaurantBranchKey, productKey, ingredients);
        return CanIncreaseItemCount(context, inflater, quantity);
    }

    public static Boolean CanIncreaseItemCount(Context context, LayoutInflater inflater, Integer quantity) {

        if (quantity + 1 > AppConfig.MAX_CART_PER_ITEM) {
            AlertDialog.Builder mDialog = new AlertDialog.Builder(context, R.style.MyAlertDialogStyle);
            final View vDialog = inflater.inflate(R.layout.dialog_my_cart_max_items, null);
            Button btnDismiss = (Button) vDialog.findViewById(R.id.btnDismiss);
            mDialog.setView(vDialog);
            final AlertDialog mAlertDilaog = mDialog.create();
            mAlertDilaog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            mAlertDilaog.show();

            btnDismiss.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mAlertDilaog != null && mAlertDilaog.isShowing()) {
                        mAlertDilaog.dismiss();
                    }
                }
            });

            return false;
        } else {
            return true;
        }
    }

    public static void GetAddress(final Context context, final CustomProgressDialog mCustomProgressDialog, final EventBus bus) {

        if (!mCustomProgressDialog.isShowing()) {
            mCustomProgressDialog.show(context);
        }

        if (!UserDetails.getCustomerKey().isEmpty()) {
            DAOAddress.getInstance().
                    Callresponse("", UserDetails.getCustomerKey(), AppSharedValues.getLanguage(), "$2y$13$O8eus5IZeW9tkpEB3.sahuVVsmZemaXTyIxqDi3ROO2uEYvRxVC72", AppSharedValues.getSelectedRestaurantBranchKey(), new Callback<DAOAddress.Address>() {

                        @Override
                        public void success(DAOAddress.Address address, Response response) {

                            DBActionsUserAddress.deleteAll();

                            if (address.getHttpcode().equals("200") && address.getData() != null && address.getData().getAddress_list() != null) {
                                for (DAOAddress.Address_list addressList : address.getData().getAddress_list()) {
                                    SessionManager.getInstance().setAddressType(context, addressList.getAddressType());
                                    SessionManager.getInstance().setAddresskey(context, addressList.getAddress_key());
                                    SessionManager.getInstance().setAddressTypeId(context, addressList.getAddressTypeId());
                                    DBActionsUserAddress.add(
                                            addressList.getAddress_key(),
                                            addressList.getAddressType(),
                                            addressList.getFlat_no(),
                                            addressList.getApartment(),
                                            addressList.getPostal_code(),
                                            addressList.getLandmark(),
                                            addressList.getCompany(),
                                            addressList.getCustomer_latitude(),
                                            addressList.getCity(),
                                            addressList.getCustomer_longitude(),
                                            addressList.getLocation(),
                                            addressList.getIs_delivery(),
                                            addressList.getCity());
                                    System.out.println("is_delivery :" + addressList.getIs_delivery());
                                }

                                bus.post(new SigninEvent());
                            }
                            if (mCustomProgressDialog.isShowing()) {
                                mCustomProgressDialog.dismiss();
                            }
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            String ss = "";
                            Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                            if (mCustomProgressDialog.isShowing()) {
                                mCustomProgressDialog.dismiss();
                            }
                        }
                    });
        } else {
            if (mCustomProgressDialog.isShowing()) {
                mCustomProgressDialog.dismiss();
            }
        }
    }

    public static Boolean IsShopOpened(Context context, LayoutInflater mInflater) {
        if (AppSharedValues.getSelectedRestaurantBranchStatus() == AppSharedValues.ShopStatus.Closed) {

            AlertDialog.Builder mDialog = new AlertDialog.Builder(context, R.style.MyAlertDialogStyle);
            final View vDialog = mInflater.inflate(R.layout.dialog_shop_offline, null);
            Button btnDismiss = (Button) vDialog.findViewById(R.id.btnDismiss);
            mDialog.setView(vDialog);
            final AlertDialog mAlertDilaog = mDialog.create();
            mAlertDilaog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            mAlertDilaog.show();

            btnDismiss.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mAlertDilaog != null && mAlertDilaog.isShowing()) {
                        mAlertDilaog.dismiss();
                    }
                }
            });

            return false;
        }
        return true;
    }

    public static void setListViewHeightBasedOnChildren1(ListView listView, int value) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();


    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            float px = 300 * (listView.getResources().getDisplayMetrics().density);
            listItem.measure(View.MeasureSpec.makeMeasureSpec((int) px, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
//            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();


    }


    public boolean isInternetConnected(Context ctx) {
        ConnectivityManager connectivityMgr = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connectivityMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = connectivityMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        // Check if wifi or mobile network is available or not. If any of them is
        // available or connected then it will return true, otherwise false;
        if (wifi != null) {
            if (wifi.isConnected()) {
                return true;
            }
        }
        if (mobile != null) {
            if (mobile.isConnected()) {
                return true;
            }
        }
        return false;
    }

    public static List<Date> getDates(String dateString1, String dateString2) {
        ArrayList<Date> dates = new ArrayList<Date>();
        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");

        Date date1 = null;
        Date date2 = null;

        try {
            date1 = df1.parse(dateString1);
            date2 = df1.parse(dateString2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);


        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        while (!cal1.after(cal2)) {
            dates.add(cal1.getTime());
            cal1.add(Calendar.DATE, 1);
        }
        return dates;
    }

    public static void getTotalHeightofListView(ListView listView) {

        ListAdapter mAdapter = listView.getAdapter();

        int totalHeight = 0;

        for (int i = 0; i < mAdapter.getCount(); i++) {
            View mView = mAdapter.getView(i, null, listView);

            mView.measure(
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),

                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

            totalHeight += mView.getMeasuredHeight();
            Log.w("HEIGHT" + i, String.valueOf(totalHeight));

        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (mAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();

    }

    public static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public static String getPriceWithCurrencyCode(String totalPrice) {
        totalPrice = totalPrice == null || totalPrice.trim() == "" ? "0.00" : totalPrice.trim();
        double f = Double.parseDouble(totalPrice);


        //if RTL not enabled
        //so show the price accordingly...
        if (!AppSharedValues.isRTLEnabled()) {
            String space = "";
            if (AppSharedValues.isShowPriceWithSpace()) {
                space = " ";
            }
            if (AppSharedValues.isShowCurrencyCodeInRight()) {
                return String.format("%.2f", new BigDecimal(f)) + space + AppSharedValues.getSelectedRestaurantBranchCurrencyCode();
            } else {
                return AppSharedValues.getSelectedRestaurantBranchCurrencyCode() + space + String.format("%.2f", new BigDecimal(f));
            }
        } else {
            String space = "";
            if (AppSharedValues.isShowPriceWithSpace()) {
                space = " ";
            }
            if (AppSharedValues.isShowCurrencyCodeInRight()) {
                return AppSharedValues.getSelectedRestaurantBranchCurrencyCode() + space + String.format("%.2f", new BigDecimal(f));
            } else {
                return totalPrice + space + AppSharedValues.getSelectedRestaurantBranchCurrencyCode();
            }
        }
    }


    public static class PaymentGateways {
        /**
         * Hash Key Calculation
         *
         * @param type
         * @param str
         * @return
         */
        public static String hashCal(String type, String str) {
            byte[] hashSequence = str.getBytes();
            StringBuffer hexString = new StringBuffer();
            try {
                MessageDigest algorithm = MessageDigest.getInstance(type);
                algorithm.reset();
                algorithm.update(hashSequence);
                byte messageDigest[] = algorithm.digest();

                for (int i = 0; i < messageDigest.length; i++) {
                    String hex = Integer.toHexString(0xFF & messageDigest[i]);
                    if (hex.length() == 1)
                        hexString.append("0");
                    hexString.append(hex);
                }
            } catch (NoSuchAlgorithmException NSAE) {
            }
            return hexString.toString();
        }
    }


    public String getCertificateSHA1Fingerprint() {
        PackageManager pm = mContext.getPackageManager();
        String packageName = mContext.getPackageName();
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

    public static String byte2HexFormatted(byte[] arr) {
        StringBuilder str = new StringBuilder(arr.length * 2);
        for (int i = 0; i < arr.length; i++) {
            String h = Integer.toHexString(arr[i]);
            int l = h.length();
            if (l == 1) h = "0" + h;
            if (l > 2) h = h.substring(l - 2, l);
            str.append(h.toUpperCase());
            if (i < (arr.length - 1)) str.append(':');
        }
        return str.toString();
    }

    public static void ChangeImageViewBackground(ImageView mImageView, Context mContext) {
        mImageView.setColorFilter(mContext.getResources().getColor(R.color.white), android.graphics.PorterDuff.Mode.MULTIPLY);
    }

    public static void showFresco(final Context context, final SimpleDraweeView imageView, final String imageUrl) {
        try {

            Uri uri = Uri.parse(imageUrl);

            int width = 200, height = 200;
            ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                    .setResizeOptions(new ResizeOptions(width, height))
                    .build();
            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setOldController(imageView.getController())
                    .setImageRequest(request)
                    .build();
            imageView.setController(controller);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showFresco1(final Context context, final SimpleDraweeView imageView, final String imageUrl) {
        try {

            Uri uri = Uri.parse(imageUrl);
            imageView.setImageURI(uri);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}


