package com.zhihangjia.mainmodule.window;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.databinding.WindowMerchantSpreadBinding;

/**
 * Created by ${nmssdmf} on 2018/11/15 0015.
 */

public class MerchantSpreadWindow extends PopupWindow {

    public MerchantSpreadWindow(Context context) {
        setContentView(initView(context));
    }

    private View initView(Context context) {
        WindowMerchantSpreadBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.window_merchant_spread, null, false);
        return binding.getRoot();
    }
}
