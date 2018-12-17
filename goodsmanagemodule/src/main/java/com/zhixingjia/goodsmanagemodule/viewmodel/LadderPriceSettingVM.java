package com.zhixingjia.goodsmanagemodule.viewmodel;

import android.app.Activity;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhixingjia.bean.mainmodule.CommodityInitialize;
import com.zhixingjia.bean.goodsmanagemodel.SepcPriceStockSet;
import com.zhixingjia.goodsmanagemodule.bean.SepcPriceStockUnit;
import com.zhixingjia.goodsmanagemodule.bean.SepcStockPrice;
import com.zhixingjia.goodsmanagemodule.callback.LadderPriceSettingCB;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chenbin
 * @version v3.2.0
 * @description 设置阶梯价价格库存
 * @date 2018/11/21 14:51
 */
public class LadderPriceSettingVM extends BaseVM {
    public Map<String, List<String>> stock = new LinkedHashMap<>();
    public List<CommodityInitialize.CateInfo.SepcInfo> sepcInfos = new ArrayList<>();
    public List<List<SepcPriceStockSet.SpecInfo>> specInfos = new ArrayList<>();
    public List<String> units = new ArrayList<>();
    public List<SepcStockPrice> priceSet = new ArrayList<>();
    public List<SepcStockPrice> stockSet = new ArrayList<>();
    public SepcPriceStockUnit sepcPriceStockUnit;

    public final ObservableField<String> unit = new ObservableField<>();

    private LadderPriceSettingCB callback;

    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public LadderPriceSettingVM(LadderPriceSettingCB callBack) {
        super(callBack);
        this.callback = callBack;
        initData();
    }

    private void initData() {
        Bundle bundle = baseCallBck.getIntentData();
        if (bundle != null) {
            stock = (Map<String, List<String>>) bundle.getSerializable(IntentConfig.SEPC_INFO_SELECTED);
            sepcInfos = (List<CommodityInitialize.CateInfo.SepcInfo>) bundle.getSerializable(IntentConfig.SEPC_INFO);
            units = (List<String>) bundle.getSerializable(IntentConfig.UNIT);
            sepcPriceStockUnit = (SepcPriceStockUnit) bundle.getSerializable(IntentConfig.STOCK_PRICE_SPEC);
            if (sepcPriceStockUnit != null)
                unit.set(sepcPriceStockUnit.getUnit());
        }
        //将数据组合成便于提交的数据
        /** 'spec_info': [{//规格数据
         *    'norms_id': '1',//key值对应的ID
         *    'key': '颜色',
         *    'value': '黑色'
         * }, {
         *    'norms_id': '2',
         *    'key': '尺寸',
         *    'value': '小'
         * }]
         **/
        if (sepcPriceStockUnit == null) {
            List<List<SepcPriceStockSet.SpecInfo>> selectedStandardValues = new ArrayList<>();
            for (CommodityInitialize.CateInfo.SepcInfo sepcInfo : sepcInfos) {
                String norms_id = sepcInfo.getNorms_id();
                String key = sepcInfo.getType_name();
                if (selectedStandardValues.size() != 0) {
                    List<String> selectedValues = stock.get(sepcInfo.getNorms_id());
                    List<List<SepcPriceStockSet.SpecInfo>> result = new ArrayList();
                    for (List<SepcPriceStockSet.SpecInfo> value : selectedStandardValues) {
                        for (String selectedValue : selectedValues) {
                            List<SepcPriceStockSet.SpecInfo> temp = new ArrayList<>();
                            temp.addAll(value);
                            SepcPriceStockSet.SpecInfo specInfoAdded = new SepcPriceStockSet.SpecInfo();
                            specInfoAdded.setKey(key);
                            specInfoAdded.setNorms_id(norms_id);
                            specInfoAdded.setValue(selectedValue);
                            temp.add(specInfoAdded);
                            result.add(temp);
                        }
                    }
                    selectedStandardValues = result;
                } else {
                    for (String value : stock.get(sepcInfo.getNorms_id())) {
                        List<SepcPriceStockSet.SpecInfo> specInfos = new ArrayList<>();
                        SepcPriceStockSet.SpecInfo specInfoAdded = new SepcPriceStockSet.SpecInfo();
                        specInfoAdded.setKey(key);
                        specInfoAdded.setNorms_id(norms_id);
                        specInfoAdded.setValue(value);
                        specInfos.add(specInfoAdded);
                        selectedStandardValues.add(specInfos);
                    }
                }
            }
            specInfos = selectedStandardValues;
        } else {
            for (SepcPriceStockSet sepcPriceStockSet : sepcPriceStockUnit.getSepcPriceStockSets()) {
                List<SepcPriceStockSet.SpecInfo> spec = new ArrayList<>();
                spec.addAll(sepcPriceStockSet.getSpec_info());
                specInfos.add(spec);
            }
        }
    }

    public void onSelectUnitClick(View view) {
        callback.showUnitWheelPickWindow();
    }

    public void onConfirmClick(View view) {
        SepcPriceStockUnit sepcPriceStockUnit = new SepcPriceStockUnit();
        List<SepcPriceStockSet> result = new ArrayList<>();
        for (int i = 0; i < priceSet.size(); i++) {
            SepcPriceStockSet sepcPriceStockSet = new SepcPriceStockSet();
            sepcPriceStockSet.setSku_product_id("0");
            if (TextUtils.isEmpty(priceSet.get(i).getValue())) {
                callback.showToast("请输入"+priceSet.get(i).getName()+"的价格");
                return;
            }
            if (TextUtils.isEmpty(stockSet.get(i).getValue())) {
                callback.showToast("请输入"+stockSet.get(i).getName()+"的库存");
                return;
            }
            if (this.sepcPriceStockUnit != null)
                sepcPriceStockSet.setSku_product_id(this.sepcPriceStockUnit.getSepcPriceStockSets().get(i).getSku_product_id());
            sepcPriceStockSet.setPrice(priceSet.get(i).getValue());
            sepcPriceStockSet.setStock(stockSet.get(i).getValue());
            sepcPriceStockSet.setSpec_info(specInfos.get(i));
            result.add(sepcPriceStockSet);
        }
        sepcPriceStockUnit.setSepcPriceStockSets(result);
        if (TextUtils.isEmpty(unit.get())) {
            callback.showToast("请输入单位");
            return;
        }
        sepcPriceStockUnit.setUnit(unit.get());
        Bundle bundle = new Bundle();
        bundle.putSerializable(IntentConfig.STOCK_PRICE_SPEC, sepcPriceStockUnit);
        callback.setResultCode(Activity.RESULT_OK, bundle);
        callback.finishActivity();
    }
}
