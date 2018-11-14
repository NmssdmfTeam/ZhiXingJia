package com.zhixingjia.service;

import com.nmssdmf.commonlib.bean.BaseData;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by ${nmssdmf} on 2018/11/13 0013.
 */

public interface LoginService {
    @FormUrlEncoded
    @POST("/api/auth/register")
    Observable<BaseData> register(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("/api/auth/login")
    Observable<BaseData> login(@FieldMap Map<String, String> map);
}
