package com.example.deathdevilt_t.googlemaps_testv1.FragmentSetting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.deathdevilt_t.googlemaps_testv1.R;

/**
 * Created by chtnnnmtgkyp on 6/26/2017.
 */

public class DFragment extends DialogFragment {
    ViewPager viewPager;
    TabLayout tabLayout;
    OnABCClick onABCClick;
    String phoneNumber;


    public DFragment(){
    }

    public static DFragment newInstance(OnABCClick onABCClick,String phoneNumber){
        DFragment f = new DFragment();
        f.onABCClick = onABCClick;
        f.phoneNumber = phoneNumber;
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.custom_dialog, container);
        getDialog().setTitle("Hola tronco");

        //content to show in the fragments
        viewPager = (ViewPager) view.findViewById(R.id.v_pager);
        viewPager.setOffscreenPageLimit(4);
        viewPager.setAdapter(new CustomAdapter(getChildFragmentManager(),getContext(),onABCClick,phoneNumber));

        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
        });

        return view;
    }
}
