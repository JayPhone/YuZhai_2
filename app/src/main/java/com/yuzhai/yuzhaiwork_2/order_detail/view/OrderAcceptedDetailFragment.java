package com.yuzhai.yuzhaiwork_2.order_detail.view;

import android.content.Intent;
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
import com.yuzhai.yuzhaiwork_2.base.global.CustomApplication;
import com.yuzhai.yuzhaiwork_2.order_detail.adapter.NeededImageAdapter;
import com.yuzhai.yuzhaiwork_2.order_detail.bean.NeededImageData;
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

public class OrderAcceptedDetailFragment extends Fragment {
    public static OrderAcceptedDetailFragment newInstance() {
        Bundle args = new Bundle();
        OrderAcceptedDetailFragment fragment = new OrderAcceptedDetailFragment();
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
        return inflater.inflate(R.layout.fragment_order_accepted_detail, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        EventBus.getDefault().register(this);
        View root = getView();
        if (root != null) {
            mOrderId = (TextView) getView().findViewById(R.id.id);
            mTitle = (TextView) getView().findViewById(R.id.title);
            mReward = (TextView) getView().findViewById(R.id.price);
            mStatus = (TextView) getView().findViewById(R.id.status);
            mDeadline = (TextView) getView().findViewById(R.id.deadline);
            mDate = (TextView) getView().findViewById(R.id.date);
            mTel = (TextView) getView().findViewById(R.id.tel);
            mDescription = (TextView) getView().findViewById(R.id.description);
            mImageViewPager = (ViewPager) getView().findViewById(R.id.image_viewpager);
//            mImageFlipper.setOnItemClickListener(new CustomViewFlipper.OnItemClickListener() {
//                @Override
//                public void onItemClick(int position) {
//                    if (CustomApplication.isConnect) {
//                        Intent showImage = new Intent(getActivity(), ShowImageActivity.class);
//                        showImage.putExtra(IMAGE_URL, mOrder.getPictures().get(position).getImage());
//                        Log.i("url", mOrder.getPictures().get(position).getImage());
//                        startActivity(showImage);
//                    }
//                }
//            });
        }
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onDetailOrderData(OrderAcceptedDetailResponse orderAcceptedDetailResponse) {
        Log.d(TAG, orderAcceptedDetailResponse.toString());
        setDetailData(orderAcceptedDetailResponse);
    }

    public void setDetailData(OrderAcceptedDetailResponse orderAcceptedDetailResponse) {
        //设置订单号
        mOrderId.setText(orderAcceptedDetailResponse.getDetailed_order().getOrder_id());
        //设置订单标题
        mTitle.setText(orderAcceptedDetailResponse.getDetailed_order().getTitle());
        //设置订单状态
        mStatus.setText(orderAcceptedDetailResponse.getDetailed_order().getStatus());
        //设置订单期限
        mDeadline.setText(orderAcceptedDetailResponse.getDetailed_order().getDeadline());
        //设置订单发布日期
        mDate.setText(orderAcceptedDetailResponse.getDetailed_order().getDate());
        //设置发布方联系电话
        mTel.setText(orderAcceptedDetailResponse.getDetailed_order().getTel());
        //设置订单详细描述
        mDescription.setText(orderAcceptedDetailResponse.getDetailed_order().getDescription());
        //设置订单金额
        mReward.setText(orderAcceptedDetailResponse.getDetailed_order().getReward());
        //设置订单图片
        setNeededImages(orderAcceptedDetailResponse.getDetailed_order().getPictures());
    }

    private void setNeededImages(List<OrderAcceptedDetailResponse.WorkDetail.Pictures> pictures) {
        if (pictures != null && pictures.size() > 0) {
            List<NeededImageData> neededImageDatas = new ArrayList<>();
            for (OrderAcceptedDetailResponse.WorkDetail.Pictures url : pictures) {
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
