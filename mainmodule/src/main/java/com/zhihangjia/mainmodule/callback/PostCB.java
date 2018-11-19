package com.zhihangjia.mainmodule.callback;

import com.nmssdmf.commonlib.callback.BaseCB;

/**
* @description 发帖callback
* @author chenbin
* @date 2018/11/19 19:17
* @version v3.2.0
*/
public interface PostCB extends BaseCB {

    void addContent();

    void showChooseWindow();

}
