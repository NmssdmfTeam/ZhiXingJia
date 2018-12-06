package com.nmssdmf.commonlib.social;

import android.content.Context;

import com.nmssdmf.commonlib.config.BaseConfig;
import com.nmssdmf.commonlib.util.JLog;
import com.nmssdmf.commonlib.util.ToastUtil;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Create by chenbin on 2018/4/9
 * <p>
 * 微信登录api
 */
public class WXLoginApi {
    private IWXAPI iwxapi;
    private static final String TAG = WXLoginApi.class.getSimpleName();

    public WXLoginApi(Context context) {
        iwxapi = WXAPIFactory.createWXAPI(context, BaseConfig.WXAPI_APPID);
        iwxapi.registerApp(BaseConfig.WXAPI_APPID);
    }

    /**
     * 进行微信授权
     */
    public void weChatLogin() {
        if (iwxapi.isWXAppInstalled()) {
            SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = "com.jushi.product";
            boolean result = iwxapi.sendReq(req);
            JLog.d(TAG, "sendReq Result:" + result);
        } else {
            ToastUtil.getInstance().showToast("用户未安装微信");
        }
    }
}
