package com.zhixingjia.service;

import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.commonlib.bean.BaseData;
import com.nmssdmf.commonlib.bean.BaseListData;
import com.zhixingjia.bean.mainmodule.BbsCategory;
import com.zhixingjia.bean.mainmodule.BbsInfoList;
import com.zhixingjia.bean.mainmodule.HotHistory;
import com.zhixingjia.bean.mainmodule.IndexBean;
import com.zhixingjia.bean.mainmodule.MessageComment;
import com.zhixingjia.bean.mainmodule.MessageDetail;

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
}
