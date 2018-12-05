package com.nmssdmf.commonlib.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.gson.annotations.SerializedName;
import com.nmssdmf.commonlib.BR;

/**
* @description 付款操作成功输出
* @author chenbin
* @date 2018/12/4 11:00
* @version v3.2.0
*/
public class Payment extends BaseObservable {
    private Alipay alipay;//支付宝,把payment内的值整体能过APP发启给支付宝，为空用null表示
    private Weixin weixin;//选择微信支付后，把服务端给出这些数据从客户端发启给微支付，如不是微支付此字段为null

    @Bindable
    public Alipay getAlipay() {
        return alipay;
    }

    public void setAlipay(Alipay alipay) {
        this.alipay = alipay;
        notifyPropertyChanged(BR.alipay);
    }

    @Bindable
    public Weixin getWeixin() {
        return weixin;
    }

    public void setWeixin(Weixin weixin) {
        this.weixin = weixin;
        notifyPropertyChanged(BR.weixin);
    }

    public static class Alipay extends BaseObservable {
        private String payment;

        @Bindable
        public String getPayment() {
            return payment;
        }

        public void setPayment(String payment) {
            this.payment = payment;
            notifyPropertyChanged(BR.payment);
        }
    }

    public static class  Weixin extends BaseObservable {
        private String prepayid;
        private String appid;
        private String partnerid;
        @SerializedName("package")
        private String packageX;
        private String noncestr;
        private String timestamp;
        private String sign;

        @Bindable
        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
            notifyPropertyChanged(BR.prepayid);
        }

        @Bindable
        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
            notifyPropertyChanged(BR.appid);
        }

        @Bindable
        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
            notifyPropertyChanged(BR.partnerid);
        }

        @Bindable
        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
            notifyPropertyChanged(BR.packageX);
        }

        @Bindable
        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
            notifyPropertyChanged(BR.noncestr);
        }

        @Bindable
        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
            notifyPropertyChanged(BR.timestamp);
        }

        @Bindable
        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
            notifyPropertyChanged(BR.sign);
        }
    }
}
