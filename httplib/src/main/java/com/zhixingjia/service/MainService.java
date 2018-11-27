package com.zhixingjia.service;

import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.commonlib.bean.BaseData;
import com.nmssdmf.commonlib.bean.BaseListData;
import com.zhixingjia.bean.mainmodule.Banner;
import com.zhixingjia.bean.mainmodule.BbsCategory;
import com.zhixingjia.bean.mainmodule.BbsInfoList;
import com.zhixingjia.bean.mainmodule.Comment;
import com.zhixingjia.bean.mainmodule.Commodity;
import com.zhixingjia.bean.mainmodule.CommodityDetail;
import com.zhixingjia.bean.mainmodule.HotHistory;
import com.zhixingjia.bean.mainmodule.HouseBean;
import com.zhixingjia.bean.mainmodule.IndexBean;
import com.zhixingjia.bean.mainmodule.MessageComment;
import com.zhixingjia.bean.mainmodule.MessageDetail;
import com.zhixingjia.bean.mainmodule.Seller;
import com.zhixingjia.bean.mainmodule.ShopCar;
import com.zhixingjia.bean.mainmodule.TradeArea;
import com.zhixingjia.bean.mainmodule.UserInfo;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Create by chenbin on 2018/11/14
 * <p>
 * <p>
 */
public interface MainService {

    /**
     * 获取首页数据
     * @return
     */
    @GET("/api/index")
    Observable<BaseData<IndexBean>> getIndex();

    /**
     * 分类列表帖子
     * @return
     */
    @GET("/api/bbs/info_list")
    Observable<BaseListData<BbsInfoList>> getBbsInfoList(@Query("types") String types, @Query("cate_id")String cate_id, @Query("pages") String pages);
    /**
     * 热门搜索
     * @return
     */
    @GET ("/api/hotkey")
    Observable<BaseListData<HotHistory>> getHotHistory();

    /**
     * 帖子详情
     */
    @GET ("/api/bbs/view")
    Observable<BaseData<MessageDetail>> getMessageDetail(@Query("bbs_id") String bbs_id);

    /**
     * 分类
     * @return
     */
    @GET ("/api/bbs/cate")
    Observable<BaseListData<BbsCategory>> getBbsCate();

    /**
     * 帖子评论列表
     * @return
     */
    @GET ("/api/bbs/comment")
    Observable<BaseListData<MessageComment>> getCommentList(@QueryMap Map<String, String> map);

    /**
     * 发帖保存
     * @return
     */
    @FormUrlEncoded
    @POST("/api/bbs/insert")
    Observable<Base> postBbs(@FieldMap Map<String,Object> params);

    /**
     * 帖子评论保存
     * @return
     */
    @FormUrlEncoded
    @POST("/api/bbs/comment_insert")
    Observable<Base> commentInsert(@FieldMap Map<String,Object> params);

    /**
     * 评论文章点赞
     * @return
     */
    @FormUrlEncoded
    @POST("/api/bbs/give_insert")
    Observable<Base> giveInsert(@FieldMap Map<String,Object> params);

    /**
     * 首页
     * 广告汇总
     * @return
     */
    @GET ("/api/banners")
    Observable<BaseData<Banner>> getBanner(@Query("source") String source);

    /**
     * 我的
     * @return
     */
    @GET("/api/my")
    Observable<BaseData<UserInfo>> getUserInfo(@Query("identity")String identity);

    /**
     * 建材家居 首页
     * @return
     */
    @GET("/api/house")
    Observable<BaseData<HouseBean>> getHouseIndex();

    /**
     * 商家搜索
     * @return
     */
    @GET ("/api/house/seller")
    Observable<BaseListData<Seller>> getSeller(@QueryMap Map<String, String> map);

    /**
     * 商品搜索
     * @param map
     * @return
     */
    @GET ("/api/house/commodity")
    Observable<BaseListData<Commodity>> getCommodity(@QueryMap Map<String, String> map);

    /**
     * 所在商圈
     * @return
     */
    @GET ("/api/trade_area")
    Observable<BaseListData<TradeArea>> getTradeArea();

    /**
     * 商品详情
     * @return
     */
    @GET ("/api/house/commodity_view")
    Observable<BaseData<CommodityDetail>> getCommodity(@Query("commodity_id") String commodity_id);

    /**
     * 确认订单
     */
    @FormUrlEncoded
    @POST ("/api/cart/settle")
    Observable<BaseData> getConformData(@FieldMap Map<String, String> map);

    /**
     * 购物车
     * @return
     */
    @GET ("/api/cart")
    Observable<BaseListData<ShopCar>> getShopCarData();

    /**
     * 商品详情 - 评论列表
     * @return
     */
    @GET ("/api/house/commodity_comment")
    Observable<BaseListData<Comment>> getCommodityComment(@Query("commodity_id") String commodity_id, @Query("pages") String pages);

    /**
     * 商品详情 - 评论列表
     * @return
     */
    @FormUrlEncoded
    @POST ("/api/cart/store")
    Observable<Base> getCartStore(@FieldMap Map<String,String> params);
}
