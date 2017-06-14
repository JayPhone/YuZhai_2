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
import com.yuzhai.yuzhaiwork_2.order_detail.bean.OrderAcceptedDetailResponse;
import com.yuzhai.yuzhaiwork_2.order_detail.bean.OrderAppliedDetailResponse;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by 35429 on 2017/6/13.
 */

public class OrderAcceptedProcessFragment extends Fragment {
    public static OrderAcceptedProcessFragment newInstance() {
        Bundle args = new Bundle();
        OrderAcceptedProcessFragment fragment = new OrderAcceptedProcessFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private VerticalStepView mVerticalStepView;
    private List<String> list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_accepted_process, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        EventBus.getDefault().register(this);
        View root = getView();
        if (root != null) {
            mVerticalStepView = (VerticalStepView) root.findViewById(R.id.step_view);
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
    public void onDetailOrderData(OrderAcceptedDetailResponse orderAcceptedDetailResponse) {
        Log.d(TAG, orderAcceptedDetailResponse.toString());
        setStepViewData(orderAcceptedDetailResponse);
    }

    private void setStepViewData(OrderAcceptedDetailResponse orderAcceptedDetailResponse) {
        list = new ArrayList<>();
        if (orderAcceptedDetailResponse.getDetailed_order().getStatus().equals("取消发布订单")) {
            list.add("订单接收成功");
            list.add("订单已被发布方取消");
            list.add("订单停止");
        } else {
            list.add("订单接收成功");
            list.add("订单开始");
            list.add("提交作品");
            list.add("订单完成");
        }

        mVerticalStepView.setStepViewTexts(list);//总步骤
        if (orderAcceptedDetailResponse.getDetailed_order().getStatus().equals("取消发布订单")) {
            mVerticalStepView.setStepsViewIndicatorComplectingPosition(2);//设置完成的步数
        } else {
            mVerticalStepView.setStepsViewIndicatorComplectingPosition(1);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
