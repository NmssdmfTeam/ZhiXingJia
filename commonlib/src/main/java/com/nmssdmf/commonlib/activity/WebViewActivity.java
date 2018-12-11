package com.nmssdmf.commonlib.activity;

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

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
        binding.wv.getSettings().setJavaScriptEnabled(true);
        binding.wv.loadUrl(vm.getLink());
        // 覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        binding.wv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                //设置加载进度条
                view.setWebChromeClient(new WebChromeClient());
                return true;
            }

        });
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_web_view;
    }
}
