package com.yuzhai.yuzhaiwork_2.main.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yuzhai.yuzhaiwork_2.R;
import com.yuzhai.yuzhaiwork_2.base.view.recycler.DividerGridItemDecoration;
import com.yuzhai.yuzhaiwork_2.base.view.TranslucentScrollView;
import com.yuzhai.yuzhaiwork_2.base.view.recycler.ItemTouchHelperCallback;
import com.yuzhai.yuzhaiwork_2.category.view.CategoryActivity;
import com.yuzhai.yuzhaiwork_2.main.adapter.CategoryRVAdapter;
import com.yuzhai.yuzhaiwork_2.main.adapter.HomeBannberAdapter;
import com.yuzhai.yuzhaiwork_2.main.bean.BannerData;
import com.yuzhai.yuzhaiwork_2.main.bean.CategoryData;
import com.yuzhai.yuzhaiwork_2.main.contact.HomeContact;
import com.yuzhai.yuzhaiwork_2.notification.view.NotificationCenterActivity;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static junit.runner.Version.id;

/**
 * Created by 35429 on 2017/5/25.
 */

public class HomeFragment extends Fragment implements HomeContact.View, View.OnClickListener,
        CategoryRVAdapter.OnCategoryClickListener {
    public static final String CATEGORY = "category";
    private static final String TAG = "HomeFragment";
    private HomeContact.Presenter mPresenter;
    private ViewPager mBanner;
    private RecyclerView mCategory;
    private DrawerLayout mDrawerLayout;
    private Disposable mDisposable;
    private List<BannerData> mBannerData;
    private boolean isLooping = false;
    private boolean isInit = false;

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setPresenter(HomeContact.Presenter presenter) {
        mPresenter = presenter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final View root = getView();
        if (root != null) {
            //侧滑菜单
            mDrawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer);

            //初始化ToolBar
            final FrameLayout statusBar = (FrameLayout) root.findViewById(R.id.home_status_bar);
            statusBar.getBackground().mutate().setAlpha(0);
            final RelativeLayout toolbar = (RelativeLayout) root.findViewById(R.id.home_toolbar);
            toolbar.getBackground().mutate().setAlpha(0);
            ImageView navigationImage = (ImageView) root.findViewById(R.id.menu_image);
            navigationImage.setOnClickListener(this);
            TextView searchView = (TextView) getView().findViewById(R.id.search_view);
            searchView.setOnClickListener(this);
            ImageView notificationsImage = (ImageView) getView().findViewById(R.id.notification_image);
            notificationsImage.setOnClickListener(this);

            //初始化标题栏透明滑动视图
            TranslucentScrollView translucentScrollView = (TranslucentScrollView) root.findViewById(R.id.home_scroll);
            translucentScrollView.setOnScrollingListener(new TranslucentScrollView.OnScrollingListener() {
                @Override
                public void onTranslucent(int h, int v, int oldH, int oldV) {
                    if ((float) v <= mBanner.getMeasuredHeight() - toolbar.getMeasuredHeight()) {
                        statusBar.getBackground().mutate().setAlpha(
                                (int) ((float) v / (mBanner.getMeasuredHeight() - toolbar.getMeasuredHeight()) * 255));
                        toolbar.getBackground().mutate().setAlpha(
                                (int) ((float) v / (mBanner.getMeasuredHeight() - toolbar.getMeasuredHeight()) * 255));
                    } else if (oldV > mBanner.getMeasuredHeight() - toolbar.getMeasuredHeight()) {
                        statusBar.getBackground().mutate().setAlpha(255);
                        toolbar.getBackground().mutate().setAlpha(255);
                    }
                }
            });

            //初始化Banner
            mBanner = (ViewPager) root.findViewById(R.id.banner);
            mBanner.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            //停止滚动
                            if (mBannerData != null && isLooping)
                                stopLoopBanner();
                            break;
                        case MotionEvent.ACTION_UP:
                            //开始滚动
                            if (mBannerData != null && !isLooping)
                                startLoopBannber();
                            break;
                    }
                    return false;
                }
            });

            //初始化类别面板
            mCategory = (RecyclerView) root.findViewById(R.id.category);
            mCategory.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(), 3) {
                @Override
                public boolean canScrollVertically() {
                    //禁止竖向滑动
                    return false;
                }
            });
            mCategory.setItemAnimator(new DefaultItemAnimator());
            mCategory.addItemDecoration(new DividerGridItemDecoration(getActivity().getApplicationContext()));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menu_image:
                mDrawerLayout.openDrawer(Gravity.START);
                break;
            case R.id.notification_image:
                Intent notification_center = new Intent(getActivity(), NotificationCenterActivity.class);
                startActivity(notification_center);
                break;
            case R.id.search_view:
//                    Intent search_intent = new Intent();
//                    search_intent.setClass(mainActivity, SearchActivity.class);
//                    startActivity(search_intent);
                break;
        }
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(getActivity().getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startLoopBannber() {
        Observable.interval(5, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        isLooping = true;
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(@NonNull Long aLong) {
                        if (mBanner.getCurrentItem() == mBannerData.size() - 2) {
                            mBanner.setCurrentItem(1, false);
                        } else {
                            mBanner.setCurrentItem(mBanner.getCurrentItem() + 1, false);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
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
    public void stopLoopBanner() {
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
            isLooping = false;
        }
    }

    @Override
    public void setBannerData(List<BannerData> bannerDatas) {
        Log.d(TAG, bannerDatas.toString());
        //获取Banner数据
        mBannerData = bannerDatas;
        //为Banner设置数据源
        new HomeBannberAdapter(getActivity().getApplicationContext(), mBannerData, mBanner);
        if (!isLooping) {
            startLoopBannber();
        }
    }

    @Override
    public void setCategoryData(List<CategoryData> categoryDatas) {
        Log.d(TAG, categoryDatas.toString());
        //获取Category数据
        CategoryRVAdapter adapter = new CategoryRVAdapter(categoryDatas);
        adapter.setOnCategoryClickListenter(this);
        //为Category设置数据源
        mCategory.setAdapter(adapter);
        ItemTouchHelperCallback itemTouchHelperCallback = new ItemTouchHelperCallback(adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchHelperCallback);
        itemTouchHelper.attachToRecyclerView(mCategory);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //banner数据不为空且正在显示开始滚动面板
        if (mBannerData != null) {
            if (isVisibleToUser) {
                if (!isLooping) {
                    startLoopBannber();
                }
            } else {
                if (isLooping) {
                    stopLoopBanner();
                }
            }
        }
        Log.i(TAG, "UserVisible:" + isVisibleToUser);
    }

    @Override
    public void onCategoryClick(String type) {
        Log.d(TAG, type);
        Intent category = new Intent(getActivity(), CategoryActivity.class);
        category.putExtra(CATEGORY, type);
        getActivity().startActivity(category);
    }
}
