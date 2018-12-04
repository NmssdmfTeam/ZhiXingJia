package com.zhihangjia.project;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.nmssdmf.commonlib.config.BaseConfig;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
public class AppRegister extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		final IWXAPI msgApi = WXAPIFactory.createWXAPI(context, null);

		// 将该app注册到微信
		msgApi.registerApp(BaseConfig.WXAPI_APPID);
	}
}
