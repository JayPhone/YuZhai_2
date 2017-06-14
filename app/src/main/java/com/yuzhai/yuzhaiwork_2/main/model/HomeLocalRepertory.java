package com.yuzhai.yuzhaiwork_2.main.model;

import com.yuzhai.yuzhaiwork_2.R;
import com.yuzhai.yuzhaiwork_2.base.model.BaseModel;
import com.yuzhai.yuzhaiwork_2.main.bean.BannerData;
import com.yuzhai.yuzhaiwork_2.main.bean.CategoryData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 35429 on 2017/5/28.
 */

public class HomeLocalRepertory implements IHomeModel {
    private List<BannerData> mBannerDatas;
    private List<CategoryData> mCategoryDatas;
    private List<Integer> mCategoryImages;
    private List<String> mCategoryTexts;

    /**
     * 返回广告栏数据
     */
    public void getBannerData(OnRequestResponse<List<BannerData>> onRequestResponse) {
        if (mBannerDatas == null) {
            mBannerDatas = new ArrayList<>();
            mBannerDatas.add(new BannerData(R.drawable.test1));
            mBannerDatas.add(new BannerData(R.drawable.test2));
            mBannerDatas.add(new BannerData(R.drawable.test3));
            mBannerDatas.add(new BannerData(R.drawable.test4));
        }
        onRequestResponse.onSuccess(mBannerDatas);
    }

    /**
     * 返回类别数据
     */
    public void getCategoryData(OnRequestResponse<List<CategoryData>> onRequestResponse) {
        if (mCategoryImages == null) {
            mCategoryImages = new ArrayList<>();
            mCategoryImages.add(R.drawable.it);
            mCategoryImages.add(R.drawable.music);
            mCategoryImages.add(R.drawable.design);
            mCategoryImages.add(R.drawable.movie);
            mCategoryImages.add(R.drawable.game);
            mCategoryImages.add(R.drawable.write);
            mCategoryImages.add(R.drawable.calculate);
            mCategoryImages.add(R.drawable.it);
            mCategoryImages.add(R.drawable.music);
            mCategoryImages.add(R.drawable.design);
            mCategoryImages.add(R.drawable.movie);
            mCategoryImages.add(R.drawable.game);
            mCategoryImages.add(R.drawable.write);
            mCategoryImages.add(R.drawable.calculate);
            mCategoryImages.add(R.drawable.plus);
        }

        if (mCategoryTexts == null) {
            mCategoryTexts = new ArrayList<>();
            mCategoryTexts.add("软件IT");
            mCategoryTexts.add("音乐制作");
            mCategoryTexts.add("平面设计");
            mCategoryTexts.add("视频拍摄");
            mCategoryTexts.add("游戏研发");
            mCategoryTexts.add("文案撰写");
            mCategoryTexts.add("金融会计");
            mCategoryTexts.add("软件IT");
            mCategoryTexts.add("音乐制作");
            mCategoryTexts.add("平面设计");
            mCategoryTexts.add("视频拍摄");
            mCategoryTexts.add("游戏研发");
            mCategoryTexts.add("文案撰写");
            mCategoryTexts.add("金融会计");
            mCategoryTexts.add("添加类别");
        }

        if (mCategoryDatas == null) {
            mCategoryDatas = new ArrayList<>();
            for (int i = 0; i < mCategoryImages.size(); i++) {
                mCategoryDatas.add(new CategoryData(mCategoryImages.get(i), mCategoryTexts.get(i)));
            }
        }

        onRequestResponse.onSuccess(mCategoryDatas);
    }
}
