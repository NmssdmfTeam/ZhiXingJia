package com.zhihangjia.mainmodule.viewmodel;

import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.commonlib.callback.BaseRecyclerViewFragmentCB;
import com.nmssdmf.commonlib.viewmodel.BaseRecyclerViewFragmentVM;

import java.util.ArrayList;
import java.util.List;

/**
* @description 要闻动态viewmodel
* @author chenbin
* @date 2018/11/16 16:53
* @version v3.2.0
*/
public class FocusNewsVM extends BaseRecyclerViewFragmentVM {
    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public FocusNewsVM(BaseRecyclerViewFragmentCB callBack) {
        super(callBack);
    }

    @Override
    public void initData(boolean isRefresh) {
        List<Base> list = new ArrayList<>();
        for (int i=0; i < 10; i++) {
            list.add(new Base());
        }
        baseCB.refreshAdapter(isRefresh,list);
    }
}
