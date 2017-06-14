package com.yuzhai.yuzhaiwork_2.main.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yuzhai.yuzhaiwork_2.base.adapter.AbsLoopPagerAdapter;
import com.yuzhai.yuzhaiwork_2.main.bean.BannerData;

import java.util.List;

/**
 * Created by 35429 on 2017/5/27.
 */

public class HomeBannberAdapter extends AbsLoopPagerAdapter<BannerData> {
    public HomeBannberAdapter(Context context, List<BannerData> data, ViewPager viewPager) {
        super(context, data, viewPager);
    }

    private ViewGroup.LayoutParams layoutParams;

    @Override
    protected View getItemView(BannerData data) {
        if (layoutParams == null) {
            layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
        ImageView imageView = new ImageView(mContext);
        imageView.setLayoutParams(layoutParams);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setImageResource(data.getImageId());
        return imageView;
    }
}
