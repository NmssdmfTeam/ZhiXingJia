package com.zhihangjia.mainmodule.viewmodel;

import com.nmssdmf.commonlib.bean.BaseData;
import com.nmssdmf.commonlib.callback.BaseCB;
import com.nmssdmf.commonlib.config.HttpVersionConfig;
import com.nmssdmf.commonlib.httplib.HttpUtils;
import com.nmssdmf.commonlib.httplib.RxRequest;
import com.nmssdmf.commonlib.httplib.ServiceCallback;
import com.nmssdmf.commonlib.rxbus.EventInfo;
import com.nmssdmf.commonlib.rxbus.RxBus;
import com.nmssdmf.commonlib.rxbus.RxEvent;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.callback.MessageCenterCB;
import com.zhixingjia.bean.mainmodule.MessageUnread;
import com.zhixingjia.service.MainService;

public class MessageCenterVM extends BaseVM {
    public MessageUnread messageUnread;
    private MessageCenterCB callback;

    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public MessageCenterVM(MessageCenterCB callBack) {
        super(callBack);
        this.callback = callBack;
        getMessageUnread();
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
                messageUnread = messageUnreadBaseData.getData();
                //设置是否显示小红点
                callback.showNotice();
            }

            @Override
            public void onDefeated(BaseData<MessageUnread> messageUnreadBaseData) {

            }
        });
    }

    @Override
    public void registerRxBus() {
        super.registerRxBus();
        RxBus.getInstance().register(RxEvent.PersonInfoEvent.SYSTEM_NOTIFICATION_READED, this);
        RxBus.getInstance().register(RxEvent.PersonInfoEvent.ORDER_NOTIFICATION_READED, this);
    }

    @Override
    public void unRegisterRxBus() {
        super.unRegisterRxBus();
        RxBus.getInstance().unregister(RxEvent.PersonInfoEvent.SYSTEM_NOTIFICATION_READED, this);
        RxBus.getInstance().unregister(RxEvent.PersonInfoEvent.ORDER_NOTIFICATION_READED, this);
    }

    public void onRxEvent(RxEvent event, EventInfo info) {
        switch (event.getType()) {
            case RxEvent.PersonInfoEvent.SYSTEM_NOTIFICATION_READED:
                messageUnread.setSys_message("0");
                messageUnread.setAll_message(messageUnread.getOrder_message());
                callback.showNotice();
                break;
            case RxEvent.PersonInfoEvent.ORDER_NOTIFICATION_READED:
                messageUnread.setOrder_message("0");
                messageUnread.setAll_message(messageUnread.getSys_message());
                callback.showNotice();
                break;
        }
    }
}
