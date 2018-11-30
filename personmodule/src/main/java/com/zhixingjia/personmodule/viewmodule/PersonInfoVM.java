package com.zhixingjia.personmodule.viewmodule;

import android.databinding.ObservableField;
import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;
import com.nmssdmf.commonlib.bean.BaseData;
import com.nmssdmf.commonlib.callback.WheelPickerWindowCB;
import com.nmssdmf.commonlib.config.HttpVersionConfig;
import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.config.PrefrenceConfig;
import com.nmssdmf.commonlib.httplib.HttpUtils;
import com.nmssdmf.commonlib.httplib.RxRequest;
import com.nmssdmf.commonlib.httplib.ServiceCallback;
import com.nmssdmf.commonlib.util.PreferenceUtil;
import com.nmssdmf.commonlib.util.StringUtil;
import com.nmssdmf.commonlib.util.ToastUtil;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhixingjia.bean.mainmodule.UserInfo;
import com.zhixingjia.personmodule.activity.ChangeNameActivity;
import com.zhixingjia.personmodule.callback.PersonInfoCB;
import com.zhixingjia.service.PersonService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ${nmssdmf} on 2018/11/20 0020.
 */

public class PersonInfoVM extends BaseVM implements WheelPickerWindowCB {
    public static final int CHANGE_NAME_REQUEST_CODE = 100;
    private PersonInfoCB cb;

    private List<String> sexList = new ArrayList<>();
    public final ObservableField<UserInfo> userInfo = new ObservableField<>();

    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public PersonInfoVM(PersonInfoCB callBack) {
        super(callBack);
        cb = callBack;
    }

    public void initData() {
        String userInfoString = PreferenceUtil.getString(PrefrenceConfig.USER_INFO, "");
        if (StringUtil.isEmpty(userInfoString)) {

        } else {
            userInfo.set(new Gson().fromJson(userInfoString, UserInfo.class));
        }


        sexList.add("男");
        sexList.add("女");
    }

    public void tvNickNameClick(View view, int type) {
        Bundle bundle = new Bundle();
        if (type == 1) {
            bundle.putString(IntentConfig.NAME, userInfo.get().getNickname());
        } else {
            bundle.putString(IntentConfig.NAME, userInfo.get().getRealname());
        }
        bundle.putInt(IntentConfig.TYPE, type);
        cb.doIntentForResult(ChangeNameActivity.class, bundle, CHANGE_NAME_REQUEST_CODE);
    }

    @Override
    public void tvSureClick(String item, final int position) {
        if (userInfo.get().getSex().equals(String.valueOf(position)))//没有变化
            return;
        Map<String, String> map = new HashMap<>();
        map.put("sex", String.valueOf(position));
        cb.showLoaddingDialog();
        HttpUtils.doHttp(subscription, RxRequest.create(PersonService.class, HttpVersionConfig.API_MY_INFO_SAVE).infoSave(map), new ServiceCallback<BaseData>() {
            @Override
            public void onError(Throwable error) {

            }

            @Override
            public void onSuccess(BaseData data) {
                ToastUtil.showMsg(data.getMessage());
                userInfo.get().setSex(String.valueOf(position));
                PreferenceUtil.setStringValue(PrefrenceConfig.USER_INFO, new Gson().toJson(userInfo));
            }

            @Override
            public void onDefeated(BaseData data) {

            }
        });
    }

    public List<String> getSexList() {
        return sexList;
    }

    public void setSexList(List<String> sexList) {
        this.sexList = sexList;
    }
}
