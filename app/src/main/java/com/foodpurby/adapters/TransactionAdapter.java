package com.foodpurby.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.foodpurby.R;
import com.foodpurby.api.TransactionApi;

import java.util.List;

/**
 * Created by tech on 11/2/2015.
 */
public class TransactionAdapter extends BaseAdapter {


    private final Context mContext;
    private final LayoutInflater mInflater;
    private final List<TransactionApi.Datum> mTransactionList;

    public TransactionAdapter(Context mContext, List<TransactionApi.Datum> mTransactionList) {
        this.mContext = mContext;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mTransactionList = mTransactionList;


    }

    @Override
    public int getCount() {
        return mTransactionList.size();
    }

    @Override
    public Object getItem(int position) {
        return mTransactionList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder mHolder = new ViewHolder();

        convertView = mInflater.inflate(R.layout.lv_transaction, null);
        mHolder.tvTransactionId = (TextView) convertView.findViewById(R.id.transaction_id);
        mHolder.tvReferenceNo = (TextView) convertView.findViewById(R.id.reference_no);
        mHolder.tvAmount = (TextView) convertView.findViewById(R.id.amount);
        mHolder.tvTime = (TextView) convertView.findViewById(R.id.time);
        mHolder.tvStatus = (TextView) convertView.findViewById(R.id.status);


        mHolder.tvTransactionId.setText(mTransactionList.get(position).getTracking_id() + "");
        mHolder.tvReferenceNo.setText(mTransactionList.get(position).getBank_ref_no() + "");
        mHolder.tvAmount.setText(mTransactionList.get(position).getWallet_amount() + "");
        mHolder.tvTime.setText(mTransactionList.get(position).getDate_formatted() + "");
        mHolder.tvStatus.setText(mTransactionList.get(position).getTransaction_status() + "");


        if (mTransactionList.get(position).getTransaction_status().equalsIgnoreCase("Failure")) {
            mHolder.tvStatus.setTextColor(Color.RED);
        } else if (mTransactionList.get(position).getTransaction_status().equalsIgnoreCase("Success")) {
            mHolder.tvStatus.setTextColor(mContext.getResources().getColor(R.color.green));
        } else {
            mHolder.tvStatus.setTextColor(Color.BLACK);
        }

        return convertView;
    }


    public class ViewHolder {

        public TextView tvTransactionId, tvReferenceNo, tvAmount, tvTime, tvStatus;
    }
}
