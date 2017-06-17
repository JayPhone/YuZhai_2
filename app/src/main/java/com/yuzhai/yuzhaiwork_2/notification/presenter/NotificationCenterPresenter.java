package com.yuzhai.yuzhaiwork_2.notification.presenter;

import com.yuzhai.yuzhaiwork_2.base.model.BaseModel;
import com.yuzhai.yuzhaiwork_2.notification.bean.DeleteNotificationResponse;
import com.yuzhai.yuzhaiwork_2.notification.bean.NotificationDB;
import com.yuzhai.yuzhaiwork_2.notification.contact.NotificationCenterContact;
import com.yuzhai.yuzhaiwork_2.notification.model.INotificationCenterModel;
import com.yuzhai.yuzhaiwork_2.notification.model.NotificationCenterRepertory;
import com.yuzhai.yuzhaiwork_2.notification.request.GetNotificationRequest;
import com.yuzhai.yuzhaiwork_2.notification.view.NotificationCenterFragment;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by 35429 on 2017/6/17.
 */

public class NotificationCenterPresenter implements NotificationCenterContact.Presenter {
    private WeakReference<NotificationCenterContact.View> mNotificationCenterView;
    private INotificationCenterModel mNotificationCenterModel = new NotificationCenterRepertory();

    public NotificationCenterPresenter(NotificationCenterFragment notificationCenterFragment) {
        mNotificationCenterView = new WeakReference<NotificationCenterContact.View>(notificationCenterFragment);
        mNotificationCenterView.get().setPresenter(this);
    }

    @Override
    public void start() {
        getNotificationData(new GetNotificationRequest(0));
    }

    @Override
    public void clear() {
        if (mNotificationCenterView.get() != null) {
            mNotificationCenterView.clear();
        }
    }

    @Override
    public void getNotificationData(GetNotificationRequest getNotificationRequest) {
        mNotificationCenterView.get().startLoading();
        mNotificationCenterModel.getNotificationData(getNotificationRequest,
                new BaseModel.OnRequestResponse<List<NotificationDB>>() {
                    @Override
                    public void onSuccess(List<NotificationDB> notificationDBs) {
                        if (mNotificationCenterView.get().isActive()) {
                            mNotificationCenterView.get().setNotificationData(notificationDBs);
                            mNotificationCenterView.get().dimissLoading();
                        }
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        if (mNotificationCenterView.get().isActive()) {
                            mNotificationCenterView.get().showToast("数据加载出错");
                            mNotificationCenterView.get().dimissLoading();
                        }
                    }
                });
    }

    @Override
    public void deleteAllNotification() {
        mNotificationCenterModel.deleteAllNotifications(new BaseModel.OnRequestResponse<DeleteNotificationResponse>() {
            @Override
            public void onSuccess(DeleteNotificationResponse deleteNotificationResponse) {
                if (mNotificationCenterView.get().isActive()) {
                    if (deleteNotificationResponse.getDeleteCount() > 0) {
                        mNotificationCenterView.get().showToast("数据清除成功");
                        //清空界面
                        mNotificationCenterView.get().deleteAll();
                    }
                }
            }

            @Override
            public void onFailure(Throwable e) {
                if (mNotificationCenterView.get().isActive()) {
                    mNotificationCenterView.get().showToast("数据清空出错");
                }
            }
        });
    }
}
