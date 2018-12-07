package com.zhixingjia.personmodule.activity;

import android.os.Bundle;

import com.nmssdmf.commonlib.activity.BaseTitleActivity;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhixingjia.personmodule.R;
import com.zhixingjia.personmodule.callback.FeedBackCB;
import com.zhixingjia.personmodule.databinding.ActivityFeedbackBinding;
import com.zhixingjia.personmodule.viewmodule.FeedbackVM;

/**
* @description 意见反馈
* @author chenbin
* @date 2018/12/7 15:15
* @version v3.2.0
*/
public class FeedbackActivity extends BaseTitleActivity implements FeedBackCB {
    private final String TAG = FeedbackActivity.class.getSimpleName();
    private FeedbackVM vm;
    private ActivityFeedbackBinding binding;

    @Override
    public String setTitle() {
        return "意见反馈";
    }

    @Override
    public void initContent(Bundle savedInstanceState) {
        binding = (ActivityFeedbackBinding) baseViewBinding;
        binding.setVm(vm);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_feedback;
    }

    @Override
    public BaseVM initViewModel() {
        vm = new FeedbackVM(this);
        return vm;
    }

    @Override
    public String getTAG() {
        return TAG;
    }

}
