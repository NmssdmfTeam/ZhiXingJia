package com.zhihangjia.mainmodule.viewmodel;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.view.View;

import com.nmssdmf.commonlib.bean.BaseListData;
import com.nmssdmf.commonlib.callback.BaseRecyclerViewFragmentCB;
import com.nmssdmf.commonlib.config.HttpVersionConfig;
import com.nmssdmf.commonlib.httplib.HttpUtils;
import com.nmssdmf.commonlib.httplib.RxRequest;
import com.nmssdmf.commonlib.httplib.ServiceCallback;
import com.nmssdmf.commonlib.viewmodel.BaseRecyclerViewFragmentVM;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.callback.MerchantAllFragmentCB;
import com.zhixingjia.bean.mainmodule.Commodity;
import com.zhixingjia.service.MainService;

public class MerchantAllFragmentVM extends BaseRecyclerViewFragmentVM {
    public String memberId;
    private int pages = 1;
    private MerchantAllFragmentCB callback;
    public final ObservableField<String> sortType = new ObservableField<>("all");

    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public MerchantAllFragmentVM(MerchantAllFragmentCB callBack) {
        super(callBack);
        this.callback = callBack;
    }

    @Override
    public void initData(boolean isRefresh) {
        if (isRefresh)
            pages = 1;
        HttpUtils.doHttp(subscription, RxRequest.create(MainService.class,
                HttpVersionConfig.API_SHOPINFO_COMMODITY).getShopInfoCommodity(memberId, pages, sortType.get()),
                new ServiceCallback<BaseListData<Commodity>>() {
            @Override
            public void onError(Throwable error) {

            }

            @Override
            public void onSuccess(BaseListData<Commodity> commodityBaseListData) {
                callback.refreshAdapter(isRefresh, commodityBaseListData.getData());
                pages++;
            }

            @Override
            public void onDefeated(BaseListData<Commodity> commodityBaseListData) {

            }
        });
    }

    public void onSortClick(View view) {
        if (view.getId() == R.id.tvAll) {
            sortType.set("all");
        } else if (view.getId() == R.id.tvSales) {
            sortType.set("sales");
        } else if (view.getId() == R.id.tvPrice) {
            sortType.set("price");
        }
        initData(true);
    }
}
