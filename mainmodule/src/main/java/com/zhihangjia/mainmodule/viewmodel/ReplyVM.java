package com.zhihangjia.mainmodule.viewmodel;

import android.os.Bundle;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.commonlib.callback.BaseCB;
import com.nmssdmf.commonlib.config.HttpVersionConfig;
import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.config.StringConfig;
import com.nmssdmf.commonlib.httplib.HttpUtils;
import com.nmssdmf.commonlib.httplib.RxRequest;
import com.nmssdmf.commonlib.httplib.ServiceCallback;
import com.nmssdmf.commonlib.rxbus.RxBus;
import com.nmssdmf.commonlib.rxbus.RxEvent;
import com.nmssdmf.commonlib.util.ToastUtil;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.bean.PostContent;
import com.zhixingjia.service.MainService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @description 回复
* @author chenbin
* @date 2018/11/21 17:37
* @version v3.2.0
*/
public class ReplyVM extends BaseVM {
    public String bbsId;
    public String commentId;

    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public ReplyVM(BaseCB callBack) {
        super(callBack);
        initData();
    }

    private void initData() {
        Bundle bundle = baseCallBck.getIntentData();
        bbsId = bundle.getString(IntentConfig.BBS_ID);
        commentId = bundle.getString(IntentConfig.COMMENT_ID);
    }


    /**
     * 评论
     * @param postContents
     */
    public void comment(List<PostContent> postContents) {
        baseCallBck.showLoaddingDialog();
        if (postContents == null || postContents.size() == 0){
            ToastUtil.showMsg("请填写内容");
            baseCallBck.dismissLoaddingDialog();
            return;
        }
        Map<String,Object> params = new HashMap<>();
        params.put("bbs_id", bbsId);
        if (!TextUtils.isEmpty(commentId))
            params.put("comment_id", commentId);
        params.put("contents", new Gson().toJson(postContents));
        //先上传图片
        HttpUtils.doHttp(subscription, RxRequest.create(MainService.class, HttpVersionConfig.API_BBS_COMMENT_INSERT).commentInsert(params),
                new ServiceCallback<Base>() {
                    @Override
                    public void onError(Throwable error) {
                        baseCallBck.dismissLoaddingDialog();
                    }

                    @Override
                    public void onSuccess(Base base) {
                        if (StringConfig.OK.equals(base.getStatus_code())) {
                            baseCallBck.dismissLoaddingDialog();
                            baseCallBck.finishActivity();
                            //刷新消息
                            RxBus.getInstance().send(RxEvent.BbsEvent.COMMENT_INSERT, null);
                        }
                    }

                    @Override
                    public void onDefeated(Base base) {
                        baseCallBck.dismissLoaddingDialog();
                    }
                });
    }
}
