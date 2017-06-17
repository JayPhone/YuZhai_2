package com.yuzhai.yuzhaiwork_2.notification.model;

import com.yuzhai.yuzhaiwork_2.notification.bean.DeleteNotificationResponse;
import com.yuzhai.yuzhaiwork_2.notification.bean.NotificationDB;
import com.yuzhai.yuzhaiwork_2.notification.request.GetNotificationRequest;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by 35429 on 2017/6/17.
 */

public class NotificationCenterRepertory implements INotificationCenterModel {
    @Override
    public void getNotificationData(GetNotificationRequest getNotificationRequest,
                                    OnRequestResponse<List<NotificationDB>> onRequestResponse) {
        if (getNotificationRequest.getLimit() == 0) {
            onRequestResponse.onSuccess(DataSupport.findAll(NotificationDB.class));
        } else {
            //返回更新的数据
            onRequestResponse.onSuccess(DataSupport
                    .limit(getNotificationRequest.getLimit())
                    .offset(DataSupport.findAll(NotificationDB.class).size() - 1)
                    .find(NotificationDB.class));
        }
    }

    @Override
    public void deleteAllNotifications(OnRequestResponse<DeleteNotificationResponse> onRequestResponse) {
        DataSupport.deleteAll(NotificationDB.class);
    }
}
