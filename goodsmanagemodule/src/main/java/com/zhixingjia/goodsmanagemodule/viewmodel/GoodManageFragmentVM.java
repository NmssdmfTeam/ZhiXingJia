package com.zhixingjia.goodsmanagemodule.viewmodel;

import android.text.TextUtils;

import com.nmssdmf.commonlib.bean.Base;
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
import com.zhixingjia.service.GoodsManageService;
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
    public GoodManageFragmentVM(GoodManageFragmentCB callBack, int type) {
        super(callBack);
        this.cb = callBack;
        this.type = type;
        if (type == 1)
            RxBus.getInstance().register(RxEvent.GoodsManageEvent.REFRESH_GOODSMANAGE_AVAILABLE, this);
        else
            RxBus.getInstance().register(RxEvent.GoodsManageEvent.REFRESH_GOODSMANAGE_UNAVAILABLE, this);
    }

    @Override
    public void initData(final boolean isRefresh) {
        if (isRefresh)
            page = "0";
        else
            page = cb.getLastPageId();
        final Map<String, String> params = new HashMap<>();
        params.put("status", String.valueOf(type));
        params.put("page", page);
        if (!TextUtils.isEmpty(keyword))
            params.put("keyword", keyword);
        HttpUtils.doHttp(subscription,
                RxRequest.create(GoodsManageService.class, HttpVersionConfig.API_COMMODITY_INDEX).getCommodityIndex(params),
                new ServiceCallback<BaseListData<Commodity>>() {
            @Override
            public void onError(Throwable error) {

            }

            @Override
            public void onSuccess(BaseListData<Commodity> commodityBaseListData) {
                if (isRefresh)
                    RxBus.getInstance().send(RxEvent.GoodsManageEvent.REFRESH_GOODSMANAGE_NUMBER, null);
                baseCB.refreshAdapter(isRefresh, commodityBaseListData.getData());
            }

            @Override
            public void onDefeated(BaseListData<Commodity> commodityBaseListData) {

            }
        });
    }

    /**
     * 商品下架
     */
    public void pullOffGood(Commodity commodity, final int position) {
        baseCallBck.showLoaddingDialog();
        HttpUtils.doHttp(subscription,
                RxRequest.create(GoodsManageService.class, HttpVersionConfig.API_COMMODITY_UPPER_LOWER).commodityUpperLower(commodity.getCommodity_id(), String.valueOf(type==0?1:0)),
                new ServiceCallback<Base>() {
            @Override
            public void onError(Throwable error) {

            }

            @Override
            public void onSuccess(Base base) {
                if (type == 1) {//有商品下架，仓库中商品刷新
                    RxBus.getInstance().send(RxEvent.GoodsManageEvent.REFRESH_GOODSMANAGE_UNAVAILABLE, null);
                } else {
                    RxBus.getInstance().send(RxEvent.GoodsManageEvent.REFRESH_GOODSMANAGE_AVAILABLE, null);
                }
                //移除商品
                cb.removeItem(position);
            }

            @Override
            public void onDefeated(Base base) {

            }
        });
    }

    /**
     * 商品删除
     */
    public void deleteGood(Commodity commodity, final int position) {
        baseCallBck.showLoaddingDialog();
        HttpUtils.doHttp(subscription,
                RxRequest.create(GoodsManageService.class, HttpVersionConfig.API_COMMODITY_DEL).commodityDelete(commodity.getCommodity_id()),
                new ServiceCallback<Base>() {
                    @Override
                    public void onError(Throwable error) {

                    }

                    @Override
                    public void onSuccess(Base base) {
                        //移除商品
                        cb.removeItem(position);
                    }

                    @Override
                    public void onDefeated(Base base) {

                    }
                });
    }

    @Override
    public void registerRxBus() {
        super.registerRxBus();
    }

    @Override
    public void unRegisterRxBus() {
        super.unRegisterRxBus();
        if (type == 1)
            RxBus.getInstance().unregister(RxEvent.GoodsManageEvent.REFRESH_GOODSMANAGE_AVAILABLE, this);
        else
            RxBus.getInstance().unregister(RxEvent.GoodsManageEvent.REFRESH_GOODSMANAGE_UNAVAILABLE, this);
    }

    public void onRxEvent(RxEvent event, EventInfo info) {
        switch (event.getType()) {
            case RxEvent.GoodsManageEvent.REFRESH_GOODSMANAGE_AVAILABLE:
            case RxEvent.GoodsManageEvent.REFRESH_GOODSMANAGE_UNAVAILABLE:
                initData(true);
            break;
        }
    }
}
