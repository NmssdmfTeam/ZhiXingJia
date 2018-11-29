package com.zhihangjia.mainmodule.viewmodel;

import com.nmssdmf.commonlib.bean.BaseData;
import com.nmssdmf.commonlib.bean.BaseListData;
import com.nmssdmf.commonlib.config.HttpVersionConfig;
import com.nmssdmf.commonlib.httplib.HttpUtils;
import com.nmssdmf.commonlib.httplib.RxRequest;
import com.nmssdmf.commonlib.httplib.ServiceCallback;
import com.nmssdmf.commonlib.util.ToastUtil;
import com.nmssdmf.commonlib.viewmodel.BaseRecyclerViewFragmentVM;
import com.zhihangjia.mainmodule.adapter.OrderAdapter;
import com.zhihangjia.mainmodule.callback.OrderListFragmentCB;
import com.zhixingjia.bean.mainmodule.Order;
import com.zhixingjia.service.MainService;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ${nmssdmf} on 2018/11/20 0020.
 */

public class OrderListFragmentVM extends BaseRecyclerViewFragmentVM implements OrderAdapter.OrderAdapterListener{
    private OrderListFragmentCB cb;
    private String identity = "buyer";
    private int status = 0;//订单状态值，0=全部 1=待付款 2=待发货 3=待收货 4=待评价

    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public OrderListFragmentVM(OrderListFragmentCB callBack) {
        super(callBack);
        cb = callBack;
    }


    @Override
    public void initData(boolean isRefresh) {
        Map<String, String> map = new HashMap<>();
        map.put("identity", identity);
        map.put("status", String.valueOf(status));
        if (!isRefresh && list.size() > 0) {
            map.put("page", ((Order)list.get(list.size() - 1)).getId());
        }
        HttpUtils.doHttp(subscription, RxRequest.create(MainService.class, HttpVersionConfig.API_ORDER).getOrderList(map), new ServiceCallback<BaseListData<Order>>() {
            @Override
            public void onError(Throwable error) {

            }

            @Override
            public void onSuccess(BaseListData<Order> data) {
                cb.refreshAdapter(isRefresh, data.getData());
            }

            @Override
            public void onDefeated(BaseListData<Order> data) {

            }
        });
    }


    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * 取消订单
     * @param item
     * @param index
     */
    @Override
    public void cancelOrder(Order item, int index) {
        cb.showLoaddingDialog();
        HttpUtils.doHttp(subscription, RxRequest.create(MainService.class, HttpVersionConfig.API_ORDER_CANCEL).cancelOrder(item.getOrder_id()), new ServiceCallback<BaseData>() {
            @Override
            public void onError(Throwable error) {

            }

            @Override
            public void onSuccess(BaseData data) {
                ToastUtil.showMsg(data.getMessage());
                list.remove(index);
                cb.refreshAdapter(index);
            }

            @Override
            public void onDefeated(BaseData data) {

            }
        });
    }

    @Override
    public void offlinePayOrder(Order item, int index) {
        HttpUtils.doHttp(subscription, RxRequest.create(MainService.class, HttpVersionConfig.API_ORDER_SHOPPAY).offlinePay(item.getOrder_id()), new ServiceCallback<BaseData>() {
            @Override
            public void onError(Throwable error) {

            }

            @Override
            public void onSuccess(BaseData data) {
                ToastUtil.showMsg(data.getMessage());
                list.remove(index);
                cb.refreshAdapter(index);
            }

            @Override
            public void onDefeated(BaseData data) {

            }
        });
    }

    @Override
    public void checkOfflinePayOrder(Order item, int index) {
        HttpUtils.doHttp(subscription, RxRequest.create(MainService.class, HttpVersionConfig.API_ORDER_SHOPPAY_CONFIRM).checkOfflinePay(item.getOrder_id()), new ServiceCallback<BaseData>() {
            @Override
            public void onError(Throwable error) {

            }

            @Override
            public void onSuccess(BaseData data) {
                ToastUtil.showMsg(data.getMessage());
                list.remove(index);
                cb.refreshAdapter(index);
            }

            @Override
            public void onDefeated(BaseData data) {

            }
        });
    }

    @Override
    public void sendOrder(Order item, int index) {
        HttpUtils.doHttp(subscription, RxRequest.create(MainService.class, HttpVersionConfig.API_ORDER_DELIVER).send(item.getOrder_id()), new ServiceCallback<BaseData>() {
            @Override
            public void onError(Throwable error) {

            }

            @Override
            public void onSuccess(BaseData data) {
                ToastUtil.showMsg(data.getMessage());
                list.remove(index);
                cb.refreshAdapter(index);
            }

            @Override
            public void onDefeated(BaseData data) {

            }
        });
    }

    @Override
    public void checkReceiver(Order item, int index) {
        HttpUtils.doHttp(subscription, RxRequest.create(MainService.class, HttpVersionConfig.API_ORDER_CONFIRM_RECEIPT).checkReceiver(item.getOrder_id()), new ServiceCallback<BaseData>() {
            @Override
            public void onError(Throwable error) {

            }

            @Override
            public void onSuccess(BaseData data) {
                ToastUtil.showMsg(data.getMessage());
                list.remove(index);
                cb.refreshAdapter(index);
            }

            @Override
            public void onDefeated(BaseData data) {

            }
        });
    }
}
