package com.yuzhai.yuzhaiwork_2.personal_order.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yuzhai.yuzhaiwork_2.R;
import com.yuzhai.yuzhaiwork_2.base.view.BaseLazyLoadFragment;
import com.yuzhai.yuzhaiwork_2.order_detail.view.OrderPublishedDetailActivity;
import com.yuzhai.yuzhaiwork_2.personal_order.adapter.PublishedRVAdapter;
import com.yuzhai.yuzhaiwork_2.personal_order.bean.OrderPublishedDatas;
import com.yuzhai.yuzhaiwork_2.personal_order.contact.OrderPublishedContact;
import com.yuzhai.yuzhaiwork_2.personal_order.model.OrderPublishedRemoteRepertory;


/**
 * Created by 35429 on 2017/6/1.
 */

public class OrderPublishedFragment extends BaseLazyLoadFragment implements OrderPublishedContact.View,
        SwipeRefreshLayout.OnRefreshListener,
        PublishedRVAdapter.OnPublishedItemClickListener {
    public static final String ORDER_ID = "order_id";
    private static final String TAG = "OrderPublishedFragment";
    private OrderPublishedContact.Presenter mPresenter;
    private SwipeRefreshLayout mPublishedSrl;
    private RecyclerView mPublishedRv;
    private PublishedRVAdapter mAdapter;
    private OrderPublishedDatas mOrderPublishedDatas;
    private boolean isInit = false;

    public static OrderPublishedFragment newInstance() {
        Bundle args = new Bundle();
        OrderPublishedFragment fragment = new OrderPublishedFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.order_viewpager_published_layout, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View root = getView();
        if (root != null) {
            mPublishedSrl = (SwipeRefreshLayout) getView().findViewById(R.id.published_order_refresh);
            //设置下拉刷新监听
            mPublishedSrl.setOnRefreshListener(this);
            //设置刷新样式
            mPublishedSrl.setColorSchemeResources(R.color.mainColor);

            mPublishedRv = (RecyclerView) getView().findViewById(R.id.published_recyclerView);
            mPublishedRv.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
            mPublishedRv.setItemAnimator(new DefaultItemAnimator());
        }
        //视图已成功加载
        isViewCreated = true;
    }

    @Override
    public void setPresenter(OrderPublishedContact.Presenter presenter) {
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
    public void setPublishedOrderData(OrderPublishedDatas orderPublishedDatas) {
        if (mOrderPublishedDatas == null) {
            mOrderPublishedDatas = orderPublishedDatas;
            mAdapter = new PublishedRVAdapter(mOrderPublishedDatas);
            mAdapter.setOnPublishedItemClickListener(this);
            mPublishedRv.setAdapter(mAdapter);
        } else {
            //把新返回的数据加入到数据集
            mOrderPublishedDatas.getOrders().addAll(0, orderPublishedDatas.getOrders());
            mAdapter.notifyDataSetChanged();
            //新返回的数据不为空
            if (orderPublishedDatas.getOrders().size() > 0) {
                //recyclerView滚动到顶部
                mPublishedRv.smoothScrollToPosition(0);
            }
        }
    }

    @Override
    public void dimissLoading() {
        if (mPublishedSrl.isRefreshing()) {
            mPublishedSrl.setRefreshing(false);
        }
    }

    @Override
    public void startLoading() {
        if (!mPublishedSrl.isRefreshing()) {
            mPublishedSrl.setRefreshing(true);
        }
    }

    @Override
    public void clearData() {
        mAdapter.notifyItemRangeRemoved(0, mOrderPublishedDatas.getOrders().size());
        mOrderPublishedDatas.getOrders().clear();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isInit) {
            mPresenter.start();
            isInit = true;
        }
    }

    @Override
    protected void lazyLoadData() {
        super.lazyLoadData();
        if (isViewCreated) {
            if (mOrderPublishedDatas != null) {
                if (mOrderPublishedDatas.getOrders().size() > 0) {
                    clearData();
                }
            }
            mPresenter.start();
        }
    }

    @Override
    public void onRefresh() {
        //获取更新的数据
        mPresenter.getPublishedOrderData(OrderPublishedRemoteRepertory.NOT_FIRST_TIME);
    }

    @Override
    public void onPublishedItemClick(String orderId) {
        Intent publishedOrderDetail = new Intent(getActivity(), OrderPublishedDetailActivity.class);
        publishedOrderDetail.putExtra(ORDER_ID, orderId);
        startActivity(publishedOrderDetail);
    }
}
