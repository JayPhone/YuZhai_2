package com.yuzhai.yuzhaiwork_2.personal_order.presenter;

import com.yuzhai.yuzhaiwork_2.base.model.BaseModel;
import com.yuzhai.yuzhaiwork_2.personal_order.bean.OrderAppliedDatas;
import com.yuzhai.yuzhaiwork_2.personal_order.contact.OrderAppliedContact;
import com.yuzhai.yuzhaiwork_2.personal_order.model.IOrderAppliedModel;
import com.yuzhai.yuzhaiwork_2.personal_order.model.OrderAppliedLocalRepertory;
import com.yuzhai.yuzhaiwork_2.personal_order.model.OrderAppliedRemoteRepertory;
import com.yuzhai.yuzhaiwork_2.personal_order.view.OrderAppliedFragment;

import java.lang.ref.WeakReference;

/**
 * Created by 35429 on 2017/6/1.
 */

public class OrderAppliedPresenter implements OrderAppliedContact.Presenter {
    private WeakReference<OrderAppliedContact.View> mOrderAppliedView;
    private IOrderAppliedModel mOrderAppliedModel = new OrderAppliedRemoteRepertory();

    public OrderAppliedPresenter(OrderAppliedFragment orderAppliedFragment) {
        this.mOrderAppliedView = new WeakReference<OrderAppliedContact.View>(orderAppliedFragment);
        this.mOrderAppliedView.get().setPresenter(this);
    }

    @Override
    public void start() {
        mOrderAppliedView.get().startLoading();
        getAppliedOrderData(OrderAppliedRemoteRepertory.IS_FIRST_TIME);
    }

    @Override
    public void clear() {
        if (mOrderAppliedView.get() != null) {
            mOrderAppliedView.clear();
        }
    }

    @Override
    public void getAppliedOrderData(String isFirst) {
        mOrderAppliedModel.getAppliedOrderData(isFirst, new BaseModel.OnRequestResponse<OrderAppliedDatas>() {
            @Override
            public void onSuccess(OrderAppliedDatas orderAppliedDatas) {
                if (orderAppliedDatas.getOrders() != null) {
                    if (mOrderAppliedView.get().isActive()) {
                        mOrderAppliedView.get().setAppliedOrderData(orderAppliedDatas);
                        mOrderAppliedView.get().showToast("更新了" + orderAppliedDatas.getOrders().size() + "条数据");
                    }
                } else {
                    mOrderAppliedView.get().showToast("请先登录");
                }
                mOrderAppliedView.get().dimissLoading();
            }

            @Override
            public void onFailure(Throwable e) {
                if (mOrderAppliedView.get().isActive()) {
                    mOrderAppliedView.get().showToast("数据加载错误");
                    mOrderAppliedView.get().dimissLoading();
                }
            }
        });
    }
}
