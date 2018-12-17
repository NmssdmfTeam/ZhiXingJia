package com.zhixingjia.goodsmanagemodule.bean;

import android.databinding.BaseObservable;

import com.nmssdmf.commonlib.bean.UploadImage;

import java.io.Serializable;
import java.util.List;

/** 商品描述
* @description
* @author chenbin
* @date 2018/12/17 10:16
* @version v3.2.0
*/
public class ProductDescribe extends BaseObservable implements Serializable {
    private String note;                 //商品描述文字内容
    private List<UploadImage> imgs;          //商品描述图片内容

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<UploadImage> getImgs() {
        return imgs;
    }

    public void setImgs(List<UploadImage> imgs) {
        this.imgs = imgs;
    }
}
