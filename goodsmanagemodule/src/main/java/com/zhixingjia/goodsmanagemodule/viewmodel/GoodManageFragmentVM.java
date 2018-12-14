package com.zhixingjia.goodsmanagemodule.viewmodel;

import android.text.TextUtils;

import com.nmssdmf.commonlib.bean.BaseListData;
import com.nmssdmf.commonlib.config.HttpVersionConfig;
import com.nmssdmf.commonlib.httplib.HttpUtils;
import com.nmssdmf.commonlib.httplib.RxRequest;
import com.nmssdmf.commonlib.httplib.ServiceCallback;
import com.nmssdmf.commonlib.rxbus.EventInfo;
import com.nmssdmf.commonlib.rxbus.RxBus;
import com.nmssdmf.commonlib.rxbus.RxEvent;
import com.nmssdmf.commonlib.viewmodel.BaseRecyclerViewFragmentVM;
import com.zhixingjia.bean.mainmodule.Commodity;
import com.zhixingjia.goodsmanagemodule.callback.GoodManageFragmentCB;
import com.zhixingjia.service.MainService;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ${nmssdmf} on 2018/11/21 0021.
 */

public class GoodManageFragmentVM extends BaseRecyclerViewFragmentVM {
    private GoodManageFragmentCB cb;
    private String page = "0";
    public int type = 0; //类型 1=出售中(上架中的商品) 0=仓库中(下架中的商品)
    public String keyword;
    public static final int GOODMANAGE_ONOFFER = 1;
    public static final int GOODMANAGE_OUTOFFER = 0;

    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public GoodManageFragmentVM(GoodManageFragmentCB callBack) {
        super(callBack);
        cb = callBack;
    }

    @Override
    public void initData(final boolean isRefresh) {
        if (isRefresh)
            page = "0";
        final Map<String, String> params = new HashMap<>();
        params.put("status", String.valueOf(type));
        params.put("page", page);
        if (!TextUtils.isEmpty(keyword))
            params.put("keyword", keyword);
        HttpUtils.doHttp(subscription,
                RxRequest.create(MainService.class, HttpVersionConfig.API_COMMODITY_INDEX).getCommodityIndex(params),
                new ServiceCallback<BaseListData<Commodity>>() {
            @Override
            public void onError(Throwable error) {

            }

            @Override
            public void onSuccess(BaseListData<Commodity> commodityBaseListData) {
                if (isRefresh)
                    RxBus.getInstance().send(RxEvent.GoodsManageEvent.REFRESH_GOODSMANAGE_NUMBER, null);
                baseCB.refreshAdapter(isRefresh, commodityBaseListData.getData());
                if (commodityBaseListData.getData() != null && commodityBaseListData.getData().size() > 0) {
                    page = commodityBaseListData.getData().get(commodityBaseListData.getData().size() - 1).getCommodity_id();
                }
            }

            @Override
            public void onDefeated(BaseListData<Commodity> commodityBaseListData) {

            }
        });
    }
}
