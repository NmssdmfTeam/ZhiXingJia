package com.zhixingjia.personmodule.callback;

import com.nmssdmf.commonlib.callback.BaseCB;

import java.util.List;

/**
 * 添加或编辑地址callback
 * Create by chenbin on 2018/11/24
 * <p>
 * <p>
 */
public interface AddOrEditAddressCB extends BaseCB {

    void setArea(List<String> areas);

    void chnageTitle(String title);
}
