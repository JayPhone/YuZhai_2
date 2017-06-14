package com.yuzhai.yuzhaiwork_2.login_reg.view;

import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.yuzhai.yuzhaiwork_2.R;
import com.yuzhai.yuzhaiwork_2.base.util.ActivityUtil;
import com.yuzhai.yuzhaiwork_2.login_reg.event.SkipEvent;
import com.yuzhai.yuzhaiwork_2.login_reg.presenter.ForgetPswPresnter;
import com.yuzhai.yuzhaiwork_2.login_reg.presenter.LoginPresenter;
import com.yuzhai.yuzhaiwork_2.login_reg.presenter.RegPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class LoginRegActivity extends AppCompatActivity {
    private LoginFragment mLoginFragment;
    private RegFragment mRegFragment;
    private ForgetPswFragment mForgetPswFragment;
    private Fragment mCurrentFragment;

    private LoginPresenter mLoginPresenter;
    private RegPresenter mRegPresenter;
    private ForgetPswPresnter mForgetPswPresnter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);

        EventBus.getDefault().register(this);

        if (mLoginFragment == null) {
            //添加登录Fragment到Activity
            mLoginFragment = LoginFragment.newInstance();
            ActivityUtil.addFragment(getSupportFragmentManager(),
                    mLoginFragment, R.id.content);
        }
        //设置当前Fragment
        mCurrentFragment = mLoginFragment;
        //创建LoginPresenter
        if (mLoginPresenter == null) {
            mLoginPresenter = new LoginPresenter(mLoginFragment, getApplicationContext());
        }
    }

    //Fragment切换
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void OnEventAccept(SkipEvent skipEvent) {
        //获取事件来源
        switch (skipEvent.getFrom()) {
            case SkipEvent.LOGIN_EVENT:
                switch (skipEvent.getTo()) {
                    case SkipEvent.REG_SKIP:
                        showRegFragment();
                        break;
                    case SkipEvent.FGT_PSW_SKIP:
                        showForgetPswFragment();
                        break;
                }
                break;
            case SkipEvent.REG_EVENT:
                switch (skipEvent.getTo()) {
                    case SkipEvent.LOGIN_SKIP:
                        showLoginFragment();
                        break;
                }
                break;
            case SkipEvent.FGT_PSW_EVENT:
                switch (skipEvent.getTo()) {
                    case SkipEvent.LOGIN_SKIP:
                        showLoginFragment();
                        break;
                }
                break;
        }
    }

    /**
     * 显示LoginFragment
     */
    private void showLoginFragment() {
        if (mLoginFragment == null) {
            mLoginFragment = LoginFragment.newInstance();
        }
        ActivityUtil.addOrShowFragment(getSupportFragmentManager(),
                mCurrentFragment, mLoginFragment, R.id.content);
        //设置当前Fragment
        mCurrentFragment = mLoginFragment;
        //创建LoginPresenter
        if (mLoginFragment == null) {
            mLoginPresenter = new LoginPresenter(mLoginFragment, getApplicationContext());
        }
    }

    /**
     * 显示RegFragment
     */
    private void showRegFragment() {
        if (mRegFragment == null) {
            mRegFragment = RegFragment.newInstance();
        }
        ActivityUtil.addOrShowFragment(getSupportFragmentManager(),
                mCurrentFragment, mRegFragment, R.id.content);
        //设置当前Fragment
        mCurrentFragment = mRegFragment;
        //创建RegPresenter
        if (mRegPresenter == null) {
            mRegPresenter = new RegPresenter(mRegFragment, getApplicationContext());
        }
    }

    /**
     * 显示ForgetPswFragment
     */
    private void showForgetPswFragment() {
        if (mForgetPswFragment == null) {
            mForgetPswFragment = ForgetPswFragment.newInstance();

        }
        ActivityUtil.addOrShowFragment(getSupportFragmentManager(),
                mCurrentFragment, mForgetPswFragment, R.id.content);
        //设置当前Fragment
        mCurrentFragment = mForgetPswFragment;
        //创建ForgetPswPresenter
        if (mForgetPswPresnter == null) {
            mForgetPswPresnter = new ForgetPswPresnter(mForgetPswFragment, getApplicationContext());
        }
    }

    @Override
    protected void onDestroy() {
        if (mLoginPresenter != null) {
            mLoginPresenter.clear();
        }
        if (mRegPresenter != null) {
            mRegPresenter.clear();
        }
        if (mForgetPswPresnter != null) {
            mForgetPswPresnter.clear();
        }
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
}
