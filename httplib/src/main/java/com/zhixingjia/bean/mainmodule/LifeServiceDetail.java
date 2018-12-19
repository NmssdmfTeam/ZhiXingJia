package com.zhixingjia.bean.mainmodule;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import com.zhixingjia.httplib.BR;
import java.util.List;

/**
* @description 生活服务详情
* @author chenbin
* @date 2018/12/19 14:42
* @version v3.2.0
*/
public class LifeServiceDetail extends BaseObservable {
    private String info_name;       //标题
    private String read_sum;        //阅读数量
    private String info_tel;        //电话
    private String info_addr;       //地址
    private String longitude;       //地址经度
    private String latitude;        //地址纬度
    private String createtime;      //时间
    private List<ContentsBean> content;//内容

    @Bindable
    public String getInfo_name() {
        return info_name;
    }

    public void setInfo_name(String info_name) {
        this.info_name = info_name;
        notifyPropertyChanged(BR.info_name);
    }

    @Bindable
    public String getRead_sum() {
        return read_sum;
    }

    public void setRead_sum(String read_sum) {
        this.read_sum = read_sum;
        notifyPropertyChanged(BR.read_sum);
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

    @Bindable
    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
        notifyPropertyChanged(BR.longitude);
    }

    @Bindable
    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
        notifyPropertyChanged(BR.latitude);
    }

    @Bindable
    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
        notifyPropertyChanged(BR.createtime);
    }

    @Bindable
    public List<ContentsBean> getContent() {
        return content;
    }

    public void setContent(List<ContentsBean> content) {
        this.content = content;
        notifyPropertyChanged(BR.content);
    }
}
