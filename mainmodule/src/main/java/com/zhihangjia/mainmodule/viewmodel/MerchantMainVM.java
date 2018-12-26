package com.zhihangjia.mainmodule.viewmodel;

import android.databinding.ObservableField;
import android.os.Bundle;
import android.view.View;

import com.nmssdmf.commonlib.bean.BaseData;
import com.nmssdmf.commonlib.config.HttpVersionConfig;
import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.httplib.HttpUtils;
import com.nmssdmf.commonlib.httplib.RxRequest;
import com.nmssdmf.commonlib.httplib.ServiceCallback;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.callback.MerchantMainCB;
import com.zhixingjia.bean.mainmodule.ShopInfo;
import com.zhixingjia.service.MainService;

public class MerchantMainVM extends BaseVM {
    public String member_id;
    public final ObservableField<ShopInfo> shopInfo = new ObservableField<>();
    private MerchantMainCB callback;
    private int type;

    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public MerchantMainVM(MerchantMainCB callBack) {
        super(callBack);
        this.callback = callBack;

    }

    public void initData() {
        Bundle bundle = callback.getIntentData();
        if (bundle != null) {
            member_id = bundle.getString(IntentConfig.ID);
            type = bundle.getInt(IntentConfig.TYPE);
        }
    }

    /**
     * 获取店铺首页
     */
    public void getShopInfo() {
        HttpUtils.doHttp(subscription,
                RxRequest.create(MainService.class, HttpVersionConfig.API_SHOPINFO).getShopInfo(member_id),
                new ServiceCallback<BaseData<ShopInfo>>() {
            @Override
            public void onError(Throwable error) {

            }

            @Override
            public void onSuccess(BaseData<ShopInfo> shopInfoBaseListData) {
                shopInfo.set(shopInfoBaseListData.getData());
                callback.setHeadData();
            }

            @Override
            public void onDefeated(BaseData<ShopInfo> shopInfoBaseListData) {

            }
        });
    }

    /**
     * 点击拨打电话
     */
    public void onCallClick(View view) {
        callback.callPhone();
    }

    /**
     * 点击显示地图位置
     * @param view
     */
    public void onAddressClick(View view) {
        callback.showChooseMapWindow();
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
