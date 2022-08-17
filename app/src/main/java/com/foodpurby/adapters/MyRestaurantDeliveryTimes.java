package com.foodpurby.adapters;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.foodpurby.R;
import com.foodpurby.utillities.AppSharedValues;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by android1 on 12/29/2015.
 */
public class MyRestaurantDeliveryTimes extends BaseAdapter implements BaseAdapterInterface {

    Calendar cal = Calendar.getInstance();
    private LayoutInflater inflater;
    private Context context;
    //private List<Integer> dates = new ArrayList<Integer>();
    private List<String> times = new ArrayList<String>();
    private String restaurantBranchKey;
    private AlertDialog mAlertDilaog;
    private TextView tvCollectDateTime;

    SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a");
    private EventBus bus;
    public MyRestaurantDeliveryTimes(Context context, EventBus bus, String restaurantBranchKey, AlertDialog mAlertDilaog, TextView tvCollectDateTime)
    {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;

        this.bus = bus;
        this.restaurantBranchKey = restaurantBranchKey;
        this.mAlertDilaog = mAlertDilaog;
        this.tvCollectDateTime = tvCollectDateTime;

        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);

    }
    @Override
    public int getCount() {
        return times.size();
    }

    @Override
    public Object getItem(int position) {
        return times.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        row = inflater.inflate(R.layout.lv_my_delivery_times, parent, false);

        Button btnDate1 = (Button) row.findViewById(R.id.btnDate1);
        btnDate1.setText(getItem(position).toString());

        Button btnDate2 = (Button) row.findViewById(R.id.btnDate2);
        btnDate2.setText(getItem(position).toString());

        btnDate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    tvCollectDateTime.setText(AppSharedValues.getDeliveryDateSelected() + " " + ((Button) v).getText());
                    if (mAlertDilaog.isShowing()) {
                        mAlertDilaog.dismiss();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        btnDate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    tvCollectDateTime.setText(AppSharedValues.getDeliveryDateSelected() + " " + ((Button) v).getText());
                    if (mAlertDilaog.isShowing()) {
                        mAlertDilaog.dismiss();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        try {
            Date date = dateFormat.parse(getItem(position).toString());

            Calendar dateTime = Calendar.getInstance();
            dateTime.setTime(date);
            dateTime.add(Calendar.MINUTE, 15);
            date = dateTime.getTime();

            String result2 = String.format("%02d", dateTime.get(Calendar.HOUR)) + ":" + String.format("%02d", dateTime.get(Calendar.MINUTE));
            btnDate2.setText(dateFormat.parse(date.toString()).toString());

        } catch (Exception e) {
            e.printStackTrace();
        }


        return row;
    }

    @Override
    public void notifyChanges() {
        this.times = AppSharedValues.getDeliveryDateTimes().get(AppSharedValues.getDeliveryDateSelected());
        this.notifyDataSetChanged();
    }
}
