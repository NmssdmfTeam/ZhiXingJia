package com.zhihangjia.mainmodule.viewmodel;

import android.databinding.ObservableField;
import android.text.TextUtils;
import android.view.View;

import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.commonlib.bean.BaseData;
import com.nmssdmf.commonlib.config.HttpVersionConfig;
import com.nmssdmf.commonlib.config.StringConfig;
import com.nmssdmf.commonlib.httplib.HttpUtils;
import com.nmssdmf.commonlib.httplib.RxRequest;
import com.nmssdmf.commonlib.httplib.ServiceCallback;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.callback.MerchandiseDetailFragmentCB;
import com.zhixingjia.bean.mainmodule.CommodityDetail;
import com.zhixingjia.service.MainService;

import java.util.HashMap;
import java.util.Map;

public class MerchandiseDetailFragmentVM extends BaseVM {
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

    }

    public void ivAddShoppingCarClick(View view){

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

    /**
     * 获取商品详情
     */
    public void getCommondityDetail() {
        HttpUtils.doHttp(subscription,
                RxRequest.create(MainService.class, HttpVersionConfig.API_HOUSE_COMMODITY_VIEW).getCommodity("1"),
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
        if (commodityDetail.get().getSepc_val() != null && commodityDetail.get().getSepc_val().size() > 0) {
            String product_sku_id = cb.getProductSkuId();
            if (TextUtils.isEmpty(product_sku_id)) {
                cb.showToast("请选择规格");
                cb.showChooseSpecificationWindow();
                return;
            }
            map.put("product_sku_id", product_sku_id);
        }
        String goodsSum = cb.getGoodsSum();
        if ("0".equals(goodsSum) || TextUtils.isEmpty(goodsSum)) {
            baseCallBck.showToast("请填写购买数量");
            cb.showChooseSpecificationWindow();
            return;
        }
        map.put("goods_sum", String.valueOf(goodsSum));
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
            }

            @Override
            public void onDefeated(Base base) {

            }
        });
    }
}
