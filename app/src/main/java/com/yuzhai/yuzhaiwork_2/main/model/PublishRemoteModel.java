package com.yuzhai.yuzhaiwork_2.main.model;


import android.util.Log;

import com.yuzhai.yuzhaiwork_2.base.model.BaseModel;
import com.yuzhai.yuzhaiwork_2.base.util.RetrofitUtil;
import com.yuzhai.yuzhaiwork_2.main.bean.PublishResponse;
import com.yuzhai.yuzhaiwork_2.main.request.PublishRequest;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by 35429 on 2017/6/10.
 */

public class PublishRemoteModel implements IPublishModel {
    private static final String TAG = "PublishRemoteModel";
    public static final String DATA = "data";
    private List<Map<String, String>> mTypeDatas = null;
    private List<Map<String, String>> mLimitDatas = null;
    private String[] typeTexts = new String[]{"请选择项目类型", "软件IT", "音乐制作", "平面设计", "视频拍摄", "游戏研发", "文案撰写", "金融会计"};
    private String[] limitTexts = new String[]{"请选择预期时长", "7天", "15天", "30天", "365天"};

    @Override
    public void getTypeSpinnerData(OnRequestResponse<List<Map<String, String>>> onRequestResponse) {
        if (mTypeDatas == null) {
            mTypeDatas = new ArrayList<>();
            Map<String, String> sexMap;
            for (String sex : typeTexts) {
                sexMap = new HashMap<>();
                sexMap.put(DATA, sex);
                mTypeDatas.add(sexMap);
            }
        }
        onRequestResponse.onSuccess(mTypeDatas);
    }

    @Override
    public void getLimitSpinnerData(OnRequestResponse<List<Map<String, String>>> onRequestResponse) {
        if (mLimitDatas == null) {
            mLimitDatas = new ArrayList<>();
            Map<String, String> sexMap;
            for (String sex : limitTexts) {
                sexMap = new HashMap<>();
                sexMap.put(DATA, sex);
                mLimitDatas.add(sexMap);
            }
        }
        onRequestResponse.onSuccess(mLimitDatas);
    }

    @Override
    public void sendPublishOrderRequest(PublishRequest publishRequest, final OnRequestResponse<PublishResponse> onRequestResponse) {
        Map<String, RequestBody> files = new HashMap<>();
        for (int i = 0; i < publishRequest.getFiles().size(); i++) {
            File file = (File) publishRequest.getFiles().get(i);
            files.put("files" + "\"; filename=\"" + file.getName(),
                    RetrofitUtil.MediaTypeImageJpeg(file));
        }
        RetrofitUtil.getInstance()
                .getPublishService()
                .publishOrder(RetrofitUtil.MediaTypeTextPlain(publishRequest.getTitle()),
                        RetrofitUtil.MediaTypeTextPlain(publishRequest.getDescription()),
                        RetrofitUtil.MediaTypeTextPlain(publishRequest.getType()),
                        RetrofitUtil.MediaTypeTextPlain(publishRequest.getDeadline()),
                        RetrofitUtil.MediaTypeTextPlain(publishRequest.getTel()),
                        RetrofitUtil.MediaTypeTextPlain(publishRequest.getReward()),
                        files)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<PublishResponse>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull PublishResponse publishResponse) {
                onRequestResponse.onSuccess(publishResponse);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                onRequestResponse.onFailure(e);
            }

            @Override
            public void onComplete() {

            }
        });
    }
}
