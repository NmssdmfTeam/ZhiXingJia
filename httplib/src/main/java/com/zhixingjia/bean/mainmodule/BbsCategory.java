package com.zhixingjia.bean.mainmodule;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import com.zhixingjia.httplib.BR;

import java.io.Serializable;

/**
* @description 百姓信息类型
* @author chenbin
* @date 2018/11/22 13:26
* @version v3.2.0
*/
public class BbsCategory extends BaseObservable implements Serializable {

    /**
     * cate_id : 4
     * cate_name : 宜兴天地
     * cate_img : http://h5.mobilekoudai.com/uploads/bbs_icon.png
     */

    private String cate_id;             //分类ID
    private String cate_name;           //分类名称
    private String cate_img;            //分类图标

    @Bindable
    public String getCate_id() {
        return cate_id;
    }

    public void setCate_id(String cate_id) {
        this.cate_id = cate_id;
        notifyPropertyChanged(BR.cate_id);
    }

    @Bindable
    public String getCate_name() {
        return cate_name;
    }

    public void setCate_name(String cate_name) {
        this.cate_name = cate_name;
        notifyPropertyChanged(BR.cate_name);
    }

    @Bindable
    public String getCate_img() {
        return cate_img;
    }

    public void setCate_img(String cate_img) {
        this.cate_img = cate_img;
        notifyPropertyChanged(BR.cate_img);
    }
}
