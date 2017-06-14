package com.yuzhai.yuzhaiwork_2.home_page.presenter;

import android.content.SharedPreferences;
import android.util.Log;

import com.xiaomi.mipush.sdk.MiPushClient;
import com.yuzhai.yuzhaiwork_2.base.global.CustomApplication;
import com.yuzhai.yuzhaiwork_2.base.model.BaseModel;
import com.yuzhai.yuzhaiwork_2.base.util.SharePerferenceUtil;
import com.yuzhai.yuzhaiwork_2.home_page.bean.HomePageData;
import com.yuzhai.yuzhaiwork_2.home_page.contact.HomePageContact;
import com.yuzhai.yuzhaiwork_2.home_page.model.HomePageRemoteModel;
import com.yuzhai.yuzhaiwork_2.home_page.model.IHomePageModel;
import com.yuzhai.yuzhaiwork_2.login_reg.request.LoginRequest;
import com.yuzhai.yuzhaiwork_2.login_reg.request.LoginResponse;

import java.lang.ref.WeakReference;

/**
 * Created by 35429 on 2017/6/6.
 */

public class HomePagePresenter implements HomePageContact.Presenter {
    private static final String TAG = "HomePagePresenter";
    private WeakReference<HomePageContact.View> mHomePageView;
    private IHomePageModel mHomePageModel = new HomePageRemoteModel();

    public HomePagePresenter(HomePageContact.View homePageView) {
        this.mHomePageView = new WeakReference<>(homePageView);
        this.mHomePageView.get().setPresenter(this);
    }

    @Override
    public void start() {
        //获取封面
        getHomePageData();
    }

    @Override
    public void clear() {
        if (mHomePageView.get() != null) {
            mHomePageView.clear();
        }
    }

    @Override
    public void getHomePageData() {
        mHomePageModel.getHomePageData(new BaseModel.OnRequestResponse<HomePageData>() {
            @Override
            public void onSuccess(HomePageData homePageData) {
                mHomePageView.get().setHomePageData(homePageData);
            }

            @Override
            public void onFailure(Throwable e) {

            }
        });
    }

    @Override
    public void sendLoginRequest() {
        //获取本地的用户信息
        SharedPreferences perference = SharePerferenceUtil
                .getSharePerference(CustomApplication.getInstance().getApplicationContext(),
                        SharePerferenceUtil.FileName.USER_INFO);
        String userName = perference.getString(SharePerferenceUtil.Key.USERNAME, "");
        String userPsw = perference.getString(SharePerferenceUtil.Key.USERPSW, "");

        if (!userName.equals("") && !userPsw.equals("")) {
            LoginRequest loginRequest = new LoginRequest(userName, userPsw,
                    MiPushClient.getRegId(CustomApplication.getInstance().getApplicationContext()));

            Log.d(TAG, loginRequest.toString());

            //发送登录请求
            mHomePageModel.sentLoginRequest(loginRequest, new BaseModel.OnRequestResponse<LoginResponse>() {
                @Override
                public void onSuccess(LoginResponse loginResponse) {
                    if (mHomePageView.get().isActive()) {
                        if (loginResponse.getCode().equals("1")) {
                            mHomePageView.get().setAutoLoginState(true);
                            mHomePageView.get().setLoginResponseData(loginResponse);
                        }
                    }
                }

                @Override
                public void onFailure(Throwable e) {
                    if (mHomePageView.get().isActive()) {
                        Log.d(TAG, "服务器异常");
                        mHomePageView.get().showToast("服务器异常");
                        mHomePageView.get().setLoginResponseData(new LoginResponse("-1", null, null));
                    }
                }
            });
        } else {
            Log.d(TAG, "自动登录失败");
            mHomePageView.get().setLoginResponseData(new LoginResponse("-1", null, null));
        }
    }
}
