package com.zhihangjia.mainmodule.callback;

import com.nmssdmf.commonlib.callback.BaseCB;
import com.zhihangjia.mainmodule.bean.MainBean;
import com.zhixingjia.bean.mainmodule.IndexBean;

import java.util.List;

public interface MainFragmentCB extends BaseCB {
    /**
     * 顶部广告栏
     * @param bannersBeans
     */
    void setRollPagerView(List<IndexBean.BannersBean> bannersBeans);

    /**
     * 头条
     * @param articleBeans
     */
    void setFillerView(List<IndexBean.ArticleBean> articleBeans);

    /**
     * 初始化固定广告位图片
     * @param bannerFixedBeans
     */
    void setBannerFixed(List<IndexBean.BannerFixedBean> bannerFixedBeans);

    /**
     * 优秀建材商家
     * @param seller
     */
    void setExcellentSeller(MainBean seller);

    /**
     * //商品推荐
     * @param commodity
     */
    void setCommodity(MainBean commodity);

    /**
     * 信息中心
     * @param forum
     */
    void setForum(MainBean forum);

    /**
     * 结束刷新
     */
    void setRefreshend();
}
