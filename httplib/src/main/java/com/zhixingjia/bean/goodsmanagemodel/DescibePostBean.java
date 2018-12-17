package com.zhixingjia.bean.goodsmanagemodel;

import java.util.List;

/**
* @description 商品描述提交类
* @author chenbin
* @date 2018/12/17 14:49
* @version v3.2.0
*/
public class DescibePostBean {
    private String note;        //内容
    private List<String> imgs;  //图片ID格式

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<String> getImgs() {
        return imgs;
    }

    public void setImgs(List<String> imgs) {
        this.imgs = imgs;
    }
}
