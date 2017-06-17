package com.yuzhai.yuzhaiwork_2.notification.model;

import com.yuzhai.yuzhaiwork_2.base.model.BaseModel;
import com.yuzhai.yuzhaiwork_2.notification.bean.DeleteNotificationResponse;
import com.yuzhai.yuzhaiwork_2.notification.bean.NotificationDB;
import com.yuzhai.yuzhaiwork_2.notification.request.GetNotificationRequest;

import java.util.List;

/**
 * Created by 35429 on 2017/6/17.
 */

public interface INotificationCenterModel extends BaseModel {
    void getNotificationData(GetNotificationRequest getNotificationRequest, OnRequestResponse<List<NotificationDB>> onRequestResponse);

    void deleteAllNotifications(OnRequestResponse<DeleteNotificationResponse> onRequestResponse);
}
