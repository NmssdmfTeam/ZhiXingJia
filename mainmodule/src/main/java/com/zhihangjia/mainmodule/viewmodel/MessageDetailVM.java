package com.zhihangjia.mainmodule.viewmodel;


import com.nmssdmf.commonlib.bean.BaseData;
import com.nmssdmf.commonlib.config.HttpVersionConfig;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.callback.MessageDetailCB;
import com.zhixingjia.bean.mainmodule.MessageDetail;
import com.zhixingjia.httplib.HttpUtils;
import com.zhixingjia.httplib.RxRequest;
import com.zhixingjia.httplib.ServiceCallback;
import com.zhixingjia.service.MainService;


/**
 * @author chenbin
 * @version v3.2.0
 * @description 信息详情viewmodel
 * @date 2018/11/20 11:12
 */
public class MessageDetailVM extends BaseVM {
    private String messageId = "1";
    private MessageDetailCB cb;

    private MessageDetail detail;
    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public MessageDetailVM(MessageDetailCB callBack) {
        super(callBack);
        this.cb = callBack;
    }

    public void getMessageDetail() {
        HttpUtils.doHttp(subscription, RxRequest.create(MainService.class, HttpVersionConfig.API_BBS_VIEW).getMessageDetail(messageId), new ServiceCallback<BaseData<MessageDetail>>() {
            @Override
            public void onError(Throwable error) {

            }

            @Override
            public void onSuccess(BaseData<MessageDetail> data) {
                detail = data.getData();
                cb.initView();
            }

            @Override
            public void onDefeated(BaseData<MessageDetail> data) {

            }
        });
    }

    public void setDetail(MessageDetail detail) {
        this.detail = detail;
    }

    public MessageDetail getDetail() {
        return detail;
    }
}
