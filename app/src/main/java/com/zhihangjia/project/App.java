package com.zhihangjia.project;

import com.bugtags.library.Bugtags;
import com.bugtags.library.BugtagsOptions;
import com.igexin.sdk.PushManager;
import com.nmssdmf.commonlib.application.BaseApplication;
import com.zhihangjia.project.service.GetuiService;
import com.zhihangjia.project.service.ZhiHangjiaPushIntentService;

public class App extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        //bugtags
        BugtagsOptions options = new BugtagsOptions.Builder().trackingCrashLog(true).build();
        //在这里初始化
        //线下
        Bugtags.start("63088815c190983c7bbc615fe4f914ff", this, Bugtags.BTGInvocationEventBubble,options);

        //个推初始化
        PushManager.getInstance().initialize(this, GetuiService.class);
        // ZhiHangjiaPushIntentService 为第三方自定义的推送服务事件接收类
        PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), ZhiHangjiaPushIntentService.class);
    }
}
