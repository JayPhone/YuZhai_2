package com.yuzhai.yuzhaiwork_2.main.view;

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
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.yuzhai.yuzhaiwork_2.R;
import com.yuzhai.yuzhaiwork_2.base.global.CustomApplication;
import com.yuzhai.yuzhaiwork_2.base.util.BitmapUtil;
import com.yuzhai.yuzhaiwork_2.base.util.FileUtil;
import com.yuzhai.yuzhaiwork_2.base.util.GetPathUtil;
import com.yuzhai.yuzhaiwork_2.login_reg.view.LoginRegActivity;
import com.yuzhai.yuzhaiwork_2.main.bean.PublishResponse;
import com.yuzhai.yuzhaiwork_2.main.contact.PublishContact;
import com.yuzhai.yuzhaiwork_2.main.event.PublishedEvent;
import com.yuzhai.yuzhaiwork_2.main.model.PublishRemoteModel;
import com.yuzhai.yuzhaiwork_2.main.request.PublishRequest;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by 35429 on 2017/5/25.
 */

public class PublishFragment extends Fragment implements PublishContact.View,
        View.OnClickListener,
        View.OnTouchListener {
    private static final String TAG = "PublishFragment";

    public static PublishFragment newInstance() {
        Bundle args = new Bundle();
        PublishFragment fragment = new PublishFragment();
        fragment.setArguments(args);
        return fragment;
    }

    //Activity请求码
    private final int CAMERA_REQUEST = 1;
    private final int IMAGE_PICK_REQUEST = 2;

    private List<String> mImagePathsList;
    private String mImagePath;

    private PublishContact.Presenter mPresenter;
    private EditText mTitleEdit;
    private EditText mDescriptionEdit;
    private LinearLayout mImagesPreviewLayout;
    private Spinner mTypeSpinner;
    private Spinner mLimitSpinner;
    private EditText mTelEdit;
    private EditText mRewardEdit;
    private Button mPublishButton;
    private Dialog mAddImageDialog;
    private Dialog mPermissionDenienDialog;
    private Dialog mDeleteImageDialog;
    private Snackbar mUnLoginSnackBar;
    private ProgressDialog mProgressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_publish, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final DrawerLayout drawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer);

        View root = getView();
        if (root != null) {
            Toolbar toolbar = (Toolbar) root.findViewById(R.id.publish_toolbar);
            toolbar.inflateMenu(R.menu.publish_menu);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawerLayout.openDrawer(Gravity.START);
                }
            });

            mTitleEdit = (EditText) root.findViewById(R.id.need_title);
            mDescriptionEdit = (EditText) root.findViewById(R.id.need_content);
            mDescriptionEdit.setOnTouchListener(this);
            mImagesPreviewLayout = (LinearLayout) root.findViewById(R.id.images_preview);
            ImageView uploadImageView = (ImageView) root.findViewById(R.id.upload_image);
            uploadImageView.setOnClickListener(this);
            mTypeSpinner = (Spinner) root.findViewById(R.id.type_spinner);
            mLimitSpinner = (Spinner) root.findViewById(R.id.date_spinner);
            mTelEdit = (EditText) root.findViewById(R.id.contact);
            mRewardEdit = (EditText) root.findViewById(R.id.reward);
            mPublishButton = (Button) root.findViewById(R.id.publish_button);
            mPublishButton.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.upload_image:
                showAddImageDialog();
                break;

            //点击发布按钮
            case R.id.publish_button:
                if (!CustomApplication.getInstance().isLogin()) {
                    showSnackBar();
                } else {
                    String type = mTypeSpinner.getSelectedItem().toString();
                    String limit = mLimitSpinner.getSelectedItem().toString();

                    //校验数据
                    mPresenter.checkPublishInput(new PublishRequest(mTitleEdit.getText().toString()
                            , mDescriptionEdit.getText().toString(),
                            type.substring(6, type.length() - 1),
                            limit.substring(6, limit.length() - 1),
                            mTelEdit.getText().toString(),
                            mRewardEdit.getText().toString(),
                            mImagePathsList));
                }
                break;
        }
    }

    /**
     * 打开相机
     */
    @Override
    public void showCamera() {
        mImagePath = FileUtil.createFilePath(FileUtil.NEED_IMAGE);
        Log.i(TAG, "path :" + mImagePath);
        Intent intent_camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent_camera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(mImagePath)));
        startActivityForResult(intent_camera, CAMERA_REQUEST);
    }

    /**
     * 打开图片选择器
     */
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
    public void showSnackBar() {
        if (mUnLoginSnackBar == null) {
            mUnLoginSnackBar = Snackbar.make(mPublishButton, "您尚未登陆，请登录后再发布需求", Snackbar.LENGTH_LONG);
            mUnLoginSnackBar.setAction("登录", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent login_reg = new Intent(getActivity(), LoginRegActivity.class);
                    startActivity(login_reg);
                }
            });
        }
        mUnLoginSnackBar.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.i(TAG, "UserVisible:" + isVisibleToUser);
    }

    @Override
    public void setPresenter(PublishContact.Presenter presenter) {
        mPresenter = presenter;
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
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.need_content:
                v.getParent().requestDisallowInterceptTouchEvent(true);
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_UP:
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
        }
        return false;
    }

    @Override
    public void setTypeSpinnerData(List<Map<String, String>> typeSpinnerData) {
        mTypeSpinner.setAdapter(new SimpleAdapter(getActivity().getApplicationContext(),
                typeSpinnerData, R.layout.publish_spinner_item_layout,
                new String[]{PublishRemoteModel.DATA},
                new int[]{R.id.spinner_item}
        ));
    }

    @Override
    public void setLimitSpinnerData(List<Map<String, String>> limitSpinnerData) {
        mLimitSpinner.setAdapter(new SimpleAdapter(getActivity().getApplicationContext(),
                limitSpinnerData, R.layout.publish_spinner_item_layout,
                new String[]{PublishRemoteModel.DATA},
                new int[]{R.id.spinner_item}
        ));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CAMERA_REQUEST:
                if (resultCode == Activity.RESULT_OK) {
                    //添加图片到预览布局
                    addImageIntoPreviewLayout(mImagePath);
                    //添加当前图片路径到List
                    mImagePathsList.add(mImagePath);
                }
                break;
            case IMAGE_PICK_REQUEST:
                if (resultCode == Activity.RESULT_OK) {
                    Uri uri = data.getData();
                    mImagePath = GetPathUtil.getImageAbsolutePath(getActivity(), uri);
                    //添加图片到预览布局
                    addImageIntoPreviewLayout(mImagePath);
                    //添加当前图片路径到List
                    mImagePathsList.add(mImagePath);
                }
                break;
        }
    }

    //添加图片到预览布局
    @Override
    public void addImageIntoPreviewLayout(String imagePath) {
        int imageMargin = 15;
        int imageLayoutWidth = mImagesPreviewLayout.getMeasuredWidth();
        int imageWidth = (imageLayoutWidth - (imageMargin * 6)) / 5;
        int imageHeight = (imageLayoutWidth - (imageMargin * 6)) / 5;
        ImageView needImage = new ImageView(getActivity().getApplicationContext());
        needImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(imageWidth, imageHeight);
        params.setMargins(imageMargin, 0, 0, 0);
        needImage.setLayoutParams(params);
        mImagesPreviewLayout.addView(needImage);
        needImage.setImageBitmap(BitmapUtil.decodeSampledBitmapFromFile(imagePath, imageWidth, imageHeight));
    }

    @Override
    public void showDeleteImageDialog(final View view) {
        if (mDeleteImageDialog == null) {
            mDeleteImageDialog = new AlertDialog
                    .Builder(getActivity())
                    .setTitle("确认删除所选图片")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mImagesPreviewLayout.removeView(view);
                        }
                    }).setNegativeButton("取消", null).create();
        }
        mDeleteImageDialog.show();
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
    public void clearInput() {
        mTitleEdit.getText().clear();
        mDescriptionEdit.getText().clear();
        mImagesPreviewLayout.removeAllViews();
        mTypeSpinner.setSelection(0);
        mLimitSpinner.setSelection(0);
        mTelEdit.getText().clear();
        mRewardEdit.getText().clear();
    }

    @Override
    public void setPublishResult(PublishResponse publishResponse) {
        if (publishResponse.getCode().equals("1")) {
            showToast("发布成功");
            clearInput();
            EventBus.getDefault().postSticky(new PublishedEvent());
        }
    }

    /**
     * 检测是否拥有相机权限
     */
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
    public boolean isSelectImageNumOverflow() {
        if (mImagePathsList == null) {
            mImagePathsList = new ArrayList<>();
        }
        return mImagePathsList.size() > 5;
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

    //对话框显示图片的添加方式
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
                                            if (isSelectImageNumOverflow()) {
                                                showToast(getString(R.string.image_select_limit));
                                            } else {
                                                checkCameraPermission();
                                            }
                                            break;
                                        case 1:
                                            if (isSelectImageNumOverflow()) {
                                                showToast(getString(R.string.image_select_limit));
                                            } else {
                                                showImagePick();
                                            }
                                            break;
                                    }
                                }
                            }).create();
        }
        mAddImageDialog.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }
}
