package com.yuzhai.yuzhaiwork_2.main.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.bumptech.glide.Glide;
import com.yuzhai.yuzhaiwork_2.R;
import com.yuzhai.yuzhaiwork_2.base.global.CustomApplication;
import com.yuzhai.yuzhaiwork_2.base.http.IPConfig;
import com.yuzhai.yuzhaiwork_2.base.util.ActivityUtil;
import com.yuzhai.yuzhaiwork_2.base.util.SharePerferenceUtil;
import com.yuzhai.yuzhaiwork_2.base.view.CircleImageView;
import com.yuzhai.yuzhaiwork_2.collection.view.CollectionActivity;
import com.yuzhai.yuzhaiwork_2.login_reg.event.UserInfoEvent;
import com.yuzhai.yuzhaiwork_2.login_reg.view.LoginRegActivity;
import com.yuzhai.yuzhaiwork_2.main.event.UserDetailInfoEvent;
import com.yuzhai.yuzhaiwork_2.main.presenter.HomePresenter;
import com.yuzhai.yuzhaiwork_2.main.presenter.PublishPresenter;
import com.yuzhai.yuzhaiwork_2.resume.view.PublishResumeActivity;
import com.yuzhai.yuzhaiwork_2.user_info.event.AvatarAlterEvent;
import com.yuzhai.yuzhaiwork_2.base.event.LoginEvent;
import com.yuzhai.yuzhaiwork_2.user_info.event.ReNameEvent;
import com.yuzhai.yuzhaiwork_2.user_info.view.UserInfoManagerActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by 35429 on 2017/5/23.
 */

