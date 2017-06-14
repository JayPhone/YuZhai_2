package com.yuzhai.yuzhaiwork_2.order_detail.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baoyachi.stepview.VerticalStepView;
import com.yuzhai.yuzhaiwork_2.R;
import com.yuzhai.yuzhaiwork_2.base.util.SharePerferenceUtil;
import com.yuzhai.yuzhaiwork_2.order_detail.bean.OrderAppliedDetailResponse;
import com.yuzhai.yuzhaiwork_2.order_detail.bean.OrderPublishedDetailResponse;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 35429 on 2017/6/13.
 */

public class OrderAppliedProcessFragment extends Fragment {
    private static final String TAG = "OrderAppliedProcessFrag";

    public static OrderAppliedProcessFragment newInstance() {
        Bundle args = new Bundle();
        OrderAppliedProcessFragment fragment = new OrderAppliedProcessFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private VerticalStepView mVerticalStepView;
    private List<String> list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_applied_process, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        EventBus.getDefault().register(this);
        View root = getView();
        if (root != null) {
            mVerticalStepView = (VerticalStepView) getView().findViewById(R.id.step_view);
            mVerticalStepView
                    .setStepsViewIndicatorCompletedLineColor(ContextCompat.getColor(getActivity(), R.color.mainColor))//设置StepsViewIndicator完成线的颜色
                    .setStepsViewIndicatorUnCompletedLineColor(ContextCompat.getColor(getActivity(), R.color.color_BDBDBD))//设置StepsViewIndicator未完成线的颜色
                    .setStepViewComplectedTextColor(ContextCompat.getColor(getActivity(), R.color.color_757575))//设置StepsView text完成线的颜色
                    .setStepViewUnComplectedTextColor(ContextCompat.getColor(getActivity(), R.color.color_BDBDBD))//设置StepsView text未完成线的颜色
                    .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(getActivity(), R.drawable.complted))//设置StepsViewIndicator CompleteIcon
                    .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(getActivity(), R.drawable.complted_no))//设置StepsViewIndicator DefaultIcon
                    .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(getActivity(), R.drawable.attention))//设置StepsViewIndicator
                    .reverseDraw(false);
        }
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onDetailOrderData(OrderAppliedDetailResponse orderAppliedDetailResponse) {
        Log.d(TAG, orderAppliedDetailResponse.toString());
        setStepViewData(orderAppliedDetailResponse);
    }

    private void setStepViewData(OrderAppliedDetailResponse orderAppliedDetailResponse) {
        String userPhone = SharePerferenceUtil.getSharePerference(getActivity()
                .getApplicationContext(), SharePerferenceUtil.FileName.USER_INFO)
                .getString(SharePerferenceUtil.Key.USERNAME, "");
        list = new ArrayList<>();
        if (orderAppliedDetailResponse.getDetailed_order().getStatus().equals("取消发布订单")) {//发布方取消订单
            list.add("发起订单申请");
            list.add("订单已被发布方取消");
            list.add("申请订单失败");
        } else {//订单状态正常
            if (orderAppliedDetailResponse.getDetailed_order().getBidder().equals("0") ||
                    orderAppliedDetailResponse.getDetailed_order().getBidder()
                            .equals(userPhone)) {//无接收者或接收者为自己
                list.add("发起订单申请");
                list.add("等待发布方确认");
                list.add("申请订单成功");
            } else if (!orderAppliedDetailResponse.getDetailed_order().getBidder()
                    .equals(userPhone)) {//有接收者但不是自己
                list.add("发起订单申请");
                list.add("订单已被其他用户接收");
                list.add("申请订单失败");
            }
        }
        mVerticalStepView.setStepViewTexts(list);//总步骤

        if (orderAppliedDetailResponse.getDetailed_order().getStatus().equals("取消发布订单")) {//发布方取消订单
            mVerticalStepView.setStepsViewIndicatorComplectingPosition(2);
        } else {
            if (orderAppliedDetailResponse.getDetailed_order().getBidder()
                    .equals(userPhone)) {//接收者为自己
                mVerticalStepView.setStepsViewIndicatorComplectingPosition(2);
            } else if (orderAppliedDetailResponse.getDetailed_order()
                    .getBidder().equals("0")) {//无接收者
                mVerticalStepView.setStepsViewIndicatorComplectingPosition(1);
            } else if (!orderAppliedDetailResponse.getDetailed_order().getBidder()
                    .equals(userPhone)) {//接收者不是自己
                mVerticalStepView.setStepsViewIndicatorComplectingPosition(2);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
