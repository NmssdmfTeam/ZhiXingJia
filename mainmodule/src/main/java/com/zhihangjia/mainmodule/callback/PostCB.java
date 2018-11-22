package com.zhihangjia.mainmodule.callback;

import com.nmssdmf.commonlib.callback.BaseCB;
import com.zhixingjia.bean.mainmodule.BbsCategory;

import java.util.List;

/**
* @description 发帖callback
* @author chenbin
* @date 2018/11/19 19:17
* @version v3.2.0
*/
public interface PostCB extends BaseCB {

    void addContent();

    void showChooseWindow();

    void setCat(List<BbsCategory> cats);          //设置类别
}
