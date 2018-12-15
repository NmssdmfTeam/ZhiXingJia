package com.zhixingjia.goodsmanagemodule.viewmodel;

import android.databinding.ObservableField;
import android.os.Bundle;
import android.view.View;

import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhixingjia.bean.mainmodule.CommodityInitialize;
import com.zhixingjia.goodsmanagemodule.callback.LadderPriceSettingCB;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
* @description 设置阶梯价价格库存
* @author chenbin
* @date 2018/11/21 14:51
* @version v3.2.0
*/
public class LadderPriceSettingVM extends BaseVM {
    public Map<String, List<String>> stock = new LinkedHashMap<>();
    public List<CommodityInitialize.CateInfo.SepcInfo> sepcInfos = new ArrayList<>();
    public List<String> stockName = new ArrayList<>();
    public List<String> units = new ArrayList<>();

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
        }
        List<String> selectedStandardValues = new ArrayList<>();
        for (CommodityInitialize.CateInfo.SepcInfo sepcInfo : sepcInfos) {
            if (selectedStandardValues.size() != 0) {
                List<String> selectedValues = stock.get(sepcInfo.getNorms_id());
                List<String> result = new ArrayList();
                for (String value : selectedStandardValues) {
                    for (String selectedValue : selectedValues) {
                        StringBuffer stringBuffer = new StringBuffer(value);
                        stringBuffer.append("+");
                        stringBuffer.append(selectedValue);
                        result.add(stringBuffer.toString());
                    }
                }
                selectedStandardValues = result;
            } else {
                selectedStandardValues.addAll(stock.get(sepcInfo.getNorms_id()));
            }
        }
        stockName = selectedStandardValues;
    }

    public void onSelectUnitClick(View view) {
        callback.showUnitWheelPickWindow();
    }
}
