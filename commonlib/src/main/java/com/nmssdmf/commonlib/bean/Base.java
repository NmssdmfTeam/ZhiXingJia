package com.nmssdmf.commonlib.bean;

import android.databinding.BaseObservable;

import java.io.Serializable;

/**
 * <p>
 * 基础的bean，所有bean类需要继承此类
 */

public class Base extends BaseObservable implements Serializable {
    private String status_code;
    private String message;

    public String getStatus_code() {
        return status_code;
    }

    public void setStatus_code(String status_code) {
        this.status_code = status_code;
    }

    public String getMessage() {
        return message + "";
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
