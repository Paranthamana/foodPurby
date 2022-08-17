package com.foodpurby.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.foodpurby.R;
import com.foodpurby.model.DAOAddressTypeList;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by android1 on 12/29/2015.
 */
public class AddressTypeAdapter extends BaseAdapter implements BaseAdapterInterface {

    List<DAOAddressTypeList.Address_list> addressType;
    private LayoutInflater inflater;
    private Context context;
    private EventBus bus;
    public AddressTypeAdapter(Context context, EventBus bus, List<DAOAddressTypeList.Address_list> cityLists)
    {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        this.bus = bus;
        this.addressType = cityLists;
    }

    @Override
    public int getCount() {
        return this.addressType.size();
    }

    @Override
    public Object getItem(int position) {
        return this.addressType.get(position);
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

        DAOAddressTypeList.Address_list addressType = (DAOAddressTypeList.Address_list)getItem(position);
        viewHolder.tvName.setText(addressType.getType_name());
        viewHolder.tvName.setTag(addressType);

        viewHolder.ivLoc.setVisibility(View.GONE);
        row.setTag(viewHolder);
        return row;
    }

    public void SearchKeyword(List<DAOAddressTypeList.Address_list> cityLists) {
        this.addressType = cityLists;
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
