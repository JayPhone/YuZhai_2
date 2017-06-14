package com.yuzhai.yuzhaiwork_2.user_info.request;

import com.yuzhai.yuzhaiwork_2.user_info.bean.UploadAvaterResponse;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by 35429 on 2017/6/11.
 */

public interface UploadAvaterService {
    @Multipart
    @POST("changeavatar")
    Observable<UploadAvaterResponse> uploadAvater(@Part MultipartBody.Part image);
}
