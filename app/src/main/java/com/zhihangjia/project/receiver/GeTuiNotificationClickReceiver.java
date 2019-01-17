package com.zhihangjia.project.receiver;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.nmssdmf.commonlib.activity.WebViewActivity;
import com.nmssdmf.commonlib.bean.PushMessage;
import com.nmssdmf.commonlib.config.ActivityNameConfig;
import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.config.StringConfig;
import com.nmssdmf.commonlib.util.DisposeIntentMessage;
import com.zhihangjia.project.FirstActivity;

import java.util.List;

public class GeTuiNotificationClickReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        PushMessage.Payload payload = (PushMessage.Payload) intent.getSerializableExtra("message");
        if (!isExsitMianActivity(context, ActivityNameConfig.MAIN_ACTIVITY)) {//MainActivity未开启,传递给MainActivity处理
            Bundle bundle = new Bundle();
            bundle.putSerializable("message", payload);
            Intent activityIntent = new Intent();
            activityIntent.putExtras(bundle);
            activityIntent.setClassName(context, FirstActivity.class.getName());
            context.startActivity(activityIntent);
            return;
        }
        DisposeIntentMessage.resovleIntent(context, payload);
    }

    /**
     * 判断某一个类是否存在任务栈里面
     *
     * @return
     */
    public boolean isExsitMianActivity(Context context, String className) {
        Intent intent = new Intent();
        intent.setClassName(context, className);
        ComponentName cmpName = intent.resolveActivity(context
                .getPackageManager());
        boolean flag = false;
        if (cmpName != null) { // 说明系统中存在这个activity
            ActivityManager am = (ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> taskInfoList = am.getRunningTasks(100);
            for (ActivityManager.RunningTaskInfo taskInfo : taskInfoList) {
                if (taskInfo.baseActivity.equals(cmpName)) { // 说明它已经启动了
                    flag = true;
                    break; // 跳出循环，优化效率
                }
            }
        }
        return flag;
    }
}
