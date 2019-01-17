package com.nmssdmf.commonlib.util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.nmssdmf.commonlib.activity.WebViewActivity;
import com.nmssdmf.commonlib.bean.PushMessage;
import com.nmssdmf.commonlib.config.ActivityNameConfig;
import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.config.StringConfig;

public class DisposeIntentMessage {

    /**
     * 处理消息做跳转
     *
     * @param context
     * @param payload
     */
    public static void resovleIntent(Context context, PushMessage.Payload payload) {
        Intent activityIntent = new Intent();
        Bundle bundle = new Bundle();
        activityIntent.setClassName(context, ActivityNameConfig.MAIN_ACTIVITY);
        if (payload != null) {
            if (TextUtils.isEmpty(payload.getSource_type())) {
                activityIntent.setClassName(context, ActivityNameConfig.MAIN_ACTIVITY);
                return;
            }
            switch (payload.getSource_type()) {
                case "all":
                    activityIntent.setClassName(context, ActivityNameConfig.MAIN_ACTIVITY);
                    break;
                case "order"://订单详情
                    bundle.putString(IntentConfig.ID, payload.getSource_jumps());
                    bundle.putString(IntentConfig.IDENTITY, payload.getOrder_role().equals("1") ? StringConfig.BUYER : StringConfig.PROVIDER);
                    activityIntent.putExtras(bundle);
                    activityIntent.setClassName(context, ActivityNameConfig.ORDERDETAIL_ACTIVITY);
                    break;
                case "h5":
                    bundle.putString(IntentConfig.LINK, payload.getSource_jumps());
                    activityIntent.putExtras(bundle);
                    activityIntent.setClass(context, WebViewActivity.class);
                    break;
                case "shop"://商家首页
                    bundle.putString(IntentConfig.ID, payload.getSource_jumps());
                    activityIntent.putExtras(bundle);
                    activityIntent.setClassName(context, ActivityNameConfig.MERCHANTMAIN_ACTIVITY);
                    break;
                case "commodity"://商品详情
                    bundle.putString(IntentConfig.COMMODITY_ID, payload.getSource_jumps());
                    activityIntent.putExtras(bundle);
                    activityIntent.setClassName(context, ActivityNameConfig.MERCHANDISEDETAIL_ACTIVITY);
                    break;
                case "bbsinfo":
                    bundle.putString(IntentConfig.ID, payload.getSource_jumps());
                    activityIntent.putExtras(bundle);
                    activityIntent.setClassName(context, ActivityNameConfig.MESSAGEDETAIL_ACTIVITY);
                    break;
            }
        }
        try {
            activityIntent.putExtras(bundle);
            context.startActivity(activityIntent);
        } catch (Exception e) {
        }
    }

}
