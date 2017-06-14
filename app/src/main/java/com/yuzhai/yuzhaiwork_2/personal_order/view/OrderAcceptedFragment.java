package com.yuzhai.yuzhaiwork_2.personal_order.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yuzhai.yuzhaiwork_2.R;
import com.yuzhai.yuzhaiwork_2.base.view.BaseLazyLoadFragment;
import com.yuzhai.yuzhaiwork_2.order_detail.view.OrderAcceptedDetailActivity;
import com.yuzhai.yuzhaiwork_2.personal_order.adapter.AcceptedRVAdapter;
import com.yuzhai.yuzhaiwork_2.personal_order.adapter.AppliedRVAdapter;
import com.yuzhai.yuzhaiwork_2.personal_order.bean.OrderAcceptedDatas;
import com.yuzhai.yuzhaiwork_2.personal_order.contact.OrderAcceptedContact;
import com.yuzhai.yuzhaiwork_2.personal_order.model.OrderAcceptedRemoteRepertory;

import java.util.List;

/**
 * Created by 35429 on 2017/6/1.
 */

public class OrderAcceptedFragment extends BaseLazyLoadFragment implements OrderAcceptedContact.View,
        SwipeRefreshLayout.OnRefreshListener,
        AcceptedRVAdapter.OnAcceptedItemClickListener {
    public static final String ORDER_ID = "order_id";
    private static final String TAG = "OrderAcceptedFragment";
    private OrderAcceptedContact.Presenter mPresenter;
    private SwipeRefreshLayout mAcceptedSrl;
    private RecyclerView mAcceptedRv;
    private AcceptedRVAdapter mAdapter;
    private OrderAcceptedDatas mOrderAcceptedDatas;

    public static OrderAcceptedFragment newInstance() {
        Bundle args = new Bundle();
        OrderAcceptedFragment fragment = new OrderAcceptedFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.order_viewpager_accepted_layout, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View root = getView();
        if (root != null) {
            mAcceptedSrl = (SwipeRefreshLayout) getView().findViewById(R.id.accepted_order_refresh);
            //设置下拉刷新监听
            mAcceptedSrl.setOnRefreshListener(this);
            //设置刷新样式
            mAcceptedSrl.setColorSchemeResources(R.color.mainColor);

            mAcceptedRv = (RecyclerView) getView().findViewById(R.id.accepted_recyclerView);
            mAcceptedRv.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
            mAcceptedRv.setItemAnimator(new DefaultItemAnimator());

            //视图已加载成功
            isViewCreated = true;
        }
    }

    @Override
    public void setPresenter(OrderAcceptedContact.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(getActivity().getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void setAcceptedOrderData(OrderAcceptedDatas orderAcceptedDatas) {
        if (mOrderAcceptedDatas == null) {
            mOrderAcceptedDatas = orderAcceptedDatas;
            mAdapter = new AcceptedRVAdapter(mOrderAcceptedDatas);
            mAdapter.setOnAcceptedItemClickListener(this);
            mAcceptedRv.setAdapter(mAdapter);
        } else {
            //把新返回的数据加入到数据集
            mOrderAcceptedDatas.getOrders().addAll(0, orderAcceptedDatas.getOrders());
            mAdapter.notifyDataSetChanged();
            //新返回的数据不为空
            if (orderAcceptedDatas.getOrders().size() > 0) {
                //recyclerView滚动到顶部
                mAcceptedRv.smoothScrollToPosition(0);
            }
        }
    }

    public void dimissLoading() {
        if (mAcceptedSrl.isRefreshing()) {
            mAcceptedSrl.setRefreshing(false);
        }
    }

    @Override
    public void startLoading() {
        if (!mAcceptedSrl.isRefreshing()) {
            mAcceptedSrl.setRefreshing(true);
        }
    }

    @Override
    public void clearData() {
        mAdapter.notifyItemRangeRemoved(0, mOrderAcceptedDatas.getOrders().size());
        mOrderAcceptedDatas.getOrders().clear();
    }

    @Override
    protected void lazyLoadData() {
        super.lazyLoadData();
        Log.d(TAG, "lazyLoadData");
        if (mOrderAcceptedDatas != null) {
            if (mOrderAcceptedDatas.getOrders().size() > 0) {
                clearData();
            }
        }
        mPresenter.start();
    }

    @Override
    public void onRefresh() {
        //获取更新的数据
        mPresenter.getAcceptedOrderData(OrderAcceptedRemoteRepertory.NOT_FIRST_TIME);
    }

    @Override
    public void onAcceptedItemClick(String orderId) {
        Intent orderAcceptedDetail = new Intent(getActivity(), OrderAcceptedDetailActivity.class);
        orderAcceptedDetail.putExtra(ORDER_ID, orderId);
        getActivity().startActivity(orderAcceptedDetail);
    }
}
