package com.nmssdmf.commonlib.config;

/**
 * Created by ${nmssdmf} on 2018/11/14 0014.
 */

public class HttpVersionConfig {
    /**
     * 注册
     */
    public static final int API_AUTH_REGISTER = 1;
    /**
     * 登陆
     */
    public static final int API_AUTH_LOGIN = 1;
    /**
     * 发送验证码
     */
    public static final int API_AUTH_SEND_SMS = 1;
    /**
     * 找回密码
     */
    public static final int API_AUTH_FIND_PASSWORD = 1;
    /**
     * 首页
     */
    public static final int API_INDEX = 1;

    /**
     * 分类列表帖子
     */
    public static final int API_BBS_INFO_LIST = 1;
    /**
     * 热门搜索
     */
    public static final int API_HOTKEY = 1;
    /**
     * 帖子详情
     */
    public static final int API_BBS_VIEW = 2;

    /**
     * 宜兴头条详情评论
     */
    public static final int API_NEWS_HEADLINES_COMMENT = 2;

    /**
     * 分类
     */
    public static final int API_BBS_CATE = 1;
    /**
     * 帖子评论详情
     */
    public static final int API_BBS_COMMENT = 1;

    /**
     * 发帖保存
     */
    public static final int API_BBS_INSERT = 2;

    /**
     * 帖子评论保存
     */
    public static final int API_BBS_COMMENT_INSERT = 1;
    /**
     * 宜兴头条评论保存
     */
    public static final int API_HEADLINES_COMMENT_INSERT = 2;

    /**
     * 帖子评论保存
     */
    public static final int API_BBS_GIVE_INSERT = 1;

    /**
     * 宜兴头条点赞保存或者取消（评论点赞、帖子点赞）
     */
    public static final int API_HEADLINES_GIVE_INSERT = 2;

    /**
     * 所在地区
     */
    public static final int API_AREA = 1;

    /**
     * 收货地址 - 添加
     */
    public static final int API_ADDRESS_INSERT = 1;

    /**
     * 收货地址 - 添加
     */
    public static final int API_ADDRESS = 1;

    /**
     * 首页
     */
    public static final int API_MY = 1;

    /**
     * 建材家居--首页
     */
    public static final int API_HOUSE = 1;

    /**
     *根据分类或者搜索关键字来获取商家列表
     */
    public static final int API_HOUSE_SELLER = 1;
    /**
     *根据分类或者搜索关键字来获取商品列表
     */
    public static final int API_HOUSE_COMMODITY = 1;
    /**
     * 所在商圈
     */
    public static final int API_TRADE_AREA = 1;

    /**
     * 商品详情
     */
    public static final int API_HOUSE_COMMODITY_VIEW = 1;

    /**
     * 商品详情 - 评论列表
     */
    public static final int API_HOUSE_COMMODITY_COMMENT = 1;

    /**
     * 加入购物车（保存）
     */
    public static final int API_CART_STORE = 1;
    /**
     * 确认订单
     */
    public static final int API_CART_SETTLE = 1;
    /**
     * 购物车
     */
    public static final int API_CART = 1;
    /**
     * 删除购物车
     */
    public static final int API_CART_DEL = 1;

    /**
     * 提交订单
     */
    public static final int API_CART_SUBMITORDER = 1;
    /**
     * 宜兴头条，政务公告
     */
    public static final int API_NEWS = 1;
    /**
     * 订单列表
     */
    public static final int API_ORDER = 1;
    /**
     * 订单详情
     */
    public static final int API_ORDER_SHOW = 1;
    /**
     * 取消订单
     */
    public static final int API_ORDER_CANCEL = 1;
    /**
     * 到店付
     */
    public static final int API_ORDER_SHOPPAY = 1;
    /**
     * 确认到店付
     */
    public static final int API_ORDER_SHOPPAY_CONFIRM = 1;
    /**
     * 卖家发货
     */
    public static final int API_ORDER_DELIVER = 1;
    /**
     * 确认收货
     */
    public static final int API_ORDER_CONFIRM_RECEIPT = 1;

    /**
     * 评价保存
     */
    public static final int API_ORDER_JUDGE_SAVE = 1;

    /**
     * 卖家优惠券列表
     */
    public static final int API_COUPON_SELLER = 1;

    /**
     * 卖家优惠券添加、编辑保存
     */
    public static final int API_COUPON_SAVEINFO = 1;

