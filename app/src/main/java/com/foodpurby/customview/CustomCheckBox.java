package com.foodpurby.customview;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.CheckBox;

import com.foodpurby.util.Common;


public class CustomCheckBox extends CheckBox {
    public CustomCheckBox(Context context) {
        super(context);
        setFont();
    }

    public CustomCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont();
    }

    public CustomCheckBox(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setFont();
    }

    private void setFont() {
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), Common.appFon);
        setTypeface(font, Typeface.NORMAL);
    }
}
