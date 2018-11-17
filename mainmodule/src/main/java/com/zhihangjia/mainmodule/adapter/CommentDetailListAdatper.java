package com.zhihangjia.mainmodule.adapter;

import android.support.annotation.Nullable;

import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.customerviewlib.databindingbase.BaseBindingViewHolder;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.databinding.ItemCommentDetailBinding;

import java.util.List;

/**
 * 评论详情adatper
 * Create by chenbin on 2018/11/17
 * <p>
 * <p>
 */
public class CommentDetailListAdatper extends BaseDataBindingAdapter<Base,ItemCommentDetailBinding> {

    public CommentDetailListAdatper(@Nullable List<Base> data) {
        super(R.layout.item_comment_detail, data);
    }

    @Override
    protected void convert2(BaseBindingViewHolder<ItemCommentDetailBinding> helper, Base item, int position) {

    }
}
