package com.zhixingjia.personmodule.callback;

import com.nmssdmf.commonlib.callback.BaseCB;
import com.zhixingjia.bean.personmodule.Address;

import java.util.List;

/**
 * Create by chenbin on 2018/11/24
 * <p>
 * <p>
 */
public interface ManageAddressCB extends BaseCB {

    void endFresh();

    void setData(List<Address> addresses, boolean isRefresh);

    void setAddress(Address address, int position);

    void deleteAddress(int position);
}
