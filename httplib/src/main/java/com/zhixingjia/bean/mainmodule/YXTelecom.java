package com.zhixingjia.bean.mainmodule;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.zhixingjia.httplib.BR;

/**
 * 中国电信
 * Create by chenbin on 2018/12/2
 * <p>
 * <p>
 */
public class YXTelecom extends BaseObservable {
    private String id;          //ID号，分页需要传的值
    private String img_url;     //图片
    private String link_url;    //链接地址，直接用此字段点击跳转到H5页面

    @Bindable
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
        notifyPropertyChanged(BR.id);
    }

    @Bindable
    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
        notifyPropertyChanged(BR.img_url);
    }

    @Bindable
    public String getLink_url() {
        return link_url;
    }

    public void setLink_url(String link_url) {
        this.link_url = link_url;
        notifyPropertyChanged(BR.link_url);
    }
}
