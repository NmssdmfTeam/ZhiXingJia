package com.zhihangjia.mainmodule.callback;

import com.nmssdmf.commonlib.callback.BaseCB;
import com.zhixingjia.bean.mainmodule.BbsCategory;

import java.util.List;

/**
* @description 首页消息
* @author chenbin
* @date 2018/11/22 11:46
* @version v3.2.0
*/
public interface IndexMessageCB extends BaseCB {

    void setMessageCategory(List<BbsCategory> bbsCategories);
}
