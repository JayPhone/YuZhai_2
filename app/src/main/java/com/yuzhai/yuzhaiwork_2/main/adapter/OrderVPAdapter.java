package com.yuzhai.yuzhaiwork_2.main.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by 35429 on 2017/6/1.
 */

public class OrderVPAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragmentList;
    private String[] titles = new String[]{"已发布", "已申请", "已接收"};

    public OrderVPAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        mFragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
