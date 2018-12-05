package com.zhihangjia.mainmodule.viewmodel;

import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.commonlib.bean.BaseListData;
import com.nmssdmf.commonlib.callback.BaseRecyclerViewFragmentCB;
import com.nmssdmf.commonlib.config.HttpVersionConfig;
import com.nmssdmf.commonlib.httplib.HttpUtils;
import com.nmssdmf.commonlib.httplib.RxRequest;
import com.nmssdmf.commonlib.httplib.ServiceCallback;
import com.nmssdmf.commonlib.viewmodel.BaseRecyclerViewFragmentVM;
import com.zhixingjia.bean.mainmodule.IndexBean;
import com.zhixingjia.service.MainService;

import java.util.ArrayList;
import java.util.List;

/**
 * 热点新闻viewmodel
 * Create by chenbin on 2018/11/18
 * <p>
 * <p>
 */
public class DailyHotNewsVM extends BaseRecyclerViewFragmentVM {
    private BaseRecyclerViewFragmentCB callback;
    public int types = 0;

    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public DailyHotNewsVM(BaseRecyclerViewFragmentCB callBack) {
        super(callBack);
        this.callback = callBack;
    }

    @Override
    public void initData(boolean isRefresh) {
        HttpUtils.doHttp(subscription,
                RxRequest.create(MainService.class, HttpVersionConfig.API_BBS_INDEX).getBbsIndex(types),
                new ServiceCallback<BaseListData<IndexBean.ForumBean>>() {
                    @Override
                    public void onError(Throwable error) {
                        callback.stopRefreshAnim();
                    }

                    @Override
                    public void onSuccess(BaseListData<IndexBean.ForumBean> base) {
                        callback.refreshAdapter(isRefresh, base.getData());
                    }

                    @Override
                    public void onDefeated(BaseListData<IndexBean.ForumBean> base) {
                        callback.stopRefreshAnim();
                    }
                });
    }
}
