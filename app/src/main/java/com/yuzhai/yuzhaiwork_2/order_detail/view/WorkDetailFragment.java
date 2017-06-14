package com.yuzhai.yuzhaiwork_2.order_detail.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.yuzhai.yuzhaiwork_2.R;
import com.yuzhai.yuzhaiwork_2.base.global.CustomApplication;
import com.yuzhai.yuzhaiwork_2.base.http.IPConfig;
import com.yuzhai.yuzhaiwork_2.base.view.CircleImageView;
import com.yuzhai.yuzhaiwork_2.category.view.WorkFragment;
import com.yuzhai.yuzhaiwork_2.collection.view.CollectionActivity;
import com.yuzhai.yuzhaiwork_2.order_detail.adapter.NeededImageAdapter;
import com.yuzhai.yuzhaiwork_2.order_detail.bean.NeededImageData;
import com.yuzhai.yuzhaiwork_2.order_detail.bean.WorkDetailResponse;
import com.yuzhai.yuzhaiwork_2.order_detail.contact.WorkDetailContact;
import com.yuzhai.yuzhaiwork_2.order_detail.request.ApplyOrderRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 35429 on 2017/6/12.
 */

public class WorkDetailFragment extends Fragment implements WorkDetailContact.View,
        Toolbar.OnMenuItemClickListener,
        View.OnClickListener {
    private static final String TAG = "WorkDetailFragment";

    public static WorkDetailFragment newInstance(String orderId) {
        Bundle args = new Bundle();
        args.putString(WorkFragment.ORDER_ID, orderId);
        WorkDetailFragment fragment = new WorkDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private WorkDetailContact.Presenter mPresenter;

    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private TextView mTitle;
    private CircleImageView mUserHeader;
    private TextView mUserName;
    private TextView mID;
    private TextView mStatus;
    private TextView mDeadline;
    private TextView mDate;
    private TextView mTel;
    private TextView mDescription;
    private ViewPager mImagesViewPager;
    private LinearLayout mApplyUserLayout;
    private FloatingActionButton mApplyOrderFab;
    private ProgressDialog mProgressDialog;
    private Dialog mApplyOrderDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_work_detail, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View root = getView();
        if (root != null) {
            mCollapsingToolbarLayout = (CollapsingToolbarLayout) root.findViewById(R.id.toolbar_layout);
            mCollapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
            mCollapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);

            Toolbar toolbar = (Toolbar) root.findViewById(R.id.toolbar);
            toolbar.inflateMenu(R.menu.detail_order_menu);
            toolbar.setOnMenuItemClickListener(this);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().finish();
                }
            });

            mTitle = (TextView) root.findViewById(R.id.title);
            mUserHeader = (CircleImageView) root.findViewById(R.id.user_header);
            mUserHeader.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent userData = new Intent(WorkDetailActivity.this, UserDataActivity.class);
//                    userData.putExtra(AVATAR, mOrder.getPublisherAvatar());
//                    startActivity(userData);
                }
            });

            mUserName = (TextView) root.findViewById(R.id.user_name);
            mID = (TextView) root.findViewById(R.id.id);
            mStatus = (TextView) root.findViewById(R.id.status);
            mDeadline = (TextView) root.findViewById(R.id.deadline);
            mDate = (TextView) root.findViewById(R.id.date);
            mTel = (TextView) root.findViewById(R.id.tel);
            mDescription = (TextView) root.findViewById(R.id.description);
            mImagesViewPager = (ViewPager) root.findViewById(R.id.image_viewpager);
            mApplyUserLayout = (LinearLayout) root.findViewById(R.id.apply_user);
            mApplyOrderFab = (FloatingActionButton) root.findViewById(R.id.apply_order_fab);
            mApplyOrderFab.setOnClickListener(this);
        }


