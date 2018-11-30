package com.zhihangjia.mainmodule.callback;

import com.nmssdmf.commonlib.callback.BaseCB;
import com.zhixingjia.bean.mainmodule.CouponSeller;

import java.util.List;

/**
 * Created by ${nmssdmf} on 2018/11/21 0021.
 */

public interface ShopCouponListCB extends BaseCB {
    void setData(List<CouponSeller> couponSellers, boolean isRefresh);
    void setData(CouponSeller couponSeller, int position);
    void deletData(int position);
}
