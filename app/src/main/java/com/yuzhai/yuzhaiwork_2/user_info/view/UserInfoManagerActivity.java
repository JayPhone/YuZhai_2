package com.yuzhai.yuzhaiwork_2.user_info.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.yuzhai.yuzhaiwork_2.R;
import com.yuzhai.yuzhaiwork_2.base.util.ActivityUtil;
import com.yuzhai.yuzhaiwork_2.user_info.presenter.UserInfoManagerPresenter;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by 35429 on 2017/6/8.
 */

public class UserInfoManagerActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo_manager);

        UserInfoManagerFragment userInfoManagerFragment = UserInfoManagerFragment.newInstance();
        ActivityUtil.addFragment(getSupportFragmentManager(), userInfoManagerFragment, R.id.userinfo_manager_content);
        new UserInfoManagerPresenter(userInfoManagerFragment);
    }
}
