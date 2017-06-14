package com.yuzhai.yuzhaiwork_2.category.view;

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
import com.yuzhai.yuzhaiwork_2.category.adapter.ResumeRVAdapter;
import com.yuzhai.yuzhaiwork_2.category.bean.ResumeDatas;
import com.yuzhai.yuzhaiwork_2.category.contact.ResumeContact;
import com.yuzhai.yuzhaiwork_2.category.model.ResumeRemoteRepertory;
import com.yuzhai.yuzhaiwork_2.category.request.ResumeRequest;

/**
 * Created by 35429 on 2017/6/3.
 */

public class ResumeFragment extends BaseLazyLoadFragment implements ResumeContact.View,
        SwipeRefreshLayout.OnRefreshListener {
    private static final String CATEGORY = "category";
    private boolean isInit = false;

    public static ResumeFragment newInstance(String type) {
        Bundle args = new Bundle();
        args.putString(CATEGORY, type);
        ResumeFragment fragment = new ResumeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private ResumeContact.Presenter mPresenter;
    private SwipeRefreshLayout mResumeSrl;
    private RecyclerView mResumeRv;
    private ResumeDatas mResumeDatas;
    private ResumeRVAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.category_viewpager_resume_layout, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View root = getView();
        if (root != null) {
            mResumeSrl = (SwipeRefreshLayout) getView().findViewById(R.id.resume_refresh);
            //设置下拉刷新监听
            mResumeSrl.setOnRefreshListener(this);
            //设置刷新样式
            mResumeSrl.setColorSchemeResources(R.color.mainColor);

            mResumeRv = (RecyclerView) getView().findViewById(R.id.resume_recycler_view);
            mResumeRv.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
            mResumeRv.setItemAnimator(new DefaultItemAnimator());

            //视图加载完成
            isViewCreated = true;
        }
    }

    @Override
    public void setPresenter(ResumeContact.Presenter presenter) {
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
    protected void lazyLoadData() {
        super.lazyLoadData();
        //视图已加载完成但数据还未初始化
        if (isViewCreated && !isInit) {
            mPresenter.start();
            getResumeData();
            isInit = true;
        }
    }

    @Override
    public void setResumeData(ResumeDatas resumeDatas) {
        if (mResumeDatas == null) {
            mResumeDatas = resumeDatas;
            mAdapter = new ResumeRVAdapter(mResumeDatas);
            mResumeRv.setAdapter(mAdapter);
        } else {
            //把新返回的数据加入到数据集
            mResumeDatas.getResumes().addAll(0, resumeDatas.getResumes());
            mAdapter.notifyDataSetChanged();
            //新返回的数据不为空
            if (resumeDatas.getResumes().size() > 0) {
                //recyclerView滚动到顶部
                mResumeRv.smoothScrollToPosition(0);
            }
        }
    }

    @Override
    public void getResumeData() {
        mPresenter.getResumeData(new ResumeRequest(getArguments().getString(CATEGORY),
                ResumeRemoteRepertory.IS_FIRST_TIME));
    }

    @Override
    public void dimissLoading() {
        if (mResumeSrl.isRefreshing()) {
            mResumeSrl.setRefreshing(false);
        }
    }

    @Override
    public void startLoading() {
        if (!mResumeSrl.isRefreshing()) {
            mResumeSrl.setRefreshing(true);
        }
    }

    @Override
    public void onRefresh() {
        mPresenter.getResumeData(new ResumeRequest(getArguments().getString(CATEGORY),
                ResumeRemoteRepertory.NOT_FIRST_TIME));
    }
}
