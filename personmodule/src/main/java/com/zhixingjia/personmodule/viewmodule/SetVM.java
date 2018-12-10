package com.zhixingjia.personmodule.viewmodule;

import android.app.Activity;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
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
import com.nmssdmf.commonlib.util.DataCleanManager;
import com.nmssdmf.commonlib.util.PreferenceUtil;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhixingjia.bean.mainmodule.Link;
import com.zhixingjia.personmodule.activity.ChangePwdActivity;
import com.zhixingjia.personmodule.activity.FeedbackActivity;
import com.zhixingjia.personmodule.callback.SetCB;
import com.zhixingjia.service.MainService;

import org.reactivestreams.Subscriber;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author chenbin
 * @version v3.2.0
 * @description 设置viewmodel
 * @date 2018/11/28 15:50
 */
public class SetVM extends BaseVM {
    private SetCB callback;
    public final ObservableField<String> cacheSize = new ObservableField<>();
    public String phoneNumber;

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

    public void getCacheSize(final Activity activity) {
        Observable observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                try {
                    String data = DataCleanManager.getTotalCacheSize(activity);
                    e.onNext(data);
                } catch (Exception error) {
                    e.onError(error);
                    return;
                }
                e.onComplete();
            }
        });
        Observer<String> observer = new Observer<String>() {

            @Override
            public void onSubscribe(Disposable d) {
                subscription.add(d);
            }

            @Override
            public void onNext(String s) {
                cacheSize.set(s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(observer);
    }

    public void onCleanCacheClick(View view) {
        callback.confirmCleanCache();
    }

    public void cleanCache(final Activity activity) {
        Observable observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                try {
                    DataCleanManager.clearAllCache(activity);
                } catch (Exception error) {
                    e.onError(error);
                    return;
                }
                e.onComplete();
            }
        });
        Observer<String> observer = new Observer<String>() {

            @Override
            public void onSubscribe(Disposable d) {
                callback.showLoaddingDialog();
                subscription.add(d);
            }

            @Override
            public void onNext(String s) {
                cacheSize.set(s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                callback.dismissLoaddingDialog();
                cacheSize.set("0K");
            }
        };
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(observer);
    }

    /**
     * 意见反馈
     *
     * @param view
     */
    public void onFeedBackClick(View view) {
        callback.doIntent(FeedbackActivity.class, null);
    }

    /**
     * 修改帐号
     *
     * @param view
     */
    public void onChangeAccountClick(View view) {
        callback.doIntentClassName(ActivityNameConfig.CHANGEACCOUNT_ACTIVITY, null);
    }

    /**
     * 修改密码
     * @param view
     */
    public void onChangePswClick(View view) {
        Bundle bundle = new Bundle();
        bundle.putString(IntentConfig.PHONE_NUM, phoneNumber);
        callback.doIntent(ChangePwdActivity.class, bundle);
    }

    private void clearUserInfo() {
        PreferenceUtil.setStringValue(PrefrenceConfig.TOKEN, "");
        PreferenceUtil.setStringValue(PrefrenceConfig.USER_INFO, "");
        PreferenceUtil.setStringValue(PrefrenceConfig.IDENTIFY, "");
    }
}
