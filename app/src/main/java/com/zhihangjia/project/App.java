package com.zhihangjia.project;

import com.bugtags.library.Bugtags;
import com.bugtags.library.BugtagsOptions;
import com.nmssdmf.commonlib.application.BaseApplication;

public class App extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        //bugtags
        BugtagsOptions options = new BugtagsOptions.Builder().trackingCrashLog(true).build();
        //在这里初始化
        //线下
        Bugtags.start("63088815c190983c7bbc615fe4f914ff", this, Bugtags.BTGInvocationEventBubble,options);
        //线上
//        Bugtags.start("77c3590c8ea6e18389c3a0f0a17f3677", this, Bugtags.BTGInvocationEventBubble,options);
    }
}
