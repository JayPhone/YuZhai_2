package com.yuzhai.yuzhaiwork_2.login_reg.view;

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
import com.yuzhai.yuzhaiwork_2.login_reg.contact.ForgetPswContact;
import com.yuzhai.yuzhaiwork_2.login_reg.request.AlterPswRequest;
import com.yuzhai.yuzhaiwork_2.login_reg.request.ValidateRequest;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by 35429 on 2017/5/24.
 */

public class ForgetPswFragment extends Fragment implements ForgetPswContact.View, View.OnClickListener {
    private static final String TAG = "ForgetPswFragment";
    private TextInputEditText mUserPhoneEdt;
    private TextInputEditText mValidateCodeEdt;
    private TextInputEditText mNewPswEdt;
    private TextInputEditText mCfmPswEdt;
    private ForgetPswContact.Presenter mPresenter;

    public static ForgetPswFragment newInstance() {
        return new ForgetPswFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_forget_psw, container, false);
        mUserPhoneEdt = (TextInputEditText) root.findViewById(R.id.user_phone);
        mValidateCodeEdt = (TextInputEditText) root.findViewById(R.id.verify_code);
        mNewPswEdt = (TextInputEditText) root.findViewById(R.id.new_psw);
        mCfmPswEdt = (TextInputEditText) root.findViewById(R.id.confirm_psw);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View root = getView();
        if (root != null) {
            Button validateCodeButton = (Button) root.findViewById(R.id.verify_button);
            validateCodeButton.setOnClickListener(this);
            Button alterButton = (Button) root.findViewById(R.id.alter_button);
            alterButton.setOnClickListener(this);
            Button loginButton = (Button) root.findViewById(R.id.login_button);
            loginButton.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.verify_button:
                mPresenter.checkValidateInput(new ValidateRequest(mUserPhoneEdt.getText().toString(),
                        mValidateCodeEdt.getText().toString()));
                break;
            case R.id.alter_button:
                mPresenter.checkAlterPswInput(new AlterPswRequest(mUserPhoneEdt.getText().toString(),
                        mValidateCodeEdt.getText().toString(),
                        mNewPswEdt.getText().toString(),
                        mCfmPswEdt.getText().toString()));
                break;
            case R.id.login_button:
                EventBus.getDefault().post(new SkipEvent(SkipEvent.FGT_PSW_EVENT, SkipEvent.LOGIN_SKIP));
                break;
        }
    }

    @Override
    public void setPresenter(ForgetPswContact.Presenter presenter) {
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
}
