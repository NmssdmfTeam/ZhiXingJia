package com.zhihangjia.mainmodule.viewmodel;

import android.databinding.ObservableField;
import android.view.View;

import com.nmssdmf.commonlib.bean.BaseListData;
import com.nmssdmf.commonlib.config.HttpVersionConfig;
import com.nmssdmf.commonlib.httplib.HttpUtils;
import com.nmssdmf.commonlib.httplib.RxRequest;
import com.nmssdmf.commonlib.httplib.ServiceCallback;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.activity.ConfirmOrderActivity;
import com.zhihangjia.mainmodule.callback.ShopCarFragmentCB;
import com.zhixingjia.bean.mainmodule.ShopCar;
import com.zhixingjia.service.MainService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${nmssdmf} on 2018/11/19 0019.
 */

public class ShopCarFragmentVM extends BaseVM {
    private List<ShopCar> list = new ArrayList<>();
    private ShopCarFragmentCB cb;
    public final ObservableField<String> totalPrice = new ObservableField<>();
    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public ShopCarFragmentVM(ShopCarFragmentCB callBack) {
        super(callBack);
        cb = callBack;
    }

    public void getData(final boolean refresh){
        HttpUtils.doHttp(subscription, RxRequest.create(MainService.class, HttpVersionConfig.API_CART).getShopCarData(), new ServiceCallback<BaseListData<ShopCar>>() {
            @Override
            public void onError(Throwable error) {

            }

            @Override
            public void onSuccess(BaseListData<ShopCar> data) {
                if (data.getData() != null && data.getData().size() > 0) {
                }
                cb.refreshData(data.getData(), refresh);
            }

            @Override
            public void onDefeated(BaseListData<ShopCar> data) {

            }
        });
    }

    public void countTotalPrice(){
        String price = "0";
        for (int i = 0; i < list.size(); i ++) {
            price = new BigDecimal(price).add(new BigDecimal(list.get(i).getTotalPrice())).toString();
        }
        totalPrice.set(new BigDecimal(price).setScale(2, BigDecimal.ROUND_DOWN).toString());
    }

    public void tvSettleAccountsClick(View view){
        cb.doIntent(ConfirmOrderActivity.class, null);
    }

    public List<ShopCar> getList() {
        return list;
    }

    public void setList(List<ShopCar> list) {
        this.list = list;
    }
}
