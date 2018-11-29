package com.zhihangjia.mainmodule.fragment;

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
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.adapter.OrderWaitForPayAdapter;
import com.zhihangjia.mainmodule.callback.OrderListWaitForPayFragmentCB;
import com.zhixingjia.bean.mainmodule.Order;
import com.zhixingjia.service.MainService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderListWaitForPayFragmentVM extends BaseVM implements OrderWaitForPayAdapter.OrderWaitForPayAdapterListener{
    private OrderListWaitForPayFragmentCB cb;
    private List<Order> list = new ArrayList<>();
    private String identity = "buyer";
        private int status = 1;//订单状态值，0=全部 1=待付款 2=待发货 3=待收货 4=待评价
    private boolean current;//是否是当前显示的fragment
    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public OrderListWaitForPayFragmentVM(OrderListWaitForPayFragmentCB callBack) {
        super(callBack);
        cb = callBack;
    }

    @Override
    public void registerRxBus() {
        super.registerRxBus();
        RxBus.getInstance().register(RxEvent.OrderEvent.ORDER_CANCEL, this);
        RxBus.getInstance().register(RxEvent.OrderEvent.ORDER_PAY, this);
        RxBus.getInstance().register(RxEvent.OrderEvent.ORDER_OFFLINE_PAY, this);
    }

    @Override
    public void unRegisterRxBus() {
        super.unRegisterRxBus();
        RxBus.getInstance().unregister(RxEvent.OrderEvent.ORDER_CANCEL, this);
        RxBus.getInstance().unregister(RxEvent.OrderEvent.ORDER_PAY, this);
        RxBus.getInstance().unregister(RxEvent.OrderEvent.ORDER_OFFLINE_PAY, this);
    }


    public void getData(boolean isRefresh){
        if (isRefresh)
            list.clear();
        Map<String, String> map = new HashMap<>();
        map.put("identity", identity);
        map.put("status", String.valueOf(status));
        if (!isRefresh && list.size() > 0) {
            map.put("page", (list.get(list.size() - 1)).getId());
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

    public List<Order> getList() {
        return list;
    }

    public void setList(List<Order> list) {
        this.list = list;
    }

    @Override
    public void cancelOrder( int index) {
        cb.showLoaddingDialog();
        Order item = list.get(index);
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
        Order item =  list.get(index);
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
        }
    }

    public boolean isCurrent() {
        return current;
    }

    public void setCurrent(boolean current) {
        this.current = current;
    }
}
