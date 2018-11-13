package com.zhihangjia.mainmodule.bean;

import android.databinding.Bindable;

import com.nmssdmf.commonlib.bean.Base;
import com.zhihangjia.mainmodule.BR;

/**
 * Create by huscarter@163.com on 11/22/17
 * <p>
 * 首页广告类
 * <p>
 */

public class IndexAdvertise extends Base {

    private String jump_page;
    private String link;
    private String ident;

    @Bindable
    public String getJump_page() {
        return jump_page;
    }

    public void setJump_page(String jump_page) {
        this.jump_page = jump_page;
        notifyPropertyChanged(BR.jump_page);
    }

    @Bindable
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
        notifyPropertyChanged(BR.link);
    }

    @Bindable
    public String getIdent() {
        return ident;
    }

    public void setIdent(String ident) {
        this.ident = ident;
        notifyPropertyChanged(BR.ident);
    }
}
