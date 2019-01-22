package com.zhixingjia.bean.mainmodule;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import com.nmssdmf.commonlib.BR;

/**
* @description 领券中心
* @author chenbin
* @date 2019/1/22 13:23
* @version v3.2.0
*/
public class CenterCoupon extends BaseObservable {

    /**
     * coupon_id : 2
     * title : 优惠券9有老克勒有老克勒宵中
     * coupon_img : http://h6.mobilekoudai.com/uploads/2019-01-21/7a27666aa4004c03bf7ba6501c2174b9dd.png
     * company_name : 仅限蒙得切家具使用
     * description : 1、213
     2、老克勒
     3、老克勒
     * decrease : 2000
     * morethannumber : 0
     * cond : all
     * allsum : 46
     * receive_sum : 0
     */

    private String coupon_id;
    private String title;                       //卡券名称
    private String coupon_img;                  //卡券图片
    private String company_name;                //卡券发布者
    private String description;                 //卡券优惠说明
    private String decrease;                    //卡券优惠券金额
    private String morethannumber;              //满减需要达到的金额
    private String cond;                        //卡券类型 all=全额购 morethan=满减券
    private String allsum;                      //卡券发行的数量
    private String receive_sum;                 //卡券领取的数量，当这个值等于allsum值时就表示已抢光

    @Bindable
    public String getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(String coupon_id) {
        this.coupon_id = coupon_id;
        notifyPropertyChanged(BR.coupon_id);
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }

    @Bindable
    public String getCoupon_img() {
        return coupon_img;
    }

    public void setCoupon_img(String coupon_img) {
        this.coupon_img = coupon_img;
        notifyPropertyChanged(BR.coupon_img);
    }

    @Bindable
    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
        notifyPropertyChanged(BR.company_name);
    }

    @Bindable
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        notifyPropertyChanged(BR.description);
    }

    @Bindable
    public String getDecrease() {
        return decrease;
    }

    public void setDecrease(String decrease) {
        this.decrease = decrease;
        notifyPropertyChanged(BR.decrease);
    }

    @Bindable
    public String getMorethannumber() {
        return morethannumber;
    }

    public void setMorethannumber(String morethannumber) {
        this.morethannumber = morethannumber;
        notifyPropertyChanged(BR.morethannumber);
    }

    @Bindable
    public String getCond() {
        return cond;
    }

    public void setCond(String cond) {
        this.cond = cond;
        notifyPropertyChanged(BR.cond);
    }

    @Bindable
    public String getAllsum() {
        return allsum;
    }

    public void setAllsum(String allsum) {
        this.allsum = allsum;
        notifyPropertyChanged(BR.allsum);
    }

    @Bindable
    public String getReceive_sum() {
        return receive_sum;
    }

    public void setReceive_sum(String receive_sum) {
        this.receive_sum = receive_sum;
        notifyPropertyChanged(BR.receive_sum);
    }
}
