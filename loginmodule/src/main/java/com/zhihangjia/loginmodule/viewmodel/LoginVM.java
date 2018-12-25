package com.zhihangjia.loginmodule.viewmodel;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;
import com.nmssdmf.commonlib.activity.WebViewActivity;
import com.nmssdmf.commonlib.bean.BaseData;
import com.nmssdmf.commonlib.config.ActivityNameConfig;
import com.nmssdmf.commonlib.config.BaseConfig;
import com.nmssdmf.commonlib.config.HttpVersionConfig;
import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.config.PrefrenceConfig;
import com.nmssdmf.commonlib.config.StringConfig;
import com.nmssdmf.commonlib.httplib.HttpUtils;
import com.nmssdmf.commonlib.httplib.RxRequest;
import com.nmssdmf.commonlib.httplib.ServiceCallback;
import com.nmssdmf.commonlib.net.IServiceLib;
import com.nmssdmf.commonlib.net.http.OkHttpClientProvider;
import com.nmssdmf.commonlib.net.retrofit.HttpObserver;
import com.nmssdmf.commonlib.rxbus.EventInfo;
import com.nmssdmf.commonlib.rxbus.RxBus;
import com.nmssdmf.commonlib.rxbus.RxEvent;
import com.nmssdmf.commonlib.social.WXLoginApi;
import com.nmssdmf.commonlib.util.Base64Utils;
import com.nmssdmf.commonlib.util.JLog;
import com.nmssdmf.commonlib.util.PreferenceUtil;
import com.nmssdmf.commonlib.util.RSAUtil;
import com.nmssdmf.commonlib.util.StringUtil;
import com.nmssdmf.commonlib.util.ToastUtil;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.loginmodule.Activity.BindRegisterActivity;
import com.zhihangjia.loginmodule.Activity.ForgetPwdActivity;
import com.zhihangjia.loginmodule.callback.LoginCB;
import com.zhixingjia.bean.loginmodule.LoginResult;
import com.zhixingjia.bean.loginmodule.WXAccessToken;
import com.zhixingjia.bean.loginmodule.WXUserInfo;
import com.zhixingjia.bean.mainmodule.Link;
import com.zhixingjia.bean.mainmodule.UserInfo;
import com.zhixingjia.service.LoginService;
import com.zhixingjia.service.MainService;

import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ${nmssdmf} on 2018/11/13 0013.
 */

public class LoginVM extends BaseVM {
    private final String TAG = LoginVM.class.getSimpleName();
    public static final int REGISTER_REQUEST_CODE = 1;
    public static final int FORGET_PWD_REQUEST_CODE = 2;
    public final ObservableField<String> phoneNum = new ObservableField<>();
    public final ObservableField<String> pwd = new ObservableField<>();
    public final ObservableBoolean pwdShow = new ObservableBoolean(false);

    private LoginCB cb;

    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public LoginVM(LoginCB callBack) {
        super(callBack);
        cb = callBack;
    }

    public void tvForgetPwdClick(View view) {
        cb.doIntentForResult(ForgetPwdActivity.class, null, FORGET_PWD_REQUEST_CODE);
    }

    public void tvLoginClick(View view) {
        if (StringUtil.isEmpty(phoneNum.get())) {
            ToastUtil.showMsg("请输入手机号");
            return;
        }

        if (StringUtil.isEmpty(pwd.get())) {
            ToastUtil.showMsg("请输入密码");
            return;
        }

        if (StringUtil.checkPwd(pwd.get())) {
            ToastUtil.showMsg("密码格式不正确");
            return;
        }

        doLogin();
    }

    public void ivHidePwdClick(View view) {
        pwdShow.set(!pwdShow.get());

        cb.setInputType(pwdShow.get());
    }

    public void ivWechatClick(View view) {
        cb.wxAuth();
    }