    /**
     * 卖家优惠券删除
     */
    public static final int API_COUPON_DEL = 1;
    /**
     * 个人信息保存
     */
    public static final int API_MY_INFO_SAVE = 1;
    /**
     * 买家优惠券列表
     */
    public static final int API_COUPON_INFO = 1;
    /**
     * 领取商家优惠券
     */
    public static final int API_COUPON_RECEIVE = 1;
    /**
     * 我的帖子 - 发帖
     */
    public static final int API_MY_PLACARD = 1;
    /**
     * 我的帖子 - 回帖
     */
    public static final int API_MY_REPLIES = 1;
    /**
     * 分类列表
     */
    public static final int API_HOUSE_CATE = 1;
    /**
     * 成为卖家
     */
    public static final int API_MY_BECOME_SELLER = 1;
    /**
     * 宜兴电信广告信息列表
     */
    public static final int API_DX_INFOLISTS = 2;
    /**
     * 确认支付页面(选择支付方式)
     */
    public static final int API_PAY = 1;
    /**
     * 店铺首页
     */
    public static final int API_SHOPINFO = 1;
    /**
     * 店铺的全部商品
     */
    public static final int API_SHOPINFO_COMMODITY = 1;
    /**
     * 店铺的全部商品
     */
    public static final int API_SHOPINFO_EVALUATE = 1;
    /**
     * 付款操作
     */
    public static final int API_PAY_PAYMENT = 1;

    /**
     * 关于我们、注册协议、隐私政策
     */
    public static final int API_SINGLE = 1;

    /**
     * 关于我们、注册协议、隐私政策
     */
    public static final int API_AUTH_LOGOUT = 1;

    /**
     * 购物车中的猜你喜欢
     */
    public static final int API_GUESS_COMMODITY = 1;

    /**
     * 首页三大模块（24小时热点、最新发布、最后回复）
     */
    public static final int API_BBS_INDEX = 1;

    /**
     * 微信登录
     */
    public static final int API_AUTH_WEIXIN_LOGIN = 1;

    /**
     * 搜索帖子、精华置顶列表
     */
    public static final int API_BBS_SEARCHINFO = 1;

    /**
     * 搜索帖子、精华置顶列表
     */
    public static final int API_MESSAGE = 1;

    /**
     * 消息未读统计
     */
    public static final int API_MESSAGE_UNREAD = 1;

    /**
     * 意见反馈
     */
    public static final int API_MY_FEEDBACK = 1;

    /**
     * 变更登录账号
     */
    public static final int API_MY_CHANGE_ACCOUNT = 1;

    /**
     * 购物车数量统计
     */
    public static final int API_CART_ALLSUM = 1;

    /**
     * 商家商品列表
     */
    public static final int API_COMMODITY_INDEX = 1;

    /**
     * 商品数量统计
     */
    public static final int API_COMMODITY_NUMBER = 1;

    /**
     * 商品上下架操作
     */
    public static final int API_COMMODITY_UPPER_LOWER = 1;

    /**
     * 商品删除
     */
    public static final int API_COMMODITY_DEL = 1;

    /**
     * 获取商品的分类、规格、单位、品牌数据(添加与编辑的时候都需要请求)
     */
    public static final int API_COMMODITY_INITIALIZE = 1;

    /**
     * 商品添加、编辑保存
     */
    public static final int API_COMMODITY_OPERATION = 1;

    /**
     * 商品编辑详情
     */
    public static final int API_COMMODITY_SHOW = 1;

    /**
     * 商家信息
     */
    public static final int API_MY_COMPANY = 1;

    /**
     * 生活服务全部分类列表
     */
    public static final int API_LIFE_CATE = 1;

    /**
     * 信息列表
     */
    public static final int API_LIFE_INDEX = 1;

    /**
     * 广告汇总
     */
    public static final int API_BANNERS = 1;

    /**
     * 信息详情
     */
    public static final int API_LIFE_SHOW = 1;

    /**
     * 首页精华置顶
     */
    public static final int API_BBS_STICKS = 1;

    /**
     * 公司资料修改保存
     */
    public static final int API_MY_COMPANY_SAVE = 1;

    /**
     * 获取APP版本更新信息
     */
    public static final int API_APP_UPDATE_INFO = 1;

    /**
     * 收货地址 - 删除
     */
    public static final int API_ADDRESS_DEL = 1;

    /**
     * 获取用户的在个推的ClientID
     */
    public static final int API_MY_GETUI_PUSH = 1;

    /**
     * 帖子黑名单
     */
    public static final int API_BBS_BLACK  = 1;

    /**
     * 帖子删除
     */
    public static final int API_BBS_MYDEL  = 1;

    /**
     * 帖子举报
     */
    public static final int API_BBS_REPORT  = 1;

    /**
     * 促销活动列表
     */
    public static final int API_ZHANSHI_PROMOTION  = 2;

    /**
     * 领券中心列表
     */
    public static final int API_ZHANSHI_COUPON  = 2;

    /**
     * 领券中心列表 - 立即领取
     */
    public static final int API_ZHANSHI_COUPON_RECEIVE  = 2;

    /**
     * 宜兴电信分类点击更多的商品列表
     */
    public static final int API_DX_COMMODITY_LIST  = 2;

    /**
     * 我的卡券列表
     */
    public static final int API_MY_CARD_TICKET  = 2;

    /**
     *买家扫一扫核销卡券
     */
    public static final int API_MY_COUPON_WRITE_OFF = 2;

    /**
     * 宜兴头条详情
     */
    public static final int API_NEWS_HEADLINES = 2;
}
