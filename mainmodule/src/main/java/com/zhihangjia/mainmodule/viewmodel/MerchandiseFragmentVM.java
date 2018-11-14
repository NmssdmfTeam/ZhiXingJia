package com.zhihangjia.mainmodule.viewmodel;

import com.nmssdmf.commonlib.callback.BaseRecyclerViewFragmentCB;
import com.nmssdmf.commonlib.viewmodel.BaseRecyclerViewFragmentVM;
import com.zhihangjia.mainmodule.callback.MerchandiseFragmentCB;

public class MerchandiseFragmentVM extends BaseRecyclerViewFragmentVM {

    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public MerchandiseFragmentVM(MerchandiseFragmentCB callBack) {
        super(callBack);
    }

    @Override
    public void initData(boolean isRefresh) {

    }
}
