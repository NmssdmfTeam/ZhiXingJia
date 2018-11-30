package com.zhihangjia.mainmodule.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.customerviewlib.databindingbase.BaseBindingViewHolder;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.activity.MessageDetailActivity;
import com.zhihangjia.mainmodule.databinding.ItemMyPostBinding;
import com.zhixingjia.bean.personmodule.Placard;

import java.util.List;

public class MyPostAdapter extends BaseDataBindingAdapter<Placard, ItemMyPostBinding> {
    public MyPostAdapter(@Nullable List<Placard> data) {
        super(R.layout.item_my_post, data);
    }

    @Override
    protected void convert2(BaseBindingViewHolder<ItemMyPostBinding> helper, Placard item, int position) {
        ItemMyPostBinding postBinding = helper.getBinding();
        postBinding.setData(item);
        postBinding.getRoot().setOnClickListener(v -> {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString(IntentConfig.ID, item.getBbs_id());
            intent.putExtras(bundle);
            intent.setClass(mContext,MessageDetailActivity.class);
            mContext.startActivity(intent);
        });
    }
}
