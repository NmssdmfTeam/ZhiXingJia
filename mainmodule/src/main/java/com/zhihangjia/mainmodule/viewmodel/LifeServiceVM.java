package com.zhihangjia.mainmodule.viewmodel;

import android.os.Bundle;
import android.text.TextUtils;

import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.commonlib.bean.BaseData;
import com.nmssdmf.commonlib.bean.BaseListData;
import com.nmssdmf.commonlib.config.HttpVersionConfig;
import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.httplib.HttpUtils;
import com.nmssdmf.commonlib.httplib.RxRequest;
import com.nmssdmf.commonlib.httplib.ServiceCallback;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.callback.LifeServiceCB;
import com.zhixingjia.bean.mainmodule.Banner;
import com.zhixingjia.bean.mainmodule.HouseBean;
import com.zhixingjia.bean.mainmodule.LifeService;
import com.zhixingjia.service.MainService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${nmssdmf} on 2018/11/23 0023.
 */

public class LifeServiceVM extends BaseVM {
    private LifeServiceCB cb;
    private String cateId;
    public String cateName;
    public List<HouseBean.CateBean> cateBeans;
    private String page = "0";

    private List<Base> list = new ArrayList<>();
    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public LifeServiceVM(LifeServiceCB callBack) {
        super(callBack);
        this.cb = callBack;
        initData();
        getBanners();
    }

    private void initData() {
        Bundle bundle = cb.getIntentData();
        if (bundle != null) {
            cateId = bundle.getString(IntentConfig.ID);
            cateName = bundle.getString(IntentConfig.NAME);
            cateBeans = (List<HouseBean.CateBean>) bundle.getSerializable(IntentConfig.LIFE_CATE);
        }
    }

    public void getData(boolean isRefresh){
        if (isRefresh)
            page = "0";
        else
            page = cb.getPage();
        HttpUtils.doHttp(subscription, RxRequest.create(MainService.class, HttpVersionConfig.API_LIFE_INDEX).getLifeService(page, cateId),
                new ServiceCallback<BaseListData<LifeService>>() {
            @Override
            public void onError(Throwable error) {

            }

            @Override
            public void onSuccess(BaseListData<LifeService> lifeServiceBaseListData) {
                cb.setData(lifeServiceBaseListData.getData(),isRefresh);
            }

            @Override
            public void onDefeated(BaseListData<LifeService> lifeServiceBaseListData) {

            }
        });
    }

    /**
     * 获取广告
     */
    public void getBanners() {
        HttpUtils.doHttp(subscription,
                RxRequest.create(MainService.class, HttpVersionConfig.API_BANNERS).getBanner("life"),
                new ServiceCallback<BaseData<Banner>>() {
            @Override
            public void onError(Throwable error) {

            }

            @Override
            public void onSuccess(BaseData<Banner> bannerBaseData) {
                cb.setBanner(bannerBaseData.getData());
            }

            @Override
            public void onDefeated(BaseData<Banner> bannerBaseData) {

            }
        });
    }

    /**
     * 获取建材家居分类列表
     */
    public void getLifeCate() {
        if(cateBeans != null)
            cb.setCategoryData(cateBeans);
        else {
            HttpUtils.doHttp(subscription,
                    RxRequest.create(MainService.class, HttpVersionConfig.API_LIFE_CATE).getLifeCate(),
                    new ServiceCallback<BaseListData<HouseBean.CateBean>>() {
                        @Override
                        public void onError(Throwable error) {

                        }

                        @Override
                        public void onSuccess(BaseListData<HouseBean.CateBean> cateBeanBaseListData) {
                            cateBeans = cateBeanBaseListData.getData();
                            if (TextUtils.isEmpty(cateId)) {
                                if (cateBeanBaseListData.getData().size() > 0) {
                                    cateId = cateBeanBaseListData.getData().get(0).getCate_id();
                                    cb.setLifeServiceTitile(cateBeanBaseListData.getData().get(0).getCate_name());
                                    getData(true);
                                }
                            }
                            cb.setCategoryData(cateBeans);
                        }

                        @Override
                        public void onDefeated(BaseListData<HouseBean.CateBean> cateBeanBaseListData) {

                        }

                    });
        }
    }

    public List<Base> getList() {
        return list;
    }

    public void setList(List<Base> list) {
        this.list = list;
    }

    public String getCateId() {
        return cateId;
    }

    public void setCateId(String cateId) {
        this.cateId = cateId;
    }
}
