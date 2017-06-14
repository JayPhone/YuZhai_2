package com.yuzhai.yuzhaiwork_2.login_reg.request;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by 35429 on 2017/5/22.
 */

public interface RegService {
    @FormUrlEncoded
    @POST("regisuser")
    Observable<RegResponse> register(@Field("userPhone") String userPhone,
                                     @Field("userPsw") String userPsw);
}
