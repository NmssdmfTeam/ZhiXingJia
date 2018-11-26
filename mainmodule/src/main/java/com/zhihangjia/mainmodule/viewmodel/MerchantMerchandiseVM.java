package com.zhihangjia.mainmodule.viewmodel;

import android.databinding.Observable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableInt;
import android.view.View;

import com.nmssdmf.commonlib.bean.BaseListData;
import com.nmssdmf.commonlib.config.HttpVersionConfig;
import com.nmssdmf.commonlib.httplib.HttpUtils;
import com.nmssdmf.commonlib.httplib.RxRequest;
import com.nmssdmf.commonlib.httplib.ServiceCallback;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.activity.SearchActivity;
import com.zhihangjia.mainmodule.callback.MerchantMerchandiseCB;
import com.zhixingjia.bean.mainmodule.TradeArea;
import com.zhixingjia.service.MainService;

import java.util.ArrayList;
import java.util.List;

public class MerchantMerchandiseVM extends BaseVM {
    public static final int TYPE_MERCHANT = 0;//商家
    public static final int TYPE_MERCHANDISE = 1;//商品
    public final ObservableInt type = new ObservableInt(TYPE_MERCHANT);//0表示商家, 1：表示商品

    public final ObservableBoolean tvMerchantChooseSelect = new ObservableBoolean(false);//商家
    public final ObservableBoolean tvPopularitySelect = new ObservableBoolean(true);//true:人气最高, false:好评优先

    private List<TradeArea> areaList = new ArrayList<>();

    private MerchantMerchandiseCB cb;

    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public MerchantMerchandiseVM(MerchantMerchandiseCB callBack) {
        super(callBack);
        cb = callBack;
        type.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                cb.changeViewpager();
            }
        });
    }

    /**
     * 获取商圈地址
     */
    public void getTradeAreaList() {
        HttpUtils.doHttp(subscription, RxRequest.create(MainService.class, HttpVersionConfig.API_TRADE_AREA).getTradeArea(), new ServiceCallback<BaseListData<TradeArea>>() {
            @Override
            public void onError(Throwable error) {

            }

            @Override
            public void onSuccess(BaseListData<TradeArea> data) {
                if (data.getData() != null && data.getData().size() >0) {
                    areaList.addAll(data.getData());
                    cb.refreshAreaAdapter();
                }
            }

            @Override
            public void onDefeated(BaseListData<TradeArea> data) {

            }
        });
    }

    public void search(View view){
        cb.doIntent(SearchActivity.class, null);
    }


    public void tvMerchantClick(View view) {
        type.set(TYPE_MERCHANT);
    }

    public void tvMerchandiseClick(View view) {
        type.set(TYPE_MERCHANDISE);
    }

    public void tvMerchantChooseClick(View view) {
        tvMerchantChooseSelect.set(!tvMerchantChooseSelect.get());
    }

    public void tvPopularityClick(View view, boolean select) {
        if (tvPopularitySelect.get() == select) {
            return;
        }
        tvPopularitySelect.set(select);
        cb.changeSelectType(tvPopularitySelect.get());
    }

    public List<TradeArea> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<TradeArea> areaList) {
        this.areaList = areaList;
    }
}
