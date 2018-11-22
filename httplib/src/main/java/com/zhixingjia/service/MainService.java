package com.zhixingjia.service;

import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.commonlib.bean.BaseData;
import com.zhixingjia.bean.mainmodule.IndexBean;

import io.reactivex.Observable;
import retrofit2.http.GET;

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

//    Observable<Base>
}
