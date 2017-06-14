package com.yuzhai.yuzhaiwork_2.base.model;

import com.yuzhai.yuzhaiwork_2.login_reg.request.LoginResponse;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by 35429 on 2017/5/23.
 */

public interface BaseModel {
    /**
     * 请求回调接口
     */
    interface OnRequestResponse<T> {
        void onSuccess(T t);

        void onFailure(Throwable e);
    }
}
