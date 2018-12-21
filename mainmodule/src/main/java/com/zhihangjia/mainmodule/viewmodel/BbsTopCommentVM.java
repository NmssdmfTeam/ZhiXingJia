package com.zhihangjia.mainmodule.viewmodel;

import com.nmssdmf.commonlib.bean.BaseListData;
import com.nmssdmf.commonlib.callback.BaseTitleRecyclerViewCB;
import com.nmssdmf.commonlib.config.HttpVersionConfig;
import com.nmssdmf.commonlib.httplib.HttpUtils;
import com.nmssdmf.commonlib.httplib.RxRequest;
import com.nmssdmf.commonlib.httplib.ServiceCallback;
import com.nmssdmf.commonlib.viewmodel.BaseTitleRecyclerViewVM;
import com.zhixingjia.bean.mainmodule.BbsInfoList;
import com.zhixingjia.bean.mainmodule.IndexBean;
import com.zhixingjia.service.MainService;

import java.util.HashMap;
import java.util.Map;

/**
* @description 精华置顶列表
* @author chenbin
* @date 2018/12/21 13:44
* @version v3.2.0
*/
public class BbsTopCommentVM extends BaseTitleRecyclerViewVM {
    private String page = "0";

    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public BbsTopCommentVM(BaseTitleRecyclerViewCB callBack) {
        super(callBack);
    }

    @Override
    public void initData(boolean isRefresh) {
        if (isRefresh)
            page = "0";
        else {
            if (list.size() > 0)
                page = ((BbsInfoList)list.get(list.size() - 1)).getBbs_id();
        }
        Map<String,String> map = new HashMap<>();
        map.put("types", "2");
        map.put("pages", page);
        HttpUtils.doHttp(subscription,
                RxRequest.create(MainService.class, HttpVersionConfig.API_BBS_INFO_LIST).getBbsSearchInfo(map),
                new ServiceCallback<BaseListData<IndexBean.ForumBean>>() {
            @Override
            public void onError(Throwable error) {

            }

            @Override
            public void onSuccess(BaseListData<IndexBean.ForumBean> bbsInfoListBaseListData) {
                baseTitleRecyclerViewCB.refreshAdapter(isRefresh, bbsInfoListBaseListData.getData());
            }

            @Override
            public void onDefeated(BaseListData<IndexBean.ForumBean> bbsInfoListBaseListData) {

            }
        });
    }
}
