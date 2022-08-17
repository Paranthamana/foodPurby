package com.foodpurby.events;

import android.view.View;

/**
 * Created by android1 on 12/17/2015.
 */
public class AddRemoveToCartEvent {

    public Boolean getVibrate() {
        return vibrate;
    }

    public void setVibrate(Boolean vibrate) {
        this.vibrate = vibrate;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    private Boolean vibrate;
    private View view;

    public AddRemoveToCartEvent(Boolean vibrate, View view) {
        this.vibrate = vibrate;
        this.view = view;
    }
}
