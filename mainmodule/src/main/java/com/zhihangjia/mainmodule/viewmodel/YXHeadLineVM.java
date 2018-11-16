package com.zhihangjia.mainmodule.viewmodel;

import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.commonlib.callback.BaseCB;
import com.nmssdmf.commonlib.callback.BaseTitleRecyclerViewCB;
import com.nmssdmf.commonlib.viewmodel.BaseTitleRecyclerViewVM;
import com.nmssdmf.commonlib.viewmodel.BaseVM;

import java.util.ArrayList;
import java.util.List;

/**
* @description 宜兴头条viewmodel
* @author chenbin
* @date 2018/11/16 15:29
* @version v3.2.0
*/
public class YXHeadLineVM extends BaseTitleRecyclerViewVM {

    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public YXHeadLineVM(BaseTitleRecyclerViewCB callBack) {
        super(callBack);
    }

    @Override
    public void initData(boolean isRefresh) {
        List<Base> list = new ArrayList<>();
        for (int i=0; i < 10; i++) {
            list.add(new Base());
        }
        baseTitleRecyclerViewCB.refreshAdapter(isRefresh,list);
    }
}
