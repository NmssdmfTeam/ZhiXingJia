package com.zhixingjia.personmodule.viewmodule;

import android.os.Bundle;

import com.nmssdmf.commonlib.bean.BaseListData;
import com.nmssdmf.commonlib.config.HttpVersionConfig;
import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.config.StringConfig;
import com.nmssdmf.commonlib.httplib.HttpUtils;
import com.nmssdmf.commonlib.httplib.RxRequest;
import com.nmssdmf.commonlib.httplib.ServiceCallback;
import com.nmssdmf.commonlib.rxbus.EventInfo;
import com.nmssdmf.commonlib.rxbus.RxBus;
import com.nmssdmf.commonlib.rxbus.RxEvent;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhixingjia.bean.personmodule.Address;
import com.zhixingjia.personmodule.callback.ManageAddressCB;
import com.zhixingjia.service.PersonService;

/**
 * Create by chenbin on 2018/11/20
 * <p>
 * <p>
 */
public class ManageAddressListVM extends BaseVM {
    private int page = 0;
    private ManageAddressCB callback;
    public boolean isSelect;

    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public ManageAddressListVM(ManageAddressCB callBack) {
        super(callBack);
        this.callback = callBack;
        initData();
    }

    private void initData() {
        Bundle bundle = callback.getIntentData();
        if (bundle != null) {
            isSelect = bundle.getBoolean(IntentConfig.IS_SELECT,false);
        }
    }

    public void getAddressList(final boolean isRefresh) {
        if (isRefresh)
            page = 0;
        HttpUtils.doHttp(subscription,
                RxRequest.create(PersonService.class, HttpVersionConfig.API_ADDRESS).getCommentList(page),
                new ServiceCallback<BaseListData<Address>>() {
            @Override
            public void onError(Throwable error) {
                callback.endFresh();
            }

            @Override
            public void onSuccess(BaseListData<Address> addressBaseListData) {
                callback.endFresh();
                if (StringConfig.OK.equals(addressBaseListData.getStatus_code())) {
                    callback.setData(addressBaseListData.getData(),isRefresh);
                    page++;
                }
            }

            @Override
            public void onDefeated(BaseListData<Address> addressBaseListData) {
                callback.endFresh();
            }
        });
    }

    @Override
    public void registerRxBus() {
        super.registerRxBus();
        RxBus.getInstance().register(RxEvent.PersonInfoEvent.ADDRESS_INSERT, this);
        RxBus.getInstance().register(RxEvent.PersonInfoEvent.ADDRESS_SAVE, this);
        RxBus.getInstance().register(RxEvent.PersonInfoEvent.ADDRESS_DELETE, this);
    }

    @Override
    public void unRegisterRxBus() {
        super.unRegisterRxBus();
        RxBus.getInstance().unregister(RxEvent.PersonInfoEvent.ADDRESS_INSERT, this);
        RxBus.getInstance().unregister(RxEvent.PersonInfoEvent.ADDRESS_SAVE, this);
        RxBus.getInstance().unregister(RxEvent.PersonInfoEvent.ADDRESS_DELETE, this);
    }

    public void onRxEvent(RxEvent event, EventInfo info) {
        switch (event.getType()) {
            case RxEvent.PersonInfoEvent.ADDRESS_INSERT:
                getAddressList(true);
                break;
            case RxEvent.PersonInfoEvent.ADDRESS_SAVE:
                Address address = (Address) info.getContent();
                callback.setAddress(address, info.getIndex());
                break;
            case RxEvent.PersonInfoEvent.ADDRESS_DELETE:
                int postion = info.getIndex();
                callback.deleteAddress(postion);
                break;
        }
    }

}
