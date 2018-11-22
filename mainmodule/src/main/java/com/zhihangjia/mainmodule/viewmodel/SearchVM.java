package com.zhihangjia.mainmodule.viewmodel;

import com.nmssdmf.commonlib.bean.BaseListData;
import com.nmssdmf.commonlib.config.HttpVersionConfig;
import com.nmssdmf.commonlib.httplib.HttpUtils;
import com.nmssdmf.commonlib.httplib.RxRequest;
import com.nmssdmf.commonlib.httplib.ServiceCallback;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.callback.SearchCB;
import com.zhixingjia.bean.mainmodule.HotHistory;
import com.zhixingjia.service.MainService;

import java.util.List;

/**
 * Created by ${nmssdmf} on 2018/11/22 0022.
 */

public class SearchVM extends BaseVM {
    private final String HOT_SEARCH_SELLER = "HOT_SEARCH_SELLER";//商家的热门搜索关键字
    private final String HOT_SEARCH_COMMODITY = "HOT_SEARCH_COMMODITY";//商品的热门搜索关键字
    private final String HOT_SEARCH_BBS = "HOT_SEARCH_BBS";//信息中心的热门搜索关键字
    private SearchCB cb;
    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public SearchVM(SearchCB callBack) {
        super(callBack);
        cb = callBack;
    }

    public void getHistory() {
        HttpUtils.doHttp(subscription, RxRequest.create(MainService.class, HttpVersionConfig.API_HOTKEY).getHotHistory(), new ServiceCallback<BaseListData<HotHistory>>() {
            @Override
            public void onError(Throwable error) {

            }

            @Override
            public void onSuccess(BaseListData<HotHistory> data) {
                initUiData(data);
            }

            @Override
            public void onDefeated(BaseListData<HotHistory> hotHistoryBaseListData) {

            }
        });
    }

    private void initUiData(BaseListData<HotHistory> data){
        for (HotHistory history : data.getData()) {
            List<String> hots = history.getValues();
            switch (history.getModel()) {
                case HOT_SEARCH_SELLER:{
                    if (hots != null && hots.size() > 0) {
                        cb.setMaterialsMerchantHotHistory(hots);
                    }
                    break;
                }
                case HOT_SEARCH_COMMODITY:{
                    if (hots != null && hots.size() > 0) {
                        cb.setMaterialsMerchandiseHotHistory(hots);
                    }
                    break;
                }
                case HOT_SEARCH_BBS:{
                    if (hots != null && hots.size() > 0) {
                        cb.setInformationCenterHotHistory(hots);
                    }
                    break;
                }
            }
        }
    }
}
