package com.yuzhai.yuzhaiwork_2.home_page.view;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.yuzhai.yuzhaiwork_2.R;
import com.yuzhai.yuzhaiwork_2.base.global.CustomApplication;
import com.yuzhai.yuzhaiwork_2.home_page.bean.HomePageData;
import com.yuzhai.yuzhaiwork_2.home_page.contact.HomePageContact;
import com.yuzhai.yuzhaiwork_2.login_reg.event.UserInfoEvent;
import com.yuzhai.yuzhaiwork_2.login_reg.request.LoginResponse;
import com.yuzhai.yuzhaiwork_2.main.view.MainActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

/**
 * Created by 35429 on 2017/6/6.
 */

public class HomePageFragment extends Fragment implements HomePageContact.View {
    public static HomePageFragment newInstance() {
        Bundle args = new Bundle();
        HomePageFragment fragment = new HomePageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private static final String TAG = "HomePageFragment";
    private HomePageContact.Presenter mPresenter;
    private ImageView mHomePageImage;
    //请求获取读写外存权限请求码
    private static final int STORAGE_PERMISSION = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(inflater.getContext()).inflate(R.layout.fragment_home_page, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //检测是否获取到读写内存的权限
        checkStoragePermission();
        View root = getView();
        if (root != null) {
            mHomePageImage = (ImageView) root.findViewById(R.id.logo_image);
        }
    }

    /**
     * 检测是否拥有读写外存权限
     */
    private void checkStoragePermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION);
        } else {
            Log.d(TAG, "权限已获取");
            sendLoginRequest();
        }
    }

    /**
     * 当获取读写存储器的权限拒绝时，显示一个对话框让用户开启
     */
    public void showOnDenienStoragePermission() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("权限申请");
        builder.setMessage("请到设置 - 应用 - 御宅工作 - 权限中开启读取内部存储权限，以正常使用御宅工作的功能");
        builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent getPermission = new Intent(Settings.ACTION_SETTINGS);
                startActivityForResult(getPermission, 1);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getActivity().finish();
            }
        }).create().show();
    }

    @Override
    public void setPresenter(HomePageContact.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(getActivity().getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void setHomePageData(HomePageData homePageData) {
        mHomePageImage.setImageResource(homePageData.getImageId());
    }

    @Override
    public void setAutoLoginState(boolean loginState) {
        //设置登录状态
        ((CustomApplication) getActivity().getApplication()).setIoginState(loginState);
    }

    @Override
    public void sendLoginRequest() {
        mPresenter.sendLoginRequest();
    }

    @Override
    public void setLoginResponseData(LoginResponse loginResponseData) {
        //-1登录失败，1登录成功
        if (!loginResponseData.getCode().equals("-1")) {
            EventBus.getDefault().postSticky(new UserInfoEvent(loginResponseData.getUser_name()
                    , loginResponseData.getUser_head_url()));
        }
        finishActivity();
    }

    @Override
    public void finishActivity() {
        Observable
                .timer(2, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull Long aLong) throws Exception {
                        //打开主页面
                        Intent main = new Intent(getActivity(), MainActivity.class);
                        getActivity().startActivity(main);
                        getActivity().finish();
                    }
                });
    }

    //权限许可情况调用
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case STORAGE_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    sendLoginRequest();
                } else {
                    showOnDenienStoragePermission();
                }
                break;
        }
    }
}
