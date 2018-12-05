package com.zhixingjia.bean.mainmodule;

import android.databinding.BaseObservable;
/**
* @description 关于我们、注册协议、隐私政策
* @author chenbin
* @date 2018/12/5 10:09
* @version v3.2.0
*/
public class Link extends BaseObservable {
    private String link_url;

    public String getLink_url() {
        return link_url;
    }

    public void setLink_url(String link_url) {
        this.link_url = link_url;
    }
}
