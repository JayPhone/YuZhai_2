package com.yuzhai.yuzhaiwork_2.base.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by 35429 on 2017/5/20.
 */

public class ActivityUtil {
    /**
     * 添加Fragment到Activity
     */
    public static void addFragment(FragmentManager fragmentManager,
                                   Fragment fragment, int fragmentId) {
        if (fragmentManager != null && fragment != null) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(fragmentId, fragment);
            transaction.commit();
            fragment.setUserVisibleHint(true);
        }
    }

    /**
     * 显示Fragment
     */
    public static void showFragment(FragmentManager fragmentManager, Fragment fragment) {
        if (fragmentManager != null && fragment != null) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.show(fragment);
            transaction.commit();
            fragment.setUserVisibleHint(true);
        }
    }

    /**
     * 隐藏Fragment
     */
    public static void hideFragment(FragmentManager fragmentManager, Fragment fragment) {
        if (fragmentManager != null && fragment != null) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.hide(fragment);
            transaction.commit();
            fragment.setUserVisibleHint(false);
        }
    }

    /**
     * 显示或添加Fragment
     */
    public static void addOrShowFragment(FragmentManager fragmentManager,
                                         Fragment hideFragment,
                                         Fragment showFragment,
                                         int fragmentId) {
        //隐藏当前Fragment
        ActivityUtil.hideFragment(fragmentManager, hideFragment);
        //添加或显示传入的Fragment
        if (!showFragment.isAdded()) {
            ActivityUtil.addFragment(fragmentManager, showFragment, fragmentId);
        } else {
            //显示传入的Fragment
            ActivityUtil.showFragment(fragmentManager, showFragment);
        }
    }
}
