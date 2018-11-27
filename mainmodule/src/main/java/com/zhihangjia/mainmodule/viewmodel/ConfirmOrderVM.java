package com.zhihangjia.mainmodule.viewmodel;

import android.os.Bundle;
import android.view.View;

import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.commonlib.callback.WheelPickerWindowCB;
import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.activity.ConfirmPayActivity;
import com.zhihangjia.mainmodule.callback.ConfirmOrderCB;

import java.util.ArrayList;
import java.util.List;

public class ConfirmOrderVM extends BaseVM implements WheelPickerWindowCB{
    private int type = 1;//1:购物车 2:立即购买
    private ConfirmOrderCB cb;

    private List<Base> list = new ArrayList<>();//商品数据
    private List<Base> couponList = new ArrayList<>();//优惠券数据

    private List<String> deliveryMethodList = new ArrayList<>();
    private String skuJsonString = "";//购物车时需要传入的数据[{'sku_sum':'8','id':'20731'},{'sku_sum':'8','id':'20732'}]，
    private String productSkuId = "";//立即购买，需要传入的数据 规格的id，没有规格可以不需要传或者传0
    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public ConfirmOrderVM(ConfirmOrderCB callBack) {
        super(callBack);
        cb = callBack;
    }

    public void initData(){
        Bundle bundle = cb.getIntentData();
        if (bundle == null)
            return;
        if (type == 1) {//购物车
            skuJsonString = bundle.getString(IntentConfig.SKU_JSON);
        } else {
            productSkuId = bundle.getString(IntentConfig.PRODUCT_SKU_ID);
        }
        list.add(new Base());
        list.add(new Base());
        list.add(new Base());

        deliveryMethodList.add("商家配送");
        deliveryMethodList.add("上门自提");


    }

    public void tvSubmitClick(View view){
        cb.doIntent(ConfirmPayActivity.class, null);
    }

    public List<Base> getList() {
        return list;
    }

    public void setList(List<Base> list) {
        this.list = list;
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
