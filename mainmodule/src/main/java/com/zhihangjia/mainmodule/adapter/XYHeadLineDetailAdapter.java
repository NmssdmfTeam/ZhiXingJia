package com.zhihangjia.mainmodule.adapter;

import android.content.Intent;
import android.databinding.Observable;
import android.databinding.ObservableBoolean;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.glide.util.GlideUtil;
import com.nmssdmf.commonlib.util.DensityUtil;
import com.nmssdmf.commonlib.view.GlideImageView;
import com.nmssdmf.customerviewlib.databindingbase.BaseBindingViewHolder;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingMultiItemQuickAdapter;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.activity.ReplyActivity;
import com.zhihangjia.mainmodule.bean.HeadLineDetailInfo;
import com.zhihangjia.mainmodule.databinding.ItemCommentContentBinding;
import com.zhihangjia.mainmodule.databinding.ItemImageContentBinding;
import com.zhihangjia.mainmodule.databinding.ItemYxcommentFilterBinding;
import com.zhihangjia.mainmodule.databinding.ItemYxheadlineDetailZanBinding;
import com.zhixingjia.bean.mainmodule.MessageComment;

import java.util.List;

/**
* @description 宜兴头题详情adapter
* @author chenbin
* @date 2019/1/25 20:13
* @version v3.2.0
*/
public class XYHeadLineDetailAdapter extends BaseDataBindingMultiItemQuickAdapter<HeadLineDetailInfo> {

    public final ObservableBoolean onlyLookBuilder = new ObservableBoolean(false);//是否只看楼主，默认是0，0=否 1=是
    public final ObservableBoolean isHot = new ObservableBoolean(false);//最赞
    public final ObservableBoolean isSortDesc = new ObservableBoolean(false);//最赞

    private int yxZanPosition = -1;
    private int yxCommentFilterPosition = -1;
    private ItemClickListener itemClickListener;
    private String yxHeadlineId;

    public XYHeadLineDetailAdapter(@Nullable List data, ItemClickListener itemClickListener) {
        super(data);
        this.itemClickListener = itemClickListener;
        addItemType(0,R.layout.item_image_content);
        addItemType(1,R.layout.item_yxheadline_detail_zan);
        addItemType(2,R.layout.item_yxcomment_filter);
        addItemType(3,R.layout.item_comment_content);
        isHot.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if (isHot.get())
                    itemClickListener.tvSortClick(isHot.get(), false);
            }
        });
    }

    @Override
    protected void convert2(BaseBindingViewHolder<ViewDataBinding> helper, HeadLineDetailInfo item, int position) {
        if (item.getItemType() == 0) {
            ItemImageContentBinding binding = (ItemImageContentBinding) helper.getBinding();
            binding.setData(item);
        } else if (item.getItemType() == 1) {
            ItemYxheadlineDetailZanBinding binding = (ItemYxheadlineDetailZanBinding) helper.getBinding();
            binding.setData(item);
        } else if (item.getItemType() == 2) {
            ItemYxcommentFilterBinding binding = (ItemYxcommentFilterBinding) helper.getBinding();
            binding.setAdapter(this);
        } else if (item.getItemType() == 3) {
            ItemCommentContentBinding binding = (ItemCommentContentBinding) helper.getBinding();
            binding.setData(item.getMessageComment());
            MessageComment.ContentsBean contentsBean = item.getMessageComment().getContents().get(0);
            binding.setContent(contentsBean);

            if (contentsBean.getImgs() != null && contentsBean.getImgs().size() > 0) {
                binding.tl.removeAllViews();
                binding.tl.setVisibility(View.VISIBLE);
                for (MessageComment.ContentsBean.ImgsBean img : contentsBean.getImgs()){
                    GlideImageView imageView = new GlideImageView(mContext);
                    GlideUtil.load(imageView, img.getM_url());
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtil.dpToPx(mContext, 111),DensityUtil.dpToPx(mContext, 111));
                    binding.tl.addView(imageView, params);
                }
            } else {
                binding.tl.setVisibility(View.GONE);
            }
            if (TextUtils.isEmpty(item.getMessageComment().getGive_sum()) || "0".equals(item.getMessageComment().getGive_sum())) {
                binding.tvZan.setText("点赞");
            } else {
                binding.tvZan.setText(item.getMessageComment().getGive_sum());
            }
            binding.tvToComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(mContext, ReplyActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(IntentConfig.YXHEADLINE_ID, yxHeadlineId);
                    bundle.putString(IntentConfig.COMMENT_ID, item.getMessageComment().getComment_id());
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                }
            });
            binding.tvZan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        itemClickListener.onZanClick(item.getMessageComment(), position);
                    }
                }
            });
        }
    }

    public interface ItemClickListener {
        void onZanClick(MessageComment item, int position);
        void tvSortClick(boolean isHot, boolean isSortDesc);
    }

    public void tvSortClick(View view) {
        this.isHot.set(false);
        this.isSortDesc.set(!this.isSortDesc.get());
        itemClickListener.tvSortClick(isHot.get(), isSortDesc.get());
    }

    public int getYxZanPosition() {
        return yxZanPosition;
    }

    public String getYxHeadlineId() {
        return yxHeadlineId;
    }

    public void setYxHeadlineId(String yxHeadlineId) {
        this.yxHeadlineId = yxHeadlineId;
    }

    public void setYxZanPosition(int yxZanPosition) {
        this.yxZanPosition = yxZanPosition;
    }

    public int getYxCommentFilterPosition() {
        return yxCommentFilterPosition;
    }

    public void setYxCommentFilterPosition(int yxCommentFilterPosition) {
        this.yxCommentFilterPosition = yxCommentFilterPosition;
    }

    public void notifyGiveInfo(String giveNames, int zanNum) {
        if (yxZanPosition > 0) {//若有点赞数据
            if (TextUtils.isEmpty(giveNames)) {//若点赞信息为空
                getData().remove(yxZanPosition);
                notifyItemRemoved(yxZanPosition + 1);
                yxZanPosition = -1;
                yxCommentFilterPosition -= 1;
            } else {
                //刷新点赞信息
                getData().get(yxZanPosition).setZanNum(zanNum);
                getData().get(yxZanPosition).setGiveNames(giveNames);
                notifyItemChanged(yxZanPosition + 1);
            }
        } else {//若没有点赞数量
            if (!TextUtils.isEmpty(giveNames)) {//若点赞数量不为空
                //创建点赞数据，并加入
                HeadLineDetailInfo headLineDetailInfo = new HeadLineDetailInfo();
                headLineDetailInfo.setType(1);
                headLineDetailInfo.setGiveNames(giveNames);
                headLineDetailInfo.setZanNum(zanNum);
                getData().add(yxCommentFilterPosition, headLineDetailInfo);
                yxZanPosition = yxCommentFilterPosition;
                yxCommentFilterPosition += 1;
                notifyItemInserted(yxZanPosition + 1);
            } else {
                //若点赞信息为空并且还没有点赞数据
            }
        }
    }
}
