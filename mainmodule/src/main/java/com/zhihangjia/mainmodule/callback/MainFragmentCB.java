package com.zhihangjia.mainmodule.callback;

import com.nmssdmf.commonlib.callback.BaseCB;
import com.zhihangjia.mainmodule.bean.MainBean;
import com.zhixingjia.bean.mainmodule.Banner;
import com.zhixingjia.bean.mainmodule.IndexBean;
import com.zhixingjia.bean.mainmodule.MessageUnread;

import java.util.List;

public interface MainFragmentCB extends BaseCB {
    /**
     * 顶部广告栏
     * @param bannersBeans
     */
    void setRollPagerView(List<Banner.CommomBanner> bannersBeans);

    /**
     * 头条
     * @param articleBeans
     */
    void setFillerView(List<IndexBean.ArticleBean> articleBeans);

    /**
     * 初始化固定广告位图片
     * @param bannerFixedBeans
     */
    void setBannerFixed(List<Banner.CommomBanner> bannerFixedBeans);

    /**
     * 刷新中间的广告图片
     * @param bannerMiddle
     */
    void setBannerMiddle(Banner.CommomBanner bannerMiddle);

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
     * 百姓信息
     * @param forum
     */
    void setForum(MainBean forum);

    /**
     * 生活服务模块
     * @param lifeCates
     */
    void setLifeCate(MainBean lifeCates);

    /**
     * 结束刷新
     */
    void setRefreshend();

    /**
     * 跳转建材家居
     */
    void toOtherFragment(int index);

    void showNotice(MessageUnread messageUnread);
}
