package com.zhihangjia.mainmodule.viewmodel;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nmssdmf.commonlib.bean.BaseData;
import com.nmssdmf.commonlib.callback.BaseCB;
import com.nmssdmf.commonlib.config.HttpVersionConfig;
import com.nmssdmf.commonlib.config.PrefrenceConfig;
import com.nmssdmf.commonlib.httplib.HttpUtils;
import com.nmssdmf.commonlib.httplib.RxRequest;
import com.nmssdmf.commonlib.httplib.ServiceCallback;
import com.nmssdmf.commonlib.util.PreferenceUtil;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.bean.House;
import com.zhihangjia.mainmodule.callback.MarketFragmentCB;
import com.zhixingjia.bean.mainmodule.Banner;
import com.zhixingjia.bean.mainmodule.HouseBean;
import com.zhixingjia.bean.mainmodule.IndexBean;
import com.zhixingjia.service.MainService;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by chenbin on 2018/11/15
 * <p>
 * <p>
 */
public class MarketFragmentVM extends BaseVM {
    private MarketFragmentCB callBack;

    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public MarketFragmentVM(MarketFragmentCB callBack) {
        super(callBack);
        this.callBack = callBack;
    }

    /**
     * 首页建材家具内容
     */
    public void getHouseIndex(boolean isReadCash) {
        if (isReadCash) {
            String indexInfo = PreferenceUtil.getString(PrefrenceConfig.HOUSE_INDEX, "");
            if (!TextUtils.isEmpty(indexInfo)) {
                try {
                    BaseData<HouseBean> indexBeanBaseData = new Gson().fromJson(indexInfo, new TypeToken<BaseData<HouseBean>>() {
                    }.getType());
                    if (indexBeanBaseData != null) {
                        setData(indexBeanBaseData.getData());
                    }

                } catch (Exception e) {

                }
            }
        }
        HttpUtils.doHttp(subscription,
                RxRequest.create(MainService.class, HttpVersionConfig.API_HOUSE).getHouseIndex(),
                new ServiceCallback<BaseData<HouseBean>>() {
            @Override
            public void onError(Throwable error) {
                callBack.endFresh();
            }

            @Override
            public void onSuccess(BaseData<HouseBean> houseBeanBaseData) {
                callBack.endFresh();
                PreferenceUtil.setStringValue(PrefrenceConfig.HOUSE_INDEX, new Gson().toJson(houseBeanBaseData));
                setData(houseBeanBaseData.getData());
            }

            @Override
            public void onDefeated(BaseData<HouseBean> houseBeanBaseData) {
                callBack.endFresh();
            }
        });
    }

    /**
     * 获取广告
     * @param isReadCash
     */
    public void getHouseBanner(boolean isReadCash) {
        if (isReadCash) {
            String indexBanner = PreferenceUtil.getString(PrefrenceConfig.HOUSE_BANNER, "");
            if (!TextUtils.isEmpty(indexBanner)) {
                try {
                    BaseData<Banner> indexBannerData = new Gson().fromJson(indexBanner, new TypeToken<BaseData<Banner>>() {
                    }.getType());
                    if (indexBannerData != null) {
                        setBanner(indexBannerData.getData());
                    }

                } catch (Exception e) {

                }
            }
        }
        HttpUtils.doHttp(subscription, RxRequest.create(MainService.class, HttpVersionConfig.API_INDEX).getBanner("house"),
                new ServiceCallback<BaseData<Banner>>() {
                    @Override
                    public void onError(Throwable error) {
                    }

                    @Override
                    public void onSuccess(BaseData<Banner> indexBeanBaseData) {
                        setBanner(indexBeanBaseData.getData());
                        PreferenceUtil.setStringValue(PrefrenceConfig.HOUSE_BANNER, new Gson().toJson(indexBeanBaseData));
                    }

                    @Override
                    public void onDefeated(BaseData<Banner> indexBeanBaseData) {
                    }
                });
    }

    private void setData(HouseBean houseBean) {
        if (houseBean != null) {
            HouseBean.CateBean cateBean = new HouseBean.CateBean();
            cateBean.setCate_name("全部分类");
            houseBean.getCate().add(cateBean);
            callBack.setCatData(houseBean.getCate());

            callBack.setBrandData(houseBean.getBrands());
            List<House> houses = new ArrayList<>();
            //爆款推荐
            House hothouse = new House();
            hothouse.setItemType(0);
            hothouse.setProduct(houseBean.getProduct());
            houses.add(hothouse);

            //推荐商家
            House seller = new House();
            seller.setItemType(1);
            seller.setSeller(houseBean.getSeller());
            houses.add(seller);
            callBack.setListData(houses);
        }
    }

    private void setBanner(Banner banner) {
        callBack.setRollPagerBanner(banner.getBanner_top());
        //设置中间的广告
        if (banner.getBanner_middle() != null && banner.getBanner_middle().size() > 0)
            callBack.setMiddleBanner(banner.getBanner_middle().get(0));
        else
            callBack.setMiddleBanner(null);
    }
}
