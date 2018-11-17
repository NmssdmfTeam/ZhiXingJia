package com.zhihangjia.mainmodule.viewmodel;

import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.commonlib.callback.BaseRecyclerViewFragmentCB;
import com.nmssdmf.commonlib.viewmodel.BaseRecyclerViewFragmentVM;

import java.util.ArrayList;
import java.util.List;

/**
 * 评论详情viewmodel
 * Create by chenbin on 2018/11/17
 * <p>
 * <p>
 */
public class CommentDetailListVM extends BaseRecyclerViewFragmentVM {
    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public CommentDetailListVM(BaseRecyclerViewFragmentCB callBack) {
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
