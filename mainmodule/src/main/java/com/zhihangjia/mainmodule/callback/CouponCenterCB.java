package com.zhihangjia.mainmodule.callback;

import com.nmssdmf.commonlib.callback.BaseRecyclerViewFragmentCB;
import com.zhixingjia.bean.mainmodule.CenterCoupon;

/**
* @description 领券中心callback
* @author chenbin
* @date 2019/1/23 10:06
* @version v3.2.0
*/
public interface CouponCenterCB extends BaseRecyclerViewFragmentCB {
    void notifyItemChange(CenterCoupon item, int position);
}