    private void doLogin() {
        Map<String, String> map = new HashMap<>();
        map.put("login_account", phoneNum.get());
        try {
            PublicKey publicKey = RSAUtil.loadPublicKey(BaseConfig.PUBLIC_KEY);
            // 加密
            byte[] encryptByte = RSAUtil.encryptData(pwd.get().getBytes(), publicKey);
            // 为了方便观察吧加密后的数据用base64加密转一下，要不然看起来是乱码,所以解密是也是要用Base64先转换
            String afterencrypt = Base64Utils.encode(encryptByte);
            map.put("password", afterencrypt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        cb.showLoaddingDialog();
        HttpUtils.doHttp(subscription, RxRequest.create(LoginService.class, HttpVersionConfig.API_AUTH_LOGIN).login(map), new ServiceCallback<BaseData<LoginResult>>() {
            @Override
            public void onError(Throwable error) {
            }

            @Override
            public void onSuccess(BaseData<LoginResult> data) {
                ToastUtil.showMsg(data.getMessage());
                PreferenceUtil.setStringValue(PrefrenceConfig.TOKEN, data.getData().getToken());
                getUserInfo();
            }

            @Override
            public void onDefeated(BaseData<LoginResult> data) {
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
                            cb.doIntentClassName(ActivityNameConfig.MAIN_ACTIVITY, null);
                            cb.finishActivity();
                        }
                    }

                    @Override
                    public void onDefeated(BaseData<UserInfo> userInfoBaseData) {
                    }
                });
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
                        cb.doIntent(WebViewActivity.class, bundle);
                    }

                    @Override
                    public void onDefeated(BaseData<Link> stringBaseData) {

                    }
                });
    }

    public void onServiceProtocalClick(View view) {
        getSingle("1");
    }

    public void onPrivacyPolicyClick(View view) {
        getSingle("3");
    }

    @Override
    public void registerRxBus() {
        super.registerRxBus();
        RxBus.getInstance().register(RxEvent.LoginEvent.LOGIN_WEXIN, this);
        RxBus.getInstance().register(RxEvent.LoginEvent.BIND_WEXIN, this);
    }

    @Override
    public void unRegisterRxBus() {
        super.unRegisterRxBus();
        RxBus.getInstance().unregister(RxEvent.LoginEvent.LOGIN_WEXIN, this);
        RxBus.getInstance().unregister(RxEvent.LoginEvent.BIND_WEXIN, this);
    }

    public void onRxEvent(RxEvent event, EventInfo info) {
        switch (event.getType()) {
            case RxEvent.LoginEvent.LOGIN_WEXIN:
                String code = (String) info.getContent();
                //调用获取微信access_token
                cb.showLoaddingDialog();
                wxGetAccessToken(code);
                break;
            case RxEvent.LoginEvent.BIND_WEXIN:
                String token = (String) info.getContent();
                PreferenceUtil.setStringValue(PrefrenceConfig.TOKEN, token);
                getUserInfo();
                break;
            default:
                break;
        }
    }

    /**
     * 获取微信access_token
     *
     * @param code
     */
    public void wxGetAccessToken(String code) {
        LoginService iService = new Retrofit.Builder()
                .baseUrl(BaseConfig.WXACCESSTOKEN_API_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .client(OkHttpClientProvider.getInstance().getClient()).build().create(LoginService.class);
        Map<String, Object> params = new HashMap<>();
        params.put("appid", BaseConfig.WXAPI_APPID);
        params.put("secret", BaseConfig.WXAPI_APPSECRET);
        params.put("code", code);
        params.put("grant_type", "authorization_code");
        iService.requestWXAccessToken(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new HttpObserver<WXAccessToken>() {

            @Override
            public void onNext(WXAccessToken wxAccessToken) {
                wxLogin(wxAccessToken);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        });
    }

    public void wxLogin(final WXAccessToken wxAccessToken) {
        HttpUtils.doHttp(subscription,
                RxRequest.create(LoginService.class, HttpVersionConfig.API_AUTH_WEIXIN_LOGIN).wexinLogin(wxAccessToken.getOpenid()),
                new ServiceCallback<BaseData<LoginResult>>() {
                    @Override
                    public void onError(Throwable error) {

                    }

                    @Override
                    public void onSuccess(BaseData<LoginResult> loginResultBaseData) {
                        if ("1".equals(loginResultBaseData.getData().getStatus())) {    //存在，会直接输出下面的token值
                            ToastUtil.showMsg("登录成功");
                            PreferenceUtil.setStringValue(PrefrenceConfig.TOKEN, loginResultBaseData.getData().getToken());
                            getUserInfo();
                        } else {
                            //跳转绑定页面
                            wxGetUserInfo(wxAccessToken);
                        }
                    }

                    @Override
                    public void onDefeated(BaseData<LoginResult> loginResultBaseData) {

                    }
                });
    }

    /**
     * 获取微信用户信息
     */
    public void wxGetUserInfo(final WXAccessToken wxAccessToken) {
        LoginService iService = new Retrofit.Builder()
                .baseUrl(BaseConfig.WXACCESSTOKEN_API_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .client(OkHttpClientProvider.getInstance().getClient()).build().create(LoginService.class);
        Map<String,Object> params = new HashMap<>();
        params.put("access_token", wxAccessToken.getAccess_token());
        params.put("openid",wxAccessToken.getOpenid());
        iService.requestWXUserInfo(params).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new HttpObserver<WXUserInfo>() {
                    @Override
                    public void onNext(WXUserInfo wxUserInfo) {
                        Bundle bundle = new Bundle();
                        bundle.putString(IntentConfig.OPEN_ID, wxAccessToken.getOpenid());
                        bundle.putSerializable(IntentConfig.WX_USERINFO, wxUserInfo);
                        cb.doIntent(BindRegisterActivity.class, bundle);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }
}
