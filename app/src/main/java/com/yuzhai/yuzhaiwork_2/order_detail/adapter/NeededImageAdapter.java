package com.yuzhai.yuzhaiwork_2.order_detail.adapter;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yuzhai.yuzhaiwork_2.R;
import com.yuzhai.yuzhaiwork_2.base.adapter.AbsLoopPagerAdapter;
import com.yuzhai.yuzhaiwork_2.base.http.IPConfig;
import com.yuzhai.yuzhaiwork_2.order_detail.bean.NeededImageData;

import java.util.List;

/**
 * Created by 35429 on 2017/6/13.
 */

public class NeededImageAdapter extends AbsLoopPagerAdapter<NeededImageData> {

    public NeededImageAdapter(Context context, List<NeededImageData> data, ViewPager viewPager) {
        super(context, data, viewPager);
    }

    private ViewGroup.LayoutParams layoutParams;

    @Override
    protected View getItemView(NeededImageData data) {
        if (layoutParams == null) {
            layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
        ImageView imageView = new ImageView(mContext);
        imageView.setLayoutParams(layoutParams);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(mContext)
                .load(IPConfig.IMAGE_PREFIX + data.getImageUrl())
                .placeholder(R.drawable.default_image)
                .error(R.drawable.default_image)
                .into(imageView);
        return imageView;
    }
}
