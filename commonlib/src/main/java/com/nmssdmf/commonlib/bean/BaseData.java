package com.nmssdmf.commonlib.bean;

import android.databinding.Bindable;

import com.nmssdmf.commonlib.BR;


/**
 * Create by huscarter@163.com on 11/14/17
 * <p>
 * 基础的数据实体
 */
public final class BaseData<T> extends Base {
    private String count;

    private T data;

    @Bindable
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
        notifyPropertyChanged(BR.data);
    }

    @Bindable
    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
        notifyPropertyChanged(BR.count);
    }
}
