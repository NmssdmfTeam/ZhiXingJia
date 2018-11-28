package com.zhihangjia.mainmodule.viewmodel;

import com.nmssdmf.commonlib.callback.BaseCB;
import com.nmssdmf.commonlib.config.ActivityNameConfig;
import com.nmssdmf.commonlib.rxbus.EventInfo;
import com.nmssdmf.commonlib.rxbus.RxBus;
import com.nmssdmf.commonlib.rxbus.RxEvent;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.callback.MainCB;

/**
* @description 首页viewmodel
* @author chenbin
* @date 2018/11/13 15:23
* @version v3.2.0
*/
public class MainVM extends BaseVM {
    private MainCB callback;

    public String identify = "buyer";
    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public MainVM(MainCB callBack) {
        super(callBack);
        this.callback = callBack;
    }

    @Override
    public void registerRxBus() {
        super.registerRxBus();
        RxBus.getInstance().register(RxEvent.BbsEvent.INDEX_SWITCH, this);
        RxBus.getInstance().register(RxEvent.LoginEvent.RE_LOGIN, this);
        RxBus.getInstance().register(RxEvent.LoginEvent.LOGOUT, this);
    }

    @Override
    public void unRegisterRxBus() {
        super.unRegisterRxBus();
        RxBus.getInstance().unregister(RxEvent.BbsEvent.INDEX_SWITCH, this);
        RxBus.getInstance().unregister(RxEvent.LoginEvent.RE_LOGIN, this);
        RxBus.getInstance().unregister(RxEvent.LoginEvent.LOGOUT, this);
    }

    public void onRxEvent(RxEvent event, EventInfo info) {
        switch (event.getType()) {
            case RxEvent.BbsEvent.INDEX_SWITCH:
                callback.switchFragment(info.getIndex());
                break;
            case RxEvent.LoginEvent.RE_LOGIN:
                callback.doIntentClassName(ActivityNameConfig.LOGIN_ACTIVITY, null);
                break;
            case RxEvent.LoginEvent.LOGOUT:
                callback.switchFragment(0);
                break;
        }
    }
}
