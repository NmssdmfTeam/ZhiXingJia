package com.zhixingjia.bean.mainmodule;

/**
* @description 我的卡券数量
* @author chenbin
* @date 2019/1/24 16:50
* @version v3.2.0
*/
public class CouponCardTicketSum {
    private String notused_sum;         //未使用数量
    private String used_sum;            //已使用数量
    private String failure_sum;         //已失效数量

    public String getNotused_sum() {
        return notused_sum;
    }

    public void setNotused_sum(String notused_sum) {
        this.notused_sum = notused_sum;
    }

    public String getUsed_sum() {
        return used_sum;
    }

    public void setUsed_sum(String used_sum) {
        this.used_sum = used_sum;
    }

    public String getFailure_sum() {
        return failure_sum;
    }

    public void setFailure_sum(String failure_sum) {
        this.failure_sum = failure_sum;
    }
}
