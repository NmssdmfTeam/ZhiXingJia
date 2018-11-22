package com.zhihangjia.mainmodule.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;

import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.glide.util.GlideUtil;
import com.nmssdmf.commonlib.util.DensityUtil;
import com.nmssdmf.commonlib.view.GlideImageView;
import com.nmssdmf.customerviewlib.databindingbase.BaseBindingViewHolder;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.activity.ReplyActivity;
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
    private String bbsId;
    public CommentListContentAdapter(@Nullable List<MessageComment> data, String bbsId) {
        super(R.layout.item_comment_content, data);
        this.bbsId = bbsId;
    }

    @Override
    protected void convert2(BaseBindingViewHolder<ItemCommentContentBinding> helper, final MessageComment item, int position) {
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
        binding.tvToComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mContext, ReplyActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(IntentConfig.BBS_ID, bbsId);
                bundle.putString(IntentConfig.COMMENT_ID, item.getComment_id());
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
    }
}
