package com.zhihangjia.mainmodule.callback;

import com.nmssdmf.commonlib.callback.BaseCB;

import java.util.List;

public interface MerchandiseDetailFragmentCB extends BaseCB {
    void onBack();
    void showChooseCouponWindow();
    void showChooseSpecificationWindow();

    void gotoCommentDetail();

    void setCommodityImgs(List<String> imgs);
    void initView();
}
