package com.yuzhai.yuzhaiwork_2.personal_order.presenter;

import android.util.Log;

import com.yuzhai.yuzhaiwork_2.base.model.BaseModel;
import com.yuzhai.yuzhaiwork_2.personal_order.bean.OrderPublishedDatas;
import com.yuzhai.yuzhaiwork_2.personal_order.contact.OrderPublishedContact;
import com.yuzhai.yuzhaiwork_2.personal_order.model.IOrderPublishedModel;
import com.yuzhai.yuzhaiwork_2.personal_order.model.OrderPublishedLocalRepertory;
import com.yuzhai.yuzhaiwork_2.personal_order.model.OrderPublishedRemoteRepertory;
import com.yuzhai.yuzhaiwork_2.personal_order.view.OrderPublishedFragment;

import java.lang.ref.WeakReference;

/**
 * Created by 35429 on 2017/6/1.
 */

public class OrderPublishedPresenter implements OrderPublishedContact.Presenter {
    private static final String TAG = "OrderPublishedPresenter";
    private WeakReference<OrderPublishedContact.View> mOrderPublishedView;
    //用本地数据测试
    private IOrderPublishedModel mOrderPublishedModel = new OrderPublishedRemoteRepertory();

    public OrderPublishedPresenter(OrderPublishedFragment orderPublishedFragment) {
        mOrderPublishedView = new WeakReference<OrderPublishedContact.View>(orderPublishedFragment);
        mOrderPublishedView.get().setPresenter(this);
    }

    @Override
    public void start() {
        mOrderPublishedView.get().startLoading();
        //初始化数据，第一次加载
        getPublishedOrderData(OrderPublishedRemoteRepertory.IS_FIRST_TIME);
    }

    @Override
    public void clear() {
        if (mOrderPublishedView.get() != null) {
            mOrderPublishedView.clear();
        }
    }

    @Override
    public void getPublishedOrderData(String isFirst) {
        mOrderPublishedModel.getPublishedOrderData(isFirst, new BaseModel.OnRequestResponse<OrderPublishedDatas>() {
            @Override
            public void onSuccess(OrderPublishedDatas orderPublishedDatas) {
                if (mOrderPublishedView.get().isActive()) {
                    if (orderPublishedDatas.getOrders() != null) {
                        mOrderPublishedView.get().setPublishedOrderData(orderPublishedDatas);
                        mOrderPublishedView.get().showToast("更新了" + orderPublishedDatas.getOrders().size() + "条数据");
                        Log.d(TAG, String.valueOf(orderPublishedDatas.getOrders().size()));
                    } else {
                        mOrderPublishedView.get().showToast("请先登录");
                    }
                    mOrderPublishedView.get().dimissLoading();
                }
            }

            @Override
            public void onFailure(Throwable e) {
                if (mOrderPublishedView.get().isActive()) {
                    mOrderPublishedView.get().showToast("数据加载错误");
                    mOrderPublishedView.get().dimissLoading();
                }
            }
        });
    }
}
