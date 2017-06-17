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
import com.yuzhai.yuzhaiwork_2.order_detail.adapter.OrdersAppliedViewPagerAdapter;
import com.yuzhai.yuzhaiwork_2.order_detail.bean.OrderAppliedDetailResponse;
import com.yuzhai.yuzhaiwork_2.order_detail.contact.OrderAppliedDetailContact;
import com.yuzhai.yuzhaiwork_2.order_detail.presenter.OrderAppliedDetailPresenter;
import com.yuzhai.yuzhaiwork_2.order_detail.request.CancelAppliedOrderRequest;
import com.yuzhai.yuzhaiwork_2.personal_order.view.OrderAppliedFragment;
import com.yuzhai.yuzhaiwork_2.user_data.view.UserDataActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.yuzhai.yuzhaiwork_2.user_data.view.UserDataActivity.AVATAR;

/**
 * Created by 35429 on 2017/6/13.
 */

public class OrderAppliedDetailActivity extends AppCompatActivity implements OrderAppliedDetailContact.View,
        View.OnClickListener {
    private OrderAppliedDetailContact.Presenter mPresenter;
    private OrderAppliedDetailResponse mOrderAppliedDetailResponse;

    private Toolbar mToolbar;
    private CircleImageView mPublisherHeader;
    private ProgressDialog mProgressDialog;
    private Dialog mCancelAppliedOrderDialog;
    private boolean isInit = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_applied);

        mToolbar = (Toolbar) findViewById(R.id.applied_toolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mToolbar.inflateMenu(R.menu.applied_detail_menu);
        mPublisherHeader = (CircleImageView) findViewById(R.id.publisher_header);
        mPublisherHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent userData = new Intent(OrderAppliedDetailActivity.this, UserDataActivity.class);
                userData.putExtra(AVATAR, mOrderAppliedDetailResponse.getDetailed_order().getPublisher_avatar());
                startActivity(userData);
            }
        });

        //订单进度Fragment
        OrderAppliedProcessFragment orderAppliedProcessFragment = OrderAppliedProcessFragment.newInstance();
        //订单详情Fragment
        OrderAppliedDetailFragment orderAppliedDetailFragment = OrderAppliedDetailFragment.newInstance();

        //添加viewPager的页面布局到List<View>里
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(orderAppliedProcessFragment);
        fragmentList.add(orderAppliedDetailFragment);

        //创建viewPager的适配器并设置
        ViewPager appliedViewPager = (ViewPager) findViewById(R.id.applied_viewPager);
        OrdersAppliedViewPagerAdapter ordersAppliedViewPagerAdapter = new OrdersAppliedViewPagerAdapter(
                getSupportFragmentManager(), fragmentList);
        appliedViewPager.setAdapter(ordersAppliedViewPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        if (tabLayout != null) {
            tabLayout.setupWithViewPager(appliedViewPager);
        }

        FloatingActionButton cancelFab = (FloatingActionButton) findViewById(R.id.cancel_order_fab);
        cancelFab.setOnClickListener(this);

        mPresenter = new OrderAppliedDetailPresenter(this);
    }

    @Override
    public void setPresenter(OrderAppliedDetailContact.Presenter presenter) {

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
    public void setAppliedDetailData(OrderAppliedDetailResponse orderAppliedDetailResponse) {
        mOrderAppliedDetailResponse = orderAppliedDetailResponse;
        EventBus.getDefault().post(orderAppliedDetailResponse);
        setPublishName(orderAppliedDetailResponse.getDetailed_order().getPublisher());
        setPublishAvater(orderAppliedDetailResponse.getDetailed_order().getPublisher_avatar());
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
        return getIntent().getStringExtra(OrderAppliedFragment.ORDER_ID);
    }

    @Override
    public void showCancelAppliedOrderDialog() {
        if (mCancelAppliedOrderDialog == null) {
            mCancelAppliedOrderDialog = new AlertDialog.Builder(this)
                    .setTitle("取消申请")
                    .setMessage("您确定要取消申请订单吗?")
                    .setPositiveButton("取消申请", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mPresenter.sendCancelAppliedOrderRequest(new CancelAppliedOrderRequest(getOrderId()));
                        }
                    })
                    .setNegativeButton("先不取消", null)
                    .create();
        }
        mCancelAppliedOrderDialog.show();
    }

    @Override
    public void setPublishName(String publishName) {
        mToolbar.setSubtitle(publishName);
    }

    @Override
    public void setPublishAvater(String avatarUrl) {
        Glide.with(this)
                .load(IPConfig.IMAGE_PREFIX + avatarUrl)
                .dontAnimate()
                .placeholder(R.drawable.default_image)
                .error(R.drawable.default_image)
                .into(mPublisherHeader);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel_order_fab:
                showCancelAppliedOrderDialog();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.clear();
    }
}
