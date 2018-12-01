package com.zhihangjia.mainmodule.viewmodel;

import com.nmssdmf.commonlib.bean.BaseListData;
import com.nmssdmf.commonlib.callback.BaseRecyclerViewFragmentCB;
import com.nmssdmf.commonlib.config.HttpVersionConfig;
import com.nmssdmf.commonlib.httplib.HttpUtils;
import com.nmssdmf.commonlib.httplib.RxRequest;
import com.nmssdmf.commonlib.httplib.ServiceCallback;
import com.nmssdmf.commonlib.viewmodel.BaseRecyclerViewFragmentVM;
import com.zhixingjia.bean.personmodule.Reply;
import com.zhixingjia.service.MainService;

/**
* @description 我的贴子 回帖 fragment viewmodel
* @author chenbin
* @date 2018/11/20 18:24
* @version v3.2.0
*/
public class MyPostReplyFragmentVM extends BaseRecyclerViewFragmentVM {
    private String page = "0";
    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public MyPostReplyFragmentVM(BaseRecyclerViewFragmentCB callBack) {
        super(callBack);
    }

    @Override
    public void initData(boolean isRefresh) {
        if (isRefresh)
            page = "0";
        HttpUtils.doHttp(subscription,
                RxRequest.create(MainService.class, HttpVersionConfig.API_MY_REPLIES).getReplies(page),
                new ServiceCallback<BaseListData<Reply>>() {
                    @Override
                    public void onError(Throwable error) {

                    }

                    @Override
                    public void onSuccess(BaseListData<Reply> replyBaseListData) {
                        if (replyBaseListData.getData().size() > 0)
                            page = replyBaseListData.getData().get(replyBaseListData.getData().size() - 1).getComment_id();
                        baseCB.refreshAdapter(isRefresh,replyBaseListData.getData());
                    }

                    @Override
                    public void onDefeated(BaseListData<Reply> replyBaseListData) {

                    }
                });
    }
}
