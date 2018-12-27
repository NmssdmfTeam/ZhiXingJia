package com.zhihangjia.mainmodule.callback;

import com.nmssdmf.commonlib.callback.BaseCB;
import com.zhixingjia.bean.mainmodule.CommodityDetail;

import java.util.List;

public interface MerchandiseDetailFragmentCB extends BaseCB {
    void onBack();
    void showChooseCouponWindow();
    void showChooseSpecificationWindow();

    void gotoCommentDetail();

    void setCommodityImgs(List<String> imgs);
    void initView();
    void setCommodityComment(List<CommodityDetail.OrderComment> orderComments);

    String getProductSkuId();
    String getGoodsSum();

    void toShare(CommodityDetail commodityDetail);
    void showShopCarRedPoint();
    String getStock();//获取库存，可能有规格，那就需要从选择规格选择窗口中获取库存数据
}
