package com.zhixingjia.personmodule.viewmodule;

import android.databinding.ObservableField;
import android.os.Bundle;

import com.nmssdmf.commonlib.callback.BaseCB;
import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.viewmodel.BaseVM;

import static android.app.Activity.RESULT_OK;

/**
 * Created by ${nmssdmf} on 2018/11/20 0020.
 */

public class ChangeNameVM extends BaseVM {
    public final ObservableField<String> name = new ObservableField<>();
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
        }
    }

    public void changeName() {
        //调用接口
        Bundle bundle = new Bundle();
        bundle.putString(IntentConfig.NAME, name.get());
        baseCallBck.setResultCode(RESULT_OK, bundle);
        baseCallBck.finishActivity();
    }
}
