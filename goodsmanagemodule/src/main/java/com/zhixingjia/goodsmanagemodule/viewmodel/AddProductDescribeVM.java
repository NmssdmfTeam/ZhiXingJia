package com.zhixingjia.goodsmanagemodule.viewmodel;

import android.os.Bundle;
import android.view.View;

import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhixingjia.goodsmanagemodule.bean.ProductDescribe;
import com.zhixingjia.goodsmanagemodule.callback.AddProductDescribeCB;

import java.util.ArrayList;
import java.util.List;

/**
* @description 图文详情
* @author chenbin
* @date 2018/11/21 13:39
* @version v3.2.0
*/
public class AddProductDescribeVM extends BaseVM {
    private AddProductDescribeCB cb;
    public List<ProductDescribe> productDescribes = new ArrayList<>();

    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public AddProductDescribeVM(AddProductDescribeCB callBack) {
        super(callBack);
        this.cb = callBack;
        initData();
    }

    private void initData() {
        Bundle bundle = baseCallBck.getIntentData();
        if (bundle != null) {
            productDescribes = (List<ProductDescribe>) bundle.getSerializable(IntentConfig.PRODUCT_DESCRIBES);
        }
    }

    /**
     * 添加内容
     * @param view
     */
    public void onAddContentClick(View view) {
        cb.addContent();
    }
}
