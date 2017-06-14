package com.yuzhai.yuzhaiwork_2.main.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yuzhai.yuzhaiwork_2.R;
import com.yuzhai.yuzhaiwork_2.base.event.LoginEvent;
import com.yuzhai.yuzhaiwork_2.base.global.CustomApplication;
import com.yuzhai.yuzhaiwork_2.main.adapter.OrderVPAdapter;
import com.yuzhai.yuzhaiwork_2.main.contact.OrderContact;
import com.yuzhai.yuzhaiwork_2.personal_order.presenter.OrderAcceptedPresenter;
import com.yuzhai.yuzhaiwork_2.personal_order.presenter.OrderAppliedPresenter;
import com.yuzhai.yuzhaiwork_2.personal_order.presenter.OrderPublishedPresenter;
import com.yuzhai.yuzhaiwork_2.personal_order.view.OrderAcceptedFragment;
import com.yuzhai.yuzhaiwork_2.personal_order.view.OrderAppliedFragment;
import com.yuzhai.yuzhaiwork_2.personal_order.view.OrderPublishedFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import static com.yuzhai.yuzhaiwork_2.R.id.toolbar;

/**
 * Created by 35429 on 2017/5/25.
 */

public class OrderFragment extends Fragment {
    public static OrderFragment newInstance() {
        Bundle args = new Bundle();
        OrderFragment fragment = new OrderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_order, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final DrawerLayout drawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer);
        View root = getView();
        if (root != null) {
            Toolbar toolbar = (Toolbar) root.findViewById(R.id.order_toolbar);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawerLayout.openDrawer(Gravity.START);
                }
            });

            //已发布订单Fragment
            OrderPublishedFragment publishedFragment = OrderPublishedFragment.newInstance();
            //已申请订单Fragment
            OrderAppliedFragment appliedFragment = OrderAppliedFragment.newInstance();
            //己接收订单Fragment
            OrderAcceptedFragment acceptedFragment = OrderAcceptedFragment.newInstance();

            //添加viewPager的页面布局到List<View>里
            List<Fragment> fragmentList = new ArrayList<>();
            fragmentList.add(publishedFragment);
            fragmentList.add(appliedFragment);
            fragmentList.add(acceptedFragment);

            //创建viewPager的适配器并设置
            ViewPager viewPager = (ViewPager) root.findViewById(R.id.order_viewPager);
            //加载全部的Fragment布局，实现懒加载
            viewPager.setOffscreenPageLimit(3);
            OrderVPAdapter adapter = new OrderVPAdapter(getChildFragmentManager(), fragmentList);
            viewPager.setAdapter(adapter);
            TabLayout tabLayout = (TabLayout) root.findViewById(R.id.tab_layout);
            tabLayout.setupWithViewPager(viewPager);

            //初始化已发布订单Presenter
            new OrderPublishedPresenter(publishedFragment);
            //初始化已申请订单Presenter
            new OrderAppliedPresenter(appliedFragment);
            //初始化已接收订单Presenter
            new OrderAcceptedPresenter(acceptedFragment);
        }
    }
}