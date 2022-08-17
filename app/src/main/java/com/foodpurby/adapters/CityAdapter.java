package com.foodpurby.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.foodpurby.model.DAOCity;
import com.foodpurby.R;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by android1 on 12/29/2015.
 */
public class CityAdapter extends BaseAdapter implements BaseAdapterInterface {

    List<DAOCity.City_list> cityLists;
    private LayoutInflater inflater;
    private Context context;
    private EventBus bus;
    public CityAdapter(Context context, EventBus bus, List<DAOCity.City_list> cityLists)
    {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        this.bus = bus;
        this.cityLists = cityLists;
    }

    @Override
    public int getCount() {
        return this.cityLists.size();
    }

    @Override
    public Object getItem(int position) {
        return this.cityLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        row = inflater.inflate(R.layout.lv_city_area, parent, false);

        ViewHolder viewHolder = new ViewHolder();
        viewHolder.ivLoc = (ImageView) row.findViewById(R.id.ivLoc);
        viewHolder.tvName = (TextView) row.findViewById(R.id.tvName);


        DAOCity.City_list city = (DAOCity.City_list)getItem(position);
        viewHolder.tvName.setText(city.getCity_name());
        viewHolder.tvName.setTag(city);

        row.setTag(viewHolder);
        return row;
    }

    public void SearchKeyword(List<DAOCity.City_list> cityLists) {
        this.cityLists = cityLists;
        notifyDataSetChanged();
    }

    @Override
    public void notifyChanges() {
        notifyDataSetChanged();
    }

    public class ViewHolder
    {
        public ImageView ivLoc;
        public TextView tvName;
    }
}
