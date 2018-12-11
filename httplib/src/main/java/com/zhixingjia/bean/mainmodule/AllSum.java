package com.zhixingjia.bean.mainmodule;

import android.databinding.BaseObservable;

/**
* @description 购物车数量统计
* @author chenbin
* @date 2018/12/11 9:57
* @version v3.2.0
*/
public class AllSum extends BaseObservable {
    private String allsum;

    public String getAllsum() {
        return allsum;
    }

    public void setAllsum(String allsum) {
        this.allsum = allsum;
    }
}
