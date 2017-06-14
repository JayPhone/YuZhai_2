package com.yuzhai.yuzhaiwork_2.main.contact;

import com.yuzhai.yuzhaiwork_2.base.model.BaseModel;
import com.yuzhai.yuzhaiwork_2.base.presenter.BasePresenter;
import com.yuzhai.yuzhaiwork_2.base.view.BaseView;
import com.yuzhai.yuzhaiwork_2.main.bean.BannerData;
import com.yuzhai.yuzhaiwork_2.main.bean.CategoryData;

import java.util.List;
import java.util.Map;

/**
 * Created by 35429 on 2017/5/23.
 */

public interface HomeContact {
    interface View extends BaseView<Presenter> {

        void startLoopBannber();

        void stopLoopBanner();

        void setBannerData(List<BannerData> bannerDatas);

        void setCategoryData(List<CategoryData> categoryDatas);
    }

    interface Presenter extends BasePresenter {
        void getBannerData();

        void getCategoryDatas();
    }

}
