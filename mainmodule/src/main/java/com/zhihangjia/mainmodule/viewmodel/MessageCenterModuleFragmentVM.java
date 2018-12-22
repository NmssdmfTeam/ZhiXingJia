package com.zhihangjia.mainmodule.viewmodel;

import com.nmssdmf.commonlib.bean.BaseListData;
import com.nmssdmf.commonlib.config.HttpVersionConfig;
import com.nmssdmf.commonlib.httplib.HttpUtils;
import com.nmssdmf.commonlib.httplib.RxRequest;
import com.nmssdmf.commonlib.httplib.ServiceCallback;
import com.nmssdmf.commonlib.rxbus.EventInfo;
import com.nmssdmf.commonlib.rxbus.RxBus;
import com.nmssdmf.commonlib.rxbus.RxEvent;
import com.nmssdmf.commonlib.viewmodel.BaseRecyclerViewFragmentVM;
import com.zhihangjia.mainmodule.callback.MessageCenterModuleCB;
import com.zhixingjia.bean.mainmodule.IndexBean;
import com.zhixingjia.service.MainService;

/**
 * Create by chenbin on 2018/11/19
 * <p>
 * <p>
 */
public class MessageCenterModuleFragmentVM extends BaseRecyclerViewFragmentVM {
    private String pages;       //分页需要传输的值
    private MessageCenterModuleCB callback;
    public String types;
    public String cate_id = "1";

    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public MessageCenterModuleFragmentVM(MessageCenterModuleCB callBack) {
        super(callBack);
        this.callback = callBack;
    }

    @Override
    public void initData(boolean isRefresh) {
        getBbsInfoList(types, cate_id, isRefresh);
    }


    public void getBbsInfoList(String types, String cate_id, final boolean isRefresh) {
        if (isRefresh)
            pages = "0";
        HttpUtils.doHttp(subscription, RxRequest.create(MainService.class, HttpVersionConfig.API_BBS_INFO_LIST)
                        .getBbsInfoList(types, cate_id, pages),
                new ServiceCallback<BaseListData<IndexBean.ForumBean>>() {
                    @Override
                    public void onError(Throwable error) {
                        callback.stopFreshAction();
                    }

                    @Override
                    public void onSuccess(BaseListData<IndexBean.ForumBean> bbsInfoListBaseListData) {
                        if (bbsInfoListBaseListData.getData() != null) {
                            callback.setData(bbsInfoListBaseListData.getData(), isRefresh);
                            if (bbsInfoListBaseListData.getData().size() > 0)
                                pages = bbsInfoListBaseListData.getData().get(bbsInfoListBaseListData.getData().size() - 1).getPages();
                        }
                    }

                    @Override
                    public void onDefeated(BaseListData<IndexBean.ForumBean> bbsInfoListBaseListData) {
                        callback.stopFreshAction();
                    }
                });
    }

    @Override
    public void registerRxBus() {
        super.registerRxBus();
        RxBus.getInstance().register(RxEvent.BbsEvent.BBS_INSERT, this);
        RxBus.getInstance().register(RxEvent.BbsEvent.COMMENT_INSERT, this);
    }

    @Override
    public void unRegisterRxBus() {
        super.unRegisterRxBus();
        RxBus.getInstance().unregister(RxEvent.BbsEvent.BBS_INSERT, this);
        RxBus.getInstance().unregister(RxEvent.BbsEvent.COMMENT_INSERT, this);
    }

    public void onRxEvent(RxEvent event, EventInfo info) {
        if ("1".equals(types)) {//最新发布
            switch (event.getType()) {
                case RxEvent.BbsEvent.BBS_INSERT:
                    getBbsInfoList(types, cate_id, true);
                    break;
            }
        } else {//最新回复,精华热帖
            switch (event.getType()) {
                case RxEvent.BbsEvent.COMMENT_INSERT:
                    getBbsInfoList(types, cate_id, true);
                    break;
            }
        }
    }
}
