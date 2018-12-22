package com.zhihangjia.mainmodule.callback;

import com.nmssdmf.commonlib.callback.BaseCB;
import com.zhixingjia.bean.mainmodule.Banner;
import com.zhixingjia.bean.mainmodule.HouseBean;
import com.zhixingjia.bean.mainmodule.LifeService;

import java.util.List;

/**
 * Created by ${nmssdmf} on 2018/11/23 0023.
 */

public interface LifeServiceCB extends BaseCB {
    String getPage();
    void setData(List<LifeService> lifeServices,boolean isRefresh);
    void setBanner(Banner banner);
    void setCategoryData(List<HouseBean.CateBean> cateBeans);
    void setLifeServiceTitile(String title);
}
