package com.zhihangjia.mainmodule.callback;

import com.nmssdmf.commonlib.callback.BaseCB;
import com.zhihangjia.mainmodule.bean.House;
import com.zhixingjia.bean.mainmodule.Banner;
import com.zhixingjia.bean.mainmodule.HouseBean;

import java.util.List;

/**
 * 建材家具callback
 * Create by chenbin on 2018/11/15
 * <p>
 * <p>
 */
public interface MarketFragmentCB extends BaseCB {

    void endFresh();

    void setCatData(List<HouseBean.CateBean> cateBeans);            //初始化建材家具类别
    void setMiddleBanner(Banner.CommomBanner middleBanner);         //设置中间的广告栏
    void setRollPagerBanner(List<Banner.CommomBanner> topBanner);   //设置顶部的广告栏
    void setBrandData(List<HouseBean.BrandsBean> brandData);        //设置热门品牌
    void setListData(List<House> houses);                           //设置列表数据内容
}
