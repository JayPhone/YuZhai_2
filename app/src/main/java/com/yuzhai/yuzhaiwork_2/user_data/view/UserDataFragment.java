package com.yuzhai.yuzhaiwork_2.user_data.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.yuzhai.yuzhaiwork_2.R;
import com.yuzhai.yuzhaiwork_2.base.http.IPConfig;
import com.yuzhai.yuzhaiwork_2.user_data.bean.UserDataResponse;
import com.yuzhai.yuzhaiwork_2.user_data.contact.UserDataContact;
import com.yuzhai.yuzhaiwork_2.user_data.request.AgreeAcceptOrderRequest;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.yuzhai.yuzhaiwork_2.user_data.view.UserDataActivity.PRICE;

/**
 * Created by 35429 on 2017/6/14.
 */

public class UserDataFragment extends Fragment implements UserDataContact.View, View.OnClickListener {
    private static final String TAG = "UserDataFragment";

    public static UserDataFragment newInstance() {
        Bundle args = new Bundle();
        UserDataFragment fragment = new UserDataFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private UserDataContact.Presenter mPresenter;
    private CircleImageView mUserAvatars;
    private TextView mUserName;
    private TextView mUserPhone;
    private TextView mUserRealName;
    private ProgressDialog mProgressDialog;
    private Dialog mAgreeAcceptDialog;
    private boolean isInit = false;

    private UserDataResponse mUserDataResponse;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_data, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        View root = getView();
        if (root != null) {

            Toolbar toolbar = (Toolbar) root.findViewById(R.id.user_data_toolbar);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().finish();
                }
            });
            mUserName = (TextView) root.findViewById(R.id.user_name);
            mUserAvatars = (CircleImageView) root.findViewById(R.id.user_header);
            mUserPhone = (TextView) root.findViewById(R.id.status);
            mUserRealName = (TextView) root.findViewById(R.id.authen);

            //如果返回的金额为空，不显示同意接收按钮
            TextView applyOrder = (TextView) root.findViewById(R.id.agree_accept_text);
            applyOrder.setOnClickListener(this);
            if (getPrice() == null) {
                applyOrder.setVisibility(View.GONE);
            }

            FloatingActionButton contactFab = (FloatingActionButton) root.findViewById(R.id.contact_fab);
            contactFab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                //被聊天用户
//                ContactUserDataBean contactUserDataBean = new ContactUserDataBean(
//                        mApplyUserDataBean.getUserPhone(),
//                        mApplyUserDataBean.getUserName(),
//                        mApplyUserDataBean.getAvatar());
//
//                BmobIMUserInfo bmobIMUserInfo =
//                        new BmobIMUserInfo(contactUserDataBean.getUserId(), contactUserDataBean.getName(), contactUserDataBean.getAvatar());
//                //开启对话
//                mConversation = BmobIM.getInstance().startPrivateConversation(bmobIMUserInfo, null);
//                //进入详细聊天界面
//                Intent contactDetail = new Intent(UserDataActivity.this, ContactDetailActivity.class);
//                contactDetail.putExtra("c", mConversation);
//                startActivity(contactDetail);
                }
            });
        }
    }

    @Override
    public void setPresenter(UserDataContact.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(getActivity().getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isInit) {
            mPresenter.start();
            isInit = true;
        }
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public String getAvatarUrl() {
        return getActivity().getIntent().getStringExtra(UserDataActivity.AVATAR);
    }

    @Override
    public String getPrice() {
        return getActivity().getIntent().getStringExtra(PRICE);
    }

    @Override
    public String getOrderId() {
        return getActivity().getIntent().getStringExtra(UserDataActivity.ORDER);
    }

    @Override
    public void showAgreeAcceptDialog() {
        if (mAgreeAcceptDialog == null) {
            mAgreeAcceptDialog = new AlertDialog.Builder(getActivity())
                    .setTitle("同意申请")
                    .setMessage("同意申请者接单，你需要缴纳订单金额，保证金(订单金额30%)及服务费(订单金额3%)，缴纳成功后，订单开始")
                    .setPositiveButton("我要支付", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mPresenter.sendAgreeAcceptOrderRequest(new AgreeAcceptOrderRequest(getOrderId(),
                                    mUserDataResponse.getUserPhone()));
//                Intent pay = new Intent(AcceptUserActivity.this, PayActivity.class);
//                pay.putExtra(PAY_ROLE, PUBLISH_ROLE);
//                pay.putExtra(PRICE, mPrice);
//                pay.putExtra(PAY_DESCRIPTION, "支付金额为您的订单金额加上订单保证金(订单金额的30%)加上订单服务费(订单金额的3%),订单保证金会在接收方完成后退还到您的账户，同时，如果申请人在24小时内没有确认订单，将退还订单金额到您的账户");
//                startActivity(pay);
//                sendAgreeApplyOrderRequest(mOrderId, mApplyUserDataBean.getUserPhone());
                        }
                    })
                    .setNegativeButton("取消", null)
                    .create();
        }
        mAgreeAcceptDialog.show();
    }

    @Override
    public void setUserData(UserDataResponse userDataResponse) {
        mUserDataResponse = userDataResponse;
        //获取头像
        Glide.with(this)
                .load(IPConfig.IMAGE_PREFIX + userDataResponse.getAvatar())
                .dontAnimate()
                .placeholder(R.drawable.default_image)
                .error(R.drawable.default_image)
                .into(mUserAvatars);
        mUserName.setText(userDataResponse.getUserName());
        mUserPhone.setText(userDataResponse.getUserPhone());
        mUserRealName.setText(userDataResponse.getAuthen());
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
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.agree_accept_text:
                showAgreeAcceptDialog();
                break;
        }
    }
}
