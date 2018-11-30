package com.zhihangjia.mainmodule.viewmodel;


import com.nmssdmf.commonlib.bean.BaseListData;
import com.nmssdmf.commonlib.callback.BaseRecyclerViewFragmentCB;
import com.nmssdmf.commonlib.config.HttpVersionConfig;
import com.nmssdmf.commonlib.httplib.HttpUtils;
import com.nmssdmf.commonlib.httplib.RxRequest;
import com.nmssdmf.commonlib.httplib.ServiceCallback;
import com.nmssdmf.commonlib.viewmodel.BaseRecyclerViewFragmentVM;
import com.zhixingjia.bean.personmodule.Placard;
import com.zhixingjia.service.MainService;

/**
* @description 我的发帖viewmodel
* @author chenbin
* @date 2018/11/20 17:43
* @version v3.2.0
*/
public class MyPostFragmentVM extends BaseRecyclerViewFragmentVM {
    private String page = "0";

    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public MyPostFragmentVM(BaseRecyclerViewFragmentCB callBack) {
        super(callBack);
    }

    @Override
    public void initData(boolean isRefresh) {
        if (isRefresh)
            page = "0";
        HttpUtils.doHttp(subscription,
                RxRequest.create(MainService.class, HttpVersionConfig.API_MY_PLACARD).getPlacard(page),
                new ServiceCallback<BaseListData<Placard>>() {
            @Override
            public void onError(Throwable error) {

            }

            @Override
            public void onSuccess(BaseListData<Placard> placardBaseListData) {
                if (placardBaseListData.getData().size() > 0)
                    page = placardBaseListData.getData().get(placardBaseListData.getData().size() - 1).getBbs_id();
                baseCB.refreshAdapter(isRefresh,placardBaseListData.getData());
            }

            @Override
            public void onDefeated(BaseListData<Placard> placardBaseListData) {

            }
        });

    }
}
