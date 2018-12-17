package com.zhixingjia.goodsmanagemodule.viewmodel;

import android.databinding.ObservableField;

import com.nmssdmf.commonlib.bean.BaseData;
import com.nmssdmf.commonlib.callback.BaseCB;
import com.nmssdmf.commonlib.config.HttpVersionConfig;
import com.nmssdmf.commonlib.httplib.HttpUtils;
import com.nmssdmf.commonlib.httplib.RxRequest;
import com.nmssdmf.commonlib.httplib.ServiceCallback;
import com.nmssdmf.commonlib.rxbus.EventInfo;
import com.nmssdmf.commonlib.rxbus.RxBus;
import com.nmssdmf.commonlib.rxbus.RxEvent;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhixingjia.bean.mainmodule.GoodManageNumber;
import com.zhixingjia.goodsmanagemodule.callback.GoodManageCB;
import com.zhixingjia.service.GoodsManageService;
import com.zhixingjia.service.MainService;

/**
* @description 商品管理viewmodel
* @author chenbin
* @date 2018/12/14 14:56
* @version v3.2.0
*/
public class GoodManageVM extends BaseVM {
    public int sale_sum;
    public int depot_sum;
    private GoodManageCB callback;
    public final ObservableField<String> keyword = new ObservableField<>();

    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public GoodManageVM(GoodManageCB callBack) {
        super(callBack);
        this.callback = callBack;
    }

    /**
     * 商品数量统计
     */
    public void getCommodityNumber() {
        HttpUtils.doHttp(subscription,
                RxRequest.create(GoodsManageService.class, HttpVersionConfig.API_COMMODITY_NUMBER).getCommodityNumber(),
                new ServiceCallback<BaseData<GoodManageNumber>>() {
            @Override
            public void onError(Throwable error) {

            }

            @Override
            public void onSuccess(BaseData<GoodManageNumber> goodManageNumberBaseData) {
                try {
                    sale_sum = Integer.valueOf(goodManageNumberBaseData.getData().getSale_sum());
                    depot_sum = Integer.valueOf(goodManageNumberBaseData.getData().getDepot_sum());
                } catch (Exception e) {

                }
                callback.setNumber();
            }

            @Override
            public void onDefeated(BaseData<GoodManageNumber> goodManageNumberBaseData) {

            }
        });
    }

    @Override
    public void registerRxBus() {
        super.registerRxBus();
        RxBus.getInstance().register(RxEvent.GoodsManageEvent.REFRESH_GOODSMANAGE_NUMBER, this);
    }

    @Override
    public void unRegisterRxBus() {
        super.unRegisterRxBus();
        RxBus.getInstance().unregister(RxEvent.GoodsManageEvent.REFRESH_GOODSMANAGE_NUMBER, this);
    }

    public void onRxEvent(RxEvent event, EventInfo info) {
        switch (event.getType()) {
            case RxEvent.GoodsManageEvent.REFRESH_GOODSMANAGE_NUMBER:
                getCommodityNumber();
                break;
        }
    }
}
