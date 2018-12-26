package com.zhihangjia.mainmodule.viewmodel.message;

import com.nmssdmf.commonlib.bean.BaseListData;
import com.nmssdmf.commonlib.callback.BaseRecyclerViewFragmentCB;
import com.nmssdmf.commonlib.config.HttpVersionConfig;
import com.nmssdmf.commonlib.httplib.HttpUtils;
import com.nmssdmf.commonlib.httplib.RxRequest;
import com.nmssdmf.commonlib.httplib.ServiceCallback;
import com.nmssdmf.commonlib.rxbus.RxBus;
import com.nmssdmf.commonlib.rxbus.RxEvent;
import com.nmssdmf.commonlib.viewmodel.BaseRecyclerViewFragmentVM;
import com.zhixingjia.bean.mainmodule.Message;
import com.zhixingjia.service.MainService;

import java.util.HashMap;
import java.util.Map;

public class SystemMessageFragmentVM extends BaseRecyclerViewFragmentVM {
    private String page = "0";

    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public SystemMessageFragmentVM(BaseRecyclerViewFragmentCB callBack) {
        super(callBack);
    }

    @Override
    public void initData(boolean isRefresh) {
        if (isRefresh)
            page = "0";
        Map<String, String> map = new HashMap<>();
        map.put("types", "0");
        map.put("page", page);
        HttpUtils.doHttp(subscription,
                RxRequest.create(MainService.class, HttpVersionConfig.API_MESSAGE).getMessage(map),
                new ServiceCallback<BaseListData<Message>>() {
                    @Override
                    public void onError(Throwable error) {

                    }

                    @Override
                    public void onSuccess(BaseListData<Message> messageBaseListData) {
                        baseCB.refreshAdapter(isRefresh, messageBaseListData.getData());
                        RxBus.getInstance().send(RxEvent.PersonInfoEvent.SYSTEM_NOTIFICATION_READED, null);
                        if (messageBaseListData != null && messageBaseListData.getData().size() > 0) {
                            page = messageBaseListData.getData().get(messageBaseListData.getData().size() - 1).getId();
                        }
                    }

                    @Override
                    public void onDefeated(BaseListData<Message> messageBaseListData) {

                    }
                });
    }
}
