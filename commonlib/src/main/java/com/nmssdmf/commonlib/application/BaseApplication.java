package com.nmssdmf.commonlib.application;

import android.app.Application;

import com.igexin.sdk.PushManager;
import com.nmssdmf.commonlib.util.PreferenceUtil;
import com.nmssdmf.commonlib.util.ToastUtil;
import com.nmssdmf.commonlib.util.WeChatPayUtil;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

/**
 * Created by ${nmssdmf} on 2018/10/15 0015.
 */

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化Toast
        ToastUtil.getInstance().init(this);
        // 初始化PreferenceUtil
        PreferenceUtil.initialize(getApplicationContext());
        WeChatPayUtil.getInstance().registerApp(this);
        //友盟分享
        UMConfigure.init(this,"5c205fc4f1f55685f00001f3"
                ,"umeng",UMConfigure.DEVICE_TYPE_PHONE,"");
        PlatformConfig.setWeixin("wx74034cb7f59a0bc1", "1e44c8f1e9fe81964ef7144cbdf8b62b");
    }
}
