package com.yuzhai.yuzhaiwork_2.notification.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.yuzhai.yuzhaiwork_2.R;
import com.yuzhai.yuzhaiwork_2.base.util.ActivityUtil;
import com.yuzhai.yuzhaiwork_2.notification.presenter.NotificationCenterPresenter;

/**
 * Created by 35429 on 2017/6/17.
 */

public class NotificationCenterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_center);

        NotificationCenterFragment notificationCenterFragment = NotificationCenterFragment.newInstance();
        ActivityUtil.addFragment(getSupportFragmentManager(), notificationCenterFragment, R.id.notification_center_content);

        new NotificationCenterPresenter(notificationCenterFragment);
    }
}
