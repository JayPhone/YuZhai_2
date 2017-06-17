package com.yuzhai.yuzhaiwork_2.notification.contact;

import com.yuzhai.yuzhaiwork_2.base.presenter.BasePresenter;
import com.yuzhai.yuzhaiwork_2.base.view.BaseView;
import com.yuzhai.yuzhaiwork_2.notification.bean.NotificationDB;
import com.yuzhai.yuzhaiwork_2.notification.request.GetNotificationRequest;

import java.util.List;

/**
 * Created by 35429 on 2017/6/17.
 */

public interface NotificationCenterContact {
    interface View extends BaseView<Presenter> {
        void setNotificationData(List<NotificationDB> notificationDBs);

        void showProgressDialog(String msg);

        void hideProgressDialog();

        void showDeleteAllNotificationDialog();

        void dimissLoading();

        void startLoading();

        void deleteAll();
    }

    interface Presenter extends BasePresenter {
        void getNotificationData(GetNotificationRequest getNotificationRequest);

        void deleteAllNotification();
    }
}
