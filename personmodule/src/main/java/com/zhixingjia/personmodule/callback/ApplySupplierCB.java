package com.zhixingjia.personmodule.callback;

import com.nmssdmf.commonlib.callback.BaseCB;

import java.util.List;

/**
 * 申请店铺callback
 * Create by chenbin on 2018/12/1
 * <p>
 * <p>
 */
public interface ApplySupplierCB extends BaseCB {
    void showBusinessCirclePopWindow();
    void setPickViewData(List<String> tradeArea);
    void loadFrontIDCardImg(String filepath);
    void loadBackIDCardImg(String filepath);
    void loadLicenseImg(String filepath);
    void showSelectImageDialog(String filepath);
}
