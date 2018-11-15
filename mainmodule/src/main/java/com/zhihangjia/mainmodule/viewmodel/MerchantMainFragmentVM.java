package com.zhihangjia.mainmodule.viewmodel;

import com.nmssdmf.commonlib.callback.BaseRecyclerViewFragmentCB;
import com.nmssdmf.commonlib.viewmodel.BaseRecyclerViewFragmentVM;
import com.zhihangjia.mainmodule.callback.MerchantMainFragmentCB;

public class MerchantMainFragmentVM extends BaseRecyclerViewFragmentVM {
    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public MerchantMainFragmentVM(MerchantMainFragmentCB callBack) {
        super(callBack);
    }

    @Override
    public void initData(boolean isRefresh) {

    }
}
