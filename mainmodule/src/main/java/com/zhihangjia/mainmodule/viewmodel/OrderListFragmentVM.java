package com.zhihangjia.mainmodule.viewmodel;

import com.nmssdmf.commonlib.bean.BaseData;
import com.nmssdmf.commonlib.bean.BaseListData;
import com.nmssdmf.commonlib.config.HttpVersionConfig;
import com.nmssdmf.commonlib.httplib.HttpUtils;
import com.nmssdmf.commonlib.httplib.RxRequest;
import com.nmssdmf.commonlib.httplib.ServiceCallback;
import com.nmssdmf.commonlib.rxbus.EventInfo;
import com.nmssdmf.commonlib.rxbus.RxBus;
import com.nmssdmf.commonlib.rxbus.RxEvent;
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

    private boolean current;//是否是当前显示的fragment

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
    public void registerRxBus() {
        super.registerRxBus();
        RxBus.getInstance().register(RxEvent.OrderEvent.ORDER_CANCEL, this);
        RxBus.getInstance().register(RxEvent.OrderEvent.ORDER_SEND, this);
        RxBus.getInstance().register(RxEvent.OrderEvent.ORDER_PAY, this);
        RxBus.getInstance().register(RxEvent.OrderEvent.ORDER_OFFLINE_PAY, this);
        RxBus.getInstance().register(RxEvent.OrderEvent.ORDER_CHECK_OFFLINE_PAY, this);
        RxBus.getInstance().register(RxEvent.OrderEvent.ORDER_COMMENT, this);
        RxBus.getInstance().register(RxEvent.OrderEvent.ORDER_CHECK_RECEIVER, this);
    }

    @Override
    public void unRegisterRxBus() {
        super.unRegisterRxBus();
        RxBus.getInstance().unregister(RxEvent.OrderEvent.ORDER_CANCEL, this);
        RxBus.getInstance().unregister(RxEvent.OrderEvent.ORDER_SEND, this);
        RxBus.getInstance().unregister(RxEvent.OrderEvent.ORDER_PAY, this);
        RxBus.getInstance().unregister(RxEvent.OrderEvent.ORDER_OFFLINE_PAY, this);
        RxBus.getInstance().unregister(RxEvent.OrderEvent.ORDER_CHECK_OFFLINE_PAY, this);
        RxBus.getInstance().unregister(RxEvent.OrderEvent.ORDER_COMMENT, this);
        RxBus.getInstance().unregister(RxEvent.OrderEvent.ORDER_CHECK_RECEIVER, this);
    }

    @Override
    public void initData(boolean isRefresh) {
        Map<String, String> map = new HashMap<>();
        map.put("identity", identity);
        map.put("status", String.valueOf(status));
        if (!isRefresh && list.size() > 0) {
            map.put("page", ((Order)list.get(list.size() - 1)).getId());
        }
        cb.showLoaddingDialog();
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
     * @param index
     */
    @Override
    public void cancelOrder(int index) {
        cb.showLoaddingDialog();
        Order item = (Order) list.get(index);
        HttpUtils.doHttp(subscription, RxRequest.create(MainService.class, HttpVersionConfig.API_ORDER_CANCEL).cancelOrder(item.getOrder_id()), new ServiceCallback<BaseData>() {
            @Override
            public void onError(Throwable error) {

            }

            @Override
            public void onSuccess(BaseData data) {
                ToastUtil.showMsg(data.getMessage());
                list.remove(index);
                cb.cancelOrder();
            }

            @Override
            public void onDefeated(BaseData data) {

            }
        });
    }

    @Override
    public void offlinePayOrder(int index) {
        cb.showLoaddingDialog();
        Order item = (Order) list.get(index);
        HttpUtils.doHttp(subscription, RxRequest.create(MainService.class, HttpVersionConfig.API_ORDER_SHOPPAY).offlinePay(item.getOrder_id()), new ServiceCallback<BaseData>() {
            @Override
            public void onError(Throwable error) {

            }

            @Override
            public void onSuccess(BaseData data) {
                ToastUtil.showMsg(data.getMessage());
                item.setOrder_status("99");
                item.setOrder_status_name("到店付审核中");
                cb.nofityItem(index);
            }

            @Override
            public void onDefeated(BaseData data) {

            }
        });
    }

    @Override
    public void checkOfflinePayOrder( int index) {
        Order item = (Order) list.get(index);
        HttpUtils.doHttp(subscription, RxRequest.create(MainService.class, HttpVersionConfig.API_ORDER_SHOPPAY_CONFIRM).checkOfflinePay(item.getOrder_id()), new ServiceCallback<BaseData>() {
            @Override
            public void onError(Throwable error) {

            }

            @Override
            public void onSuccess(BaseData data) {
                ToastUtil.showMsg(data.getMessage());
                list.remove(index);
                cb.cancelOrder();
            }

            @Override
            public void onDefeated(BaseData data) {

            }
        });
    }

    @Override
    public void sendOrder(int index) {
        Order item = (Order) list.get(index);
        HttpUtils.doHttp(subscription, RxRequest.create(MainService.class, HttpVersionConfig.API_ORDER_DELIVER).send(item.getOrder_id()), new ServiceCallback<BaseData>() {
            @Override
            public void onError(Throwable error) {

            }

            @Override
            public void onSuccess(BaseData data) {
                ToastUtil.showMsg(data.getMessage());
                list.remove(index);
                cb.cancelOrder();
            }

            @Override
            public void onDefeated(BaseData data) {

            }
        });
    }

    @Override
    public void checkReceiver(int index) {
        Order item = (Order) list.get(index);
        HttpUtils.doHttp(subscription, RxRequest.create(MainService.class, HttpVersionConfig.API_ORDER_CONFIRM_RECEIPT).checkReceiver(item.getOrder_id()), new ServiceCallback<BaseData>() {
            @Override
            public void onError(Throwable error) {

            }

            @Override
            public void onSuccess(BaseData data) {
                ToastUtil.showMsg(data.getMessage());
                list.remove(index);
                cb.cancelOrder();
            }

            @Override
            public void onDefeated(BaseData data) {

            }
        });
    }

    public void onRxEvent(RxEvent event, EventInfo info) {
        if (!current) {//不是当前的fragment,不刷新数据
            return;
        }
        switch (event.getType()) {
            case RxEvent.OrderEvent.ORDER_CANCEL:{
                list.remove(info.getIndex());
                cb.cancelOrder();
                break;
            }
            case RxEvent.OrderEvent.ORDER_SEND:{
                list.remove(info.getIndex());
                cb.cancelOrder();
                break;
            }
            case RxEvent.OrderEvent.ORDER_PAY:{
                list.remove(info.getIndex());
                cb.cancelOrder();
                break;
            }
            case RxEvent.OrderEvent.ORDER_OFFLINE_PAY:{
                Order item = (Order)list.get(info.getIndex());
                item.setOrder_status("99");
                item.setOrder_status_name("到店付审核中");
                cb.nofityItem(info.getIndex());
                break;
            }
            case RxEvent.OrderEvent.ORDER_CHECK_OFFLINE_PAY:{
                list.remove(info.getIndex());
                cb.cancelOrder();
                break;
            }
            case RxEvent.OrderEvent.ORDER_COMMENT:{
                list.remove(info.getIndex());
                cb.cancelOrder();
                break;
            }
            case RxEvent.OrderEvent.ORDER_CHECK_RECEIVER:{
                list.remove(info.getIndex());
                cb.cancelOrder();
                break;
            }
        }
    }

    public boolean isCurrent() {
        return current;
    }

    public void setCurrent(boolean current) {
        this.current = current;
    }
}
