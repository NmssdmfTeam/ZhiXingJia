package com.nmssdmf.commonlib.activity;

import android.os.Bundle;

import com.nmssdmf.commonlib.R;
import com.nmssdmf.commonlib.callback.WebViewCB;
import com.nmssdmf.commonlib.databinding.ActivityWebViewBinding;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.nmssdmf.commonlib.viewmodel.WebViewVM;

public class WebViewActivity extends BaseTitleActivity implements WebViewCB{

    private final String TAG = WebViewActivity.class.getSimpleName();

    private WebViewVM vm;
    private ActivityWebViewBinding binding;

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public BaseVM initViewModel() {
        vm = new WebViewVM(this);
        return vm;
    }

    @Override
    public String setTitle() {
        return null;
    }

    @Override
    public void initContent(Bundle savedInstanceState) {
        binding = (ActivityWebViewBinding) baseViewBinding;
        vm.getIntentData();
        binding.wv.loadUrl(vm.getLink());
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_web_view;
    }
}
