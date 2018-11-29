package com.zhihangjia.mainmodule.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.zhihangjia.mainmodule.BR;

import java.util.List;

/**
* @description 商品评价
* @author chenbin
* @date 2018/11/29 11:27
* @version v3.2.0
*/
public class GoodsComment extends BaseObservable {
    private String item_id;             //订单明细ID
    private int commodity_score;     //商品描述评分,1-5分       如果分值为5分时,评价内容可以不填写,少于5分必须填写
    private String note;                //评价内容
    private List<String> imgs;          //图片        图片可传可不传
    private String item_img;

    @Bindable
    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
        notifyPropertyChanged(BR.item_id);
    }

    @Bindable
    public int getCommodity_score() {
        return commodity_score;
    }

    public void setCommodity_score(int commodity_score) {
        this.commodity_score = commodity_score;
        notifyPropertyChanged(BR.commodity_score);
    }

    @Bindable
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
        notifyPropertyChanged(BR.note);
    }

    @Bindable
    public List<String> getImgs() {
        return imgs;
    }

    public void setImgs(List<String> imgs) {
        this.imgs = imgs;
        notifyPropertyChanged(BR.imgs);
    }

    @Bindable
    public String getItem_img() {
        return item_img;
    }

    public void setItem_img(String item_img) {
        this.item_img = item_img;
        notifyPropertyChanged(BR.item_img);
    }
}
