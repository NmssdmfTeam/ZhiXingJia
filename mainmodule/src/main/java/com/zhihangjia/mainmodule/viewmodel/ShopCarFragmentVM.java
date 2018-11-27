package com.zhihangjia.mainmodule.viewmodel;

import android.databinding.Observable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;
import com.nmssdmf.commonlib.bean.BaseData;
import com.nmssdmf.commonlib.bean.BaseListData;
import com.nmssdmf.commonlib.config.HttpVersionConfig;
import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.httplib.HttpUtils;
import com.nmssdmf.commonlib.httplib.RxRequest;
import com.nmssdmf.commonlib.httplib.ServiceCallback;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.activity.ConfirmOrderActivity;
import com.zhihangjia.mainmodule.bean.ShopCarIntent;
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
    public final ObservableField<String> totalPrice = new ObservableField<>("0");

    public final ObservableBoolean select = new ObservableBoolean(false);

    public final ObservableBoolean edit = new ObservableBoolean(false);

    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public ShopCarFragmentVM(ShopCarFragmentCB callBack) {
        super(callBack);
        cb = callBack;

        select.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).setSelect(select.get());
                    for (int j = 0; j < list.get(i).getProduct_list().size(); j++) {
                        list.get(i).getProduct_list().get(j).setSelect(select.get());
                        List<ShopCar.ProductListBean.SkuListBean> skuListBeans = list.get(i).getProduct_list().get(j).getSku_list();
                        if (skuListBeans != null && skuListBeans.size() > 0) {
                            for (int k = 0; k < skuListBeans.size(); k++) {
                                skuListBeans.get(k).setSelect(select.get());
                            }
                        }
                    }
                    list.get(i).setTotalPrice(getTotalPrice(list.get(i)));
                }

                countTotalPrice();
            }
        });
    }

    public void getData(final boolean refresh) {
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

    /**
     * 计算item的总价
     * @param item
     * @return
     */
    private String getTotalPrice(ShopCar item) {
        String totalPrice = "0";
        List<ShopCar.ProductListBean> productListBeans = item.getProduct_list();
        for (int i = 0; i < productListBeans.size(); i++) {
            ShopCar.ProductListBean productListBean = productListBeans.get(i);
            List<ShopCar.ProductListBean.SkuListBean> skuListBeans = productListBean.getSku_list();
            if (skuListBeans != null && skuListBeans.size() == 0) {
                continue;
            }
            for (int j = 0; j < skuListBeans.size(); j++) {
                ShopCar.ProductListBean.SkuListBean skuListBean = skuListBeans.get(j);
                if (skuListBean.isSelect())
                    totalPrice = new BigDecimal(totalPrice).add(new BigDecimal(skuListBean.getSku_price()).multiply(new BigDecimal(skuListBean.getSku_sum()))).toString();
            }
        }
        return new BigDecimal(totalPrice).setScale(2, BigDecimal.ROUND_DOWN).toString();
    }

    public void changeSelect(){
        boolean select = true;
        for (int i = 0; i < list.size(); i++) {
            if (!list.get(i).isSelect()) {
                select = false;
                break;
            }
        }
        this.select.set(select);
    }

    /**
     * 计算价格，并调整选择状态
     */
    public void countTotalPrice() {
        String price = "0";
        for (int i = 0; i < list.size(); i++) {
            price = new BigDecimal(price).add(new BigDecimal(list.get(i).getTotalPrice())).toString();
        }
        totalPrice.set(new BigDecimal(price).setScale(2, BigDecimal.ROUND_DOWN).toString());
    }

    public void tvSettleAccountsClick(View view) {
        if (edit.get()) {//删除
            delete();
        } else {
            String carIntentsString = getCrdIntents();
            Bundle bundle = new Bundle();
            bundle.putString(IntentConfig.SKU_JSON, carIntentsString);
            cb.doIntent(ConfirmOrderActivity.class, bundle);
        }
    }

    private String getCrdIntents(){
        List<ShopCarIntent> carIntents = new ArrayList<>();
        List<String> ids = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.get(i).getProduct_list().size(); j++) {
                List<ShopCar.ProductListBean.SkuListBean> skuListBeans = list.get(i).getProduct_list().get(j).getSku_list();
                if (skuListBeans != null && skuListBeans.size() > 0) {
                    for (int k = 0; k < skuListBeans.size(); k++) {
                        if (skuListBeans.get(k).isSelect()) {
                            ShopCarIntent shopCarIntent = new ShopCarIntent();
                            shopCarIntent.setSku_sum(skuListBeans.get(k).getSku_sum());
                            shopCarIntent.setId(skuListBeans.get(k).getCart_id());
                        }
                    }
                }
            }
        }
        return new Gson().toJson(carIntents);
    }

    private void delete(){
        String ids = getDeleteIds();
        cb.showLoaddingDialog();
        HttpUtils.doHttp(subscription, RxRequest.create(MainService.class, HttpVersionConfig.API_CART_DEL).shopCarDelete(ids), new ServiceCallback<BaseData>() {
            @Override
            public void onError(Throwable error) {

            }

            @Override
            public void onSuccess(BaseData data) {
                getData(true);
            }

            @Override
            public void onDefeated(BaseData data) {

            }
        });
    }

    private String getDeleteIds(){
        List<String> ids = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.get(i).getProduct_list().size(); j++) {
                List<ShopCar.ProductListBean.SkuListBean> skuListBeans = list.get(i).getProduct_list().get(j).getSku_list();
                if (skuListBeans != null && skuListBeans.size() > 0) {
                    for (int k = 0; k < skuListBeans.size(); k++) {
                        if (skuListBeans.get(k).isSelect()) {
                            ids.add(skuListBeans.get(k).getCart_id());
                        }
                    }
                }
            }
        }
        return new Gson().toJson(ids.toArray());
    }

    public void editClick(View view) {
        edit.set(!edit.get());
    }

    public List<ShopCar> getList() {
        return list;
    }

    public void setList(List<ShopCar> list) {
        this.list = list;
    }
}
