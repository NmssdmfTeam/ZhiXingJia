package com.nmssdmf.commonlib.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.nmssdmf.commonlib.R;
import com.nmssdmf.commonlib.bean.WebViewJsBridge;
import com.nmssdmf.commonlib.callback.WebViewCB;
import com.nmssdmf.commonlib.config.StringConfig;
import com.nmssdmf.commonlib.databinding.ActivityWebViewBinding;
import com.nmssdmf.commonlib.util.JLog;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.nmssdmf.commonlib.viewmodel.WebViewVM;

public class WebViewActivity extends BaseTitleActivity implements WebViewCB{
    private boolean isTitleShow = true;
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
        WebViewJsBridge jsbridge = new WebViewJsBridge(this);
        jsbridge.setCallback(new JsCallbackImpl());
        binding.wv.addJavascriptInterface(jsbridge, StringConfig.WEB_JS);

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

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                setTitle(view.getTitle());
            }
        });
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_web_view;
    }

    private class JsCallbackImpl implements WebViewJsBridge.WebViewJsCallback {
        @Override
        public void setTitle(final String title) {
            baseTitleBinding.tTitle.post(new Runnable() {
                @Override
                public void run() {
                    WebViewActivity.this.setTitle(title);
//                    include_title.setVisibility(View.VISIBLE);
                }
            });
        }

        @Override
        public void setMenu(String param) {

        }

        @Override
        public void modifyMenu(String param) {

        }

        @Override
        public void setTitleGone() {
            JLog.d(TAG, "include_title.setVisibility(View.GONE);");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    baseTitleBinding.tTitle.setVisibility(View.GONE);
                    isTitleShow = false;
                }
            });
        }
    }
}
