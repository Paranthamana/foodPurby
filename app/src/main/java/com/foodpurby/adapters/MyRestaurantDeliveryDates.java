package com.foodpurby.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.foodpurby.utillities.AppSharedValues;
import com.foodpurby.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * Created by android1 on 12/29/2015.
 */
public class MyRestaurantDeliveryDates extends BaseAdapter implements BaseAdapterInterface {

    Calendar cal = Calendar.getInstance();
    private LayoutInflater inflater;
    private Context context;
    private List<String> dates = new ArrayList<String>();
    private String restaurantBranchKey;

    private LinearLayout llDateList;
    private LinearLayout llTime;
    private LinearLayout llTimeListOther;
    private MyRestaurantDeliveryTimes myRestaurantDeliveryTimes;
    private TextView tvSelectedDate;

    private EventBus bus;
    public MyRestaurantDeliveryDates(Context context, EventBus bus, String restaurantBranchKey, LinearLayout llDateList, LinearLayout llTime, LinearLayout llTimeListOther,
                                     MyRestaurantDeliveryTimes myRestaurantDeliveryTimes, TextView tvSelectedDate)
    {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;

        this.bus = bus;
        this.restaurantBranchKey = restaurantBranchKey;

        this.tvSelectedDate = tvSelectedDate;
        this.llDateList = llDateList;
        this.llTime = llTime;
        this.llTimeListOther = llTimeListOther;

        this.myRestaurantDeliveryTimes = myRestaurantDeliveryTimes;

        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);

        dates.clear();
        Iterator<Map.Entry<String, List<String>>> iterator = AppSharedValues.getDeliveryDateTimes().entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, List<String>> pairs = iterator.next();
            dates.add(pairs.getKey());
        }
    }
    @Override
    public int getCount() {
        return dates.size();
    }

    @Override
    public Object getItem(int position) {
        return dates.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        row = inflater.inflate(R.layout.lv_my_delivery_dates, parent, false);

        Button btnDate1 = (Button) row.findViewById(R.id.btnDate1);

        //String dateIs = (new SimpleDateFormat("MM/dd/yyyy")).format(((Date) getItem(position)));
        btnDate1.setText(getItem(position).toString());

        btnDate1.setTag(getItem(position));
        btnDate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                llTimeListOther.setVisibility(View.VISIBLE);
                llDateList.setVisibility(View.GONE);
                tvSelectedDate.setText(v.getTag().toString());
                AppSharedValues.setDeliveryDateSelected(v.getTag().toString());
                myRestaurantDeliveryTimes.notifyChanges();
            }
        });
        row.setTag(btnDate1);
        return row;
    }

    @Override
    public void notifyChanges() {
        dates.clear();
        Iterator<Map.Entry<String, List<String>>> iterator = AppSharedValues.getDeliveryDateTimes().entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, List<String>> pairs = iterator.next();
            dates.add(pairs.getKey());
        }
        this.notifyDataSetChanged();
    }
}
