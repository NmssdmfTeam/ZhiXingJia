package com.zhihangjia.mainmodule.callback;

import com.nmssdmf.commonlib.callback.BaseCB;
import com.zhixingjia.bean.mainmodule.MessageUnread;

/**
 * Create by chenbin on 2018/11/24
 * <p>
 * <p>
 */
public interface MineProviderFragmentCB extends BaseCB {
    void bindVM();

    void endFresh();

    void initView();

    void showNotice(MessageUnread messageUnread);

    boolean checkPermission();
}
