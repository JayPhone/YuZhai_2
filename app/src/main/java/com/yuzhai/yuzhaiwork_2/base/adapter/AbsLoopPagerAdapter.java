package com.yuzhai.yuzhaiwork_2.base.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 35429 on 2017/5/27.
 */

public abstract class AbsLoopPagerAdapter<T> extends PagerAdapter implements ViewPager.OnPageChangeListener {
    //当前页面
    private int currentPosition = 0;
    protected Context mContext;
    private List<View> mViews;
    private ViewPager mViewPager;

    protected AbsLoopPagerAdapter(Context context, List<T> data, ViewPager viewPager) {
        this.mContext = context;
        mViews = new ArrayList<>();
        //31231
        //数据大于1条时
        if (data.size() > 1) {
            //添加最后一页到第一页
            data.add(0, data.get(data.size() - 1));
            //添加第一页到最后一页
            data.add(data.get(1));
        }
        for (T res : data) {
            mViews.add(getItemView(res));
        }
        this.mViewPager = viewPager;
        mViewPager.setAdapter(this);
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setCurrentItem(1, false);
    }

    @Override
    public int getCount() {
        return mViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mViews.get(position));
        return mViews.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViews.get(position));
    }

    /**
     * 子类实现
     */
    protected abstract View getItemView(T data);

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        currentPosition = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //31231
        //若viewpager滑动未停止，直接返回
        if (state != ViewPager.SCROLL_STATE_IDLE) {
            return;
        }
        //若当前为第一张，设置页面为倒数第二张
        if (currentPosition == 0) {
            mViewPager.setCurrentItem(mViews.size() - 2, false);
        } else if (currentPosition == mViews.size() - 1) {
            //若当前为倒数第一张，设置页面为第二张
            mViewPager.setCurrentItem(1, false);
        }
    }
}
