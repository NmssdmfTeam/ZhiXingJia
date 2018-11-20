package com.zhihangjia.mainmodule.viewmodel;


import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.commonlib.callback.BaseRecyclerViewFragmentCB;
import com.nmssdmf.commonlib.viewmodel.BaseRecyclerViewFragmentVM;

import java.util.ArrayList;
import java.util.List;

/**
* @description 我的发帖viewmodel
* @author chenbin
* @date 2018/11/20 17:43
* @version v3.2.0
*/
public class MyPostFragmentVM extends BaseRecyclerViewFragmentVM {
    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public MyPostFragmentVM(BaseRecyclerViewFragmentCB callBack) {
        super(callBack);
    }

    @Override
    public void initData(boolean isRefresh) {
        List<Base> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new Base());
        }
        baseCB.refreshAdapter(isRefresh,list);
    }
}
