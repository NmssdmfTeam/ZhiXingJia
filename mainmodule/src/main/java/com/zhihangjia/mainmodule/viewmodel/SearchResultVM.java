package com.zhihangjia.mainmodule.viewmodel;

import android.os.Bundle;

import com.nmssdmf.commonlib.bean.BaseListData;
import com.nmssdmf.commonlib.config.HttpVersionConfig;
import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.httplib.HttpUtils;
import com.nmssdmf.commonlib.httplib.RxRequest;
import com.nmssdmf.commonlib.httplib.ServiceCallback;
import com.nmssdmf.commonlib.viewmodel.BaseTitleRecyclerViewVM;
import com.zhihangjia.mainmodule.callback.SearchResultCB;
import com.zhixingjia.bean.mainmodule.Commodity;
import com.zhixingjia.bean.mainmodule.Seller;
import com.zhixingjia.service.MainService;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ${nmssdmf} on 2018/11/26 0026.
 */

public class SearchResultVM extends BaseTitleRecyclerViewVM {
    private String type = SearchFragmentVM.TYPE_MATERIALS_MERCHANT;

    private SearchResultCB cb;

    private String keyword;

    private int page = 1;

    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public SearchResultVM(SearchResultCB callBack) {
        super(callBack);
        cb = callBack;
    }

    public void getData() {
        Bundle bundle = cb.getIntentData();
        type = bundle.getString(IntentConfig.TYPE);
        keyword = bundle.getString(IntentConfig.KEYWORD);
        cb.refreshTitle(keyword);
    }

    @Override
    public void initData(final boolean isRefresh) {
        if (isRefresh)
            page = 1;
        switch (type) {
            case SearchFragmentVM.TYPE_MATERIALS_MERCHANT: {
                getMerchantData(isRefresh);
                break;
            }
            case SearchFragmentVM.TYPE_MATERIALS_MERCHANDISE: {
                getMerchanDise(isRefresh);
                break;
            }
            case SearchFragmentVM.TYPE_INFORMATION_CENTER: {
                break;
            }
        }

    }

    public void getMerchanDise(final boolean isRefresh) {
        Map<String, String> map = new HashMap<>();
        map.put("source", "search");
        map.put("pages", String.valueOf(page));
        map.put("keyword", keyword);
        HttpUtils.doHttp(subscription, RxRequest.create(MainService.class, HttpVersionConfig.API_HOUSE_COMMODITY).getCommodity(map), new ServiceCallback<BaseListData<Commodity>>() {
            @Override
            public void onError(Throwable error) {

            }

            @Override
            public void onSuccess(BaseListData<Commodity> data) {
                cb.refreshAdapter(isRefresh, data.getData());
                page += 1;
            }

            @Override
            public void onDefeated(BaseListData<Commodity> data) {

            }
        });
    }

    public void getMerchantData(final boolean isRefresh) {
        Map<String, String> map = new HashMap<>();
        map.put("source", "search");
        map.put("pages", String.valueOf(page));
        map.put("keyword", keyword);
        HttpUtils.doHttp(subscription, RxRequest.create(MainService.class, HttpVersionConfig.API_HOUSE_SELLER).getSeller(map), new ServiceCallback<BaseListData<Seller>>() {
            @Override
            public void onError(Throwable error) {

            }

            @Override
            public void onSuccess(BaseListData<Seller> data) {
                if (data.getData() != null && data.getData().size() > 0) {
                    cb.refreshAdapter(isRefresh, data.getData());
                    page += 1;
                }
            }

            @Override
            public void onDefeated(BaseListData<Seller> data) {

            }
        });
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
