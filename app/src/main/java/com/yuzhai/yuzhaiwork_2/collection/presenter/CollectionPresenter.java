package com.yuzhai.yuzhaiwork_2.collection.presenter;

import android.util.Log;

import com.yuzhai.yuzhaiwork_2.base.model.BaseModel;
import com.yuzhai.yuzhaiwork_2.collection.bean.CollectionData;
import com.yuzhai.yuzhaiwork_2.collection.contact.CollectionContact;
import com.yuzhai.yuzhaiwork_2.collection.model.CollectionModel;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by 35429 on 2017/5/29.
 */

public class CollectionPresenter implements CollectionContact.Presenter {
    private static final String TAG = "CollectionPresenter";
    private WeakReference<CollectionContact.View> mCollectionView;
    private CollectionModel mCollectionModel = new CollectionModel();

    public CollectionPresenter(CollectionContact.View collectionView) {
        this.mCollectionView = new WeakReference<>(collectionView);
        this.mCollectionView.get().setPresenter(this);
    }

    @Override
    public void start() {
        //获取数据
        getCollectionDataByLocal();
    }

    @Override
    public void clear() {
        if (mCollectionView.get() != null) {
            mCollectionView.clear();
        }
    }

    @Override
    public void getCollectionDataByLocal() {
        mCollectionModel.getCollectionDataByLocal(new BaseModel.OnRequestResponse<List<CollectionData>>() {
            @Override
            public void onSuccess(List<CollectionData> collectionDatas) {
                if (mCollectionView.get().isActive()) {
                    mCollectionView.get().setCollectionData(collectionDatas);
                }
            }

            @Override
            public void onFailure(Throwable e) {
                if (mCollectionView.get().isActive()) {
                    mCollectionView.get().showToast("数据加载失败");
                }
            }
        });
    }
}
