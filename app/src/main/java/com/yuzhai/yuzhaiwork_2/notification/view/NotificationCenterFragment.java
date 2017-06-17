package com.yuzhai.yuzhaiwork_2.notification.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yuzhai.yuzhaiwork_2.R;
import com.yuzhai.yuzhaiwork_2.notification.adapter.NotificationRVAdapter;
import com.yuzhai.yuzhaiwork_2.notification.bean.NotificationDB;
import com.yuzhai.yuzhaiwork_2.notification.contact.NotificationCenterContact;
import com.yuzhai.yuzhaiwork_2.notification.request.GetNotificationRequest;
import com.yuzhai.yuzhaiwork_2.order_detail.view.OrderAcceptedDetailActivity;
import com.yuzhai.yuzhaiwork_2.order_detail.view.OrderAppliedDetailActivity;
import com.yuzhai.yuzhaiwork_2.order_detail.view.OrderPublishedDetailActivity;
import com.yuzhai.yuzhaiwork_2.personal_order.view.OrderAcceptedFragment;
import com.yuzhai.yuzhaiwork_2.personal_order.view.OrderPublishedFragment;

import org.litepal.crud.DataSupport;

import java.util.Collection;
import java.util.List;

import static com.yuzhai.yuzhaiwork_2.notification.adapter.NotificationRVAdapter.APPLY;
import static com.yuzhai.yuzhaiwork_2.notification.adapter.NotificationRVAdapter.RECEIVE;

/**
 * Created by 35429 on 2017/6/17.
 */

public class NotificationCenterFragment extends Fragment implements NotificationCenterContact.View,
        Toolbar.OnMenuItemClickListener,
        SwipeRefreshLayout.OnRefreshListener,
        NotificationRVAdapter.OnNotificationClickListener {

    public static NotificationCenterFragment newInstance() {
        Bundle args = new Bundle();
        NotificationCenterFragment fragment = new NotificationCenterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private NotificationCenterContact.Presenter mPresenter;
    private RecyclerView mRecyclerView;
    private NotificationRVAdapter mNotificationRVAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ProgressDialog mProgressDialog;
    private Dialog mDeleteAllNotificationsDialog;
    private List<NotificationDB> mNotificationDBList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notification_center, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View root = getView();
        if (root != null) {
            Toolbar toolbar = (Toolbar) root.findViewById(R.id.notification_toolbar);
            toolbar.inflateMenu(R.menu.notification_center_menu);
            toolbar.setOnMenuItemClickListener(this);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().finish();
                }
            });

            mSwipeRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.notification_refresh);
            //设置下拉刷新监听
            mSwipeRefreshLayout.setOnRefreshListener(this);
            //设置刷新样式
            mSwipeRefreshLayout.setColorSchemeResources(R.color.mainColor);
            mRecyclerView = (RecyclerView) root.findViewById(R.id.notification_recycler_view);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        }
    }

    @Override
    public void setPresenter(NotificationCenterContact.Presenter presenter) {
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
    public void setNotificationData(List<NotificationDB> notificationDBs) {
        if (mNotificationDBList == null) {
            mNotificationDBList = notificationDBs;
            mNotificationRVAdapter = new NotificationRVAdapter(mNotificationDBList);
            mNotificationRVAdapter.setOnNotificationClickListener(this);
            mRecyclerView.setAdapter(mNotificationRVAdapter);
        } else {
            //把新返回的数据加入到数据集
            mNotificationDBList.addAll(notificationDBs);
            mNotificationRVAdapter.notifyDataSetChanged();
            //新返回的数据不为空
            if (notificationDBs.size() > 0) {
                //recyclerView滚动到顶部
                mRecyclerView.smoothScrollToPosition(mNotificationDBList.size() - 1);
            }
        }
    }

    @Override
    public void showProgressDialog(String msg) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        }
        mProgressDialog.setMessage(msg);
        mProgressDialog.show();
    }

    @Override
    public void hideProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void showDeleteAllNotificationDialog() {
        if (mDeleteAllNotificationsDialog == null) {
            mDeleteAllNotificationsDialog = new AlertDialog.Builder(getActivity())
                    .setTitle("删除所有通知")
                    .setMessage("确认要删除所有通知吗，删除后将不能恢复，请谨慎操作！")
                    .setPositiveButton("我确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //删除数据库中的所有通知数据
                            mPresenter.deleteAllNotification();
                        }
                    })
                    .setNegativeButton("取消", null)
                    .create();
        }
        mDeleteAllNotificationsDialog.show();
    }

    @Override
    public void dimissLoading() {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void startLoading() {
        if (!mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(true);
        }
    }

    @Override
    public void deleteAll() {
        mNotificationRVAdapter.notifyItemRangeRemoved(0, mNotificationDBList.size());
        mNotificationDBList.clear();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete:
                showDeleteAllNotificationDialog();
                break;
        }
        return false;
    }

    @Override
    public void onRefresh() {
        mPresenter.getNotificationData(new GetNotificationRequest(mNotificationDBList.size()));
    }

    @Override
    public void onNotificationClick(String type, String orderId) {
        switch (type) {
            case NotificationRVAdapter.PUBLISH: //打开发布详细页面
                Intent orderPublishedDetail = new Intent(getActivity(), OrderPublishedDetailActivity.class);
                orderPublishedDetail.putExtra(OrderPublishedFragment.ORDER_ID, orderId);
                startActivity(orderPublishedDetail);
                break;
            case NotificationRVAdapter.RECEIVE: //打开接收详细页面
                Intent orderAcceptedDetail = new Intent(getActivity(), OrderAcceptedDetailActivity.class);
                orderAcceptedDetail.putExtra(OrderAcceptedFragment.ORDER_ID, orderId);
                startActivity(orderAcceptedDetail);
                break;
            case NotificationRVAdapter.APPLY: //打开申请详细页面
                Intent orderAppliedDetail = new Intent(getActivity(), OrderAppliedDetailActivity.class);
                orderAppliedDetail.putExtra(OrderAcceptedFragment.ORDER_ID, orderId);
                startActivity(orderAppliedDetail);
                break;
        }
    }
}
