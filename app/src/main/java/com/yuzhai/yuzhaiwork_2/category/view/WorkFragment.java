package com.yuzhai.yuzhaiwork_2.category.view;

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
import com.yuzhai.yuzhaiwork_2.category.adapter.WorkRVAdapter;
import com.yuzhai.yuzhaiwork_2.category.bean.WorkDatas;
import com.yuzhai.yuzhaiwork_2.category.contact.WorkContact;
import com.yuzhai.yuzhaiwork_2.category.model.WorkRemoteRepertory;
import com.yuzhai.yuzhaiwork_2.category.request.WorkRequest;
import com.yuzhai.yuzhaiwork_2.order_detail.view.WorkDetailActivity;


/**
 * Created by 35429 on 2017/6/3.
 */

public class WorkFragment extends BaseLazyLoadFragment implements WorkContact.View,
        SwipeRefreshLayout.OnRefreshListener,
        WorkRVAdapter.OnWorkItemClickListener {
    public static final String ORDER_ID = "order_id";
    private static final String CATEGORY = "category";

    public static WorkFragment newInstance(String type) {
        Bundle args = new Bundle();
        args.putString(CATEGORY, type);
        WorkFragment fragment = new WorkFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private WorkContact.Presenter mPresenter;
    private WorkDatas mWorkDatas;
    private SwipeRefreshLayout mWorkSrl;
    private RecyclerView mWorkRv;
    private WorkRVAdapter mAdapter;
    private boolean isInit = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.category_viewpager_work_layout, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View root = getView();
        if (root != null) {
            mWorkSrl = (SwipeRefreshLayout) root.findViewById(R.id.work_refresh);
            //设置下拉刷新监听
            mWorkSrl.setOnRefreshListener(this);
            //设置刷新样式
            mWorkSrl.setColorSchemeResources(R.color.mainColor);

            mWorkRv = (RecyclerView) root.findViewById(R.id.work_recycler_view);
            mWorkRv.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
            mWorkRv.setItemAnimator(new DefaultItemAnimator());
        }
    }

    @Override
    public void setPresenter(WorkContact.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(getActivity().getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
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
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void setWorkData(WorkDatas workDatas) {
        if (mWorkDatas == null) {
            mWorkDatas = workDatas;
            mAdapter = new WorkRVAdapter(mWorkDatas);
            mAdapter.setOnWorkItemClickListener(this);
            mWorkRv.setAdapter(mAdapter);
        } else {
            //把新返回的数据加入到数据集
            mWorkDatas.getOrders().addAll(0, workDatas.getOrders());
            mAdapter.notifyDataSetChanged();
            //新返回的数据不为空
            if (workDatas.getOrders().size() > 0) {
                //recyclerView滚动到顶部
                mWorkRv.smoothScrollToPosition(0);
            }
        }
    }


    @Override
    public void dimissLoading() {
        if (mWorkSrl.isRefreshing()) {
            mWorkSrl.setRefreshing(false);
        }
    }

    @Override
    public void startLoading() {
        if (!mWorkSrl.isRefreshing()) {
            mWorkSrl.setRefreshing(true);
        }
    }

    @Override
    public String getCategory() {
        return getArguments().getString(CATEGORY);
    }

    @Override
    public void onRefresh() {
        mPresenter.getWorkData(new WorkRequest(getArguments().getString(CATEGORY),
                WorkRemoteRepertory.NOT_FIRST_TIME));
    }

    @Override
    public void onWorkItemClick(String orderId) {
        Intent work_detail = new Intent(getActivity(), WorkDetailActivity.class);
        work_detail.putExtra(ORDER_ID, orderId);
        getActivity().startActivity(work_detail);
    }
}
