package com.yuzhai.yuzhaiwork_2.collection.contact;

import com.yuzhai.yuzhaiwork_2.base.model.BaseModel;
import com.yuzhai.yuzhaiwork_2.base.presenter.BasePresenter;
import com.yuzhai.yuzhaiwork_2.base.view.BaseView;
import com.yuzhai.yuzhaiwork_2.collection.bean.CollectionData;

import java.util.List;
import java.util.Map;

/**
 * Created by 35429 on 2017/5/28.
 */

public interface CollectionContact {
    interface View extends BaseView<Presenter> {
        void setCollectionData(List<CollectionData> collectionData);
    }

    interface Presenter extends BasePresenter {
        void getCollectionDataByLocal();
    }
}
