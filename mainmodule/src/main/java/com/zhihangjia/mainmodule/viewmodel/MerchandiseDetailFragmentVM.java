package com.zhihangjia.mainmodule.viewmodel;

import android.databinding.ObservableField;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.commonlib.bean.BaseData;
import com.nmssdmf.commonlib.config.HttpVersionConfig;
import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.httplib.HttpUtils;
import com.nmssdmf.commonlib.httplib.RxRequest;
import com.nmssdmf.commonlib.httplib.ServiceCallback;
import com.nmssdmf.commonlib.rxbus.RxBus;
import com.nmssdmf.commonlib.rxbus.RxEvent;
import com.nmssdmf.commonlib.util.ToastUtil;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.activity.ConfirmOrderActivity;
import com.zhihangjia.mainmodule.activity.MainActivity;
import com.zhihangjia.mainmodule.activity.MerchantMainActivity;
import com.zhihangjia.mainmodule.adapter.MerchandiseDetailChooseCouponAdapter;
import com.zhihangjia.mainmodule.callback.MerchandiseDetailFragmentCB;
import com.zhixingjia.bean.mainmodule.CommodityDetail;
import com.zhixingjia.service.MainService;

import java.util.HashMap;
import java.util.Map;

public class MerchandiseDetailFragmentVM extends BaseVM implements MerchandiseDetailChooseCouponAdapter.MerchandiseDetailChooseCouponAdapterListener{
    private MerchandiseDetailFragmentCB cb;
    public String commodityId = "1";

    public ObservableField<CommodityDetail> commodityDetail = new ObservableField<>();

    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public MerchandiseDetailFragmentVM(MerchandiseDetailFragmentCB callBack) {
        super(callBack);
        cb = callBack;
        commodityDetail.set(new CommodityDetail());
    }

    public void ivShareClick(View view){
        cb.toShare(commodityDetail.get());
    }

    public void ivAddShoppingCarClick(View view){
        Bundle bundle = new Bundle();
        bundle.putInt(IntentConfig.POSITION, 3);
        cb.doIntent(MainActivity.class, bundle);
    }

    public void ivBackClick(View view){
        cb.onBack();
    }

    public void gotoCommentList(View view){
        cb.gotoCommentDetail();
    }

    public void getCoupon(View view){
        cb.showChooseCouponWindow();
    }

    public void chooseSpecification(View view){
        cb.showChooseSpecificationWindow();
    }

    public void onMerchantsClick(View view) {
        Bundle bundle = new Bundle();
        bundle.putString(IntentConfig.ID, commodityDetail.get().getProvider_id());
        bundle.putInt(IntentConfig.TYPE, 1);
        cb.doIntent(MerchantMainActivity.class, bundle);
    }

    /**
     * 获取商品详情
     */
    public void getCommondityDetail() {
        cb.showLoaddingDialog();
        HttpUtils.doHttp(subscription,
                RxRequest.create(MainService.class, HttpVersionConfig.API_HOUSE_COMMODITY_VIEW).getCommodity(commodityId),
                new ServiceCallback<BaseData<CommodityDetail>>() {
                    @Override
                    public void onError(Throwable error) {

                    }

                    @Override
                    public void onSuccess(BaseData<CommodityDetail> commodityDetailBaseData) {
                        commodityDetail.set(commodityDetailBaseData.getData());
                        cb.setCommodityImgs(commodityDetailBaseData.getData().getImgs());
                        cb.initView();
                        cb.setCommodityComment(commodityDetailBaseData.getData().getOrder_comment());

                    }

                    @Override
                    public void onDefeated(BaseData<CommodityDetail> commodityDetailBaseData) {

                    }
                });
    }

    /**
     * 加入购物车
     */
    public void addCartStore() {
        Map<String,String> map = new HashMap<>();
        Map<String,String> inputData = isDataValidate();
        if (inputData == null) {
            return;
        }
        map.putAll(inputData);
        map.put("commodity_id", commodityId);
        cb.showLoaddingDialog();
        HttpUtils.doHttp(subscription,
                RxRequest.create(MainService.class, HttpVersionConfig.API_CART_STORE).getCartStore(map),
                new ServiceCallback<Base>() {
            @Override
            public void onError(Throwable error) {

            }

            @Override
            public void onSuccess(Base base) {
                cb.showToast("添加购物车成功");
                cb.showShopCarRedPoint();
                RxBus.getInstance().send(RxEvent.OrderEvent.SHOPCAR_ADD, null);
            }

            @Override
            public void onDefeated(Base base) {

            }
        });
    }

    private Map<String,String> isDataValidate() {
        Map<String,String> map = new HashMap<>();
        if (commodityDetail.get().getSepc_val() != null && commodityDetail.get().getSepc_val().size() > 0) {
            String product_sku_id = cb.getProductSkuId();
            if (TextUtils.isEmpty(product_sku_id)) {
                cb.showToast("请选择规格");
                cb.showChooseSpecificationWindow();
                return null;
            }
            map.put("product_sku_id", product_sku_id);
        }
        String goodsSum = cb.getGoodsSum();
        if ("0".equals(goodsSum) || TextUtils.isEmpty(goodsSum)) {
            baseCallBck.showToast("请填写购买数量");
            cb.showChooseSpecificationWindow();
            return null;
        }
        map.put("goods_sum", String.valueOf(goodsSum));
        return map;
    }

    public void buyNow() {
        Map<String,String> inputData = isDataValidate();
        if (inputData == null) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString(IntentConfig.COMMODITY_ID, commodityId);
        bundle.putString(IntentConfig.PRODUCT_SKU_ID, inputData.get("product_sku_id"));
        bundle.putString(IntentConfig.GOODS_SUM, inputData.get("goods_sum"));
        baseCallBck.doIntent(ConfirmOrderActivity.class, bundle);
    }

    @Override
    public void getCoupon(String id) {
        cb.showLoaddingDialog();
        HttpUtils.doHttp(subscription, RxRequest.create(MainService.class, HttpVersionConfig.API_COUPON_RECEIVE).getCoupon(id), new ServiceCallback<BaseData>() {
            @Override
            public void onError(Throwable error) {

            }

            @Override
            public void onSuccess(BaseData data) {
                ToastUtil.showMsg(data.getMessage());
            }

            @Override
            public void onDefeated(BaseData data) {

            }
        });
    }
}
