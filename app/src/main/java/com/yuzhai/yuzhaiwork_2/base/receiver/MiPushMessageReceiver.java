package com.yuzhai.yuzhaiwork_2.base.receiver;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;
import com.xiaomi.mipush.sdk.ErrorCode;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.xiaomi.mipush.sdk.MiPushCommandMessage;
import com.xiaomi.mipush.sdk.MiPushMessage;
import com.xiaomi.mipush.sdk.PushMessageReceiver;
import com.yuzhai.yuzhaiwork_2.notification.bean.NotificationDB;
import com.yuzhai.yuzhaiwork_2.notification.bean.NotificationReceiver;
import com.yuzhai.yuzhaiwork_2.order_detail.view.OrderAcceptedDetailActivity;
import com.yuzhai.yuzhaiwork_2.order_detail.view.OrderAppliedDetailActivity;
import com.yuzhai.yuzhaiwork_2.order_detail.view.OrderPublishedDetailActivity;
import com.yuzhai.yuzhaiwork_2.personal_order.view.OrderAcceptedFragment;
import com.yuzhai.yuzhaiwork_2.personal_order.view.OrderPublishedFragment;

public class MiPushMessageReceiver extends PushMessageReceiver {
    private final static String PUBLISH = "publish";
    private final static String APPLY = "apply";
    private final static String RECEIVE = "receive";
    private String mRegId;
    private long mResultCode = -1;
    private String mReason;
    private String mCommand;
    private String mMessage;
    private String mTopic;
    private String mAlias;
    private String mUserAccount;
    private String mStartTime;
    private String mEndTime;

    //onReceivePassThroughMessage 方法用来接收服务器向客户端发送的透传消息。
    public void onReceivePassThroughMessage(Context context, MiPushMessage message) {
        //打印消息方便测试
        Log.i("onReceivePassThrough", "透传消息到达了");
        Log.i("onReceivePassThrough", "透传消息是" + message.toString());
    }

    //方法用来接收服务器向客户端发送的通知消息，这个回调方法是在通知消息到达客户端时触发。
    //另外应用在前台时不弹出通知的通知消息到达客户端也会触发这个回调函数。
    @Override
    public void onNotificationMessageArrived(Context context, MiPushMessage message) {
        //打印消息方便测试
        Log.i("onNotificationArrived", "通知消息到达了");
        Log.i("onNotificationArrived", "通知消息是" + message.toString());

        //保存通知消息到数据库
        Gson gson = new Gson();
        NotificationReceiver notificationReceiver = gson.fromJson(message.getContent(), NotificationReceiver.class);
        NotificationDB notificationDB = new NotificationDB();
        notificationDB.setTitle(message.getTitle());
        notificationDB.setDescription(message.getDescription());
        notificationDB.setType(notificationReceiver.getType());
        notificationDB.setDate(notificationReceiver.getDate());
        notificationDB.setOrder_id(notificationReceiver.getOrder_id());
        notificationDB.save();
    }

    //onNotificationMessageClicked 方法用来接收服务器向客户端发送的通知消息，
    //这个回调方法会在用户手动点击通知后触发。
    @Override
    public void onNotificationMessageClicked(Context context, MiPushMessage message) {
        //打印消息方便测试
        Log.i("onNotificationClicked", "用户点击了通知消息");
        Log.i("onNotificationClicked", "通知消息是" + message.toString());
        Log.i("onNotificationClicked", "点击后,会进入应用");

        //打开对应的界面
        Gson gson = new Gson();
        NotificationReceiver notificationReceiver = gson.fromJson(message.getContent(), NotificationReceiver.class);
        switch (notificationReceiver.getType()) {
            case PUBLISH: //已发布详细页面
                Intent orderPublishedDetail = new Intent(context, OrderPublishedDetailActivity.class);
                orderPublishedDetail.putExtra(OrderPublishedFragment.ORDER_ID, notificationReceiver.getOrder_id());
                orderPublishedDetail.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(orderPublishedDetail);
                break;
            case RECEIVE: //已接受详细页面
                Intent orderAcceptedDetail = new Intent(context, OrderAcceptedDetailActivity.class);
                orderAcceptedDetail.putExtra(OrderAcceptedFragment.ORDER_ID, notificationReceiver.getOrder_id());
                orderAcceptedDetail.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(orderAcceptedDetail);
                break;
            case APPLY: //已申请详细页面
                Intent orderAppliedDetail = new Intent(context, OrderAppliedDetailActivity.class);
                orderAppliedDetail.putExtra(OrderAcceptedFragment.ORDER_ID, notificationReceiver.getOrder_id());
                orderAppliedDetail.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(orderAppliedDetail);
                break;
        }
    }

    //onCommandResult 方法用来接收客户端向服务器发送命令后的响应结果。
    @Override
    public void onCommandResult(Context context, MiPushCommandMessage message) {
        String command = message.getCommand();
        Log.i("onCommandResult", "command message:" + command);

        if (MiPushClient.COMMAND_REGISTER.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {

                //打印信息便于测试注册成功与否
                Log.i("onCommandResult", "注册成功");

            } else {
                Log.i("onCommandResult", "注册失败");
            }
        }

    }

    //onReceiveRegisterResult 方法用来接收客户端向服务器发送注册命令后的响应结果。
    @Override
    public void onReceiveRegisterResult(Context context, MiPushCommandMessage message) {
        String command = message.getCommand();
        Log.i("onReceiveRegisterResult", "command message:" + command);

        if (MiPushClient.COMMAND_REGISTER.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                Log.i("regId", message.getCommandArguments().get(0));
                //打印日志：注册成功
                Log.i("onReceiveRegisterResult", "注册成功");
            } else {
                //打印日志：注册失败
                Log.i("onReceiveRegisterResult", "注册失败");
            }
        } else {
            System.out.println("其他情况" + message.getReason());
        }
    }

}
