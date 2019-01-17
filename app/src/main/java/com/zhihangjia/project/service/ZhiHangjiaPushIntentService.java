package com.zhihangjia.project.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.google.gson.Gson;
import com.igexin.sdk.GTIntentService;
import com.igexin.sdk.PushManager;
import com.igexin.sdk.message.GTCmdMessage;
import com.igexin.sdk.message.GTNotificationMessage;
import com.igexin.sdk.message.GTTransmitMessage;
import com.nmssdmf.commonlib.bean.PushMessage;
import com.nmssdmf.commonlib.util.JLog;
import com.zhihangjia.project.R;
import com.zhihangjia.project.receiver.GeTuiNotificationClickReceiver;

import org.json.JSONObject;

import java.util.logging.Logger;

import me.jessyan.autosize.utils.LogUtils;

public class ZhiHangjiaPushIntentService extends GTIntentService {
    @Override
    public void onReceiveServicePid(Context context, int pid) {
    }

    @Override
    public void onReceiveMessageData(Context context, GTTransmitMessage msg) {
        // 透传消息的处理，详看SDK demo
        String appid = msg.getAppid();
        String taskid = msg.getTaskId();
        String messageid = msg.getMessageId();
        byte[] payload = msg.getPayload();
        String pkg = msg.getPkgName();
        String cid = msg.getClientId();

        if (payload == null) {
            JLog.e(TAG, "receiver payload = null");
        } else {
            String data = new String(payload);
            JLog.d(TAG, "receiver payload = " + data);
            try {
                PushMessage pushMessage = new Gson().fromJson(data, PushMessage.class);
                addNotification(pushMessage.getTitle(), pushMessage.getText(), pushMessage.getPayload());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 个推透传创建通知栏
     *
     * @param title
     * @param subtitle
     */
    private void addNotification(String title, String subtitle, PushMessage.Payload payload) {
        //显示不重复通知
        int requestCode = (int) System.currentTimeMillis();

        Intent broadcastIntent = new Intent(this, GeTuiNotificationClickReceiver.class);
        broadcastIntent.putExtra("message", payload);
        PendingIntent pendingIntent = PendingIntent.
                getBroadcast(this, requestCode, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder builder = new Notification.Builder(this);
        builder.setWhen(System.currentTimeMillis())
                .setContentTitle(title)
                .setContentText(subtitle)
                .setDefaults(Notification.DEFAULT_LIGHTS)
                //.setVibrate(new long[]{0, 300, 300, 300})
                //设置点击通知跳转页面后，通知消失
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);
        //获取app工程中的图片资源
        builder.setSmallIcon(R.mipmap.ic_zhihangjia_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_zhihangjia_launcher));
        NotificationManager manager = (NotificationManager) getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
        manager.notify(requestCode, builder.build());
    }

    @Override
    public void onReceiveClientId(Context context, String clientid) {
        JLog.e(TAG, "onReceiveClientId -> " + "clientid = " + clientid);
    }

    @Override
    public void onReceiveOnlineState(Context context, boolean online) {
    }

    @Override
    public void onReceiveCommandResult(Context context, GTCmdMessage cmdMessage) {
    }

    @Override
    public void onNotificationMessageArrived(Context context, GTNotificationMessage msg) {
    }

    @Override
    public void onNotificationMessageClicked(Context context, GTNotificationMessage msg) {
    }
}
