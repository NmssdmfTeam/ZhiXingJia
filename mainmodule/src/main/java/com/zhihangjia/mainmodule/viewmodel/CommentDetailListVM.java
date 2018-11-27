package com.zhihangjia.mainmodule.viewmodel;

import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.commonlib.bean.BaseListData;
import com.nmssdmf.commonlib.callback.BaseRecyclerViewFragmentCB;
import com.nmssdmf.commonlib.config.HttpVersionConfig;
import com.nmssdmf.commonlib.httplib.HttpUtils;
import com.nmssdmf.commonlib.httplib.RxRequest;
import com.nmssdmf.commonlib.httplib.ServiceCallback;
import com.nmssdmf.commonlib.rxbus.RxEvent;
import com.nmssdmf.commonlib.viewmodel.BaseRecyclerViewFragmentVM;
import com.zhixingjia.bean.mainmodule.Comment;
import com.zhixingjia.service.MainService;

import java.util.ArrayList;
import java.util.List;

/**
 * 评论详情viewmodel
 * Create by chenbin on 2018/11/17
 * <p>
 * <p>
 */
public class CommentDetailListVM extends BaseRecyclerViewFragmentVM {
    private String commodityId = "1";
    private String page = "0";

    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public CommentDetailListVM(BaseRecyclerViewFragmentCB callBack) {
        super(callBack);
    }

    @Override
    public void initData(final boolean isRefresh) {
        if (isRefresh)
            page = "0";
        HttpUtils.doHttp(subscription,
                RxRequest.create(MainService.class, HttpVersionConfig.API_HOUSE_COMMODITY_COMMENT).getCommodityComment(commodityId, page),
                new ServiceCallback<BaseListData<Comment>>() {
            @Override
            public void onError(Throwable error) {

            }

            @Override
            public void onSuccess(BaseListData<Comment> commentBaseListData) {
                baseCB.refreshAdapter(isRefresh,commentBaseListData.getData());
                if (commentBaseListData.getData().size() > 0) {
                    page = commentBaseListData.getData().get(commentBaseListData.getData().size() - 1).getComment_id();
                }
            }

            @Override
            public void onDefeated(BaseListData<Comment> commentBaseListData) {

            }
        });
    }
}
