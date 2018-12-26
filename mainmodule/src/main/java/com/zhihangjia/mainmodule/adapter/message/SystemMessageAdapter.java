package com.zhihangjia.mainmodule.adapter.message;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.View;

import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.config.PrefrenceConfig;
import com.nmssdmf.commonlib.config.StringConfig;
import com.nmssdmf.commonlib.util.PreferenceUtil;
import com.nmssdmf.customerviewlib.databindingbase.BaseBindingViewHolder;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.activity.OrderDetailActivity;
import com.zhihangjia.mainmodule.databinding.ItemSystemMessageBinding;
import com.zhixingjia.bean.mainmodule.Message;

import java.util.List;

public class SystemMessageAdapter extends BaseDataBindingAdapter<Message, ItemSystemMessageBinding> {

    public SystemMessageAdapter( @Nullable List<Message> data) {
        super(R.layout.item_system_message, data);
    }

    @Override
    protected void convert2(BaseBindingViewHolder<ItemSystemMessageBinding> helper, Message item, int position) {
        helper.getBinding().setData(item);
        String str = item.getContents();
        if (!"0".equals(item.getJumps()))
            str = str + "<font color='#FF9A14'><small>点击查看</small></font>";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            helper.getBinding().tvContent.setText(Html.fromHtml(str,Html.FROM_HTML_MODE_LEGACY));
        } else {
            helper.getBinding().tvContent.setText(Html.fromHtml(str));
        }

        helper.getBinding().getRoot().setOnClickListener(v -> {
            if ("0".equals(item.getJumps())) {

            } else if ("1".equals(item.getJumps())) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                intent.setClass(mContext ,OrderDetailActivity.class);
                bundle.putString(IntentConfig.ID, item.getRelation());
                bundle.putString(IntentConfig.IDENTITY, StringConfig.BUYER);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            } else if ("2".equals(item.getJumps())) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                intent.setClass(mContext ,OrderDetailActivity.class);
                bundle.putString(IntentConfig.ID, item.getRelation());
                bundle.putString(IntentConfig.IDENTITY, StringConfig.PROVIDER);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
    }
}
