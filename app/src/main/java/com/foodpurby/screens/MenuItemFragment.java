package com.foodpurby.screens;

import android.app.Activity;
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
import com.foodpurby.R;
import com.foodpurby.adapters.CustomPagerAdapter;
import com.foodpurby.events.AddRemoveToCartEvent;
import com.foodpurby.events.DummyEvent;
import com.foodpurby.events.FilterIconVisibilityEvent;

import de.greenrobot.event.EventBus;

public class MenuItemFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM_GROUP = "group";

    // TODO: Rename and change types of parameters

    private int mParamGroup;
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

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MenuItemFragment.
     * @params param 1 Parameter 1.
     * @params param 2 Parameter 2.
     */
    // TODO: Rename and change types and number of parameters
    public static MenuItemFragment newInstance(int mParamGroup) {
        MenuItemFragment fragment = new MenuItemFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM_GROUP, mParamGroup);
        fragment.setArguments(args);
        return fragment;
    }

    public MenuItemFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParamGroup = getArguments().getInt(ARG_PARAM_GROUP);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vMenuItems = inflater.inflate(R.layout.fragment_menu_item, container, false);
      //  FontsManager.initFormAssets(getActivity(), "Lato-Light.ttf");
      //  FontsManager.changeFonts(vMenuItems);
        viewPager = (ViewPager) vMenuItems.findViewById(R.id.pager);
        getTabsIcon(vMenuItems);
        viewPager.setCurrentItem(mParamGroup);
        bus.post(new AddRemoveToCartEvent(true, null));
        bus.post(new FilterIconVisibilityEvent(View.GONE, FilterIconVisibilityEvent.PageTitleType.MenuItems));
        return vMenuItems;
    }


    private void getTabsIcon(View vMenuItems) {
        CustomPagerAdapter customPagerAdapter = new CustomPagerAdapter(getChildFragmentManager(), ICONS, "Grp1");
        viewPager.setAdapter(customPagerAdapter);
        PagerSlidingTabStrip tabsStrip = (PagerSlidingTabStrip) vMenuItems.findViewById(R.id.tabs);
        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());
        viewPager.setPageMargin(pageMargin);
        tabsStrip.setViewPager(viewPager);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(int position) {

        if (mListener != null) {
            mListener.onArticleSelected(position);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        EventBus.getDefault().register(this);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        EventBus.getDefault().unregister(this);
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onArticleSelected(int position);
    }

    public void onEvent(final DummyEvent ev) {
    }
}
