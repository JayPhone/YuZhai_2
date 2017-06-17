package com.yuzhai.yuzhaiwork_2.notification.bean;

/**
 * Created by 35429 on 2017/6/17.
 */

public class DeleteNotificationResponse {
    private int deleteCount;

    public DeleteNotificationResponse(int deleteCount) {
        this.deleteCount = deleteCount;
    }

    public int getDeleteCount() {
        return deleteCount;
    }

    public void setDeleteCount(int deleteCount) {
        this.deleteCount = deleteCount;
    }

    @Override
    public String toString() {
        return "DeleteNotificationResponse{" +
                "deleteCount=" + deleteCount +
                '}';
    }
}
