package com.zhihangjia.mainmodule.viewmodel;

import android.os.Bundle;

import com.nmssdmf.commonlib.bean.BaseListData;
import com.nmssdmf.commonlib.config.HttpVersionConfig;
import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.httplib.HttpUtils;
import com.nmssdmf.commonlib.httplib.RxRequest;
import com.nmssdmf.commonlib.httplib.ServiceCallback;
import com.nmssdmf.commonlib.viewmodel.BaseRecyclerViewFragmentVM;
import com.zhihangjia.mainmodule.callback.MerchandiseFragmentCB;
import com.zhixingjia.bean.mainmodule.Commodity;
import com.zhixingjia.service.MainService;

import java.util.HashMap;
import java.util.Map;

public class MerchandiseFragmentVM extends BaseRecyclerViewFragmentVM {
    private String tradeAreaId = "0";
    private int type = 1;//1:人气最高，2好评优先
    private String cateId;//分类id
    private int page = 1;
    private MerchandiseFragmentCB cb;
    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public MerchandiseFragmentVM(MerchandiseFragmentCB callBack) {
        super(callBack);
        cb = callBack;
    }

    public void getIntentData() {
        Bundle bundle = cb.getIntentData();
        if (bundle != null) {
            cateId = bundle.getString(IntentConfig.ID);
        }
    }

    @Override
    public void initData(final boolean isRefresh) {
        if (isRefresh)
            page = 1;
        Map<String, String> map = new HashMap<>();
        map.put("source", "cate");
        map.put("cate_id", cateId);
        map.put("pages", String.valueOf(page));
        map.put("trade_area", tradeAreaId);
        map.put("sort", type == 1 ? "sales" : "praise");
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

    public String getTradeAreaId() {
        return tradeAreaId;
    }

    public void setTradeAreaId(String tradeAreaId) {
        this.tradeAreaId = tradeAreaId;
        initData(true);
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
        initData(true);
    }
}
