package com.zhihangjia.mainmodule.viewmodel;

import com.nmssdmf.commonlib.callback.BaseRecyclerViewFragmentCB;
import com.nmssdmf.commonlib.config.HttpVersionConfig;
import com.nmssdmf.commonlib.viewmodel.BaseRecyclerViewFragmentVM;
import com.zhixingjia.bean.mainmodule.IndexBean;
import com.zhixingjia.httplib.HttpUtils;
import com.zhixingjia.httplib.RxRequest;
import com.zhixingjia.service.MainService;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by chenbin on 2018/11/19
 * <p>
 * <p>
 */
public class MessageCenterModuleFragmentVM extends BaseRecyclerViewFragmentVM {
    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public MessageCenterModuleFragmentVM(BaseRecyclerViewFragmentCB callBack) {
        super(callBack);
    }

    @Override
    public void initData(boolean isRefresh) {
        List<IndexBean.ForumBean> list = new ArrayList<>();
        for (int i=0; i < 10; i++) {
            list.add(new IndexBean.ForumBean());
        }
        baseCB.refreshAdapter(isRefresh,list);
    }

    public void getBbsInfoList() {
//        HttpUtils.doHttp(subscription, RxRequest.create(MainService.class, HttpVersionConfig.API_BBS_INFO_LIST));
    }
}
