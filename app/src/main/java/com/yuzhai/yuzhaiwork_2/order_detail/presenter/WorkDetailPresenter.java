package com.yuzhai.yuzhaiwork_2.order_detail.presenter;

import com.yuzhai.yuzhaiwork_2.base.model.BaseModel;
import com.yuzhai.yuzhaiwork_2.base.view.UnRepeatToast;
import com.yuzhai.yuzhaiwork_2.order_detail.bean.ApplyOrderResponse;
import com.yuzhai.yuzhaiwork_2.order_detail.bean.WorkDetailResponse;
import com.yuzhai.yuzhaiwork_2.order_detail.contact.WorkDetailContact;
import com.yuzhai.yuzhaiwork_2.order_detail.model.IWorkDetailModel;
import com.yuzhai.yuzhaiwork_2.order_detail.model.WorkDetailRemoteRepertory;
import com.yuzhai.yuzhaiwork_2.order_detail.request.ApplyOrderRequest;
import com.yuzhai.yuzhaiwork_2.order_detail.request.WorkDetailRequest;
import com.yuzhai.yuzhaiwork_2.order_detail.view.WorkDetailActivity;
import com.yuzhai.yuzhaiwork_2.order_detail.view.WorkDetailFragment;

import java.lang.ref.WeakReference;

/**
 * Created by 35429 on 2017/6/13.
 */

public class WorkDetailPresenter implements WorkDetailContact.Presenter {
    private WeakReference<WorkDetailContact.View> mWorkDetailView;
    private IWorkDetailModel mWorkDetailModel = new WorkDetailRemoteRepertory();

    public WorkDetailPresenter(WorkDetailFragment workDetailFragment) {
        mWorkDetailView = new WeakReference<WorkDetailContact.View>(workDetailFragment);
        mWorkDetailView.get().setPresenter(this);
    }

    @Override
    public void start() {
        sendWorkDetailRequest(new WorkDetailRequest(mWorkDetailView.get().getOrderId()));
    }

    @Override
    public void clear() {
        if (mWorkDetailView.get() != null) {
            mWorkDetailView.clear();
        }
    }

    @Override
    public void sendWorkDetailRequest(WorkDetailRequest workDetailRequest) {
        mWorkDetailView.get().showProgressDialog("正在加载数据，请稍后");
        mWorkDetailModel.sendWorkDetailRequest(workDetailRequest, new BaseModel.OnRequestResponse<WorkDetailResponse>() {
            @Override
            public void onSuccess(WorkDetailResponse workDetailResponse) {
                if (mWorkDetailView.get().isActive()) {
                    mWorkDetailView.get().hideProgressDialog();
                    mWorkDetailView.get().setWorkDetailData(workDetailResponse);
                }
            }

            @Override
            public void onFailure(Throwable e) {
                if (mWorkDetailView.get().isActive()) {
                    mWorkDetailView.get().hideProgressDialog();
                    mWorkDetailView.get().showToast("服务器异常");
                }
            }
        });
    }

    @Override
    public void sentApplyOrderRequest(ApplyOrderRequest applyOrderRequest) {
        mWorkDetailView.get().showProgressDialog("正在申请订单，请稍后");
        mWorkDetailModel.sendApplyOrderRequest(applyOrderRequest, new BaseModel.OnRequestResponse<ApplyOrderResponse>() {
            @Override
            public void onSuccess(ApplyOrderResponse applyOrderResponse) {
                if (mWorkDetailView.get().isActive()) {
                    if (applyOrderResponse.getCode().equals("1")) {
                        mWorkDetailView.get().showToast("订单申请成功");
                    }
                    //订单重复申请接收
                    else if (applyOrderResponse.getCode().equals("2")) {
                        mWorkDetailView.get().showToast("你已成功申请，请不要重复申请");
                    }
                    mWorkDetailView.get().hideProgressDialog();
                }
            }

            @Override
            public void onFailure(Throwable e) {
                if (mWorkDetailView.get().isActive()) {
                    mWorkDetailView.get().hideProgressDialog();
                }
            }
        });
    }

}
