package com.yuzhai.yuzhaiwork_2.collection.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.yuzhai.yuzhaiwork_2.R;
import com.yuzhai.yuzhaiwork_2.base.util.ActivityUtil;
import com.yuzhai.yuzhaiwork_2.collection.presenter.CollectionPresenter;

/**
 * Created by 35429 on 2017/5/28.
 */

public class CollectionActivity extends AppCompatActivity {
    private CollectionPresenter mCollectionPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);

        Toolbar toolbar = (Toolbar) findViewById(R.id.collection_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        CollectionFragment collectionFragment = CollectionFragment.newInstance();
        ActivityUtil.addFragment(getSupportFragmentManager(), collectionFragment, R.id.collection_content);

        //初始化Presenter
        if (mCollectionPresenter == null) {
            mCollectionPresenter = new CollectionPresenter(collectionFragment);
        }
    }

    @Override
    protected void onDestroy() {
        if (mCollectionPresenter != null) {
            mCollectionPresenter.clear();
        }
        super.onDestroy();
    }
}
