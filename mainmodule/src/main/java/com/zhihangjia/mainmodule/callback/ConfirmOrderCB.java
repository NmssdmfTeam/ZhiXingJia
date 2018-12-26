package com.zhihangjia.mainmodule.callback;

import com.nmssdmf.commonlib.callback.BaseCB;
import com.zhixingjia.bean.mainmodule.CommodityComfirm;
import com.zhixingjia.bean.personmodule.Coupon;

import java.util.List;

public interface ConfirmOrderCB extends BaseCB {

    void setData(List<CommodityComfirm.InfoListBean> infoListBeans, boolean isRefresh);
    void setAddressData(CommodityComfirm.AddressInfoBean addressData);
    List<CommodityComfirm.InfoListBean> getPayData();
    void refreshCouponList(boolean isRefresh, List<Coupon> list);

    void showCouponWindow(boolean refresh);
    void closeChooseCouponWindow();
    void notifyPosition(int position);
}
