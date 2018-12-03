package com.zhihangjia.mainmodule.viewmodel;

import android.databinding.ObservableField;
import android.os.Bundle;
import android.view.View;

import com.nmssdmf.commonlib.bean.BaseData;
import com.nmssdmf.commonlib.bean.BaseListData;
import com.nmssdmf.commonlib.config.HttpVersionConfig;
import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.httplib.HttpUtils;
import com.nmssdmf.commonlib.httplib.RxRequest;
import com.nmssdmf.commonlib.httplib.ServiceCallback;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.activity.MapActivity;
import com.zhihangjia.mainmodule.callback.MerchantMainCB;
import com.zhixingjia.bean.mainmodule.ShopInfo;
import com.zhixingjia.service.MainService;

import java.util.Observable;

public class MerchantMainVM extends BaseVM {
    public String member_id = "14";
    public final ObservableField<ShopInfo> shopInfo = new ObservableField<>();
    private MerchantMainCB callback;

    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public MerchantMainVM(MerchantMainCB callBack) {
        super(callBack);
        this.callback = callBack;
        initData();
    }

    private void initData() {
        Bundle bundle = callback.getIntentData();
        if (bundle != null) {
            member_id = bundle.getString(IntentConfig.ID);
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
        Bundle bundle = new Bundle();
        bundle.putDouble(IntentConfig.LAT, Double.valueOf(shopInfo.get().getMember().getLatitude()));
        bundle.putDouble(IntentConfig.LONG, Double.valueOf(shopInfo.get().getMember().getLongitude()));
        bundle.putString(IntentConfig.COMPANY_NAME, shopInfo.get().getMember().getCompany_name());
        bundle.putString(IntentConfig.LOCATION, shopInfo.get().getMember().getCo_addr());
        callback.doIntent(MapActivity.class, bundle);
    }
}
