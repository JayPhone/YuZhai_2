package com.yuzhai.yuzhaiwork_2.login_reg.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.xiaomi.mipush.sdk.MiPushClient;
import com.yuzhai.yuzhaiwork_2.R;
import com.yuzhai.yuzhaiwork_2.base.event.LoginEvent;
import com.yuzhai.yuzhaiwork_2.base.global.CustomApplication;
import com.yuzhai.yuzhaiwork_2.login_reg.event.SkipEvent;
import com.yuzhai.yuzhaiwork_2.login_reg.event.UserInfoEvent;
import com.yuzhai.yuzhaiwork_2.login_reg.contact.LoginContact;
import com.yuzhai.yuzhaiwork_2.login_reg.request.LoginRequest;
import com.yuzhai.yuzhaiwork_2.login_reg.request.LoginResponse;

import org.greenrobot.eventbus.EventBus;


/**
 * Created by 35429 on 2017/5/17.
 */

public class LoginFragment extends Fragment implements LoginContact.View, View.OnClickListener {
    private static final String TAG = "LoginFragment";
    private LoginContact.Presenter mPresenter;
    private TextInputEditText mUserPhoneEdt;
    private TextInputEditText mUserPswEdt;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_login, container, false);
        mUserPhoneEdt = (TextInputEditText) root.findViewById(R.id.user_phone);
        mUserPswEdt = (TextInputEditText) root.findViewById(R.id.user_psw);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View root = getView();
        if (root != null) {
            Button loginButton = (Button) root.findViewById(R.id.login_btn);
            loginButton.setOnClickListener(this);
            Button regButton = (Button) root.findViewById(R.id.register_btn);
            regButton.setOnClickListener(this);
            Button forgetPswButton = (Button) root.findViewById(R.id.forget_psw_btn);
            forgetPswButton.setOnClickListener(this);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                //请求presenter校验用户填写的数据
                mPresenter.checkInput(new LoginRequest(mUserPhoneEdt.getText().toString()
                        , mUserPswEdt.getText().toString()
                        , MiPushClient.getRegId(getActivity().getApplicationContext())));
                break;
            case R.id.register_btn:
                EventBus.getDefault().post(new SkipEvent(SkipEvent.LOGIN_EVENT, SkipEvent.REG_SKIP));
                break;
            case R.id.forget_psw_btn:
                EventBus.getDefault().post(new SkipEvent(SkipEvent.LOGIN_EVENT, SkipEvent.FGT_PSW_SKIP));
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(LoginContact.Presenter presenter) {
        if (presenter != null) {
            mPresenter = presenter;
        }
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(getActivity().getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void setData(LoginResponse loginResponse) {
        Log.d(TAG, loginResponse.toString());
        //发送登录成功返回的数据给MainActivity
        EventBus.getDefault().post(new UserInfoEvent(
                loginResponse.getUser_name(), loginResponse.getUser_head_url()));
        getActivity().finish();
        EventBus.getDefault().post(new LoginEvent(true));
    }
}
