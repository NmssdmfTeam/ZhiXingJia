package com.zhixingjia.personmodule.viewmodule;

import android.view.View;

import com.nmssdmf.commonlib.callback.BaseCB;
import com.nmssdmf.commonlib.config.ActivityNameConfig;
import com.nmssdmf.commonlib.config.PrefrenceConfig;
import com.nmssdmf.commonlib.rxbus.RxBus;
import com.nmssdmf.commonlib.rxbus.RxEvent;
import com.nmssdmf.commonlib.util.PreferenceUtil;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhixingjia.personmodule.callback.SetCB;

/**
* @description 设置viewmodel
* @author chenbin
* @date 2018/11/28 15:50
* @version v3.2.0
*/
public class SetVM extends BaseVM {
    private SetCB callback;

    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public SetVM(SetCB callBack) {
        super(callBack);
        this.callback = callBack;
    }

    /**
     * 退出登录
     * @param view
     */
    public void onLogoutClick(View view) {
        //显示提示框，是否要退出登录
        callback.confirmLogout();
    }

    public void logout() {
        RxBus.getInstance().send(RxEvent.LoginEvent.LOGOUT, null);
        // 跳转到登录页面
        baseCallBck.doIntentClassName(ActivityNameConfig.LOGIN_ACTIVITY, null);
        clearUserInfo();
    }

    private void clearUserInfo() {
        PreferenceUtil.setStringValue(PrefrenceConfig.TOKEN, "");
        PreferenceUtil.setStringValue(PrefrenceConfig.USER_INFO, "");
        PreferenceUtil.setStringValue(PrefrenceConfig.IDENTIFY, "");
    }
}
