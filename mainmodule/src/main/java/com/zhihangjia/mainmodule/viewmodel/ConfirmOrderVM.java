package com.zhihangjia.mainmodule.viewmodel;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.commonlib.bean.BaseData;
import com.nmssdmf.commonlib.callback.WheelPickerWindowCB;
import com.nmssdmf.commonlib.config.HttpVersionConfig;
import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.httplib.HttpUtils;
import com.nmssdmf.commonlib.httplib.RxRequest;
import com.nmssdmf.commonlib.httplib.ServiceCallback;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.activity.ConfirmPayActivity;
import com.zhihangjia.mainmodule.callback.ConfirmOrderCB;
import com.zhixingjia.bean.mainmodule.CommodityComfirm;
import com.zhixingjia.service.MainService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfirmOrderVM extends BaseVM implements WheelPickerWindowCB {
    private ConfirmOrderCB cb;
    private List<Base> couponList = new ArrayList<>();//优惠券数据

    private List<String> deliveryMethodList = new ArrayList<>();
    private String cart_info = "";      //选填(购物车点击结算时必填)，json格式:[{'sku_sum':'8','cart_id':'20731'},{'sku_sum':'8','cart_id':'20732'}]，sku_sum是购物车页上的选择的数量，cart_id是购物车列表规格所对应的cart_id
    private String productSkuId = "1";   //立即购买，需要传入的数据 规格的id，没有规格可以不需要传或者传0
    private String goods_sum = "1";      //选填(立即购买点击时必填)，购买商品的总数量
    private String commodity_id = "1";   //选填(立即购买点击时必填)，商品id

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
    }

    public void getIntentData() {
        Bundle bundle = cb.getIntentData();
        if (bundle == null)
            return;
        cart_info = bundle.getString(IntentConfig.CART_INFO);
        productSkuId = bundle.getString(IntentConfig.PRODUCT_SKU_ID);
        goods_sum = bundle.getString(IntentConfig.GOODS_SUM);
        commodity_id = bundle.getString(IntentConfig.COMMODITY_ID);

        deliveryMethodList.add("商家配送");
        deliveryMethodList.add("上门自提");
    }

    private void getData(final boolean isRefresh) {
        Map<String, String> map = new HashMap<>();
        if (!TextUtils.isEmpty(cart_info)) {
            map.put("cart_info", cart_info);
        }
        if (!TextUtils.isEmpty(productSkuId)) {
            map.put("product_sku_id", productSkuId);
        }
        map.put("goods_sum", goods_sum);
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
                cb.setData(data.getData().getInfo_list(),isRefresh);
            }

            @Override
            public void onDefeated(BaseData data) {

            }
        });
    }

    public void tvSubmitClick(View view) {
        cb.doIntent(ConfirmPayActivity.class, null);
    }

    public List<Base> getCouponList() {
        return couponList;
    }

    public void setCouponList(List<Base> couponList) {
        this.couponList = couponList;
    }

    public List<String> getDeliveryMethodList() {
        return deliveryMethodList;
    }

    public void setDeliveryMethodList(List<String> deliveryMethodList) {
        this.deliveryMethodList = deliveryMethodList;
    }

    @Override
    public void tvSureClick(String item, int position) {

    }
}
