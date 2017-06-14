package com.yuzhai.yuzhaiwork_2.order_detail.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by 35429 on 2017/2/21.
 */

public class OrdersAppliedViewPagerAdapter  extends FragmentPagerAdapter {
    private List<Fragment> mFragmentList;
    private String[] titles = new String[]{"项目进度", "项目详情"};

    public OrdersAppliedViewPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.mFragmentList = fragmentList;
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
