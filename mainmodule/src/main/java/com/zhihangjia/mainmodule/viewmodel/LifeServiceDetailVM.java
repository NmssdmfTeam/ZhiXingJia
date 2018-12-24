package com.zhihangjia.mainmodule.viewmodel;

import android.databinding.ObservableField;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.nmssdmf.commonlib.bean.BaseData;
import com.nmssdmf.commonlib.config.HttpVersionConfig;
import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.httplib.HttpUtils;
import com.nmssdmf.commonlib.httplib.RxRequest;
import com.nmssdmf.commonlib.httplib.ServiceCallback;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.callback.LifeServiceDetailCB;
import com.zhixingjia.bean.mainmodule.LifeServiceDetail;
import com.zhixingjia.service.MainService;

import java.util.ArrayList;
import java.util.List;

/**
* @description 生活服务详情
* @author chenbin
* @date 2018/12/19 14:39
* @version v3.2.0
*/
public class LifeServiceDetailVM extends BaseVM {
    private LifeServiceDetailCB callback;
    private String info_id;
    public String title;
    public final ObservableField<LifeServiceDetail> lifeServiceDetail = new ObservableField<>();
    public List<Uri> imageUrls = new ArrayList<>();

    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public LifeServiceDetailVM(LifeServiceDetailCB callBack) {
        super(callBack);
        this.callback = callBack;
        initData();
    }

    public void initData() {
        Bundle bundle = callback.getIntentData();
        if (bundle != null) {
            info_id = bundle.getString(IntentConfig.ID);
            title = bundle.getString(IntentConfig.NAME);
            if (!TextUtils.isEmpty(info_id)) {
                getDetailInfo();
            }
        }
    }

    /**
     * 获取详情
     */
    public void getDetailInfo() {
        HttpUtils.doHttp(subscription,
                RxRequest.create(MainService.class, HttpVersionConfig.API_LIFE_SHOW).getLifeServiceDetail(info_id),
                new ServiceCallback<BaseData<LifeServiceDetail>>() {
            @Override
            public void onError(Throwable error) {

            }

            @Override
            public void onSuccess(BaseData<LifeServiceDetail> lifeServiceDetailBaseData) {
                lifeServiceDetail.set(lifeServiceDetailBaseData.getData());
                callback.setContent();
            }

            @Override
            public void onDefeated(BaseData<LifeServiceDetail> lifeServiceDetailBaseData) {

            }
        });
    }

    public void onPhoneCallClick(View view) {
        callback.phoneCall(lifeServiceDetail.get().getInfo_tel());
    }

    public void onNavigationClick(View view) {
        callback.showChooseMapsWindows();
    }
}
