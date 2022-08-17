package com.foodpurby.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.foodpurby.customview.ImageViewRounded;
import com.foodpurby.R;
import com.foodpurby.model.DAOCategory;
import com.foodpurby.utillities.AppSharedValues;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by Android on 7/1/2016.
 */
public class GridCategoryAdapter extends BaseAdapter {
    private Context mContext;
    List<DAOCategory.Data> CategoryList;
    private EventBus bus;
    private final int[] Imageid;

    public GridCategoryAdapter(Context context, EventBus bus, List<DAOCategory.Data> mCategoryList, int[] Imageid) {
        mContext = context;
        this.bus = bus;
        this.CategoryList = mCategoryList;
        this.Imageid = Imageid;
    }

    @Override
    public int getCount() {
        return CategoryList.size();
    }

    @Override
    public Object getItem(int position) {
        return CategoryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.grid_category_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.ivCategory = (ImageViewRounded) convertView.findViewById(R.id.category_grid_image);
            viewHolder.tvCategoryName = (TextView) convertView.findViewById(R.id.category_grid_text);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

//        if(AppSharedValues.CategorySelection.equalsIgnoreCase("1")){

            if (AppSharedValues.getCategory().equalsIgnoreCase("1") && position == 0){
                convertView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.category_selector));
            }else if (AppSharedValues.getCategory().equalsIgnoreCase("4") && position == 1){
                convertView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.category_selector));
            }else{
                convertView.setBackgroundDrawable(null);
            }
        /*}else{
            convertView.setBackgroundDrawable(null);
        }*/

        viewHolder.ivCategory.setImageResource(Imageid[position]);
        if (viewHolder.tvCategoryName != null) {
            if (!CategoryList.get(position).getCategory_name().equalsIgnoreCase("")) {
                viewHolder.tvCategoryName.setText(CategoryList.get(position).getCategory_name());
            }
        }

        return convertView;
    }

    public class ViewHolder {
        public ImageViewRounded ivCategory;
        public TextView tvCategoryName;
    }
}
