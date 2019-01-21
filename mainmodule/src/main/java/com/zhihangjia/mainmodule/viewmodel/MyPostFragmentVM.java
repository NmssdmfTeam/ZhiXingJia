package com.zhihangjia.mainmodule.viewmodel;


import com.nmssdmf.commonlib.bean.BaseListData;
import com.nmssdmf.commonlib.callback.BaseRecyclerViewFragmentCB;
import com.nmssdmf.commonlib.config.HttpVersionConfig;
import com.nmssdmf.commonlib.httplib.HttpUtils;
import com.nmssdmf.commonlib.httplib.RxRequest;
import com.nmssdmf.commonlib.httplib.ServiceCallback;
import com.nmssdmf.commonlib.rxbus.EventInfo;
import com.nmssdmf.commonlib.rxbus.RxBus;
import com.nmssdmf.commonlib.rxbus.RxEvent;
import com.nmssdmf.commonlib.viewmodel.BaseRecyclerViewFragmentVM;
import com.zhihangjia.mainmodule.callback.MyPostCB;
import com.zhixingjia.bean.personmodule.Placard;
import com.zhixingjia.service.MainService;

/**
* @description 我的发帖viewmodel
* @author chenbin
* @date 2018/11/20 17:43
* @version v3.2.0
*/
public class MyPostFragmentVM extends BaseRecyclerViewFragmentVM {
    public String page = "0";
    private MyPostCB cb;

    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public MyPostFragmentVM(MyPostCB callBack) {
        super(callBack);
        this.cb = callBack;
    }

    @Override
    public void initData(boolean isRefresh) {
        if (isRefresh)
            page = "0";
        HttpUtils.doHttp(subscription,
                RxRequest.create(MainService.class, HttpVersionConfig.API_MY_PLACARD).getPlacard(page),
                new ServiceCallback<BaseListData<Placard>>() {
            @Override
            public void onError(Throwable error) {

            }

            @Override
            public void onSuccess(BaseListData<Placard> placardBaseListData) {
                if (placardBaseListData.getData().size() > 0)
                    page = placardBaseListData.getData().get(placardBaseListData.getData().size() - 1).getBbs_id();
                baseCB.refreshAdapter(isRefresh,placardBaseListData.getData());
            }

            @Override
            public void onDefeated(BaseListData<Placard> placardBaseListData) {

            }
        });

    }

    @Override
    public void registerRxBus() {
        super.registerRxBus();
        RxBus.getInstance().register(RxEvent.BbsEvent.BBS_DELETE, this);
        RxBus.getInstance().register(RxEvent.BbsEvent.BBS_BLACK, this);
    }

    @Override
    public void unRegisterRxBus() {
        super.unRegisterRxBus();
        RxBus.getInstance().unregister(RxEvent.BbsEvent.BBS_DELETE, this);
        RxBus.getInstance().unregister(RxEvent.BbsEvent.BBS_BLACK, this);
    }

    public void onRxEvent(RxEvent event, EventInfo info) {
        switch (event.getType()) {
            case RxEvent.BbsEvent.BBS_DELETE:
                if (info != null) {
                    int position = info.getIndex();
                    cb.removeItemNotify(position);
                } else {
                    initData(true);
                }
                break;
            case RxEvent.BbsEvent.BBS_BLACK:
                initData(true);
                break;
        }
    }
}
