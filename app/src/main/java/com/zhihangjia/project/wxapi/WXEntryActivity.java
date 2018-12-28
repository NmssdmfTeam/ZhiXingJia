package com.zhihangjia.project.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nmssdmf.commonlib.config.BaseConfig;
import com.nmssdmf.commonlib.rxbus.EventInfo;
import com.nmssdmf.commonlib.rxbus.RxBus;
import com.nmssdmf.commonlib.rxbus.RxEvent;
import com.nmssdmf.commonlib.util.JLog;
import com.nmssdmf.commonlib.util.ToastUtil;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.ShowMessageFromWX;
import com.tencent.mm.opensdk.modelmsg.WXAppExtendObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.socialize.weixin.view.WXCallbackActivity;
import com.zhihangjia.project.R;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
	private final String TAG = WXEntryActivity.class.getSimpleName();
	private static final int TIMELINE_SUPPORTED_VERSION = 0x21020001;

	
	// IWXAPI 是第三方app和微信通信的openapi接口
    private IWXAPI api;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entry);
        
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
    	api = WXAPIFactory.createWXAPI(this, BaseConfig.WXAPI_APPID, false);
        
        api.handleIntent(getIntent(), this);
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	// 微信发送请求到第三方应用时，会回调到该方法
	@Override
	public void onReq(BaseReq req) {
		Toast.makeText(this, "openid = " + req.openId, Toast.LENGTH_SHORT).show();
		
		switch (req.getType()) {
		case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:
			break;
		case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:
			break;
		case ConstantsAPI.COMMAND_LAUNCH_BY_WX:
			break;
		default:
			break;
		}
	}

	// 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
	@Override
	public void onResp(BaseResp resp) {
		JLog.i(TAG, "onResp resp:" + new Gson().toJson(resp));
		if (resp.getType() == ConstantsAPI.COMMAND_SENDAUTH) {
			sendAuthResult(resp);
		}
		finish();
	}

	/**
	 * 授权登录回调处理
	 *
	 * @param resp
	 */
	private void sendAuthResult(BaseResp resp) {
		switch (resp.errCode) {
			case BaseResp.ErrCode.ERR_OK:
				if (resp instanceof SendAuth.Resp) {
					SendAuth.Resp newResp = (SendAuth.Resp) resp;
					//获取微信传回的code
					String code = newResp.code;
					JLog.i("WXTest", "onResp code = " + code);
					RxBus.getInstance().send(RxEvent.LoginEvent.LOGIN_WEXIN, new EventInfo(code));
				}
				break;
			case BaseResp.ErrCode.ERR_USER_CANCEL:
				break;
			case BaseResp.ErrCode.ERR_AUTH_DENIED:
				ToastUtil.getInstance().showToast("发送被拒绝");
				break;
			default:
				ToastUtil.getInstance().showToast("发送返回");
				break;
		}
	}

}