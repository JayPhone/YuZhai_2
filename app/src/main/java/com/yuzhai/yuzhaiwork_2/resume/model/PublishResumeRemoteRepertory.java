package com.yuzhai.yuzhaiwork_2.resume.model;

import android.util.Log;
import android.widget.SimpleAdapter;

import com.yuzhai.yuzhaiwork_2.base.global.CustomApplication;
import com.yuzhai.yuzhaiwork_2.base.model.BaseModel;
import com.yuzhai.yuzhaiwork_2.base.util.RegexUtil;
import com.yuzhai.yuzhaiwork_2.base.util.RetrofitUtil;
import com.yuzhai.yuzhaiwork_2.resume.bean.PublishResumeResponse;
import com.yuzhai.yuzhaiwork_2.resume.bean.QueryResumeResponse;
import com.yuzhai.yuzhaiwork_2.resume.request.PublishResumeRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

/**
 * Created by 35429 on 2017/6/9.
 */

public class PublishResumeRemoteRepertory implements IPublishResumeModel {
    private static final String TAG = "PublishResumeRemoteRepe";
    public static final String DATA = "data";
    private String[] sexTexts = new String[]{"请选择您的性别", "男性", "女性"};
    private String[] typeTexts = new String[]{"请选择投放板块", "软件IT", "音乐制作", "平面设计", "视频拍摄", "游戏研发", "文案撰写", "金融会计"};
    private String[] educationTexts = new String[]{"请选择您的学历", "初中", "高中", "专科", "本科", "硕士", "博士"};
    private List<Map<String, String>> mSexDatas = null;
    private List<Map<String, String>> mTypeDatas = null;
    private List<Map<String, String>> mEducationDatas = null;

    @Override
    public void getSexSpinnerData(OnRequestResponse<List<Map<String, String>>> onRequestResponse) {
        if (mSexDatas == null) {
            mSexDatas = new ArrayList<>();
            Map<String, String> sexMap;
            for (String sex : sexTexts) {
                sexMap = new HashMap<>();
                sexMap.put(DATA, sex);
                mSexDatas.add(sexMap);
            }
        }
        onRequestResponse.onSuccess(mSexDatas);
    }

    @Override
    public void getTypeSpinnerData(OnRequestResponse<List<Map<String, String>>> onRequestResponse) {
        if (mTypeDatas == null) {
            mTypeDatas = new ArrayList<>();
            Map<String, String> typeMap;
            for (String type : typeTexts) {
                typeMap = new HashMap<>();
                typeMap.put(DATA, type);
                mTypeDatas.add(typeMap);
            }
        }
        onRequestResponse.onSuccess(mTypeDatas);
    }

    @Override
    public void getEducationSpinnerData(OnRequestResponse<List<Map<String, String>>> onRequestResponse) {
        if (mEducationDatas == null) {
            mEducationDatas = new ArrayList<>();
            Map<String, String> educationMap;
            for (String education : educationTexts) {
                educationMap = new HashMap<>();
                educationMap.put(DATA, education);
                mEducationDatas.add(educationMap);
            }
        }
        onRequestResponse.onSuccess(mEducationDatas);
    }

    @Override
    public void sendPublishResumeRequest(PublishResumeRequest publishResumeRequest, final OnRequestResponse<PublishResumeResponse> onRequestResponse) {
        RetrofitUtil.getInstance()
                .getPublishResumeService()
                .publishResume(publishResumeRequest.getName(),
                        publishResumeRequest.getSex(),
                        publishResumeRequest.getType(),
                        publishResumeRequest.getEducation(),
                        publishResumeRequest.getTel(),
                        publishResumeRequest.getEducationalExperience(),
                        publishResumeRequest.getSkill(),
                        publishResumeRequest.getWorkExperience(),
                        publishResumeRequest.getSelfEvaluation())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<PublishResumeResponse>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull PublishResumeResponse publishResumeResponse) {
                Log.d(TAG, publishResumeResponse.toString());
                onRequestResponse.onSuccess(publishResumeResponse);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG, e.getMessage());
                onRequestResponse.onFailure(e);
            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void sendQueryResumeRequest(final OnRequestResponse<QueryResumeResponse> onRequestResponse) {
        RetrofitUtil.getInstance()
                .getPublishResumeService()
                .queryResume()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<QueryResumeResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull QueryResumeResponse queryResumeResponse) {
                        Log.d(TAG, queryResumeResponse.toString());
                        onRequestResponse.onSuccess(queryResumeResponse);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, e.getMessage());
                        onRequestResponse.onFailure(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
