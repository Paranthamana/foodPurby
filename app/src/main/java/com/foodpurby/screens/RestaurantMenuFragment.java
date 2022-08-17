package com.foodpurby.screens;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.sloop.fonts.FontsManager;
import com.foodpurby.adapters.MenuPagerAdapter;
import com.foodpurby.R;
import com.foodpurby.events.FilterIconVisibilityEvent;

import de.greenrobot.event.EventBus;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RestaurantMenuFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RestaurantMenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RestaurantMenuFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private int mGroupPosition;
    private int[] ICONS = {R.drawable.footer_profile,
            R.drawable.footer_profile, R.drawable.footer_map,
            R.drawable.footer_timeline};

    TextView txtTitle;
    ViewPager viewPager;
    FrameLayout containerLayout;
    Drawable myDrawable;
    Context mContext;
    private OnFragmentInteractionListener mListener;
    private EventBus bus = EventBus.getDefault();

    public RestaurantMenuFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static RestaurantMenuFragment newInstance(int mParamGroup) {
        RestaurantMenuFragment fragment = new RestaurantMenuFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, mParamGroup);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mGroupPosition = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vMenuItems = inflater.inflate(R.layout.fragment_restaurent_menu, container, false);
        //FontsManager.initFormAssets(getActivity(),"Lato-Light.ttf");
      //  FontsManager.changeFonts(vMenuItems);

        viewPager = (ViewPager) vMenuItems.findViewById(R.id.menu_pager);
        getTabsIcon(vMenuItems);
        return vMenuItems;
    }

    private void getTabsIcon(View vMenuItems) {
        MenuPagerAdapter customPagerAdapter = new MenuPagerAdapter(getChildFragmentManager(), ICONS,mGroupPosition);
        viewPager.setAdapter(customPagerAdapter);
        PagerSlidingTabStrip tabsStrip = (PagerSlidingTabStrip) vMenuItems.findViewById(R.id.menu_tabs);
        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());
        viewPager.setPageMargin(pageMargin);
        tabsStrip.setViewPager(viewPager);

    }

    @Override
    public void onResume() {
        super.onResume();
        bus.post(new FilterIconVisibilityEvent(View.GONE, FilterIconVisibilityEvent.PageTitleType.Menu));
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(int position) {
        if (mListener != null) {
            mListener.onArticleSelected(position);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onArticleSelected(int position);
    }
}
