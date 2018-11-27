package com.zhihangjia.mainmodule.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.zhihangjia.mainmodule.BR;

/**
* @description 头条
* @author chenbin
* @date 2018/11/14 9:58
* @version v3.2.0
*/
public class Headline extends BaseObservable {
    private int article_id;

    private String title;

    @Bindable
    public int getArticle_id() {
        return article_id;
    }

    public void setArticle_id(int article_id) {
        this.article_id = article_id;
        notifyPropertyChanged(BR.article_id);
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }
}
