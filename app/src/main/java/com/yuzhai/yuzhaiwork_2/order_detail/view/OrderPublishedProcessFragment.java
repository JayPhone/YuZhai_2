package com.yuzhai.yuzhaiwork_2.order_detail.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.baoyachi.stepview.VerticalStepView;
import com.bumptech.glide.Glide;
import com.yuzhai.yuzhaiwork_2.R;
import com.yuzhai.yuzhaiwork_2.base.http.IPConfig;
import com.yuzhai.yuzhaiwork_2.order_detail.bean.OrderPublishedDetailResponse;
import com.yuzhai.yuzhaiwork_2.user_data.view.UserDataActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.yuzhai.yuzhaiwork_2.user_data.view.UserDataActivity.AVATAR;
import static com.yuzhai.yuzhaiwork_2.user_data.view.UserDataActivity.ORDER;
import static com.yuzhai.yuzhaiwork_2.user_data.view.UserDataActivity.PRICE;

/**
 * Created by 35429 on 2017/6/13.
 */

public class OrderPublishedProcessFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "OrderPublishedProcessFr";

    public static OrderPublishedProcessFragment newInstance() {
        Bundle args = new Bundle();
        OrderPublishedProcessFragment fragment = new OrderPublishedProcessFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private VerticalStepView mVerticalStepView;
    private LinearLayout mApplyUserLayout;
    private List<CircleImageView> mImageViewList;
    private OrderPublishedDetailResponse mOrderPublishedDetailResponse;

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_published_process, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        EventBus.getDefault().register(this);
        View root = getView();
        if (root != null) {
            mApplyUserLayout = (LinearLayout) root.findViewById(R.id.apply_user);
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
    public void onDetailOrderData(OrderPublishedDetailResponse orderPublishedDetailResponse) {
        Log.d(TAG, orderPublishedDetailResponse.toString());
        mOrderPublishedDetailResponse = orderPublishedDetailResponse;
        setApplicantAvatars(orderPublishedDetailResponse.getDetailed_order().getApplicant_avatars());
        setStepViewData(orderPublishedDetailResponse.getDetailed_order().getBidder());
    }

    private void setStepViewData(String binder) {
        //初始化进度数据
        List<String> list = new ArrayList<>();
        list.add("订单发布");
        list.add("等待用户申请接收订单");
        list.add("同意用户接收订单");
        list.add("订单开始");
        list.add("确认收货");
        list.add("订单完成");
        //设置进度流程
        mVerticalStepView.setStepViewTexts(list);//总步骤

        if (!binder.equals("0")) {//有接收者
            mVerticalStepView.setStepsViewIndicatorComplectingPosition(3);
        } else {//没有接收者
            mVerticalStepView.setStepsViewIndicatorComplectingPosition(1);
        }
    }

    private void setApplicantAvatars(List<OrderPublishedDetailResponse.WorkDetail.ApplicantAvatars> applicantAvatars) {
        if (applicantAvatars != null && applicantAvatars.size() > 0) {
            mImageViewList = new ArrayList<>();
            int layoutWidth = mApplyUserLayout.getWidth();
            int circleImageWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 45, getResources().getDisplayMetrics());
            int circleImageHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 45, getResources().getDisplayMetrics());
            int marginLeft = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics());

            int hasCount = applicantAvatars.size();
            int maxCount = (layoutWidth + marginLeft) / (circleImageWidth + marginLeft);
            maxCount = hasCount >= maxCount ? maxCount : hasCount;

            for (int i = 0; i < maxCount; i++) {
                CircleImageView circleImageView = new CircleImageView(getActivity());
                circleImageView.setBorderColor(Color.WHITE);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(circleImageWidth, circleImageHeight);
                if (i != 0) {
                    params.setMargins(marginLeft, 0, 0, 0);
                }
                circleImageView.setLayoutParams(params);
                //获取头像
                Glide.with(OrderPublishedProcessFragment.this)
                        .load(IPConfig.IMAGE_PREFIX + applicantAvatars.get(i).getApplicantAvatar())
                        .dontAnimate()
                        .placeholder(R.drawable.default_image)
                        .error(R.drawable.default_image)
                        .into(circleImageView);
                circleImageView.setOnClickListener(this);
                circleImageView.setTag(i);
                mImageViewList.add(circleImageView);
                mApplyUserLayout.addView(circleImageView);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onClick(View v) {
        Log.i(TAG, String.valueOf(v.getTag()));
        Log.i(TAG, mImageViewList.toString());
        for (int id = 0; id < mImageViewList.size(); id++) {
            Object imageTag = mImageViewList.get(id).getTag();
            if (imageTag == v.getTag()) {
                Intent userData = new Intent(getActivity(), UserDataActivity.class);
                //计算支付金额
                double rewardPrice = Double.parseDouble(mOrderPublishedDetailResponse.
                        getDetailed_order().getReward());
                double cashDeposit = rewardPrice * 0.3;
                double serviceCharge = rewardPrice * 0.03;
                double allPrice = rewardPrice + cashDeposit + serviceCharge;
                userData.putExtra(PRICE, String.valueOf(allPrice));
                userData.putExtra(ORDER, mOrderPublishedDetailResponse.getDetailed_order().getOrder_id());
                userData.putExtra(AVATAR, mOrderPublishedDetailResponse.getDetailed_order()
                        .getApplicant_avatars().get(id).getApplicantAvatar());
                getActivity().startActivity(userData);
            }
        }
    }
}
