package com.zhihangjia.mainmodule.viewmodel;

import com.nmssdmf.commonlib.bean.BaseListData;
import com.nmssdmf.commonlib.config.HttpVersionConfig;
import com.nmssdmf.commonlib.httplib.HttpUtils;
import com.nmssdmf.commonlib.httplib.RxRequest;
import com.nmssdmf.commonlib.httplib.ServiceCallback;
import com.nmssdmf.commonlib.viewmodel.BaseRecyclerViewFragmentVM;
import com.zhihangjia.mainmodule.callback.DailyHotNewsCB;
import com.zhixingjia.bean.mainmodule.IndexBean;
import com.zhixingjia.service.MainService;

import java.util.HashMap;
import java.util.Map;

/**
 * 热点新闻viewmodel
 * Create by chenbin on 2018/11/18
 * <p>
 * <p>
 */
public class DailyHotNewsVM extends BaseRecyclerViewFragmentVM {
    private DailyHotNewsCB callback;
    public int types = 0;
    private String page;

    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public DailyHotNewsVM(DailyHotNewsCB callBack) {
        super(callBack);
        this.callback = callBack;
    }

    @Override
    public void initData(boolean isRefresh) {
        if (isRefresh)
            page = "0";
        else
            page = callback.pages();
        Map<String, String> map = new HashMap<>();
        map.put("types", String.valueOf(types));
        if (types != 0 && types != 2) {
            map.put("pages", page);
        }
        HttpUtils.doHttp(subscription,
                RxRequest.create(MainService.class, HttpVersionConfig.API_BBS_INDEX).getBbsIndex(map),
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
