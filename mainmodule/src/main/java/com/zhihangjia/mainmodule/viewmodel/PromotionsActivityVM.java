package com.zhihangjia.mainmodule.viewmodel;

import com.nmssdmf.commonlib.bean.BaseData;
import com.nmssdmf.commonlib.bean.BaseListData;
import com.nmssdmf.commonlib.callback.BaseRecyclerViewFragmentCB;
import com.nmssdmf.commonlib.config.HttpVersionConfig;
import com.nmssdmf.commonlib.httplib.HttpUtils;
import com.nmssdmf.commonlib.httplib.RxRequest;
import com.nmssdmf.commonlib.httplib.ServiceCallback;
import com.nmssdmf.commonlib.viewmodel.BaseRecyclerViewFragmentVM;
import com.zhihangjia.mainmodule.callback.PromotionsCB;
import com.zhixingjia.bean.mainmodule.Banner;
import com.zhixingjia.bean.mainmodule.Promotion;
import com.zhixingjia.service.MainService;

/**
* @description 展示中心--促销活动viewmodel
* @author chenbin
* @date 2019/1/21 17:36
* @version v3.2.0
*/
public class PromotionsActivityVM extends BaseRecyclerViewFragmentVM {
    private int pages = 1;
    private PromotionsCB callback;

    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public PromotionsActivityVM(PromotionsCB callBack) {
        super(callBack);
        this.callback = callBack;
    }

    @Override
    public void initData(boolean isRefresh) {
        if (isRefresh)
            pages = 1;
        HttpUtils.doHttp(subscription,
                RxRequest.create(MainService.class, HttpVersionConfig.API_ZHANSHI_PROMOTION).getPromotion(String.valueOf(pages)),
                new ServiceCallback<BaseListData<Promotion>>() {
            @Override
            public void onError(Throwable error) {

            }

            @Override
            public void onSuccess(BaseListData<Promotion> promotionBaseListData) {
                baseCB.refreshAdapter(isRefresh, promotionBaseListData.getData());
                pages++;
            }

            @Override
            public void onDefeated(BaseListData<Promotion> promotionBaseListData) {

            }
        });
    }

    /**
     * 获取头部广告
     */
    public void getBanners() {
        HttpUtils.doHttp(subscription,
                RxRequest.create(MainService.class, HttpVersionConfig.API_BANNERS).getBanner("zs"),
                new ServiceCallback<BaseData<Banner>>() {
            @Override
            public void onError(Throwable error) {

            }

            @Override
            public void onSuccess(BaseData<Banner> bannerBaseData) {
                callback.setHeadV(bannerBaseData.getData().getBanner_top());
            }

            @Override
            public void onDefeated(BaseData<Banner> bannerBaseData) {

            }
        });
    }
}
