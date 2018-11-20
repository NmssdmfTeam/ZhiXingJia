package com.zhixingjia.personmodule.viewmodule;

import android.databinding.ObservableField;
import android.os.Bundle;
import android.view.View;

import com.nmssdmf.commonlib.callback.WheelPickerWindowCB;
import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhixingjia.personmodule.activity.ChangeNameActivity;
import com.zhixingjia.personmodule.callback.PersonInfoCB;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${nmssdmf} on 2018/11/20 0020.
 */

public class PersonInfoVM extends BaseVM implements WheelPickerWindowCB{
    public static final int CHANGE_NAME_REQUEST_CODE = 100;
    private PersonInfoCB cb;

    public final ObservableField<String> nickName = new ObservableField<>();

    private List<String> sexList = new ArrayList<>();

    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public PersonInfoVM(PersonInfoCB callBack) {
        super(callBack);
        cb = callBack;
    }

    public void initData(){
        sexList.add("男");
        sexList.add("女");
    }

    public void tvNickNameClick(View view) {
        Bundle bundle = new Bundle();
        bundle.putString(IntentConfig.NAME, nickName.get());
        cb.doIntentForResult(ChangeNameActivity.class, bundle, CHANGE_NAME_REQUEST_CODE);
    }

    @Override
    public void tvSureClick(String item, int position) {

    }

    public List<String> getSexList() {
        return sexList;
    }

    public void setSexList(List<String> sexList) {
        this.sexList = sexList;
    }
}
