package com.yuzhai.yuzhaiwork_2.order_detail.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yuzhai.yuzhaiwork_2.R;
import com.yuzhai.yuzhaiwork_2.order_detail.adapter.NeededImageAdapter;
import com.yuzhai.yuzhaiwork_2.order_detail.bean.NeededImageData;
import com.yuzhai.yuzhaiwork_2.order_detail.bean.OrderAppliedDetailResponse;
import com.yuzhai.yuzhaiwork_2.order_detail.bean.OrderPublishedDetailResponse;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by 35429 on 2017/6/13.
 */

public class OrderAppliedDetailFragment extends Fragment {
    public static OrderAppliedDetailFragment newInstance() {
        Bundle args = new Bundle();
        OrderAppliedDetailFragment fragment = new OrderAppliedDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private TextView mTitle;
    private TextView mReward;
    private TextView mOrderId;
    private TextView mStatus;
    private TextView mDeadline;
    private TextView mDate;
    private TextView mTel;
    private TextView mDescription;
    private ViewPager mImageViewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_order_applied_detail, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        EventBus.getDefault().register(this);

        View root = getView();
        if (root != null) {
            mOrderId = (TextView) root.findViewById(R.id.id);
            mTitle = (TextView) root.findViewById(R.id.title);
            mReward = (TextView) root.findViewById(R.id.price);
            mStatus = (TextView) root.findViewById(R.id.status);
            mDeadline = (TextView) root.findViewById(R.id.deadline);
            mDate = (TextView) root.findViewById(R.id.date);
            mTel = (TextView) root.findViewById(R.id.tel);
            mDescription = (TextView) root.findViewById(R.id.description);
            mImageViewPager = (ViewPager) root.findViewById(R.id.image_viewpager);
        }

    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onDetailOrderData(OrderAppliedDetailResponse orderAppliedDetailResponse) {
        Log.d(TAG, orderAppliedDetailResponse.toString());
        setDetailData(orderAppliedDetailResponse);
    }

    public void setDetailData(OrderAppliedDetailResponse orderAppliedDetailResponse) {
        //设置订单号
        mOrderId.setText(orderAppliedDetailResponse.getDetailed_order().getOrder_id());
        //设置订单标题
        mTitle.setText(orderAppliedDetailResponse.getDetailed_order().getTitle());
        //设置订单状态
        mStatus.setText(orderAppliedDetailResponse.getDetailed_order().getStatus());
        //设置订单期限
        mDeadline.setText(orderAppliedDetailResponse.getDetailed_order().getDeadline());
        //设置订单发布日期
        mDate.setText(orderAppliedDetailResponse.getDetailed_order().getDate());
        //设置发布方联系电话
        mTel.setText(orderAppliedDetailResponse.getDetailed_order().getTel());
        //设置订单详细描述
        mDescription.setText(orderAppliedDetailResponse.getDetailed_order().getDescription());
        //设置订单金额
        mReward.setText(orderAppliedDetailResponse.getDetailed_order().getReward());
        //设置订单图片
        setNeededImages(orderAppliedDetailResponse.getDetailed_order().getPictures());
    }

    private void setNeededImages(List<OrderAppliedDetailResponse.WorkDetail.Pictures> pictures) {
        if (pictures != null && pictures.size() > 0) {
            List<NeededImageData> neededImageDatas = new ArrayList<>();
            for (OrderAppliedDetailResponse.WorkDetail.Pictures url : pictures) {
                neededImageDatas.add(new NeededImageData(url.getImage()));
            }
            Log.d(TAG, neededImageDatas.toString());
            new NeededImageAdapter(getActivity(), neededImageDatas, mImageViewPager);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
