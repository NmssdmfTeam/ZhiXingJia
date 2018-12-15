package com.zhixingjia.goodsmanagemodule.viewmodel;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhixingjia.bean.mainmodule.CommodityInitialize;
import com.zhixingjia.goodsmanagemodule.callback.SelectStandardCB;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
* @description 选择规格
* @author chenbin
* @date 2018/11/21 15:52
* @version v3.2.0
*/
public class SelectStandardVM extends BaseVM {
    public List<CommodityInitialize.CateInfo.SepcInfo> sepcInfos = new ArrayList<>();
    public Map<String,List<String>> selectedSpecInfos = new LinkedHashMap<>();
    private SelectStandardCB callback;

    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public SelectStandardVM(SelectStandardCB callBack) {
        super(callBack);
        initData();
        this.callback = callBack;
    }

    private void initData() {
        Bundle bundle = baseCallBck.getIntentData();
        if (bundle != null) {
            sepcInfos = (List<CommodityInitialize.CateInfo.SepcInfo>) bundle.getSerializable(IntentConfig.SEPC_INFO);
            selectedSpecInfos = (Map<String, List<String>>) bundle.getSerializable(IntentConfig.SEPC_INFO_SELECTED);
        }
    }

    public void onConfirmClick(View view) {
        if (selectedSpecInfos.size() < sepcInfos.size()) {
            for (CommodityInitialize.CateInfo.SepcInfo sepcInfo : sepcInfos) {
                if (selectedSpecInfos.get(sepcInfo.getNorms_id()) == null
                        || selectedSpecInfos.get(sepcInfo.getNorms_id()).size() == 0) {
                    callback.showToast("请选择:"+sepcInfo.getType_name());
                    return;
                }
            }
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable(IntentConfig.SEPC_INFO_SELECTED, (Serializable) selectedSpecInfos);
        callback.setResultCode(Activity.RESULT_OK,bundle);
        callback.finishActivity();
    }
}
