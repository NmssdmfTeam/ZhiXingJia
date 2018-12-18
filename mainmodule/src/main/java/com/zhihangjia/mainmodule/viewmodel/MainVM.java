package com.zhihangjia.mainmodule.viewmodel;

import com.nmssdmf.commonlib.bean.BaseData;
import com.nmssdmf.commonlib.config.ActivityNameConfig;
import com.nmssdmf.commonlib.config.HttpVersionConfig;
import com.nmssdmf.commonlib.config.PrefrenceConfig;
import com.nmssdmf.commonlib.httplib.HttpUtils;
import com.nmssdmf.commonlib.httplib.RxRequest;
import com.nmssdmf.commonlib.httplib.ServiceCallback;
import com.nmssdmf.commonlib.rxbus.EventInfo;
import com.nmssdmf.commonlib.rxbus.RxBus;
import com.nmssdmf.commonlib.rxbus.RxEvent;
import com.nmssdmf.commonlib.util.PreferenceUtil;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.callback.MainCB;
import com.zhixingjia.bean.mainmodule.AllSum;
import com.zhixingjia.service.MainService;

/**
* @description 首页viewmodel
* @author chenbin
* @date 2018/11/13 15:23
* @version v3.2.0
*/
public class MainVM extends BaseVM {
    private MainCB callback;

    public String identify;
    private String ShopCarNum;
    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public MainVM(MainCB callBack) {
        super(callBack);
        this.callback = callBack;
        identify = PreferenceUtil.getString(PrefrenceConfig.IDENTIFY, "buyer");
    }

    @Override
    public void registerRxBus() {
        super.registerRxBus();
        RxBus.getInstance().register(RxEvent.BbsEvent.INDEX_SWITCH, this);
        RxBus.getInstance().register(RxEvent.LoginEvent.RE_LOGIN, this);
        RxBus.getInstance().register(RxEvent.LoginEvent.LOGOUT, this);
        RxBus.getInstance().register(RxEvent.LoginEvent.LOGIN_SUCCESS, this);
        RxBus.getInstance().register(RxEvent.OrderEvent.SHOPCAR_DELETE, this);
        RxBus.getInstance().register(RxEvent.OrderEvent.SHOP_CAR_CONFIRM_ORDER, this);
        RxBus.getInstance().register(RxEvent.OrderEvent.SHOPCAR_ADD,this);
    }

    @Override
    public void unRegisterRxBus() {
        super.unRegisterRxBus();
        RxBus.getInstance().unregister(RxEvent.BbsEvent.INDEX_SWITCH, this);
        RxBus.getInstance().unregister(RxEvent.LoginEvent.RE_LOGIN, this);
        RxBus.getInstance().unregister(RxEvent.LoginEvent.LOGOUT, this);
        RxBus.getInstance().unregister(RxEvent.LoginEvent.LOGIN_SUCCESS, this);
        RxBus.getInstance().unregister(RxEvent.OrderEvent.SHOPCAR_DELETE, this);
        RxBus.getInstance().unregister(RxEvent.OrderEvent.SHOP_CAR_CONFIRM_ORDER, this);
        RxBus.getInstance().unregister(RxEvent.OrderEvent.SHOPCAR_ADD,this);
    }

    /**
     * 获取购物车数量
     */
    public void getShopCarAllNum() {
        HttpUtils.doHttp(subscription,
                RxRequest.create(MainService.class, HttpVersionConfig.API_CART_ALLSUM).getAllSum(),
                new ServiceCallback<BaseData<AllSum>>() {
            @Override
            public void onError(Throwable error) {

            }

            @Override
            public void onSuccess(BaseData<AllSum> allSumBaseData) {
                try {
                    ShopCarNum = allSumBaseData.getData().getAllsum();
                    if (Integer.valueOf(ShopCarNum) > 99) {
                        ShopCarNum = "99";
                    }
                    callback.setShopCarNumber(ShopCarNum);
                } catch (Exception e) {

                }
            }

            @Override
            public void onDefeated(BaseData<AllSum> allSumBaseData) {

            }
        });
    }

    public void onRxEvent(RxEvent event, EventInfo info) {
        switch (event.getType()) {
            case RxEvent.BbsEvent.INDEX_SWITCH:
                callback.switchFragment(info.getIndex());
                break;
            case RxEvent.LoginEvent.RE_LOGIN:
                callback.switchFragment(0);
                callback.doIntentClassName(ActivityNameConfig.LOGIN_ACTIVITY, null);
                break;
            case RxEvent.LoginEvent.LOGOUT:
                callback.switchFragment(0);
                callback.setShopCarNumber("0");
                break;
            case RxEvent.LoginEvent.LOGIN_SUCCESS:
                callback.initTab();
                getShopCarAllNum();
                break;
            case RxEvent.OrderEvent.SHOPCAR_DELETE:
            case RxEvent.OrderEvent.SHOPCAR_ADD:
            case RxEvent.OrderEvent.SHOP_CAR_CONFIRM_ORDER:
                getShopCarAllNum();
                break;
        }
    }

    public String getShopCarNum() {
        return ShopCarNum;
    }

    public void setShopCarNum(String shopCarNum) {
        ShopCarNum = shopCarNum;
    }
}
