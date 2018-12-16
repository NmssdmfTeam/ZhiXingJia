package com.zhixingjia.goodsmanagemodule.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.zhixingjia.goodsmanagemodule.BR;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Create by chenbin on 2018/12/15
 * <p>
 * <p>
 */
public class SepcStockPrice extends BaseObservable {
    private List<SepcPriceStockSet.SpecInfo> normsIds = new ArrayList<>();      //key值对应的ID
    private String name;
    private String value;

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
        notifyPropertyChanged(BR.value);
    }

    @Bindable
    public List<SepcPriceStockSet.SpecInfo> getNormsIds() {
        return normsIds;
    }

    public void setNormsIds(List<SepcPriceStockSet.SpecInfo> normsIds) {
        this.normsIds = normsIds;
        notifyPropertyChanged(BR.normsIds);
    }
}
