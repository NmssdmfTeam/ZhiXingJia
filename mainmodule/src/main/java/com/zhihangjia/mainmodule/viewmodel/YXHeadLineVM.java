package com.zhihangjia.mainmodule.viewmodel;

import com.nmssdmf.commonlib.bean.BaseListData;
import com.nmssdmf.commonlib.callback.BaseTitleRecyclerViewCB;
import com.nmssdmf.commonlib.config.HttpVersionConfig;
import com.nmssdmf.commonlib.httplib.HttpUtils;
import com.nmssdmf.commonlib.httplib.RxRequest;
import com.nmssdmf.commonlib.httplib.ServiceCallback;
import com.nmssdmf.commonlib.viewmodel.BaseTitleRecyclerViewVM;
import com.zhixingjia.bean.mainmodule.New;
import com.zhixingjia.service.MainService;

/**
 * @author chenbin
 * @version v3.2.0
 * @description 宜兴头条viewmodel
 * @date 2018/11/16 15:29
 */
public class YXHeadLineVM extends BaseTitleRecyclerViewVM {
    private BaseTitleRecyclerViewCB cb;

    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public YXHeadLineVM(BaseTitleRecyclerViewCB callBack) {
        super(callBack);
        cb = callBack;
    }

    @Override
    public void initData(boolean isRefresh) {
        cb.showLoaddingDialog();
        String page = "0";
        if (isRefresh || list.size() == 0) {

        } else {
            page = ((New)list.get(list.size() -1)).getId();
        }

        HttpUtils.doHttp(subscription, RxRequest.create(MainService.class, HttpVersionConfig.API_NEWS).getNews("0", String.valueOf(page)), new ServiceCallback<BaseListData<New>>() {
            @Override
            public void onError(Throwable error) {

            }

            @Override
            public void onSuccess(BaseListData<New> data) {
                cb.refreshAdapter(isRefresh, data.getData());
            }

            @Override
            public void onDefeated(BaseListData<New> data) {

            }
        });
    }
}
