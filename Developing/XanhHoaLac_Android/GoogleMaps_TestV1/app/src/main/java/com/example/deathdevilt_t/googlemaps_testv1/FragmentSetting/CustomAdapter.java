package com.example.deathdevilt_t.googlemaps_testv1.FragmentSetting;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


/**
 * Created by chtnnnmtgkyp on 6/10/2017.
 */

public class CustomAdapter extends FragmentPagerAdapter {

    private OnABCClick onABCClick;
    private String phoneNumber;

    private String fragment[] = {"Cài đặt Node","Nạp tiền","Lịch sử thao tác"};

    public CustomAdapter(FragmentManager fm, Context context, OnABCClick abcClick,String phoneNumber) {
        super(fm);
        this.onABCClick = abcClick;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new SetTheCalendar(phoneNumber,onABCClick);
            case 1:
                return new PhoneRecharge(phoneNumber,onABCClick);
            case 2:
                return new HistoryOfAction(phoneNumber,onABCClick);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return fragment.length;
    }
    @Override
    public CharSequence getPageTitle(int pos){
        return fragment[pos];
    }
}
