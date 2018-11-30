package com.zhihangjia.mainmodule.viewmodel;

import android.databinding.ObservableField;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.nmssdmf.commonlib.rxbus.EventInfo;
import com.nmssdmf.commonlib.rxbus.RxBus;
import com.nmssdmf.commonlib.rxbus.RxEvent;
import com.nmssdmf.commonlib.util.PreferenceUtil;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.activity.OrderListSupplierActivity;
import com.zhihangjia.mainmodule.activity.ShopCouponListActivity;
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
                            setData(userInfo);
                        }
                    }

                    @Override
                    public void onDefeated(BaseData<UserInfo> userInfoBaseData) {
                        callback.endFresh();
                    }
                });
    }

    public void setData(UserInfo userInfo) {
        if (userInfo == null) {
            String userinfoPrefrence = PreferenceUtil.getString(PrefrenceConfig.USER_INFO, null);
            if (!TextUtils.isEmpty(userinfoPrefrence))
                userInfo = new Gson().fromJson(userinfoPrefrence, UserInfo.class);
            if (userInfo == null) {
                callback.doIntentClassName(ActivityNameConfig.LOGIN_ACTIVITY, null);
                RxBus.getInstance().send(RxEvent.LoginEvent.RE_LOGIN, null);
            }
        }
        try {
            userinfo.set(userInfo);
            callback.bindVM();
            callback.initView();
        } catch (Exception e) {

        }
    }

    public void onSettingClick(View view) {
        callback.doIntentClassName(ActivityNameConfig.SET_ACTIVITY, null);
    }

    public void onCouponManagementClick(View view) {
        callback.doIntent(ShopCouponListActivity.class, null);
    }

    @Override
    public void registerRxBus() {
        super.registerRxBus();
        RxBus.getInstance().register(RxEvent.PersonInfoEvent.IDENTIFY_CHANGE, this);
    }

    @Override
    public void unRegisterRxBus() {
        super.unRegisterRxBus();
        RxBus.getInstance().unregister(RxEvent.PersonInfoEvent.IDENTIFY_CHANGE, this);
    }

    public void onRxEvent(RxEvent event, EventInfo info) {
        switch (event.getType()) {
            case RxEvent.PersonInfoEvent.IDENTIFY_CHANGE:
                getUserInfo();
                break;
        }
    }
}
