package com.zhixingjia.bean.mainmodule;

import android.databinding.BaseObservable;

import com.nmssdmf.commonlib.bean.Base;

import java.io.Serializable;
import java.util.List;

/**
* @description 分类列表帖子
* @author chenbin
* @date 2018/11/22 10:27
* @version v3.2.0
*/
public class BbsInfoList extends BaseObservable implements Serializable {

    private String bbs_id;      //论坛ID
    private String title;       //标题
    private String read_sum;    //阅读数量
    private String createtime;  //时间
    private String nickname;    //发布者昵称
    private String types;       //类型  1=无图片只有标题  2=单个图片  3=有三张图片
    private String pages;       //分页需要传输的值
    private List<String> imgs;  //图片集，如果types为1时,此字段是空数组

    public String getBbs_id() {
        return bbs_id;
    }

    public void setBbs_id(String bbs_id) {
        this.bbs_id = bbs_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRead_sum() {
        return read_sum;
    }

    public void setRead_sum(String read_sum) {
        this.read_sum = read_sum;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public List<String> getImgs() {
        return imgs;
    }

    public void setImgs(List<String> imgs) {
        this.imgs = imgs;
    }
}
