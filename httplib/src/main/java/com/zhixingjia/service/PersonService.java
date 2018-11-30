package com.zhixingjia.service;

import com.nmssdmf.commonlib.bean.BaseData;
import com.nmssdmf.commonlib.bean.BaseListData;
import com.zhixingjia.bean.personmodule.Address;
import com.zhixingjia.bean.personmodule.AddressInsertResult;
import com.zhixingjia.bean.personmodule.Coupon;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * 个人信息Service
 * Create by chenbin on 2018/11/24
 * <p>
 * <p>
 */
public interface PersonService {
    /**
     * 获取地址列表
     * @return
     */
    @GET("/api/address")
    Observable<BaseListData<Address>> getCommentList(@Query("page") int page);

    /**
     * 收货地址 - 添加
     * @return
     */
    @FormUrlEncoded
    @POST("/api/address/insert")
    Observable<BaseData<AddressInsertResult>> addressInsert(@FieldMap Map<String,Object> params);

    /**
     * 收货地址 - 保存
     * @return
     */
    @FormUrlEncoded
    @POST("/api/address/save")
    Observable<BaseData<AddressInsertResult>> addressSave(@FieldMap Map<String,Object> params);

    /**
     * 所在地区
     * @return
     */
    @GET("/api/area")
    Observable<BaseListData<String>> getArea();

    /**
     * 个人资料修改保存
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST ("/api/my/info_save")
    Observable<BaseData> infoSave(@FieldMap Map<String, String> map);

    /**
     * 买家优惠券列表 、 可用商家优惠券列表、可用平台优惠券列表
     * @return
     */
    @GET ("/api/coupon/info")
    Observable<BaseListData<Coupon>> getMyCoupon(@QueryMap Map<String, String> map);
}
