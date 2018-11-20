package com.zhixingjia.personmodule.viewmodule;


import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.commonlib.viewmodel.BaseTitleRecyclerViewVM;
import com.zhixingjia.personmodule.callback.MyCouponsCB;

/**
 * Created by ${nmssdmf} on 2018/11/20 0020.
 */

public class MyCouponsVM extends BaseTitleRecyclerViewVM {
    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public MyCouponsVM(MyCouponsCB callBack) {
        super(callBack);
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
        list.add(new Base());
        list.add(new Base());
        list.add(new Base());
    }
}
