package com.nmssdmf.commonlib.util;

import android.app.Activity;
import android.app.Application;

import com.nmssdmf.commonlib.bean.Payment;
import com.nmssdmf.commonlib.config.BaseConfig;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WeChatPayUtil {
    private final String TAG = WeChatPayUtil.class.getSimpleName();
    private IWXAPI api;
    private static WeChatPayUtil instance;

    public static synchronized WeChatPayUtil getInstance() {
        if (instance == null) {
            instance = new WeChatPayUtil();
        }
        return instance;
    }

    public void pay(Activity activity, Payment.Weixin weixin) {
        api = WXAPIFactory.createWXAPI(activity, BaseConfig.WXAPI_APPID);
        PayReq req = new PayReq();
        req.appId = weixin.getAppid();
        req.partnerId = weixin.getPartnerid();
        req.prepayId = weixin.getPrepayid();
        req.nonceStr = weixin.getNoncestr();
        req.timeStamp = weixin.getTimestamp();
        req.packageValue = weixin.getPackageX();
        req.sign = weixin.getSign();


//        SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
//        parameters.put("appid", req.appId);
//        parameters.put("noncestr", req.nonceStr);
//        parameters.put("package", req.packageValue);
//        parameters.put("partnerid", req.partnerId);
//        parameters.put("prepayid", req.prepayId);
//        parameters.put("timestamp", req.timeStamp);
//        String sign = createSign("UTF-8",parameters);
//        JLog.d(TAG, "sign:"+sign);
//        req.sign = sign;

        api.sendReq(req);
    }

    public void registerApp(Application application) {
        api = WXAPIFactory.createWXAPI(application, null);
        api.registerApp(BaseConfig.WXAPI_APPID);
    }
}
