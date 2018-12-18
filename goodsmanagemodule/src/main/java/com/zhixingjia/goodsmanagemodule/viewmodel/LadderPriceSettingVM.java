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
    public List<String> units = new ArrayList<>();
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
        //比对sepcPriceStockUnit已选择的，若规格一致，比对是否存在sku_product_id
        if (sepcPriceStockUnit != null) {
            for (List<SepcPriceStockSet.SpecInfo> specInfoList : selectedStandardValues) {
                boolean isExist = false;
                for (SepcPriceStockSet sepcPriceStockSet : sepcPriceStockUnit.getSepcPriceStockSets()) {
                    List<SepcPriceStockSet.SpecInfo> selectedSpecInfos = sepcPriceStockSet.getSpec_info();
                    if (isSpecListEqual(selectedSpecInfos, specInfoList)) {//若是不存在的规格，则添加
                        isExist = true;
                    }
                }
                if (!isExist) {
                    SepcPriceStockSet sepcPriceStockSet = new SepcPriceStockSet();
                    sepcPriceStockSet.setSku_product_id("0");
                    sepcPriceStockSet.setSpec_info(specInfoList);
                    sepcPriceStockUnit.getSepcPriceStockSets().add(sepcPriceStockSet);
                }
            }
        } else {
            sepcPriceStockUnit = new SepcPriceStockUnit();
            sepcPriceStockUnit.setSepcPriceStockSets(new ArrayList<SepcPriceStockSet>());
            for (List<SepcPriceStockSet.SpecInfo> specInfoList : selectedStandardValues) {
                SepcPriceStockSet sepcPriceStockSet = new SepcPriceStockSet();
                sepcPriceStockSet.setSku_product_id("0");
                sepcPriceStockSet.setSpec_info(specInfoList);
                sepcPriceStockUnit.getSepcPriceStockSets().add(sepcPriceStockSet);
            }
        }
    }

    private boolean isSpecListEqual(List<SepcPriceStockSet.SpecInfo> specInfosPrev, List<SepcPriceStockSet.SpecInfo> specInfosAfter) {
        if (specInfosPrev.size() != specInfosAfter.size())
            return false;
        for (SepcPriceStockSet.SpecInfo prevSpecInfo : specInfosPrev) {
            boolean isInclude = false;
            for (SepcPriceStockSet.SpecInfo afterSpecInfo : specInfosAfter) {
                if (prevSpecInfo.getNorms_id().equals(afterSpecInfo.getNorms_id()) && prevSpecInfo.getValue().equals(afterSpecInfo.getValue())) { //规格相同
                    isInclude = true;
                }
            }
            if (!isInclude) {
                return false;
            }
        }
        return true;
    }

    public void onSelectUnitClick(View view) {
        callback.showUnitWheelPickWindow();
    }

    public void onConfirmClick(View view) {
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
