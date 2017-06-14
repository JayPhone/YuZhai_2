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
import com.yuzhai.yuzhaiwork_2.order_detail.view.OrderAppliedDetailActivity;
import com.yuzhai.yuzhaiwork_2.personal_order.adapter.AppliedRVAdapter;
import com.yuzhai.yuzhaiwork_2.personal_order.adapter.PublishedRVAdapter;
import com.yuzhai.yuzhaiwork_2.personal_order.bean.OrderAppliedDatas;
import com.yuzhai.yuzhaiwork_2.personal_order.bean.OrderPublishedDatas;
import com.yuzhai.yuzhaiwork_2.personal_order.contact.OrderAppliedContact;
import com.yuzhai.yuzhaiwork_2.personal_order.model.OrderAppliedRemoteRepertory;

import java.util.List;

/**
 * Created by 35429 on 2017/6/1.
 */

public class OrderAppliedFragment extends BaseLazyLoadFragment implements OrderAppliedContact.View,
        SwipeRefreshLayout.OnRefreshListener,
        AppliedRVAdapter.OnAppliedItemClickListener {
    public static final String ORDER_ID = "order_id";
    private static final String TAG = "OrderAppliedFragment";
    private OrderAppliedContact.Presenter mPresenter;
    private SwipeRefreshLayout mAppliedSrl;
    private RecyclerView mAppliedRv;
    private AppliedRVAdapter mAdapter;
    private OrderAppliedDatas mOrderAppliedDatas;

    public static OrderAppliedFragment newInstance() {
        Bundle args = new Bundle();
        OrderAppliedFragment fragment = new OrderAppliedFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.order_viewpager_applied_layout, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View root = getView();
        if (root != null) {
            mAppliedSrl = (SwipeRefreshLayout) getView().findViewById(R.id.applied_order_refresh);
            //设置下拉刷新监听
            mAppliedSrl.setOnRefreshListener(this);
            //设置刷新样式
            mAppliedSrl.setColorSchemeResources(R.color.mainColor);

            mAppliedRv = (RecyclerView) getView().findViewById(R.id.applied_recyclerView);
            mAppliedRv.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
            mAppliedRv.setItemAnimator(new DefaultItemAnimator());

            //视图已成功加载
            isViewCreated = true;
        }
    }

    @Override
    public void setPresenter(OrderAppliedContact.Presenter presenter) {
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
    public void setAppliedOrderData(OrderAppliedDatas orderAppliedDatas) {
        if (mOrderAppliedDatas == null) {
            mOrderAppliedDatas = orderAppliedDatas;
            mAdapter = new AppliedRVAdapter(mOrderAppliedDatas);
            mAdapter.setOnAppliedItemClickListener(this);
            mAppliedRv.setAdapter(mAdapter);
        } else {
            //把新返回的数据加入到数据集
            mOrderAppliedDatas.getOrders().addAll(0, orderAppliedDatas.getOrders());
            mAdapter.notifyDataSetChanged();
            //新返回的数据不为空
            if (orderAppliedDatas.getOrders().size() > 0) {
                //recyclerView滚动到顶部
                mAppliedRv.smoothScrollToPosition(0);
            }
        }

    }

    @Override
    public void dimissLoading() {
        if (mAppliedSrl.isRefreshing()) {
            mAppliedSrl.setRefreshing(false);
        }
    }

    @Override
    public void startLoading() {
        if (!mAppliedSrl.isRefreshing()) {
            mAppliedSrl.setRefreshing(true);
        }
    }

    @Override
    public void clearData() {
        mAdapter.notifyItemRangeRemoved(0, mOrderAppliedDatas.getOrders().size());
        mOrderAppliedDatas.getOrders().clear();
    }

    @Override
    protected void lazyLoadData() {
        super.lazyLoadData();
        Log.d(TAG, "lazyLoadData");
        if (mOrderAppliedDatas != null) {
            if (mOrderAppliedDatas.getOrders().size() > 0) {
                clearData();
            }
        }
        mPresenter.start();
    }

    @Override
    public void onRefresh() {
        //获取更新的数据
        mPresenter.getAppliedOrderData(OrderAppliedRemoteRepertory.NOT_FIRST_TIME);
    }

    @Override
    public void onAppliedItemClick(String orderId) {
        Intent appliedOrderDetail = new Intent(getActivity(), OrderAppliedDetailActivity.class);
        appliedOrderDetail.putExtra(ORDER_ID, orderId);
        getActivity().startActivity(appliedOrderDetail);
    }
}
