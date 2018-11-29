package com.zhihangjia.mainmodule.viewmodel;

import android.databinding.ObservableField;
import android.os.Bundle;

import com.nmssdmf.commonlib.bean.BaseData;
import com.nmssdmf.commonlib.config.HttpVersionConfig;
import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.httplib.HttpUtils;
import com.nmssdmf.commonlib.httplib.RxRequest;
import com.nmssdmf.commonlib.httplib.ServiceCallback;
import com.nmssdmf.commonlib.util.ToastUtil;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.callback.OrderDetailCB;
import com.zhixingjia.bean.mainmodule.Order;
import com.zhixingjia.bean.mainmodule.OrderDetail;
import com.zhixingjia.service.MainService;

/**
 * Created by ${nmssdmf} on 2018/11/28 0028.
 */

public class OrderDetailVM extends BaseVM {
    private OrderDetailCB cb;
    private String id;
    private int index;
    public final ObservableField<OrderDetail> detail = new ObservableField<>();
    public final ObservableField<String> log = new ObservableField<>();
    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public OrderDetailVM(OrderDetailCB callBack) {
        super(callBack);
        cb = callBack;
    }

    public void getIntentData(){
        Bundle bundle = cb.getIntentData();
        if (bundle == null)
            return;
        id = bundle.getString(IntentConfig.ID);
        index = bundle.getInt(IntentConfig.POSITION);
        initData();
    }

    private void initData() {

        HttpUtils.doHttp(subscription, RxRequest.create(MainService.class, HttpVersionConfig.API_ORDER_SHOW).getOrderDetail(id), new ServiceCallback<BaseData<OrderDetail>>() {
            @Override
            public void onError(Throwable error) {

            }

            @Override
            public void onSuccess(BaseData<OrderDetail> data) {
                detail.set(data.getData());
                String logString = "";
                for (String string:detail.get().getOrder_log()) {
                    logString += string + "\n";
                }
                log.set(logString.substring(0, logString.length() -1));
                cb.addOrderStatusBtn(data.getData().getOrder_status());
                cb.addGoods();
            }

            @Override
            public void onDefeated(BaseData<OrderDetail> data) {

            }
        });
    }


    public void cancelOrder() {
        cb.showLoaddingDialog();
        HttpUtils.doHttp(subscription, RxRequest.create(MainService.class, HttpVersionConfig.API_ORDER_CANCEL).cancelOrder(detail.get().getOrder_id()), new ServiceCallback<BaseData>() {
            @Override
            public void onError(Throwable error) {

            }

            @Override
            public void onSuccess(BaseData data) {
                ToastUtil.showMsg(data.getMessage());
            }

            @Override
            public void onDefeated(BaseData data) {

            }
        });
    }

    public void offlinePayOrder() {
        cb.showLoaddingDialog();
        HttpUtils.doHttp(subscription, RxRequest.create(MainService.class, HttpVersionConfig.API_ORDER_SHOPPAY).offlinePay(detail.get().getOrder_id()), new ServiceCallback<BaseData>() {
            @Override
            public void onError(Throwable error) {

            }

            @Override
            public void onSuccess(BaseData data) {
                ToastUtil.showMsg(data.getMessage());
                detail.get().setOrder_status("99");
                detail.get().setOrder_status_name("到店付审核中");
            }

            @Override
            public void onDefeated(BaseData data) {

            }
        });
    }

    public void checkOfflinePayOrder() {
        HttpUtils.doHttp(subscription, RxRequest.create(MainService.class, HttpVersionConfig.API_ORDER_SHOPPAY_CONFIRM).checkOfflinePay(detail.get().getOrder_id()), new ServiceCallback<BaseData>() {
            @Override
            public void onError(Throwable error) {

            }

            @Override
            public void onSuccess(BaseData data) {
                ToastUtil.showMsg(data.getMessage());
            }

            @Override
            public void onDefeated(BaseData data) {

            }
        });
    }

    public void sendOrder() {
        HttpUtils.doHttp(subscription, RxRequest.create(MainService.class, HttpVersionConfig.API_ORDER_DELIVER).send(detail.get().getOrder_id()), new ServiceCallback<BaseData>() {
            @Override
            public void onError(Throwable error) {

            }

            @Override
            public void onSuccess(BaseData data) {
                ToastUtil.showMsg(data.getMessage());
            }

            @Override
            public void onDefeated(BaseData data) {

            }
        });
    }

    public void checkReceiver() {
        HttpUtils.doHttp(subscription, RxRequest.create(MainService.class, HttpVersionConfig.API_ORDER_CONFIRM_RECEIPT).checkReceiver(detail.get().getOrder_id()), new ServiceCallback<BaseData>() {
            @Override
            public void onError(Throwable error) {

            }

            @Override
            public void onSuccess(BaseData data) {
                ToastUtil.showMsg(data.getMessage());
            }

            @Override
            public void onDefeated(BaseData data) {

            }
        });
    }
}
