package com.zhihangjia.mainmodule.callback;

import com.nmssdmf.commonlib.callback.BaseCB;
import com.zhixingjia.bean.mainmodule.CouponCardTicketSum;

public interface MyCouponCardCB extends BaseCB {
    void setCouponTicketCount(CouponCardTicketSum sum);
}