public class MainActivity extends AppCompatActivity implements
        View.OnClickListener,
        NavigationView.OnNavigationItemSelectedListener,
        BottomNavigationBar.OnTabSelectedListener {
    private static final String TAG = "MainActivity";

    private TextView mUserName;
    private CircleImageView mUserAvater;
    private Button mLoginAndRegButton;
    private String mUserNameText;
    private String mUserAvaterUrl;
    private LinearLayout mUserInfoLayout;
    private NavigationView mNavigationView;

    private HomeFragment mHomeFragment;
    private OrderFragment mOrderFragment;
    private PublishFragment mPublishFragment;
    private ContactFragment mContactFragment;
    private Fragment mCurrentFragment;

    private HomePresenter mHomePresenter;
    private PublishPresenter mPublishPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EventBus.getDefault().register(this);

        //默认初始化HomeFragment
        initFirstTab();

        //透明状态栏
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        //底部导航栏
        BottomNavigationBar bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.main_nav_bar);
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        //添加御宅Tab
        bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.home, getString(R.string.yu_zhai))
                .setActiveColorResource(R.color.mainColor));
        //添加订单Tab
        bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.order, getString(R.string.orders))
                .setActiveColorResource(R.color.mainColor));
        //添加发布Tab
        bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.publish, getString(R.string.publish))
                .setActiveColorResource(R.color.mainColor));
        //添加聊天Tab
        bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.contact, getString(R.string.contact))
                .setActiveColorResource(R.color.mainColor));
        bottomNavigationBar.setFirstSelectedPosition(0).initialise();
        //设置切换监听
        bottomNavigationBar.setTabSelectedListener(this);

        //侧滑下部分菜单
        mNavigationView = (NavigationView) findViewById(R.id.navigation);
        mNavigationView.setItemIconTintList(null);
        mNavigationView.setNavigationItemSelectedListener(this);

        //侧滑上部分(登录)
        View headerView = mNavigationView.inflateHeaderView(R.layout.navigation_menu_header_layout);
        mUserInfoLayout = (LinearLayout) headerView.findViewById(R.id.user_info);
        mUserName = (TextView) headerView.findViewById(R.id.user_name);
        mUserAvater = (CircleImageView) headerView.findViewById(R.id.head_image);
        mUserAvater.setOnClickListener(this);

        //侧滑上部分(未登录)
        mLoginAndRegButton = (Button) headerView.findViewById(R.id.menu_login_register);
        mLoginAndRegButton.setOnClickListener(this);

        //根据登录状态加载侧滑菜单布局
        initDrawerLayout();
    }

    //初始化首个Fragment
    private void initFirstTab() {
        clickHomeTab();
    }

    //初始化菜单布局
    private void initDrawerLayout() {
        if (((CustomApplication) getApplication()).isLogin()) {
            //登录布局
            initLoginLayout();
        } else {
            //非登录布局
            initUnLoginLayout();
        }
    }

    //初始化登录时的布局
    private void initLoginLayout() {
        mLoginAndRegButton.setVisibility(View.INVISIBLE);
        mUserAvater.setVisibility(View.VISIBLE);
        mUserInfoLayout.setVisibility(View.VISIBLE);
        mNavigationView.getMenu().clear();
        mNavigationView.inflateMenu(R.menu.drawer_login_menu);
        //设置用户名
        if (mUserNameText != null) {
            setUserName(mUserNameText);
        }
        //设置用户头像
        if (mUserAvaterUrl != null) {
            setUserHeader(mUserAvaterUrl);
        }
    }

    //初始化未登录时的布局
    private void initUnLoginLayout() {
        mUserAvater.setVisibility(View.INVISIBLE);
        mUserInfoLayout.setVisibility(View.INVISIBLE);
        mLoginAndRegButton.setVisibility(View.VISIBLE);
        mNavigationView.getMenu().clear();
        mNavigationView.inflateMenu(R.menu.drawer_no_login_menu);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.collection:
                Intent collection = new Intent(this, CollectionActivity.class);
                startActivity(collection);
                break;
            case R.id.resume:
                Intent publishResume = new Intent(this, PublishResumeActivity.class);
                startActivity(publishResume);
                break;
            case R.id.realName:
//                Intent identityAuthen = new Intent(this, IdentityAuthenActivity.class);
//                startActivity(identityAuthen);
                break;
            case R.id.setting:
//                Intent setUp = new Intent(this, SetUpActivity.class);
//                startActivity(setUp);
                break;
            case R.id.about:
                break;
            case R.id.exit:
//                android.os.Process.killProcess(android.os.Process.myPid());
                break;
        }
        return true;
    }

    @Override
    public void onTabSelected(int position) {
        switch (position) {
            case 0:
                clickHomeTab();
                break;
            case 1:
                clickOrderTab();
                break;
            case 2:
                clickPublishTab();
                break;
            case 3:
                clickContactTab();
                break;
        }
    }

    private void clickHomeTab() {
        if (mHomeFragment == null) {
            mHomeFragment = HomeFragment.newInstance();
        }
        ActivityUtil.addOrShowFragment(getSupportFragmentManager(), mCurrentFragment,
                mHomeFragment, R.id.main_content);
        mCurrentFragment = mHomeFragment;
        if (mHomePresenter == null) {
            mHomePresenter = new HomePresenter(mHomeFragment);
        }
    }

    private void clickOrderTab() {
        if (mOrderFragment == null) {
            mOrderFragment = OrderFragment.newInstance();
        }
        ActivityUtil.addOrShowFragment(getSupportFragmentManager(), mCurrentFragment,
                mOrderFragment, R.id.main_content);
        mCurrentFragment = mOrderFragment;
    }

    private void clickPublishTab() {
        if (mPublishFragment == null) {
            mPublishFragment = PublishFragment.newInstance();
        }
        ActivityUtil.addOrShowFragment(getSupportFragmentManager(), mCurrentFragment,
                mPublishFragment, R.id.main_content);
        mCurrentFragment = mPublishFragment;
        if (mPublishPresenter == null) {
            mPublishPresenter = new PublishPresenter(mPublishFragment);
        }
    }

    private void clickContactTab() {
        if (mContactFragment == null) {
            mContactFragment = ContactFragment.newInstance();
        }
        ActivityUtil.addOrShowFragment(getSupportFragmentManager(), mCurrentFragment,
                mContactFragment, R.id.main_content);
        mCurrentFragment = mContactFragment;
    }


    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_image:
                Intent userInfo_manager = new Intent(MainActivity.this, UserInfoManagerActivity.class);
                startActivity(userInfo_manager);
                //通过EventBus发送登录后返回的个人信息到UserInfoManagerActivity
                EventBus.getDefault().postSticky(new UserDetailInfoEvent(
                        mUserAvaterUrl,
                        mUserNameText,
                        SharePerferenceUtil.
                                getSharePerference(getApplicationContext(),
                                        SharePerferenceUtil.FileName.USER_INFO)
                                .getString(SharePerferenceUtil.Key.USERNAME, ""),
                        "未认证"));
                break;

            case R.id.menu_login_register:
                Intent login_reg = new Intent(MainActivity.this, LoginRegActivity.class);
                startActivity(login_reg);
                break;
        }
    }

    //用户信息返回时触发
    @Subscribe(sticky = true, threadMode = ThreadMode.POSTING)
    public void onUserInfoAccept(UserInfoEvent userInfoEvent) {
        Log.d(TAG, userInfoEvent.toString());
        //成功登录
        if (userInfoEvent.getUserName() != null && userInfoEvent.getUserAvaterUrl() != null) {
            Log.d(TAG, userInfoEvent.toString());
            mUserNameText = userInfoEvent.getUserName();
            mUserAvaterUrl = userInfoEvent.getUserAvaterUrl();

            //非stick事件
            if (mUserAvater != null && mUserName != null) {
                setUserHeader(mUserAvaterUrl);
                setUserName(mUserNameText);
                initLoginLayout();
            }
        }
    }

    //退出登录时触发
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onLoginExit(LoginEvent loginEvent) {
        if (!loginEvent.isLogin()) {
            initUnLoginLayout();
            clearUserInfo();
        }
    }

    //修改用户名后触发
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onAlterUserName(ReNameEvent reNameEvent) {
        mUserNameText = reNameEvent.getNewName();
        setUserName(mUserNameText);
    }

    //修改用户头像后触发
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onAlterAvatar(AvatarAlterEvent avatarAlterEvent) {
        mUserAvaterUrl = avatarAlterEvent.getUserAvatarUrl();
        setUserHeader(mUserAvaterUrl);
    }

    //清除用户信息
    private void clearUserInfo() {
        //清除Cookie
        SharePerferenceUtil.getSharePerference(getApplicationContext(), SharePerferenceUtil.FileName.COOKIE)
                .edit()
                .putString(SharePerferenceUtil.Key.COOKIE, "")
                .apply();
        //清除已登录的用户信息
        SharePerferenceUtil.getSharePerference(getApplicationContext(), SharePerferenceUtil.FileName.USER_INFO)
                .edit()
                .putString(SharePerferenceUtil.Key.USERNAME, "")
                .putString(SharePerferenceUtil.Key.USERPSW, "").apply();
    }

    public void setUserHeader(String userHeadUrl) {
        //通过返回的用户头像地址获取用户头像
        if (userHeadUrl != null) {
            Log.i(TAG, IPConfig.IMAGE_PREFIX + "/" + userHeadUrl);
            Glide.with(this)
                    .load(IPConfig.IMAGE_PREFIX + "/" + userHeadUrl)
                    .placeholder(R.drawable.default_image)
                    .error(R.drawable.default_image)
                    .into(mUserAvater);
        }
    }

    public void setUserName(String userName) {
        if (userName != null) {
            mUserName.setText(userName);
        }
    }

    @Override
    protected void onDestroy() {
        if (mHomePresenter != null) {
            mHomePresenter.clear();
        }
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        //侧边菜单导航栏
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        if (drawerLayout.isDrawerOpen(Gravity.START)) {
            drawerLayout.closeDrawer(Gravity.START);
        } else {
            super.onBackPressed();
        }
    }
}
