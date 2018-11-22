package com.zhixingjia.service;

import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.commonlib.bean.BaseData;
import com.nmssdmf.commonlib.bean.BaseListData;
import com.zhixingjia.bean.mainmodule.BbsCategory;
import com.zhixingjia.bean.mainmodule.BbsInfoList;
import com.zhixingjia.bean.mainmodule.HotHistory;
import com.zhixingjia.bean.mainmodule.IndexBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

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

    @GET ("/api/hotkey")
    Observable<BaseListData<HotHistory>> getHotHistory();

    /**
     * 分类
     * @return
     */
    @GET ("/api/bbs/cate")
    Observable<BaseListData<BbsCategory>> getBbsCate();
}
