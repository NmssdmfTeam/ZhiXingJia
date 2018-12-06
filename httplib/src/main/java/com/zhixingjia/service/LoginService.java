package com.zhixingjia.service;

import com.nmssdmf.commonlib.bean.BaseData;
import com.zhixingjia.bean.loginmodule.LoginResult;
import com.zhixingjia.bean.loginmodule.WXAccessToken;
import com.zhixingjia.bean.loginmodule.WXUserInfo;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

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

    /**
     * 微信验证获取权限
     * @param  map
     * @return
     */
    @GET("/sns/oauth2/access_token")
    Observable<WXAccessToken> requestWXAccessToken(@QueryMap Map<String, Object> map);

    /**
     * 微信获取用户个人信息
     * @param map
     * @return
     */
    @GET("/sns/userinfo")
    Observable<WXUserInfo> requestWXUserInfo(@QueryMap Map<String, Object> map);

    /**
     * 发送验证码
     * @param openid 必填，微信的openid
     * @return
     */
    @FormUrlEncoded
    @POST ("/api/auth/weixin_login")
    Observable<BaseData<LoginResult>> wexinLogin(@Field("openid") String openid);

    /**
     * 注册
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("/api/auth/weixin_bind")
    Observable<BaseData<LoginResult>> wexinBind(@FieldMap Map<String, String> map);
}
