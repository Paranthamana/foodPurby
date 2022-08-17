package com.foodpurby.screens;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.foodpurby.R;
import com.foodpurby.adapters.TransactionAdapter;
import com.foodpurby.api.TransactionApi;
import com.foodpurby.util.CustomProgressDialog;
import com.foodpurby.utillities.UserDetails;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class TransactionActivity extends AppCompatActivity {

    private ListView vListView;
    private List<TransactionApi.Datum> mTransactionList;
    private Toolbar mToolbar;
    private TextView mToolHeading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        mToolbar = (Toolbar) findViewById(R.id.tb_header);
        mToolHeading = (TextView) findViewById(R.id.tool_title);
        mToolbar.setNavigationIcon(R.drawable.back);
        mToolHeading.setText(R.string.wallet_history);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        vListView = (ListView) findViewById(R.id.transaction_list);


        final CustomProgressDialog mCustomProgressDialog = new CustomProgressDialog();
        mCustomProgressDialog.show(TransactionActivity.this);


        TransactionApi.getInstance().Callresponse("en", UserDetails.getCustomerKey(),
                new Callback<TransactionApi.ResponseTransaction>() {
                    @Override
                    public void success(TransactionApi.ResponseTransaction responseTransaction, Response response) {
                        mCustomProgressDialog.dismiss();
                        if (responseTransaction.getHttpcode() == 200) {
                            mTransactionList = responseTransaction.getData();
                            TransactionAdapter mTransactionAdapter = new TransactionAdapter(TransactionActivity.this, mTransactionList);
                            vListView.setAdapter(mTransactionAdapter);
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        error.printStackTrace();
                        mCustomProgressDialog.dismiss();
                        Toast.makeText(TransactionActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

    }
}
