package com.yuzhai.yuzhaiwork_2.order_detail.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.yuzhai.yuzhaiwork_2.R;
import com.yuzhai.yuzhaiwork_2.order_detail.adapter.OrdersPublishedViewPagerAdapter;
import com.yuzhai.yuzhaiwork_2.order_detail.bean.OrderPublishedDetailResponse;
import com.yuzhai.yuzhaiwork_2.order_detail.contact.OrderPublishedDetailContact;
import com.yuzhai.yuzhaiwork_2.order_detail.presenter.OrderPublishedDetailPresenter;
import com.yuzhai.yuzhaiwork_2.order_detail.request.CancelPublishedOrderRequest;
import com.yuzhai.yuzhaiwork_2.personal_order.view.OrderPublishedFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 35429 on 2017/6/13.
 */

public class OrderPublishedDetailActivity extends AppCompatActivity implements
        View.OnClickListener,
        OrderPublishedDetailContact.View {

    private Toolbar mToolbar;
    private OrderPublishedDetailContact.Presenter mPresenter;
    private ProgressDialog mProgressDialog;
    private Dialog mCancelPublishedOrderDialog;
    private Dialog mConfirmFinishDialog;
    private boolean isInit = false;

    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_published);

        mToolbar = (Toolbar) findViewById(R.id.published_toolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //订单进度Fragment
        OrderPublishedProcessFragment orderPublishedProcessFragment = OrderPublishedProcessFragment.newInstance();
        //订单详情Fragment
        OrderPublishedDetailFragment orderPublishedDetailFragment = OrderPublishedDetailFragment.newInstance();

        //添加viewPager的页面布局到List<View>里
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(orderPublishedProcessFragment);
        fragmentList.add(orderPublishedDetailFragment);

        //创建viewPager的适配器并设置
        ViewPager publishedViewPager = (ViewPager) findViewById(R.id.published_viewPager);
        OrdersPublishedViewPagerAdapter publishedViewPagerAdapter = new OrdersPublishedViewPagerAdapter(getSupportFragmentManager(), fragmentList);
        publishedViewPager.setAdapter(publishedViewPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(publishedViewPager);

        FloatingActionButton cancelFab = (FloatingActionButton) findViewById(R.id.cancel_order_fab);
        cancelFab.setOnClickListener(this);

        FloatingActionButton finishFab = (FloatingActionButton) findViewById(R.id.finish_order_fab);
        finishFab.setOnClickListener(this);

        mPresenter = new OrderPublishedDetailPresenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel_order_fab:
                showCancelPublishedOrderDialog();
                break;
            case R.id.finish_order_fab:
                showConfirmFinishDialog();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isInit) {
            mPresenter.start();
            isInit = true;
        }
    }

    @Override
    public void setPresenter(OrderPublishedDetailContact.Presenter presenter) {
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this.getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void setPublishDetailData(OrderPublishedDetailResponse orderPublishedDetailResponse) {
        setOrderState(orderPublishedDetailResponse.getDetailed_order().getStatus());
        EventBus.getDefault().post(orderPublishedDetailResponse);
    }

    @Override
    public void showProgressDialog(String msg) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        }
        mProgressDialog.setMessage(msg);
        mProgressDialog.show();
    }

    @Override
    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public String getOrderId() {
        return getIntent().getStringExtra(OrderPublishedFragment.ORDER_ID);
    }

    @Override
    public void showCancelPublishedOrderDialog() {
        if (mCancelPublishedOrderDialog == null) {
            mCancelPublishedOrderDialog = new AlertDialog.Builder(this)
                    .setTitle("取消发单")
                    .setMessage("您确定要取消订单，如果该订单已经被接收，则需要赔付保证金(订单金额的30%)给接收方,你所缴纳的订单金额将退还到您的账户，您还要继续吗?")
                    .setPositiveButton("我要退", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //发送取消已发布订单请求
                            mPresenter.sendCancelPublishedOrderRequest(new CancelPublishedOrderRequest(getOrderId()));
                        }
                    })
                    .setNegativeButton("先不退", null)
                    .create();
        }
        mCancelPublishedOrderDialog.show();
    }

    @Override
    public void showConfirmFinishDialog() {
        if (mConfirmFinishDialog == null) {
            mConfirmFinishDialog = new AlertDialog.Builder(this)
                    .setTitle("确认完成")
                    .setMessage("点击确认订单后，你交付在本平台上的项目金额将转账到对方账号，请谨慎操作！")
                    .setPositiveButton("我确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .setNegativeButton("取消", null)
                    .create();
        }
        mConfirmFinishDialog.show();
    }

    @Override
    public void setOrderState(String state) {
        mToolbar.setSubtitle(state);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.clear();
    }
}
