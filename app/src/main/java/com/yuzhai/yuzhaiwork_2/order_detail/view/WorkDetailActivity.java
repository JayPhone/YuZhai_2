package com.yuzhai.yuzhaiwork_2.order_detail.view;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.yuzhai.yuzhaiwork_2.R;
import com.yuzhai.yuzhaiwork_2.base.util.ActivityUtil;
import com.yuzhai.yuzhaiwork_2.category.view.WorkFragment;
import com.yuzhai.yuzhaiwork_2.order_detail.presenter.WorkDetailPresenter;

/**
 * Created by 35429 on 2017/6/12.
 */

public class WorkDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_work_datail);

        WorkDetailFragment workDetailFragment = WorkDetailFragment
                .newInstance(getIntent().getStringExtra(WorkFragment.ORDER_ID));
        ActivityUtil.addFragment(getSupportFragmentManager(), workDetailFragment, R.id.work_detail_content);

        new WorkDetailPresenter(workDetailFragment);
    }
}
