package com.zhihangjia.mainmodule.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.glide.util.GlideUtil;
import com.nmssdmf.commonlib.util.DensityUtil;
import com.nmssdmf.customerviewlib.databindingbase.BaseBindingViewHolder;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.activity.MessageDetailActivity;
import com.zhihangjia.mainmodule.databinding.ItemDailyHotNewsBinding;
import com.zhixingjia.bean.mainmodule.IndexBean;

import java.util.List;

/**
* @description 百姓信息分类模块adapter
* @author chenbin
* @date 2018/11/14 15:14
* @version v3.2.0
*/
public class MessageCenterModuleAdapter extends BaseDataBindingAdapter<IndexBean.ForumBean, ItemDailyHotNewsBinding> {
    public MessageCenterModuleAdapter(@Nullable List<IndexBean.ForumBean> data) {
        super(R.layout.item_daily_hot_news, data);
    }

    @Override
    protected void convert2(BaseBindingViewHolder<ItemDailyHotNewsBinding> helper, final IndexBean.ForumBean item, int position) {
        ItemDailyHotNewsBinding itemMessageModuleBinding = helper.getBinding();
        itemMessageModuleBinding.setData(item);
        if (item.getImgs()== null || item.getImgs().size() == 0) {
            itemMessageModuleBinding.llImgs.setVisibility(View.GONE);
            itemMessageModuleBinding.ivPic.setVisibility(View.GONE);
        } else if (item.getImgs().size() == 1) {
            itemMessageModuleBinding.llImgs.setVisibility(View.GONE);
            itemMessageModuleBinding.ivPic.setVisibility(View.VISIBLE);
            GlideUtil.load(itemMessageModuleBinding.ivPic,item.getImgs().get(0));
        } else {
            itemMessageModuleBinding.ivPic.setVisibility(View.GONE);
            itemMessageModuleBinding.llImgs.setVisibility(View.VISIBLE);
            itemMessageModuleBinding.llImgs.removeAllViews();
            int index = 0;
            for (String img:item.getImgs()) {
                if (index > 2)
                    break;
                ImageView imageView = new ImageView(mContext);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(DensityUtil.dpToPx(mContext,111),DensityUtil.dpToPx(mContext,75));
                if (index != 2)
                    layoutParams.rightMargin = DensityUtil.dpToPx(mContext,5);
                imageView.setLayoutParams(layoutParams);
                GlideUtil.load(imageView,img);
                itemMessageModuleBinding.llImgs.addView(imageView);
                index++;
            }
        }
        itemMessageModuleBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString(IntentConfig.ID, item.getBbs_id());
                intent.putExtras(bundle);
                intent.setClass(mContext, MessageDetailActivity.class);
                mContext.startActivity(intent);
            }
        });
    }
}
