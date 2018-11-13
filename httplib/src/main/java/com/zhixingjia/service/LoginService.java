package com.zhixingjia.service;

import com.nmssdmf.commonlib.bean.BaseData;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by ${nmssdmf} on 2018/11/13 0013.
 */

public interface LoginService {
    @FormUrlEncoded
    @POST("")
    Observable<BaseData> register(@FieldMap Map<String, String> map);

    @GET("")
    Observable<BaseData> login(@QueryMap Map<String, String> map);
}
