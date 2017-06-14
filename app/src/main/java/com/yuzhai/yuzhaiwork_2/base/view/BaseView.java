package com.yuzhai.yuzhaiwork_2.base.view;

/**
 * Created by 35429 on 2017/5/17.
 * V层的接口类
 */

public interface BaseView<T> {
    void setPresenter(T presenter);

    void showToast(String msg);

    boolean isActive();
}
