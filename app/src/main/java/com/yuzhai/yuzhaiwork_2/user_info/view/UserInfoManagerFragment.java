package com.yuzhai.yuzhaiwork_2.user_info.view;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.yuzhai.yuzhaiwork_2.R;
import com.yuzhai.yuzhaiwork_2.alter_psw.view.AlterPswActivity;
import com.yuzhai.yuzhaiwork_2.base.global.CustomApplication;
import com.yuzhai.yuzhaiwork_2.base.http.IPConfig;
import com.yuzhai.yuzhaiwork_2.base.util.FileUtil;
import com.yuzhai.yuzhaiwork_2.base.util.GetPathUtil;
import com.yuzhai.yuzhaiwork_2.main.event.UserDetailInfoEvent;
import com.yuzhai.yuzhaiwork_2.user_info.bean.ExitLoginResponse;
import com.yuzhai.yuzhaiwork_2.user_info.bean.UploadAvaterResponse;
import com.yuzhai.yuzhaiwork_2.user_info.contact.UserInfoManagerContact;
import com.yuzhai.yuzhaiwork_2.user_info.event.AvatarAlterEvent;
import com.yuzhai.yuzhaiwork_2.base.event.LoginEvent;
import com.yuzhai.yuzhaiwork_2.user_info.event.ReNameEvent;
import com.yuzhai.yuzhaiwork_2.user_info.request.ReNameRequest;
import com.yuzhai.yuzhaiwork_2.user_info.request.UploadAvaterRequest;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;

/**
 * Created by 35429 on 2017/6/8.
 */

public class UserInfoManagerFragment extends Fragment implements UserInfoManagerContact.View,
        View.OnClickListener {
    public static UserInfoManagerFragment newInstance() {
        Bundle args = new Bundle();
        UserInfoManagerFragment fragment = new UserInfoManagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private static final String TAG = "UserInfoManagerFragment";

    //Activity请求码
    private final int CAMERA_REQUEST = 1;
    private final int IMAGE_PICK_REQUEST = 2;
    private String mImagePath;

    private String mUserAvatarUrl;
    private String mUserName;
    private String mUserPhone;
    private String mRealName;
    private String mNewName;

    private TextView mUserNameText;
    private TextView mUserPhoneText;
    private TextView mRealNameText;

    private UserInfoManagerContact.Presenter mPresenter;
    private ImageView mAvatarImage;
    private Dialog mPermissionDenienDialog;
    private Dialog mAddImageDialog;
    private ProgressDialog mProgressDialog;
    private Dialog mAlterUserNameDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_userinfo_manager, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onActivityCreated(savedInstanceState);
        View root = getView();
        if (root != null) {
            Toolbar toolbar = (Toolbar) root.findViewById(R.id.user_info_toolbar);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().finish();
                }
            });

            RelativeLayout alterHeaderImageLayout = (RelativeLayout) root.findViewById(R.id.alter_image_layout);
            alterHeaderImageLayout.setOnClickListener(this);
            mAvatarImage = (ImageView) root.findViewById(R.id.header_image);
            TextView alterPswText = (TextView) root.findViewById(R.id.change_pswd);
            alterPswText.setOnClickListener(this);
            RelativeLayout alterUserNameLayout = (RelativeLayout) root.findViewById(R.id.alter_user_name_layout);
            alterUserNameLayout.setOnClickListener(this);
            mUserNameText = (TextView) root.findViewById(R.id.user_name);
            mUserPhoneText = (TextView) root.findViewById(R.id.user_phone);
            mRealNameText = (TextView) root.findViewById(R.id.certification);
            Button loginExit = (Button) root.findViewById(R.id.exit_login);
            loginExit.setOnClickListener(this);
//
//            //设置监听器

