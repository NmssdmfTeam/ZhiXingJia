package com.zhixingjia.goodsmanagemodule.viewmodel;

import android.databinding.ObservableField;
import android.view.View;

import com.nmssdmf.commonlib.bean.BaseData;
import com.nmssdmf.commonlib.config.HttpVersionConfig;
import com.nmssdmf.commonlib.httplib.HttpUtils;
import com.nmssdmf.commonlib.httplib.RxRequest;
import com.nmssdmf.commonlib.httplib.ServiceCallback;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhixingjia.bean.mainmodule.CommodityInitialize;
import com.zhixingjia.bean.mainmodule.HouseBean;
import com.zhixingjia.goodsmanagemodule.activity.AddProductDescribeActivity;
import com.zhixingjia.goodsmanagemodule.activity.SelectStandardActivity;
import com.zhixingjia.goodsmanagemodule.callback.AddOrEditProductCB;
import com.zhixingjia.service.MainService;

import java.util.ArrayList;
import java.util.List;

/**
* @description 发布商品
* @author chenbin
* @date 2018/11/21 11:23
* @version v3.2.0
*/
public class AddOrEditProductVM extends BaseVM {
    private AddOrEditProductCB cb;
    public CommodityInitialize commodityInitialize;
    public String categoryId;

    public List<String> categoryNames = new ArrayList<>();
    public List<String> brandNames = new ArrayList<>();

    public ObservableField<String> categoryName = new ObservableField<>();

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
        cb.doIntent(SelectStandardActivity.class, null);
    }

    public void productDescriptionClick(View view) {
        cb.doIntent(AddProductDescribeActivity.class, null);
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
}
