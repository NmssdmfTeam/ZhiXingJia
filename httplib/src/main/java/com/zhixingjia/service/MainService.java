package com.zhixingjia.service;

import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.commonlib.bean.BaseData;
import com.nmssdmf.commonlib.bean.BaseListData;
import com.zhixingjia.bean.mainmodule.Banner;
import com.zhixingjia.bean.mainmodule.BbsCategory;
import com.zhixingjia.bean.mainmodule.BbsInfoList;
import com.zhixingjia.bean.mainmodule.Comment;
import com.zhixingjia.bean.mainmodule.Commodity;
import com.zhixingjia.bean.mainmodule.CommodityComfirm;
import com.zhixingjia.bean.mainmodule.CommodityDetail;
import com.zhixingjia.bean.mainmodule.CouponSeller;
import com.zhixingjia.bean.mainmodule.HotHistory;
import com.zhixingjia.bean.mainmodule.HouseBean;
import com.zhixingjia.bean.mainmodule.IndexBean;
import com.zhixingjia.bean.mainmodule.MessageComment;
import com.zhixingjia.bean.mainmodule.MessageDetail;
import com.zhixingjia.bean.mainmodule.New;
import com.zhixingjia.bean.mainmodule.Order;
import com.zhixingjia.bean.mainmodule.OrderDetail;
import com.zhixingjia.bean.mainmodule.PayInfo;
import com.nmssdmf.commonlib.bean.Payment;
import com.zhixingjia.bean.mainmodule.Seller;
import com.zhixingjia.bean.mainmodule.ShopCar;
import com.zhixingjia.bean.mainmodule.ShopInfo;
import com.zhixingjia.bean.mainmodule.TradeArea;
import com.zhixingjia.bean.mainmodule.UserInfo;
import com.zhixingjia.bean.mainmodule.YXTelecom;
import com.zhixingjia.bean.personmodule.Placard;
import com.zhixingjia.bean.personmodule.Reply;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Field;
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
    Observable<BaseData<CommodityComfirm>> getConformData(@FieldMap Map<String, String> map);

    /**
     * 购物车
     * @return
     */
    @GET ("/api/cart")
    Observable<BaseListData<ShopCar>> getShopCarData();

    /**
     * 删除购物车
     * @param cart_id 必填，JSON格式,JOSN里面需要双引号，['1','3','2']，选择要删除的规格中的cart_id
     * @return
     */
    @FormUrlEncoded
    @POST ("/api/cart/del")
    Observable<BaseData> shopCarDelete(@Field("cart_id") String cart_id);

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

    /**
     * 提交订单
     * @return
     */
    @FormUrlEncoded
    @POST ("/api/cart/submitorder")
    Observable<BaseListData<String>> submitOrder(@Field("addr_id") String addr_id, @Field("orders") String orders);

    /**
     * 宜兴头条
     * @param types 必填，类型，0=宜兴头条 1=要闻动态 2=基层动态 3=基层动态
     * @param page 选填，分页的时候传输数组中是后一条数据的ID值，一页10条数据，默认为0
     * @return
     */
    @GET ("/api/news")
    Observable<BaseListData<New>> getNews(@Query("types") String types, @Query("page") String page );

    /**
     * 订单列表
     * @param map
     * @return
     */
    @GET ("/api/order")
    Observable<BaseListData<Order>> getOrderList(@QueryMap Map<String, String> map);

    /**
     * 订单详情
     * @return
     */
    @GET ("/api/order/show")
    Observable<BaseData<OrderDetail>> getOrderDetail(@Query("order_id") String order_id);

    /**
     * 取消订单
     * @param order_id
     * @return
     */
    @FormUrlEncoded
    @POST ("/api/order/cancel")
    Observable<BaseData> cancelOrder(@Field("order_id") String order_id);

    /**
     * 到店付
     * @param order_id
     * @return
     */
    @FormUrlEncoded
    @POST ("/api/order/shoppay")
    Observable<BaseData> offlinePay(@Field("order_id") String order_id);

    /**
     * 确认到店付
     * @param order_id
     * @return
     */
    @FormUrlEncoded
    @POST ("/api/order/shoppay_confirm")
    Observable<BaseData> checkOfflinePay(@Field("order_id") String order_id);

    /**
     * 卖家发货
     */
    @FormUrlEncoded
    @POST ("/api/order/deliver")
    Observable<BaseData> send(@Field("order_id") String order_id);

    /**
     * 确认收货
     * @param order_id
     * @return
     */
    @FormUrlEncoded
    @POST ("/api/order/confirm_receipt")
    Observable<BaseData> checkReceiver(@Field("order_id") String order_id);

    /**
     * 评价保存
     */
    @FormUrlEncoded
    @POST ("/api/order/judge_save")
    Observable<Base> orderJudgeSave(@FieldMap() Map<String,String> map);

    /**
     * 卖家优惠券列表
     * @return
     */
    @GET ("/api/coupon/seller")
    Observable<BaseListData<CouponSeller>> getCouponSeller(@Query("page") String page);

    /**
     * 卖家优惠券添加、编辑保存
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST ("/api/coupon/saveinfo")
    Observable<Base> couponSaveInfo(@FieldMap() Map<String,String> map);

    /**
     * 卖家优惠券添加、编辑保存
     * @param coupon_id
     * @return
     */
    @FormUrlEncoded
    @POST ("/api/coupon/del")
    Observable<Base> couponDel(@Field("coupon_id") String coupon_id);

    /**
     * 领取商家优惠券
     * @param coupon_id
     * @return
     */
    @FormUrlEncoded
    @POST ("/api/coupon/receive")
    Observable<BaseData> getCoupon(@Field("coupon_id") String coupon_id);

    /**
     * 我的帖子 - 发帖
     */
    @GET("/api/my/placard")
    Observable<BaseListData<Placard>> getPlacard(@Query("page") String page);

    /**
     * 我的帖子 - 回帖
     */
    @GET("/api/my/replies")
    Observable<BaseListData<Reply>> getReplies(@Query("page") String page);

    /**
     * 成为卖家
     */
    @FormUrlEncoded
    @POST("/api/my/become_seller")
    Observable<Base> becomeSeller(@FieldMap Map<String,String> map);

    /**
     * 成为卖家
     */
    @GET("/api/house/cate")
    Observable<BaseListData<HouseBean.CateBean>> getHouseCate();

    /**
     * 宜兴电信广告信息列表
     */
    @GET("/api/dx/infolist")
    Observable<BaseListData<YXTelecom>> getYXInfoList(@Query("page") String page);

    /**
     * 确认支付页面(选择支付方式)
     */
    @GET("/api/pay")
    Observable<BaseData<PayInfo>> apiPay(@Query("order_id") String order_id);

    /**
     * 店铺首页
     */
    @GET("/api/shopinfo")
    Observable<BaseData<ShopInfo>> getShopInfo(@Query("member_id") String member_id);

    /**
     * 店铺的全部商品
     */
    @GET("/api/shopinfo/commodity")
    Observable<BaseListData<Commodity>> getShopInfoCommodity(@Query("member_id") String member_id, @Query("pages") int pages, @Query("sort") String sort);

    /**
     * 店铺中的口碑评价
     */
    @GET("/api/shopinfo/evaluate")
    Observable<BaseListData<Comment>> getShopInfoEvaluate(@Query("member_id") String member_id, @Query("pages") String pages);

    /**
     * 付款操作
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST ("/api/pay/payment")
    Observable<BaseData<Payment>> payment(@FieldMap Map<String, String> map);
}
