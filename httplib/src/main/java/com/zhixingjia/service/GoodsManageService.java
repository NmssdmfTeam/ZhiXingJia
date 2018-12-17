package com.zhixingjia.service;

import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.commonlib.bean.BaseData;
import com.nmssdmf.commonlib.bean.BaseListData;
import com.zhixingjia.bean.goodsmanagemodel.CommodityShow;
import com.zhixingjia.bean.mainmodule.Commodity;
import com.zhixingjia.bean.mainmodule.CommodityInitialize;
import com.zhixingjia.bean.mainmodule.GoodManageNumber;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface GoodsManageService {

    /**
     * 商家商品列表
     */
    @GET("/api/commodity/index")
    Observable<BaseListData<Commodity>> getCommodityIndex(@QueryMap Map<String, String> map);

    /**
     * 商品数量统计
     */
    @GET("/api/commodity/number")
    Observable<BaseData<GoodManageNumber>> getCommodityNumber();

    /**
     * 商品上下架操作
     */
    @FormUrlEncoded
    @POST("/api/commodity/upper_lower")
    Observable<Base> commodityUpperLower(@Field("commodity_id") String commodity_id, @Field("status") String status);

    /**
     * 商品删除
     */
    @FormUrlEncoded
    @POST("/api/commodity/del")
    Observable<Base> commodityDelete(@Field("commodity_id") String commodity_id);

    /**
     * 获取商品的分类、规格、单位、品牌数据(添加与编辑的时候都需要请求)
     */
    @GET("/api/commodity/initialize")
    Observable<BaseData<CommodityInitialize>> commodityInitialize();

    /**
     * 商品添加、编辑保存
     */
    @FormUrlEncoded
    @POST("/api/commodity/operation")
    Observable<Base> commodityOperation(@FieldMap Map<String,Object> params);

    /**
     * 商品编辑详情
     */
    @GET("/api/commodity/show")
    Observable<BaseData<CommodityShow>> commodityShow(@Query("commodity_id") String commodity_id);
}
