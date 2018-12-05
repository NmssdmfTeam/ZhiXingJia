package com.zhihangjia.mainmodule.viewmodel;

import android.os.Bundle;
import android.preference.Preference;
import android.view.View;

import com.google.gson.Gson;
import com.nmssdmf.commonlib.bean.BaseListData;
import com.nmssdmf.commonlib.callback.BaseCB;
import com.nmssdmf.commonlib.config.HttpVersionConfig;
import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.config.PrefrenceConfig;
import com.nmssdmf.commonlib.httplib.HttpUtils;
import com.nmssdmf.commonlib.httplib.RxRequest;
import com.nmssdmf.commonlib.httplib.ServiceCallback;
import com.nmssdmf.commonlib.util.PreferenceUtil;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.activity.SearchActivity;
import com.zhihangjia.mainmodule.callback.IndexMessageCB;
import com.zhixingjia.bean.mainmodule.BbsCategory;
import com.zhixingjia.service.MainService;

/**
 * 信息中心viewmodel
 * Create by chenbin on 2018/11/17
 * <p>
 * <p>
 */
public class MessageVM extends BaseVM {
    private IndexMessageCB callback;

    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public MessageVM(IndexMessageCB callBack) {
        super(callBack);
        this.callback = callBack;
    }

    /**
     * 获取类别
     */
    public void getMessageCat() {
        HttpUtils.doHttp(subscription, RxRequest.create(MainService.class, HttpVersionConfig.API_BBS_CATE).getBbsCate(),
                new ServiceCallback<BaseListData<BbsCategory>>() {
            @Override
            public void onError(Throwable error) {

            }

            @Override
            public void onSuccess(BaseListData<BbsCategory> bbsCategoryBaseListData) {
                if (bbsCategoryBaseListData.getData() != null) {
                    callback.setMessageCategory(bbsCategoryBaseListData.getData());
                    //对信息中心的消息类型进行缓存
                    PreferenceUtil.setStringValue(PrefrenceConfig.BBS_CAT, new Gson().toJson(bbsCategoryBaseListData.getData()));
                }
            }

            @Override
            public void onDefeated(BaseListData<BbsCategory> bbsCategoryBaseListData) {

            }
        });
    }

    /**
     * 搜索
     * @param view
     */
    public void onSearchClick(View view) {
        Bundle bundle = new Bundle();
        bundle.putInt(IntentConfig.POSITION, 2);
        callback.doIntent(SearchActivity.class, bundle);
    }
}
