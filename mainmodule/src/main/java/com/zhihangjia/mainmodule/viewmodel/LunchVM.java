package com.zhihangjia.mainmodule.viewmodel;

import com.google.gson.Gson;
import com.nmssdmf.commonlib.bean.BaseData;
import com.nmssdmf.commonlib.callback.BaseCB;
import com.nmssdmf.commonlib.config.HttpVersionConfig;
import com.nmssdmf.commonlib.config.PrefrenceConfig;
import com.nmssdmf.commonlib.config.StringConfig;
import com.nmssdmf.commonlib.httplib.HttpUtils;
import com.nmssdmf.commonlib.httplib.RxRequest;
import com.nmssdmf.commonlib.httplib.ServiceCallback;
import com.nmssdmf.commonlib.rxbus.RxBus;
import com.nmssdmf.commonlib.rxbus.RxEvent;
import com.nmssdmf.commonlib.util.PreferenceUtil;
import com.nmssdmf.commonlib.util.ToastUtil;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhixingjia.bean.loginmodule.LoginResult;
import com.zhixingjia.bean.mainmodule.UserInfo;
import com.zhixingjia.service.LoginService;
import com.zhixingjia.service.MainService;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ${nmssdmf} on 2018/11/22 0022.
 */

public class LunchVM extends BaseVM {

    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public LunchVM(BaseCB callBack) {
        super(callBack);
    }

    public void doLogin(){
        Map<String, String> map = new HashMap<>();
        map.put("login_account", "15606813948");
//        map.put("login_account", "15700103112");
        map.put("password", "123456");
        baseCallBck.showLoaddingDialog();
        HttpUtils.doHttp(subscription, RxRequest.create(LoginService.class, HttpVersionConfig.API_AUTH_LOGIN).login(map), new ServiceCallback<BaseData<LoginResult>>() {
            @Override
            public void onError(Throwable error) {

            }

            @Override
            public void onSuccess(BaseData<LoginResult> data) {
                ToastUtil.showMsg("登陆成功");
                PreferenceUtil.setStringValue(PrefrenceConfig.TOKEN, data.getData().getToken());
                getUserInfo();
            }

            @Override
            public void onDefeated(BaseData<LoginResult> loginResultBaseData) {

            }
        });
    }

    /**
     * 获取用户信息
     */
    public void getUserInfo() {
        HttpUtils.doHttp(subscription,
                RxRequest.create(MainService.class, HttpVersionConfig.API_MY).getUserInfo("buyer"),
                new ServiceCallback<BaseData<UserInfo>>() {
                    @Override
                    public void onError(Throwable error) {
                    }

                    @Override
                    public void onSuccess(BaseData<UserInfo> userInfoBaseData) {
                        if (StringConfig.OK.equals(userInfoBaseData.getStatus_code())) {
                            PreferenceUtil.setStringValue(PrefrenceConfig.USER_INFO, new Gson().toJson(userInfoBaseData.getData()));
                            RxBus.getInstance().send(RxEvent.LoginEvent.LOGIN_SUCCESS, null);
                        }
                    }

                    @Override
                    public void onDefeated(BaseData<UserInfo> userInfoBaseData) {
                    }
                });
    }
}
