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
import com.zhihangjia.mainmodule.activity.PoliticsNoticeActivity;
import com.zhihangjia.mainmodule.activity.XYTelecomActivity;
import com.zhihangjia.mainmodule.bean.MainBean;
import com.zhihangjia.mainmodule.callback.MainFragmentCB;
import com.zhixingjia.bean.mainmodule.Banner;
import com.zhixingjia.bean.mainmodule.IndexBean;
import com.zhixingjia.bean.mainmodule.MessageUnread;
import com.zhixingjia.service.MainService;

/**
 * @author chenbin
 * @version v3.2.0
 * @description 首页viewmodel
 * @date 2018/11/13 15:23
 */
public class MainFragmentVM extends BaseVM {
    private MainFragmentCB cb;
    private Banner.CommomBanner banner_middle;

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

    public void getIndexBanner(boolean isReadCash) {
        if (isReadCash) {
            String indexBanner = PreferenceUtil.getString(PrefrenceConfig.INDEX_BANNER, "");
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
        HttpUtils.doHttp(subscription, RxRequest.create(MainService.class, HttpVersionConfig.API_INDEX).getBanner("index"),
                new ServiceCallback<BaseData<Banner>>() {
                    @Override
                    public void onError(Throwable error) {
                    }

                    @Override
                    public void onSuccess(BaseData<Banner> indexBeanBaseData) {
                        setBanner(indexBeanBaseData.getData());
                        PreferenceUtil.setStringValue(PrefrenceConfig.INDEX_INFO, new Gson().toJson(indexBeanBaseData));
                    }

                    @Override
                    public void onDefeated(BaseData<Banner> indexBeanBaseData) {
                    }
                });
    }

    private void setData(BaseData<IndexBean> indexBeanBaseData) {

        cb.setFillerView(indexBeanBaseData.getData().getArticle());

        MainBean sellerBean = new MainBean();
        sellerBean.setItemType(0);
        sellerBean.setSellerBeans(indexBeanBaseData.getData().getSeller());
        cb.setExcellentSeller(sellerBean);

        MainBean commodityBean = new MainBean();
        commodityBean.setItemType(1);
        commodityBean.setBannerMiddle(banner_middle);
        commodityBean.setCommodityBeans(indexBeanBaseData.getData().getCommodity());
        cb.setCommodity(commodityBean);

        MainBean forumBean = new MainBean();
        forumBean.setItemType(3);
        forumBean.setForumBeans(indexBeanBaseData.getData().getForum());
        cb.setForum(forumBean);
    }

    /**
     * 这只广告内容
     */
    private void setBanner(Banner banner) {
        cb.setRollPagerView(banner.getBanner_top());                //设置化轮播图广告
        if (banner.getBanner_middle().size() > 0) {                 //设置列表中间的广告
            banner_middle = banner.getBanner_middle().get(0);
            cb.setBannerMiddle(banner_middle);
        }
        cb.setBannerFixed(banner.getBanner_fixed());                //设置左上下广告
    }

    public void getMessageUnread() {
        if (!TextUtils.isEmpty(PreferenceUtil.getString(PrefrenceConfig.TOKEN,""))) {
            HttpUtils.doHttp(subscription,
                    RxRequest.create(MainService.class, HttpVersionConfig.API_MESSAGE_UNREAD).getMessageUnread(),
                    new ServiceCallback<BaseData<MessageUnread>>() {
                        @Override
                        public void onError(Throwable error) {

                        }

                        @Override
                        public void onSuccess(BaseData<MessageUnread> messageUnreadBaseData) {
                            //设置是否显示小红点
                            cb.showNotice(messageUnreadBaseData.getData());
                        }

                        @Override
                        public void onDefeated(BaseData<MessageUnread> messageUnreadBaseData) {

                        }
                    });
        }
    }

    /**
     * 点击建材家居
     *
     * @param view
     */
    public void onMaterialsClick(View view) {
        cb.toOtherFragment(1);
    }

    /**
     * 点击信息中心
     *
     * @param view
     */
    public void onMessageCenterClick(View view) {
        cb.toOtherFragment(2);
    }

    public void onXYTelecomClick(View view) {
        cb.doIntent(XYTelecomActivity.class, null);
    }

    /**
     * 政务公告
     * @param view
     */
    public void onGovernmentClick(View view) {
        cb.doIntent(PoliticsNoticeActivity.class, null);
    }

    public void onLifeServiceClick(View view) {
        cb.doIntent(LifeServiceActivity.class, null);
    }
}
