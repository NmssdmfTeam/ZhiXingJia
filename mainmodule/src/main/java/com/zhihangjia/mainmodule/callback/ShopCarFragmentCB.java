package com.zhihangjia.mainmodule.callback;

import com.nmssdmf.commonlib.callback.BaseCB;
import com.zhixingjia.bean.mainmodule.ShopCar;

import java.util.List;

/**
 * Created by ${nmssdmf} on 2018/11/19 0019.
 */

public interface ShopCarFragmentCB extends BaseCB {
    void refreshData(List<ShopCar> list, boolean refresh);
}
