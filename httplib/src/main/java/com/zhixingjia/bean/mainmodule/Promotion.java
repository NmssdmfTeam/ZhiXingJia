package com.zhixingjia.bean.mainmodule;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.zhixingjia.httplib.BR;

public class Promotion extends BaseObservable {
    private String info_img;    //图片
    private String share_url;   //点击图片跳转链接地址 - H5页面

    @Bindable
    public String getInfo_img() {
        return info_img;
    }

    public void setInfo_img(String info_img) {
        this.info_img = info_img;
        notifyPropertyChanged(BR.info_img);
    }

    @Bindable
    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
        notifyPropertyChanged(BR.share_url);
    }
}
