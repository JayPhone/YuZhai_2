package com.yuzhai.yuzhaiwork_2.base.view;

import android.support.v4.app.Fragment;

/**
 * Created by 35429 on 2017/2/14.
 */

public class BaseLazyLoadFragment extends Fragment {
    protected boolean isViewCreated = false;
    private static final String TAG = "BaseLazyLoadFragment";

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            lazyLoadData();
        }
    }

    /**
     * 懒加载方法
     */
    protected void lazyLoadData() {

    }
}
