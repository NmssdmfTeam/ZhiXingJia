package com.zhixingjia.goodsmanagemodule.viewmodel;

import android.view.View;

import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhixingjia.goodsmanagemodule.callback.AddProductDescribeCB;

/**
* @description 图文详情
* @author chenbin
* @date 2018/11/21 13:39
* @version v3.2.0
*/
public class AddProductDescribeVM extends BaseVM {
    private AddProductDescribeCB cb;

    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public AddProductDescribeVM(AddProductDescribeCB callBack) {
        super(callBack);
        this.cb = callBack;
    }

    /**
     * 添加内容
     * @param view
     */
    public void onAddContentClick(View view) {
        cb.addContent();
    }
}
