package com.foodpurby.utillities;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

/**
 * Created by android1 on 1/4/2016.
 */
public class LoadImages {

    public static void show(final Context context, final ImageView imageView, final String imageUrl) {
        try {

            if(!imageUrl.trim().isEmpty()) {
                Picasso.with(context)
                        .load(imageUrl)
                        .networkPolicy(NetworkPolicy.OFFLINE)
                        .into(imageView, new Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError() {
                                //Try again online if cache failed
                                Picasso.with(context)
                                        .load(imageUrl)
                                        //.error(R.drawable.ic_location)
                                        .into(imageView, new Callback() {
                                            @Override
                                            public void onSuccess() {

                                            }

                                            @Override
                                            public void onError() {
                                                Log.v("Picasso", "Could not fetch image");
                                            }
                                        });
                            }
                        });
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void show1(final ProgressBar mProgressbar, final Context context, final ImageView imageView, final String imageUrl) {
        try {

            if(!imageUrl.trim().isEmpty()) {
                Picasso.with(context)
                        .load(imageUrl)
                        .networkPolicy(NetworkPolicy.OFFLINE)
                        .into(imageView, new Callback() {
                            @Override
                            public void onSuccess() {
                                mProgressbar.setVisibility(View.GONE);
                            }

                            @Override
                            public void onError() {
                                //Try again online if cache failed
                                Picasso.with(context)
                                        .load(imageUrl)
                                        //.error(R.drawable.ic_location)
                                        .into(imageView, new Callback() {
                                            @Override
                                            public void onSuccess() {

                                            }

                                            @Override
                                            public void onError() {
                                                Log.v("Picasso", "Could not fetch image");
                                            }
                                        });
                            }
                        });
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
