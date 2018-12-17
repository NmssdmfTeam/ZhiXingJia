package com.zhixingjia.goodsmanagemodule.bean;

import android.databinding.BaseObservable;

import com.zhixingjia.bean.goodsmanagemodel.SepcPriceStockSet;

import java.io.Serializable;
import java.util.List;

/**
 * Create by chenbin on 2018/12/16
 * <p>
 * <p>
 */
public class SepcPriceStockUnit extends BaseObservable implements Serializable {
    private String unit;                                    //单位
    private List<SepcPriceStockSet> sepcPriceStockSets;     //有规格价格库存

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public List<SepcPriceStockSet> getSepcPriceStockSets() {
        return sepcPriceStockSets;
    }

    public void setSepcPriceStockSets(List<SepcPriceStockSet> sepcPriceStockSets) {
        this.sepcPriceStockSets = sepcPriceStockSets;
    }
}
