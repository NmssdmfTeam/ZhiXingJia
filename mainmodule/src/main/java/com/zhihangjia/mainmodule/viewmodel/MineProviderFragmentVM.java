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
import com.zhihangjia.mainmodule.activity.CaptureActivity;
import com.zhihangjia.mainmodule.activity.MessageCenterActivity;
import com.zhihangjia.mainmodule.activity.OrderListSupplierActivity;
import com.zhihangjia.mainmodule.activity.ShopCouponListActivity;
import com.zhihangjia.mainmodule.callback.MineProviderFragmentCB;
import com.zhixingjia.bean.mainmodule.MessageUnread;
import com.zhixingjia.bean.mainmodule.UserInfo;
import com.zhixingjia.bean.personmodule.Company;
import com.zhixingjia.service.MainService;
import com.zhixingjia.service.PersonService;

import java.util.Map;

/**
 * @author chenbin
 * @version v3.2.0
 * @description 我的--卖家
 * @date 2018/11/20 16:40
 */
public class MineProviderFragmentVM extends BaseVM {
    public static final int REQUEST_CODE_SCAN = 0x0000;// 扫描二维码
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

    public void publishGoodsClick(View view) {
        baseCallBck.doIntentClassName(ActivityNameConfig.ADD_OR_EDIT_PRODUCT_ACTIVITY, null);
    }

    public void goodsManageClick(View view) {
        baseCallBck.doIntentClassName(ActivityNameConfig.GOOD_MANAGE_ACTIVITY, null);
    }

    public void onCompanyClick(View view) {
        getMyCompany();
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

    public void onScan(View view) {
        //动态权限申请
        if (callback.checkPermission()) {
            callback.doIntentForResult(CaptureActivity.class, null, MineProviderFragmentVM.REQUEST_CODE_SCAN);
        }
    }

    public void onMessageClick(View view) {
        callback.doIntent(MessageCenterActivity.class, null);
    }

    @Override
    public void registerRxBus() {
        super.registerRxBus();
        RxBus.getInstance().register(RxEvent.PersonInfoEvent.IDENTIFY_CHANGE, this);
        RxBus.getInstance().register(RxEvent.LoginEvent.LOGOUT, this);
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

    public void getMyCompany() {
        callback.showLoaddingDialog();
        HttpUtils.doHttp(subscription,
                RxRequest.create(PersonService.class, HttpVersionConfig.API_MY_COMPANY).getMyCompany(),
                new ServiceCallback<BaseData<Company>>() {
                    @Override
                    public void onError(Throwable error) {

                    }

                    @Override
                    public void onSuccess(BaseData<Company> companyBaseData) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(IntentConfig.COMPANY, companyBaseData.getData());
                        callback.doIntentClassName(ActivityNameConfig.APPLYSUPPLIER_ACIVITY, bundle);
                    }

                    @Override
                    public void onDefeated(BaseData<Company> companyBaseData) {

                    }
                });
    }

    public void getCouponWriteOff( Map<String, String> map){
        callback.showLoaddingDialog();
        HttpUtils.doHttp(subscription, RxRequest.create(MainService.class, HttpVersionConfig.API_MY_COUPON_WRITE_OFF).getCouponWriteOff(map),
                new ServiceCallback<BaseData>() {
                    @Override
                    public void onError(Throwable error) {

                    }

                    @Override
                    public void onSuccess(BaseData data) {
                        callback.showToast(data.getMessage());
                    }

                    @Override
                    public void onDefeated(BaseData data) {

                    }
                });
    }

    @Override
    public void unRegisterRxBus() {
        super.unRegisterRxBus();
        RxBus.getInstance().unregister(RxEvent.PersonInfoEvent.IDENTIFY_CHANGE, this);
        RxBus.getInstance().unregister(RxEvent.LoginEvent.LOGOUT, this);
    }

    public void onRxEvent(RxEvent event, EventInfo info) {
        switch (event.getType()) {
            case RxEvent.PersonInfoEvent.IDENTIFY_CHANGE:
                getUserInfo();
                break;
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
