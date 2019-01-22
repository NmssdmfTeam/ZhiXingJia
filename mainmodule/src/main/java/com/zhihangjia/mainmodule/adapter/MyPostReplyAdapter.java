package com.zhihangjia.mainmodule.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.customerviewlib.databindingbase.BaseBindingViewHolder;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.activity.MessageDetailActivity;
import com.zhihangjia.mainmodule.databinding.ItemMyPostReplyBinding;
import com.zhixingjia.bean.personmodule.Reply;

import java.util.List;

/**
* @description 我的帖子 回复
* @author chenbin
* @date 2018/11/20 18:21
* @version v3.2.0
*/
public class MyPostReplyAdapter extends BaseDataBindingAdapter<Reply, ItemMyPostReplyBinding> {
    public MyPostReplyAdapter(@Nullable List<Reply> data) {
        super(R.layout.item_my_post_reply, data);
    }

    @Override
    protected void convert2(BaseBindingViewHolder<ItemMyPostReplyBinding> helper, Reply item, int position) {
        ItemMyPostReplyBinding binding = helper.getBinding();
        binding.setData(item);
        binding.getRoot().setOnClickListener(v -> {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString(IntentConfig.ID, item.getBbs_id());
            bundle.putInt(IntentConfig.POSITION, position);
            intent.putExtras(bundle);
            intent.setClass(mContext,MessageDetailActivity.class);
            mContext.startActivity(intent);
        });
    }
}
