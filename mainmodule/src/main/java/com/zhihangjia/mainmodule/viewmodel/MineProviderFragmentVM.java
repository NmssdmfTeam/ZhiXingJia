package com.zhihangjia.mainmodule.viewmodel;

import android.databinding.ObservableField;
import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;
import com.nmssdmf.commonlib.bean.BaseData;
import com.nmssdmf.commonlib.config.ActivityNameConfig;
import com.nmssdmf.commonlib.config.HttpVersionConfig;
import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.config.PrefrenceConfig;
import com.nmssdmf.commonlib.config.StringConfig;
import com.nmssdmf.commonlib.httplib.HttpUtils;
import com.nmssdmf.commonlib.httplib.RxRequest;
import com.nmssdmf.commonlib.httplib.ServiceCallback;
import com.nmssdmf.commonlib.util.PreferenceUtil;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.activity.OrderListSupplierActivity;
import com.zhihangjia.mainmodule.callback.MineProviderFragmentCB;
import com.zhixingjia.bean.mainmodule.UserInfo;
import com.zhixingjia.service.MainService;

/**
* @description 我的--卖家
* @author chenbin
* @date 2018/11/20 16:40
* @version v3.2.0
*/
public class MineProviderFragmentVM extends BaseVM {

    public ObservableField<UserInfo> userinfo = new ObservableField<>();
    private MineProviderFragmentCB callback;

    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public MineProviderFragmentVM(MineProviderFragmentCB callBack) {
        super(callBack);
        this.callback = callBack;
    }

    public void orderListClick(View view, int position) {
        Bundle bundle = new Bundle();
        bundle.putInt(IntentConfig.POSITION, position);
        baseCallBck.doIntent(OrderListSupplierActivity.class, bundle);
    }

    public void publishGoodsClick(View view){
        baseCallBck.doIntentClassName(ActivityNameConfig.ADD_OR_EDIT_PRODUCT_ACTIVITY, null);
    }

    public void goodsManageClick(View view) {
        baseCallBck.doIntentClassName(ActivityNameConfig.GOOD_MANAGE_ACTIVITY, null);
    }

    /**
     * 获取用户信息
     */
    public void getUserInfo() {
        HttpUtils.doHttp(subscription,
                RxRequest.create(MainService.class, HttpVersionConfig.API_MY).getUserInfo("provider"),
                new ServiceCallback<BaseData<UserInfo>>() {
                    @Override
                    public void onError(Throwable error) {
                        callback.endFresh();
                    }

                    @Override
                    public void onSuccess(BaseData<UserInfo> userInfoBaseData) {
                        callback.endFresh();
                        if (StringConfig.OK.equals(userInfoBaseData.getStatus_code())) {
                            PreferenceUtil.setStringValue(PrefrenceConfig.USER_INFO, new Gson().toJson(userInfoBaseData.getData()));
                            UserInfo userInfo = userInfoBaseData.getData();
                            userinfo.set(userInfo);
                            callback.bindVM();
                            callback.initView();
                        }
                    }

                    @Override
                    public void onDefeated(BaseData<UserInfo> userInfoBaseData) {
                        callback.endFresh();
                    }
                });
    }
}
