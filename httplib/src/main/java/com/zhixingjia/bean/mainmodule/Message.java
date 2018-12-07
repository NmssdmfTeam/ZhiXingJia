package com.zhixingjia.bean.mainmodule;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.zhixingjia.httplib.BR;

public class Message extends BaseObservable {
    private String id;              //ID，分页的时候需要传输
    private String jumps;           //跳转说明 0=不跳转 1=采购方跳转订单详情  2=供应商跳转订单详情
    private String relation;        //跳转的关联信息，订单号
    private String contents;        //内容
    private String createtime;      //时间

    @Bindable
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
        notifyPropertyChanged(BR.id);
    }

    @Bindable
    public String getJumps() {
        return jumps;
    }

    public void setJumps(String jumps) {
        this.jumps = jumps;
        notifyPropertyChanged(BR.jumps);
    }

    @Bindable
    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
        notifyPropertyChanged(BR.relation);
    }

    @Bindable
    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
        notifyPropertyChanged(BR.contents);
    }

    @Bindable
    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
        notifyPropertyChanged(BR.createtime);
    }
}
