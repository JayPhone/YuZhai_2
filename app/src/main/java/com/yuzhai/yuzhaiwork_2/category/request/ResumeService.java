package com.yuzhai.yuzhaiwork_2.category.request;

import com.yuzhai.yuzhaiwork_2.category.bean.ResumeDatas;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by 35429 on 2017/6/9.
 */

public interface ResumeService {
    @FormUrlEncoded
    @POST("resumes")
    Observable<ResumeDatas> getResumeData(@Field("type") String categoryType,
                                          @Field("first") String isFirst);
}
