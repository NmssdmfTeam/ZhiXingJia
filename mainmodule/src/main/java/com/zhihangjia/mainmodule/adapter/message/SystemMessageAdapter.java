package com.zhihangjia.mainmodule.adapter.message;

import android.support.annotation.Nullable;
import android.text.Html;
import android.view.View;

import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.customerviewlib.databindingbase.BaseBindingViewHolder;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.databinding.ItemSystemMessageBinding;

import java.util.List;

public class SystemMessageAdapter extends BaseDataBindingAdapter<Base, ItemSystemMessageBinding> {

    public SystemMessageAdapter( @Nullable List<Base> data) {
        super(R.layout.item_system_message, data);
    }

    @Override
    protected void convert2(BaseBindingViewHolder<ItemSystemMessageBinding> helper, Base item, int position) {
        String str = "你的订单1823414324324已支付成功，请尽快发货请快发布哦 <font color='#FF9A14'><small>点击查看</small></font>";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            helper.getBinding().tvContent.setText(Html.fromHtml(str,Html.FROM_HTML_MODE_LEGACY));
        } else {
            helper.getBinding().tvContent.setText(Html.fromHtml(str));
        }

        helper.getBinding().getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
