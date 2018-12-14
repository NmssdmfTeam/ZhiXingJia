package com.zhixingjia.bean.mainmodule;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.zhixingjia.httplib.BR;

/**
* @description 商品数量统计
* @author chenbin
* @date 2018/12/14 15:01
* @version v3.2.0
*/
public class GoodManageNumber extends BaseObservable {
    private String sale_sum;            //出售中数量，也就是上架数量

    private String depot_sum;           //仓库中数量，也就是下架数量

    @Bindable
    public String getSale_sum() {
        return sale_sum;
    }

    public void setSale_sum(String sale_sum) {
        this.sale_sum = sale_sum;
        notifyPropertyChanged(BR.sale_sum);
    }

    @Bindable
    public String getDepot_sum() {
        return depot_sum;
    }

    public void setDepot_sum(String depot_sum) {
        this.depot_sum = depot_sum;
        notifyPropertyChanged(BR.depot_sum);
    }
}
