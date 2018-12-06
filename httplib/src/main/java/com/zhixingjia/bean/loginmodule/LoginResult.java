package com.zhixingjia.bean.loginmodule;

/**
 * Created by ${nmssdmf} on 2018/11/14 0014.
 */

public class LoginResult  {
    private String status;//类型 0=不存在，需要跳到微信绑定的界面，1=存在，会直接输出下面的token值
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
