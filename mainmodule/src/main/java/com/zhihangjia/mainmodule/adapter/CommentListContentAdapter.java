package com.zhihangjia.mainmodule.adapter;

import android.support.annotation.Nullable;
import android.widget.LinearLayout;

import com.nmssdmf.commonlib.glide.util.GlideUtil;
import com.nmssdmf.commonlib.util.DensityUtil;
import com.nmssdmf.commonlib.view.GlideImageView;
import com.nmssdmf.customerviewlib.databindingbase.BaseBindingViewHolder;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.databinding.ItemCommentContentBinding;
import com.zhixingjia.bean.mainmodule.MessageComment;

import java.util.List;

/**
* @description 评论详情内容adapter
* @author chenbin
* @date 2018/11/20 11:41
* @version v3.2.0
*/
public class CommentListContentAdapter extends BaseDataBindingAdapter<MessageComment,ItemCommentContentBinding> {

    public CommentListContentAdapter(@Nullable List<MessageComment> data) {
        super(R.layout.item_comment_content, data);
    }

    @Override
    protected void convert2(BaseBindingViewHolder<ItemCommentContentBinding> helper, MessageComment item, int position) {
        ItemCommentContentBinding binding= helper.getBinding();
        binding.setData(item);
        MessageComment.ContentsBean contentsBean = item.getContents().get(0);
        binding.setContent(contentsBean);

        if (contentsBean.getImgs() != null && contentsBean.getImgs().size() > 0) {
            binding.tl.removeAllViews();
            for (MessageComment.ContentsBean.ImgsBean img : contentsBean.getImgs()){
                GlideImageView imageView = new GlideImageView(mContext);
                GlideUtil.load(imageView, img.getM_url());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtil.dpToPx(mContext, 111),DensityUtil.dpToPx(mContext, 111));
                binding.tl.addView(imageView, params);
            }
        }
    }
}