//        mImageFlipper.setOnItemClickListener(new CustomViewFlipper.OnItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//                if (CustomApplication.isConnect) {
//                    Intent showImage = new Intent(WorkDetailActivity.this, ShowImageActivity.class);
//                    showImage.putExtra(IMAGE_URL, mOrder.getPictures().get(position).getImage());
//                    Log.i(TAG, "pictures_url:" + mOrder.getPictures().get(position).getImage());
//                    startActivity(showImage);
//                }
//            }
//        });

    }

    @Override
    public void setPresenter(WorkDetailContact.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
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
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.star:
                Snackbar sb = Snackbar.make(mApplyOrderFab, "收藏成功，点击浏览查看收藏列表", Snackbar.LENGTH_SHORT);
                sb.show();
                sb.setAction("浏览", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent collection = new Intent(getActivity(), CollectionActivity.class);
                        startActivity(collection);
                    }
                });
                break;
            case R.id.call:
                break;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.apply_order_fab:
                if (!CustomApplication.getInstance().isLogin()) {
                    Snackbar.make(v, "你尚未登陆，请登录后再接单", Snackbar.LENGTH_SHORT).show();
                } else {
                    showApplyOrderDialog();
                }
                break;
        }
    }

    @Override
    public void setWorkDetailData(WorkDetailResponse workDetailData) {
        //设置订单标题
        mTitle.setText(workDetailData.getDetailed_order().getTitle());
        //设置订单金额
        mCollapsingToolbarLayout.setTitle("￥" + workDetailData.getDetailed_order().getReward());
        //设置发布用户名
        mUserName.setText(workDetailData.getDetailed_order().getPublisher());
        //设置订单Id
        mID.setText(workDetailData.getDetailed_order().getOrder_id());
        //设置订单状态
        mStatus.setText(workDetailData.getDetailed_order().getStatus());
        //设置订单期限
        mDeadline.setText(workDetailData.getDetailed_order().getDeadline());
        //设置订单发布日期
        mDate.setText(workDetailData.getDetailed_order().getDate());
        //设置发布方联系电话
        mTel.setText(workDetailData.getDetailed_order().getTel());
        //设置订单详细描述
        mDescription.setText(workDetailData.getDetailed_order().getDescription());
        //设置订单图片
        setNeededImages(workDetailData.getDetailed_order().getPictures());
        //设置用户头像
        Log.d(TAG, IPConfig.IMAGE_PREFIX + workDetailData.getDetailed_order().getPublisher_avatar());
        Glide.with(this)
                .load(IPConfig.IMAGE_PREFIX + workDetailData.getDetailed_order().getPublisher_avatar())
                .placeholder(R.drawable.default_image)
                .error(R.drawable.default_image)
                .into(mUserHeader);
        //设置申请订单用户
        setApplicantAvatars(workDetailData.getDetailed_order().getApplicant_avatars());
    }

    @Override
    public String getOrderId() {
        return getArguments().getString(WorkFragment.ORDER_ID);
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
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

    @Override
    public void setNeededImages(List<WorkDetailResponse.WorkDetail.Pictures> pictures) {
        if (pictures != null && pictures.size() > 0) {
            List<NeededImageData> neededImageDatas = new ArrayList<>();
            for (WorkDetailResponse.WorkDetail.Pictures url : pictures) {
                neededImageDatas.add(new NeededImageData(url.getImage()));
            }
            Log.d(TAG, neededImageDatas.toString());
            new NeededImageAdapter(getActivity(), neededImageDatas, mImagesViewPager);
        }
    }

    @Override
    public void setApplicantAvatars(List<WorkDetailResponse.WorkDetail.ApplicantAvatars> applicantAvatarses) {
        if (applicantAvatarses != null && applicantAvatarses.size() > 0) {
            int layoutWidth = mApplyUserLayout.getMeasuredWidth();
            int circleImageWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 45, getResources().getDisplayMetrics());
            int circleImageHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 45, getResources().getDisplayMetrics());
            int marginLeft = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics());

            int hasCount = applicantAvatarses.size();
            int maxCount = (layoutWidth + marginLeft) / (circleImageWidth + marginLeft);
            maxCount = hasCount >= maxCount ? maxCount : hasCount;

            for (int i = 0; i < maxCount; i++) {
                CircleImageView circleImageView = new CircleImageView(getActivity());
                circleImageView.setBorderColor(Color.WHITE);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(circleImageWidth, circleImageHeight);
                if (i != 0) {
                    params.setMargins(marginLeft, 0, 0, 0);
                }

                circleImageView.setLayoutParams(params);
                Log.i(TAG, applicantAvatarses.get(i).getApplicantAvatar());
                Glide.with(getActivity().getApplicationContext())
                        .load(IPConfig.IMAGE_PREFIX + applicantAvatarses.get(i).getApplicantAvatar())
                        .placeholder(R.drawable.default_image)
                        .error(R.drawable.default_image)
                        .into(circleImageView);
                mApplyUserLayout.addView(circleImageView);
            }
        }
    }

    /**
     * 显示申请订单对话框
     */
    @Override
    public void showApplyOrderDialog() {
        if (mApplyOrderDialog == null) {
            mApplyOrderDialog = new AlertDialog.Builder(getActivity())
                    .setTitle("申请接单")
                    .setMessage("申请接单需要缴纳违约金(订单金额30%)，违约金将会在订单完成之后返还到您的账户")
                    .setPositiveButton("申请并支付", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
//                                Intent pay = new Intent(getActivity(), PayActivity.class);
                            //计算支付金额
//                                double rewardPrice = Double.parseDouble(mOrder.getReward());
//                                double penalty = rewardPrice * 0.3;
//                                pay.putExtra(PAY_ROLE, APPLY_ROLE);
//                                pay.putExtra(PRICE, String.valueOf(penalty));
//                                pay.putExtra(PAY_DESCRIPTION, "支付金额为您申请接收的订单违约金(订单金额的30%),订单违约金会在发布方确认完成订单后退还到您的账户。");
//                                startActivity(pay);
                            mPresenter.sentApplyOrderRequest(new ApplyOrderRequest(getOrderId()));
                        }
                    })
                    .setNegativeButton("取消", null).create();
        }
        mApplyOrderDialog.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }
}
