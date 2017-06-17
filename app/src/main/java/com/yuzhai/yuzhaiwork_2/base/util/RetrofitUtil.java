package com.yuzhai.yuzhaiwork_2.base.util;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.yuzhai.yuzhaiwork_2.base.http.AcceptInterceptor;
import com.yuzhai.yuzhaiwork_2.base.http.IPConfig;
import com.yuzhai.yuzhaiwork_2.base.http.ReadCookieInterceptor;
import com.yuzhai.yuzhaiwork_2.base.http.SaveCookieInterceptor;
import com.yuzhai.yuzhaiwork_2.category.request.ResumeService;
import com.yuzhai.yuzhaiwork_2.category.request.WorkService;
import com.yuzhai.yuzhaiwork_2.login_reg.request.AlterPswService;
import com.yuzhai.yuzhaiwork_2.login_reg.request.LoginService;
import com.yuzhai.yuzhaiwork_2.login_reg.request.RegService;
import com.yuzhai.yuzhaiwork_2.main.request.PublishService;
import com.yuzhai.yuzhaiwork_2.order_detail.request.ApplyOrderService;
import com.yuzhai.yuzhaiwork_2.order_detail.request.CancelAcceptedOrderService;
import com.yuzhai.yuzhaiwork_2.order_detail.request.CancelAppliedOrderService;
import com.yuzhai.yuzhaiwork_2.order_detail.request.CancelPublishedOrderService;
import com.yuzhai.yuzhaiwork_2.order_detail.request.OrderAcceptedDetailService;
import com.yuzhai.yuzhaiwork_2.order_detail.request.OrderAppliedDetailService;
import com.yuzhai.yuzhaiwork_2.order_detail.request.OrderPublishedDetailService;
import com.yuzhai.yuzhaiwork_2.order_detail.request.WorkDetailService;
import com.yuzhai.yuzhaiwork_2.personal_order.request.AcceptedOrderService;
import com.yuzhai.yuzhaiwork_2.personal_order.request.AppliedOrderService;
import com.yuzhai.yuzhaiwork_2.personal_order.request.PublishedOrderService;
import com.yuzhai.yuzhaiwork_2.resume.request.PublishResumeService;
import com.yuzhai.yuzhaiwork_2.user_data.request.AgreeAcceptOrderService;
import com.yuzhai.yuzhaiwork_2.user_data.request.UserDataService;
import com.yuzhai.yuzhaiwork_2.user_info.request.ExitLoginService;
import com.yuzhai.yuzhaiwork_2.user_info.request.ReNameService;
import com.yuzhai.yuzhaiwork_2.user_info.request.UploadAvaterService;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.R.attr.data;

/**
 * Created by 35429 on 2017/5/21.
 */

public class RetrofitUtil {
    private static final int DEFAULT_TIMEOUT = 5;
    private static RetrofitUtil retrofitUtil;
    private Retrofit retrofit;

    private RetrofitUtil() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new AcceptInterceptor())
                .addInterceptor(new ReadCookieInterceptor())
                .addInterceptor(new SaveCookieInterceptor())
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();

        retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(IPConfig.ACTION_PREFIX)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

    }

    public static RetrofitUtil getInstance() {
        if (retrofitUtil == null) {
            synchronized (RetrofitUtil.class) {
                retrofitUtil = new RetrofitUtil();
            }
        }
        return retrofitUtil;
    }

    /**
     * 设置content-type为text/plain
     */
    public static RequestBody MediaTypeTextPlain(String data) {
        return RequestBody.create(MediaType.parse("text/plain"), data);
    }

    /**
     * 设置content-type为image/jpeg
     */
    public static RequestBody MediaTypeImageJpeg(File file) {
        return RequestBody.create(MediaType.parse("image/jpeg"), file);
    }

    /**
     * 获取LoginService
     */
    public LoginService getLoginService() {
        return retrofit.create(LoginService.class);
    }

    /**
     * 获取RegService
     */
    public RegService getRegService() {
        return retrofit.create(RegService.class);
    }

    /**
     * 获取AlterPswService
     */
    public AlterPswService getAlterPswService() {
        return retrofit.create(AlterPswService.class);
    }

    /**
     * 获取PublishedOrderService
     */
    public PublishedOrderService getPublishedOrderService() {
        return retrofit.create(PublishedOrderService.class);
    }

    /**
     * 获取AppliedOrderService
     */
    public AppliedOrderService getAppliedOrderService() {
        return retrofit.create(AppliedOrderService.class);
    }

    /**
     * 获取AcceptedOrderService
     */
    public AcceptedOrderService getAcceptedOrderService() {
        return retrofit.create(AcceptedOrderService.class);
    }

    /**
     * 获取WorkDataService
     */
    public WorkService getWorkDataService() {
        return retrofit.create(WorkService.class);
    }

    /**
     * 获取ExitLoginService
     */
    public ExitLoginService getExitLoginService() {
        return retrofit.create(ExitLoginService.class);
    }

    /**
     * 获取ResumeService
     */
    public ResumeService getResumeService() {
        return retrofit.create(ResumeService.class);
    }

    /**
     * 获取PublishResumeService
     */
    public PublishResumeService getPublishResumeService() {
        return retrofit.create(PublishResumeService.class);
    }

    /**
     * 获取UploadAvaterService
     */
    public UploadAvaterService getUploadAvaterService() {
        return retrofit.create(UploadAvaterService.class);
    }

    /**
     * 获取ReNameService
     */
    public ReNameService getReNameService() {
        return retrofit.create(ReNameService.class);
    }

    /**
     * 获取PublishService
     */
    public PublishService getPublishService() {
        return retrofit.create(PublishService.class);
    }

    /**
     * 获取WorkDetailService
     */
    public WorkDetailService getWorkDetailService() {
        return retrofit.create(WorkDetailService.class);
    }

    /**
     * 获取ApplyOrderService
     */
    public ApplyOrderService getApplyOrderService() {
        return retrofit.create(ApplyOrderService.class);
    }

    /**
     * 获取OrderPublishedDetailService
     */
    public OrderPublishedDetailService getOrderPublishedDetailService() {
        return retrofit.create(OrderPublishedDetailService.class);
    }

    /**
     * 获取OrderAcceptedDetailService
     */
    public OrderAcceptedDetailService getOrderAcceptedDetailService() {
        return retrofit.create(OrderAcceptedDetailService.class);
    }

    /**
     * 获取OrderAppliedDetailService
     */
    public OrderAppliedDetailService getOrderAppliedDetailService() {
        return retrofit.create(OrderAppliedDetailService.class);
    }

    /**
     * 获取CancelPublishedOrderService
     */
    public CancelPublishedOrderService getCancelPublishedOrderService() {
        return retrofit.create(CancelPublishedOrderService.class);
    }

    /**
     * 获取CancelAppliedOrderService
     */
    public CancelAppliedOrderService getCancelAppliedOrderService() {
        return retrofit.create(CancelAppliedOrderService.class);
    }

    /**
     * 获取CancelAcceptedOrderService
     */
    public CancelAcceptedOrderService getCancelAcceptedOrderService() {
        return retrofit.create(CancelAcceptedOrderService.class);
    }

    /**
     * 获取UserDataService
     */
    public UserDataService getUserDataService() {
        return retrofit.create(UserDataService.class);
    }

    /**
     * 获取AgreeAcceptOrderService
     */
    public AgreeAcceptOrderService getAgreeAcceptOrderService() {
        return retrofit.create(AgreeAcceptOrderService.class);
    }
}
