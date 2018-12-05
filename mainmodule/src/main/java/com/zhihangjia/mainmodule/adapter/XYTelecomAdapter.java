package com.zhihangjia.mainmodule.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.nmssdmf.commonlib.activity.WebViewActivity;
import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.customerviewlib.databindingbase.BaseBindingViewHolder;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.databinding.ItemXyTelecomBinding;
import com.zhixingjia.bean.mainmodule.YXTelecom;

import java.util.List;

/**
 * Created by ${nmssdmf} on 2018/11/23 0023.
 */

public class XYTelecomAdapter extends BaseDataBindingAdapter<YXTelecom, ItemXyTelecomBinding> {

    public XYTelecomAdapter( @Nullable List<YXTelecom> data) {
        super(R.layout.item_xy_telecom, data);
    }

    @Override
    protected void convert2(BaseBindingViewHolder<ItemXyTelecomBinding> helper, YXTelecom item, int position) {
        ItemXyTelecomBinding binding = helper.getBinding();
        binding.setData(item);
        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString(IntentConfig.LINK, item.getLink_url());
                intent.putExtras(bundle);
                intent.setClass(mContext, WebViewActivity.class);
                mContext.startActivity(intent);
            }
        });
    }
}
