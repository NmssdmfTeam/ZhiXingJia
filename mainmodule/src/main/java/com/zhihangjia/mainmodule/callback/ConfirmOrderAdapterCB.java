package com.zhihangjia.mainmodule.callback;

/**
 * Created by ${nmssdmf} on 2018/11/19 0019.
 */

public interface ConfirmOrderAdapterCB {
    void chooseDeliveryMethod(int position);

    /**
     *
     * @param position
     * @param id 商家id
     * @param money 单商家金额
     */
    void chooseCoupon(int position, String id, String money);

}
