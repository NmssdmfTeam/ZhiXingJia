package com.zhixingjia.goodsmanagemodule.viewmodel;

import android.app.Activity;
import android.content.Intent;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;

import com.nmssdmf.commonlib.bean.BaseData;
import com.nmssdmf.commonlib.config.HttpVersionConfig;
import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.httplib.HttpUtils;
import com.nmssdmf.commonlib.httplib.RxRequest;
import com.nmssdmf.commonlib.httplib.ServiceCallback;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhixingjia.bean.mainmodule.CommodityInitialize;
import com.zhixingjia.bean.mainmodule.HouseBean;
import com.zhixingjia.goodsmanagemodule.activity.AddProductDescribeActivity;
import com.zhixingjia.goodsmanagemodule.activity.LadderPriceSettingActivity;
import com.zhixingjia.goodsmanagemodule.activity.PriceSettingActivity;
import com.zhixingjia.goodsmanagemodule.activity.SelectStandardActivity;
import com.zhixingjia.goodsmanagemodule.bean.PriceSet;
import com.zhixingjia.goodsmanagemodule.bean.SepcPriceStockUnit;
import com.zhixingjia.goodsmanagemodule.callback.AddOrEditProductCB;
import com.zhixingjia.service.MainService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
* @description 发布商品
* @author chenbin
* @date 2018/11/21 11:23
* @version v3.2.0
*/
public class AddOrEditProductVM extends BaseVM {
    private AddOrEditProductCB cb;
    public CommodityInitialize commodityInitialize;
    public String categoryId;                                               //选择的类目id
    public int categoryIndex;                                               //选择的类目位置
    public Map<String,List<String>> selectedSepc = new LinkedHashMap<>();        //已选择的规格
    public PriceSet priceSet;                                               //价格库存
    public SepcPriceStockUnit sepcPriceStockUnit;                      //已选规格的价格库存

    public List<String> categoryNames = new ArrayList<>();
    public List<String> brandNames = new ArrayList<>();

    public ObservableField<String> categoryName = new ObservableField<>();  //类目名称
    public ObservableField<String> brandName = new ObservableField<>();     //品牌名称
    public ObservableField<String> sepcName = new ObservableField<>();      //选择的规格
    public ObservableField<String> skuName = new ObservableField<>();       //设置价格库存
    public ObservableField<String> picContent = new ObservableField<>();       //设置图文详情
    public ObservableField<String> title = new ObservableField<>();       //标题
    public ObservableField<String> count = new ObservableField<>("0/50");       //标题
    public ObservableField<String> code = new ObservableField<>();          //商品货号
    public ObservableBoolean isSpec = new ObservableBoolean();          //是否有规格

    public static final int SELECT_SEPC = 10355;
    public static final int SELECT_STOCK = 10356;
    public static final int SELECT_STOCK_SEPC = 10357;                      //有规格的价格库存
    public static final int PIC_CONTENT_DETAIL = 10358;                      //图文详情

    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public AddOrEditProductVM(AddOrEditProductCB callBack) {
        super(callBack);
        this.cb = callBack;
        initalize();
    }

    public void onSelectBrandClick(View view) {
        cb.showBrandWindow();
    }

    public void onSelectCategoryClick(View view) {
        cb.showCategoryWindow();
    }

    public void selectSpecificationClick(View view) {
        if (TextUtils.isEmpty(categoryId)) {
            baseCallBck.showToast("请选择类目");
            return;
        }
        Bundle bundle = new Bundle();
        List<CommodityInitialize.CateInfo.SepcInfo> sepcInfo = commodityInitialize.getCateinfo().get(categoryIndex).getSepc_info();
        bundle.putSerializable(IntentConfig.SEPC_INFO, (Serializable) sepcInfo);
        bundle.putSerializable(IntentConfig.SEPC_INFO_SELECTED, (Serializable) selectedSepc);
        cb.doIntentForResult(SelectStandardActivity.class, bundle, SELECT_SEPC);
    }

    public void productDescriptionClick(View view) {
        cb.doIntentForResult(AddProductDescribeActivity.class, null, PIC_CONTENT_DETAIL);
    }

