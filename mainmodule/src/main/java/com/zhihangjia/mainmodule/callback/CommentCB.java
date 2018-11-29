package com.zhihangjia.mainmodule.callback;

import com.nmssdmf.commonlib.callback.BaseCB;

/**
* @description 发表评论callback
* @author chenbin
* @date 2018/11/29 11:15
* @version v3.2.0
*/
public interface CommentCB extends BaseCB {
    void getOrderDetailSuccess();

    int uploadImg();

    void setImgIds();
}
