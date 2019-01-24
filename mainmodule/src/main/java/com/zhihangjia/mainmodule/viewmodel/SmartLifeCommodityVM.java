package com.zhihangjia.mainmodule.viewmodel;

import android.os.Bundle;

import com.nmssdmf.commonlib.bean.BaseListData;
import com.nmssdmf.commonlib.callback.BaseTitleRecyclerViewCB;
import com.nmssdmf.commonlib.config.HttpVersionConfig;
import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.httplib.HttpUtils;
import com.nmssdmf.commonlib.httplib.RxRequest;
import com.nmssdmf.commonlib.httplib.ServiceCallback;
import com.nmssdmf.commonlib.viewmodel.BaseTitleRecyclerViewVM;
import com.zhixingjia.bean.mainmodule.YXTelecom;
import com.zhixingjia.service.MainService;

public class SmartLifeCommodityVM extends BaseTitleRecyclerViewVM {
    private String category;
    private int page = 1;

    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public SmartLifeCommodityVM(BaseTitleRecyclerViewCB callBack) {
        super(callBack);
        initData();
    }

    private void initData() {
        Bundle bundle = baseCallBck.getIntentData();
        if (bundle != null) {
            category = bundle.getString(IntentConfig.CAT_ID);
        }
    }

    @Override
    public void initData(boolean isRefresh) {
        if (isRefresh)
            page = 1;
        HttpUtils.doHttp(subscription,
                RxRequest.create(MainService.class, HttpVersionConfig.API_DX_COMMODITY_LIST).getDxCommodity(String.valueOf(page), category),
                new ServiceCallback<BaseListData<YXTelecom.CommodityBean.CommodityInfoBean>>() {
                    @Override
                    public void onError(Throwable error) {

                    }

                    @Override
                    public void onSuccess(BaseListData<YXTelecom.CommodityBean.CommodityInfoBean> commodityInfoBeanBaseListData) {
                        baseTitleRecyclerViewCB.refreshAdapter(isRefresh, commodityInfoBeanBaseListData.getData());
                        page++;
                    }

                    @Override
                    public void onDefeated(BaseListData<YXTelecom.CommodityBean.CommodityInfoBean> commodityInfoBeanBaseListData) {

                    }
                });
    }
}