    /**
     * 设置价格库存
     * @param view
     */
    public void selectSkuClick(View view) {
        if ((selectedSepc == null || selectedSepc.size() == 0)
                && (commodityInitialize.getCateinfo().get(categoryIndex).getSepc_info() != null && commodityInitialize.getCateinfo().get(categoryIndex).getSepc_info().size() > 0)) {
            cb.showToast("请选择规格");
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable(IntentConfig.STOCK_PRICE, priceSet);
        bundle.putSerializable(IntentConfig.UNIT, (Serializable) commodityInitialize.getUnit_info());
        if (commodityInitialize.getCateinfo().get(categoryIndex).getSepc_info() != null
                && commodityInitialize.getCateinfo().get(categoryIndex).getSepc_info().size() > 0) {
            bundle.putSerializable(IntentConfig.SEPC_INFO_SELECTED, (Serializable) selectedSepc);
            bundle.putSerializable(IntentConfig.SEPC_INFO, (Serializable) commodityInitialize.getCateinfo().get(categoryIndex).getSepc_info());
            cb.doIntentForResult(LadderPriceSettingActivity.class, bundle, SELECT_STOCK);
        } else {
            cb.doIntentForResult(PriceSettingActivity.class, bundle, SELECT_STOCK);
        }

    }

    /**
     * 初始化数据
     */
    public void initalize() {
        baseCallBck.showLoaddingDialog();
        HttpUtils.doHttp(subscription,
                RxRequest.create(MainService.class, HttpVersionConfig.API_COMMODITY_INITIALIZE).commodityInitialize(),
                new ServiceCallback<BaseData<CommodityInitialize>>() {
            @Override
            public void onError(Throwable error) {

            }

            @Override
            public void onSuccess(BaseData<CommodityInitialize> commodityInitializeBaseData) {
                commodityInitialize = commodityInitializeBaseData.getData();
                categoryNames.clear();
                brandNames.clear();
                for (CommodityInitialize.CateInfo cateInfo : commodityInitializeBaseData.getData().getCateinfo()) {
                    categoryNames.add(cateInfo.getCate_name());
                }
                for (HouseBean.BrandsBean brandsBean : commodityInitializeBaseData.getData().getBrand_info()) {
                    brandNames.add(brandsBean.getTitle());
                }
                cb.initData();
            }

            @Override
            public void onDefeated(BaseData<CommodityInitialize> commodityInitializeBaseData) {

            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode != Activity.RESULT_OK)
            return;
        if (requestCode == SELECT_SEPC) {
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                selectedSepc = (Map<String, List<String>>) bundle.getSerializable(IntentConfig.SEPC_INFO_SELECTED);
                if (selectedSepc != null && selectedSepc.size() > 0) {
                    sepcName.set("已设置");
                    skuName.set(null);//重新填写价格库存
                }
            }
        } else if (requestCode == SELECT_STOCK) {
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                priceSet = (PriceSet) bundle.getSerializable(IntentConfig.STOCK_PRICE);
                sepcPriceStockUnit = null;
                skuName.set("已设置");
            }
        } else if (requestCode == SELECT_STOCK_SEPC) {
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                sepcPriceStockUnit = (SepcPriceStockUnit) bundle.getSerializable(IntentConfig.SEPC_INFO_SELECTED);
                priceSet = null;
                skuName.set("已设置");
            }
        }
    }

    public void afterTextChanged(Editable s) {
        String str = s + "";
        if (str.length() > 50) {
            str = str.substring(0, 50);
            title.set(str);
            cb.showToast("输入的文字超过上限");
        }
        count.set(str.length() + "/50");
    }

    /**
     * 数据是否有效
     */
    private void isDataValidate() {
        if (TextUtils.isEmpty(categoryName.get())) {
            baseCallBck.showToast("请选择商品类目");
            return;
        }
        if (TextUtils.isEmpty(title.get())) {
            baseCallBck.showToast("请填写商品标题");
            return;
        }
        if (TextUtils.isEmpty(title.get())) {
            baseCallBck.showToast("请填写商品标题");
            return;
        }
        int imgsize = cb.getImgSize();
        if (imgsize == 0) {
            baseCallBck.showToast("请上传商品图片");
            return;
        }
        if (TextUtils.isEmpty(code.get())) {
            baseCallBck.showToast("请填写货号");
            return;
        }
        if (isSpec.get()) {
            if (TextUtils.isEmpty(sepcName.get())) {
                baseCallBck.showToast("请填写商品规格");
                return;
            }
        }
        if (TextUtils.isEmpty(skuName.get())) {
            baseCallBck.showToast("请填写价格库存");
            return;
        }

    }

    public void addToStockClick(View view) {

    }

    public void pullOnSaleClick(View view) {

    }
}
