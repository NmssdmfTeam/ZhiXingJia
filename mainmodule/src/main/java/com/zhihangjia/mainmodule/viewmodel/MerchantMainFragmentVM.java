package com.zhihangjia.mainmodule.viewmodel;

import com.nmssdmf.commonlib.viewmodel.BaseRecyclerViewFragmentVM;
import com.zhihangjia.mainmodule.callback.MerchantMainFragmentCB;
import com.zhixingjia.bean.mainmodule.Banner;
import com.zhixingjia.bean.mainmodule.Commodity;
import com.zhixingjia.bean.mainmodule.ShopInfo;

import java.util.List;

public class MerchantMainFragmentVM extends BaseRecyclerViewFragmentVM {
    public List<Banner.CommomBanner> bannersBeans;
    public List<Commodity> commodityInfoBeans;

    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public MerchantMainFragmentVM(MerchantMainFragmentCB callBack) {
        super(callBack);
    }

    @Override
    public void initData(boolean isRefresh) {

    }
}
