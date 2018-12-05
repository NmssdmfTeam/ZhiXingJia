package com.zhixingjia.personmodule.viewmodule;

import android.os.Bundle;
import android.view.View;

import com.nmssdmf.commonlib.activity.WebViewActivity;
import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.commonlib.bean.BaseData;
import com.nmssdmf.commonlib.callback.BaseCB;
import com.nmssdmf.commonlib.config.ActivityNameConfig;
import com.nmssdmf.commonlib.config.HttpVersionConfig;
import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.config.PrefrenceConfig;
import com.nmssdmf.commonlib.httplib.HttpUtils;
import com.nmssdmf.commonlib.httplib.RxRequest;
import com.nmssdmf.commonlib.httplib.ServiceCallback;
import com.nmssdmf.commonlib.rxbus.RxBus;
import com.nmssdmf.commonlib.rxbus.RxEvent;
import com.nmssdmf.commonlib.util.PreferenceUtil;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhixingjia.bean.mainmodule.Link;
import com.zhixingjia.personmodule.callback.SetCB;
import com.zhixingjia.service.MainService;

/**
 * @author chenbin
 * @version v3.2.0
 * @description 设置viewmodel
 * @date 2018/11/28 15:50
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
     *
     * @param view
     */
    public void onLogoutClick(View view) {
        //显示提示框，是否要退出登录
        callback.confirmLogout();
    }

    /**
     * 点击关于我们
     *
     * @param view
     */
    public void onAboutUsClick(View view) {
        getSingle("2");
    }

    private void getSingle(final String types) {
        HttpUtils.doHttp(subscription,
                RxRequest.create(MainService.class, HttpVersionConfig.API_SINGLE).getSingle(types),
                new ServiceCallback<BaseData<Link>>() {
                    @Override
                    public void onError(Throwable error) {

                    }

                    @Override
                    public void onSuccess(BaseData<Link> stringBaseData) {
                        Bundle bundle = new Bundle();
                        bundle.putString(IntentConfig.LINK, stringBaseData.getData().getLink_url());
                        callback.doIntent(WebViewActivity.class, bundle);
                    }

                    @Override
                    public void onDefeated(BaseData<Link> stringBaseData) {

                    }
                });
    }

    /**
     * @param view
     */
    public void onPrivacyClick(View view) {
        getSingle("3");
    }

    public void logout() {
        callback.showLoaddingDialog();
        HttpUtils.doHttp(subscription,
                RxRequest.create(MainService.class, HttpVersionConfig.API_AUTH_LOGOUT).logout(),
                new ServiceCallback<Base>() {
            @Override
            public void onError(Throwable error) {

            }

            @Override
            public void onSuccess(Base base) {
                RxBus.getInstance().send(RxEvent.LoginEvent.LOGOUT, null);
                // 跳转到登录页面
                baseCallBck.doIntentClassName(ActivityNameConfig.LOGIN_ACTIVITY, null);
                clearUserInfo();
            }

            @Override
            public void onDefeated(Base base) {

            }
        });
    }

    private void clearUserInfo() {
        PreferenceUtil.setStringValue(PrefrenceConfig.TOKEN, "");
        PreferenceUtil.setStringValue(PrefrenceConfig.USER_INFO, "");
        PreferenceUtil.setStringValue(PrefrenceConfig.IDENTIFY, "");
    }
}
