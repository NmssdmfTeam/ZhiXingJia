package com.zhihangjia.mainmodule.viewmodel;

import android.databinding.ObservableField;
import android.os.Bundle;

import com.nmssdmf.commonlib.bean.BaseData;
import com.nmssdmf.commonlib.config.HttpVersionConfig;
import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.httplib.HttpUtils;
import com.nmssdmf.commonlib.httplib.RxRequest;
import com.nmssdmf.commonlib.httplib.ServiceCallback;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.callback.OrderDetailCB;
import com.zhixingjia.bean.mainmodule.OrderDetail;
import com.zhixingjia.service.MainService;

/**
 * Created by ${nmssdmf} on 2018/11/28 0028.
 */

public class OrderDetailVM extends BaseVM {
    private OrderDetailCB cb;
    private String id;
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
}
