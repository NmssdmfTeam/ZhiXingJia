package com.zhihangjia.project;

import com.bugtags.library.Bugtags;
import com.nmssdmf.commonlib.application.BaseApplication;

public class App extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        //在这里初始化
        Bugtags.start("63088815c190983c7bbc615fe4f914ff", this, Bugtags.BTGInvocationEventBubble);
    }
}
