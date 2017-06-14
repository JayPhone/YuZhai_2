package com.yuzhai.yuzhaiwork_2.category.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.yuzhai.yuzhaiwork_2.R;
import com.yuzhai.yuzhaiwork_2.category.adapter.CategoryVPAdapter;
import com.yuzhai.yuzhaiwork_2.category.presenter.ResumePresenter;
import com.yuzhai.yuzhaiwork_2.category.presenter.WorkPresenter;
import com.yuzhai.yuzhaiwork_2.main.view.HomeFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 35429 on 2017/6/3.
 */

public class CategoryActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        String categoryType = getIntent().getStringExtra(HomeFragment.CATEGORY);

        //初始化ToolBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.category_toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setTitle(categoryType);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //工作Fragment
        WorkFragment workFragment = WorkFragment.newInstance(categoryType);
        //简历Fragment
        ResumeFragment resumeFragment = ResumeFragment.newInstance(categoryType);

        //添加viewPager的页面布局到List<View>里
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(workFragment);
        fragmentList.add(resumeFragment);

        //创建viewPager的适配器并设置
        ViewPager categoryViewPager = (ViewPager) findViewById(R.id.category_viewPager);
        categoryViewPager.setOffscreenPageLimit(2);
        CategoryVPAdapter categoryViewPagerAdapter = new CategoryVPAdapter(
                getSupportFragmentManager(), fragmentList);
        categoryViewPager.setAdapter(categoryViewPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(categoryViewPager);

        //初始化WorkPresenter
        new WorkPresenter(workFragment);
        //初始化ResumePresenter
        new ResumePresenter(resumeFragment);
    }
}
