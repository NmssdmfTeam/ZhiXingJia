package com.zhihangjia.mainmodule.viewmodel;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.os.Bundle;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.commonlib.bean.BaseData;
import com.nmssdmf.commonlib.config.HttpVersionConfig;
import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.httplib.HttpUtils;
import com.nmssdmf.commonlib.httplib.RxRequest;
import com.nmssdmf.commonlib.httplib.ServiceCallback;
import com.nmssdmf.commonlib.rxbus.RxBus;
import com.nmssdmf.commonlib.rxbus.RxEvent;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.bean.GoodsComment;
import com.zhihangjia.mainmodule.callback.CommentCB;
import com.zhixingjia.bean.mainmodule.OrderDetail;
import com.zhixingjia.service.MainService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @description 发表评论viewmodel
* @author chenbin
* @date 2018/11/28 19:58
* @version v3.2.0
*/
public class CommentVM extends BaseVM {
    private String orderId = "181128175448797138";
    private CommentCB callback;
    public ObservableField<OrderDetail> orderDetail = new ObservableField<>();
    public List<GoodsComment> goodsComments = new ArrayList<>();
    public ObservableInt logisticsScore = new ObservableInt();              //物流服务
    public ObservableInt serviceScore = new ObservableInt();                //卖家服务

    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public CommentVM(CommentCB callBack) {
        super(callBack);
        this.callback = callBack;
        iniData();
    }

    private void iniData() {
        Bundle bundle = callback.getIntentData();
        if (bundle != null) {
            orderId = bundle.getString(IntentConfig.ORDER_ID);
        }
    }

    /**
     * 获取订单详情
     */
    public void getOrderInfo() {
        HttpUtils.doHttp(subscription,
                RxRequest.create(MainService.class, HttpVersionConfig.API_ORDER_SHOW).getOrderDetail(orderId),
                new ServiceCallback<BaseData<OrderDetail>>() {
            @Override
            public void onError(Throwable error) {

            }

            @Override
            public void onSuccess(BaseData<OrderDetail> orderDetailBaseData) {
                orderDetail.set(orderDetailBaseData.getData());
                callback.getOrderDetailSuccess();
            }

            @Override
            public void onDefeated(BaseData<OrderDetail> orderDetailBaseData) {

            }
        });
    }

    public void post() {
        if (!isDataValidate())
            return;
        //开始上传图片
        callback.showLoaddingDialog();
        int imgcount = callback.uploadImg();
        if (imgcount == 0) {
            postInfo();
        }
    }

    /**
     * 发表评论
     */
    public void postInfo() {
        //获取上传完成的图片ids，加到goodsComments列表内
        callback.setImgIds();
        Map<String, String> map = new HashMap<>();
        map.put("logistics_score", String.valueOf(logisticsScore.get()));
        map.put("service_score", String.valueOf(serviceScore.get()));
        map.put("good_score", new Gson().toJson(goodsComments));
        map.put("order_id", orderId);
        HttpUtils.doHttp(subscription,
                RxRequest.create(MainService.class, HttpVersionConfig.API_ORDER_JUDGE_SAVE).orderJudgeSave(map),
                new ServiceCallback<Base>() {
            @Override
            public void onError(Throwable error) {

            }

            @Override
            public void onSuccess(Base base) {
                callback.showToast("发表评论成功");
                RxBus.getInstance().send(RxEvent.OrderEvent.ORDER_COMMENT, null);
            }

            @Override
            public void onDefeated(Base base) {

            }
        });
    }

    /**
     * 数据是否有效
     */
    private boolean isDataValidate() {
        for (GoodsComment goodsComment : goodsComments) {
            if (goodsComment.getCommodity_score() < 5 && TextUtils.isEmpty(goodsComment.getNote())) {
                callback.showToast("请填写评论内容");
                return false;
            }
        }
        if (logisticsScore.get() == 0) {
            callback.showToast("请为物流服务打分");
            return false;
        }
        if (serviceScore.get() == 0) {
            callback.showToast("请为卖家服务打分");
            return false;
        }
        return true;
    }
}
