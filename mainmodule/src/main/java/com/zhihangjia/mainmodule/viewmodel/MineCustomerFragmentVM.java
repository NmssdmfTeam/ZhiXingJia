package com.zhihangjia.mainmodule.viewmodel;

import android.databinding.ObservableBoolean;
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
import com.zhihangjia.mainmodule.activity.OrderListPurchaserActivity;
import com.zhihangjia.mainmodule.callback.MineCustomerFragmentCB;
import com.zhixingjia.bean.mainmodule.UserInfo;
import com.zhixingjia.service.MainService;


/**
* @description 我的买家版
* @author chenbin
* @date 2018/11/20 13:26
* @version v3.2.0
*/
public class MineCustomerFragmentVM extends BaseVM {
    public ObservableField<UserInfo> userinfo = new ObservableField<>();
    public ObservableBoolean isProvider = new ObservableBoolean();
    private MineCustomerFragmentCB callback;

    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public MineCustomerFragmentVM(MineCustomerFragmentCB callBack) {
        super(callBack);
        this.callback = callBack;
    }

    public void applySupplierActivity(View view) {
        if ("审核成功".equals(userinfo.get().getVerify_status()))
        baseCallBck.doIntentClassName(ActivityNameConfig.APPLY_SUPPLIER_ACTIVITY, null);
    }

    public void orderClick(View view, int postion){
        Bundle bundle = new Bundle();
        bundle.putInt(IntentConfig.POSITION, postion);
        baseCallBck.doIntent(OrderListPurchaserActivity.class, bundle);
    }

    public void myCouponClick(View view){
        baseCallBck.doIntentClassName(ActivityNameConfig.MY_COUPONS_ACTIVITY, null);
    }

    public void personInfoClick(View view) {
        baseCallBck.doIntentClassName(ActivityNameConfig.PERSON_INFO_ACTIVITY, null);
    }

    public void receiveAddress(View view){
        baseCallBck.doIntentClassName(ActivityNameConfig.MANAGE_ADDRESS_LIST_ACTIVITY, null);
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
                        callback.endFresh();
                    }

                    @Override
                    public void onSuccess(BaseData<UserInfo> userInfoBaseData) {
                        callback.endFresh();
                        if (StringConfig.OK.equals(userInfoBaseData.getStatus_code())) {
                            PreferenceUtil.setStringValue(PrefrenceConfig.USER_INFO, new Gson().toJson(userInfoBaseData.getData()));
                            UserInfo userInfo = userInfoBaseData.getData();
                            userinfo.set(userInfoBaseData.getData());
                            isProvider.set("1".equals(userInfo.getHouse_provider()));
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

    public void onSettingClick(View view) {
        callback.doIntentClassName(ActivityNameConfig.SET_ACTIVITY, null);
    }

    public void userIconClick(View view) {
        callback.doIntentClassName(ActivityNameConfig.SET_ACTIVITY, null);
    }
}
