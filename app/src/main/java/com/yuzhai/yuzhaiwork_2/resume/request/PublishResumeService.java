package com.yuzhai.yuzhaiwork_2.resume.request;

import com.yuzhai.yuzhaiwork_2.resume.bean.PublishResumeResponse;
import com.yuzhai.yuzhaiwork_2.resume.bean.QueryResumeResponse;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by 35429 on 2017/6/10.
 */

public interface PublishResumeService {
    @FormUrlEncoded
    @POST("sendresume")
    Observable<PublishResumeResponse> publishResume(@Field("name") String name,
                                                    @Field("sex") String sex,
                                                    @Field("module") String module,
                                                    @Field("education") String education,
                                                    @Field("contactNumber") String contactNum,
                                                    @Field("educationExperience") String educationExperience,
                                                    @Field("skill") String skill,
                                                    @Field("workExperience") String workExperience,
                                                    @Field("selfEvaluation") String selfEvaluation);

    @GET("selfdetailedresume")
    Observable<QueryResumeResponse> queryResume();
}
