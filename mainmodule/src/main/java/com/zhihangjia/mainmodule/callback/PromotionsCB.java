package com.zhihangjia.mainmodule.callback;

import com.nmssdmf.commonlib.callback.BaseRecyclerViewFragmentCB;
import com.zhixingjia.bean.mainmodule.Banner;

import java.util.List;

public interface PromotionsCB extends BaseRecyclerViewFragmentCB {
    void setHeadV(List<Banner.CommomBanner> banners);
}
