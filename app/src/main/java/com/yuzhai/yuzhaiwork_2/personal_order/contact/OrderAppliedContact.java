package com.yuzhai.yuzhaiwork_2.personal_order.contact;

import com.yuzhai.yuzhaiwork_2.base.presenter.BasePresenter;
import com.yuzhai.yuzhaiwork_2.base.view.BaseView;
import com.yuzhai.yuzhaiwork_2.personal_order.bean.OrderAppliedDatas;

import java.util.List;

/**
 * Created by 35429 on 2017/6/1.
 */

public interface OrderAppliedContact {
    interface View extends BaseView<Presenter> {
        void setAppliedOrderData(OrderAppliedDatas orderAppliedDatas);

        void dimissLoading();

        void startLoading();

        void clearData();
    }

    interface Presenter extends BasePresenter {
        void getAppliedOrderData(String isFirst);
    }
}
