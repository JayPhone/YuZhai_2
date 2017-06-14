package com.yuzhai.yuzhaiwork_2.base.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by Administrator on 2016/10/4.
 */
public class TranslucentScrollView extends ScrollView {
    private static final String TAG = "TranslucentScrollView";
    private OnScrollingListener mOnScrollingListener;

    public TranslucentScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public void setOnScrollingListener(OnScrollingListener onScrollingListener) {
        this.mOnScrollingListener = onScrollingListener;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mOnScrollingListener != null) {
            mOnScrollingListener.onTranslucent(l, t, oldl, oldt);
        }
    }

    public interface OnScrollingListener {
        /**
         * 透明度的回调
         */
        void onTranslucent(int h, int v, int oldH, int oldV);
    }
}
