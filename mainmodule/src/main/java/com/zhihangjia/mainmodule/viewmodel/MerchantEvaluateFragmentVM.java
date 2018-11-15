package com.zhihangjia.mainmodule.viewmodel;

import com.nmssdmf.commonlib.callback.BaseRecyclerViewFragmentCB;
import com.nmssdmf.commonlib.viewmodel.BaseRecyclerViewFragmentVM;
import com.zhihangjia.mainmodule.callback.MerchantEvaluateFragmentCB;

public class MerchantEvaluateFragmentVM extends BaseRecyclerViewFragmentVM {
    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public MerchantEvaluateFragmentVM(MerchantEvaluateFragmentCB callBack) {
        super(callBack);
    }

    @Override
    public void initData(boolean isRefresh) {

    }
}
