package com.zhihangjia.mainmodule.callback;

import com.nmssdmf.commonlib.callback.BaseRecyclerViewFragmentCB;
import com.zhixingjia.bean.mainmodule.IndexBean;

import java.util.List;

/**
* @description  信息中心分类新闻callback
* @author chenbin
* @date 2018/11/22 10:38
* @version v3.2.0
*/
public interface MessageCenterModuleCB extends BaseRecyclerViewFragmentCB {

    void stopFreshAction();

    void setData(List<IndexBean.ForumBean> data, boolean isRefresh);
}
