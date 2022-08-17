package com.foodpurby.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sloop.fonts.FontsManager;
import com.foodpurby.R;
import com.foodpurby.baseclass.MyActivity;
import com.foodpurby.model.DAOCMSList;
import com.foodpurby.utillities.AppSharedValues;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by android1 on 12/29/2015.
 */
public class HelpAdapter extends BaseAdapter implements BaseAdapterInterface {

    List<DAOCMSList.Cms_list> help = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;
    private EventBus bus;

    public HelpAdapter(Context context, EventBus bus) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        this.bus = bus;
        this.help = AppSharedValues.getCMSList();
      //  FontsManager.initFormAssets(this.context, "Lato-Light.ttf");

    }

    @Override
    public int getCount() {
        return this.help.size();
    }

    @Override
    public Object getItem(int position) {
        return this.help.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        row = inflater.inflate(R.layout.lv_help, parent, false);
       // FontsManager.changeFonts(row);
        DAOCMSList.Cms_list value = (DAOCMSList.Cms_list) getItem(position);
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.tvName = (TextView) row.findViewById(R.id.tvName);
        viewHolder.tvName.setText(value.getPage_name());
        viewHolder.tvName.setTag(value);
        viewHolder.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DAOCMSList.Cms_list key = (DAOCMSList.Cms_list) v.getTag();
                MyActivity.DisplayHelpBrowser(context, key.getPage_url(), key.getPage_name());
            }
        });
        row.setTag(viewHolder);
        return row;
    }

    @Override
    public void notifyChanges() {
        notifyDataSetChanged();
    }

    public class ViewHolder {
        public TextView tvName;
    }
}
