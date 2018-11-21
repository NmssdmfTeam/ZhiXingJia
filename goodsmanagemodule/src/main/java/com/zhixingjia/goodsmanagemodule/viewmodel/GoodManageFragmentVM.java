package com.zhixingjia.goodsmanagemodule.viewmodel;

import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.commonlib.viewmodel.BaseRecyclerViewFragmentVM;
import com.zhixingjia.goodsmanagemodule.callback.GoodManageFragmentCB;

/**
 * Created by ${nmssdmf} on 2018/11/21 0021.
 */

public class GoodManageFragmentVM extends BaseRecyclerViewFragmentVM {
    private GoodManageFragmentCB cb;

    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public GoodManageFragmentVM(GoodManageFragmentCB callBack) {
        super(callBack);
        cb = callBack;
    }

    @Override
    public void initData(boolean isRefresh) {
        list.add(new Base());
        list.add(new Base());
        list.add(new Base());
        list.add(new Base());
        list.add(new Base());
        list.add(new Base());
        list.add(new Base());
        list.add(new Base());
        list.add(new Base());
    }
}
