package com.foodpurby.screens;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.foodpurby.ondbstorage.Cuisine;
import com.foodpurby.baseclass.MyActivity;
import com.foodpurby.ondbstorage.DBActionsCuisine;
import com.foodpurby.utillities.AppSharedValues;
import com.foodpurby.R;

import java.util.List;

public class MenuCuisines extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        final List<Cuisine> cuisinesD = DBActionsCuisine.getCuisines();

        AppSharedValues.setSelectedRestaurantBranchKey("1b7ff608-ef32-4c2a-bff1-4823dd404cdf");



        Button btnDrinks = (Button) findViewById(R.id.btnDrinks);
        btnDrinks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.getInstance().trackEvent("Button", "Click", "Drinks");
                final List<Cuisine> cuisines = DBActionsCuisine.getCuisines();
                MyActivity.DisplayProducts(MenuCuisines.this, cuisines.get(0).getRestaurantBranchKey(), cuisines.get(0).getCuisineKey());
            }

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
