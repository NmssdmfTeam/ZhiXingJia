package com.zhihangjia.mainmodule.adapter;

import android.support.annotation.Nullable;

import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.customerviewlib.databindingbase.BaseBindingViewHolder;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.databinding.ItemCommentContentBinding;

import java.util.List;

/**
* @description 评论详情内容adapter
* @author chenbin
* @date 2018/11/20 11:41
* @version v3.2.0
*/
public class CommentListContentAdapter extends BaseDataBindingAdapter<Base,ItemCommentContentBinding> {

    public CommentListContentAdapter(@Nullable List<Base> data) {
        super(R.layout.item_comment_content, data);
    }

    @Override
    protected void convert2(BaseBindingViewHolder<ItemCommentContentBinding> helper, Base item, int position) {

    }
}
