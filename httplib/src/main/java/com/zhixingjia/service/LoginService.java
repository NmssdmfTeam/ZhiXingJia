package com.zhixingjia.service;

import com.nmssdmf.commonlib.bean.BaseData;
import com.zhixingjia.bean.loginmodule.LoginResult;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by ${nmssdmf} on 2018/11/13 0013.
 */

public interface LoginService {
    /**
     * 注册
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("/api/auth/register")
    Observable<BaseData> register(@FieldMap Map<String, String> map);

    /**
     * 登陆
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("/api/auth/login")
    Observable<BaseData<LoginResult>> login(@FieldMap Map<String, String> map);

    /**
     * 发送验证码
     * @param types 必填，类型 1=注册时发送短信 2=找回密码或者在注册后需要发的短信
     * @param mobile 必填且长度为11位，手机号
     * @return
     */
    @FormUrlEncoded
    @POST ("/api/auth/send_sms")
    Observable<BaseData> sendVerificationCode(@Field("types") String types, @Field("mobile") String mobile);

    /**
     * 找回密码
     * @param map
     * @return
     */
    @FormUrlEncoded
   @ POST ("/api/auth/find_password")
    Observable<BaseData> findPassword(@FieldMap Map<String, String> map);
}
