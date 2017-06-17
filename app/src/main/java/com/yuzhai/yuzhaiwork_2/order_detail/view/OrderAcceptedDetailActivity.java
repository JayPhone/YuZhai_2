package com.yuzhai.yuzhaiwork_2.order_detail.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

import com.bumptech.glide.Glide;
import com.yuzhai.yuzhaiwork_2.R;
import com.yuzhai.yuzhaiwork_2.base.http.IPConfig;
import com.yuzhai.yuzhaiwork_2.order_detail.adapter.OrdersAcceptedViewPagerAdapter;
import com.yuzhai.yuzhaiwork_2.order_detail.bean.OrderAcceptedDetailResponse;
import com.yuzhai.yuzhaiwork_2.order_detail.contact.OrderAcceptedDetailContact;
import com.yuzhai.yuzhaiwork_2.order_detail.presenter.OrderAcceptedDetailPresenter;
import com.yuzhai.yuzhaiwork_2.order_detail.request.CancelAcceptedOrderRequest;
import com.yuzhai.yuzhaiwork_2.personal_order.view.OrderAcceptedFragment;
import com.yuzhai.yuzhaiwork_2.user_data.view.UserDataActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 35429 on 2017/6/13.
 */

public class OrderAcceptedDetailActivity extends AppCompatActivity implements OrderAcceptedDetailContact.View,
        View.OnClickListener {
    private OrderAcceptedDetailContact.Presenter mPresenter;
    private OrderAcceptedDetailResponse mOrderAcceptedDetailResponse;

    private Toolbar mToolbar;
    private CircleImageView mPublisherHeader;
    private Dialog mCancelAcceptedOrderDialog;
    private ProgressDialog mProgressDialog;
    private boolean isInit = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_accepted);

        mToolbar = (Toolbar) findViewById(R.id.accepted_toolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mToolbar.inflateMenu(R.menu.accepted_detail_menu);
        mPublisherHeader = (CircleImageView) findViewById(R.id.publisher_header);
        mPublisherHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent userData = new Intent(OrderAcceptedDetailActivity.this, UserDataActivity.class);
                userData.putExtra(UserDataActivity.AVATAR, mOrderAcceptedDetailResponse.getDetailed_order().getPublisher_avatar());
                startActivity(userData);
            }
        });

        //订单进度Fragment
        OrderAcceptedProcessFragment orderAcceptedProcessFragment = OrderAcceptedProcessFragment.newInstance();
        //订单详情Fragment
        OrderAcceptedDetailFragment orderAcceptedDetailFragment = OrderAcceptedDetailFragment.newInstance();

        //添加viewPager的页面布局到List<View>里
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(orderAcceptedProcessFragment);
        fragmentList.add(orderAcceptedDetailFragment);

        //创建viewPager的适配器并设置
        ViewPager acceptedViewPager = (ViewPager) findViewById(R.id.accepted_viewPager);
        OrdersAcceptedViewPagerAdapter acceptedViewPagerAdapter = new OrdersAcceptedViewPagerAdapter(
                getSupportFragmentManager(), fragmentList);
        acceptedViewPager.setAdapter(acceptedViewPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        if (tabLayout != null) {
            tabLayout.setupWithViewPager(acceptedViewPager);
        }

        FloatingActionButton cancelFab = (FloatingActionButton) findViewById(R.id.cancel_order_fab);
        cancelFab.setOnClickListener(this);

        mPresenter = new OrderAcceptedDetailPresenter(this);
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
    public void setPresenter(OrderAcceptedDetailContact.Presenter presenter) {

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
    public void setAcceptedDetailData(OrderAcceptedDetailResponse orderAcceptedDetailResponse) {
        mOrderAcceptedDetailResponse = orderAcceptedDetailResponse;
        EventBus.getDefault().post(orderAcceptedDetailResponse);
        setPublishName(orderAcceptedDetailResponse.getDetailed_order().getPublisher());
        setPublishAvater(orderAcceptedDetailResponse.getDetailed_order().getPublisher_avatar());
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
        return getIntent().getStringExtra(OrderAcceptedFragment.ORDER_ID);
    }

    @Override
    public void showCancelAcceptedOrderDialog() {
        if (mCancelAcceptedOrderDialog == null) {
            mCancelAcceptedOrderDialog = new AlertDialog.Builder(this)
                    .setTitle("取消接单")
                    .setMessage("您确定要取消接收订单，如果该订单已经同意您接收，则需要赔付你所缴纳的违约金，您还要继续吗?")
                    .setPositiveButton("我要取消接收", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mPresenter.sendCancelAcceptedOrderRequest(new CancelAcceptedOrderRequest(getOrderId()));
                        }
                    })
                    .setNegativeButton("先不取消", null)
                    .create();
        }
        mCancelAcceptedOrderDialog.show();

    }

    @Override
    public void setPublishName(String publishName) {
        mToolbar.setSubtitle(publishName);
    }

    @Override
    public void setPublishAvater(String avatarUrl) {
        Glide.with(this)
                .load(IPConfig.IMAGE_PREFIX + "/" + avatarUrl)
                .dontAnimate()
                .placeholder(R.drawable.default_image)
                .error(R.drawable.default_image)
                .into(mPublisherHeader);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel_order_fab:
                showCancelAcceptedOrderDialog();
                break;
        }
    }
}
