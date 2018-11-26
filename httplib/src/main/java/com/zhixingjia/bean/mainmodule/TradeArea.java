package com.zhixingjia.bean.mainmodule;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.zhixingjia.httplib.BR;

/**
 * Created by ${nmssdmf} on 2018/11/26 0026.
 */

public class TradeArea extends BaseObservable {

    private String trade_id;
    private String trade_name;

    public String getTrade_id() {
        return trade_id;
    }

    public void setTrade_id(String trade_id) {
        this.trade_id = trade_id;
    }

    @Bindable
    public String getTrade_name() {
        return trade_name;
    }

    public void setTrade_name(String trade_name) {
        this.trade_name = trade_name;
        notifyPropertyChanged(BR.trade_name);
    }
}
