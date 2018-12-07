package com.zhihangjia.mainmodule.callback;

import com.nmssdmf.commonlib.callback.BaseCB;
import com.zhixingjia.bean.mainmodule.MessageUnread;

/**
 * Create by chenbin on 2018/11/24
 * <p>
 * <p>
 */
public interface MineCustomerFragmentCB extends BaseCB {
    void bindVM();

    void endFresh();

    void initView();

    void phoneCall(String phoneNumber);

    void showNotice(MessageUnread messageUnread);
}
