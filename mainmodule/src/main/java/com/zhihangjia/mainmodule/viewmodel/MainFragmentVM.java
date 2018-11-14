package com.zhihangjia.mainmodule.viewmodel;

import com.nmssdmf.commonlib.bean.BaseData;
import com.nmssdmf.commonlib.config.HttpVersionConfig;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.bean.MainBean;
import com.zhihangjia.mainmodule.callback.MainFragmentCB;
import com.zhixingjia.bean.mainmodule.IndexBean;
import com.zhixingjia.httplib.HttpUtils;
import com.zhixingjia.httplib.RxRequest;
import com.zhixingjia.httplib.ServiceCallback;
import com.zhixingjia.service.MainService;

import java.util.List;

/**
* @description 首页viewmodel
* @author chenbin
* @date 2018/11/13 15:23
* @version v3.2.0
*/
public class MainFragmentVM extends BaseVM {
    private MainFragmentCB cb;
    private List<IndexBean.BannerFixedBean> fixedBeanList;

    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public MainFragmentVM(MainFragmentCB callBack) {
        super(callBack);
        this.cb = callBack;
    }

    public void getIndex() {
        HttpUtils.doHttp(subscription, RxRequest.create(MainService.class, HttpVersionConfig.API_INDEX).getIndex(),
                new ServiceCallback<BaseData<IndexBean>>() {
            @Override
            public void onError(Throwable error) {
                cb.setRefreshend();
            }

            @Override
            public void onSuccess(BaseData<IndexBean> indexBeanBaseData) {
                cb.setRefreshend();
                cb.setRollPagerView(indexBeanBaseData.getData().getBanners());

                cb.setFillerView(indexBeanBaseData.getData().getArticle());

                fixedBeanList = indexBeanBaseData.getData().getBanner_fixed();
                cb.setBannerFixed(indexBeanBaseData.getData().getBanner_fixed());

                MainBean sellerBean = new MainBean();
                sellerBean.setItemType(0);
                sellerBean.setSellerBeans(indexBeanBaseData.getData().getSeller());
                cb.setExcellentSeller(sellerBean);

                MainBean commodityBean = new MainBean();
                commodityBean.setItemType(1);
                for (IndexBean.BannerFixedBean bannerFixedBean:fixedBeanList){
                    if ("all".equals(bannerFixedBean.getModel_name())) {
                        commodityBean.setBannerFixedBean(bannerFixedBean);
                    }
                }
                commodityBean.setCommodityBeans(indexBeanBaseData.getData().getCommodity());
                cb.setCommodity(commodityBean);

                MainBean forumBean = new MainBean();
                forumBean.setItemType(3);
                forumBean.setForumBeans(indexBeanBaseData.getData().getForum());
                cb.setForum(forumBean);
            }

            @Override
            public void onDefeated(BaseData<IndexBean> indexBeanBaseData) {
                cb.setRefreshend();
            }
        });
    }
}
