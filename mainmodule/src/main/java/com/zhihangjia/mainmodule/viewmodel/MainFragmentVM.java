package com.zhihangjia.mainmodule.viewmodel;

import android.text.TextUtils;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nmssdmf.commonlib.bean.BaseData;
import com.nmssdmf.commonlib.config.HttpVersionConfig;
import com.nmssdmf.commonlib.config.PrefrenceConfig;
import com.nmssdmf.commonlib.httplib.HttpUtils;
import com.nmssdmf.commonlib.httplib.RxRequest;
import com.nmssdmf.commonlib.httplib.ServiceCallback;
import com.nmssdmf.commonlib.util.PreferenceUtil;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.activity.LifeServiceActivity;
import com.zhihangjia.mainmodule.activity.XYTelecomActivity;
import com.zhihangjia.mainmodule.bean.MainBean;
import com.zhihangjia.mainmodule.callback.MainFragmentCB;
import com.zhixingjia.bean.mainmodule.IndexBean;
import com.zhixingjia.service.MainService;

import java.util.List;

/**
 * @author chenbin
 * @version v3.2.0
 * @description 首页viewmodel
 * @date 2018/11/13 15:23
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

    public void getIndex(boolean isReadCash) {
        if (isReadCash) {
            String indexInfo = PreferenceUtil.getString(PrefrenceConfig.INDEX_INFO, "");
            if (!TextUtils.isEmpty(indexInfo)) {
                try {
                    BaseData<IndexBean> indexBeanBaseData = new Gson().fromJson(indexInfo, new TypeToken<BaseData<IndexBean>>() {
                    }.getType());
                    if (indexBeanBaseData != null) {
                        setData(indexBeanBaseData);
                    }

                } catch (Exception e) {

                }
            }
        }
        HttpUtils.doHttp(subscription, RxRequest.create(MainService.class, HttpVersionConfig.API_INDEX).getIndex(),
                new ServiceCallback<BaseData<IndexBean>>() {
                    @Override
                    public void onError(Throwable error) {
                        cb.setRefreshend();
                    }

                    @Override
                    public void onSuccess(BaseData<IndexBean> indexBeanBaseData) {
                        setData(indexBeanBaseData);
                        cb.setRefreshend();
                        PreferenceUtil.setStringValue(PrefrenceConfig.INDEX_INFO, new Gson().toJson(indexBeanBaseData));
                    }

                    @Override
                    public void onDefeated(BaseData<IndexBean> indexBeanBaseData) {
                        cb.setRefreshend();
                    }
                });
    }

    private void setData(BaseData<IndexBean> indexBeanBaseData) {
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
        for (IndexBean.BannerFixedBean bannerFixedBean : fixedBeanList) {
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

    /**
     * 点击建材家居
     * @param view
     */
    public void onMaterialsClick(View view) {
        cb.toOtherFragment(1);
    }

    /**
     * 点击信息中心
     * @param view
     */
    public void onMessageCenterClick(View view) {
        cb.toOtherFragment(2);
    }

    public void onXYTelecomClick(View view) {
        cb.doIntent(XYTelecomActivity.class, null);
    }

    public void onLifeServiceClick(View view) {
        cb.doIntent(LifeServiceActivity.class, null);
    }
}
