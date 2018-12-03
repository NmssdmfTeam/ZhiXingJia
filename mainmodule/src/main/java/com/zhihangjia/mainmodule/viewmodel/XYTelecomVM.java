package com.zhihangjia.mainmodule.viewmodel;

import com.nmssdmf.commonlib.bean.BaseListData;
import com.nmssdmf.commonlib.config.HttpVersionConfig;
import com.nmssdmf.commonlib.httplib.HttpUtils;
import com.nmssdmf.commonlib.httplib.RxRequest;
import com.nmssdmf.commonlib.httplib.ServiceCallback;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.callback.XYTelecomCB;
import com.zhixingjia.bean.mainmodule.YXTelecom;
import com.zhixingjia.service.MainService;

/**
 * Created by ${nmssdmf} on 2018/11/23 0023.
 */

public class XYTelecomVM extends BaseVM {
    private XYTelecomCB cb;
    private String page = "0";

    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public XYTelecomVM(XYTelecomCB callBack) {
        super(callBack);
        cb = callBack;
    }

    public void getData(boolean isRefresh){
        if (isRefresh) {
            page = "0";
        }
        HttpUtils.doHttp(subscription,
                RxRequest.create(MainService.class, HttpVersionConfig.API_DX_INFOLISTS).getYXInfoList(page),
                new ServiceCallback<BaseListData<YXTelecom>>() {
            @Override
            public void onError(Throwable error) {
                cb.endFresh();
            }

            @Override
            public void onSuccess(BaseListData<YXTelecom> xyTelecomBaseListData) {
                cb.endFresh();
                cb.setData(xyTelecomBaseListData.getData(),isRefresh);
                if (xyTelecomBaseListData.getData().size() > 0) {
                    page = xyTelecomBaseListData.getData().get(xyTelecomBaseListData.getData().size() - 1).getId();
                }
            }

            @Override
            public void onDefeated(BaseListData<YXTelecom> xyTelecomBaseListData) {
                cb.endFresh();
            }
        });
    }
}
