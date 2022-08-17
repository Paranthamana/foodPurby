package com.foodpurby.screens;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.foodpurby.ondbstorage.DBActionsGroup;
import com.foodpurby.events.AddRemoveToCartEvent;
import com.foodpurby.events.DummyEvent;
import com.foodpurby.events.FilterIconVisibilityEvent;
import com.foodpurby.ondbstorage.Group;
import com.foodpurby.R;

import de.greenrobot.event.EventBus;


public class MainMenuFragment extends Fragment {
    private EventBus bus = EventBus.getDefault();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String[] TITLE = {"Food", "Bar", "Search"};
    private OnFragmentInteractionListener mListener;
    ViewPager viewPager;
    private FragmentTabHost mTabHost;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainMenuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainMenuFragment newInstance(String param1, String param2) {
        MainMenuFragment fragment = new MainMenuFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public MainMenuFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View vMainMenu = inflater.inflate(R.layout.fragment_main_menu, container, false);
        mTabHost = (FragmentTabHost) vMainMenu.findViewById(android.R.id.tabhost);
        mTabHost.setup(getActivity(), getChildFragmentManager(), android.R.id.tabcontent);
        for (Group group : DBActionsGroup.getGroups()) {
            Bundle bundle = new Bundle();
            bundle.putString("group", group.getGroupKey());
            mTabHost.addTab(mTabHost.newTabSpec(group.getGroupKey()).setIndicator(group.getGroupName(), null), MenuItemFragment.class, bundle);
        }


        bus.post(new AddRemoveToCartEvent(true, null));
        bus.post(new FilterIconVisibilityEvent(View.GONE, FilterIconVisibilityEvent.PageTitleType.MenuItems));
        return vMainMenu;
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(int position) {
        if (mListener != null) {
            mListener.onFragmentInteraction(position);
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
        public void onFragmentInteraction(int position);
    }

    public void onEvent(final DummyEvent ev) {
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        android.view.MenuItem item = menu.findItem(R.id.action_search);
        item.setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }
}
