package com.zhixingjia.bean.mainmodule;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.zhixingjia.httplib.BR;

/**
* @description 生活服务列表数据
* @author chenbin
* @date 2018/12/19 10:38
* @version v3.2.0
*/
public class LifeService extends BaseObservable {

    private String info_id;             //信息ID，分页与详情的时候需要传输
    private String info_name;           //标题
    private String info_img;            //图片
    private String info_tel;            //联系方式
    private String info_addr;           //地址

    @Bindable
    public String getInfo_id() {
        return info_id;
    }

    public void setInfo_id(String info_id) {
        this.info_id = info_id;
        notifyPropertyChanged(BR.info_id);
    }

    @Bindable
    public String getInfo_name() {
        return info_name;
    }

    public void setInfo_name(String info_name) {
        this.info_name = info_name;
        notifyPropertyChanged(BR.info_name);
    }

    @Bindable
    public String getInfo_img() {
        return info_img;
    }

    public void setInfo_img(String info_img) {
        this.info_img = info_img;
        notifyPropertyChanged(BR.info_img);
    }

    @Bindable
    public String getInfo_tel() {
        return info_tel;
    }

    public void setInfo_tel(String info_tel) {
        this.info_tel = info_tel;
        notifyPropertyChanged(BR.info_tel);
    }

    @Bindable
    public String getInfo_addr() {
        return info_addr;
    }

    public void setInfo_addr(String info_addr) {
        this.info_addr = info_addr;
        notifyPropertyChanged(BR.info_addr);
    }
}
