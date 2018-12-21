package com.zhihangjia.mainmodule.callback;

import com.nmssdmf.commonlib.callback.BaseCB;

/**
* @description 生活服务详情callback
* @author chenbin
* @date 2018/12/19 14:40
* @version v3.2.0
*/
public interface LifeServiceDetailCB extends BaseCB {

    void setContent();
    void phoneCall(String phoneNumber);
    void showChooseMapsWindows();
}
