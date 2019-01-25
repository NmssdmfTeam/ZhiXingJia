package com.zhihangjia.mainmodule.viewmodel;

import android.databinding.ObservableField;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.google.gson.Gson;
import com.nmssdmf.commonlib.bean.BaseData;
import com.nmssdmf.commonlib.bean.BaseListData;
import com.nmssdmf.commonlib.config.ActivityNameConfig;
import com.nmssdmf.commonlib.config.HttpVersionConfig;
import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.config.StringConfig;
import com.nmssdmf.commonlib.httplib.HttpUtils;
import com.nmssdmf.commonlib.httplib.RxRequest;
import com.nmssdmf.commonlib.httplib.ServiceCallback;
import com.nmssdmf.commonlib.rxbus.EventInfo;
import com.nmssdmf.commonlib.rxbus.RxBus;
import com.nmssdmf.commonlib.rxbus.RxEvent;
import com.nmssdmf.commonlib.util.JLog;
import com.nmssdmf.commonlib.util.StringUtil;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.activity.ConfirmPayActivity;
import com.zhihangjia.mainmodule.adapter.ChooseCouponAdater;
import com.zhihangjia.mainmodule.bean.ProductBean;
import com.zhihangjia.mainmodule.bean.ProductParams;
import com.zhihangjia.mainmodule.callback.ConfirmOrderCB;
import com.zhixingjia.bean.mainmodule.CommodityComfirm;
import com.zhixingjia.bean.personmodule.Address;
import com.zhixingjia.bean.personmodule.Coupon;
import com.zhixingjia.service.MainService;
import com.zhixingjia.service.PersonService;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfirmOrderVM extends BaseVM implements ChooseCouponAdater.ChooseCouponAdaterListener {
    private final String TAG = ConfirmOrderVM.class.getSimpleName();
    private ConfirmOrderCB cb;

    Map<Integer, List<Coupon>> couponMap = new HashMap<>();

    private List<String> deliveryMethodList = new ArrayList<>();
    private String cart_info = "";      //选填(购物车点击结算时必填)，json格式:[{'sku_sum':'8','cart_id':'20731'},{'sku_sum':'8','cart_id':'20732'}]，sku_sum是购物车页上的选择的数量，cart_id是购物车列表规格所对应的cart_id
    private String productSkuId = "1";   //立即购买，需要传入的数据 规格的id，没有规格可以不需要传或者传0
    private String goods_sum = "1";      //选填(立即购买点击时必填)，购买商品的总数量
    private String commodity_id = "1";   //选填(立即购买点击时必填)，商品id

    public ObservableField<String> totalAmount = new ObservableField<>();
    private CommodityComfirm.AddressInfoBean addressInfoBean;
    public int position;

    private List<CommodityComfirm.InfoListBean> listBeans = new ArrayList<>();
    private String orderAmount = "0";

    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public ConfirmOrderVM(ConfirmOrderCB callBack) {
        super(callBack);
        cb = callBack;
        getIntentData();
        getData(true);
        deliveryMethodList.add("商家配送");
        deliveryMethodList.add("上门自提");
    }

    public void getIntentData() {
        Bundle bundle = cb.getIntentData();
        if (bundle == null)
            return;
        cart_info = bundle.getString(IntentConfig.CART_INFO);
        productSkuId = bundle.getString(IntentConfig.PRODUCT_SKU_ID);
        goods_sum = bundle.getString(IntentConfig.GOODS_SUM);
        commodity_id = bundle.getString(IntentConfig.COMMODITY_ID);
    }

    private void getData(final boolean isRefresh) {
        Map<String, String> map = new HashMap<>();
        if (!TextUtils.isEmpty(cart_info)) {
            map.put("cart_info", cart_info);
        }
        if (!TextUtils.isEmpty(productSkuId)) {
            map.put("product_sku_id", productSkuId);
        }
        if (!TextUtils.isEmpty(goods_sum)) {
            map.put("goods_sum", goods_sum);
        }
        if (!TextUtils.isEmpty(commodity_id)) {
            map.put("commodity_id", commodity_id);
        }
        cb.showLoaddingDialog();
        HttpUtils.doHttp(subscription, RxRequest.create(MainService.class, HttpVersionConfig.API_CART_SETTLE).getConformData(map), new ServiceCallback<BaseData<CommodityComfirm>>() {
            @Override
            public void onError(Throwable error) {

            }

            @Override
            public void onSuccess(BaseData<CommodityComfirm> data) {
                cb.setData(data.getData().getInfo_list(), isRefresh);
                cb.setAddressData(data.getData().getAddress_info());
                addressInfoBean = data.getData().getAddress_info();
                totalAmount.set(data.getData().getOrder_pay_amount());
                orderAmount = data.getData().getOrder_pay_amount();
            }

            @Override
            public void onDefeated(BaseData data) {

            }
        });
    }

    public void tvSubmitClick(View view) {
        List<CommodityComfirm.InfoListBean> infoListBeans = cb.getPayData();
        ProductParams productParams = new ProductParams();
        List<ProductBean> productBeans = new ArrayList<>();
        for (CommodityComfirm.InfoListBean infoListBean : infoListBeans) {
            ProductBean productBean = new ProductBean();
            productBean.setCoupon_code(null);
            productBean.setDispatching_type(String.valueOf(infoListBean.getFreight_type()));
            productBean.setNote(infoListBean.getMemo() == null ? "" : infoListBean.getMemo());
            productBean.setProvider_id(infoListBean.getProvider_id());
            List<ProductBean.GoodsInfo> goodsInfos = new ArrayList<>();
            for (CommodityComfirm.InfoListBean.ListInfoBean listInfoBean : infoListBean.getList_info()) {
                ProductBean.GoodsInfo goodsInfo = new ProductBean.GoodsInfo();
                goodsInfo.setCart_id(listInfoBean.getCart_id());
                goodsInfo.setCommodity_id(listInfoBean.getCommodity_id());
                goodsInfo.setNumber(listInfoBean.getSku_sum());
                goodsInfo.setProduct_sku_id(listInfoBean.getProduct_sku_id());
                goodsInfos.add(goodsInfo);
            }
            productBean.setCoupon_code(infoListBean.getCoupon_code());
            productBean.setGoods_info(goodsInfos);
            productBeans.add(productBean);
        }
        productParams.setProduct_params(productBeans);
        //提交订单
        if (addressInfoBean != null) {
            submitOrder(addressInfoBean.getAddr_id(), new Gson().toJson(productParams));
        } else {
            cb.showToast("请选择地址");
        }
    }

    private void submitOrder(String addrId, String orders) {
        cb.showLoaddingDialog();
        HttpUtils.doHttp(subscription,
                RxRequest.create(MainService.class, HttpVersionConfig.API_CART_SUBMITORDER).submitOrder(addrId, orders),
                new ServiceCallback<BaseListData<String>>() {
                    @Override
                    public void onError(Throwable error) {

                    }

                    @Override
                    public void onSuccess(BaseListData<String> base) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(IntentConfig.PAY_IDS, (Serializable) base.getData());
                        bundle.putString(IntentConfig.IDENTITY, StringConfig.BUYER);
                        cb.doIntent(ConfirmPayActivity.class, bundle);
                        if (!TextUtils.isEmpty(cart_info)) {
                            RxBus.getInstance().send(RxEvent.OrderEvent.SHOP_CAR_CONFIRM_ORDER, null);
                        } else {
                            RxBus.getInstance().send(RxEvent.OrderEvent.CONFIRM_ORDER, null);
                        }
                    }

                    @Override
                    public void onDefeated(BaseListData<String> base) {

                    }
                });
    }

    public List<String> getDeliveryMethodList() {
        return deliveryMethodList;
    }

    public void setDeliveryMethodList(List<String> deliveryMethodList) {
        this.deliveryMethodList = deliveryMethodList;
    }

    /**
     * 添加收件地址
     *
     * @param view
     */
    public void onAddAddressClick(View view) {
        cb.doIntentClassName(ActivityNameConfig.ADDOREDITADDRESS_ACIVITY, null);
    }

    /**
     * 选择地址
     *
     * @param view
     */
    public void onSelectAddressClick(View view) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(IntentConfig.IS_SELECT, true);
        cb.doIntentClassName(ActivityNameConfig.MANAGEADDRESSLIST_ACTIVITY, bundle);
    }

    /**
     * 获取商家优惠券
     */
    public void getCoupons(boolean isRefresh, String providerId, String money) {
        if (couponMap.get(position) != null && couponMap.get(position).size() > 0) {
            cb.showCouponWindow(true);
            return;
        }
        cb.showLoaddingDialog();
        String page = "0";
        if (couponMap.get(position) != null && couponMap.get(position).size() > 0) {
            page = couponMap.get(position).get(couponMap.get(position).size() - 1).getCode_id();
        }
        Map<String, String> map = new HashMap<>();
        map.put("type", "3");
        map.put("amount", money);
        map.put("page", page);
        map.put("provider_id", providerId);
        HttpUtils.doHttp(subscription, RxRequest.create(PersonService.class, HttpVersionConfig.API_COUPON_INFO).getMyCoupon(map), new ServiceCallback<BaseListData<Coupon>>() {
            @Override
            public void onError(Throwable error) {

            }

            @Override
            public void onSuccess(BaseListData<Coupon> data) {
                cb.dismissLoaddingDialog();
                if (data.getData() != null && data.getData().size() > 0) {
                    couponMap.put(position, data.getData());
                    cb.refreshCouponList(isRefresh, data.getData());
                }
                JLog.d(TAG, "couponMap = " + new Gson().toJson(couponMap));
                cb.showCouponWindow(isRefresh);
            }

            @Override
            public void onDefeated(BaseListData<Coupon> data) {

            }
        });
    }

    @Override
    public void registerRxBus() {
        super.registerRxBus();
        RxBus.getInstance().register(RxEvent.OrderEvent.SELECT_ADDRESS, this);
        RxBus.getInstance().register(RxEvent.PersonInfoEvent.ADDRESS_INSERT, this);
    }

    @Override
    public void unRegisterRxBus() {
        super.unRegisterRxBus();
        RxBus.getInstance().unregister(RxEvent.OrderEvent.SELECT_ADDRESS, this);
        RxBus.getInstance().unregister(RxEvent.PersonInfoEvent.ADDRESS_INSERT, this);
    }

    public void onRxEvent(RxEvent event, EventInfo info) {
        Address address = (Address) info.getContent();
        CommodityComfirm.AddressInfoBean addressInfoBean = formatAddress(address);
        switch (event.getType()) {
            case RxEvent.OrderEvent.SELECT_ADDRESS:
            case RxEvent.PersonInfoEvent.ADDRESS_INSERT:
                this.addressInfoBean = addressInfoBean;
                cb.setAddressData(addressInfoBean);
                break;
        }
    }

    private CommodityComfirm.AddressInfoBean formatAddress(Address address) {
        CommodityComfirm.AddressInfoBean addressInfoBean = new CommodityComfirm.AddressInfoBean();
        addressInfoBean.setAddr_id(address.getAddr_id());
        addressInfoBean.setLocation(address.getArea() + address.getAddr());
        addressInfoBean.setMobile(address.getMobile());
        addressInfoBean.setName(address.getNames());
        return addressInfoBean;
    }

    public Map<Integer, List<Coupon>> getCouponMap() {
        return couponMap;
    }

    public void setCouponMap(Map<Integer, List<Coupon>> couponMap) {
        this.couponMap = couponMap;
    }

    @Override
    public void useCoupon(Coupon item) {
        listBeans.get(position).setCoupon_code(item.getCode_id());
        listBeans.get(position).setCoupon_price(item.getDecrease());
        cb.closeChooseCouponWindow();

        BigDecimal totalCouponPrice = new BigDecimal(0);
        for (CommodityComfirm.InfoListBean bean : listBeans) {
            if (StringUtil.isEmpty(bean.getCoupon_code())) {

            } else {
                totalCouponPrice = totalCouponPrice.add(new BigDecimal(bean.getCoupon_price()));
            }
        }

        totalAmount.set(new BigDecimal(orderAmount).subtract(totalCouponPrice).toString());
        cb.notifyPosition(position);
    }

    @Override
    public void dontUseCoupon() {
        listBeans.get(position).setCoupon_code("");
        listBeans.get(position).setCoupon_price("");
        cb.closeChooseCouponWindow();

        BigDecimal totalCouponPrice = new BigDecimal(0);
        totalAmount.set(new BigDecimal(orderAmount).subtract(totalCouponPrice).toString());
        cb.notifyPosition(position);
    }

    public List<CommodityComfirm.InfoListBean> getListBeans() {
        return listBeans;
    }

    public void setListBeans(List<CommodityComfirm.InfoListBean> listBeans) {
        this.listBeans = listBeans;
    }
}
