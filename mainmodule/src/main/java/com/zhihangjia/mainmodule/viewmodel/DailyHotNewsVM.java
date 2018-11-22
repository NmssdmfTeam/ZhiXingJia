package com.zhihangjia.mainmodule.viewmodel;

import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.commonlib.callback.BaseRecyclerViewFragmentCB;
import com.nmssdmf.commonlib.config.HttpVersionConfig;
import com.nmssdmf.commonlib.viewmodel.BaseRecyclerViewFragmentVM;
import com.zhixingjia.httplib.HttpUtils;
import com.zhixingjia.httplib.RxRequest;
import com.zhixingjia.httplib.ServiceCallback;
import com.zhixingjia.service.MainService;

import java.util.ArrayList;
import java.util.List;

/**
 * 热点新闻viewmodel
 * Create by chenbin on 2018/11/18
 * <p>
 * <p>
 */
public class DailyHotNewsVM extends BaseRecyclerViewFragmentVM {
    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public DailyHotNewsVM(BaseRecyclerViewFragmentCB callBack) {
        super(callBack);
    }

    @Override
    public void initData(boolean isRefresh) {
        List<Base> list = new ArrayList<>();
        for (int i=0; i < 10; i++) {
            list.add(new Base());
        }
        baseCB.refreshAdapter(isRefresh,list);
    }

    /**
     * 获取首页三大模块（24小时热点、最新发布、最后回复）
     * @param type 必填，类型，0=24小时热点、1=最新发布、2=最后回复
     */
    public void getBbsIndex(int type) {
//        HttpUtils.doHttp(subscription, RxRequest.create(MainService.class, HttpVersionConfig.API_BBS_INDEX).getIndex(), new ServiceCallback<Base>() {
//            @Override
//            public void onError(Throwable error) {
//
//            }
//
//            @Override
//            public void onSuccess(Base base) {
//
//            }
//
//            @Override
//            public void onDefeated(Base base) {
//
//            }
//        });
    }
}
