package com.zhihangjia.mainmodule.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.customerviewlib.databindingbase.BaseBindingViewHolder;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.activity.XYHeadLineDetailActivity;
import com.zhihangjia.mainmodule.databinding.ItemHeadlineBinding;
import com.zhixingjia.bean.mainmodule.New;

import java.util.List;

/**
* @description 宜兴头条列表adatper
* @author chenbin
* @date 2018/11/16 15:33
* @version v3.2.0
*/
public class YXHeadLineAdapter extends BaseDataBindingAdapter<New,ItemHeadlineBinding> {
    public YXHeadLineAdapter(@Nullable List<New> data) {
        super(R.layout.item_headline, data);
    }

    @Override
    protected void convert2(BaseBindingViewHolder<ItemHeadlineBinding> helper, New item, int position) {
        ItemHeadlineBinding binding = helper.getBinding();
        binding.setData(item);
        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(item.getLink_url()))
                    return;
                Intent intent = new Intent();
                intent.setClass(mContext,XYHeadLineDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(IntentConfig.ID, item.getId());
                bundle.putInt(IntentConfig.POSITION, position);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
    }
}
