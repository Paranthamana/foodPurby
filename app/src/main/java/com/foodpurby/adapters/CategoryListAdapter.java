package com.foodpurby.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.foodpurby.R;
import com.foodpurby.model.DAOCategory;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by android1 on 4/6/2016.
 */
public class CategoryListAdapter extends BaseAdapter implements BaseAdapterInterface {

    List<DAOCategory.Category> CategoryList;
    private LayoutInflater inflater;
    private Context context;
    private EventBus bus;


    public CategoryListAdapter(Context context, EventBus bus, List<DAOCategory.Category> mCategoryList) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        this.bus = bus;
        this.CategoryList = mCategoryList;
    }

    @Override
    public int getCount() {
        return this.CategoryList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.CategoryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        row = inflater.inflate(R.layout.lv_city_area,
                parent, false);

        ViewHolder viewHolder = new ViewHolder();
        viewHolder.ivLoc = (ImageView) row.findViewById(R.id.ivLoc);
        viewHolder.tvName = (TextView) row.findViewById(R.id.tvName);

        DAOCategory.Category mCategoryy = (DAOCategory.Category) getItem(position);
        viewHolder.tvName.setText(mCategoryy.getCategory_name());

        viewHolder.tvName.setTag(mCategoryy);

        row.setTag(viewHolder);
        return row;
    }

    public void SearchKeyword(List<DAOCategory.Category> CategoryList) {
        this.CategoryList = CategoryList;
        notifyDataSetChanged();
    }

    @Override
    public void notifyChanges() {
        notifyDataSetChanged();
    }

    public class ViewHolder {
        public ImageView ivLoc;
        public TextView tvName;
    }
}
