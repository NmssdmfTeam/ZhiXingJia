package com.zhihangjia.mainmodule.viewmodel;

import android.os.Bundle;

import com.nmssdmf.commonlib.callback.BaseCB;
import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.viewmodel.BaseVM;

/**
 * Create by chenbin on 2018/11/19
 * <p>
 * <p>
 */
public class MessageCenterModuleVM extends BaseVM {
    public String cat_id;
    public String name;

    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public MessageCenterModuleVM(BaseCB callBack) {
        super(callBack);
        initData();
    }

    private void initData() {
        Bundle bundle = baseCallBck.getIntentData();
        if (bundle != null) {
            cat_id = bundle.getString(IntentConfig.CAT_ID);
            name = bundle.getString(IntentConfig.NAME);
        }
    }
}