//            mAvatarImage.setOnClickListener(this);


        }

        setInitData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //点击左边选择头像
            case R.id.alter_image_layout:
                //打开选择头像对话框
                showAddImageDialog();
                break;

            //点击修改用户名
            case R.id.alter_user_name_layout:
                showAlterUserNameDialog();
                break;

            //点击修改密码
            case R.id.change_pswd:
                Intent alterPsw = new Intent(getActivity(), AlterPswActivity.class);
                startActivity(alterPsw);
                break;

            //点击退出登录按钮
            case R.id.exit_login:
                exitLogin();
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.POSTING, sticky = true)
    public void onAcceptUserInfoFromMain(UserDetailInfoEvent userDetailInfoEvent) {
        mUserAvatarUrl = userDetailInfoEvent.getUserAvatarUrl();
        mUserName = userDetailInfoEvent.getUserName();
        mUserPhone = userDetailInfoEvent.getUserPhone();
        mRealName = userDetailInfoEvent.getRealName();
    }

    @Override
    public void setPresenter(UserInfoManagerContact.Presenter presenter) {
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
    public void setInitData() {
        mUserNameText.setText(mUserName);
        mUserPhoneText.setText(mUserPhone);
        mRealNameText.setText(mRealName);
        setAvatar(mUserAvatarUrl);
    }

    @Override
    public void exitLogin() {
        mPresenter.sendLoginExitRequest();
    }

    @Override
    public void exitLoginResponse(ExitLoginResponse exitLoginResponse) {
        if (exitLoginResponse.getCode().equals("1")) {
            EventBus.getDefault().post(new LoginEvent(true));
            CustomApplication.getInstance().setIoginState(false);
            getActivity().finish();
        }
    }

    @Override
    public void showAddImageDialog() {
        if (mAddImageDialog == null) {
            mAddImageDialog = new AlertDialog
                    .Builder(getActivity())
                    .setTitle(R.string.add_images)
                    .setItems(new String[]{getString(R.string.add_image_by_camera),
                                    getString(R.string.add_image_by_pick)},
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which) {
                                        case 0:
                                            checkCameraPermission();
                                            break;
                                        case 1:
                                            showImagePick();
                                            break;
                                    }
                                }
                            }).create();
        }
        mAddImageDialog.show();
    }

    @Override
    public void checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            this.requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST);
        } else {
            showCamera();
        }
    }

    @Override
    public void showOnDenienCameraPermission() {
        if (mPermissionDenienDialog == null) {
            mPermissionDenienDialog = new AlertDialog
                    .Builder(getActivity())
                    .setTitle("权限申请")
                    .setMessage("请到设置 - 应用 - 御宅工作 - 权限中开启相机权限，以正常使用头像上传，需求图片上传等功能")
                    .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent_getPermission = new Intent(Settings.ACTION_SETTINGS);
                            startActivity(intent_getPermission);
                        }
                    }).setNegativeButton("取消", null).create();
        }
        mPermissionDenienDialog.show();
    }

    @Override
    public void showCamera() {
        //生成路径
        mImagePath = FileUtil.createFilePath(FileUtil.HEAD_IMAGE);
        //启动相机
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        camera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(mImagePath)));
        startActivityForResult(camera, CAMERA_REQUEST);
    }

    @Override
    public void showImagePick() {
        Intent intent_imagePick = new Intent(Intent.ACTION_PICK);
        intent_imagePick.setType("image/*");
        //是否按比例缩放
        intent_imagePick.putExtra("scale", "true");
        //图片输出大小
        intent_imagePick.putExtra("outputX", 640);
        intent_imagePick.putExtra("outputY", 640);
        //图片输出格式
        intent_imagePick.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent_imagePick.putExtra("return-data", false);
        startActivityForResult(intent_imagePick, IMAGE_PICK_REQUEST);
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

    @Override
    public void setAvaterData(UploadAvaterResponse uploadAvaterResponse) {
        if (uploadAvaterResponse.getAvatar() != null) {
            setAvatar(uploadAvaterResponse.getAvatar());
            notifyUserAvatarAlter(uploadAvaterResponse.getAvatar());
        }
    }

    @Override
    public void setAvatar(String avatarUrl) {
        Log.i(TAG, "user_avatar_url:" + IPConfig.IMAGE_PREFIX + avatarUrl);
        Glide.with(this)
                .load(IPConfig.IMAGE_PREFIX + avatarUrl)
                .placeholder(R.drawable.default_image)
                .error(R.drawable.default_image)
                .into(mAvatarImage);

    }

    @Override
    public void showAlterUserNameDialog() {
        if (mAlterUserNameDialog == null) {
            View alterNameView = getActivity().getLayoutInflater()
                    .inflate(R.layout.userinfo_alter_username_layout, null);
            final EditText alterUserName = (EditText) alterNameView.findViewById(R.id.alter_user_name_edit);

            //创建重命名对话框
            mAlterUserNameDialog = new AlertDialog.Builder(getActivity()).setTitle("输入用户名")
                    .setView(alterNameView)
                    .setPositiveButton("修改", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (!alterUserName.getText().toString().equals("")) {
                                mNewName = alterUserName.getText().toString();
                                //发送重命名请求
                                mPresenter.sendReNameRequest(new ReNameRequest(mNewName));
                            }
                        }
                    })
                    .setNegativeButton("取消", null).create();
        }
        mAlterUserNameDialog.show();
    }

    @Override
    public void setNewName() {
        mUserNameText.setText(mNewName);
        notifyUserNameAlter();
    }

    @Override
    public void notifyUserNameAlter() {
        EventBus.getDefault().post(new ReNameEvent(mNewName));
    }

    @Override
    public void notifyUserAvatarAlter(String avatarUrl) {
        EventBus.getDefault().post(new AvatarAlterEvent(avatarUrl));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CAMERA_REQUEST:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showCamera();
                } else {
                    showOnDenienCameraPermission();
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CAMERA_REQUEST:
                if (resultCode == Activity.RESULT_OK) {
                    //检测数据
                    Log.d(TAG, mImagePath);
                    mPresenter.checkUploadAvater(new UploadAvaterRequest(mImagePath));
                }
                break;
            case IMAGE_PICK_REQUEST:
                if (resultCode == Activity.RESULT_OK) {
                    Uri uri = data.getData();
                    mImagePath = GetPathUtil.getImageAbsolutePath(getActivity(), uri);
                    //检测数据
                    Log.d(TAG, mImagePath);
                    mPresenter.checkUploadAvater(new UploadAvaterRequest(mImagePath));
                }
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
