package com.zhihangjia.mainmodule.bean;

import com.zhixingjia.bean.mainmodule.BbsCategory;

import java.util.List;

/**
* @description 百姓信息类型组合类
* @author chenbin
* @date 2018/11/22 13:56
* @version v3.2.0
*/
public class MessageCategory {
    private List<BbsCategory> categories;

    public List<BbsCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<BbsCategory> categories) {
        this.categories = categories;
    }
}
