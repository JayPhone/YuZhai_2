package com.yuzhai.yuzhaiwork_2.login_reg.request;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by 35429 on 2017/5/24.
 */

public interface AlterPswService {
    @FormUrlEncoded
    @POST("resetpsw")
    Observable<AlterPswResponse> alterPsw(@Field("userPhone") String userPhone,
                                          @Field("psw1") String newPsw,
                                          @Field("psw2") String cfmPsw);
}
