package com.foodpurby.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.foodpurby.R;
import com.foodpurby.model.DAOArea;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by android1 on 12/29/2015.
 */
public class AreaAdapter extends BaseAdapter implements BaseAdapterInterface {

    String keyword;
    List<DAOArea.Area_list> areaLists;
    private LayoutInflater inflater;
    private Context context;
    private EventBus bus;
    public AreaAdapter(Context context, EventBus bus, List<DAOArea.Area_list> areaLists)
    {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;

        this.bus = bus;

        this.areaLists = areaLists;
    }

    @Override
    public int getCount() {
        return this.areaLists.size();
    }

    @Override
    public Object getItem(int position) {
        return this.areaLists.get(position);
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


        DAOArea.Area_list area = (DAOArea.Area_list)getItem(position);
        viewHolder.tvName.setText(area.getArea_name());
        viewHolder.tvName.setTag(area);

        row.setTag(viewHolder);
        return row;
    }

    public void SearchKeyword(List<DAOArea.Area_list> areaLists) {
        this.areaLists = areaLists;
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
