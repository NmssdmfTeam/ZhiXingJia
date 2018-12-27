package com.zhihangjia.mainmodule.viewmodel;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.google.gson.Gson;
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
import com.nmssdmf.commonlib.rxbus.EventInfo;
import com.nmssdmf.commonlib.rxbus.RxBus;
import com.nmssdmf.commonlib.rxbus.RxEvent;
import com.nmssdmf.commonlib.util.PreferenceUtil;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.activity.MessageCenterActivity;
import com.zhihangjia.mainmodule.activity.MyPostActivity;
import com.zhihangjia.mainmodule.activity.OrderListPurchaserActivity;
import com.zhihangjia.mainmodule.callback.MineCustomerFragmentCB;
import com.zhixingjia.bean.mainmodule.MessageUnread;
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
    private String customerServiceCall;

    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public MineCustomerFragmentVM(MineCustomerFragmentCB callBack) {
        super(callBack);
        this.callback = callBack;
        getMessageUnread();
    }

    public void applySupplierActivity(View view) {
        if ("审核成功".equals(userinfo.get().getVerify_status())) return;
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
                            customerServiceCall = userInfoBaseData.getData().getService_tel();
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
            isProvider.set("1".equals(userInfo.getHouse_provider()));
            callback.bindVM();
            callback.initView();
        } catch (Exception e) {

        }
    }

    public void onSettingClick(View view) {
        callback.doIntentClassName(ActivityNameConfig.SET_ACTIVITY, null);
    }

    public void userIconClick(View view) {
        callback.doIntentClassName(ActivityNameConfig.SET_ACTIVITY, null);
    }

    public void onCustomerServiceCallClick(View view) {
        callback.phoneCall(customerServiceCall);
    }

    public void onMessageClick(View view) {
        callback.doIntent(MessageCenterActivity.class, null);
    }

    public void getMessageUnread() {
        HttpUtils.doHttp(subscription,
                RxRequest.create(MainService.class, HttpVersionConfig.API_MESSAGE_UNREAD).getMessageUnread(),
                new ServiceCallback<BaseData<MessageUnread>>() {
                    @Override
                    public void onError(Throwable error) {

                    }

                    @Override
                    public void onSuccess(BaseData<MessageUnread> messageUnreadBaseData) {
                        //设置是否显示小红点
                        callback.showNotice(messageUnreadBaseData.getData());
                    }

                    @Override
                    public void onDefeated(BaseData<MessageUnread> messageUnreadBaseData) {

                    }
                });
    }

    /**
     * 我的发帖
     * @param view
     */
    public void onBbsPostClick(View view) {
        Bundle bundle = new Bundle();
        bundle.putInt(IntentConfig.POSITION, 0);
        callback.doIntent(MyPostActivity.class, bundle);
    }

    public void onBbsReplyClick(View view) {
        Bundle bundle = new Bundle();
        bundle.putInt(IntentConfig.POSITION, 1);
        callback.doIntent(MyPostActivity.class, bundle);
    }

    @Override
    public void registerRxBus() {
        super.registerRxBus();
        RxBus.getInstance().register(RxEvent.LoginEvent.LOGOUT, this);
    }

    @Override
    public void unRegisterRxBus() {
        super.unRegisterRxBus();
        RxBus.getInstance().unregister(RxEvent.LoginEvent.LOGOUT, this);
    }

    public void onRxEvent(RxEvent event, EventInfo info) {
        switch (event.getType()) {
            case RxEvent.LoginEvent.LOGOUT:
                MessageUnread messageUnread = new MessageUnread();
                messageUnread.setAll_message("0");
                messageUnread.setOrder_message("0");
                messageUnread.setSys_message("0");
                callback.showNotice(messageUnread);
                break;
        }
    }
}
