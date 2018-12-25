package com.zhixingjia.personmodule.viewmodule;

import android.databinding.ObservableField;
import android.os.Bundle;

import com.google.gson.Gson;
import com.nmssdmf.commonlib.bean.BaseData;
import com.nmssdmf.commonlib.callback.BaseCB;
import com.nmssdmf.commonlib.config.HttpVersionConfig;
import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.config.PrefrenceConfig;
import com.nmssdmf.commonlib.httplib.HttpUtils;
import com.nmssdmf.commonlib.httplib.RxRequest;
import com.nmssdmf.commonlib.httplib.ServiceCallback;
import com.nmssdmf.commonlib.util.PreferenceUtil;
import com.nmssdmf.commonlib.util.StringUtil;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhixingjia.bean.mainmodule.UserInfo;
import com.zhixingjia.service.PersonService;

import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

/**
 * Created by ${nmssdmf} on 2018/11/20 0020.
 */

public class ChangeNameVM extends BaseVM {
    public final ObservableField<String> name = new ObservableField<>();
    private int type = 1;//1：nickname 2：realname
    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public ChangeNameVM(BaseCB callBack) {
        super(callBack);
    }

    public void getData(){
        Bundle bundle = baseCallBck.getIntentData();
        if (bundle != null) {
            name.set(bundle.getString(IntentConfig.NAME));
            type = bundle.getInt(IntentConfig.TYPE, 1);
        }
    }

    public void changeName() {
        if (StringUtil.isEmpty(name.get())) {
            baseCallBck.showToast("请先输入内容");
            return;
        }
        Map<String, String> map = new HashMap<>();
        if (type == 1) {
            map.put("nickname", name.get());
        } else {
            map.put("realname", name.get());
        }
        baseCallBck.showLoaddingDialog();
        HttpUtils.doHttp(subscription, RxRequest.create(PersonService.class, HttpVersionConfig.API_MY_INFO_SAVE).infoSave(map), new ServiceCallback<BaseData>() {
            @Override
            public void onError(Throwable error) {

            }

            @Override
            public void onSuccess(BaseData data) {
                UserInfo userInfo = new Gson().fromJson(PreferenceUtil.getString(PrefrenceConfig.USER_INFO, ""), UserInfo.class);
                if (type == 1) {
                    userInfo.setNickname(name.get());
                } else {
                    userInfo.setRealname(name.get());
                }
                PreferenceUtil.setStringValue(PrefrenceConfig.USER_INFO, new Gson().toJson(userInfo));
                //调用接口
                Bundle bundle = new Bundle();
                bundle.putString(IntentConfig.NAME, name.get());
                bundle.putInt(IntentConfig.TYPE, type);
                baseCallBck.setResultCode(RESULT_OK, bundle);
                baseCallBck.finishActivity();
            }

            @Override
            public void onDefeated(BaseData data) {

            }
        });
    }
}
