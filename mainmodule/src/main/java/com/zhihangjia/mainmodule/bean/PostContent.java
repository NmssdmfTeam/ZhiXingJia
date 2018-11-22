package com.zhihangjia.mainmodule.bean;

/**
* @description 发帖内容
* @author chenbin
* @date 2018/11/22 16:24
* @version v3.2.0
*/
public class PostContent {

    private String note;        //内容

    private String[] imgs;      //图片ID格式


    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String[] getImgs() {
        return imgs;
    }

    public void setImgs(String[] imgs) {
        this.imgs = imgs;
    }
}
