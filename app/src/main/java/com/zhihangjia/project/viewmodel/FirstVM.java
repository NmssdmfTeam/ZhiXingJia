package com.zhihangjia.project.viewmodel;

import android.os.Bundle;

import com.nmssdmf.commonlib.callback.BaseCB;
import com.nmssdmf.commonlib.config.ActivityNameConfig;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
* @description 启动页viewmodel
* @author chenbin
* @date 2018/12/11 13:27
* @version v3.2.0
*/
public class FirstVM extends BaseVM {

    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public FirstVM(BaseCB callBack) {
        super(callBack);
        init();
    }

    public void init() {
        Bundle bundle = baseCallBck.getIntentData();
        Observable observable = Observable.create((ObservableOnSubscribe<String>) e -> {
            Thread.sleep(1000);
            e.onComplete();
        });
        Observer<String> observer = new Observer<String>() {

            @Override
            public void onSubscribe(Disposable d) {
                subscription.add(d);
            }

            @Override
            public void onNext(String s) {
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                baseCallBck.doIntentClassName(ActivityNameConfig.MAIN_ACTIVITY, bundle);
                baseCallBck.finishActivity();
            }
        };
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(observer);
    }
}
