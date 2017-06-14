package com.yuzhai.yuzhaiwork_2.main.presenter;

import com.yuzhai.yuzhaiwork_2.base.model.BaseModel;
import com.yuzhai.yuzhaiwork_2.main.bean.BannerData;
import com.yuzhai.yuzhaiwork_2.main.bean.CategoryData;
import com.yuzhai.yuzhaiwork_2.main.contact.HomeContact;
import com.yuzhai.yuzhaiwork_2.main.model.HomeLocalRepertory;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by 35429 on 2017/5/25.
 */

public class HomePresenter implements HomeContact.Presenter {
    private WeakReference<HomeContact.View> mHomeView;
    private HomeLocalRepertory mHomeLocalRepertory = new HomeLocalRepertory();

    public HomePresenter(HomeContact.View homeView) {
        mHomeView = new WeakReference<>(homeView);
        mHomeView.get().setPresenter(this);
    }

    @Override
    public void start() {
        //获取Banner数据
        getBannerData();
        //获取Category数据
        getCategoryDatas();
    }

    @Override
    public void clear() {
        if (mHomeView.get() != null) {
            mHomeView.clear();
        }
    }

    @Override
    public void getBannerData() {
        mHomeLocalRepertory.getBannerData(new BaseModel.OnRequestResponse<List<BannerData>>() {
            @Override
            public void onSuccess(List<BannerData> bannerDatas) {
                if (mHomeView.get().isActive()) {
                    mHomeView.get().setBannerData(bannerDatas);
                }
            }

            @Override
            public void onFailure(Throwable e) {
                if (mHomeView.get().isActive()) {
                    mHomeView.get().showToast("数据加载错误");
                }
            }
        });
    }

    @Override
    public void getCategoryDatas() {
        mHomeLocalRepertory.getCategoryData(new BaseModel.OnRequestResponse<List<CategoryData>>() {
            @Override
            public void onSuccess(List<CategoryData> categoryDatas) {
                if (mHomeView.get().isActive()) {
                    mHomeView.get().setCategoryData(categoryDatas);
                }
            }

            @Override
            public void onFailure(Throwable e) {
                if (mHomeView.get().isActive()) {
                    mHomeView.get().showToast("数据加载错误");
                }
            }
        });
    }
}
