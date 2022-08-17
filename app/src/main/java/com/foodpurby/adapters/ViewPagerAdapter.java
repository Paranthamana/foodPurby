package com.foodpurby.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.foodpurby.utillities.LoadImages;
import com.foodpurby.R;

public class ViewPagerAdapter extends PagerAdapter {
    // Declare Variables
    Context context;
    int[] flag;
    String[] flags;
    int text = 0;
    LayoutInflater inflater;

    public ViewPagerAdapter(Context context, int[] flag) {
        this.context = context;
        this.flag = flag;
        text = 1;
    }

    public ViewPagerAdapter(Context context, String[] flag) {
        this.context = context;
        this.flags = flag;
        text = 2;
    }

    @Override
    public int getCount() {

        if (text == 1) {
            return flag.length;
        }
        if (text == 2) {
            return flags.length;
        }

        return flag.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        // Declare Variables
        ImageView imgflag;
        ProgressBar mProgressbar;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.getstart_viewpager_item, container,
                false);

        // Locate the ImageView in viewpager_item.xml
        imgflag = (ImageView) itemView.findViewById(R.id.flag);
        mProgressbar = (ProgressBar) itemView.findViewById(R.id.progres);
        mProgressbar.setVisibility(View.GONE);
        // Capture position and set to the ImageView

        if (text == 1)

            imgflag.setImageResource(flag[position]);
        else
            LoadImages.show(context, imgflag, flags[position]);
        //LoadImages.show1(mProgressbar,context, imgflag, flags[position]);
        // Add viewpager_item.xml to ViewPager
        ((ViewPager) container).addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // Remove viewpager_item.xml from ViewPager
        ((ViewPager) container).removeView((RelativeLayout) object);
    }
}
