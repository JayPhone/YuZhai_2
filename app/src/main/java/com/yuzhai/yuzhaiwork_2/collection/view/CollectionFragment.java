package com.yuzhai.yuzhaiwork_2.collection.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yuzhai.yuzhaiwork_2.R;
import com.yuzhai.yuzhaiwork_2.collection.adapter.CollectionRVAdapter;
import com.yuzhai.yuzhaiwork_2.collection.bean.CollectionData;
import com.yuzhai.yuzhaiwork_2.collection.contact.CollectionContact;

import java.util.List;

/**
 * Created by 35429 on 2017/5/29.
 */

public class CollectionFragment extends Fragment implements CollectionContact.View {
    private static final String TAG = "CollectionFragment";
    private CollectionContact.Presenter mPresenter;
    private RecyclerView mCollectionRV;

    public static CollectionFragment newInstance() {
        Bundle args = new Bundle();
        CollectionFragment fragment = new CollectionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_collection, container, false);
        if (root != null) {
            mCollectionRV = (RecyclerView) root.findViewById(R.id.collection_recyclerView);
            mCollectionRV.setLayoutManager(new LinearLayoutManager(getActivity()));
            mCollectionRV.setItemAnimator(new DefaultItemAnimator());
        }
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void setPresenter(CollectionContact.Presenter presenter) {
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
    public void setCollectionData(List<CollectionData> collectionData) {
        Log.d(TAG, collectionData.toString());
        mCollectionRV.setAdapter(new CollectionRVAdapter(collectionData));
    }
}
