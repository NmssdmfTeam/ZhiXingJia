package com.zhihangjia.mainmodule.viewmodel;

import android.os.Bundle;

import com.nmssdmf.commonlib.callback.BaseCB;
import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.viewmodel.BaseVM;

/**
* @description 我的发帖 activity viewmodel
* @author chenbin
* @date 2018/11/20 17:54
* @version v3.2.0
*/
public class MyPostVM extends BaseVM {
    public int index;

    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public MyPostVM(BaseCB callBack) {
        super(callBack);
        initData();
    }

    private void initData() {
        Bundle bundle = baseCallBck.getIntentData();
        if (bundle != null) {
            index = bundle.getInt(IntentConfig.POSITION);
        }
    }
}
