package com.zhihangjia.mainmodule.callback;

import com.nmssdmf.commonlib.callback.BaseCB;
import com.zhixingjia.bean.mainmodule.HouseBean;

import java.util.List;

public interface AllCategoriesCB extends BaseCB {

    void setData(List<HouseBean.CateBean> cateBeans);

}
