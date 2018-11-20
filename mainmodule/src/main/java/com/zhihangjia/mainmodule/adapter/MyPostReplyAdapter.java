package com.zhihangjia.mainmodule.adapter;

import android.support.annotation.Nullable;
import android.widget.BaseAdapter;

import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.customerviewlib.databindingbase.BaseBindingViewHolder;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.databinding.ItemMyPostReplyBinding;

import java.util.List;

/**
* @description 我的帖子 回复
* @author chenbin
* @date 2018/11/20 18:21
* @version v3.2.0
*/
public class MyPostReplyAdapter extends BaseDataBindingAdapter<Base, ItemMyPostReplyBinding> {
    public MyPostReplyAdapter(@Nullable List<Base> data) {
        super(R.layout.item_my_post_reply, data);
    }

    @Override
    protected void convert2(BaseBindingViewHolder<ItemMyPostReplyBinding> helper, Base item, int position) {

    }
}
