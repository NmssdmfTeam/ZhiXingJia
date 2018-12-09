package com.zhixingjia.personmodule.callback;

import com.nmssdmf.commonlib.callback.BaseCB;

/**
 * Created by ${nmssdmf} on 2018/11/20 0020.
 */

public interface PersonInfoCB extends BaseCB {
    void showModifyAvatarWindow();
    void dismissModifyAvatarWindow();
}
