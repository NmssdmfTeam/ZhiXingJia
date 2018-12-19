package com.zhihangjia.mainmodule.callback;

import com.nmssdmf.commonlib.callback.BaseCB;
import com.zhixingjia.bean.mainmodule.HouseBean;

import java.util.List;

/**
* @description 生活服务分类
* @author chenbin
* @date 2018/12/19 9:46
* @version v3.2.0
*/
public interface AllLifeCategoriesCB extends BaseCB {

    void setData(List<HouseBean.CateBean> cateBeans);

}
