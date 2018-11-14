package com.zhihangjia.mainmodule.bean;

import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.customerviewlib.entity.MultiItemEntity;

/**
 * 首页列表数据bean
 * Create by chenbin on 2018/11/13
 * <p>
 * <p>
 */
public class MainBean extends Base implements MultiItemEntity {
    private int itemType;

    @Override
    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }
}
