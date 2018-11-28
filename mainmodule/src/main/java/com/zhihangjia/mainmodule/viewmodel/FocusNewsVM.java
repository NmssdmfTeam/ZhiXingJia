package com.zhihangjia.mainmodule.viewmodel;

import com.nmssdmf.commonlib.bean.BaseListData;
import com.nmssdmf.commonlib.callback.BaseRecyclerViewFragmentCB;
import com.nmssdmf.commonlib.config.HttpVersionConfig;
import com.nmssdmf.commonlib.httplib.HttpUtils;
import com.nmssdmf.commonlib.httplib.RxRequest;
import com.nmssdmf.commonlib.httplib.ServiceCallback;
import com.nmssdmf.commonlib.viewmodel.BaseRecyclerViewFragmentVM;
import com.zhixingjia.bean.mainmodule.New;
import com.zhixingjia.service.MainService;

/**
* @description 要闻动态viewmodel
* @author chenbin
* @date 2018/11/16 16:53
* @version v3.2.0
*/
public class FocusNewsVM extends BaseRecyclerViewFragmentVM {
    private int type = 1;//1=要闻动态 2=基层动态 3=基层动态
    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public FocusNewsVM(BaseRecyclerViewFragmentCB callBack) {
        super(callBack);
    }

    @Override
    public void initData(boolean isRefresh) {
        baseCallBck.showLoaddingDialog();
        String page = "0";
        if (isRefresh || list.size() == 0) {

        } else {
            page = ((New)list.get(list.size() -1)).getId();
        }

        HttpUtils.doHttp(subscription, RxRequest.create(MainService.class, HttpVersionConfig.API_NEWS).getNews(String.valueOf(type), String.valueOf(page)), new ServiceCallback<BaseListData<New>>() {
            @Override
            public void onError(Throwable error) {

            }

            @Override
            public void onSuccess(BaseListData<New> data) {
                baseCB.refreshAdapter(isRefresh, data.getData());
            }

            @Override
            public void onDefeated(BaseListData<New> data) {

            }
        });
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
