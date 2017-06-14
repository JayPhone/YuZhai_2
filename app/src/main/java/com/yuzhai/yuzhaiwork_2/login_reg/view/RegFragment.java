package com.yuzhai.yuzhaiwork_2.login_reg.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.yuzhai.yuzhaiwork_2.R;
import com.yuzhai.yuzhaiwork_2.login_reg.event.SkipEvent;
import com.yuzhai.yuzhaiwork_2.login_reg.contact.RegContact;
import com.yuzhai.yuzhaiwork_2.login_reg.request.RegRequest;
import com.yuzhai.yuzhaiwork_2.login_reg.request.ValidateRequest;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 35429 on 2017/5/17.
 */

public class RegFragment extends Fragment implements RegContact.View, View.OnClickListener {

    private static final String TAG = "RegFragment";
    private TextInputEditText mUserPhoneEdt;
    private TextInputEditText mValidateCodeEdt;
    private TextInputEditText mUserPswEdt;
    private TextInputEditText mUserCfmPswEdt;
    private Button mValidateCodeButton;
    private RegContact.Presenter mPresenter;
    private ProgressDialog mProgressDialog;

    public static RegFragment newInstance() {
        return new RegFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_register, container, false);
        mUserPhoneEdt = (TextInputEditText) root.findViewById(R.id.user_phone);
        mValidateCodeEdt = (TextInputEditText) root.findViewById(R.id.verify_code);
        mUserPswEdt = (TextInputEditText) root.findViewById(R.id.psw);
        mUserCfmPswEdt = (TextInputEditText) root.findViewById(R.id.confirm_psw);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View root = getView();
        if (root != null) {
            mValidateCodeButton = (Button) root.findViewById(R.id.verify_button);
            mValidateCodeButton.setOnClickListener(this);
            Button regButton = (Button) root.findViewById(R.id.register_btn);
            regButton.setOnClickListener(this);
            Button loginButton = (Button) root.findViewById(R.id.login_btn);
            loginButton.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.verify_button:
                mPresenter.checkValidateInput(new ValidateRequest(mUserPhoneEdt.getText().toString(),
                        mValidateCodeEdt.getText().toString()));
                setValidateBtnEnable(false);
                break;
            case R.id.register_btn:
                mPresenter.checkRegInput(new RegRequest(mUserPhoneEdt.getText().toString(),
                        mValidateCodeEdt.getText().toString(),
                        mUserPswEdt.getText().toString(),
                        mUserCfmPswEdt.getText().toString()));
                break;
            case R.id.login_btn:
                EventBus.getDefault().post(new SkipEvent(SkipEvent.REG_EVENT, SkipEvent.LOGIN_SKIP));
                break;
        }
    }

    @Override
    public void setPresenter(RegContact.Presenter presenter) {
        this.mPresenter = presenter;
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
    public void setValidateBtnEnable(boolean isEnable) {
        if (isEnable) {
            mValidateCodeButton.setBackgroundResource(R.drawable.code_button_style);
        } else {
            mValidateCodeButton.setBackgroundResource(R.drawable.code_button_unable_style);
        }
        mValidateCodeButton.setEnabled(isEnable);
    }

    @Override
    public void startCountDown() {
        final int count = 30;
        Observable.interval(0, 1, TimeUnit.SECONDS)
                .take(30)
                .map(new Function<Long, String>() {
                    @Override
                    public String apply(@NonNull Long aLong) throws Exception {
                        return String.valueOf(count - aLong);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        mValidateCodeButton.setText(s + "秒后重新获取");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        mValidateCodeButton.setText("获取验证码");
                        setValidateBtnEnable(true);
                    }
                });
    }

    @Override
    public void showProgressDialog(String msg) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        }
        mProgressDialog.setMessage(msg);
        mProgressDialog.show();
    }

    @Override
    public void hideProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.hide();
        }
    }
}
