package com.nmssdmf.commonlib.util;

import android.app.Activity;
import android.app.Application;

import com.nmssdmf.commonlib.bean.Payment;
import com.nmssdmf.commonlib.config.BaseConfig;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

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


        SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
        parameters.put("appid", req.appId);
        parameters.put("noncestr", req.nonceStr);
        parameters.put("package", req.packageValue);
        parameters.put("partnerid", req.partnerId);
        parameters.put("prepayid", req.prepayId);
        parameters.put("timestamp", req.timeStamp);
        String sign = createSign("UTF-8",parameters);
        JLog.d(TAG, "sign:"+sign);
        req.sign = sign;

        api.sendReq(req);
    }

    /**
     *   * 微信支付签名算法sign
     *   *
     *   * @param characterEncoding
     *   * @param parameters
     *   * @return
     *   
     */
    public static String createSign(String characterEncoding,
                                    SortedMap<Object, Object> parameters) {

        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();// 所有参与传参的参数按照accsii排序（升序）
        Iterator it = es.iterator();
        while (it.hasNext()) {
            @SuppressWarnings("rawtypes")
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            Object v = entry.getValue();
            if (null != v && !"".equals(v) && !"sign".equals(k)
                    && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + BaseConfig.WXAPI_APPSECRET); //KEY是商户秘钥
        String sign = MD5Util.MD5Encode(sb.toString(), characterEncoding)
                .toUpperCase();
        return sign;
    }

    public void registerApp(Application application) {
        api = WXAPIFactory.createWXAPI(application, null);
        api.registerApp(BaseConfig.WXAPI_APPID);
    }
}
