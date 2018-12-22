package com.zhixingjia.bean.mainmodule;

/**
 * 百姓信息
 * Create by chenbin on 2018/12/22
 * <p>
 * <p>
 */
public class BbsInsertResult {
    private String bbs_id;          //帖子ID，发布成功之后需要跳到帖子详情上

    public String getBbs_id() {
        return bbs_id;
    }

    public void setBbs_id(String bbs_id) {
        this.bbs_id = bbs_id;
    }
}
