package com.zhihangjia.mainmodule.viewmodel;

import com.nmssdmf.commonlib.bean.BaseListData;
import com.nmssdmf.commonlib.callback.BaseRecyclerViewFragmentCB;
import com.nmssdmf.commonlib.config.HttpVersionConfig;
import com.nmssdmf.commonlib.config.StringConfig;
import com.nmssdmf.commonlib.httplib.HttpUtils;
import com.nmssdmf.commonlib.httplib.RxRequest;
import com.nmssdmf.commonlib.httplib.ServiceCallback;
import com.nmssdmf.commonlib.viewmodel.BaseRecyclerViewFragmentVM;
import com.zhihangjia.mainmodule.callback.MessageCenterModuleCB;
import com.zhixingjia.bean.mainmodule.BbsInfoList;
import com.zhixingjia.bean.mainmodule.IndexBean;
import com.zhixingjia.service.MainService;

import java.util.ArrayList;
import java.util.List;

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
        getBbsInfoList(types,cate_id,isRefresh);
    }


    public void getBbsInfoList(String types, String cate_id, final boolean isRefresh) {
        if (isRefresh)
            pages = "0";
        HttpUtils.doHttp(subscription, RxRequest.create(MainService.class, HttpVersionConfig.API_BBS_INFO_LIST)
                        .getBbsInfoList(types,cate_id,pages),
                new ServiceCallback<BaseListData<BbsInfoList>>() {
            @Override
            public void onError(Throwable error) {
                callback.stopFreshAction();
            }

            @Override
            public void onSuccess(BaseListData<BbsInfoList> bbsInfoListBaseListData) {
                if (bbsInfoListBaseListData.getData() != null) {
                    callback.setData(bbsInfoListBaseListData.getData(),isRefresh);
                    if (bbsInfoListBaseListData.getData().size() > 0)
                        pages = bbsInfoListBaseListData.getData().get(bbsInfoListBaseListData.getData().size() - 1).getPages();
                }
            }

            @Override
            public void onDefeated(BaseListData<BbsInfoList> bbsInfoListBaseListData) {
                callback.stopFreshAction();
            }
        });
    }
}
