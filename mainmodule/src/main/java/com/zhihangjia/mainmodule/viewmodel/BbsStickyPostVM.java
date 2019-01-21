package com.zhihangjia.mainmodule.viewmodel;

import android.os.Bundle;

import com.nmssdmf.commonlib.bean.BaseListData;
import com.nmssdmf.commonlib.callback.BaseRecyclerViewFragmentCB;
import com.nmssdmf.commonlib.callback.BaseTitleRecyclerViewCB;
import com.nmssdmf.commonlib.config.HttpVersionConfig;
import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.httplib.HttpUtils;
import com.nmssdmf.commonlib.httplib.RxRequest;
import com.nmssdmf.commonlib.httplib.ServiceCallback;
import com.nmssdmf.commonlib.rxbus.EventInfo;
import com.nmssdmf.commonlib.rxbus.RxBus;
import com.nmssdmf.commonlib.rxbus.RxEvent;
import com.nmssdmf.commonlib.viewmodel.BaseRecyclerViewFragmentVM;
import com.nmssdmf.commonlib.viewmodel.BaseTitleRecyclerViewVM;
import com.zhihangjia.mainmodule.callback.BbsStickPostCB;
import com.zhixingjia.bean.mainmodule.IndexBean;
import com.zhixingjia.service.MainService;

import java.util.HashMap;
import java.util.Map;

/**
* @description 精华帖子
* @author chenbin
* @date 2018/12/6 16:49
* @version v3.2.0
*/
public class BbsStickyPostVM extends BaseTitleRecyclerViewVM {
    public String page = "0";
    private String type = "1";
    private String keyword;
    private BbsStickPostCB cb;

    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public BbsStickyPostVM(BbsStickPostCB callBack) {
        super(callBack);
        this.cb = callBack;
        initData();
    }

    private void initData() {
        Bundle bundle = baseCallBck.getIntentData();
        if (bundle != null) {
            type = bundle.getString(IntentConfig.TYPE);
            keyword = bundle.getString(IntentConfig.KEYWORD);
        }
    }

    @Override
    public void initData(boolean isRefresh) {
        if (isRefresh)
            page = "0";
        Map<String, String> params = new HashMap<>();
        params.put("types", type);
        if ("1".equals(type)) {//1=搜索帖子
            params.put("keyword", keyword);
        }
        params.put("pages", page);
        HttpUtils.doHttp(subscription,
                RxRequest.create(MainService.class, HttpVersionConfig.API_BBS_SEARCHINFO).getBbsSearchInfo(params),
                new ServiceCallback<BaseListData<IndexBean.ForumBean>>() {
            @Override
            public void onError(Throwable error) {

            }

            @Override
            public void onSuccess(BaseListData<IndexBean.ForumBean> forumBeanBaseListData) {
                baseTitleRecyclerViewCB.refreshAdapter(isRefresh, forumBeanBaseListData.getData());
                if (forumBeanBaseListData.getData() != null && forumBeanBaseListData.getData().size() > 0) {
                    page = forumBeanBaseListData.getData().get(forumBeanBaseListData.getData().size() - 1).getBbs_id();
                }
            }

            @Override
            public void onDefeated(BaseListData<IndexBean.ForumBean> forumBeanBaseListData) {

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
