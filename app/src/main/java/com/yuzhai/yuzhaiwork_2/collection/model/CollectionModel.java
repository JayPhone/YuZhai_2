package com.yuzhai.yuzhaiwork_2.collection.model;

import android.util.Log;

import com.yuzhai.yuzhaiwork_2.R;
import com.yuzhai.yuzhaiwork_2.base.model.BaseModel;
import com.yuzhai.yuzhaiwork_2.collection.bean.CollectionData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by 35429 on 2017/5/29.
 */

public class CollectionModel implements BaseModel {
    private static final String TAG = "CollectionModel";
    private List<CollectionData> mCollectionData;
    private List<String> mTitle;
    private List<String> mDate;
    private List<Integer> mImage;
    private List<String> mType;

    public void getCollectionDataByLocal(OnRequestResponse<List<CollectionData>> onRequestResponse) {
        if (mTitle == null) {
            mTitle = new ArrayList<>();
            mTitle.add("帮我做一个很厉害的APP，记住，是很厉害的，普通厉害的不要!!");
            mTitle.add("帮我做一个很厉害的APP，记住，是很厉害的，普通厉害的不要!!");
            mTitle.add("帮我做一个很厉害的APP，记住，是很厉害的，普通厉害的不要!!");
            mTitle.add("帮我做一个很厉害的APP，记住，是很厉害的，普通厉害的不要!!");
            mTitle.add("帮我做一个很厉害的APP，记住，是很厉害的，普通厉害的不要!!");
            mTitle.add("帮我做一个很厉害的APP，记住，是很厉害的，普通厉害的不要!!");
            mTitle.add("帮我做一个很厉害的APP，记住，是很厉害的，普通厉害的不要!!");
        }

        if (mDate == null) {
            mDate = new ArrayList<>();
            mDate.add("2016-07-14");
            mDate.add("2016-07-14");
            mDate.add("2016-07-14");
            mDate.add("2016-07-14");
            mDate.add("2016-07-14");
            mDate.add("2016-07-14");
            mDate.add("2016-07-14");
        }

        if (mImage == null) {
            mImage = new ArrayList<>();
            mImage.add(R.drawable.test1);
            mImage.add(R.drawable.test2);
            mImage.add(R.drawable.test3);
            mImage.add(R.drawable.test4);
            mImage.add(R.drawable.test1);
            mImage.add(R.drawable.test2);
            mImage.add(R.drawable.test3);
        }

        if (mType == null) {
            mType = new ArrayList<>();
            mType.add("软件IT");
            mType.add("音乐制作");
            mType.add("平面设计");
            mType.add("视频拍摄");
            mType.add("游戏研发");
            mType.add("文案撰写");
            mType.add("金融会计");
        }

        if (mCollectionData == null) {
            mCollectionData = new ArrayList<>();
            for (int i = 0; i < mTitle.size(); i++) {
                mCollectionData.add(new CollectionData(mTitle.get(i), mType.get(i), mImage.get(i), mDate.get(i)));
            }
            onRequestResponse.onSuccess(mCollectionData);
        }
    }

}
